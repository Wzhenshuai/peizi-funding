package com.icaopan.trade.service.impl;

import com.icaopan.common.util.ExceptionConstants;
import com.icaopan.customer.dao.CustomerMapper;
import com.icaopan.customer.model.BuyLimitChannel;
import com.icaopan.customer.model.Customer;
import com.icaopan.enums.enumBean.TradeStatus;
import com.icaopan.enums.enumBean.UserChannelType;
import com.icaopan.enums.enumBean.UserTradeType;
import com.icaopan.trade.dao.PlacementMapper;
import com.icaopan.trade.model.ChannelPlacement;
import com.icaopan.trade.model.Placement;
import com.icaopan.trade.service.dto.PlacementChannelDto;
import com.icaopan.user.model.ChannelSecurityPosition;
import com.icaopan.user.model.User;
import com.icaopan.util.BigDecimalUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


/**
 * Created by Wangzs on 2017-02-13.
 */
@Service("PlacementConvertServiceImpl")
public class PlacementConvertServiceImpl {

    @Autowired
    PlacementMapper placementMapper;

    @Autowired
    CustomerMapper customerMapper;

    /**
     * 买入下单拆弹策略
     *
     * @param user
     * @param channelList
     * @param placement
     * @return
     */
    public List<PlacementChannelDto> generateBuyPlacementChannel(User user, List<BuyLimitChannel> channelList, Placement placement) {
        if (user.getAvailable().compareTo(placement.getAmount()) < 0) {
            throw new RuntimeException(ExceptionConstants.CASH_NOT_ENOUGH);
        }
        //placement.getChannelId()说明是指定通道买入，不受交易策略限制，直接该通道买入
//        if(placement.getChannelId()!=null){
//            BuyLimitChannel channel = null;
//            for(BuyLimitChannel c : channelList){
//            	if(c.getId()!=null&&placement.getChannelId()!=null){
//            		 if(c.getId().intValue()==placement.getChannelId().intValue()){
//                         channel =c;
//                     }
//            	}
//            }
//            if(channel==null){
//                throw new RuntimeException(ExceptionConstants.CHANNEL_NOT_EXIST);
//            }
//            PlacementChannelDto placementChannelDto = new PlacementChannelDto(0,channel.getId(), channel.getCode());
//            placementChannelDto.setHandleQuantity(placement.getQuantity());
//            List<PlacementChannelDto> list = new ArrayList<>();
//            list.add(placementChannelDto);
//            return list;
//        }
        List<Integer> channelIdList=placement.getChannelIds();
        if(channelIdList!=null&&!channelIdList.isEmpty()){
        	//只保留选择的通道
        	Iterator<BuyLimitChannel> itor=channelList.iterator();
        	while(itor.hasNext()){
        		BuyLimitChannel channel=itor.next();
        		Integer chalId=channel.getId();
        		if(!channelIdList.contains(chalId)){
        			itor.remove();
        		}
        	}
        }
        //策略拆分通道
        switch (user.getUserTradeType()){
            case PRIOR:
                return generatePriorityBuyPlacementChannel(user, channelList, placement);
            case DIVIDE:
                return generateDivideBuyPlacementChannel(user, channelList, placement);
            case PROPORTION:
                return  generateProportionBuyPlacementChannel(user, channelList, placement);
        }
        return  new ArrayList<PlacementChannelDto>();
    }

