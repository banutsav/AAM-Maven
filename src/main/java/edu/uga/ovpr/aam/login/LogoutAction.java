package edu.uga.ovpr.aam.login;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Glenn Owens
 */
public class LogoutAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            sm.killSession();
            return mapping.findForward("gfwd_login");
        } catch (Exception ex) {
            servlet.log("Error message", ex);
            throw ex;//return a Tomcat Error page with exception stacktrace
            //return mapping.findForward("gfwd_error");
        }
    }
}