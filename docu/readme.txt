mvn clean -Djetty.port=9998 jetty:run

nohup mvn clean -Djetty.port=9998 jetty:run > /www/outinterface/logs/out.log &
tail -f /www/outinterface/logs/out.log