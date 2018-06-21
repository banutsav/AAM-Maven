package edu.uga.ovpr.aam.login;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.rolebasedsecurity.IUserRoleRetriever;
import edu.uga.ovpr.rolebasedsecurity.Role;
import edu.uga.ovpr.rolebasedsecurity.IUserIdentity;
import java.util.HashSet;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Jennifer Green
 */
public class UserRoleRetriever implements IUserRoleRetriever, java.io.Serializable {

    private final String alreadyRefreshedKey = "edu.uga.ovpr.rolebasedsecurity.alreadyRefreshed";

    public HashSet<Role> retrieveRoles(IUserIdentity userIdentity, HttpServletRequest request) throws Exception {
        if (request.getAttribute(alreadyRefreshedKey) != null) {
            //Keep current roles
            return null;
        }

        final UserIdentity c_userIdentity = (UserIdentity) userIdentity;
        final HashSet<Role> roles = new HashSet();

        //Assemble the user's roles from the database.

       //System-wide roles
        {
            final String query = "select `r`.`roleName`" +
                    "from `" + Constants.DB_SCHEMA + "`.`Auth` `ra`" +
                    "inner join `" + Constants.DB_SCHEMA + "`.`Roles` `r` on `ra`.`role_id`=`r`.`id`" +
                    "where `ra`.`can`='" + StringEscapeUtils.escapeSql(c_userIdentity.getCanNum()) + "'";
            /*final List<DynaBean> results = OVPRMethods._SQL_ExecuteQuery(query, Constants.DS_MYSQL1).getRows();
            for (DynaBean d : results) {
                final String roleName = (String) d.get("roleName");
                roles.add(new Role(roleName, null, null));
            }*/
        }

        request.setAttribute(alreadyRefreshedKey, true);
        return roles;
    }
}
