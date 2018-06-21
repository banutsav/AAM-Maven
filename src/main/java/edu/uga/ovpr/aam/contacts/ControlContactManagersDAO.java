/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.contacts;

import edu.uga.ovpr.aam.superuser.*;
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
 * @author utsavb
 */
public class ControlContactManagersDAO {
    
    public static HashMap<String, Object> SearchPaula(ControlContactManagersForm controlContactManagersForm) throws Exception {
        HashMap<String, Object> results = new HashMap<String, Object>();
        ArrayList<Object> people = new ArrayList<Object>();
        Connection conn = null;
        
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement searchPaulaStat = conn.createStatement();
            Statement searchPaulaCountStat = conn.createStatement();

            String whereClause = "";
            if (!controlContactManagersForm.getLastName().isEmpty()) {
                if (controlContactManagersForm.getLastNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`LASTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlContactManagersForm.getLastName()) + "%'";
                } else if (controlContactManagersForm.getLastNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`LASTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlContactManagersForm.getLastName()) + "%'";
                } else if (controlContactManagersForm.getLastNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`LASTNAME`='" + GeneralDAO.CleanMySQLString(controlContactManagersForm.getLastName()) + "'";
                }
            }
            if (!controlContactManagersForm.getFirstName().isEmpty()) {
                if (controlContactManagersForm.getFirstNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`FIRSTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlContactManagersForm.getFirstName()) + "%'";
                } else if (controlContactManagersForm.getFirstNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`FIRSTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlContactManagersForm.getFirstName()) + "%'";
                } else if (controlContactManagersForm.getFirstNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`FIRSTNAME`='" + GeneralDAO.CleanMySQLString(controlContactManagersForm.getFirstName()) + "'";
                }
            }
            if (!controlContactManagersForm.getMyId().isEmpty()) {
                if (controlContactManagersForm.getMyIdOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `p`.`MYID` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlContactManagersForm.getMyId()) + "%'";
                } else if (controlContactManagersForm.getMyIdOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `p`.`MYID` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlContactManagersForm.getMyId()) + "%'";
                } else if (controlContactManagersForm.getMyIdOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `p`.`MYID`='" + GeneralDAO.CleanMySQLString(controlContactManagersForm.getMyId()) + "'";
                }
            }
            if (!controlContactManagersForm.getCannum().isEmpty()) {
                whereClause += " AND `p`.`CANNUM`='" + GeneralDAO.CleanMySQLString(controlContactManagersForm.getCannum()) + "'";
            }
            if (controlContactManagersForm.getIncInactive().equals("No")) {
                whereClause += " AND `p`.`active`='1' ";
            }

            if (!whereClause.isEmpty()) {
                whereClause = whereClause.replaceFirst(" AND", " WHERE");
            }

            final String searchPaulaCountQuery = "SELECT"
                    + " COUNT(DISTINCT `aa`.`CANNUM`) AS `COUNT`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`CONTACT_ADMIN` `aa`"
                    + " ON `aa`.`CANNUM`=`p`.`CANNUM`"
                    //+ " WHERE `aa`.`ACTIVE` = '1'"
                    + whereClause;
           
            //System.out.println("\ncount query = \n" + searchPaulaCountQuery);
            
            ResultSet searchPaulaCountRs = searchPaulaCountStat.executeQuery(searchPaulaCountQuery);
            Integer searchPaulaCount = 0;
            if (searchPaulaCountRs.next()) {
                searchPaulaCount = searchPaulaCountRs.getInt("COUNT");
            }
            results.put("COUNT", searchPaulaCount);

            String orderBy = " ORDER BY `CANNUM`,`LASTNAME`,`FIRSTNAME`, `MYID`";

            Integer start = controlContactManagersForm.getPageNum() * 100;
            String limitClause = " LIMIT " + start + ", 100";

            final String searchPaulaQuery = "SELECT DISTINCT"
                    + " `aa`.`CANNUM` AS `ID`"
                    + ", `p`.`ACTIVE` AS `PERSON_ACTIVE`"
                    + ", `aa`.`ACTIVE` AS `ADMIN_ACTIVE`"
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
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`CONTACT_ADMIN` `aa`"
                    + " ON `aa`.`CANNUM`=`p`.`CANNUM`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`DEPT` `d`"
                    + " ON `p`.`HOMEDEPT_ID`=`d`.`ID`"
                    //+ "WHERE `aa`.`ACTIVE` = '1'"
                    + whereClause
                    + orderBy
                    + limitClause;

            //System.out.println("\nContact Managers search:\n" + searchPaulaQuery + "\n");
           
            final ResultSet searchPaulaRs = searchPaulaStat.executeQuery(searchPaulaQuery);
            while (searchPaulaRs.next()) {
                HashMap<String, String> entry = new HashMap<String, String>();
                entry.put("ID", searchPaulaRs.getString("ID"));
                entry.put("ACTIVE", searchPaulaRs.getString("PERSON_ACTIVE"));
                entry.put("ADMIN_ACTIVE", searchPaulaRs.getString("ADMIN_ACTIVE"));
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
}
