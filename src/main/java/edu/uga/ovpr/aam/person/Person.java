package edu.uga.ovpr.aam.person;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.statics.OVPRMethods;
import java.util.List;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringEscapeUtils;

/**
 *
 * @author Glenn Owens
 */
public class Person implements java.io.Serializable {

    private String id = "";
    private String can = "";
    private String lastName = "Unknown";
    private String initial = "";
    private String middleName = "";
    private String firstName = "Name";
    private String title = "";
    private String myid = "";
    private String phone = "";
    private String email = "";
    private String deptNum = "";
    private String deptName = "";
    private String organization = "";

    public static Person getPersonBean(String canNum) throws Exception {
        if (canNum.isEmpty()) {
            return null;
        }
        final String affiliateQuery = "select `A`.`ID`,`A`.`firstName`" +
                ",`A`.`lastName`,`A`.`pseudoCan`,`A`.`email`,`A`.`phone`" +
                ",`A`.`ovprid`,`A`.`middleName`,`A`.`title`" +
                "from `paul`.`AFFILIATE` `A`" +
                "where `A`.`pseudoCan`='" + StringEscapeUtils.escapeSql(canNum) + "';";
        List<DynaBean> results = OVPRMethods._SQL_ExecuteQuery(affiliateQuery, Constants.DS_MYSQL1).getRows();
        Person personBean = new Person();
        if (!results.isEmpty()) {
            final DynaBean pb = results.get(0);
            personBean.setId((String) pb.get("ID"));
            personBean.setCan((String) pb.get("pseudoCan"));
            personBean.setFirstName((String) pb.get("firstName"));
            personBean.setInitial((String) pb.get("middleName"));
            personBean.setMiddleName((String) pb.get("middleName"));
            personBean.setLastName((String) pb.get("lastName"));
            personBean.setTitle((String) pb.get("title"));
            personBean.setMyid((String) pb.get("ovprid"));
            personBean.setEmail((String) pb.get("email"));
            personBean.setPhone((String) pb.get("phone"));
        } else {
            final String personQuery = "select `P`.`ID`,`P`.`firstName`" +
                    ",`P`.`middleName`,`P`.`lastName`,`P`.`canNum`,`P`.`email`,`P`.`phone`" +
                    ",`P`.`myid`,`P`.`initial`,`P`.`title`" +
                    "from `paul`.`PERSON` `P`" +
                    "where `P`.`cannum`='" + StringEscapeUtils.escapeSql(canNum) + "';";
            results = OVPRMethods._SQL_ExecuteQuery(personQuery, Constants.DS_MYSQL1).getRows();
            if (!results.isEmpty()) {
                final DynaBean pb = results.get(0);
                personBean.setId((String) pb.get("ID"));
                personBean.setCan((String) pb.get("canNum"));
                personBean.setFirstName((String) pb.get("firstName"));
                personBean.setInitial((String) pb.get("initial"));
                personBean.setMiddleName((String) pb.get("middleName"));
                personBean.setLastName((String) pb.get("lastName"));
                personBean.setTitle((String) pb.get("title"));
                personBean.setMyid((String) pb.get("myid"));
                personBean.setEmail((String) pb.get("email"));
                personBean.setPhone((String) pb.get("phone"));
            }
        }
        return personBean;
    }

    public static String makeFullName(String lastName, String firstName, String myid) {
        if (lastName == null) {
            lastName = "";
        }
        if (firstName == null) {
            firstName = "";
        }
        if (myid == null) {
            myid = "";
        }
        String fullName = lastName;
        if (!lastName.isEmpty() && !firstName.isEmpty()) {
            fullName += ", ";
        }
        fullName += firstName;
        if (!fullName.isEmpty() && !myid.isEmpty()) {
            fullName += " (";
        }
        fullName += myid;
        if (!fullName.isEmpty() && !myid.isEmpty()) {
            fullName += ")";
        }
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCan() {
        return can;
    }

    public void setCan(String can) {
        this.can = can;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }
    
    public String getMiddleName(){
      return this.middleName;
    }
    
    public void setMiddleName(String middleName){
      this.middleName = middleName;
    }

    public String getMyid() {
        return myid;
    }

    public void setMyid(String myid) {
        this.myid = myid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptNum() {
        return deptNum;
    }

    public void setDeptNum(String deptNum) {
        this.deptNum = deptNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getOrganization(){
      return this.organization;
    }
    
    public void setOrganization(String organization){
      this.organization = organization;
    }
}