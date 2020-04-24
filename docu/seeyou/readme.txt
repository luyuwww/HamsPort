1. 组织机构同步不会删除,只会增加和修改,配置文件config.properties里面需要配置seeyou开始的3个参数
2. S_QZH表的备注字段放oa的单位名称
3. s_group和s_user表加字段  都是varchar2(256)
     *    S_GROUP 增加 DEPCODE和 GFZJ
     *    S_USER  增加 ESBID,ESBCODE
4. 轮转器和index.jsp都可以触发同步
5. 如果部门需要排序,请修改lams中S_GROIUP.xml中46后的order by语句 为:ORDER BY GID
6. 单点提供给致远实施的地址:http://localhost:8887/HamsPort/sso?ticket=asdfasdfasdfasdf
7. 组织机构同步每晚23点同步一次,也可以在index页面中手动触发