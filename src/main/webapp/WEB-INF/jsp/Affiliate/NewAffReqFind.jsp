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
    String currEmailAddr;%>
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
            
            function SubmitForm(){
                //alert($("#email").val());
                var emailPattern = /^[a-zA-Z0-9\._-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z]{2,4}$/;
                if($("#email").val() === ""){
                    alert("Enter an email address.");
                    return false;
                } else if($("#email").val().match("[\\.@]uga\\.edu$")){
                    alert("Enter a non-UGA email address.");
                    return false;
                } else if($("#email").val() !== $("#email2").val()){
                    alert("The two email addresses don't match.")
                    return false;
                } else if(!emailPattern.test($("#email").val())){
                    alert("The email address entered does not match standard email format.");
                    return false;
                }
                $("#action").val("search");
                $("#newAffReqFindForm").submit();
            };
            
            function ResetForm(){
                $("#action").val("reset");
                $("#newAffReqFindForm").submit();
            }
            
            function ManageAffiliate(){
                $("#action").val("manage");
                $("#newAffReqFindForm").submit();
            }
            
            function RequestPurpose(){
                if($("#dialogAddPurposeId").val() === ""){
                    alert("You must select a purpose to add.");
                    return false;
                }
                $("#targPurpId").val($("#dialogAddPurposeId").val());
                $("#action").val("requestPurpose");
                $("#newAffReqFindForm").submit();
            }
        </script>
    </head>
    <body>
        <html:form action="NewAffReqFind.do" styleId="newAffReqFindForm">
            <html:hidden styleId="action" property="action"/>
            <html:hidden styleId="targPurpId" property="targPurpId"/>
            <bean:define id="currEmailAddr" name="newAffReqFindForm" property="email" scope="session" toScope="page" type="String"/>
            <%@include file="/WEB-INF/jspf/header.jsp"%>
            <%@include file="/WEB-INF/jspf/header_login.jsp"%>
            <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>New Affiliate Request</h3>
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

                <table>
                    <tr>
                        <td>
                            Enter the new user’s email address: 
                        </td>
                        <td>
                            <input type="text" id="email" name="email" size="64" maxlength="128" value="<%=currEmailAddr%>" />
                        </td>
                    </tr>
                    <tr>
                        <td>Reenter to confirm:</td>
                        <td>
                            <input type="text" id="email2" name="email" size="64" maxlength="128" value="<%=currEmailAddr%>"/>
                        </td>
                    </tr>
                </table>
                <br>
                <button id="submit" onclick="SubmitForm();return false;">Submit</button>
                &nbsp;&nbsp;
                <button id="reset" onclick="ResetForm();return false;">Reset</button>
                <logic:notEmpty name="newAffReqFindForm" property="results" scope="session">   
                    <bean:define id="affInfo" name="newAffReqFindForm" property="results" scope="session" toScope="page" type="java.util.HashMap"/>
                    <br><br>
                    An affiliate already uses "<bean:write name="newAffReqFindForm" property="email" scope="session"/>"
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
                        Not currently linked to any purposes.
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
                                                            <bean:write name="aff_purpose" property="LINK_APPROVED" scope="page" />
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
                        <br>
                        This affiliate account is currently linked to all purposes.
                    </logic:empty>
                    <logic:notEmpty name="affInfo" property="openPurposes" scope="page">
                        <br>
                        To request another purpose, select a purpose from the list below and then click submit.
                        <br>
                        <bean:define id="affInfo" name="newAffReqFindForm" property="results" scope="session" toScope="page" type="java.util.HashMap"/>
                        <select id="dialogAddPurposeId">
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
