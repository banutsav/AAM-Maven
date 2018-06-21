package edu.uga.ovpr.aam;

import edu.uga.ovpr.OVPRSession;
import edu.uga.ovpr.aam.login.AuthState;
import edu.uga.ovpr.aam.login.SecurityDAO;
import edu.uga.ovpr.aam.person.Person;
import edu.uga.ovpr.rolebasedsecurity.UserPrincipal;
import java.util.Collection;
import java.util.LinkedHashSet;

/**
 *
 * @author Jennifer Green
 */
public class Session implements OVPRSession, java.io.Serializable {

    private boolean loggedIn = false;
    public boolean wasSuperAdmin;
    private UserPrincipal userPrincipal;
    private Person userInfo;
    private String userCan;
    private AuthState authState;
    private LinkedHashSet<String> notices = new LinkedHashSet();
    private LinkedHashSet<String> warnings = new LinkedHashSet();
    private LinkedHashSet<String> errors = new LinkedHashSet();
    private boolean reallyAdmin = false;
    private boolean isAssociateManager = false;
    private boolean isContactManager = false;
    
    public void setisAssociateManager() throws Exception
    {
        isAssociateManager = SecurityDAO.isAssociateManager(userCan);
    }
    
    public boolean getisAssociateManager()
    {
        return isAssociateManager;
    }
    
    public void setisContactManager() throws Exception
    {
        isContactManager = SecurityDAO.isContactManager(userCan);
    }
    
    public boolean getisContactManager()
    {
        return isContactManager;
    }
    
    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public UserPrincipal getUserPrincipal() {
        return userPrincipal;
    }

    public void setUserPrincipal(UserPrincipal userPrincipal) {
        this.userPrincipal = userPrincipal;
    }

    public Person getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Person userInfo) {
        this.userInfo = userInfo;
    }
    
    public String getUserCan() {
        return userCan;
    }

    public void setUserCan(String userCan) {
        this.userCan = userCan;
    }
    
    public AuthState getAuthState() {
        return authState;
    }

    public void setAuthState(AuthState authState) {
        this.authState = authState;
    }
    
    public boolean isReallyAdmin() {
        return reallyAdmin;
    }

    public void setReallyAdmin(boolean reallyAdmin) {
        this.reallyAdmin = reallyAdmin;
    }
    
    public boolean isAnyUser() throws Exception {
        return SecurityDAO.hasAnyRole(this);
    }

    public boolean isSuperUser() throws Exception {
        return SecurityDAO.hasSuperUserAccess(this);
    }

    /**public boolean isCanViewUGAID() throws Exception {
        return SecurityDAO.hasSuperUserAccess(this) ||
                SecurityDAO.hasTRAdminAccess(this);
    }**/

    public boolean isManagerProxy() throws Exception {
        return SecurityDAO.hasManagerProxyAccess(this);
    }
    
    public boolean isManager() throws Exception {
        return SecurityDAO.hasManagerAccess(this);
    }
    
    public String getUserRole() throws Exception {
        String role = "";
        if (isSuperUser()){
            role = "SuperUser";
        }else if (isManager()){
            role = "Manager";
        }else if (isManagerProxy()){
            role = "ManagerProxy";
        }
        return role;
    }

    //Convenience methods for <bean:write>
    /**public String getUserCan() {
        if (userPrincipal == null) {
            return "";
        }
        final String canNum = ((UserIdentity) userPrincipal.getUserIdentity()).getCanNum();
        if (canNum == null) {
            return "";
        }
        return canNum;
    }**/

    //Functions for messages (Notices and Errors)
    public boolean isHasMessages() {
        if (errors.isEmpty() && warnings.isEmpty() && notices.isEmpty()) {
            return false;
        }
        return true;
    }

    public LinkedHashSet<String> getNotices() {
        final LinkedHashSet<String> noticesCopy = new LinkedHashSet();
        for (String e : notices) {
            noticesCopy.add(e);
        }
        notices = new LinkedHashSet();
        return noticesCopy;
    }

    public void addNotice(String notice) {
        notices.add(notice);
    }

    public void addNotices(Collection<String> notices) {
        for (String e : notices) {
            this.notices.add(e);
        }
    }

    public LinkedHashSet<String> getWarnings() {
        final LinkedHashSet<String> warningsCopy = new LinkedHashSet();
        for (String e : warnings) {
            warningsCopy.add(e);
        }
        warnings = new LinkedHashSet();
        return warningsCopy;
    }

    public void addWarning(String warning) {
        warnings.add(warning);
    }

    public void addWarnings(Collection<String> warnings) {
        for (String e : warnings) {
            this.warnings.add(e);
        }
    }

    public LinkedHashSet<String> getErrors() {
        final LinkedHashSet<String> errorsCopy = new LinkedHashSet();
        for (String e : errors) {
            errorsCopy.add(e);
        }
        errors = new LinkedHashSet();
        return errorsCopy;
    }

    public void addError(String error) {
        errors.add(error);
    }

    public void addErrors(Collection<String> errors) {
        for (String e : errors) {
            this.errors.add(e);
        }
    }
}