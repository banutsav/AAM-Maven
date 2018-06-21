/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.purpose;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Static;
import edu.uga.ovpr.aam.information.GeneralDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author submyers
 */
public class ReqNewPurposeDAO {
    
    public static String AddNewPurposeReq(ReqNewPurposeForm reqNewPurposeForm, String cannum) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        String response = "";
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement doesPurposeExistStat = conn.createStatement();
            final Statement insertNewPurposeStat = conn.createStatement();
            
            commitStat.execute("START TRANSACTION");
            
            final String doesPurposeExistQuery = "SELECT"
                    + " `APPROVED`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                    + " WHERE"
                    + " `SHORT_NAME`='" + GeneralDAO.CleanMySQLString(reqNewPurposeForm.getShortName()) + "'";
            //System.out.println("Does purpose exist query:\n" + doesPurposeExistQuery + "\n");
            final ResultSet doesPurposeExistRs = doesPurposeExistStat.executeQuery(doesPurposeExistQuery);
            if(doesPurposeExistRs.next()){
                response = "A purpose with short name '" + GeneralDAO.CleanMySQLString(reqNewPurposeForm.getShortName()) + "' already exists with approval status '" + doesPurposeExistRs.getString("APPROVED") + "'.";
            } else {
                final String insertNewPurposeCmd = "INSERT INTO"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                        + " SET"
                        + " `ACTIVE`='1'"
                        + ", `SHORT_NAME`='" + GeneralDAO.CleanMySQLString(reqNewPurposeForm.getShortName()) + "'"
                        + ", `DESCRIPTION`='" + GeneralDAO.CleanMySQLString(reqNewPurposeForm.getDescription()) + "'"
                        + ", `CREATION_DATE`=NOW()"
                        + ", `PERIOD`='" + GeneralDAO.CleanMySQLString(reqNewPurposeForm.getPeriod()) + "'"
                        + ", `CREATED_BY_CANNUM`='" + cannum + "'";
                //System.out.println("Insert New Purpose:\n" + insertNewPurposeCmd + "\n");
                insertNewPurposeStat.executeUpdate(insertNewPurposeCmd);
            }
            
            commitStat.execute("COMMIT");
            return response;
        } catch (Exception ex) {
            commitStat.execute("ROLLBACK");
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
