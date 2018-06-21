<%-- 
    Document   : ControlPurpose
    Created on : May 2, 2013, 5:26:10 PM
    Author     : submyers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html>
<%! String title, purposeId;%>
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
            
            var holdPeriod = "1";
            
            function OpenModPurpDialog(purposeId,shortName,description,active,approved,period){
                $("#purposeId").val(purposeId);
                $("#modShortNameInput").val(shortName);
                $("#modDescriptionInput").html(description);
                $("#modActiveInput").val(active);
                $("#modApprovedInput").val(approved);
                if(period === "-1"){
                    holdPeriod = "1";
                    $("#modPeriodInput").val("");
                    $("#modPeriodInput").attr("readonly","readonly");
                    $("#modPeriodNotExpire").attr("checked","checked");
                } else {
                    holdPeriod = period;
                    $("#modPeriodInput").val(period);
                    $("#modPeriodInput").removeAttr("readonly");
                    $("#modPeriodNotExpire").removeAttr("checked");
                }
                $("#modify_purpose_dialog").dialog("open");
            }
            
            function OpenDeactivatePurpose(purposeId,shortName){
                $("#purposeId").val(purposeId);
                $("#targShortName").val(shortName);
                $("#deactivate_purpose_short_name").html(shortName);
                $("#deactivate_purpose_dialog").dialog("open");
            }
            
            function ReactivatePurpose(purposeId,shortName){
                $("#purposeId").val(purposeId);
                $("#targShortName").val(shortName);
                $("#action").val("ReactivatePurpose");
                $("#controlPurposesForm").submit();
            }
            
            $(document).ready(function(){
                $("#modify_purpose_dialog").dialog({
                    autoOpen: false,
                    width: 500,
                    modal: true,
                    resizable: false,
                    minWidth: 370,
                    maxWidth: 500
                });
                
                $("#add_purpose_dialog").dialog({
                    autoOpen: false,
                    width: 500,
                    modal: true,
                    resizable: false,
                    minWidth: 370,
                    maxWidth: 500
                });
                
                $("#deactivate_purpose_dialog").dialog({
                    autoOpen: false,
                    width: 500,
                    modal: true,
                    resizable: false,
                    minWidth: 370,
                    maxWidth: 500
                });

                addEventListener("keypress", function(event){
                    if(event.which == 13){
                        $("#searchBtn").click();
                    }
                });
                $("#searchBtn").click(function(){
                    $("#action").val("Search");
                    $("#controlPurposesForm").submit();
                });
                $("#resetBtn").click(function(){
                    $("#action").val("Reset");
                    $("#controlPurposesForm").submit();
                });
                $("#firstLink").click(function(){
                    $("#action").val("First");
                    $("#controlPurposesForm").submit();
                });
                $("#prevLink").click(function(){
                    $("#action").val("Prev");
                    $("#controlPurposesForm").submit();
                });
                $("#nextLink").click(function(){
                    $("#action").val("Next");
                    $("#controlPurposesForm").submit();
                });
                $("#lastLink").click(function(){
                    $("#action").val("Last");
                    $("#controlPurposesForm").submit();
                });
                $("#backLink").click(function(){
                    $("#action").val("Back");
                    $("#controlPurposesForm").submit();
                });
                $("#cancelBtn").click(function(){
                    $("#action").val("Cancel");
                    $("#controlPurposesForm").submit();
                });
                $("#modify_purpose_dialog_submit").click(function(){
                    var period = "";
                    if($("#modPeriodNotExpire").is(":checked")){
                        period = "-1";
                    } else if(!$("#modPeriodInput").val().match("^\\d+$")){
                        alert("You must enter an integer for the number of months > 0.");
                        return false;
                    } else if($("#modPeriodInput").val() <= 0){
                        alert("You must define the period as a number of months > 0.");
                        return false;
                    } else {
                        period = $("#modPeriodInput").val();
                    }
                    
                    $("#newShortName").val($("#modShortNameInput").val());
                    $("#newDescription").val($("#modDescriptionInput").val());
                    $("#newApproved").val($("#modApprovedInput").val());
                    $("#newPeriod").val(period);
                    $("#action").val("UpdatePurpose");
                    $("#controlPurposesForm").submit();
                });
                $("#modify_purpose_dialog_cancel").click(function(){
                    $("#modify_purpose_dialog").dialog("close");
                });
                $("#addPurpBtn").click(function(){
                    holdPeriod = "1";
                    $("#addPeriodInput").val(holdPeriod);
                    $("#addPeriodInput").removeAttr("readonly");
                    $("#addPeriodNotExpire").removeAttr("checked");
                    $("#add_purpose_dialog").dialog("open");
                });
                $("#add_purpose_dialog_submit").click(function(){
                    var period = "";
                    if($("#addPeriodNotExpire").is(":checked")){
                        period = "-1";
                    } else if(!$("#addPeriodInput").val().match("^\\d+$")){
                        alert("You must enter an integer for the number of months > 0.");
                        return false;
                    } else if($("#addPeriodInput").val() <= 0){
                        alert("You must define the period as a number of months > 0.");
                        return false;
                    } else {
                        period = $("#addPeriodInput").val();
                    }
                    
                    $("#newShortName").val($("#addShortNameInput").val());
                    $("#newDescription").val($("#addDescriptionInput").val());
                    $("#newApproved").val($("#addApprovedInput").val());
                    $("#newPeriod").val(period);
                    $("#action").val("AddPurpose");
                    $("#controlPurposesForm").submit();
                });
                $("#add_purpose_dialog_cancel").click(function(){
                    $("#add_purpose_dialog").dialog("close");
                });
                $("#deactivate_purpose_dialog_submit").click(function(){
                    $("#action").val("DeactivatePurpose");
                    $("#controlPurposesForm").submit();
                });
                $("#deactivate_purpose_dialog_cancel").click(function(){
                    $("#addPeriodNotExpire").removeAttr("checked");
                    $("#addPeriodInput").val("1");
                    $("#addPeriodInput").removeAttr("readonly");
                    $("#deactivate_purpose_dialog").dialog("close");
                });
                $("#modPeriodNotExpire").click(function(){
                    if($("#modPeriodNotExpire").is(":checked")){
                        holdPeriod = $("#modPeriodInput").val();
                        $("#modPeriodInput").val("");
                        $("#modPeriodInput").attr("readonly","readonly");
                    } else {
                        $("#modPeriodInput").val(holdPeriod);
                        $("#modPeriodInput").removeAttr("readonly")
                    }
                });
                $("#addPeriodNotExpire").click(function(){
                    if($("#addPeriodNotExpire").is(":checked")){
                        holdPeriod = $("#addPeriodInput").val();
                        $("#addPeriodInput").val("");
                        $("#addPeriodInput").attr("readonly","readonly");
                    } else {
                        $("#addPeriodInput").val(holdPeriod);
                        $("#addPeriodInput").removeAttr("readonly")
                    }
                });
            });
        </script>
    </head>
    <body>
        <html:form styleId="controlPurposesForm" action="ControlPurposes.do">
            <%@include file="/WEB-INF/jspf/header.jsp"%>
            <%@include file="/WEB-INF/jspf/header_login.jsp"%>            
            <html:hidden styleId="action" property="action"/>
            <html:hidden styleId="purposeId" property="purposeId"/>
            <html:hidden styleId="targShortName" property="targShortName"/>
            <html:hidden styleId="newShortName" property="newShortName"/>
            <html:hidden styleId="newDescription" property="newDescription"/>
            <html:hidden styleId="newApproved" property="newApproved"/>
            <html:hidden styleId="newPeriod" property="newPeriod"/>
            <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Manage Purposes</h3>
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
                <h5>Search Purposes:</h5>
                <table class="addAPOC_table">
                    <tbody>
                        <tr>
                            <td>Short Name:</td>
                            <td><html:text property="shortName" maxlength="64" size="32"/></td>
                            <td><html:radio value="Begins" property="shortNameOpt">Begins with</html:radio></td>
                            <td><html:radio value="Contains" property="shortNameOpt">Contains</html:radio></td>
                            <td><html:radio value="Exact" property="shortNameOpt">Exact</html:radio></td>                                
                        </tr>
                        <tr>
                            <td>Description:</td>
                            <td><html:text property="description" maxlength="64" size="32"/></td>
                            <td><html:radio value="Begins" property="descriptionOpt">Begins with</html:radio></td>
                            <td><html:radio value="Contains" property="descriptionOpt">Contains</html:radio></td>
                            <td><html:radio value="Exact" property="descriptionOpt">Exact</html:radio></td>                                
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
                                <button id="searchBtn" onclick="return false;">Search</button>
                                &nbsp;&nbsp;
                                <button id="resetBtn" onclick="return false;">Reset</button>
                                &nbsp;&nbsp;
                                <button id="cancelBtn" onclick="return false;">Cancel</button>
                                &nbsp;&nbsp;
                                <button id="addPurpBtn" onclick="return false;">New Purpose</button>
                            </td>
                        </tr>
                    </tbody>                  
                </table> 

                <br><br>

                <logic:equal name="controlPurposesForm" property="count" value="0" scope="session">
                    <h5>Search Results</h5>
                    None
                </logic:equal> 
                <logic:notEqual name="controlPurposesForm" property="count" value="0" scope="session">
                    <h5>Search Results</h5>
                    <div id="searchResults">
                        Page <bean:write name="controlPurposesForm" property="displayPageNum" scope="session"/> of <bean:write name="controlPurposesForm" property="displayPageCount" scope="session"/>
                        <br>
                        Number of Results: <bean:write name="controlPurposesForm" property="count" scope="session"/>
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
                                        <th>Short Name</th>
                                        <th>Description</th>
                                        <th>Approved</th>
                                        <th>Period (Months)</th>
                                        <th>Active Affiliates</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <logic:iterate id="purpose" name="purposes" scope="request">
                                        <tr>
                                            <td>
                                                <a href="#" onclick="OpenModPurpDialog('<bean:write name="purpose" property="ID" scope="page"/>','<bean:write name="purpose" property="SHORT_NAME" scope="page"/>','<bean:write name="purpose" property="DESCRIPTION" scope="page"/>','<bean:write name="purpose" property="ACTIVE" scope="page"/>','<bean:write name="purpose" property="APPROVED" scope="page"/>','<bean:write name="purpose" property="PERIOD" scope="page"/>');return false;">
                                                    <bean:write name="purpose" property="SHORT_NAME" scope="page"/>
                                                </a>
                                            </td>
                                            <td><bean:write name="purpose" property="DESCRIPTION" scope="page"/></td>
                                            <td>
                                                <logic:equal name="purpose" property="ACTIVE" scope="page" value="0">
                                                    Inactive
                                                </logic:equal>
                                                <logic:notEqual name="purpose" property="ACTIVE" scope="page" value="0">
                                                    <bean:write name="purpose" property="APPROVED" scope="page"/>
                                                </logic:notEqual>
                                            </td>
                                            <td>
                                                <logic:equal name="purpose" property="PERIOD" scope="page" value="-1">
                                                    <b>Does not expire</b>
                                                </logic:equal>
                                                <logic:notEqual name="purpose" property="PERIOD" scope="page" value="-1">
                                                    <bean:write name="purpose" property="PERIOD" scope="page"/>
                                                </logic:notEqual>
                                            </td>
                                            <td>
                                                <bean:write name="purpose" property="AFF_COUNT" scope="page"/>
                                            </td>
                                            <td>
                                                <logic:equal name="purpose" property="AFF_COUNT" scope="page" value="0">
                                                    <logic:equal name="purpose" property="ACTIVE" scope="page" value="0">
                                                        <a href="#" id="reactivate_link" onclick="ReactivatePurpose('<bean:write name="purpose" property="ID" scope="page"/>','<bean:write name="purpose" property="SHORT_NAME" scope="page"/>');return false;">Reactivate</a>
                                                    </logic:equal>
                                                    <logic:notEqual name="purpose" property="ACTIVE" scope="page" value="0">
                                                        <a href="#" id="deativate_link" onclick="OpenDeactivatePurpose('<bean:write name="purpose" property="ID" scope="page"/>','<bean:write name="purpose" property="SHORT_NAME" scope="page"/>');return false;" style="color: red">Deactivate</a>
                                                    </logic:notEqual>
                                                </logic:equal>
                                                <logic:notEqual name="purpose" property="AFF_COUNT" scope="page" value="0">
                                                    -
                                                </logic:notEqual>
                                            </td>
                                        </tr>
                                    </logic:iterate>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </logic:notEqual>
            </div>
            </center>
            <%@include file="/WEB-INF/jspf/footer.jsp"%>
            <div id="modify_purpose_dialog" title="Modify Purpose" style="display: none; font-size: 12px; text-align: center;">
                <div style="text-align: left;">
                    Change purpose values then click "Submit"
                    <br><br>
                    <table>
                        <tr>
                            <td>Short Name:</td>
                            <td><input id="modShortNameInput" type="text"></td>
                        </tr>
                        <tr>
                            <td>Description:</td>
                            <td><textarea id="modDescriptionInput"></textarea></td>
                        </tr>
                        <tr>
                            <td>Approved:</td>
                            <td>
                                <select id="modApprovedInput">
                                    <option id="modApprovedInputSubmitted" value="Submitted">Submitted</option>
                                    <option id="modApprovedInputApproved" value="Approved">Approved</option>
                                    <option id="modApprovedInputRejected" value="Rejected">Rejected</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>Period:</td>
                            <td>
                                <input id="modPeriodInput" type="text" size="5" maxlength="5">
                                <b>OR</b>
                                <input id="modPeriodNotExpire" type="checkbox"> Does not expire
                            </td>
                        </tr>
                    </table>
                    <button id="modify_purpose_dialog_submit" onclick="return false;">Submit</button>
                    &nbsp;&nbsp;
                    <button id="modify_purpose_dialog_cancel" onclick="return false;">Cancel</button>
                </div>
            </div>
            <div id="add_purpose_dialog" title="New Purpose" style="display: none; font-size: 12px; text-align: center;">
                <div style="text-align: left;">
                    Define purpose values then click "Submit"
                    <br><br>
                    <table>
                        <tr>
                            <td>Short Name:</td>
                            <td><input id="addShortNameInput" type="text"></td>
                        </tr>
                        <tr>
                            <td>Description:</td>
                            <td><textarea id="addDescriptionInput"></textarea></td>
                        </tr>
                        <tr>
                            <td>Approved:</td>
                            <td>
                                <select id="addApprovedInput">
                                    <option id="addApprovedInputSubmitted" value="Submitted">Submitted</option>
                                    <option id="addApprovedInputApproved" value="Approved">Approved</option>
                                    <option id="addApprovedInputRejected" value="Rejected">Rejected</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>Period:</td>
                            <td>
                                <input id="addPeriodInput" type="text" size="5" maxlength="5">
                                <b>OR</b>
                                <input id="addPeriodNotExpire" type="checkbox"> Does not expire
                            </td>
                        </tr>
                    </table>
                    <button id="add_purpose_dialog_submit" onclick="return false;">Submit</button>
                    &nbsp;&nbsp;
                    <button id="add_purpose_dialog_cancel" onclick="return false;">Cancel</button>
                </div>
            </div>
            <div id="deactivate_purpose_dialog" title="Deactivate Purpose" style="display: none; font-size: 12px; text-align: center;">
                Are you sure you want to deactivate purpose "<span id="deactivate_purpose_short_name"></span>"?
                <br><br>
                <button id="deactivate_purpose_dialog_submit" onclick="return false">Submit</button>
                &nbsp;&nbsp;
                <button id="deactivate_purpose_dialog_cancel" onclick="return fales">Cancel</button>
            </div>
        </html:form>
    </body>
</html>
