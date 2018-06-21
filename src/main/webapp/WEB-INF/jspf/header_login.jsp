<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<bean:define id="authState" property="authState" name="ses" scope="session"/>
<bean:define id="userInfo" property="userInfo" name="authState" scope="page"/>
<div class="login_div_template">
                    Welcome, <bean:write property="firstName" name="userInfo" scope="page"/> <bean:write property="lastName" name="userInfo" scope="page"/> &middot; <a href="Logout.do">Logout</a><br>
</div>
