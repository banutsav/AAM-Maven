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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author submyers
 */
public class ViewManCurrAffDAO {

    public static ArrayList<Object> FindAffWithArrList(ViewManCurrAffForm viewManCurrAffForm, String userCannum, String role) throws Exception {
        Connection conn = null;
        ArrayList<Object> results = new ArrayList<Object>();
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement findAffStat = conn.createStatement();
            final Statement nextExpStat = conn.createStatement();

            String findAffQuery = "SELECT DISTINCT"
                    + " `a`.`ID` AS `ID`"
                    + ", `a`.`FIRSTNAME` AS `FIRSTNAME`"
                    + ", `a`.`MIDDLENAME` AS `MIDDLENAME`"
                    + ", `a`.`LASTNAME` AS `LASTNAME`"
                    + ", `a`.`OVPRID` AS `OVPRID`"
                    + ", `a`.`PSEUDOCAN` AS `PSEUDOCAN`"
                    + ", `a`.`TITLE` AS `TITLE`"
                    + ", `a`.`DEPARTMENT` AS `DEPARTMENT`"
                    + ", `a`.`ORGANIZATION` AS `ORGANIZATION`"
                    + ", `a`.`PHONE` AS `PHONE`"
                    //+ ", CASE WHEN `a`.`ACTIVE`='1' AND `apl`.`EXPIRATION_DATE`>NOW() AND `apl`.`ID` IS NOT NULL THEN '1'"
                    //+ "  ELSE '0' END AS `ACTIVE`"
                    + ", `a`.`ACTIVE` AS `ACTIVE`"
                    //+ ", `ae`.`INSTITUTION_COMPANY` AS `INSTITUTION_COMPANY`"
                    + ", `ae`.`ADDR_1` AS `ADDR_1`"
                    + ", `ae`.`ADDR_2` AS `ADDR_2`"
                    + ", `ae`.`CITY` AS `CITY`"
                    + ", `ae`.`STATE` AS `STATE`"
                    + ", `ae`.`COUNTRY` AS `COUNTRY`"
                    + ", `ae`.`ZIP` AS `ZIP`"
                    + ", `ae`.`BUSINESS_PHONE_NUM` AS `BUSINESS_PHONE_NUM`"
                    //+ ", `ae`.`ALT_PHONE_NUM` AS `ALT_PHONE_NUM`"
                    + ", `ae`.`FAX_NUMBER` AS `FAX_NUMBER`"
                    //+ ", CASE WHEN `ae`.`EXPIRATION_DATE` IS NULL THEN ''"
                    //+ "  ELSE `ae`.`EXPIRATION_DATE` END AS `EXPIRATION_DATE`"
                    + ", `ae`.`COMMENTS` AS `COMMENTS`"
                    + ", `ae`.`FSCODES` AS `FSCODES`"
                    + ", `apl`.`AFFILIATE_ID` AS `AFFILIATE_ID`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_EXPANSION` `ae`"
                    + " ON `a`.`ID`=`ae`.`AFFILIATE_ID`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                    + " ON `apl`.`AFFILIATE_ID`=`a`.`ID`"
                    + " AND `apl`.`ACTIVE`='1'"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`PERSON` `m`"
                    + " ON `m`.`CANNUM`=`apl`.`MANAGER_CANNUM`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`DEPT` `d`"
                    + " ON `m`.`HOMEDEPT_ID`=`d`.`ID`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                    + " ON `aap`.`AFFILIATE_PURPOSE_ID`=`apl`.`AFFILIATE_PURPOSE_ID`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " ON `aap`.`AFFILIATE_ADMIN_ID`=`aa`.`ID`"
                    + " AND `aap`.`ACTIVE`='1'"
                    + " AND `aa`.`ACTIVE`='1'";

            String whereClause = "";
            if (!viewManCurrAffForm.getFirstName().isEmpty()) {
                if (viewManCurrAffForm.getFirstNameOpt().equalsIgnoreCase("begins")) {
                    whereClause += "`a`.`FIRSTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(viewManCurrAffForm.getFirstName()) + "%' AND ";
                } else if (viewManCurrAffForm.getFirstNameOpt().equalsIgnoreCase("contains")) {
                    whereClause += "`a`.`FIRSTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(viewManCurrAffForm.getFirstName()) + "%' AND ";
                } else if (viewManCurrAffForm.getFirstNameOpt().equalsIgnoreCase("exact")) {
                    whereClause += "`a`.`FIRSTNAME`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getFirstName()) + "' AND ";
                }
            }
            if (!viewManCurrAffForm.getLastName().isEmpty()) {
                if (viewManCurrAffForm.getLastNameOpt().equalsIgnoreCase("begins")) {
                    whereClause += "`a`.`LASTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(viewManCurrAffForm.getLastName()) + "%' AND ";
                } else if (viewManCurrAffForm.getLastNameOpt().equalsIgnoreCase("contains")) {
                    whereClause += "`a`.`LASTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(viewManCurrAffForm.getLastName()) + "%' AND ";
                } else if (viewManCurrAffForm.getLastNameOpt().equalsIgnoreCase("exact")) {
                    whereClause += "`a`.`LASTNAME`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getLastName()) + "' AND ";
                }
            }
            if (!viewManCurrAffForm.getOvprId().isEmpty()) {
                whereClause += "`a`.`OVPRID`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getOvprId()) + "' AND ";
            }
            if (!viewManCurrAffForm.getPseudoCan().isEmpty()) {
                whereClause += "`a`.`PSEUDOCAN`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getPseudoCan()) + "' AND ";
            }
            if (!viewManCurrAffForm.getEmail().isEmpty()) {
                if (viewManCurrAffForm.getEmailOpt().equalsIgnoreCase("begins")) {
                    whereClause += "`a`.`EMAIL` LIKE '" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getEmail()) + "%' AND ";
                } else if (viewManCurrAffForm.getEmailOpt().equalsIgnoreCase("contains")) {
                    whereClause += "`a`.`EMAIL` LIKE '%" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getEmail()) + "%' AND ";
                } else if (viewManCurrAffForm.getEmailOpt().equalsIgnoreCase("exact")) {
                    whereClause += "`a`.`EMAIL`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getEmail()) + "' AND ";
                }
            }

            if (!viewManCurrAffForm.getOrganizationCompany().isEmpty()) {
                if (viewManCurrAffForm.getOrganizationCompanyOpt().equalsIgnoreCase("begins")) {
                    whereClause += "`a`.`ORGANIZATION` LIKE '" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getOrganizationCompany()) + "%' AND ";
                } else if (viewManCurrAffForm.getOrganizationCompanyOpt().equalsIgnoreCase("contains")) {
                    whereClause += "`a`.`ORGANIZATION` LIKE '%" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getOrganizationCompany()) + "%' AND ";
                } else if (viewManCurrAffForm.getOrganizationCompanyOpt().equalsIgnoreCase("exact")) {
                    whereClause += "`a`.`ORGANIZATION`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getOrganizationCompany()) + "' AND ";
                }
            }

            if (!viewManCurrAffForm.getManFirstName().isEmpty()) {
                if (viewManCurrAffForm.getManFirstNameOpt().equalsIgnoreCase("begins")) {
                    whereClause += "`m`.`FIRSTNAME` LIKE '" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getManFirstName()) + "%' AND ";
                } else if (viewManCurrAffForm.getManFirstNameOpt().equalsIgnoreCase("contains")) {
                    whereClause += "`m`.`FIRSTNAME` LIKE '%" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getManFirstName()) + "%' AND ";
                } else if (viewManCurrAffForm.getManFirstNameOpt().equalsIgnoreCase("exact")) {
                    whereClause += "`m`.`FIRSTNAME`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getManFirstName()) + "' AND ";
                }
            }

            if (!viewManCurrAffForm.getManLastName().isEmpty()) {
                if (viewManCurrAffForm.getManLastNameOpt().equalsIgnoreCase("begins")) {
                    whereClause += "`m`.`LASTNAME` LIKE '" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getManLastName()) + "%' AND ";
                } else if (viewManCurrAffForm.getManLastNameOpt().equalsIgnoreCase("contains")) {
                    whereClause += "`m`.`LASTNAME` LIKE '%" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getManLastName()) + "%' AND ";
                } else if (viewManCurrAffForm.getManLastNameOpt().equalsIgnoreCase("exact")) {
                    whereClause += "`m`.`LASTNAME`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getManLastName()) + "' AND ";
                }
            }

            if (!viewManCurrAffForm.getManMyId().isEmpty()) {
                whereClause += "`m`.`MYID`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getManMyId()) + "' AND ";
            }

            if (!viewManCurrAffForm.getManUgaId().isEmpty()) {
                whereClause += "`m`.`CANNUM`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getManUgaId()) + "' AND ";
            }

            if (!viewManCurrAffForm.getManDeptName().isEmpty()) {
                if (viewManCurrAffForm.getManDeptNameOpt().equalsIgnoreCase("begins")) {
                    whereClause += "`d`.`DEPTNAME` LIKE '" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getManDeptName()) + "%' AND ";
                } else if (viewManCurrAffForm.getManDeptNameOpt().equalsIgnoreCase("contains")) {
                    whereClause += "`d`.`DEPTNAME` LIKE '%" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getManDeptName()) + "%' AND ";
                } else if (viewManCurrAffForm.getManDeptNameOpt().equalsIgnoreCase("exact")) {
                    whereClause += "`d`.`DEPTNAME`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getManDeptName()) + "' AND ";
                }
            }

            if (!viewManCurrAffForm.getManDeptNum().isEmpty()) {
                whereClause += "`d`.`DEPTNUM`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getManDeptNum()) + "' AND ";
            }

            if (viewManCurrAffForm.getShowInactive().equalsIgnoreCase("No")) {
                whereClause += "`a`.`ACTIVE`='1' AND ";
            }

            if (viewManCurrAffForm.getShowExpire().equals("Yes")) {
                whereClause += "`apl`.`EXPIRATION_DATE` <= DATE_ADD(NOW(), INTERVAL 30 DAY)"
                        + " AND `apl`.`EXPIRATION_DATE` >= NOW() AND ";
            }

            if (viewManCurrAffForm.getShowExpired().equals("No")) {
                whereClause += "(`apl`.`EXPIRATION_DATE` >= NOW()"
                        + " OR `apl`.`EXPIRES`='0'"
                        + " OR `apl`.`EXPIRATION_DATE` IS NULL) AND ";
            }

            if (viewManCurrAffForm.getPurposeManageFilter().equals("Yes")) {
                whereClause += "`aa`.`CANNUM`='" + userCannum + "' AND ";
            }

            if (!viewManCurrAffForm.getPurposeIds().isEmpty()) {
                String[] purposeIds = viewManCurrAffForm.getPurposeIds().replaceFirst("\\s+$", "").split("\\s+");
                whereClause += "(";
                for (String purposeId : purposeIds) {
                    whereClause += " `apl`.`AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(purposeId) + "' OR ";
                }
                whereClause = whereClause.replaceAll(" OR $", ") ");
            }

            whereClause += " `apl`.`AFFILIATE_PURPOSE_ID` != '" + Constants.associate_code + "' ";
            
            if (!whereClause.isEmpty()) {
                findAffQuery += " WHERE " + whereClause.replaceFirst("AND $", "");
            }

            findAffQuery += " ORDER BY"
                    + " `LASTNAME`"
                    + ", `FIRSTNAME`"
                    + ", `OVPRID`"
                    + ", `PSEUDOCAN`"
                    + ", `ORGANIZATION`";
            
            //System.out.println("Find Affiliate Query:\n" + findAffQuery + "\n");

            final ResultSet findAffRs = findAffStat.executeQuery(findAffQuery);
            boolean cont = true;
            while (findAffRs.next()) {
                HashMap<String, Object> data = new HashMap<String, Object>();

                if (findAffRs.getString("AFFILIATE_ID") == null) {
                    data.put("NEXT_EXP", "-");
                    data.put("ACTIVE_PURPOSES", "-");
                } else {
                    final String nextExpQuery = "SELECT"
                            + " CASE WHEN MIN(`apl`.`EXPIRES`) IS NULL"
                            + " THEN 'None active'"
                            + " WHEN MIN(`apl`.`EXPIRES`)='0'"
                            + " THEN 'Does not expire'"
                            + " ELSE MIN(DATE(`apl`.`EXPIRATION_DATE`)) END AS `NEXT_EXP`" +
                            ",COUNT(*) as `ACTIVE_PURPOSES`"
                            + " FROM"
                            + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                            + " WHERE"
                            + " `apl`.`AFFILIATE_ID`='" + findAffRs.getString("ID") + "'"
                            + " AND `apl`.`ACTIVE`='1'"
                            + " AND `apl`.`AFFILIATE_PURPOSE_ID` != '" + Constants.associate_code + "' "
                            + " AND `apl`.`APPROVED`='Approved'"
                            + " AND"
                            + " (`apl`.`EXPIRATION_DATE`>=NOW() OR `apl`.`EXPIRES`='0')";
                    final ResultSet nextExpRs = nextExpStat.executeQuery(nextExpQuery);
                    if (cont) {
                        cont = false;
                        
                        //System.out.println("Next Exp Query:\n" + nextExpQuery + "\n");
                    
                    }
                    if (nextExpRs.next()) {
                        data.put("NEXT_EXP", nextExpRs.getString("NEXT_EXP"));
                        if (nextExpRs.getString("NEXT_EXP").equalsIgnoreCase("None active")) {
                            data.put("ACTIVE_PURPOSES", "<b style='color: red'>0</b>");
                        } else {
                            data.put("ACTIVE_PURPOSES", nextExpRs.getString("ACTIVE_PURPOSES"));
                        }
                    } else {
                        data.put("NEXT_EXP", "None active");
                        data.put("ACTIVE_PURPOSES", "<b style='color: red;'>No</b>");
                    }
                }

                data.put("ID", findAffRs.getString("ID"));
                data.put("FIRSTNAME", findAffRs.getString("FIRSTNAME"));
                data.put("MIDDLENAME", findAffRs.getString("MIDDLENAME"));
                data.put("LASTNAME", findAffRs.getString("LASTNAME"));
                data.put("OVPRID", findAffRs.getString("OVPRID"));
                data.put("PSEUDOCAN", findAffRs.getString("PSEUDOCAN"));
                data.put("TITLE", findAffRs.getString("TITLE"));
                data.put("DEPARTMENT", findAffRs.getString("DEPARTMENT"));
                data.put("ORGANIZATION", findAffRs.getString("ORGANIZATION"));
                data.put("PHONE", findAffRs.getString("PHONE"));
                data.put("ACTIVE", findAffRs.getString("ACTIVE"));
                data.put("ADDR_1", findAffRs.getString("ADDR_1"));
                data.put("ADDR_2", findAffRs.getString("ADDR_2"));
                data.put("CITY", findAffRs.getString("CITY"));
                data.put("STATE", findAffRs.getString("STATE"));
                data.put("COUNTRY", findAffRs.getString("COUNTRY"));
                data.put("ZIP", findAffRs.getString("ZIP"));
                data.put("BUSINESS_PHONE_NUM", findAffRs.getString("BUSINESS_PHONE_NUM"));
                data.put("FAX_NUMBER", findAffRs.getString("FAX_NUMBER"));
                data.put("COMMENTS", findAffRs.getString("COMMENTS"));
                data.put("FSCODES", findAffRs.getString("FSCODES"));
                data.put("ZIP", findAffRs.getString("ZIP"));

                results.add(data);
            }

            return results;

        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static String UpdateAffPurpose(ViewManCurrAffForm viewManCurrAffForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement updateAffPurposeStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            String date = GeneralDAO.ParseDate(GeneralDAO.CleanMySQLString(viewManCurrAffForm.getTargExpDate()));

            final String updateAffPurposeCmd = "UPDATE"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                    + " SET"
                    + ", `EXPIRATION_DATE`='" + date + "'"
                    + " WHERE"
                    + " `AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getTargPurposeId()) + "'"
                    + " AND `AFFILIATE_ID`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getTargUserId()) + "'";
            //System.out.println("Update Aff Purpose Cmd:\n" + updateAffPurposeCmd + "\n");
            updateAffPurposeStat.executeUpdate(updateAffPurposeCmd);

            commitStat.execute("COMMIT");
            return "Successfully updated.";
        } catch (Exception ex) {
            commitStat.execute("ROLLBACK");
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void DefinePurposeGroups(ViewManCurrAffForm viewManCurrAffForm, String cannum, String role) throws Exception {
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getNonSelectedStat = conn.createStatement();

            ArrayList<Object> selectedPurps = new ArrayList<Object>();
            Collections.addAll(selectedPurps, viewManCurrAffForm.getPurposeIds().replaceFirst("\\s+$", "").split("\\s+"));

            String getNonSelectedWhereClause = "";
            for (Object purpObj : viewManCurrAffForm.getPurposes()) {
                getNonSelectedWhereClause += " AND `ap`.`ID`!='" + purpObj.toString() + "'";
            }

            String getNonSelecetedQuery = "SELECT DISTINCT"
                    + " `ap`.`ID`"
                    + ", `ap`.`SHORT_NAME`"
                    + ", `ap`.`DESCRIPTION`"
                    + ", `ap`.`PERIOD`"
                    + ", CASE WHEN `ap`.`CREATION_DATE` IS NULL THEN ''"
                    + "  ELSE `ap`.`CREATION_DATE` END AS `CREATION_DATE`"
                    + ", `p`.`FIRSTNAME`"
                    + ", `p`.`LASTNAME`"
                    + ", `p`.`MYID`"
                    + ", `p`.`EMAIL`"
                    + ", `p`.`ACTIVE`"
                    + ", `p`.`CANNUM`"
                    + ", DATE_FORMAT(DATE_ADD(NOW(), INTERVAL `ap`.`PERIOD` Month),'%Y-%m-%d') AS `DEFAULT_EXP_DATE`";
            if (role.equalsIgnoreCase("SuperUser")) {
                getNonSelecetedQuery += ", '1' AS 'PERMISSION'";
            } else {
                getNonSelecetedQuery += ", CASE WHEN `aa`.`CANNUM` IS NOT NULL AND `aap`.`ACTIVE`='1' AND `aa`.`ACTIVE`='1' THEN '1'"
                        + "  ELSE '0' END AS `PERMISSION`";
            }
            getNonSelecetedQuery += " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " INNER JOIN"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " ON `p`.`CANNUM`=`ap`.`CREATED_BY_CANNUM`"
                    
                    + " AND `ap`.`SHORT_NAME` != 'Associate'" //'associate' purpose not to be used
                   
                    + " AND `ap`.`ACTIVE`='1'";
            if (!role.equalsIgnoreCase("SuperUser")) {
                getNonSelecetedQuery += " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                        + " ON `ap`.`ID`=`aap`.`AFFILIATE_PURPOSE_ID`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                        + " ON `aa`.`ID`=`aap`.`AFFILIATE_ADMIN_ID`"
                        + " AND `aa`.`CANNUM`='" + cannum + "'";
            }
            getNonSelecetedQuery += " WHERE"
                    + " `ap`.`CREATION_DATE` <= NOW()"
                    + " AND `ap`.`ACTIVE`='1'"
                    + " AND `ap`.`APPROVED`='Approved'";
            if (!role.equalsIgnoreCase("SuperUser")) {
                getNonSelecetedQuery += " AND `aap`.`ACTIVE`='1'";
            }
            getNonSelecetedQuery += getNonSelectedWhereClause
                    + " ORDER BY"
                    + " `SHORT_NAME`, `CREATION_DATE`";

            final ResultSet getNonSelectedRs = getNonSelectedStat.executeQuery(getNonSelecetedQuery);
            viewManCurrAffForm.getPurposes().clear();
            while (getNonSelectedRs.next()) {
                HashMap<String, String> data = new HashMap<String, String>();
                if (selectedPurps.contains(getNonSelectedRs.getString("ID"))) {
                    data.put("SELECTED", "1");
                } else {
                    data.put("SELECTED", "0");
                }
                data.put("ID", getNonSelectedRs.getString("ID"));
                data.put("SHORT_NAME", getNonSelectedRs.getString("SHORT_NAME"));
                data.put("DESCRIPTION", getNonSelectedRs.getString("DESCRIPTION"));
                data.put("PERIOD", getNonSelectedRs.getString("PERIOD"));
                data.put("CREATION_DATE", getNonSelectedRs.getString("CREATION_DATE").replaceFirst(":\\d{2}(\\.\\d){0,1}$", ""));
                data.put("DEFAULT_EXP_DATE", getNonSelectedRs.getString("DEFAULT_EXP_DATE"));
                data.put("CANNUM", getNonSelectedRs.getString("CANNUM"));
                data.put("FIRSTNAME", getNonSelectedRs.getString("FIRSTNAME"));
                data.put("LASTNAME", getNonSelectedRs.getString("LASTNAME"));
                data.put("MYID", getNonSelectedRs.getString("MYID"));
                data.put("EMAIL", getNonSelectedRs.getString("EMAIL"));
                data.put("ACTIVE", getNonSelectedRs.getString("ACTIVE"));
                data.put("PERMISSION", getNonSelectedRs.getString("PERMISSION"));
                viewManCurrAffForm.getPurposes().add(data);
            }
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static String AddAffPurpose(ViewManCurrAffForm viewManCurrAffForm, String cannum) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        String response = "Successfully updated.";
        String approved = "0";
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement doesLinkExistStat = conn.createStatement();
            final Statement insertLinkStat = conn.createStatement();
            final Statement activateAffStat = conn.createStatement();
            final Statement laterDateStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            String date = GeneralDAO.ParseDate(GeneralDAO.CleanMySQLString(viewManCurrAffForm.getAddPurpExpDate()));

            final String laterDateQuery = "SELECT"
                    + " CASE WHEN NOW() > '" + date + "' THEN '0'"
                    + " ELSE '1' END AS `ALLOWED`";
            //System.out.println("Later date query:\n" + laterDateQuery + "\n");
            final ResultSet laterDateRs = laterDateStat.executeQuery(laterDateQuery);
            if (laterDateRs.next()) {
                if (laterDateRs.getString("ALLOWED").equalsIgnoreCase("0")) {
                    response = "ERROR: You must enter a date later than now.";
                } else {
                    final String doesLinkExistQuery = "SELECT"
                            + " `a`.`FIRSTNAME`"
                            + ", `a`.`LASTNAME`"
                            + ", `ap`.`ACTIVE` AS `PURPOSE_ACTIVE`"
                            + ", `ap`.`SHORT_NAME`"
                            + ", CASE WHEN MAX(`apl`.`EXPIRATION_DATE`) > NOW() THEN '0'"
                            + "  ELSE '1' END AS `ADD_ALLOWED`"
                            + ", `ap`.`APPROVED`"
                            + " FROM"
                            + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                            + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                            + " ON `a`.`ID`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getAddPurpUserId()) + "'"
                            + " AND `apl`.`AFFILIATE_ID`=`a`.`ID`"
                            + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                            + " ON `apl`.`AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getAddPurpPurposeId()) + "'"
                            + " AND `ap`.`ID`=`apl`.`AFFILIATE_PURPOSE_ID`"
                            
                            + " AND `ap`.`SHORT_NAME` != 'Associate'" //'associate' purpose not to be used
                   
                            + " AND `ap`.`ACTIVE`='1'";
                    
                    //System.out.println("Does Link Exist Query:\n" + doesLinkExistQuery + "\n");
                    final ResultSet doesLinkExistRs = doesLinkExistStat.executeQuery(doesLinkExistQuery);
                    if (doesLinkExistRs.next()) {
                        if (doesLinkExistRs.getString("PURPOSE_ACTIVE").equalsIgnoreCase("0")) {
                            response = "ERROR: The purpose '" + doesLinkExistRs.getString("SHORT_NAME") + "' is no longer active.";
                        } else if (doesLinkExistRs.getString("ADD_ALLOWED").equalsIgnoreCase("0")) {
                            response = "ERROR: A link already exists between "
                                    + "'" + doesLinkExistRs.getString("LASTNAME") + ", "
                                    + doesLinkExistRs.getString("FIRSTNAME") + "'"
                                    + " and purpose '" + doesLinkExistRs.getString("SHORT_NAME") + "'"
                                    + " with expiration date later than today.";
                        } else if (!doesLinkExistRs.getString("PURPOSE_ACTIVE").equalsIgnoreCase("Approved")) {
                            response = "ERROR: The purpose '" + doesLinkExistRs.getString("SHORT_NAME") + "' has not been approved.";
                        } else {
                            final String insertLinkCmd = "INSERT INTO"
                                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                                    + " SET"
                                    + " `ACTIVE`='1'"
                                    + ", `AFFILIATE_ID`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getAddPurpUserId()) + "'"
                                    + ", `AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getAddPurpPurposeId()) + "'"
                                    + ", `APPROVED`='Approved'"
                                    + ", `MANAGER_CANNUM`='" + cannum + "'"
                                    + ", `CREATION_DATE`=NOW()"
                                    + ", `EXPIRATION_DATE`='" + date + "'"
                                    + ", `MANAGE_DATE`=NOW()";
                            //System.out.println("Insert Link Cmd:\n" + insertLinkCmd + "\n");
                            insertLinkStat.executeUpdate(insertLinkCmd);

                            final String activateAffCmd = "UPDATE"
                                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE`"
                                    + " SET"
                                    + " `ACTIVE`='1'"
                                    + " WHERE"
                                    + " `ID`='" + GeneralDAO.CleanMySQLString(viewManCurrAffForm.getAddPurpUserId()) + "'";
                            //System.out.println("Activate aff cmd:\n" + activateAffCmd + "\n");
                            activateAffStat.executeUpdate(activateAffCmd);
                        }
                    } else {
                        response = "ERROR: Query did not generate responses. Contact responsible application programmer.";
                    }
                }
            }

            commitStat.execute("COMMIT");
            return response;
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
