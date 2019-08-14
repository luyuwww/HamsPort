HamsPort
========
通用数据接口。
---
clean -Djetty.port=8887 jetty:run
clean compile war:war

mvn clean -Djetty.port=9998 jetty:run
nohup mvn clean -Djetty.port=9998 jetty:run > /www/outinterface/logs/out.log &

tail -f /www/outinterface/logs/out.log
http://115.28.168.237:9998/HamsPort/viewLog?logFilePath=D_FILE64.XML&fileType=xml&rand=0.5779046603333
http://115.28.168.237:9998/HamsPort/cxf/ArcDataWsSingle?wsdl
