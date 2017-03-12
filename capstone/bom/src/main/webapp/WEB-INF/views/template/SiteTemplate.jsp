<%--
  Created by IntelliJ IDEA.
  User: manlm
  Date: 8/8/2016
  Time: 9:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <%--Material Admin------------------------------------------------------------------------------------------------%>

    <!-- Vendor CSS -->
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/fullcalendar/dist/fullcalendar.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/animate.css/animate.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/google-material-color/dist/palette.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/bootstrap-select/dist/css/bootstrap-select.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/nouislider/distribute/jquery.nouislider.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/css/bootstrap-datetimepicker.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/farbtastic/farbtastic.css"
          rel="stylesheet">
    <%--<link href="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/chosen/chosen.min.css"
          rel="stylesheet">--%>
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/summernote/dist/summernote.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/bootstrap-sweetalert/lib/sweet-alert.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/bootgrid/jquery.bootgrid.min.css"
          rel="stylesheet">

    <!-- CSS -->
    <link href="${pageContext.request.contextPath}/resources/material-admin/css/app.min.1.css" rel="stylesheet">
    <%--<link href="${pageContext.request.contextPath}/resources/material-admin/css/app.min.2.css" rel="stylesheet">--%>

    <%----------------------------------------------------------------------------------------------------------------%>
    <%--jQuery--%>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/jquery/dist/jquery.min.js"></script>

    <%--Boostrap--%>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <%----------------------------------------------------------------------------------------------------------------%>


    <%--Material Admin------------------------------------------------------------------------------------------------%>

    <!-- Core Scripts - Include with every page -->
    <!-- Javascript Libraries -->

    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/Waves/dist/waves.min.js"
            type=""></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/bootstrap-growl/bootstrap-growl.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/moment/min/moment.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/bootstrap-select/dist/js/bootstrap-select.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/nouislider/distribute/jquery.nouislider.all.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/eonasdan-bootstrap-datetimepicker/build/js/bootstrap-datetimepicker.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/typeahead.js/dist/typeahead.bundle.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/summernote/dist/summernote-updated.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/bootgrid/jquery.bootgrid.updated.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/chosen/chosen.jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/fileinput/fileinput.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/input-mask/input-mask.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/farbtastic/farbtastic.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/bootstrap-sweetalert/lib/sweet-alert.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/autosize/dist/autosize.min.js"></script>

    <script src="${pageContext.request.contextPath}/resources/material-admin/js/functions.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/js/actions.js"></script>

    <link href="${pageContext.request.contextPath}/resources/css/layout.css"
          rel="stylesheet">

    <script src="${pageContext.request.contextPath}/resources/js/layout.js"></script>

</head>

<body>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="curPage" scope="request"
       value="${fn:replace(requestScope['javax.servlet.forward.request_uri'],pageContext.request.contextPath ,'' )}"/>
<div id="wrapper">

</div>
<tiles:insertAttribute name="menu"/>

<div id="page-wrapper" class="gray-bg">
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="footer"/>
    <div class="wrapper wrapper-content animated fadeInRight my-wrapper">
        <tiles:insertAttribute name="body"/>
    </div>
</div>

<form id="logoutForm" name="logoutForm" class="form-horizontal"
      role="form"
      action="${pageContext.request.contextPath}/j_spring_security_logout"
      method="POST">
    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>
</form>

</body>

</html>
