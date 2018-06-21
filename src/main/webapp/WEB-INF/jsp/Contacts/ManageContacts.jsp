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
                   
                    $("#action").val("Search");
                    $("#manageContactsForm").submit();
                });
                $("#firstLink").click(function(){
                    $("#action").val("First");
                    $("#manageContactsForm").submit();
                });
                $("#prevLink").click(function(){
                    $("#action").val("Prev");
                    $("#manageContactsForm").submit();
                });
                $("#nextLink").click(function(){
                    $("#action").val("Next");
                    $("#manageContactsForm").submit();
                });
                $("#lastLink").click(function(){
                    $("#action").val("Last");
                    $("#manageContactsForm").submit();
                }); 
                $("#resetBtn").click(function(){
                    $("#action").val("Reset");
                    $("#manageContactsForm").submit();
                });
                $("#submitBtn").click(function(){
                    if(($('input[name=targPersonCannum]:checked', '#manageContactsForm').val()) === undefined){
                        alert("Select a Contact.");
                        return false;
                    }
                    
                    $("#action").val("EditContact");
                    $("#targPersonCannum").val($('input[name=targPersonCannum]:checked', '#manageContactsForm').val());
                    $("#manageContactsForm").submit();
                });
            });
        </script>
        
    </head>
    <body>
        <html:form action="ManageContacts.do" styleId="manageContactsForm">
            <html:hidden styleId="action" property="action"/>
            <html:hidden styleId="targPersonCannum" property="targPersonCannum"/>
            <%@include file="/WEB-INF/jspf/header.jsp"%>
            <%@include file="/WEB-INF/jspf/header_login.jsp"%>
            <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>
                    <h3>Manage Contacts</h3> 
                    <hr>
                    
                    <logic:notEmpty name="returnMsg" scope="request">
                        <div>
                            <logic:equal name="returnMsg" value="1" scope="request">
                            <b style="color: green;"><u>Contact Information Updated</u></b>
                            </logic:equal>
                            
                            <logic:equal name="returnMsg" value="2" scope="request">
                            <b style="color: green;"><u>Successfully updated the status(ACTIVE/INACTIVE) of the Contact record</u></b>
                            </logic:equal>
                            
                            <logic:equal name="returnMsg" value="0" scope="request">
                            <b style="color: red;"><u>Error updating Contact Information</u></b>
                            </logic:equal>
                        </div>
                    </logic:notEmpty>
                    
                    <div>
                        <div style="text-align: right;">
                            <a href="./Get.do?action=home">Main Menu</a>
                        </div>
                    </div>    
                    
                    <div style="text-align: left;">
                        <table>
                            <tr><td><h5>Search Contacts:</h5></td></tr>
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
                                <td>Organization:</td>
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
                            
                            <tr>
                                <td colspan="5">
                                Include Inactive:&nbsp;&nbsp;
                                <logic:equal name="manageContactsForm" property="showInactive" scope="session" value="Yes">
                                    <input type="radio" name="showInactive" value="Yes" checked> Yes&nbsp;&nbsp;
                                    <input type="radio" name="showInactive" value="No"> No
                                </logic:equal>
                                <logic:equal name="manageContactsForm" property="showInactive" scope="session" value="No">
                                    <input type="radio" name="showInactive" value="Yes"> Yes&nbsp;&nbsp;
                                    <input type="radio" name="showInactive" value="No" checked> No
                                </logic:equal>
                                </td>
                            </tr>
                            
                        </table>
                    </div>
                            
                    <br>
                    
                    <logic:notEmpty name="manageContactsForm" property="executed" scope="session">
                        <div style="text-align: left;">
                            
                            <logic:equal name="manageContactsForm" property="count" value="0" scope="session">
                            <h5>Search Results</h5>
                            None
                            <br>
                            </logic:equal>
                            
                            <logic:notEqual name="manageContactsForm" property="count" value="0" scope="session">
                              <h5>Search Results</h5>
                              <div id="searchResults">
                                  Number of Results: <bean:write name="manageContactsForm" property="count" scope="session"/>
                              </div>
                              <br>
                              <div>
                              <button id="submitBtn" onclick="return false">Edit Selected Contact</button>            
                              </div>
                              <br>
                              <div>
                              <a id="firstLink" href="#" onclick="return false;">First</a> &nbsp;
                              <a id="prevLink" href="#" onclick="return false;">Prev</a> &nbsp;
                              <a id="nextLink" href="#" onclick="return false;">Next</a> &nbsp;
                              <a id="lastLink" href="#" onclick="return false;">Last</a> &nbsp;  
                              </div>
                              <div class="tableContainer">
                                  <table class="paulaQueryTable">
                                      <thead>
                                          <tr>
                                              <th></th>
                                              <th>Full Name</th>
                                              <th>UGAID PRIME</th>
                                              <th>UGAID</th>
                                              <th>ACTIVE</th>
                                              <th>ORGANIZATION</th>
                                              <th>TITLE</th>
                                              <th>EMAIL</th>
                                              <th>PHONE</th>
                                          </tr>
                                          
                                      </thead>
                                      <tbody>
                                          <logic:iterate id="person" name="queryResults" scope="request">
                                              <tr>
                                              <td style="background-color: #E0E8F4"><input type="radio" name="targPersonCannum"  id="targPersonCannum" value="<bean:write name="person" property="CONTACT_UGAID" scope="page"/>"></td>
                                              <td style="background-color: #E0E8F4"><bean:write name="person" property="LASTNAME" scope="page"/>, <bean:write name="person" property="FIRSTNAME" scope="page"/></td>
                                              <td style="background-color: #E0E8F4"><bean:write name="person" property="CANNUM" scope="page"/></td>
                                               <td style="background-color: #E0E8F4"><bean:write name="person" property="CONTACT_UGAID" scope="page"/></td>
                                               
                                               
                                               <td style="background-color: #E0E8F4">
                                                    <logic:equal name="person" property="ACTIVE" scope="page" value="1">
                                                        X
                                                    </logic:equal>
                                               </td>
                                               
                                               <td style="background-color: #E0E8F4"><bean:write name="person" property="ORGANIZATION" scope="page"/></td>
                                               <td style="background-color: #E0E8F4"><bean:write name="person" property="TITLE" scope="page"/></td>
                                               <td style="background-color: #E0E8F4"><bean:write name="person" property="EMAIL" scope="page"/></td>
                                               <td style="background-color: #E0E8F4"><bean:write name="person" property="PHONE" scope="page"/></td>

                                              </tr>
                                          </logic:iterate>
                                          
                                                  
                                      </tbody>
                                  </table>
                              </div>
                            </logic:notEqual>
                            
                        </div>
                      
                        
                    </logic:notEmpty>        
                    
            </center>
            <%@include file="/WEB-INF/jspf/footer.jsp"%>
        </html:form>
        
    </body>
</html>
