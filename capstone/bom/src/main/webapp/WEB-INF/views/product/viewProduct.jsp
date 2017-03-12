<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 19/10/2016
  Time: 8:56 AM
  To change this template use File | Settings | File Templates.
--%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${pageContext.request.contextPath}/resources/js/Common.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/dataTables/datatables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/jeditable/jquery.jeditable.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/sweetalert/sweetalert.min.js"></script>

<!-- Switchery -->
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/switchery/switchery.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/viewProduct.js"></script>

<html>
<head>
    <title>Product</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/dataTables/datatables.min.css">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/style.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/viewProduct.css">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/CommonAlert.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/switchery/switchery.css"
          rel="stylesheet">
</head>
<body>
<div class="row wrapper  white-bg page-heading">
    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px;padding-top: 25px;">
        <div class="col-md-12">
            <a class="btn btn-primary waves-effect col-mb-3 fa fa-plus" style="font-size: 20px"
               href="${pageContext.request.contextPath}/viewAddProduct">
            </a>
        </div>
        <div class="col-md-12">
            <h2>Manage product</h2>
        </div>
    </div>
    <div class="col-lg-15">

        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <div class="table-responsive">
                    <table id="basictable"
                           class="table table-striped table-bordered table-hover dataTables-example">
                        <thead>
                        <tr style="text-align: center">
                            <th style="background-color:#B0BEC5;" width="8%">No.</th>
                            <th style="background-color:#B0BEC5">Code</th>
                            <th style="background-color:#B0BEC5;">Name</th>
                            <th class="text-center" style="background-color:#B0BEC5">Price</th>

                            <th id="headerRelation" class="text-center" style="background-color:#B0BEC5">Relation
                            </th>
                            <th id="headerUpdate" class="text-center" style="background-color:#B0BEC5"
                                data-sorter="false">
                                Update
                            </th>
                            <th id="headerDetail" class="text-center" style="background-color:#B0BEC5" width="10%">
                                Details
                            </th>
                            <th id="headerDelete" class="text-center" style="background-color:#B0BEC5"
                                data-sorter="false" width="10%">
                                Change status
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <input id="sizelist" type="hidden" name="txtId" value="${listproduct.size()}"/>
                        <c:forEach var="list" items="${listproduct}" varStatus="counter">
                            <tr>

                                <td class="text-right" style="padding-right:35px;">${counter.count}</td>
                                <td>${list.code}</td>
                                <td>${list.name}</td>
                                <td class="text-right" style="padding-right:35px;" width="10%"><fmt:formatNumber
                                        type="number"
                                        maxFractionDigits="3" value="${list.price}"/></td>


                                <td class="text-center">
                                    <form action="${pageContext.request.contextPath}/viewRelation">
                                        <input type="hidden" name="txtProductId" value="${list.id}"/>
                                        <button type="submit" class="btn btn-primary btn-circle"><i
                                                class="fa fa-exchange"></i></button>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </form>
                                </td>
                                <td class="text-center">
                                    <form action="${pageContext.request.contextPath}/viewUpdateProduct" method="GET"
                                          id="formUpdate"
                                          class="form-horizontal form-label-left">
                                        <input type="hidden" name="txtId" value="${list.id}"/>
                                        <input type="hidden" name="txtCode" value="${list.code}"/>
                                        <input type="hidden" name="txtName" value="${list.name}"/>
                                        <input type="hidden" name="txtPrice" value="${list.price}"/>
                                        <input type="hidden" name="txtDetails" value="${list.details}"/>
                                        <input type="hidden" name="txtAreaName" value="${list.areaName}"/>
                                        <input type="hidden" name="txtAreaId" value="${list.areaId}"/>
                                        <input type="hidden" name="txtImgUrl" value="${list.imgUrl}"/>
                                        <input type="hidden" name="txtImgPromotionUrl" value="${list.imgPromotionUrl}"/>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input id="listProductId" type="hidden" name="listProductId"/>
                                        <button type="submit" class="btn btn-success btn-circle"><i
                                                class="fa fa-edit "></i></button>
                                    </form>
                                </td>

                                <td class="text-center">
                                    <a href="${pageContext.request.contextPath}/viewDetailProduct?txtId=${list.id}"
                                       class="btn btn-sm btn-primary"> More info</a>
                                </td>
                                <td class="text-center">
                                    <c:if test="${list.status==true}">
                                        <form action="${pageContext.request.contextPath}/deleteProduct" method="POST"
                                              id="deactivate-account"
                                              class="form-horizontal form-label-left form-deactivate-${list.id}">
                                            <button type="button" value="${list.id}"
                                                    class="btn palette-Red bg waves-effect btn-deactivate">
                                                Deactivate
                                            </button>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <input type="hidden" name="txtId" value="${list.id}"/>
                                        </form>
                                    </c:if>
                                    <c:if test="${list.status==false}">
                                        <form action="${pageContext.request.contextPath}/deleteProduct" method="POST"
                                              id="activate-account"
                                              class="form-horizontal form-label-left form-activate-${list.id}">
                                            <button type="button" value="${list.id}"
                                                    class="btn btn-info btn-icon-text waves-effect btn-activate">
                                                Activate
                                            </button>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <input type="hidden" name="txtId" value="${list.id}"/>
                                        </form>
                                    </c:if>
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


<!-- Modal -->
<div class="modal fade" id="deactivateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="deactivateModalLabel" style="color: red">Warning</h4>
            </div>
            <div class="modal-body">
                <h2>Do you want to deactivate product?</h2>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button id="deactivate-submit" type="button" class="btn btn-danger">Deactivate</button>
            </div>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="activateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="activateModalLabel" style="color: red">Warning</h4>
            </div>
            <div class="modal-body">
                <h2>Do you want to activate product?</h2>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button id="activate-submit" type="button" class="btn btn-info">Activate</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>

