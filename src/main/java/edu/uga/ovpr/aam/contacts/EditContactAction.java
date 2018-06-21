/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.contacts;

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
 * @author utsavb
 */
public class EditContactAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }
    
        EditContactForm n_form = (EditContactForm) form;
        
        //System.out.println("targ cannum = " + n_form.getTarg_cannum());
        
        if (n_form.getAction().equalsIgnoreCase("New"))
        {
            //update contact
            int status = 0;
            status = EditContactDAO.editContact(n_form);
            request.setAttribute("returnMsg", status);
                    
        }
        else if (n_form.getAction().equalsIgnoreCase("Deactivate"))
        {
            // deactivate contact
            /*int status = 0;
            status = EditContactDAO.editStatusOfContact(n_form, '0');
            request.setAttribute("returnMsg", status);*/
            n_form.setTarg_person(n_form.getTarg_cannum());
            n_form.setActive("0");
            request.setAttribute("editContactForm", n_form);
            return new ActionForward("/WEB-INF/jsp/Contacts/EditContact.jsp");
        }
        else if (n_form.getAction().equalsIgnoreCase("activate"))
        {
            // activate contact
            /*int status = 0;
            status = EditContactDAO.editStatusOfContact(n_form, '1');
            request.setAttribute("returnMsg", status);*/
            n_form.setTarg_person(n_form.getTarg_cannum());
            n_form.setActive("1");
            request.setAttribute("editContactForm", n_form);
            return new ActionForward("/WEB-INF/jsp/Contacts/EditContact.jsp");
        }
        
        
        ManageContactsForm m_form = new ManageContactsForm();
        m_form.reset();
        request.getSession().setAttribute("manageContactsForm", m_form);
        return new ActionForward("/WEB-INF/jsp/Contacts/ManageContacts.jsp");
    }
}
