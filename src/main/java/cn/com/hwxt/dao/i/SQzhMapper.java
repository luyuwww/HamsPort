package cn.com.hwxt.dao.i;

import cn.com.hwxt.dao.BaseDao;
import cn.com.hwxt.pojo.SQzh;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SQzhMapper extends BaseDao {
    int deleteByPrimaryKey(Integer did);

    int insert(SQzh record);

    int insertSelective(SQzh record);

    SQzh selectByPrimaryKey(Integer did);

    int updateByPrimaryKeySelective(SQzh record);

    int updateByPrimaryKey(SQzh record);

    @Select("SELECT * FROM S_QZH WHERE BZ = '${ORGID}'")
    SQzh getSQzhByPOrgID(@Param("ORGID") String orgID);

    /**
     * s_qzh.bz记录oa的orgid
     * @return
     */
    @Select("SELECT * FROM S_QZH WHERE BZ IS NOT NULL")
    List<SQzh> getQzhList();

    @Select("SELECT * FROM S_QZH WHERE ISDEF=0")
    List<SQzh> listAllSqzh();
}