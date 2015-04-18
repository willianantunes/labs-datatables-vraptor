<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<fmt:message key="site.name" var="siteName" />

<fmt:message key="not_found.title" var="title"/>

<fmt:message key="metas.generic.title" var="genericTitle" >
	<fmt:param value="${siteName}" />
</fmt:message>

<c:url value="/" var="rootPath" />

<tags:head title="${genericTitle} - ${title}" includeHead="false"/>

<div class="torso">
	<div class="torso-content">
		<div class="painel-login">
			<h1><a href="http://willianantunes.blogspot.com.br/" target="_blank"><fmt:message key="metas.page.name"/></a></h1>
			<div class="clear"></div>
			<h1 class="errorCodeTitle">405 - <fmt:message key="method_not_allowed.title"/></h1>
			<p class="errorCodeMessage">${t['http_status_code.standard.message'].args(rootPath)}.</p>
		</div>	
	</div>
</div><!-- #torso -->