    public List<PlacementChannelDto> generateSellPlacementChannel(UserTradeType userTradeType, BigDecimal userAvailable, Placement placement, List<ChannelSecurityPosition> securityPositionList) {


        //placement.getChannelId()说明是指定通道卖出，不受交易策略限制，直接该通道卖出
//        if(placement.getChannelId()!=null){
//            ChannelSecurityPosition position = null;
//            for(ChannelSecurityPosition p : securityPositionList){
//            	if(p.getChannelId()!=null&&placement.getChannelId()!=null){
//            		if(p.getChannelId().intValue()==placement.getChannelId().intValue()){
//                        position =p;
//                    }
//            	}
//            }
//            if(position==null){
//                throw new RuntimeException(ExceptionConstants.CHANNEL_NOT_EXIST);
//            }
//            if(position.getAvailable().intValue()<placement.getQuantity().intValue()){
//                throw new RuntimeException(ExceptionConstants.SELL_QUANTITY_NOT_ENOUGH);
//            }
//
//            PlacementChannelDto placementChannelDto = new PlacementChannelDto(0,position.getChannelId(), position.getChannelCode());
//            placementChannelDto.setHandleQuantity(placement.getQuantity());
//            List<PlacementChannelDto> list = new ArrayList<>();
//            list.add(placementChannelDto);
//            return list;
//        }

    	List<Integer> channelIdList=placement.getChannelIds();
        if(channelIdList!=null&&!channelIdList.isEmpty()){
        	//只保留选择的通道
        	Iterator<ChannelSecurityPosition> itor=securityPositionList.iterator();
        	while(itor.hasNext()){
        		ChannelSecurityPosition position=itor.next();
        		Integer chalId=position.getChannelId();
        		if(!channelIdList.contains(chalId)){
        			itor.remove();
        		}
        	}
        }
        //策略拆分通道
        switch (userTradeType){
            case PRIOR:
                return generatePrioritySellPlacementChannel(userAvailable, placement, securityPositionList);
            case DIVIDE:
            case PROPORTION://卖出时候按平分下单逻辑处理
            default:
                return generateDivideSellPlacementChannel(userAvailable, placement, securityPositionList);
        }
    }


