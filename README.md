# dameng_jmeter_plugin

/**********************************

jmeter damengdb插件

/***************************************

**使用方法：** <br /> 
1.clone工程到本地。<br /> 
2.使用maven打成jar包。 <br /> 
3.copy jar包到 jmeter安装目录 \lib\ext 下。 <br /> 
4.copy dameng数据库驱动DmJdbcDriver18.jar 到 jmeter安装目录 \lib\ext 下。<br /> 
5.druid 的jar包也要先copy到 \lib\ext目录下 <br />
6.工程中的druid配置文件 config.properties要拷贝到Jmeter bin目录下<br />
6.运行jmeter, 在线程组下添加java request 选择对应工程中的类名即可（前提是先要保证damengdb可用）<br /> 

本工程插件在jmeter 5.4.1版本下测试通过

想了解实现详细过程，扫以下二维码了解：

![](https://wx2.sinaimg.cn/bmiddle/006oh24igy1hjusx1xd7dj30b40b4wec.jpg)
