<%-- 
    Document   : NewAffReqFind
    Created on : Apr 30, 2013, 3:09:11 PM
    Author     : submyers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.HashMap" %>
<%! HashMap<String, Object> affInfo;
    String targAffId;%>
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
            var currPurpId;
            
            function RequestPurpose(){
                if($("#addPurposeId").val() === ""){
                    alert("You must select a purpose to add.");
                    return false;
                }
                $("#targPurpId").val($("#addPurposeId").val());
                $("#action").val("requestPurpose");
                $("#addAffPurposeReqForm").submit();
            }
            
            $(document).ready(function(){
                $("#backLink").click(function(){
                    $("#action").val("back");
                    $("#addAffPurposeReqForm").submit();
                });
            });
        </script>
    </head>
    <body>
        <html:form action="AddAffPurposeReq.do" styleId="addAffPurposeReqForm">
            <html:hidden styleId="action" property="action"/>
            <html:hidden styleId="targPurpId" property="targPurpId"/>
            <bean:define id="targAffId" name="addAffPurposeReqForm" property="targAffId" scope="request" type="String"/>
            <html:hidden styleId="targAffId" property="targAffId" value="<%=targAffId%>"/>
            <%@include file="/WEB-INF/jspf/header.jsp"%>
            <%@include file="/WEB-INF/jspf/header_login.jsp"%>
            <center id="container">
                <%@include file="/WEB-INF/jspf/messages.jsp"%>

                <h3>Manage Affiliates</h3>
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
                    <logic:empty name="addAffPurposeReqForm" property="personInfo" scope="request">
                        No information found for the affiliate with affiliate id "<bean:write name="addAffPurposeReqForm" property="targAffId" scope="request"/>". Contact superuser for support.
                    </logic:empty>
                    <logic:notEmpty name="addAffPurposeReqForm" property="personInfo" scope="request">   
                        <bean:define id="affInfo" name="addAffPurposeReqForm" property="personInfo" scope="request" toScope="page" type="java.util.HashMap"/>
                        <h2>Affiliate Information</h2>
                        <table>
                            <tr>
                                <td>
                                    <b>Name:</b>
                                </td>
                                <td>
                                    <bean:write name="affInfo" property="LASTNAME" scope="page"/>,
                                    <bean:write name="affInfo" property="FIRSTNAME" scope="page"/>
                                    <bean:write name="affInfo" property="MIDDLENAME" scope="page"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Email:</b>
                                </td>
                                <td>
                                    <bean:write name="affInfo" property="EMAIL" scope="page"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>OVPR ID:</b>
                                </td>
                                <td>
                                    <bean:write name="affInfo" property="OVPRID" scope="page"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>PSEUDOCAN:</b>
                                </td>
                                <td>
                                    <logic:empty name="affInfo" property="PSEUDOCAN" scope="page">
                                        -
                                    </logic:empty>
                                    <bean:write name="affInfo" property="PSEUDOCAN" scope="page"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Title:</b>
                                </td>
                                <td>
                                    <logic:empty name="affInfo" property="TITLE" scope="page">
                                        -
                                    </logic:empty>
                                    <logic:notEmpty name="affInfo" property="TITLE" scope="page">
                                        <bean:write name="affInfo" property="TITLE" scope="page"/>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Organization:</b>
                                </td>
                                <td>
                                    <logic:empty name="affInfo" property="ORGANIZATION" scope="page">
                                        -
                                    </logic:empty>
                                    <logic:notEmpty name="affInfo" property="ORGANIZATION" scope="page">
                                        <bean:write name="affInfo" property="ORGANIZATION" scope="page"/>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Department:</b>
                                </td>
                                <td>
                                    <logic:empty name="affInfo" property="DEPARTMENT" scope="page">
                                        -
                                    </logic:empty>
                                    <logic:notEmpty name="affInfo" property="DEPARTMENT" scope="page">
                                        <bean:write name="affInfo" property="DEPARTMENT" scope="page"/>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr>
                                <td valign="top">
                                    <b>Address:</b>
                                </td>
                                <td>
                                    <bean:write name="affInfo" property="ADDR_1" scope="page"/>
                                    <br>
                                    <logic:notEmpty name="affInfo" property="ADDR_2" scope="page">
                                        <bean:write name="affInfo" property="ADDR_2" scope="page"/>
                                        <br>
                                    </logic:notEmpty>
                                    <bean:write name="affInfo" property="CITY" scope="page"/>,
                                    <bean:write name="affInfo" property="STATE" scope="page"/>
                                    <bean:write name="affInfo" property="ZIP" scope="page"/>
                                    <br>
                                    <bean:write name="affInfo" property="COUNTRY" scope="page"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Phone number:</b>
                                </td>
                                <td>
                                    <bean:write name="affInfo" property="PHONE" scope="page"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Business Phone:</b>
                                </td>
                                <td>
                                    <bean:write name="affInfo" property="BUSINESS_PHONE_NUM" scope="page"/>
                                </td>
                            </tr>
                            <!--tr>
                                <td>
                                    Alt. Phone number
                                </td>
                                <td>
                                    <!bean:write name="affInfo" property="ALT_PHONE_NUM" scope="page"/>
                                </td>
                            </tr-->
                            <tr>
                                <td>
                                    <b>Fax Number:</b>
                                </td>
                                <td>
                                    <bean:write name="affInfo" property="FAX_NUMBER" scope="page"/>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Comments:</b>
                                </td>
                                <td>
                                    <logic:empty name="affInfo" property="COMMENTS" scope="page">
                                        -
                                    </logic:empty>
                                    <logic:notEmpty name="affInfo" property="COMMENTS" scope="page">
                                        <bean:write name="affInfo" property="COMMENTS" scope="page"/>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>FSCODES:</b>
                                </td>
                                <td>
                                    <logic:empty name="affInfo" property="FSCODES" scope="page">
                                        -
                                    </logic:empty>
                                    <logic:notEmpty name="affInfo" property="FSCODES" scope="page">
                                        <bean:write name="affInfo" property="FSCODES" scope="page"/>
                                    </logic:notEmpty>
                                </td>
                            </tr>
                        </table>
                        <br>
                        <logic:empty name="affInfo" property="purposes" scope="page">
                            Not currently linked to any purposes.<br>
                        </logic:empty>
                        <logic:notEmpty name="affInfo" property="purposes" scope="page">
                            <table>
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
                                                            Status
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
                                                <logic:iterate id="aff_purpose" name="affInfo" property="purposes" scope="page">
                                                    <tr>
                                                        <td>
                                                            <div style="width: 240px; overflow: auto;">
                                                                <bean:write name="aff_purpose" property="SHORT_NAME" scope="page"/>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div style="width: 140px; overflow: auto;">
                                                                <logic:equal  name="aff_purpose" property="LINK_APPROVED" scope="page" value="Approved">
                                                                    <span style="color: blue">Approved</span>
                                                                </logic:equal>
                                                                <logic:equal  name="aff_purpose" property="LINK_APPROVED" scope="page" value="Rejected">
                                                                    <span style="color: red">Rejected</span>
                                                                </logic:equal>
                                                                <logic:equal  name="aff_purpose" property="LINK_APPROVED" scope="page" value="Submitted">
                                                                    Submitted
                                                                </logic:equal>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div style="width: 140px; overflow: auto;">
                                                                <logic:empty name="aff_purpose" property="CREATION_DATE" scope="page">
                                                                    -
                                                                </logic:empty>
                                                                <logic:notEmpty name="aff_purpose" property="CREATION_DATE" scope="page">
                                                                    <bean:write name="aff_purpose" property="CREATION_DATE" scope="page"/>
                                                                </logic:notEmpty>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div style="width: 180px; overflow: auto;">
                                                                <logic:empty name="aff_purpose" property="EXPIRATION_DATE" scope="page">
                                                                    <logic:equal name="aff_purpose" property="EXPIRES" value="0" scope="page">
                                                                        Does not expire
                                                                    </logic:equal>
                                                                    <logic:notEqual name="aff_purpose" property="EXPIRES" value="0" scope="page">
                                                                        -
                                                                    </logic:notEqual>
                                                                </logic:empty>
                                                                <logic:notEmpty name="aff_purpose" property="EXPIRATION_DATE" scope="page">
                                                                    <logic:notEqual name="aff_purpose" property="LINK_APPROVED" value="Approved" scope="page">
                                                                        -
                                                                    </logic:notEqual>
                                                                    <logic:equal name="aff_purpose" property="LINK_APPROVED" value="Approved" scope="page">
                                                                        <bean:write name="aff_purpose" property="EXPIRATION_DATE" scope="page"/>
                                                                    </logic:equal>
                                                                </logic:notEmpty>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </logic:iterate>
                                            </table>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </logic:notEmpty>
                        <logic:empty name="affInfo" property="openPurposes" scope="page">
                            <br>This affiliate is currently linked to all active purposes.<br>
                        </logic:empty>
                        <logic:notEmpty name="affInfo" property="openPurposes" scope="page">
                            <br>
                            <b>Request assignment to another purpose:</b>
                            <br>
                            Select a purpose from the list below and then click submit.
                            <br>
                            <bean:define id="affInfo" name="addAffPurposeReqForm" property="personInfo" scope="request" toScope="page" type="java.util.HashMap"/>
                            <select id="addPurposeId">
                                <option id="undef_purpose"></option>
                                <logic:notEmpty name="affInfo" property="openPurposes" scope="page">
                                    <logic:iterate id="purpose"  name="affInfo" property="openPurposes" scope="page">
                                        <option id="<bean:write name="purpose" property="ID" scope="page"/>" value="<bean:write name="purpose" property="ID" scope="page"/>"><bean:write name="purpose" property="SHORT_NAME" scope="page"/></option>
                                    </logic:iterate>
                                </logic:notEmpty>
                            </select>
                            <br>
                            <button id="addPurposeBtn" onclick="RequestPurpose();return false;">Submit</button>
                        </logic:notEmpty>
                    </logic:notEmpty>
                </div>
            </center>
                <%@include file="/WEB-INF/jspf/footer.jsp"%>
            </html:form>
    </body>
</html>
