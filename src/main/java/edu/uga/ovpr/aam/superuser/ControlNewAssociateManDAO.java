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
public class ControlNewAssociateManDAO {

    public static HashMap<String, Object> SearchPaula(ControlNewAssociateManForm controlNewAssociateManForm) throws Exception {
        HashMap<String, Object> results = new HashMap<String, Object>();
        ArrayList<Object> people = new ArrayList<Object>();
        Connection conn = null;
        
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement searchPaulaStat = conn.createStatement();
            Statement searchPaulaCountStat = conn.createStatement();

            String whereClause = "";
            if (!controlNewAssociateManForm.getLastName().isEmpty()) {
                if (controlNewAssociateManForm.getLastNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`LASTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlNewAssociateManForm.getLastName()) + "%'";
                } else if (controlNewAssociateManForm.getLastNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`LASTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlNewAssociateManForm.getLastName()) + "%'";
                } else if (controlNewAssociateManForm.getLastNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`LASTNAME`='" + GeneralDAO.CleanMySQLString(controlNewAssociateManForm.getLastName()) + "'";
                }
            }
            if (!controlNewAssociateManForm.getFirstName().isEmpty()) {
                if (controlNewAssociateManForm.getFirstNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`FIRSTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlNewAssociateManForm.getFirstName()) + "%'";
                } else if (controlNewAssociateManForm.getFirstNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`FIRSTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlNewAssociateManForm.getFirstName()) + "%'";
                } else if (controlNewAssociateManForm.getFirstNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`FIRSTNAME`='" + GeneralDAO.CleanMySQLString(controlNewAssociateManForm.getFirstName()) + "'";
                }
            }
            if (!controlNewAssociateManForm.getMyId().isEmpty()) {
                if (controlNewAssociateManForm.getMyIdOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`MYID` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlNewAssociateManForm.getMyId()) + "%'";
                } else if (controlNewAssociateManForm.getMyIdOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`MYID` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlNewAssociateManForm.getMyId()) + "%'";
                } else if (controlNewAssociateManForm.getMyIdOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`MYID`='" + GeneralDAO.CleanMySQLString(controlNewAssociateManForm.getMyId()) + "'";
                }
            }
            if (!controlNewAssociateManForm.getCannum().isEmpty()) {
                whereClause += " AND `p`.`CANNUM`='" + GeneralDAO.CleanMySQLString(controlNewAssociateManForm.getCannum()) + "'";
            }
            if (controlNewAssociateManForm.getIncInactive().equals("No")) {
                whereClause += " AND `p`.`active`='1'";
            }

            whereClause = " WHERE `p`.`CANNUM` NOT LIKE '999%'" + whereClause;


            final String searchPaulaCountQuery = "SELECT"
                    + " COUNT(DISTINCT `p`.`ID`) AS `COUNT`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    +  whereClause + " AND `p`.`CANNUM` NOT IN (SELECT `aa`.`CANNUM` FROM `" + Constants.DB_PAUL + "`.`ASSOCIATE_ADMIN` `aa`)";
           
            //System.out.println("DEBUG: Search For New Manager Count:\n" + searchPaulaCountQuery + "\n");
            
            ResultSet searchPaulaCountRs = searchPaulaCountStat.executeQuery(searchPaulaCountQuery);
            Integer searchPaulaCount = 0;
            if (searchPaulaCountRs.next()) {
                searchPaulaCount = searchPaulaCountRs.getInt("COUNT");
            }
            results.put("COUNT", searchPaulaCount);

            String orderByClause = " ORDER BY `LASTNAME`,`FIRSTNAME`, `MYID`";

            Integer start = controlNewAssociateManForm.getPageNum() * 100;
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
                    + whereClause + " AND `p`.`CANNUM` NOT IN (SELECT `aa`.`CANNUM` FROM `" + Constants.DB_PAUL + "`.`ASSOCIATE_ADMIN` `aa`) "
                    + orderByClause
                    + limitClause;

            //System.out.println("DEBUG: Search for new manager:\n" + searchPaulaQuery + "\n");
            
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

    public static void AddNewManager(ControlNewAssociateManForm controlNewAssociateManForm, String cannum) throws Exception {
        
        System.out.println("DEBUG: created by cannum = " + cannum);
        
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
                    + " `" + Constants.DB_PAUL + "`.`ASSOCIATE_ADMIN` `aa`"
                    + " WHERE"
                    + " `aa`.`CANNUM`='" + controlNewAssociateManForm.getTargPersonCannum() + "'";
            final ResultSet doesAdminExistRs = doesAdminExistStat.executeQuery(doesAdminExistQuery);
            if (doesAdminExistRs.next()) {
                final String updateAdminCmd = "UPDATE"
                        + " `" + Constants.DB_PAUL + "`.`ASSOCIATE_ADMIN`"
                        + " SET"
                        + " `ACTIVE`='1'"
                        + " WHERE"
                        + " `CANNUM`='" + controlNewAssociateManForm.getTargPersonCannum() + "'";
                //System.out.println("Update admin:\n" + updateAdminCmd + "\n");
                updateAdminStat.executeUpdate(updateAdminCmd);
            } else {
                final String insertAdminCmd = "INSERT INTO"
                        + " `" + Constants.DB_PAUL + "`.`ASSOCIATE_ADMIN`"
                        + " SET"
                        + " `ACTIVE`='1'"
                        + ", `CANNUM`='" + controlNewAssociateManForm.getTargPersonCannum() + "'"
                        + ", `CREATED_BY_CANNUM`='" + cannum + "'";
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
