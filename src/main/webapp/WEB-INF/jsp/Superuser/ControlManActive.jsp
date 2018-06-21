<%-- 
    Document   : ControlManActive
    Created on : May 16, 2013, 10:55:41 AM
    Author     : submyers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="/WEB-INF/tags-compress" prefix="compress" %>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String adminId, cannum;%>
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
            function OpenManagePurpose(targPurpId,short_name){
                $(".purp_name_manage_purp_dialog").html(short_name);
                $("#targPurpId").val(targPurpId);
                $("#manage_purp_dialog").dialog("open");
            }
            
            function OpenDeactivateProxy(targProxyId,targPurpId,firstName,lastName,purposeName){
                $("#proxy_name_manage_proxy_dialog").html(firstName + " " + lastName);
                $("#purpose_name_manage_proxy_dialog").html(purposeName);
                $("#targProxyId").val(targProxyId);
                $("#targPurpId").val(targPurpId);
                $("#manage_proxy_dialog").dialog("open");
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
                
                $("#manage_proxy_dialog").dialog({
                    autoOpen: false,
                    width: "auto",
                    modal: true,
                    resizable: false
                });
                
                $("#add_purp_dialog").dialog({
                    autoOpen: false,
                    width: "auto",
                    modal: true,
                    resizable: false
                });
                
                $("#deactivate_btn").click(function(){
                    $("#deactivate_dialog").dialog("open");
                });
                
                $("#deactivate_dialog_cancel").click(function(){
                    $("#deactivate_dialog").dialog("close");
                });
                
                $("#deactivate_dialog_submit").click(function(){
                    $("#action").val("deactivate");
                    $("#controlManActiveForm").submit();
                });
                
                $("#submit_manage_purp_dialog").click(function(){
                    $("#action").val("rmPurpose");
                    $("#controlManActiveForm").submit();
                })
                
                $("#cancel_manage_purp_dialog").click(function(){
                    $("#manage_purp_dialog").dialog("close");
                })
                
                $("#submit_add_purp_dialog").click(function(){
                    if($("#purp_id_add_purp_dialog").val() === ""){
                        alert("You must select a purpose.");
                        return false;
                    }
                    $("#targPurpId").val($("#purp_id_add_purp_dialog").val());
                    $("#action").val("addPurpose");
                    $("#controlManActiveForm").submit();
                })
                
                $(".cancel_add_purp_dialog").click(function(){
                    $("#add_purp_dialog").dialog("close");
                })
                
                $("#backLink").click(function(){
                    $("#action").val("back");
                    $("#controlManActiveForm").submit();
                });
                
                $("#add_purpose").click(function(){
                    $("#add_purp_dialog").dialog("open");
                });
                
                $("#submit_manage_proxy_dialog").click(function(){
                    $("#action").val("rmProxy");
                    $("#controlManActiveForm").submit();
                });
                
                $("#cancel_manage_proxy_dialog").click(function(){
                    $("#manage_proxy_dialog").dialog("close");
                });
                
                $("#add_proxy_btn").click(function(){
                    $("#action").val("addProxy");
                    $("#controlManActiveForm").submit();
                });
                
                $("#activate_btn").click(function(){
                    $("#action").val("activate");
                    $("#controlManActiveForm").submit();
                });
            });
        </script>
    </head>
    <body>
        <compress:compress>
            <html:form action="ControlManActive.do" styleId="controlManActiveForm">
                <%@include file="/WEB-INF/jspf/header.jsp"%>
                <%@include file="/WEB-INF/jspf/header_login.jsp"%>
                <html:hidden styleId="action" property="action"/>
                <bean:define id="adminId" name="controlManActiveForm" property="adminId" scope="request" toScope="page" type="String"/>
                <html:hidden styleId="adminId" property="adminId" value="<%= adminId%>"/>
                <bean:define id="cannum" name="controlManActiveForm" property="cannum" scope="request" toScope="page" type="String"/>
                <html:hidden styleId="adminId" property="cannum" value="<%= cannum%>"/>
                <html:hidden styleId="targPurpId" property="targPurpId"/>
                <html:hidden styleId="targProxyId" property="targProxyId"/>

                <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Manage Affiliate Managers</h3>
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
                    <h5>Manager Information:</h5>
                    <table>
                        <tr>
                            <td><b>Name:</b></td>
                            <td><bean:write name="controlManActiveForm" property="lastName" scope="request"/>, <bean:write name="controlManActiveForm" property="firstName" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>Active Manager:</b></td>
                            <td>
                                <logic:equal name="controlManActiveForm" property="adminActive" scope="request" value="1">
                                    Yes
                                </logic:equal>
                                <logic:equal name="controlManActiveForm" property="adminActive" scope="request" value="0">
                                    <b style="color: red;">No</b>
                                </logic:equal>
                            </td>
                        </tr>
                        <tr>
                            <td><b>MyID:</b></td>
                            <td><bean:write name="controlManActiveForm" property="myId" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>UGAID:</b></td>
                            <td><bean:write name="controlManActiveForm" property="cannum" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>Title:</b></td>
                            <td><bean:write name="controlManActiveForm" property="title" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>Email:</b></td>
                            <td><bean:write name="controlManActiveForm" property="email" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>Phone:</b></td>
                            <td><bean:write name="controlManActiveForm" property="phone" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>Department:</b></td>
                            <td><bean:write name="controlManActiveForm" property="deptNum" scope="request"/> / <bean:write name="controlManActiveForm" property="deptName" scope="request"/></td>
                        </tr>
                    </table>
                    <br>
                    <logic:equal name="controlManActiveForm" property="adminActive" scope="request" value="1">
                        <button id="deactivate_btn" onclick="return false;">Deactivate Manager</button>
                    </logic:equal>
                    <logic:equal name="controlManActiveForm" property="adminActive" scope="request" value="0">
                        <button id="activate_btn" onclick="return false;">Activate Manager</button>
                    </logic:equal>
                    <br><br>
                    <b>Manager for purposes:</b>
                    <logic:empty name="controlManActiveForm" property="purposes" scope="request">
                        <br>Not currently defined as a manager for any purpose.<br>
                    </logic:empty>
                    <logic:notEmpty name="controlManActiveForm" property="purposes" scope="request">
                        <div style="position: relative; max-width: 900px; overflow: auto;">
                            <table class="paulaQueryTable">
                                <thead>
                                    <tr>
                                        <th>Short Name</th>
                                        <th>Description</th>
                                        <th>Approval Status</th>
                                        <th>Period (Months)</th>
                                        <th>Purpose Manager Count</th>
                                        <th>Creation Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <logic:iterate id="purpose" name="controlManActiveForm" property="purposes" scope="request">
                                        <tr>
                                            <td><a href="#" onclick="OpenManagePurpose('<bean:write name="purpose" property="PURPOSE_ID" scope="page"/>','<bean:write name="purpose" property="SHORT_NAME" scope="page"/>');return false;"><bean:write name="purpose" property="SHORT_NAME" scope="page"/></a></td>
                                            <td><bean:write name="purpose" property="DESCRIPTION" scope="page"/></td>
                                            <td><bean:write name="purpose" property="APPROVED" scope="page"/></td>
                                            <td>
                                                <logic:equal name="purpose" property="PERIOD" scope="page" value="-1">
                                                    <b>Does not expire</b>
                                                </logic:equal>
                                                <logic:notEqual name="purpose" property="PERIOD" scope="page" value="-1">
                                                    <bean:write name="purpose" property="PERIOD" scope="page"/>
                                                </logic:notEqual>
                                            </td>
                                            <td><bean:write name="purpose" property="ADMIN_COUNT" scope="page"/></td>
                                            <td><bean:write name="purpose" property="CREATION_DATE" scope="page"/></td>
                                        </tr>
                                    </logic:iterate>
                                </tbody>
                            </table>
                        </div>
                    </logic:notEmpty>
                    <logic:equal name="controlManActiveForm" property="adminActive" scope="request" value="1">
                        <button id="add_purpose" onclick="return false;">Add Purpose</button>
                    </logic:equal>
                    <br><br>
                    <logic:notEmpty name="controlManActiveForm" property="purposes" scope="request">
                        <b>Manager Proxies:</b>
                        <logic:empty name="controlManActiveForm" property="proxies" scope="request">
                            <br>No proxy managers.<br>
                        </logic:empty>
                        <logic:notEmpty name="controlManActiveForm" property="proxies" scope="request">
                            <div style="position: relative; max-width: 900px; overflow: auto;">
                                <table class="paulaQueryTable">
                                    <thead>
                                        <tr>
                                            <th>Name</th>
                                            <th>Purpose Short Name</th>
                                            <th>MyID</th>
                                            <th>UGAID</th>
                                            <th>Title</th>
                                            <th>Email</th>
                                            <th>Phone</th>
                                            <th>Department</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <logic:iterate id="proxy" name="controlManActiveForm" property="proxies" scope="request">
                                            <tr>
                                                <td><a href="#" onclick="OpenDeactivateProxy('<bean:write name="proxy" property="ID" scope="page"/>','<bean:write name="proxy" property="PURPOSE_ID" scope="page"/>','<bean:write name="proxy" property="FIRSTNAME" scope="page"/>','<bean:write name="proxy" property="LASTNAME" scope="page"/>','<bean:write name="proxy" property="SHORT_NAME" scope="page"/>');return false;"><bean:write name="proxy" property="LASTNAME" scope="page"/>, <bean:write name="proxy" property="FIRSTNAME" scope="page"/></a></td>
                                                <td><bean:write name="proxy" property="SHORT_NAME" scope="page"/></td>
                                                <td><bean:write name="proxy" property="MYID" scope="page"/></td>
                                                <td><bean:write name="proxy" property="CANNUM" scope="page"/></td>
                                                <td><bean:write name="proxy" property="TITLE" scope="page"/></td>
                                                <td><bean:write name="proxy" property="EMAIL" scope="page"/></td>
                                                <td><bean:write name="proxy" property="PHONE" scope="page"/></td>
                                                <td><bean:write name="proxy" property="DEPTNUM" scope="page"/> / <bean:write name="proxy" property="DEPTNAME" scope="page"/></td>
                                            </tr>
                                        </logic:iterate>
                                    </tbody>
                                </table>
                            </div>
                        </logic:notEmpty>
                        <button id="add_proxy_btn" onclick="return false;">Add Proxy</button>                    
                    </logic:notEmpty>
                </div>
                </center>
                <%@include file="/WEB-INF/jspf/footer.jsp"%>

                <div id="deactivate_dialog" title="Deactivate Affiliate" style="display: none; font-size: 12px; text-align: left;">
                    Click submit to deactivate the manager
                    <div style="position: relative; width: 100%; text-align: center;">
                        <b><bean:write name="controlManActiveForm" property="firstName" scope="request"/> <bean:write name="controlManActiveForm" property="lastName" scope="request"/></b>
                    </div>
                    in table paul.AFFILIATE_ADMIN.
                    <br>
                    This will also deactivate <b>all</b> linked manager proxies
                    <br>
                    in table paul.AFFILIATE_PURPOSE_LINK
                    <br>
                    <div style="position: relative; width: 100%; text-align: center;">
                        <button id="deactivate_dialog_submit">Submit</button>
                        &nbsp;&nbsp;&nbsp;
                        <button id="deactivate_dialog_cancel">Cancel</button>
                    </div>
                </div>

                <div id="manage_purp_dialog" title="Remove Purpose Management Authority" style="display: none; font-size: 12px; text-align: left;">
                    Click submit to remove manager permission from
                    <div style="position:relative;width:100%;text-align: center;">
                        <b><bean:write name="controlManActiveForm" property="firstName" scope="request"/> <bean:write name="controlManActiveForm" property="lastName" scope="request"/></b>
                    on purpose
                        <b class="purp_name_manage_purp_dialog"></b>
                    </div>
                    (<b>NOTE:</b> Click submit would cause all manager proxies for <br>
                    <b><bean:write name="controlManActiveForm" property="firstName" scope="request"/> <bean:write name="controlManActiveForm" property="lastName" scope="request"/></b>
                    on purpose <b class="purp_name_manage_purp_dialog"></b>
                    to lose manager permission.)<br>
                    <br>
                    <div style="position:relative;width:100%;text-align: center;">
                        <button id="submit_manage_purp_dialog">Submit</button>
                        &nbsp;&nbsp;&nbsp;
                        <button id="cancel_manage_purp_dialog">Cancel</button>
                    </div>
                </div>

                <div id="add_purp_dialog" title="Add Purpose" style="display: none; font-size: 12px; text-align: left;">
                    <logic:empty name="controlManActiveForm" property="otherPurposes" scope="request">
                        Currently linked to all purposes.
                        <button class="cancel_add_purp_dialog">Cancel</button>
                    </logic:empty>
                    <logic:notEmpty name="controlManActiveForm" property="otherPurposes" scope="request">
                        Select one purpose from the
                        <br>
                        list below then click submit
                        <br><br>
                        <select id="purp_id_add_purp_dialog">
                            <option value=""></option>
                            <logic:iterate id="purpose" name="controlManActiveForm" property="otherPurposes" scope="request">
                                <option value="<bean:write name="purpose" property="ID" scope="page"/>"><bean:write name="purpose" property="SHORT_NAME" scope="page"/></option>
                            </logic:iterate>
                        </select>
                        <br><br>
                        <div style="position:relative;width:100%;text-align: center;">
                            <button id="submit_add_purp_dialog">Submit</button>
                            &nbsp;&nbsp;&nbsp;
                            <button class="cancel_add_purp_dialog">Cancel</button>
                        </div>
                    </logic:notEmpty>
                </div>

                <div id="manage_proxy_dialog" title="Remove Proxy" style="display: none; font-size: 12px; text-align: left;">
                    Click submit to remove the rights for
                    <div style="text-align: center;">
                        <b id="proxy_name_manage_proxy_dialog"></b>
                    </div>
                    to serve as a proxy manager for
                    <div style="position:relative;width:100%;text-align: center;">
                        <b><bean:write name="controlManActiveForm" property="firstName" scope="request"/> <bean:write name="controlManActiveForm" property="lastName" scope="request"/></b>
                    on purpose
                        <b id="purpose_name_manage_proxy_dialog"></b>
                    </div>
                    <br>
                    <div style="position:relative;width:100%;text-align: center;">
                        <button id="submit_manage_proxy_dialog">Submit</button>
                        &nbsp;&nbsp;&nbsp;
                        <button id="cancel_manage_proxy_dialog">Cancel</button>
                    </div>
                </div>
            </html:form>
        </compress:compress>
    </body>
</html>
