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
            function AssociateDetails(userId){
                $("#targUserId").val(userId);
                $("#action").val("openAssociatePage");
                $("#viewManCurrAssociate").submit();
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
                    $("#viewManCurrAssociate").submit();
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
                    $("#viewManCurrAssociate").submit();
                });
                
                $("#resetBtn").click(function(){
                    $("#action").val("reset");
                    $("#viewManCurrAssociate").submit();
                });
                
                $("#cancelBtn").click(function(){
                    $("#action").val("back");
                    $("#viewManCurrAssociate").submit();
                });
                
                $(".createBtn").click(function(){
                    $("#action").val("create");
                    $("#viewManCurrAssociate").submit();
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
            <html:form styleId="viewManCurrAssociate" action="ViewManCurrAssociate.do">
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
                <bean:define id="manOptOpen" name="viewManCurrAssociateForm" property="manOptOpen" scope="session" toScope="page" type="String"/>
                <bean:define id="purposeOptOpen" name="viewManCurrAssociateForm" property="purposeOptOpen" scope="session" toScope="page" type="String"/>
                <script>
                    manOptOpen = <% out.print(manOptOpen.toString());%>;
                    purposeOptOpen = <% out.print(purposeOptOpen.toString());%>;
                </script>
                <logic:iterate id="purpose"  name="viewManCurrAssociateForm" property="purposes" scope="session">
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

                    <h3>View/Manage Current Associates</h3>
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
                                <b>Basic Associate Options:</b>
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
                                    MyID:
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
                                    CANNUM:
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
                            <td>Include inactive Associates in results?</td>
                                <td colspan="4">
                                    <html:radio value="Yes" property="showInactive">Yes</html:radio>
                                    <html:radio value="No" property="showInactive">No</html:radio>
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
                        
                        
                    </table>
                    <br>
                    <logic:notEmpty name="viewManCurrAssociateForm" property="executed" scope="session">
                        <logic:empty name="AssociateQueryResults" scope="request">
                            No results found.
                        </logic:empty>
                        <logic:notEmpty name="AssociateQueryResults" scope="request">
                            <div style="width: 850px;">
                                <table>
                                    <tr>
                                        <td style="background-color: #DDDDDD;">
                                            <div style="width: 175px;">
                                                Name
                                            </div>
                                        </td>
                                        
                                        <td style="background-color: #DDDDDD;">
                                            <div style="width: 285px;">
                                                MyID
                                            </div>
                                        </td>
                                        
                                        <td style="background-color: #DDDDDD;">
                                            <div style="width: 285px;">
                                                Login Username (Associate Email)
                                            </div>
                                        </td>
                                        
                                        <td style="background-color: #DDDDDD;">
                                            <div style="width: 150px;">
                                                Organization/Company
                                            </div>
                                        </td>
                                        <td style="background-color: #DDDDDD;">
                                            <div style="width: 60px;">
                                                Active
                                            </div>
                                        </td>
                                        <td style="background-color: #DDDDDD;">
                                            <div style="width: 100px;">
                                                Expiration Date
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div style="width: 1100px; overflow: auto; max-height:625px;">
                                <bean:define id="oddEven" value="0" toScope="page" type="String"/>
                                <table>
                                    <logic:iterate id="Associate" name="AssociateQueryResults" scope="request">
                                        <tr style="background-color: #FFFFFF;">
                                            <td>
                                                <div style="width: 175px;">
                                                    <a href="#" onclick="AssociateDetails('<bean:write name="Associate" property="ID" scope="page"/>'); return false;">
                                                        <bean:write name="Associate" property="LASTNAME" scope="page"/>,
                                                        <bean:write name="Associate" property="FIRSTNAME" scope="page"/>
                                                    </a>
                                                </div>
                                            </td>
                                           
                                            <td>
                                                <div style="width: 285px;">
                                                    <bean:write name="Associate" property="OVPRID" scope="page"/>
                                                </div>
                                            </td>
                                            
                                            <td>
                                                <div style="width: 285px;">
                                                    <bean:write name="Associate" property="EMAIL" scope="page"/>
                                                </div>
                                            </td>
                                            
                                            
                                            <td>
                                                <div style="width: 150px;">
                                                    <logic:empty name="Associate" property="ORGANIZATION" scope="page">
                                                        -
                                                    </logic:empty>
                                                    <logic:notEmpty name="Associate" property="ORGANIZATION" scope="page">
                                                        <bean:write name="Associate" property="ORGANIZATION" scope="page"/>
                                                    </logic:notEmpty>
                                                </div>
                                            </td>
                                            <td>
                                                <div style="width: 60px;">
                                                    <logic:equal name="Associate" property="ACTIVE" scope="page" value="1">
                                                        Yes
                                                    </logic:equal>
                                                    <logic:equal name="Associate" property="ACTIVE" scope="page" value="0">
                                                        <b style="color:red">No</b>
                                                    </logic:equal>
                                                </div>
                                            </td>
                                            <td>
                                                <div style="width: 100px;">
                                                    <bean:write name="Associate" property="NEXT_EXP" scope="page"/>
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
