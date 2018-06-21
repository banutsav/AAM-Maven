/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.superuser;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
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
public class ControlManActiveAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ControlManActiveForm controlManActiveForm = (ControlManActiveForm) form;

        //System.out.println("CANNUM = '" + controlManActiveForm.getCannum() + "','" + controlManActiveForm.getTargPurpId() + "','" + controlManActiveForm.getTargProxyId() + "'");

        if (controlManActiveForm.getAction().equalsIgnoreCase("back")) {
            controlManActiveForm.reset();
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
        } else if (controlManActiveForm.getAction().equalsIgnoreCase("activate")) {
            ControlManActiveDAO.ActivateManager(controlManActiveForm);
            request.setAttribute("message","Successfully activated manager.");
        } else if (controlManActiveForm.getAction().equalsIgnoreCase("deactivate")) {
            ControlManActiveDAO.DeactivateManager(controlManActiveForm);
            request.setAttribute("message","Successfully deactivated manager.");
        } else if (controlManActiveForm.getAction().equalsIgnoreCase("rmPurpose")) {
            ControlManActiveDAO.DeactivatePurpose(controlManActiveForm);
            request.setAttribute("message","Successfully removed purpose.");
        } else if (controlManActiveForm.getAction().equalsIgnoreCase("addPurpose")) {
            ControlManActiveDAO.AddPurpose(controlManActiveForm);
            request.setAttribute("message","Successfully added purpose.");
        } else if (controlManActiveForm.getAction().equalsIgnoreCase("rmProxy")) {
            ControlManActiveDAO.DeactivateTargProxy(controlManActiveForm);
            request.setAttribute("message","Successfully removed proxy.");
        } else if (controlManActiveForm.getAction().equalsIgnoreCase("addProxy")) {
            ControlManAddProxyForm controlManAddProxyForm = new ControlManAddProxyForm();
            controlManAddProxyForm.setAdminId(controlManActiveForm.getAdminId());
            ControlManAddProxyDAO.InitializeData(controlManAddProxyForm);
            ControlManAddProxyDAO.GetPurposes(controlManAddProxyForm);
            controlManAddProxyForm.setAction("");
            request.setAttribute("controlManAddProxyForm", controlManAddProxyForm);
            return new ActionForward("/WEB-INF/jsp/Superuser/ControlManAddProxy.jsp");
        }

        controlManActiveForm.setAction("");
        ControlManActiveDAO.GetManInfo(controlManActiveForm);
        request.setAttribute("controlManActiveForm", controlManActiveForm);
        return new ActionForward("/WEB-INF/jsp/Superuser/ControlManActive.jsp");
    }
}
