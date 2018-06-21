<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="/WEB-INF/tags-compress" prefix="compress" %>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String aplId, propId, purposeId, oddEven, permissionFound, currColorCode, manOptOpen, purposeOptOpen;%>
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
            var purposeCount = 0;
            var purposeTotal = 0;
            var currTargUserId = "";
            var currTargPurposeId = "";

            function RemovePurpose(purposeId){
                $("#selected_" + purposeId).css("display","none");
                $("#selected_" + purposeId).removeAttr("refId");
                $("#purpose_details_" + purposeId).css("display","none");
                $("#" + purposeId ).removeAttr('disabled');
                $("#" + purposeId ).css("display","");
                purposeCount--;
                purposeTotal++;
                $("#purposeDiv").css("display","");
                if(purposeCount <= 0){
                    purposeCount = 0;
                    //$("#selectedPurposeDiv").html("<br>");
                    $("#selectedPurposeDiv").css("display","none");
                    $("#purpose_title").css("padding-top","2px");
                }
            }
            function AffDetails(userId){
                $("#targUserId").val(userId);
                $("#action").val("openAffPage");
                $("#viewManCurrAff").submit();
            }
            
            $(document).ready(function(){
                                
                if(purposeOptOpen == "1"){
                    $("#purposes").slideToggle("fast");
                    $("#purpose_title").css("padding-top","5px");
                    $("#selectedPurposeDiv").css("display","");
                }
                
                if(purposeTotal==0){
                    $("#purposeDiv").css("display","none");
                }
                
                $("#backLink").click(function(){
                    $("#action").val("back");
                    $("#viewManCurrAff").submit();
                });
                
                $("#submitBtn").click(function(){
                    var purposeIds = "";
                    $(".selected_purpose").each(function(){
                        if($(this).css("display") !== "none"){
                            purposeIds += $(this).attr("id").toString().replace(/^[^_]+_/, " ");
                        }
                    });
                                        
                    $("#action").val("submit");
                    $("#manOptOpen").val(manOptOpen);
                    $("#purposeOptOpen").val(purposeOptOpen);
                    $("#purposeIds").val(purposeIds);
                    $("#viewManCurrAff").submit();
                });
                
                $("#resetBtn").click(function(){
                    $("#action").val("reset");
                    $("#viewManCurrAff").submit();
                });
                
                $("#cancelBtn").click(function(){
                    $("#action").val("back");
                    $("#viewManCurrAff").submit();
                });
                
                $(".createBtn").click(function(){
                    $("#action").val("create");
                    $("#viewManCurrAff").submit();
                });
                
                $("#dateManLink").click(function(){
                    $("#dateManOpts").slideToggle("fast");
                    manOptOpen = !manOptOpen;
                });
                
                $("#purposesLink").click(function(){
                    $("#purposes").slideToggle("fast"); 
                    if(purposeOptOpen==1) purposeOptOpen = 0;
                    else purposeOptOpen = 1;
                });
                
                $("#addPurpose").click(function(){
                    if($('#purpose option:selected').attr('id') === "undef_purpose") return;
                    $("#selected_" + $('#purpose option:selected').attr('id')).css("display","");
                    $("#selected_" + $('#purpose option:selected').attr('id')).attr("refId",$('#purpose option:selected').attr('id'));
                    $("#purpose_details_" + $('#purpose option:selected').attr('id')).css("display","none");
                    $('#purpose option:selected').css("display","none");
                    $('#purpose option:selected').attr('disabled','disabled');
                    $('#purpose option:selected').removeAttr("selected");
                    $("#undef_purpose").attr("selected","selected");
                    $("#selectedPurposeDiv").css("display","");
                    $("#purpose_title").css("padding-top","5px");
                    purposeCount++;
                    purposeTotal--;
                    if(purposeTotal === 0){
                        $("#purposeDiv").css("display","none");
                    }
                });
                
            });
        </script>
    </head>
    <body>
        <compress:compress>
            <%@include file="/WEB-INF/jspf/header.jsp"%>
            <%@include file="/WEB-INF/jspf/header_login.jsp"%>
            <html:form styleId="viewManCurrAff" action="ViewManCurrAff.do">
                <html:hidden styleId="action" property="action"/>
                <html:hidden styleId="manOptOpen" property="manOptOpen"/>
                <html:hidden styleId="targUserId" property="targUserId"/>
                <html:hidden styleId="targPurposeId" property="targPurposeId"/>
                <html:hidden styleId="targApproved" property="targApproved"/>
                <html:hidden styleId="targActive" property="targActive"/>
                <html:hidden styleId="targExpDate" property="targExpDate"/>
                <html:hidden styleId="addPurpUserId" property="addPurpUserId"/>
                <html:hidden styleId="addPurpPurposeId" property="addPurpPurposeId"/>
                <html:hidden styleId="addPurpApproved" property="addPurpApproved"/>
                <html:hidden styleId="addPurpActive" property="addPurpActive"/>
                <html:hidden styleId="addPurpExpDate" property="addPurpExpDate"/>
                <html:hidden styleId="purposeOptOpen" property="purposeOptOpen"/>
                <html:hidden styleId="purposeIds" property="purposeIds"/>
                <bean:define id="manOptOpen" name="viewManCurrAffForm" property="manOptOpen" scope="session" toScope="page" type="String"/>
                <bean:define id="purposeOptOpen" name="viewManCurrAffForm" property="purposeOptOpen" scope="session" toScope="page" type="String"/>
                <script>
                    manOptOpen = <% out.print(manOptOpen.toString());%>;
                    purposeOptOpen = <% out.print(purposeOptOpen.toString());%>;
                </script>
                <logic:iterate id="purpose"  name="viewManCurrAffForm" property="purposes" scope="session">
                    <script>
                        purposeTotal++;
                    </script>
                </logic:iterate>
                <logic:notEmpty name="errorMsg" scope="request">
                    <script>
                        alert("<bean:write name="errorMsg" scope="request" filter="false"/>");
                    </script>
                </logic:notEmpty>
                <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>View/Manage Current Affiliates</h3>
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
                    Open and fill the search form below
                    <table>
                        <tr>
                            <td colspan="5">
                                <b>Basic Affiliate Options:</b>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                Last Name:
                            </td>
                            <td>
                                <html:text property="lastName" size="32" maxlength="64"/>
                            </td>
                            <td>
                                <html:radio property="lastNameOpt" value="begins">Begins with</html:radio>
                                </td>
                                <td>
                                <html:radio property="lastNameOpt" value="contains">Contains</html:radio>
                                </td>
                                <td>
                                <html:radio property="lastNameOpt" value="exact">Exact</html:radio>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    First Name:
                                </td>
                                <td>
                                <html:text property="firstName" size="32" maxlength="64"/>
                            </td>
                            <td>
                                <html:radio property="firstNameOpt" value="begins">Begins with</html:radio>
                                </td>
                                <td>
                                <html:radio property="firstNameOpt" value="contains">Contains</html:radio>
                                </td>
                                <td>
                                <html:radio property="firstNameOpt" value="exact">Exact</html:radio>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    OVPR ID:
                                </td>
                                <td>
                                <html:text property="ovprId" size="32" maxlength="64"/>
                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                            <td>
                                <html:radio property="ovprIdOpt" value="exact">Exact</html:radio>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Pseudo UGAID
                                </td>
                                <td>
                                <html:text property="pseudoCan" size="9" maxlength="9"/>
                            </td>
                            <td>
                            </td>
                            <td>
                            </td>
                            <td>
                                <html:radio property="pseudoCanOpt" value="exact">Exact</html:radio>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Email:
                                </td>
                                <td>
                                <html:text property="email" size="32" maxlength="64"/>
                            </td>
                            <td>
                                <html:radio property="emailOpt" value="begins">Begins with</html:radio>
                                </td>
                                <td>
                                <html:radio property="emailOpt" value="contains">Contains</html:radio>
                                </td>
                                <td>
                                <html:radio property="emailOpt" value="exact">Exact</html:radio>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    Organization/Company:
                                </td>
                                <td>
                                <html:text property="organizationCompany" size="32" maxlength="64"/>
                            </td>
                            <td>
                                <html:radio property="organizationCompanyOpt" value="begins">Begins with</html:radio>
                                </td>
                                <td>
                                <html:radio property="organizationCompanyOpt" value="contains">Contains</html:radio>
                                </td>
                                <td>
                                <html:radio property="organizationCompanyOpt" value="exact">Exact</html:radio>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <b>Purpose Options:</b>
                                </td>
                                <td colspan="4">
                                    <span id="purposesLink" style="color: red; cursor: pointer;">(Show Options)</span>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="5">
                                    <div id="purposes" style="display: none; background-color: #E0E8F4;">
                                        <table>
                                            <tr>
                                                <td id="purpose_title" style="vertical-align: top; padding-top: 2px;">
                                                    Target purposes:
                                                </td>
                                                <td>
                                                    <div id="selectedPurposeDiv" style="display: none;">
                                                        <table>
                                                        <logic:iterate id="purpose"  name="viewManCurrAffForm" property="purposes" scope="session">
                                                            <logic:equal name="purpose" property="SELECTED" value="0" scope="page">
                                                                <tr id="selected_<bean:write name="purpose" property="ID" scope="page"/>" class="selected_purpose" style="display: none;">
                                                                    <td>
                                                                        <bean:write name="purpose" property="SHORT_NAME" scope="page"/>  
                                                                    </td>
                                                                    <td>
                                                                        <input id="rm_<bean:write name="purpose" property="ID" scope="page"/>" type="button" value="Remove" onclick="RemovePurpose('<bean:write name="purpose" property="ID" scope="page"/>');return false;">
                                                                    </td>
                                                                </tr>
                                                            </logic:equal>
                                                            <logic:notEqual name="purpose" property="SELECTED" value="0" scope="page">
                                                                <tr id="selected_<bean:write name="purpose" property="ID" scope="page"/>" class="selected_purpose">
                                                                    <td>
                                                                        <script>
                                                                            purposeCount++;
                                                                            purposeTotal--;
                                                                        </script>
                                                                        <bean:write name="purpose" property="SHORT_NAME" scope="page"/>  
                                                                    </td>
                                                                    <td>
                                                                        <input id="rm_<bean:write name="purpose" property="ID" scope="page"/>" type="button" value="Remove" onclick="RemovePurpose('<bean:write name="purpose" property="ID" scope="page"/>');return false;">
                                                                    </td>
                                                                </tr>
                                                            </logic:notEqual>
                                                        </logic:iterate>
                                                    </table>
                                                </div>
                                                <div id="purposeDiv">
                                                    <select id="purpose">
                                                        <option id="undef_purpose" value=""></option>
                                                        <logic:iterate id="purpose"  name="viewManCurrAffForm" property="purposes" scope="session">
                                                            <logic:equal name="purpose" property="SELECTED" value="0" scope="page">
                                                                <option id="<bean:write name="purpose" property="ID" scope="page"/>" value="<bean:write name="purpose" property="SHORT_NAME" scope="page"/>"><bean:write name="purpose" property="SHORT_NAME" scope="page"/></option>
                                                            </logic:equal>
                                                            <logic:notEqual name="purpose" property="SELECTED" value="0" scope="page">
                                                                <option id="<bean:write name="purpose" property="ID" scope="page"/>" value="<bean:write name="purpose" property="SHORT_NAME" scope="page"/>" style="display: none;"><bean:write name="purpose" property="SHORT_NAME" scope="page"/></option>
                                                            </logic:notEqual>
                                                        </logic:iterate>
                                                    </select>
                                                    <input type="button" id="addPurpose" onclick="return false;" value="Add Selected">
                                                </div>
                                            </td>
                                        </tr>
                                    </table>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                Include Excluded:&nbsp;&nbsp;
                                <logic:equal name="viewManCurrAffForm" property="showInactive" scope="session" value="Yes">
                                    <input type="radio" name="showInactive" value="Yes" checked> Yes&nbsp;&nbsp;
                                    <input type="radio" name="showInactive" value="No"> No
                                </logic:equal>
                                <logic:equal name="viewManCurrAffForm" property="showInactive" scope="session" value="No">
                                    <input type="radio" name="showInactive" value="Yes"> Yes&nbsp;&nbsp;
                                    <input type="radio" name="showInactive" value="No" checked> No
                                </logic:equal>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                Include Expired/Submitted/Rejected:&nbsp;&nbsp;
                                <logic:equal name="viewManCurrAffForm" property="showExpired" scope="session" value="Yes">
                                    <input type="radio" name="showExpired" value="Yes" checked> Yes&nbsp;&nbsp;
                                    <input type="radio" name="showExpired" value="No"> No
                                </logic:equal>
                                <logic:equal name="viewManCurrAffForm" property="showExpired" scope="session" value="No">
                                    <input type="radio" name="showExpired" value="Yes"> Yes&nbsp;&nbsp;
                                    <input type="radio" name="showExpired" value="No" checked> No
                                </logic:equal>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                Only affiliates who expire within 30 days:&nbsp;&nbsp;
                                <logic:equal name="viewManCurrAffForm" property="showExpire" scope="session" value="Yes">
                                    <input type="radio" name="showExpire" value="Yes" checked> Yes&nbsp;&nbsp;
                                    <input type="radio" name="showExpire" value="No"> No
                                </logic:equal>
                                <logic:equal name="viewManCurrAffForm" property="showExpire" scope="session" value="No">
                                    <input type="radio" name="showExpire" value="Yes"> Yes&nbsp;&nbsp;
                                    <input type="radio" name="showExpire" value="No" checked> No
                                </logic:equal>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                Only affiliates for purposes you manage:&nbsp;&nbsp;
                                <logic:equal name="viewManCurrAffForm" property="purposeManageFilter" scope="session" value="Yes">
                                    <input type="radio" name="purposeManageFilter" value="Yes" checked> Yes&nbsp;&nbsp;
                                    <input type="radio" name="purposeManageFilter" value="No"> No
                                </logic:equal>
                                <logic:equal name="viewManCurrAffForm" property="purposeManageFilter" scope="session" value="No">
                                    <input type="radio" name="purposeManageFilter" value="Yes"> Yes&nbsp;&nbsp;
                                    <input type="radio" name="purposeManageFilter" value="No" checked> No
                                </logic:equal>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="5">
                                <button id="submitBtn" onclick="return false;">Submit Search</button>
                                &nbsp;&nbsp;
                                <button id="resetBtn" onclick="return false;">Reset</button>
                                &nbsp;&nbsp;
                                <button id="cancelBtn" onclick="return false;">Cancel</button>
                            </td>
                        </tr>
                        <!--tr>
                            <td colspan="5" style="padding-top: 15px;">
                                <button class="createBtn" onclick="return false;">New Affiliate</button> (Check to see if the affiliate already exists)
                            </td>
                        </tr-->
                    </table>
                    <br>
                    <logic:notEmpty name="viewManCurrAffForm" property="executed" scope="session">
                        <logic:empty name="affQueryResults" scope="request">
                            No results found.
                        </logic:empty>
                        <logic:notEmpty name="affQueryResults" scope="request">
                            <div style="width: 850px;">
                                <table>
                                    <tr>
                                        <td style="background-color: #DDDDDD;">
                                            <div style="width: 175px;">
                                                Name
                                            </div>
                                        </td>
                                        <!--td style="background-color: lightskyblue;">
                                            <div style="width: 55px;">
                                                Manage
                                            </div>
                                        </td-->
                                        <td style="background-color: #DDDDDD;">
                                            <div style="width: 285px;">
                                                OVPR ID
                                            </div>
                                        </td>
                                        <!--td style="background-color: #DDDDDD;">
                                            <div style="width: 100px;">
                                                Pseudo UGAID
                                            </div>
                                        </td-->
                                        <td style="background-color: #DDDDDD;">
                                            <div style="width: 150px;">
                                                Organization/Company
                                            </div>
                                        </td>
                                        <td style="background-color: #DDDDDD;">
                                            <div style="width: 60px;">
                                                Excluded
                                            </div>
                                        </td>
                                        <td style="background-color: #DDDDDD;">
                                            <div style="width: 70px;">
                                                Active Purposes
                                            </div>
                                        </td>
                                        <td style="background-color: #DDDDDD;">
                                            <div style="width: 100px;">
                                                Next Purpose Expiration
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div style="width: 883px; overflow: auto; max-height:625px;">
                                <bean:define id="oddEven" value="0" toScope="page" type="String"/>
                                <table>
                                    <logic:iterate id="aff" name="affQueryResults" scope="request">
                                        <tr style="background-color: #FFFFFF;">
                                            <td>
                                                <div style="width: 175px;">
                                                    <a href="#" onclick="AffDetails('<bean:write name="aff" property="ID" scope="page"/>'); return false;">
                                                        <bean:write name="aff" property="LASTNAME" scope="page"/>,
                                                        <bean:write name="aff" property="FIRSTNAME" scope="page"/>
                                                    </a>
                                                </div>
                                            </td>
                                            <!--td>
                                                <div style="width: 55px;">
                                                    <span style="color: red; cursor: pointer;" onclick="AffDetails('<!bean:write name="aff" property="ID" scope="page"/>'); return false;">Open</span>
                                                </div>
                                            </td-->
                                            <td>
                                                <div style="width: 285px;">
                                                    <bean:write name="aff" property="OVPRID" scope="page"/>
                                                </div>
                                            </td>
                                            <!--td>
                                                <div style="width: 100px;">
                                                    <!bean:write name="aff" property="PSEUDOCAN" scope="page"/>
                                                </div>
                                            </td-->
                                            <td>
                                                <div style="width: 150px;">
                                                    <logic:empty name="aff" property="ORGANIZATION" scope="page">
                                                        -
                                                    </logic:empty>
                                                    <logic:notEmpty name="aff" property="ORGANIZATION" scope="page">
                                                        <bean:write name="aff" property="ORGANIZATION" scope="page"/>
                                                    </logic:notEmpty>
                                                </div>
                                            </td>
                                            <td>
                                                <div style="width: 60px;">
                                                    <logic:equal name="aff" property="ACTIVE" scope="page" value="1">
                                                        -
                                                    </logic:equal>
                                                    <logic:equal name="aff" property="ACTIVE" scope="page" value="0">
                                                        <b style="color:red">Yes</b>
                                                    </logic:equal>
                                                </div>
                                            </td>
                                            <td>
                                                <div style="width: 70px;">
                                                    <bean:write name="aff" property="ACTIVE_PURPOSES" scope="page" filter="false"/>
                                                </div>
                                            </td>
                                            <td>
                                                <div style="width: 100px;">
                                                    <bean:write name="aff" property="NEXT_EXP" scope="page"/>
                                                </div>
                                            </td>
                                        </tr>
                                    </logic:iterate>
                                </table>
                            </div>
                        </logic:notEmpty>
                    </logic:notEmpty>
                </div>
                </center>
                <%@include file="/WEB-INF/jspf/footer.jsp"%>
            </html:form>
        </compress:compress>
    </body>
</html>