    /**
     * 比例买入拆单
     *
     * @param user
     * @param channelList
     * @param placement
     * @return
     */
    private List<PlacementChannelDto> generateProportionBuyPlacementChannel(User user, List<BuyLimitChannel> channelList, Placement placement) {
        List<PlacementChannelDto> placementChannelDtoList = new ArrayList<>();
        //买入股票的金额
        BigDecimal price = placement.getPrice();
        int quantity = placement.getQuantity().intValue();
        int proportionCount =0;//比例总和
        for(BuyLimitChannel channel : channelList){
            proportionCount += channel.getProportion();
        }
        int quantityWholeCount=0;//比例下单的整股的总和
        for(BuyLimitChannel channel : channelList){
            int channelQuantity = (quantity*channel.getProportion()/proportionCount/100)*100;
            quantityWholeCount += channelQuantity;
        }
        int oddQuantity = quantity - quantityWholeCount;//（取整后的零头数量）
        //按可用金额从小到大排列
        Collections.sort(channelList, new Comparator<BuyLimitChannel>() {
            @Override
            public int compare(BuyLimitChannel o1, BuyLimitChannel o2) {
                return o1.getAvailable().doubleValue() - o2.getAvailable().doubleValue() > 0 ? 1 : -1;
            }
        });
        //零头优先安排到可用金额小的通道下单
        for (BuyLimitChannel channel : channelList) {
            int channelQuantity = 0;
            int divideQuantity = (BigDecimalUtil.divide(channel.getAvailable(), price).intValue() / 100) * 100;//通道按可用下单的数量
            int proportionQuantity = (quantity*channel.getProportion()/proportionCount/100)*100;//通道按比例下单的数量
            int quotaQuantity = (BigDecimalUtil.divide(channel.getQuota(), price).intValue() / 100) * 100;
            if (divideQuantity > proportionQuantity + oddQuantity) {//通道可用资金充足，将零头也下单
                channelQuantity = proportionQuantity + oddQuantity;
                oddQuantity = 0;
            } else if (divideQuantity > proportionQuantity) {//通道资金超过平均数，但不足添加全部零头，资金通道全用
                channelQuantity = divideQuantity;
                oddQuantity = oddQuantity - (divideQuantity - proportionQuantity);
            } else {//通道资金不足，资金全用
                channelQuantity = divideQuantity;
                oddQuantity += proportionQuantity - channelQuantity;
            }
            if(channelQuantity<=0){
                continue;
            }
            PlacementChannelDto placementChannelDto = new PlacementChannelDto(channel.getId(), channel.getCode(), quotaQuantity, channel.getPriorityLevel(), channel.getAvailable().intValue());
            placementChannelDto.setHandleQuantity(new BigDecimal(channelQuantity));
            if(StringUtils.equals(channel.getUserChannelTypeVal(), UserChannelType.UNLIMITED.getCode())){
                placementChannelDto.setLeft(1);
            }else{
                placementChannelDto.setLeft(-1);//用-1表示限额,1表示非限额
            }
            placementChannelDtoList.add(placementChannelDto);
        }
        if (placementChannelDtoList.size() == 0) {//所有通道都不满足情况下，挑第一个通道下单（通常会造成该通道委托废单）
//            BuyLimitChannel channel = channelList.get(0);
//            //通道限额不足情况下，页面异常出错
//            if (StringUtils.equals(channel.getUserChannelTypeVal(), UserChannelType.LIMITED.getCode())&&channel.getQuota().compareTo(placement.getAmount()) < 0) {
//                throw new RuntimeException(ExceptionConstants.QUOTA_NOT_ENOUGH);
//            }
//            PlacementChannelDto placementChannelDto = new PlacementChannelDto(channel.getId(), channel.getCode(), 0, channel.getPriorityLevel(), channel.getAvailable().intValue());
//            placementChannelDto.setHandleQuantity(placement.getQuantity());
//            placementChannelDtoList.add(placementChannelDto);
//            return placementChannelDtoList;
        	throw new RuntimeException("无可用下单通道");
        }
        //还剩下零头的话，就加到非限额的通道中，否则就说明符合要求的抛异常
        if (oddQuantity > 0) {
            boolean added =false;
            //当前已经选中的委托中是否有不限额的，有就加入到不限额的那个中
            for(PlacementChannelDto placementChannelDto : placementChannelDtoList) {
                if(placementChannelDto.getLeft()==1){
                    int firstQuantity = placementChannelDto.getHandleQuantity().intValue();
                    placementChannelDto.setHandleQuantity(new BigDecimal(firstQuantity + oddQuantity));
                    added = true;
                    break;
                }
            }
            //用户通道中是否有不限额的，有就加入到不限额的中
            if(added==false){
                for (BuyLimitChannel channel : channelList) {
                    if (StringUtils.equals(channel.getUserChannelTypeVal(), UserChannelType.UNLIMITED.getCode())){
                        PlacementChannelDto placementChannelDto = new PlacementChannelDto(channel.getId(), channel.getCode(), 0, channel.getPriorityLevel(), channel.getAvailable().intValue());
                        placementChannelDto.setHandleQuantity(new BigDecimal(oddQuantity));
                        placementChannelDtoList.add(placementChannelDto);
                        added = true;
                        break;
                    }
                }
            }
            if(added==false) {
                //根据限额额度来调整下单数量
                for(PlacementChannelDto placementChannelDto : placementChannelDtoList) {
                    if(oddQuantity == 0){
                        break;
                    }
                    int betaQuantity = placementChannelDto.getWhole() - placementChannelDto.getHandleQuantity().intValue();
                    if(betaQuantity <= 0){
                        continue;
                    }
                    if(betaQuantity >= oddQuantity){
                        placementChannelDto.setHandleQuantity(new BigDecimal(oddQuantity).add(placementChannelDto.getHandleQuantity()));
                        oddQuantity = 0;
                    }else{
                        placementChannelDto.setHandleQuantity(new BigDecimal(placementChannelDto.getWhole()));
                        oddQuantity -= betaQuantity;
                    }
                }

            }
            if(added == false && oddQuantity > 0){
                throw new RuntimeException(ExceptionConstants.QUOTA_NOT_ENOUGH);
            }
        }
        return placementChannelDtoList;
    }

