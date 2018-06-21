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
public class ControlContactManagersAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ControlContactManagersForm controlContactManagersForm = (ControlContactManagersForm) form;
        controlContactManagersForm.setExecuted("true");
        
        try {
            
            if (controlContactManagersForm.getAction().equalsIgnoreCase("Search")) {
                controlContactManagersForm.setPageNum(0);

            }
            else if (controlContactManagersForm.getAction().equalsIgnoreCase("First")) {
                controlContactManagersForm.setPageNum(0);

            } else if (controlContactManagersForm.getAction().equalsIgnoreCase("Last")) {
                controlContactManagersForm.setPageNum((controlContactManagersForm.getCount() / 100));

            } else if (controlContactManagersForm.getAction().equalsIgnoreCase("Prev")) {
                if (controlContactManagersForm.getPageNum() > 0) {
                    controlContactManagersForm.setPageNum(((Integer) controlContactManagersForm.getPageNum()) - 1);
                }

            } else if (controlContactManagersForm.getAction().equalsIgnoreCase("Next")) {
                if (controlContactManagersForm.getPageNum() < controlContactManagersForm.getCount() / 100) {
                    controlContactManagersForm.setPageNum(((Integer) controlContactManagersForm.getPageNum()) + 1);
                }

            }            
            else if (controlContactManagersForm.getAction().equalsIgnoreCase("Reset")) {
                controlContactManagersForm.reset();
                
            } 
            else if (controlContactManagersForm.getAction().equalsIgnoreCase("newManager")) {
                
                ControlNewContactManagerForm controlNewContactManagerForm = new ControlNewContactManagerForm();
                request.setAttribute("controlNewContactManagerForm", controlNewContactManagerForm);
                return new ActionForward("/WEB-INF/jsp/Contacts/ControlNewContactManager.jsp");
                
            } 
            else if (controlContactManagersForm.getAction().equalsIgnoreCase("manageMan")) {
              
                ControlContactManagerActiveForm controlContactManagerActiveForm = new ControlContactManagerActiveForm();
                controlContactManagerActiveForm.setAdminId(controlContactManagersForm.getAdminId());
                
                ControlContactManagerActiveDAO.GetContactManInfo(controlContactManagerActiveForm);
                
                request.setAttribute("controlContactManagerActiveForm", controlContactManagerActiveForm);
                return new ActionForward("/WEB-INF/jsp/Contacts/ControlContactManagerActive.jsp");
                        
            }
            
            
            
            //    search PAULA for associate managers
            
            HashMap<String, Object> searchResults = ControlContactManagersDAO.SearchPaula(controlContactManagersForm);  
            request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
            controlContactManagersForm.setCount((Integer) searchResults.get("COUNT"));

            if (controlContactManagersForm.getCount() > 0) {
                controlContactManagersForm.setDisplayPageNum(Integer.toString(controlContactManagersForm.getPageNum() + 1));
                controlContactManagersForm.setDisplayPageCount(Integer.toString((controlContactManagersForm.getCount() / 100) + 1));
            }

            request.getSession().setAttribute("controlContactManagersForm", controlContactManagersForm);
            return new ActionForward("/WEB-INF/jsp/Contacts/ControlContactManagers.jsp");
            
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(System.out);
            ex.printStackTrace(pw);
            request.setAttribute("errorMsg", "Exception occurred.");
            request.setAttribute("controlContactManagersForm", controlContactManagersForm);

            return new ActionForward("/WEB-INF/jsp/Contacts/ControlContactManagers.jsp");
        }
    }
}
