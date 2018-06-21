/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.superuser;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Static;
import edu.uga.ovpr.aam.information.GeneralDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author submyers
 */
public class ControlPurposesDAO {

    public static ArrayList<Object> SearchPaula(ControlPurposesForm controlPurposesForm) throws Exception {
        Connection conn = null;
        ArrayList<Object> results = new ArrayList<Object>();
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement searchPaulaStat = conn.createStatement();
            Statement searchPaulaCountStat = conn.createStatement();
            Statement purposeAffCountStat = conn.createStatement();
            

            String whereClause = "";
            if (!controlPurposesForm.getShortName().isEmpty()) {
                if (controlPurposesForm.getShortNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `ap`.`SHORT_NAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlPurposesForm.getShortName()) + "%'";
                } else if (controlPurposesForm.getShortNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `ap`.`SHORT_NAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlPurposesForm.getShortName()) + "%'";
                } else if (controlPurposesForm.getShortNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `ap`.`SHORT_NAME`='" + GeneralDAO.CleanMySQLString(controlPurposesForm.getShortName()) + "'";
                }
            }
            if (!controlPurposesForm.getDescription().isEmpty()) {
                if (controlPurposesForm.getDescriptionOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `ap`.`DESCRIPTION` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlPurposesForm.getDescription()) + "%'";
                } else if (controlPurposesForm.getDescriptionOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `ap`.`DESCRIPTION` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlPurposesForm.getDescription()) + "%'";
                } else if (controlPurposesForm.getDescriptionOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `ap`.`DESCRIPTION`='" + GeneralDAO.CleanMySQLString(controlPurposesForm.getDescription()) + "'";
                }
            }
            if (controlPurposesForm.getIncInactive().equals("No")) {
                whereClause += " AND `ap`.`ACTIVE`='1'";
            }

            if (!whereClause.isEmpty()) {
                whereClause = whereClause.replaceFirst(" AND", " WHERE");
            }

            final String searchPaulaCountQuery = "SELECT"
                    + " COUNT(`ap`.`ID`) AS `COUNT`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + whereClause + " AND `ap`.`ID` != '" + Constants.associate_code + "'";
            //System.out.println("Search Purposes Count:\n" + searchPaulaCountQuery + "\n");
            ResultSet searchPaulaCountRs = searchPaulaCountStat.executeQuery(searchPaulaCountQuery);
            Integer searchPaulaCount = 0;
            if (searchPaulaCountRs.next()) {
                searchPaulaCount = searchPaulaCountRs.getInt("COUNT");
            }
            controlPurposesForm.setCount(searchPaulaCount);

            String orderBy = " ORDER BY `ACTIVE` desc, FIND_IN_SET(`APPROVED`, 'Submitted,Approved,Rejected'), `SHORT_NAME`";

            Integer start = controlPurposesForm.getPageNum() * 100;
            String limitClause = " LIMIT " + start + ", 100";

            final String searchPaulaQuery = "SELECT"
                    + " `ap`.`ID` AS `ID`"
                    + ", `ap`.`ACTIVE` AS `ACTIVE`"
                    + ", `ap`.`SHORT_NAME` AS `SHORT_NAME`"
                    + ", `ap`.`DESCRIPTION` AS `DESCRIPTION`"
                    + ", `ap`.`APPROVED` AS `APPROVED`"
                    + ", `ap`.`CREATION_DATE` AS `CREATION_DATE`"
                    + ", `ap`.`PERIOD` AS `PERIOD`"
                    + ", `p`.`FIRSTNAME` AS `FIRSTNAME`"
                    + ", `p`.`LASTNAME` AS `LASTNAME`"
                    + ", `p`.`CANNUM` AS `CANNUM`"
                    + ", `p`.`MYID` AS `MYID`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " ON `p`.`CANNUM`=`ap`.`CREATED_BY_CANNUM`"
                    + whereClause + " AND `ap`.`ID` != '" + Constants.associate_code + "'"
                    + orderBy
                    + limitClause;

            //System.out.println("Purposes search:\n" + searchPaulaQuery + "\n");
            final ResultSet searchPaulaRs = searchPaulaStat.executeQuery(searchPaulaQuery);
            while (searchPaulaRs.next()) {                
                HashMap<String, String> entry = new HashMap<String, String>();
                entry.put("ID", searchPaulaRs.getString("ID"));
                entry.put("ACTIVE", searchPaulaRs.getString("ACTIVE"));
                entry.put("SHORT_NAME", searchPaulaRs.getString("SHORT_NAME"));
                entry.put("DESCRIPTION", searchPaulaRs.getString("DESCRIPTION"));
                entry.put("APPROVED", searchPaulaRs.getString("APPROVED"));
                entry.put("CREATION_DATE", searchPaulaRs.getString("CREATION_DATE"));
                entry.put("PERIOD", searchPaulaRs.getString("PERIOD"));
                entry.put("FIRSTNAME", searchPaulaRs.getString("FIRSTNAME"));
                entry.put("LASTNAME", searchPaulaRs.getString("LASTNAME"));
                entry.put("CANNUM", searchPaulaRs.getString("CANNUM"));
                entry.put("MYID", searchPaulaRs.getString("MYID"));
                String affCount = "0";
                final String purposeAffCountQuery = "SELECT"
                        + " COUNT(`a`.`ID`) AS `COUNT`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                        + " ON `apl`.`AFFILIATE_ID`=`a`.`ID`"
                        + " AND `a`.`ACTIVE`='1'"
                        + " AND `apl`.`ACTIVE`='1'"
                        + " AND ("
                        + " `apl`.`EXPIRES`='0'"
                        + " OR ("
                        + " `apl`.`EXPIRES`='1'"
                        + " AND `apl`.`EXPIRATION_DATE`>NOW()"
                        + " ) )"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                        + " ON `apl`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                        + " AND `ap`.`ID`='" + searchPaulaRs.getString("ID") + "'";
                //System.out.println("Purpose Aff Count Query:\n" + purposeAffCountQuery + "\n");
                final ResultSet purposeAffCountRs = purposeAffCountStat.executeQuery(purposeAffCountQuery);
                if(purposeAffCountRs.next()){
                    affCount = purposeAffCountRs.getString("COUNT");
                }
                entry.put("AFF_COUNT",affCount);
                results.add(entry);
            }
            return results;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
    }

    public static String AddPurpose(ControlPurposesForm controlPurposesForm, String cannum) throws Exception {
        
        Connection conn = null;
        Statement commitStat = null;
        
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            
            System.out.println("DEBUG: created statement");
            
            final Statement doesPurpExistStat = conn.createStatement();
            final Statement insertPurposeStat = conn.createStatement();

            final String doesPurpExistQuery = "SELECT"
                    + " '1'"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                    + " WHERE"
                    + " `SHORT_NAME`='" + GeneralDAO.CleanMySQLString(controlPurposesForm.getNewShortName()) + "'";
            final ResultSet doesPurpExistRs = doesPurpExistStat.executeQuery(doesPurpExistQuery);
            if(doesPurpExistRs.next()){
                return("A purpose already exists with Short Name '" + controlPurposesForm.getNewShortName() + "'");
            }
            commitStat.execute("START TRANSACTION");

            final String insertPurposeCmd = "INSERT INTO"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                    + " SET"
                    + " `SHORT_NAME`='" + GeneralDAO.CleanMySQLString(controlPurposesForm.getNewShortName()) + "'"
                    + ", `DESCRIPTION`='" + GeneralDAO.CleanMySQLString(controlPurposesForm.getNewDescription()) + "'"
                    + ", `APPROVED`='" + GeneralDAO.CleanMySQLString(controlPurposesForm.getNewApproved()) + "'"
                    + ", `PERIOD`='" + GeneralDAO.CleanMySQLString(controlPurposesForm.getNewPeriod()) + "'"
                    + ", `CREATED_BY_CANNUM`='" + cannum + "'";
            //System.out.println("Update Purpose Cmd:\n" + updatePurposeCmd + "\n");
            insertPurposeStat.executeUpdate(insertPurposeCmd);

            commitStat.execute("COMMIT");
            return "";
        } catch (Exception ex) {
            commitStat.execute("ROLLBACK");
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public static void UpdatePurpose(ControlPurposesForm controlPurposesForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement updatePurposeStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String updatePurposeCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                    + " SET"
                    + " `SHORT_NAME`='" + GeneralDAO.CleanMySQLString(controlPurposesForm.getNewShortName()) + "'"
                    + ", `DESCRIPTION`='" + GeneralDAO.CleanMySQLString(controlPurposesForm.getNewDescription()) + "'"
                    + ", `APPROVED`='" + GeneralDAO.CleanMySQLString(controlPurposesForm.getNewApproved()) + "'"
                    + ", `PERIOD`='" + GeneralDAO.CleanMySQLString(controlPurposesForm.getNewPeriod()) + "'"
                    + " WHERE"
                    + " `ID`='" + GeneralDAO.CleanMySQLString(controlPurposesForm.getPurposeId()) + "'";
            //System.out.println("Update Purpose Cmd:\n" + updatePurposeCmd + "\n");
            updatePurposeStat.executeUpdate(updatePurposeCmd);
            commitStat.execute("COMMIT");
        } catch (Exception ex) {
            commitStat.execute("ROLLBACK");
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public static void DeactivatePurpose(ControlPurposesForm controlPurposesForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement deactivatePurposeStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String deactivatePurposeCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                    + " SET"
                    + " `ACTIVE`='0'"
                    + " WHERE"
                    + " `ID`='" + GeneralDAO.CleanMySQLString(controlPurposesForm.getPurposeId()) + "'";
            //System.out.println("Deactivate Purpose Cmd:\n" + deactivatePurposeCmd + "\n");
            deactivatePurposeStat.executeUpdate(deactivatePurposeCmd);
            commitStat.execute("COMMIT");
        } catch (Exception ex) {
            commitStat.execute("ROLLBACK");
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public static void ReactivatePurpose(ControlPurposesForm controlPurposesForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement reactivatePurposeStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String reactivatePurposeCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                    + " SET"
                    + " `ACTIVE`='1'"
                    + " WHERE"
                    + " `ID`='" + GeneralDAO.CleanMySQLString(controlPurposesForm.getPurposeId()) + "'";
            //System.out.println("Reactivate Purpose Cmd:\n" + reactivatePurposeCmd + "\n");
            reactivatePurposeStat.executeUpdate(reactivatePurposeCmd);
            commitStat.execute("COMMIT");
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
