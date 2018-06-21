/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.manager;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Static;
import edu.uga.ovpr.aam.information.GeneralDAO;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author submyers
 */
public class ManCreateNewAffDAO {

    public static String CreateNewAffiliate(ManCreateNewAffForm manCreateNewAffForm, HttpServletRequest request, String cannum) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement checkCurrAffsStat = conn.createStatement();
            final Statement getMaxPseudoCanStat = conn.createStatement();
            final Statement insertAffReqStat = conn.createStatement();
            final Statement getLastAffIdStat = conn.createStatement();
            final Statement insertAffReqExpStat = conn.createStatement();
            final Statement findAffPurposeLinkStat = conn.createStatement();
            final Statement insertAffPurposeLinkStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String checkCurrAffsQuery = "SELECT"
                    + " `ID`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE`"
                    + " WHERE"
                    + " `OVPRID`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getEmail()) + "'";
            final ResultSet checkCurrAffsRs = checkCurrAffsStat.executeQuery(checkCurrAffsQuery);
            int affId;
            if (checkCurrAffsRs.next()) {
                commitStat.execute("ROLLBACK");
                return "ERROR: An Affiliate account with the email address '" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getEmail()) + "' already exists.";
            } else {

                final String getMaxPseudoCanQuery = "SELECT"
                        + " MAX(`PSEUDOCAN`) AS `maxPseudoCan`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE`";
                final ResultSet getMaxPseudoCanRs = getMaxPseudoCanStat.executeQuery(getMaxPseudoCanQuery);
                int maxPseudoCan = 1;
                if (getMaxPseudoCanRs.next()) {
                    maxPseudoCan = getMaxPseudoCanRs.getInt("maxPseudoCan") + 1;
                }

                final String insertAffReqCmd = "INSERT INTO"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE`"
                        + " SET"
                        + " `CREATED`=NOW()"
                        + ", `CREATEDBY`='AAM'"
                        + ", `ACTIVE`='1'"
                        + ", `STATUS`='Non-Provisional'"
                        + ", `PSEUDOCAN`='" + maxPseudoCan + "'"
                        + ", `OVPRID`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getEmail()) + "'"
                        + ", `FIRSTNAME`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getFirstName()) + "'"
                        + ", `MIDDLENAME`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getMiddleName()) + "'"
                        + ", `LASTNAME`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getLastName()) + "'"
                        + ", `TITLE`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getTitle()) + "'"
                        + ", `DEPARTMENT`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getDepartment()) + "'"
                        + ", `ORGANIZATION`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getOrganization()) + "'"
                        + ", `EMAIL`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getEmail()) + "'"
                        + ", `PHONE`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getpAreaCode()) + "-" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getpPhone1()) + "-" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getpPhone2()) + "'";

                //System.out.println("Insert Aff Req Cmd:\n" + insertAffReqCmd + "\n");
                insertAffReqStat.executeUpdate(insertAffReqCmd);

                final String getLastAffIdQuery = "SELECT"
                        + " MAX(`id`) as `id`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE`";
                final ResultSet getLastAffIdRs = getLastAffIdStat.executeQuery(getLastAffIdQuery);
                if (getLastAffIdRs.next()) {
                    affId = getLastAffIdRs.getInt("id");
                } else {
                    throw new Exception("The following basic query did not generate results.\n" + getLastAffIdQuery + "\n");
                }

                if (manCreateNewAffForm.getZip2().isEmpty()) {
                    manCreateNewAffForm.setZip2("0000");
                }

                final String insertAffReqExpCmd = "INSERT INTO"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_EXPANSION`"
                        + " SET"
                        + " `AFFILIATE_ID`='" + affId + "'"
                        + ", `ADDR_1`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getAddr1()) + "'"
                        + ", `ADDR_2`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getAddr2()) + "'"
                        + ", `CITY`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getCity()) + "'"
                        + ", `STATE`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getState()) + "'"
                        + ", `COUNTRY`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getCountry()) + "'"
                        + ", `ZIP`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getZip1()) + "-" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getZip2()) + "'"
                        + ", `BUSINESS_PHONE_NUM`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getbAreaCode()) + "-" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getbPhone1()) + "-" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getbPhone2()) + "'"
                        + ", `FAX_NUMBER`='" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getfAreaCode()) + "-" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getfPhone1()) + "-" + GeneralDAO.CleanMySQLString(manCreateNewAffForm.getfPhone2()) + "'";
                //System.out.println("Insert Aff Req Exp Cmd:\n" + insertAffReqExpCmd + "\n");
                insertAffReqExpStat.executeUpdate(insertAffReqExpCmd);

                // Link the user with target purposes
                String purpose_ids = GeneralDAO.CleanMySQLString(manCreateNewAffForm.getPurpose_ids());
                String purpose_periods = GeneralDAO.CleanMySQLString(manCreateNewAffForm.getPurpose_periods());
                purpose_ids = purpose_ids.replaceAll("\\s+$", "");
                purpose_periods = purpose_periods.replaceAll("\\s+$", "");
                String[] purpose_arr = purpose_ids.split("\\s+");
                String[] purpose_period_arr = purpose_periods.split("\\s+");
                Integer[] period_time_arr = new Integer[purpose_period_arr.length];
                for (int i = 0; i < purpose_period_arr.length; i++) {
                    period_time_arr[i] = Integer.parseInt(purpose_period_arr[i]);
                    //System.out.println(">>> Period ID '" + purpose_period_arr[i] + "' has period time '" + period_time_arr[i] + "' <<<" );
                }
                for (int i = 0; i < purpose_arr.length; i++) {
                    String purpose_id = purpose_arr[i];
                    final String findAffPurposeLinkQuery = "SELECT"
                            + " 1"
                            + " FROM"
                            + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                            + " WHERE"
                            + " `apl`.`ACTIVE`='1'"
                            + " AND `apl`.`AFFILIATE_ID`='" + affId + "'"
                            + " AND `apl`.`ID`='" + purpose_id + "'";
                    //System.out.println("Find Aff Purpose Link Query: " + findAffPurposeLinkQuery + "\n");
                    final ResultSet findAffPurposeLinkRs = findAffPurposeLinkStat.executeQuery(findAffPurposeLinkQuery);
                    if (!findAffPurposeLinkRs.next()) {
                        String insertAffPurposeLinkCmd = "INSERT INTO"
                                + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                                + " SET"
                                + " `AFFILIATE_ID`='" + affId + "'"
                                + ", `AFFILIATE_PURPOSE_ID`='" + purpose_id + "'"
                                + ", `APPROVED`='Approved'"
                                + ", `MANAGE_DATE`=NOW()";
                        if (period_time_arr[i] == -1) {
                            insertAffPurposeLinkCmd += ", `EXPIRES`='0'";
                        } else {
                            insertAffPurposeLinkCmd += ", `EXPIRATION_DATE`=DATE_ADD(NOW(), INTERVAL " + period_time_arr[i] + " MONTH)";
                        }
                        insertAffPurposeLinkCmd += ", `MANAGER_CANNUM`='" + cannum + "'";
                        //System.out.println("Insert aff purpose link:\n" + insertAffPurposeLinkCmd + "\n");
                        insertAffPurposeLinkStat.executeUpdate(insertAffPurposeLinkCmd);
                    }
                }
            }
            commitStat.execute("COMMIT");
            return "";
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
