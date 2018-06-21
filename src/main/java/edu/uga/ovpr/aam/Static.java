package edu.uga.ovpr.aam;

//import edu.uga.ovpr.rcc.billing.lab.request.form.RequestFormDAO;
import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 *
 * @author Glenn Owens
 */
public class Static {

    public static final String DB = "ors_lab";
    public static final String AffilAcctDB = "ORSAffiliateAccountManager";

    public static Connection getConnection(final String dataSource) throws Exception {
        try {
            Context ctx = new InitialContext();
            ctx = (Context) ctx.lookup("java:comp/env");
            final DataSource ds = (DataSource) ctx.lookup(dataSource);
            return ds.getConnection();
        } catch (Exception ex) {
            throw ex;
        }
    }

    /**
     * Convenience method for reconciling session-scope criteria with request-scope data.
     * Runs the validate method of an ActionForm.
     * If there are no validation errors, a copy of the ActionForm is placed in
     * session-scope and the ActionForm is returned.
     * If there is at least one validation error, the previously stored session-scope ActionForm is returned (may be <tt>null</tt>).
     * String criteriaKey is used as the key for the session-scope copy.
     */
    public static ActionForm validateCriteriaForm(final String criteriaKey, final ActionMapping mapping, final ActionForm form, final HttpServletRequest request) {
        final ActionErrors errors = form.validate(mapping, request);
        final HttpSession session = request.getSession();
        if (errors == null || errors.isEmpty()) {//no validation errors
            session.setAttribute(criteriaKey, form);
            return form;
        } else {//one or more validation errors
            request.setAttribute(Globals.ERROR_KEY, errors);
            return (ActionForm) session.getAttribute(criteriaKey);
        }
    }
}