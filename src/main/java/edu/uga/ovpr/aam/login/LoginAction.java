package edu.uga.ovpr.aam.login;

import edu.uga.ovpr.OVPRSessionManager;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.aam.SessionFactory;
import edu.uga.ovpr.aam.email.AffiliateEmail;
import edu.uga.ovpr.aam.person.Person;
import java.util.ArrayList;
import java.util.HashMap;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;

/**
 *
 * @author Glenn Owens
 */
public class LoginAction extends Action {

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            final OVPRSessionManager<Session> sm = new OVPRSessionManager(new SessionFactory(), request, response);
            final Session ses = sm.getSession();
            final LoginForm c_form = (LoginForm) form;

            final LoginResult loginResult = SecurityDAO.login_LDAP(c_form);
            form.reset(mapping, request); //clear credentials from memory
            
            if (loginResult.isFailedAttempt) {
                ses.addError("Unable to Authenticate MyID");
            } else if (loginResult.isBadMyID || loginResult.isBadPassword) {
                ses.addError("Incorrect MyID or Password");
            } else {
                //System.out.println(loginResult.canNum);
                final AuthState authState = SecurityDAO.makeAuthState(loginResult.canNum);
                ses.setUserCan(loginResult.canNum);

                //Just so I can see using Tomcat Manager who is logged in
                final HttpSession session = request.getSession();
                final Person person = Person.getPersonBean(loginResult.canNum);
                session.setAttribute("userCan", loginResult.canNum);
                session.setAttribute("userName", person.getFirstName() + " " + person.getLastName());


                ses.setAuthState(authState);
                ses.setLoggedIn(true);
                if (authState.hasRole("Admin")) {
                    ses.setReallyAdmin(true);
                }

                if (request.getSession().getAttribute(redirectToAttr) != null) {
                    final String redirectTo = (String) request.getSession().getAttribute(redirectToAttr);
                    request.getSession().removeAttribute(redirectToAttr);
                    return new ActionRedirect(redirectTo);
                }

                //check if associate manager                
                ses.setisAssociateManager();
                
                //check if contact manager
                ses.setisContactManager();
                                        
                return new ActionForward("/WEB-INF/jsp/Affiliate/AffiliateOption.jsp");
            //request.setAttribute("errorMsg", "Insufficient Privileges");
            }
            return mapping.findForward("gfwd_login");
        } catch (Exception ex) {
            servlet.log("Error message", ex);
            return mapping.findForward("gfwd_error");
        }
    }
    public static final String redirectToAttr = "edu.uga.ovpr.aam.login.redirectTo";

    public static void setRedirectTo(String redirectTo, HttpServletRequest request) {
        request.getSession().setAttribute(redirectToAttr, redirectTo);
    }

    public static void setRedirectHere(boolean includeQueryString, HttpServletRequest request) {
        String redirectTo = request.getServletPath();
        if (includeQueryString && request.getQueryString() != null) {
            redirectTo += "?" + request.getQueryString();
        }
        setRedirectTo(redirectTo, request);
    }

    public static void setRedirectHere(HttpServletRequest request) {
        setRedirectHere(true, request);
    }
}