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
public class ControlManAddProxyAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ControlManAddProxyForm controlManAddProxyForm = (ControlManAddProxyForm) form;
        controlManAddProxyForm.clearResults();
        ControlManAddProxyDAO.InitializeData(controlManAddProxyForm);

        try {
            if (controlManAddProxyForm.getAction().equalsIgnoreCase("Search")) {
                controlManAddProxyForm.setPageNum(0);
                HashMap<String, Object> searchResults = ControlManAddProxyDAO.SearchPaula(controlManAddProxyForm);
                controlManAddProxyForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlManAddProxyForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlManAddProxyForm.getAction().equalsIgnoreCase("First")) {
                controlManAddProxyForm.setPageNum(0);
                HashMap<String, Object> searchResults = ControlManAddProxyDAO.SearchPaula(controlManAddProxyForm);
                controlManAddProxyForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlManAddProxyForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlManAddProxyForm.getAction().equalsIgnoreCase("Last")) {
                controlManAddProxyForm.setPageNum((controlManAddProxyForm.getCount() / 100));
                //System.out.println("PageNum = " + controlManAddProxyForm.getPageNum());
                HashMap<String, Object> searchResults = ControlManAddProxyDAO.SearchPaula(controlManAddProxyForm);
                controlManAddProxyForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlManAddProxyForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlManAddProxyForm.getAction().equalsIgnoreCase("Prev")) {
                if (controlManAddProxyForm.getPageNum() > 0) {
                    controlManAddProxyForm.setPageNum(((Integer) controlManAddProxyForm.getPageNum()) - 1);
                }
                //System.out.println("PageNum = " + controlManAddProxyForm.getPageNum());
                HashMap<String, Object> searchResults = ControlManAddProxyDAO.SearchPaula(controlManAddProxyForm);
                controlManAddProxyForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlManAddProxyForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlManAddProxyForm.getAction().equalsIgnoreCase("Next")) {
                if (controlManAddProxyForm.getPageNum() < controlManAddProxyForm.getCount() / 100) {
                    controlManAddProxyForm.setPageNum(((Integer) controlManAddProxyForm.getPageNum()) + 1);
                }
                //System.out.println("PageNum = " + controlManAddProxyForm.getPageNum());
                HashMap<String, Object> searchResults = ControlManAddProxyDAO.SearchPaula(controlManAddProxyForm);
                controlManAddProxyForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlManAddProxyForm.setCount((Integer) searchResults.get("COUNT"));
            } else if (controlManAddProxyForm.getAction().equalsIgnoreCase("Reset")) {
                controlManAddProxyForm.reset();
            } else if (controlManAddProxyForm.getAction().equalsIgnoreCase("addProxy")) {
                String message = ControlManAddProxyDAO.AddProxy(controlManAddProxyForm);
                //System.out.println("Add Proxy Message \"" + message + "\"\n");
                if (message.isEmpty()) {
                    request.setAttribute("message", "Successfully added new Proxy Manager.");
                    ControlManActiveForm controlManActiveForm = new ControlManActiveForm();
                    controlManActiveForm.setAdminId(controlManAddProxyForm.getAdminId());
                    ControlManActiveDAO.GetManInfo(controlManActiveForm);
                    request.setAttribute("controlManActiveForm", controlManActiveForm);
                    return new ActionForward("/WEB-INF/jsp/Superuser/ControlManActive.jsp");
                } else {
                    request.setAttribute("errorMsg", message);
                    HashMap<String, Object> searchResults = ControlManAddProxyDAO.SearchPaula(controlManAddProxyForm);
                    controlManAddProxyForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                    controlManAddProxyForm.setCount((Integer) searchResults.get("COUNT"));
                }
            } else if (controlManAddProxyForm.getAction().equalsIgnoreCase("back")) {
                ControlManActiveForm controlManActiveForm = new ControlManActiveForm();
                controlManActiveForm.setAdminId(controlManAddProxyForm.getAdminId());
                ControlManActiveDAO.GetManInfo(controlManActiveForm);
                request.setAttribute("controlManActiveForm", controlManActiveForm);
                return new ActionForward("/WEB-INF/jsp/Superuser/ControlManActive.jsp");
            }

            if (controlManAddProxyForm.getCount() > 0) {
                controlManAddProxyForm.setDisplayPageNum(Integer.toString(controlManAddProxyForm.getPageNum() + 1));
                controlManAddProxyForm.setDisplayPageCount(Integer.toString((controlManAddProxyForm.getCount() / 100) + 1));
            }

            ControlManAddProxyDAO.GetPurposes(controlManAddProxyForm);
            request.setAttribute("controlManAddProxyForm", controlManAddProxyForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlManAddProxy.jsp");
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(System.out);
            ex.printStackTrace(pw);
            request.setAttribute("errorMsg", "Exception occurred.");
            request.setAttribute("controlManAddProxyForm", controlManAddProxyForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlManAddProxy.jsp");
        }
    }
}
