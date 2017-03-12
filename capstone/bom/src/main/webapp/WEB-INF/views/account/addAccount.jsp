<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    var addResult = '${addResult}';
    var attUsername = '${username}';
    var attFirstName = '${firstName}';
    var attLastName = '${lastName}';
    var attPhone = '${phone}';
    var attchkAdmin = '${chkAdmin}';
</script>
<script src="${pageContext.request.contextPath}/resources/js/addAccount.js"></script>

<html>
<head>
    <title><spring:message code="add_account"/></title>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/style.css" rel="stylesheet">
</head>
<body>

<div class="col-lg-12">
    <div class="ibox product-detail">
        <div class="ibox-content">
            <div class="row">
                <h2>
                    <i class="fa fa-database p-r-10"></i><spring:message code="add_account"/>
                </h2>
                <p>
                    Please input!
                </p>

                <form action="${pageContext.request.contextPath}/addAccount" class="form-horizontal form-label-left" method="POST"
                      onsubmit="return validateForm()">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-8">Username * </label>
                        <div class="col-md-4 col-sm-4 col-xs-8">
                            <input type="text" id="username" name="username"
                                   class="form-control col-md-7 col-xs-12">
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-8">
                            <div class="c-red usernameErr">
                                Username must be between 3 and 8 characters
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-8">First Name * </label>
                        <div class="col-md-4 col-sm-4 col-xs-8">
                            <input type="text" id="firstName" name="firstName"
                                   class="form-control col-md-7 col-xs-12">
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-8">
                            <div class="c-red firstNameErr">
                                First Name must not be empty
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-8">Last Name * </label>
                        <div class="col-md-4 col-sm-4 col-xs-8">
                            <input type="text" id="lastName" name="lastName"
                                   class="form-control col-md-7 col-xs-12">
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-8">
                            <div class="c-red lastNameErr">
                                Last Name must not be empty
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-8">Phone * </label>
                        <div class="col-md-4 col-sm-4 col-xs-8">
                            <input type="text" id="phone" name="phone"
                                   class="form-control col-md-7 col-xs-12">
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-8">
                            <div class="c-red phoneErr">
                                Phone number must be between 8 and 11 numeric characters
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-8"></label>
                        <label>
                            <input type="checkbox" name="chkAdmin" value="admin">
                            <i class="input-helper"></i>
                            is Admin
                        </label>
                    </div>


                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-primary waves-effect col-mb-3 fa fa-history" style="font-size:18px"
                               href="/viewAccount">
                                Back
                            </a>
                            <button type="reset" class="btn btn-primary fa fa-remove" style="font-size:18px"> Cancel
                            </button>
                            <button type="submit" class="btn btn-success fa fa-plus" style="font-size:18px"> Add
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