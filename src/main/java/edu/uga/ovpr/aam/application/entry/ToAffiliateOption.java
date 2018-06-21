/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.application.entry;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
import edu.uga.ovpr.aam.application.AffiliateOptionForm;
import edu.uga.ovpr.aam.information.GeneralDAO;
import edu.uga.ovpr.aam.manager.DefineProxyManagerDAO;
import edu.uga.ovpr.aam.manager.DefineProxyManagerForm;
import edu.uga.ovpr.aam.manager.FindAffForm;
import edu.uga.ovpr.aam.manager.ViewManCurrAffDAO;
import edu.uga.ovpr.aam.manager.ViewManCurrAffForm;
import edu.uga.ovpr.aam.purpose.ManPurposeDAO;
import edu.uga.ovpr.aam.purpose.ManPurposeForm;
import edu.uga.ovpr.aam.request.GenFindAffDAO;
import edu.uga.ovpr.aam.request.GenFindAffForm;
import edu.uga.ovpr.aam.request.ManAffReqForm;
import edu.uga.ovpr.aam.request.NewAffReqFindDAO;
import edu.uga.ovpr.aam.request.NewAffReqFindForm;
import edu.uga.ovpr.aam.superuser.ControlAffiliatesDAO;
import edu.uga.ovpr.aam.superuser.ControlAffiliatesForm;
import edu.uga.ovpr.aam.superuser.ControlManagersDAO;
import edu.uga.ovpr.aam.superuser.ControlManagersForm;
import edu.uga.ovpr.aam.superuser.ControlPurposesDAO;
import edu.uga.ovpr.aam.superuser.ControlPurposesForm;
import edu.uga.ovpr.aam.users.ManageUsersDAO;
import edu.uga.ovpr.aam.users.ManageUsersForm;
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
public class ToAffiliateOption extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }
        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");

        AffiliateOptionForm affiliateOptionForm = (AffiliateOptionForm) form;
        request.setAttribute("action", affiliateOptionForm.getAction());
        if (affiliateOptionForm.getAction().equals("addNonProvAff")) {
            form.reset(mapping, request);
            return new ActionForward("/WEB-INF/jsp/Affiliate/AddNonProvisionalAffiliate.jsp");
        } else if (affiliateOptionForm.getAction().equals("modNonProvAff")) {
            return new ActionForward("/WEB-INF/jsp/Affiliate/UpgradeProvisionalAffiliate.jsp");
        } else if (affiliateOptionForm.getAction().equals("manUsers")) {
            ManageUsersForm manageUsersForm = new ManageUsersForm();
            manageUsersForm.setCurrUsers(ManageUsersDAO.GetCurrUsers());
            request.setAttribute("manageUsersForm", manageUsersForm);
            return new ActionForward("/WEB-INF/jsp/Manager/ManageUsers.jsp");
        } else if (affiliateOptionForm.getAction().equals("manAffReq")) {
            ManAffReqForm manAffReqForm = new ManAffReqForm();
            request.setAttribute("manAffReqForm", manAffReqForm);
            return new ActionForward("/ManAffReq.do");
        } else if (affiliateOptionForm.getAction().equals("manAffPurpose")) {
            ManPurposeForm manPurposeForm = new ManPurposeForm();
            ManPurposeDAO.GetPurposes(manPurposeForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("manPurposeForm", manPurposeForm);
            return new ActionForward("/WEB-INF/jsp/Manager/ManPurpose.jsp");
        } else if (affiliateOptionForm.getAction().equals("manAffMan")) {
            DefineProxyManagerForm defineProxyManagerForm = new DefineProxyManagerForm();
            //DefineProxyManagerDAO.GetManagers(defineProxyManagerForm);
            DefineProxyManagerDAO.GetProxyPurposes(defineProxyManagerForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("defineProxyManagerForm", defineProxyManagerForm);
            return new ActionForward("/WEB-INF/jsp/Manager/DefineProxyManager.jsp");
        } else if (affiliateOptionForm.getAction().equals("viewManCurrAff")) {
            if (request.getSession().getAttribute("viewManCurrAffForm") == null) {
                ViewManCurrAffForm viewManCurrAffForm = new ViewManCurrAffForm();
                viewManCurrAffForm.setPurposes(GeneralDAO.GetAllPurposeArr());
                ViewManCurrAffDAO.DefinePurposeGroups(viewManCurrAffForm, ses.getUserCan(), ses.getUserRole());
                request.getSession().setAttribute("viewManCurrAffForm", viewManCurrAffForm);
            } else {
                if (!((ViewManCurrAffForm) request.getSession().getAttribute("viewManCurrAffForm")).getExecuted().isEmpty()) {
                    request.setAttribute("affQueryResults", ViewManCurrAffDAO.FindAffWithArrList((ViewManCurrAffForm) request.getSession().getAttribute("viewManCurrAffForm"), ses.getUserCan(), ses.getUserRole()));
                }
            }
            return new ActionForward("/WEB-INF/jsp/Manager/ViewManCurrAff.jsp");
        } else if (affiliateOptionForm.getAction().equals("incExpVewManCurrAff")) {
            ViewManCurrAffForm viewManCurrAffForm;
            if (request.getSession().getAttribute("viewManCurrAffForm") == null) {
                viewManCurrAffForm = new ViewManCurrAffForm();
                viewManCurrAffForm.setPurposes(GeneralDAO.GetAllPurposeArr());
                ViewManCurrAffDAO.DefinePurposeGroups(viewManCurrAffForm, ses.getUserCan(), ses.getUserRole());
                request.getSession().setAttribute("viewManCurrAffForm", viewManCurrAffForm);
            } else {
                viewManCurrAffForm = (ViewManCurrAffForm) request.getSession().getAttribute("viewManCurrAffForm");
            }
            viewManCurrAffForm.setExecuted("1");
            viewManCurrAffForm.setShowExpire("Yes");
            viewManCurrAffForm.setPurposeManageFilter("Yes");
            request.setAttribute("affQueryResults", ViewManCurrAffDAO.FindAffWithArrList(viewManCurrAffForm, ses.getUserCan(), ses.getUserRole()));

            return new ActionForward("/WEB-INF/jsp/Manager/ViewManCurrAff.jsp");
        } else if (affiliateOptionForm.getAction().equals("manCreateNewAff")) {
            if (request.getSession().getAttribute("findAffForm") == null) {
                FindAffForm findAffForm = new FindAffForm();
                request.getSession().setAttribute("findAffForm", findAffForm);
            }
            return new ActionForward("/WEB-INF/jsp/Manager/FindAff.jsp");
        } else if (affiliateOptionForm.getAction().equals("addAffReq")) {
            if (request.getSession().getAttribute("newAffReqFindForm") == null) {
                NewAffReqFindForm newAffReqFindForm = new NewAffReqFindForm();
                request.getSession().setAttribute("newAffReqFindForm", newAffReqFindForm);
            } else {
                if (!((NewAffReqFindForm) request.getSession().getAttribute("newAffReqFindForm")).getExecuted().isEmpty()) {
                    NewAffReqFindDAO.FindAff((NewAffReqFindForm) request.getSession().getAttribute("newAffReqFindForm"));
                }
            }
            return new ActionForward("/WEB-INF/jsp/Affiliate/NewAffReqFind.jsp");
        } else if (affiliateOptionForm.getAction().equals("addAffPurp")) {
            if (request.getSession().getAttribute("genFindAffForm") == null) {
                GenFindAffForm genFindAffForm = new GenFindAffForm();
                request.getSession().setAttribute("genFindAffForm", genFindAffForm);
            } else {
                if (!((GenFindAffForm) request.getSession().getAttribute("genFindAffForm")).getExecuted().isEmpty()) {
                    request.setAttribute("results", GenFindAffDAO.SearchPaula((GenFindAffForm) request.getSession().getAttribute("genFindAffForm")));
                }
            }
            return new ActionForward("/WEB-INF/jsp/Affiliate/GenFindAff.jsp");
        } else if (affiliateOptionForm.getAction().equals("superControlAff")) {
            if (request.getSession().getAttribute("controlAffiliatesForm") == null) {
                ControlAffiliatesForm controlAffiliatesForm = new ControlAffiliatesForm();
                request.getSession().setAttribute("controlAffiliatesForm", controlAffiliatesForm);
            } else {
                if (!((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")).getExecuted().isEmpty()) {
                    HashMap<String, Object> searchResults = ControlAffiliatesDAO.SearchPaula(((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    ((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")).setCount((Integer) searchResults.get("COUNT"));

                    if (((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")).getCount() > 0) {
                        ((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")).setDisplayPageNum(Integer.toString(((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")).getPageNum() + 1));
                        ((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")).setDisplayPageCount(Integer.toString((((ControlAffiliatesForm) request.getSession().getAttribute("controlAffiliatesForm")).getCount() / 100) + 1));
                    }
                }
            }
            return new ActionForward("/WEB-INF/jsp/Superuser/ControlAffiliates.jsp");
        } else if (affiliateOptionForm.getAction().equals("superControlMan")) {
            if (request.getSession().getAttribute("controlManagersForm") == null) {
                ControlManagersForm controlManagersForm = new ControlManagersForm();
                request.getSession().setAttribute("controlManagersForm", controlManagersForm);
            } else {
                if (!((ControlManagersForm) request.getSession().getAttribute("controlManagersForm")).getExecuted().isEmpty()) {
                    HashMap<String, Object> searchResults = ControlManagersDAO.SearchPaula(((ControlManagersForm) request.getSession().getAttribute("controlManagersForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    //controlManagersForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                    ((ControlManagersForm) request.getSession().getAttribute("controlManagersForm")).setCount((Integer) searchResults.get("COUNT"));
                }
            }
            return new ActionForward("/WEB-INF/jsp/Superuser/ControlManagers.jsp");
        } else if (affiliateOptionForm.getAction().equals("superControlPurpose")) {
            if (request.getSession().getAttribute("controlPurposesForm") == null) {
                ControlPurposesForm controlPurposesForm = new ControlPurposesForm();
                request.getSession().setAttribute("controlPurposesForm", controlPurposesForm);
            } else {
                if (!((ControlPurposesForm) request.getSession().getAttribute("controlPurposesForm")).getExecuted().isEmpty()) {
                    request.setAttribute("purposes", ControlPurposesDAO.SearchPaula(((ControlPurposesForm) request.getSession().getAttribute("controlPurposesForm"))));
                    request.getSession().setAttribute("controlPurposesForm", ((ControlPurposesForm) request.getSession().getAttribute("controlPurposesForm")));
                    if (((ControlPurposesForm) request.getSession().getAttribute("controlPurposesForm")).getCount() > 0) {
                        ((ControlPurposesForm) request.getSession().getAttribute("controlPurposesForm")).setDisplayPageNum(Integer.toString(((ControlPurposesForm) request.getSession().getAttribute("controlPurposesForm")).getPageNum() + 1));
                        ((ControlPurposesForm) request.getSession().getAttribute("controlPurposesForm")).setDisplayPageCount(Integer.toString((((ControlPurposesForm) request.getSession().getAttribute("controlPurposesForm")).getCount() / 100) + 1));
                    }
                }
            }
            return new ActionForward("/WEB-INF/jsp/Superuser/ControlPurposes.jsp");
        } else {
            request.setAttribute("errorMessage", "Error: Unrecognized option " + affiliateOptionForm.getAction());
            return new ActionForward("/WEB-INF/jsp/Affiliate/AffiliateOption.jsp");
        }
    }
}
