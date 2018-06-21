/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.contacts;

import edu.uga.ovpr.aam.superuser.*;
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
public class ControlNewContactManagerAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ControlNewContactManagerForm controlNewContactManagerForm = (ControlNewContactManagerForm) form;

        try {
            
            if (controlNewContactManagerForm.getAction().equalsIgnoreCase("Search")) {
                controlNewContactManagerForm.setPageNum(0);
                HashMap<String, Object> searchResults = ControlNewContactManagerDAO.SearchPaula(controlNewContactManagerForm);
                controlNewContactManagerForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewContactManagerForm.setCount((Integer) searchResults.get("COUNT"));

            }
            else if (controlNewContactManagerForm.getAction().equalsIgnoreCase("First")) {
                controlNewContactManagerForm.setPageNum(0);
                HashMap<String, Object> searchResults = ControlNewContactManagerDAO.SearchPaula(controlNewContactManagerForm);
                controlNewContactManagerForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewContactManagerForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlNewContactManagerForm.getAction().equalsIgnoreCase("Last")) {
                
                controlNewContactManagerForm.setPageNum((controlNewContactManagerForm.getCount() / 100));
                HashMap<String, Object> searchResults = ControlNewContactManagerDAO.SearchPaula(controlNewContactManagerForm);
                controlNewContactManagerForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewContactManagerForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlNewContactManagerForm.getAction().equalsIgnoreCase("Prev")) {
                if (controlNewContactManagerForm.getPageNum() > 0) {
                    controlNewContactManagerForm.setPageNum(((Integer) controlNewContactManagerForm.getPageNum()) - 1);
                }
                
                HashMap<String, Object> searchResults = ControlNewContactManagerDAO.SearchPaula(controlNewContactManagerForm);
                controlNewContactManagerForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewContactManagerForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (controlNewContactManagerForm.getAction().equalsIgnoreCase("Next")) {
                if (controlNewContactManagerForm.getPageNum() < controlNewContactManagerForm.getCount() / 100) {
                    controlNewContactManagerForm.setPageNum(((Integer) controlNewContactManagerForm.getPageNum()) + 1);
                }
                
                HashMap<String, Object> searchResults = ControlNewContactManagerDAO.SearchPaula(controlNewContactManagerForm);
                controlNewContactManagerForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                controlNewContactManagerForm.setCount((Integer) searchResults.get("COUNT"));
            } 
            else if (controlNewContactManagerForm.getAction().equalsIgnoreCase("Reset")) {
                controlNewContactManagerForm.reset();
            } 
            else if (controlNewContactManagerForm.getAction().equalsIgnoreCase("newManager")) {
                
                ControlNewContactManagerDAO.AddNewManager(controlNewContactManagerForm, ses.getUserCan());
                request.setAttribute("message", "Successfully created new manager.");
                
                if (request.getSession().getAttribute("controlContactManagersForm") == null) {
                    
                    ControlContactManagersForm controlContactManagersForm = new ControlContactManagersForm();
                    request.getSession().setAttribute("controlContactManagersForm", controlContactManagersForm);
                } 
                else if (!((ControlContactManagersForm) request.getSession().getAttribute("controlContactManagersForm")).getExecuted().isEmpty()) {
                    
                    HashMap<String, Object> searchResults = ControlContactManagersDAO.SearchPaula(((ControlContactManagersForm) request.getSession().getAttribute("controlContactManagersForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    ((ControlContactManagersForm) request.getSession().getAttribute("controlContactManagersForm")).setCount((Integer) searchResults.get("COUNT"));
                }
                
                return new ActionForward("/WEB-INF/jsp/Contacts/ControlContactManagers.jsp");
                        
            } 
            else if (controlNewContactManagerForm.getAction().equalsIgnoreCase("back")) {
                
                if (request.getSession().getAttribute("controlContactManagersForm") == null) {
                    ControlContactManagersForm controlContactManagersForm = new ControlContactManagersForm();
                    request.getSession().setAttribute("controlContactManagersForm", controlContactManagersForm);
                } else if (!((ControlContactManagersForm) request.getSession().getAttribute("controlContactManagersForm")).getExecuted().isEmpty()) {
                    HashMap<String, Object> searchResults = ControlContactManagersDAO.SearchPaula(((ControlContactManagersForm) request.getSession().getAttribute("controlContactManagersForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    ((ControlContactManagersForm) request.getSession().getAttribute("controlContactManagersForm")).setCount((Integer) searchResults.get("COUNT"));
                }
                return new ActionForward("/WEB-INF/jsp/Contacts/ControlContactManagers.jsp");
            }

            if (controlNewContactManagerForm.getCount() > 0) {
                controlNewContactManagerForm.setDisplayPageNum(Integer.toString(controlNewContactManagerForm.getPageNum() + 1));
                controlNewContactManagerForm.setDisplayPageCount(Integer.toString((controlNewContactManagerForm.getCount() / 100) + 1));
            }
            
            request.setAttribute("controlNewContactManagerForm", controlNewContactManagerForm);
            return new ActionForward("/WEB-INF/jsp/Contacts/ControlNewContactManager.jsp");
            
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(System.out);
            ex.printStackTrace(pw);
            request.setAttribute("errorMsg", "Exception occurred.");
            request.setAttribute("controlNewContactManagerForm", controlNewContactManagerForm);
            return new ActionForward("/WEB-INF/jsp/Contacts/ControlNewContactManager.jsp");
        }
    }
}
