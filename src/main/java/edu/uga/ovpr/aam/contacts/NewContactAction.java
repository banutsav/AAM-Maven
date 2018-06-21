/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.contacts;

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
 * @author utsavb
 */
public class NewContactAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }
    
        NewContactForm n_form = (NewContactForm) form;
        
        if (n_form.getAction().equalsIgnoreCase("New"))
        {
            //insert new contact
            int status = 0;
            status = NewContactDAO.addContact(n_form);
            
            CreateContactsForm c_form = new CreateContactsForm();
            c_form.reset();
            c_form.setExecuted("true");
            c_form.setPrime(n_form.getTargPersonCannum());
            
            HashMap<String, Object> searchResults = CreateContactsDAO.SearchPaula(c_form);
            c_form.setCount((Integer) searchResults.get("COUNT"));
            c_form.setPageNum(0);
            request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
            
            if (c_form.getCount() > 0) 
            {
             c_form.setDisplayPageNum(Integer.toString(c_form.getPageNum() + 1));
             c_form.setDisplayPageCount(Integer.toString((c_form.getCount() / 100) + 1));
            }
            
            request.getSession().setAttribute("createContactsForm", c_form);
            request.setAttribute("returnMsg", status);
            return new ActionForward("/WEB-INF/jsp/Contacts/CreateContacts.jsp");
        }
        return new ActionForward("/WEB-INF/jsp/Contacts/NewContact.jsp");
    }
}
