package edu.uga.ovpr.aam.login;

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
public class ExtendSessionAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            //System.out.println(new Date(System.currentTimeMillis()).toString() + ": Is requested session valid? " + request.isRequestedSessionIdValid());
            request.getSession();//Creates new session or resets existing session's timeout timer
            return mapping.findForward("fwd_blank");
        } catch (Exception ex) {
            servlet.log("Error message", ex);
            return mapping.findForward("gfwd_error");
        }
    }
}