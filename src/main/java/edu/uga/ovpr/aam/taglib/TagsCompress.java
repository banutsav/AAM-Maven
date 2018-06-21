/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uga.ovpr.aam.taglib;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 *
 * @author submyers
 */
public class TagsCompress extends BodyTagSupport {

    @Override
    public int doStartTag() throws JspException {
        //try {
            //pageContext.getOut().print("doStartTag()");
            return EVAL_BODY_BUFFERED;
        //} catch (IOException ioe) {
        //    throw new JspException(ioe.getMessage());
        //}
    }

    @Override
    public void doInitBody() throws JspException {
        //try {
            // Note, that this is a different writer than the one
            // you have in doStartTag and doEndTag.
            //bodyContent.print("doInitBody()");
        //} catch (IOException ioe) {
        //    throw new JspException(ioe.getMessage());
        //}
    }

    @Override
    public int doAfterBody() throws JspException {
        //try {
            // Note, that this is a different writer than the one
            // you have in doStartTag and doEndTag.
            //bodyContent.print("doAfterBody()");
            // return IterationTag.EVAL_BODY_AGAIN;  
            // Use this to loop
            return SKIP_BODY;
        //} catch (IOException ioe) {
        //    throw new JspException(ioe.getMessage());
        //}
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            String bodyStr = bodyContent.getString();
            
            bodyStr = bodyStr.replaceAll("\\s+", " ");
            
            pageContext.getOut().print(bodyStr);
            // Write from bodyContent writer to original writer.
            //pageContext.getOut().print(bodyContent.getString());
            // Now we're back to the original writer.
            //pageContext.getOut().print("doEndTag()");
            return EVAL_PAGE;
        } catch (IOException ioe) {
            throw new JspException(ioe.getMessage());
        }
    }
}