package edu.uga.ovpr.aam;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.application.AffiliateOptionForm;
import edu.uga.ovpr.aam.contacts.ControlContactManagersDAO;
import edu.uga.ovpr.aam.contacts.ControlContactManagersForm;
import edu.uga.ovpr.aam.information.GeneralDAO;
import edu.uga.ovpr.aam.manager.*;
import edu.uga.ovpr.aam.purpose.*;
import edu.uga.ovpr.aam.request.*;
import edu.uga.ovpr.aam.superuser.*;
import edu.uga.ovpr.aam.users.*;
import edu.uga.ovpr.aam.contacts.CreateContactsForm;
import edu.uga.ovpr.aam.contacts.ManageContactsForm;
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
 * @author Jennifer Green
 */
public class GetAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
        final Session ses = sm.getSession();
        if (!ses.isLoggedIn()) {
            return mapping.findForward("gfwd_login");
        }

        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");

        final String action = request.getParameter("action");
        request.setAttribute("action", action);
        
        //System.out.println("action = " + action);
        
        if (action.equals("addNonProvAff")) {
            form.reset(mapping, request);
            return new ActionForward("/WEB-INF/jsp/Affiliate/AddNonProvisionalAffiliate.jsp");
        } else if (action.equals("modNonProvAff")) {
            return new ActionForward("/WEB-INF/jsp/Affiliate/UpgradeProvisionalAffiliate.jsp");
        } else if (action.equals("manUsers")) {
            ManageUsersForm manageUsersForm = new ManageUsersForm();
            manageUsersForm.setCurrUsers(ManageUsersDAO.GetCurrUsers());
            request.setAttribute("manageUsersForm", manageUsersForm);
            return new ActionForward("/WEB-INF/jsp/Manager/ManageUsers.jsp");
        } else if (action.equals("manAffReq")) {
            ManAffReqForm manAffReqForm = new ManAffReqForm();
            request.setAttribute("manAffReqForm", manAffReqForm);
            return new ActionForward("/ManAffReq.do");
        } else if (action.equals("manAffPurpose")) {
            ManPurposeForm manPurposeForm = new ManPurposeForm();
            ManPurposeDAO.GetPurposes(manPurposeForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("manPurposeForm", manPurposeForm);
            return new ActionForward("/WEB-INF/jsp/Manager/ManPurpose.jsp");
        } else if (action.equals("manAffMan")) {
            DefineProxyManagerForm defineProxyManagerForm = new DefineProxyManagerForm();
            //DefineProxyManagerDAO.GetManagers(defineProxyManagerForm);
            DefineProxyManagerDAO.GetProxyPurposes(defineProxyManagerForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("defineProxyManagerForm", defineProxyManagerForm);
            return new ActionForward("/WEB-INF/jsp/Manager/DefineProxyManager.jsp");
        } else if (action.equals("viewManCurrAff")) {
           
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
        }
        
        // manage existing associates
        
        else if(action.equals("viewManCurrAssociate"))
        {
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
        
        
        else if (action.equals("incExpVewManCurrAff")) {
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
        } else if (action.equals("manCreateNewAff")) {
            if (request.getSession().getAttribute("findAffForm") == null) {
                FindAffForm findAffForm = new FindAffForm();
                request.getSession().setAttribute("findAffForm", findAffForm);
            }
            return new ActionForward("/WEB-INF/jsp/Manager/FindAff.jsp");
        } 
        
        // create new associate
        
        else if(action.equals("manCreateNewAssoc"))
        {
            FindAssocForm findAssocForm = new FindAssocForm();
            request.getSession().setAttribute("findAssocForm", findAssocForm);
            
            return new ActionForward("/WEB-INF/jsp/Manager/FindAssoc.jsp");
        }
        
        else if (action.equals("addAffReq")) {
            if (request.getSession().getAttribute("newAffReqFindForm") == null) {
                NewAffReqFindForm newAffReqFindForm = new NewAffReqFindForm();
                request.getSession().setAttribute("newAffReqFindForm", newAffReqFindForm);
            } else {
                if (!((NewAffReqFindForm) request.getSession().getAttribute("newAffReqFindForm")).getExecuted().isEmpty()) {
                    NewAffReqFindDAO.FindAff((NewAffReqFindForm) request.getSession().getAttribute("newAffReqFindForm"));
                }
            }
            return new ActionForward("/WEB-INF/jsp/Affiliate/NewAffReqFind.jsp");
        } else if (action.equals("addAffPurp")) {
            if (request.getSession().getAttribute("genFindAffForm") == null) {
                GenFindAffForm genFindAffForm = new GenFindAffForm();
                request.getSession().setAttribute("genFindAffForm", genFindAffForm);
            } else {
                if (!((GenFindAffForm) request.getSession().getAttribute("genFindAffForm")).getExecuted().isEmpty()) {
                    request.setAttribute("results", GenFindAffDAO.SearchPaula((GenFindAffForm) request.getSession().getAttribute("genFindAffForm")));
                }
            }
            return new ActionForward("/WEB-INF/jsp/Affiliate/GenFindAff.jsp");
        } 
        
        // create new contact
        else if(action.equals("ManCreateNewContact"))
        {
            CreateContactsForm c_form = new CreateContactsForm();
            request.getSession().setAttribute("createContactsForm", c_form);
            return new ActionForward("/WEB-INF/jsp/Contacts/CreateContacts.jsp");
        }
        
        // manage contacts
        else if(action.equals("ManViewContacts"))
        {
            ManageContactsForm c_form = new ManageContactsForm();
            request.getSession().setAttribute("manageContactsForm", c_form);
            return new ActionForward("/WEB-INF/jsp/Contacts/ManageContacts.jsp");
        }
        
        else if (action.equals("superControlAff")) 
        {
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
        } 
        
        else if (action.equals("superControlAssociate")) 
        {
            if (request.getSession().getAttribute("controlAssociatesForm") == null) {
                ControlAssociatesForm controlAssociatesForm = new ControlAssociatesForm();
                request.getSession().setAttribute("controlAssociatesForm", controlAssociatesForm);
            } else {
            
                if (!((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")).getExecuted().isEmpty()) {
                    HashMap<String, Object> searchResults = ControlAssociatesDAO.SearchPaula(((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    ((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")).setCount((Integer) searchResults.get("COUNT"));

                    if (((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")).getCount() > 0) {
                        ((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")).setDisplayPageNum(Integer.toString(((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")).getPageNum() + 1));
                        ((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")).setDisplayPageCount(Integer.toString((((ControlAssociatesForm) request.getSession().getAttribute("controlAssociatesForm")).getCount() / 100) + 1));
                    }
                }
            }
            
            return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociates.jsp");
        } 
        
        
        else if (action.equals("superControlMan")) {
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
        } else if (action.equals("superControlPurpose")) {
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
        }
        // superuser create/activate/deactivate associate managers
        else if(action.equals("superControlAssociateManagers"))
        {
            if (request.getSession().getAttribute("controlAssociateManagersForm") == null) 
            {
                ControlAssociateManagersForm controlAssociateManagersForm = new ControlAssociateManagersForm();
                request.getSession().setAttribute("controlAssociateManagersForm", controlAssociateManagersForm);
            }
            else {
                if (!((ControlAssociateManagersForm) request.getSession().getAttribute("controlAssociateManagersForm")).getExecuted().isEmpty()) {
                    HashMap<String, Object> searchResults = ControlAssociateManagersDAO.SearchPaula(((ControlAssociateManagersForm) request.getSession().getAttribute("controlAssociateManagersForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    //controlManagersForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                    ((ControlAssociateManagersForm) request.getSession().getAttribute("controlAssociateManagersForm")).setCount((Integer) searchResults.get("COUNT"));
                }
            }
            return new ActionForward("/WEB-INF/jsp/Superuser/ControlAssociateManagers.jsp");
        }
        // superuser create/activate/deactivate contact managers
        else if(action.equals("superControlContactManagers"))
        {
            if (request.getSession().getAttribute("controlContactManagersForm") == null) 
            {
                ControlContactManagersForm controlContactManagersForm = new ControlContactManagersForm();
                request.getSession().setAttribute("controlContactManagersForm", controlContactManagersForm);
            }
            else {
                if (!((ControlContactManagersForm) request.getSession().getAttribute("controlContactManagersForm")).getExecuted().isEmpty()) {
                    HashMap<String, Object> searchResults = ControlContactManagersDAO.SearchPaula(((ControlContactManagersForm) request.getSession().getAttribute("controlContactManagersForm")));
                    request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
                    ((ControlContactManagersForm) request.getSession().getAttribute("controlContactManagersForm")).setCount((Integer) searchResults.get("COUNT"));
                }
            }
            
            return new ActionForward("/WEB-INF/jsp/Contacts/ControlContactManagers.jsp");
        }
        else if (action.equals("home")) {
            request.removeAttribute("manageUsersForm");
            return new ActionForward("/WEB-INF/jsp/Affiliate/AffiliateOption.jsp");
        } else {
            request.setAttribute("errorMessage", "Error: Unrecognized option " + action);
            return new ActionForward("/WEB-INF/jsp/Affiliate/AffiliateOption.jsp");
        }
    }
}
