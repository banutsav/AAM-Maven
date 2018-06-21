/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.email;

import edu.uga.ovpr.aam.Constants;
import edu.uga.ovpr.aam.Static;
import edu.uga.ovpr.aam.request.AddAffPurposeReqForm;
import edu.uga.ovpr.aam.request.NewAffReqForm;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author submyers
 */
public class AffiliateEmail {

    public static void EmailManagersNewAff(NewAffReqForm newAffReqForm) throws Exception {

        System.out.println("Email New Aff function called...");

        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getManagersStat = conn.createStatement();

            ArrayList<String> purposes = new ArrayList<String>();
            Collections.addAll(purposes, newAffReqForm.getPurpose_ids().replaceAll("\\s+$", "").split(" "));

            //System.out.println("Purpose count: " + purposes.size() + "\n");
            if (purposes.isEmpty()) {
                // Nothing to do
                return;
            }

            HashMap<String, Object> emailAddrs = new HashMap<String, Object>();
            ArrayList<Address> toRecipientsAddrs = new ArrayList();
            ArrayList<Address> ccRecipientsAddrs = new ArrayList();
            ArrayList<Address> bccRecipientsAddrs = new ArrayList();
            emailAddrs.put("toAddrs", toRecipientsAddrs);
            emailAddrs.put("ccAddrs", ccRecipientsAddrs);
            emailAddrs.put("bccAddrs", bccRecipientsAddrs);

            //bccRecipientsAddrs.add(new InternetAddress("submyers@uga.edu"));
            HashMap<String, HashMap<String, Object>> managerPurposes = new HashMap<String, HashMap<String, Object>>();

            for (String purposeId : purposes) {
                final String getManagersQuery = "SELECT"
                        + " `p`.`EMAIL`"
                        + ", `p`.`FIRSTNAME`"
                        + ", `p`.`LASTNAME`"
                        + ", `ap`.`SHORT_NAME`"
                        + " FROM"
                        + " `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                        + " ON `aa`.`CANNUM`=`p`.`CANNUM`"
                        + " AND `aa`.`ACTIVE`='1'"
                        + " AND `p`.`ACTIVE`='1'"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                        + " ON `aa`.`ID`=`aap`.`AFFILIATE_ADMIN_ID`"
                        + " AND `aap`.`ACTIVE`='1'"
                        + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                        + " ON `ap`.`ID`=`aap`.`AFFILIATE_PURPOSE_ID`"
                        + " AND `ap`.`ACTIVE`='1'"
                        + " AND `ap`.`APPROVED`='Approved'"
                        + " WHERE"
                        + " `aap`.`AFFILIATE_PURPOSE_ID`='" + purposeId + "'";
                //System.out.println("Get Managers Query:\n" + getManagersQuery + "\n");
                final ResultSet getManagersRs = getManagersStat.executeQuery(getManagersQuery);
                while (getManagersRs.next()) {
                    if (!managerPurposes.containsKey(getManagersRs.getString("EMAIL"))) {
                        managerPurposes.put(getManagersRs.getString("EMAIL"), new HashMap<String, Object>());
                    }
                    managerPurposes.get(getManagersRs.getString("EMAIL")).put("EMAIL", getManagersRs.getString("EMAIL"));
                    managerPurposes.get(getManagersRs.getString("EMAIL")).put("FIRSTNAME", getManagersRs.getString("FIRSTNAME"));
                    managerPurposes.get(getManagersRs.getString("EMAIL")).put("LASTNAME", getManagersRs.getString("LASTNAME"));
                    if (!managerPurposes.get(getManagersRs.getString("EMAIL")).containsKey("PURPOSES")) {
                        managerPurposes.get(getManagersRs.getString("EMAIL")).put("PURPOSES", new ArrayList<String>());
                    }
                    ((ArrayList<String>) managerPurposes.get(getManagersRs.getString("EMAIL")).get("PURPOSES")).add(getManagersRs.getString("SHORT_NAME"));
                }
            }

            for (HashMap<String, Object> entry : managerPurposes.values()) {
                toRecipientsAddrs.clear();
                toRecipientsAddrs.add(new InternetAddress(entry.get("EMAIL").toString()));

                String emailContent = "Hi " + entry.get("FIRSTNAME").toString() + " " + entry.get("LASTNAME").toString() + ",<br><br>"
                        + "A new affiliate request has been submitted for<br><br>"
                        + "&nbsp;&nbsp;&nbsp;" + newAffReqForm.getLastName() + ", " + newAffReqForm.getFirstName() + " (" + newAffReqForm.getEmail() + ")<br><br>"
                        + "for the following purpose(s)<br><br>";
                ArrayList<String> hold = (ArrayList<String>) entry.get("PURPOSES");
                for (String purposeName : hold) {
                    emailContent += "&nbsp;&nbsp;&nbsp;" + purposeName + "<br>";
                }
                emailContent += "<br>Visit <a href='https://paul.ovpr.uga.edu/AAM/'>https://paul.ovpr.uga.edu/AAM/</a> to manage request(s).";
                //System.out.println("Email content:\n" + emailContent + "\n");

                AffiliateEmail.SendEmail(emailContent, "New Affiliate Requests", emailAddrs);

            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    public static void EmailManagersNewAffPurpose(AddAffPurposeReqForm addAffPurposeReqForm) throws Exception {

        //System.out.println("Email function called...");
        Connection conn = null;
        try {
            conn = Static.getConnection("jdbc/paul");
            final Statement getManagersStat = conn.createStatement();

            final HashMap<String, Object> emailAddrs = new HashMap<String, Object>();
            final HashMap<String, Object> personInfo = addAffPurposeReqForm.getPersonInfo();
            final ArrayList<Address> toRecipientsAddrs = new ArrayList();
            final ArrayList<Address> ccRecipientsAddrs = new ArrayList();
            final ArrayList<Address> bccRecipientsAddrs = new ArrayList();
            emailAddrs.put("toAddrs", toRecipientsAddrs);
            emailAddrs.put("ccAddrs", ccRecipientsAddrs);
            emailAddrs.put("bccAddrs", bccRecipientsAddrs);

            //bccRecipientsAddrs.add(new InternetAddress("submyers@uga.edu"));
            HashMap<String, HashMap<String, Object>> managerPurposes = new HashMap<String, HashMap<String, Object>>();

            final String getManagersQuery = "SELECT"
                    + " `p`.`EMAIL`"
                    + ", `p`.`FIRSTNAME`"
                    + ", `p`.`LASTNAME`"
                    + ", `ap`.`SHORT_NAME`"
                    + " FROM"
                    + " `" + Constants.DB_PAUL + "`.`PERSON` `p`"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN` `aa`"
                    + " ON `aa`.`CANNUM`=`p`.`CANNUM`"
                    + " AND `aa`.`ACTIVE`='1'"
                    + " AND `p`.`ACTIVE`='1'"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_ADMIN_PURPOSE` `aap`"
                    + " ON `aa`.`ID`=`aap`.`AFFILIATE_ADMIN_ID`"
                    + " AND `aap`.`ACTIVE`='1'"
                    + " INNER JOIN `" + Constants.DB_PAUL + "`.`AFFILIATE_PURPOSE` `ap`"
                    + " ON `ap`.`ID`=`aap`.`AFFILIATE_PURPOSE_ID`"
                    + " AND `ap`.`ACTIVE`='1'"
                    + " AND `ap`.`APPROVED`='Approved'"
                    + " WHERE"
                    + " `aap`.`AFFILIATE_PURPOSE_ID`='" + addAffPurposeReqForm.getTargPurpId() + "'";
            //System.out.println("Get Managers Query:\n" + getManagersQuery + "\n");
            final ResultSet getManagersRs = getManagersStat.executeQuery(getManagersQuery);
            while (getManagersRs.next()) {
                if (!managerPurposes.containsKey(getManagersRs.getString("EMAIL"))) {
                    managerPurposes.put(getManagersRs.getString("EMAIL"), new HashMap<String, Object>());
                }
                managerPurposes.get(getManagersRs.getString("EMAIL")).put("EMAIL", getManagersRs.getString("EMAIL"));
                managerPurposes.get(getManagersRs.getString("EMAIL")).put("FIRSTNAME", getManagersRs.getString("FIRSTNAME"));
                managerPurposes.get(getManagersRs.getString("EMAIL")).put("LASTNAME", getManagersRs.getString("LASTNAME"));
                if (!managerPurposes.get(getManagersRs.getString("EMAIL")).containsKey("PURPOSES")) {
                    managerPurposes.get(getManagersRs.getString("EMAIL")).put("PURPOSES", new ArrayList<String>());
                }
                ((ArrayList<String>) managerPurposes.get(getManagersRs.getString("EMAIL")).get("PURPOSES")).add(getManagersRs.getString("SHORT_NAME"));
            }

            for (HashMap<String, Object> entry : managerPurposes.values()) {
                toRecipientsAddrs.clear();
                toRecipientsAddrs.add(new InternetAddress(entry.get("EMAIL").toString()));

                String emailContent = "Hi " + entry.get("FIRSTNAME").toString() + " " + entry.get("LASTNAME").toString() + ",<br><br>"
                        + "A request has been submitted for affiliate<br><br>"
                        + "&nbsp;&nbsp;&nbsp;" + personInfo.get("LASTNAME") + ", " + personInfo.get("FIRSTNAME") + " (" + personInfo.get("EMAIL") + ")<br><br>"
                        + "to add them to purpose<br><br>";
                ArrayList<String> hold = (ArrayList<String>) entry.get("PURPOSES");
                for (String purposeName : hold) {
                    emailContent += "&nbsp;&nbsp;&nbsp;" + purposeName + "<br>";
                }
                emailContent += "<br>Visit <a href='https://paul.ovpr.uga.edu/AAM/'>https://paul.ovpr.uga.edu/AAM/</a> to manage request(s).";
                //System.out.println("Email content:\n" + emailContent + "\n");
                AffiliateEmail.SendEmail(emailContent, "New Affiliate Requests", emailAddrs);
            }

        } catch (Exception ex) {
            throw ex;
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    private static Session getTlsEmailSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "post.uga.edu");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.from", "noreply@ovpr.uga.edu");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.sendpartial", "true");
        props.put("mail.smtp.starttls.enable", "true");
        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("z-ovprmail", "dz%4!&$%RqvZ");
            }
        });
    }

    public static void SendEmail(String emailContent, String emailSubject, HashMap<String, Object> emailAddrs) throws Exception {
        final Properties smtpSettings = new Properties();
        smtpSettings.setProperty("mail.smtp.host", "mail.ovpr.uga.edu");
        smtpSettings.setProperty("mail.smtp.from", "noreply@ovpr.uga.edu");
        try {
            //final Session session = Session.getInstance(smtpSettings, null);
            //final MimeMessage message = new MimeMessage(session);
            
            final MimeMessage message = new MimeMessage(getTlsEmailSession()); 

            final ArrayList<Address> toRecipientsAddrs = (ArrayList<Address>) emailAddrs.get("toAddrs");
            final ArrayList<Address> ccRecipientsAddrs = (ArrayList<Address>) emailAddrs.get("ccAddrs");
            final ArrayList<Address> bccRecipientsAddrs = (ArrayList<Address>) emailAddrs.get("bccAddrs");

            message.addRecipients(Message.RecipientType.TO, toRecipientsAddrs.toArray(new Address[0]));
            message.addRecipients(Message.RecipientType.CC, ccRecipientsAddrs.toArray(new Address[0]));
            message.addRecipients(Message.RecipientType.BCC, bccRecipientsAddrs.toArray(new Address[0]));

            message.setSubject(emailSubject);
            message.setContent(emailContent, "text/html");

            (new Thread(new EmailThread(message))).start();

        } catch (Exception ex) {
            throw ex;
        }
    }
    /**
     * public static void EmailNewAffiliate(String emailAddress, String
     * firstName, String lastName, String provider) throws Exception {
     *
     * /**
     * Look to see if variables have been defined for a "group" in the MySQL
     * database (the group defining this affiliate).
     *
     * Connection conn = null; // DEFAULT VALUES FOR THE PROVIDER String
     * provider_acronym = "OVPR"; String provider_name = "Office of the Vice
     * President for Research (OVPR)"; String from_email_addr =
     * "noreply@ovpr.uga.edu"; String provider_url = "ovpr.uga.edu"; try { conn
     * = Static.getConnection("jdbc/paul"); final Statement stat =
     * conn.createStatement(); final String query = "SELECT " + "
     * `sp`.`PROVIDER_ACRONYM`," + " `sp`.`PROVIDER_NAME`," + "
     * `sp`.`FROM_EMAIL_ADDR`," + " `sp`.`PROVIDER_URL`" + " FROM `" +
     * Static.AffilAcctDB + "`.`SERVICE_PROVIDER` `sp`" + " WHERE
     * `sp`.`PROVIDER_ACRONYM`='" + provider + "'";
     * //System.out.println("Provider Query: " + query); final ResultSet rs =
     * stat.executeQuery(query); if (rs.next()) { provider_acronym =
     * rs.getString("PROVIDER_ACRONYM"); provider_name =
     * rs.getString("PROVIDER_NAME"); from_email_addr =
     * rs.getString("FROM_EMAIL_ADDR"); provider_url =
     * rs.getString("PROVIDER_URL"); } } catch (Exception ignoreEx) {
     * //System.out.println("Exception in provider query!!"); } finally { if
     * (conn != null) { try { conn.close(); } catch (Exception ignoreEx) { } } }
     *
     * /**
     * Now define the content of the email and send it!
     *
     * final Properties smtpSettings = new Properties();
     * smtpSettings.setProperty("mail.smtp.host", "mail-gateway.uga.edu");
     * smtpSettings.setProperty("mail.smtp.from", from_email_addr); try { final
     * Session session = Session.getInstance(smtpSettings, null); final
     * MimeMessage message = new MimeMessage(session);
     *
     *
     * Timestamp currentTime = new Timestamp(new Date().getTime());
     *
     * final ArrayList<Address> toRecipientsAddrs = new ArrayList(); final
     * ArrayList<Address> bccRecipientsAddrs = new ArrayList();
     *
     * toRecipientsAddrs.add(new InternetAddress(emailAddress));
     *
     * bccRecipientsAddrs.add(new InternetAddress("submyers@uga.edu"));
     * bccRecipientsAddrs.add(new InternetAddress("donellam@uga.edu"));
     * //bccRecipientsAddrs.add(new InternetAddress("dgarland@uga.edu"));
     * //bccRecipientsAddrs.add(new InternetAddress("gwowens@uga.edu"));
     * //bccRecipientsAddrs.add(new InternetAddress("nesmithj@uga.edu"));
     * //bccRecipientsAddrs.add(new InternetAddress("jcoquinn@uga.edu"));
     * //bccRecipientsAddrs.add(new InternetAddress("rscott@uga.edu"));
     *
     * message.addRecipients(Message.RecipientType.TO,
     * toRecipientsAddrs.toArray(new Address[0]));
     * message.addRecipients(Message.RecipientType.BCC,
     * bccRecipientsAddrs.toArray(new Address[0]));
     *
     * String subject = "UGA: OVPR"; if (!provider_acronym.equals("OVPR")) {
     * subject += ", " + provider_acronym; } subject += " Password - " +
     * emailAddress; message.setSubject(subject);
     *
     * String emailContent = "<br/>Hello " + firstName + " " + lastName +
     * ",<br/><br/>You now have a new UGA-OVPR affiliate account"; if
     * (!provider_acronym.equals("OVPR")) { emailContent += " generated by the "
     * + provider_name; } emailContent += ".<br/><br/>" +
     * "&nbsp;&nbsp;&nbsp;User Email Address: " + emailAddress + "<br/><br/>You
     * will soon receive an email from the OVPR (noreply@ovpr.uga.edu) to define
     * a password for the account. Your account will have access to the GGF
     * Bioinformatics Lab Registration Form tomorrow after the 6:00 AM execution
     * of database management software.<br/><br/>" + "Thank you,<br/>&nbsp;OVPR
     * (<a href='ovpr.uga.edu'>ovpr.uga.edu</a>)<br/>"; if
     * (!provider_acronym.equals("OVPR")) { emailContent += "&nbsp;" +
     * provider_acronym + " (<a href='" + provider_url + "'>" + provider_url +
     * "</a>)<br/>"; } //System.out.println(emailContent);
     * //System.out.println("New Non-Provisional Affiliate Email:\nName: " +
     * firstName + " " + lastName + "\nEmail Addr: " + emailAddress + "Time: " +
     * currentTime.toString() + "Content:\n" + emailContent);
     *
     * message.setContent(emailContent, "text/html");
     *
     * (new Thread(new EmailThread(message))).start();
     *
     * } catch (Exception ex) { throw ex; }
    }*
     */
}
