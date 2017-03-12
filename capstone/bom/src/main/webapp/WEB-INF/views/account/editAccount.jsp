<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script src="${pageContext.request.contextPath}/resources/js/editAccount.js"></script>

<html>
<head>
    <title><spring:message code="add_beacon"/></title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/style.css" rel="stylesheet">
</head>
<body>
<div class="col-lg-12">
    <c:set var="account" value="${AccountEmployee}"></c:set>
    <div class="ibox product-detail">
        <div class="ibox-content">
            <div class="row">
                <h2>
                    <i class="fa fa-database p-r-10"></i><spring:message code="update_profile"/>
                </h2>
                <p>
                    Please input!
                </p>

                <form action="${pageContext.request.contextPath}/editAccount" class="form-horizontal form-label-left"
                      onsubmit="return validateForm()">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-8">Username </label>
                        <div class="col-md-4 col-sm-4 col-xs-8">
                            <input type="text" class="form-control col-md-7 col-xs-12" id="username" name="username"
                                   value="${account.username}" readonly="readonly" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-8">Email </label>
                        <div class="col-md-4 col-sm-4 col-xs-8">
                            <input type="text" class="form-control col-md-7 col-xs-12"
                                   value="${account.email}" readonly="readonly" >
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-8">First Name * </label>
                        <div class="col-md-4 col-sm-4 col-xs-8">
                            <input type="text"id="firstName" name="firstName"  value="${account.firstName}"
                                   class="form-control col-md-7 col-xs-12">
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-8">
                            <div class="c-red firstNameErr">
                                First Name must not be null
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-8">Last Name </label>
                        <div class="col-md-4 col-sm-4 col-xs-8">
                            <input type="text" id="lastName" name="lastName"  value="${account.lastName}"
                                   class="form-control col-md-7 col-xs-12">
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-8">
                            <div class="c-red lastNameErr">
                                Last Name must not be null
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-8">Phone Number </label>
                        <div class="col-md-4 col-sm-4 col-xs-8">
                            <input type="text" id="phone" name="phone" value="${account.phone}"
                                   class="form-control col-md-7 col-xs-12">
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-8">
                            <div class="c-red phoneErr">
                                Phone number must be numeric
                            </div>
                        </div>
                    </div>

                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <button type="reset" class="btn btn-primary fa fa-remove" style="font-size:18px"> Cancel
                            </button>
                            <button type="submit" class="btn btn-success fa fa-edit" style="font-size:18px"> Update
                            </button>
                        </div>
                    </div>
                </form>
            </div>

        </div>

    </div>

</div>
</div>
</body>
</html>

