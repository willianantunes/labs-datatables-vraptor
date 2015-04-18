<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="siteName" value="${t['site.name']}"/>

<c:set var="title" value="${t['metas.user.title']}"/>

<tags:head title="${siteName} - ${title}" includeHead="true"/>

<div class="torso">
	<div class="torso-content">
	
	<h1>${title} - <a href="<c:url value="/users/add" />" title="<fmt:message key="user.create_action"/>"><fmt:message key="message.add"/></a></h1>
	<table id="usersListDataTables" class="hover cell-border order-column td-small-font-size">
	    <thead>
	        <tr>
				<th><fmt:message key="message.actions"/></th>
				<th><fmt:message key="user.name"/></th>
				<th><fmt:message key="user.email"/></th>
				<th><fmt:message key="user.register_date"/></th>				            
	        </tr>  
	    </thead>	
	</table>

	</div>
</div><!-- #torso -->