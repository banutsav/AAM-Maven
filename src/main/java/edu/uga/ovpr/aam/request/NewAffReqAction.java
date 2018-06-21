/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.request;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
import edu.uga.ovpr.aam.email.AffiliateEmail;
import edu.uga.ovpr.aam.information.GeneralDAO;
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
public class NewAffReqAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {


        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        request.getSession().setAttribute("purposes", GeneralDAO.GetAllPurposeArr());

        NewAffReqForm newAffReqForm = (NewAffReqForm) form;

        System.out.println("inside new aff req action");
        
        if (newAffReqForm.getAction().equalsIgnoreCase("backLink")) {
            newAffReqForm.reset();
            request.removeAttribute("newAffReqForm");
            if (request.getSession().getAttribute("newAffReqFindForm") == null) {
                NewAffReqFindForm newAffReqFindForm = new NewAffReqFindForm();
                request.getSession().setAttribute("newAffReqFindForm", newAffReqFindForm);
            }
            return new ActionForward("/WEB-INF/jsp/Affiliate/NewAffReqFind.jsp");
        }
        if (newAffReqForm.getAction().equalsIgnoreCase("submit")) {
            if (newAffReqForm.getEmail().isEmpty()
                    || newAffReqForm.getFirstName().isEmpty()
                    || newAffReqForm.getLastName().isEmpty()) {
                request.setAttribute("errorMsg", "Missing required fields.");
            } else {
                String errorMsg = NewAffReqDAO.SubmitAffiliateRequest(newAffReqForm);
                if (!errorMsg.isEmpty()) {
                    request.setAttribute("errorMsg", errorMsg);
                    newAffReqForm.setAction("");
                    return new ActionForward("/WEB-INF/jsp/Affiliate/NewAffReq.jsp");
                }
                request.setAttribute("message", "Successfully submitted affiliate application form.");
                
                AffiliateEmail.EmailManagersNewAff(newAffReqForm);
                
                newAffReqForm.reset();
                request.removeAttribute("newAffReqForm");
                return new ActionForward("/Login.do");
            }
        }

        newAffReqForm.setAction("");
        newAffReqForm.setPurposes(GeneralDAO.GetAllPurposeArr());
        request.setAttribute("newAffReqForm", newAffReqForm);
        return new ActionForward("/WEB-INF/jsp/Affiliate/NewAffReq.jsp");
    }
}
