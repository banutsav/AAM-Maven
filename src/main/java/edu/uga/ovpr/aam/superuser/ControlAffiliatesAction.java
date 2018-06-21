/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.superuser;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
import java.io.PrintWriter;
import java.io.StringWriter;
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
public class ControlAffiliatesAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ControlAffiliatesForm controlAffiliatesForm = (ControlAffiliatesForm) form;
        controlAffiliatesForm.setExecuted("true");
        try {
            if (controlAffiliatesForm.getAction().equalsIgnoreCase("Search")) {
                controlAffiliatesForm.setPageNum(0);

            } else if (controlAffiliatesForm.getAction().equalsIgnoreCase("First")) {
                controlAffiliatesForm.setPageNum(0);
            } else if (controlAffiliatesForm.getAction().equalsIgnoreCase("Last")) {
                controlAffiliatesForm.setPageNum((controlAffiliatesForm.getCount() / 100));
            } else if (controlAffiliatesForm.getAction().equalsIgnoreCase("Prev")) {
                if (controlAffiliatesForm.getPageNum() > 0) {
                    controlAffiliatesForm.setPageNum(((Integer) controlAffiliatesForm.getPageNum()) - 1);
                }
            } else if (controlAffiliatesForm.getAction().equalsIgnoreCase("Next")) {
                if (controlAffiliatesForm.getPageNum() < controlAffiliatesForm.getCount() / 100) {
                    controlAffiliatesForm.setPageNum(((Integer) controlAffiliatesForm.getPageNum()) + 1);
                }
            } else if (controlAffiliatesForm.getAction().equalsIgnoreCase("Reset")) {
                controlAffiliatesForm.reset();
                request.getSession().setAttribute("controlAffiliatesForm", controlAffiliatesForm);
                return new ActionForward("/WEB-INF/jsp/Superuser/ControlAffiliates.jsp");
            } else if (controlAffiliatesForm.getAction().equalsIgnoreCase("manageAff")) {
                
                ControlAffActiveForm controlAffActiveForm = new ControlAffActiveForm();
                controlAffActiveForm.setAffId(controlAffiliatesForm.getAffId());
                ControlAffActiveDAO.GetAffInfo(controlAffActiveForm);
                request.setAttribute("controlAffActiveForm", controlAffActiveForm);
                return new ActionForward("/WEB-INF/jsp/Superuser/ControlAffActive.jsp");
            }

            HashMap<String, Object> searchResults = ControlAffiliatesDAO.SearchPaula(controlAffiliatesForm);
            request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
            controlAffiliatesForm.setCount((Integer) searchResults.get("COUNT"));

            if (controlAffiliatesForm.getCount() > 0) {
                controlAffiliatesForm.setDisplayPageNum(Integer.toString(controlAffiliatesForm.getPageNum() + 1));
                controlAffiliatesForm.setDisplayPageCount(Integer.toString((controlAffiliatesForm.getCount() / 100) + 1));
            }

            request.getSession().setAttribute("controlAffiliatesForm", controlAffiliatesForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlAffiliates.jsp");
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(System.out);
            ex.printStackTrace(pw);
            request.setAttribute("errorMsg", "Exception occurred.");
            request.getSession().setAttribute("controlAffiliatesForm", controlAffiliatesForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlAffiliates.jsp");
        }
    }
}
