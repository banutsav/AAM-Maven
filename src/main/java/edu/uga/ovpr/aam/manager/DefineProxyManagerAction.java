/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.manager;

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
 * @author programmer
 */
public class DefineProxyManagerAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        DefineProxyManagerForm defineProxyManagerForm = (DefineProxyManagerForm) form;
        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");

        if( defineProxyManagerForm.getAction().equalsIgnoreCase("back")){
            defineProxyManagerForm.reset();
            return new ActionForward("/WEB-INF/jsp/Affiliate/AffiliateOption.jsp");
        } else if (defineProxyManagerForm.getAction().equalsIgnoreCase("removeManager")){
            DefineProxyManagerDAO.DeactivateProxy(defineProxyManagerForm);
            request.setAttribute("message", "Proxy manager was successfully removed.");
        } else if( defineProxyManagerForm.getAction().equalsIgnoreCase("addManager")){
            AddAffManagerForm addAffManagerForm = new AddAffManagerForm();
            addAffManagerForm.setPurposeId(defineProxyManagerForm.getTargPurposeId());
            addAffManagerForm.setPurposeShortName(defineProxyManagerForm.getTargPurposeShortName());
            //addAffManagerForm.setManagerCannum(ses.getUserCan());
            //AddAffManagerDAO.DefinePurposes(addAffManagerFrom, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("addAffManagerForm",addAffManagerForm);
            defineProxyManagerForm.reset();
            return new ActionForward("/WEB-INF/jsp/Manager/AddAffManager.jsp");
        }
        
        //DefineProxyManagerDAO.GetManagers(defineProxyManagerForm);
        DefineProxyManagerDAO.GetProxyPurposes(defineProxyManagerForm, ses.getUserCan(), ses.getUserRole());
        request.setAttribute("defineProxyManagerForm", defineProxyManagerForm);
        return new ActionForward("/WEB-INF/jsp/Manager/DefineProxyManager.jsp");
    }
}
