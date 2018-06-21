/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uga.ovpr.aam.manager;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
public class FindAssocAction extends Action{
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

        final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            if (!ses.isLoggedIn()) {
                return mapping.findForward("gfwd_login");
            }
            
        FindAssocForm findAssocForm = (FindAssocForm) form;    
        findAssocForm.setExecuted("true");    
        int result;
        String status_string = "";
        
        if (findAssocForm.getAction().equalsIgnoreCase("search"))
        {
            findAssocForm.resetResults();
            FindAssocDAO.FindAssoc(findAssocForm);
            
            /*
                check if the person exists in the UGA system or not
            */
            
            if(findAssocForm.getResults().isEmpty())
            {
                request.setAttribute("errorMsg", "No UGA Record Exists for this Person with MyID: '" + findAssocForm.getuga_myid() + "'");
                request.setAttribute("associateStatus", "");
            }
            else
            {
                /*
                    person exits in the system: check if is already an associate or not
                    if already not an associate, then check if is eligile or not to be associate
                */
                
                status_string = FindAssocDAO.checkAssociateStatus(findAssocForm);
                request.setAttribute("associateStatus", status_string);
                request.setAttribute("existingAssociate", findAssocForm.getis_associate());
                
            }
            
            /*   
                If the person is eligible to become an associate then
                check if the email address is already in use for other AFFILIATES/ASSOCIATES
            */
            
            if(status_string.equals("This person is eligible to become an Associate"))
            {
                if(FindAssocDAO.validateAssociateEmail(findAssocForm.getnon_uga_email())==true)
                    request.setAttribute("non_uga_email_msg", "Non UGA email address is already in use");
                else
                    request.setAttribute("non_uga_email_msg", "Non UGA email address is valid");
            }
            else
                request.setAttribute("non_uga_email_msg", "");
        }  
        else if(findAssocForm.getAction().equalsIgnoreCase("update_exp_date"))
        {   
            /*
                update the expiration date for current associate
            */
            
            if(findAssocForm.getnever_expires().equals("NA")&&(findAssocForm.getexpdate_months()!=""))
            {
                Calendar cal = Calendar.getInstance();   
                cal.add(Calendar.MONTH, Integer.parseInt(findAssocForm.getexpdate_months()));
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                result = FindAssocDAO.updateExpirationDateForAssociate(findAssocForm.getuga_myid(), dateFormat.format(cal.getTime()));
                setOutputMessage(result, request, "updated", "updating");
                findAssocForm.resetResults();
            }
            
            /*
                expiration date set to "never expires"
            */
            
            if(findAssocForm.getexpdate_months().equals("NA")&&(findAssocForm.getnever_expires().equals("never_expires")))
            { 
                result = FindAssocDAO.updateExpirationDateForAssociate(findAssocForm.getuga_myid(), "NE");
                setOutputMessage(result, request, "updated", "updating");
                findAssocForm.resetResults();
            }
        }
        else if(findAssocForm.getAction().equalsIgnoreCase("na_update_exp_date"))
        {   
            /*
                check if associate already exists before doing the insert
            */
            
            if(FindAssocDAO.associateExists(findAssocForm.getuga_myid())==true)
            {
                request.setAttribute("errorMsg","Error: Associate Record already exists");
                request.getSession().setAttribute("findAssocForm", findAssocForm);
                return new ActionForward("/WEB-INF/jsp/Manager/FindAssoc.jsp");
            }
            
            
            /*
                update the expiration date for a NEW associate
            */
            
            if(findAssocForm.getna_never_expires().equals("NA")&&(findAssocForm.getna_expdate_months()!=""))
            {
                Calendar cal = Calendar.getInstance();   
                cal.add(Calendar.MONTH, Integer.parseInt(findAssocForm.getna_expdate_months()));
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                result = FindAssocDAO.createNewAssociate(findAssocForm.getuga_myid(), dateFormat.format(cal.getTime()), ses.getUserCan(), findAssocForm);
                setOutputMessage(result, request, "created", "creating");
                findAssocForm.resetResults();
            }
            
            if(findAssocForm.getna_expdate_months().equals("NA")&&(findAssocForm.getna_never_expires().equals("na_never_expires")))
            {
                result = FindAssocDAO.createNewAssociate(findAssocForm.getuga_myid(), "NE", ses.getUserCan(), findAssocForm);
                setOutputMessage(result, request, "created", "creating");
                findAssocForm.resetResults();
            }
        }
        
        request.getSession().setAttribute("findAssocForm", findAssocForm);
        return new ActionForward("/WEB-INF/jsp/Manager/FindAssoc.jsp");
            
    }
    
    public void setOutputMessage(int result, HttpServletRequest request, String msg1, String msg2)
    {
        if(result>0)
            request.setAttribute("successMsg", "Associate Record " + msg1 + " Successfully");
        else    
            request.setAttribute("errorMsg","Error " + msg2 + " Associate Record");
        
    }
}    
