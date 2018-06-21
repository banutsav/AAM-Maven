/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.contacts;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Static;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author submyers
 */
public class NewContactDAO {
    
    public static HashMap<String, String> getPersonRecord(String cannum) throws Exception {
        Connection conn = null;
        int flag = 0;
        
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement searchPaulaStat = conn.createStatement();
            Statement searchAffStat = conn.createStatement();
         
            
            // get PAULA people
            final String searchPaulaQuery = "SELECT"
                    + " `a`.`FIRSTNAME` AS `FIRSTNAME`"
                    + ", `a`.`LASTNAME` AS `LASTNAME`"
                    + ", `a`.`MYID` AS `MYID`"
                    + ", `a`.`CANNUM` AS `CANNUM`"
                    + ", `a`.`TITLE` AS `TITLE`"
                    + ", `a`.`EMAIL` AS `EMAIL`"
                    + ", `a`.`ACTIVE` AS `ACTIVE`"
                    + ", `a`.`EMAIL` AS `EMAIL`"
                    + ", `a`.`PHONE` AS `PHONE`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `a` where `a`.`CANNUM` = '" + cannum + "'";
            
            //System.out.println("Person search:\n" + searchPaulaQuery + "\n");
            
            
            final ResultSet searchPaulaRs = searchPaulaStat.executeQuery(searchPaulaQuery);
            HashMap<String, String> entry = new HashMap<String, String>();
            while (searchPaulaRs.next()) {
                entry = new HashMap<String, String>();
                entry.put("FIRSTNAME", searchPaulaRs.getString("FIRSTNAME"));
                entry.put("LASTNAME", searchPaulaRs.getString("LASTNAME"));
                entry.put("EMAIL", searchPaulaRs.getString("EMAIL"));
                entry.put("PHONE", searchPaulaRs.getString("PHONE"));
                entry.put("TITLE", searchPaulaRs.getString("TITLE"));
                entry.put("MYID", searchPaulaRs.getString("MYID"));
                flag = 1;
            }
            
            // get AFFILIATE people
            
            if(flag==0)
            {    
                final String searchAffQuery = "SELECT"
                        + " `a`.`FIRSTNAME` AS `FIRSTNAME`"
                        + ", `a`.`LASTNAME` AS `LASTNAME`"
                        + ", `a`.`OVPRID` AS `MYID`"
                        + ", `a`.`PSEUDOCAN` AS `CANNUM`"
                        + ", `a`.`TITLE` AS `TITLE`"
                        + ", `a`.`EMAIL` AS `EMAIL`"
                        + ", `a`.`ACTIVE` AS `ACTIVE`"
                        + ", `a`.`EMAIL` AS `EMAIL`"
                        + ", `a`.`PHONE` AS `PHONE`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a` where `a`.`PSEUDOCAN` = '" + cannum + "'";

                //System.out.println("Affiliates search:\n" + searchPaulaQuery + "\n");


                final ResultSet searchAffRs = searchAffStat.executeQuery(searchAffQuery);
                //HashMap<String, String> entry = new HashMap<String, String>();

                while (searchAffRs.next()) {
                    entry.put("FIRSTNAME", searchAffRs.getString("FIRSTNAME"));
                    entry.put("LASTNAME", searchAffRs.getString("LASTNAME"));
                    entry.put("EMAIL", searchAffRs.getString("EMAIL"));
                    entry.put("PHONE", searchPaulaRs.getString("PHONE"));
                }

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
    
    // get a new pseudo contact UGAID
    public static int getContactUGAID() throws Exception
    {
        Connection conn = null;
        
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement getMaxPseudoCanStat2 = conn.createStatement();        
         
            final String getMaxPseudoCanQuery2 = "SELECT"
                        + " MAX(`PSEUDOCAN`) AS `maxPseudoCan`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE`";
            
            
            final ResultSet getMaxPseudoCanRs2 = getMaxPseudoCanStat2.executeQuery(getMaxPseudoCanQuery2);
            
            
            int maxPseudoCan2 = 1;
            if (getMaxPseudoCanRs2.next())     
                maxPseudoCan2 = getMaxPseudoCanRs2.getInt("maxPseudoCan") + 1;
                
            return maxPseudoCan2;
        }
        catch (Exception ex) {
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
    
    // insert new contact
    public static int addContact(NewContactForm form) throws Exception
    {
        Connection conn = null;
        
        try {
            conn = Static.getConnection("jdbc/paul");
            //Statement stat1 = conn.createStatement();
            //Statement stat2 = conn.createStatement();
            Statement stat3 = conn.createStatement();
            
            // check if already a contact 
            //final String check = "SELECT `ID` from `CONTACTS`.`PERSON` where `UGAID_PRIME` = '" + form.getTargPersonCannum() + "'";
            //ResultSet Rs = stat1.executeQuery(check);
            
            //int maxcan = getContactUGAID();
            String prime, ugaid, myid;
            
            //determine the prime UGAID
            if(form.getTargPersonCannum().equals("0"))
            {
                //new person
                prime = String.valueOf(getContactUGAID());
                ugaid = String.valueOf(getContactUGAID());
                myid = String.valueOf(getContactUGAID());
            }
            else
            {
                //existing person
                prime = form.getTargPersonCannum();
                ugaid = String.valueOf(getContactUGAID());
                myid = form.getMyid();
            }
            
            /*if(!Rs.next())
            {
                //insert contact record
                final String insert = "INSERT INTO `CONTACTS`.`PERSON` SET `UGAID_PRIME` = '" + prime + "'";
                stat2.executeUpdate(insert);
            }*/
            
            
            
            // insert role 
            final String query = "INSERT INTO `" + Constants.DB_PAUL + "`.`AFFILIATE` SET "
                    + "`FIRSTNAME` = '" + form.getFirstName() + "'" +
                    " , `LASTNAME` = '" + form.getLastName() + "'" +
                    ", `UGAID_PRIME` = '" + prime + "'" +
                    ", `PSEUDOCAN` = '" + ugaid + "'" +
                    ", `ACTIVE` = '1'" +
                    ", `STATUS` = 'Non-Provisional'" +
                    ", `CREATEDBY` = 'AAM-contacts'" +
                    ", `PERSON_TYPE` = '1'" + // contacts have person type '1'
                    " , `ORGANIZATION` = '" + form.getOrganization() + "'" +
                    ", `EMAIL` = '" + form.getEmail() + "'" + 
                    ", `ADDR1` = '" + form.getAddr1() + "'" + 
                    ", `ADDR2` = '" + form.getAddr2() + "'" + 
                    ", `CITY` = '" + form.getCity() + "'" + 
                    ", `STATE` = '" + form.getState() + "'" + 
                    ", `ZIP` = '" + form.getZip() + "'" + 
                    ", `COUNTRY` = '" + form.getCountry() + "'" + 
                    ", `PHONE` = '" + form.getTelephone() + "'" + 
                    ", `CELLPHONE` = '" + form.getCellphone() + "'" + 
                    ", `FAX` = '" + form.getFax() + "'" + 
                    ", `TITLE` = '" + form.getTitle() + "'" +
                    ", `OVPRID` = '" + ugaid + "'"
                    ;
            //System.out.println("\ncontact insert = \n" + query);
            
            stat3.executeUpdate(query);
            
            return 1;
            
        }  
        catch (Exception ex) {
            ex.printStackTrace();
            return 0;
            //throw ex;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {
                    return 0;
                }
            }
        }
    }
}
