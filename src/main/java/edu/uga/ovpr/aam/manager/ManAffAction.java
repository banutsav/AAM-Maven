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
public class ManAffAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {


        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ManAffForm manAffForm = (ManAffForm) form;
        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");

        if (manAffForm.getAction().equalsIgnoreCase("create")) {
            //viewManCurrAffForm.reset();
            ManCreateNewAffForm manCreateNewAffForm = new ManCreateNewAffForm();
            manCreateNewAffForm.setPurposes(GeneralDAO.GetPurposeArr(ses.getUserCan(), ses.getUserRole()));
            request.setAttribute("manCreateNewAffForm", manCreateNewAffForm);
            return new ActionForward("/WEB-INF/jsp/Manager/ManCreateNewAff.jsp");
        } else if (manAffForm.getAction().equalsIgnoreCase("manageAffPurpose")) {
            String msg = ManAffDAO.UpdateAffPurpose(manAffForm);
            if (!msg.isEmpty()) {
                request.setAttribute("errorMsg", msg);
            } else {
                request.setAttribute("message", "Successfully updated.");
            }
        } else if (manAffForm.getAction().equalsIgnoreCase("addAffPurpose")) {
            String msg = ManAffDAO.AddAffPurpose(manAffForm, ses.getUserCan());
            if (!msg.isEmpty()) {
                request.setAttribute("errorMsg", msg);
            } else {
                request.setAttribute("message", "Successfully updated.");
            }
        } else if (manAffForm.getAction().equalsIgnoreCase("back")) {
            if (manAffForm.getBack().equalsIgnoreCase("viewManCurrAff")) {
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
            } else if (manAffForm.getBack().equalsIgnoreCase("findAff")) {
                if (request.getSession().getAttribute("findAffForm") == null) {
                    FindAffForm findAffForm = new FindAffForm();
                    request.getSession().setAttribute("findAffForm", findAffForm);
                }
                return new ActionForward("/WEB-INF/jsp/Manager/FindAff.jsp");
            } else if (manAffForm.getBack().equalsIgnoreCase("purposeData")) {
                PurposeDataForm purposeDataForm = new PurposeDataForm();
                purposeDataForm.setPurposeId(manAffForm.getBackPurposeId());
                PurposeDataDAO.GetPurposeData(purposeDataForm);
                request.setAttribute("purposeDataForm", purposeDataForm);
                return new ActionForward("/WEB-INF/jsp/Manager/PurposeData.jsp");
            }
        } else if (manAffForm.getAction().equalsIgnoreCase("managePurposeExp")) {
            String msg = "";
            if (manAffForm.getExpires().equalsIgnoreCase("0")) {
                ManAffDAO.unexpirePurpose(manAffForm);
            } else {
                msg = ManAffDAO.UpdateAffPurpose(manAffForm);
            }
            if (!msg.isEmpty()) {
                request.setAttribute("errorMsg", msg);
            } else {
                request.setAttribute("message", "Successfully updated.");
            }
        } else if (manAffForm.getAction().equalsIgnoreCase("unexpireAffPurpose")) {
            ManAffDAO.unexpirePurpose(manAffForm);
            request.setAttribute("message", "Successfully updated.");
        } else if (manAffForm.getAction().equalsIgnoreCase("done")) {
            
            return new ActionForward("/Get.do?action=home");
            //return new ActionForward("/Login.do");
        
        } else if (manAffForm.getAction().equalsIgnoreCase("changeApprovalStatus")) {
            ManAffDAO.changeApprovalStatus(manAffForm);
            request.setAttribute("message", "Successfully updated.");
        } else if (manAffForm.getAction().equalsIgnoreCase("manRequests")) {
            ManAffReqForm manAffReqForm = new ManAffReqForm();
            request.setAttribute("manAffReqForm", manAffReqForm);
            return new ActionForward("/ManAffReq.do");
        }

        manAffForm.setAction("");
        //ManAffDAO.UpdateActiveStatus(manAffForm);
        ManAffDAO.GetAffData(manAffForm, ses.getUserCan(), ses.getUserRole());
        ManAffDAO.GetAuthPurposes(manAffForm, ses.getUserCan(), ses.getUserRole());
        request.setAttribute("manAffForm", manAffForm);
        return new ActionForward("/WEB-INF/jsp/Manager/manAff.jsp");
    }
}
