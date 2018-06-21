<%-- 
    Document   : login
    Created on : Sep 29, 2011, 10:06:21 AM
    Author     : submyers
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <%@include file="/WEB-INF/html/meta.html"%>
        <%@include file="/WEB-INF/html/title.html"%>
        <%@include file="/WEB-INF/html/css.html"%>
        <script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
        <link rel="stylesheet" href="/css/statusio_widget.css">
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jsp"%>

        <div id="container">
            <h3 style="text-align: center; min-width: 100%;">OVPR Affiliate Account Manager</h3>
            <hr>
            <logic:notEmpty name="message" scope="request">
                <br>
                <b>Message:</b>
                <bean:write name="message" scope="request"/><br>
            </logic:notEmpty>
            <br>
            <html:form styleId="loginForm" action="/Login.do" focus="myid" focus="ugamyid">
                <logic:notEmpty name="errorMessage" scope="request">
                    <br>
                    <b class="redFlag">Error:</b>
                    <bean:write name="errorMessage" scope="request"/><br>
                </logic:notEmpty>
                <table>
                    <thead></thead>
                    <tbody>
                        <tr>
                            <td>MyID:</td>
                            <td><html:text property="ugamyid" maxlength="64" size="32" value=""/></td>
                        </tr>
                        <tr>
                            <td>Password:</td>
                            <td><html:password property="password" maxlength="64" size="32" value=""/></td>
                        </tr>
                    </tbody>
                </table>
                <html:submit value="Login"/>
            </html:form>
            <p>&nbsp; </p>
            <div>
                <a target="_blank" href="https://ugaovpr.atlassian.net/wiki/display/HD/">Report a Problem</a>
            </div>
            <div>
                <a href="https://ugaovpr.statuspage.io/" target="_blank">APPLICATION STATUS - <span id="status-description" style="text-transform: capitalize;"></span>
                    <i class="current-status-indicator"></i>
                </a>    
            </div>
            <div>
                <u>Updates: <span id="mnt-description"></span></u>
            </div>
        </div>
        <script type="text/javascript" src="https://cdn.statuspage.io/se-v2.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
        <script>
            var sp = new StatusPage.page({page: 'gvrmz4tx5yrx'});
            sp.summary({
                success: function (data) {
                    for (index = 0, len = data.components.length; index < len; ++index) {

                        if (data.components[index].name == "Affiliate Account Manager") {
                            $("#status-description").text(data.components[index].status);
                            if (data.components[index].status == "major_outage")
                                $(".current-status-indicator").addClass("red");
                            else if (data.components[index].status == "partial_outage")
                                $(".current-status-indicator").addClass("yellow");
                            else if (data.components[index].status == "degraded_performance")
                                $(".current-status-indicator").addClass("yellow");
                            else if (data.components[index].status == "under_maintenance")
                                $(".current-status-indicator").addClass("blue");
                            else if (data.components[index].status == "operational")
                                $(".current-status-indicator").addClass("green");
                            break;
                        }
                    }
                }
            });
            sp.scheduled_maintenances({
                filter: 'upcoming',
                success: function (data) {
                    for (index = 0, len = data.scheduled_maintenances.length; index < len; ++index) {
                        if (data.scheduled_maintenances[index].name == "Affiliate Account Manager")
                        {
                            var startDate = new Date(data.scheduled_maintenances[index].scheduled_for);
                            startDay = (startDate.getMonth() + 1) + "-" + startDate.getDate() + "-" + startDate.getFullYear()
                            startTime = startDate.getHours() + ":" + startDate.getMinutes() + ":" + startDate.getSeconds()

                            var endDate = new Date(data.scheduled_maintenances[index].scheduled_until);
                            endDay = (endDate.getMonth() + 1) + "-" + endDate.getDate() + "-" + endDate.getFullYear()
                            endTime = endDate.getHours() + ":" + endDate.getMinutes() + ":" + endDate.getSeconds()
                            
                            var mntStatus = data.scheduled_maintenances[index].incident_updates[0].body + " from " + startDay + ", " + startTime + " to " + endDay + ", " + endTime;
                            $("#mnt-description").text(mntStatus);
                        }
                    }
                }
            });
        </script>
        <%@include file="/WEB-INF/jspf/footer.jsp"%>
    </body>
</html>
