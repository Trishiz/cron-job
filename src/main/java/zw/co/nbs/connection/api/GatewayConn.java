package zw.co.nbs.connection.api;

import java.sql.ResultSet;

public interface GatewayConn {

    ResultSet executeQuery(String sql);
    void openConn() throws Exception;
}
