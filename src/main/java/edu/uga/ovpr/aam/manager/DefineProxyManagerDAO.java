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
 * @author programmer
 */
public class DefineProxyManagerDAO {

    public static void GetManagers(DefineProxyManagerForm defineProxyManagerForm) throws Exception {
        ArrayList<Object> dataArr = new ArrayList<Object>();
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");

            final Statement getManagerStat = conn.createStatement();
            final Statement getPurposeStat = conn.createStatement();

            final String getManagerQuery = "SELECT"
                    + " `p`.`FIRSTNAME`"
                    + ", `p`.`LASTNAME`"
                    + ", `p`.`MYID`"
                    + ", `p`.`CANNUM`"
                    + ", `p`.`ACTIVE`"
                    + ", `aa`.`ID`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " ON `aa`.`CANNUM`=`p`.`CANNUM`"
                    + " WHERE"
                    + " `ROLE_ID`='2'"
                    + " ORDER BY"
                    + " `LASTNAME`, `FIRSTNAME`, `MYID`, `CANNUM`";
            //System.out.println("Get Manager Query:\n" + getManagerQuery + "\n");
            final ResultSet getManagerRs = getManagerStat.executeQuery(getManagerQuery);
            while (getManagerRs.next()) {
                HashMap<String, Object> data = new HashMap<String, Object>();
                ArrayList<Object> purposes = new ArrayList<Object>();
                final String getPurposeQuery = "SELECT"
                        + " `ap`.`SHORT_NAME`"
                        + ", `ap`.`DESCRIPTION`"
                        + ", `aap`.`PROXY`"
                        + ", `aap`.`ACTIVE` AS `LINK_ACTIVE`"
                        + ", `ap`.`ACTIVE` AS `PURPOSE_ACTIVE`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                        + " ON `aap`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                        + " AND `ap`.`ACTIVE`='1'"
                        + " AND `ap`.`APPROVED`='Approved'"
                        + " WHERE"
                        + " `aap`.`AFFILIATE_ADMIN_ID`='" + GeneralDAO.CleanMySQLString(getManagerRs.getString("ID")) + "'"
                        + " ORDER BY"
                        + " `SHORT_NAME`";
                //System.out.println("Get Purpose Query:\n" + getPurposeQuery + "\n");
                final ResultSet getPurposeRs = getPurposeStat.executeQuery(getPurposeQuery);
                while (getPurposeRs.next()) {
                    HashMap<String, Object> purpose = new HashMap<String, Object>();
                    purpose.put("SHORT_NAME", getPurposeRs.getString("SHORT_NAME"));
                    purpose.put("DESCRIPTION", getPurposeRs.getString("DESCRIPTION"));
                    purpose.put("PROXY", getPurposeRs.getString("PROXY"));
                    purpose.put("LINK_ACTIVE", getPurposeRs.getString("LINK_ACTIVE"));
                    purpose.put("PURPOSE_ACTIVE", getPurposeRs.getString("PURPOSE_ACTIVE"));
                    purposes.add(purpose);
                }
                data.put("FIRSTNAME", getManagerRs.getString("FIRSTNAME"));
                data.put("LASTNAME", getManagerRs.getString("LASTNAME"));
                data.put("MYID", getManagerRs.getString("MYID"));
                data.put("CANNUM", getManagerRs.getString("CANNUM"));
                data.put("ACTIVE", getManagerRs.getString("ACTIVE"));
                data.put("ID", getManagerRs.getString("ID"));
                data.put("purposes", purposes);
                dataArr.add(data);
            }
            defineProxyManagerForm.setPurposes(dataArr);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void GetProxyPurposes(DefineProxyManagerForm defineProxyManagerForm, String cannum, String role) throws Exception {
        ArrayList<Object> dataArr = new ArrayList<Object>();
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");

            final Statement getManagerStat = conn.createStatement();
            final Statement getProxiesStat = conn.createStatement();
            final Statement getPurposeStat = conn.createStatement();

            String getPurposeQuery = "SELECT"
                    + " `ap`.`ID`"
                    + ", `ap`.`SHORT_NAME`"
                    + ", `ap`.`DESCRIPTION`"
                    + ", `ap`.`PERIOD`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " ON `aa`.`CANNUM`='" + cannum + "'"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                    + " ON `aap`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                    + " AND `aap`.`AFFILIATE_ADMIN_ID`=`aa`.`ID`"
                    + " WHERE"
                    + " `aap`.`PROXY`='0'"
                    + " AND `aap`.`ACTIVE`='1'"
                    + " AND `ap`.`APPROVED`='Approved'"
                    + " AND `ap`.`ACTIVE`='1'"
                    + " ORDER BY"
                    + " `SHORT_NAME`";
            //System.out.println("Get Purpose Query:\n" + getPurposeQuery + "\n");
            final ResultSet getPurposeRs = getManagerStat.executeQuery(getPurposeQuery);
            while (getPurposeRs.next()) {
                HashMap<String, Object> data = new HashMap<String, Object>();
                ArrayList<Object> managers = new ArrayList<Object>();
                final String getManagerQuery = "SELECT"
                        + " `aa`.`ID`"
                        + ", `p`.`FIRSTNAME`"
                        + ", `p`.`LASTNAME`"
                        + ", `p`.`MYID`"
                        + ", `p`.`CANNUM`"
                        + ", CASE WHEN `m`.`FIRSTNAME` IS NULL THEN ''"
                        + "  ELSE `m`.`FIRSTNAME` END AS `MANAGER_FIRSTNAME`"
                        + ", CASE WHEN `m`.`LASTNAME` IS NULL THEN ''"
                        + "  ELSE `m`.`LASTNAME` END AS `MANAGER_LASTNAME`"
                        + ", CASE WHEN `m`.`MYID` IS NULL THEN ''"
                        + "  ELSE `m`.`MYID` END AS `MANAGER_MYID`"
                        + ", CASE WHEN `m`.`CANNUM` IS NULL THEN ''"
                        + "  ELSE `m`.`CANNUM` END AS `MANAGER_CANNUM`"
                        + ", `aap`.`PROXY`"
                        + ", CASE"
                        + "  WHEN `aap`.`CREATED_BY_CANNUM`='" + cannum + "'"
                        + "  AND `aap`.`PROXY`='1' THEN '1'"
                        + "  ELSE '0' END AS `MANAGE_PERMISSION`"
                        + ", `p`.`ACTIVE` AS `PERSON_ACTIVE`"
                        + ", `aap`.`ACTIVE` AS `LINK_ACTIVE`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                        + " ON `ap`.`ID`='" + getPurposeRs.getString("ID") + "'"
                        + " AND `ap`.`ID`=`aap`.`AFFILIATE_PURPOSE_ID`"
                        + " AND `aap`.`ACTIVE`='1'"
                        + " AND `ap`.`APPROVED`='Approved'"
                        + " AND `ap`.`ACTIVE`='1'"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                        + " ON `aap`.`AFFILIATE_ADMIN_ID`=`aa`.`ID`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                        + " ON `p`.`CANNUM`=`aa`.`CANNUM`"
                        + " LEFT JOIN `" + Constants.DB_PAUL + "`.`PERSON` `m`"
                        + " ON `m`.`CANNUM`=`aap`.`CREATED_BY_CANNUM`"
                        + " WHERE"
                        + " `aap`.`PROXY`='0'"
                        + " ORDER BY"
                        + " `LASTNAME`, `FIRSTNAME`, `MYID`, `CANNUM`";
                //System.out.println("Get Manager Query:\n" + getManagerQuery + "\n");
                final ResultSet getManagerRs = getPurposeStat.executeQuery(getManagerQuery);
                while (getManagerRs.next()) {
                    HashMap<String, Object> manager = new HashMap<String, Object>();
                    manager.put("ID", getManagerRs.getString("ID"));
                    manager.put("FIRSTNAME", getManagerRs.getString("FIRSTNAME"));
                    manager.put("LASTNAME", getManagerRs.getString("LASTNAME"));
                    manager.put("MYID", getManagerRs.getString("MYID"));
                    manager.put("CANNUM", getManagerRs.getString("CANNUM"));
                    manager.put("MANAGER_FIRSTNAME", getManagerRs.getString("MANAGER_FIRSTNAME"));
                    manager.put("MANAGER_LASTNAME", getManagerRs.getString("MANAGER_LASTNAME"));
                    manager.put("MANAGER_MYID", getManagerRs.getString("MANAGER_MYID"));
                    manager.put("MANAGER_CANNUM", getManagerRs.getString("MANAGER_CANNUM"));
                    manager.put("PROXY", getManagerRs.getString("PROXY"));
                    manager.put("LINK_ACTIVE", getManagerRs.getString("MANAGE_PERMISSION"));
                    manager.put("MANAGE_PERMISSION", getManagerRs.getString("MANAGE_PERMISSION"));
                    manager.put("PERSON_ACTIVE", getManagerRs.getString("PERSON_ACTIVE"));
                    managers.add(manager);
                }

                ArrayList<Object> proxies = new ArrayList<Object>();
                final String getProxiesQuery = "SELECT"
                        + " `aa`.`ID`"
                        + ", `p`.`FIRSTNAME`"
                        + ", `p`.`LASTNAME`"
                        + ", `p`.`MYID`"
                        + ", `p`.`CANNUM`"
                        + ", CASE WHEN `m`.`FIRSTNAME` IS NULL THEN ''"
                        + "  ELSE `m`.`FIRSTNAME` END AS `MANAGER_FIRSTNAME`"
                        + ", CASE WHEN `m`.`LASTNAME` IS NULL THEN ''"
                        + "  ELSE `m`.`LASTNAME` END AS `MANAGER_LASTNAME`"
                        + ", CASE WHEN `m`.`MYID` IS NULL THEN ''"
                        + "  ELSE `m`.`MYID` END AS `MANAGER_MYID`"
                        + ", CASE WHEN `m`.`CANNUM` IS NULL THEN ''"
                        + "  ELSE `m`.`CANNUM` END AS `MANAGER_CANNUM`"
                        + ", `aap`.`PROXY`"
                        + ", CASE"
                        + "  WHEN `aap`.`CREATED_BY_CANNUM`='" + cannum + "'"
                        + "  AND `aap`.`PROXY`='1' THEN '1'"
                        + "  ELSE '0' END AS `MANAGE_PERMISSION`"
                        + ", `p`.`ACTIVE` AS `PERSON_ACTIVE`"
                        + ", `aap`.`ACTIVE` AS `LINK_ACTIVE`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                        + " ON `ap`.`ID`='" + getPurposeRs.getString("ID") + "'"
                        + " AND `ap`.`ID`=`aap`.`AFFILIATE_PURPOSE_ID`"
                        + " AND `aap`.`ACTIVE`='1'"
                        + " AND `ap`.`ACTIVE`='1'"
                        + " AND `ap`.`APPROVED`='Approved'"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                        + " ON `aap`.`AFFILIATE_ADMIN_ID`=`aa`.`ID`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                        + " ON `p`.`CANNUM`=`aa`.`CANNUM`"
                        + " LEFT JOIN `" + Constants.DB_PAUL + "`.`PERSON` `m`"
                        + " ON `m`.`CANNUM`=`aap`.`CREATED_BY_CANNUM`"
                        + " WHERE"
                        + " `aap`.`PROXY`='1'"
                        + " ORDER BY"
                        + " `LASTNAME`, `FIRSTNAME`, `MYID`, `CANNUM`";
                //System.out.println("Get Proxy Query:\n" + getProxiesQuery + "\n");
                final ResultSet getProxiesRs = getProxiesStat.executeQuery(getProxiesQuery);
                while (getProxiesRs.next()) {
                    HashMap<String, Object> proxy = new HashMap<String, Object>();
                    proxy.put("ID", getProxiesRs.getString("ID"));
                    proxy.put("FIRSTNAME", getProxiesRs.getString("FIRSTNAME"));
                    proxy.put("LASTNAME", getProxiesRs.getString("LASTNAME"));
                    proxy.put("MYID", getProxiesRs.getString("MYID"));
                    proxy.put("CANNUM", getProxiesRs.getString("CANNUM"));
                    proxy.put("MANAGER_FIRSTNAME", getProxiesRs.getString("MANAGER_FIRSTNAME"));
                    proxy.put("MANAGER_LASTNAME", getProxiesRs.getString("MANAGER_LASTNAME"));
                    proxy.put("MANAGER_MYID", getProxiesRs.getString("MANAGER_MYID"));
                    proxy.put("MANAGER_CANNUM", getProxiesRs.getString("MANAGER_CANNUM"));
                    proxy.put("PROXY", getProxiesRs.getString("PROXY"));
                    proxy.put("LINK_ACTIVE", getProxiesRs.getString("MANAGE_PERMISSION"));
                    proxy.put("MANAGE_PERMISSION", getProxiesRs.getString("MANAGE_PERMISSION"));
                    proxy.put("PERSON_ACTIVE", getProxiesRs.getString("PERSON_ACTIVE"));
                    proxies.add(proxy);
                }
                data.put("ID", getPurposeRs.getString("ID"));
                data.put("SHORT_NAME", getPurposeRs.getString("SHORT_NAME"));
                data.put("DESCRIPTION", getPurposeRs.getString("DESCRIPTION"));
                data.put("PERIOD", getPurposeRs.getString("PERIOD"));
                data.put("managers", managers);
                data.put("proxies", proxies);
                dataArr.add(data);
            }
            defineProxyManagerForm.setPurposes(dataArr);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void DeactivateProxy(DefineProxyManagerForm defineProxyManagerForm) throws Exception {
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement deactivateProxyStat = conn.createStatement();
            final String deactivateProxyCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE`"
                    + " SET"
                    + " `ACTIVE`='0'"
                    + " WHERE"
                    + " `AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(defineProxyManagerForm.getTargPurposeId()) + "'"
                    + " AND `AFFILIATE_ADMIN_ID`='" + GeneralDAO.CleanMySQLString(defineProxyManagerForm.getTargAdminId()) + "'";
            //System.out.println("Deactivate Proxy Cmd:\n" + deactivateProxyCmd + "\n");
            deactivateProxyStat.executeUpdate(deactivateProxyCmd);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
