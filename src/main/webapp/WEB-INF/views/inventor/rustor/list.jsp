<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="inventor.rustor.list.label.code" path="code" />	
	<acme:list-column code="inventor.rustor.list.label.theme" path="theme" />
	<acme:list-column code="inventor.rustor.list.label.sahre" path="share" />
	<acme:list-column code="inventor.rustor.list.label.itemName" path="itemName" />
</acme:list>