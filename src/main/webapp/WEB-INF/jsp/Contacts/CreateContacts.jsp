<%-- 
    Document   : Create New Contact
    Author     : utsavb
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="java.util.HashMap" %>
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
            
            $(document).ready(function(){
               
               $("#searchBtn").click(function(){
                   
                   if(($("#firstName").val() === "")&&($("#lastName").val() === "")&&($("#ovprId").val() === "")&&($("#org").val() === ""))
                    {    
                        alert("Please fill out atleast one search criteria.");
                        return false;
                    }
                    
                    $("#action").val("Search");
                    $("#createContactsForm").submit();
                });
                $("#resetBtn").click(function(){
                    $("#action").val("Reset");
                    $("#createContactsForm").submit();
                });
                $("#firstLink").click(function(){
                    $("#action").val("First");
                    $("#createContactsForm").submit();
                });
                $("#prevLink").click(function(){
                    $("#action").val("Prev");
                    $("#createContactsForm").submit();
                });
                $("#nextLink").click(function(){
                    $("#action").val("Next");
                    $("#createContactsForm").submit();
                });
                $("#lastLink").click(function(){
                    $("#action").val("Last");
                    $("#createContactsForm").submit();
                }); 
                $("#newContact").click(function(){
                    $("#action").val("NewPerson");
                    $("#targPersonCannum").val("0");
                    $("#createContactsForm").submit();
                });
                $("#submitBtn").click(function(){
                    
                    if(($('input[name=targPersonCannum]:checked', '#createContactsForm').val()) === undefined){
                        alert("Select a person.");
                        return false;
                    }
                    $("#action").val("New");
                    $("#targPersonCannum").val($('input[name=targPersonCannum]:checked', '#createContactsForm').val());
                    $("#createContactsForm").submit();
                });
            });
        </script>
    </head>
    <body>
        <html:form action="CreateContacts.do" styleId="createContactsForm">
            <html:hidden styleId="action" property="action"/>
            <html:hidden styleId="targPersonCannum" property="targPersonCannum"/>
            <%@include file="/WEB-INF/jspf/header.jsp"%>
            <%@include file="/WEB-INF/jspf/header_login.jsp"%>
            <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>
                    <h3>Create Contact</h3>
                    
                    <logic:notEmpty name="returnMsg" scope="request">
                        <div>
                            <logic:equal name="returnMsg" value="1" scope="request">
                            <b style="color: green;"><u>Successfully created new Contact record</u></b>
                            </logic:equal>
                            <logic:equal name="returnMsg" value="0" scope="request">
                            <b style="color: red;"><u>Error creating new Contact record</u></b>
                            </logic:equal>
                        </div>
                    </logic:notEmpty>
                    
                    <hr>
                    <div>
                        <div style="text-align: right;">
                            <a href="./Get.do?action=home">Main Menu</a>
                        </div>
                    </div>    
                    
                    <div style="text-align: left;">
                        <table>
                            
                            <tr><td><h5>Select or Create Primary UGAID</h5></td></tr>
                            
                            <tr><td><h5>Search PAULA (including Affiliates and Contacts):</h5></td></tr>
                            <tr>
                                <td>Last Name:</td>
                                <td><html:text property="lastName" maxlength="64" size="32" styleId="lastName"/></td>
                                <td><html:radio value="Begins" property="lastNameOpt">Begins with</html:radio></td>
                                <td><html:radio value="Contains" property="lastNameOpt">Contains</html:radio></td>
                                <td><html:radio value="Exact" property="lastNameOpt">Exact</html:radio></td>                                
                            </tr> 
                            
                            <tr>
                                <td>First Name:</td>
                                <td><html:text property="firstName" maxlength="64" size="32" styleId="firstName"/></td>
                                <td><html:radio value="Begins" property="firstNameOpt">Begins with</html:radio></td>
                                <td><html:radio value="Contains" property="firstNameOpt">Contains</html:radio></td>
                                <td><html:radio value="Exact" property="firstNameOpt">Exact</html:radio></td>                                
                            </tr> 
                            <tr>
                                <td>UGA MYID:</td>
                                <td><html:text property="ovprId" maxlength="64" size="32" styleId="ovprId"/></td>
                                <td><html:radio value="Begins" property="ovprIdOpt">Begins with</html:radio></td>
                                <td><html:radio value="Contains" property="ovprIdOpt">Contains</html:radio></td>
                                <td><html:radio value="Exact" property="ovprIdOpt">Exact</html:radio></td>                                
                            </tr> 
                            <tr>
                                <td>Organization/Department:</td>
                                <td><html:text property="org" maxlength="64" size="32" styleId="org"/></td>
                                <td><html:radio value="Begins" property="orgOpt">Begins with</html:radio></td>
                                <td><html:radio value="Contains" property="orgOpt">Contains</html:radio></td>
                                <td><html:radio value="Exact" property="orgOpt">Exact</html:radio></td>                                
                            </tr> 
                            
                            <tr>
                                <td colspan="5">
                                    <button id="searchBtn" onclick="return false">Search</button>
                                    &nbsp;&nbsp;
                                    <button id="resetBtn" onclick="return false">Reset</button>
                                </td>
                            </tr>
                            
                        </table>    
                    </div>
                    <br>
                    <div style="text-align: left;">
                    <a id="newContact" href="#" onclick="return false;">Create New Contact as Primary UGAID</a>
                    </div>
                    <br>
                    
                    <logic:notEmpty name="createContactsForm" property="executed" scope="session">
                    <div style="text-align: left;">    
                        <logic:equal name="createContactsForm" property="count" value="0" scope="session">
                            <h5>Search Results</h5>
                            None
                            <br>
                            <!--<a id="newContact" href="#" onclick="return false;">Create New Contact</a>-->
                        </logic:equal> 
                            
                        <logic:notEqual name="createContactsForm" property="count" value="0" scope="session">
                            <h5>Search Results</h5>
                            <div id="searchResults">
                                Page <bean:write name="createContactsForm" property="displayPageNum" scope="session"/> of <bean:write name="createContactsForm" property="displayPageCount" scope="session"/>
                                <br>
                                Number of Results: <bean:write name="createContactsForm" property="count" scope="session"/>
                                <br>                        
                                <a id="firstLink" href="#" onclick="return false;">First</a> &nbsp;
                                <a id="prevLink" href="#" onclick="return false;">Prev</a> &nbsp;
                                <a id="nextLink" href="#" onclick="return false;">Next</a> &nbsp;
                                <a id="lastLink" href="#" onclick="return false;">Last</a> &nbsp;
                                <br>
                                <button id="submitBtn" onclick="return false">Select as Primary UGAID</button>
                                <br>
                                <div class="tableContainer">
                                    <table class="paulaQueryTable">
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th>Contact</th>
                                                <th>Affiliate</th>
                                                <th>UGA Fac/Staff/Student</th>
                                                <th>Full Name</th>
                                                <th>MYID</th>
                                                <!--<th>UGAID PRIME</th>-->
                                                <th>UGAID</th>
                                                <th>ORGANIZATION</th>
                                                <th>TITLE</th>
                                                <th>EMAIL</th>
                                                <th>PHONE</th>
                                                
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <logic:iterate id="person" name="queryResults" scope="request">
                                                
                                                <logic:equal name="person" property="COLOR_CODE" value="0" scope="page">
                                                    <tr bgcolor="red"> 
                                                        <td style="background-color: #E0E8F4"><input type="radio" name="targPersonCannum"  id="targPersonCannum" value="<bean:write name="person" property="CANNUM" scope="page"/>"></td>
                                                        
                                                        <logic:equal name="person" property="CONTACT" value="1" scope="page">
                                                            <logic:equal name="person" property="CONTACT_ACTIVE" value="1" scope="page">
                                                            <td style="background-color: #E0E8F4"><font color="green">Active</font></td>
                                                            </logic:equal>
                                                            <logic:equal name="person" property="CONTACT_ACTIVE" value="0" scope="page">
                                                            <td style="background-color: #E0E8F4"><font color="red">Inactive</font></td>
                                                            </logic:equal>    
                                                        </logic:equal>
                                                        
                                                        <logic:equal name="person" property="CONTACT" value="0" scope="page">
                                                        <td style="background-color: #E0E8F4"></td>    
                                                        </logic:equal>
                                                        
                                                        
                                                        <logic:equal name="person" property="AFFILIATE" value="1" scope="page">
                                                        <td style="background-color: #E0E8F4"><font color="green">X</font></td>    
                                                        </logic:equal>
                                                        
                                                        <logic:equal name="person" property="AFFILIATE" value="0" scope="page">
                                                        <td style="background-color: #E0E8F4"></td>    
                                                        </logic:equal>
                                                        
                                                        <logic:equal name="person" property="UGA" value="1" scope="page">
                                                        <td style="background-color: #E0E8F4"><font color="green">X</font></td>    
                                                        </logic:equal>
                                                        
                                                        <logic:equal name="person" property="UGA" value="0" scope="page">
                                                        <td style="background-color: #E0E8F4"></td>    
                                                        </logic:equal>
                                                        
                                                        <td style="background-color: #E0E8F4"><bean:write name="person" property="LASTNAME" scope="page"/>, <bean:write name="person" property="FIRSTNAME" scope="page"/></td>
                                                        <td style="background-color: #E0E8F4"><bean:write name="person" property="MYID" scope="page"/></td>
                                                        <!--<td style="background-color: #E0E8F4"><bean:write name="person" property="CANNUM" scope="page"/></td>-->
                                                        <td style="background-color: #E0E8F4"><bean:write name="person" property="CONTACT_UGAID" scope="page"/></td>
                                                        <td style="background-color: #E0E8F4"><bean:write name="person" property="ORGANIZATION" scope="page"/></td>
                                                        <td style="background-color: #E0E8F4"><bean:write name="person" property="TITLE" scope="page"/></td>
                                                        <td style="background-color: #E0E8F4"><bean:write name="person" property="EMAIL" scope="page"/></td>
                                                        <td style="background-color: #E0E8F4"><bean:write name="person" property="PHONE" scope="page"/></td>
                                                        
                                                        
                                                    </tr>
                                                </logic:equal>
                                                
                                                <logic:equal name="person" property="COLOR_CODE" value="1" scope="page">    
                                                    <tr>
                                                        <td><input type="radio" name="targPersonCannum"  id="targPersonCannum" value="<bean:write name="person" property="CANNUM" scope="page"/>"></td>
                                                        
                                                        <logic:equal name="person" property="CONTACT" value="1" scope="page">
                                                            <logic:equal name="person" property="CONTACT_ACTIVE" value="1" scope="page">
                                                            <td><font color="green">Active</font></td>
                                                            </logic:equal>
                                                            <logic:equal name="person" property="CONTACT_ACTIVE" value="0" scope="page">
                                                            <td><font color="red">Inactive</font></td>
                                                            </logic:equal>
                                                        </logic:equal>
                                                        
                                                        <logic:equal name="person" property="CONTACT" value="0" scope="page">
                                                        <td></td>    
                                                        </logic:equal>
                                                        
                                                        
                                                        <logic:equal name="person" property="AFFILIATE" value="1" scope="page">
                                                        <td><font color="green">X</font></td>    
                                                        </logic:equal>
                                                        
                                                        <logic:equal name="person" property="AFFILIATE" value="0" scope="page">
                                                        <td></td>    
                                                        </logic:equal>
                                                        
                                                        
                                                        <logic:equal name="person" property="UGA" value="1" scope="page">
                                                        <td><font color="green">X</font></td>    
                                                        </logic:equal>
                                                        
                                                        <logic:equal name="person" property="UGA" value="0" scope="page">
                                                        <td></td>    
                                                        </logic:equal>
                                                        
                                                        <td><bean:write name="person" property="LASTNAME" scope="page"/>, <bean:write name="person" property="FIRSTNAME" scope="page"/></td>
                                                        <td><bean:write name="person" property="MYID" scope="page"/></td>
                                                        <!--<td><bean:write name="person" property="CANNUM" scope="page"/></td>-->
                                                        <td><bean:write name="person" property="CONTACT_UGAID" scope="page"/></td>
                                                        <td><bean:write name="person" property="ORGANIZATION" scope="page"/></td>
                                                        <td><bean:write name="person" property="TITLE" scope="page"/></td>
                                                        <td><bean:write name="person" property="EMAIL" scope="page"/></td>
                                                        <td><bean:write name="person" property="PHONE" scope="page"/></td> 
                                                    
                                                    </tr>
                                                </logic:equal>
                                                
                                                
                                                
                                            </logic:iterate>
                                        </tbody>
                                    </table>
                                </div> 
                            </div>
                        </logic:notEqual>
                       
                            
                    </div>        
                    </logic:notEmpty>
                            
            </center>
            <%@include file="/WEB-INF/jspf/footer.jsp"%>
        </html:form>
        
    </body>
</html>
