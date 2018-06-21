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
public class ManageContactsAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }

        ManageContactsForm c_form = (ManageContactsForm) form;
        c_form.setExecuted("true");
        
        
        if (c_form.getAction().equalsIgnoreCase("Reset"))
        {
            c_form.reset();
            request.getSession().setAttribute("manageContactsForm", c_form);
            return new ActionForward("/WEB-INF/jsp/Contacts/ManageContacts.jsp");
            
        }
        else if(c_form.getAction().equalsIgnoreCase("EditContact"))
        {
            EditContactForm n_form = new EditContactForm();
            HashMap<String, String> contact = ManageContactsDAO.getPersonRecord(c_form.getTargPersonCannum());
            
            n_form.setFirstName(contact.get("FIRSTNAME"));
            n_form.setLastName(contact.get("LASTNAME"));
            n_form.setEmail(contact.get("EMAIL"));
            n_form.setOrganization(contact.get("ORGANIZATION"));
            n_form.setTitle(contact.get("TITLE"));
            n_form.setUgaid_prime(contact.get("UGAID_PRIME"));
            n_form.setTelephone(contact.get("PHONE"));
            n_form.setAddr1(contact.get("ADDR1"));
            n_form.setAddr2(contact.get("ADDR2"));
            n_form.setCity(contact.get("CITY"));
            n_form.setState(contact.get("STATE"));
            n_form.setZip(contact.get("ZIP"));
            n_form.setCellphone(contact.get("CELLPHONE"));
            n_form.setFax(contact.get("FAX"));
            n_form.setActive(contact.get("ACTIVE"));
            n_form.setTarg_person(c_form.getTargPersonCannum());
            
            request.setAttribute("editContactForm", n_form);
            return new ActionForward("/WEB-INF/jsp/Contacts/EditContact.jsp");
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
                
        HashMap<String, Object> searchResults = ManageContactsDAO.SearchContacts(c_form);
        c_form.setCount((Integer) searchResults.get("COUNT"));
        request.setAttribute("queryResults", (ArrayList<Object>) searchResults.get("people"));
        request.getSession().setAttribute("manageContactsForm", c_form);
        
        if (c_form.getCount() > 0) 
        {
            c_form.setDisplayPageNum(Integer.toString(c_form.getPageNum() + 1));
            c_form.setDisplayPageCount(Integer.toString((c_form.getCount() / 100) + 1));
        }
        
        return new ActionForward("/WEB-INF/jsp/Contacts/ManageContacts.jsp");
    }
}
