package edu.uga.ovpr.aam.login;

import edu.uga.ovpr.aam.person.Person;
import java.util.HashSet;

/**
 *
 * @author Glenn Owens
 */
public class AuthState {

    private HashSet<String> roles = new HashSet<String>();
    private Person userInfo = new Person();
    
    public boolean hasNoRole(){
        return roles==null || roles.isEmpty();
    }

    public HashSet<String> getRoles() {
        return roles;
    }

    public void setRoles(HashSet<String> roles) {
        this.roles = roles;
    }

    public Person getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Person userInfo) {
        this.userInfo = userInfo;
    }

    public boolean hasRole(String role) {
        return roles.contains(role);
    }
}
