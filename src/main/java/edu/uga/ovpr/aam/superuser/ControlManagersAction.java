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
public class ControlManagersAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ControlManagersForm controlManagersForm = (ControlManagersForm) form;
        controlManagersForm.setExecuted("true");
        try {
            if (controlManagersForm.getAction().equalsIgnoreCase("Search")) {
                controlManagersForm.setPageNum(0);
                //HashMap<String, Object> searchResults = ControlManagersDAO.SearchPaula(controlManagersForm);
                //controlManagersForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                //controlManagersForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlManagersForm.getAction().equalsIgnoreCase("First")) {
                controlManagersForm.setPageNum(0);
                HashMap<String, Object> searchResults = ControlManagersDAO.SearchPaula(controlManagersForm);
                //controlManagersForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                //controlManagersForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlManagersForm.getAction().equalsIgnoreCase("Last")) {
                controlManagersForm.setPageNum((controlManagersForm.getCount() / 100));
                //System.out.println("PageNum = " + controlManagersForm.getPageNum());
                //HashMap<String, Object> searchResults = ControlManagersDAO.SearchPaula(controlManagersForm);
                //controlManagersForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                //controlManagersForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlManagersForm.getAction().equalsIgnoreCase("Prev")) {
                if (controlManagersForm.getPageNum() > 0) {
                    controlManagersForm.setPageNum(((Integer) controlManagersForm.getPageNum()) - 1);
                }
                //System.out.println("PageNum = " + controlManagersForm.getPageNum());
                //HashMap<String, Object> searchResults = ControlManagersDAO.SearchPaula(controlManagersForm);
                //controlManagersForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                //controlManagersForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlManagersForm.getAction().equalsIgnoreCase("Next")) {
                if (controlManagersForm.getPageNum() < controlManagersForm.getCount() / 100) {
                    controlManagersForm.setPageNum(((Integer) controlManagersForm.getPageNum()) + 1);
                }
                //System.out.println("PageNum = " + controlManagersForm.getPageNum());
                //HashMap<String, Object> searchResults = ControlManagersDAO.SearchPaula(controlManagersForm);
                //controlManagersForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                //controlManagersForm.setCount((Integer) searchResults.get("COUNT"));
            } else if (controlManagersForm.getAction().equalsIgnoreCase("Reset")) {
                controlManagersForm.reset();
            } else if (controlManagersForm.getAction().equalsIgnoreCase("newManager")) {
                ControlNewManForm controlNewManForm = new ControlNewManForm();
                request.setAttribute("controlNewManForm", controlNewManForm);
                return new ActionForward("/WEB-INF/jsp/Superuser/ControlNewMan.jsp");
            } else if (controlManagersForm.getAction().equalsIgnoreCase("manageMan")) {
                ControlManActiveForm controlManActiveForm = new ControlManActiveForm();
                controlManActiveForm.setAdminId(controlManagersForm.getAdminId());
                ControlManActiveDAO.GetManInfo(controlManActiveForm);
                request.setAttribute("controlManActiveForm", controlManActiveForm);
                return new ActionForward("/WEB-INF/jsp/Superuser/ControlManActive.jsp");
            }

            HashMap<String, Object> searchResults = ControlManagersDAO.SearchPaula(controlManagersForm);
            request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
            //controlManagersForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
            controlManagersForm.setCount((Integer) searchResults.get("COUNT"));

            if (controlManagersForm.getCount() > 0) {
                controlManagersForm.setDisplayPageNum(Integer.toString(controlManagersForm.getPageNum() + 1));
                controlManagersForm.setDisplayPageCount(Integer.toString((controlManagersForm.getCount() / 100) + 1));
            }

            request.getSession().setAttribute("controlManagersForm", controlManagersForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlManagers.jsp");
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(System.out);
            ex.printStackTrace(pw);
            request.setAttribute("errorMsg", "Exception occurred.");
            request.setAttribute("controlManagersForm", controlManagersForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlManagers.jsp");
        }
    }
}
