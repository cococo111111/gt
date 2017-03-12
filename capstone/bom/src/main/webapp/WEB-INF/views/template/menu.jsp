<%--
  Created by IntelliJ IDEA.
  User: manlm
  Date: 8/8/2016
  Time: 9:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
</sec:authorize>

<nav id="nav-menu" class="navbar-default navbar-static-side my-menu" role="navigation">
    <div class="sidebar-collapse">
        <ul class="nav metismenu" id="side-menu">
            <li class="nav-header">
                <div class="dropdown profile-element">
                    <span>
                        <img alt="image" class="img-circle"
                             src="${pageContext.request.contextPath}/resources/img/profile.jpg"/>
                    </span>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <span class="clear">
                            <span class="block m-t-xs">
                                <strong class="font-bold">
                                    ${sessionScope.username}
                                </strong>
                            </span>
                            <span class="text-muted text-xs block">
                                ${sessionScope.role}
                                <b class="caret"></b>
                            </span>
                        </span>
                    </a>
                    <ul class="dropdown-menu animated fadeInRight m-t-xs">
                        <li>
                            <a href="${pageContext.request.contextPath}/viewEditAccount?username=${sessionScope.username}"><spring:message
                                    code="profile"/></a></li>
                        <li class="divider"></li>
                        <li>
                            <a href="#" onclick="document.getElementById('logoutForm').submit()">
                                <spring:message code="logout"/>
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="logo-element">
                    <spring:message code="ISMB_short"/>
                </div>
            </li>
            <c:if test="${principal.authorities=='[ADMIN]'}">
                <li><a><i class="fa fa-desktop"></i><span class="nav-label">Account &nbsp;</span></a>
                    <ul class="nav child_menu">
                        <li><a href="${pageContext.request.contextPath}/viewAccount"><i class="fa fa-th-large"></i>
                            <span
                                    class="nav-label">View</span> </a>
                        <li>
                        <li><a href="${pageContext.request.contextPath}/viewAddAccount"><i class="fa fa-th-large"></i>
                            <span
                                    class="nav-label">Add</span> </a>
                        <li>
                    </ul>
                </li>
            </c:if>
            <c:if test="${principal.authorities=='[STAFF]'}">
                <li
                        <c:if test="${curPage == '/viewProduct' or curPage == '/viewAddProduct' or curPage == '/viewImportData'}">class="active"</c:if>>
                    <a><i class="fa fa-dropbox"></i><span class="nav-label">Product &nbsp;</span></a>
                    <ul class="nav child_menu">
                        <li <c:if test="${curPage == '/viewProduct'}">class="active"</c:if>>
                            <a href="${pageContext.request.contextPath}/viewProduct"><i class="fa fa-th-large"></i>
                                <span
                                        class="nav-label">View</span> </a>
                        </li>
                        <li <c:if test="${curPage == '/viewAddProduct'}">class="active"</c:if>><a
                                href="${pageContext.request.contextPath}/viewAddProduct"><i class="fa fa-plus"></i>
                            <span
                                    class="nav-label">Add</span> </a>
                        <li>
                        <li <c:if test="${curPage == '/viewImportData'}">class="active"</c:if>><a
                                href="${pageContext.request.contextPath}/viewImportData"><i class="fa fa-download"></i>
                            <span
                                    class="nav-label">Import data</span> </a>
                        <li>
                    </ul>
                </li>
                <li <c:if test="${curPage == '/viewBeacon'}">class="active"</c:if>><a><i
                        class="fa fa-bullseye"></i><span class="nav-label">Beacon &nbsp;</span></a>
                    <ul class="nav child_menu">
                        <li <c:if test="${curPage == '/viewBeacon'}">class="active"</c:if>><a
                                href="${pageContext.request.contextPath}/viewBeacon"><i class="fa fa-th-large"></i>
                            <span
                                    class="nav-label">View</span> </a>
                        <li>
                    </ul>
                </li>
                <li <c:if test="${curPage == '/viewArea'}">class="active"</c:if>><a><i
                        class="fa fa-globe"></i><span class="nav-label">Area &nbsp;</span></a>
                    <ul class="nav child_menu">
                        <li <c:if test="${curPage == '/viewArea'}">class="active"</c:if>><a
                                href="${pageContext.request.contextPath}/viewArea"><i class="fa fa-th-large"></i> <span
                                class="nav-label">View</span> </a>
                        <li>
                    </ul>
                </li>
                <li <c:if test="${curPage == '/viewPromotion' or curPage == '/viewAddPromotion'}">class="active"</c:if>>
                    <a><i class="fa fa-gift"></i><span class="nav-label">Promotion &nbsp;</span></a>
                    <ul class="nav child_menu">
                        <li <c:if test="${curPage == '/viewPromotion'}">class="active"</c:if>><a
                                href="${pageContext.request.contextPath}/viewPromotion"><i class="fa fa-th-large"></i>
                            <span
                                    class="nav-label">View</span> </a>
                        <li>
                        <li <c:if test="${curPage == '/viewAddPromotion'}">class="active"</c:if>><a
                                href="${pageContext.request.contextPath}/viewAddPromotion"><i class="fa fa-plus"></i>
                            <span
                                    class="nav-label">Add</span> </a>
                        <li>
                    </ul>
                </li>
                <li <c:if test="${curPage == '/viewFloor' or curPage == '/viewAddFloor'}">class="active"</c:if>><a><i
                        class="fa fa-align-center"></i><span class="nav-label">Floor &nbsp;</span></a>
                    <ul class="nav child_menu">
                        <li <c:if test="${curPage == '/viewFloor'}">class="active"</c:if>><a
                                href="${pageContext.request.contextPath}/viewFloor"><i class="fa fa-th-large"></i> <span
                                class="nav-label">View</span> </a>
                        <li>
                        <li <c:if test="${curPage == '/viewAddFloor'}">class="active"</c:if>><a
                                href="${pageContext.request.contextPath}/viewAddFloor"><i class="fa fa-plus"></i> <span
                                class="nav-label">Add</span> </a>
                        <li>
                    </ul>
                </li>
                <li <c:if test="${curPage == '/viewCategory' or curPage == '/viewAddCategory'}">class="active"</c:if>>
                    <a><i class="fa fa-tags"></i><span class="nav-label">Category &nbsp;</span></a>
                    <ul class="nav child_menu">
                        <li <c:if test="${curPage == '/viewCategory' }">class="active"</c:if>><a
                                href="${pageContext.request.contextPath}/viewCategory"><i class="fa fa-th-large"></i>
                            <span
                                    class="nav-label">View</span> </a>
                        <li>
                        <li <c:if test="${curPage == '/viewAddCategory'}">class="active"</c:if>><a
                                href="${pageContext.request.contextPath}/viewAddCategory"><i class="fa fa-plus"></i>
                            <span
                                    class="nav-label">Add</span> </a>
                        <li>
                    </ul>
                </li>
                <li
                        <c:if test="${curPage == '/viewKeyOriginal' or curPage == '/viewAddKeyOriginal'}">class="active"</c:if>>
                    <a><i class="fa fa-sitemap"></i><span class="nav-label">Synonym &nbsp;</span></a>
                    <ul class="nav child_menu">
                        <li <c:if test="${curPage == '/viewKeyOriginal'}">class="active"</c:if>><a
                                href="${pageContext.request.contextPath}/viewKeyOriginal"><i class="fa fa-th-large"></i>
                            <span
                                    class="nav-label">View Original Word</span> </a>
                        <li>
                        <li <c:if test="${curPage == '/viewAddKeyOriginal'}">class="active"</c:if>><a
                                href="${pageContext.request.contextPath}/viewAddKeyOriginal"><i class="fa fa-plus"></i>
                            <span
                                    class="nav-label">Add Original Word</span> </a>
                        <li>
                    </ul>
                </li>
                <li <c:if test="${curPage == '/viewChatLog'}">class="active"</c:if>><a><i
                        class="fa fa-comment"></i><span class="nav-label">Chat log &nbsp;</span></a>
                    <ul class="nav child_menu">
                        <li <c:if test="${curPage == '/viewChatLog'}">class="active"</c:if>><a
                                href="${pageContext.request.contextPath}/viewChatLog"><i class="fa fa-th-large"></i>
                            <span
                                    class="nav-label">View </span> </a>
                        <li>
                    </ul>
                </li>
            </c:if>
        </ul>
    </div>
</nav>