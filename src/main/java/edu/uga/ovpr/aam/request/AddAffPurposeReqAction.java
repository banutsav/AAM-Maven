/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.request;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
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
public class AddAffPurposeReqAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }
        AddAffPurposeReqForm addAffPurposeReqForm = (AddAffPurposeReqForm) form;
        AddAffPurposeReqDAO.FindAff(addAffPurposeReqForm);
        if (addAffPurposeReqForm.getAction().equalsIgnoreCase("requestPurpose")) {
            AddAffPurposeReqDAO.AddPurposeReq(addAffPurposeReqForm);
            AddAffPurposeReqDAO.FindAff(addAffPurposeReqForm);
        } else if (addAffPurposeReqForm.getAction().equalsIgnoreCase("back")) {
            if (request.getSession().getAttribute("genFindAffForm") == null) {
                GenFindAffForm genFindAffForm = new GenFindAffForm();
                request.setAttribute("results", GenFindAffDAO.SearchPaula(genFindAffForm));
                request.getSession().setAttribute("genFindAffForm", genFindAffForm);
            } else {
                if (!((GenFindAffForm) request.getSession().getAttribute("genFindAffForm")).getExecuted().isEmpty()) {
                    request.setAttribute("results", GenFindAffDAO.SearchPaula((GenFindAffForm) request.getSession().getAttribute("genFindAffForm")));
                }
            }
            return new ActionForward("/WEB-INF/jsp/Affiliate/GenFindAff.jsp");
        }
        request.setAttribute("addAffPurposeReqForm", addAffPurposeReqForm);
        return new ActionForward("/WEB-INF/jsp/Affiliate/AddAffPurposeReq.jsp");
    }
}
