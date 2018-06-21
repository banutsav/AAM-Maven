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
public class ManPurposeAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");
        ManPurposeForm manPurposeForm = (ManPurposeForm) form;
       
        //System.out.println("DEBUG: action = " + manPurposeForm.getAction());
        
        if (manPurposeForm.getAction().equalsIgnoreCase("backLink")) {
            manPurposeForm.reset();
            return new ActionForward("/WEB-INF/jsp/Affiliate/AffiliateOption.jsp");
        } else if (manPurposeForm.getAction().equalsIgnoreCase("newPurpose")) {
            manPurposeForm.reset();
            NewPurposeForm newPurposeForm = new NewPurposeForm();
            newPurposeForm.setTitle("New Purpose");
            request.setAttribute("newPurposeForm", newPurposeForm);

            return new ActionForward("/WEB-INF/jsp/Manager/NewPurpose.jsp");
        } else if (manPurposeForm.getAction().equalsIgnoreCase("reqNewPurpose")) {
            manPurposeForm.reset();
            ReqNewPurposeForm reqNewPurposeForm = new ReqNewPurposeForm();
            request.setAttribute("reqNewPurposeForm", reqNewPurposeForm);

            return new ActionForward("/WEB-INF/jsp/Manager/ReqNewPurpose.jsp");
        } else if (manPurposeForm.getAction().equalsIgnoreCase("purposeData")) {
            PurposeDataForm purposeDataForm = new PurposeDataForm();
            purposeDataForm.setPurposeId(manPurposeForm.getPurposeId());
            PurposeDataDAO.GetPurposeData(purposeDataForm);
            request.setAttribute("purposeDataForm", purposeDataForm);
            return new ActionForward("/WEB-INF/jsp/Manager/PurposeData.jsp");
        }

        //request.setAttribute("purposes", GeneralDAO.GetPurposeArr(ses.getUserCan(), ses.getUserRole()));
        request.setAttribute("manPurposeForm", manPurposeForm);
        return new ActionForward("/WEB-INF/jsp/Manager/ManPurpose.jsp");
    }
}
