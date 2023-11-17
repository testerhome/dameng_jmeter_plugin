import com.alibaba.druid.pool.DruidDataSourceFactory;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

@Slf4j
public class DruidUtils {
    static DataSource dataSource; //连接池对象

    static {
        try {
            //获取资源
            Properties ps = new Properties();
            //打印当前路径是这个  /E:/apache-jmeter-5.4.1/bin/ApacheJMeter.jar!/org/apache/jmeter/
            File file = new File("");
            String execPath= file.getCanonicalPath();
            log.info(execPath);
//            String realPath = execPath.substring(execPath.indexOf(":")+2, execPath.indexOf("!")-16);
//            log.info(realPath);
            //加载资源
            // 使用InPutStream流读取properties文件 这种可以读取任意路径的
            BufferedReader bufferedReader = new BufferedReader(new FileReader(execPath+"\\config.properties"));
//            InputStream fis = DruidUtils.class.getResourceAsStream("config.properties");
            ps.load(bufferedReader);

            //只能读取resources文件夹下的
//            ps.load(DruidUtils.class.getResourceAsStream("/config.properties"));

            //创建数据库连接池 利用配置文件
            dataSource = DruidDataSourceFactory.createDataSource(ps);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    //获取连接方法
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

}