    /**
     * 平均买入拆单
     *
     * @param user
     * @param channelList
     * @param placement
     * @return
     */
    private List<PlacementChannelDto> generateDivideBuyPlacementChannel(User user, List<BuyLimitChannel> channelList, Placement placement) {
        List<PlacementChannelDto> placementChannelDtoList = new ArrayList<>();
        //买入股票的金额
        BigDecimal price = placement.getPrice();
        int quantity = placement.getQuantity().intValue();
        int averageQuantity = (quantity / channelList.size() / 100) * 100;//平均的数量（按100取整）
        int oddQuantity = quantity - averageQuantity * channelList.size();//（取整后的零头数量）
        //按可用金额从小到大排列
        Collections.sort(channelList, new Comparator<BuyLimitChannel>() {
            @Override
            public int compare(BuyLimitChannel o1, BuyLimitChannel o2) {
                return o1.getAvailable().doubleValue() - o2.getAvailable().doubleValue() > 0 ? 1 : -1;
            }
        });
        //零头优先安排到可用金额小的通道下单
        for (BuyLimitChannel channel : channelList) {
            int channelQuantity = 0;
            int divideQuantity = (BigDecimalUtil.divide(channel.getAvailable(), price).intValue() / 100) * 100;
            int quotaQuantity = (BigDecimalUtil.divide(channel.getQuota(), price).intValue() / 100) * 100;
            if (divideQuantity > averageQuantity + oddQuantity) {//通道可用资金充足，将零头也下单
                channelQuantity = averageQuantity + oddQuantity;
                oddQuantity = 0;
            } else if (divideQuantity > averageQuantity) {//通道资金超过平均数，但不足添加全部零头，资金通道全用
                channelQuantity = divideQuantity;
                oddQuantity = oddQuantity - (divideQuantity - averageQuantity);
            } else {//通道资金不足，资金全用
                channelQuantity = divideQuantity;
                oddQuantity += averageQuantity - channelQuantity;
            }
            if(channelQuantity<=0){
                continue;
            }
            PlacementChannelDto placementChannelDto = new PlacementChannelDto(channel.getId(), channel.getCode(), quotaQuantity, channel.getPriorityLevel(), channel.getAvailable().intValue());
            placementChannelDto.setHandleQuantity(new BigDecimal(channelQuantity));
            if(StringUtils.equals(channel.getUserChannelTypeVal(), UserChannelType.UNLIMITED.getCode())){
                placementChannelDto.setLeft(1);
            }else{
                placementChannelDto.setLeft(-1);//用-1表示限额,1表示非限额
            }
            placementChannelDtoList.add(placementChannelDto);
        }
        if (placementChannelDtoList.size() == 0) {//所有通道都不满足情况下，挑第一个通道下单（通常会造成该通道委托废单）
//            BuyLimitChannel channel = channelList.get(0);
//            //通道限额不足情况下，页面异常出错
//            if (StringUtils.equals(channel.getUserChannelTypeVal(), UserChannelType.LIMITED.getCode())&&channel.getQuota().compareTo(placement.getAmount()) < 0) {
//                throw new RuntimeException(ExceptionConstants.QUOTA_NOT_ENOUGH);
//            }
//            PlacementChannelDto placementChannelDto = new PlacementChannelDto(channel.getId(), channel.getCode(), 0, channel.getPriorityLevel(), channel.getAvailable().intValue());
//            placementChannelDto.setHandleQuantity(placement.getQuantity());
//            placementChannelDtoList.add(placementChannelDto);
//            return placementChannelDtoList;
        	throw new RuntimeException("无可用下单通道");
        }
        //还剩下零头的话，就加到非限额的通道中，否则就说明符合要求的抛异常
        if (oddQuantity > 0) {
            boolean added =false;
            //当前已经选中的委托中是否有不限额的，有就加入到不限额的那个中
            for(PlacementChannelDto placementChannelDto : placementChannelDtoList) {
                if(placementChannelDto.getLeft()==1){
                    int firstQuantity = placementChannelDto.getHandleQuantity().intValue();
                    placementChannelDto.setHandleQuantity(new BigDecimal(firstQuantity + oddQuantity));
                    added = true;
                    break;
                }
            }
            //用户通道中是否有不限额的，有就加入到不限额的中
            if(added==false){
                for (BuyLimitChannel channel : channelList) {
                    if (StringUtils.equals(channel.getUserChannelTypeVal(), UserChannelType.UNLIMITED.getCode())){
                        PlacementChannelDto placementChannelDto = new PlacementChannelDto(channel.getId(), channel.getCode(), 0, channel.getPriorityLevel(), channel.getAvailable().intValue());
                        placementChannelDto.setHandleQuantity(new BigDecimal(oddQuantity));
                        placementChannelDtoList.add(placementChannelDto);
                        added = true;
                        break;
                    }
                }
            }
            if(added==false) {
                //根据限额额度来调整下单数量
                for(PlacementChannelDto placementChannelDto : placementChannelDtoList) {
                    if(oddQuantity == 0){
                        break;
                    }
                    int betaQuantity = placementChannelDto.getWhole() - placementChannelDto.getHandleQuantity().intValue();
                    if(betaQuantity <= 0){
                        continue;
                    }
                    if(betaQuantity >= oddQuantity){
                        placementChannelDto.setHandleQuantity(new BigDecimal(oddQuantity).add(placementChannelDto.getHandleQuantity()));
                        oddQuantity = 0;
                    }else{
                        placementChannelDto.setHandleQuantity(new BigDecimal(placementChannelDto.getWhole()));
                        oddQuantity -= betaQuantity;
                    }
                }

            }
            if(added == false && oddQuantity > 0){
                throw new RuntimeException(ExceptionConstants.QUOTA_NOT_ENOUGH);
            }
        }
        return placementChannelDtoList;
    }

