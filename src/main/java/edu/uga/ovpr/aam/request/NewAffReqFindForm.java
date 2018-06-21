/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.request;

import java.util.HashMap;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author submyers
 */
public class NewAffReqFindForm extends ValidatorForm {
    private String action = "";
    private String email = "";
    private String targAffId = "";
    private String targPurpId = "";
    private String executed = "";
    HashMap<String,Object> results = new HashMap<String,Object>();
    
    public void reset(){
        action="";
        email="";
        targAffId = "";
        targPurpId = "";
        executed = "";
        results = new HashMap<String,Object>();
    }

    public String getExecuted() {
        return executed;
    }

    public void setExecuted(String executed) {
        this.executed = executed;
    }

    public String getTargPurpId() {
        return targPurpId;
    }

    public void setTargPurpId(String targPurpId) {
        this.targPurpId = targPurpId;
    }

    public String getTargAffId() {
        return targAffId;
    }

    public void setTargAffId(String targAffId) {
        this.targAffId = targAffId;
    }

    public HashMap<String, Object> getResults() {
        return results;
    }

    public void setResults(HashMap<String, Object> results) {
        this.results = results;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }    
}
