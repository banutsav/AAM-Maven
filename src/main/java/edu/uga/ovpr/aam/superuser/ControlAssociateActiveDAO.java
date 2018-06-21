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
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/**
 *
 * @author submyers
 */
public class ControlAssociateActiveDAO {

    private static ArrayList<String> ldapUsers = new ArrayList<String>();
    
    /*
        get list of LDAP users
    */
    public static NamingEnumeration getLDAPUserList(String branch) throws Exception {

        /** Local Variables **/
        DirContext dc;
        NamingEnumeration ne = null;
        SearchControls sc = null;
        String query = "ou=" + branch + ",dc=ovpr,dc=uga,dc=edu";

        /* The following are environment variables used for connection to
         * database ldap.ovpr.uga.edu.
         */
        String dn = "cn=admin,dc=ovpr,dc=uga,dc=edu";
        String ldapURL = "ldaps://ldapssl.ovpr.uga.edu";

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_PRINCIPAL, dn);
        env.put(Context.SECURITY_CREDENTIALS, Constants.ldapPassWord);
        env.put(Context.PROVIDER_URL, ldapURL);
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        
        try 
        {
            dc = new InitialDirContext(env);
           
            sc = new SearchControls();
            String[] attributeFilter = {"cn"};
            sc.setReturningAttributes(attributeFilter);
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
            ne = dc.search(query, "(&(cn=*))", sc);

            dc.close();
            return ne;
                
        }   
        catch (Exception ex) 
        {
            ex.printStackTrace(System.err);
            throw ex;
        }
        
    }
    
    /*
        check if a user is present in our LDAP user list
    */
    
    public static boolean userExists(NamingEnumeration ne, String user) throws NamingException
    {
        SearchResult sr;
        Attributes allUsers;
        String cn;
         
        /*
            the naming enumeration will be traversed only one time
            when the method will be called the first time.
        
            once the usernames are added to the array list, the list
            will drive the check
        */
        
        while (ne.hasMore()) 
        {
            sr = (SearchResult) ne.next();
            allUsers = sr.getAttributes();
            cn = allUsers.get("cn").toString();
            String temp = cn.substring(4);
            
            ldapUsers.add(temp);
            
        }
        
        ne.close();
        
        /*
            check if user in array list
        */
        
        for (String ldapUser : ldapUsers)
        {
            //System.out.println("existing LDAP user = " + ldapUser);
            
            if(ldapUser.equals(user))
                return true;
        }
        
        return false;
    }
    
    
    /*
        delete a user from our LDAP
    */
    
    public static void DeleteUser(String userName, String branch) throws Exception {

        /** Local Variables **/
        DirContext dc = null;
        String dbBasePath = "ou=" + branch + ",dc=ovpr,dc=uga,dc=edu";
        String entryPath = "";

        /* The following are environment variables used for connection to
         * database ldap.ovpr.uga.edu.
         */
        String dn = "cn=admin,dc=ovpr,dc=uga,dc=edu";
        String ldapURL = "ldaps://ldapssl.ovpr.uga.edu";

        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_PRINCIPAL, dn);
        env.put(Context.SECURITY_CREDENTIALS, Constants.ldapPassWord);
        env.put(Context.PROVIDER_URL, ldapURL);
        env.put(Context.SECURITY_PROTOCOL, "ssl");

        try {
            dc = new InitialDirContext(env);

            /** Define the target of the unbind command **/
            entryPath = "cn=".concat(userName).concat(",").concat(dbBasePath);
            dc.unbind(entryPath);

            /** Double check to make sure the entry is gone! **/
            Object test = null;
            try {
                test = dc.lookup(entryPath);

                /** If the try has made it here then an exception should be thrown **/
                System.err.println("LDAP_DB.DeleteValues ERROR: Unable to delete entry " + entryPath);
                Exception ex = new Exception();
                throw ex;
            } catch (Exception ex) {
                // No worries..
            }
            dc.close();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            throw ex;
        }
    }

    
    /*
        if a user an associate is deactivated by a superuser
        then remove the associate from OVPR LDAP
    */
    
    public static void removeAssociateFromLDAP(String userid) throws Exception
    {   
        String email = "";
        Connection conn = Static.getConnection("jdbc/paul");
        final Statement stat = conn.createStatement();
        final String q = "select `EMAIL` from `" + Constants.DB_PAUL + "`.`AFFILIATE` where `ID` = '" + userid + "'";
        final ResultSet result = stat.executeQuery(q);
        
        if(result.next())
        {
            email = result.getString("EMAIL");
        }
        
        NamingEnumeration ne = getLDAPUserList("users");
        
        if(userExists(ne, email))
        {
            DeleteUser(email, "users");
        }
    }
    
    public static void GetAssociateInfo(ControlAssociateActiveForm controlAssociateActiveForm) throws Exception {
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getInfoStat = conn.createStatement();

            final String getInfoQuery = "SELECT"
                    + " `a`.`FIRSTNAME`"
                    + ", `a`.`LASTNAME`"
                    + ", `a`.`OVPRID`"
                    + ", `a`.`PSEUDOCAN`"
                    + ", `a`.`ORGANIZATION`"
                    + ", `a`.`DEPARTMENT`"
                    + ", `a`.`TITLE`"
                    + ", `a`.`EMAIL`"
                    + ", `a`.`PHONE`"
                    + ", `a`.`ACTIVE`"
                    + ", `ep`.`isEnrolled` AS `IS_ENROLLED`"
                    + ", `ep`.`ugaHRStatus` AS `UGA_HR_STATUS`"
                    
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    
                    + " left join `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                    + " on `a`.`ID` = `apl`.`AFFILIATE_ID` "
                    
                    + " left join `authdata3`.`EITS_Persons` `ep`"
                    + " on `a`.`PSEUDOCAN` = `ep`.`CAN9`"
                    
                    + " WHERE"
                    
                    + " `apl`.`AFFILIATE_PURPOSE_ID` = '" + Constants.associate_code + "' "
                    
                    + " AND "
                    
                    + " `a`.`id`='" + controlAssociateActiveForm.getAffId() + "'";
            
            final ResultSet getInfoRs = getInfoStat.executeQuery(getInfoQuery);
            
            if (getInfoRs.next()) {
                controlAssociateActiveForm.setFirstName(getInfoRs.getString("FIRSTNAME"));
                controlAssociateActiveForm.setLastName(getInfoRs.getString("LASTNAME"));
                controlAssociateActiveForm.setOvprId(getInfoRs.getString("OVPRID"));
                controlAssociateActiveForm.setPseudoCan(getInfoRs.getString("PSEUDOCAN"));
                controlAssociateActiveForm.setOrganization(getInfoRs.getString("ORGANIZATION"));
                controlAssociateActiveForm.setDepartment(getInfoRs.getString("DEPARTMENT"));
                controlAssociateActiveForm.setTitle(getInfoRs.getString("TITLE"));
                controlAssociateActiveForm.setEmail(getInfoRs.getString("EMAIL"));
                controlAssociateActiveForm.setPhone(getInfoRs.getString("PHONE"));
                controlAssociateActiveForm.setActive(getInfoRs.getString("ACTIVE"));
                
                controlAssociateActiveForm.setisEnrolled(getInfoRs.getString("IS_ENROLLED"));
                controlAssociateActiveForm.setugaHRStatus(getInfoRs.getString("UGA_HR_STATUS"));
            }


        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void DeactivateAff(ControlAssociateActiveForm controlAssociateActiveForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            
            final Statement updatePurposeStat = conn.createStatement();
            final Statement updatePurposeLinkStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String updatePurposesCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE`"
                    + " SET"
                    + " `ACTIVE`='0'"
                    + " WHERE"
                    + " `ID`='" + controlAssociateActiveForm.getAffId() + "'";
            //System.out.println("Update Purpose Command:\n" + updatePurposesCmd + "\n");
            updatePurposeStat.executeUpdate(updatePurposesCmd);

            final String updatePurposesLinkCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                    + " SET"
                    + " `ACTIVE`='0'"
                    + " WHERE"
                    + " `AFFILIATE_ID`='" + controlAssociateActiveForm.getAffId() + "'"
                    + " AND `AFFILIATE_PURPOSE_ID` = '" + Constants.associate_code + "'";
            
            //System.out.println("Update Purpose Command:\n" + updatePurposesCmd + "\n");
            updatePurposeLinkStat.executeUpdate(updatePurposesLinkCmd);
            
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

    public static void ActivateAff(ControlAssociateActiveForm controlAssociateActiveForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement updatePurposeStat = conn.createStatement();
            final Statement updatePurposeLinkStat = conn.createStatement();
            
            commitStat.execute("START TRANSACTION");

            final String updatePurposesCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE`"
                    + " SET"
                    + " `ACTIVE`='1'"
                    + " WHERE"
                    + " `ID`='" + controlAssociateActiveForm.getAffId() + "'";
            //System.out.println("Update Purpose Command:\n" + updatePurposesCmd + "\n");
            updatePurposeStat.executeUpdate(updatePurposesCmd);

            final String updatePurposesLinkCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                    + " SET"
                    + " `ACTIVE`='1'"
                    + " WHERE"
                    + " `AFFILIATE_ID`='" + controlAssociateActiveForm.getAffId() + "'"
                    + " AND `AFFILIATE_PURPOSE_ID` = '" + Constants.associate_code + "' ";
            
            //System.out.println("Update Purpose Command:\n" + updatePurposesCmd + "\n");
            
            updatePurposeLinkStat.executeUpdate(updatePurposesLinkCmd);
            
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

    public static void DeactivateAll(ControlAffActiveForm controlAffActiveForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement updatePurposeStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String updatePurposesCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                    + " SET"
                    + " `EXPIRATION_DATE`=NOW()"
                    + ", `EXPIRES`='1'"
                    + " WHERE"
                    + " `AFFILIATE_ID`='" + controlAffActiveForm.getAffId() + "'"
                    + " AND ("
                    + " `EXPIRATION_DATE`>NOW()"
                    + " OR `EXPIRES`='1')";
            //System.out.println("Update Purpose Command:\n" + updatePurposesCmd + "\n");
            updatePurposeStat.executeUpdate(updatePurposesCmd);

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
