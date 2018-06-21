<%-- 
    Document   : ManAffManager
    Created on : Apr 18, 2013, 11:50:35 AM
    Author     : submyers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String propId;%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/html/meta.html"%>
        <%@include file="/WEB-INF/html/title.html"%>
        <%@include file="/WEB-INF/html/css.html"%>

        <script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>
        <link type="text/css" media="all" rel="stylesheet" href="css/jquery-ui-1.10.2.custom.min.css">
        <link type="text/css" media="all" rel="stylesheet" href="css/jquery_datetimepicker.css">
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jsp"%>
        <%@include file="/WEB-INF/jspf/header_login.jsp"%>
        <html:form styleId="manCreateNewAff" action="ManCreateNewAff.do">
            <logic:iterate id="purpose"  name="manCreateNewAffForm" property="purposes" scope="request">
                <bean:define id="propId" name="purpose" property="ID" scope="page" toScope="page" type="String"/>
                <script>
                    purposeTotal++;
                    $(function(){
                        $("#dateTime_<%= propId%>").datetimepicker({controlType: 'slider'});
                    });
                </script>
            </logic:iterate>
            <html:hidden styleId="action" property="action"/>
            <html:hidden styleId="purpose_ids" property="purpose_ids"/>
            <html:hidden styleId="state" property="state"/>
            <html:hidden styleId="country" property="country"/>
            <html:hidden styleId="expiration_dates" property="expiration_dates"/>
            <div id="container">
            </div>
            <%@include file="/WEB-INF/jspf/footer.jsp"%>
            <div id="error_dialog" title="Unable to submit" style="display: none; font-size: 12px; text-align: center;">
                <div id="errorMsg"></div>
                <input type="button" onclick="$('#error_dialog').dialog('close'); return false;" value="Close">
            </div>
        </html:form>
    </body>
</html>
