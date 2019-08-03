package cn.com.hwxt.dao;

import ch.qos.logback.classic.Logger;
import cn.com.hwxt.pojo.EFile;
import org.apache.ibatis.jdbc.RuntimeSqlException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Component("jdbcDao")
public class JdbcDaoImpl implements JdbcDao {
    public void excute(String sql) throws RuntimeException {
        jdbcTemplate.execute(sql);
    }

    public void update(String sql) throws RuntimeException {
        jdbcTemplate.update(sql);
    }

    public void insert(String sql) throws RuntimeException {
        jdbcTemplate.update(sql);
    }

    public String getLjfByLibcode(Integer libcode) {
        String result = "";
        try {
            result = jdbcTemplate.queryForObject("select ljf from s_ljf where libcode=" + libcode, String.class);
        } catch (Exception e) {
            result = "-";
            log.error(e.getMessage());
        }
        return result;
    }

    public Map<String, Object> queryForMap(String sql) {
        try {
            return jdbcTemplate.queryForMap(sql);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public String query4String(String sql) {
        try {
            return jdbcTemplate.queryForObject(sql, String.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return "";
        }
    }

    public Map<String, String> quert2Colum4Map(String sql, String col1, String col2) {
        Map<String, String> resultMap = null;
        try {
            List<Map<String, Object>> resultListMap = jdbcTemplate.queryForList(sql);
            if (resultListMap != null && resultListMap.size() > 0) {
                resultMap = new HashMap<String, String>();
                for (Map<String, Object> map : resultListMap) {
                    if (map.get(col1) != null && map.get(col2) != null) {
                        resultMap.put(map.get(col1).toString(), map.get(col2).toString());
                    } else {
                        continue;
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return resultMap;
    }

    public List<Map<String, Object>> quertListMap(String sql) {
        try {
            return jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public List<String> quert4List(String sql) {
        try {
            return jdbcTemplate.queryForList(sql, String.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public Connection getConn() {
        try {
            return jdbcTemplate.getDataSource().getConnection();
        } catch (Exception e) {
            log.error("得到数据库连接错误", e);
            return null;
        }
    }

    public Integer insertEfile(String tableName, final EFile eFile) {
        return jdbcTemplate.update("insert into " + tableName +
                "(DID,PID,EFILENAME,TITLE,EXT,PZM,PATHNAME,STATUS,ATTR,ATTREX,CREATOR,CREATETIME,FILESIZE,MD5,CONVERTSTATUS) "
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                Integer i = 1;
                ps.setInt(i++, eFile.getDid());
                ps.setInt(i++, eFile.getPid());
                ps.setString(i++, eFile.getEfilename());
                ps.setString(i++, eFile.getTitle());
                ps.setString(i++, eFile.getExt());
                ps.setString(i++, eFile.getPzm());
                ps.setString(i++, eFile.getPathname());
                ps.setInt(i++, eFile.getStatus());
                ps.setInt(i++, eFile.getAttr());
                ps.setInt(i++, eFile.getAttrex());
                ps.setString(i++, eFile.getCreator());
                ps.setDate(i++, new java.sql.Date(eFile.getCreatetime().getTime()));
                ps.setInt(i++, eFile.getFilesize());
                ps.setString(i++, eFile.getMd5());
                ps.setInt(i++, 0);//电子文件转换的一个标识  0 是未转换
            }
        });
    }

    /**
     * 得到数据库的类型str
     */
    private String getDBTyeStr() throws RuntimeSqlException {
        String typeStr = null;
        TimeZone.setDefault(TimeZone.getTimeZone("ETC/GMT-8"));  //设置时区 中国/北京/香港
        Connection conn = null;
        DatabaseMetaData dbmd = null;
        try {
            conn = getConn();
            dbmd = conn.getMetaData();
            typeStr = dbmd.getDatabaseProductName();
        } catch (Exception e) {
            log.error("get database type is error!", e);
        } finally {
            try {
                dbmd = null;
                conn.close();
            } catch (SQLException exx) {
                log.error(exx.getMessage());
            }
        }
        return typeStr;
    }

    /**
     * 获取数据库参数 数据库类型名称,时间
     */
    private String getSysdate() {
        String sysdate = null;
        try {
            String databaseType = getDBTyeStr();
            if (databaseType != null && databaseType.equals("Microsoft SQL Server")) {
                sysdate = "GETDATE()";
            } else if (databaseType != null && databaseType.equals("Oracle")) {
                sysdate = "SYSDATE";
            } else if (databaseType != null && databaseType.equals("Db2")) {
                sysdate = "CURRENT TIMESTAMP";
            } else if (databaseType != null && databaseType.equals("MySQL")) {
                sysdate = "NOW()";
            } else if (databaseType != null && databaseType.equals("H2")) {
                sysdate = "current_timestamp";
            }
        } catch (Exception e) {
            log.error("get database time is error!");
        }
        return sysdate;
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private Logger log = (Logger) LoggerFactory.getLogger(this.getClass());
}
