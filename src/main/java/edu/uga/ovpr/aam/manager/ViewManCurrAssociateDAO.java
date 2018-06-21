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
public class ViewManCurrAssociateDAO {

    public static ArrayList<Object> FindAssociateWithArrList(ViewManCurrAssociateForm viewManCurrAssociateForm, String userCannum, String role) throws Exception {
        Connection conn = null;
        ArrayList<Object> results = new ArrayList<Object>();
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement findAssociateStat = conn.createStatement();
            final Statement nextExpStat = conn.createStatement();

            String findAssociateQuery = "SELECT DISTINCT"
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
                    + ", `a`.`EMAIL`"
                    + ", `a`.`ACTIVE` AS `ACTIVE`"
                    
                    + ", `apl`.`EXPIRES`"
                    + ", `apl`.`EXPIRATION_DATE`"
                    
                    + ", `ae`.`ADDR_1` AS `ADDR_1`"
                    + ", `ae`.`ADDR_2` AS `ADDR_2`"
                    + ", `ae`.`CITY` AS `CITY`"
                    + ", `ae`.`STATE` AS `STATE`"
                    + ", `ae`.`COUNTRY` AS `COUNTRY`"
                    + ", `ae`.`ZIP` AS `ZIP`"
                    + ", `ae`.`BUSINESS_PHONE_NUM` AS `BUSINESS_PHONE_NUM`"
                    
                    + ", `ae`.`FAX_NUMBER` AS `FAX_NUMBER`"
                  
                    + ", `ae`.`COMMENTS` AS `COMMENTS`"
                    + ", `ae`.`FSCODES` AS `FSCODES`"
                    + ", `apl`.`AFFILIATE_ID` AS `AFFILIATE_ID`"
                    
                    + ", `ep`.`isEnrolled` AS `IS_ENROLLED`"
                    + ", `ep`.`ugaHRStatus` AS `UGA_HR_STATUS`"
                    
                    
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_EXPANSION` `ae`"
                    + " ON `a`.`ID`=`ae`.`AFFILIATE_ID`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                    + " ON `apl`.`AFFILIATE_ID`=`a`.`ID`"
                    
                    //+ " AND `apl`.`ACTIVE`='1'"
                    
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`PERSON` `m`"
                    + " ON `m`.`CANNUM`=`apl`.`MANAGER_CANNUM`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`DEPT` `d`"
                    + " ON `m`.`HOMEDEPT_ID`=`d`.`ID`"
                    
                    + " LEFT JOIN `authdata3`.`EITS_Persons` `ep`"
                    + " ON `a`.`OVPRID` = `ep`.`MYID`"
                    
                    ;
                    

            String whereClause = "";
            
            whereClause += "`apl`.`AFFILIATE_PURPOSE_ID` = '" + Constants.associate_code + "' AND ";
            
            if (!viewManCurrAssociateForm.getFirstName().isEmpty()) {
                if (viewManCurrAssociateForm.getFirstNameOpt().equalsIgnoreCase("begins")) {
                    whereClause += "`a`.`FIRSTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(viewManCurrAssociateForm.getFirstName()) + "%' AND ";
                } else if (viewManCurrAssociateForm.getFirstNameOpt().equalsIgnoreCase("contains")) {
                    whereClause += "`a`.`FIRSTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(viewManCurrAssociateForm.getFirstName()) + "%' AND ";
                } else if (viewManCurrAssociateForm.getFirstNameOpt().equalsIgnoreCase("exact")) {
                    whereClause += "`a`.`FIRSTNAME`='" + GeneralDAO.CleanMySQLString(viewManCurrAssociateForm.getFirstName()) + "' AND ";
                }
            }
            if (!viewManCurrAssociateForm.getLastName().isEmpty()) {
                if (viewManCurrAssociateForm.getLastNameOpt().equalsIgnoreCase("begins")) {
                    whereClause += "`a`.`LASTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(viewManCurrAssociateForm.getLastName()) + "%' AND ";
                } else if (viewManCurrAssociateForm.getLastNameOpt().equalsIgnoreCase("contains")) {
                    whereClause += "`a`.`LASTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(viewManCurrAssociateForm.getLastName()) + "%' AND ";
                } else if (viewManCurrAssociateForm.getLastNameOpt().equalsIgnoreCase("exact")) {
                    whereClause += "`a`.`LASTNAME`='" + GeneralDAO.CleanMySQLString(viewManCurrAssociateForm.getLastName()) + "' AND ";
                }
            }
            if (!viewManCurrAssociateForm.getOvprId().isEmpty()) {
                whereClause += "`a`.`OVPRID`='" + GeneralDAO.CleanMySQLString(viewManCurrAssociateForm.getOvprId()) + "' AND ";
            }
            if (!viewManCurrAssociateForm.getPseudoCan().isEmpty()) {
                whereClause += "`a`.`PSEUDOCAN`='" + GeneralDAO.CleanMySQLString(viewManCurrAssociateForm.getPseudoCan()) + "' AND ";
            }
            if (!viewManCurrAssociateForm.getEmail().isEmpty()) {
                if (viewManCurrAssociateForm.getEmailOpt().equalsIgnoreCase("begins")) {
                    whereClause += "`a`.`EMAIL` LIKE '" + GeneralDAO.CleanMySQLString(viewManCurrAssociateForm.getEmail()) + "%' AND ";
                } else if (viewManCurrAssociateForm.getEmailOpt().equalsIgnoreCase("contains")) {
                    whereClause += "`a`.`EMAIL` LIKE '%" + GeneralDAO.CleanMySQLString(viewManCurrAssociateForm.getEmail()) + "%' AND ";
                } else if (viewManCurrAssociateForm.getEmailOpt().equalsIgnoreCase("exact")) {
                    whereClause += "`a`.`EMAIL`='" + GeneralDAO.CleanMySQLString(viewManCurrAssociateForm.getEmail()) + "' AND ";
                }
            }

            if (!viewManCurrAssociateForm.getOrganizationCompany().isEmpty()) {
                if (viewManCurrAssociateForm.getOrganizationCompanyOpt().equalsIgnoreCase("begins")) {
                    whereClause += "`a`.`ORGANIZATION` LIKE '" + GeneralDAO.CleanMySQLString(viewManCurrAssociateForm.getOrganizationCompany()) + "%' AND ";
                } else if (viewManCurrAssociateForm.getOrganizationCompanyOpt().equalsIgnoreCase("contains")) {
                    whereClause += "`a`.`ORGANIZATION` LIKE '%" + GeneralDAO.CleanMySQLString(viewManCurrAssociateForm.getOrganizationCompany()) + "%' AND ";
                } else if (viewManCurrAssociateForm.getOrganizationCompanyOpt().equalsIgnoreCase("exact")) {
                    whereClause += "`a`.`ORGANIZATION`='" + GeneralDAO.CleanMySQLString(viewManCurrAssociateForm.getOrganizationCompany()) + "' AND ";
                }
            }

            
            if (viewManCurrAssociateForm.getShowInactive().equalsIgnoreCase("No")) {
                whereClause += "`a`.`ACTIVE`='1' AND `apl`.`ACTIVE` = '1' AND ";
            }

            
            if (!whereClause.isEmpty()) {
                findAssociateQuery += " WHERE " + whereClause.replaceFirst("AND $", "");
            }

            findAssociateQuery += " ORDER BY"
                    + " `LASTNAME`"
                    + ", `FIRSTNAME`"
                    + ", `OVPRID`"
                    + ", `PSEUDOCAN`"
                    + ", `ORGANIZATION`";
            
            //System.out.println("DEBUG: Find Associate Query:\n" + findAssociateQuery + "\n");

            final ResultSet findAssociateRs = findAssociateStat.executeQuery(findAssociateQuery);
            boolean cont = true;
               
            while (findAssociateRs.next()) {
            
                HashMap<String, Object> data = new HashMap<String, Object>();

                if (findAssociateRs.getString("AFFILIATE_ID") == null) {
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
                            + " `apl`.`AFFILIATE_ID`='" + findAssociateRs.getString("ID") + "'"
                            + " AND `apl`.`ACTIVE`='1'"
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

                data.put("ID", findAssociateRs.getString("ID"));
                data.put("FIRSTNAME", findAssociateRs.getString("FIRSTNAME"));
                data.put("MIDDLENAME", findAssociateRs.getString("MIDDLENAME"));
                data.put("LASTNAME", findAssociateRs.getString("LASTNAME"));
                data.put("OVPRID", findAssociateRs.getString("OVPRID"));
                data.put("PSEUDOCAN", findAssociateRs.getString("PSEUDOCAN"));
                data.put("TITLE", findAssociateRs.getString("TITLE"));
                data.put("DEPARTMENT", findAssociateRs.getString("DEPARTMENT"));
                data.put("ORGANIZATION", findAssociateRs.getString("ORGANIZATION"));
                data.put("PHONE", findAssociateRs.getString("PHONE"));
                data.put("ACTIVE", findAssociateRs.getString("ACTIVE"));
                data.put("ADDR_1", findAssociateRs.getString("ADDR_1"));
                data.put("ADDR_2", findAssociateRs.getString("ADDR_2"));
                data.put("CITY", findAssociateRs.getString("CITY"));
                data.put("STATE", findAssociateRs.getString("STATE"));
                data.put("COUNTRY", findAssociateRs.getString("COUNTRY"));
                data.put("ZIP", findAssociateRs.getString("ZIP"));
                data.put("BUSINESS_PHONE_NUM", findAssociateRs.getString("BUSINESS_PHONE_NUM"));
                data.put("FAX_NUMBER", findAssociateRs.getString("FAX_NUMBER"));
                data.put("COMMENTS", findAssociateRs.getString("COMMENTS"));
                data.put("FSCODES", findAssociateRs.getString("FSCODES"));
                data.put("ZIP", findAssociateRs.getString("ZIP"));
                data.put("EMAIL", findAssociateRs.getString("EMAIL"));
                
                data.put("IS_ENROLLED", findAssociateRs.getString("IS_ENROLLED"));
                data.put("UGA_HR_STATUS", findAssociateRs.getString("UGA_HR_STATUS"));
                
                if(findAssociateRs.getString("EXPIRES").equals("0"))
                    data.put("NEXT_EXP", "never expires");
                else    
                    data.put("NEXT_EXP", findAssociateRs.getString("EXPIRATION_DATE").substring(0,10));
                

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
