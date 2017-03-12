<%--
  Created by IntelliJ IDEA.
  User: manlm
  Date: 7/22/2016
  Time: 7:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><spring:message code="login"/></title>

    <!-- Vendor CSS -->
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/animate.css/animate.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/google-material-color/dist/palette.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/material-design-iconic-font/dist/css/material-design-iconic-font.min.css"
          rel="stylesheet">

    <!-- CSS -->
    <link href="${pageContext.request.contextPath}/resources/material-admin/css/app.min.1.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/material-admin/css/app.min.2.css" rel="stylesheet">

    <!-- Javascript Libraries -->
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/jquery/dist/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/vendors/bower_components/Waves/dist/waves.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/material-admin/js/functions.js"></script>
</head>

<body>
<div class="login" data-lbg="teal" style=" background-size:cover;background-image: url('${pageContext.request.contextPath}/resources/img/supermarket.png')">


    <!-- Login -->
    <div class="l-block toggled" id="l-login" style="background-color: transparent">
        <div class="lb-header palette-Teal bg" style="background-color: transparent !important;">
            <img src="${pageContext.request.contextPath}/resources/img/bot.png" style="width: 200px;height:200px;"/>
        </div>

        <div class="lb-body">
            <form action="j_spring_security_check" method="POST">
                <div class="form-group fg-float">
                    <div class="fg-line">
                        <input type="text" name="username" class="input-sm form-control fg-input" style="color: black">
                        <label class="fg-label" style="color: black"><spring:message code="username"/></label>
                    </div>
                </div>

                <div class="form-group fg-float">
                    <div class="fg-line">
                        <input type="password" name="password" class="input-sm form-control fg-input" style="color: black">
                        <label class="fg-label" style="color: black"><spring:message code="password"/></label>
                    </div>
                </div>

                <button type="submit" class="btn palette-Teal bg"><spring:message code="sign_in"/></button>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </div>
    </div>
</div>

</body>

</html>