    /**
     * 优先买入拆单
     *
     * @param user
     * @param channelList
     * @param placement
     * @return
     */
    private List<PlacementChannelDto> generatePriorityBuyPlacementChannel(User user, List<BuyLimitChannel> channelList, Placement placement) {
        //用户可用大于买入的所需的金额
        int quantity = placement.getQuantity().intValue();
        BigDecimal price = placement.getPrice();
        List<PlacementChannelDto> newList = new ArrayList<>();
        List<PlacementChannelDto> wholeList = new ArrayList<>();//整股list
        BigDecimal totalAvailable = null;
        int totalQuantity = 0;
        for (BuyLimitChannel channel : channelList) {
            if (channel.getIsAvailable() == false) {
                continue;
            }
            totalAvailable = BigDecimalUtil.add(totalAvailable, channel.getAvailable());
            int channelQuantity = BigDecimalUtil.divide(channel.getAvailable(), price).intValue();
            int quatoQuantity = (BigDecimalUtil.divide(channel.getAvailable(), price).intValue()/100)*100;
            int wholeQuantity = (channelQuantity / 100) * 100;
            totalQuantity += wholeQuantity;
            if (wholeQuantity != 0) {
                PlacementChannelDto placementChannelDto = new PlacementChannelDto(channel.getId(), channel.getCode(), wholeQuantity, channel.getPriorityLevel(), channel.getAvailable().intValue());
                placementChannelDto.setLeft(quatoQuantity);//用left表示限额的下单数量
                if (StringUtils.equals(channel.getUserChannelTypeVal(), UserChannelType.UNLIMITED.getCode())) {
                    placementChannelDto.setHandleQuantity(BigDecimal.ZERO);//用handleQuantity非空来表示非限额
                }
                wholeList.add(placementChannelDto);
            }
        }
        //当通道可用、限额中小值累加不够数量时，需要考虑限额超可用时，使用限额
        for(PlacementChannelDto dto : wholeList){
            if(totalQuantity >= quantity) {
                break;
            }
            if(dto.getHandleQuantity()!=null){
                dto.setWhole(dto.getWhole() + quantity - totalQuantity);
                totalQuantity = quantity;
            }else if(dto.getLeft() > dto.getWhole()){
                totalQuantity += dto.getLeft() - dto.getWhole();
                dto.setWhole(dto.getLeft());
            }
        }

        if (totalQuantity < quantity) {//通道限额不足情况下，如果存在非限额的通道，就直接全部下一单，页面异常出错
            for (BuyLimitChannel channel : channelList) {
                if (StringUtils.equals(channel.getUserChannelTypeVal(), UserChannelType.UNLIMITED.getCode())){
                    PlacementChannelDto placementChannelDto = new PlacementChannelDto(channel.getId(), channel.getCode(), 0, channel.getPriorityLevel(), channel.getAvailable().intValue());
                    placementChannelDto.setHandleQuantity(placement.getQuantity());
                    newList.add(placementChannelDto);
                    return newList;
                }
            }
            throw new RuntimeException(ExceptionConstants.QUOTA_NOT_ENOUGH);
        }
        Collections.sort(wholeList, new Comparator<PlacementChannelDto>() {
            @Override
            public int compare(PlacementChannelDto o1, PlacementChannelDto o2) {
                if (o1.getPriorityLevel() != o2.getPriorityLevel()) {
                    return o1.getPriorityLevel() - o2.getPriorityLevel();
                }
                return o2.getCashAvailable() - o1.getCashAvailable();
            }
        });
        for (PlacementChannelDto placementChannelDto : wholeList) {
            if (quantity == 0) {
                break;
            }
            if (quantity >= placementChannelDto.getWhole()) {
                placementChannelDto.setHandleQuantity(new BigDecimal(placementChannelDto.getWhole()));
                newList.add(placementChannelDto);
                quantity -= placementChannelDto.getWhole();
            } else {
                placementChannelDto.setHandleQuantity(new BigDecimal(quantity));
                newList.add(placementChannelDto);
                quantity = 0;
            }
        }

        return newList;
    }


