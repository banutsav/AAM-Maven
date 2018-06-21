/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.users;

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
 * @author submyers
 */
public class ManageUsersAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ManageUsersForm manageUsersForm = (ManageUsersForm) form;
        if (manageUsersForm.getAction().equalsIgnoreCase("backLink")) {
            request.removeAttribute("manageUsersForm");
            return new ActionForward("/WEB-INF/jsp/Affiliate/AffiliateOption.jsp");
        }

        manageUsersForm.setCurrUsers(ManageUsersDAO.GetCurrUsers());
        request.setAttribute("manageUsersForm",manageUsersForm);
        return new ActionForward("/WEB-INF/jsp/Manager/ManageUsers.jsp");
    }
}
