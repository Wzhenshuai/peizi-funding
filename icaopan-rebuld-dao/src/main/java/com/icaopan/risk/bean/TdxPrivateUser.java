package com.icaopan.risk.bean;



import com.icaopan.util.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

public class TdxPrivateUser {
    private Integer id;

    private String nickName;

    private String accountNo;

    private String tradeAccount;

    private String txPassword;

    private String jYPassWord;

    private String gddmSz;

    private String gddmSh;

    private BigDecimal warnLine;

    private BigDecimal openLine;

    private BigDecimal totalAmount;

    private BigDecimal available;

    private BigDecimal openRate;

    private BigDecimal warnRate;

	private String dllName;

    private String status;

    private String lastOperate;

    private String remark;

    private String updateBy;

    private Date updateTime;

    private String customerId;

    private String brokerinfoId;
        
    private String serverIp;
    
    private Integer port;
    
    private String brokerName;
    
    private String serverName;

    private String version;

    private String yybName;
    
    private Integer yybCode;
    
    private Integer yybId;
    
    private Integer serverId;
    
    private Integer brokerBaseInfoId;

    private String errorInfo;

    public TdxPrivateUser() {
    }

    public TdxPrivateUser(Integer id, String nickName, BigDecimal warnLine, BigDecimal openLine, String status, String remark) {
        this.id = id;
        this.nickName = nickName;
        this.warnLine = warnLine;
        this.openLine = openLine;
        this.status = status;
        this.remark = remark;
    }

    public Integer getYybId() {
		return yybId;
	}

	public void setYybId(Integer yybId) {
		this.yybId = yybId;
	}

	public Integer getServerId() {
		return serverId;
	}

	public void setServerId(Integer serverId) {
		this.serverId = serverId;
	}

	private String updateTimeStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getTradeAccount() {
        return tradeAccount;
    }

    public void setTradeAccount(String tradeAccount) {
        this.tradeAccount = tradeAccount == null ? null : tradeAccount.trim();
    }

    public String getTxPassword() {
        return txPassword;
    }

    public void setTxPassword(String txPassword) {
        this.txPassword = txPassword == null ? null : txPassword.trim();
    }

    public String getGddmSz() {
        return gddmSz;
    }

    public void setGddmSz(String gddmSz) {
        this.gddmSz = gddmSz == null ? null : gddmSz.trim();
    }

    public String getGddmSh() {
        return gddmSh;
    }

    public void setGddmSh(String gddmSh) {
        this.gddmSh = gddmSh == null ? null : gddmSh.trim();
    }

    public BigDecimal getWarnLine() {
        return warnLine;
    }

    public void setWarnLine(BigDecimal warnLine) {
        this.warnLine = warnLine;
    }

    public BigDecimal getOpenLine() {
        return openLine;
    }

    public void setOpenLine(BigDecimal openLine) {
        this.openLine = openLine;
    }


    public String getDllName() {
        return dllName;
    }

    public void setDllName(String dllName) {
        this.dllName = dllName == null ? null : dllName.trim();
    }

    public BigDecimal getOpenRate() {
        return openLine==null||totalAmount==null||openLine.compareTo(BigDecimal.ZERO)==0? BigDecimal.ZERO:totalAmount.divide(openLine,3, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getWarnRate() {
        return warnLine==null||totalAmount==null||warnLine.compareTo(BigDecimal.ZERO)==0? BigDecimal.ZERO:totalAmount.divide(warnLine,3, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getAvailable() {
        return available;
    }

    public void setAvailable(BigDecimal available) {
        this.available = available;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastOperate() {
        return lastOperate;
    }

    public void setLastOperate(String lastOperate) {
        this.lastOperate = lastOperate == null ? null : lastOperate.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getBrokerinfoId() {
        return brokerinfoId;
    }

    public void setBrokerinfoId(String brokerinfoId) {
        this.brokerinfoId = brokerinfoId == null ? null : brokerinfoId.trim();
    }

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getBrokerName() {
		return brokerName;
	}

	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getYybName() {
		return yybName;
	}

	public void setYybName(String yybName) {
		this.yybName = yybName;
	}

    public String getjYPassWord() {
        return jYPassWord;
    }

    public void setjYPassWord(String jYPassWord) {
        this.jYPassWord = jYPassWord;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getYybCode() {
        return yybCode;
    }

    public void setYybCode(Integer yybCode) {
        this.yybCode = yybCode;
    }

    public String getUpdateTimeStr() {
		return updateTime!=null? DateUtils.formatDateTime(updateTime):null;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public Integer getBrokerBaseInfoId() {
		return brokerBaseInfoId;
	}

	public void setBrokerBaseInfoId(Integer brokerBaseInfoId) {
		this.brokerBaseInfoId = brokerBaseInfoId;
	}

    public String getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
    }
}