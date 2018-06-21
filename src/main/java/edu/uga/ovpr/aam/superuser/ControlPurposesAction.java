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
public class ControlPurposesAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }
        ControlPurposesForm controlPurposesForm = (ControlPurposesForm) form;
        controlPurposesForm.setExecuted("true");
        controlPurposesForm.setNewShortName(controlPurposesForm.getNewShortName().replaceAll("^\\s+","").replaceAll("\\s+$",""));
        
        
        System.out.println("DEBUG: action = " + controlPurposesForm.getAction());
        
        try {
            if (controlPurposesForm.getAction().equalsIgnoreCase("Search")) {
                controlPurposesForm.setPageNum(0);
            } else if (controlPurposesForm.getAction().equalsIgnoreCase("First")) {
                controlPurposesForm.setPageNum(0);
            } else if (controlPurposesForm.getAction().equalsIgnoreCase("Last")) {
                controlPurposesForm.setPageNum((controlPurposesForm.getCount() / 100));
            } else if (controlPurposesForm.getAction().equalsIgnoreCase("Prev")) {
                if (controlPurposesForm.getPageNum() > 0) {
                    controlPurposesForm.setPageNum(((Integer) controlPurposesForm.getPageNum()) - 1);
                }
            } else if (controlPurposesForm.getAction().equalsIgnoreCase("Next")) {
                if (controlPurposesForm.getPageNum() < controlPurposesForm.getCount() / 100) {
                    controlPurposesForm.setPageNum(((Integer) controlPurposesForm.getPageNum()) + 1);
                }
            } else if (controlPurposesForm.getAction().equalsIgnoreCase("Reset")) {
                controlPurposesForm.reset();
                request.getSession().setAttribute("controlPurposesForm", controlPurposesForm);
                return new ActionForward("/WEB-INF/jsp/Superuser/ControlPurposes.jsp");
            } else if (controlPurposesForm.getAction().equalsIgnoreCase("Back")) {
            
                return new ActionForward("/WEB-INF/jsp/Affiliate/AffiliateOption.jsp");
                
            } else if (controlPurposesForm.getAction().equalsIgnoreCase("Cancel")) {
            
                return new ActionForward("/WEB-INF/jsp/Affiliate/AffiliateOption.jsp");
                
            } else if (controlPurposesForm.getAction().equalsIgnoreCase("AddPurpose")) {
                
                String errorMsg = ControlPurposesDAO.AddPurpose(controlPurposesForm, ses.getUserCan());
                
                if(!errorMsg.isEmpty()){
                    request.setAttribute("errorMsg", errorMsg);
                }
                
                
            } else if (controlPurposesForm.getAction().equalsIgnoreCase("DeactivatePurpose")) {
                ControlPurposesDAO.DeactivatePurpose(controlPurposesForm);
                request.setAttribute("message","Deactivated purpose '" + controlPurposesForm.getTargShortName() + "'.");
            } else if (controlPurposesForm.getAction().equalsIgnoreCase("ReactivatePurpose")) {
                ControlPurposesDAO.ReactivatePurpose(controlPurposesForm);
                request.setAttribute("message","Reactivated purpose '" + controlPurposesForm.getTargShortName() + "'.");
            } else if (controlPurposesForm.getAction().equalsIgnoreCase("UpdatePurpose")) {
                ControlPurposesDAO.UpdatePurpose(controlPurposesForm);
                request.setAttribute("message", "Successfully updated purpose '" + controlPurposesForm.getNewShortName() + "'.");
            }

            request.setAttribute("purposes",ControlPurposesDAO.SearchPaula(controlPurposesForm));
            request.getSession().setAttribute("controlPurposesForm", controlPurposesForm);
            if (controlPurposesForm.getCount() > 0) {
                controlPurposesForm.setDisplayPageNum(Integer.toString(controlPurposesForm.getPageNum() + 1));
                controlPurposesForm.setDisplayPageCount(Integer.toString((controlPurposesForm.getCount() / 100) + 1));
            }

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlPurposes.jsp");
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(System.out);
            ex.printStackTrace(pw);
            request.setAttribute("errorMsg", "Exception occurred.");
            request.setAttribute("controlPurposesForm", controlPurposesForm);

            return new ActionForward("/WEB-INF/jsp/Superuser/ControlPurposes.jsp");
        }
    }
}
