/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.manager;

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
public class ManCreateNewAffAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {


        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ManCreateNewAffForm manCreateNewAffForm = (ManCreateNewAffForm) form;

        System.out.println("inside man create new aff action");
        
        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");

        if (manCreateNewAffForm == null || manCreateNewAffForm.getAction() == null || manCreateNewAffForm.getAction().isEmpty()) {
            form.reset(mapping, request);
            manCreateNewAffForm = new ManCreateNewAffForm();
            manCreateNewAffForm.setPurposes(GeneralDAO.GetPurposeArr(ses.getUserCan(), ses.getUserRole()));
            request.setAttribute("manCreateNewAffForm", manCreateNewAffForm);
            manCreateNewAffForm.setAction("");
            return new ActionForward("/WEB-INF/jsp/Manager/ManCreateNewAff.jsp");
        }

        if (manCreateNewAffForm.getAction().equalsIgnoreCase("backLink")) {
            if (request.getSession().getAttribute("findAffForm") == null) {
                FindAffForm findAffForm = new FindAffForm();
                request.getSession().setAttribute("findAffForm", findAffForm);
            }
            return new ActionForward("/WEB-INF/jsp/Manager/FindAff.jsp");
        }
        if (manCreateNewAffForm.getAction().equalsIgnoreCase("submit")) {
            manCreateNewAffForm.setPurposes(GeneralDAO.GetPurposeArr(ses.getUserCan(), ses.getUserRole()));
            request.setAttribute("manCreateNewAffForm", manCreateNewAffForm);
            if (manCreateNewAffForm.getEmail().isEmpty()
                    || manCreateNewAffForm.getFirstName().isEmpty()
                    || manCreateNewAffForm.getLastName().isEmpty()) {
                request.setAttribute("errorMsg", "Missing required fields.");
                manCreateNewAffForm.setAction("");
                return new ActionForward("/WEB-INF/jsp/Manager/ManCreateNewAff.jsp");
            }

            String errorMsg = ManCreateNewAffDAO.CreateNewAffiliate(manCreateNewAffForm, request, ses.getUserCan());
            if (!errorMsg.isEmpty()) {
                request.setAttribute("errorMsg", errorMsg);
                manCreateNewAffForm.setAction("");
                return new ActionForward("/WEB-INF/jsp/Manager/ManCreateNewAff.jsp");
            }
            request.setAttribute("message", "Successfully submitted new non-provisional affiliate form.");
            manCreateNewAffForm.reset();
            return new ActionForward("/WEB-INF/jsp/Affiliate/AffiliateOption.jsp");
        }

        manCreateNewAffForm.setAction("");
        return new ActionForward("/WEB-INF/jsp/Manager/ManCreateNewAff.jsp");
    }
}
