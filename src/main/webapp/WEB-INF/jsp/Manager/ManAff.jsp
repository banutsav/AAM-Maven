<%-- 
    Document   : ManAff
    Created on : Apr 15, 2013, 1:17:30 PM
    Author     : submyers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="/WEB-INF/tags-compress" prefix="compress" %>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String back, backPurposeId;%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/html/meta.html"%>
        <%@include file="/WEB-INF/html/title.html"%>
        <%@include file="/WEB-INF/html/css.html"%>
        <script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-min.js"></script>
        <link type="text/css" media="all" rel="stylesheet" href="css/jquery-ui-1.10.2.custom.min.css">
        <script>
            var currTargUserId;
            var currTargPurposeId;
            var currTargPeriod;
            var linkExpDate;
            
            function ManageRequests(){
                $("#action").val("manRequests");
                $("#manAffForm").submit();            
            }
            
            function PurposeDetails(purposeId){
                $("#purpose_details_" + purposeId).slideToggle("fast");
            }

            function OpenAffPurposeDetails(userId,purposeId){
                $("#purpose_details_" + userId + "_" + purposeId).slideToggle("fast");
            }

            function OpenAffPurposeApproval(userId,purposeId){
                $("#purpose_approval_" + userId + "_" + purposeId).slideToggle("fast");
            }

            function OpenAddDialog(userId){
                
                currTargUserId = userId;

                $("#add_purpose_dialog").dialog("open");
            }
            
            function CloseAddDialog(){
                $("#add_purpose_dialog").dialog("close");
            }
            
            function CommitAddPurposeDialog(){
                if($("#dialogAddPurposeId").val() === ""){
                    alert("You must select a purpose.");
                    return false;
                }
                
                $("#affId").val(currTargUserId);
                $("#addPurposeId").val(currTargPurposeId);
                $("#periodLength").val(currTargPeriod);
                $("#action").val("addAffPurpose");
                
                $("#manAffForm").submit();
            }
            
            function OpenManageDialog(userId,purposeId,approval,exp_date,short_name,period,unexpire_privilege){
                
                currTargUserId = userId;
                currTargPurposeId = purposeId;
                currTargPeriod = period;
                linkExpDate = exp_date;
                
                $("#manage_purpose_dialog_status_select_" + approval).attr("selected","selected");
                
                $(".manage_purpose_dialog_short_name").html(short_name);
                
                $("#manage_purpose_dialog").dialog("open");
            }

            function CommitManagePurposeDialog(){
                if(parseInt($("#dialogExpDateMonths").val()) < 1){
                    alert("The number of months must be greater than or equal to 1.");
                    return false;
                }
                
                $("#affId").val(currTargUserId);
                $("#targPurposeId").val(currTargPurposeId);
                $("#monthCount").val($("#dialogExpDateMonths").val());
                $("#periodLength").val(currTargPeriod);
                $("#linkExpDate").val(linkExpDate);
                $("#action").val("manageAffPurpose");
                
                $("#manAffForm").submit();
            }
            
            function OpenManageExpDateDialog(userId,purposeId,expires,exp_date,short_name,period,unexpire_privilege){
                currTargUserId = userId;
                currTargPurposeId = purposeId;
                currTargPeriod = period;
                linkExpDate = exp_date;

                if(unexpire_privilege == 1){
                    $("#man_exp_date_unexpire").css("display","");
                    if(expires == 0){
                        $("#man_exp_date_unexpire_checkbox").attr("checked", "checked");
                        $("#dialogExpDateMonths").val("");
                        $("#dialogExpDateMonths").attr("readonly", "readonly");
                    } else {
                        $("#man_exp_date_unexpire_checkbox").removeAttr("checked");
                        $("#dialogExpDateMonths").val("1");
                        $("#dialogExpDateMonths").removeAttr("readonly");
                    }
                } else {
                    $("#dialogExpDateMonths").val("1");
                    $("#dialogExpDateMonths").removeAttr("readonly");
                    $("#man_exp_date_unexpire").css("display","none");
                }
                
                $(".man_exp_dialog_short_name").html(short_name);
                
                $("#man_exp_date_dialog").dialog("open");
            }
            
            function CommitManageExpDateDialog(){
                
                if($("#man_exp_date_unexpire_checkbox").is(":checked")){
                    $("#expires").val("0");
                } else {
                    $("#expires").val("1");
                }
                
                $("#affId").val(currTargUserId);
                $("#targPurposeId").val(currTargPurposeId);
                $("#purposeId").val(currTargPurposeId);
                $("#monthCount").val($("#dialogExpDateMonths").val());
                $("#periodLength").val(currTargPeriod);
                $("#linkExpDate").val(linkExpDate);
                $("#action").val("managePurposeExp");
                
                $("#manAffForm").submit();
            }

            function backSubmit(){
                $("#action").val("back");
                $("#manAffForm").submit();
            };
                
            function CloseManageDialog(){
                $("#manage_purpose_dialog").dialog("close");
            }
            
            $(document).ready(function(){
                
                $("#add_purpose_dialog").dialog({
                    autoOpen: false,
                    width: "auto",
                    modal: true,
                    resizable: false
                });
                
                $("#manage_purpose_dialog").dialog({
                    autoOpen: false,
                    width: 500,
                    modal: true,
                    resizable: false,
                    minWidth: 370,
                    maxWidth: 500
                });
                
                $("#man_exp_date_dialog").dialog({
                    autoOpen: false,
                    modal: true,
                    resizable: false
                });
                
                
                $("#dialogAddPurposeId").change(function(){
                    var arr = $("#dialogAddPurposeId").val().split(",");
                    currTargPurposeId = arr[0];
                    currTargPeriod = arr[1];
                });
                
                $("#manage_purpose_dialog_unexpire").click(function(){
                    $("#affId").val(currTargUserId);
                    $("#targPurposeId").val(currTargPurposeId);
                    $("#monthCount").val($("#dialogExpDateMonths").val());
                    $("#periodLength").val(currTargPeriod);
                    $("#linkExpDate").val(linkExpDate);
                    $("#action").val("unexpireAffPurpose");
                
                    $("#manAffForm").submit();
                });
                
                $("#manage_purpose_dialog_change").click(function(){
                    $("#affId").val(currTargUserId);
                    $("#targPurposeId").val(currTargPurposeId);
                    $("#newApprovalStatus").val($("#manage_purpose_dialog_status_select").val());
                    $("#action").val("changeApprovalStatus");
                    $("#manAffForm").submit();
                });
                
                $("#done_btn").click(function(){
                    $("#action").val("done");
                    $("#manAffForm").submit();
                });
                
                $("#man_exp_date_unexpire_checkbox").click(function(){
                    if($("#man_exp_date_unexpire_checkbox").is(":checked")){
                        $("#dialogExpDateMonths").val("");
                        $("#dialogExpDateMonths").attr("readonly", "readonly");
                    } else {
                        $("#dialogExpDateMonths").val("1");
                        $("#dialogExpDateMonths").removeAttr("readonly");
                    }
                });
            });
        </script>
    </head>
    <body>
        <compress:compress>
            <html:form action="ManAff.do" styleId="manAffForm">
                <%@include file="/WEB-INF/jspf/header.jsp"%>
                <%@include file="/WEB-INF/jspf/header_login.jsp"%>
                <html:hidden styleId="action" property="action"/>
                <html:hidden styleId="affId" property="affId"/>
                <html:hidden styleId="purposeId" property="purposeId"/>
                <html:hidden styleId="addPurposeId" property="addPurposeId"/>
                <html:hidden styleId="addPurposeExpDate" property="addPurposeExpDate"/>
                <html:hidden styleId="targPurposeId" property="targPurposeId"/>
                <html:hidden styleId="targExpDate" property="targExpDate"/>
                <html:hidden styleId="monthCount" property="monthCount"/>
                <html:hidden styleId="periodLength" property="periodLength"/>
                <html:hidden styleId="linkExpDate" property="linkExpDate"/>
                <html:hidden styleId="newApprovalStatus" property="newApprovalStatus"/>
                <html:hidden styleId="expires" property="expires"/>
                <bean:define id="back" name="manAffForm" property="back" scope="request" toScope="page" type="String"/>
                <html:hidden styleId="back" property="back" value="<%= back%>"/>
                <bean:define id="backPurposeId" name="manAffForm" property="backPurposeId" scope="request" toScope="page" type="String"/>
                <html:hidden styleId="backPurposeId" property="backPurposeId" value="<%= backPurposeId%>"/>

                <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Manage Affiliate</h3>
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
                    <bean:define id="aff" name="manAffForm" property="aff" scope="request" toScope="page" type="java.util.HashMap"/>
                    <div id="aff_details_<bean:write name="aff" property="ID" scope="page"/>">
                        <table>
                            <tr>
                                <td>
                                    <b>Name:</b>
                                </td>
                                <td>
                                    <bean:write name="aff" property="LASTNAME" scope="page"/>,
                                    <bean:write name="aff" property="FIRSTNAME" scope="page"/>
                                    <bean:write name="aff" property="MIDDLENAME" scope="page"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Login Username:</b>
                                </td>
                                <td>
                                    <bean:write name="aff" property="OVPRID" scope="page"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Excluded from LDAP:</b>
                                </td>
                                <td>
                                    <logic:equal name="aff" property="ACTIVE" scope="page" value="1">
                                        No
                                    </logic:equal>
                                    <logic:equal name="aff" property="ACTIVE" scope="page" value="0">
                                        <b style="color: red;">Yes</b>
                                    </logic:equal>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Pseudo UGAID:</b>
                                </td>
                                <td>
                                    <bean:write name="aff" property="PSEUDOCAN" scope="page"/>
                                </td>
                            </tr>
                            <tr>
                                <td valign="top">
                                    <b>Address:</b>
                                </td>
                                <td>
                                    <logic:empty name="aff" property="ADDR_1" scope="page">
                                        -
                                    </logic:empty>
                                    <logic:notEmpty name="aff" property="ADDR_1" scope="page">
                                        <bean:write name="aff" property="ADDR_1" scope="page"/>
                                        <br>
                                        <logic:notEmpty name="aff" property="ADDR_2" scope="page">
                                            <bean:write name="aff" property="ADDR_2" scope="page"/>
                                            <br>
                                        </logic:notEmpty>
                                        <bean:write name="aff" property="CITY" scope="page"/>, 
                                        <bean:write name="aff" property="STATE" scope="page"/>
                                        <bean:write name="aff" property="ZIP" scope="page"/>
                                        <br>
                                        <bean:write name="aff" property="COUNTRY" scope="page"/>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Organization:</b>
                                </td>
                                <td>
                                    <logic:empty name="aff" property="ORGANIZATION" scope="page">
                                        -
                                    </logic:empty>
                                    <logic:notEmpty name="aff" property="ORGANIZATION" scope="page">
                                        <bean:write name="aff" property="ORGANIZATION" scope="page"/>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Department:</b>
                                </td>
                                <td>
                                    <logic:empty name="aff" property="DEPARTMENT" scope="page">
                                        -
                                    </logic:empty>
                                    <logic:notEmpty name="aff" property="DEPARTMENT" scope="page">
                                        <bean:write name="aff" property="DEPARTMENT" scope="page"/>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Title:</b>
                                </td>
                                <td>
                                    <logic:empty name="aff" property="TITLE" scope="page">
                                        -
                                    </logic:empty>
                                    <logic:notEmpty name="aff" property="TITLE" scope="page">
                                        <bean:write name="aff" property="TITLE" scope="page"/>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Phone Number:</b>
                                </td>
                                <td>
                                    <logic:empty name="aff" property="PHONE" scope="page">
                                        -
                                    </logic:empty>
                                    <logic:notEmpty name="aff" property="PHONE" scope="page">
                                        <bean:write name="aff" property="PHONE" scope="page"/>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Business Phone Number:</b>
                                </td>
                                <td>
                                    <logic:empty name="aff" property="BUSINESS_PHONE_NUM" scope="page">
                                        -
                                    </logic:empty>
                                    <logic:notEmpty name="aff" property="BUSINESS_PHONE_NUM" scope="page">
                                        <bean:write name="aff" property="BUSINESS_PHONE_NUM" scope="page"/>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Fax:</b>
                                </td>
                                <td>
                                    <logic:empty name="aff" property="FAX_NUMBER" scope="page">
                                        -
                                    </logic:empty>
                                    <logic:notEmpty name="aff" property="FAX_NUMBER" scope="page">
                                        <bean:write name="aff" property="FAX_NUMBER" scope="page"/>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>FSCODES:</b>
                                </td>
                                <td>
                                    <logic:empty name="aff" property="FSCODES" scope="page">
                                        -
                                    </logic:empty>
                                    <logic:notEmpty name="aff" property="FSCODES" scope="page">
                                        <bean:write name="aff" property="FSCODES" scope="page"/>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr>
                                <td style="width:200px;">
                                    <b>Comments:</b>
                                </td>
                                <td colspan="3">
                                    <logic:empty name="aff" property="COMMENTS" scope="page">
                                        -
                                    </logic:empty>
                                    <logic:notEmpty name="aff" property="COMMENTS" scope="page">
                                        <div style="max-width: 400px;">
                                            <bean:write name="aff" property="COMMENTS" scope="page"/>
                                        </div>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <logic:empty name="aff" property="purposeDetails" scope="page">
                                <tr>
                                    <td colspan="2">
                                        <b>Not approved for any purpose.</b>
                                    </td>
                                </tr>
                            </logic:empty>
                            <logic:notEmpty name="aff" property="purposeDetails" scope="page">
                                <tr>
                                    <td colspan="2">
                                        <div style="min-width: 750px;">
                                            <table>
                                                <tr>
                                                    <td>
                                                        <div style="width: 240px; background-color: lightblue; overflow: auto;">
                                                            Purpose Short Name
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div style="width: 140px; background-color: lightblue; overflow: auto;">
                                                            Approval Status
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div style="width: 70px; background-color: lightblue; overflow: auto;">
                                                            Expired
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div style="width: 140px; background-color: lightblue; overflow: auto;">
                                                            Request Date
                                                        </div>
                                                    </td>
                                                    <td>
                                                        <div style="width: 180px; background-color: lightblue; overflow: auto;">
                                                            Approval Expiration Date
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <div style="min-width: 750px;">
                                            <table>
                                                <logic:iterate id="aff_purpose" name="aff" property="purposeDetails" scope="page">
                                                    <tr>
                                                        <td>
                                                            <div style="width: 240px; overflow: auto;">
                                                                <logic:equal name="aff_purpose" property="PERMISSION" scope="page" value="1"> 
                                                                    <a href="#" onclick="OpenManageDialog('<bean:write name="aff" property="ID" scope="page"/>','<bean:write name="aff_purpose" property="PURPOSE_ID" scope="page"/>','<bean:write name="aff_purpose" property="LINK_APPROVED" scope="page"/>','<bean:write name="aff_purpose" property="LINK_EXPIRATION_DATE" scope="page"/>','<bean:write name="aff_purpose" property="SHORT_NAME" scope="page"/>','<bean:write name="aff_purpose" property="PERIOD" scope="page"/>','<bean:write name="aff_purpose" property="UNEXPIRE_PRIVILEGE" scope="page"/>'); return false;"><bean:write name="aff_purpose" property="SHORT_NAME" scope="page"/></a>
                                                                </logic:equal>
                                                                <logic:notEqual name="aff_purpose" property="PERMISSION" scope="page" value="1"> 
                                                                    <bean:write name="aff_purpose" property="SHORT_NAME" scope="page"/>
                                                                </logic:notEqual>
                                                                <!--a href="#"  onClick="OpenAffPurposeDetails('<!bean:write name="aff" property="ID" scope="page"/>','<!bean:write name="aff_purpose" property="PURPOSE_ID" scope="page"/>');return false;"><!bean:write name="aff_purpose" property="SHORT_NAME" scope="page"/></a-->
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div style="width: 140px; overflow: auto;">
                                                                <logic:equal name="aff_purpose" property="LINK_APPROVED" scope="page" value="Submitted">
                                                                    Submitted
                                                                </logic:equal>
                                                                <logic:equal name="aff_purpose" property="LINK_APPROVED" scope="page" value="Approved">
                                                                    Approved
                                                                    <!--a href="#" onClick="OpenAffPurposeApproval('<!bean:write name="aff" property="ID" scope="page"/>','<!bean:write name="aff_purpose" property="PURPOSE_ID" scope="page"/>');return false;">Approved</a-->
                                                                </logic:equal>
                                                                <logic:equal name="aff_purpose" property="LINK_APPROVED" scope="page" value="Rejected">
                                                                    Rejected
                                                                    <!--a href="#" onClick="OpenAffPurposeApproval('<!bean:write name="aff" property="ID" scope="page"/>','<!bean:write name="aff_purpose" property="PURPOSE_ID" scope="page"/>');return false;">Rejected</a-->
                                                                </logic:equal>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div style="width: 70px; overflow: auto;">
                                                                <logic:equal name="aff_purpose" property="LINK_APPROVED" scope="page" value="Approved">
                                                                    <logic:notEqual name="aff_purpose" property="EXPIRED" value="1" scope="page">
                                                                        -
                                                                    </logic:notEqual>
                                                                    <logic:equal name="aff_purpose" property="EXPIRED" value="1" scope="page">
                                                                        <b style="color: red;">Yes</b>
                                                                    </logic:equal>
                                                                </logic:equal>
                                                                <logic:notEqual name="aff_purpose" property="LINK_APPROVED" scope="page" value="Approved">
                                                                    -
                                                                </logic:notEqual>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div style="width: 140px; overflow: auto;">
                                                                <logic:empty name="aff_purpose" property="LINK_CREATION_DATE" scope="page">
                                                                    -
                                                                </logic:empty>
                                                                <logic:notEmpty name="aff_purpose" property="LINK_CREATION_DATE" scope="page">
                                                                    <bean:write name="aff_purpose" property="LINK_CREATION_DATE" scope="page"/>
                                                                </logic:notEmpty>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div style="width: 180px; overflow: auto;">
                                                                <logic:notEqual name="aff_purpose" property="EXPIRES" value="0" scope="page">
                                                                    <logic:empty name="aff_purpose" property="LINK_EXPIRATION_DATE" scope="page">
                                                                        <logic:equal name="aff_purpose" property="LINK_APPROVED" scope="page" value="Approved">
                                                                            <logic:equal name="aff_purpose" property="PERMISSION" scope="page" value="1"> 
                                                                                <a href="#" onclick="OpenManageExpDateDialog('<bean:write name="aff" property="ID" scope="page"/>','<bean:write name="aff_purpose" property="PURPOSE_ID" scope="page"/>','<bean:write name="aff_purpose" property="EXPIRES" scope="page"/>','<bean:write name="aff_purpose" property="LINK_EXPIRATION_DATE" scope="page"/>','<bean:write name="aff_purpose" property="SHORT_NAME" scope="page"/>','<bean:write name="aff_purpose" property="PERIOD" scope="page"/>','<bean:write name="aff_purpose" property="UNEXPIRE_PRIVILEGE" scope="page"/>'); return false;">Does not expire</a>
                                                                            </logic:equal>
                                                                            <logic:notEqual name="aff_purpose" property="PERMISSION" scope="page" value="1"> 
                                                                                Does not expire
                                                                            </logic:notEqual>
                                                                        </logic:equal>
                                                                        <logic:notEqual name="aff_purpose" property="LINK_APPROVED" scope="page" value="Approved">
                                                                            -
                                                                        </logic:notEqual>
                                                                    </logic:empty>
                                                                    <logic:notEmpty name="aff_purpose" property="LINK_EXPIRATION_DATE" scope="page">
                                                                        <logic:equal name="aff_purpose" property="PERMISSION" scope="page" value="1"> 
                                                                            <logic:equal name="aff_purpose" property="LINK_APPROVED" scope="page" value="Approved">
                                                                                <a href="#" onclick="OpenManageExpDateDialog('<bean:write name="aff" property="ID" scope="page"/>','<bean:write name="aff_purpose" property="PURPOSE_ID" scope="page"/>','<bean:write name="aff_purpose" property="EXPIRES" scope="page"/>','<bean:write name="aff_purpose" property="LINK_EXPIRATION_DATE" scope="page"/>','<bean:write name="aff_purpose" property="SHORT_NAME" scope="page"/>','<bean:write name="aff_purpose" property="PERIOD" scope="page"/>','<bean:write name="aff_purpose" property="UNEXPIRE_PRIVILEGE" scope="page"/>'); return false;"><bean:write name="aff_purpose" property="LINK_EXPIRATION_DATE" scope="page"/></a>
                                                                            </logic:equal>
                                                                            <logic:notEqual name="aff_purpose" property="LINK_APPROVED" scope="page" value="Approved">
                                                                                -
                                                                                <!bean:write name="aff_purpose" property="LINK_EXPIRATION_DATE" scope="page"/>
                                                                            </logic:notEqual>
                                                                        </logic:equal>
                                                                        <logic:notEqual name="aff_purpose" property="PERMISSION" scope="page" value="1"> 
                                                                            <bean:write name="aff_purpose" property="LINK_EXPIRATION_DATE" scope="page"/>
                                                                        </logic:notEqual>
                                                                    </logic:notEmpty>
                                                                </logic:notEqual>
                                                                <logic:equal name="aff_purpose" property="EXPIRES" value="0" scope="page">
                                                                    <logic:equal name="aff_purpose" property="LINK_APPROVED" scope="page" value="Approved">
                                                                        <logic:equal name="aff_purpose" property="PERMISSION" scope="page" value="1">
                                                                            <a href="#" onclick="OpenManageExpDateDialog('<bean:write name="aff" property="ID" scope="page"/>','<bean:write name="aff_purpose" property="PURPOSE_ID" scope="page"/>','<bean:write name="aff_purpose" property="EXPIRES" scope="page"/>','<bean:write name="aff_purpose" property="LINK_EXPIRATION_DATE" scope="page"/>','<bean:write name="aff_purpose" property="SHORT_NAME" scope="page"/>','<bean:write name="aff_purpose" property="PERIOD" scope="page"/>','<bean:write name="aff_purpose" property="UNEXPIRE_PRIVILEGE" scope="page"/>'); return false;">Does not expire</a>
                                                                        </logic:equal>
                                                                        <logic:notEqual name="aff_purpose" property="PERMISSION" scope="page" value="1"> 
                                                                            Does not expire
                                                                        </logic:notEqual>
                                                                    </logic:equal>
                                                                    <logic:notEqual name="aff_purpose" property="LINK_APPROVED" scope="page" value="Approved">
                                                                        -
                                                                    </logic:notEqual>
                                                                </logic:equal>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </logic:iterate>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                            </logic:notEmpty>
                            <tr>
                                <td colspan="2">
                                    <button id="add_purpose_<bean:write name="aff" property="ID" scope="page"/>" onclick="OpenAddDialog('<bean:write name="aff" property="ID" scope="page"/>');return false;">Add Purpose</button>
                                    &nbsp;&nbsp;
                                    <button id="done_btn" onclick="return false;">Done</button>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                </center>
                <%@include file="/WEB-INF/jspf/footer.jsp"%>
                <div id="manage_purpose_dialog" title="Manage Affiliate-Purpose Approval/Expiration" style="display: none; font-size: 12px; text-align: center;">
                    <div style="text-align: left;">
                        For purpose <b class="manage_purpose_dialog_short_name"></b><br><br>
                        Change affiliate approval status. Select status then click the "Change Status" button.
                    </div>
                    Status
                    <select id="manage_purpose_dialog_status_select">
                        <option id="manage_purpose_dialog_status_select_Approved" value="Approved">Approved</option>
                        <option id="manage_purpose_dialog_status_select_Rejected" value="Rejected">Rejected</option>
                        <option id="manage_purpose_dialog_status_select_Submitted" value="Submitted">Submitted</option>
                    </select>
                    <button id="manage_purpose_dialog_change" onclick="return false;">Change Status</button>               
                    <br><br>
                    <button id="manage_purpose_dialog_cancel" onclick="CloseManageDialog();return false;">Cancel</button>
                </div>
                <div id="add_purpose_dialog" title="Add Affiliate Purpose" style="display: none; font-size: 12px; text-align: center;">
                    <br>
                    <div id="add_purpose_div" style="text-align:center;">
                        <logic:empty name="manAffForm" property="purposes" scope="request">
                            This affiliate is currently linked to all active purposes you have authority to manage.
                            <br><br>
                            <button id="add_purpose_dialog_close" onclick="CloseAddDialog();return false;">Close</button>
                        </logic:empty>
                        <logic:notEmpty name="manAffForm" property="purposes" scope="request">
                            You have permission to manage the purposes in the list below.<br><br>
                            Purpose:
                            <select id="dialogAddPurposeId">
                                <option id="undef_purpose"></option>
                                <logic:iterate id="purpose"  name="manAffForm" property="purposes" scope="request">
                                    <bean:define id="permissionFound" scope="page" value="1"/>
                                    <option id="add_purpose_<bean:write name="purpose" property="ID" scope="page"/>" value="<bean:write name="purpose" property="ID" scope="page"/>,<bean:write name="purpose" property="PERIOD" scope="page"/>"><bean:write name="purpose" property="SHORT_NAME" scope="page"/></option>
                                </logic:iterate>
                            </select>
                            &nbsp;&nbsp;
                            <button id="add_purpose_dialog_commit" onclick="CommitAddPurposeDialog();return false;">Submit</button>
                            &nbsp;&nbsp;
                            <button id="add_purpose_dialog_cancel" onclick="CloseAddDialog();return false;">Cancel</button>
                        </logic:notEmpty>
                    </div>
                </div>
                <div id="man_exp_date_dialog" title="Manage Expiration Purpose" style="display: none; font-size: 12px; text-align: center;">
                    <div style="text-align: left;">
                        For purpose <b class="man_exp_dialog_short_name"></b><br><br>
                        Set the affiliate's approval expiration date to today's date
                        plus the number of months you define then click the "Submit" button
                    </div>
                    <input type="text" id="dialogExpDateMonths" maxlength="2" size="2"> Month(s)
                    <span id="man_exp_date_unexpire" style="display: none;"> <b>OR</b> <input type="checkbox" id="man_exp_date_unexpire_checkbox">Never Expires</span>
                    <br><br>
                    <button id="man_exp_date_commit" onclick="CommitManageExpDateDialog();return false;">Submit</button>
                    &nbsp;&nbsp;
                    <button id="man_exp_date_cancel" onclick="$('#man_exp_date_dialog').dialog('close');return false;">Cancel</button>
                </div>
            </html:form>
        </compress:compress>
    </body>
</html>
