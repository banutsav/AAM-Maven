/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.manager;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
import edu.uga.ovpr.aam.information.GeneralDAO;
import edu.uga.ovpr.aam.purpose.PurposeDataDAO;
import edu.uga.ovpr.aam.purpose.PurposeDataForm;
import edu.uga.ovpr.aam.request.ManAffReqForm;
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
public class ManAssociateAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {


        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ManAssociateForm manAssociateForm = (ManAssociateForm) form;
        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");

        
        if (manAssociateForm.getAction().equalsIgnoreCase("managePurposeExp")) 
        { 
            String msg = "";
            if (manAssociateForm.getExpires().equalsIgnoreCase("0")) {
                
                // never expires
                ManAssociateDAO.unexpireAssociatePurpose(manAssociateForm);
            } else {
                
                msg = ManAssociateDAO.UpdateAssociateExpirationDate(manAssociateForm);
            }
            
            
            if (!msg.isEmpty()) {
                request.setAttribute("errorMsg", msg);
            } else {
                request.setAttribute("message", "Successfully updated Associate Record.");
            }
        } 
        
        else if (manAssociateForm.getAction().equalsIgnoreCase("done")) {
            
            if (request.getSession().getAttribute("viewManCurrAssociateForm") == null) 
            {
                ViewManCurrAssociateForm viewManCurrAssociateForm = new ViewManCurrAssociateForm();
                request.getSession().setAttribute("viewManCurrAssociateForm", viewManCurrAssociateForm);
            
            } else {
                if (!((ViewManCurrAssociateForm) request.getSession().getAttribute("viewManCurrAssociateForm")).getExecuted().isEmpty()) {
                    request.setAttribute("AssociateQueryResults", ViewManCurrAssociateDAO.FindAssociateWithArrList((ViewManCurrAssociateForm) request.getSession().getAttribute("viewManCurrAssociateForm"), ses.getUserCan(), ses.getUserRole()));
                }
            }
            return new ActionForward("/WEB-INF/jsp/Manager/ViewManCurrAssociate.jsp");
        }

        manAssociateForm.setAction("");
        
        ManAssociateDAO.GetAssociateData(manAssociateForm, ses.getUserCan(), ses.getUserRole());
        request.setAttribute("manAssociateForm", manAssociateForm);
        return new ActionForward("/WEB-INF/jsp/Manager/manAssociate.jsp");
    }
}
