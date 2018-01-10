package com.icaopan.marketdata.provider.tonghuashun;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.icaopan.marketdata.market.StockBonus;
import com.icaopan.util.HttpUtil;

public class ThsDataProvider {

	private static String bonusUrl="http://x.10jqka.com.cn/stockpick/search?typed=1&preParams=&ts=1&f=1&qs=result_rewrite&w=%E9%99%A4%E6%9D%83%E9%99%A4%E6%81%AF%E6%97%A5%3D";
	
	
	/**
	 * 
	 * @param exDivDate 除权除息日
	 * @return
	 * @throws IOException
	 * @throws EncoderException
	 */
	public static List<StockBonus> getStockBonus(String exDivDate) throws Exception{
		List<StockBonus> list=new ArrayList<StockBonus>();
		if(StringUtils.isNotEmpty(exDivDate)){
			if(exDivDate.contains("-")){
	        	exDivDate=exDivDate.replaceAll("-", "");
	        }
	        String url=bonusUrl+exDivDate;
	        try {
				String content=HttpUtil.readContentFromJQKGet(url);
				Document doc = Jsoup.parse(content);
				Elements  es=doc.getElementsByAttributeValueContaining("fullstr", "10");
				for (Element element : es) {
					String fullStr=element.attr("fullstr");
					String href=element.attr("href");
					String zhuanQuantity="0";
					String songQuantity="0";
					String paiCash="0";
					StockBonus bonus=new StockBonus();
					if(StringUtils.isNotEmpty(fullStr)){
						if(fullStr.contains("转")){
							int zhuanIndex=fullStr.indexOf("转");
							int guIndex=fullStr.lastIndexOf("股");
							zhuanQuantity=fullStr.substring(zhuanIndex+1,guIndex);
						}
						if(fullStr.contains("送")){
							int songIndex=fullStr.indexOf("送");
							int guIndex=fullStr.indexOf("股");
							songQuantity=fullStr.substring(songIndex+1, guIndex);
						}
						if(fullStr.contains("派")){
							int paiIndex=fullStr.indexOf("派");
							int yuanIndex=fullStr.indexOf("元");
							paiCash=fullStr.substring(paiIndex+1,yuanIndex);
						}
						String code=getStockCode(href);
						bonus.setTicker(code);
						bonus.setExDivDate(exDivDate);
						bonus.setPerCashDiv(Double.valueOf(paiCash));
						bonus.setPerShareDivRatio(Double.valueOf(songQuantity));
						bonus.setPerShareTransRatio(Double.valueOf(zhuanQuantity));
						list.add(bonus);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException("获取同花顺分红配股信息失败："+e.getMessage());
			}
		}
        return list;
    }
	
	private static String getStockCode(String href){
		String code="";
		if(href.contains(".SZ")){
			int szIndex=href.indexOf(".SZ");
			code=href.substring(szIndex-6, szIndex);
		}else if(href.contains(".SH")){
			int shIndex=href.indexOf(".SH");
			code=href.substring(shIndex-6, shIndex);
		}
		if(code.length()!=6){
			throw new RuntimeException("解析股票代买失败");
		}
		return code;
	}
	
	public static void main(String[] args) {
		try {
			getStockBonus("2017.12.06");
			System.out.println("dddd");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
