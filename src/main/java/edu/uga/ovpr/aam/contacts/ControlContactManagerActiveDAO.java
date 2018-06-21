/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.contacts;

import edu.uga.ovpr.aam.superuser.*;
import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Static;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author submyers
 */
public class ControlContactManagerActiveDAO {

    public static void GetContactManInfo(ControlContactManagerActiveForm controlContactManagerActiveForm) throws Exception {
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getBasicInfoStat = conn.createStatement();

            final String getBasicInfoQuery = "SELECT"
                    + " `p`.`ID` AS `PERSON_ID`"
                    + ", `aa`.`ACTIVE` AS `ADMIN_ACTIVE`"
                    + ", `p`.`FIRSTNAME`"
                    + ", `p`.`LASTNAME`"
                    + ", `p`.`MYID`"
                    + ", `p`.`CANNUM`"
                    + ", `p`.`EMAIL`"
                    + ", `p`.`PHONE`"
                    + ", `p`.`TITLE`"
                    + ", `d`.`DEPTNAME`"
                    + ", `d`.`DEPTNUM`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`CONTACT_ADMIN` `aa`"
                    + " ON `aa`.`CANNUM`=`p`.`CANNUM`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`DEPT` `d`"
                    + " ON `p`.`HOMEDEPT_ID`=`d`.`ID`"
                    + " WHERE"
                    + " `aa`.`CANNUM`='" + controlContactManagerActiveForm.getAdminId() + "'";
            
            //System.out.println("Get Basic Man Info:\n" + getBasicInfoQuery + "\n");
            
            final ResultSet getBasicInfoRs = getBasicInfoStat.executeQuery(getBasicInfoQuery);
            if (getBasicInfoRs.next()) {
                controlContactManagerActiveForm.setAdminActive(getBasicInfoRs.getString("ADMIN_ACTIVE"));
                controlContactManagerActiveForm.setFirstName(getBasicInfoRs.getString("FIRSTNAME"));
                controlContactManagerActiveForm.setLastName(getBasicInfoRs.getString("LASTNAME"));
                controlContactManagerActiveForm.setMyId(getBasicInfoRs.getString("MYID"));
                controlContactManagerActiveForm.setCannum(getBasicInfoRs.getString("CANNUM"));
                controlContactManagerActiveForm.setEmail(getBasicInfoRs.getString("EMAIL"));
                controlContactManagerActiveForm.setPhone(getBasicInfoRs.getString("PHONE"));
                controlContactManagerActiveForm.setTitle(getBasicInfoRs.getString("TITLE"));
                controlContactManagerActiveForm.setDeptName(getBasicInfoRs.getString("DEPTNAME"));
                controlContactManagerActiveForm.setDeptNum(getBasicInfoRs.getString("DEPTNUM"));
            }


        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (Exception ex) {
                    throw ex;
                }
            }
        }
    }

    public static void DeactivateManager(ControlContactManagerActiveForm controlContactManagerActiveForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
          
            final Statement deactivateAdminStat = conn.createStatement();
            
            commitStat.execute("START TRANSACTION");
            
            final String deactivateAdminCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`CONTACT_ADMIN`"
                    + " SET"
                    + " `ACTIVE`='0'"
                    + " WHERE"
                    + " `CANNUM`='" + controlContactManagerActiveForm.getAdminId() + "'";
            
            //System.out.println("Deactivate Admin:\n" + deactivateAdminCmd + "\n");
            deactivateAdminStat.executeUpdate(deactivateAdminCmd);

            commitStat.execute("COMMIT");
        } catch (Exception ex) {
            commitStat.execute("ROLLBACK");
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void ActivateManager(ControlContactManagerActiveForm controlContactManagerActiveForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement activateAdminStat = conn.createStatement();
            
            commitStat.execute("START TRANSACTION");
            
            final String activateAdminCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`CONTACT_ADMIN`"
                    + " SET"
                    + " `ACTIVE`='1'"
                    + " WHERE"
                    + " `CANNUM`='" + controlContactManagerActiveForm.getAdminId() + "'";
            
            //System.out.println("Deactivate Admin:\n" + activateAdminCmd + "\n");
            activateAdminStat.executeUpdate(activateAdminCmd);

            commitStat.execute("COMMIT");
        } catch (Exception ex) {
            commitStat.execute("ROLLBACK");
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
}
