/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.purpose;

import java.sql.Connection;
import java.sql.Statement;
import edu.uga.ovpr.aam.Static;
import java.sql.ResultSet;
import java.util.ArrayList;
import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.information.GeneralDAO;
import java.util.HashMap;

/**
 *
 * @author submyers
 */
public class PurposeDataDAO {
    
    public static void GetPurposeData(PurposeDataForm purposeDataForm) throws Exception {
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getPurposeDataStat = conn.createStatement();
            final Statement getPurposeAffStat = conn.createStatement();
            final Statement getPurposeManagersStat = conn.createStatement();
            
            final String getPurposeDataQuery = "SELECT"
                    + " `ap`.`SHORT_NAME`"
                    + ", `ap`.`DESCRIPTION`"
                    + ", `ap`.`APPROVED`"
                    + ", DATE(`ap`.`CREATION_DATE`) AS `CREATION_DATE`"
                    + ", `ap`.`PERIOD`"
                    + ", `ap`.`ACTIVE` AS `PURPOSE_ACTIVE`"
                    + ", `p`.`FIRSTNAME`"
                    + ", `p`.`LASTNAME`"
                    + ", `p`.`MYID`"
                    + ", `p`.`CANNUM`"
                    + ", `p`.`ACTIVE` AS `PERSON_ACTIVE`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " ON `ap`.`CREATED_BY_CANNUM`=`p`.`CANNUM`"
                    + " WHERE"
                    + " `ap`.`ID`='" + GeneralDAO.CleanMySQLString(purposeDataForm.getPurposeId()) + "'";
            //System.out.println("Get Purpose Data Query:\n" + getPurposeDataQuery + "\n");
            final ResultSet getPurposeDataRs = getPurposeDataStat.executeQuery(getPurposeDataQuery);
            if(getPurposeDataRs.next()){
                HashMap<String,Object> data = new HashMap<String,Object>();
                data.put("SHORT_NAME", getPurposeDataRs.getString("SHORT_NAME"));
                data.put("DESCRIPTION", getPurposeDataRs.getString("DESCRIPTION"));
                data.put("APPROVED", getPurposeDataRs.getString("APPROVED"));
                data.put("CREATION_DATE", getPurposeDataRs.getString("CREATION_DATE"));
                data.put("PERIOD", getPurposeDataRs.getString("PERIOD"));
                data.put("PURPOSE_ACTIVE", getPurposeDataRs.getString("PURPOSE_ACTIVE"));
                data.put("FIRSTNAME", getPurposeDataRs.getString("FIRSTNAME"));
                data.put("LASTNAME", getPurposeDataRs.getString("LASTNAME"));
                data.put("MYID", getPurposeDataRs.getString("MYID"));
                data.put("CANNUM", getPurposeDataRs.getString("CANNUM"));
                data.put("PERSON_ACTIVE", getPurposeDataRs.getString("PERSON_ACTIVE"));
                purposeDataForm.setPurposeData(data);
            }
            
            final String getPurposeAffQuery = "SELECT"
                    + " `apl`.`APPROVED`"
                    + ", `apl`.`EXPIRES`"
                    + ", date(`apl`.`EXPIRATION_DATE`) AS `EXPIRATION_DATE`"
                    + ", `a`.`ID`"
                    + ", `a`.`FIRSTNAME`"
                    + ", `a`.`LASTNAME`"
                    + ", `a`.`PSEUDOCAN`"
                    + ", `a`.`OVPRID`"
                    + ", `a`.`EMAIL`"
                    + ", `a`.`ACTIVE`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    + " ON `apl`.`AFFILIATE_ID`=`a`.`ID`"
                    + " WHERE"
                    + " `apl`.`AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(purposeDataForm.getPurposeId()) + "'"
                    + " AND `apl`.`ACTIVE`='1'"
                    + " ORDER BY"
                    + " FIND_IN_SET(`APPROVED`, 'Submitted,Approved,Rejected'), `LASTNAME`, `FIRSTNAME`, `OVPRID`";
            //System.out.println("Get Purpose Aff Query:\n" + getPurposeAffQuery + "\n");
            final ResultSet getPurposeAffRs = getPurposeAffStat.executeQuery(getPurposeAffQuery);
            while(getPurposeAffRs.next()){
                HashMap<String,Object> data = new HashMap<String,Object>();
                data.put("APPROVED",getPurposeAffRs.getString("APPROVED"));
                data.put("EXPIRES",getPurposeAffRs.getString("EXPIRES"));
                data.put("EXPIRATION_DATE",getPurposeAffRs.getString("EXPIRATION_DATE"));
                data.put("ID",getPurposeAffRs.getString("ID"));
                data.put("FIRSTNAME",getPurposeAffRs.getString("FIRSTNAME"));
                data.put("LASTNAME",getPurposeAffRs.getString("LASTNAME"));
                data.put("PSEUDOCAN",getPurposeAffRs.getString("PSEUDOCAN"));
                data.put("OVPRID",getPurposeAffRs.getString("OVPRID"));
                data.put("EMAIL",getPurposeAffRs.getString("EMAIL"));
                data.put("ACTIVE",getPurposeAffRs.getString("ACTIVE"));
                purposeDataForm.getPurposeAff().add(data);
            }
            
            final String getPurposeManagersQuery = "SELECT"
                    + " `p`.`FIRSTNAME`"
                    + ", `p`.`LASTNAME`"
                    + ", `p`.`MYID`"
                    + ", `p`.`CANNUM`"
                    + ", `d`.`DEPTNAME`"
                    + ", `d`.`DEPTNUM`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " ON `aap`.`AFFILIATE_ADMIN_ID`=`aa`.`ID`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " ON `aa`.`CANNUM`=`p`.`CANNUM`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`DEPT` `d`"
                    + " ON `p`.`HOMEDEPT_ID`=`d`.`ID`"
                    + " WHERE"
                    + " `aap`.`AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(purposeDataForm.getPurposeId()) + "'"
                    + " AND `aap`.`PROXY`='0'"
                    + " AND `aap`.`ACTIVE`='1'";
            //System.out.println("Get Purpose Managers Query:\n" + getPurposeManagersQuery + "\n");
            final ResultSet getPurposeManagersRs = getPurposeManagersStat.executeQuery(getPurposeManagersQuery);
            while(getPurposeManagersRs.next()){
                HashMap<String,Object> data = new HashMap<String,Object>();
                data.put("FIRSTNAME", getPurposeManagersRs.getString("FIRSTNAME"));
                data.put("LASTNAME", getPurposeManagersRs.getString("LASTNAME"));
                data.put("MYID", getPurposeManagersRs.getString("MYID"));
                data.put("CANNUM", getPurposeManagersRs.getString("CANNUM"));
                data.put("DEPTNAME", getPurposeManagersRs.getString("DEPTNAME"));
                data.put("DEPTNUM", getPurposeManagersRs.getString("DEPTNUM"));
                purposeDataForm.getPurposeManagers().add(data);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
