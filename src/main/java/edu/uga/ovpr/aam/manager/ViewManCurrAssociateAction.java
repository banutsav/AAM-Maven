/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.manager;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
import edu.uga.ovpr.aam.information.GeneralDAO;
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
public class ViewManCurrAssociateAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ViewManCurrAssociateForm viewManCurrAssociateForm = (ViewManCurrAssociateForm) form;
        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");
        
        //System.out.println("Action is - " + viewManCurrAffForm.getAction());
        
        
        if (viewManCurrAssociateForm.getAction().equalsIgnoreCase("back")) {
            return new ActionForward("/WEB-INF/jsp/Affiliate/AffiliateOption.jsp");
        } 
        else if (viewManCurrAssociateForm.getAction().equalsIgnoreCase("reset")) {
            viewManCurrAssociateForm.reset();
        } 
        
        else if (viewManCurrAssociateForm.getAction().equalsIgnoreCase("submit")) {
            
            ArrayList<Object> results = ViewManCurrAssociateDAO.FindAssociateWithArrList(viewManCurrAssociateForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("AssociateQueryResults", results);
            viewManCurrAssociateForm.setExecuted("true");
        } 
        
        else if(viewManCurrAssociateForm.getAction().equalsIgnoreCase("openAssociatePage"))
        {
            ManAssociateForm manAssociateForm = new ManAssociateForm();
            manAssociateForm.setassocId(viewManCurrAssociateForm.getTargUserId());
            
            manAssociateForm.setBack("viewManCurrAssociate");
            
            ManAssociateDAO.GetAssociateData(manAssociateForm, ses.getUserCan(), ses.getUserRole());
           
            //ManAssociateDAO.GetAuthPurposes(manAssociateForm, ses.getUserCan(), ses.getUserRole());
            
            request.setAttribute("manAssociateForm", manAssociateForm);
            return new ActionForward("/WEB-INF/jsp/Manager/manAssociate.jsp");
        }
        
        else if(viewManCurrAssociateForm.getAction().equalsIgnoreCase("done"))
        {
        }
        
        viewManCurrAssociateForm.setAction("");
        
        //ViewManCurrAffDAO.DefinePurposeGroups(viewManCurrAssociateForm,ses.getUserCan(), ses.getUserRole());
        
        request.getSession().setAttribute("viewManCurrAssociateForm", viewManCurrAssociateForm);        
        return new ActionForward("/WEB-INF/jsp/Manager/ViewManCurrAssociate.jsp");
    }
}
