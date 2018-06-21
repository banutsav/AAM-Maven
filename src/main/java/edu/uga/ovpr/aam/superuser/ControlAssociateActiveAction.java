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
public class ControlAssociateActiveAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ControlAssociateActiveForm controlAssociateActiveForm = (ControlAssociateActiveForm) form;

        if (controlAssociateActiveForm.getAction().equalsIgnoreCase("back")) 
        {
            if (request.getSession().getAttribute("controlAssociatesForm") == null) {
                
                ControlAssociatesForm controlAssociatesForm = new ControlAssociatesForm();
                request.getSession().setAttribute("controlAssociatesForm", controlAssociatesForm);
            
            } else {
                
                if (!((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")).getExecuted().isEmpty()) {
                    HashMap<String, Object> searchResults = ControlAssociatesDAO.SearchPaula(((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    ((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")).setCount((Integer) searchResults.get("COUNT"));

                    if (((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")).getCount() > 0) {
                        ((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")).setDisplayPageNum(Integer.toString(((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")).getPageNum() + 1));
                        ((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")).setDisplayPageCount(Integer.toString((((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")).getCount() / 100) + 1));
                    }
                }
            }
            return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociates.jsp");
        } 
        
        else if (controlAssociateActiveForm.getAction().equalsIgnoreCase("activate")) {
            ControlAssociateActiveDAO.ActivateAff(controlAssociateActiveForm);
            request.setAttribute("message", "Activated associate.");
        }
        
        else if (controlAssociateActiveForm.getAction().equalsIgnoreCase("deactivate")) {
            ControlAssociateActiveDAO.DeactivateAff(controlAssociateActiveForm);
            ControlAssociateActiveDAO.removeAssociateFromLDAP(controlAssociateActiveForm.getAffId());
            request.setAttribute("message", "Deactivated associate.");
        }

        controlAssociateActiveForm.setAction("");
        ControlAssociateActiveDAO.GetAssociateInfo(controlAssociateActiveForm);
        request.setAttribute("controlAssociateActiveForm", controlAssociateActiveForm);
        return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociateActive.jsp");
    }
}
