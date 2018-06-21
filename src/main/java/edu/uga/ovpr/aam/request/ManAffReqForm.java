/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.request;

import java.util.ArrayList;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author submyers
 */
public class ManAffReqForm extends ValidatorForm {

    private String action = "";
    private String reqId = "";
    private String expirationDate = "";
    private String pseudoCannum = "";
    private String purpose_period = "";
    private String purposeName = "";
    private String affName = "";
    private String numberOfRequests = "";
    private ArrayList<Object> requests = new ArrayList<Object>();

    public void reset() {
        action = "";
        reqId = "";
        expirationDate = "";
        pseudoCannum = "";
        purpose_period = "";
        purposeName = "";
        affName = "";
        numberOfRequests = "";
        requests = new ArrayList<Object>();
    }

    public String getNumberOfRequests() {
        return numberOfRequests;
    }

    public void setNumberOfRequests(String numberOfRequests) {
        this.numberOfRequests = numberOfRequests;
    }

    public String getAffName() {
        return affName;
    }

    public void setAffName(String affName) {
        this.affName = affName;
    }

    public String getPurposeName() {
        return purposeName;
    }

    public void setPurposeName(String purposeName) {
        this.purposeName = purposeName;
    }

    public String getPurpose_period() {
        return purpose_period;
    }

    public void setPurpose_period(String purpose_period) {
        this.purpose_period = purpose_period;
    }

    public String getPseudoCannum() {
        return pseudoCannum;
    }

    public void setPseudoCannum(String pseudoCannum) {
        this.pseudoCannum = pseudoCannum;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public ArrayList<Object> getRequests() {
        return requests;
    }

    public void setRequests(ArrayList<Object> requests) {
        this.requests = requests;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
