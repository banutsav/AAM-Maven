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
public class NewAffReqDAO {

    public static String SubmitAffiliateRequest(NewAffReqForm newAffReqForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement checkCurrAffsStat = conn.createStatement();
            final Statement getMaxPseudoCanStat = conn.createStatement();
            final Statement insertAffReqStat = conn.createStatement();
            final Statement getLastAffIdStat = conn.createStatement();
            final Statement insertAffReqExpStat = conn.createStatement();
            final Statement findAffPurposeLinkStat = conn.createStatement();
            final Statement insertAffPurposeLinkStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String checkCurrAffsQuery = "SELECT 1"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE`"
                    + " WHERE"
                    + " `OVPRID`='" + GeneralDAO.CleanMySQLString(newAffReqForm.getEmail()) + "'";
            final ResultSet checkCurrAffsRs = checkCurrAffsStat.executeQuery(checkCurrAffsQuery);
            if (checkCurrAffsRs.next()) {
                return "An Affiliate account with the email address '" + GeneralDAO.CleanMySQLString(newAffReqForm.getEmail()) + "' already exists.";
            }

            final String getMaxPseudoCanQuery = "SELECT"
                    + " MAX(`PSEUDOCAN`) AS `maxPseudoCan`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE`";
            final ResultSet getMaxPseudoCanRs = getMaxPseudoCanStat.executeQuery(getMaxPseudoCanQuery);
            int maxPseudoCan = 1;
            if (getMaxPseudoCanRs.next()) {
                maxPseudoCan = getMaxPseudoCanRs.getInt("maxPseudoCan") + 1;
            }
            
            String phone = "";
            if(!newAffReqForm.getpAreaCode().isEmpty() && !newAffReqForm.getpPhone1().isEmpty() && !newAffReqForm.getpPhone2().isEmpty()){
                phone = newAffReqForm.getpAreaCode() + "-" + newAffReqForm.getpPhone1() + "-" + newAffReqForm.getpPhone2();
            }
            
            final String insertAffReqCmd = "INSERT INTO"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE`"
                    + " SET"
                    + " `CREATED`=NOW()"
                    + ", `CREATEDBY`='AAM'"
                    + ", `ACTIVE`='1'"
                    + ", `STATUS`='Provisional'"
                    + ", `PSEUDOCAN`='" + maxPseudoCan + "'"
                    + ", `OVPRID`='" + GeneralDAO.CleanMySQLString(newAffReqForm.getEmail()) + "'"
                    + ", `FIRSTNAME`='" + GeneralDAO.CleanMySQLString(newAffReqForm.getFirstName()) + "'"
                    + ", `MIDDLENAME`='" + GeneralDAO.CleanMySQLString(newAffReqForm.getMiddleName()) + "'"
                    + ", `LASTNAME`='" + GeneralDAO.CleanMySQLString(newAffReqForm.getLastName()) + "'"
                    + ", `TITLE`='" + GeneralDAO.CleanMySQLString(newAffReqForm.getTitle()) + "'"
                    + ", `DEPARTMENT`='" + GeneralDAO.CleanMySQLString(newAffReqForm.getDepartment()) + "'"
                    + ", `ORGANIZATION`='" + GeneralDAO.CleanMySQLString(newAffReqForm.getOrganization()) + "'"
                    + ", `EMAIL`='" + GeneralDAO.CleanMySQLString(newAffReqForm.getEmail()) + "'"
                    + ", `PHONE`='" + GeneralDAO.CleanMySQLString(phone) + "'";

            //System.out.println("Insert Aff Req Cmd:\n" + insertAffReqCmd + "\n");
            insertAffReqStat.executeUpdate(insertAffReqCmd);

            final String getLastAffIdQuery = "SELECT"
                    + " MAX(`id`) as `id`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE`";
            final ResultSet getLastAffIdRs = getLastAffIdStat.executeQuery(getLastAffIdQuery);
            int lastId;
            if (getLastAffIdRs.next()) {
                lastId = getLastAffIdRs.getInt("id");
            } else {
                throw new Exception("The following basic query did not generate results.\n" + getLastAffIdQuery + "\n");
            }

            String zipCode = "";
            if (!newAffReqForm.getZip1().isEmpty()) {
                if (newAffReqForm.getZip2().isEmpty()) {
                    zipCode = newAffReqForm.getZip1() + "-0000";
                } else {
                    zipCode = newAffReqForm.getZip1() + "-" + newAffReqForm.getZip2();
                }
            }

            String faxNum = "";
            if(!newAffReqForm.getfAreaCode().isEmpty() && !newAffReqForm.getfPhone1().isEmpty() && !newAffReqForm.getfPhone2().isEmpty()){
                faxNum = newAffReqForm.getfAreaCode() + "-" + newAffReqForm.getfPhone1() + "-" + newAffReqForm.getfPhone2();
            }
            String businessPhoneNum = "";
            if(!newAffReqForm.getbAreaCode().isEmpty() && !newAffReqForm.getbPhone1().isEmpty() && !newAffReqForm.getbPhone2().isEmpty()){
                businessPhoneNum = newAffReqForm.getbAreaCode() + "-" + newAffReqForm.getbPhone1() + "-" + newAffReqForm.getbPhone2();
            }
            
            final String insertAffReqExpCmd = "INSERT INTO"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_EXPANSION`"
                    + " SET"
                    + " `AFFILIATE_ID`='" + lastId + "'"
                    + ", `ADDR_1`='" + GeneralDAO.CleanMySQLString(newAffReqForm.getAddr1()) + "'"
                    + ", `ADDR_2`='" + GeneralDAO.CleanMySQLString(newAffReqForm.getAddr2()) + "'"
                    + ", `CITY`='" + GeneralDAO.CleanMySQLString(newAffReqForm.getCity()) + "'"
                    + ", `STATE`='" + GeneralDAO.CleanMySQLString(newAffReqForm.getState()) + "'"
                    + ", `COUNTRY`='" + GeneralDAO.CleanMySQLString(newAffReqForm.getCountry()) + "'"
                    + ", `ZIP`='" + GeneralDAO.CleanMySQLString(zipCode) + "'"
                    + ", `BUSINESS_PHONE_NUM`='" + GeneralDAO.CleanMySQLString(businessPhoneNum) + "'"
                    + ", `FAX_NUMBER`='" + GeneralDAO.CleanMySQLString(faxNum) + "'";
            //System.out.println("Insert Aff Req Exp Cmd:\n" + insertAffReqExpCmd + "\n");
            insertAffReqExpStat.executeUpdate(insertAffReqExpCmd);

            // Link the user with target purposes
            String purpose_ids = newAffReqForm.getPurpose_ids();
            //System.out.println("Purpose IDs = " + purpose_ids + "\n");
            purpose_ids = purpose_ids.replaceAll("\\s+$", "");
            String[] purpose_arr = purpose_ids.split("\\s+");
            for (String purpose_id : purpose_arr) {
                // Does an active, unexpired link already exist?
                final String findAffPurposeLinkQuery = "SELECT"
                        + " 1"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                        + " WHERE"
                        + " `apl`.`AFFILIATE_ID`='" + lastId + "'"
                        + " AND `apl`.`AFFILIATE_PURPOSE_ID`='" + purpose_id + "'";
                //System.out.println("Find Aff Purpose Link Query: " + findAffPurposeLinkQuery + "\n");
                final ResultSet findAffPurposeLinkRs = findAffPurposeLinkStat.executeQuery(findAffPurposeLinkQuery);
                if (!findAffPurposeLinkRs.next()) {
                    final String insertAffPurposeLinkCmd = "INSERT INTO"
                            + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                            + " SET"
                            + " `APPROVED`='Submitted'"
                            + ", `AFFILIATE_ID`='" + lastId + "'"
                            + ", `AFFILIATE_PURPOSE_ID`='" + purpose_id + "'"
                            + ", `CREATION_DATE`=NOW()";
                    //System.out.println("Insert aff purpose link:\n" + insertAffPurposeLinkCmd + "\n");
                    insertAffPurposeLinkStat.executeUpdate(insertAffPurposeLinkCmd);
                }
            }
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
}
