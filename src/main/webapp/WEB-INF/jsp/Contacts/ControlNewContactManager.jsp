<%--
    Document   : ControlContactManagers
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
        <script>
            $(document).ready(function(){
                addEventListener("keypress", function(event){
                    if(event.which == 13){
                        $("#searchBtn").click();
                    }
                });
                $("#searchBtn").click(function(){
                    $("#action").val("Search");
                    $("#controlNewContactManagerForm").submit();
                });
                $("#resetBtn").click(function(){
                    $("#action").val("Reset");
                    $("#controlNewContactManagerForm").submit();
                });
                $("#firstLink").click(function(){
                    $("#action").val("First");
                    $("#controlNewContactManagerForm").submit();
                });
                $("#prevLink").click(function(){
                    $("#action").val("Prev");
                    $("#controlNewContactManagerForm").submit();
                });
                $("#nextLink").click(function(){
                    $("#action").val("Next");
                    $("#controlNewContactManagerForm").submit();
                });
                $("#lastLink").click(function(){
                    $("#action").val("Last");
                    $("#controlNewContactManagerForm").submit();
                });
                $("#backBtn").click(function(){
                    $("#action").val("back");
                    $("#controlNewContactManagerForm").submit();
                });
                $(".submit").click(function(){
                    if($("#targPersonCannum").val() === ""){
                        alert("Select a person.");
                        return false;
                    }
                    //alert("Targ persion cannum = '" + $("#targPersonCannum").val() + "' AND targ purp id = '" + $("#targPurpId").val() + "'");
                    $("#action").val("newManager");
                    $("#controlNewContactManagerForm").submit();
                });
            });
        </script>
    </head>
    <body>
        <compress:compress>
            <html:form action="ControlNewContactManager.do" styleId="controlNewContactManagerForm">
                <%@include file="/WEB-INF/jspf/header.jsp"%>
                <%@include file="/WEB-INF/jspf/header_login.jsp"%>
                <html:hidden styleId="action" property="action"/>

                <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Add New Contact Manager</h3>
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
                    <br>
                    <h5>Search PAULA:</h5>
                    
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
                                    &nbsp;&nbsp;
                                    <button id="backBtn" onclick="return false">Back</button>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    
                    <br><br>

                    <logic:equal name="controlNewContactManagerForm" property="count" value="0" scope="request">
                        <h5>Search Results</h5>
                        None
                    </logic:equal>
                        
                    <logic:greaterThan name="controlNewContactManagerForm" property="count" value="0" scope="request">
                        <h5>Search Results</h5>
                        <div id="searchResults">
                            Page <bean:write name="controlNewContactManagerForm" property="displayPageNum" scope="request"/> of <bean:write name="controlNewContactManagerForm" property="displayPageCount" scope="request"/>
                            <br>
                            Number of Results: <bean:write name="controlNewContactManagerForm" property="count" scope="request"/>
                            <br>
                            <a id="firstLink" href="#" onclick="return false;">First</a> &nbsp;
                            <a id="prevLink" href="#" onclick="return false;">Prev</a> &nbsp;
                            <a id="nextLink" href="#" onclick="return false;">Next</a> &nbsp;
                            <a id="lastLink" href="#" onclick="return false;">Last</a> &nbsp;
                            <br>
                            <button class="submit" onclick="return false;">Submit</button>
                            <br>
                            <div class="tableContainer">
                                <table class="paulaQueryTable">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>Full Name</th>
                                            <th>Active</th>
                                            <th>MyID</th>
                                            <th>UGAID</th>
                                            <th>Title</th>
                                            <th>Department</th>
                                            <th>E-Mail</th>
                                            <th>Main Phone</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <logic:iterate id="person" name="controlNewContactManagerForm" property="queryResults" scope="request">
                                            <tr>
                                                <td>
                                                    <input type="radio" name="targPersonCannum"  id="targPersonCannum" value="<bean:write name="person" property="CANNUM" scope="page"/>">
                                                </td>
                                                <td>
                                                    <bean:write name="person" property="LASTNAME" scope="page"/>, <bean:write name="person" property="FIRSTNAME" scope="page"/>
                                                </td>
                                                <td>
                                                    <logic:equal name="person" property="ACTIVE" scope="page" value="1">
                                                        Yes
                                                    </logic:equal>
                                                    <logic:equal name="person" property="ACTIVE" scope="page" value="0">
                                                        No
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
                        <button class="submit" onclick="return false;">Submit</button>
                    </logic:greaterThan>
                    
                </div>
                    
                </center>
                <%@include file="/WEB-INF/jspf/footer.jsp"%>
            </html:form>
        </compress:compress>
    </body>
</html>
