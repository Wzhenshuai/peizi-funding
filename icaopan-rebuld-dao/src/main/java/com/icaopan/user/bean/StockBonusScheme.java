package com.icaopan.user.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 红股分配方案
 *
 * @author yong
 * @Description
 * @date 2017年4月20日
 */
public class StockBonusScheme implements Serializable {
	private static final long serialVersionUID = 1L;

	private String securityCode;
    
    private String securityName;
    //每10股送红利数
    private BigDecimal bonusProfit=BigDecimal.ZERO;
    //每10股送红股数
    private BigDecimal distributeStockAmount=BigDecimal.ZERO;
    //每10股转增股数
    private BigDecimal donationStockAmount=BigDecimal.ZERO;
    //股权登记日
    private String recordDate;
    //除权除息日
    private String exDivDate;
    
    public StockBonusScheme(){}

    public StockBonusScheme(String securityCode, BigDecimal bonusProfit,
			BigDecimal distributeStockAmount, BigDecimal donationStockAmount) {
		super();
		this.securityCode = securityCode;
		this.bonusProfit = bonusProfit;
		this.distributeStockAmount = distributeStockAmount;
		this.donationStockAmount = donationStockAmount;
	}

	public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public BigDecimal getBonusProfit() {
        return bonusProfit;
    }

    public void setBonusProfit(BigDecimal bonusProfit) {
        this.bonusProfit = bonusProfit;
    }

    public BigDecimal getDistributeStockAmount() {
        return distributeStockAmount;
    }

    public void setDistributeStockAmount(BigDecimal distributeStockAmount) {
        this.distributeStockAmount = distributeStockAmount;
    }

    public BigDecimal getDonationStockAmount() {
        return donationStockAmount;
    }

    public void setDonationStockAmount(BigDecimal donationStockAmount) {
        this.donationStockAmount = donationStockAmount;
    }

	public String getSecurityName() {
		return securityName;
	}

	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getExDivDate() {
		return exDivDate;
	}

	public void setExDivDate(String exDivDate) {
		this.exDivDate = exDivDate;
	}

    
}
