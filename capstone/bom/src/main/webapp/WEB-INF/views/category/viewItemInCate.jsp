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
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/dataTables/datatables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/jeditable/jquery.jeditable.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/sweetalert/sweetalert.min.js"></script>
<!-- Chosen -->
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/chosen/chosen.jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/viewItemInCate.js"></script>
<html>
<head>
    <title>View Product in category</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/dataTables/datatables.min.css">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/select2/select2.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/viewProductInCate.css" rel="stylesheet">
</head>
<body>
<div class="row wrapper border-bottom white-bg page-heading">
    <div class="col-lg-10">
        <h2>Category: <span style="font-style: italic">${txtName}</span>
        </h2>
        <div class="m-t-10 m-b-10 m-l-15">
            <a class="btn btn-primary waves-effect col-mb-3 fa fa-history"
               style="font-size:18px" href="${pageContext.request.contextPath}/viewCategory">
                Back
            </a>
        </div>
    </div>
    <br>

    <div class="col-xs-12 m-t-5">

        <div class=" col-xs-6  col-sm-6">
            <h3>Products</h3>

            <form action="${pageContext.request.contextPath}/addProductInCate" method="POST">
                <select id="select_item" name="listAddProduct" data-placeholder="Choose a product..."
                        class="chosen-select text-center" multiple
                        style="width:90%;"
                        tabindex="4">
                    <c:forEach var="listProduct" items="${listProduct}">
                        <option value="${listProduct.id}">${listProduct.name}</option>
                    </c:forEach>
                </select>
                <div class="m-b-10">
                    <br>
                    <button id="btn_add" type="submit" class="btn btn-success fa fa-plus" style="font-size:18px"
                    > Add
                    </button>
                    <input type="hidden" name="txtId" value="${txtId}"/>
                    <input type="hidden" name="txtName" value="${txtName}"/>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </div>
            </form>
        </div>

        <div class="table-responsive col-xs-6">
            <table
                    class="table table-striped table-bordered table-hover dataTables-example2">
                <thead>
                <tr style="text-align: center">
                    <th width="20%" style="background-color:#B0BEC5;" class="text-center">Picture</th>
                    <th style="background-color:#B0BEC5;">Name</th>
                    <th id=deleteItem class="text-center" style="background-color:#B0BEC5"
                        data-sorter="false" width="25%">
                        Delete
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="list" items="${listProductInCate}">
                    <tr>
                        <td class="text-center"><img class="img-circle" alt="Cinque Terre"
                                                     src="${list.imgUrl}" alt="Smiley face" height="42"
                                                     width="60"></td>
                        <td>${list.name}</td>
                        <form action="${pageContext.request.contextPath}/deteleProductInCate" method="POST"
                              class="form-horizontal form-label-left">
                            <input type="hidden" name="txtProductId" value="${list.id}"/>
                            <input type="hidden" name="txtId" value="${txtId}"/>
                            <input type="hidden" name="txtName" value="${txtName}"/>
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

        $('.dataTables-example').DataTable({
            "aoColumnDefs": [
                {
                    'bSortable': false,
                    'aTargets': [0, 2]
                }],
            dom: '<"html5buttons"B>lTfgitp',
            buttons: []
        });
        $('.dataTables-example2').DataTable({
            "aoColumnDefs": [
                {
                    'bSortable': false,
                    'aTargets': [0, 2]
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
