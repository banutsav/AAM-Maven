/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.xml;

//import edu.uga.ovpr.aam.application.AffiliateApplicationForm;

/**
 *
 * @author submyers
 */
public class xmlUtil {
/**
    public static String GenAffiliateApplicationXML(AffiliateApplicationForm form) {
        String xmlForm = "<CreateAffiliateRequest>\n"
                + "\t<Email>" + form.getEmailAddr() + "</Email>\n"
                + "\t<FirstName>" + form.getFirstName() + "</FirstName>\n"
                + "\t<MiddleName>" + form.getMiddleName() + "</MiddleName>\n"
                + "\t<LastName>" + form.getLastName() + "</LastName>\n"
                + "\t<Title>" + form.getTitle() + "</Title>\n"
                + "\t<Organization>" + form.getOrganization() + "</Organization>\n"
                + "\t<Department>" + form.getDepartment() + "</Department>\n"
                + "\t<PreferredEmail>" + form.getEmailAddr() + "</PreferredEmail>\n"
                + "\t<Phone>" + form.getPhone() + "</Phone>\n"
                + "\t<CreatedBy>ORS Affiliate Lab Manager - Website</CreatedBy>\n"
                + "</CreateAffiliateRequest>";

        return xmlForm;
    }

    public static String SubmitAffiliateApplicationXML(String xmlForm, String emailAddr) throws Exception {

        final ResourceBundle rb = ResourceBundle.getBundle("ORSAffiliateAccountManagerResource");

        try {
            final URL url = new URL(rb.getString("paulaAdminCreateAffiliate"));
            final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setDoOutput(true);
            urlConn.setRequestProperty("Content-type", "text/xml");
            final String credentials = new String(Base64.encodeBase64(("finch:applepie").getBytes()));
            urlConn.setRequestProperty("Authorization", "Basic " + credentials);

            final BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConn.getOutputStream()));

            bw.write(xmlForm);
            bw.flush();
            bw.close();

            final BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));

            String line = null;
            String pseudoUGAID = null;
            boolean errorExists = false;
            while ((line = br.readLine()) != null) {
                if (line.matches("^.*<CreateAffiliateResponse.* isError=\"true\">.*$")) {
                    errorExists = true;
                }
                if (errorExists && line.matches("^.*<Error>.*$")) {
                    line = br.readLine();
                    line = line.replaceAll("^.*<Message>", "").replaceAll("</Message>.*$", "");
                    return line;
                }
                if (line.matches("^.*<IsPreExistingAffiliate>true</IsPreExistingAffiliate>.*$")) {
                    errorExists = true;
                    line = "Account for email address " + emailAddr + " already exists";
                    return line;
                }
                if (line.matches("^.*<PseudoUGAID>\\d{9}</PseudoUGAID>.*$")) {
                    pseudoUGAID = line.replaceAll("^.*<PseudoUGAID>", "").replaceAll("</PseudoUGAID>.*$", "");
                    return pseudoUGAID;
                }
            }

            br.close();
            return "Neither success nor failure. Contact Jonathan Myers (submyers@uga.edu)";
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static String SubmitAffiliateUpgrade(String pseudoUGAID, String myID) throws Exception {

        final ResourceBundle rb = ResourceBundle.getBundle("ORSAffiliateAccountManagerResource");

        try {
            final URL url = new URL(rb.getString("paulaAdminUpgradeAffiliate") + "?PseudoUGAID=" + pseudoUGAID);
            final HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            urlConn.setRequestMethod("GET");
            urlConn.setDoOutput(true);
            urlConn.setRequestProperty("Content-type", "text/xml");
            final String credentials = new String(Base64.encodeBase64(("finch:applepie").getBytes()));
            urlConn.setRequestProperty("Authorization", "Basic " + credentials);

            final BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line = null;
            boolean errorExists = false;
            while ((line = br.readLine()) != null) {
                if (line.matches("^.*<CreateAffiliateResponse.* isError=\"true\">.*$")) {
                    errorExists = true;
                }
                if (errorExists && line.matches("^.*<Error>.*$")) {
                    line = br.readLine();
                    line = line.replaceAll("^.*<Message>", "").replaceAll("</Message>.*$", "");
                    return line;
                }
                if (line.matches("^.*<AffiliateNotFound>true</AffiliateNotFound>.*$")) {
                    return "An account for " + myID + " does not exist.";
                }
                if (line.matches("^.*<WasAlreadyUpgraded>true</WasAlreadyUpgraded>.*$")) {
                    return "The account for " + myID + " already exists with Non-Provisional status.";
                }
            }

            br.close();
            if (errorExists && line == null) {
                return "Unrecognized error occurred. Contact Jonathan Myers (submyers@uga.edu)";
            }
            return null;
        } catch (Exception ex) {
            throw ex;
        }
    }**/
}
