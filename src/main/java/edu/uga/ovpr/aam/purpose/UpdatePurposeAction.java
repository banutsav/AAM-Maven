/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.purpose;

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
public class UpdatePurposeAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");

        UpdatePurposeForm updatePurposeForm = (UpdatePurposeForm) form;
        if (updatePurposeForm.getAction().equalsIgnoreCase("backLink")) {
            PurposeDataForm purposeDataForm = new PurposeDataForm();
            purposeDataForm.setPurposeId(updatePurposeForm.getPurposeId());
            PurposeDataDAO.GetPurposeData(purposeDataForm);
            request.setAttribute("purposeDataForm", purposeDataForm);
            return new ActionForward("/WEB-INF/jsp/Manager/PurposeData.jsp");
            /**
            ManPurposeForm manPurposeForm = new ManPurposeForm();
            ManPurposeDAO.GetPurposes(manPurposeForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("manPurposeForm", manPurposeForm);
            return new ActionForward("/WEB-INF/jsp/Manager/ManPurpose.jsp");**/
        } else if (updatePurposeForm.getAction().equalsIgnoreCase("submit")) {
            String errorMsg = UpdatePurposeDAO.ManagePurpose(updatePurposeForm);
            if (errorMsg.isEmpty() || errorMsg.matches("^Successfully")) {
                request.setAttribute("message", "Successfully updated purpose.");
                PurposeDataForm purposeDataForm = new PurposeDataForm();
                purposeDataForm.setPurposeId(updatePurposeForm.getPurposeId());
                PurposeDataDAO.GetPurposeData(purposeDataForm);
                request.setAttribute("purposeDataForm", purposeDataForm);
                return new ActionForward("/WEB-INF/jsp/Manager/PurposeData.jsp");
                /**
                ManPurposeForm manPurposeForm = new ManPurposeForm();
                ManPurposeDAO.GetPurposes(manPurposeForm, ses.getUserCan(), ses.getUserRole());
                request.setAttribute("manPurposeForm", manPurposeForm);
                return new ActionForward("/WEB-INF/jsp/Manager/ManPurpose.jsp");**/
            } else {
                request.setAttribute("errorMsg", errorMsg);
                request.setAttribute("UpdatePurposeForm", updatePurposeForm);
                return new ActionForward("/WEB-INF/jsp/Manager/UpdatePurpose.jsp");
            }
        }
        request.setAttribute("UpdatePurposeForm", updatePurposeForm);
        return new ActionForward("/WEB-INF/jsp/Manager/UpdatePurpose.jsp");
    }
}
