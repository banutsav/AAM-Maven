/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.contacts;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Static;
import edu.uga.ovpr.aam.information.GeneralDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author submyers
 */
public class ManageContactsDAO {
    
    public static HashMap<String, Object> SearchContacts(ManageContactsForm c_form) throws Exception {
        HashMap<String, Object> results = new HashMap<String, Object>();
        ArrayList<Object> people = new ArrayList<Object>();
        Connection conn = null;
        
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement searchPaulaStat = conn.createStatement();
            Statement searchPaulaCountStat = conn.createStatement();
            
            String whereClause = " WHERE `a`.`PERSON_TYPE` = '1'";
            
            if (!c_form.getLastName().isEmpty()) {
                if (c_form.getLastNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`LASTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(c_form.getLastName()) + "%'";
                } else if (c_form.getLastNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`LASTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(c_form.getLastName()) + "%'";
                } else if (c_form.getLastNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`LASTNAME`='" + GeneralDAO.CleanMySQLString(c_form.getLastName()) + "'";
                }
            }
            if (!c_form.getFirstName().isEmpty()) {
                if (c_form.getFirstNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`FIRSTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(c_form.getFirstName()) + "%'";
                } else if (c_form.getFirstNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`FIRSTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(c_form.getFirstName()) + "%'";
                } else if (c_form.getFirstNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`FIRSTNAME`='" + GeneralDAO.CleanMySQLString(c_form.getFirstName()) + "'";
                }
            }
            
            if (!c_form.getOrg().isEmpty()) {
                if (c_form.getOrgOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`ORGANIZATION` LIKE '" + GeneralDAO.CleanMySQLStringForLike(c_form.getOrg()) + "%'";
                } else if (c_form.getOrgOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`ORGANIZATION` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(c_form.getOrg()) + "%'";
                } else if (c_form.getOrgOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`ORGANIZATION`='" + GeneralDAO.CleanMySQLString(c_form.getOrg()) + "'";
                }
            }
            
            if (c_form.getShowInactive().equalsIgnoreCase("No")) {
                whereClause += " AND `a`.`ACTIVE`='1' ";
            }
            
            // search PAULA
            final String searchPaulaCountQuery = "SELECT"
                    + " COUNT(`a`.`ID`) AS `COUNT`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a` "
                    + whereClause
                    ;
            
            //System.out.println("Search PAULA Count:\n" + searchPaulaCountQuery + "\n");
            
            
            ResultSet searchPaulaCountRs = searchPaulaCountStat.executeQuery(searchPaulaCountQuery);
            Integer searchPaulaCount = 0;
            if (searchPaulaCountRs.next()) {
                searchPaulaCount = searchPaulaCountRs.getInt("COUNT");
            }
            
            results.put("COUNT", searchPaulaCount);
            
            String orderBy = " ORDER BY `LASTNAME`,`FIRSTNAME`";

            Integer start = c_form.getPageNum() * 100;
            String limitClause = " LIMIT " + start + ", 100";

            
            
            // get contacts people
            final String searchPaulaQuery = "SELECT"
                    + " `a`.`FIRSTNAME` AS `FIRSTNAME`"
                    + ", `a`.`LASTNAME` AS `LASTNAME`"
                    + ", `a`.`PSEUDOCAN` AS `CONTACT_UGAID`"
                    + ", `a`.`UGAID_PRIME` AS `CANNUM`"
                    + ", `a`.`TITLE` AS `TITLE`"
                    + ", `a`.`EMAIL` AS `EMAIL`"
                    + ", `a`.`OVPRID` AS `MYID`"
                    + ", `a`.`ORGANIZATION` AS `ORGANIZATION`"
                    + ", `a`.`PHONE` AS `PHONE`"
                    + ", `a`.`ACTIVE` AS `ACTIVE`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    
                    + whereClause
                    + orderBy
                    + limitClause;

            //System.out.println("\nContacts search:\n" + searchPaulaQuery + "\n");
            
            
            final ResultSet searchPaulaRs = searchPaulaStat.executeQuery(searchPaulaQuery);
            
            while (searchPaulaRs.next()) {
                HashMap<String, String> entry = new HashMap<String, String>();
                entry.put("FIRSTNAME", searchPaulaRs.getString("FIRSTNAME"));
                entry.put("LASTNAME", searchPaulaRs.getString("LASTNAME"));
                entry.put("MYID", searchPaulaRs.getString("MYID"));
                entry.put("CANNUM", searchPaulaRs.getString("CANNUM"));
                entry.put("CONTACT_UGAID", searchPaulaRs.getString("CONTACT_UGAID"));
                entry.put("PHONE", searchPaulaRs.getString("PHONE"));
                entry.put("TITLE", searchPaulaRs.getString("TITLE"));
                entry.put("EMAIL", searchPaulaRs.getString("EMAIL"));
                entry.put("ACTIVE", searchPaulaRs.getString("ACTIVE"));
                entry.put("ORGANIZATION", searchPaulaRs.getString("ORGANIZATION"));
                
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
   
    public static HashMap<String, String> getPersonRecord(String cannum) throws Exception {
        Connection conn = null;
        
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement searchPaulaStat = conn.createStatement();
            
            // get PAULA people
            final String searchPaulaQuery = "SELECT"
                    + " `a`.`FIRSTNAME` AS `FIRSTNAME`"
                    + ", `a`.`LASTNAME` AS `LASTNAME`"
                    + ", `a`.`UGAID_PRIME` AS `UGAID_PRIME`"
                    + ", `a`.`TITLE` AS `TITLE`"
                    + ", `a`.`ACTIVE` AS `ACTIVE`"
                    + ", `a`.`EMAIL` AS `EMAIL`"
                    + ", `a`.`PHONE` AS `PHONE`"
                    + ", `a`.`ADDR1` AS `ADDR1`"
                    + ", `a`.`ADDR2` AS `ADDR2`"
                    + ", `a`.`CITY` AS `CITY`"
                    + ", `a`.`STATE` AS `STATE`"
                    + ", `a`.`CELLPHONE` AS `CELLPHONE`"
                    + ", `a`.`FAX` AS `FAX`"
                    + ", `a`.`ZIP` AS `ZIP`"
                    + ", `a`.`ORGANIZATION` AS `ORGANIZATION`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a` where `a`.`PSEUDOCAN` = '" + cannum + "'";
            
            //System.out.println("Contact search:\n" + searchPaulaQuery + "\n");
            
            HashMap<String, String> entry = new HashMap<String, String>();
            final ResultSet searchPaulaRs = searchPaulaStat.executeQuery(searchPaulaQuery);
            
            while (searchPaulaRs.next()) {
                entry.put("FIRSTNAME", searchPaulaRs.getString("FIRSTNAME"));
                entry.put("LASTNAME", searchPaulaRs.getString("LASTNAME"));
                entry.put("EMAIL", searchPaulaRs.getString("EMAIL"));
                entry.put("PHONE", searchPaulaRs.getString("PHONE"));
                entry.put("TITLE", searchPaulaRs.getString("TITLE"));
                entry.put("ADDR1", searchPaulaRs.getString("ADDR1"));
                entry.put("ADDR2", searchPaulaRs.getString("ADDR2"));
                entry.put("CITY", searchPaulaRs.getString("CITY"));
                entry.put("STATE", searchPaulaRs.getString("STATE"));
                entry.put("CELLPHONE", searchPaulaRs.getString("CELLPHONE"));
                entry.put("FAX", searchPaulaRs.getString("FAX"));
                entry.put("ZIP", searchPaulaRs.getString("ZIP"));
                entry.put("ACTIVE", searchPaulaRs.getString("ACTIVE"));
                entry.put("UGAID_PRIME", searchPaulaRs.getString("UGAID_PRIME"));
                entry.put("ORGANIZATION", searchPaulaRs.getString("ORGANIZATION"));
            }
            
            return entry;    
            
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
