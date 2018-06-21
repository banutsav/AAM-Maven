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
public class ControlNewAssociateManAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ControlNewAssociateManForm controlNewAssociateManForm = (ControlNewAssociateManForm) form;

        //System.out.println("DEBUG: action = " + controlNewAssociateManForm.getAction());
        
        try {
            if (controlNewAssociateManForm.getAction().equalsIgnoreCase("Search")) {
                controlNewAssociateManForm.setPageNum(0);
                HashMap<String, Object> searchResults = ControlNewAssociateManDAO.SearchPaula(controlNewAssociateManForm);
                controlNewAssociateManForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewAssociateManForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlNewAssociateManForm.getAction().equalsIgnoreCase("First")) {
                controlNewAssociateManForm.setPageNum(0);
                HashMap<String, Object> searchResults = ControlNewAssociateManDAO.SearchPaula(controlNewAssociateManForm);
                controlNewAssociateManForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewAssociateManForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlNewAssociateManForm.getAction().equalsIgnoreCase("Last")) {
                
                controlNewAssociateManForm.setPageNum((controlNewAssociateManForm.getCount() / 100));
                HashMap<String, Object> searchResults = ControlNewAssociateManDAO.SearchPaula(controlNewAssociateManForm);
                controlNewAssociateManForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewAssociateManForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlNewAssociateManForm.getAction().equalsIgnoreCase("Prev")) {
                if (controlNewAssociateManForm.getPageNum() > 0) {
                    controlNewAssociateManForm.setPageNum(((Integer) controlNewAssociateManForm.getPageNum()) - 1);
                }
                
                HashMap<String, Object> searchResults = ControlNewAssociateManDAO.SearchPaula(controlNewAssociateManForm);
                controlNewAssociateManForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewAssociateManForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlNewAssociateManForm.getAction().equalsIgnoreCase("Next")) {
                if (controlNewAssociateManForm.getPageNum() < controlNewAssociateManForm.getCount() / 100) {
                    controlNewAssociateManForm.setPageNum(((Integer) controlNewAssociateManForm.getPageNum()) + 1);
                }
                
                HashMap<String, Object> searchResults = ControlNewAssociateManDAO.SearchPaula(controlNewAssociateManForm);
                controlNewAssociateManForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewAssociateManForm.setCount((Integer) searchResults.get("COUNT"));
            } else if (controlNewAssociateManForm.getAction().equalsIgnoreCase("Reset")) {
                controlNewAssociateManForm.reset();
            } else if (controlNewAssociateManForm.getAction().equalsIgnoreCase("newManager")) {
                
                ControlNewAssociateManDAO.AddNewManager(controlNewAssociateManForm, ses.getUserCan());
                request.setAttribute("message", "Successfully created new manager.");
                
                if (request.getSession().getAttribute("controlAssociateManagersForm") == null) {
                    ControlAssociateManagersForm controlAssociateManagersForm = new ControlAssociateManagersForm();
                    request.getSession().setAttribute("controlAssociateManagersForm", controlAssociateManagersForm);
                } else if (!((ControlAssociateManagersForm) request.getSession().getAttribute("controlAssociateManagersForm")).getExecuted().isEmpty()) {
                    HashMap<String, Object> searchResults = ControlAssociateManagersDAO.SearchPaula(((ControlAssociateManagersForm) request.getSession().getAttribute("controlAssociateManagersForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    //controlManagersForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                    ((ControlAssociateManagersForm) request.getSession().getAttribute("controlAssociateManagersForm")).setCount((Integer) searchResults.get("COUNT"));
                }
                return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociateManagers.jsp");
            
            } else if (controlNewAssociateManForm.getAction().equalsIgnoreCase("back")) {
                
                if (request.getSession().getAttribute("controlAssociateManagersForm") == null) {
                    ControlAssociateManagersForm controlAssociateManagersForm = new ControlAssociateManagersForm();
                    request.getSession().setAttribute("controlAssociateManagersForm", controlAssociateManagersForm);
                } else if (!((ControlAssociateManagersForm) request.getSession().getAttribute("controlAssociateManagersForm")).getExecuted().isEmpty()) {
                    HashMap<String, Object> searchResults = ControlAssociateManagersDAO.SearchPaula(((ControlAssociateManagersForm) request.getSession().getAttribute("controlAssociateManagersForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    //controlManagersForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                    ((ControlAssociateManagersForm) request.getSession().getAttribute("controlAssociateManagersForm")).setCount((Integer) searchResults.get("COUNT"));
                }
                return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociateManagers.jsp");
            }

            if (controlNewAssociateManForm.getCount() > 0) {
                controlNewAssociateManForm.setDisplayPageNum(Integer.toString(controlNewAssociateManForm.getPageNum() + 1));
                controlNewAssociateManForm.setDisplayPageCount(Integer.toString((controlNewAssociateManForm.getCount() / 100) + 1));
            }

            request.setAttribute("controlNewAssociateManForm", controlNewAssociateManForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlNewAssociateMan.jsp");
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(System.out);
            ex.printStackTrace(pw);
            request.setAttribute("errorMsg", "Exception occurred.");
            request.setAttribute("controlNewAssociateManForm", controlNewAssociateManForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlNewAssociateMan.jsp");
        }
    }
}
