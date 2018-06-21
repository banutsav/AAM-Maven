<%-- 
    Document   : ManagePurpose
    Created on : Feb 22, 2013, 3:28:36 PM
    Author     : submyers
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
        <script type="text/javascript">

            function ManagePurpose(purposeId) {
                $("#purposeId").val(purposeId);
                $("#action").val("managePurpose");
                $("#manPurpose").submit();
            }

            function ManagePurposeData(purposeId) {
                $("#purposeId").val(purposeId);
                $("#action").val("purposeData");
                $("#manPurpose").submit();
            }

            $(document).ready(function() {

                $(".incApprovedBox").click(function(){
                    if($(".incApprovedBox").is(":checked")){
                        $(".Approved").css("display","");
                    } else {
                        $(".Approved").css("display","none");
                    }
                });

                $(".incSubmittedBox").click(function(){
                    if($(".incSubmittedBox").is(":checked")){
                        $(".Submitted").css("display","");
                    } else {
                        $(".Submitted").css("display","none");
                    }
                });

                $(".incRejectedBox").click(function(){
                    if($(".incRejectedBox").is(":checked")){
                        $(".Rejected").css("display","");
                    } else {
                        $(".Rejected").css("display","none");
                    }
                });

                $("#backLink").click(function() {
                    $("#action").val("backLink");
                    $("#manPurpose").submit();
                });

                $("#reqNewPurpose").click(function() {
                    $("#action").val("reqNewPurpose");
                    $("#manPurpose").submit();
                });
            });
        </script>
    </head>
    <body>
        <html:form styleId="manPurpose" action="ManPurpose.do">
            <%@include file="/WEB-INF/jspf/header.jsp"%>
            <%@include file="/WEB-INF/jspf/header_login.jsp"%>
            <html:hidden styleId="action" property="action"/>
            <html:hidden styleId="purposeActive" property="purposeActive"/>
            <html:hidden styleId="purposeId" property="purposeId"/>

            <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>View/Manage Affiliation Purposes</h3>
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
                <button id="reqNewPurpose" onclick="return false;">Request New Purpose</button>
                <br><br>
                <b>Show:</b>
                <input type="checkbox" class="incSubmittedBox" checked> Submitted,
                <input type="checkbox" class="incApprovedBox" checked> Approved,
                <input type="checkbox" class="incRejectedBox" checked> Rejected

                <br><br>
                <logic:empty name="manPurposeForm" property="purposes" scope="request">
                    <logic:empty name="manPurposeForm" property="managerPurposes" scope="request">
                        No purposes defined.
                    </logic:empty>
                </logic:empty>
                <logic:notEmpty name="manPurposeForm" property="managerPurposes" scope="request">
                    <b>Purposes you manage</b>
                    <div style="width: 850px; overflow: auto; max-height:625px;">
                        <table>
                            <tr>
                                <td>
                                    <div style="width: 100px; overflow: auto; background-color: #DDDDDD;">
                                        Short Name
                                    </div>
                                </td>
                                <td>
                                    <div style="width: 280px; overflow: auto; background-color: #DDDDDD;">
                                        Purpose
                                    </div>
                                </td>
                                <td>
                                    <div style="width: 140px; overflow: auto; background-color: #DDDDDD;">
                                        Approval Status
                                    </div>
                                </td>
                                <td>
                                    <div style="width: 140px; overflow: auto; background-color: #DDDDDD;">
                                        Submission Date
                                    </div>
                                </td>
                                <td>
                                    <div style="width: 160px; overflow: auto; background-color: #DDDDDD;">
                                        Expiration Period
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div style="width: 850px; overflow: auto; max-height:625px;">
                        <table>
                            <logic:iterate id="purpose" name="manPurposeForm" property="managerPurposes" scope="request">
                                <tr class="<bean:write name="purpose" property="APPROVED" scope="page"/>">
                                    <td>
                                        <div style="width: 100px; overflow: auto;">
                                            <logic:equal name="purpose" property="APPROVED" value="Approved">
                                                <logic:equal name="purpose" property="PERMISSION" scope="page" value="1">
                                                    <a href="#" id="manage" onclick="ManagePurposeData('<bean:write name="purpose" property="ID" scope="page"/>'); return false;"><bean:write name="purpose" property="SHORT_NAME" scope="page"/></a>
                                                </logic:equal>
                                                <logic:equal name="purpose" property="PERMISSION" scope="page" value="0">
                                                    <bean:write name="purpose" property="SHORT_NAME" scope="page"/>
                                                </logic:equal>
                                            </logic:equal>
                                            <logic:notEqual name="purpose" property="APPROVED" value="Approved">
                                                <bean:write name="purpose" property="SHORT_NAME" scope="page"/>
                                            </logic:notEqual>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="width: 280px; overflow: auto;">
                                            <bean:write name="purpose" property="DESCRIPTION" scope="page"/>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="width: 140px; overflow: auto;">
                                            <bean:write name="purpose" property="APPROVED" scope="page"/>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="width: 140px; overflow: auto;">
                                            <logic:empty name="purpose" property="CREATION_DATE" scope="page">
                                                -
                                            </logic:empty>
                                            <logic:notEmpty name="purpose" property="CREATION_DATE" scope="page">
                                                <bean:write name="purpose" property="CREATION_DATE" scope="page"/>
                                            </logic:notEmpty>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="width: 160px; overflow: auto;">
                                            <logic:equal name="purpose" property="PERIOD" value="-1">
                                                <b>Does not expire.</b>
                                            </logic:equal>
                                            <logic:notEqual name="purpose" property="PERIOD" value="-1">
                                                <bean:write name="purpose" property="PERIOD" scope="page"/> Month(s)
                                            </logic:notEqual>
                                        </div>
                                    </td>
                                </tr>
                            </logic:iterate>
                        </table>                        
                    </div>
                </logic:notEmpty>
                <logic:notEmpty name="manPurposeForm" property="purposes" scope="request">
                    <br><br>
                    <b>Purposes you don't manage</b>
                    <div style="width: 850px; overflow: auto; max-height:625px;">
                        <table>
                            <tr>
                                <td>
                                    <div style="width: 100px; overflow: auto; background-color: #DDDDDD;">
                                        Short Name
                                    </div>
                                </td>
                                <td>
                                    <div style="width: 280px; overflow: auto; background-color: #DDDDDD;">
                                        Purpose
                                    </div>
                                </td>
                                <td>
                                    <div style="width: 140px; overflow: auto; background-color: #DDDDDD;">
                                        Approval Status
                                    </div>
                                </td>
                                <td>
                                    <div style="width: 140px; overflow: auto; background-color: #DDDDDD;">
                                        Submission Date
                                    </div>
                                </td>
                                <td>
                                    <div style="width: 160px; overflow: auto; background-color: #DDDDDD;">
                                        Expiration Period
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div style="width: 850px; overflow: auto; max-height:625px;">
                        <table>
                            <logic:iterate id="purpose" name="manPurposeForm" property="purposes" scope="request">
                                <tr class="<bean:write name="purpose" property="APPROVED" scope="page"/>">
                                    <td>
                                        <div style="width: 100px; overflow: auto;">
                                            <bean:write name="purpose" property="SHORT_NAME" scope="page"/>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="width: 280px; overflow: auto;">
                                            <bean:write name="purpose" property="DESCRIPTION" scope="page"/>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="width: 140px; overflow: auto;">
                                            <bean:write name="purpose" property="APPROVED" scope="page"/>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="width: 140px; overflow: auto;">
                                            <logic:empty name="purpose" property="CREATION_DATE" scope="page">
                                                -
                                            </logic:empty>
                                            <logic:notEmpty name="purpose" property="CREATION_DATE" scope="page">
                                                <bean:write name="purpose" property="CREATION_DATE" scope="page"/>
                                            </logic:notEmpty>
                                        </div>
                                    </td>
                                    <td>
                                        <div style="width: 160px; overflow: auto;">
                                            <logic:equal name="purpose" property="PERIOD" value="-1">
                                                <b>Does not expire.</b>
                                            </logic:equal>
                                            <logic:notEqual name="purpose" property="PERIOD" value="-1">
                                                <bean:write name="purpose" property="PERIOD" scope="page"/> Month(s)
                                            </logic:notEqual>
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
        </html:form>
    </body>
</html>
