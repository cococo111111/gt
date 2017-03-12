<%--
  Created by IntelliJ IDEA.
  User: manlm
  Date: 8/8/2016
  Time: 9:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%--Inspinia-----------------------------------------------------------------------------------------------------%>

<%--<link href="${pageContext.request.contextPath}/resources/inspinia/css/bootstrap.min.css" rel="stylesheet">--%>
<link href="${pageContext.request.contextPath}/resources/inspinia/font-awesome/css/font-awesome.css"
      rel="stylesheet">

<!-- Toastr style -->
<link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/toastr/toastr.min.css"
      rel="stylesheet">

<link href="${pageContext.request.contextPath}/resources/inspinia/css/animate.css" rel="stylesheet">
<link href="${pageContext.request.contextPath}/resources/inspinia/css/style.css" rel="stylesheet">

<%--Inspinia-----------------------------------------------------------------------------------------------------%>

<!-- Mainly scripts -->
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

<!-- Custom and plugin javascript -->
<script src="${pageContext.request.contextPath}/resources/inspinia/js/inspinia.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/pace/pace.min.js"></script>

<!-- Toastr -->
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/toastr/toastr.min.js"></script>

<div class="row border-bottom my-row">
    <nav class="navbar navbar-static-top white-bg my-navbar" role="navigation">
        <div id="nav-btn" class="navbar-header">
            <a class="navbar-minimalize minimalize-styl-2 btn btn-primary" href="#" onclick="updateWidthOnClick()">
                <i class="fa fa-bars"></i>
            </a>
        </div>
        <ul class="nav navbar-top-links navbar-right">
            <li>
                <span class="m-r-sm text-muted welcome-message"><spring:message code="welcome_message_full"/></span>
            </li>
            <li>
                <a href="#" onclick="document.getElementById('logoutForm').submit()">
                    <i class="fa fa-sign-out"></i>
                    <spring:message code="logout"/>
                </a>
            </li>
        </ul>
    </nav>
</div>