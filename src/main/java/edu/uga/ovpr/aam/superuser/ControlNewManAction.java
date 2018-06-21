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
public class ControlNewManAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ControlNewManForm controlNewManForm = (ControlNewManForm) form;

        try {
            if (controlNewManForm.getAction().equalsIgnoreCase("Search")) {
                controlNewManForm.setPageNum(0);
                HashMap<String, Object> searchResults = ControlNewManDAO.SearchPaula(controlNewManForm);
                controlNewManForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewManForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlNewManForm.getAction().equalsIgnoreCase("First")) {
                controlNewManForm.setPageNum(0);
                HashMap<String, Object> searchResults = ControlNewManDAO.SearchPaula(controlNewManForm);
                controlNewManForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewManForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlNewManForm.getAction().equalsIgnoreCase("Last")) {
                controlNewManForm.setPageNum((controlNewManForm.getCount() / 100));
                //System.out.println("PageNum = " + controlNewManForm.getPageNum());
                HashMap<String, Object> searchResults = ControlNewManDAO.SearchPaula(controlNewManForm);
                controlNewManForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewManForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlNewManForm.getAction().equalsIgnoreCase("Prev")) {
                if (controlNewManForm.getPageNum() > 0) {
                    controlNewManForm.setPageNum(((Integer) controlNewManForm.getPageNum()) - 1);
                }
                //System.out.println("PageNum = " + controlNewManForm.getPageNum());
                HashMap<String, Object> searchResults = ControlNewManDAO.SearchPaula(controlNewManForm);
                controlNewManForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewManForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlNewManForm.getAction().equalsIgnoreCase("Next")) {
                if (controlNewManForm.getPageNum() < controlNewManForm.getCount() / 100) {
                    controlNewManForm.setPageNum(((Integer) controlNewManForm.getPageNum()) + 1);
                }
                //System.out.println("PageNum = " + controlNewManForm.getPageNum());
                HashMap<String, Object> searchResults = ControlNewManDAO.SearchPaula(controlNewManForm);
                controlNewManForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewManForm.setCount((Integer) searchResults.get("COUNT"));
            } else if (controlNewManForm.getAction().equalsIgnoreCase("Reset")) {
                controlNewManForm.reset();
            } else if (controlNewManForm.getAction().equalsIgnoreCase("newManager")) {
                ControlNewManDAO.AddNewManager(controlNewManForm);
                request.setAttribute("message", "Successfully created new manager.");
                if (request.getSession().getAttribute("controlManagersForm") == null) {
                    ControlManagersForm controlManagersForm = new ControlManagersForm();
                    request.getSession().setAttribute("controlManagersForm", controlManagersForm);
                } else if (!((ControlManagersForm) request.getSession().getAttribute("controlManagersForm")).getExecuted().isEmpty()) {
                    HashMap<String, Object> searchResults = ControlManagersDAO.SearchPaula(((ControlManagersForm) request.getSession().getAttribute("controlManagersForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    //controlManagersForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                    ((ControlManagersForm) request.getSession().getAttribute("controlManagersForm")).setCount((Integer) searchResults.get("COUNT"));
                }
                return new ActionForward("/WEB-INF/jsp/Superuser/ControlManagers.jsp");
            } else if (controlNewManForm.getAction().equalsIgnoreCase("back")) {
                if (request.getSession().getAttribute("controlManagersForm") == null) {
                    ControlManagersForm controlManagersForm = new ControlManagersForm();
                    request.getSession().setAttribute("controlManagersForm", controlManagersForm);
                } else if (!((ControlManagersForm) request.getSession().getAttribute("controlManagersForm")).getExecuted().isEmpty()) {
                    HashMap<String, Object> searchResults = ControlManagersDAO.SearchPaula(((ControlManagersForm) request.getSession().getAttribute("controlManagersForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    //controlManagersForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                    ((ControlManagersForm) request.getSession().getAttribute("controlManagersForm")).setCount((Integer) searchResults.get("COUNT"));
                }
                return new ActionForward("/WEB-INF/jsp/Superuser/ControlManagers.jsp");
            }

            if (controlNewManForm.getCount() > 0) {
                controlNewManForm.setDisplayPageNum(Integer.toString(controlNewManForm.getPageNum() + 1));
                controlNewManForm.setDisplayPageCount(Integer.toString((controlNewManForm.getCount() / 100) + 1));
            }

            request.setAttribute("controlNewManForm", controlNewManForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlNewMan.jsp");
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(System.out);
            ex.printStackTrace(pw);
            request.setAttribute("errorMsg", "Exception occurred.");
            request.setAttribute("controlNewManForm", controlNewManForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlNewMan.jsp");
        }
    }
}
