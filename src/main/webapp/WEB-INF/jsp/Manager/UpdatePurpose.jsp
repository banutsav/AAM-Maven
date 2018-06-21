<%-- 
    Document   : UpdatePurpose
    Created on : Apr 23, 2013, 3:16:06 PM
    Author     : submyers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String purposeId;%>
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
            var role = <%= session.getAttribute("role") %>;
            var lastPeriod = "1";
            
            $(document).ready(function(){
                
                $("#confirm_dialog").dialog({
                    autoOpen: false,
                    modal: true,
                    resizable: false
                });
                
                $(".expiresCheck").click(function(){
                    if($(".expiresCheck").is(":checked")){
                        lastPeriod = $(".periodText").val();
                        $(".periodText").val("");
                        $(".periodText").attr("readonly","readonly");
                    } else {
                        $(".periodText").val(lastPeriod);
                        $(".periodText").removeAttr("readonly");
                    }
                });
                
                $("#backLink").click(function(){
                    $("#action").val("backLink");
                    $("#updatePurpose").submit();
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
                    if($("period").val() === ""){
                        alert("ERROR: Please define the expiration period number of month(s).");
                        return false;
                    }
                    
                    var period = parseInt($(".periodText").val());
                    if($(".expiresCheck").is(":checked")){
                        period = -1;
                    } else if($(".periodText").val() === ""){
                        alert("ERROR: Please define the expiration period number of month(s).");
                        return false;
                    } else if(period < 1){
                        alert("ERROR: The period must be an integer >= 1.");
                        return false;
                    }
                    
                    $("#period").val(period);
                    
                    if(role !== "SuperUser"){
                        $("#confirm_dialog").dialog("open");
                        return false;
                    }
                                       
                    $("#action").val("submit");
                    $("#updatePurpose").submit();
                });
                
                $("#submit_confirm_dialog").click(function(){
                    $("#action").val("submit");
                    $("#updatePurpose").submit();
                });
            });
        </script>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jsp"%>
        <%@include file="/WEB-INF/jspf/header_login.jsp"%>
        <html:form styleId="updatePurpose" action="UpdatePurpose.do">
            <html:hidden styleId="action" property="action"/>
            <html:hidden styleId="period" property="period"/>
            <bean:define id="purposeId" name="updatePurposeForm" property="purposeId" scope="request" toScope="page" type="String"/>
            <html:hidden styleId="purposeId" property="purposeId" value="<%= purposeId%>"/>
            <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Edit Purpose</h3>
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
                <b>Edit Purpose:</b><br>
                (All fields required)<br>
                <table>
                    <tr>
                        <td>Short Name:</td>
                        <td><input type="text" id="shortName" name="shortName" value="<bean:write name="updatePurposeForm" property="shortName" scope="request"/>"></td>
                    </tr>
                    <tr>
                        <td>Description:</td>
                        <td><textarea id="description" name="description" style="width: 400px; height: 100px;"><bean:write name="updatePurposeForm" property="description" scope="request"/></textarea></td>
                    </tr>
                    <tr>
                        <td>Expiration Period:</td>
                        <td>
                            <logic:notEqual name="updatePurposeForm" property="period" value="-1" scope="request">
                                <input type="text" size="4" class="periodText" value="<bean:write name="updatePurposeForm" property="period" scope="request"/>"> Month(s)
                            </logic:notEqual>
                            <logic:equal name="updatePurposeForm" property="period" value="-1" scope="request">
                                <input type="text" size="4" class="periodText" value="" readonly>
                            </logic:equal>
                            <logic:equal name="updatePurposeForm" property="permission" value="1" scope="request">
                                <b>OR</b>
                                <logic:equal name="updatePurposeForm" property="period" value="-1" scope="request">
                                    <input type="checkbox" class="expiresCheck" size="4" checked> Does not expire
                                </logic:equal>
                                <logic:notEqual name="updatePurposeForm" property="period" value="-1" scope="request">
                                    <input type="checkbox" class="expiresCheck" size="4"> Does not expire
                                </logic:notEqual>
                            </logic:equal>
                        </td>
                    </tr>
                </table>
                <button id="submitBtn" onclick="return false">Submit</button>
            </div>
            </center>
        </html:form>
        <%@include file="/WEB-INF/jspf/footer.jsp"%>
        <div id="confirm_dialog" title="Remove No Expiration" style="display: none; font-size: 12px; text-align: center;">
            <div style="text-align: left;">
                If you deactivate the property "Does not expire", you don't have authority to reactivate the property later. Are you sure you want to deactivate the property "Does not expire"?
            </div>
            <button id="submit_confirm_dialog" onclick="return false;">Submit</button>
            &nbsp;&nbsp;
            <button id="cancel_confirm_dialog" onclick="$('#confirm_dialog').dialog('close');return false;">Cancel</button>
        </div>
    </body>
</html>
