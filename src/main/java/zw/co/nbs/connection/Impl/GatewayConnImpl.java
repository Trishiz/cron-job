package zw.co.nbs.connection.Impl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import zw.co.nbs.connection.api.GatewayConn;

import java.sql.*;

@Slf4j
@NoArgsConstructor
public class GatewayConnImpl implements GatewayConn {
    public static Connection conn;

    @Value("${spring.datasource.driver-class-name}")
    private String jdbcClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;


    public void openConn() throws Exception {

        if (conn == null || conn.isClosed()) {
            try {
                log.debug("Connecting to : {},{},{}",url,username,password);
                Class.forName(jdbcClassName);
                conn= DriverManager.getConnection(url, username, password);
            } catch (SQLException | ClassNotFoundException e) {
                log.error("Error Opening Connection to local", e);
                log.error("Failed to establish a connection to local:::  {}", e.getMessage());
                throw new SQLException("Failed to connect to local database: " + url);
            }
        }
    }

    public ResultSet executeQuery(String sqlString) {
        ResultSet rs = null;
        try {
            log.debug(sqlString);
            openConn();
            Statement sttmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            sttmt.executeQuery(sqlString);
            rs = sttmt.getResultSet();
        } catch (Exception ex) {
            log.error("SQL Query Error", ex);
        }
        return rs;
    }

}
