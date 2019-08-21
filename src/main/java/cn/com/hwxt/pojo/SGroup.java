package cn.com.hwxt.pojo;

import java.io.Serializable;
import java.util.Objects;

public class SGroup implements Serializable {
    private static final long serialVersionUID = 7668913055238566167L;
    //<result column="DEPCODE" property="depcode" jdbcType="VARCHAR"/>code
    //<result column="DEPID" property="depid" jdbcType="VARCHAR"/> departmentid
    //<result column="GFZJ" property="gfzj" jdbcType="VARCHAR"/> getSubcompanyid
    //<result column="BH" property="bh" jdbcType="VARCHAR"/>    getSupdepartmentid
    private Integer did;

    private Integer pid;

    private String qzh;

    private Integer gid;

    private String gname;

    private String bz;

    private String depcode;//oa中code

    private String depid;//oa中departmentid

    private String gfzj;//oa中companyid

    private String bh; //oa中supdepartmentid

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

    public String getDepid() {
        return depid;
    }

    public void setDepid(String depid) {
        this.depid = depid;
    }

    public String getGfzj() {
        return gfzj;
    }

    public void setGfzj(String gfzj) {
        this.gfzj = gfzj;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh;
    }

    @Override
    public String toString() {
        return "SGroup{" +
                "did=" + did +
                ", pid=" + pid +
                ", qzh='" + qzh + '\'' +
                ", gid=" + gid +
                ", gname='" + gname + '\'' +
                ", bz='" + bz + '\'' +
                ", depcode='" + depcode + '\'' +
                ", depid='" + depid + '\'' +
                ", gfzj='" + gfzj + '\'' +
                ", bh='" + bh + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SGroup sGroup = (SGroup) o;
        return pid.equals(sGroup.pid) &&
                qzh.equals(sGroup.qzh) &&
                gname.equals(sGroup.gname) &&
                depcode.equals(sGroup.depcode) &&
                depid.equals(sGroup.depid) &&
                gfzj.equals(sGroup.gfzj) &&
                bh.equals(sGroup.bh);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, qzh, gname, depcode, depid, gfzj, bh);
    }
}