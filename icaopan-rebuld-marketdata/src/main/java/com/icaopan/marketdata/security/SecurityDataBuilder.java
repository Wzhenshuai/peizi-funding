package com.icaopan.marketdata.security;

import com.icaopan.marketdata.constant.SecurityTypeConsts;
import com.icaopan.marketdata.provider.tonglian.Ticker;
import org.springframework.stereotype.Service;

@Service
public class SecurityDataBuilder {

    public SecurityData convert(Ticker ticker) {
        SecurityData securityData = new SecurityData();
        securityData.setSymbol(ticker.getTicker());
        securityData.setName(ticker.getShortNM());
        securityData.setExchange(ticker.getExchangeCD());
        securityData.setType(SecurityTypeConsts.STOCK);
        if (ticker.getSuspension() != null) {
            securityData.setSuspensionFlag(ticker.getSuspension() == 1);
        }
        return securityData;
    }

}
