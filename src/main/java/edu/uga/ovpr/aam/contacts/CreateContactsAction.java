/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.contacts;

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
 * @author utsavb
 */
public class CreateContactsAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        CreateContactsForm c_form = (CreateContactsForm) form;
        c_form.setExecuted("true");
        
        
        if (c_form.getAction().equalsIgnoreCase("Reset"))
        {
            c_form.reset();
            request.getSession().setAttribute("createContactsForm", c_form);
            return new ActionForward("/WEB-INF/jsp/Contacts/CreateContacts.jsp");
            
        }
        else if(c_form.getAction().equalsIgnoreCase("New"))
        {
            NewContactForm n_form = new NewContactForm();
            HashMap<String, String> person = NewContactDAO.getPersonRecord(c_form.getTargPersonCannum());
            n_form.setTargPersonCannum(c_form.getTargPersonCannum());
            n_form.setFirstName(person.get("FIRSTNAME")); 
            n_form.setLastName(person.get("LASTNAME"));
            n_form.setEmail(person.get("EMAIL"));
            n_form.setTelephone(person.get("PHONE"));
            n_form.setTitle(person.get("TITLE"));
            n_form.setMyid(person.get("MYID"));

            request.setAttribute("newContactForm", n_form);
            return new ActionForward("/WEB-INF/jsp/Contacts/NewContact.jsp");
        }
        else if(c_form.getAction().equalsIgnoreCase("NewPerson"))
        {
            NewContactForm n_form = new NewContactForm();
            
            //n_form.setTargPersonCannum(String.valueOf(NewContactDAO.getContactUGAID()));
            //n_form.setMyid(String.valueOf(NewContactDAO.getContactUGAID()));
            n_form.setFirstName(c_form.getFirstName());
            n_form.setLastName(c_form.getLastName());
            n_form.setOrganization(c_form.getOrg());
            request.setAttribute("newContactForm", n_form);
            return new ActionForward("/WEB-INF/jsp/Contacts/NewContact.jsp");
        }
        else if(c_form.getAction().equalsIgnoreCase("Search"))
        {
            c_form.setPageNum(0);
        }
        else if (c_form.getAction().equalsIgnoreCase("First")) 
        {
            c_form.setPageNum(0);
        } 
        else if (c_form.getAction().equalsIgnoreCase("Last")) 
        {
            c_form.setPageNum((c_form.getCount() / 100));
        } 
        else if (c_form.getAction().equalsIgnoreCase("Prev")) 
        {
                if (c_form.getPageNum() > 0) {
                    c_form.setPageNum(((Integer) c_form.getPageNum()) - 1);
                }
        } 
        else if (c_form.getAction().equalsIgnoreCase("Next")) 
        {
                if (c_form.getPageNum() < c_form.getCount() / 100) {
                    c_form.setPageNum(((Integer) c_form.getPageNum()) + 1);
                }
        }
                
        HashMap<String, Object> searchResults = CreateContactsDAO.SearchPaula(c_form);
        c_form.setCount((Integer) searchResults.get("COUNT"));
        request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
        request.getSession().setAttribute("createContactsForm", c_form);
        
        if (c_form.getCount() > 0) 
        {
            c_form.setDisplayPageNum(Integer.toString(c_form.getPageNum() + 1));
            c_form.setDisplayPageCount(Integer.toString((c_form.getCount() / 100) + 1));
        }
        
        return new ActionForward("/WEB-INF/jsp/Contacts/CreateContacts.jsp");
    }
}
