<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 04/11/2016
  Time: 2:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/resources/js/Common.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/dataTables/datatables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/jeditable/jquery.jeditable.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/sweetalert/sweetalert.min.js"></script>
<!-- Chosen -->
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/chosen/chosen.jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/viewItemInCate.js"></script>

<html>
<head>
    <title>Relation</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/dataTables/datatables.min.css">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/viewProductInCate.css" rel="stylesheet">
</head>
<body>
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10 m-t-30">

        <div class="col-lg-4">
            <img class="img-thumbnail" alt="Cinque Terre"
                 src="${product.imgUrl}" alt="Smiley face">
        </div>
        <div class="col-lg-6" style="font-size: 17px">

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
            </div>
            <br>
            <a class="btn btn-primary waves-effect col-mb-3 fa fa-history"
               style="font-size:18px" href="${pageContext.request.contextPath}/viewProduct">
                Back
            </a>
        </div>
        <div class="m-t-10 m-b-10 m-l-15">
        </div>
    </div>
    <br>

    <div class="col-xs-12 m-t-5">
        <hr>
        <form action="${pageContext.request.contextPath}/addRelation" method="POST">
            <div class=" col-xs-6">
                <div class="m-b-10">
                    <h3>Not Suggest</h3>
                </div>

                <table
                        class="table table-striped table-bordered table-hover ">
                    <thead>
                    <tr style="text-align: center">
                        <th width="70%">Product</th>
                        <th id="addItems" width="30%"
                            data-sorter="false">
                            Weight
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>
                            <select id="select_item" name="txtRelativeProductId" data-placeholder="Choose a product..."
                                    class="chosen-select text-center"
                                    style="width:90%"
                                    tabindex="4">
                                <c:forEach var="listProduct" items="${listNotSuggest}">
                                    <option value="${listProduct.id}">${listProduct.name}</option>
                                </c:forEach>
                            </select>
                        </td>
                        <td class="text-center">
                            <input type="number" name="txtWeight" value="1" style="font-size: 18px;margin-top: 12px;">
                        </td>
                    </tr>
                    </tbody>
                </table>
                <button id="btn_add" type="submit" class="btn btn-success fa fa-plus"
                        style="font-size:18px;margin-top: 15px"
                > Add
                </button>
                <input type="hidden" name="txtProductId" value="${product.id}"/>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

            </div>
        </form>
        <div class="table-responsive col-xs-6">
            <div class="m-b-10">
                <h3>Suggested</h3>
            </div>
            <table
                    class="table table-striped table-bordered table-hover dataTables-example2">
                <thead>
                <tr style="text-align: center">
                    <th style="background-color:#B0BEC5;text-align: center; width: 20%">Picture</th>
                    <th style="background-color:#B0BEC5;">Name</th>
                    <th style="background-color:#B0BEC5;" class="text-center">Weight</th>
                    <th id=deleteItem class="text-center" style="background-color:#B0BEC5"
                        data-sorter="false">
                        Delete
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="list" items="${listSuggest}">
                    <tr>
                        <td class="text-center"><img class="img-circle" alt="Cinque Terre"
                                                     src="${list.imgUrl}" alt="Smiley face" height="42"
                                                     width="60"></td>
                        <td>${list.name}</td>
                        <td class="text-right" width="15%" style="padding-right: 20px">${list.weight}</td>
                        <form action="${pageContext.request.contextPath}/deleteRelation" method="POST"
                              class="form-horizontal form-label-left">
                            <input type="hidden" name="txtRelativeProductId" value="${list.id}"/>
                            <input type="hidden" name="txtProductId" value="${product.id}"/>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <td class="text-center">
                                <button type="submit" class="btn btn-danger btn-circle"><i
                                        class="fa fa-times "></i></button>
                            </td>
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
<script>
    $(document).ready(function () {

        $('.dataTables-example2').DataTable({
            "aoColumnDefs": [
                {
                    'bSortable': false,
                    'aTargets': [0, 3]
                }],
            dom: '<"html5buttons"B>lTfgitp',
            buttons: []
        });
        var config = {
            '.chosen-select': {},
            '.chosen-select-deselect': {allow_single_deselect: true},
            '.chosen-select-no-single': {disable_search_threshold: 10},
            '.chosen-select-no-results': {no_results_text: 'Oops, nothing found!'},
            '.chosen-select-width': {width: "95%"}
        }
        for (var selector in config) {
            $(selector).chosen(config[selector]);
        }
    });
</script>
</body>
</html>
