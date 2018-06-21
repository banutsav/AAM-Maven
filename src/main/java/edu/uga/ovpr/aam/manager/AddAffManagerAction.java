/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.manager;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
import java.io.PrintWriter;
import java.io.StringWriter;
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
 * @author programmer
 */
public class AddAffManagerAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        AddAffManagerForm addAffManagerForm = (AddAffManagerForm) form;
        HashMap<String, String> userInfo = (HashMap<String, String>) request.getSession().getAttribute("userInfo");

        try {
            if (addAffManagerForm.getSubmitOpt().equalsIgnoreCase("Cancel")) {
                addAffManagerForm.reset();
                DefineProxyManagerForm defineProxyManagerForm = new DefineProxyManagerForm();
                //DefineProxyManagerDAO.GetManagers(defineProxyManagerForm);
                DefineProxyManagerDAO.GetProxyPurposes(defineProxyManagerForm, ses.getUserCan(), ses.getUserRole());
                request.setAttribute("defineProxyManagerForm", defineProxyManagerForm);
                return new ActionForward("/WEB-INF/jsp/Manager/DefineProxyManager.jsp");
            } else if (addAffManagerForm.getSubmitOpt().equalsIgnoreCase("Search")) {
                addAffManagerForm.setPageNum(0);
                HashMap<String, Object> searchResults = AddAffManagerDAO.SearchPaula(addAffManagerForm);
                addAffManagerForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                addAffManagerForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (addAffManagerForm.getSubmitOpt().equalsIgnoreCase("First")) {
                addAffManagerForm.setPageNum(0);
                HashMap<String, Object> searchResults = AddAffManagerDAO.SearchPaula(addAffManagerForm);
                addAffManagerForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                addAffManagerForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (addAffManagerForm.getSubmitOpt().equalsIgnoreCase("Last")) {
                addAffManagerForm.setPageNum((addAffManagerForm.getCount() / 100));
                //System.out.println("PageNum = " + addAffManagerForm.getPageNum());
                HashMap<String, Object> searchResults = AddAffManagerDAO.SearchPaula(addAffManagerForm);
                addAffManagerForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                addAffManagerForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (addAffManagerForm.getSubmitOpt().equalsIgnoreCase("Prev")) {
                if (addAffManagerForm.getPageNum() > 0) {
                    addAffManagerForm.setPageNum(((Integer) addAffManagerForm.getPageNum()) - 1);
                }
                //System.out.println("PageNum = " + addAffManagerForm.getPageNum());
                HashMap<String, Object> searchResults = AddAffManagerDAO.SearchPaula(addAffManagerForm);
                addAffManagerForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                addAffManagerForm.setCount((Integer) searchResults.get("COUNT"));

            } else if (addAffManagerForm.getSubmitOpt().equalsIgnoreCase("Next")) {
                if (addAffManagerForm.getPageNum() < addAffManagerForm.getCount() / 100) {
                    addAffManagerForm.setPageNum(((Integer) addAffManagerForm.getPageNum()) + 1);
                }
                //System.out.println("PageNum = " + addAffManagerForm.getPageNum());
                HashMap<String, Object> searchResults = AddAffManagerDAO.SearchPaula(addAffManagerForm);
                addAffManagerForm.setQueryResults((ArrayList<Object>) searchResults.get("people"));
                addAffManagerForm.setCount((Integer) searchResults.get("COUNT"));
            } else if (addAffManagerForm.getSubmitOpt().equalsIgnoreCase("Reset")) {
                addAffManagerForm.resetQuery();
                //request.setAttribute("addAffManagerForm", addAffManagerForm);
                //return new ActionForward("/WEB-INF/jsp/manager/addAffManager.jsp");
            } else if (addAffManagerForm.getSubmitOpt().equalsIgnoreCase("Commit")) {
                String message = AddAffManagerDAO.AddAffManager(addAffManagerForm, ses.getUserCan());
                if (message.isEmpty()) {
                    request.setAttribute("message", addAffManagerForm.getNewProxyManagerFullName() + " added as Proxy Manager for " + addAffManagerForm.getPurposeShortName() + ".");
                    DefineProxyManagerForm defineProxyManagerForm = new DefineProxyManagerForm();
                    //DefineProxyManagerDAO.GetManagers(defineProxyManagerForm);
                    DefineProxyManagerDAO.GetProxyPurposes(defineProxyManagerForm, ses.getUserCan(), ses.getUserRole());
                    request.setAttribute("defineProxyManagerForm", defineProxyManagerForm);
                    return new ActionForward("/WEB-INF/jsp/Manager/DefineProxyManager.jsp");
                } else {
                    request.setAttribute("errorMsg", message);
                }
            }

            //AddAffManagerDAO.DefinePurposes(addAffManagerForm, ses.getUserCan(), ses.getUserRole());
            request.setAttribute("addAffManagerForm", addAffManagerForm);

            return new ActionForward("/WEB-INF/jsp/Manager/AddAffManager.jsp");
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(System.out);
            ex.printStackTrace(pw);
            request.setAttribute("errorMsg", "Exception occurred.");
            request.setAttribute("addAffManagerForm", addAffManagerForm);

            return new ActionForward("/WEB-INF/jsp/Manager/AddAffManager.jsp");
        }
    }
}
