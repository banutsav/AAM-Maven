/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.contacts;

import edu.uga.ovpr.aam.superuser.*;
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
public class ControlContactManagerActiveAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ControlContactManagerActiveForm controlContactManagerActiveForm = (ControlContactManagerActiveForm) form;

        if (controlContactManagerActiveForm.getAction().equalsIgnoreCase("back")) {
            
            controlContactManagerActiveForm.reset();
            
            if (request.getSession().getAttribute("controlContactManagersForm") == null) 
            {
                ControlContactManagersForm controlContactManagersForm = new ControlContactManagersForm();
                request.getSession().setAttribute("controlContactManagersForm", controlContactManagersForm);
            
            } else {
                
                if (!((ControlContactManagersForm) request.getSession().getAttribute("controlContactManagersForm")).getExecuted().isEmpty()) {
                    HashMap<String, Object> searchResults = ControlContactManagersDAO.SearchPaula(((ControlContactManagersForm) request.getSession().getAttribute("controlContactManagersForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    ((ControlContactManagersForm) request.getSession().getAttribute("controlContactManagersForm")).setCount((Integer) searchResults.get("COUNT"));
                }
            
            }
            
            return new ActionForward("/WEB-INF/jsp/Contacts/ControlContactManagers.jsp");
        }
        else if (controlContactManagerActiveForm.getAction().equalsIgnoreCase("activate")) {
        
            ControlContactManagerActiveDAO.ActivateManager(controlContactManagerActiveForm);
            request.setAttribute("message","Successfully activated manager.");
        
        } 
        else if (controlContactManagerActiveForm.getAction().equalsIgnoreCase("deactivate")) {
        
            ControlContactManagerActiveDAO.DeactivateManager(controlContactManagerActiveForm);
            request.setAttribute("message","Successfully deactivated manager.");
        
        } 
       
        controlContactManagerActiveForm.setAction("");
        ControlContactManagerActiveDAO.GetContactManInfo(controlContactManagerActiveForm);
                
        request.setAttribute("controlContactManagerActiveForm", controlContactManagerActiveForm);
        return new ActionForward("/WEB-INF/jsp/Contacts/ControlContactManagerActive.jsp");
    }
}
