/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.purpose;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Static;
import edu.uga.ovpr.aam.information.GeneralDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author submyers
 */
public class ManPurposeDAO {

    public static String GetPurposes(ManPurposeForm manPurposeForm, String cannum, String role) throws Exception {
        String result = "";
        ArrayList<Object> dataArr = new ArrayList<Object>();
        ArrayList<String> purpsManageArr = new ArrayList<String>();
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");

            final Statement getPurposesStat = conn.createStatement();
            final Statement getAllPurpsStat = conn.createStatement();
            
            final String getPurposesQuery = "SELECT DISTINCT"
                    + " `ap`.`ID`"
                    + ", `ap`.`ACTIVE`"
                    + ", `ap`.`SHORT_NAME`"
                    + ", `ap`.`DESCRIPTION`"
                    + ", `ap`.`PERIOD`"
                    + ", `ap`.`APPROVED`"
                    + ", DATE(`ap`.`CREATION_DATE`) AS `CREATION_DATE`"
                    + ", `p`.`FIRSTNAME`"
                    + ", `p`.`LASTNAME`"
                    + ", `p`.`MYID`"
                    + ", `p`.`ACTIVE`"
                    + ", `p`.`CANNUM`"
                    + ", CASE WHEN '" + role + "'='superuser' THEN '1'"
                    + "  WHEN `aap`.`ACTIVE` IS NOT NULL AND `aap`.`ACTIVE`='1' THEN '1'"
                    + "  ELSE '0' END AS `PERMISSION`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " ON `p`.`CANNUM`=`ap`.`CREATED_BY_CANNUM`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`PERSON` `admin`"
                    + " ON `admin`.`CANNUM`='" + cannum + "'"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " ON `aa`.`CANNUM`='" + cannum + "'"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                    + " ON `aap`.`AFFILIATE_ADMIN_ID`=`aa`.`ID`"
                    + " AND `aap`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                    + " AND `aap`.`ACTIVE`='1'"
                    + " WHERE"
                    + "`ap`.`ID` != '" + Constants.associate_code + "'"
                    + " AND"
                    + " `ap`.`ACTIVE`='1'"
                    + " AND ("
                    + " '" + role + "'='superuser'"
                    + " OR (`aap`.`ACTIVE` IS NOT NULL AND `aap`.`ACTIVE`='1')"
                    + " )"
                    + " ORDER BY"
                    + " FIND_IN_SET(`APPROVED`, 'Submitted,Approved,Rejected'), `SHORT_NAME`";
            //System.out.println("Get Manage Purposes Query:\n" + getPurposesQuery + "\n");
            final ResultSet getPurposesRs = getPurposesStat.executeQuery(getPurposesQuery);
            while (getPurposesRs.next()) {
                purpsManageArr.add(getPurposesRs.getString("ID"));
                HashMap<String, Object> data = new HashMap<String, Object>();
                data.put("ID", getPurposesRs.getString("ID"));
                data.put("ACTIVE", getPurposesRs.getString("ACTIVE"));
                data.put("APPROVED", getPurposesRs.getString("APPROVED"));
                data.put("SHORT_NAME", getPurposesRs.getString("SHORT_NAME"));
                data.put("DESCRIPTION", getPurposesRs.getString("DESCRIPTION"));
                data.put("PERIOD", getPurposesRs.getString("PERIOD"));
                data.put("CREATION_DATE", getPurposesRs.getString("CREATION_DATE"));
                data.put("FIRSTNAME", getPurposesRs.getString("FIRSTNAME"));
                data.put("LASTNAME", getPurposesRs.getString("LASTNAME"));
                data.put("MYID", getPurposesRs.getString("MYID"));
                data.put("ACTIVE", getPurposesRs.getString("ACTIVE"));
                data.put("CANNUM", getPurposesRs.getString("CANNUM"));
                //data.put("EXPIRED", getPurposesRs.getString("EXPIRED"));
                data.put("PERMISSION", getPurposesRs.getString("PERMISSION"));
                dataArr.add(data);
            }

            manPurposeForm.setManagerPurposes(dataArr);
            dataArr = new ArrayList<Object>();
   
            final String getAllPurpsQuery = "SELECT DISTINCT"
                    + " `ap`.`ID`"
                    + ", `ap`.`ACTIVE`"
                    + ", `ap`.`SHORT_NAME`"
                    + ", `ap`.`DESCRIPTION`"
                    + ", `ap`.`PERIOD`"
                    + ", `ap`.`APPROVED`"
                    + ", DATE(`ap`.`CREATION_DATE`) AS `CREATION_DATE`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " WHERE"
                    + " `ap`.`ID` != '" + Constants.associate_code + "' AND"
                    + " `ap`.`ACTIVE`='1'";
            final ResultSet getAllPurpsRs = getAllPurpsStat.executeQuery(getAllPurpsQuery);
            //System.out.println("Get All Purposes Query:\n" + getAllPurpsQuery + "\n");
            while(getAllPurpsRs.next()){
                if(!purpsManageArr.contains(getAllPurpsRs.getString("ID"))){
                    HashMap<String,Object> data = new HashMap<String,Object>();
                    data.put("ID", getAllPurpsRs.getString("ID"));
                    data.put("ACTIVE", getAllPurpsRs.getString("ACTIVE"));
                    data.put("SHORT_NAME", getAllPurpsRs.getString("SHORT_NAME"));
                    //System.out.println("Added short name: " + data.get("SHORT_NAME"));
                    data.put("DESCRIPTION", getAllPurpsRs.getString("DESCRIPTION"));
                    data.put("PERIOD", getAllPurpsRs.getString("PERIOD"));
                    data.put("APPROVED", getAllPurpsRs.getString("APPROVED"));
                    data.put("CREATION_DATE", getAllPurpsRs.getString("CREATION_DATE"));
                    dataArr.add(data);
                }
            }
            
            manPurposeForm.setPurposes(dataArr);
            
            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public static NewPurposeForm SetNewPurposeForm(String purposeId) throws Exception {
        NewPurposeForm newPurposeForm = new NewPurposeForm();
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getPurposeDataStat = conn.createStatement();
            
            final String getPurposeDataQuery = "SELECT"
                    + " `SHORT_NAME`"
                    + ", `DESCRIPTION`"
                    + ", `PERIOD`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                    + " WHERE"
                    + " `ID`='" + GeneralDAO.CleanMySQLString(purposeId) + "'"
                    + " AND `APPROVED`='Approved'"
                    + " AND `ACTIVE`='1'";
            //System.out.println("Get Purpose Data Query:\n" + getPurposeDataQuery + "\n");
            final ResultSet getPurposeDataRs = getPurposeDataStat.executeQuery(getPurposeDataQuery);
            if(getPurposeDataRs.next()){
               newPurposeForm.setShortName(getPurposeDataRs.getString("SHORT_NAME"));
               newPurposeForm.setDescription(getPurposeDataRs.getString("DESCRIPTION"));
               newPurposeForm.setPeriod(getPurposeDataRs.getString("PERIOD"));
               newPurposeForm.setPurposeId(purposeId);
            }

            return newPurposeForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        
    }
    
    public static UpdatePurposeForm SetUpdatePurposeForm(String purposeId,String role) throws Exception {
        UpdatePurposeForm updatePurposeForm = new UpdatePurposeForm();
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getPurposeDataStat = conn.createStatement();
            
            final String getPurposeDataQuery = "SELECT"
                    + " `SHORT_NAME`"
                    + ", `DESCRIPTION`"
                    + ", `PERIOD`"
                    + ", CASE WHEN `PERIOD`='-1' OR '" + role + "'='superuser' THEN '1'"
                    + "  ELSE '0' END AS `PERMISSION`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE`"
                    + " WHERE"
                    + " `ID`='" + GeneralDAO.CleanMySQLString(purposeId) + "'"
                    + " AND `APPROVED`='Approved'"
                    + " AND `ACTIVE`='1'";
            //System.out.println("Get Purpose Data Query:\n" + getPurposeDataQuery + "\n");
            final ResultSet getPurposeDataRs = getPurposeDataStat.executeQuery(getPurposeDataQuery);
            if(getPurposeDataRs.next()){
               updatePurposeForm.setShortName(getPurposeDataRs.getString("SHORT_NAME"));
               updatePurposeForm.setDescription(getPurposeDataRs.getString("DESCRIPTION"));
               updatePurposeForm.setPeriod(getPurposeDataRs.getString("PERIOD"));
               updatePurposeForm.setPermission(getPurposeDataRs.getString("PERMISSION"));
               updatePurposeForm.setPurposeId(purposeId);
            }

            return updatePurposeForm;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        
    }
}
