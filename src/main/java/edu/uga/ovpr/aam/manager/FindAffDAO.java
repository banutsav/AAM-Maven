/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.manager;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Static;
import edu.uga.ovpr.aam.information.GeneralDAO;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 *
 * @author submyers
 */
public class FindAffDAO {

    public static void FindAff(FindAffForm findAffForm) throws Exception {
        Connection conn = null;
        final HashMap<String, Object> results = new HashMap<String, Object>();

        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement findAffStat = conn.createStatement();
            final String findAffQuery = "SELECT DISTINCT"
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
                    + " `a`.`OVPRID`='" + GeneralDAO.CleanMySQLString(findAffForm.getEmail()) + "'"
                    + " OR `a`.`EMAIL`='" + GeneralDAO.CleanMySQLString(findAffForm.getEmail()) + "'";
            //System.out.println("Find Affiliate Query:\n" + findAffQuery + "\n");
            final ResultSet findAffRs = findAffStat.executeQuery(findAffQuery);
            if (findAffRs.next()) {
                results.put("ID", findAffRs.getString("ID"));
                results.put("FIRSTNAME", findAffRs.getString("FIRSTNAME"));
                results.put("MIDDLENAME", findAffRs.getString("MIDDLENAME"));
                results.put("LASTNAME", findAffRs.getString("LASTNAME"));
                results.put("OVPRID", findAffRs.getString("OVPRID"));
                results.put("EMAIL", findAffRs.getString("EMAIL"));
                results.put("PSEUDOCAN", findAffRs.getString("PSEUDOCAN"));
                results.put("TITLE", findAffRs.getString("TITLE"));
                results.put("DEPARTMENT", findAffRs.getString("DEPARTMENT"));
                results.put("ORGANIZATION", findAffRs.getString("ORGANIZATION"));
                results.put("PHONE", findAffRs.getString("PHONE"));
                results.put("ACTIVE", findAffRs.getString("ACTIVE"));
                results.put("ADDR_1", findAffRs.getString("ADDR_1"));
                results.put("ADDR_2", findAffRs.getString("ADDR_2"));
                results.put("CITY", findAffRs.getString("CITY"));
                results.put("STATE", findAffRs.getString("STATE"));
                results.put("COUNTRY", findAffRs.getString("COUNTRY"));
                results.put("ZIP", findAffRs.getString("ZIP"));
                results.put("BUSINESS_PHONE_NUM", findAffRs.getString("BUSINESS_PHONE_NUM"));
                results.put("FAX_NUMBER", findAffRs.getString("FAX_NUMBER"));
                results.put("COMMENTS", findAffRs.getString("COMMENTS"));
                results.put("FSCODES", findAffRs.getString("FSCODES"));
                findAffForm.setTargAffId(findAffRs.getString("ID"));
            }
            findAffForm.setResults(results);
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
