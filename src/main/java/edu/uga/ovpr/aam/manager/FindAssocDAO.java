/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.uga.ovpr.aam.manager;

import edu.uga.ovpr.aam.Static;
import edu.uga.ovpr.aam.information.GeneralDAO;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import org.apache.commons.lang.StringEscapeUtils;
import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Session;
import edu.uga.ovpr.statics.DynaResultSet;
import edu.uga.ovpr.statics.OVPRMethods;
import java.sql.ResultSet;
import org.apache.commons.beanutils.DynaBean;

/**
 *
 * @author utsavb
 */
public class FindAssocDAO {
    
    
    /*
        check if new associate email address is already used or not
    */
    
    public static boolean validateAssociateEmail(String email) throws Exception
    {
        final String q = "select `ID` from `" + Constants.DB_PAUL + "`.`AFFILIATE` where `EMAIL` = '" + email + "'";
        
        DynaResultSet result = OVPRMethods._SQL_ExecuteQuery(q, Constants.DS_MYSQL1);
        
        if(result.getTotalRecords()!=0)
            return true;
  
        return false;
    }
    
    /*
        check if an associate exists before doing an insert
        for a new associate record
    */
    
    public static boolean associateExists(String myid) throws Exception
    {
        final String q = "select `ID` from `" + Constants.DB_PAUL + "`.`AFFILIATE` where `OVPRID` = '" + 
                 myid + "';";
       
        DynaResultSet result = OVPRMethods._SQL_ExecuteQuery(q, Constants.DS_MYSQL1);
        
        if(result.getTotalRecords()!=0)
            return true;
        
        return false;
    }
    
    /*
        create NEW associate
    */
    
    public static int createNewAssociate(String myid, String date, String cannum, FindAssocForm findAssocForm) throws Exception
    {
        int r1 = 0, r2 = 0, r3 = 0;
        String id = "";
        
        /*
            insert record into AFFILIATE table
        */
       
        final String insertQuery = "insert into `" + Constants.DB_PAUL + "`.`AFFILIATE` set " + 
                "`CREATED` = NOW(), " + 
                "`CREATEDBY` = 'AAM', " +
                "`ACTIVE` = '1', " + 
                "`STATUS` = 'Non-Provisional', " +
                "`PSEUDOCAN` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_cannum()) + "', " +
                "`CANNUM` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_cannum()) + "', " +
                "`OVPRID` = '" + myid + "', " +
                "`FIRSTNAME` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_firstname()) + "', " +
                "`MIDDLENAME` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_middlename()) + "', " + 
                "`LASTNAME` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_lastname()) + "', " +
                "`TITLE` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_title()) + "', " + 
                "`DEPARTMENT` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_department()) + "', " +
                "`ORGANIZATION` = 'University of Georgia', " + 
                "`EMAIL` = '" + GeneralDAO.CleanMySQLString(findAssocForm.getnon_uga_email()) + "', " +
                "`PHONE` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_phone()) + "'";
         
        Connection conn = Static.getConnection(Constants.DS_MYSQL1);
        final Statement s = conn.createStatement();
        r1 = s.executeUpdate(insertQuery);
        
        /*
            insert into AFFILIATE EXPANSION table
        */
        
        if(r1>0)
        {
           final String q = "select `ID` from `" + Constants.DB_PAUL + "`.`AFFILIATE` where `OVPRID` = '" + myid + "';";
           DynaResultSet result = OVPRMethods._SQL_ExecuteQuery(q, Constants.DS_MYSQL1);
           
           if(result.getTotalRecords()!=0)
           {    
                id = result.getRows().get(0).get("ID").toString();
                
                final String query2 = "insert into `" + Constants.DB_PAUL + "`.`AFFILIATE_EXPANSION` set" + 
                " `AFFILIATE_ID` = '" + id + "'," + 
                " `ADDR_1` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_addr1()) + "'," +
                " `ADDR_2` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_addr2()) + "'," +
                " `CITY` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_city()) + "'," +
                " `STATE` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_state()) + "'," +
                " `COUNTRY` = 'United States'," +
                " `ZIP` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_zip()) + "'," + 
                " `BUSINESS_PHONE_NUM` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_altphone()) + "'," + 
                //" `FAX_NUMBER` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_fax()) + "'," +
                " `COMMENTS` = 'Associate'," + 
                " `FSCODES` = '" + GeneralDAO.CleanMySQLString(findAssocForm.get_fscodes()) + "';";
                r2 = s.executeUpdate(query2);
            }            
        
        }
        
        /*
            insert into AFFILIATE PURPOSE LINK table
        */
        
        /*
            check if expiration date set or NEVER EXPIRES
        */
       
        String expdate, expires;
        
        if(date.equals("NE"))
        { 
            expdate = "NOW()";
            expires = "0";
        }    
        else
        {    
            expires = "1";
            expdate = "'" + date + "'";
        }
        
        if(r2>0)
        {
            final String query3 = "insert into `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` set" + 
                    " `AFFILIATE_ID` = '" + id + "'," +
                    " `AFFILIATE_PURPOSE_ID` = '" + Constants.associate_code + "'," +
                    " `ACTIVE` = '1'," + 
                    " `APPROVED` = 'Approved'," +
                    " `EXPIRES` = '" + expires + "'," +
                    " `MANAGER_CANNUM` = '" + cannum + "'," +
                    " `CREATION_DATE` = NOW()," +
                    " `EXPIRATION_DATE` = " + expdate + "," +
                    " `MANAGE_DATE` = NOW();"; 
            
            r3 = s.executeUpdate(query3);
        }
        
        /*
            if all 3 r1, r2, r3 return positive values then the entire update
            went through and the ASSOCIATE record should have consistent 
            values across all affiliate tables
        */
        
        return (r1*r2*r3);
    }
    
