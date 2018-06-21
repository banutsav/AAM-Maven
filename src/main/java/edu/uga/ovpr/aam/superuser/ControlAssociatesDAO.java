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
public class ControlAssociatesDAO {
    
    public static HashMap<String, Object> SearchPaula(ControlAssociatesForm controlAssociatesForm) throws Exception {
        HashMap<String, Object> results = new HashMap<String, Object>();
        ArrayList<Object> people = new ArrayList<Object>();
        Connection conn = null;
        
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement searchPaulaStat = conn.createStatement();
            Statement searchPaulaCountStat = conn.createStatement();

            String whereClause = "";
            if (!controlAssociatesForm.getLastName().isEmpty()) {
                if (controlAssociatesForm.getLastNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`LASTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlAssociatesForm.getLastName()) + "%'";
                } else if (controlAssociatesForm.getLastNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`LASTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlAssociatesForm.getLastName()) + "%'";
                } else if (controlAssociatesForm.getLastNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`LASTNAME`='" + GeneralDAO.CleanMySQLString(controlAssociatesForm.getLastName()) + "'";
                }
            }
            if (!controlAssociatesForm.getFirstName().isEmpty()) {
                if (controlAssociatesForm.getFirstNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`FIRSTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlAssociatesForm.getFirstName()) + "%'";
                } else if (controlAssociatesForm.getFirstNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`FIRSTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlAssociatesForm.getFirstName()) + "%'";
                } else if (controlAssociatesForm.getFirstNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`FIRSTNAME`='" + GeneralDAO.CleanMySQLString(controlAssociatesForm.getFirstName()) + "'";
                }
            }
            if (!controlAssociatesForm.getOvprId().isEmpty()) {
                if (controlAssociatesForm.getOvprIdOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`OVPRID` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlAssociatesForm.getOvprId()) + "%'";
                } else if (controlAssociatesForm.getOvprIdOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`OVPRID` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlAssociatesForm.getOvprId()) + "%'";
                } else if (controlAssociatesForm.getOvprIdOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`OVPRID`='" + GeneralDAO.CleanMySQLString(controlAssociatesForm.getOvprId()) + "'";
                }
            }
            if (!controlAssociatesForm.getPseudoCan().isEmpty()) {
                whereClause += " AND `a`.`PSEUDOCAN`='" + GeneralDAO.CleanMySQLString(controlAssociatesForm.getPseudoCan()) + "'";
            }
            if (!controlAssociatesForm.getJobTitle().isEmpty()) {
                if (controlAssociatesForm.getJobTitleOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`TITLE` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlAssociatesForm.getJobTitle()) + "%'";
                } else if (controlAssociatesForm.getJobTitleOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`TITLE` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlAssociatesForm.getJobTitle()) + "%'";
                } else if (controlAssociatesForm.getJobTitleOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`TITLE`='" + GeneralDAO.CleanMySQLString(controlAssociatesForm.getJobTitle()) + "'";
                }
            }
            if (!controlAssociatesForm.getDeptName().isEmpty()) {
                if (controlAssociatesForm.getDeptNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`DEPARTMENT` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlAssociatesForm.getDeptName()) + "%'";
                } else if (controlAssociatesForm.getDeptNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`DEPARTMENT` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlAssociatesForm.getDeptName()) + "%'";
                } else if (controlAssociatesForm.getDeptNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`DEPARTMENT`='" + GeneralDAO.CleanMySQLString(controlAssociatesForm.getDeptName()) + "'";
                }
            }
            if (!controlAssociatesForm.getOrgName().isEmpty()) {
                if (controlAssociatesForm.getOrgNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`ORGANIZATION` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlAssociatesForm.getOrgName()) + "%'";
                } else if (controlAssociatesForm.getOrgNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`ORGANIZATION` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlAssociatesForm.getOrgName()) + "%'";
                } else if (controlAssociatesForm.getOrgNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`ORGANIZATION`='" + GeneralDAO.CleanMySQLString(controlAssociatesForm.getOrgName()) + "'";
                }
            }
            if (controlAssociatesForm.getIncInactive().equals("No")) {
                whereClause += " AND `a`.`active`='1'";
            }

            whereClause += " AND `apl`.`AFFILIATE_PURPOSE_ID` = '" + Constants.associate_code + "' ";
            
            if (!whereClause.isEmpty()) {
                whereClause = whereClause.replaceFirst(" AND", " WHERE");
            }

            final String searchPaulaCountQuery = "SELECT"
                    + " COUNT(`a`.`ID`) AS `COUNT`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    + " left join `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`" 
                    + " on (`a`.`ID` = `apl`.`AFFILIATE_ID`) "
                    + whereClause;
            
            //System.out.println("Search Affiliates Count:\n" + searchPaulaCountQuery + "\n");
            
            ResultSet searchPaulaCountRs = searchPaulaCountStat.executeQuery(searchPaulaCountQuery);
            Integer searchPaulaCount = 0;
            if (searchPaulaCountRs.next()) {
                searchPaulaCount = searchPaulaCountRs.getInt("COUNT");
            }
            results.put("COUNT", searchPaulaCount);

            String orderBy = " ORDER BY `LASTNAME`,`FIRSTNAME`, `OVPRID`";

            Integer start = controlAssociatesForm.getPageNum() * 100;
            String limitClause = " LIMIT " + start + ", 100";

            final String searchPaulaQuery = "SELECT"
                    + " `a`.`ID` AS `ID`"
                    + ", `a`.`ACTIVE` AS `ACTIVE`"
                    + ", `a`.`FIRSTNAME` AS `FIRSTNAME`"
                    + ", `a`.`LASTNAME` AS `LASTNAME`"
                    + ", `a`.`OVPRID` AS `OVPRID`"
                    + ", `a`.`PSEUDOCAN` AS `PSEUDOCAN`"
                    + ", `a`.`TITLE` AS `TITLE`"
                    + ", `a`.`EMAIL` AS `EMAIL`"
                    + ", `a`.`PHONE` AS `PHONE`"
                    + ", `a`.`ORGANIZATION` AS `ORGANIZATION`"
                    + ", `a`.`DEPARTMENT` AS `DEPARTMENT`"
                    + ", `apl`.`EXPIRATION_DATE`"
                    + ", `apl`.`EXPIRES`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    
                    + " left join `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`" 
                    + " on (`a`.`ID` = `apl`.`AFFILIATE_ID`) "
                    
                    + whereClause
                    + orderBy
                    + limitClause;

            //System.out.println("Affiliates search:\n" + searchPaulaQuery + "\n");
            
            
            final ResultSet searchPaulaRs = searchPaulaStat.executeQuery(searchPaulaQuery);
            while (searchPaulaRs.next()) {
                HashMap<String, String> entry = new HashMap<String, String>();
                entry.put("ID", searchPaulaRs.getString("ID"));
                entry.put("ACTIVE", searchPaulaRs.getString("ACTIVE"));
                entry.put("FIRSTNAME", searchPaulaRs.getString("FIRSTNAME"));
                entry.put("LASTNAME", searchPaulaRs.getString("LASTNAME"));
                entry.put("OVPRID", searchPaulaRs.getString("OVPRID"));
                entry.put("PSEUDOCAN", searchPaulaRs.getString("PSEUDOCAN"));
                entry.put("TITLE", searchPaulaRs.getString("TITLE"));
                entry.put("EMAIL", searchPaulaRs.getString("EMAIL"));
                entry.put("PHONE", searchPaulaRs.getString("PHONE"));
                entry.put("ORGANIZATION", searchPaulaRs.getString("ORGANIZATION"));
                entry.put("DEPARTMENT", searchPaulaRs.getString("DEPARTMENT"));
                
                if("0".equals(searchPaulaRs.getString("EXPIRES")))
                 entry.put("EXPIRATION_DATE", "does not expire");               
                else    
                 entry.put("EXPIRATION_DATE", searchPaulaRs.getString("EXPIRATION_DATE").substring(0, 10));
                
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
