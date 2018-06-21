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
public class ControlAssociateManActiveAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ControlAssociateManActiveForm controlAssociateManActiveForm = (ControlAssociateManActiveForm) form;

        //System.out.println("CANNUM = '" + controlManActiveForm.getCannum() + "','" + controlManActiveForm.getTargPurpId() + "','" + controlManActiveForm.getTargProxyId() + "'");

        if (controlAssociateManActiveForm.getAction().equalsIgnoreCase("back")) {
            
            controlAssociateManActiveForm.reset();
            
            if (request.getSession().getAttribute("controlAssociateManagersForm") == null) 
            {
                ControlAssociateManagersForm controlAssociateManagersForm = new ControlAssociateManagersForm();
                request.getSession().setAttribute("controlAssociateManagersForm", controlAssociateManagersForm);
            
            } else {
                
                if (!((ControlAssociateManagersForm) request.getSession().getAttribute("controlAssociateManagersForm")).getExecuted().isEmpty()) {
                    HashMap<String, Object> searchResults = ControlAssociateManagersDAO.SearchPaula(((ControlAssociateManagersForm) request.getSession().getAttribute("controlAssociateManagersForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    ((ControlAssociateManagersForm) request.getSession().getAttribute("controlAssociateManagersForm")).setCount((Integer) searchResults.get("COUNT"));
                }
            
            }
            
            return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociateManagers.jsp");
        } 
        
        else if (controlAssociateManActiveForm.getAction().equalsIgnoreCase("activate")) {
        
            ControlAssociateManActiveDAO.ActivateManager(controlAssociateManActiveForm);
            request.setAttribute("message","Successfully activated manager.");
        
        } 
        
        else if (controlAssociateManActiveForm.getAction().equalsIgnoreCase("deactivate")) {
        
            ControlAssociateManActiveDAO.DeactivateManager(controlAssociateManActiveForm);
            request.setAttribute("message","Successfully deactivated manager.");
        
        } 
       

        controlAssociateManActiveForm.setAction("");
        ControlAssociateManActiveDAO.GetAssociateManInfo(controlAssociateManActiveForm);
        request.setAttribute("controlAssociateManActiveForm", controlAssociateManActiveForm);
        return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociateManActive.jsp");
    }
}
