<%-- 
    Document   : FindAssoc
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
    String uga_myid;
    String non_uga_email;
%>
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
                
                
                // check non UGA email
                
                var emailPattern = /^[a-zA-Z0-9\._-]+@([a-zA-Z0-9-]+\.)+[a-zA-Z]{2,4}$/;
                
                if($("#non_uga_email").val() === "")
                {
                    alert("Enter an email address.");
                    document.getElementById("non_uga_email").focus();
                    return false;
                } 
                
                else if($("#non_uga_email").val().match("[\\.@]uga\\.edu$"))
                {
                    alert("Please enter a non UGA email address.");
                    document.getElementById("non_uga_email").focus();
                    return false;
                } 
                else if(!emailPattern.test($("#non_uga_email").val()))
                {
                    alert("The email address entered does not match the standard email format.");
                    document.getElementById("non_uga_email").focus();
                    return false;
                }
                
                $("#action").val("search");
                $("#findAssocForm").submit();
            };
            
            function CancelExpDateBox()
            {
                $("#expdate_text").hide();
                $("#expdate_months_box").hide();
                $("#expdate_months_text").hide();
                $("#expdate_never_expires").hide();
                $("#expdate_never_expires_text").hide();
                $("#expdate_box_error").hide();
                $("#expdate_cancel").hide();
                $("#expdate_update").hide();
                $("#manage_expiration").show();
            };
            
            function CancelNAExpDateBox()
            {
                $("#na_expdate_text").hide();
                $("#na_expdate_months_box").hide();
                $("#na_expdate_months_text").hide();
                $("#na_expdate_never_expires").hide();
                $("#na_expdate_never_expires_text").hide();
                $("#na_expdate_box_error").hide();
                $("#na_expdate_cancel").hide();
                $("#na_expdate_update").hide();
                $("#create_associate_button").show();
            };
            
            function ManageExpirationDate(){
               $("#expdate_text").show();
               $("#expdate_months_box").show();
               $("#expdate_months_text").show();
               $("#expdate_never_expires").show();
               $("#expdate_never_expires_text").show();
               $("#expdate_cancel").show();
               $("#expdate_update").show();
               $("#manage_expiration").hide();
            };
            
            function expdate_checkbox_select(){
                
                if($("input:checked").val()==="never_expires")
                {
                    $("#expdate_months_box").prop('disabled', true);
                }
                else
                {    
                    $("#expdate_months_box").prop('disabled', false);
                    $("#expdate_months_box").focus();
                }
                
            }
            
            function na_expdate_checkbox_select(){
                
                if($("input:checked").val()==="na_never_expires")
                {
                    $("#na_expdate_months_box").prop('disabled', true);
                }
                else
                {    
                    $("#na_expdate_months_box").prop('disabled', false);
                    $("#na_expdate_months_box").focus();
                }
                
            }
            
            function CreateNewAssociate(){
               
               $("#create_associate_button").hide();
               $("#na_expdate_text").show();
               $("#na_expdate_months_box").show();
               $("#na_expdate_months_text").show();
               $("#na_expdate_never_expires").show();
               $("#na_expdate_never_expires_text").show();
               $("#na_expdate_cancel").show();
               $("#na_expdate_update").show();
               
            }
            
            function updateExpirationDate(){ 
                
                var intRegex = /[0-9 -()+]+$/;
                
                // check if the "never expires" box is checked
                
                if($("input:checked").val()==="never_expires")
                {
                    $("#expdate_box_error").hide();
                    $("#action").val("update_exp_date");
                    $("#never_expires").val("never_expires");
                    $("#expdate_months").val("NA");
                    $("#findAssocForm").submit();
                }
                else
                {   
                    // validate number of months as numeric 
                    if($("#expdate_months_box").val().match(intRegex))
                    {    
                        $("#expdate_box_error").hide();
                        $("#action").val("update_exp_date");
                        $("#expdate_months").val($("#expdate_months_box").val());
                        $("#never_expires").val("NA");
                        $("#findAssocForm").submit();
                    }
                    else
                        $("#expdate_box_error").show();
                }    
            };
            
            function updateNAExpirationDate(){ 
                var intRegex = /[0-9 -()+]+$/;
                
                // check if the "never expires" box is checked
                
                if($("input:checked").val()==="na_never_expires")
                {
                    $("#na_expdate_box_error").hide();
                    $("#action").val("na_update_exp_date");
                    $("#na_never_expires").val("na_never_expires");
                    $("#na_expdate_months").val("NA");
                    $("#findAssocForm").submit();
                }
                else
                {   
                    // validate number of months as numeric 
                    if($("#na_expdate_months_box").val().match(intRegex))
                    {    
                        $("#na_expdate_box_error").hide();
                        $("#action").val("na_update_exp_date");
                        $("#na_expdate_months").val($("#na_expdate_months_box").val());
                        $("#na_never_expires").val("NA");
                        $("#findAssocForm").submit();
                    }
                    else
                        $("#na_expdate_box_error").show();
                }    
            };
            
        </script>
    </head>
    <body>
        <html:form action="FindAssoc.do" styleId="findAssocForm">
            <html:hidden styleId="action" property="action"/>
            <html:hidden styleId="expdate_months" property="expdate_months"/>
            <html:hidden styleId="never_expires" property="never_expires"/>
            <html:hidden styleId="na_expdate_months" property="na_expdate_months"/>
            <html:hidden styleId="na_never_expires" property="na_never_expires"/>
            <html:hidden styleId="can_be_associate" property="can_be_associate"/>
            <html:hidden styleId="is_associate" property="is_associate"/>
            <%@include file="/WEB-INF/jspf/header.jsp"%>
            <%@include file="/WEB-INF/jspf/header_login.jsp"%>
            <bean:define id="uga_myid" name="findAssocForm" property="uga_myid" scope="session" toScope="page" type="String"/>
            <bean:define id="non_uga_email" name="findAssocForm" property="non_uga_email" scope="session" toScope="page" type="String"/>
            
            <bean:define id="exp_date" name="findAssocForm" property="exp_date" scope="session" toScope="page" type="String"/>
            
            <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Create New Associate</h3>
                    <hr/>
                    <logic:notEmpty name="errorMsg" scope="request">
                        <div>
                            <b style="color: red;"><u><bean:write name="errorMsg" scope="request"/></u></b>
                        </div>
                    </logic:notEmpty>
                    
                    <!-- message on successful database update -->
                    <logic:notEmpty name="successMsg" scope="request">
                        <div>
                            <b style="color: green;"><u><bean:write name="successMsg" scope="request"/></u></b>
                        </div>
                    </logic:notEmpty>
                    
                    
                    <div style="text-align: left;">
                        <div style="text-align: right;">
                            <a href="./Get.do?action=home">Main Menu</a>
                        </div>
                        
                        
                <table>
                    <tr>
                        <td>
                            Enter the Associate's UGA MyID: 
                        </td>
                        <td>
                            <input type="text" id="uga_myid" name="uga_myid" size="32" maxlength="128" value="<%=uga_myid%>"/>
                        </td>
                    </tr>
                    
                    <tr>
                        <td>
                            Enter the Associate's non UGA email address: 
                        </td>
                        <td>
                            <input type="text" id="non_uga_email" name="non_uga_email" size="64" maxlength="128" value="<%=non_uga_email%>"/>
                        </td>
                    </tr>
                    
                    
                </table>
                <br><button id="submit" onclick="SubmitForm();return false;">Search MyID</button>
                &nbsp;&nbsp;
               
                <logic:notEmpty name="findAssocForm" property="results" scope="session">   
                    <bean:define id="assocInfo" name="findAssocForm" property="results" scope="session" toScope="page" type="java.util.HashMap"/>
                    <br><br>
                    <b>Detailed Information for UGA MyID: "<bean:write name="findAssocForm" property="uga_myid" scope="session"/>" </b>
                    <br>
                    
                    <!-- print the status of the person, already an associate, eligible/not if not already an associate -->
                    <br>
                    <logic:notEmpty name="associateStatus" scope="request">
                        
                        <div>
                            <b style="color: blue;"><u><bean:write name="associateStatus" scope="request"/></u></b>
                        </div>
                    
                    </logic:notEmpty>
                    
                    <logic:notEmpty name="non_uga_email_msg" scope="request">
                    
                        <logic:equal name="non_uga_email_msg" value="Non UGA email address is already in use" scope="request">    
                        <div>
                            <b style="color: red;"><u><bean:write name="non_uga_email_msg" scope="request"/></u></b>
                        </div>
                        
                        </logic:equal>
                    
                        <logic:equal name="non_uga_email_msg" value="Non UGA email address is valid" scope="request">    
                        <div>
                            <b style="color: blue;"><u><bean:write name="non_uga_email_msg" scope="request"/></u></b>
                        </div>
                        
                        </logic:equal>
                        
                    </logic:notEmpty>
                    
                    <br>
                    
                    <table>
                        <tr>
                            <td>
                                <b>Name:</b>
                            </td>
                            <td>
                                <bean:write name="assocInfo" property="LASTNAME" scope="page"/>,
                                <bean:write name="assocInfo" property="FIRSTNAME" scope="page"/>
                                <bean:write name="assocInfo" property="MIDDLENAME" scope="page"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <b>Email:</b>
                            </td>
                            <td>
                                <bean:write name="assocInfo" property="EMAIL" scope="page"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <b>MYID:</b>
                            </td>
                            <td>
                                <bean:write name="assocInfo" property="MYID" scope="page"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <b>UGAID:</b>
                            </td>
                            <td>
                                <logic:empty name="assocInfo" property="CANNUM" scope="page">
                                    -
                                </logic:empty>
                                <bean:write name="assocInfo" property="CANNUM" scope="page"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <b>Title:</b>
                            </td>
                            <td>
                                <logic:empty name="assocInfo" property="TITLE" scope="page">
                                    -
                                </logic:empty>
                                <logic:notEmpty name="assocInfo" property="TITLE" scope="page">
                                    <bean:write name="assocInfo" property="TITLE" scope="page"/>
                                </logic:notEmpty>
                            </td>
                        </tr>
                        
                        <tr>
                            <td>
                                <b>Job Class:</b>
                            </td>
                            <td>
                                <logic:empty name="assocInfo" property="jobClass" scope="page">
                                    -
                                </logic:empty>
                                <logic:notEmpty name="assocInfo" property="jobClass" scope="page">
                                    <bean:write name="assocInfo" property="jobClass" scope="page"/>
                                </logic:notEmpty>
                            </td>
                        </tr>
                        
                        <tr>
                            <td>
                                <b>Job Title:</b>
                            </td>
                            <td>
                                <logic:empty name="assocInfo" property="jobTitle" scope="page">
                                    -
                                </logic:empty>
                                <logic:notEmpty name="assocInfo" property="jobTitle" scope="page">
                                    <bean:write name="assocInfo" property="jobTitle" scope="page"/>
                                </logic:notEmpty>
                            </td>
                        </tr>
                        
                        <tr>
                            <td>
                                <b>Termination Date:</b>
                            </td>
                            <td>
                                <logic:empty name="assocInfo" property="termDate" scope="page">
                                    -
                                </logic:empty>
                                <logic:notEmpty name="assocInfo" property="termDate" scope="page">
                                    <bean:write name="assocInfo" property="termDate" scope="page"/>
                                </logic:notEmpty>
                            </td>
                        </tr>
                        
                        <tr>
                            <td>
                                <b>Department:</b>
                            </td>
                            <td>
                                <logic:empty name="assocInfo" property="homeDept" scope="page">
                                    -
                                </logic:empty>
                                <logic:notEmpty name="assocInfo" property="homeDept" scope="page">
                                    <bean:write name="assocInfo" property="homeDept" scope="page"/>
                                </logic:notEmpty>
                            </td>
                        </tr>
                        <tr>
                            <td valign="top">
                                <b>Address:</b>
                            </td>
                            <td>
                                <bean:write name="assocInfo" property="addr1" scope="page"/>
                                <br>
                                <logic:notEmpty name="assocInfo" property="addr2" scope="page">
                                    <bean:write name="assocInfo" property="addr2" scope="page"/>
                                    <br>
                                </logic:notEmpty>
                                <bean:write name="assocInfo" property="city" scope="page"/>,
                                <bean:write name="assocInfo" property="state" scope="page"/>
                                <bean:write name="assocInfo" property="zip" scope="page"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <b>Phone number:</b>
                            </td>
                            <td>
                                <bean:write name="assocInfo" property="PHONE" scope="page"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <b>Alternate Phone:</b>
                            </td>
                            <td>
                                <bean:write name="assocInfo" property="ALTPHONE" scope="page"/>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <b>Fax Number:</b>
                            </td>
                            <td>
                                <bean:write name="assocInfo" property="FAX" scope="page"/>
                            </td>
                        </tr>
                        
                        <tr>
                            <td>
                                <b>Is Enrolled:</b>
                            </td>
                            <td>
                                <bean:write name="assocInfo" property="isEnrolled" scope="page"/>
                            </td>
                        </tr>
                        
                        <tr>
                            <td>
                                <b>HR Status:</b>
                            </td>
                            <td>
                                <bean:write name="assocInfo" property="ugaHRStatus" scope="page"/>
                            </td>
                        </tr>
                        
                        <tr>
                            <td>
                                <b>Last Pay Date:</b>
                            </td>
                            <td>
                                <bean:write name="assocInfo" property="ugaLastPayDate" scope="page"/>
                            </td>
                        </tr>
                        
                        <tr>
                            <td>
                                <b>Term Credit Hours:</b>
                            </td>
                            <td>
                                <bean:write name="assocInfo" property="ugaTermCreditHours" scope="page"/>
                            </td>
                        </tr>
                        
                        <tr>
                            <td>
                                <b>Inactive Date:</b>
                            </td>
                            <td>
                                <bean:write name="assocInfo" property="ugaInactiveDate" scope="page"/>
                            </td>
                        </tr>
                        
                        
                        <tr>
                            <td>
                                <b>FSCODES:</b>
                            </td>
                            <td>
                                <logic:empty name="assocInfo" property="ESCODES" scope="page">
                                    -
                                </logic:empty>
                                <logic:notEmpty name="assocInfo" property="ESCODES" scope="page">
                                    <bean:write name="assocInfo" property="ESCODES" scope="page"/>
                                </logic:notEmpty>
                            </td>
                        </tr>
                        
                        <!-- if an existing associate then show the expiration date otherwise not -->
                        <tr>
                        <logic:equal name="existingAssociate" scope="request" value="yes">
                            <td>
                                <b>Expiration Date:</b>
                            </td>
                            <td style="color: blue">
                                <bean:write name="findAssocForm" property="exp_date" scope="session"/>
                            </td>
                        </tr>    
                        
                        <!-- manage expiration date button for existing affiliates -->
                        <tr>    
                            <td><br>
                                <button id="manage_expiration" onclick="ManageExpirationDate();return false;">Manage Expiration Date</button>
                            <br></td>
                            
                        </logic:equal>
                        </tr>
                        
                        <!-- button to create new associate -->
                        
                        <tr>
                            <td><br>
                                <button id="create_associate_button" style="display:none" onclick="CreateNewAssociate();return false;">Create New Associate</button>
                            </td>
                        </tr>        
                        
                        <logic:equal name="non_uga_email_msg" value="Non UGA email address is already in use" scope="request">
                            <script>
                                document.getElementById('create_associate_button').disabled = true;
                            </script>
                        </logic:equal>
                            
                    </table>
                              
                <!-- existing associate dialog -->
               
                <div id="expdate_text" style="display:none" align="left"><p><b>Set the associate's approval expiration date to today's date plus the number of months you define then click the "Update Expiration Date" button</b></p></div>
                <input id="expdate_months_box" name="expdate_months_box" style="display:none" type="number" min="1" size="8" maxlength="8" /> 
                <text id="expdate_months_text" style="display:none">Month(s) <b>OR</b></text>
                <input type="checkbox" id="expdate_never_expires" onchange="expdate_checkbox_select();return false" style="display:none" value="never_expires">
                <text id="expdate_never_expires_text" style="display:none">Never Expires<br></text>
                <br>        
                <p><text id="expdate_box_error" style="display:none; color: Red">
                    <b>Please enter a valid number of months</b></text></p>
                <button id="expdate_update" style="display:none" onclick="updateExpirationDate();return false">Submit</button>
                <button id="expdate_cancel" style="display:none" onclick="CancelExpDateBox();return false">Cancel</button>
                            
                <!-- create new associate dialog -->
                
                <div id="na_expdate_text" style="display:none" align="left"><p><b>Set the new associate's account expiration date to today's date plus the number of months you select, then click the "Submit" button</b></p></div>
                <input id="na_expdate_months_box" name="na_expdate_months_box" style="display:none" type="number" min="1" size="8" maxlength="8" />
                <text id="na_expdate_months_text" style="display:none">Month(s) <b>OR</b></text>
                <input type="checkbox" id="na_expdate_never_expires" onchange="na_expdate_checkbox_select();return false" style="display:none" value="na_never_expires">
                <text id="na_expdate_never_expires_text" style="display:none">Never Expires<br></text>
                <br>
                <p><text id="na_expdate_box_error" style="display:none; color: Red">
                    <b>Please enter a valid number of months</b></text></p>
                <button id="na_expdate_update" style="display:none" onclick="updateNAExpirationDate();return false">Submit</button>
                <button id="na_expdate_cancel" style="display:none" onclick="CancelNAExpDateBox();return false">Cancel</button>
                
                
                </logic:notEmpty>     
                
                <script>
                    $("#create_associate_button").hide();
                    var a = $("#can_be_associate").val();
                    var b = $("#is_associate").val();
                    if((a==="eligible")&&(b==="no"))
                        $("#create_associate_button").show();
                    else
                        $("#create_associate_button").hide();   
                    
                </script>
                
                
            </div>
            
            </center>
            <%@include file="/WEB-INF/jspf/footer.jsp"%>
        </html:form>
    </body>
</html>
