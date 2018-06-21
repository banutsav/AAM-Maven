/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.superuser;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Static;
import edu.uga.ovpr.aam.information.GeneralDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

/**
 *
 * @author submyers
 */
public class ControlAffActiveDAO {

    public static void GetAffInfo(ControlAffActiveForm controlAffActiveForm) throws Exception {
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getInfoStat = conn.createStatement();
            final Statement getPurposesStat = conn.createStatement();

            final String getInfoQuery = "SELECT"
                    + " `a`.`FIRSTNAME`"
                    + ", `a`.`LASTNAME`"
                    + ", `a`.`OVPRID`"
                    + ", `a`.`PSEUDOCAN`"
                    + ", `a`.`ORGANIZATION`"
                    + ", `a`.`DEPARTMENT`"
                    + ", `a`.`TITLE`"
                    + ", `a`.`EMAIL`"
                    + ", `a`.`PHONE`"
                    + ", `a`.`ACTIVE`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    + " WHERE"
                    + " `a`.`id`='" + controlAffActiveForm.getAffId() + "'";
            final ResultSet getInfoRs = getInfoStat.executeQuery(getInfoQuery);
            if (getInfoRs.next()) {
                controlAffActiveForm.setFirstName(getInfoRs.getString("FIRSTNAME"));
                controlAffActiveForm.setLastName(getInfoRs.getString("LASTNAME"));
                controlAffActiveForm.setOvprId(getInfoRs.getString("OVPRID"));
                controlAffActiveForm.setPseudoCan(getInfoRs.getString("PSEUDOCAN"));
                controlAffActiveForm.setOrganization(getInfoRs.getString("ORGANIZATION"));
                controlAffActiveForm.setDepartment(getInfoRs.getString("DEPARTMENT"));
                controlAffActiveForm.setTitle(getInfoRs.getString("TITLE"));
                controlAffActiveForm.setEmail(getInfoRs.getString("EMAIL"));
                controlAffActiveForm.setPhone(getInfoRs.getString("PHONE"));
                controlAffActiveForm.setActive(getInfoRs.getString("ACTIVE"));
            }

            final String getPurposesQuery = "SELECT"
                    + " `ap`.`ID`"
                    + ", `ap`.`SHORT_NAME`"
                    + ", `ap`.`DESCRIPTION`"
                    + ", `ap`.`PERIOD`"
                    + ", `apl`.`APPROVED`"
                    + ", `apl`.`EXPIRES`"
                    + ", `apl`.`MANAGER_CANNUM`"
                    + ", CASE WHEN `apl`.`EXPIRATION_DATE`<=NOW() AND `apl`.`EXPIRES`='1' THEN 1"
                    + "  ELSE 0 END AS `EXPIRED`"
                    + ", DATE(`apl`.`EXPIRATION_DATE`) AS `EXPIRATION_DATE`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " ON `apl`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                    + " AND `ap`.`ACTIVE`='1'"
                    + " WHERE"
                    + " `apl`.`AFFILIATE_ID`='" + controlAffActiveForm.getAffId() + "'"
                    + " ORDER BY"
                    + " FIND_IN_SET(`apl`.`APPROVED`, 'Submitted,Approved,Rejected'), `ap`.`SHORT_NAME`";
            //System.out.println("Get Aff Purposes Query:\n" + getPurposesQuery + "\n");
            final ResultSet getPurposesRs = getPurposesStat.executeQuery(getPurposesQuery);
            controlAffActiveForm.getPurposes().clear();
            while (getPurposesRs.next()) {
                HashMap<String, Object> data = new HashMap<String, Object>();
                data.put("ID", getPurposesRs.getString("ID"));
                data.put("SHORT_NAME", getPurposesRs.getString("SHORT_NAME"));
                data.put("DESCRIPTION", getPurposesRs.getString("DESCRIPTION"));
                data.put("PERIOD", getPurposesRs.getString("PERIOD"));
                data.put("APPROVED", getPurposesRs.getString("APPROVED"));
                data.put("EXPIRES", getPurposesRs.getString("EXPIRES"));
                data.put("EXPIRED", getPurposesRs.getString("EXPIRED"));
                data.put("MANAGER_CANNUM", getPurposesRs.getString("MANAGER_CANNUM"));
                data.put("EXPIRATION_DATE", getPurposesRs.getString("EXPIRATION_DATE"));
                controlAffActiveForm.getPurposes().add(data);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void DeactivateAff(ControlAffActiveForm controlAffActiveForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement updatePurposeStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String updatePurposesCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE`"
                    + " SET"
                    + " `ACTIVE`='0'"
                    + " WHERE"
                    + " `ID`='" + controlAffActiveForm.getAffId() + "'";
            //System.out.println("Update Purpose Command:\n" + updatePurposesCmd + "\n");
            updatePurposeStat.executeUpdate(updatePurposesCmd);

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

    public static void ActivateAff(ControlAffActiveForm controlAffActiveForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement updatePurposeStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String updatePurposesCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE`"
                    + " SET"
                    + " `ACTIVE`='1'"
                    + " WHERE"
                    + " `ID`='" + controlAffActiveForm.getAffId() + "'";
            //System.out.println("Update Purpose Command:\n" + updatePurposesCmd + "\n");
            updatePurposeStat.executeUpdate(updatePurposesCmd);

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

    public static void DeactivateAll(ControlAffActiveForm controlAffActiveForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement updatePurposeStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String updatePurposesCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                    + " SET"
                    + " `EXPIRATION_DATE`=NOW()"
                    + ", `EXPIRES`='1'"
                    + " WHERE"
                    + " `AFFILIATE_ID`='" + controlAffActiveForm.getAffId() + "'"
                    + " AND ("
                    + " `EXPIRATION_DATE`>NOW()"
                    + " OR `EXPIRES`='1')";
            //System.out.println("Update Purpose Command:\n" + updatePurposesCmd + "\n");
            updatePurposeStat.executeUpdate(updatePurposesCmd);

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

    public static void UpdatePurpose(ControlAffActiveForm controlAffActiveForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement updatePurposeStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            String updatePurposeCmd;
            if (controlAffActiveForm.getTargStatus().equalsIgnoreCase("Approved")) {
                String expDate = controlAffActiveForm.getTargExpDate().replaceAll("\\s+.*$", "");
                if (expDate.contains("/")) {
                    String[] dateVals = expDate.split("/");
                    expDate = dateVals[2] + "-" + dateVals[0] + "-" + dateVals[1];
                }
                updatePurposeCmd = "UPDATE"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                        + " SET"
                        + " `EXPIRATION_DATE`='" + GeneralDAO.CleanMySQLString(expDate) + "'"
                        + ", `APPROVED`='" + GeneralDAO.CleanMySQLString(controlAffActiveForm.getTargStatus()) + "'"
                        + ", `EXPIRES`='" + GeneralDAO.CleanMySQLString(controlAffActiveForm.getTargExpires()) + "'"
                        + " WHERE"
                        + " `AFFILIATE_ID`='" + controlAffActiveForm.getAffId() + "'"
                        + " AND `AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(controlAffActiveForm.getTargPurposeId()) + "'";
            } else {
                updatePurposeCmd = "UPDATE"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                        + " SET"
                        + " `EXPIRATION_DATE`=NOW()"
                        + ", `APPROVED`='" + GeneralDAO.CleanMySQLString(controlAffActiveForm.getTargStatus()) + "'"
                        + ", `EXPIRES`='" + GeneralDAO.CleanMySQLString(controlAffActiveForm.getTargExpires()) + "'"
                        + " WHERE"
                        + " `AFFILIATE_ID`='" + controlAffActiveForm.getAffId() + "'"
                        + " AND `AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(controlAffActiveForm.getTargPurposeId()) + "'";
            }
            //System.out.println("Update Purpose Command:\n" + updatePurposeCmd + "\n");
            updatePurposeStat.executeUpdate(updatePurposeCmd);

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
