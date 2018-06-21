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
public class NewPurposeAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");

        NewPurposeForm newPurposeForm = (NewPurposeForm) form;
        if (newPurposeForm.getAction().equalsIgnoreCase("backLink")) {
            newPurposeForm.reset();
            ManPurposeForm manPurposeForm = new ManPurposeForm();
            ManPurposeDAO.GetPurposes(manPurposeForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("manPurposeForm", manPurposeForm);
            return new ActionForward("/WEB-INF/jsp/Manager/ManPurpose.jsp");
        } else if (newPurposeForm.getAction().equalsIgnoreCase("submit")) {
            if (newPurposeForm.getTitle().equalsIgnoreCase("Manage Purpose")) {
                String errorMsg = NewPurposeDAO.ManagePurpose(newPurposeForm);
                if (errorMsg.isEmpty() || errorMsg.matches("^Successfully")) {
                    newPurposeForm.reset();
                    request.setAttribute("message", "Successfully updated purpose.");
                    ManPurposeForm manPurposeForm = new ManPurposeForm();
                    ManPurposeDAO.GetPurposes(manPurposeForm, ses.getUserCan(), ses.getUserRole());
                    request.setAttribute("manPurposeForm", manPurposeForm);
                    return new ActionForward("/WEB-INF/jsp/Manager/ManPurpose.jsp");
                } else {
                    request.setAttribute("errorMsg", errorMsg);
                    request.setAttribute("newPurposeForm", newPurposeForm);
                    //System.out.println("Going to new purpose!!!");
                    return new ActionForward("/WEB-INF/jsp/Manager/NewPurpose.jsp");
                }
            } else {
                String errorMsg = NewPurposeDAO.CreateNewPurpose(newPurposeForm, ses.getUserCan());
                if (errorMsg.isEmpty() || errorMsg.matches("^Successfully")) {
                    newPurposeForm.reset();
                    request.setAttribute("message", "Successfully created new purpose.");
                    ManPurposeForm manPurposeForm = new ManPurposeForm();
                    ManPurposeDAO.GetPurposes(manPurposeForm, ses.getUserCan(), ses.getUserRole());
                    request.setAttribute("manPurposeForm", manPurposeForm);
                    return new ActionForward("/WEB-INF/jsp/Manager/ManPurpose.jsp");
                } else {
                    request.setAttribute("errorMsg", errorMsg);
                    request.setAttribute("newPurposeForm", newPurposeForm);
                    //System.out.println("Going to new purpose!!!");
                    return new ActionForward("/WEB-INF/jsp/Manager/NewPurpose.jsp");
                }
            }
        }
        request.setAttribute("newPurposeForm", newPurposeForm);
        //System.out.println("Going to new purpose!!!");
        return new ActionForward("/WEB-INF/jsp/Manager/NewPurpose.jsp");
    }
}
