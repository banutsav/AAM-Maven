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
public class ControlAssociateManagersAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ControlAssociateManagersForm controlAssociateManagersForm = (ControlAssociateManagersForm) form;
        controlAssociateManagersForm.setExecuted("true");
        try {
            if (controlAssociateManagersForm.getAction().equalsIgnoreCase("Search")) {
                controlAssociateManagersForm.setPageNum(0);

            } else if (controlAssociateManagersForm.getAction().equalsIgnoreCase("First")) {
                controlAssociateManagersForm.setPageNum(0);

            } else if (controlAssociateManagersForm.getAction().equalsIgnoreCase("Last")) {
                controlAssociateManagersForm.setPageNum((controlAssociateManagersForm.getCount() / 100));

            } else if (controlAssociateManagersForm.getAction().equalsIgnoreCase("Prev")) {
                if (controlAssociateManagersForm.getPageNum() > 0) {
                    controlAssociateManagersForm.setPageNum(((Integer) controlAssociateManagersForm.getPageNum()) - 1);
                }

            } else if (controlAssociateManagersForm.getAction().equalsIgnoreCase("Next")) {
                if (controlAssociateManagersForm.getPageNum() < controlAssociateManagersForm.getCount() / 100) {
                    controlAssociateManagersForm.setPageNum(((Integer) controlAssociateManagersForm.getPageNum()) + 1);
                }

            } else if (controlAssociateManagersForm.getAction().equalsIgnoreCase("Reset")) {
                controlAssociateManagersForm.reset();
                
            } else if (controlAssociateManagersForm.getAction().equalsIgnoreCase("newManager")) {
                
                ControlNewAssociateManForm controlNewAssociateManForm = new ControlNewAssociateManForm();
                request.setAttribute("controlNewAssociateManForm", controlNewAssociateManForm);
                return new ActionForward("/WEB-INF/jsp/Superuser/ControlNewAssociateMan.jsp");
                
            } else if (controlAssociateManagersForm.getAction().equalsIgnoreCase("manageMan")) {
              
                ControlAssociateManActiveForm controlAssociateManActiveForm = new ControlAssociateManActiveForm();
                
                controlAssociateManActiveForm.setAdminId(controlAssociateManagersForm.getAdminId());
                
                ControlAssociateManActiveDAO.GetAssociateManInfo(controlAssociateManActiveForm);
                
                request.setAttribute("controlAssociateManActiveForm", controlAssociateManActiveForm);
                return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociateManActive.jsp");
            }

            
            /*
                search PAULA for associate managers
            */
            
            HashMap<String, Object> searchResults = ControlAssociateManagersDAO.SearchPaula(controlAssociateManagersForm);  
            request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
            controlAssociateManagersForm.setCount((Integer) searchResults.get("COUNT"));

            if (controlAssociateManagersForm.getCount() > 0) {
                controlAssociateManagersForm.setDisplayPageNum(Integer.toString(controlAssociateManagersForm.getPageNum() + 1));
                controlAssociateManagersForm.setDisplayPageCount(Integer.toString((controlAssociateManagersForm.getCount() / 100) + 1));
            }

            request.getSession().setAttribute("controlAssociateManagersForm", controlAssociateManagersForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociateManagers.jsp");
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(System.out);
            ex.printStackTrace(pw);
            request.setAttribute("errorMsg", "Exception occurred.");
            request.setAttribute("controlAssociateManagersForm", controlAssociateManagersForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociateManagers.jsp");
        }
    }
}
