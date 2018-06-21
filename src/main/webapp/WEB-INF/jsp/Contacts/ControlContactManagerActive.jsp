<%-- 
    Document   : ControlContactManagerActive
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
            
            $(document).ready(function(){
                
                $("#deactivate_btn").click(function(){
                    $("#action").val("deactivate");
                    $("#controlContactManagerActiveForm").submit();
                });
                
                $("#cancelBtn").click(function(){
                    $("#action").val("back");
                    $("#controlContactManagerActiveForm").submit();
                });
                  
                $("#activate_btn").click(function(){
                    $("#action").val("activate");
                    $("#controlContactManagerActiveForm").submit();
                });
                
            });
            
        </script>
    </head>
    <body>
        <compress:compress>
            <html:form action="ControlContactManagerActive.do" styleId="controlContactManagerActiveForm">
                <%@include file="/WEB-INF/jspf/header.jsp"%>
                <%@include file="/WEB-INF/jspf/header_login.jsp"%>
                <html:hidden styleId="action" property="action"/>
                <bean:define id="adminId" name="controlContactManagerActiveForm" property="adminId" scope="request" toScope="page" type="String"/>
                <html:hidden styleId="adminId" property="adminId" value="<%= adminId%>"/>
                <bean:define id="cannum" name="controlContactManagerActiveForm" property="cannum" scope="request" toScope="page" type="String"/>
                <html:hidden styleId="adminId" property="cannum" value="<%= cannum%>"/>
                <html:hidden styleId="targPurpId" property="targPurpId"/>
                <html:hidden styleId="targProxyId" property="targProxyId"/>

                <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Manage Contact Managers</h3>
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
                    <h5>Manager Information:</h5>
                    
                    <table>
                        <tr>
                            <td><b>Name:</b></td>
                            <td><bean:write name="controlContactManagerActiveForm" property="lastName" scope="request"/>, <bean:write name="controlContactManagerActiveForm" property="firstName" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>Active Manager:</b></td>
                            <td>
                                <logic:equal name="controlContactManagerActiveForm" property="adminActive" scope="request" value="1">
                                    Yes
                                </logic:equal>
                                <logic:equal name="controlContactManagerActiveForm" property="adminActive" scope="request" value="0">
                                    <b style="color: red;">No</b>
                                </logic:equal>
                            </td>
                        </tr>
                        <tr>
                            <td><b>MyID:</b></td>
                            <td><bean:write name="controlContactManagerActiveForm" property="myId" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>UGAID:</b></td>
                            <td><bean:write name="controlContactManagerActiveForm" property="cannum" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>Title:</b></td>
                            <td><bean:write name="controlContactManagerActiveForm" property="title" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>Email:</b></td>
                            <td><bean:write name="controlContactManagerActiveForm" property="email" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>Phone:</b></td>
                            <td><bean:write name="controlContactManagerActiveForm" property="phone" scope="request"/></td>
                        </tr>
                        <tr>
                            <td><b>Department:</b></td>
                            <td><bean:write name="controlContactManagerActiveForm" property="deptNum" scope="request"/> / <bean:write name="controlContactManagerActiveForm" property="deptName" scope="request"/></td>
                        </tr>
                    </table>
                    
                    <br>
                    
                    
                    <logic:equal name="controlContactManagerActiveForm" property="adminActive" scope="request" value="1">
                        <button id="deactivate_btn" onclick="return false;">Deactivate Manager</button>
                    </logic:equal>
                    <logic:equal name="controlContactManagerActiveForm" property="adminActive" scope="request" value="0">
                        <button id="activate_btn" onclick="return false;">Activate Manager</button>
                    </logic:equal>
                       
                    <button id="cancelBtn" onclick="return false;">Back</button>
                        
                    <br><br>
                    
                </div>
                </center>
                <%@include file="/WEB-INF/jspf/footer.jsp"%>

                    
            </html:form>
        </compress:compress>
    </body>
</html>
