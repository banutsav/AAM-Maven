package edu.uga.ovpr.aam.login;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Jennifer Green
 */
public class Access {

    public static boolean hasAnyRole(HttpServletRequest request) throws Exception {
        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, null);
        final Session ses = sm.getSession();
        return !ses.getUserPrincipal().getRoles(request).isEmpty();
    }

    public static boolean isSuperUser(HttpServletRequest request) throws Exception {
        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, null);
        final Session ses = sm.getSession();
        return ses.getUserPrincipal().hasRole("SuperUser", request);
    }

}