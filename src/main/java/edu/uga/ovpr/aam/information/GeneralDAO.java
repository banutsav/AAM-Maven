/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.information;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Static;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author submyers
 */
public class GeneralDAO {

    public static ArrayList<Object> GetPurposeArr(String userCannum, String role) throws Exception {
        ArrayList<Object> result = new ArrayList<Object>();
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getPurposesStat = conn.createStatement();
            String getPurposesQuery = "SELECT DISTINCT"
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
                getPurposesQuery += ", '1' AS 'PERMISSION'";
            } else {
                getPurposesQuery += ", CASE WHEN `aa`.`CANNUM` IS NOT NULL AND `aap`.`ACTIVE`='1' AND `aa`.`ACTIVE`='1' THEN '1'"
                        + "  ELSE '0' END AS `PERMISSION`";
            }
            getPurposesQuery += " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " INNER JOIN"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " ON `p`.`CANNUM`=`ap`.`CREATED_BY_CANNUM`";
            if (!role.equalsIgnoreCase("SuperUser")) {
                getPurposesQuery += " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                        + " ON `ap`.`ID`=`aap`.`AFFILIATE_PURPOSE_ID`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                        + " ON `aa`.`ID`=`aap`.`AFFILIATE_ADMIN_ID`"
                        + " AND `aa`.`CANNUM`='" + userCannum + "'";
            }
            getPurposesQuery += " WHERE"
                    + " `ap`.`CREATION_DATE` <= NOW()"
                    + " AND `ap`.`ACTIVE`='1'"
                    + " AND `ap`.`SHORT_NAME` != 'Associate'"
                    + " AND `ap`.`APPROVED`='Approved'";
            if (!role.equalsIgnoreCase("SuperUser")) {
                getPurposesQuery += " AND `aap`.`ACTIVE`='1'";
            }
            getPurposesQuery += " ORDER BY"
                    + " `SHORT_NAME`, `CREATION_DATE`";

            //System.out.println("Get Purpose Query:\n" + getPurposesQuery + "\n");
            
