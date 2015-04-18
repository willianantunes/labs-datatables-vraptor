<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="siteName" value="${t['site.name']}"/>

<c:set var="title" value="${t['user.user_details']}"/>

<tags:head title="${siteName} - ${title}" includeHead="true"/>

<div class="torso">
	<div class="torso-content">
	
	<h1><fmt:message key="user.user_details"/></h1>
	
	<form action="<c:url value="/users" />" name="___frmUser" id="___frmUser" class="standard-form" method="post">
		<label for="user.name"><fmt:message key="user.name"/>:</label>
		<input type="text" name="user.name" title="<fmt:message key="user.name.tooltip"/>." class="validate[required] text-input" value="${user.name}" />
		<div class="clear"></div>
		
		<label for="user.email"><fmt:message key="user.email"/>:</label>
		<input type="text" name="user.email" title="<fmt:message key="user.email.tooltip"/>." class="validate[required,custom[email]] text-input" value="${user.email}" />	
		<div class="clear"></div>		
		
		<label for="user.password"><fmt:message key="user.password"/>:</label>
		<input type="password" name="user.password" id="userPassword" value="" class="" />	
		<div class="clear"></div>
		
		<label for="confirmPassword"><fmt:message key="user.confirm_password"/>:</label>
		<input type="password" name="confirmPassword" value="" class="validate[condRequired[userPassword],equals[userPassword]] text-input"/>
																
		<div class="clear"></div>
		<input type="hidden" name="_method" value="put"/>
		<input type="hidden" name="_timestamp" value="${_timestamp}"/>
		<input type="submit" value="<fmt:message key="message.update"/>" />
		<input type="button" value="<fmt:message key="message.back"/>" />
	</form>

	</div>
</div><!-- #torso -->