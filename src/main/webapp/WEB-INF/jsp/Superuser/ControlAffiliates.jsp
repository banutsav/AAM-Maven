<%-- 
    Document   : ControlAffiliates
    Created on : May 14, 2013, 3:07:04 PM
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
            function ManageAff(affId){
                $("#affId").val(affId);
                $("#action").val("manageAff");
                $("#controlAffiliatesForm").submit();
            }
            $(document).ready(function(){
                addEventListener("keypress", function(event){
                    if(event.which == 13){
                        $("#searchBtn").click();
                    }
                });
                $("#searchBtn").click(function(){
                    $("#action").val("Search");
                    $("#controlAffiliatesForm").submit();
                });
                $("#resetBtn").click(function(){
                    $("#action").val("Reset");
                    $("#controlAffiliatesForm").submit();
                });
                $("#firstLink").click(function(){
                    $("#action").val("First");
                    $("#controlAffiliatesForm").submit();
                });
                $("#prevLink").click(function(){
                    $("#action").val("Prev");
                    $("#controlAffiliatesForm").submit();
                });
                $("#nextLink").click(function(){
                    $("#action").val("Next");
                    $("#controlAffiliatesForm").submit();
                });
                $("#lastLink").click(function(){
                    $("#action").val("Last");
                    $("#controlAffiliatesForm").submit();
                });
                $("#cancel").click(function(){
                    $("#action").val("cancel");
                    $("#controlAffiliatesForm").submit();
                });
            });
        </script>
    </head>
    <body>
        <compress:compress>
            <html:form action="ControlAffiliates.do" styleId="controlAffiliatesForm">
                <%@include file="/WEB-INF/jspf/header.jsp"%>
                <%@include file="/WEB-INF/jspf/header_login.jsp"%>
                <html:hidden styleId="action" property="action"/>
                <html:hidden styleId="affId" property="affId"/>

                <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Activate/Deactivate Affiliate</h3>
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
                    <h5>Search Affiliates:</h5>
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
                                <td>OVPR ID:</td>
                                <td><html:text property="ovprId" maxlength="64" size="32"/></td>
                                <td><html:radio value="Begins" property="ovprIdOpt">Begins with</html:radio></td>
                                <td><html:radio value="Contains" property="ovprIdOpt">Contains</html:radio></td>
                                <td><html:radio value="Exact" property="ovprIdOpt">Exact</html:radio></td>                                
                            </tr> 
                            <tr>
                                <td>Pseudo-UGAID:</td>
                                <td><html:text property="pseudoCan" maxlength="9" size="9"/></td>
                                <td></td>
                                <td></td>
                                <td><html:radio value="Exact" property="pseudoCanOpt">Exact</html:radio></td>                                
                            </tr> 
                            <tr>
                                <td>Job Title:</td>
                                <td><html:text property="jobTitle" maxlength="64" size="32"/></td>
                                <td><html:radio value="Begins" property="jobTitleOpt">Begins with</html:radio></td>
                                <td><html:radio value="Contains" property="jobTitleOpt">Contains</html:radio></td>
                                <td><html:radio value="Exact" property="jobTitleOpt">Exact</html:radio></td>                                
                            </tr>
                            <tr>
                                <td>Organization Name</td>
                                <td><html:text property="orgName" maxlength="64" size="32"/></td>
                                <td><html:radio value="Begins" property="orgNameOpt">Begins with</html:radio></td>
                                <td><html:radio value="Contains" property="orgNameOpt">Contains</html:radio></td>
                                <td><html:radio value="Exact" property="orgNameOpt">Exact</html:radio></td>                                
                            </tr>
                            <tr>
                                <td>Department Name</td>
                                <td><html:text property="deptName" maxlength="64" size="32"/></td>
                                <td><html:radio value="Begins" property="deptNameOpt">Begins with</html:radio></td>
                                <td><html:radio value="Contains" property="deptNameOpt">Contains</html:radio></td>
                                <td><html:radio value="Exact" property="deptNameOpt">Exact</html:radio></td>                                
                            </tr>
                            <tr>
                                <td>Include inactive in results?</td>
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
                                </td>
                            </tr>
                        </tbody>                  
                    </table> 

                    <br><br>

                    <logic:notEmpty name="controlAffiliatesForm" property="executed" scope="session">
                        <logic:equal name="controlAffiliatesForm" property="count" value="0" scope="session">
                            <h5>Search Results</h5>
                            None
                        </logic:equal> 
                        <logic:notEqual name="controlAffiliatesForm" property="count" value="0" scope="session">
                            <h5>Search Results</h5>
                            <div id="searchResults">
                                Page <bean:write name="controlAffiliatesForm" property="displayPageNum" scope="session"/> of <bean:write name="controlAffiliatesForm" property="displayPageCount" scope="session"/>
                                <br>
                                Number of Results: <bean:write name="controlAffiliatesForm" property="count" scope="session"/>
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
                                                <th>Active</th>
                                                <th>OVPR ID</th>
                                                <th>Pseudo-UGAID</th>
                                                <th>Title</th>
                                                <th>Organization/Department</th>
                                                <th>E-Mail</th>
                                                <th>Main Phone</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <logic:iterate id="person" name="queryResults" scope="request">
                                                <tr>
                                                    <td>
                                                        <a href="#" onclick="ManageAff('<bean:write name="person" property="ID" scope="page"/>');return false;">
                                                            <bean:write name="person" property="LASTNAME" scope="page"/>, <bean:write name="person" property="FIRSTNAME" scope="page"/>
                                                        </a>
                                                    </td>
                                                    <td>
                                                        <logic:equal name="person" property="ACTIVE" scope="page" value="1">
                                                            Yes
                                                        </logic:equal>
                                                        <logic:equal name="person" property="ACTIVE" scope="page" value="0">
                                                            No
                                                        </logic:equal>
                                                    </td>
                                                    <td><bean:write name="person" property="OVPRID" scope="page"/></td>
                                                    <td><bean:write name="person" property="PSEUDOCAN" scope="page"/></td>
                                                    <td><bean:write name="person" property="TITLE" scope="page"/></td>
                                                    <td><bean:write name="person" property="ORGANIZATION" scope="page"/> / <bean:write name="person" property="DEPARTMENT" scope="page"/></td>
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
