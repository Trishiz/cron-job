package zw.co.nbs.connection.api;

import java.sql.Connection;
import java.sql.ResultSet;

public interface GatewayConn {

    ResultSet executeQuery(String sql);
    Connection openConn() throws Exception;
}
