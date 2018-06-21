/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.application;

import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author submyers
 */
public class AffiliateOptionForm extends ValidatorForm {

    private String action="";

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
