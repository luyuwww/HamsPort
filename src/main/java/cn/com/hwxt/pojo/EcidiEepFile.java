package cn.com.hwxt.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author DaMo
 * 华东院提供的meta_file.xml解释 文件对象
 * @UPDATE 2020/4/2-23:56
 * @since 2020/4/2-23:56
 */
public class EcidiEepFile implements Serializable {
    private static final long serialVersionUID = -628241464125557958L;
    private String title;//标题
    private Integer size;//大小
    private String fileBizName;//名称(文件名),包含扩展名
    private String  ext;
    private String md5;//数字摘要值
    private Date createTime;//创建时间

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getFileBizName() {
        return fileBizName;
    }

    public void setFileBizName(String fileBizName) {
        this.fileBizName = fileBizName;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}