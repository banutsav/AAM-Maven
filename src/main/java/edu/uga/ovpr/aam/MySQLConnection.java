/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam;
import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author submyers
 */
public class MySQLConnection {

    public static Connection getConnection(final String dataSource) throws Exception {
        try {
            Context ctx = new InitialContext();
            ctx = (Context) ctx.lookup("java:comp/env");
            final DataSource ds = (DataSource) ctx.lookup(dataSource);
            return ds.getConnection();
        } catch (Exception ex) {
            throw ex;
        }
    }
}
