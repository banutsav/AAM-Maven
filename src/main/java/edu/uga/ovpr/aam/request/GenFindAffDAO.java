/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.request;

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
public class GenFindAffDAO {

    public static ArrayList<Object> SearchPaula(GenFindAffForm genFindAffForm) throws Exception {
        ArrayList<Object> results = new ArrayList<Object>();
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement searchPaulaStat = conn.createStatement();
            Statement searchPaulaCountStat = conn.createStatement();

            String whereClause = "";
            if (!genFindAffForm.getLastName().isEmpty()) {
                if (genFindAffForm.getLastNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`LASTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getLastName()) + "%'";
                } else if (genFindAffForm.getLastNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`LASTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getLastName()) + "%'";
                } else if (genFindAffForm.getLastNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`LASTNAME`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getLastName()) + "'";
                }
            }
            if (!genFindAffForm.getFirstName().isEmpty()) {
                if (genFindAffForm.getFirstNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`FIRSTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getFirstName()) + "%'";
                } else if (genFindAffForm.getFirstNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`FIRSTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getFirstName()) + "%'";
                } else if (genFindAffForm.getFirstNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`FIRSTNAME`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getFirstName()) + "'";
                }
            }
            if (!genFindAffForm.getEmail().isEmpty()) {
                if (genFindAffForm.getEmailOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`EMAIL` LIKE '" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getEmail()) + "%'";
                } else if (genFindAffForm.getEmailOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`EMAIL` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getEmail()) + "%'";
                } else if (genFindAffForm.getEmailOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`EMAIL`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getEmail()) + "'";
                }
            }
            if (!genFindAffForm.getOvprId().isEmpty()) {
                if (genFindAffForm.getOvprIdOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`OVPRID` LIKE '" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getOvprId()) + "%'";
                } else if (genFindAffForm.getOvprIdOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`OVPRID` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getOvprId()) + "%'";
                } else if (genFindAffForm.getOvprIdOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`OVPRID`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getOvprId()) + "'";
                }
            }
            if (!genFindAffForm.getPseudoCan().isEmpty()) {
                whereClause += " AND `a`.`PSEUDOCAN`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getPseudoCan()) + "'";
            }
            if (!genFindAffForm.getJobTitle().isEmpty()) {
                if (genFindAffForm.getJobTitleOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`TITLE` LIKE '" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getJobTitle()) + "%'";
                } else if (genFindAffForm.getJobTitleOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`TITLE` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getJobTitle()) + "%'";
                } else if (genFindAffForm.getJobTitleOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`TITLE`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getJobTitle()) + "'";
                }
            }
            if (!genFindAffForm.getOrgName().isEmpty()) {
                if (genFindAffForm.getOrgNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`ORGANIZATION` LIKE '" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getOrgName()) + "%'";
                } else if (genFindAffForm.getOrgNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`ORGANIZATION` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getOrgName()) + "%'";
                } else if (genFindAffForm.getOrgNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`ORGANIZATION`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getOrgName()) + "'";
                }
            }
            if (!genFindAffForm.getDeptName().isEmpty()) {
                if (genFindAffForm.getDeptNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`DEPARTMENT` LIKE '" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getDeptName()) + "%'";
                } else if (genFindAffForm.getDeptNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`DEPARTMENT` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getDeptName()) + "%'";
                } else if (genFindAffForm.getDeptNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`DEPARTMENT`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getDeptName()) + "'";
                }
            }

            if (!whereClause.isEmpty()) {
                whereClause = whereClause.replaceFirst(" AND", " WHERE");
            }

            final String searchPaulaCountQuery = "SELECT"
                    + " COUNT(`a`.`ID`) AS `COUNT`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_EXPANSION` `ae`"
                    + " ON `ae`.`AFFILIATE_ID`=`a`.`ID`"
                    
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                    + " ON `apl`.`ID` = `a`.`ID`"
                    
                    + whereClause + " AND `apl`.`AFFILIATE_PURPOSE_ID` != '" + Constants.associate_code + "'";
            
            ResultSet searchPaulaCountRs = searchPaulaCountStat.executeQuery(searchPaulaCountQuery);
            if (searchPaulaCountRs.next()) {
                genFindAffForm.setCount(searchPaulaCountRs.getInt("COUNT"));
            } else {
                genFindAffForm.setCount(0);
                return results;
            }

            String orderBy = " ORDER BY `LASTNAME`,`FIRSTNAME`, `OVPRID`";

            Integer start = genFindAffForm.getPageNum() * 100;
            String limitClause = " LIMIT " + start + ", 100";

            final String searchPaulaQuery = "SELECT"
                    + " `a`.`ID`"
                    + ", `a`.`ACTIVE` AS `ACTIVE`"
                    + ", `a`.`FIRSTNAME` AS `FIRSTNAME`"
                    + ", `a`.`LASTNAME` AS `LASTNAME`"
                    + ", `a`.`OVPRID` AS `OVPRID`"
                    + ", `a`.`PSEUDOCAN` AS `PSEUDOCAN`"
                    + ", `a`.`TITLE` AS `TITLE`"
                    + ", `a`.`EMAIL` AS `EMAIL`"
                    + ", `a`.`PHONE` AS `PHONE`"
                    + ", `a`.`ORGANIZATION` AS `ORGANIZATION`"
                    + ", `a`.`DEPARTMENT` AS `DEPARTMENT`"
                    + ", `ae`.`FAX_NUMBER` AS `FAX_NUMBER`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_EXPANSION` `ae`"
                    + " ON `ae`.`AFFILIATE_ID`=`a`.`ID`"
                    
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                    + " ON `apl`.`AFFILIATE_ID` = `a`.`ID`"
                    
                    + whereClause + " AND `apl`.`AFFILIATE_PURPOSE_ID` != '" + Constants.associate_code + "' "
                    + orderBy
                    + limitClause;

            //System.out.println("Paula search:\n" + searchPaulaQuery + "\n");
            
            final ResultSet searchPaulaRs = searchPaulaStat.executeQuery(searchPaulaQuery);
            while (searchPaulaRs.next()) {
                HashMap<String, String> entry = new HashMap<String, String>();
                entry.put("ID", searchPaulaRs.getString("ID"));
                entry.put("ACTIVE", searchPaulaRs.getString("ACTIVE"));
                entry.put("FIRSTNAME", searchPaulaRs.getString("FIRSTNAME"));
                entry.put("LASTNAME", searchPaulaRs.getString("LASTNAME"));
                entry.put("OVPRID", searchPaulaRs.getString("OVPRID"));
                entry.put("PSEUDOCAN", searchPaulaRs.getString("PSEUDOCAN"));
                entry.put("TITLE", searchPaulaRs.getString("TITLE"));
                entry.put("EMAIL", searchPaulaRs.getString("EMAIL"));
                entry.put("PHONE", searchPaulaRs.getString("PHONE"));
                entry.put("FAX_NUMBER", searchPaulaRs.getString("FAX_NUMBER"));
                entry.put("ORGANIZATION", searchPaulaRs.getString("ORGANIZATION"));
                entry.put("DEPARTMENT", searchPaulaRs.getString("DEPARTMENT"));
                results.add(entry);
            }
            return results;
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
    
    public static void SearchPaulaOrg(GenFindAffForm genFindAffForm) throws Exception {
        ArrayList<Object> results = new ArrayList<Object>();
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            Statement searchPaulaStat = conn.createStatement();
            Statement searchPaulaCountStat = conn.createStatement();

            String whereClause = "";
            if (!genFindAffForm.getLastName().isEmpty()) {
                if (genFindAffForm.getLastNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`LASTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getLastName()) + "%'";
                } else if (genFindAffForm.getLastNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`LASTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getLastName()) + "%'";
                } else if (genFindAffForm.getLastNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`LASTNAME`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getLastName()) + "'";
                }
            }
            if (!genFindAffForm.getFirstName().isEmpty()) {
                if (genFindAffForm.getFirstNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`FIRSTNAME` LIKE '" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getFirstName()) + "%'";
                } else if (genFindAffForm.getFirstNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`FIRSTNAME` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getFirstName()) + "%'";
                } else if (genFindAffForm.getFirstNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`FIRSTNAME`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getFirstName()) + "'";
                }
            }
            if (!genFindAffForm.getEmail().isEmpty()) {
                if (genFindAffForm.getEmailOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`EMAIL` LIKE '" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getEmail()) + "%'";
                } else if (genFindAffForm.getEmailOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`EMAIL` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getEmail()) + "%'";
                } else if (genFindAffForm.getEmailOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`EMAIL`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getEmail()) + "'";
                }
            }
            if (!genFindAffForm.getOvprId().isEmpty()) {
                if (genFindAffForm.getOvprIdOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`OVPRID` LIKE '" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getOvprId()) + "%'";
                } else if (genFindAffForm.getOvprIdOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`OVPRID` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getOvprId()) + "%'";
                } else if (genFindAffForm.getOvprIdOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`OVPRID`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getOvprId()) + "'";
                }
            }
            if (!genFindAffForm.getPseudoCan().isEmpty()) {
                whereClause += " AND `a`.`PSEUDOCAN`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getPseudoCan()) + "'";
            }
            if (!genFindAffForm.getJobTitle().isEmpty()) {
                if (genFindAffForm.getJobTitleOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`TITLE` LIKE '" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getJobTitle()) + "%'";
                } else if (genFindAffForm.getJobTitleOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`TITLE` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getJobTitle()) + "%'";
                } else if (genFindAffForm.getJobTitleOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`TITLE`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getJobTitle()) + "'";
                }
            }
            if (!genFindAffForm.getOrgName().isEmpty()) {
                if (genFindAffForm.getOrgNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`ORGANIZATION` LIKE '" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getOrgName()) + "%'";
                } else if (genFindAffForm.getOrgNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`ORGANIZATION` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getOrgName()) + "%'";
                } else if (genFindAffForm.getOrgNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`ORGANIZATION`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getOrgName()) + "'";
                }
            }
            if (!genFindAffForm.getDeptName().isEmpty()) {
                if (genFindAffForm.getDeptNameOpt().equalsIgnoreCase("Begins")) {
                    whereClause += " AND `a`.`DEPARTMENT` LIKE '" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getDeptName()) + "%'";
                } else if (genFindAffForm.getDeptNameOpt().equalsIgnoreCase("Contains")) {
                    whereClause += " AND `a`.`DEPARTMENT` LIKE '%" + GeneralDAO.CleanMySQLStringForLike(genFindAffForm.getDeptName()) + "%'";
                } else if (genFindAffForm.getDeptNameOpt().equalsIgnoreCase("Exact")) {
                    whereClause += " AND `a`.`DEPARTMENT`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getDeptName()) + "'";
                }
            }

            if (!whereClause.isEmpty()) {
                whereClause = whereClause.replaceFirst(" AND", " WHERE");
            }

            final String searchPaulaCountQuery = "SELECT"
                    + " COUNT(`a`.`ID`) AS `COUNT`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_EXPANSION` `ae`"
                    + " ON `ae`.`AFFILIATE_ID`=`a`.`ID`"
                    + whereClause;
            ResultSet searchPaulaCountRs = searchPaulaCountStat.executeQuery(searchPaulaCountQuery);
            if (searchPaulaCountRs.next()) {
                genFindAffForm.setCount(searchPaulaCountRs.getInt("COUNT"));
            } else {
                genFindAffForm.setCount(0);
                return;
            }

            String orderBy = " ORDER BY `LASTNAME`,`FIRSTNAME`, `OVPRID`";

            Integer start = genFindAffForm.getPageNum() * 100;
            String limitClause = " LIMIT " + start + ", 100";

            final String searchPaulaQuery = "SELECT"
                    + " `a`.`ID`"
                    + ", `a`.`ACTIVE` AS `ACTIVE`"
                    + ", `a`.`FIRSTNAME` AS `FIRSTNAME`"
                    + ", `a`.`LASTNAME` AS `LASTNAME`"
                    + ", `a`.`OVPRID` AS `OVPRID`"
                    + ", `a`.`PSEUDOCAN` AS `PSEUDOCAN`"
                    + ", `a`.`TITLE` AS `TITLE`"
                    + ", `a`.`EMAIL` AS `EMAIL`"
                    + ", `a`.`PHONE` AS `PHONE`"
                    + ", `a`.`ORGANIZATION` AS `ORGANIZATION`"
                    + ", `a`.`DEPARTMENT` AS `DEPARTMENT`"
                    + ", `ae`.`FAX_NUMBER` AS `FAX_NUMBER`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_EXPANSION` `ae`"
                    + " ON `ae`.`AFFILIATE_ID`=`a`.`ID`"
                    + whereClause
                    + orderBy
                    + limitClause;

            //System.out.println("Paula search:\n" + searchPaulaQuery + "\n");
            final ResultSet searchPaulaRs = searchPaulaStat.executeQuery(searchPaulaQuery);
            while (searchPaulaRs.next()) {
                HashMap<String, String> entry = new HashMap<String, String>();
                entry.put("ID", searchPaulaRs.getString("ID"));
                entry.put("ACTIVE", searchPaulaRs.getString("ACTIVE"));
                entry.put("FIRSTNAME", searchPaulaRs.getString("FIRSTNAME"));
                entry.put("LASTNAME", searchPaulaRs.getString("LASTNAME"));
                entry.put("OVPRID", searchPaulaRs.getString("OVPRID"));
                entry.put("PSEUDOCAN", searchPaulaRs.getString("PSEUDOCAN"));
                entry.put("TITLE", searchPaulaRs.getString("TITLE"));
                entry.put("EMAIL", searchPaulaRs.getString("EMAIL"));
                entry.put("PHONE", searchPaulaRs.getString("PHONE"));
                entry.put("FAX_NUMBER", searchPaulaRs.getString("FAX_NUMBER"));
                entry.put("ORGANIZATION", searchPaulaRs.getString("ORGANIZATION"));
                entry.put("DEPARTMENT", searchPaulaRs.getString("DEPARTMENT"));
                results.add(entry);
            }
            genFindAffForm.setResults(results);
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

    public static void FindAff(GenFindAffForm genFindAffForm) throws Exception {
        Connection conn = null;
        final HashMap<String, Object> results = new HashMap<String, Object>();
        final HashMap<String, Object> linkedPurposes = new HashMap<String, Object>();

        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement genFindAffStat = conn.createStatement();
            final Statement getPurposesStat = conn.createStatement();
            final Statement getAllPurposesStat = conn.createStatement();
            final String genFindAffQuery = "SELECT DISTINCT"
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
                    + ", `a`.`ACTIVE` AS `ACTIVE`"
                    + ", `a`.`EMAIL` AS `EMAIL`"
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
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                    + " LEFT JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_EXPANSION` `ae`"
                    + " ON `a`.`ID`=`ae`.`AFFILIATE_ID`"
                    + " WHERE"
                    + " `a`.`OVPRID`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getEmail()) + "'"
                    + " OR `a`.`EMAIL`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getEmail()) + "'";
            //System.out.println("Find Affiliate Query:\n" + genFindAffQuery + "\n");
            final ResultSet genFindAffRs = genFindAffStat.executeQuery(genFindAffQuery);
            if (genFindAffRs.next()) {
                ArrayList<Object> purposes = new ArrayList<Object>();
                final String getPurposesQuery = "SELECT"
                        + " `ap`.`ID`"
                        + ", `ap`.`SHORT_NAME`"
                        + ", `ap`.`DESCRIPTION`"
                        + ", `ap`.`PERIOD`"
                        + ", `apl`.`APPROVED`"
                        + ", `apl`.`EXPIRES`"
                        + ", `apl`.`CREATION_DATE`"
                        + ", `apl`.`EXPIRATION_DATE`"
                        + ", `apl`.`MANAGE_DATE`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE` `a`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                        + " ON `a`.`ID`=`apl`.`AFFILIATE_ID`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                        + " ON `apl`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                        + " AND `ap`.`ACTIVE`='1'"
                        + " WHERE"
                        + " `a`.`PSEUDOCAN`='" + genFindAffRs.getString("PSEUDOCAN") + "'"
                        + " AND `ap`.`APPROVED`='Approved'"
                        + " ORDER BY"
                        + " FIND_IN_SET(`APPROVED`, 'Submitted,Approved,Rejected'), `SHORT_NAME`";
                //System.out.println("Get Purposes Query:\n" + getPurposesQuery + "\n");
                final ResultSet getPurposesRs = getPurposesStat.executeQuery(getPurposesQuery);
                while (getPurposesRs.next()) {
                    HashMap<String, Object> data = new HashMap<String, Object>();
                    data.put("ID", getPurposesRs.getString("ID"));
                    data.put("SHORT_NAME", getPurposesRs.getString("SHORT_NAME"));
                    data.put("DESCRIPTION", getPurposesRs.getString("DESCRIPTION"));
                    data.put("PERIOD", getPurposesRs.getString("PERIOD"));
                    data.put("APPROVED", getPurposesRs.getString("APPROVED"));
                    data.put("EXPIRES", getPurposesRs.getString("EXPIRES"));
                    data.put("CREATION_DATE", getPurposesRs.getString("CREATION_DATE"));
                    data.put("EXPIRATION_DATE", getPurposesRs.getString("EXPIRATION_DATE"));
                    data.put("MANAGE_DATE", getPurposesRs.getString("MANAGE_DATE"));
                    linkedPurposes.put(getPurposesRs.getString("ID"), getPurposesRs.getString("ID"));
                    purposes.add(data);
                }
                results.put("purposes", purposes);
 
                final String getAllPurposesQuery = "SELECT"
                        + " `ap`.`ID`"
                        + ", `ap`.`SHORT_NAME`"
                        + ", `ap`.`DESCRIPTION`"
                        + ", `ap`.`PERIOD`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                        + " WHERE"
                        + " `ap`.`APPROVED`='Approved'"
                        + " AND `ap`.`SHORT_NAME` != 'Associate'"
                        + " AND `ap`.`ACTIVE`='1'";
                
                //System.out.println("DEBUG: Get All Purposes Query:\n" + getAllPurposesQuery + "\n");
                
                final ResultSet getAllPurposesRs = getAllPurposesStat.executeQuery(getAllPurposesQuery);
                final ArrayList<Object> openPurposes = new ArrayList<Object>();
                while (getAllPurposesRs.next()) {
                    //System.out.println(getAllPurposesRs.getString("ID") + ": " + linkedPurposes.get(getAllPurposesRs.getString("ID")));
                    if (!linkedPurposes.containsKey(getAllPurposesRs.getString("ID"))) {
                        HashMap<String, Object> data = new HashMap<String, Object>();
                        data.put("ID", getAllPurposesRs.getString("ID"));
                        data.put("SHORT_NAME", getAllPurposesRs.getString("SHORT_NAME"));
                        data.put("DESCRIPTION", getAllPurposesRs.getString("DESCRIPTION"));
                        data.put("PERIOD", getAllPurposesRs.getString("PERIOD"));
                        openPurposes.add(data);
                    }
                }
                results.put("openPurposes", openPurposes);

                results.put("ID", genFindAffRs.getString("ID"));
                results.put("FIRSTNAME", genFindAffRs.getString("FIRSTNAME"));
                results.put("MIDDLENAME", genFindAffRs.getString("MIDDLENAME"));
                results.put("LASTNAME", genFindAffRs.getString("LASTNAME"));
                results.put("OVPRID", genFindAffRs.getString("OVPRID"));
                results.put("EMAIL", genFindAffRs.getString("EMAIL"));
                results.put("PSEUDOCAN", genFindAffRs.getString("PSEUDOCAN"));
                results.put("TITLE", genFindAffRs.getString("TITLE"));
                results.put("DEPARTMENT", genFindAffRs.getString("DEPARTMENT"));
                results.put("ORGANIZATION", genFindAffRs.getString("ORGANIZATION"));
                results.put("PHONE", genFindAffRs.getString("PHONE"));
                results.put("ACTIVE", genFindAffRs.getString("ACTIVE"));
                results.put("ADDR_1", genFindAffRs.getString("ADDR_1"));
                results.put("ADDR_2", genFindAffRs.getString("ADDR_2"));
                results.put("CITY", genFindAffRs.getString("CITY"));
                results.put("STATE", genFindAffRs.getString("STATE"));
                results.put("COUNTRY", genFindAffRs.getString("COUNTRY"));
                results.put("ZIP", genFindAffRs.getString("ZIP"));
                results.put("BUSINESS_PHONE_NUM", genFindAffRs.getString("BUSINESS_PHONE_NUM"));
                results.put("FAX_NUMBER", genFindAffRs.getString("FAX_NUMBER"));
                results.put("COMMENTS", genFindAffRs.getString("COMMENTS"));
                results.put("FSCODES", genFindAffRs.getString("FSCODES"));
                genFindAffForm.setTargAffId(genFindAffRs.getString("ID"));
            }
            //genFindAffForm.setResults(results);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void AddPurposeReq(GenFindAffForm genFindAffForm) throws Exception {
        Connection conn = null;
        Statement commitStat = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            commitStat = conn.createStatement();
            final Statement addPurposeStat = conn.createStatement();

            commitStat.execute("START TRANSACTION");

            final String addPurposeCmd = "INSERT INTO"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK`"
                    + " SET"
                    + " `AFFILIATE_ID`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getTargAffId()) + "'"
                    + ", `AFFILIATE_PURPOSE_ID`='" + GeneralDAO.CleanMySQLString(genFindAffForm.getTargPurpId()) + "'"
                    + ", `CREATION_DATE`=NOW()";
            addPurposeStat.executeUpdate(addPurposeCmd);

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
