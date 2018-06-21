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
        <script type="text/css" src="/css/ORSAffiliateAccount.css"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#backLink").click(function(){
                    $("#action").val("backLink");
                    $("#manageUsers").submit();
                });
                
                $("#submitBtn").click(function(){
                    $("#action").val("submit");
                    $("#manageUsers").submit();
                });
                
            });
            
            function ToggleRow(rowId){
                $(rowId).slideToggle("slow");
            }
        </script>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jsp"%>
        <%@include file="/WEB-INF/jspf/header_login.jsp"%>
        <html:form styleId="manageUsers" action="ManageUsers.do">
            <html:hidden styleId="action" property="action"/>
            <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Manage Users</h3>
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
                <b>Current Users:</b><br>
                (Click user's name for assigned purpose information)<br>
                <logic:empty name="manageUsersForm" property="currUsers" scope="request">
                    <br>No current Users<br>
                </logic:empty>
                <logic:notEmpty name="manageUsersForm" property="currUsers" scope="request">
                    <br>
                    <div class="userPurpose">
                        <table>
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>MyID</th>
                                    <th>UGAID</th>
                                    <th>Email</th>
                                    <th>Phone</th>
                                </tr>
                            </thead>
                            <tbody>
                                <logic:iterate id="user" name="manageUsersForm" property="currUsers" scope="request">
                                    <tr>
                                        <td><a href="#" onclick="$('#user_purpose_<bean:write name="user" property="CANNUM" scope="page"/>').slideToggle('slow'); return false;"><bean:write name="user" property="LASTNAME" scope="page"/>, <bean:write name="user" property="FIRSTNAME" scope="page"/> <bean:write name="user" property="MIDDLENAME" scope="page"/></a></td>
                                        <td><bean:write name="user" property="MYID" scope="page"/></td>
                                        <td><bean:write name="user" property="CANNUM" scope="page"/></td>
                                        <td><bean:write name="user" property="EMAIL" scope="page"/></td>
                                        <td><bean:write name="user" property="PHONE" scope="page"/></td>
                                    </tr>
                                    <tr id="user_purpose_<bean:write name="user" property="CANNUM" scope="page"/>" style="display: none; background-color: white;">
                                        <logic:empty name="user" property="PURPOSES" scope="page">
                                            <td colspan="5">
                                                No purpose defined.
                                            </td>
                                        </logic:empty>
                                        <logic:notEmpty name="user" property="PURPOSES" scope="page">
                                            <td colspan="5">
                                                <bean:write name="user" property="LASTNAME" scope="page"/>, <bean:write name="user" property="FIRSTNAME" scope="page"/> <bean:write name="user" property="MIDDLENAME" scope="page"/> has access to the following purposes.<br>
                                                <table>
                                                    <thead>
                                                        <tr>
                                                            <th>Short Name</th>
                                                            <th>Create Date</th>
                                                            <th>Creator Name</th>
                                                            <th>Creator MyID</th>
                                                            <th>Creator UGAID</th>
                                                            <th>Creator Active</th>
                                                        </tr> 
                                                    </thead>
                                                    <tbody>
                                                        <logic:iterate id="purpose" name="user" property="PURPOSES" scope="page">                                                        
                                                            <tr>
                                                                <td><bean:write name="purpose" property="SHORT_NAME" scope="page"/></td>
                                                                <td><bean:write name="purpose" property="CREATION_DATE" scope="page"/></td>
                                                                <td><bean:write name="purpose" property="LASTNAME" scope="page"/>, <bean:write name="purpose" property="FIRSTNAME" scope="page"/> <bean:write name="purpose" property="MIDDLENAME" scope="page"/></td>
                                                                <td><bean:write name="purpose" property="MYID" scope="page"/></td>
                                                                <td><bean:write name="purpose" property="CANNUM" scope="page"/></td>
                                                                <td><bean:write name="purpose" property="ACTIVE" scope="page"/></td>
                                                            </tr>
                                                        </logic:iterate>
                                                    </tbody>
                                                </table>
                                            </td>
                                        </logic:notEmpty>
                                    </tr>
                                </logic:iterate>
                            </tbody>
                        </table>
                    </div>
                </logic:notEmpty>
                <button id="submitBtn" onclick="return false;">Submit</button>
            </div>
            </center>
        </html:form>
        <%@include file="/WEB-INF/jspf/footer.jsp"%>
    </body>
</html>
