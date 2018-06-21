/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.purpose;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
import edu.uga.ovpr.aam.manager.ManAffDAO;
import edu.uga.ovpr.aam.manager.ManAffForm;
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
public class PurposeDataAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");
        PurposeDataForm purposeDataForm = (PurposeDataForm) form;
        
        if(purposeDataForm.getAction().equalsIgnoreCase("back")){
            
            ManPurposeForm manPurposeForm = new ManPurposeForm();
            ManPurposeDAO.GetPurposes(manPurposeForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("manPurposeForm", manPurposeForm);
            return new ActionForward("/WEB-INF/jsp/Manager/ManPurpose.jsp");
        
        } else if(purposeDataForm.getAction().equalsIgnoreCase("manAff")){
            ManAffForm manAffForm = new ManAffForm();
            manAffForm.setAffId(purposeDataForm.getTargAffId());
            manAffForm.setBack("purposeData");
            manAffForm.setBackPurposeId(purposeDataForm.getPurposeId());
            ManAffDAO.GetAffData(manAffForm, ses.getUserCan(), ses.getUserRole());
            ManAffDAO.GetAuthPurposes(manAffForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("manAffForm", manAffForm);
            return new ActionForward("/WEB-INF/jsp/Manager/manAff.jsp");
        } else if(purposeDataForm.getAction().equalsIgnoreCase("updatePurpose")){
            
            UpdatePurposeForm updatePurposeForm = ManPurposeDAO.SetUpdatePurposeForm(purposeDataForm.getPurposeId(),(String)request.getSession().getAttribute("role"));
            request.setAttribute("updatePurposeForm", updatePurposeForm);
            
            //request.setAttribute("userRole",(String)request.getSession().getAttribute("role"));
            
            return new ActionForward("/WEB-INF/jsp/Manager/UpdatePurpose.jsp");
        }
        
        request.setAttribute("purposeDataForm",purposeDataForm);
        return new ActionForward("/WEB-INF/jsp/Manager/PurposeData.jsp");
    }
    
}
