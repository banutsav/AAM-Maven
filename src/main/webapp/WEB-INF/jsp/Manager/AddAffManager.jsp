<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String cannum, purposeId, purposeShortName;%>
<html>
    <head>
        <%@include file="/WEB-INF/html/meta.html"%>
        <%@include file="/WEB-INF/html/title.html"%>
        <%@include file="/WEB-INF/html/css.html"%>
        <script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
        <script type="text/javascript" src="js/jquery.tools.min.js"></script>        
        <script type="text/javascript" src="http://cdn.jquerytools.org/1.2.7/full/jquery.tools.min.js"></script>
        <bean:define id="userInfo" property="userInfo" name="ses" scope="session"/>
        <script type="text/javascript">
            $(document).ready(function(){
                addEventListener("keypress", function(event){
                    if(event.which == 13){
                        $("#searchBtn").click();
                    }
                });
                $("#searchBtn").click(function(){
                    $("#submitOpt").val("Search");
                    $("#addAffManagerForm").submit();
                });
                $("#resetBtn").click(function(){
                    $("#submitOpt").val("Reset");
                    $("#addAffManagerForm").submit();
                });
                $("#firstLink").click(function(){
                    $("#submitOpt").val("First");
                    $("#addAffManagerForm").submit();
                });
                $("#prevLink").click(function(){
                    $("#submitOpt").val("Prev");
                    $("#addAffManagerForm").submit();
                });
                $("#nextLink").click(function(){
                    $("#submitOpt").val("Next");
                    $("#addAffManagerForm").submit();
                });
                $("#lastLink").click(function(){
                    $("#submitOpt").val("Last");
                    $("#addAffManagerForm").submit();
                });
                $("#cancel").click(function(){
                    $("#submitOpt").val("cancel");
                    $("#addAffManagerForm").submit();
                });
                $(".submit").click(function(){
                    if($("#managerCannum").val() === null || $("#managerCannum").val() === ""){
                        alert("You must select a person.");
                        return false;
                    }
                    
                    //alert("You selected cannum -> '" + $("#managerCannum").val() + "'");
                    $("#submitOpt").val("Commit");
                    $("#addAffManagerForm").submit();
                });
            });
        </script>
    </head>
    <body>
        <html:form styleId="addAffManagerForm" action="AddAffManager.do">
            <%@include file="/WEB-INF/jspf/header.jsp"%>
            <%@include file="/WEB-INF/jspf/header_login.jsp"%>
            <html:hidden styleId="submitOpt" property="submitOpt" value=""/>
            <bean:define id="purposeId" name="addAffManagerForm" property="purposeId" scope="request" toScope="page" type="String"/>
            <html:hidden styleId="purposeId" property="purposeId" value="<%= purposeId%>"/>
            <bean:define id="purposeShortName" name="addAffManagerForm" property="purposeShortName" scope="request" toScope="page" type="String"/>
            <html:hidden styleId="purposeShortName" property="purposeShortName" value="<%= purposeShortName%>"/>
            <!--bean:define id="managerCannum" name="addAffManagerForm" property="managerCannum" scope="request" toScope="page" type="String"/>
            <!html:hidden styleId="managerCannum" property="managerCannum" value="<!%= managerCannum%>"/-->

            <div style="padding: 0px 10px;">
                <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>
                    <h3>Add Proxy Manager</h3>
                    <hr>
                    <logic:notEmpty name="errorMsg" scope="request">
                        <div>
                            <b style="color: red;"><u><bean:write name="errorMsg" scope="request"/></u></b>
                        </div>
                    </logic:notEmpty>
                    <div style="text-align: left;">
                        <div style="text-align: right;">
                            <a href="./Get.do?action=home">Main Menu</a><br>
                            <a href="./GET.do?action=manAffMan">Cancel</a>
                        </div>
                        <b>Add Proxy Manager for Purpose "<bean:write name="addAffManagerForm" property="purposeShortName" scope="request"/>"</b>
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
                                        <td>UGA ID:</td>
                                        <td><html:text property="ugaId" maxlength="9" size="9"/></td>
                                    <td></td>
                                    <td></td>
                                    <td><html:radio value="Exact" property="ugaIdOpt">Exact</html:radio></td>                                
                                    </tr> 
                                    <tr>
                                        <td>Job Title:</td>
                                        <td><html:text property="jobTitle" maxlength="64" size="32"/></td>
                                    <td><html:radio value="Begins" property="jobTitleOpt">Begins with</html:radio></td>
                                    <td><html:radio value="Contains" property="jobTitleOpt">Contains</html:radio></td>
                                    <td><html:radio value="Exact" property="jobTitleOpt">Exact</html:radio></td>                                
                                    </tr> 
                                    <tr>
                                        <td>Home Dept. Num</td>
                                        <td><html:text property="homeDeptNum" maxlength="64" size="32"/></td>
                                    <td></td>
                                    <td></td>
                                    <td><html:radio value="Exact" property="homeDeptNumOpt">Exact</html:radio></td>                            
                                    </tr> 
                                    <tr>
                                        <td>Home Dept. Name</td>
                                        <td><html:text property="homeDeptName" maxlength="64" size="32"/></td>
                                    <td><html:radio value="Begins" property="homeDeptNameOpt">Begins with</html:radio></td>
                                    <td><html:radio value="Contains" property="homeDeptNameOpt">Contains</html:radio></td>
                                    <td><html:radio value="Exact" property="homeDeptNameOpt">Exact</html:radio></td>                                
                                    </tr>
                                    <tr>
                                        <td>Include affiliates in results?</td>
                                        <td colspan="4">
                                        <html:radio value="Yes" property="incAffiliates">Yes</html:radio>
                                        <html:radio value="No" property="incAffiliates">No</html:radio>
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

                        <logic:equal name="addAffManagerForm" property="count" value="0" scope="request">
                            <h5>Search Results</h5>
                            None
                        </logic:equal> 
                        <logic:notEqual name="addAffManagerForm" property="count" value="0" scope="request">
                            <h5>Search Results</h5>
                            <div id="searchResults">
                                Number of Results: <bean:write name="addAffManagerForm" property="count" scope="request"/>
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
                                                <th>MyID</th>
                                                <th>UGAID</th>
                                                <th>Title</th>
                                                <th>Home Department</th>
                                                <th>E-Mail</th>
                                                <th>Main Phone</th>
                                                <th>Alternate<br>Phone</th>
                                                <th>Fax</th>
                                                <th>Active</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <logic:iterate id="person" name="addAffManagerForm" property="queryResults" scope="request">
                                                <tr>
                                                    <bean:define id="cannum" name="person" property="CANNUM" scope="page" type="String"/>
                                                    <td><html:radio styleId="managerCannum" property="managerCannum" value="<%=cannum%>"/></td>
                                                    <td><bean:write name="person" property="LASTNAME" scope="page"/>, <bean:write name="person" property="FIRSTNAME" scope="page"/></td>
                                                    <td><bean:write name="person" property="MYID" scope="page"/></td>
                                                    <td><bean:write name="person" property="CANNUM" scope="page"/></td>
                                                    <td><bean:write name="person" property="TITLE" scope="page"/></td>
                                                    <td><bean:write name="person" property="DEPTNUM" scope="page"/> / <bean:write name="person" property="DEPTNAME" scope="page"/></td>
                                                    <td><bean:write name="person" property="EMAIL" scope="page"/></td>
                                                    <td><bean:write name="person" property="PHONE" scope="page"/></td>
                                                    <td><bean:write name="person" property="ALTPHONE" scope="page"/></td>
                                                    <td><bean:write name="person" property="FAX" scope="page"/></td>
                                                    <td>
                                                        <logic:equal name="person" property="ACTIVE" scope="page" value="1">
                                                            Yes
                                                        </logic:equal>
                                                        <logic:equal name="person" property="ACTIVE" scope="page" value="0">
                                                            No
                                                        </logic:equal>
                                                    </td>
                                                </tr>
                                            </logic:iterate>
                                        </tbody>
                                    </table>
                                </div>
                                <button class="submit" onclick="return false;">Submit</button>
                            </div>
                        </logic:notEqual>
                        <!--/logic:equal-->
                    </div>
                </center>
            </div>

            <%@include file="/WEB-INF/jspf/footer.jsp"%>
        </html:form>
    </body>
</html>
