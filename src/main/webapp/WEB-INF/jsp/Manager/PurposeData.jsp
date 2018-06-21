<%-- 
    Document   : PurposeData
    Created on : Jun 5, 2013, 9:19:00 AM
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
            function OpenManAff(targAffId){
                $("#targAffId").val(targAffId);
                $("#action").val("manAff");
                $("#purposeDataForm").submit();
            }
            
            $(document).ready(function(){
                $("#backLink").click(function(){
                    $("#action").val("back");
                    $("#purposeDataForm").submit();
                });
                
                $("#doneBtn").click(function(){
                    $("#action").val("back");
                    $("#purposeDataForm").submit();
                });
                
                $("#updatePurposeBtn").click(function(){
                    $("#action").val("updatePurpose");
                    $("#purposeDataForm").submit();
                });
            });
        </script>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jsp"%>
        <%@include file="/WEB-INF/jspf/header_login.jsp"%>
        <html:form styleId="purposeDataForm" action="PurposeData.do">
            <html:hidden styleId="action" property="action"/>
            <html:hidden styleId="targAffId" property="targAffId"/>
            <bean:define id="purposeId" name="purposeDataForm" property="purposeId" scope="request" toScope="page" type="String"/>
            <html:hidden styleId="purposeId" property="purposeId" value="<%= purposeId%>"/>
            <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Purpose Data</h3>
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
                <bean:define id="purposeData" name="purposeDataForm" property="purposeData" scope="request" toScope="page"/>
                <table>
                    <tr>
                        <td><b>Short Name:</b></td>
                        <td><bean:write name="purposeData" property="SHORT_NAME" scope="page"/></td>
                    </tr>
                    <tr>
                        <td><b>Description:</b></td>
                        <td><bean:write name="purposeData" property="DESCRIPTION" scope="page"/></td>
                    </tr>
                    <tr>
                        <td><b>Approval Status:</b></td>
                        <td><bean:write name="purposeData" property="APPROVED" scope="page"/></td>
                    </tr>
                    <tr>
                        <td><b>Period (Months:)</b></td>
                        <td>
                            <logic:equal name="purposeData" property="PERIOD" scope="page" value="-1">
                                Does not expire
                            </logic:equal>
                            <logic:notEqual name="purposeData" property="PERIOD" scope="page" value="-1">
                                <bean:write name="purposeData" property="PERIOD" scope="page"/>
                            </logic:notEqual>
                        </td>
                    </tr>
                    <tr>
                        <td><b>Creation Date:</b></td>
                        <td><bean:write name="purposeData" property="CREATION_DATE" scope="page"/></td>
                    </tr>
                    <tr>
                        <td style="padding-top: 5px; vertical-align: top;">
                            <b>Created By:</b>
                        </td>
                        <td>
                            <table>
                                <tr>
                                    <td>
                                        <bean:write name="purposeData" property="LASTNAME" scope="page"/>,
                                        <bean:write name="purposeData" property="FIRSTNAME" scope="page"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <bean:write name="purposeData" property="MYID" scope="page"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <bean:write name="purposeData" property="CANNUM" scope="page"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
                <button id="updatePurposeBtn" onclick="return false;">Edit Purpose</button>
                &nbsp;&nbsp;
                <button id="doneBtn" onclick="return false;">Done</button>
                <br><br>
                <b>Purpose Managers:</b>
                <div class="tableContainer">
                    <table class="paulaQueryTable">
                        <thead>
                            <tr>
                                <th>
                                    Name
                                </th>
                                <th>
                                    MyID
                                </th>
                                <th>
                                    UGA ID
                                </th>
                                <th>
                                    Department
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <logic:iterate id="manager" name="purposeDataForm" property="purposeManagers" scope="request">
                                <tr>
                                    <td>
                                        <bean:write name="manager" property="LASTNAME" scope="page"/>,
                                        <bean:write name="manager" property="FIRSTNAME" scope="page"/>
                                    </td>
                                    <td>
                                        <bean:write name="manager" property="MYID" scope="page"/>
                                    </td>
                                    <td>
                                        <bean:write name="manager" property="CANNUM" scope="page"/>
                                    </td>
                                    <td>
                                        <bean:write name="manager" property="DEPTNUM" scope="page"/> / 
                                        <bean:write name="manager" property="DEPTNAME" scope="page"/>
                                    </td>
                                </tr>
                            </logic:iterate>
                        </tbody>
                    </table>
                    <br><br>
                    <b>Affiliates:</b>
                    <div class="tableContainer">
                        <table class="paulaQueryTable">
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>OVPR ID</th>
                                    <th>EMAIL</th>
                                    <th>APPROVAL STATUS</th>
                                    <th>EXPIRATION DATE</th>
                                    <th>Excluded</th>
                                </tr>
                            </thead>
                            <tbody>
                                <logic:iterate id="aff" name="purposeDataForm" property="purposeAff" scope="request">
                                    <tr>
                                        <td>
                                            <a id="manAff" href="#" onclick="OpenManAff('<bean:write name="aff" property="ID" scope="page"/>');return false;">
                                                <bean:write name="aff" property="LASTNAME" scope="page"/>,
                                                <bean:write name="aff" property="FIRSTNAME" scope="page"/>
                                            </a>
                                        </td>
                                        <td>
                                            <bean:write name="aff" property="OVPRID" scope="page"/>
                                        </td>
                                        <td><bean:write name="aff" property="EMAIL" scope="page"/></td>
                                        <td><bean:write name="aff" property="APPROVED" scope="page"/></td>
                                        <td>
                                            <logic:equal name="aff" property="EXPIRES" scope="page" value="0">
                                                <b>Does not expire</b>
                                            </logic:equal>
                                            <logic:equal name="aff" property="EXPIRES" scope="page" value="1">
                                                <bean:write name="aff" property="EXPIRATION_DATE" scope="page"/>
                                            </logic:equal>
                                        </td>
                                        <td>
                                            <logic:equal name="aff" property="ACTIVE" scope="page" value="0">
                                                <b style="color: red;">Yes</b>
                                            </logic:equal>
                                            <logic:equal name="aff" property="ACTIVE" scope="page" value="1">
                                                -
                                            </logic:equal>
                                        </td>
                                    </tr>
                                </logic:iterate>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            </center>
        </html:form>
        <%@include file="/WEB-INF/jspf/footer.jsp"%>
    </body>
</html>
