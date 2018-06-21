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
public class UpdatePurposeDAO {
    public static String ManagePurpose(UpdatePurposeForm updatePurposeForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        String response = "";
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement doesPurposeExistStat = conn.createStatement();
            final Statement updatePurposeStat = conn.createStatement();
            
            commitStat.execute("START TRANSACTION");
            
            final String doesPurposeExistQuery = "SELECT"
                    + " `APPROVED`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                    + " WHERE"
                    + " `SHORT_NAME`='" + GeneralDAO.CleanMySQLString(updatePurposeForm.getShortName()) + "'"
                    + " AND `ID`!='" + GeneralDAO.CleanMySQLString(updatePurposeForm.getPurposeId()) + "'";
            //System.out.println("Does purpose exist query:\n" + doesPurposeExistQuery + "\n");
            final ResultSet doesPurposeExistRs = doesPurposeExistStat.executeQuery(doesPurposeExistQuery);
            if(doesPurposeExistRs.next()){
                response = "Another purpose with short name '" + GeneralDAO.CleanMySQLString(updatePurposeForm.getShortName()) + "' already exists with approval status '" + doesPurposeExistRs.getString("APPROVED") + "'.";
            } else {
                final String updatePurposeCmd = "UPDATE"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                        + " SET"
                        + " `ACTIVE`='1'"
                        + ", `SHORT_NAME`='" + GeneralDAO.CleanMySQLString(updatePurposeForm.getShortName()) + "'"
                        + ", `DESCRIPTION`='" + GeneralDAO.CleanMySQLString(updatePurposeForm.getDescription()) + "'"
                        + ", `PERIOD`='" + GeneralDAO.CleanMySQLString(updatePurposeForm.getPeriod()) + "'"
                        + " WHERE"
                        + " `ID`='" + GeneralDAO.CleanMySQLString(updatePurposeForm.getPurposeId()) + "'";
                //System.out.println("Update Purpose:\n" + updatePurposeCmd + "\n");
                updatePurposeStat.executeUpdate(updatePurposeCmd);
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
