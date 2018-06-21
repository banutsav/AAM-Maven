/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.superuser;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Static;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author submyers
 */
public class ControlManActiveDAO {

    public static void GetManInfo(ControlManActiveForm controlManActiveForm) throws Exception {
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getBasicInfoStat = conn.createStatement();
            final Statement getPurposesStat = conn.createStatement();
            final Statement getOtherPurposesStat = conn.createStatement();
            final Statement purpManCountStat = conn.createStatement();
            final Statement getProxiesStat = conn.createStatement();

            HashMap<String, Object> purposesSeen = new HashMap<String, Object>();

            controlManActiveForm.getPurposes().clear();
            controlManActiveForm.getProxies().clear();
            controlManActiveForm.getOtherPurposes().clear();

            final String getBasicInfoQuery = "SELECT"
                    + " `p`.`ID` AS `PERSON_ID`"
                    + ", `aa`.`ACTIVE` AS `ADMIN_ACTIVE`"
                    + ", `p`.`FIRSTNAME`"
                    + ", `p`.`LASTNAME`"
                    + ", `p`.`MYID`"
                    + ", `p`.`CANNUM`"
                    + ", `p`.`EMAIL`"
                    + ", `p`.`PHONE`"
                    + ", `p`.`TITLE`"
                    + ", `d`.`DEPTNAME`"
                    + ", `d`.`DEPTNUM`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " ON `aa`.`CANNUM`=`p`.`CANNUM`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`DEPT` `d`"
                    + " ON `p`.`HOMEDEPT_ID`=`d`.`ID`"
                    + " WHERE"
                    + " `aa`.`ID`='" + controlManActiveForm.getAdminId() + "'";
            //System.out.println("Get Basic Man Info:\n" + getBasicInfoQuery + "\n");
            final ResultSet getBasicInfoRs = getBasicInfoStat.executeQuery(getBasicInfoQuery);
            if (getBasicInfoRs.next()) {
                controlManActiveForm.setAdminActive(getBasicInfoRs.getString("ADMIN_ACTIVE"));
                controlManActiveForm.setFirstName(getBasicInfoRs.getString("FIRSTNAME"));
                controlManActiveForm.setLastName(getBasicInfoRs.getString("LASTNAME"));
                controlManActiveForm.setMyId(getBasicInfoRs.getString("MYID"));
                controlManActiveForm.setCannum(getBasicInfoRs.getString("CANNUM"));
                controlManActiveForm.setEmail(getBasicInfoRs.getString("EMAIL"));
                controlManActiveForm.setPhone(getBasicInfoRs.getString("PHONE"));
                controlManActiveForm.setTitle(getBasicInfoRs.getString("TITLE"));
                controlManActiveForm.setDeptName(getBasicInfoRs.getString("DEPTNAME"));
                controlManActiveForm.setDeptNum(getBasicInfoRs.getString("DEPTNUM"));
            }

            final String getPurposesQuery = "SELECT"
                    + " `ap`.`ID` AS `PURPOSE_ID`"
                    + ", `ap`.`SHORT_NAME`"
                    + ", `ap`.`DESCRIPTION`"
                    + ", `ap`.`APPROVED`"
                    + ", `ap`.`PERIOD`"
                    + ", DATE(`ap`.`CREATION_DATE`) AS `CREATION_DATE`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                    + " ON `aa`.`ID`='" + controlManActiveForm.getAdminId() + "'"
                    + " AND `aap`.`AFFILIATE_ADMIN_ID`=`aa`.`ID`"
                    + " AND `aap`.`PROXY`='0'"
                    + " AND `aap`.`ACTIVE`='1'"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " ON `aap`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                    + " AND `ap`.`ACTIVE`='1'"
                    
                    + " ORDER BY"
                    + " `SHORT_NAME`";
            //System.out.println("Manager Get Purposes Query:\n" + getPurposesQuery + "\n");
            final ResultSet getPurposesRs = getPurposesStat.executeQuery(getPurposesQuery);
            while (getPurposesRs.next()) {
                HashMap<String, Object> data = new HashMap<String, Object>();

                final String purpManCountQuery = "SELECT"
                        + " COUNT(`aap`.`AFFILIATE_ADMIN_ID`) AS `ADMIN_COUNT`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                        + " WHERE `aap`.`AFFILIATE_PURPOSE_ID`='" + getPurposesRs.getString("PURPOSE_ID") + "'"
                        + " AND `aap`.`ACTIVE`='1'"
                        + " AND `aap`.`PROXY`='0'";
                //System.out.println("Get Purpose Managers Count:\n" + purpManCountQuery + "\n");
                final ResultSet purpManCountRs = purpManCountStat.executeQuery(purpManCountQuery);
                if (purpManCountRs.next()) {
                    data.put("ADMIN_COUNT", purpManCountRs.getString("ADMIN_COUNT"));
                } else {
                    data.put("ADMIN_COUNT", "0");
                }
                data.put("PURPOSE_ID", getPurposesRs.getString("PURPOSE_ID"));
                data.put("SHORT_NAME", getPurposesRs.getString("SHORT_NAME"));
                data.put("DESCRIPTION", getPurposesRs.getString("DESCRIPTION"));
                data.put("APPROVED", getPurposesRs.getString("APPROVED"));
                data.put("PERIOD", getPurposesRs.getString("PERIOD"));
                data.put("CREATION_DATE", getPurposesRs.getString("CREATION_DATE"));
                controlManActiveForm.getPurposes().add(data);
                purposesSeen.put(getPurposesRs.getString("PURPOSE_ID"), true);
            }
            final String getOtherPurposesQuery = "SELECT"
                    + " `ap`.`ID`"
                    + ", `ap`.`SHORT_NAME`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " WHERE"
                    + " `ap`.`ACTIVE`='1'"
                    
                    + " AND `ap`.`ID` != '" + Constants.associate_code + "' "
                    
                    + " AND `ap`.`APPROVED`='Approved'";
            final ResultSet getOtherPurposesRs = getOtherPurposesStat.executeQuery(getOtherPurposesQuery);
            while (getOtherPurposesRs.next()) {
                if (!purposesSeen.containsKey(getOtherPurposesRs.getString("ID"))) {
                    HashMap<String, Object> data = new HashMap<String, Object>();
                    data.put("ID", getOtherPurposesRs.getString("ID"));
                    data.put("SHORT_NAME", getOtherPurposesRs.getString("SHORT_NAME"));
                    controlManActiveForm.getOtherPurposes().add(data);
                }
            }

            final String getProxiesQuery = "SELECT"
                    + " `aa`.`ID`"
                    + ", `p`.`FIRSTNAME`"
                    + ", `p`.`LASTNAME`"
                    + ", `p`.`MYID`"
                    + ", `p`.`TITLE`"
                    + ", `p`.`CANNUM`"
                    + ", `p`.`EMAIL`"
                    + ", `p`.`PHONE`"
                    + ", `ap`.`SHORT_NAME`"
                    + ", `ap`.`ID` AS `PURPOSE_ID`"
                    + ", `d`.`DEPTNAME`"
                    + ", `d`.`DEPTNUM`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                    + " ON `aap`.`AFFILIATE_ADMIN_ID`=`aa`.`ID`"
                    + " AND `aap`.`PROXY`='1'"
                    + " AND `aap`.`ACTIVE`='1'"
                    + " AND `aa`.`ACTIVE`='1'"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa2`"
                    + " ON `aap`.`CREATED_BY_CANNUM`=`aa2`.`CANNUM`"
                    + " AND `aa2`.`ID`='" + controlManActiveForm.getAdminId() + "'"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " ON `aap`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                    + " AND `ap`.`ACTIVE`='1'"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " ON `p`.`CANNUM`=`aa`.`CANNUM`"
                    + " AND `p`.`ACTIVE`='1'"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`DEPT` `d`"
                    + " ON `d`.`ID`=`p`.`HOMEDEPT_ID`"
                    + " ORDER BY"
                    + " `ap`.`SHORT_NAME`"
                    + ", `p`.`LASTNAME`"
                    + ", `p`.`FIRSTNAME`"
                    + ", `p`.`MYID`";
            //System.out.println("Get Proxies Query:\n" + getProxiesQuery + "\n");
            final ResultSet getProxiesRs = getProxiesStat.executeQuery(getProxiesQuery);
            while (getProxiesRs.next()) {
                final HashMap<String, Object> data = new HashMap<String, Object>();
                data.put("ID", getProxiesRs.getString("ID"));
                data.put("FIRSTNAME", getProxiesRs.getString("FIRSTNAME"));
                data.put("LASTNAME", getProxiesRs.getString("LASTNAME"));
                data.put("MYID", getProxiesRs.getString("MYID"));
                data.put("PURPOSE_ID", getProxiesRs.getString("PURPOSE_ID"));
                data.put("TITLE", getProxiesRs.getString("TITLE"));
                data.put("CANNUM", getProxiesRs.getString("CANNUM"));
                data.put("EMAIL", getProxiesRs.getString("EMAIL"));
                data.put("PHONE", getProxiesRs.getString("PHONE"));
                data.put("SHORT_NAME", getProxiesRs.getString("SHORT_NAME"));
                data.put("DEPTNAME", getProxiesRs.getString("DEPTNAME"));
                data.put("DEPTNUM", getProxiesRs.getString("DEPTNUM"));
                controlManActiveForm.getProxies().add(data);
            }

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

    public static void DeactivateManager(ControlManActiveForm controlManActiveForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement deactivateLinksStat = conn.createStatement();
            final Statement deactivateAdminStat = conn.createStatement();
            
            commitStat.execute("START TRANSACTION");
            
            final String deactivateLinksCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE`"
                    + " SET"
                    + " `ACTIVE`='0'"
                    + " WHERE"
                    + " `CREATED_BY_CANNUM`='" + controlManActiveForm.getCannum() + "'"
                    + " OR"
                    + " `AFFILIATE_ADMIN_ID`='" + controlManActiveForm.getAdminId() + "'";
            //System.out.println("Deactivate admin links:\n" + deactivateLinksCmd + "\n");
            deactivateLinksStat.executeUpdate(deactivateLinksCmd);
            
            final String deactivateAdminCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN`"
                    + " SET"
                    + " `ACTIVE`='0'"
                    + " WHERE"
                    + " `ID`='" + controlManActiveForm.getAdminId() + "'";
            //System.out.println("Deactivate Admin:\n" + deactivateAdminCmd + "\n");
            deactivateAdminStat.executeUpdate(deactivateAdminCmd);

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

    public static void ActivateManager(ControlManActiveForm controlManActiveForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement activateAdminStat = conn.createStatement();
            
            commitStat.execute("START TRANSACTION");
            
            final String activateAdminCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN`"
                    + " SET"
                    + " `ACTIVE`='1'"
                    + " WHERE"
                    + " `ID`='" + controlManActiveForm.getAdminId() + "'";
            //System.out.println("Deactivate Admin:\n" + deactivateAdminCmd + "\n");
            activateAdminStat.executeUpdate(activateAdminCmd);

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
    
    public static void DeactivatePurpose(ControlManActiveForm controlManActiveForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement deactivateLinksStat = conn.createStatement();
            
            commitStat.execute("START TRANSACTION");
            
            final String deactivateLinksCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE`"
                    + " SET"
                    + " `ACTIVE`='0'"
                    + " WHERE"
                    + " `AFFILIATE_PURPOSE_ID`='" + controlManActiveForm.getTargPurpId() + "'"
                    + " AND ("
                    + " `AFFILIATE_ADMIN_ID`='" + controlManActiveForm.getAdminId() + "'"
                    + " OR `CREATED_BY_CANNUM`='" + controlManActiveForm.getCannum() + "'"
                    + " )";
            //System.out.println("Deactivate purpose admin link:\n" + deactivateLinksCmd + "\n");
            deactivateLinksStat.executeUpdate(deactivateLinksCmd);
            
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
    
    public static void DeactivateTargProxy(ControlManActiveForm controlManActiveForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement deactivateProxyStat = conn.createStatement();
            
            commitStat.execute("START TRANSACTION");
            
            final String deactivateProxyCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE`"
                    + " SET"
                    + " `ACTIVE`='0'"
                    + " WHERE"
                    + " `AFFILIATE_PURPOSE_ID`='" + controlManActiveForm.getTargPurpId() + "'"
                    + " AND `CREATED_BY_CANNUM`='" + controlManActiveForm.getCannum() + "'"
                    + " AND `AFFILIATE_ADMIN_ID`='" + controlManActiveForm.getTargProxyId() + "'";
            //System.out.println("Deactivate proxy link:\n" + deactivateProxyCmd + "\n");
            deactivateProxyStat.executeUpdate(deactivateProxyCmd);
            
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
    
    public static void AddPurpose(ControlManActiveForm controlManActiveForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement doesLinkExistStat = conn.createStatement();
            final Statement insertLinkStat = conn.createStatement();
            final Statement updateLinkStat = conn.createStatement();
            final Statement deactivateProxyStat = conn.createStatement();
            
            commitStat.execute("START TRANSACTION");
            
            final String doesLinkExistQuery = "SELECT"
                    + " 1"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE`"
                    + " WHERE"
                    + " `AFFILIATE_ADMIN_ID`='" + controlManActiveForm.getAdminId() + "'"
                    + " AND `AFFILIATE_PURPOSE_ID`='" + controlManActiveForm.getTargPurpId() + "'";
            final ResultSet doesLinkExistRs = doesLinkExistStat.executeQuery(doesLinkExistQuery);
            if(doesLinkExistRs.next()){
                final String updateLinkCmd = "UPDATE"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE`"
                        + " SET"
                        + " `ACTIVE`='1'"
                        + ", `PROXY`='0'"
                        + " WHERE"
                        + " `AFFILIATE_ADMIN_ID`='" + controlManActiveForm.getAdminId() + "'"
                        + " AND `AFFILIATE_PURPOSE_ID`='" + controlManActiveForm.getTargPurpId() + "'"
                        + " AND `CREATED_BY_CANNUM` IS NULL";
                //System.out.println("Update link cmd:\n" + updateLinkCmd + "\n");
                updateLinkStat.executeUpdate(updateLinkCmd);
            } else {
                final String insertLinkCmd = "INSERT INTO"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE`"
                        + " SET"
                        + " `AFFILIATE_ADMIN_ID`='" + controlManActiveForm.getAdminId() + "'"
                        + ", `AFFILIATE_PURPOSE_ID`='" + controlManActiveForm.getTargPurpId() + "'";
                //System.out.println("Insert link cmd:\n" + insertLinkCmd + "\n");
                insertLinkStat.executeUpdate(insertLinkCmd);
            }
            
            final String deactivateProxyCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE`"
                    + " SET"
                    + " `ACTIVE`='0'"
                    + " WHERE"
                    + " `AFFILIATE_ADMIN_ID`='" + controlManActiveForm.getAdminId() + "'"
                    + " AND `AFFILIATE_PURPOSE_ID`='" + controlManActiveForm.getTargPurpId() + "'"
                    + " AND ("
                    + " `PROXY`='1'"
                    + " OR `CREATED_BY_CANNUM` IS NOT NULL"
                    + " )";
            //System.out.println("Deactivate proxy occurrences:\n" + deactivateProxyCmd + "\n");
            deactivateProxyStat.executeUpdate(deactivateProxyCmd);
            
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
