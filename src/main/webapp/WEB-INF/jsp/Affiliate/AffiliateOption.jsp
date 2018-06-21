<%-- 
    Document   : AffiliateOptions
    Created on : Sep 29, 2011, 3:58:01 PM
    Author     : submyers
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/html/meta.html"%>
        <%@include file="/WEB-INF/html/title.html"%>
        <%@include file="/WEB-INF/html/css.html"%>
        <script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
        <script type="text/javascript">
            $(document).ready(function(){
                $("#addAffReq").click(function(){
                    $("#action").val("addAffReq");
                    $("#affiliateOption").submit();
                });
                $("#addAffPurp").click(function(){
                    $("#action").val("addAffPurp");
                    $("#affiliateOption").submit();
                });
                $("#manAffReq").click(function(){
                    $("#action").val("manAffReq");
                    $("#affiliateOption").submit();
                });
                $("#manAffMan").click(function(){
                    $("#action").val("manAffMan");
                    $("#affiliateOption").submit();
                });
                $("#viewManCurrAff").click(function(){
                    $("#action").val("viewManCurrAff");
                    $("#affiliateOption").submit();
                });
                $("#manAffPurpose").click(function(){
                    $("#action").val("manAffPurpose");
                    $("#affiliateOption").submit();
                });
                $("#manCreateNewAff").click(function(){
                    $("#action").val("manCreateNewAff");
                    $("#affiliateOption").submit();
                });
                $("#superControlAff").click(function(){
                    $("#action").val("superControlAff");
                    $("#affiliateOption").submit();
                });
                $("#superControlMan").click(function(){
                    $("#action").val("superControlMan");
                    $("#affiliateOption").submit();
                });
                $("#superControlPurpose").click(function(){
                    $("#action").val("superControlPurpose");
                    $("#affiliateOption").submit();
                });
                $("#incExpVewManCurrAff").click(function(){
                    $("#action").val("incExpVewManCurrAff");
                    $("#affiliateOption").submit();
                });
            });
        </script>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jsp"%>
        <%@include file="/WEB-INF/jspf/header_login.jsp"%>
        <html:form styleId="affiliateOption" action="AffiliateOption">
            <html:hidden styleId="action" property="action"/>
            <div id="container">
                <bean:define id="cancelPage" toScope="session" value="/WEB-INF/jsp/Affiliate/AffiliateOption.jsp"/>
                <h3 style="text-align: center; min-width: 100%;">Main Menu</h3>
                <hr>
                <br>
                <logic:notEmpty name="errorMessage" scope="request">
                    <p><b class="redFlag">Error: </b><bean:write name="errorMessage" scope="request" filter="false"/></p><br/>
                </logic:notEmpty>
                <logic:notEmpty name="message" scope="request">
                    <p><b>Response: </b><bean:write name="message" scope="request"/></p><br/>
                </logic:notEmpty>

                <h2>General Options:</h2>
                <logic:empty name="noActivePurposes" scope="session">
                    <a href="Get.do?action=addAffReq">New Affiliate Request</a><br>
                </logic:empty>
                <logic:notEmpty name="noActivePurposes" scope="session">
                    <b>No active, managed purposes exist. Contact administrators before submitting requests.</b><br>
                    <a href="#" style="color: gray;" onclick="return false;">New Affiliate Request</a><br>
                </logic:notEmpty>
                <a href="Get.do?action=addAffPurp">Manage Affiliates</a><br><br>

                <%-- contacts manager --%>
                <logic:equal value="true" property="isContactManager" name="ses" scope="session">
                <h5>Contacts</h5>
                <a href="Get.do?action=ManCreateNewContact">Create New Contact</a><br>
                <a href="Get.do?action=ManViewContacts">View/Manage Contacts</a><br>
                <br>
                </logic:equal>
                
                <logic:equal value="true" property="anyUser" name="ses" scope="session">
                    <h2>Manager Options:</h2>
                    
                    <a href="Get.do?action=manAffMan">Affiliate Manager Proxies</a><br><br>
                        
                    <!-- AFFILIATE OPTIONS -->
                    
                    <h5>Affiliates</h5>    
                    <logic:empty name="noActivePurposes" scope="session">
                        <a href="Get.do?action=manCreateNewAff">Create New Affiliate</a><br>
                    </logic:empty>
                    <logic:notEmpty name="noActivePurposes" scope="session">
                        <b>No active, managed purposes exist. Contact administrators before creating an affiliate.</b><br>
                        <a href="#" style="color: gray;" onclick="return false;">Create New affiliate</a><br>
                    </logic:notEmpty>
                    
                    <a href="Get.do?action=manAffReq">Manage Affiliate Request</a><br>
                    <a href="Get.do?action=viewManCurrAff">View/Manage Current Affiliates</a><br>
                    <a href="Get.do?action=manAffPurpose">View/Manage Affiliate Purposes</a><br>
                    
                    <br>
                    
                    <!--ASSOCIATE OPTIONS -->
                    
                    <logic:equal value="true" property="isAssociateManager" name="ses" scope="session">
                  
                    <h5>Associates</h5>
                        
                    <!-- create new associate-->
                    <a href="Get.do?action=manCreateNewAssoc">Create New Associate</a><br>
                    
                    <!-- manage current associates -->
                    <a href="Get.do?action=viewManCurrAssociate">View/Manage Current Associates</a><br>
                    
                    </logic:equal>
                    
                    <logic:notEmpty name="incExp" scope="session">
                        <a href="Get.do?action=incExpVewManCurrAff" style="text-decoration: underline;">View Soon Expiring Affiliates</a><br><br>
                    </logic:notEmpty>

                    <logic:equal value="true" property="superUser" name="ses" scope="session">
                        
                        <br>
                   
                        <h2>Superuser Options:</h2>
                        
                        <h5>Affiliates</h5>    
                   
                        <a href="Get.do?action=superControlAff">Activate/Deactivate Affiliate</a><br>
                        <a href="Get.do?action=superControlPurpose">Manage Affiliate Purposes</a><br>
                        <a href="Get.do?action=superControlMan">Create/Activate/Deactivate Affiliate Manager</a><br>
                        
                        <br>
                        
                        <h5>Associates</h5>    
                        
                        <!-- ASSOCIATE OPTIONS -->
                        <a href="Get.do?action=superControlAssociate">Activate/Deactivate Associate</a><br>
                        <a href="Get.do?action=superControlAssociateManagers">Create/Activate/Deactivate Associate Manager</a><br>
                        
                        <br>
                        
                        <h5>Contacts</h5>    
                        <a href="Get.do?action=superControlContactManagers">Manage Contact Managers</a><br>
                        
                        
                    </logic:equal>    

                </logic:equal>


            </div>
        </html:form>
        <%@include file="/WEB-INF/jspf/footer.jsp"%>
    </body>
</html>