    /*
        update expiration date for existing associate
    */
    
    public static int updateExpirationDateForAssociate(String myid, String date) throws Exception
    {
        final String q = "select `ID` from `" + Constants.DB_PAUL + "`.`AFFILIATE` where `OVPRID` = '" + 
                 myid + "';";
        int r = 0;
        DynaResultSet result = OVPRMethods._SQL_ExecuteQuery(q, Constants.DS_MYSQL1);
        
        if(result.getTotalRecords()!=0)
        {
            DynaBean b = result.getRows().get(0);
            String id = b.get("ID").toString();
            
            String query;
            
            if(date.equals("NE"))
            {
                query = "update `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` set `EXPIRES` = '0' where `AFFILIATE_ID` = '" + id + "'";
            }   
            else
            {    
                query = "update `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` set" + 
                        " `EXPIRES` = '1'," +
                        " `EXPIRATION_DATE` = '"
                + date + "' where `AFFILIATE_ID` = '" + id + "'";
            }
            Connection conn = Static.getConnection(Constants.DS_MYSQL1);
            final Statement s = conn.createStatement();
            r = s.executeUpdate(query);
        }
        
        return r;
    }
    
    /*
        sets the expiration date for an existing associate 
        this result is displayed in the associate lookup page
    */
    
    public static void setExpirationDateForAssociate(FindAssocForm findAssocForm) throws Exception
    {
                
        final String query = "select `ap`.`EXPIRATION_DATE`, `ap`.`EXPIRES` " +
                "from `" + Constants.DB_PAUL + "`.`AFFILIATE` `a` left join `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `ap` " + 
                "on (`a`.`ID` = `ap`.`AFFILIATE_ID`) " +
                "where `a`.`OVPRID` = '" + findAssocForm.getuga_myid() + "';";
        
        DynaResultSet result = OVPRMethods._SQL_ExecuteQuery(query, Constants.DS_MYSQL1);
        if(result.getTotalRecords()!=0)
        {
            
            DynaBean b = result.getRows().get(0);
            String exp_date = b.get("EXPIRATION_DATE").toString();
            String ne = b.get("EXPIRES").toString();
            
            if(ne.equals("0"))
                exp_date = "never expires";
                
            findAssocForm.setexp_date(exp_date);
        }
        
    }
    
