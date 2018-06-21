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
public class NewPurposeDAO {

    public static String CreateNewPurpose(NewPurposeForm newPurposeForm, String cannum) throws Exception {
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
                    + " `ap`.`APPROVED`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                    + " WHERE"
                    + " `SHORT_NAME`='" + GeneralDAO.CleanMySQLString(newPurposeForm.getShortName()) + "'";
            //System.out.println("Does purpose exist query:\n" + doesPurposeExistQuery + "\n");
            final ResultSet doesPurposeExistRs = doesPurposeExistStat.executeQuery(doesPurposeExistQuery);
            if(doesPurposeExistRs.next()){
                response = "A purpose with short name '" + GeneralDAO.CleanMySQLString(newPurposeForm.getShortName()) + "' already exists with approval status \"" + doesPurposeExistRs.getString("APPROVED") + "\".";
            } else {
                final String insertNewPurposeCmd = "INSERT INTO"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                        + " SET"
                        + " `ACTIVE`='1'"
                        + ", `SHORT_NAME`='" + GeneralDAO.CleanMySQLString(newPurposeForm.getShortName()) + "'"
                        + ", `DESCRIPTION`='" + GeneralDAO.CleanMySQLString(newPurposeForm.getDescription()) + "'"
                        + ", `CREATION_DATE`=NOW()"
                        + ", `PERIOD`='" + GeneralDAO.CleanMySQLString(newPurposeForm.getPeriod()) + "'"
                        + ", `CREATED_BY_CANNUM`='" + cannum + "'"
                        + ", `APPROVED`='Approved'";
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
    
    public static String ManagePurpose(NewPurposeForm newPurposeForm) throws Exception {
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
                    + " `SHORT_NAME`='" + GeneralDAO.CleanMySQLString(newPurposeForm.getShortName()) + "'"
                    + " AND `ID`!='" + GeneralDAO.CleanMySQLString(newPurposeForm.getPurposeId()) + "'";
            //System.out.println("Does purpose exist query:\n" + doesPurposeExistQuery + "\n");
            final ResultSet doesPurposeExistRs = doesPurposeExistStat.executeQuery(doesPurposeExistQuery);
            if(doesPurposeExistRs.next()){
                response = "Another purpose with short name '" + GeneralDAO.CleanMySQLString(newPurposeForm.getShortName()) + "' already exists with approval status \"" + doesPurposeExistRs.getString("APPROVED") + "\".";
            } else {
                final String updatePurposeCmd = "UPDATE"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                        + " SET"
                        + " `ACTIVE`='1'"
                        + ", `SHORT_NAME`='" + GeneralDAO.CleanMySQLString(newPurposeForm.getShortName()) + "'"
                        + ", `DESCRIPTION`='" + GeneralDAO.CleanMySQLString(newPurposeForm.getDescription()) + "'"
                        + ", `PERIOD`='" + GeneralDAO.CleanMySQLString(newPurposeForm.getPeriod()) + "'"
                        + " WHERE"
                        + " `ID`='" + GeneralDAO.CleanMySQLString(newPurposeForm.getPurposeId()) + "'";
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
