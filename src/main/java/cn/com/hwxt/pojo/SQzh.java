package cn.com.hwxt.pojo;

public class SQzh {
    private Integer did;

    private String qzh;

    private String qzmc;

    private Integer isdef;

    private String bz;

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getQzh() {
        return qzh;
    }

    public void setQzh(String qzh) {
        this.qzh = qzh == null ? null : qzh.trim();
    }

    public String getQzmc() {
        return qzmc;
    }

    public void setQzmc(String qzmc) {
        this.qzmc = qzmc == null ? null : qzmc.trim();
    }

    public Integer getIsdef() {
        return isdef;
    }

    public void setIsdef(Integer isdef) {
        this.isdef = isdef;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

}