<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="siteName" value="${t['site.name']}"/>

<c:set var="title" value="${t['metas.auth.title']}"/>

<c:set var="genericTitle" value="${t['metas.generic.title'].args(siteName)}"/>

<tags:head title="${genericTitle} - ${title}" includeHead="false"/>

<div class="torso">
	<div class="torso-content">
	
		<div class="painel-login">
			<h1><fmt:message key="metas.auth.title"/></h1>
			<div class="clear"></div>
			<form name="___frmLogin" id="___frmLogin" action="<c:url value="/login" />" method="post">
				<fieldset>
					<legend><fmt:message key="metas.auth.form"/></legend>
					<label for="user.email"><fmt:message key="user.email"/></label><br />
					<input name="user.email" type="text" class="validate[required] text-input" /><br />
					
					<label for="user.password"><fmt:message key="user.password"/></label><br />
					<input name="user.password" type="password" class="validate[required] text-input" /><br />									
					
				</fieldset>
				<input type="submit" value="<fmt:message key="auth.submit"/>" />
			</form>
		</div>
	
	</div>
</div>