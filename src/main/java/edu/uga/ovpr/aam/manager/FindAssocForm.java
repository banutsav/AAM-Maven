/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uga.ovpr.aam.manager;

import java.util.HashMap;
import org.apache.struts.validator.ValidatorForm;

/**
 *
 * @author utsavb
 */
public class FindAssocForm extends ValidatorForm{
    private String action = "";
    private String uga_myid = "";
    private String non_uga_email = "";
    private String targAffId = "";
    private String executed = "";
    private String can_be_associate = "";
    private String is_associate = "";
    private String exp_date = "";
    private String expdate_months = "";
    private String na_expdate_months = "";
    private String never_expires = "";
    private String na_never_expires = "";
    HashMap<String,Object> results = new HashMap<String,Object>();
    
    public String getnon_uga_email()
    {
        return non_uga_email;
    }
    public void setnon_uga_email(String non_uga_email)
    {
        this.non_uga_email = non_uga_email;
    }
    
    public String getnever_expires()
    {
        return never_expires;
    }
    
    public void setnever_expires(String s)
    {
        this.never_expires = s;
    }
    
    public String getna_never_expires()
    {
        return na_never_expires;
    }
    
    public void setna_never_expires(String s)
    {
        this.na_never_expires = s;
    }
    
    public String getexpdate_months()
    {
        return expdate_months;
    }
    
    public void setexpdate_months(String s)
    {
        this.expdate_months = s;
    }
    
    /*
        expiration date information for new associates
    */
    
    public String getna_expdate_months()
    {
        return na_expdate_months;
    }
    
    public void setna_expdate_months(String s)
    {
        this.na_expdate_months = s;
    }
    
    public void setis_associate(String s)
    {
        this.is_associate = s;
    }
    
    public String getis_associate()
    {
        return (this.is_associate);
    }
    
    public void setexp_date(String s)
    {
        this.exp_date = s;
    }
    
    public String getexp_date()
    {
        return exp_date;
    }
    
    public void setcan_be_associate(String s)
    {
        this.can_be_associate = s;
    }
    
    public String getcan_be_associate()
    {
        return this.can_be_associate;
    }
    
    public void reset(){
        action="";
        uga_myid="";
        targAffId = "";
        executed = "";
        results = new HashMap<String,Object>();
    }
    
    /*
        methods to retrieve individual fields
        of the results object
    */
    
    public String get_cannum()
    {
        return (results.get("CANNUM").toString());
    }
    
    public String get_firstname()
    {
        return (results.get("FIRSTNAME").toString());
    }
    
    public String get_middlename()
    {
        return (results.get("MIDDLENAME").toString());
    }
    
    public String get_lastname()
    {
        return (results.get("LASTNAME").toString());
    }
    
    public String get_title()
    {
        return (results.get("TITLE").toString());
    }
    
    public String get_department()
    {
        return (results.get("homeDept").toString());
    }
    
    public String get_organization()
    {
        return (results.get("ORGANIZATION").toString());
    }
    
    public String get_email()
    {
        return (results.get("EMAIL").toString());
    }
    
    public String get_phone()
    {
        return (results.get("PHONE").toString());
    }
    
    public String get_addr1()
    {
        return (results.get("addr1").toString());
    }
    
    public String get_addr2()
    {
        return (results.get("addr2").toString());
    }
    
    public String get_city()
    {
        return (results.get("city").toString());
    }
    
    public String get_state()
    {
        return (results.get("state").toString());
    }
    
    public String get_zip()
    {
        return (results.get("zip").toString());
    }
    
    public String get_altphone()
    {
        return (results.get("ALTPHONE").toString());
    }
    
    public String get_fax()
    {
        return (results.get("FAX").toString());
    }
    
    public String get_fscodes()
    {
        return (results.get("FSCODES").toString());
    }
    
    public void resetResults()
    {
        can_be_associate = "";
        results = new HashMap<String,Object>();
    }

    public String getExecuted() {
        return executed;
    }

    public void setExecuted(String executed) {
        this.executed = executed;
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

    public String getuga_myid() {
        return uga_myid;
    }

    public void setuga_myid(String uga_myid) {
        this.uga_myid = uga_myid;
    }
    
}
