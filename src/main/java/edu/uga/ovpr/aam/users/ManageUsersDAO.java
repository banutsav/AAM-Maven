/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.users;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.MySQLConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author submyers
 */
public class ManageUsersDAO {
    public static ArrayList<Object> GetCurrUsers() throws Exception {
        Connection conn = null;
        ArrayList<Object> results = new ArrayList<Object>();
        try {
            conn = MySQLConnection.getConnection(Constants.DS_MYSQL1);
            final Statement findUsersStat = conn.createStatement();
            final Statement findPurposeStat = conn.createStatement();
            
            final String findUsersQuery = "SELECT"
                    + " `p`.`FIRSTNAME`"
                    + ", `p`.`MIDDLENAME`"
                    + ", `p`.`LASTNAME`"
                    + ", `p`.`CANNUM`"
                    + ", `p`.`EMAIL`"
                    + ", `p`.`MYID`"
                    + ", `p`.`PHONE`"
                    + ", `p`.`TITLE`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " ON `aa`.`ACTIVE`='1' AND `aa`.`CANNUM`=`p`.`CANNUM` AND `p`.`ACTIVE`='1'";
            //System.out.println("Find Users Query:\n" + findUsersQuery + "\n");
            boolean print=true;
            final ResultSet findUsersRs = findUsersStat.executeQuery(findUsersQuery);
            while(findUsersRs.next()){
                HashMap<String,Object> entry = new HashMap<String,Object>();
                entry.put("FIRSTNAME", findUsersRs.getString("FIRSTNAME"));
                entry.put("MIDDLENAME", findUsersRs.getString("MIDDLENAME"));
                entry.put("LASTNAME", findUsersRs.getString("LASTNAME"));
                entry.put("CANNUM", findUsersRs.getString("CANNUM"));
                entry.put("MYID", findUsersRs.getString("MYID"));
                entry.put("PHONE", findUsersRs.getString("PHONE"));
                entry.put("TITLE", findUsersRs.getString("TITLE"));
                ArrayList<Object> purposes = new ArrayList<Object>();
                final String findPurposeQuery = "SELECT"
                        + " `ap`.`SHORT_NAME`"
                        + ", `ap`.`DESCRIPTION`"
                        + ", `ap`.`CREATION_DATE`"
                        //+ ", `ap`.`EXPIRATION_DATE`"
                        + ", `p`.`FIRSTNAME`"
                        + ", `p`.`LASTNAME`"
                        + ", `p`.`MIDDLENAME`"
                        + ", `p`.`MYID`"
                        + ", `p`.`CANNUM`"
                        + ", `p`.`ACTIVE`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                        + " ON `aap`.`AFFILIATE_PURPOSE_ID`=`ap`.`ID`"
                        + " AND `aap`.`ACTIVE`='1'"
                        + " AND `ap`.`ACTIVE`='1'"
                        + " AND `ap`.`APPROVED`='Approved'"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                        + " ON `aap`.`AFFILIATE_ADMIN_ID`=`aa`.`ID` AND `aa`.`ACTIVE`='1'"
                        + " AND `aa`.`CANNUM`='" + findUsersRs.getString("CANNUM") + "'"
                        + " LEFT JOIN `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                        + " ON `ap`.`ACTIVE`='1' AND `ap`.`CREATED_BY_CANNUM`=`p`.`CANNUM`";
                if(print){
                    //System.out.println("Find Purpose Query:\n" + findPurposeQuery + "\n");
                    print = false;
                }
                final ResultSet findPurposeRs = findPurposeStat.executeQuery(findPurposeQuery);
                while(findPurposeRs.next()){
                    HashMap<String,String> purposeData = new HashMap<String,String>();
                    purposeData.put("SHORT_NAME",findPurposeRs.getString("SHORT_NAME"));
                    purposeData.put("DESCRIPTION",findPurposeRs.getString("DESCRIPTION"));
                    purposeData.put("CREATION_DATE",findPurposeRs.getString("CREATION_DATE"));
                    //purposeData.put("EXPIRATION_DATE",findPurposeRs.getString("EXPIRATION_DATE"));
                    purposeData.put("FIRSTNAME",findPurposeRs.getString("FIRSTNAME"));
                    purposeData.put("LASTNAME",findPurposeRs.getString("LASTNAME"));
                    purposeData.put("MIDDLENAME",findPurposeRs.getString("MIDDLENAME"));
                    purposeData.put("MYID",findPurposeRs.getString("MYID"));
                    purposeData.put("CANNUM",findPurposeRs.getString("CANNUM"));
                    purposeData.put("ACTIVE",findPurposeRs.getString("ACTIVE"));
                    purposes.add(purposeData);
                }
                entry.put("PURPOSES", purposes);
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
}
