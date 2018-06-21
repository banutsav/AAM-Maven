<%-- 
    Document   : ContolAffActive
    Created on : May 15, 2013, 11:20:56 AM
    Author     : submyers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="/WEB-INF/tags-compress" prefix="compress" %>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String affId;%>
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
        <script>
            var currTargPurposeName = "";
            
            function OpenManPurpDialog(purposeName, purposeId, status, expires, expDate){
                                
                $("#targ_purp_name").html(purposeName);

                $("#status_manage_purp_dialog").val(status);
                
                if(expires === "1"){
                    $("#expires_manage_purp_dialog").attr("checked", "checked");
                }
                
                $("#exp_date_manage_purp_dialog").val(expDate);
                
                $("#targPurposeId").val(purposeId);
                $("#targStatus").val(status);
                $("#targExpires").val(expires);
                $("#targExpDate").val(expDate);
                                
                $("#manage_purp_dialog").dialog("open");
            }
            
            $(document).ready(function(){
                
                $("#deactivate_dialog").dialog({
                    autoOpen: false,
                    width: "auto",
                    modal: true,
                    resizable: false
                });
                
                $("#manage_purp_dialog").dialog({
                    autoOpen: false,
                    width: "auto",
                    modal: true,
                    resizable: false
                });
                
                $("#activate_btn").click(function(){
                    $("#action").val("activate");
                    $("#controlAffActiveForm").submit();
                });
                
                $("#deactivate_btn").click(function(){
                    $("#deactivate_dialog").dialog("open");
                });
                
                $("#deactivate_dialog_cancel").click(function(){
                    $("#deactivate_dialog").dialog("close");
                });
                
                $("#deactivate_dialog_submit").click(function(){
                    $("#action").val("deactivate");
                    $("#controlAffActiveForm").submit();
                });
                
                $("#submit_manage_purp_dialog").click(function(){
                    $("#targStatus").val($("#status_manage_purp_dialog").val());
                    if($("#expires_manage_purp_dialog").is(":checked")){
                        $("#targExpires").val("1");
                        if($("#exp_date_manage_purp_dialog").val() === ""){
                            alert("You must define an expiration date");
                            return false;
                        }
                    } else {
                        $("#targExpires").val("0");
                    }
                    
                    $("#targExpDate").val($("#exp_date_manage_purp_dialog").val());
                
                    $("#action").val("changePurpose");
                    $("#controlAffActiveForm").submit();
                })
                
                $("#cancel_manage_purp_dialog").click(function(){
                    $("#manage_purp_dialog").dialog("close");
                })
                
                $("#backLink").click(function(){
                    $("#action").val("back");
                    $("#controlAffActiveForm").submit();
                });
            });
        </script>
    </head>
    <body>
        <compress:compress>
            <html:form action="ControlAffActive.do" styleId="controlAffActiveForm">
                <%@include file="/WEB-INF/jspf/header.jsp"%>
                <%@include file="/WEB-INF/jspf/header_login.jsp"%>
                <html:hidden styleId="action" property="action"/>
                <bean:define id="affId" name="controlAffActiveForm" property="affId" scope="request" toScope="page" type="String"/>
                <html:hidden styleId="affId" property="affId" value="<%= affId%>"/>

                <html:hidden styleId="targPurposeId" property="targPurposeId"/>
                <html:hidden styleId="targStatus" property="targStatus"/>
                <html:hidden styleId="targExpDate" property="targExpDate"/>
                <html:hidden styleId="targExpires" property="targExpires"/>

                <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Superuser Affiliate Manager</h3>
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
                    <h5>Affiliate Information:</h5>
                    <table>
                        <tr>
                            <td><b>Name</b></td>
                            <td><bean:write name="controlAffActiveForm" property="lastName" scope="request"/>, <bean:write name="controlAffActiveForm" property="firstName" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>Active</b></td>
                            <td>
                                <logic:equal name="controlAffActiveForm" property="active" scope="request" value="0">
                                    <b style="color: red;">No</b>
                                </logic:equal>
                                <logic:equal name="controlAffActiveForm" property="active" scope="request" value="1">
                                    Yes
                                </logic:equal>
                            </td>
                        </tr>
                        <tr>
                            <td><b>OVPR ID</b></td>
                            <td><bean:write name="controlAffActiveForm" property="ovprId" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>PSEUDO-UGAID</b></td>
                            <td><bean:write name="controlAffActiveForm" property="pseudoCan" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>ORGANIZATION</b></td>
                            <td><bean:write name="controlAffActiveForm" property="organization" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>DEPARTMENT</b></td>
                            <td><bean:write name="controlAffActiveForm" property="department" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>TITLE</b></td>
                            <td><bean:write name="controlAffActiveForm" property="title" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>EMAIL</b></td>
                            <td><bean:write name="controlAffActiveForm" property="email" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>Phone</b></td>
                            <td><bean:write name="controlAffActiveForm" property="phone" scope="request"/></td>
                        </tr>
                    </table>
                    <br>
                    <logic:equal name="controlAffActiveForm" property="active" scope="request" value="0">
                        <button id="activate_btn" onclick="return false;">Activate the Affiliate</button>
                    </logic:equal>
                    <logic:equal name="controlAffActiveForm" property="active" scope="request" value="1">
                        <button id="deactivate_btn" onclick="return false;">Deactivate the Affiliate</button>
                    </logic:equal>
                    <br>
                    <h5>Purposes:</h5>
                    <logic:empty name="controlAffActiveForm" property="purposes" scope="request">
                        Not currently linked with any purpose.
                    </logic:empty>
                    <logic:notEmpty name="controlAffActiveForm" property="purposes" scope="request">
                        <table>
                            <tr>
                                <td>
                                    <div style="width: 150px; background-color: lightblue; overflow: auto;">
                                        Short Name
                                    </div>
                                </td>
                                <td>
                                    <div style="width: 250px; background-color: lightblue; overflow: auto;">
                                        Description
                                    </div>
                                </td>
                                <td>
                                    <div style="width: 120px; background-color: lightblue; overflow: auto;">
                                        Status
                                    </div>
                                </td>
                                <td>
                                    <div style="width: 60px; background-color: lightblue; overflow: auto;">
                                        Expired
                                    </div>
                                </td>
                                <td>
                                    <div style="width: 120px; background-color: lightblue; overflow: auto;">
                                        Expiration Date
                                    </div>
                                </td>
                            </tr>
                        </table>
                        <table>
                            <logic:iterate id="purpose" name="controlAffActiveForm" property="purposes" scope="request">
                                <tr>
                                    <td>
                                        <div style="width: 150px; overflow: auto;">
                                            <a href="#" onclick="OpenManPurpDialog('<bean:write name="purpose" property="SHORT_NAME" scope="page"/>','<bean:write name="purpose" property="ID" scope="page"/>','<bean:write name="purpose" property="APPROVED" scope="page"/>','<bean:write name="purpose" property="EXPIRES" scope="page"/>','<bean:write name="purpose" property="EXPIRATION_DATE" scope="page"/>');return false;">
                                                <bean:write name="purpose" property="SHORT_NAME" scope="page"/>
                                            </a>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="width: 250px; overflow: auto;">
                                            <bean:write name="purpose" property="DESCRIPTION" scope="page"/>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="width: 120px; overflow: auto;">
                                            <bean:write name="purpose" property="APPROVED" scope="page"/>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="width: 60px; overflow: auto;">
                                            <logic:equal name="purpose" property="EXPIRED" scope="page" value="1">
                                                <b style="color: red">Yes</b>
                                            </logic:equal>
                                            <logic:equal name="purpose" property="EXPIRED" scope="page" value="0">
                                                -
                                            </logic:equal>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="width: 120px; overflow: auto;">
                                            <logic:equal name="purpose" property="EXPIRES" scope="page" value="0">
                                                <b>Does not expire</b>
                                            </logic:equal>
                                            <logic:notEqual name="purpose" property="EXPIRES" scope="page" value="0">
                                                <bean:write name="purpose" property="EXPIRATION_DATE" scope="page"/>
                                            </logic:notEqual>
                                        </div>
                                    </td>
                                </tr>
                            </logic:iterate>
                        </table>
                    </logic:notEmpty>
                </div>
                </center>
                <%@include file="/WEB-INF/jspf/footer.jsp"%>

                <div id="deactivate_dialog" title="Deactivate Affiliate" style="display: none; font-size: 12px; text-align: left;">
                    Deactivating affiliate
                    <div style="position: relative; width: 100%; text-align: center;">
                        <b><bean:write name="controlAffActiveForm" property="firstName" scope="request"/> <bean:write name="controlAffActiveForm" property="lastName" scope="request"/></b>
                    </div>
                    will set ACTIVE='0' in MySQL table paul.AFFILIATE
                    <br><br>
                    Are you sure you want to deactivate the affiliate?
                    <div style="position: relative; width: 100%; text-align: center;">
                        <button id="deactivate_dialog_submit">Yes</button>
                        &nbsp;&nbsp;&nbsp;
                        <button id="deactivate_dialog_cancel">No</button>
                    </div>
                </div>

                <div id="manage_purp_dialog" title="Activate/Deactivate Affiliate" style="display: none; font-size: 12px; text-align: left;">
                    Change the status and/or expiration date for purpose "<span id="targ_purp_name"></span>"
                    <br><br>
                    Status:
                    <select id="status_manage_purp_dialog">
                        <option value="Approved">Approved</option>
                        <option value="Submitted">Submitted</option>
                        <option value="Rejected">Rejected</option>
                    </select>
                    <br><br>
                    Expires <input type="checkbox" id="expires_manage_purp_dialog">
                    <br>(Unchecked means no expiration will occur)
                    <br><br>
                    <script>
                        $('#exp_date_manage_purp_dialog').datetimepicker();
                        $('#ui-datepicker-div').css("font-size","12px");
                    </script>
                    Expiration Date <input type="text" id="exp_date_manage_purp_dialog">
                    <br><br>
                    <button id="submit_manage_purp_dialog">Submit</button>
                    &nbsp;&nbsp;&nbsp;
                    <button id="cancel_manage_purp_dialog">Cancel</button>
                </div>
            </html:form>
        </compress:compress>
    </body>
</html>
