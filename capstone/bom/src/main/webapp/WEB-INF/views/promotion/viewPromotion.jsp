<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 31/10/2016
  Time: 8:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/dataTables/datatables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/jeditable/jquery.jeditable.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/viewPromotion.js"></script>
<html>
<head>
    <title>Manage Promotion</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/dataTables/datatables.min.css">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/CommonAlert.css" rel="stylesheet">
</head>
<body>
<div class="row wrapper  white-bg page-heading">
    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px;padding-top: 25px;">
        <div class="col-md-12">
            <a class="btn btn-primary waves-effect col-mb-3 fa fa-plus" style="font-size: 20px"
               href="${pageContext.request.contextPath}/viewAddPromotion">
            </a>
        </div>
        <div class="col-md-12">
            <h2>Manage Promotion</h2>
        </div>
    </div>
    <div class="row">
        <div class="col-lg-15">
            <div class="ibox float-e-margins">

                <div class="ibox-content">
                    <div class="table-responsive">
                        <table id="basictable"
                               class="table table-striped table-bordered table-hover dataTables-example">
                            <thead>
                            <tr style="text-align: center">
                                <th width="7%" style="background-color:#B0BEC5;">No.</th>
                                <th style="background-color:#B0BEC5;">Name</th>
                                <th class="text-center" style="background-color:#B0BEC5" width="13%">Discount rate</th>
                                <th style="background-color:#B0BEC5">Details</th>
                                <th style="background-color:#B0BEC5;">Start</th>
                                <th style="background-color:#B0BEC5">End</th>
                                <th id="addItems" class="text-center" style="background-color:#B0BEC5"
                                    data-sorter="false">
                                    Add items
                                </th>
                                <th id="headerUpdate" class="text-center" style="background-color:#B0BEC5"
                                    data-sorter="false">
                                    Update
                                </th>
                                <th id="headerDelete" class="text-center" style="background-color:#B0BEC5"
                                    data-sorter="false">
                                    Delete
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <input id="sizelist" type="hidden" name="txtId" value="${listpromotion.size()}"/>
                            <c:forEach var="list" items="${listpromotion}" varStatus="counter">
                                <tr>
                                    <td class="text-right" style="padding-right:35px;">${counter.count}</td>
                                    <td>${list.name}</td>
                                    <td class="text-right" style="padding-right: 40px">${list.discountRate}%</td>
                                    <td>${list.details}</td>
                                    <td>
                                        <jsp:useBean id="dateObject" class="java.util.Date"/>
                                        <jsp:setProperty name="dateObject" property="time" value="${list.startDate}"/>
                                        <b><fmt:formatDate value="${dateObject}" pattern="dd/MM/yyyy"/></b>
                                    </td>
                                    <td>
                                        <jsp:setProperty name="dateObject" property="time" value="${list.endDate}"/>
                                        <b><fmt:formatDate value="${dateObject}" pattern="dd/MM/yyyy"/></b>
                                    </td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/viewItem?txtId=${list.id}"
                                           class="btn btn-primary btn-circle"><i class="fa fa-cubes"></i></a>
                                    </td>
                                    <form action="${pageContext.request.contextPath}/viewUpdatePromotion" method="GET"
                                          id="formUpdate"
                                          class="form-horizontal form-label-left">
                                        <input type="hidden" name="txtId" value="${list.id}"/>
                                        <input type="hidden" name="txtName" value="${list.name}"/>
                                        <input type="hidden" name="txtDetails" value="${list.details}"/>
                                        <input type="hidden" name="txtDiscountRate" value="${list.discountRate}"/>
                                        <input type="hidden" name="txtStartDate" value="${list.startDate}"/>
                                        <input type="hidden" name="txtEndDate" value="${list.endDate}"/>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <td class="text-center">
                                            <button type="submit" class="btn btn-success btn-circle"><i
                                                    class="fa fa fa-edit"></i></button>
                                        </td>
                                    </form>
                                    <td class="text-center">
                                        <form action="${pageContext.request.contextPath}/deletePromotion" method="POST"
                                              id="delete-promotion"
                                              class="form-horizontal form-label-left form-delete-${list.id}">
                                            <button type="button" value="${list.id}"
                                                    class="btn btn-danger btn-circle  btn-sm ">
                                                <i
                                                        class="fa fa-times"></i></button>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <input type="hidden" name="txtId" value="${list.id}"/>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="color: red">Warning</h4>
            </div>
            <div class="modal-body">
                <h2>Do you want to delete promotion?</h2>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button id="delete-submit" type="button" class="btn btn-danger">Delete</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
