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
public class AddAffManagerDAO {

    public static HashMap<String, Object> SearchPaula(AddAffManagerForm addAffManagerForm) throws Exception {
        HashMap<String, Object> results = new HashMap<String, Object>();
        ArrayList<Object> people = new ArrayList<Object>();
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement searchPaulaStat = conn.createStatement();
            Statement searchPaulaCountStat = conn.createStatement();

            String whereClause = "";
            if (!addAffManagerForm.getLastName().isEmpty()) {
                if (addAffManagerForm.getLastNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`LASTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(addAffManagerForm.getLastName()) + "%'";
                } else if (addAffManagerForm.getLastNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`LASTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(addAffManagerForm.getLastName()) + "%'";
                } else if (addAffManagerForm.getLastNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`LASTNAME`='" + GeneralDAO.CleanMySQLString(addAffManagerForm.getLastName()) + "'";
                }
            }
            if (!addAffManagerForm.getFirstName().isEmpty()) {
                if (addAffManagerForm.getFirstNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`FIRSTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(addAffManagerForm.getFirstName()) + "%'";
                } else if (addAffManagerForm.getFirstNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`FIRSTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(addAffManagerForm.getFirstName()) + "%'";
                } else if (addAffManagerForm.getFirstNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`FIRSTNAME`='" + GeneralDAO.CleanMySQLString(addAffManagerForm.getFirstName()) + "'";
                }
            }
            if (!addAffManagerForm.getMyId().isEmpty()) {
                if (addAffManagerForm.getMyIdOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`MYID` LIKE '" + GeneralDAO.CleanMySQLStringForLike(addAffManagerForm.getMyId()) + "%'";
                } else if (addAffManagerForm.getMyIdOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`MYID` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(addAffManagerForm.getMyId()) + "%'";
                } else if (addAffManagerForm.getMyIdOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`MYID`='" + GeneralDAO.CleanMySQLString(addAffManagerForm.getMyId()) + "'";
                }
            }
            if (!addAffManagerForm.getUgaId().isEmpty()) {
                whereClause += " AND `p`.`CANNUM`='" + GeneralDAO.CleanMySQLString(addAffManagerForm.getUgaId()) + "'";
            }
            if (!addAffManagerForm.getJobTitle().isEmpty()) {
                if (addAffManagerForm.getJobTitleOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`TITLE` LIKE '" + GeneralDAO.CleanMySQLStringForLike(addAffManagerForm.getJobTitle()) + "%'";
                } else if (addAffManagerForm.getJobTitleOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`TITLE` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(addAffManagerForm.getJobTitle()) + "%'";
                } else if (addAffManagerForm.getJobTitleOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`TITLE`='" + GeneralDAO.CleanMySQLString(addAffManagerForm.getJobTitle()) + "'";
                }
            }
            if (!addAffManagerForm.getHomeDeptNum().isEmpty()) {
                whereClause += " AND `d`.`DEPTNUM`='" + GeneralDAO.CleanMySQLString(addAffManagerForm.getHomeDeptNum()) + "'";
            }
            if (!addAffManagerForm.getHomeDeptName().isEmpty()) {
                if (addAffManagerForm.getHomeDeptNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `d`.`DEPTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(addAffManagerForm.getHomeDeptName()) + "%'";
                } else if (addAffManagerForm.getHomeDeptNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `d`.`DEPTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(addAffManagerForm.getHomeDeptName()) + "%'";
                } else if (addAffManagerForm.getHomeDeptNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `d`.`DEPTNAME`='" + GeneralDAO.CleanMySQLString(addAffManagerForm.getHomeDeptName()) + "'";
                }
            }
            if (addAffManagerForm.getIncAffiliates().equalsIgnoreCase("No")) {
                whereClause += " AND `p`.`CANNUM` NOT LIKE '999%'";
            }
            if (addAffManagerForm.getIncInactive().equals("No")) {
                whereClause += " AND `p`.`active`='1'";
            }

            whereClause = " WHERE"
                    + " `p`.`CANNUM`!='" + GeneralDAO.CleanMySQLString(addAffManagerForm.getManagerCannum()) + "'"
                    + " AND `aap`.`AFFILIATE_PURPOSE_ID` IS NULL"
                    + whereClause;

            final String searchPaulaCountQuery = "SELECT"
                    + " COUNT(`p`.`ID`) AS `COUNT`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`DEPT` `d`"
                    + " ON `p`.`HOMEDEPT_ID`=`d`.`ID`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " ON `aa`.`CANNUM`=`p`.`CANNUM`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                    + " ON `aap`.`AFFILIATE_ADMIN_ID`=`aa`.`ID`"
                    + " AND `aap`.`ACTIVE`='1'"
                    + " AND `aap`.`AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(addAffManagerForm.getPurposeId()) + "'"
                    + " AND `aap`.`CREATED_BY_CANNUM`='" + GeneralDAO.CleanMySQLString(addAffManagerForm.getManagerCannum()) + "'"
                    + whereClause;
            ResultSet searchPaulaCountRs = searchPaulaCountStat.executeQuery(searchPaulaCountQuery);
            Integer searchPaulaCount = 0;
            if (searchPaulaCountRs.next()) {
                searchPaulaCount = searchPaulaCountRs.getInt("COUNT");
            }
            results.put("COUNT", searchPaulaCount);

            String orderBy = " ORDER BY `LASTNAME`,`FIRSTNAME`, `MYID`";

            Integer start = addAffManagerForm.getPageNum() * 100;
            String limitClause = " LIMIT " + start + ", 100";

            final String searchPaulaQuery = "SELECT"
                    + " `p`.`ACTIVE` AS `ACTIVE`"
                    + ", `p`.`FIRSTNAME` AS `FIRSTNAME`"
                    + ", `p`.`LASTNAME` AS `LASTNAME`"
                    + ", `p`.`MYID` AS `MYID`"
                    + ", `p`.`CANNUM` AS `CANNUM`"
                    + ", `p`.`TITLE` AS `TITLE`"
                    + ", `p`.`EMAIL` AS `EMAIL`"
                    + ", `p`.`PHONE` AS `PHONE`"
                    + ", `p`.`ALTPHONE` AS `ALTPHONE`"
                    + ", `p`.`FAX` AS `FAX`"
                    + ", `d`.`DEPTNUM` AS `DEPTNUM`"
                    + ", `d`.`DEPTNAME` AS `DEPTNAME`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`DEPT` `d`"
                    + " ON `p`.`HOMEDEPT_ID`=`d`.`ID`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " ON `aa`.`CANNUM`=`p`.`CANNUM`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                    + " ON `aap`.`AFFILIATE_ADMIN_ID`=`aa`.`ID`"
                    + " AND `aap`.`ACTIVE`='1'"
                    + " AND `aap`.`AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(addAffManagerForm.getPurposeId()) + "'"
                    + " AND `aap`.`CREATED_BY_CANNUM`='" + GeneralDAO.CleanMySQLString(addAffManagerForm.getManagerCannum()) + "'"
                    + whereClause
                    + orderBy
                    + limitClause;

            //System.out.println("Paula search:\n" + searchPaulaQuery + "\n");
            final ResultSet searchPaulaRs = searchPaulaStat.executeQuery(searchPaulaQuery);
            while (searchPaulaRs.next()) {
                HashMap<String, String> entry = new HashMap<String, String>();
                entry.put("ACTIVE", searchPaulaRs.getString("ACTIVE"));
                entry.put("FIRSTNAME", searchPaulaRs.getString("FIRSTNAME"));
                entry.put("LASTNAME", searchPaulaRs.getString("LASTNAME"));
                entry.put("MYID", searchPaulaRs.getString("MYID"));
                entry.put("CANNUM", searchPaulaRs.getString("CANNUM"));
                entry.put("TITLE", searchPaulaRs.getString("TITLE"));
                entry.put("EMAIL", searchPaulaRs.getString("EMAIL"));
                entry.put("PHONE", searchPaulaRs.getString("PHONE"));
                entry.put("ALTPHONE", searchPaulaRs.getString("ALTPHONE"));
                entry.put("FAX", searchPaulaRs.getString("FAX"));
                entry.put("DEPTNUM", searchPaulaRs.getString("DEPTNUM"));
                entry.put("DEPTNAME", searchPaulaRs.getString("DEPTNAME"));
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

    public static void DefinePurposes(AddAffManagerForm addAffManagerForm, String cannum, String role) throws Exception {
        Connection conn = null;
        ArrayList<Object> results = new ArrayList<Object>();
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement defPurpsStat = conn.createStatement();

            String defPurpsQuery = "SELECT"
                    + " `ap`.`ID`"
                    + ", `ap`.`SHORT_NAME`"
                    + ", `ap`.`DESCRIPTION`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`";
            if (!role.equalsIgnoreCase("SuperUser")) {
                defPurpsQuery += " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                        + " ON `ap`.`ID`=`aap`.`AFFILIATE_PURPOSE_ID`"
                        + " AND `ap`.`ACTIVE`='1'"
                        + " AND `aap`.`ACTIVE`='1'"
                        + " AND `PROXY`='0'"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                        + " ON `aa`.`CANNUM`='" + cannum + "'"
                        + " AND `aa`.`ID`=`aap`.`AFFILIATE_ADMIN_ID`";
            }
            defPurpsQuery += " WHERE"
                    + " `ap`.`APPROVED`='Approved'";
            //defPurpsQuery += " WHERE"
            //        + " `ap`.`EXPIRATION_DATE` > NOW()";
            //System.out.println("Define Purposes Query:\n" + defPurpsQuery + "\n");
            final ResultSet defPurpsRs = defPurpsStat.executeQuery(defPurpsQuery);
            while (defPurpsRs.next()) {
                HashMap<String, Object> data = new HashMap<String, Object>();
                data.put("ID", defPurpsRs.getString("ID"));
                data.put("SHORT_NAME", defPurpsRs.getString("SHORT_NAME"));
                data.put("DESCRIPTION", defPurpsRs.getString("DESCRIPTION"));
                results.add(data);
            }

            addAffManagerForm.setPurposes(results);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static String AddAffManager(AddAffManagerForm addAffManagerForm, String cannum) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement findAdminStat = conn.createStatement();
            final Statement findAdminPurpLinkStat = conn.createStatement();
            final Statement insertAdminStat = conn.createStatement();
            final Statement insertAdminPurpLinkStat = conn.createStatement();
            final Statement updateAdminPurpLinkStat = conn.createStatement();
            final Statement getLastIdStat = conn.createStatement();
            final Statement getProxyNameStat = conn.createStatement();
            
            commitStat.execute("START TRANSACTION");

            final String findAdminQuery = "SELECT"
                    + " `ID`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN`"
                    + " WHERE"
                    + " `CANNUM`='" + addAffManagerForm.getManagerCannum() + "'";
            //System.out.println("Find Admin Query:\n" + findAdminQuery + "\n");
            final ResultSet findAdminRs = findAdminStat.executeQuery(findAdminQuery);
            String affAdminId = "";
            if(findAdminRs.next()){
                affAdminId = findAdminRs.getString("ID");
            } else {
                final String insertAdminCmd = "INSERT INTO"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN`"
                        + " SET"
                        + " `ACTIVE`='1'"
                        + ", `ROLE_ID`='2'"
                        + ", `CANNUM`='" + addAffManagerForm.getManagerCannum() + "'";
                //System.out.println("Insert Admin Cmd:\n" + insertAdminCmd + "\n");
                insertAdminStat.executeUpdate(insertAdminCmd);
                final String getLastIdQuery = "SELECT LAST_INSERT_ID() AS `LASTID`";
                //System.out.println("Select last insert id:\n" + getLastIdQuery + "\n");
                final ResultSet getLastIdRs = getLastIdStat.executeQuery(getLastIdQuery);
                if(getLastIdRs.next()){
                    affAdminId = getLastIdRs.getString("LASTID");
                } else {
                    commitStat.execute("ROLLBACK");
                    return "<b style='color:red;'>ERROR:</b> The query 'SELECT LAST_INSERT_ID() AS `LASTID`' did not generate results.";
                }
            }
            
            final String findAdminPurpLinkQuery = "SELECT"
                    + " `ACTIVE`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE`"
                    + " WHERE"
                    + " `AFFILIATE_ADMIN_ID`='" + affAdminId + "'"
                    + " AND `AFFILIATE_PURPOSE_ID`='" + addAffManagerForm.getPurposeId() + "'"
                    + " AND `CREATED_BY_CANNUM`='" + cannum + "'";
            //System.out.println("Find Admin Purpose Link Query:\n" + findAdminPurpLinkQuery + "\n");
            final ResultSet findAdminPurpLinkRs = findAdminPurpLinkStat.executeQuery(findAdminPurpLinkQuery);
            if(findAdminPurpLinkRs.next()){
                if(findAdminPurpLinkRs.getString("ACTIVE").equalsIgnoreCase("0")){
                    // Reactivate the linkage
                    final String updateAdminPurpLinkCmd = "UPDATE"
                            + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE`"
                            + " SET"
                            + " `ACTIVE`='1'"
                            + " WHERE"
                            + " `AFFILIATE_ADMIN_ID`='" + affAdminId + "'"
                            + " AND `AFFILIATE_PURPOSE_ID`='" + addAffManagerForm.getPurposeId() + "'"
                            + " AND `CREATED_BY_CANNUM`='" + cannum + "'";
                    //System.out.println("Update Affiliate Admin Purpose Link:\n" + updateAdminPurpLinkCmd + "\n");
                    updateAdminPurpLinkStat.executeUpdate(updateAdminPurpLinkCmd);
                } else {
                    final String getProxyNameQuery = "SELECT"
                            + " `FIRSTNAME`"
                            + ", `LASTNAME`"
                            + " FROM"
                            + " `" + Constants.DB_PAUL + "`.`PERSON`"
                            + " WHERE"
                            + " `CANNUM`='" + addAffManagerForm.getManagerCannum() + "'";
                    final ResultSet getProxyNameRs = getProxyNameStat.executeQuery(getProxyNameQuery);
                    String proxyName = "<Undefined>";
                    if(getProxyNameRs.next()){
                        proxyName = getProxyNameRs.getString("FIRSTNAME") + " " + getProxyNameRs.getString("LASTNAME");
                    }
                    
                    commitStat.execute("ROLLBACK");
                    return "<b style='color:red;'>ERROR:</b> " + proxyName + " is already defined as a proxy for you on purpose '" + addAffManagerForm.getPurposeShortName() + "'";
                }
            } else {
                final String insertAdminPurpLinkCmd = "INSERT INTO"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE`"
                        + " SET"
                        + " `ACTIVE`='1'"
                        + ", `AFFILIATE_ADMIN_ID`='" + affAdminId + "'"
                        + ", `AFFILIATE_PURPOSE_ID`='" + addAffManagerForm.getPurposeId() + "'"
                        + ", `CREATED_BY_CANNUM`='" + cannum + "'"
                        + ", `PROXY`='1'";
                //System.out.println("Insert Admin Purpose Link Cmd:\n" + insertAdminPurpLinkCmd + "\n");
                insertAdminPurpLinkStat.executeUpdate(insertAdminPurpLinkCmd);
            }

            final String getProxyNameQuery = "SELECT"
                    + " `FIRSTNAME`"
                    + ", `LASTNAME`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`PERSON`"
                    + " WHERE"
                    + " `CANNUM`='" + GeneralDAO.CleanMySQLString(addAffManagerForm.getManagerCannum()) + "'";
            //System.out.println("Get Proxy Name Query:\n" + getProxyNameQuery + "\n");
            final ResultSet getProxyNameRs = getProxyNameStat.executeQuery(getProxyNameQuery);
            if (getProxyNameRs.next()) {
                addAffManagerForm.setNewProxyManagerFullName(getProxyNameRs.getString("FIRSTNAME") + " " + getProxyNameRs.getString("LASTNAME"));
            } else {
                return "<b style='color: red;'>ERROR</b>: Unable to find a manager account with CANNUM '" + GeneralDAO.CleanMySQLString(addAffManagerForm.getManagerCannum()) + "'<br>";
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
