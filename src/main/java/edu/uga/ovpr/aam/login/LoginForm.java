package edu.uga.ovpr.aam.login;

import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author Glenn Owens
 */
public class LoginForm extends ValidatorForm implements java.io.Serializable {

    private String ugamyid;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUgamyid() {
        return ugamyid;
    }

    public void setUgamyid(String ugamyid) {
        this.ugamyid = ugamyid;
    }
}