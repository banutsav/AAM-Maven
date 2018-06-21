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
public class ViewManCurrAffAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ViewManCurrAffForm viewManCurrAffForm = (ViewManCurrAffForm) form;
        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");
        
        //System.out.println("Action is - " + viewManCurrAffForm.getAction());
        if (viewManCurrAffForm.getAction().equalsIgnoreCase("back")) {
            return new ActionForward("/WEB-INF/jsp/Affiliate/AffiliateOption.jsp");
        } else if (viewManCurrAffForm.getAction().equalsIgnoreCase("reset")) {
            viewManCurrAffForm.reset();
        } else if (viewManCurrAffForm.getAction().equalsIgnoreCase("submit")) {
            //ViewManCurrAffDAO.FindAff(viewManCurrAffForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("affQueryResults",ViewManCurrAffDAO.FindAffWithArrList(viewManCurrAffForm, ses.getUserCan(), ses.getUserRole()));
            viewManCurrAffForm.setExecuted("true");
        } else if (viewManCurrAffForm.getAction().equalsIgnoreCase("create")) {
            //viewManCurrAffForm.reset();
            ManCreateNewAffForm manCreateNewAffForm = new ManCreateNewAffForm();
            manCreateNewAffForm.setPurposes(GeneralDAO.GetPurposeArr(ses.getUserCan(), ses.getUserRole()));
            manCreateNewAffForm.setCurrAffEmails(GeneralDAO.GetCurrAffEmails());
            request.setAttribute("manCreateNewAffForm", manCreateNewAffForm);
            return new ActionForward("/WEB-INF/jsp/Manager/ManCreateNewAff.jsp");
        } else if (viewManCurrAffForm.getAction().equalsIgnoreCase("manageAffPurpose")) {
            request.setAttribute("errorMsg", ViewManCurrAffDAO.UpdateAffPurpose(viewManCurrAffForm));
            //ViewManCurrAffDAO.FindAff(viewManCurrAffForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("affQueryResults",ViewManCurrAffDAO.FindAffWithArrList(viewManCurrAffForm, ses.getUserCan(), ses.getUserRole()));
            viewManCurrAffForm.setExecuted("true");
        } else if (viewManCurrAffForm.getAction().equalsIgnoreCase("addAffPurpose")) {
            request.setAttribute("errorMsg", ViewManCurrAffDAO.AddAffPurpose(viewManCurrAffForm, ses.getUserCan()));
            //ViewManCurrAffDAO.FindAff(viewManCurrAffForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("affQueryResults",ViewManCurrAffDAO.FindAffWithArrList(viewManCurrAffForm, ses.getUserCan(), ses.getUserRole()));
            viewManCurrAffForm.setExecuted("true");
        } 
        
        else if (viewManCurrAffForm.getAction().equalsIgnoreCase("openAffPage")) {
            
            ManAffForm manAffForm = new ManAffForm();
            manAffForm.setAffId(viewManCurrAffForm.getTargUserId());
            manAffForm.setBack("viewManCurrAff");
            ManAffDAO.GetAffData(manAffForm, ses.getUserCan(), ses.getUserRole());
            ManAffDAO.GetAuthPurposes(manAffForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("manAffForm", manAffForm);
            return new ActionForward("/WEB-INF/jsp/Manager/manAff.jsp");
        }

        viewManCurrAffForm.setAction("");
        //viewManCurrAffForm.setPurposes(GeneralDAO.GetPurposeArr(ses.getUserCan(), ses.getUserRole()));
        ViewManCurrAffDAO.DefinePurposeGroups(viewManCurrAffForm,ses.getUserCan(), ses.getUserRole());
        request.getSession().setAttribute("viewManCurrAffForm", viewManCurrAffForm);
                
        //System.out.println("2) PurposeOptOpen = '" + viewManCurrAffForm.getPurposeOptOpen() + "'\n");
        
        return new ActionForward("/WEB-INF/jsp/Manager/ViewManCurrAff.jsp");
    }
}
