/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.request;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
import edu.uga.ovpr.aam.information.GeneralDAO;
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
public class NewAffReqFindAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }
        //System.out.println("Inside the New Aff Req Find Action:\n");

        NewAffReqFindForm newAffReqFindForm = (NewAffReqFindForm) form;
        newAffReqFindForm.setExecuted("true");
        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");
        if (newAffReqFindForm.getAction().equalsIgnoreCase("search")) {
            NewAffReqFindDAO.FindAff(newAffReqFindForm);
            if (newAffReqFindForm.getResults().isEmpty()) {
                NewAffReqForm newAffReqForm = new NewAffReqForm();
                newAffReqForm.setCurrAffEmails(GeneralDAO.GetCurrAffEmails());
                newAffReqForm.setPurposes(GeneralDAO.GetAllPurposeArr());
                newAffReqForm.setEmail(newAffReqFindForm.getEmail());
                request.getSession().setAttribute("newAffReqFindForm", newAffReqFindForm);
                request.setAttribute("newAffReqForm", newAffReqForm);
                return new ActionForward("/WEB-INF/jsp/Affiliate/NewAffReq.jsp");
            }
        } else if (newAffReqFindForm.getAction().equalsIgnoreCase("reset")) {
            newAffReqFindForm.reset();
        } else if (newAffReqFindForm.getAction().equalsIgnoreCase("requestPurpose")) {
            NewAffReqFindDAO.AddPurposeReq(newAffReqFindForm);
            NewAffReqFindDAO.FindAff(newAffReqFindForm);
        }

        request.getSession().setAttribute("newAffReqFindForm", newAffReqFindForm);
        return new ActionForward("/WEB-INF/jsp/Affiliate/NewAffReqFind.jsp");
    }
}
