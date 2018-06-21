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
public class UpdatePurposeForm extends ValidatorForm {

    private String action = "";
    private String purposeId = "";
    private String shortName = "";
    private String description = "";
    private String period = "";
    private String permission = "";

    public void reset() {
        action = "";
        purposeId = "";
        shortName = "";
        description = "";
        period = "";
        permission = "";
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(String purposeId) {
        this.purposeId = purposeId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
