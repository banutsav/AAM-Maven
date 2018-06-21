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
public class GenFindAffAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        GenFindAffForm genFindAffForm = (GenFindAffForm) form;
        genFindAffForm.setExecuted("true");
        
        //System.out.println("Inside the General Find Aff Action: " + genFindAffForm.getAction() + "\n");
        
        if (genFindAffForm.getAction().equalsIgnoreCase("search")) {
            //GenFindAffDAO.FindAff(genFindAffForm);
            genFindAffForm.setCount(-1);
            genFindAffForm.setPageNum(0);
            request.setAttribute("results", GenFindAffDAO.SearchPaula(genFindAffForm));
        } else if (genFindAffForm.getAction().equalsIgnoreCase("manage")) {
            AddAffPurposeReqForm addAffPurposeReqForm = new AddAffPurposeReqForm();
            addAffPurposeReqForm.setTargAffId(genFindAffForm.getTargAffId());
            AddAffPurposeReqDAO.FindAff(addAffPurposeReqForm);
            request.setAttribute("addAffPurposeReqForm", addAffPurposeReqForm);
            //System.out.println("Targ aff id: " + addAffPurposeReqForm.getTargAffId() + "\n");
            return new ActionForward("/WEB-INF/jsp/Affiliate/AddAffPurposeReq.jsp");
        } else if (genFindAffForm.getAction().equalsIgnoreCase("reset")) {
            genFindAffForm.reset();
        } else if (genFindAffForm.getAction().equalsIgnoreCase("Prev")) {
            if (genFindAffForm.getPageNum() > 0) {
                genFindAffForm.setPageNum(((Integer) genFindAffForm.getPageNum()) - 1);
            }
            request.setAttribute("results", GenFindAffDAO.SearchPaula(genFindAffForm));
        } else if (genFindAffForm.getAction().equalsIgnoreCase("Next")) {
            if (genFindAffForm.getPageNum() < genFindAffForm.getCount() / 100) {
                genFindAffForm.setPageNum(((Integer) genFindAffForm.getPageNum()) + 1);
            }
            request.setAttribute("results", GenFindAffDAO.SearchPaula(genFindAffForm));
        } else if (genFindAffForm.getAction().equalsIgnoreCase("Last")) {
            genFindAffForm.setPageNum((genFindAffForm.getCount() / 100));
            request.setAttribute("results", GenFindAffDAO.SearchPaula(genFindAffForm));
        } else if (genFindAffForm.getAction().equalsIgnoreCase("First")) {
            genFindAffForm.setPageNum(0);
            request.setAttribute("results", GenFindAffDAO.SearchPaula(genFindAffForm));
        }
        request.getSession().setAttribute("genFindAffForm", genFindAffForm);
        return new ActionForward("/WEB-INF/jsp/Affiliate/GenFindAff.jsp");
    }
}
