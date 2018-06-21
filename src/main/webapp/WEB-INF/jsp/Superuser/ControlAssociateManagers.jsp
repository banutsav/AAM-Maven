<%-- 
    Document   : ControlAssociateManagers
    Created on : May 14, 2013, 3:06:52 PM
    Author     : submyers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="/WEB-INF/tags-compress" prefix="compress" %>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
            function ManageAdmin(adminId){
                $("#adminId").val(adminId);
                $("#action").val("manageMan");
                $("#controlAssociateManagersForm").submit();
            }
            $(document).ready(function(){
                addEventListener("keypress", function(event){
                    if(event.which == 13){
                        $("#searchBtn").click();
                    }
                });
                $("#searchBtn").click(function(){
                    $("#action").val("Search");
                    $("#controlAssociateManagersForm").submit();
                });
                $("#resetBtn").click(function(){
                    $("#action").val("Reset");
                    $("#controlAssociateManagersForm").submit();
                });
                $("#newManagerBtn").click(function(){
                    $("#action").val("NewManager");
                    $("#controlAssociateManagersForm").submit();
                });
                $("#firstLink").click(function(){
                    $("#action").val("First");
                    $("#controlAssociateManagersForm").submit();
                });
                $("#prevLink").click(function(){
                    $("#action").val("Prev");
                    $("#controlAssociateManagersForm").submit();
                });
                $("#nextLink").click(function(){
                    $("#action").val("Next");
                    $("#controlAssociateManagersForm").submit();
                });
                $("#lastLink").click(function(){
                    $("#action").val("Last");
                    $("#controlAssociateManagersForm").submit();
                });
                $("#cancel").click(function(){
                    $("#action").val("cancel");
                    $("#controlAssociateManagersForm").submit();
                });
            });
        </script>
    </head>
    <body>
        <compress:compress>
            <html:form action="ControlAssociateManagers.do" styleId="controlAssociateManagersForm">
                <%@include file="/WEB-INF/jspf/header.jsp"%>
                <%@include file="/WEB-INF/jspf/header_login.jsp"%>
                <html:hidden styleId="action" property="action"/>
                <html:hidden styleId="adminId" property="adminId"/>
                <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Create/Activate/Deactivate Associate Manager</h3>
                    <hr/>
                    
                    <!-- success message -->
                    <logic:notEmpty name="message" scope="request">
                        <div>
                            <b style="color: green;"><u><bean:write name="message" scope="request"/></u></b>
                        </div>
                    </logic:notEmpty>
                   
                    
                    <logic:notEmpty name="errorMsg" scope="request">
                        <div>
                            <b style="color: red;"><u><bean:write name="errorMsg" scope="request"/></u></b>
                        </div>
                    </logic:notEmpty>
                    <div style="text-align: left;">
                        <div style="text-align: right;">
                            <a href="./Get.do?action=home">Main Menu</a>
                        </div>
                        <h5>Search Associate Managers:</h5>
                        <table class="addAPOC_table">
                            <tbody>
                                <tr>
                                    <td>Last Name:</td>
                                    <td><html:text property="lastName" maxlength="64" size="32"/></td>
                                    <td><html:radio value="Begins" property="lastNameOpt">Begins with</html:radio></td>
                                    <td><html:radio value="Contains" property="lastNameOpt">Contains</html:radio></td>
                                    <td><html:radio value="Exact" property="lastNameOpt">Exact</html:radio></td>                                
                                </tr> 
                                <tr>
                                    <td>First Name:</td>
                                    <td><html:text property="firstName" maxlength="64" size="32"/></td>
                                    <td><html:radio value="Begins" property="firstNameOpt">Begins with</html:radio></td>
                                    <td><html:radio value="Contains" property="firstNameOpt">Contains</html:radio></td>
                                    <td><html:radio value="Exact" property="firstNameOpt">Exact</html:radio></td>                                
                                </tr> 
                                <tr>
                                    <td>MyID:</td>
                                    <td><html:text property="myId" maxlength="64" size="32"/></td>
                                    <td><html:radio value="Begins" property="myIdOpt">Begins with</html:radio></td>
                                    <td><html:radio value="Contains" property="myIdOpt">Contains</html:radio></td>
                                    <td><html:radio value="Exact" property="myIdOpt">Exact</html:radio></td>                                
                                </tr> 
                                <tr>
                                    <td>UGAID:</td>
                                    <td><html:text property="cannum" maxlength="9" size="9"/></td>
                                    <td></td>
                                    <td></td>
                                    <td><html:radio value="Exact" property="cannumOpt">Exact</html:radio></td>                                
                                </tr>
                                <tr>
                                    <td>Include inactive managers in results?</td>
                                    <td colspan="4">
                                        <html:radio value="Yes" property="incInactive">Yes</html:radio>
                                        <html:radio value="No" property="incInactive">No</html:radio>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="5">
                                        <button id="searchBtn" onclick="return false">Search</button>
                                        &nbsp;&nbsp;
                                        <button id="resetBtn" onclick="return false">Reset</button>
                                        &nbsp;&nbsp;
                                        <button id="newManagerBtn" onclick="return false">New Manager</button>
                                    </td>
                                </tr>
                            </tbody>                  
                        </table> 

                        <br><br>

                        <logic:notEmpty name="controlAssociateManagersForm" property="executed" scope="session">
                            <logic:equal name="controlAssociateManagersForm" property="count" value="0" scope="session">
                                <h5>Search Results</h5>
                                None
                            </logic:equal> 
                            <logic:notEqual name="controlAssociateManagersForm" property="count" value="0" scope="session">
                                <h5>Search Results</h5>
                                <div id="searchResults">
                                    Page <bean:write name="controlAssociateManagersForm" property="displayPageNum" scope="session"/> of <bean:write name="controlAssociateManagersForm" property="displayPageCount" scope="session"/>
                                    <br>
                                    Number of Results: <bean:write name="controlAssociateManagersForm" property="count" scope="session"/>
                                    <br>                        
                                    <a id="firstLink" href="#" onclick="return false;">First</a> &nbsp;
                                    <a id="prevLink" href="#" onclick="return false;">Prev</a> &nbsp;
                                    <a id="nextLink" href="#" onclick="return false;">Next</a> &nbsp;
                                    <a id="lastLink" href="#" onclick="return false;">Last</a> &nbsp;
                                    <br>
                                    <div class="tableContainer">
                                        <table class="paulaQueryTable">
                                            <thead>
                                                <tr>
                                                    <th>Full Name</th>
                                                    <th>Active Manager</th>
                                                    <th>MyID</th>
                                                    <th>UGAID</th>
                                                    <th>Title</th>
                                                    <th>Department</th>
                                                    <th>E-Mail</th>
                                                    <th>Main Phone</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <logic:iterate id="person" name="queryResults" scope="request">
                                                    <tr>
                                                        <td>
                                                            <a href="#" onclick="ManageAdmin('<bean:write name="person" property="ID" scope="page"/>');return false;">
                                                                <bean:write name="person" property="LASTNAME" scope="page"/>, <bean:write name="person" property="FIRSTNAME" scope="page"/>
                                                            </a>
                                                        </td>
                                                        <td>
                                                            <logic:equal name="person" property="ADMIN_ACTIVE" scope="page" value="1">
                                                                Yes
                                                            </logic:equal>
                                                            <logic:equal name="person" property="ADMIN_ACTIVE" scope="page" value="0">
                                                                <b style="color:red;">No</b>
                                                            </logic:equal>
                                                        </td>
                                                        <td><bean:write name="person" property="MYID" scope="page"/></td>
                                                        <td><bean:write name="person" property="CANNUM" scope="page"/></td>
                                                        <td><bean:write name="person" property="TITLE" scope="page"/></td>
                                                        <td><bean:write name="person" property="DEPTNUM" scope="page"/> / <bean:write name="person" property="DEPTNAME" scope="page"/></td>
                                                        <td><bean:write name="person" property="EMAIL" scope="page"/></td>
                                                        <td><bean:write name="person" property="PHONE" scope="page"/></td>
                                                    </tr>
                                                </logic:iterate>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </logic:notEqual>
                        </logic:notEmpty>
                    </div>
                </center>
                <%@include file="/WEB-INF/jspf/footer.jsp"%>
            </html:form>
        </compress:compress>
    </body>
</html>
