<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<!-- messages -->
<logic:present name="ses" scope="session"><center>
        <logic:equal value="true" property="hasMessages" name="ses" scope="session">
            <div id="messages" class="clearfix" style="background: none repeat scroll 0 0 #DDDDDD">

                <bean:define id="notices" property="notices" name="ses" scope="session"/>
                <logic:notEmpty name="notices" scope="page">
                    <logic:iterate id="notice" name="notices" scope="page">
                        <span style="color:green"><bean:write name="notice" scope="page"/></span><br/>
                    </logic:iterate>
                </logic:notEmpty>

                <bean:define id="warnings" property="warnings" name="ses" scope="session"/>
                <logic:notEmpty name="warnings" scope="page">
                    <logic:iterate id="warning" name="warnings" scope="page">
                        <span style="color:#FF8800"><bean:write name="warning" scope="page"/></span><br/>
                    </logic:iterate>
                </logic:notEmpty>

                <bean:define id="errors" property="errors" name="ses" scope="session"/>
                <logic:notEmpty name="errors" scope="page">
                    <logic:iterate id="error" name="errors" scope="page">
                        <span style="color:red"><bean:write name="error" scope="page"/></span><br/>
                    </logic:iterate>
                </logic:notEmpty>

            </div>
            <div id="partitiontop"></div>
            <br/>
    </logic:equal></center>
</logic:present>