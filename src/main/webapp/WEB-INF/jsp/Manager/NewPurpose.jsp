<%-- 
    Document   : NewPurpose
    Created on : Mar 6, 2013, 5:00:43 PM
    Author     : submyers
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String title, purposeId;%>
<%out.println("Inside NewPurpose.");%>
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
        <script type="text/javascript">
            $(document).ready(function(){
                $("#backLink").click(function(){
                    $("#action").val("backLink");
                    $("#newPurpose").submit();
                });
                
                $("#submitBtn").click(function(){
                    if($("#shortName").val() === ""){
                        alert("ERROR: Please define a short name.");
                        return false;
                    }
                    if($("#description").val() === ""){
                        alert("ERROR: Please define a description.");
                        return false;
                    }
                    if($("#period").val() === ""){
                        alert("ERROR: Please define the expiration period number of month(s).");
                        return false;
                    }
                    
                    var period = parseInt($("#period").val());
                    if(period < 1 || !$("#period").val().match("^\\d+$")){
                        alert("ERROR: The period must be an integer > 1.");
                        return false;
                    }
                    
                    $("#period").val(period);
                    
                    //alert("Values: " + $("#period").val() + "(" + period + "), " + $("#description").val() + ", " + $("#shortName").val());
                    
                    $("#action").val("submit");
                    $("#newPurpose").submit();
                });
            });
        </script>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jsp"%>
        <%@include file="/WEB-INF/jspf/header_login.jsp"%>
        <html:form styleId="newPurpose" action="NewPurpose.do">
            <html:hidden styleId="action" property="action"/>
            <bean:define id="title" name="newPurposeForm" property="title" scope="request" toScope="page" type="String"/>
            <bean:define id="purposeId" name="newPurposeForm" property="purposeId" scope="request" toScope="page" type="String"/>
            <html:hidden styleId="title" property="title" value="<%= title%>"/>
            <html:hidden styleId="purposeId" property="purposeId" value="<%= purposeId%>"/>
            <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3><bean:write name="newPurposeForm" property="title" scope="request"/></h3>
                    <hr/>
                    <logic:notEmpty name="errorMsg" scope="request">
                        <div>
                            <b style="color: red;"><u><bean:write name="errorMsg" scope="request"/></u></b>
                        </div>
                    </logic:notEmpty>
                    <div style="text-align: left;">
                        <div style="text-align: right;">
                            <a href="./Get.do?action=home">Main Menu</a>
                        </div>
                <b>Create New Purpose:</b><br>
                (All fields required)<br>
                <table>
                    <tr>
                        <td>Short Name:</td>
                        <td><input type="text" id="shortName" name="shortName" value="<bean:write name="newPurposeForm" property="shortName" scope="request"/>"/></td>
                    </tr>
                    <tr>
                        <td>Description:</td>
                        <td><textarea id="description" name="description" style="width: 400px; height: 100px;"><bean:write name="newPurposeForm" property="description" scope="request"/></textarea></td>
                    </tr>
                    <tr>
                        <td>Expiration Period:</td>
                        <td><input type="text" id="period" size="4" name="period" value="<bean:write name="newPurposeForm" property="period" scope="request"/>"/> Month(s)</td>
                    </tr>
                </table>
                <button id="submitBtn" onclick="return false">Submit</button>
            </div>
            </center>
        </html:form>
        <%@include file="/WEB-INF/jspf/footer.jsp"%>
    </body>
</html>
