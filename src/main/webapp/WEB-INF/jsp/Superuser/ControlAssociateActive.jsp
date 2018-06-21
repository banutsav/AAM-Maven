<%-- 
    Document   : ContolAssociateActive
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
                
                $("#cancelBtn").click(function(){
                    $("#action").val("back");
                    $("#controlAssociateActiveForm").submit();
                });
                
                $("#activate_btn").click(function(){
                    $("#action").val("activate");
                    $("#controlAssociateActiveForm").submit();
                });
                
                $("#deactivate_btn").click(function(){
                    $("#deactivate_dialog").dialog("open");
                });
                
                $("#deactivate_dialog_cancel").click(function(){
                    $("#deactivate_dialog").dialog("close");
                });
                
                $("#deactivate_dialog_submit").click(function(){
                    $("#action").val("deactivate");
                    $("#controlAssociateActiveForm").submit();
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
                    $("#controlAssociateActiveForm").submit();
                })
                
                $("#cancel_manage_purp_dialog").click(function(){
                    $("#manage_purp_dialog").dialog("close");
                })
                
                $("#backLink").click(function(){
                    $("#action").val("back");
                    $("#controlAssociateActiveForm").submit();
                });
            });
        </script>
    </head>
    <body>
        <compress:compress>
            <html:form action="ControlAssociateActive.do" styleId="controlAssociateActiveForm">
                <%@include file="/WEB-INF/jspf/header.jsp"%>
                <%@include file="/WEB-INF/jspf/header_login.jsp"%>
                <html:hidden styleId="action" property="action"/>
                <bean:define id="affId" name="controlAssociateActiveForm" property="affId" scope="request" toScope="page" type="String"/>
                <html:hidden styleId="affId" property="affId" value="<%= affId%>"/>

                <html:hidden styleId="targPurposeId" property="targPurposeId"/>
                <html:hidden styleId="targStatus" property="targStatus"/>
                <html:hidden styleId="targExpDate" property="targExpDate"/>
                <html:hidden styleId="targExpires" property="targExpires"/>

                <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Superuser Associate Manager</h3>
                    <hr/>
                    
                    <logic:notEmpty name="errorMsg" scope="request">
                        <div>
                            <b style="color: red;"><u><bean:write name="errorMsg" scope="request"/></u></b>
                        </div>
                    </logic:notEmpty>
                    
                    <!-- success message -->
                    <logic:notEmpty name="message" scope="request">
                        <div>
                            <b style="color: green;"><u><bean:write name="message" scope="request"/></u></b>
                        </div>
                    </logic:notEmpty>
                   
                    
                    <div style="text-align: left;">
                        <div style="text-align: right;">
                            <a href="./Get.do?action=home">Main Menu</a>
                        </div>
                    <h5>Associate Information:</h5>
                    <table>
                        <tr>
                            <td><b>Name: </b></td>
                            <td><bean:write name="controlAssociateActiveForm" property="lastName" scope="request"/>, <bean:write name="controlAssociateActiveForm" property="firstName" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>Active: </b></td>
                            <td>
                                <logic:equal name="controlAssociateActiveForm" property="active" scope="request" value="0">
                                    <b style="color: red;">No</b>
                                </logic:equal>
                                <logic:equal name="controlAssociateActiveForm" property="active" scope="request" value="1">
                                    Yes
                                </logic:equal>
                            </td>
                        </tr>
                        <tr>
                            <td><b>MYID: </b></td>
                            <td><bean:write name="controlAssociateActiveForm" property="ovprId" scope="request"/></td>
                        </tr>
                        
                        <tr>
                            <td><b>Login ID (Associate Email): </b></td>
                            <td><bean:write name="controlAssociateActiveForm" property="email" scope="request"/></td>
                        </tr>
                        
                        
                        <tr>
                            <td><b>UGAID: </b></td>
                            <td><bean:write name="controlAssociateActiveForm" property="pseudoCan" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>ORGANIZATION: </b></td>
                            <td><bean:write name="controlAssociateActiveForm" property="organization" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>DEPARTMENT: </b></td>
                            <td><bean:write name="controlAssociateActiveForm" property="department" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>TITLE: </b></td>
                            <td><bean:write name="controlAssociateActiveForm" property="title" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>Phone: </b></td>
                            <td><bean:write name="controlAssociateActiveForm" property="phone" scope="request"/></td>
                        </tr>
                        
                        <tr>
                            <td><b>IS ENROLLED: </b></td>
                            <td><bean:write name="controlAssociateActiveForm" property="isEnrolled" scope="request"/></td>
                        </tr>
                        
                        <tr>
                            <td><b>HR STATUS: </b></td>
                            <td><bean:write name="controlAssociateActiveForm" property="ugaHRStatus" scope="request"/></td>
                        </tr>
                        
                    </table>
                    <br>
                    <logic:equal name="controlAssociateActiveForm" property="active" scope="request" value="0">
                        <button id="activate_btn" onclick="return false;">Activate the Associate</button>
                    </logic:equal>
                    <logic:equal name="controlAssociateActiveForm" property="active" scope="request" value="1">
                        <button id="deactivate_btn" onclick="return false;">Deactivate the Associate</button>
                    </logic:equal>
                    
                    <button id="cancelBtn" onclick="return false;">Done</button>
                    
                    <br>
                    
                </div>
                </center>
                <%@include file="/WEB-INF/jspf/footer.jsp"%>

                <div id="deactivate_dialog" title="Deactivate Associate" style="display: none; font-size: 12px; text-align: left;">
                    
                    <div style="position: relative; width: 100%; text-align: center;">
                    This action will remove the following person from the OVPR LDAP server - 
                    <b><bean:write name="controlAssociateActiveForm" property="firstName" scope="request"/> <bean:write name="controlAssociateActiveForm" property="lastName" scope="request"/></b>
                    <br><br>
                    Are you sure you want to deactivate the Associate?
                    <br><br>
                    </div>
                    
                    <div style="position: relative; width: 100%; text-align: center;">
                        <button id="deactivate_dialog_submit">Yes</button>
                        &nbsp;&nbsp;&nbsp;
                        <button id="deactivate_dialog_cancel">No</button>
                    </div>
                </div>

                <div id="manage_purp_dialog" title="Activate/Deactivate Associate" style="display: none; font-size: 12px; text-align: left;">
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