    /**
     * 平均卖出拆单
     *
     * @param userAvailable
     * @param placement
     * @param securityPositionList
     * @return
     */
    public List<PlacementChannelDto> generateDivideSellPlacementChannel(BigDecimal userAvailable, Placement placement, List<ChannelSecurityPosition> securityPositionList) {
        List<ChannelSecurityPosition> positionList = new ArrayList<ChannelSecurityPosition>();
        for(ChannelSecurityPosition position : securityPositionList){
            if(position.getAvailable().intValue()>0){
                positionList.add(position);
            }
        }
        List<PlacementChannelDto> placementChannelDtoList = new ArrayList<>();
        List<PlacementChannelDto> leftList = new ArrayList<>();//零股list
        int quantity = placement.getQuantity().intValue();
        int pLeft = quantity % 100;//委托的零股
        int averageQuantity = (quantity / positionList.size() / 100) * 100;//平均的数量（按100取整）
        int oddQuantity = quantity - averageQuantity * positionList.size();//取整后的零头数量
        
        //如果quantity有零股，则必须是全部卖出
        if(pLeft!=0){
            if(userAvailable.intValue()!=quantity){
                throw new RuntimeException(ExceptionConstants.PLACEMENT_QUANTITY_LEFT);
            }
            for (ChannelSecurityPosition position : positionList) {
                BigDecimal channelQuantity = position.getAvailable();
                int leftQuantity = channelQuantity.intValue()%100;
                int wholeQuantity = channelQuantity.intValue() - leftQuantity;
                if(leftQuantity == 0) {
                    PlacementChannelDto placementChannelDto = new PlacementChannelDto(position.getChannelId(), position.getChannelCode(), channelQuantity.intValue(), 0, 0);
                    placementChannelDto.setHandleQuantity(channelQuantity);
                    placementChannelDtoList.add(placementChannelDto);
                }else{
                    PlacementChannelDto placementChannelDto = new PlacementChannelDto(position.getChannelId(), position.getChannelCode(), wholeQuantity, 0, 0);
                    placementChannelDto.setHandleQuantity(new BigDecimal(wholeQuantity));
                    placementChannelDtoList.add(placementChannelDto);
                    placementChannelDto = new PlacementChannelDto(position.getChannelId(), position.getChannelCode(), leftQuantity, 0, 0);
                    placementChannelDto.setHandleQuantity(new BigDecimal(leftQuantity));
                    placementChannelDtoList.add(placementChannelDto);
                }
            }
            return  placementChannelDtoList;
        }
        
        //按持仓数量从小到大排列
        Collections.sort(positionList, new Comparator<ChannelSecurityPosition>() {
            @Override
            public int compare(ChannelSecurityPosition o1, ChannelSecurityPosition o2) {
                return o1.getAvailable().intValue() - o2.getAvailable().intValue();
            }
        });
        //零头优先安排到可用数量小的通道下单
        for (ChannelSecurityPosition position : positionList) {
            int intAvailablePosition = position.getAvailable().intValue();//持仓可用
            int left = intAvailablePosition % 100;//持仓可用零股
            int whole = intAvailablePosition - left;//持仓可用整股
            int channelQuantity = 0;
            //将零股部分存起来
            if (left != 0) {
                PlacementChannelDto leftPlacementChannel = new PlacementChannelDto(left, position.getChannelId(), position.getChannelCode());
                leftList.add(leftPlacementChannel);
            }

            if (whole > averageQuantity + oddQuantity) {//通道可用整股头寸充足，将零头部分也下单
                channelQuantity = averageQuantity + oddQuantity;
                oddQuantity = 0;
            } else if (whole > averageQuantity) {//通道可用整股头寸超过平均数，但不足添加全部零头部分，通道整股部分全用
                channelQuantity = whole;
                oddQuantity = oddQuantity - (whole - averageQuantity);
            } else {//通道可用整股不足，头寸整股部分全用
                channelQuantity = whole;
                oddQuantity += averageQuantity - channelQuantity;
            }
            if(channelQuantity==0){
                continue;
            }
            PlacementChannelDto placementChannelDto = new PlacementChannelDto(position.getChannelId(), position.getChannelCode(), channelQuantity, 0, 0);
            placementChannelDto.setHandleQuantity(new BigDecimal(channelQuantity));
            placementChannelDtoList.add(placementChannelDto);
        }
        if(oddQuantity!=0) {
            //还剩余未下单部分是 oddWholeQuantity+oddLeftQuantity,将剩余部分加入进来
            placementChannelDtoList.addAll(chooseLeft(leftList, oddQuantity));
        }
        
        return placementChannelDtoList;
    }

