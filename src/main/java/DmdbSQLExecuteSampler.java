import lombok.extern.slf4j.Slf4j;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import java.awt.geom.Line2D;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.sql.*;

@Slf4j
public class DmdbSQLExecuteSampler implements JavaSamplerClient {

    private Connection conn = null;
    private String sql;


    @Override
    public void setupTest(JavaSamplerContext javaSamplerContext) {
        //拼接出url jdbc:dm://192.168.1.100:12345
        String url = "jdbc:dm://" + javaSamplerContext.getParameter("host") + ":" + javaSamplerContext.getParameter("port");

        String username = javaSamplerContext.getParameter("username");
        String password = javaSamplerContext.getParameter("password");
        String driver = javaSamplerContext.getParameter("driver");
        sql = javaSamplerContext.getParameter("sql");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {

        log.info("runTest query");
        SampleResult result = new SampleResult();
        result.setSampleLabel("DMdbSQLExecuteSampler");
        result.sampleStart();


        Statement stmt = null;
        ResultSet rs = null;
        try {
//            conn = DriverManager.getConnection("jdbc:dm://localhost:5236", "SYSDBA", "123456789");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
//            rs = stmt.executeQuery("select * from dmhr.employee");
            StringBuilder sb = new StringBuilder();
            ResultSetMetaData resultSetMetaData=rs.getMetaData();
            int count = resultSetMetaData.getColumnCount();
            //返回值添加列名
            for (int i = 1; i <count ; i++) {
                sb.append(resultSetMetaData.getColumnLabel(i)).append(",");
            }
            sb.append(System.getProperty("line.separator"));
            while (rs.next()) {

                //String employeeName = rs.getString("EMPLOYEE_NAME");
                //每行的数据填充到返回结果中
                for (int i = 1; i <= count; i++) {
                    sb.append(rs.getString(i)).append(",");
                }
                sb.append(System.getProperty("line.separator"));
                //                log.info("employee:" +sb.toString());

            }
            log.info(sb.toString());
            rs.close();
            result.setResponseData(sb.toString().getBytes(StandardCharsets.UTF_8));
            result.setSuccessful(true);
            result.sampleEnd();


        } catch (SQLException e) {
            result.setResponseData("return data exception".getBytes());
            result.setSuccessful(false);
            result.sampleEnd();
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void teardownTest(JavaSamplerContext javaSamplerContext) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("host", "localhost");
        params.addArgument("port", "5236");
        params.addArgument("username", "SYSDBA");
        params.addArgument("password", "123456789");
        params.addArgument("driver", "dm.jdbc.driver.DmDriver");
        params.addArgument("sql", "select * from dmhr.employee");
        return params;
    }
}
