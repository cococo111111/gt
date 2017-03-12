<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 31/10/2016
  Time: 1:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script src="${pageContext.request.contextPath}/resources/js/Common.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/detailProduct.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/slick/slick.min.js"></script>
<html>
<head>
    <title>Detail</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/dataTables/datatables.min.css">


</head>
<body>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox product-detail">
            <div class="ibox-content">
                <div class="row">
                    <div class="col-md-5">
                        <div class="ratio-wrapper ratio-wrapper-4-3">
                            <div class="ratio-item">
                                <img class="img-responsive" src="${product.imgUrl}"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-7" style="font-size: 17px">

                        <h2 class="font-bold m-b-xs">
                            ${product.name}
                        </h2>
                        <small>Code: ${product.code}</small>
                        <div class="m-t-md">
                            <h2 class="product-main-price"><span class="numbers">${product.price} VNĐ</span>
                            </h2>
                        </div>
                        <hr>
                        <h4>Product description</h4>
                        <div class=" text-muted">
                            ${product.details}

                            <br/>
                            <br/>

                        </div>
                        <h4>Location</h4>
                        <div class="small text-muted">
                            ${product.areaLocation}
                            <br/>
                            <br/>
                        </div>
                        <h4>Area</h4>
                        <div class="small text-muted">
                            ${product.areaName}
                            <br/>
                            <br/>
                        </div>
                        <dl class="small m-t-md">
                            <dt>Promotion</dt>
                            <c:forEach var="list" items="${listPromotion}" varStatus="counter">
                                <dd>
                                    Event name: ${list.details}: ${list.discountRate}
                                    <c:if test="${list.discountRate!=0}">% </c:if>
                                    <jsp:useBean id="dateObject" class="java.util.Date"/>
                                    <jsp:setProperty name="dateObject" property="time" value="${list.startDate}"/>
                                    From: <b><fmt:formatDate value="${dateObject}" pattern="dd/MM/yyyy"/></b>
                                    <jsp:setProperty name="dateObject" property="time" value="${list.endDate}"/>
                                    To: <b><fmt:formatDate value="${dateObject}" pattern="dd/MM/yyyy"/></b>
                                </dd>
                            </c:forEach>
                            <c:if test="${not empty listSuggestion}">
                                <dt>Product relation</dt>


                                <c:forEach var="list" items="${listSuggestion}" varStatus="counter">
                                    <dd>${list.name}</dd>
                                </c:forEach>
                            </c:if>
                        </dl>
                        <hr>
                        <form action="${pageContext.request.contextPath}/viewUpdateProduct" method="GET">
                            <div class="col-md-5">
                                <div class="btn-group">
                                    <a class="btn btn-primary waves-effect col-mb-3 fa fa-history"
                                       style="font-size:18px" href="${pageContext.request.contextPath}/viewProduct">
                                        Back
                                    </a>
                                </div>
                                <input type="hidden" name="txtId" value="${product.id}"/>
                                <input type="hidden" name="txtCode" value="${product.code}"/>
                                <input type="hidden" name="txtName" value="${product.name}"/>
                                <input type="hidden" name="txtPrice" value="${product.price}"/>
                                <input type="hidden" name="txtDetails" value="${product.details}"/>
                                <input type="hidden" name="txtAreaName" value="${product.areaName}"/>
                                <input type="hidden" name="txtAreaId" value="${product.areaId}"/>
                                <input type="hidden" name="txtImgUrl" value="${product.imgUrl}"/>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <button type="submit" class="btn btn-success fa fa-edit" style="font-size:18px;"
                                        id="submit_update">Go to update
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
