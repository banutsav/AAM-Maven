<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String aplId;%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/html/meta.html"%>
        <%@include file="/WEB-INF/html/title.html"%>
        <%@include file="/WEB-INF/html/css.html"%>
        <script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>
        <!--link href="http://code.jquery.com/ui/1.10.0/themes/smoothness/jquery-ui.css" type="text/css" media="all" rel="stylesheet"-->
        <link type="text/css" media="all" rel="stylesheet" href="css/jquery-ui-1.10.2.custom.min.css">
        <link type="text/css" media="all" rel="stylesheet" href="css/jquery_datetimepicker.css">
        <script type="text/javascript">
            $(document).ready(function(){
                $("#backLink").click(function(){
                    $("#action").val("backLink");
                    $("#manAffReq").submit();
                });
                
                $("#submitBtn").click(function(){
                    $("#action").val("submit");
                    $("#manAffReq").submit();
                });                
            });
            function ExpandDetails(id){
                $("#details_" + id).slideToggle("fast");
            }
            
            function Accept(id,pseudoCannum,purposeName,email){
                $("#reqId").val(id);
                $("#pseudoCannum").val(pseudoCannum);
                $("#purpose_period").val($("#purpose_period_" + id).val());
                $("#purposeName").val(purposeName);
                $("#affName").val(email);
                $("#action").val("accept");
                $("#manAffReq").submit();
            }
            
            function Reject(id,pseudoCannum,purposeName,email){
                $("#reqId").val(id);
                $("#pseudoCannum").val(pseudoCannum);
                $("#purposeName").val(purposeName);
                $("#affName").val(email);
                $("#action").val("reject");
                $("#manAffReq").submit();
            }
        </script>

    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jsp"%>
        <%@include file="/WEB-INF/jspf/header_login.jsp"%>
        <html:form styleId="manAffReq" action="ManAffReq.do">
            <html:hidden styleId="action" property="action"/>
            <html:hidden styleId="reqId" property="reqId"/>
            <html:hidden styleId="pseudoCannum" property="pseudoCannum"/>
            <html:hidden styleId="purposeName" property="purposeName"/>
            <html:hidden styleId="affName" property="affName"/>
            <html:hidden styleId="purpose_period" property="purpose_period"/>
            <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>Manage Affiliate Requests</h3>
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
                <div class="affReqTable" style="padding: 10px 20px 10px 20px;">
                    <logic:empty name="manAffReqForm" property="requests" scope="request">
                        You have 0 requests.
                    </logic:empty>
                    <logic:notEmpty name="manAffReqForm" property="requests" scope="request">
                        You have <bean:write name="manAffReqForm" property="numberOfRequests" scope="request"/> request(s).
                        <br>
                        <table>
                            <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Purpose</th>
                                    <th>Organization</th>
                                    <th>Email</th>
                                    <th>Creation Date</th>
                                </tr>
                            </thead>
                            <tbody>                        
                                <logic:iterate id="affRequest" name="manAffReqForm" property="requests" scope="request">
                                    <tr>
                                        <td>
                                            <a id="request_<bean:write name="affRequest" property="APL_ID" scope="page"/>" href="#" onclick ="ExpandDetails('<bean:write name="affRequest" property="APL_ID" scope="page"/>');return false;">
                                                <bean:write name="affRequest" property="LASTNAME" scope="page"/>, 
                                                <bean:write name="affRequest" property="FIRSTNAME" scope="page"/> 
                                                <bean:write name="affRequest" property="MIDDLENAME" scope="page"/>
                                            </a>
                                        </td>
                                        <td>
                                            <bean:write name="affRequest" property="SHORT_NAME" scope="page"/>
                                        </td>
                                        <td>
                                            <bean:write name="affRequest" property="ORGANIZATION" scope="page"/> /
                                            <bean:write name="affRequest" property="DEPARTMENT" scope="page"/>
                                        </td>
                                        <td>
                                            <bean:write name="affRequest" property="EMAIL" scope="page"/>
                                        </td>
                                        <td>
                                            <bean:write name="affRequest" property="A_CREATION_DATE" scope="page"/>
                                        </td>
                                    </tr>
                                    <tr style="margin: 0px; padding: 0px; border: 0px;">
                                        <td colspan="6" style="margin: 0px; padding: 0px; border: 0px;">
                                            <div id="details_<bean:write name="affRequest" property="APL_ID" scope="page"/>" style="display:none; background-color: #E0E8F4; padding: 10px 20px 10px 20px;">
                                                <b>Person Information:</b>
                                                <table style="padding: 0px 20px;">
                                                    <tr>
                                                        <td>Name:</td>
                                                        <td>
                                                            <bean:write name="affRequest" property="LASTNAME" scope="page"/>, 
                                                            <bean:write name="affRequest" property="FIRSTNAME" scope="page"/> 
                                                            <bean:write name="affRequest" property="MIDDLENAME" scope="page"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Organization:</td>
                                                        <td>
                                                            <bean:write name="affRequest" property="ORGANIZATION" scope="page"/> /
                                                            <bean:write name="affRequest" property="DEPARTMENT" scope="page"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Email:</td>
                                                        <td>
                                                            <bean:write name="affRequest" property="EMAIL" scope="page"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Phone:</td>
                                                        <td>
                                                            <bean:write name="affRequest" property="PHONE" scope="page"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td style="vertical-align: top;">Address:</td>
                                                        <td>
                                                            <bean:write name="affRequest" property="ADDR_1" scope="page"/><br>
                                                            <bean:write name="affRequest" property="ADDR_2" scope="page"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>City:</td>
                                                        <td>
                                                            <bean:write name="affRequest" property="CITY" scope="page"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>State:</td>
                                                        <td>
                                                            <bean:write name="affRequest" property="STATE" scope="page"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Country:</td>
                                                        <td>
                                                            <bean:write name="affRequest" property="COUNTRY" scope="page"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Zip Code:</td>
                                                        <td>
                                                            <bean:write name="affRequest" property="ZIP" scope="page"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Business Phone Number:</td>
                                                        <td>
                                                            <bean:write name="affRequest" property="BUSINESS_PHONE_NUM" scope="page"/>
                                                        </td>
                                                    </tr>
                                                    <!--tr>
                                                        <td>Alternative Phone Number:</td>
                                                        <td>
                                                            <!bean:write name="affRequest" property="ALT_PHONE_NUM" scope="page"/>
                                                        </td>
                                                    </tr-->
                                                    <!--tr>
                                                        <td>Expiration Date:</td>
                                                        <td>
                                                            <!bean:write name="affRequest" property="A_EXPIRATION_DATE" scope="page"/>
                                                        </td>
                                                    </tr-->
                                                    <tr>
                                                        <td style="vertical-align: top;">Comments:</td>
                                                        <td>
                                                            <div style="max-width: 400px;">
                                                                <bean:write name="affRequest" property="COMMENTS" scope="page"/>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>FSCode:</td>
                                                        <td>
                                                            <bean:write name="affRequest" property="FSCODES" scope="page"/>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <b>Purpose Information:</b>
                                                <table style="padding: 0px 20px;">
                                                    <tr>
                                                        <td>Name:</td>
                                                        <td>
                                                            <bean:write name="affRequest" property="SHORT_NAME" scope="page"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td style="vertical-align: top;">Description:</td>
                                                        <td>
                                                            <div style="max-width: 400px;">
                                                                <bean:write name="affRequest" property="DESCRIPTION" scope="page"/>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Creation Date:</td>
                                                        <td>
                                                            <bean:write name="affRequest" property="AP_CREATION_DATE" scope="page"/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>Expiration Period:</td>
                                                        <td>
                                                            <logic:equal name="affRequest" property="PERIOD" scope="page" value="-1">
                                                                Does not expire
                                                            </logic:equal>
                                                            <logic:notEqual  name="affRequest" property="PERIOD" scope="page" value="-1">
                                                                <bean:write name="affRequest" property="PERIOD" scope="page"/> Month(s)
                                                            </logic:notEqual>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td style="vertical-align: top;">Created By</td>
                                                        <td>                                
                                                            <bean:write name="affRequest" property="CP_LASTNAME" scope="page"/>,
                                                            <bean:write name="affRequest" property="CP_FIRSTNAME" scope="page"/>
                                                            <br>
                                                            <bean:write name="affRequest" property="CP_MYID" scope="page"/>
                                                            <br>
                                                            <bean:write name="affRequest" property="CP_CANNUM" scope="page"/>
                                                            <br>
                                                            <bean:write name="affRequest" property="CP_EMAIL" scope="page"/>

                                                        </td>
                                                    </tr>
                                                </table>
                                                <div>
                                                    <br>
                                                    <button id="accept_<bean:write name="affRequest" property="APL_ID" scope="page"/>" onclick="Accept('<bean:write name="affRequest" property="APL_ID" scope="page"/>','<bean:write name="affRequest" property="PSEUDOCAN" scope="page"/>','<bean:write name="affRequest" property="SHORT_NAME" scope="page"/>','<bean:write name="affRequest" property="EMAIL" scope="page"/>'); return false;">Accept Request</button>
                                                    <input type="hidden" id="purpose_period_<bean:write name="affRequest" property="APL_ID" scope="page"/>" value="<bean:write name="affRequest" property="PERIOD" scope="page"/>">
                                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                                    <button id="reject_<bean:write name="affRequest" property="APL_ID" scope="page"/>" onclick="Reject('<bean:write name="affRequest" property="APL_ID" scope="page"/>','<bean:write name="affRequest" property="PSEUDOCAN" scope="page"/>','<bean:write name="affRequest" property="SHORT_NAME" scope="page"/>','<bean:write name="affRequest" property="EMAIL" scope="page"/>'); return false;">Reject Request</button><br>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </logic:iterate>
                            </tbody>
                        </table>
                    </logic:notEmpty>
                </div>
            </div>
            </center>
        </html:form>
        <%@include file="/WEB-INF/jspf/footer.jsp"%>
    </body>
</html>
