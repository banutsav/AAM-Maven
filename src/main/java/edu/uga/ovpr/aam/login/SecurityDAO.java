package edu.uga.ovpr.aam.login;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
import edu.uga.ovpr.aam.person.Person;
import edu.uga.ovpr.statics.DynaResultSet;
import edu.uga.ovpr.statics.OVPRMethods;
import java.util.*;
import javax.naming.*;
import javax.naming.directory.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Glenn Owens
 */
public class SecurityDAO {

    public static LoginResult login_LDAP(LoginForm form) throws Exception {
        final LoginResult loginResult = new LoginResult();
        if (form.getUgamyid() != null && form.getPassword() != null) {
            //final String dn = "cn=" + form.getUgamyid() + ",ou=users,o=meta";
            final String dn = "cn=" + form.getUgamyid() + ",ou=users,o=uga";
            
            final Hashtable env = new Hashtable();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            //env.put(Context.PROVIDER_URL, "ldaps://ldapssl.ovpr.uga.edu");
            env.put(Context.PROVIDER_URL, "ldaps://lds.uga.edu");
            
            env.put(Context.SECURITY_PROTOCOL, "ssl");
            env.put(Context.SECURITY_PRINCIPAL, dn);
            env.put(Context.SECURITY_CREDENTIALS, form.getPassword().trim());
            DirContext ctx = null;
            try {
                ctx = new InitialDirContext(env);
                if (ctx.getAttributes(dn, null).get("ugaAuthCheck").contains("y")) {
                    loginResult.canNum = ctx.getAttributes(dn).get("ugaIDNumber").get().toString();
                }
            } catch (NameNotFoundException ex) {
                loginResult.isBadMyID = true;
                ex.printStackTrace();
            } catch (AuthenticationException ex) {
                loginResult.isBadPassword = true;
                ex.printStackTrace();
            } catch (Exception ex) {
                loginResult.isFailedAttempt = true;
                ex.printStackTrace();
            } finally {
                try {
                    ctx.close();
                } catch (Exception ex) {
                }
                form.setUgamyid(null);
                form.setPassword(null); //clear credentials from memory
            }
        }
        return loginResult;
    }

    public static AuthState makeAuthState(final String canNum) throws Exception {
        final AuthState authState = new AuthState();
        final String roleQuery = "SELECT `aar`.`NAME`" +
                "FROM `" + Constants.DB_SCHEMA + "`.`AFFILIATE_ADMIN` `aa`" +
                "INNER JOIN `" + Constants.DB_SCHEMA + "`.`AFFILIATE_ADMIN_ROLE` `aar` on `aa`.`ROLE_ID`=`aar`.`ID`" +
                "WHERE `aa`.`CANNUM`='" + StringEscapeUtils.escapeSql(canNum) + "';";
        final List<DynaBean> roleResults = OVPRMethods._SQL_ExecuteQuery(roleQuery, Constants.DS_MYSQL1).getRows();
        final HashSet<String> roles = new HashSet<String>();
        for (DynaBean d : roleResults) {
            roles.add((String) d.get("NAME"));
        }
        final String globalRoleQuery = "SELECT `role`" +
                "FROM `global_roles`.`super_users`" +
                "WHERE `ugaid`='" + StringEscapeUtils.escapeSql(canNum) + "' AND `app`='AAM';";
        final List<DynaBean> globalRoleResults = OVPRMethods._SQL_ExecuteQuery(globalRoleQuery, Constants.DS_MYSQL1).getRows();
        for (DynaBean d : globalRoleResults) {
            roles.add((String) d.get("role"));
        }
        authState.setRoles(roles);
        final String personQuery = "SELECT `lastName`,`firstName`" +
                "FROM `paul`.`PERSON`" +
                "WHERE `cannum`='" + StringEscapeUtils.escapeSql(canNum) + "';";
        final List<DynaBean> personResults = OVPRMethods._SQL_ExecuteQuery(personQuery, Constants.DS_MYSQL1).getRows();
        final Person person = new Person();
        if (!personResults.isEmpty()) {
            final DynaBean d = personResults.get(0);
            person.setLastName((String) d.get("lastName"));
            person.setFirstName((String) d.get("firstName"));
        }
        authState.setUserInfo(person);
        return authState;
    }

    public static void refreshUserRoles(Session ses) throws Exception {
        final AuthState authState = makeAuthState(ses.getUserCan());
        ses.setAuthState(authState);
    }


    public static boolean hasSuperUserAccess(Session ses) throws Exception {
        refreshUserRoles(ses);
        return ses.getAuthState().hasRole("SuperUser") ||
                isSuperUser(ses.getUserCan());
    }
    
    public static boolean hasManagerAccess(Session ses) throws Exception {
        refreshUserRoles(ses);
        return ses.getAuthState().hasRole("Manager") ||
                isManager(ses.getUserCan());
    }
    
    public static boolean hasManagerProxyAccess(Session ses) throws Exception {
        refreshUserRoles(ses);
        return ses.getAuthState().hasRole("ManagerProxy") ||
                isManagerProxy(ses.getUserCan());
    }

