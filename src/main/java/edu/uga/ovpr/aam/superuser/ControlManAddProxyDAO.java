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
public class ControlManAddProxyDAO {

    public static HashMap<String, Object> SearchPaula(ControlManAddProxyForm controlManAddProxyForm) throws Exception {
        HashMap<String, Object> results = new HashMap<String, Object>();
        ArrayList<Object> people = new ArrayList<Object>();
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement searchPaulaStat = conn.createStatement();
            Statement searchPaulaCountStat = conn.createStatement();

            String whereClause = "";
            if (!controlManAddProxyForm.getLastName().isEmpty()) {
                if (controlManAddProxyForm.getLastNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`LASTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlManAddProxyForm.getLastName()) + "%'";
                } else if (controlManAddProxyForm.getLastNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`LASTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlManAddProxyForm.getLastName()) + "%'";
                } else if (controlManAddProxyForm.getLastNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`LASTNAME`='" + GeneralDAO.CleanMySQLString(controlManAddProxyForm.getLastName()) + "'";
                }
            }
            if (!controlManAddProxyForm.getFirstName().isEmpty()) {
                if (controlManAddProxyForm.getFirstNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`FIRSTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlManAddProxyForm.getFirstName()) + "%'";
                } else if (controlManAddProxyForm.getFirstNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`FIRSTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlManAddProxyForm.getFirstName()) + "%'";
                } else if (controlManAddProxyForm.getFirstNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`FIRSTNAME`='" + GeneralDAO.CleanMySQLString(controlManAddProxyForm.getFirstName()) + "'";
                }
            }
            if (!controlManAddProxyForm.getMyId().isEmpty()) {
                if (controlManAddProxyForm.getMyIdOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`MYID` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlManAddProxyForm.getMyId()) + "%'";
                } else if (controlManAddProxyForm.getMyIdOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`MYID` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlManAddProxyForm.getMyId()) + "%'";
                } else if (controlManAddProxyForm.getMyIdOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`MYID`='" + GeneralDAO.CleanMySQLString(controlManAddProxyForm.getMyId()) + "'";
                }
            }
            if (!controlManAddProxyForm.getCannum().isEmpty()) {
                whereClause += " AND `p`.`CANNUM`='" + GeneralDAO.CleanMySQLString(controlManAddProxyForm.getCannum()) + "'";
            }
            if (controlManAddProxyForm.getIncInactive().equals("No")) {
                whereClause += " AND `p`.`active`='1'";
            }

            whereClause = " WHERE `p`.`CANNUM`!='" + controlManAddProxyForm.getAdminCannum() + "' AND `p`.`CANNUM` NOT LIKE '999%'" + whereClause;


            final String searchPaulaCountQuery = "SELECT"
                    + " COUNT(DISTINCT `p`.`ID`) AS `COUNT`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + whereClause;
            //System.out.println("Search For Proxy Count:\n" + searchPaulaCountQuery + "\n");
            ResultSet searchPaulaCountRs = searchPaulaCountStat.executeQuery(searchPaulaCountQuery);
            Integer searchPaulaCount = 0;
            if (searchPaulaCountRs.next()) {
                searchPaulaCount = searchPaulaCountRs.getInt("COUNT");
            }
            results.put("COUNT", searchPaulaCount);

            String orderByClause = " ORDER BY `LASTNAME`,`FIRSTNAME`, `MYID`";

            Integer start = controlManAddProxyForm.getPageNum() * 100;
            String limitClause = " LIMIT " + start + ", 100";

            final String searchPaulaQuery = "SELECT DISTINCT"
                    + " `p`.`ID`"
                    + ", `p`.`ACTIVE` AS `ACTIVE`"
                    + ", `p`.`FIRSTNAME` AS `FIRSTNAME`"
                    + ", `p`.`LASTNAME` AS `LASTNAME`"
                    + ", `p`.`MYID` AS `MYID`"
                    + ", `p`.`CANNUM` AS `CANNUM`"
                    + ", `p`.`TITLE` AS `TITLE`"
                    + ", `p`.`EMAIL` AS `EMAIL`"
                    + ", `p`.`PHONE` AS `PHONE`"
                    + ", `d`.`DEPTNAME` AS `DEPTNAME`"
                    + ", `d`.`DEPTNUM` AS `DEPTNUM`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`DEPT` `d`"
                    + " ON `p`.`HOMEDEPT_ID`=`d`.`ID`"
                    + whereClause
                    + orderByClause
                    + limitClause;

            //System.out.println("Search for proxy:\n" + searchPaulaQuery + "\n");
            final ResultSet searchPaulaRs = searchPaulaStat.executeQuery(searchPaulaQuery);
            while (searchPaulaRs.next()) {
                HashMap<String, String> entry = new HashMap<String, String>();
                entry.put("ID", searchPaulaRs.getString("ID"));
                entry.put("ACTIVE", searchPaulaRs.getString("ACTIVE"));
                entry.put("FIRSTNAME", searchPaulaRs.getString("FIRSTNAME"));
                entry.put("LASTNAME", searchPaulaRs.getString("LASTNAME"));
                entry.put("MYID", searchPaulaRs.getString("MYID"));
                entry.put("CANNUM", searchPaulaRs.getString("CANNUM"));
                entry.put("TITLE", searchPaulaRs.getString("TITLE"));
                entry.put("EMAIL", searchPaulaRs.getString("EMAIL"));
                entry.put("PHONE", searchPaulaRs.getString("PHONE"));
                entry.put("DEPTNAME", searchPaulaRs.getString("DEPTNAME"));
                entry.put("DEPTNUM", searchPaulaRs.getString("DEPTNUM"));
                people.add(entry);
            }

            results.put("people", people);
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

    public static void InitializeData(ControlManAddProxyForm controlManAddProxyForm) throws Exception {
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getBasicManDataStat = conn.createStatement();
            final String getBasicManDataQuery = "SELECT"
                    + " `p`.`FIRSTNAME`"
                    + ", `p`.`LASTNAME`"
                    + ", `p`.`CANNUM`"
                    + ", `p`.`MYID`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " ON `aa`.`ID`='" + controlManAddProxyForm.getAdminId() + "'"
                    + " AND `aa`.`CANNUM`=`p`.`CANNUM`";
            //System.out.println("Initialize data query:\n" + getBasicManDataQuery + "\n");
            final ResultSet getBasicManDataRs = getBasicManDataStat.executeQuery(getBasicManDataQuery);
            if (getBasicManDataRs.next()) {
                controlManAddProxyForm.setAdminFirstName(getBasicManDataRs.getString("FIRSTNAME"));
                controlManAddProxyForm.setAdminLastName(getBasicManDataRs.getString("LASTNAME"));
                controlManAddProxyForm.setAdminCannum(getBasicManDataRs.getString("CANNUM"));
                controlManAddProxyForm.setAdminMyID(getBasicManDataRs.getString("MYID"));
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

    public static void GetPurposes(ControlManAddProxyForm controlManAddProxyForm) throws Exception {
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getPurposesStat = conn.createStatement();

            controlManAddProxyForm.getPurposes().clear();

            final String getPurposesQuery = "SELECT"
                    + " `ap`.`ID`"
                    + ", `ap`.`SHORT_NAME`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                    + " ON `ap`.`ID`=`aap`.`AFFILIATE_PURPOSE_ID`"
                    + " AND `aap`.`AFFILIATE_ADMIN_ID`='" + controlManAddProxyForm.getAdminId() + "'"
                    + " AND `aap`.`ACTIVE`='1'"
                    + " AND `ap`.`ACTIVE`='1'"
                    + " AND `ap`.`APPROVED`='Approved'"
                    + " AND `aap`.`PROXY`='0'"
                    + " AND `aap`.`CREATED_BY_CANNUM` IS NULL"
                    + " ORDER BY"
                    + " `SHORT_NAME`";
            //System.out.println("Get manager purposes query:\n" + getPurposesQuery + "\n");
            final ResultSet getPurposesRs = getPurposesStat.executeQuery(getPurposesQuery);
            while (getPurposesRs.next()) {
                HashMap<String, Object> data = new HashMap<String, Object>();
                data.put("ID", getPurposesRs.getString("ID"));
                data.put("SHORT_NAME", getPurposesRs.getString("SHORT_NAME"));
                controlManAddProxyForm.getPurposes().add(data);
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

    public static String AddProxy(ControlManAddProxyForm controlManAddProxyForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        String result = "";
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement findProxyAdminStat = conn.createStatement();
            final Statement insertProxyAdminStat = conn.createStatement();
            final Statement updateProxyAdminStat = conn.createStatement();
            final Statement getLastProxyAdminIdStat = conn.createStatement();
            final Statement isManagerStat = conn.createStatement();
            final Statement findLinkStat = conn.createStatement();
            final Statement updateLinkStat = conn.createStatement();
            final Statement insertLinkStat = conn.createStatement();

            final String isManagerQuery = "SELECT"
                    + " `p`.`FIRSTNAME`"
                    + ", `p`.`LASTNAME`"
                    + ", `p`.`MYID`"
                    + ", `ap`.`SHORT_NAME`"
                    + ", `aap`.`PROXY`"
                    + ", `aap`.`CREATED_BY_CANNUM`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                    + " ON `aa`.`ID`=`aap`.`AFFILIATE_ADMIN_ID`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " ON `aap`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                    + " AND `ap`.`ACTIVE`='1'"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " ON `p`.`CANNUM`=`aa`.`CANNUM`"
                    + " WHERE"
                    + " `aa`.`CANNUM`='" + controlManAddProxyForm.getTargPersonCannum() + "'"
                    + " AND `aa`.`ACTIVE`='1'"
                    + " AND `aap`.`AFFILIATE_PURPOSE_ID`='" + controlManAddProxyForm.getTargPurpId() + "'"
                    + " AND `aap`.`ACTIVE`='1'";
            //System.out.println("IS Manager Query:\n" + isManagerQuery + "\n");
            final ResultSet isManagerRs = isManagerStat.executeQuery(isManagerQuery);
            while (isManagerRs.next()) {
                if (isManagerRs.getString("PROXY").equalsIgnoreCase("0")) {
                    return "<b>" + isManagerRs.getString("FIRSTNAME") + " " + isManagerRs.getString("LASTNAME") + "</b> (" + isManagerRs.getString("MYID") + ") is currently an active manager for purpose <b>" + isManagerRs.getString("SHORT_NAME") + "</b>";
                } else if(isManagerRs.getString("CREATED_BY_CANNUM").equalsIgnoreCase(controlManAddProxyForm.getAdminCannum())){
                    return "<b>" + isManagerRs.getString("FIRSTNAME") + " " + isManagerRs.getString("LASTNAME") + "</b> (" + isManagerRs.getString("MYID") + ") is currently a proxy manager for <b>" + controlManAddProxyForm.getAdminFirstName() + " " + controlManAddProxyForm.getAdminLastName() + "</b> (" + controlManAddProxyForm.getAdminMyID() + ") for purpose <b>" + isManagerRs.getString("SHORT_NAME") + "</b>";
                }
            }

            String proxyAdminId = "";

            commitStat.execute("START TRANSACTION");

            final String findProxyAdminQuery = "SELECT"
                    + " `ID`"
                    + ", `ACTIVE`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN`"
                    + " WHERE"
                    + " `CANNUM`='" + controlManAddProxyForm.getTargPersonCannum() + "'";
            //System.out.println("Find Proxy Admin Query:\n" + findProxyAdminQuery + "\n");
            final ResultSet findProxyAdminRs = findProxyAdminStat.executeQuery(findProxyAdminQuery);
            if (findProxyAdminRs.next()) {
                proxyAdminId = findProxyAdminRs.getString("ID");
                if (findProxyAdminRs.getString("ACTIVE").equalsIgnoreCase("0")) {
                    final String updateProxyAdminCmd = "UPDATE"
                            + " `" + Constants.DB_PAUL + "`.`AFFILAITE_ADMIN`"
                            + " SET"
                            + " `ACTIVE`='1'"
                            + " WHERE"
                            + " `ID`='" + proxyAdminId + "'";
                    //System.out.println("Update Proxy Admin Cmd:\n" + updateProxyAdminCmd + "\n");
                    updateProxyAdminStat.executeUpdate(updateProxyAdminCmd);
                }
            } else {
                final String insertProxyAdminCmd = "INSERT INTO"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN`"
                        + " SET"
                        + " `ACTIVE`='1'"
                        + ", `CANNUM`='" + controlManAddProxyForm.getTargPersonCannum() + "'"
                        + ", `ROLE_ID`='2'";
                insertProxyAdminStat.executeUpdate(insertProxyAdminCmd);
                final String getLastProxyAdminIdQuery = "SELECT LAST_INSERT_ID() AS `ID`";
                final ResultSet getLastProxyAdminIdRs = getLastProxyAdminIdStat.executeQuery(getLastProxyAdminIdQuery);
                if (getLastProxyAdminIdRs.next()) {
                    proxyAdminId = getLastProxyAdminIdRs.getString("ID");
                }
            }


            final String findLinkQuery = "SELECT"
                    + " `ACTIVE`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE`"
                    + " WHERE"
                    + " `AFFILIATE_ADMIN_ID`='" + proxyAdminId + "'"
                    + " AND `AFFILIATE_PURPOSE_ID`='" + controlManAddProxyForm.getTargPurpId() + "'"
                    + " AND `CREATED_BY_CANNUM`='" + controlManAddProxyForm.getAdminCannum() + "'";
            //System.out.println("Find Link Query:\n" + findLinkQuery + "\n");
            final ResultSet findLinkRs = findLinkStat.executeQuery(findLinkQuery);
            if (findLinkRs.next()) {
                if (findLinkRs.getString("ACTIVE").equalsIgnoreCase("0")) {
                    final String updateLinkCmd = "UPDATE"
                            + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE`"
                            + " SET "
                            + " `ACTIVE`='1'"
                            + ", `PROXY`='1'"
                            + " WHERE"
                            + " `AFFILIATE_ADMIN_ID`='" + proxyAdminId + "'"
                            + " AND `AFFILIATE_PURPOSE_ID`='" + controlManAddProxyForm.getTargPurpId() + "'"
                            + " AND `CREATED_BY_CANNUM`='" + controlManAddProxyForm.getAdminCannum() + "'";
                    //System.out.println("Update proxy link cmd:\n" + updateLinkCmd + "\n");
                    updateLinkStat.executeUpdate(updateLinkCmd);
                }
            } else {
                final String insertLinkCmd = "INSERT INTO"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE`"
                        + " SET"
                        + " `AFFILIATE_ADMIN_ID`='" + proxyAdminId + "'"
                        + ", `AFFILIATE_PURPOSE_ID`='" + controlManAddProxyForm.getTargPurpId() + "'"
                        + ", `CREATED_BY_CANNUM`='" + controlManAddProxyForm.getAdminCannum() + "'"
                        + ", `PROXY`='1'";
                //System.out.println("Insert new proxy cmd:\n" + insertLinkCmd + "\n");
                insertLinkStat.executeUpdate(insertLinkCmd);
            }

            commitStat.execute("COMMIT");

            return result;
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
