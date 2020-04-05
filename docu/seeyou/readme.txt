1. 组织机构同步不会删除,只会增加和修改,配置文件config.properties里面需要配置seeyou开始的3个参数
2. s_group和s_user表加字段  都是varchar2(32)
     *    S_GROUP 增加 DEPCODE和 GFZJ
     *    S_USER  增加 ESBID,ESBCODE
3. 轮转器和index.jsp都可以触发同步