package cn.com.hwxt.pojo;

import java.io.Serializable;

public class SGroup implements Serializable {
    private static final long serialVersionUID = 7668913055238566167L;

    private Integer did;

    private Integer pid;

    private String qzh;

    private Integer gid;

    private String gname;

    private String bz;

    private String depcode;//数据供应方主键

    private String gfzj;//排序使用


    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getQzh() {
        return qzh;
    }

    public void setQzh(String qzh) {
        this.qzh = qzh;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getDepcode() {
        return depcode;
    }

    public void setDepcode(String depcode) {
        this.depcode = depcode;
    }

    public String getGfzj() {
        return gfzj;
    }

    public void setGfzj(String gfzj) {
        this.gfzj = gfzj;
    }


}