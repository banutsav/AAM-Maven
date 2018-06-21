package edu.uga.ovpr.aam.login;

import edu.uga.ovpr.rolebasedsecurity.IUserIdentity;

/**
 *
 * @author Glenn Owens
 */
public class UserIdentity implements IUserIdentity, java.io.Serializable {

    private String canNum;

    public String getCanNum() {
        return canNum;
    }

    public void setCanNum(String canNum) {
        this.canNum = canNum;
    }
}