    /*
        check if the person is an existing associate
    */
    
    public static String checkAssociateStatus(FindAssocForm findAssocForm) throws Exception
    {
        final String query = "select `PSEUDOCAN` from `" + Constants.DB_PAUL + "`.`AFFILIATE` where `OVPRID` = '" + StringEscapeUtils.escapeSql(findAssocForm.getuga_myid()) + "';"; 
        DynaResultSet result = OVPRMethods._SQL_ExecuteQuery(query, Constants.DS_MYSQL1); 
        
        if(result.getTotalRecords()!=0)
        {    
            // associate already exist
            setExpirationDateForAssociate(findAssocForm);
            findAssocForm.setis_associate("yes");
            return("This person is already an Associate");
        }
        
        //associate does not exist, see if eligible or not to become one
        
        findAssocForm.setis_associate("no");
        
        if(findAssocForm.getcan_be_associate().equals("eligible"))
            return("This person is eligible to become an Associate");
            
        return("This person is NOT eligible to become an Associate");
        
    }
    
    /*
        bring up the UGA record for the person, if it exists
    */
    
    public static void FindAssoc(FindAssocForm findAssocForm) throws Exception
    {
        Connection conn = null;
        final HashMap<String, Object> results = new HashMap<String, Object>();
        
        conn = Static.getConnection(Constants.DS_MYSQL1);
        final Statement stmt = conn.createStatement();
        
        final String assocQuery = "select `P`.`ID`,`P`.`firstName`,`P`.`middleName`,`P`.`lastName`,`P`.`canNum`,`P`.`email`,`P`.`phone`,`P`.`myid`,`P`.`initial`,`P`.`title`"
                + ",`P`.`prefix`,`P`.`suffix`,`EP`.`homeDept`,`P`.`altphone`,`P`.`fax`,`P`.`escodes`"
                + ",`EP`.`jobClass`,`EP`.`jobTitle`,`EP`.`termDate`,`EP`.`isEnrolled`,`EP`.`addr1`,`EP`.`addr2`,`EP`.`city`,`EP`.`state`,`EP`.`zip`,`EP`.`ugaHRStatus`"
                + ",`EP`.`ugaLastPayDate`,`EP`.`ugaTermCreditHours`,`EP`.`ugaInactiveDate`"
                + "from `" + Constants.DB_PAUL + "`.`PERSON` `P`"
                + "left join `authdata3`.`EITS_Persons` `EP` on `P`.`canNum`=`EP`.`CAN9`"
                + "where `P`.`MYID`='" + GeneralDAO.CleanMySQLString(findAssocForm.getuga_myid()) + "';";
        
        final ResultSet rs = stmt.executeQuery(assocQuery);
        
        if (rs.next()) 
        {
            results.put("FIRSTNAME", rs.getString("FIRSTNAME"));
            results.put("MIDDLENAME", rs.getString("MIDDLENAME"));
            results.put("LASTNAME", rs.getString("LASTNAME"));
            results.put("CANNUM", rs.getString("CANNUM"));
            results.put("EMAIL", rs.getString("EMAIL"));
            results.put("PHONE", rs.getString("PHONE"));
            results.put("MYID", rs.getString("MYID"));
            results.put("INITIAL", rs.getString("INITIAL"));        
            results.put("TITLE", rs.getString("TITLE"));
            results.put("PREFIX", rs.getString("PREFIX"));
            results.put("SUFFIX", rs.getString("SUFFIX"));
            results.put("homeDept", rs.getString("homeDept"));
            results.put("ALTPHONE", rs.getString("ALTPHONE"));
            results.put("FAX", rs.getString("FAX"));
            results.put("ESCODES", parseFSCodes(rs.getString("ESCODES")));
            results.put("FSCODES", rs.getString("ESCODES"));
            results.put("jobClass", rs.getString("jobClass"));
            results.put("jobTitle", rs.getString("jobTitle"));
            results.put("termDate", rs.getString("termDate"));
            
            results.put("ugaLastPayDate", rs.getString("ugaLastPayDate"));
            results.put("ugaTermCreditHours", rs.getString("ugaTermCreditHours"));
            results.put("ugaInactiveDate", rs.getString("ugaInactiveDate"));
            
            results.put("isEnrolled", rs.getString("isEnrolled"));
            results.put("addr1", rs.getString("addr1"));
            results.put("addr2", rs.getString("addr2"));
            results.put("city", rs.getString("city"));
            results.put("state", rs.getString("state"));
            results.put("zip", rs.getString("zip"));
            results.put("ugaHRStatus", rs.getString("ugaHRStatus") + "-" + getHRStatusCode(rs.getString("ugaHRStatus")));
            
            findAssocForm.setResults(results);
            
            /*
                set flag to determine whether or not person is eligible to be an associate
            */
        
            if((rs.getString("ugaHRStatus").equals("0"))||(rs.getString("isEnrolled").equals("Y")))
                findAssocForm.setcan_be_associate("not_eligible");
            else
                findAssocForm.setcan_be_associate("eligible");
        }       
        
        
    }
    
