<%--
- menu.jsp
-
- Copyright (C) 2012-2022 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@page language="java" import="acme.framework.helpers.PrincipalHelper"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:menu-bar code="master.menu.home">
	<acme:menu-left>
		<acme:menu-option code="master.menu.anonymous" access="isAnonymous()">
      		<acme:menu-suboption code="20622913D: Cambron Tocados, Vicente" action="https://www.reddit.com/user/isoyreddit/comments/ssn8l8/datos_inquietantes_que_no_querr%C3%A1s_conocer/"/>
			<acme:menu-suboption code="77976719A: Cavero Lopez, Francisco Javier" action="http://www.stackoverflow.com/"/>
		  	<acme:menu-suboption code="32070760C: Dominguez Garoz, Irene Xiang" action="https://www.zara.com/es/"/>
		  	<acme:menu-suboption code="29503178C: Garcia Fernandez, Alejandro" action="http://www.youtube.com/"/>
		  	<acme:menu-suboption code="15438310C: Garcia Marin, Jose Luis" action="https://www.fcbarcelona.es/"/>
			<acme:menu-suboption code="77858361A: Reyes Lopez, Marta" action="https://www.youtube.com/watch?v=dQw4w9WgXcQ"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.any.user-account" action="/any/user-account/list"/>
			<acme:menu-suboption code="master.menu.anonymous.list-all-toolkit" action="/any/toolkit/list"/>
			<acme:menu-suboption code="master.menu.anonymous.list-all-item" action="/any/item/list"/>
			<acme:menu-suboption code="master.menu.anonymous.chirps" action="/any/chirp/list-recent"/>
		</acme:menu-option>
    
		<acme:menu-option code="master.menu.authenticated" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.any.user-account" action="/any/user-account/list"/>
			<acme:menu-suboption code="master.menu.authenticated.list-all-toolkit" action="/any/toolkit/list"/>
			<acme:menu-suboption code="master.menu.authenticated.list-all-item" action="/any/item/list"/>
			<acme:menu-suboption code="master.menu.authenticated.money-exchange.perform" action="/authenticated/money-exchange/perform"/>
			<acme:menu-suboption code="master.menu.authenticated.chirps" action="/any/chirp/list-recent"/>
			<acme:menu-suboption code="master.menu.authenticated.announcements" action="/authenticated/announcement/list-recent"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.administrator" access="hasRole('Administrator')">
			<acme:menu-suboption code="master.menu.administrator.user-accounts" action="/administrator/user-account/list"/>
			<acme:menu-suboption code="master.menu.administrator.configuration" action="/administrator/configuration/show"/>
			<acme:menu-suboption code="master.menu.administrator.dashboard" action="/administrator/administrator-dashboard/show"/>
			<acme:menu-suboption code="master.menu.administrator.announcement" action="/administrator/announcement/create"/>
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.populate-initial" action="/administrator/populate-initial"/>
			<acme:menu-suboption code="master.menu.administrator.populate-sample" action="/administrator/populate-sample"/>			
			<acme:menu-separator/>
			<acme:menu-suboption code="master.menu.administrator.shut-down" action="/administrator/shut-down"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.patron" access="hasRole('Patron')">
			<acme:menu-suboption code="master.menu.patron.patron-dashboard" action="/patron/patron-dashboard/show"/>
			<acme:menu-suboption code="master.menu.patron.patronage.list" action="/patron/patronage/list"/>
			<acme:menu-suboption code="master.menu.patron.patronageReport.list" action="/patron/patronage-report/list"/>
    	</acme:menu-option>
    	
		<acme:menu-option code="master.menu.inventor" access="hasRole('Inventor')">
			<acme:menu-suboption code="master.menu.inventor.item.mine" action="/inventor/item/list"/>
			<acme:menu-suboption code="master.menu.inventor.toolkits.mine" action="/inventor/toolkit/list-mine"/>
			<acme:menu-suboption code="master.menu.inventor.patronage.list" action="/inventor/patronage/list"/>
			<acme:menu-suboption code="master.menu.inventor.patronageReport.list" action="/inventor/patronage-report/list"/>
			<acme:menu-suboption code="master.menu.inventor.rustor.list" action="/inventor/rustor/list"/>
		</acme:menu-option>
		
	</acme:menu-left>

	<acme:menu-right>
		<acme:menu-option code="master.menu.sign-up" action="/anonymous/user-account/create" access="isAnonymous()"/>
		<acme:menu-option code="master.menu.sign-in" action="/master/sign-in" access="isAnonymous()"/>

		<acme:menu-option code="master.menu.user-account" access="isAuthenticated()">
			<acme:menu-suboption code="master.menu.user-account.general-data" action="/authenticated/user-account/update"/>
			<acme:menu-suboption code="master.menu.user-account.become-patron" action="/authenticated/patron/create" access="!hasRole('Patron')"/>
			<acme:menu-suboption code="master.menu.user-account.patron" action="/authenticated/patron/update" access="hasRole('Patron')"/>
			<acme:menu-suboption code="master.menu.user-account.become-inventor" action="/authenticated/inventor/create" access="!hasRole('Inventor')"/>
			<acme:menu-suboption code="master.menu.user-account.inventor" action="/authenticated/inventor/update" access="hasRole('Inventor')"/>
		</acme:menu-option>

		<acme:menu-option code="master.menu.sign-out" action="/master/sign-out" access="isAuthenticated()"/>
	</acme:menu-right>
</acme:menu-bar>

