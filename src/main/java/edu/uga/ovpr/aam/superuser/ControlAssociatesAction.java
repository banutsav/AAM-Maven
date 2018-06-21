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
public class ControlAssociatesAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ControlAssociatesForm controlAssociatesForm = (ControlAssociatesForm) form;
        controlAssociatesForm.setExecuted("true");
        
        try {
            if (controlAssociatesForm.getAction().equalsIgnoreCase("Search")) {
                controlAssociatesForm.setPageNum(0);

            } else if (controlAssociatesForm.getAction().equalsIgnoreCase("First")) {
                controlAssociatesForm.setPageNum(0);
            } else if (controlAssociatesForm.getAction().equalsIgnoreCase("Last")) {
                controlAssociatesForm.setPageNum((controlAssociatesForm.getCount() / 100));
            } else if (controlAssociatesForm.getAction().equalsIgnoreCase("Prev")) {
                if (controlAssociatesForm.getPageNum() > 0) {
                    controlAssociatesForm.setPageNum(((Integer) controlAssociatesForm.getPageNum()) - 1);
                }
            } else if (controlAssociatesForm.getAction().equalsIgnoreCase("Next")) {
                if (controlAssociatesForm.getPageNum() < controlAssociatesForm.getCount() / 100) {
                    controlAssociatesForm.setPageNum(((Integer) controlAssociatesForm.getPageNum()) + 1);
                }
            } else if (controlAssociatesForm.getAction().equalsIgnoreCase("Reset")) {
                controlAssociatesForm.reset();
                request.getSession().setAttribute("controlAssociatesForm", controlAssociatesForm);
                return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociates.jsp");
            } 
            
            else if (controlAssociatesForm.getAction().equalsIgnoreCase("manageAff")) {
                
                ControlAssociateActiveForm controlAssociateActiveForm = new ControlAssociateActiveForm();
                
                controlAssociateActiveForm.setAffId(controlAssociatesForm.getAffId());
                ControlAssociateActiveDAO.GetAssociateInfo(controlAssociateActiveForm);
                request.setAttribute("controlAssociateActiveForm", controlAssociateActiveForm);
                
                return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociateActive.jsp");
            }

            HashMap<String, Object> searchResults = ControlAssociatesDAO.SearchPaula(controlAssociatesForm);
            request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
            controlAssociatesForm.setCount((Integer) searchResults.get("COUNT"));

            if (controlAssociatesForm.getCount() > 0) {
                controlAssociatesForm.setDisplayPageNum(Integer.toString(controlAssociatesForm.getPageNum() + 1));
                controlAssociatesForm.setDisplayPageCount(Integer.toString((controlAssociatesForm.getCount() / 100) + 1));
            }

            request.getSession().setAttribute("controlAssociatesForm", controlAssociatesForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociates.jsp");
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(System.out);
            ex.printStackTrace(pw);
            request.setAttribute("errorMsg", "Exception occurred.");
            request.getSession().setAttribute("controlAssociatesForm", controlAssociatesForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociates.jsp");
        }
    }
}
