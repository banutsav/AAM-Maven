/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.request;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Static;
import edu.uga.ovpr.aam.email.AffiliateEmail;
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
public class AddAffPurposeReqDAO {

    public static void FindAff(AddAffPurposeReqForm addAffPurposeReqForm) throws Exception {
        Connection conn = null;
        final HashMap<String, Object> results = new HashMap<String, Object>();
        final HashMap<String, Object> linkedPurposes = new HashMap<String, Object>();

        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement findAffStat = conn.createStatement();
            final Statement getPurposesStat = conn.createStatement();
            final Statement getAllPurposesStat = conn.createStatement();
            final String findAffQuery = "SELECT DISTINCT"
                    + " `a`.`ID` AS `ID`"
                    + ", `a`.`FIRSTNAME` AS `FIRSTNAME`"
                    + ", `a`.`MIDDLENAME` AS `MIDDLENAME`"
                    + ", `a`.`LASTNAME` AS `LASTNAME`"
                    + ", `a`.`OVPRID` AS `OVPRID`"
                    + ", `a`.`PSEUDOCAN` AS `PSEUDOCAN`"
                    + ", `a`.`TITLE` AS `TITLE`"
                    + ", `a`.`DEPARTMENT` AS `DEPARTMENT`"
                    + ", `a`.`ORGANIZATION` AS `ORGANIZATION`"
                    + ", `a`.`PHONE` AS `PHONE`"
                    + ", `a`.`ACTIVE` AS `ACTIVE`"
                    + ", `a`.`EMAIL` AS `EMAIL`"
                    //+ ", `ae`.`INSTITUTION_COMPANY` AS `INSTITUTION_COMPANY`"
                    + ", `ae`.`ADDR_1` AS `ADDR_1`"
                    + ", `ae`.`ADDR_2` AS `ADDR_2`"
                    + ", `ae`.`CITY` AS `CITY`"
                    + ", `ae`.`STATE` AS `STATE`"
                    + ", `ae`.`COUNTRY` AS `COUNTRY`"
                    + ", `ae`.`ZIP` AS `ZIP`"
                    + ", `ae`.`BUSINESS_PHONE_NUM` AS `BUSINESS_PHONE_NUM`"
                    //+ ", `ae`.`ALT_PHONE_NUM` AS `ALT_PHONE_NUM`"
                    + ", `ae`.`FAX_NUMBER` AS `FAX_NUMBER`"
                    //+ ", CASE WHEN `ae`.`EXPIRATION_DATE` IS NULL THEN ''"
                    //+ "  ELSE `ae`.`EXPIRATION_DATE` END AS `EXPIRATION_DATE`"
                    + ", `ae`.`COMMENTS` AS `COMMENTS`"
                    + ", `ae`.`FSCODES` AS `FSCODES`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_EXPANSION` `ae`"
                    + " ON `a`.`ID`=`ae`.`AFFILIATE_ID`"
                    + " WHERE"
                    + " `a`.`ID`='" + GeneralDAO.CleanMySQLString(addAffPurposeReqForm.getTargAffId()) + "'";
            //System.out.println("Find Affiliate Query:\n" + findAffQuery + "\n");
            final ResultSet findAffRs = findAffStat.executeQuery(findAffQuery);
            if (findAffRs.next()) {
                ArrayList<Object> purposes = new ArrayList<Object>();
                final String getPurposesQuery = "SELECT"
                        + " `ap`.`ID`"
                        + ", `ap`.`SHORT_NAME`"
                        + ", `ap`.`DESCRIPTION`"
                        + ", `ap`.`PERIOD`"
                        + ", `apl`.`APPROVED` AS `LINK_APPROVED`"
                        + ", `apl`.`EXPIRES`"
                        + ", DATE(`apl`.`CREATION_DATE`) AS `CREATION_DATE`"
                        + ", DATE(`apl`.`EXPIRATION_DATE`) AS `EXPIRATION_DATE`"
                        + ", DATE(`apl`.`MANAGE_DATE`) AS `MANAGE_DATE`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                        + " ON `a`.`ID`=`apl`.`AFFILIATE_ID`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                        + " ON `apl`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                        + " AND `ap`.`ACTIVE`='1'"
                        + " WHERE"
                        + " `a`.`PSEUDOCAN`='" + findAffRs.getString("PSEUDOCAN") + "'"
                        + " AND `ap`.`APPROVED`='Approved'"
                        + " ORDER BY"
                        + " FIND_IN_SET(`LINK_APPROVED`, 'Submitted,Approved,Rejected'), `SHORT_NAME`";
                //System.out.println("Get Purposes Query:\n" + getPurposesQuery + "\n");
                final ResultSet getPurposesRs = getPurposesStat.executeQuery(getPurposesQuery);
                while (getPurposesRs.next()) {
                    HashMap<String, Object> data = new HashMap<String, Object>();
                    data.put("ID", getPurposesRs.getString("ID"));
                    data.put("SHORT_NAME", getPurposesRs.getString("SHORT_NAME"));
                    data.put("DESCRIPTION", getPurposesRs.getString("DESCRIPTION"));
                    data.put("PERIOD", getPurposesRs.getString("PERIOD"));
                    data.put("LINK_APPROVED", getPurposesRs.getString("LINK_APPROVED"));
                    data.put("EXPIRES", getPurposesRs.getString("EXPIRES"));
                    data.put("CREATION_DATE", getPurposesRs.getString("CREATION_DATE"));
                    data.put("EXPIRATION_DATE", getPurposesRs.getString("EXPIRATION_DATE"));
                    //System.out.println("Expiration date = '" + data.get("EXPIRATION_DATE") + "'");
                    data.put("MANAGE_DATE", getPurposesRs.getString("MANAGE_DATE"));
                    linkedPurposes.put(getPurposesRs.getString("ID"), getPurposesRs.getString("ID"));
                    purposes.add(data);
                }
                results.put("purposes", purposes);
  
                final String getAllPurposesQuery = "SELECT"
                        + " `ap`.`ID`"
                        + ", `ap`.`SHORT_NAME`"
                        + ", `ap`.`DESCRIPTION`"
                        + ", `ap`.`PERIOD`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                        + " WHERE"
                        + " `ap`.`APPROVED`='Approved'"
                        + " AND `ap`.`ACTIVE`='1'"
                        + " AND `ap`.`SHORT_NAME` != 'Associate'";  // 'associate' purpose should not show up
                
                //System.out.println("Get All Purposes Query:\n" + getAllPurposesQuery + "\n");
                
                final ResultSet getAllPurposesRs = getAllPurposesStat.executeQuery(getAllPurposesQuery);
                final ArrayList<Object> openPurposes = new ArrayList<Object>();
                while (getAllPurposesRs.next()) {
                    //System.out.println(getAllPurposesRs.getString("ID") + ": " + linkedPurposes.get(getAllPurposesRs.getString("ID")));
                    if (!linkedPurposes.containsKey(getAllPurposesRs.getString("ID"))) {
                        HashMap<String, Object> data = new HashMap<String, Object>();
                        data.put("ID", getAllPurposesRs.getString("ID"));
                        data.put("SHORT_NAME", getAllPurposesRs.getString("SHORT_NAME"));
                        data.put("DESCRIPTION", getAllPurposesRs.getString("DESCRIPTION"));
                        data.put("PERIOD", getAllPurposesRs.getString("PERIOD"));
                        openPurposes.add(data);
                    }
                }
                results.put("openPurposes", openPurposes);

                results.put("ID", findAffRs.getString("ID"));
                results.put("FIRSTNAME", findAffRs.getString("FIRSTNAME"));
                results.put("MIDDLENAME", findAffRs.getString("MIDDLENAME"));
                results.put("LASTNAME", findAffRs.getString("LASTNAME"));
                results.put("OVPRID", findAffRs.getString("OVPRID"));
                results.put("EMAIL", findAffRs.getString("EMAIL"));
                results.put("PSEUDOCAN", findAffRs.getString("PSEUDOCAN"));
                results.put("TITLE", findAffRs.getString("TITLE"));
                results.put("DEPARTMENT", findAffRs.getString("DEPARTMENT"));
                results.put("ORGANIZATION", findAffRs.getString("ORGANIZATION"));
                results.put("PHONE", findAffRs.getString("PHONE"));
                results.put("ACTIVE", findAffRs.getString("ACTIVE"));
                //results.put("INSTITUTION_COMPANY", findAffRs.getString("INSTITUTION_COMPANY"));
                results.put("ADDR_1", findAffRs.getString("ADDR_1"));
                results.put("ADDR_2", findAffRs.getString("ADDR_2"));
                results.put("CITY", findAffRs.getString("CITY"));
                results.put("STATE", findAffRs.getString("STATE"));
                results.put("COUNTRY", findAffRs.getString("COUNTRY"));
                results.put("ZIP", findAffRs.getString("ZIP"));
                results.put("BUSINESS_PHONE_NUM", findAffRs.getString("BUSINESS_PHONE_NUM"));
                //results.put("ALT_PHONE_NUM", findAffRs.getString("ALT_PHONE_NUM"));
                results.put("FAX_NUMBER", findAffRs.getString("FAX_NUMBER"));
                //results.put("EXPIRATION_DATE", findAffRs.getString("EXPIRATION_DATE"));
                results.put("COMMENTS", findAffRs.getString("COMMENTS"));
                results.put("FSCODES", findAffRs.getString("FSCODES"));
                addAffPurposeReqForm.setTargAffId(findAffRs.getString("ID"));
            }
            addAffPurposeReqForm.setPersonInfo(results);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void AddPurposeReq(AddAffPurposeReqForm addAffPurposeReqForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement addPurposeStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String addPurposeCmd = "INSERT INTO"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                    + " SET"
                    + " `AFFILIATE_ID`='" + GeneralDAO.CleanMySQLString(addAffPurposeReqForm.getTargAffId()) + "'"
                    + ", `AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(addAffPurposeReqForm.getTargPurpId()) + "'"
                    + ", `CREATION_DATE`=NOW()";
            addPurposeStat.executeUpdate(addPurposeCmd);

            AffiliateEmail.EmailManagersNewAffPurpose(addAffPurposeReqForm);
            
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
