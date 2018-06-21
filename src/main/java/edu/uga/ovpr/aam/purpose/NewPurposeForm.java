/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.purpose;

import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author submyers
 */
public class NewPurposeForm extends ValidatorForm {

    private String action = "";
    private String shortName = "";
    private String description = "";
    private String period = "";
    private String title = "";
    private String purposeId = "";
    
    public void reset() {
        action = "";
        shortName = "";
        description = "";
        period = "";
        title = "";
        purposeId = "";
    }

    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
