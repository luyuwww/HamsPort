HamsPort
========
此分支用于Lhk的电子文件归档接口
---
clean -Djetty.port=8887 jetty:run
clean compile war:war

mvn clean -Djetty.port=9998 jetty:run
nohup mvn clean -Djetty.port=9998 jetty:run > /www/outinterface/logs/out.log &

tail -f /www/outinterface/logs/out.log
http://115.28.168.237:9998/HamsPort/viewLog?logFilePath=D_FILE64.XML&fileType=xml&rand=0.5779046603333
http://115.28.168.237:9998/HamsPort/cxf/ArcDataWsSingle?wsdl


| 序号|分支 | 说明 |
|---|---|---|
|  1|* master   |  主版本  |
| 2 |ss      | ss版本已经和立思辰做了接口，这个里面会加上 轮转器反向更新立思辰归档状态   |
| 3  |weaver    |  泛微接口，完成单点组织机构同步、oa待办接口  |
| 3 |ErTanLiangheKou    |  两河口电子文件接收 |
