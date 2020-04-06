package cn.com.hwxt.dao.i;

import cn.com.hwxt.dao.BaseDao;
import cn.com.hwxt.pojo.SGroup;
import cn.com.hwxt.pojo.SGroupExample;
import cn.com.hwxt.pojo.SQzh;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SGroupMapper extends BaseDao {
    int countByExample(SGroupExample example);

    int deleteByExample(SGroupExample example);

    int deleteByPrimaryKey(Integer did);

    int insert(SGroup record);

    int insertSelective(SGroup record);

    List<SGroup> selectByExample(SGroupExample example);

    SGroup selectByPrimaryKey(Integer did);

    int updateByExampleSelective(@Param("record") SGroup record, @Param("example") SGroupExample example);

    int updateByExample(@Param("record") SGroup record, @Param("example") SGroupExample example);

    int updateByPrimaryKeySelective(SGroup record);

    int updateByPrimaryKey(SGroup record);

    int updateByKey(SGroup record);


    /**
     * _综合管理部_薪酬考核单元
     * @param gfzj
     * @param qzh
     * @return
     */
    @Select("SELECT * FROM S_GROUP WHERE GFZJ = '${gfzj}' AND  QZH = '${qzh}' ")
    SGroup getGroupByGfzj4Seeyou(@Param("gfzj") String gfzj , @Param("qzh") String qzh);

    /**
     * <p>Title: 根据xml里面的orgCode来查询s_group中的depCode</p>
     * <p>Description: </p>
     *
     * @param depCode
     * @return
     * @date 2014年2月21日
     */
    @Select("SELECT * FROM S_GROUP WHERE DEPCODE = '${depCode}'")
    SGroup getGroupByDepCode(@Param("depCode") String depCode);

    @Select("SELECT * FROM S_GROUP WHERE bz = '${bz}'")
    SGroup getGroupByBz(@Param("bz") String bz);

    @Select("SELECT * FROM S_GROUP WHERE GFZJ = '${gfzj}'")
    SGroup getGroupByGfzj(@Param("gfzj") String gfzj);
}