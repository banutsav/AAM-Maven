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
public class ControlAffiliatesDAO {
    
    public static HashMap<String, Object> SearchPaula(ControlAffiliatesForm controlAffiliatesForm) throws Exception {
        HashMap<String, Object> results = new HashMap<String, Object>();
        ArrayList<Object> people = new ArrayList<Object>();
        Connection conn = null;
        
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement searchPaulaStat = conn.createStatement();
            Statement searchPaulaCountStat = conn.createStatement();

            String whereClause = "";
            if (!controlAffiliatesForm.getLastName().isEmpty()) {
                if (controlAffiliatesForm.getLastNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`LASTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlAffiliatesForm.getLastName()) + "%'";
                } else if (controlAffiliatesForm.getLastNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`LASTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlAffiliatesForm.getLastName()) + "%'";
                } else if (controlAffiliatesForm.getLastNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`LASTNAME`='" + GeneralDAO.CleanMySQLString(controlAffiliatesForm.getLastName()) + "'";
                }
            }
            if (!controlAffiliatesForm.getFirstName().isEmpty()) {
                if (controlAffiliatesForm.getFirstNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`FIRSTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlAffiliatesForm.getFirstName()) + "%'";
                } else if (controlAffiliatesForm.getFirstNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`FIRSTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlAffiliatesForm.getFirstName()) + "%'";
                } else if (controlAffiliatesForm.getFirstNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`FIRSTNAME`='" + GeneralDAO.CleanMySQLString(controlAffiliatesForm.getFirstName()) + "'";
                }
            }
            if (!controlAffiliatesForm.getOvprId().isEmpty()) {
                if (controlAffiliatesForm.getOvprIdOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`OVPRID` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlAffiliatesForm.getOvprId()) + "%'";
                } else if (controlAffiliatesForm.getOvprIdOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`OVPRID` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlAffiliatesForm.getOvprId()) + "%'";
                } else if (controlAffiliatesForm.getOvprIdOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`OVPRID`='" + GeneralDAO.CleanMySQLString(controlAffiliatesForm.getOvprId()) + "'";
                }
            }
            if (!controlAffiliatesForm.getPseudoCan().isEmpty()) {
                whereClause += " AND `a`.`PSEUDOCAN`='" + GeneralDAO.CleanMySQLString(controlAffiliatesForm.getPseudoCan()) + "'";
            }
            if (!controlAffiliatesForm.getJobTitle().isEmpty()) {
                if (controlAffiliatesForm.getJobTitleOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`TITLE` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlAffiliatesForm.getJobTitle()) + "%'";
                } else if (controlAffiliatesForm.getJobTitleOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`TITLE` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlAffiliatesForm.getJobTitle()) + "%'";
                } else if (controlAffiliatesForm.getJobTitleOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`TITLE`='" + GeneralDAO.CleanMySQLString(controlAffiliatesForm.getJobTitle()) + "'";
                }
            }
            if (!controlAffiliatesForm.getDeptName().isEmpty()) {
                if (controlAffiliatesForm.getDeptNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`DEPARTMENT` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlAffiliatesForm.getDeptName()) + "%'";
                } else if (controlAffiliatesForm.getDeptNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`DEPARTMENT` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlAffiliatesForm.getDeptName()) + "%'";
                } else if (controlAffiliatesForm.getDeptNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`DEPARTMENT`='" + GeneralDAO.CleanMySQLString(controlAffiliatesForm.getDeptName()) + "'";
                }
            }
            if (!controlAffiliatesForm.getOrgName().isEmpty()) {
                if (controlAffiliatesForm.getOrgNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`ORGANIZATION` LIKE '" + GeneralDAO.CleanMySQLStringForLike(controlAffiliatesForm.getOrgName()) + "%'";
                } else if (controlAffiliatesForm.getOrgNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`ORGANIZATION` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(controlAffiliatesForm.getOrgName()) + "%'";
                } else if (controlAffiliatesForm.getOrgNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`ORGANIZATION`='" + GeneralDAO.CleanMySQLString(controlAffiliatesForm.getOrgName()) + "'";
                }
            }
            if (controlAffiliatesForm.getIncInactive().equals("No")) {
                whereClause += " AND `a`.`active`='1'";
            }

            whereClause += " AND `apl`.`AFFILIATE_PURPOSE_ID` != '" + Constants.associate_code + "' ";
            
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

            Integer start = controlAffiliatesForm.getPageNum() * 100;
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
