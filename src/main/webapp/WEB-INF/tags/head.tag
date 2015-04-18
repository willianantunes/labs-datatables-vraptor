<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@attribute name="title" type="java.lang.String" required="true"%>
<%@attribute name="description" type="java.lang.String" required="false"%>
<%@attribute name="includeHead" type="java.lang.Boolean" required="true"%>

<!DOCTYPE html>
<html>
<head>
<title><c:out value="${title}" escapeXml="true" /></title>

<c:set var="metaDescription" value="${description}" scope="request" />

<!-- Configs: metas -->

<tags:brutal-include value="metas" />

<!-- Configs: styles -->

<tags:brutal-include value="styles" />

<!-- Configs: scripts -->

<tags:brutal-include value="javascripts" />

</head>
<body>

<noscript><fmt:message key="warning.javascript_disabled"/></noscript>

<c:if test="${includeHead eq true}">
	<tags:brutal-include value="header" />
</c:if>