    /**
     * 优先卖出拆单
     *
     * @param userAvailable
     * @param placement
     * @param securityPositionList
     * @return
     */
    public List<PlacementChannelDto> generatePrioritySellPlacementChannel(BigDecimal userAvailable, Placement placement, List<ChannelSecurityPosition> securityPositionList) {
        int quantity = placement.getQuantity().intValue();
        List<PlacementChannelDto> newList = new ArrayList<>();
        int pLeft = quantity % 100;//委托的零股
        int availableWhole = 0;//整股持仓总和
        List<PlacementChannelDto> wholeList = new ArrayList<>();//整股list
        List<PlacementChannelDto> leftList = new ArrayList<>();//零股list
        for (ChannelSecurityPosition securityPosition : securityPositionList) {
            PlacementChannelDto placementChannelDto = new PlacementChannelDto();
            int intAvailablePosition = securityPosition.getAvailable().intValue();
            int left = intAvailablePosition % 100;
            int whole = intAvailablePosition - left;
            availableWhole += whole;
            if (whole != 0) {
                placementChannelDto.setWhole(whole);
                placementChannelDto.setChannelId(securityPosition.getChannelId());
                placementChannelDto.setChannelCode(securityPosition.getChannelCode());
                wholeList.add(placementChannelDto);
            }
            if (left != 0) {
                PlacementChannelDto leftPlacementChannel = new PlacementChannelDto(left, securityPosition.getChannelId(), securityPosition.getChannelCode());
                leftList.add(leftPlacementChannel);
            }
        }
        if (userAvailable.intValue() == quantity) {//委托数量=持仓数量
            newList.addAll(convertWholeList(wholeList));
            newList.addAll(convertLeftList(leftList));
        } else {
            if (pLeft != 0) {//有零股
                throw new RuntimeException(ExceptionConstants.PLACEMENT_QUANTITY_LEFT);
            }
            if (userAvailable.intValue() < quantity) {//委托数量>可卖数量
                throw new RuntimeException(ExceptionConstants.SELL_QUANTITY_NOT_ENOUGH);
            }
            if (availableWhole >= quantity) {//可卖整股>委托数量
                Collections.sort(wholeList, new Comparator<PlacementChannelDto>() {
                    @Override
                    public int compare(PlacementChannelDto o1, PlacementChannelDto o2) {
                        return o2.getWhole() - o1.getWhole();
                    }
                });
                for (PlacementChannelDto p : wholeList) {
                    if (quantity == 0) {
                        break;
                    }
                    if (quantity >= p.getWhole()) {
                        p.setHandleQuantity(new BigDecimal(p.getWhole()));
                        newList.add(p);
                        quantity -= p.getWhole();
                    } else {
                        p.setHandleQuantity(new BigDecimal(quantity));
                        newList.add(p);
                        quantity = 0;
                    }
                }
            } else {//可卖整股<委托数量
                newList.addAll(convertWholeList(wholeList));//先加上所有整股
                newList.addAll(chooseLeft(leftList, quantity - availableWhole));
            }
        }
        return newList;

    }

    public List<PlacementChannelDto> convertLeftList(List<PlacementChannelDto> list) {
        for (PlacementChannelDto placementChannelDto : list) {
            placementChannelDto.setHandleQuantity(new BigDecimal(placementChannelDto.getLeft()));
        }
        return list;
    }

    public List<PlacementChannelDto> convertWholeList(List<PlacementChannelDto> list) {
        for (PlacementChannelDto placementChannelDto : list) {
            placementChannelDto.setHandleQuantity(new BigDecimal(placementChannelDto.getWhole()));
        }

        return list;

    }