    public static boolean hasAnyRole(Session ses) throws Exception {
        refreshUserRoles(ses);

        return hasSuperUserAccess(ses) ||
                hasManagerAccess(ses) ||
                 hasManagerProxyAccess(ses);
    }
    
    /**public static boolean canViewUGAID(Session ses) throws Exception {
        refreshUserRoles(ses);

        return hasSuperUserAccess(ses) ||
                hasTRAdminAccess(ses);
    }

    public static boolean isDeptAdmin(String userCan) throws Exception {
        //See if the user is an OSP Dept Contact of any departments
        final String query = "select 1 " +
                "from `cdb2_4_2`.`ENTITIES` `e`" +
                "inner join `cdb2_4_2`.`CONTACTS` `c`" +
                "on `e`.`ID`=`c`.`ENTITY_ID` and `c`.`CONTACT_TYPE_ID` IN ('5','6','14')" +//OSP,Department,Dept Primary/Secondary/Tertiary
                "and `c`.`CONTACT`='" + StringEscapeUtils.escapeSql(userCan) + "'";
        return !OVPRMethods._SQL_ExecuteQuery(query, "jdbc/mysql1").getRows().isEmpty();
    }**/

    public static boolean isSuperUser(String userCan) throws Exception {
        //See if the user is a Super User
        final String query = "select 1 " +
                "FROM `global_roles`.`super_users`" +
                "WHERE `ugaid`='" + StringEscapeUtils.escapeSql(userCan) + "' AND `app`='AAM';";
        return !OVPRMethods._SQL_ExecuteQuery(query, Constants.DS_MYSQL1).getRows().isEmpty();
    }
    
    public static boolean isManager(String userCan) throws Exception {
        //See if the user is a Manager
        //System.out.println("manager");
        final String query = "SELECT 1 " +
                "FROM `" + Constants.DB_SCHEMA + "`.`AFFILIATE_ADMIN` `aa`" +
                "INNER JOIN `" + Constants.DB_SCHEMA + "`.`AFFILIATE_ADMIN_ROLE` `aar` on `aa`.`ROLE_ID`=`aar`.`ID`" +
                "WHERE `aa`.`CANNUM`='" + StringEscapeUtils.escapeSql(userCan) + "' " +
                "AND `aa`.`ROLE_ID`='2'";
        //System.out.println(query);
        return !OVPRMethods._SQL_ExecuteQuery(query, Constants.DS_MYSQL1).getRows().isEmpty();
    }
    
    public static boolean isManagerProxy(String userCan) throws Exception {
        //See if the user is a Manager Proxy
        //System.out.println("managerProxy");
        final String query = "SELECT 1 " +
                "FROM `" + Constants.DB_SCHEMA + "`.`AFFILIATE_ADMIN` `aa`" +
                "INNER JOIN `" + Constants.DB_SCHEMA + "`.`AFFILIATE_ADMIN_ROLE` `aar` on `aa`.`ROLE_ID`=`aar`.`ID`" +
                "WHERE `aa`.`CANNUM`='" + StringEscapeUtils.escapeSql(userCan) + "' " +
                "AND `aa`.`ROLE_ID`='3'";
        //System.out.println(query);
        return !OVPRMethods._SQL_ExecuteQuery(query, Constants.DS_MYSQL1).getRows().isEmpty();
    }

    public static boolean switchUser(String canNum, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
        Session ses = sm.getSession();

        refreshUserRoles(ses);//see if user presently has Admin role
        if (ses.isReallyAdmin() || ses.getAuthState().hasRole("SuperUser")) {
            final boolean isReallyAdmin = ses.isReallyAdmin();

            //Clear session variables
            request.getSession().invalidate();

            //Start new session
            ses = sm.getSession();
            ses.setLoggedIn(true);
            ses.setReallyAdmin(isReallyAdmin);
            ses.setUserCan(canNum);
            refreshUserRoles(ses);//load the roles of the new user
            return true;
        }
        return false;
    }
    
    /*
        check if associate manager
    */
    
    public static boolean isAssociateManager(String can) throws Exception
    {
        final String query = "select * from `" + Constants.DB_PAUL + "`.`ASSOCIATE_ADMIN` where `CANNUM` = '" +
                can + "';";
        
        DynaResultSet result = OVPRMethods._SQL_ExecuteQuery(query, Constants.DS_MYSQL1);
        if(result.getTotalRecords()!=0)
        {    
            DynaBean b = result.getRows().get(0);
            String active = b.get("ACTIVE").toString();
            
            if(active.equals("1"))
                return true;
        }    
            
        return false;
    }
    
    /*
        check if contact manager
    */
    
    public static boolean isContactManager(String can) throws Exception
    {
        final String query = "select * from `" + Constants.DB_PAUL + "`.`CONTACT_ADMIN` where `CANNUM` = '" +
                can + "';";
        
        DynaResultSet result = OVPRMethods._SQL_ExecuteQuery(query, Constants.DS_MYSQL1);
        if(result.getTotalRecords()!=0)
        {    
            DynaBean b = result.getRows().get(0);
            String active = b.get("ACTIVE").toString();
            
            if(active.equals("1"))
                return true;
        }    
            
        return false;
    }
}
