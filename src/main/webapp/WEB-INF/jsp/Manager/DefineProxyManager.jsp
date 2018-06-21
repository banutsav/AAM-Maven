<%-- 
    Document   : DefineProxyManager
    Created on : Apr 14, 2013, 9:30:23 PM
    Author     : programmer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
            var targAdminId;
            var targPurposeId;
            
            function OpenRemoveProxy(lastName, firstName, purposeName, adminId, purposeId, cannum) {
                targAdminId = adminId;
                targPurposeId = purposeId;
                
                $("#man_purpose_dialog_first_name").html(firstName);
                $("#man_purpose_dialog_last_name").html(lastName);
                $("#man_purpose_dialog_cannum").html(cannum);
                $("#man_purpose_dialog_purpose_name").html(purposeName);
                
                $("#man_purpose_dialog").dialog("open");
            }
            
            function OpenPurpose(id) {
                $("#purpose_" + id).slideToggle("fast");
            }
            
            function AddProxy(id,shortName) {
                $("#action").val("addManager");
                $("#targPurposeId").val(id);
                $("#targPurposeShortName").val(shortName);
                $("#defineProxyManagerForm").submit();
            }
            
            $(document).ready(function() {
                $("#man_purpose_dialog").dialog({
                    autoOpen: false,
                    width: "auto",
                    modal: true,
                    resizable: false
                });
                $("#man_purpose_dialog_active_yes").click(function(){
                    $("#targAdminId").val(targAdminId);
                    $("#targPurposeId").val(targPurposeId);
                    $("#action").val("removeManager");
                    $("#defineProxyManagerForm").submit();
                });
                $("#man_purpose_dialog_active_no").click(function(){
                    $("#man_purpose_dialog").dialog("close");
                });
                $("#backLink").click(function(){
                    $("#action").val("back");
                    $("#defineProxyManagerForm").submit();
                });
                $("#add_manager").click(function(){
                    $("#action").val("addManager");
                    $("#defineProxyManagerForm").submit();
                });
            });
        </script>
    </head>
    <body>
        <html:form action="DefineProxyManager.do" styleId="defineProxyManagerForm">
            <%@include file="/WEB-INF/jspf/header.jsp"%>
            <%@include file="/WEB-INF/jspf/header_login.jsp"%>
            <html:hidden styleId="action" property="action"/>
            <html:hidden styleId="targAdminId" property="targAdminId"/>
            <html:hidden styleId="targPurposeId" property="targPurposeId"/>
            <html:hidden styleId="targPurposeShortName" property="targPurposeShortName"/>
            <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Manage Purpose Manager Proxies</h3>
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
                <logic:empty name="defineProxyManagerForm" property="purposes" scope="request">
                    You don't have manager access to a purpose.
                </logic:empty>
                <logic:notEmpty name="defineProxyManagerForm" property="purposes" scope="request">
                    <!--button id="add_manager">Add Proxy</button-->
                    Click on a purpose to view, add and/or remove proxy managers.
                    <br><br>
                    <div style="width: 850px; overflow: auto; max-height:625px;">
                        <table>
                            <tr>
                                <td>
                                    <div style="width: 150px; overflow: auto; background-color: #DDDDDD;">
                                        Purpose Short Name
                                    </div>
                                </td>
                                <td>
                                    <div style="width: 450px; overflow: auto; background-color: #DDDDDD;">
                                        Description
                                    </div>
                                </td>
                                <td>
                                    <div style="width: 150px; overflow: auto; background-color: #DDDDDD;">
                                        Expiration Period
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div>
                        <table>
                            <logic:iterate id="purpose" name="defineProxyManagerForm" property="purposes" scope="request">
                                <tr>
                                    <td>
                                        <div style="width: 150px; overflow: auto;">
                                            <span style="color: red; cursor: pointer;" onclick="OpenPurpose('<bean:write name="purpose" property="ID" scope="page"/>');return false;"><bean:write name="purpose" property="SHORT_NAME" scope="page"/></span>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="width: 450px; overflow: auto;">
                                            <bean:write name="purpose" property="DESCRIPTION" scope="page"/>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="width: 250px; overflow: auto;">
                                            <logic:equal name="purpose" property="PERIOD" value="-1">
                                                <b>Does not expire.</b>
                                            </logic:equal>
                                            <logic:notEqual name="purpose" property="PERIOD" value="-1">
                                                <bean:write name="purpose" property="PERIOD" scope="page"/> Month(s)
                                            </logic:notEqual>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="4">
                                        <div id="purpose_<bean:write name="purpose" property="ID" scope="page"/>" style="display: none; background-color: #E0E8F4;">
                                            <b>Managers for purpose "<bean:write name="purpose" property="SHORT_NAME"/>"</b><br>
                                            <logic:empty name="purpose" property="managers" scope="page">
                                                No managers defined for this purpose.
                                            </logic:empty>
                                            <logic:notEmpty name="purpose" property="managers" scope="page">
                                                <div style="width: 850px; overflow: auto; max-height:625px;">
                                                    <table>
                                                        <tr>
                                                            <td>
                                                                <div style="width: 150px; overflow: auto; background-color: #E1B2FF;">
                                                                    Name
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div style="width: 250px; overflow: auto; background-color: #E1B2FF;">
                                                                    MyID
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div style="width: 75px; overflow: auto; background-color: #E1B2FF;">
                                                                    UGA ID
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                                <div style="width: 850px; overflow: auto; max-height:625px;">
                                                    <table>
                                                        <logic:iterate id="manager" name="purpose" property="managers" scope="page">
                                                            <tr>
                                                                <td>
                                                                    <div style="width: 150px; overflow: auto;">
                                                                        <bean:write name="manager" property="LASTNAME" scope="page"/>,
                                                                        <bean:write name="manager" property="FIRSTNAME" scope="page"/>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div style="width: 250px; overflow: auto;">
                                                                        <bean:write name="manager" property="MYID" scope="page"/>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div style="width: 75px; overflow: auto;">
                                                                        <bean:write name="manager" property="CANNUM" scope="page"/>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </logic:iterate>
                                                    </table>
                                                    <br>
                                                </div>
                                            </logic:notEmpty>
                                            <b>Proxies for purpose "<bean:write name="purpose" property="SHORT_NAME"/>"</b><br>
                                            <logic:empty name="purpose" property="managers" scope="page">
                                                No managers defined for this purpose.
                                            </logic:empty>
                                            <logic:notEmpty name="purpose" property="managers" scope="page">
                                                <div style="width: 850px; overflow: auto; max-height:625px;">
                                                    <table>
                                                        <tr>
                                                            <td>
                                                                <div style="width: 150px; overflow: auto; background-color: #E1B2FF;">
                                                                    Name
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div style="width: 250px; overflow: auto; background-color: #E1B2FF;">
                                                                    MyID
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div style="width: 75px; overflow: auto; background-color: #E1B2FF;">
                                                                    UGA ID
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div style="width: 180px; overflow: auto; background-color: #E1B2FF;">
                                                                    Proxy For Manager
                                                                </div>
                                                            </td>
                                                            <td>
                                                                <div style="width: 75px; overflow: auto; background-color: #E1B2FF;">
                                                                    &nbsp;
                                                                </div>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                                <div style="width: 850px; overflow: auto; max-height:625px;">
                                                    <table>
                                                        <logic:iterate id="proxy" name="purpose" property="proxies" scope="page">
                                                            <tr>
                                                                <td>
                                                                    <div style="width: 150px; overflow: auto;">
                                                                        <bean:write name="proxy" property="LASTNAME" scope="page"/>,
                                                                        <bean:write name="proxy" property="FIRSTNAME" scope="page"/>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div style="width: 250px; overflow: auto;">
                                                                        <bean:write name="proxy" property="MYID" scope="page"/>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div style="width: 75px; overflow: auto;">
                                                                        <bean:write name="proxy" property="CANNUM" scope="page"/>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div style="width: 180px; overflow: auto;">
                                                                        <logic:notEqual name="proxy" property="PROXY" value="1" scope="page">
                                                                            -
                                                                        </logic:notEqual>
                                                                        <logic:equal name="proxy" property="PROXY" value="1" scope="page">
                                                                            <bean:write name="proxy" property="MANAGER_LASTNAME" scope="page"/>,
                                                                            <bean:write name="proxy" property="MANAGER_FIRSTNAME" scope="page"/>
                                                                            (<bean:write name="proxy" property="MANAGER_CANNUM" scope="page"/>)
                                                                        </logic:equal>
                                                                    </div>
                                                                </td>
                                                                <td>
                                                                    <div style="width: 75px; overflow: auto;">
                                                                        <logic:equal name="proxy" property="MANAGE_PERMISSION" value="1" scope="page">
                                                                            <span style="color: red; cursor: pointer;" onclick="OpenRemoveProxy('<bean:write name="proxy" property="LASTNAME" scope="page"/>','<bean:write name="proxy" property="FIRSTNAME" scope="page"/>','<bean:write name="purpose" property="SHORT_NAME" scope="page"/>','<bean:write name="proxy" property="ID" scope="page"/>','<bean:write name="purpose" property="ID" scope="page"/>','<bean:write name="proxy" property="CANNUM" scope="page"/>');">Remove</span>
                                                                        </logic:equal>
                                                                        <logic:notEqual name="proxy" property="MANAGE_PERMISSION" value="1" scope="page">
                                                                            -
                                                                        </logic:notEqual>
                                                                    </div>
                                                                </td>
                                                            </tr>
                                                        </logic:iterate>
                                                    </table>
                                                </div>
                                                <button id="add_manager_<bean:write name="purpose" property="ID" scope="page"/>" onclick="AddProxy('<bean:write name="purpose" property="ID" scope="page"/>','<bean:write name="purpose" property="SHORT_NAME" scope="page"/>');return false;">Add Proxy</button>
                                            </logic:notEmpty>
                                        </div>
                                    </td>
                                </tr>
                            </logic:iterate>
                        </table>
                    </div>
                </logic:notEmpty>
            </div>
            </center>
            <%@include file="/WEB-INF/jspf/footer.jsp"%>
            <div id="man_purpose_dialog" title="Remove Proxy-Manager" style="display: none; font-size: 12px; text-align: center;">
                <div style="text-align: left;">
                    Do you want to remove proxy manager
                    <br>
                    &nbsp;&nbsp;<b><span id="man_purpose_dialog_first_name"></span>, <span id="man_purpose_dialog_last_name"></span> (<span id="man_purpose_dialog_cannum"></span>)</b>
                    <br>
                    from purpose
                    <br>
                    &nbsp;&nbsp;<b><span id="man_purpose_dialog_purpose_name"></span></b>?
                    <br><br>
                    <button id="man_purpose_dialog_active_yes">Confirm</button>&nbsp;&nbsp;&nbsp;<button id="man_purpose_dialog_active_no">Cancel</button>
                    <br>
                </div>
            </div>
            
        </html:form>
    </body>
</html>