    public List<PlacementChannelDto> chooseLeft(List<PlacementChannelDto> list, int amount) {
        {
            List<PlacementChannelDto> newList = new ArrayList<PlacementChannelDto>();
            PlacementChannelDto lastBigger = null;//最后一个比委托零股数量大的持仓
            Collections.sort(list, new Comparator<PlacementChannelDto>() {
                @Override
                public int compare(PlacementChannelDto o2, PlacementChannelDto o1) {
                    return o1.getLeft() - o2.getLeft();
                }
            });
            for (int i = 0; i < list.size(); i++) {
                PlacementChannelDto p = list.get(i);
                if (amount >= 100) {//剩余委托数量大于等于100时，直接扣减该零股
                    p.setHandleQuantity(new BigDecimal(p.getLeft()));
                    newList.add(p);
                    amount -= p.getLeft();
                    continue;
                }
                if (p.getLeft() > amount) {//剩余委托数量小于100时，当持仓数量大于剩余委托数量时(不是最后一个持仓)，跳过
                    if (i != list.size() - 1) {
                        lastBigger = p;
                        continue;
                    } else {
                        p.setHandleQuantity(new BigDecimal(amount));
                        newList.add(p);//最后一个持仓情况下，卖出剩余委托数量后，直接返回
                        return newList;
                    }
                } else if (p.getLeft() == amount) {
                    p.setHandleQuantity(new BigDecimal(amount));
                    newList.add(p);//相等时也直接返回
                    return newList;
                }
                //剩余委托数量小于100时，当持仓数量小于剩余委托数量时，扣减该零股
                p.setHandleQuantity(new BigDecimal(p.getLeft()));
                newList.add(p);
                amount -= p.getLeft();
            }
            if (amount != 0) {
                if(lastBigger==null){
                    throw new RuntimeException(ExceptionConstants.SELL_QUANTITY_NOT_ENOUGH);
                }
                lastBigger.setHandleQuantity(new BigDecimal(amount));
                newList.add(lastBigger);
            }

            return newList;
        }
    }

    //list 排序
    public List<ChannelSecurityPosition> collectionsList(List<ChannelSecurityPosition> securityPositionList) {
        Collections.sort(securityPositionList, new Comparator<ChannelSecurityPosition>() {
            @Override
            public int compare(ChannelSecurityPosition o1, ChannelSecurityPosition o2) {
                return BigDecimalUtil.compareTo(o2.getAvailable(), o1.getAvailable());
            }
        });
        return securityPositionList;
    }

    //封装通道委托参数
    public ChannelPlacement wrapChannelPlacement(Placement placement) {
        ChannelPlacement channelPlacement = new ChannelPlacement();

        channelPlacement.setPlacementId(placement.getId());
        channelPlacement.setSecurityCode(placement.getSecurityCode());
        channelPlacement.setSide(placement.getSide());
        channelPlacement.setUserName(placement.getUserName());
        channelPlacement.setCustomerId(placement.getCustomerId());
        channelPlacement.setUserId(placement.getUserId());
        channelPlacement.setPrice(BigDecimalUtil.valueOfScale4(placement.getPrice()));
        channelPlacement.setAmount(BigDecimalUtil.multiply(placement.getQuantity(),placement.getPrice()));
        channelPlacement.setQuantity(placement.getQuantity());
        channelPlacement.setCreateTime(placement.getTime());
        channelPlacement.setChannelId(placement.getChannelId());
        channelPlacement.setAccount(placement.getChannelCode());
        channelPlacement.setStatus(TradeStatus.SENDACK);
        return channelPlacement;
    }

    public Placement reWrapPlacement(Placement placement, User user) {
        placement.setAmount(placement.getAmount());
        placement.setUserName(user.getUserName());
        placement.setStatus(TradeStatus.SENDACK);
        placement.setIsSzTransferFee(user.getSzTransferFee());
        placement.setMinCost(user.getMinCost());
        placement.setRatioFee(user.getRatioFee());
        placement.setUserId(user.getId());
        placement.setCustomerId(user.getCustomer().getId());
        placement.setTime(new Date());
        if (StringUtils.equals(user.getIsDefaultFee(), "1")) {//采用资金方默认低消
            Customer customer = customerMapper.selectCustomerById(user.getCustomer().getId());
//            placement.setMinCost(customer.getDefaultMinCost());//暂时先去掉，低消和最低费率应该要分开才对
            placement.setRatioFee(customer.getDefaultTatio());
        }
        return placement;
    }
}
