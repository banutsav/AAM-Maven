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
public class FindAffAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        FindAffForm findAffForm = (FindAffForm) form;
        findAffForm.setExecuted("true");
        
        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");
        
        if (findAffForm.getAction().equalsIgnoreCase("search")) {
            FindAffDAO.FindAff(findAffForm);
            
            if (findAffForm.getResults().isEmpty()) {
                ManCreateNewAffForm manCreateNewAffForm = new ManCreateNewAffForm();
                manCreateNewAffForm.setPurposes(GeneralDAO.GetPurposeArr(ses.getUserCan(), ses.getUserRole()));
                //manCreateNewAffForm.setCurrAffEmails(GeneralDAO.GetCurrAffEmails());
                manCreateNewAffForm.setEmail(findAffForm.getEmail());
                request.getSession().setAttribute("findAffForm", findAffForm);
                request.setAttribute("manCreateNewAffForm", manCreateNewAffForm);
                return new ActionForward("/WEB-INF/jsp/Manager/ManCreateNewAff.jsp");
            }
        } else if (findAffForm.getAction().equalsIgnoreCase("reset")) {
            findAffForm.reset();
        } else if (findAffForm.getAction().equalsIgnoreCase("manage")) {
            ManAffForm manAffForm = new ManAffForm();
            manAffForm.setAffId(findAffForm.getTargAffId());
            manAffForm.setBack("findAff");
            ManAffDAO.GetAffData(manAffForm, ses.getUserCan(), ses.getUserRole());
            ManAffDAO.GetAuthPurposes(manAffForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("manAffForm", manAffForm);
            return new ActionForward("/WEB-INF/jsp/Manager/ManAff.jsp");
        } else if (findAffForm.getAction().equalsIgnoreCase("back")) {
            findAffForm.reset();
            return new ActionForward("/WEB-INF/jsp/Affiliate/AffiliateOption.jsp");
        }

        request.getSession().setAttribute("findAffForm", findAffForm);
        return new ActionForward("/WEB-INF/jsp/Manager/FindAff.jsp");
    }
}