            final ResultSet getPurposesRs = getPurposesStat.executeQuery(getPurposesQuery);
            while (getPurposesRs.next()) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("ID", getPurposesRs.getString("ID"));
                data.put("SHORT_NAME", getPurposesRs.getString("SHORT_NAME"));
                data.put("DESCRIPTION", getPurposesRs.getString("DESCRIPTION"));
                data.put("PERIOD", getPurposesRs.getString("PERIOD"));
                data.put("CREATION_DATE", getPurposesRs.getString("CREATION_DATE").replaceFirst(":\\d{2}(\\.\\d){0,1}$", ""));
                data.put("DEFAULT_EXP_DATE", getPurposesRs.getString("DEFAULT_EXP_DATE"));
                data.put("CANNUM", getPurposesRs.getString("CANNUM"));
                data.put("FIRSTNAME", getPurposesRs.getString("FIRSTNAME"));
                data.put("LASTNAME", getPurposesRs.getString("LASTNAME"));
                data.put("MYID", getPurposesRs.getString("MYID"));
                data.put("EMAIL", getPurposesRs.getString("EMAIL"));
                data.put("ACTIVE", getPurposesRs.getString("ACTIVE"));
                data.put("PERMISSION", getPurposesRs.getString("PERMISSION"));
                result.add(data);
            }

            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static ArrayList<Object> GetAllPurposeArr() throws Exception {
        ArrayList<Object> result = new ArrayList<Object>();
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getPurposesStat = conn.createStatement();
            String getPurposesQuery = "SELECT"
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
                    + ", DATE_FORMAT(DATE_ADD(NOW(), INTERVAL `ap`.`PERIOD` Month),'%Y-%m-%d') AS `DEFAULT_EXP_DATE`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " INNER JOIN"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " ON `p`.`CANNUM`=`ap`.`CREATED_BY_CANNUM`"
//                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
//                    + " ON `aap`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
//                    + " AND `aap`.`ACTIVE`='1'"
                    + " WHERE"
                    + " `ap`.`CREATION_DATE` <= NOW()"
                    + " AND `ap`.`ACTIVE`='1'"
                    + " AND `ap`.`SHORT_NAME` != 'Associate'"
                    + " AND `ap`.`APPROVED`='Approved'"
                    + " ORDER BY"
                    + " `SHORT_NAME`, `CREATION_DATE`";

            //System.out.println("GeneralDAO: Get Purpose Query:\n" + getPurposesQuery + "\n");
            
            final ResultSet getPurposesRs = getPurposesStat.executeQuery(getPurposesQuery);
            while (getPurposesRs.next()) {
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("ID", getPurposesRs.getString("ID"));
                data.put("SHORT_NAME", getPurposesRs.getString("SHORT_NAME"));
                data.put("DESCRIPTION", getPurposesRs.getString("DESCRIPTION"));
                data.put("PERIOD", getPurposesRs.getString("PERIOD"));
                data.put("DEFAULT_EXP_DATE", getPurposesRs.getString("DEFAULT_EXP_DATE"));
                data.put("CREATION_DATE", getPurposesRs.getString("CREATION_DATE").replaceFirst(":\\d{2}(\\.\\d){0,1}$", ""));
                data.put("CANNUM", getPurposesRs.getString("CANNUM"));
                data.put("FIRSTNAME", getPurposesRs.getString("FIRSTNAME"));
                data.put("LASTNAME", getPurposesRs.getString("LASTNAME"));
                data.put("MYID", getPurposesRs.getString("MYID"));
                data.put("EMAIL", getPurposesRs.getString("EMAIL"));
                data.put("ACTIVE", getPurposesRs.getString("ACTIVE"));
                result.add(data);
            }

            return result;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static String CleanMySQLString(String inputString) {
        if (inputString != null) {
            inputString = inputString.replaceAll("\\\\", "\\\\\\\\").replaceAll("'", "\\\\'");
            return inputString;
        } else {
            return inputString;
        }
    }
    
    public static String CleanMySQLStringForLike(String inputString) {
        if (inputString != null) {
            inputString = inputString.replaceAll("\\\\", "\\\\\\\\\\\\\\\\").replaceAll("'", "\\\\'");
            return inputString;
        } else {
            return inputString;
        }
    }

    public static String ParseDate(String dateString) {
        String[] dateVals = dateString.split("\\s+");
        if (dateVals[1].matches("\\s+.*$")) {
            dateVals[1] = dateVals[1].replaceAll("\\s+.*$", "");
        }

        String date;
        if (!dateVals[0].matches("^\\d{4}-\\d{2}-\\d{2}")) {
            //System.out.println("Date Vals: " + dateVals[0]);
            String[] calVals = dateVals[0].split("[/:-]");
            date = calVals[2] + "-" + calVals[0] + "-" + calVals[1] + " " + dateVals[1];
        } else {
            date = dateVals[0] + " " + dateVals[1];
        }
        return date;
    }

    public static String GetCurrAffEmails() throws Exception {
        Connection conn = null;
        String emails = "";
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getCurrAffEmailsStat = conn.createStatement();

            final String getCurrAffEmailsQuery = "SELECT"
                    + " `OVPRID` AS `OVPRID`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE`"
                    + " UNION"
                    + " SELECT"
                    + " `EMAIL` AS `OVPRID`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE`";
            //System.out.println("Get current email query:\n" + getCurrAffEmailsQuery + "\n");
            ResultSet getCurrAffEmailsRs = getCurrAffEmailsStat.executeQuery(getCurrAffEmailsQuery);
            while (getCurrAffEmailsRs.next()) {
                emails += "\"" + getCurrAffEmailsRs.getString("OVPRID") + "\",";
            }
            emails = emails.replaceAll(",$", "");
            return emails;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public static boolean WillManagedAffsExpireSoon(String cannum) throws Exception {
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement willAffExpSoonStat = conn.createStatement();

            final String willAffExpSoonQuery = "SELECT"
                    + " 1"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE_LINK` `apl`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                    + " ON `aap`.`AFFILIATE_PURPOSE_ID`=`apl`.`AFFILIATE_PURPOSE_ID`"
                    + " AND `aap`.`ACTIVE`='1'"
                    + " AND `apl`.`ACTIVE`='1'"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " ON `aa`.`ID`=`aap`.`AFFILIATE_ADMIN_ID`"
                    + " AND `aa`.`ACTIVE`='1'"
                    + " AND `aa`.`CANNUM`='" + cannum + "'"
                    + " WHERE"
                    + " `apl`.`EXPIRATION_DATE` >= NOW()"
                    + " AND `apl`.`EXPIRATION_DATE` <= DATE_ADD(NOW(),INTERVAL 30 DAY)";
            //System.out.println("Will Aff Exp Soon Query\n" + willAffExpSoonQuery + "\n");
            ResultSet willAffExpSoonRs = willAffExpSoonStat.executeQuery(willAffExpSoonQuery);
            if(willAffExpSoonRs.next()){
                return true;
            }
            return false;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
    
    public static boolean DoActiveManagedPurposesExist() throws Exception {
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement doPurposesExistStat = conn.createStatement();
            final String doPurposesExistQuery = "SELECT"
                    + " '1'"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                    + " ON `ap`.`ACTIVE`='1'"
                    + " AND `aap`.`ACTIVE`='1'"
                    + " AND `aap`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " ON `aa`.`ACTIVE`='1'"
                    + " AND `aap`.`AFFILIATE_ADMIN_ID`=`aa`.`ID`";
            final ResultSet doPurposesExistRs = doPurposesExistStat.executeQuery(doPurposesExistQuery);
            if(doPurposesExistRs.next()){
                return true;
            }
            return false;
        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }
}
