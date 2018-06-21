/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.request;

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
public class ManAffReqDAO {

    public static ArrayList<Object> GetAffReqs(boolean superUser, String cannum) throws Exception {
        ArrayList<Object> result = new ArrayList<Object>();
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getAffReqsStat = conn.createStatement();
            //final Statement getPurposesStat = conn.createStatement();
            String getAffReqsQuery = "";
            getAffReqsQuery = "SELECT DISTINCT"
                    + " `a`.`FIRSTNAME`"
                    + ", `a`.`MIDDLENAME`"
                    + ", `a`.`LASTNAME`"
                    + ", `a`.`DEPARTMENT`"
                    + ", `a`.`ORGANIZATION`"
                    + ", `a`.`EMAIL`"
                    + ", `a`.`OVPRID`"
                    + ", `a`.`PSEUDOCAN`"
                    + ", `a`.`PHONE`"
                    + ", `a`.`CREATEDBY` AS `A_CREATED_BY`"
                    + ", `a`.`CREATED` AS `A_CREATION_DATE`"
                    + ", `a`.`ID` AS `AFFILIATE_ID`"
                    //+ ", `ae`.`INSTITUTION_COMPANY`"
                    + ", `ae`.`ADDR_1`"
                    + ", `ae`.`ADDR_2`"
                    + ", `ae`.`CITY`"
                    + ", `ae`.`STATE`"
                    + ", `ae`.`COUNTRY`"
                    + ", `ae`.`ZIP`"
                    + ", `ae`.`BUSINESS_PHONE_NUM`"
                    //+ ", `ae`.`ALT_PHONE_NUM`"
                    //+ ", CASE WHEN `ae`.`EXPIRATION_DATE` IS NULL THEN ''"
                    //+ "  ELSE `ae`.`EXPIRATION_DATE` END AS `A_EXPIRATION_DATE`"
                    + ", `ae`.`COMMENTS`"
                    + ", `ae`.`FSCODES`"
                    + ", `ap`.`SHORT_NAME`"
                    + ", `ap`.`DESCRIPTION`"
                    + ", `ap`.`PERIOD`"
                    + ", CASE WHEN `ap`.`CREATION_DATE` IS NULL THEN ''"
                    + "  ELSE `ap`.`CREATION_DATE` END AS `AP_CREATION_DATE`"
                    //+ ", CASE WHEN `ap`.`EXPIRATION_DATE` IS NULL THEN ''"
                    //+ "  ELSE `ap`.`EXPIRATION_DATE` END AS `AP_EXPIRATION_DATE`"
                    + ", `cp`.`FIRSTNAME` AS `CP_FIRSTNAME`"
                    + ", `cp`.`LASTNAME` AS `CP_LASTNAME`"
                    + ", `cp`.`MYID` AS `CP_MYID`"
                    + ", `cp`.`CANNUM` AS `CP_CANNUM`"
                    + ", `cp`.`EMAIL` AS `CP_EMAIL`"
                    + ", `apl`.`id` AS `APL_ID`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                    + " ON `apl`.`ACTIVE`='1'"
                    + " AND `apl`.`APPROVED`='Submitted'"
                    + " AND `apl`.`AFFILIATE_ID`=`a`.`ID`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " ON `ap`.`ID`=`apl`.`AFFILIATE_PURPOSE_ID`"
                    + " AND `ap`.`ACTIVE`='1'"
                    + " AND `ap`.`APPROVED`='Approved'"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`PERSON` `cp`"
                    + " ON `ap`.`CREATED_BY_CANNUM`=`cp`.`CANNUM`"
                    + " AND `cp`.`ACTIVE`='1'"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_EXPANSION` `ae`"
                    + " ON `ae`.`AFFILIATE_ID`=`a`.`ID`";
            if (!superUser) {
                getAffReqsQuery += " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                        + " ON `aap`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                        + " AND `aap`.`ACTIVE`='1'"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                        + " ON `aa`.`ID`=`aap`.`AFFILIATE_ADMIN_ID`"
                        + " AND `aa`.`ACTIVE`='1'"
                        + " AND `aa`.`CANNUM`='" + cannum + "'";
            }
            getAffReqsQuery += " ORDER BY"
                    + " `a`.`OVPRID`, `A_CREATION_DATE` desc";
            //System.out.println("Get Aff Requests Query:\n" + getAffReqsQuery + "\n");

            final ResultSet getAffReqsRs = getAffReqsStat.executeQuery(getAffReqsQuery);
            while (getAffReqsRs.next()) {
                HashMap<String, Object> data = new HashMap<String, Object>();
                data.put("FIRSTNAME", getAffReqsRs.getString("FIRSTNAME"));
                data.put("MIDDLENAME", getAffReqsRs.getString("MIDDLENAME"));
                data.put("LASTNAME", getAffReqsRs.getString("LASTNAME"));
                data.put("DEPARTMENT", getAffReqsRs.getString("DEPARTMENT"));
                data.put("ORGANIZATION", getAffReqsRs.getString("ORGANIZATION"));
                data.put("EMAIL", getAffReqsRs.getString("EMAIL"));
                data.put("OVPRID", getAffReqsRs.getString("OVPRID"));
                data.put("PSEUDOCAN", getAffReqsRs.getString("PSEUDOCAN"));
                data.put("PHONE", getAffReqsRs.getString("PHONE"));
                data.put("A_CREATED_BY", getAffReqsRs.getString("A_CREATED_BY"));
                data.put("A_CREATION_DATE", getAffReqsRs.getString("A_CREATION_DATE").replaceAll(":\\d{2}(\\.\\d){0,1}$", ""));
                //data.put("INSTITUTION_COMPANY", getAffReqsRs.getString("INSTITUTION_COMPANY"));
                data.put("ADDR_1", getAffReqsRs.getString("ADDR_1"));
                data.put("ADDR_2", getAffReqsRs.getString("ADDR_2"));
                data.put("CITY", getAffReqsRs.getString("CITY"));
                data.put("STATE", getAffReqsRs.getString("STATE"));
                data.put("COUNTRY", getAffReqsRs.getString("COUNTRY"));
                data.put("ZIP", getAffReqsRs.getString("ZIP"));
                data.put("BUSINESS_PHONE_NUM", getAffReqsRs.getString("BUSINESS_PHONE_NUM"));
                //data.put("ALT_PHONE_NUM", getAffReqsRs.getString("ALT_PHONE_NUM"));
                //data.put("A_EXPIRATION_DATE", getAffReqsRs.getString("A_EXPIRATION_DATE"));
                data.put("COMMENTS", getAffReqsRs.getString("COMMENTS"));
                data.put("FSCODES", getAffReqsRs.getString("FSCODES"));
                data.put("SHORT_NAME", getAffReqsRs.getString("SHORT_NAME"));
                data.put("DESCRIPTION", getAffReqsRs.getString("DESCRIPTION"));
                data.put("PERIOD", getAffReqsRs.getString("PERIOD"));
                data.put("AP_CREATION_DATE", getAffReqsRs.getString("AP_CREATION_DATE").replaceAll(":\\d{2}(\\.\\d){0,1}$", ""));
                //data.put("AP_EXPIRATION_DATE", getAffReqsRs.getString("AP_EXPIRATION_DATE").replaceAll(":\\d{2}(\\.\\d){0,1}$", ""));
                data.put("CP_FIRSTNAME", getAffReqsRs.getString("CP_FIRSTNAME"));
                data.put("CP_LASTNAME", getAffReqsRs.getString("CP_LASTNAME"));
                data.put("CP_MYID", getAffReqsRs.getString("CP_MYID"));
                data.put("CP_CANNUM", getAffReqsRs.getString("CP_CANNUM"));
                data.put("CP_EMAIL", getAffReqsRs.getString("CP_EMAIL"));
                data.put("APL_ID", getAffReqsRs.getString("APL_ID"));
                result.add(data);
            }
            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void AcceptRequest(ManAffReqForm manAffReqForm, String userCannum) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement acceptReqStat = conn.createStatement();
            final Statement updateAffStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            String acceptReqCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                    + " SET"
                    + " `APPROVED`='Approved'";
            if (manAffReqForm.getPurpose_period().equalsIgnoreCase("-1")) {
                acceptReqCmd += ", `EXPIRES`='0'";
            } else {
                acceptReqCmd += ", `EXPIRATION_DATE`=DATE_ADD(NOW(), INTERVAL " + GeneralDAO.CleanMySQLString(manAffReqForm.getPurpose_period()) + " MONTH)";
            }
            acceptReqCmd += ", `MANAGER_CANNUM`='" + userCannum + "'"
                    + ", `MANAGE_DATE`=NOW()"
                    + " WHERE"
                    + " `ID`='" + GeneralDAO.CleanMySQLString(manAffReqForm.getReqId()) + "'";
            //System.out.println("Accept Request Cmd:\n" + acceptReqCmd + "\n");
            acceptReqStat.executeUpdate(acceptReqCmd);

            final String updateAffCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE`"
                    + " SET"
                    + " `STATUS`='Non-Provisional'"
                    + " WHERE"
                    + " `PSEUDOCAN`='" + GeneralDAO.CleanMySQLString(manAffReqForm.getPseudoCannum()) + "'";
            updateAffStat.executeUpdate(updateAffCmd);

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

    public static void RejectRequest(ManAffReqForm manAffReqForm, String userCannum) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement rejectReqStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String rejectReqCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                    + " SET"
                    + " `APPROVED`='Rejected'"
                    + ", `MANAGER_CANNUM`='" + userCannum + "'"
                    + ", `MANAGE_DATE`=NOW()"
                    + " WHERE"
                    + " `ID`='" + GeneralDAO.CleanMySQLString(manAffReqForm.getReqId()) + "'";
            rejectReqStat.executeUpdate(rejectReqCmd);
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
