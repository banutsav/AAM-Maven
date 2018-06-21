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
public class ControlNewManDAO {

    public static HashMap<String, Object> SearchPaula(ControlNewManForm controlNewManForm) throws Exception {
        HashMap<String, Object> results = new HashMap<String, Object>();
        ArrayList<Object> people = new ArrayList<Object>();
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement searchPaulaStat = conn.createStatement();
            Statement searchPaulaCountStat = conn.createStatement();

            String whereClause = "";
            if (!controlNewManForm.getLastName().isEmpty()) {
                if (controlNewManForm.getLastNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`LASTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlNewManForm.getLastName()) + "%'";
                } else if (controlNewManForm.getLastNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`LASTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlNewManForm.getLastName()) + "%'";
                } else if (controlNewManForm.getLastNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`LASTNAME`='" + GeneralDAO.CleanMySQLString(controlNewManForm.getLastName()) + "'";
                }
            }
            if (!controlNewManForm.getFirstName().isEmpty()) {
                if (controlNewManForm.getFirstNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`FIRSTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlNewManForm.getFirstName()) + "%'";
                } else if (controlNewManForm.getFirstNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`FIRSTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlNewManForm.getFirstName()) + "%'";
                } else if (controlNewManForm.getFirstNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`FIRSTNAME`='" + GeneralDAO.CleanMySQLString(controlNewManForm.getFirstName()) + "'";
                }
            }
            if (!controlNewManForm.getMyId().isEmpty()) {
                if (controlNewManForm.getMyIdOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`MYID` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlNewManForm.getMyId()) + "%'";
                } else if (controlNewManForm.getMyIdOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`MYID` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlNewManForm.getMyId()) + "%'";
                } else if (controlNewManForm.getMyIdOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`MYID`='" + GeneralDAO.CleanMySQLString(controlNewManForm.getMyId()) + "'";
                }
            }
            if (!controlNewManForm.getCannum().isEmpty()) {
                whereClause += " AND `p`.`CANNUM`='" + GeneralDAO.CleanMySQLString(controlNewManForm.getCannum()) + "'";
            }
            if (controlNewManForm.getIncInactive().equals("No")) {
                whereClause += " AND `p`.`active`='1'";
            }

            whereClause = " WHERE `aa`.`ID` IS NULL AND `p`.`CANNUM` NOT LIKE '999%'" + whereClause;


            final String searchPaulaCountQuery = "SELECT"
                    + " COUNT(DISTINCT `p`.`ID`) AS `COUNT`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " ON `aa`.`CANNUM`=`p`.`CANNUM`"
                    + " AND `aa`.`ACTIVE`='1'"
                    + whereClause;
            //System.out.println("Search For New Manager Count:\n" + searchPaulaCountQuery + "\n");
            ResultSet searchPaulaCountRs = searchPaulaCountStat.executeQuery(searchPaulaCountQuery);
            Integer searchPaulaCount = 0;
            if (searchPaulaCountRs.next()) {
                searchPaulaCount = searchPaulaCountRs.getInt("COUNT");
            }
            results.put("COUNT", searchPaulaCount);

            String orderByClause = " ORDER BY `LASTNAME`,`FIRSTNAME`, `MYID`";

            Integer start = controlNewManForm.getPageNum() * 100;
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
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " ON `aa`.`CANNUM`=`p`.`CANNUM`"
                    + " AND `aa`.`ACTIVE`='1'"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`DEPT` `d`"
                    + " ON `p`.`HOMEDEPT_ID`=`d`.`ID`"
                    + whereClause
                    + orderByClause
                    + limitClause;

            //System.out.println("Search for new manager:\n" + searchPaulaQuery + "\n");
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

    public static void AddNewManager(ControlNewManForm controlNewManForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();

            final Statement doesAdminExistStat = conn.createStatement();
            final Statement updateAdminStat = conn.createStatement();
            final Statement insertAdminStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");
            
            final String doesAdminExistQuery = "SELECT"
                    + " '1'"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " WHERE"
                    + " `aa`.`CANNUM`='" + controlNewManForm.getTargPersonCannum() + "'";
            final ResultSet doesAdminExistRs = doesAdminExistStat.executeQuery(doesAdminExistQuery);
            if (doesAdminExistRs.next()) {
                final String updateAdminCmd = "UPDATE"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN`"
                        + " SET"
                        + " `ACTIVE`='1'"
                        + " WHERE"
                        + " `CANNUM`='" + controlNewManForm.getTargPersonCannum() + "'";
                //System.out.println("Update admin:\n" + updateAdminCmd + "\n");
                updateAdminStat.executeUpdate(updateAdminCmd);
            } else {
                final String insertAdminCmd = "INSERT INTO"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN`"
                        + " SET"
                        + " `ACTIVE`='1'"
                        + ", `CANNUM`='" + controlNewManForm.getTargPersonCannum() + "'"
                        + ", `ROLE_ID`='2'";
                //System.out.println("Insert new Admin:\n" + insertAdminCmd + "\n");
                insertAdminStat.executeUpdate(insertAdminCmd);
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
