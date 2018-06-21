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
public class CreateContactsDAO {
    
    public static HashMap<String, Object> SearchPaula(CreateContactsForm c_form) throws Exception {
        HashMap<String, Object> results = new HashMap<String, Object>();
        ArrayList<Object> people = new ArrayList<Object>();
        ArrayList<String> myid = new ArrayList<String>();
        Connection conn = null;
        
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement searchPaulaStat = conn.createStatement();
            Statement searchAffStat = conn.createStatement();
            Statement searchPaulaCountStat = conn.createStatement();
            Statement searchAffCountStat = conn.createStatement();

            String whereClause = "";
            
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
            
            String v1 = "", v2 = "";
            if (!c_form.getOvprId().isEmpty()) {
                if (c_form.getOvprIdOpt().equalsIgnoreCase("Begins")) {
                    v1 += " AND `a`.`MYID` LIKE '" + GeneralDAO.CleanMySQLStringForLike(c_form.getOvprId()) + "%'";
                    v2 += " AND `a`.`OVPRID` LIKE '" + GeneralDAO.CleanMySQLStringForLike(c_form.getOvprId()) + "%'";
                } else if (c_form.getOvprIdOpt().equalsIgnoreCase("Contains")) {
                    v1 += " AND `a`.`MYID` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(c_form.getOvprId()) + "%'";
                    v2 += " AND `a`.`OVPRID` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(c_form.getOvprId()) + "%'";
                } else if (c_form.getOvprIdOpt().equalsIgnoreCase("Exact")) {
                    v1 += " AND `a`.`MYID`='" + GeneralDAO.CleanMySQLString(c_form.getOvprId()) + "'";
                    v2 += " AND `a`.`OVPRID`='" + GeneralDAO.CleanMySQLString(c_form.getOvprId()) + "'";
                }
            }
            
            String w1 = "", w2 = "";
            if (!c_form.getOrg().isEmpty()) {
                if (c_form.getOrgOpt().equalsIgnoreCase("Begins")) {
                    w1 += " AND `un`.`unit_name` LIKE '" + GeneralDAO.CleanMySQLStringForLike(c_form.getOrg()) + "%'";
                    w2 += " AND `a`.`ORGANIZATION` LIKE '" + GeneralDAO.CleanMySQLStringForLike(c_form.getOrg()) + "%'";
                } else if (c_form.getOrgOpt().equalsIgnoreCase("Contains")) {
                    w1 += " AND `un`.`unit_name` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(c_form.getOrg()) + "%'";
                    w2 += " AND `a`.`ORGANIZATION` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(c_form.getOrg()) + "%'";
                } else if (c_form.getOrgOpt().equalsIgnoreCase("Exact")) {
                    w1 += " AND `un`.`unit_name`='" + GeneralDAO.CleanMySQLString(c_form.getOrg()) + "'";
                    w2 += " AND `a`.`ORGANIZATION`='" + GeneralDAO.CleanMySQLString(c_form.getOrg()) + "'";
                }
            }
            
            String whereClause1 = whereClause + w1 + v1;
            String whereClause2 = whereClause + w2 + v2 + " AND `PERSON_TYPE` = '1'"; // contacts have person type `1`

            if(!c_form.getPrime().isEmpty())
            {
                whereClause1 += " AND `CANNUM` = '" + c_form.getPrime() + "'";
                whereClause2 += " AND `UGAID_PRIME` = '" + c_form.getPrime() + "'";
            }
            
            
            if (!whereClause1.isEmpty()) {
                whereClause1 = whereClause1.replaceFirst(" AND", " WHERE");
            }
            
            if (!whereClause2.isEmpty()) {
                whereClause2 = whereClause2.replaceFirst(" AND", " WHERE");
            }
            
            // search PAULA
            final String searchPaulaCountQuery = "SELECT"
                    + " COUNT(`a`.`ID`) AS `COUNT`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `a` " +
                    "left join `" + Constants.DB_PAUL + "`.`DEPT` `d` on (`a`.`HOMEDEPT_ID` = `d`.`ID`)"
                    + " left join `uhd`.`Today_UNIT_BUDGET_CODES` `ubc` on (`d`.`DEPTNUM` = `ubc`.`budget_code`)"
                    + " left join `uhd`.`Today_UNITS` `u` on (`ubc`.`unit_id` = `u`.`id`)"
                    + " left join `uhd`.`Today_UNIT_NAMES` `un` on((`u`.`id` = `un`.`unit_id`) and (`un`.`name_type_id` = _utf8'1'))"
                    + whereClause1;
            
            //System.out.println("\nSearch PAULA Count:\n" + searchPaulaCountQuery + "\n");
            
            ResultSet searchPaulaCountRs = searchPaulaCountStat.executeQuery(searchPaulaCountQuery);
            Integer searchPaulaCount = 0;
            if (searchPaulaCountRs.next()) {
                searchPaulaCount = searchPaulaCountRs.getInt("COUNT");
            }
            
            // search contacts
            final String searchAffCountQuery = "SELECT"
                    + " COUNT(`a`.`ID`) AS `COUNT`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a` "
                    + whereClause2;
            
            
            //System.out.println("\nSearch Affiliate Count:\n" + searchAffCountQuery + "\n");
            
            ResultSet searchAffCountRs = searchAffCountStat.executeQuery(searchAffCountQuery);
            Integer searchAffCount = 0;
            if (searchAffCountRs.next()) {
                searchAffCount = searchAffCountRs.getInt("COUNT");
            }
            
            results.put("COUNT", searchPaulaCount + searchAffCount);
           
            //results.put("COUNT", searchPaulaCount);
            
            String orderBy = " ORDER BY `a`.`LASTNAME`, `a`.`FIRSTNAME`";

            Integer start = c_form.getPageNum() * 100;
            String limitClause = " LIMIT " + start + ", 100";

            
            // get PAULA people
            final String searchPaulaQuery = "SELECT"
                    + " `a`.`FIRSTNAME` AS `FIRSTNAME`"
                    + ", `a`.`LASTNAME` AS `LASTNAME`"
                    + ", `a`.`MYID` AS `MYID`"
                    + ", `a`.`CANNUM` AS `CANNUM`"
                    + ", `a`.`TITLE` AS `TITLE`"
                    + ", `a`.`EMAIL` AS `EMAIL`"
                    + ", `a`.`ACTIVE` AS `ACTIVE`"
                    + ", `a`.`PHONE` AS `PHONE`"
                    + ", `un`.`unit_name` AS `ORGANIZATION`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `a` left join `" + Constants.DB_PAUL + "`.`DEPT` `d` on (`a`.`HOMEDEPT_ID` = `d`.`ID`)"
                    + " left join `uhd`.`Today_UNIT_BUDGET_CODES` `ubc` on (`d`.`DEPTNUM` = `ubc`.`budget_code`)"
                    + " inner join `uhd`.`Today_UNITS` `u` on (`ubc`.`unit_id` = `u`.`id`)"
                    + " inner join `uhd`.`Today_UNIT_NAMES` `un` on((`u`.`id` = `un`.`unit_id`) and (`un`.`name_type_id` = _utf8'1'))"
                    + whereClause1
                    + orderBy
                    + limitClause;

            //System.out.println("\nPAULA search:\n" + searchPaulaQuery + "\n");
            
            
            final ResultSet searchPaulaRs = searchPaulaStat.executeQuery(searchPaulaQuery);
            while (searchPaulaRs.next()) {
                HashMap<String, String> entry = new HashMap<String, String>();
                entry.put("FIRSTNAME", searchPaulaRs.getString("FIRSTNAME"));
                entry.put("LASTNAME", searchPaulaRs.getString("LASTNAME"));
                entry.put("MYID", searchPaulaRs.getString("MYID"));
                entry.put("CANNUM", searchPaulaRs.getString("CANNUM"));
                entry.put("CONTACT_UGAID", searchPaulaRs.getString("CANNUM"));
                entry.put("PHONE", searchPaulaRs.getString("PHONE"));
                entry.put("TITLE", searchPaulaRs.getString("TITLE"));
                entry.put("EMAIL", searchPaulaRs.getString("EMAIL"));
                
                //System.out.println("myid = " + searchPaulaRs.getString("MYID") + ", org = " + searchPaulaRs.getString("ORGANIZATION"));
                
                if(searchPaulaRs.getString("ORGANIZATION").equals("UGA Affiliate"))
                {    
                    entry.put("ORGANIZATION", refineAffiliateDept(searchPaulaRs.getString("CANNUM"), conn));
                    entry.put("AFFILIATE", "1");
                    entry.put("UGA", "0");
                }
                else
                {   
                    entry.put("AFFILIATE", "0");
                    entry.put("UGA", "1");
                    entry.put("ORGANIZATION", searchPaulaRs.getString("ORGANIZATION"));
                }
                    
                entry.put("CONTACT", "0");
                people.add(entry);
                 
                //if(!myid.contains(searchPaulaRs.getString("MYID")))
                //{
                    myid.add(searchPaulaRs.getString("CANNUM"));
                //}
            }

            
            // get contacts people
            final String searchAffQuery = "SELECT"
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
                    
                    + whereClause2
                    + orderBy
                    + limitClause;

            //System.out.println("\nContacts search:\n" + searchAffQuery + "\n");
            
            
            final ResultSet searchAffRs = searchAffStat.executeQuery(searchAffQuery);
            while (searchAffRs.next()) {
                HashMap<String, String> entry = new HashMap<String, String>();
                entry.put("FIRSTNAME", searchAffRs.getString("FIRSTNAME"));
                entry.put("LASTNAME", searchAffRs.getString("LASTNAME"));
                entry.put("MYID", searchAffRs.getString("MYID"));
                entry.put("CANNUM", searchAffRs.getString("CANNUM"));
                entry.put("CONTACT_UGAID", searchAffRs.getString("CONTACT_UGAID"));
                entry.put("PHONE", searchAffRs.getString("PHONE"));
                entry.put("TITLE", searchAffRs.getString("TITLE"));
                entry.put("EMAIL", searchAffRs.getString("EMAIL"));
                entry.put("CONTACT_ACTIVE", searchAffRs.getString("ACTIVE"));
                entry.put("ORGANIZATION", searchAffRs.getString("ORGANIZATION"));
                entry.put("CONTACT", "1");
                entry.put("AFFILIATE", "0");
                entry.put("UGA", "0");
                
                people.add(entry);
                
                //if(!myid.contains(searchAffRs.getString("CANNUM")))
                //{
                    myid.add(searchAffRs.getString("CANNUM"));
                //}
            }
            
            ArrayList<Object> sorted = sortResults(people, myid); // sort results
            
            results.put("people", sorted);       
                    
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
    
    
    // for affiliates, get the department listed in their affiliate table
    // if their PAULA dept says "UGA Affiliate"
    public static String refineAffiliateDept(String cannum, Connection conn) throws Exception
    {
        Statement stat = conn.createStatement();
        final String query = "select `ORGANIZATION` from `" + Constants.DB_PAUL + "`.`AFFILIATE` where `PSEUDOCAN` = '" + cannum + "'";
        final ResultSet Rs = stat.executeQuery(query);
        while(Rs.next())
        {
            return Rs.getString("ORGANIZATION");
        }
        
        return "-";
    }
    
    // sort the people results to group different identities of one person (people, affiliate, contacts)
    public static ArrayList<Object> sortResults(ArrayList<Object> people, ArrayList<String> myid)
    {
        ArrayList<Object> results = new ArrayList<Object>();
        
        for(int i=0;i<myid.size()-1;i++)
        {
            for(int j=i+1;j<myid.size();j++)
            {
                if(myid.get(i).compareToIgnoreCase(myid.get(j))>0)
                {
                    Collections.swap(people, i, j);
                    Collections.swap(myid, i, j);
                }
            }
        }
        
        //get unique myids
        ArrayList<String> unique_myid = new ArrayList<String>();
        for(int i=0; i<myid.size(); i++)
        {
            if(!unique_myid.contains(myid.get(i)))
                unique_myid.add(myid.get(i));
        }
        
        //assign color codes
        int flag = 0;
        for(int i=0; i<unique_myid.size(); i++)
        {
            if(flag==0)
                flag = 1;
            else
                flag = 0;
                
            for(int j=0; j<myid.size(); j++)
            {
                if(unique_myid.get(i).equals(myid.get(j)))
                {
                    HashMap<String, String> entry = new HashMap<String, String>();
                    entry = (HashMap<String, String>) people.get(j);
                    entry.put("COLOR_CODE", String.valueOf(flag));
                    results.add(entry);
                }
            }
        }
        
        return results;
        //return people;
    }
}
