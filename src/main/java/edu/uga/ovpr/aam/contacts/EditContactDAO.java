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
public class EditContactDAO {
    
    // insert new contact
    public static int editContact(EditContactForm form) throws Exception
    {
        Connection conn = null;
        
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement stat = conn.createStatement();
            
           
            // insert role 
            final String query = "UPDATE `" + Constants.DB_PAUL + "`.`AFFILIATE` SET "
                    + "`FIRSTNAME` = '" + form.getFirstName() + "'" +
                    " , `LASTNAME` = '" + form.getLastName() + "'" +
                    ", `UGAID_PRIME` = '" + form.getUgaid_prime() + "'" +
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
                    ", `ACTIVE` = '" + form.getActive()+ "'" +
                    " WHERE `PSEUDOCAN` = '" + form.getTarg_cannum() + "'"
                    ;
            
            //System.out.println("\nUpdate contact query = \n" + query);
            
            stat.executeUpdate(query);
            
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
    
    // remove contact
    public static int editStatusOfContact(EditContactForm form, char status) throws Exception
    {
        Connection conn = null;
        
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement stat = conn.createStatement();
            
           
            // insert role 
            final String query = "UPDATE `" + Constants.DB_PAUL + "`.`AFFILIATE` SET "
                    + "`ACTIVE` = '" + status + "'" +
                    " WHERE (`PSEUDOCAN` = '" + form.getTarg_cannum() + "'" + 
                    " AND `PERSON_TYPE` = '1')"
                    ;
            //System.out.println("\nActive/inactive contact query = \n" + query);
            
            stat.executeUpdate(query);
            
            return 2;
            
        }  
        catch (Exception ex) {
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
