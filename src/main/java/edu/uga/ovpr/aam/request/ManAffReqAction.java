/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.request;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
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
public class ManAffReqAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ManAffReqForm manAffReqForm = (ManAffReqForm) form;
        String userCannum = ses.getUserCan();
        if(manAffReqForm.getAction().equalsIgnoreCase("backLink")){
            manAffReqForm.setAction("");
            manAffReqForm.reset(mapping, request);
            request.removeAttribute("manAffReqForm");
            return new ActionForward("/WEB-INF/jsp/Affiliate/AffiliateOption.jsp");
        } else if(manAffReqForm.getAction().equalsIgnoreCase("accept")){
            ManAffReqDAO.AcceptRequest(manAffReqForm,userCannum);
            request.setAttribute("message","Successfully accepted affiliate \"<b>" + manAffReqForm.getAffName() + "</b>\" for purpose \"<b>" + manAffReqForm.getPurposeName() + "</b>\".");
        } else if(manAffReqForm.getAction().equalsIgnoreCase("reject")){
            ManAffReqDAO.RejectRequest(manAffReqForm,userCannum);
            request.setAttribute("message","Successfully rejected affiliate \"<b>" + manAffReqForm.getAffName() + "</b>\" for purpose \"<b>" + manAffReqForm.getPurposeName() + "</b>\".");
        }
        
        boolean superUser = false;
        if(ses.getUserRole().equals("SuperUser")){
            superUser=true;
        }                
        manAffReqForm.setRequests(ManAffReqDAO.GetAffReqs(superUser,userCannum));
        manAffReqForm.setNumberOfRequests(Integer.toString(manAffReqForm.getRequests().size()));
        request.setAttribute("manAffReqForm",manAffReqForm);
        
        return new ActionForward("/WEB-INF/jsp/Manager/ManAffReq.jsp");
    }
    
}