      /*
        returns the FS code description from the numeric FS code
    */
    
    public static String getFSCodeDesc(final String FSCode) throws Exception
    {
        
        /*
            blank FS codes causes query to be executed with a valid result
            hence hard coding this error check
        */
        
        if(FSCode.equals(""))
            return "";
        
        final String descQuery = "select `PATRON_TYPE` from `" + Constants.DB_PAUL + "`.`FSCODES` where `FSCODE` = '" + StringEscapeUtils.escapeSql(FSCode) + "';";

        try
        {
            DynaResultSet result = OVPRMethods._SQL_ExecuteQuery(descQuery, Constants.DS_MYSQL1);
            
            if(result.getTotalRecords()==0)
                return "";
            
            DynaBean b = result.getRows().get(0);
            String desc = b.get("PATRON_TYPE").toString();
            return desc;
        
        }
        catch(Exception ex)
        {
            System.out.println("ERROR: exception = " + ex.toString());
        }
        
        return "";
        
    }
    
    
    /*
        gets the individual FS codes, which are delimited by the "!" character
        gets the description of the individual FS code
        combines all these individual descriptions into a string
    */
    
    public static String parseFSCodes(final String FSCodes) throws Exception
    {
        String result = "";
        String delims = "[!]";
        String[] codes = FSCodes.split(delims);
        for (String code : codes) {
            result = result + " | " + code + " - " + getFSCodeDesc(code) + " | ";
        }
        return result;
    }
    
    /*
        retrieve the HR Status Desciption from the status code
    */
    
    public static String getHRStatusCode(final String HRstatus) throws Exception
    {
        
        /*
            issues with status code = ""
            SQL query was still executed and returned a row of results
            hence hard coded
        */
        
        if(HRstatus.equals(""))
            return "";
        
        final String statusQuery = "select `CODE` from `" + Constants.DB_PAUL + "`.`HR_STATUS_CODES` `H` where `H`.`HR_STATUS` = '" + StringEscapeUtils.escapeSql(HRstatus) + "';";

        try
        {
            DynaResultSet result = OVPRMethods._SQL_ExecuteQuery(statusQuery, Constants.DS_MYSQL1);
        
            if(result.getTotalRecords()==0)
                return "";
            
            DynaBean b = result.getRows().get(0);
            String code = b.get("CODE").toString();
            return code;
        
        }
        catch(Exception ex)
        {
            System.out.println("ERROR: exception = " + ex.toString());
        }
        
        return "";
    }
}
