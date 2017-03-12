<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 07/11/2016
  Time: 10:34 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
<html>
<head>
    <title>Category</title>
</head>
<body>
<div class="col-lg-12">
    <div class="ibox product-detail">
        <div class="ibox-content">
            <div class="row">
                <h2>
                    <i class="fa fa-database"></i> Update Category
                </h2>
                <p>
                </p>
                <form action="${pageContext.request.contextPath}/updateCategory" method="post" id="form-category"
                      class="form-horizontal form-label-left">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-8">Name <span
                                class="required">*</span>
                        </label>
                        <div class="col-md-4 col-sm-4 col-xs-8">
                            <input type="text" id="codeproduct" required="required"
                                   class="form-control col-md-7 col-xs-12"
                                   name="txtName" value="${txtName}" maxlength="80">
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-8">
                            <span class="text-danger">${errorMessage}</span>
                        </div>
                    </div>
                    <div class="form-group">
                        <input type="hidden" name="txtId" value="${txtId}"/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div class="form-group">
                            <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                                <a class="btn btn-primary waves-effect col-mb-3 fa fa-history" style="font-size:18px"
                                   href="${pageContext.request.contextPath}/viewCategory">
                                    Back
                                </a>
                                <button type="reset" class="btn btn-primary fa fa-remove" style="font-size:18px"> Cancel
                                </button>
                                <button type="submit" class="btn btn-success fa fa-edit" style="font-size:18px"
                                        id="submit_add"> Update
                                </button>
                            </div>
                        </div>
                    </div>
                </form>

            </div>

        </div>

    </div>
</div>
</body>
</html>
