<%-- 
    Document   : FindAffEmail
    Created on : Apr 25, 2013, 4:57:24 PM
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
        <script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>
        <link type="text/css" media="all" rel="stylesheet" href="css/jquery-ui-1.10.2.custom.min.css">
        <link type="text/css" media="all" rel="stylesheet" href="css/jquery_datetimepicker.css">
        <script>
            function SubmitForm(){
                //alert($("#email").val());
                var emailPattern = /^[a-zA-Z0-9\._-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z]{2,4}$/;
                if($("#email").val() === ""){
                    alert("Enter an email address.");
                    return false;
                } else if($("#email").val().match("[\\.@]uga\\.edu$")){
                    alert("The email address should not end with \"uga.edu\". The person you entered should already have a UGA user account.");
                    return false;
                } else if($("#email").val() !== $("#email2").val()){
                    alert("The two email addresses don't match.")
                    return false;
                } else if(!emailPattern.test($("#email").val())){
                    alert("The email address entered does not match standard email format.");
                    return false;
                }
                $("#action").val("search");
                $("#findAffForm").submit();
            };
            
            function ResetForm(){
                $("#action").val("reset");
                $("#findAffForm").submit();
            }
            
            function ManageAffiliate(){
                $("#action").val("manage");
                $("#findAffForm").submit();
            }
        </script>
    </head>
    <body>
        <html:form action="FindAff.do" styleId="findAffForm">
            <html:hidden styleId="action" property="action"/>
            <%@include file="/WEB-INF/jspf/header.jsp"%>
            <%@include file="/WEB-INF/jspf/header_login.jsp"%>
            <bean:define id="currEmailAddr" name="findAffForm" property="email" scope="session" toScope="page" type="String"/>
            <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Create New Affiliate</h3>
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
                            <input type="text" id="email2" name="email" size="64" maxlength="128" value="<%=currEmailAddr%>" />
                        </td>
                    </tr>
                </table>
                <button id="submit" onclick="SubmitForm();return false;">Submit</button>
                &nbsp;&nbsp;
                <button id="reset" onclick="ResetForm();return false;">Reset</button>
                <logic:notEmpty name="findAffForm" property="results" scope="session">   
                    <bean:define id="affInfo" name="findAffForm" property="results" scope="session" toScope="page" type="java.util.HashMap"/>
                    <br><br>
                    An affiliate already uses "<bean:write name="findAffForm" property="email" scope="session"/>" (See information below)
                    <br>
                    <button id="manage_aff" onclick="ManageAffiliate();return false;">Manage this affiliate</button>
                    <br>
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
                </logic:notEmpty>
            </div>
            </center>
            <%@include file="/WEB-INF/jspf/footer.jsp"%>
            <div id="error_dialog" title="Unable to submit" style="display: none; font-size: 12px; text-align: center;">
                <div id="errorMsg"></div>
                <input type="button" onclick="$('#error_dialog').dialog('close'); return false;" value="Close">
            </div>
        </html:form>
    </body>
</html>
