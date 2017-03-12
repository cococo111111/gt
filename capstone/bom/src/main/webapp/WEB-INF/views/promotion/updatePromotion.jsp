<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 04/11/2016
  Time: 7:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script src="${pageContext.request.contextPath}/resources/js/updatePromotion.js"></script>
<html>
<head>
    <title>Insert Promotion</title>
</head>
<body>

<div class="col-lg-12">
    <div class="ibox product-detail">
        <div class="ibox-content">
            <div class="row">
                <h2>
                    <i class="fa fa-database"></i> Update Promotion
                </h2>
                <form action="${pageContext.request.contextPath}/updatePromotion" method="post" id="form-promotion"
                      class="form-horizontal form-label-left">
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-8">Name <span
                                class="required">*</span>
                        </label>
                        <div class="col-md-4 col-sm-4 col-xs-8">
                            <input type="text" id="codeproduct" required="required"
                                   class="form-control col-md-7 col-xs-12"
                                   name="txtName" value="${txtName}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="price-product" class="control-label col-md-3 col-sm-3 col-xs-8">Discount rate<span
                                class="required">*</span>
                        </label>
                        <div class="col-md-4 col-sm-4 col-xs-8">
                            <input id="price-product" class="form-control col-md-7 col-xs-8" type="number"
                                   name="txtDiscountRate"
                                   required="required" min="0" max="100"
                                   value="${txtDiscountRate}"
                            >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-8">Details <span
                                class="required">*</span>
                        </label>
                        <div class="col-md-5 col-sm-4 col-xs-8">
                            <input type="text" required="required"
                                   class="form-control col-md-7 col-xs-12"
                                   name="txtDetails" value="${txtDetails}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3 col-sm-3 col-xs-8">Start <span
                                class="required"></span>
                        </label>
                        <div class="col-md-2 col-sm-4 col-xs-8">
                            <jsp:useBean id="dateObject" class="java.util.Date"/>
                            <jsp:setProperty name="dateObject" property="time" value="${txtStartDate}"/>
                            <input type="date" required="required"
                                   class="form-control col-md-5 col-xs-12" id="startDate"
                                   name="txtStartDate"
                                   value="<fmt:formatDate value="${dateObject}" pattern="yyyy-MM-dd"/>">
                        </div>
                        <label class="control-label col-md-1 ">End <span
                                class="required"></span>
                        </label>
                        <div class="col-md-2 col-sm-4 col-xs-8">
                            <jsp:setProperty name="dateObject" property="time" value="${txtEndDate}"/>
                            <input type="date" required="required"
                                   class="form-control col-md-5 col-xs-12" id="endDate"
                                   name="txtEndDate"
                                   value="<fmt:formatDate value="${dateObject}" pattern="yyyy-MM-dd"/>">
                        </div>
                        <div class="col-md-4 col-sm-4 col-xs-8"><i style="color:red" id="error-date"></i>
                        </div>
                    </div>
                    <input type="hidden" name="txtId" value="${txtId}"/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="form-group">
                        <div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
                            <a class="btn btn-primary waves-effect col-mb-3 fa fa-history" style="font-size:18px"
                               href="${pageContext.request.contextPath}/viewPromotion">
                                Back
                            </a>
                            <button type="reset" class="btn btn-primary fa fa-remove" style="font-size:18px"> Cancel
                            </button>
                            <button type="submit" class="btn btn-success fa fa-edit" style="font-size:18px"
                                    id="submit_update"> Update
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
