set MAINCLASS=com.efida.sport.open.ExampleApi
set classpath=../lib/avalon-framework-4.1.3.jar;../lib/commons-codec-1.10.jar;../lib/commons-io-2.3.jar;../lib/commons-logging-1.1.jar;../lib/fastjson-1.1.41.jar;../lib/httpclient-4.4.1.jar;../lib/httpcore-4.4.1.jar;../lib/log4j-1.2.12.jar;../lib/logkit-1.0.1.jar;../lib/servlet-api-2.3.jar;../hzsport-open-sdk-java-20180727.jar
java -cp "%classpath%" %MAINCLASS% %*
pause
