/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.superuser;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
import java.util.ArrayList;
import java.util.HashMap;
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
public class ControlAffActiveAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ControlAffActiveForm controlAffActiveForm = (ControlAffActiveForm) form;

        if (controlAffActiveForm.getAction().equalsIgnoreCase("back")) {
            if (request.getSession().getAttribute("controlAffiliatesForm") == null) {
                ControlAffiliatesForm controlAffiliatesForm = new ControlAffiliatesForm();
                request.getSession().setAttribute("controlAffiliatesForm", controlAffiliatesForm);
            } else {
                if (!((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")).getExecuted().isEmpty()) {
                    HashMap<String, Object> searchResults = ControlAffiliatesDAO.SearchPaula(((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    ((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")).setCount((Integer) searchResults.get("COUNT"));

                    if (((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")).getCount() > 0) {
                        ((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")).setDisplayPageNum(Integer.toString(((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")).getPageNum() + 1));
                        ((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")).setDisplayPageCount(Integer.toString((((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")).getCount() / 100) + 1));
                    }
                }
            }
            return new ActionForward("/WEB-INF/jsp/Superuser/ControlAffiliates.jsp");
        } else if (controlAffActiveForm.getAction().equalsIgnoreCase("activate")) {
            ControlAffActiveDAO.ActivateAff(controlAffActiveForm);
            request.setAttribute("message", "Activated affiliate.");
        } else if (controlAffActiveForm.getAction().equalsIgnoreCase("deactivate")) {
            ControlAffActiveDAO.DeactivateAff(controlAffActiveForm);
            request.setAttribute("message", "Deactivated affiliate.");
        } else if (controlAffActiveForm.getAction().equalsIgnoreCase("changePurpose")) {
            ControlAffActiveDAO.UpdatePurpose(controlAffActiveForm);
            request.setAttribute("message", "Changed purpose.");
        }

        ControlAffActiveDAO.GetAffInfo(controlAffActiveForm);
        request.setAttribute("controlAffActiveForm", controlAffActiveForm);
        return new ActionForward("/WEB-INF/jsp/Superuser/ControlAffActive.jsp");
    }
}
