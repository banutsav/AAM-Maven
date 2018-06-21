/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.manager;

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
public class ManAffDAO {

    public static void GetAffData(ManAffForm manAffForm, String cannum, String role) throws Exception {
        Connection conn = null;
        HashMap<String, Object> affData = new HashMap<String, Object>();
        try {
            conn = Static.getConnection("jdbc/paul");

            final Statement getAffDataStat = conn.createStatement();
            final Statement getPurposesStat = conn.createStatement();

            final String getAffDataQuery = "SELECT DISTINCT"
                    + " `a`.`ID` AS `ID`"
                    + ", `a`.`FIRSTNAME` AS `FIRSTNAME`"
                    + ", `a`.`MIDDLENAME` AS `MIDDLENAME`"
                    + ", `a`.`LASTNAME` AS `LASTNAME`"
                    + ", `a`.`OVPRID` AS `OVPRID`"
                    + ", `a`.`EMAIL` AS `EMAIL`"
                    + ", `a`.`PSEUDOCAN` AS `PSEUDOCAN`"
                    + ", `a`.`TITLE` AS `TITLE`"
                    + ", `a`.`DEPARTMENT` AS `DEPARTMENT`"
                    + ", `a`.`ORGANIZATION` AS `ORGANIZATION`"
                    + ", `a`.`PHONE` AS `PHONE`"
                    + ", `a`.`ACTIVE` AS `ACTIVE`"
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
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                    + " ON `apl`.`AFFILIATE_ID`=`a`.`ID`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`PERSON` `m`"
                    + " ON `m`.`CANNUM`=`apl`.`MANAGER_CANNUM`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`DEPT` `d`"
                    + " ON `m`.`HOMEDEPT_ID`=`d`.`ID`"
                    + " WHERE"
                    + " `a`.`ID`='" + GeneralDAO.CleanMySQLString(manAffForm.getAffId()) + "'";
            //System.out.println("Get Aff Data Query:\n" + getAffDataQuery + "\n");
            final ResultSet getAffDataRs = getAffDataStat.executeQuery(getAffDataQuery);
            if (getAffDataRs.next()) {
                final ArrayList<Object> purpose = new ArrayList<Object>();
                String getPurposesQuery = "SELECT DISTINCT"
                        + " `ap`.`ID` AS `PURPOSE_ID`"
                        + ", `ap`.`SHORT_NAME`"
                        + ", `ap`.`DESCRIPTION`"
                        + ", `ap`.`PERIOD`"
                        + ", CASE WHEN `ap`.`CREATION_DATE` IS NULL THEN ''"
                        + "  ELSE DATE(`ap`.`CREATION_DATE`) END AS `PURPOSE_CREATION_DATE`"
                        + ", `apl`.`ID` AS `PURPOSE_LINK_ID`"
                        + ", `apl`.`APPROVED` AS 'LINK_APPROVED'"
                        + ", `apl`.`EXPIRES` AS 'EXPIRES'"
                        + ", CASE WHEN `apl`.`ACTIVE`='1' THEN 'Yes'"
                        + "  ELSE 'No' END AS 'ACTIVE'"
                        + ", CASE WHEN `apl`.`CREATION_DATE` IS NULL THEN ''"
                        + "  ELSE DATE(`apl`.`CREATION_DATE`) END AS `LINK_CREATION_DATE`"
                        + ", DATE(`apl`.`EXPIRATION_DATE`) AS `LINK_EXPIRATION_DATE`"
                        + ", CASE WHEN `apl`.`MANAGE_DATE` IS NULL THEN ''"
                        + "  ELSE DATE(`apl`.`MANAGE_DATE`) END AS `LINK_MANAGE_DATE`"
                        + ", CASE WHEN `apl`.`EXPIRATION_DATE` <= NOW()"
                        + "  AND `apl`.`EXPIRES`='1'"
                        + "  THEN '1'"
                        + "  ELSE '0' END AS `EXPIRED`"
                        + ", `c`.`FIRSTNAME` AS `CREATOR_FIRSTNAME`"
                        + ", `c`.`LASTNAME` AS `CREATOR_LASTNAME`"
                        + ", `c`.`MYID` AS `CREATOR_MYID`"
                        + ", `c`.`CANNUM` AS `CREATOR_CANNUM`"
                        + ", `c`.`EMAIL` AS `CREATOR_EMAIL`"
                        + ", `m`.`FIRSTNAME` AS `MANAGER_FIRSTNAME`"
                        + ", `m`.`LASTNAME` AS `MANAGER_LASTNAME`"
                        + ", `m`.`MYID` AS `MANAGER_MYID`"
                        + ", `m`.`CANNUM` AS `MANAGER_CANNUM`"
                        + ", `m`.`EMAIL` AS `MANAGER_EMAIL`";
                if (role.equalsIgnoreCase("SuperUser")) {
                    getPurposesQuery += ", '1' AS `PERMISSION`"
                            + ", '1' AS `UNEXPIRE_PRIVILEGE`";
                } else {
                    getPurposesQuery += ", CASE WHEN '" + role + "'='superuser' THEN '1'"
                            + "  WHEN `aa`.`CANNUM` IS NOT NULL"
                            + "  AND `aap`.`ACTIVE` IS NOT NULL"
                            + "  THEN '1'"
                            + "  ELSE '0' END AS `PERMISSION`"
                            + ", CASE WHEN `ap`.`PERIOD`='-1' THEN '1'"
                            + "  ELSE `aap`.`UNEXPIRE_PRIVILEGE` END AS `UNEXPIRE_PRIVILEGE`";
                }
                getPurposesQuery += " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                        + " ON `apl`.`AFFILIATE_ID`='" + getAffDataRs.getString("ID") + "'"
                        + " AND `apl`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                        + " AND `ap`.`APPROVED`='Approved'"
                        + " AND `ap`.`ACTIVE`='1'"
                        + " LEFT JOIN `" + Constants.DB_PAUL + "`.`PERSON` `c`"
                        + " ON `c`.`CANNUM`=`ap`.`CREATED_BY_CANNUM`"
                        + " LEFT JOIN `" + Constants.DB_PAUL + "`.`PERSON` `m`"
                        + " ON `m`.`CANNUM`=`apl`.`MANAGER_CANNUM`";
                if (!role.equalsIgnoreCase("SuperUser")) {
                    getPurposesQuery += " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                            + " ON `aa`.`CANNUM`='" + cannum + "'"
                            + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                            + " ON `aa`.`ID`=`aap`.`AFFILIATE_ADMIN_ID`"
                            + " AND `ap`.`ID`=`aap`.`AFFILIATE_PURPOSE_ID`"
                            + " AND `aap`.`ACTIVE`='1'";
                }
                getPurposesQuery += " ORDER BY"
                        + " FIND_IN_SET(`LINK_APPROVED`, 'Submitted,Approved,Rejected'), `SHORT_NAME`";

                //System.out.println("Get Aff's Purposes Query:\n" + getPurposesQuery + "\n");
                final ResultSet getPurposesRs = getPurposesStat.executeQuery(getPurposesQuery);
                while (getPurposesRs.next()) {
                    HashMap<String, Object> purposeData = new HashMap<String, Object>();
                    purposeData.put("PURPOSE_ID", getPurposesRs.getString("PURPOSE_ID"));
                    purposeData.put("SHORT_NAME", getPurposesRs.getString("SHORT_NAME"));
                    purposeData.put("DESCRIPTION", getPurposesRs.getString("DESCRIPTION"));
                    purposeData.put("PERIOD", getPurposesRs.getString("PERIOD"));
                    purposeData.put("PURPOSE_CREATION_DATE", getPurposesRs.getString("PURPOSE_CREATION_DATE").replaceFirst(":\\d{2}(\\.\\d){0,1}$", ""));
                    //purposeData.put("PURPOSE_EXPIRATION_DATE", getPurposesRs.getString("PURPOSE_EXPIRATION_DATE").replaceFirst(":\\d{2}(\\.\\d){0,1}$", ""));
                    purposeData.put("PURPOSE_LINK_ID", getPurposesRs.getString("PURPOSE_LINK_ID"));
                    purposeData.put("LINK_APPROVED", getPurposesRs.getString("LINK_APPROVED"));
                    purposeData.put("EXPIRES", getPurposesRs.getString("EXPIRES"));
                    purposeData.put("EXPIRED", getPurposesRs.getString("EXPIRED"));
                    purposeData.put("ACTIVE", getPurposesRs.getString("ACTIVE"));
                    purposeData.put("LINK_CREATION_DATE", getPurposesRs.getString("LINK_CREATION_DATE").replaceFirst(":\\d{2}(\\.\\d){0,1}$", ""));
                    if (getPurposesRs.getString("LINK_EXPIRATION_DATE") == null) {
                        purposeData.put("LINK_EXPIRATION_DATE", null);
                    } else {
                        purposeData.put("LINK_EXPIRATION_DATE", getPurposesRs.getString("LINK_EXPIRATION_DATE").replaceFirst(":\\d{2}(\\.\\d){0,1}$", ""));
                    }
                    purposeData.put("LINK_MANAGE_DATE", getPurposesRs.getString("LINK_MANAGE_DATE").replaceFirst(":\\d{2}(\\.\\d){0,1}$", ""));
                    purposeData.put("CREATOR_FIRSTNAME", getPurposesRs.getString("CREATOR_FIRSTNAME"));
                    purposeData.put("CREATOR_LASTNAME", getPurposesRs.getString("CREATOR_LASTNAME"));
                    purposeData.put("CREATOR_MYID", getPurposesRs.getString("CREATOR_MYID"));
                    purposeData.put("CREATOR_CANNUM", getPurposesRs.getString("CREATOR_CANNUM"));
                    purposeData.put("CREATOR_EMAIL", getPurposesRs.getString("CREATOR_EMAIL"));
                    purposeData.put("MANAGER_FIRSTNAME", getPurposesRs.getString("MANAGER_FIRSTNAME"));
                    purposeData.put("MANAGER_LASTNAME", getPurposesRs.getString("MANAGER_LASTNAME"));
                    purposeData.put("MANAGER_MYID", getPurposesRs.getString("MANAGER_MYID"));
                    purposeData.put("MANAGER_CANNUM", getPurposesRs.getString("MANAGER_CANNUM"));
                    purposeData.put("MANAGER_EMAIL", getPurposesRs.getString("MANAGER_EMAIL"));
                    purposeData.put("PERMISSION", getPurposesRs.getString("PERMISSION"));
                    purposeData.put("UNEXPIRE_PRIVILEGE", getPurposesRs.getString("UNEXPIRE_PRIVILEGE"));
                    purpose.add(purposeData);
                }
                affData.put("ID", getAffDataRs.getString("ID"));
                affData.put("FIRSTNAME", getAffDataRs.getString("FIRSTNAME"));
                affData.put("MIDDLENAME", getAffDataRs.getString("MIDDLENAME"));
                affData.put("LASTNAME", getAffDataRs.getString("LASTNAME"));
                affData.put("OVPRID", getAffDataRs.getString("OVPRID"));
                affData.put("EMAIL", getAffDataRs.getString("EMAIL"));
                affData.put("PSEUDOCAN", getAffDataRs.getString("PSEUDOCAN"));
                affData.put("TITLE", getAffDataRs.getString("TITLE"));
                affData.put("DEPARTMENT", getAffDataRs.getString("DEPARTMENT"));
                affData.put("ORGANIZATION", getAffDataRs.getString("ORGANIZATION"));
                affData.put("PHONE", getAffDataRs.getString("PHONE"));
                affData.put("ACTIVE", getAffDataRs.getString("ACTIVE"));
                //affData.put("INSTITUTION_COMPANY", getAffDataRs.getString("INSTITUTION_COMPANY"));
                affData.put("ADDR_1", getAffDataRs.getString("ADDR_1"));
                affData.put("ADDR_2", getAffDataRs.getString("ADDR_2"));
                affData.put("CITY", getAffDataRs.getString("CITY"));
                affData.put("STATE", getAffDataRs.getString("STATE"));
                affData.put("COUNTRY", getAffDataRs.getString("COUNTRY"));
                affData.put("ZIP", getAffDataRs.getString("ZIP"));
                affData.put("BUSINESS_PHONE_NUM", getAffDataRs.getString("BUSINESS_PHONE_NUM"));
                //affData.put("ALT_PHONE_NUM", getAffDataRs.getString("ALT_PHONE_NUM"));
                affData.put("FAX_NUMBER", getAffDataRs.getString("FAX_NUMBER"));
                //affData.put("EXPIRATION_DATE", getAffDataRs.getString("EXPIRATION_DATE").replaceFirst(":\\d{2}(\\.\\d){0,1}$", ""));
                affData.put("COMMENTS", getAffDataRs.getString("COMMENTS"));
                affData.put("FSCODES", getAffDataRs.getString("FSCODES"));
                affData.put("purposeDetails", purpose);
            }
            manAffForm.setAff(affData);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void GetAuthPurposes(ManAffForm manAffForm, String cannum, String role) throws Exception {
        Connection conn = null;
        ArrayList<Object> results = new ArrayList<Object>();
        try {
            conn = Static.getConnection("jdbc/paul");

            final Statement getAuthPurposesStat = conn.createStatement();
            String getAuthPurposesQuery = "SELECT DISTINCT"
                    + " `ap`.`SHORT_NAME`"
                    + ", `ap`.`ID`"
                    + ", `ap`.`DESCRIPTION`"
                    + ", `ap`.`PERIOD`"
                    + ", CASE WHEN `apl`.`ID` IS NULL THEN '1'"
                    + "  ELSE '0' END AS `OPEN_PURPOSE`"
                    //+ ", CASE WHEN `ap`.`EXPIRATION_DATE` IS NULL THEN ''"
                    //+ "  ELSE  `ap`.`EXPIRATION_DATE` END AS `EXPIRATION_DATE`"
                    //+ ", CASE WHEN NOW() > `ap`.`EXPIRATION_DATE` THEN 1"
                    //+ " ELSE 0 END AS `EXPIRED`"
                    + ", `ap`.`ACTIVE`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                    + " ON `aap`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                    + " AND `aap`.`ACTIVE`='1'"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " ON `aa`.`ID`=`aap`.`AFFILIATE_ADMIN_ID`"
                    + " AND `aa`.`CANNUM`='" + cannum + "'"
                    + " AND `aa`.`ACTIVE`='1'"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                    + " ON `apl`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                    + " AND `apl`.`AFFILIATE_ID`='" + GeneralDAO.CleanMySQLString(manAffForm.getAffId()) + "'"
                    + " WHERE"
                    + " `ap`.`APPROVED`='Approved'"
                    + " AND `ap`.`ACTIVE`='1'";
            if (!role.equalsIgnoreCase("SuperUser")) {
                getAuthPurposesQuery += " AND `aa`.`CANNUM`='" + cannum + "'";
            }
            getAuthPurposesQuery += " ORDER BY"
                    + " `SHORT_NAME`";
            //System.out.println("Get Auth Purposes Query:\n" + getAuthPurposesQuery + "\n");
            final ResultSet getAuthPurposesRs = getAuthPurposesStat.executeQuery(getAuthPurposesQuery);
            while (getAuthPurposesRs.next()) {
                if (getAuthPurposesRs.getString("OPEN_PURPOSE").equalsIgnoreCase("1")) {
                    HashMap<String, Object> data = new HashMap<String, Object>();
                    data.put("SHORT_NAME", getAuthPurposesRs.getString("SHORT_NAME"));
                    data.put("ID", getAuthPurposesRs.getString("ID"));
                    data.put("DESCRIPTION", getAuthPurposesRs.getString("DESCRIPTION"));
                    data.put("PERIOD", getAuthPurposesRs.getString("PERIOD"));
                    //data.put("EXPIRATION_DATE", getAuthPurposesRs.getString("EXPIRATION_DATE").replaceFirst(":\\d{2}(\\.\\d){0,1}$", ""));
                    //data.put("EXPIRED", getAuthPurposesRs.getString("EXPIRED"));
                    data.put("ACTIVE", getAuthPurposesRs.getString("ACTIVE"));
                    results.add(data);
                }
            }

            manAffForm.setPurposes(results);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static String UpdateAffPurpose(ManAffForm manAffForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement updateAffPurposeStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            //System.out.println(manAffForm.getPeriodCount() + " & " + manAffForm.getPeriodLength());

            final String updateAffPurposeCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                    + " SET"
                    + " `EXPIRATION_DATE`=DATE_ADD(NOW(), INTERVAL " + GeneralDAO.CleanMySQLString(manAffForm.getMonthCount()) + " MONTH)"
                    + ", `EXPIRES`='1'"
                    + " WHERE"
                    + " `AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(manAffForm.getTargPurposeId()) + "'"
                    + " AND `AFFILIATE_ID`='" + GeneralDAO.CleanMySQLString(manAffForm.getAffId()) + "'";
            //System.out.println("Update Aff Purpose Cmd:\n" + updateAffPurposeCmd + "\n");
            updateAffPurposeStat.executeUpdate(updateAffPurposeCmd);

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

    public static String AddAffPurpose(ManAffForm manAffForm, String cannum) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        String response = "";
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement doesLinkExistStat = conn.createStatement();
            final Statement insertLinkStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String doesLinkExistQuery = "SELECT"
                    + " `a`.`FIRSTNAME`"
                    + ", `a`.`LASTNAME`"
                    + ", `ap`.`ACTIVE` AS `PURPOSE_ACTIVE`"
                    + ", `ap`.`SHORT_NAME`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                    + " ON `a`.`ID`='" + GeneralDAO.CleanMySQLString(manAffForm.getAffId()) + "'"
                    + " AND `apl`.`AFFILIATE_ID`=`a`.`ID`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " ON `apl`.`AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(manAffForm.getAddPurposeId()) + "'"
                    + " AND `ap`.`ID`=`apl`.`AFFILIATE_PURPOSE_ID`"
                    + " AND `ap`.`APPROVED`='Approved'"
                    + " AND `ap`.`ACTIVE`='1'";
            //System.out.println("Does Link Exist Query:\n" + doesLinkExistQuery + "\n");
            final ResultSet doesLinkExistRs = doesLinkExistStat.executeQuery(doesLinkExistQuery);
            if (doesLinkExistRs.next()) {
                response = "ERROR: A link already exists between "
                        + "'" + doesLinkExistRs.getString("LASTNAME") + ", "
                        + doesLinkExistRs.getString("FIRSTNAME") + "'"
                        + " and purpose '" + doesLinkExistRs.getString("SHORT_NAME") + "'";
            } else {
                String insertLinkCmd = "INSERT INTO"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                        + " SET"
                        + " `ACTIVE`='1'"
                        + ", `AFFILIATE_ID`='" + GeneralDAO.CleanMySQLString(manAffForm.getAffId()) + "'"
                        + ", `AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(manAffForm.getAddPurposeId()) + "'"
                        + ", `APPROVED`='Approved'"
                        + ", `MANAGER_CANNUM`='" + cannum + "'"
                        + ", `CREATION_DATE`=NOW()";
                if (manAffForm.getPeriodLength().equalsIgnoreCase("-1")) {
                    insertLinkCmd += ", `EXPIRES`='0'";
                } else {
                    insertLinkCmd += ", `EXPIRATION_DATE`=DATE_ADD(NOW(), INTERVAL " + GeneralDAO.CleanMySQLString(manAffForm.getPeriodLength()) + " MONTH)";
                }
                insertLinkCmd += ", `MANAGE_DATE`=NOW()";
                //System.out.println("Insert Link Cmd:\n" + insertLinkCmd + "\n");
                insertLinkStat.executeUpdate(insertLinkCmd);
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

    public static void unexpirePurpose(ManAffForm manAffForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement unexpirePurposeStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String unexpirePurposeCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                    + " SET"
                    + " `EXPIRES`='0'"
                    + " WHERE"
                    + " `AFFILIATE_ID`='" + manAffForm.getAffId() + "'"
                    + " AND `AFFILIATE_PURPOSE_ID`='" + manAffForm.getTargPurposeId() + "'";
            //System.out.println("Unexpire Purpose Cmd:\n" + unexpirePurposeCmd + "\n");
            unexpirePurposeStat.executeUpdate(unexpirePurposeCmd);

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

    public static void changeApprovalStatus(ManAffForm manAffForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement getPurposePeriodStat = conn.createStatement();
            final Statement changeApprovalStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            if (manAffForm.getNewApprovalStatus().equalsIgnoreCase("Approved")) {
                final String getPurposePeriodQuery = "SELECT"
                        + " `PERIOD`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                        + " WHERE"
                        + " `ID`='" + manAffForm.getTargPurposeId() + "'";
                //System.out.println("Get Purpose Period Query:\n" + getPurposePeriodQuery + "\n");
                final ResultSet getPurposePeriodRs = getPurposePeriodStat.executeQuery(getPurposePeriodQuery);
                String period = "0";
                if (getPurposePeriodRs.next()) {
                    period = getPurposePeriodRs.getString("PERIOD");
                }
                String changeApprovalCmd = "";
                if (period.equalsIgnoreCase("-1")) {
                    changeApprovalCmd = "UPDATE"
                            + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                            + " SET"
                            + " `APPROVED`='Approved'"
                            + ", `EXPIRATION_DATE`=NOW()"
                            + ", `EXPIRES`='0'"
                            + " WHERE"
                            + " `AFFILIATE_ID`='" + manAffForm.getAffId() + "'"
                            + " AND `AFFILIATE_PURPOSE_ID`='" + manAffForm.getTargPurposeId() + "'";
                } else {
                    changeApprovalCmd = "UPDATE"
                            + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                            + " SET"
                            + " `APPROVED`='Approved'"
                            + ", `EXPIRATION_DATE`= DATE_ADD(NOW(), INTERVAL " + period + " MONTH)"
                            + ", `EXPIRES`='1'"
                            + " WHERE"
                            + " `AFFILIATE_ID`='" + manAffForm.getAffId() + "'"
                            + " AND `AFFILIATE_PURPOSE_ID`='" + manAffForm.getTargPurposeId() + "'";
                }
                //System.out.println("Change Approval Cmd:\n" + changeApprovalCmd + "\n");
                changeApprovalStat.executeUpdate(changeApprovalCmd);
            } else {
                final String changeApprovalCmd = "UPDATE"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                        + " SET"
                        + " `EXPIRES`='1'"
                        + ", `EXPIRATION_DATE`=NOW()"
                        + ", `APPROVED`='" + manAffForm.getNewApprovalStatus() + "'"
                        + " WHERE"
                        + " `AFFILIATE_ID`='" + manAffForm.getAffId() + "'"
                        + " AND `AFFILIATE_PURPOSE_ID`='" + manAffForm.getTargPurposeId() + "'";
                //System.out.println("Change Approval Cmd:\n" + changeApprovalCmd + "\n");
                changeApprovalStat.executeUpdate(changeApprovalCmd);
            }

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
