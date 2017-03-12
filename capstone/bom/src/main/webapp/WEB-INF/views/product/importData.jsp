<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 01/11/2016
  Time: 11:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/dataTables/datatables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/jeditable/jquery.jeditable.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/sweetalert/sweetalert.min.js"></script>
<!-- Steps -->
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/staps/jquery.steps.min.js"></script>

<!-- Jquery Validate -->
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/validate/jquery.validate.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/ImportData.js"></script>
<html>
<head>
    <title>ImportData</title>
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/dataTables/datatables.min.css">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/steps/jquery.steps.css" rel="stylesheet">
</head>

<body>
<div class="row">
    <div class="col-lg-12">
        <div class="ibox">
            <div class="ibox-title">
                <h5>Import data</h5>
                <div class="ibox-tools">
                    <a class="collapse-link">
                        <i class="fa fa-chevron-up"></i>
                    </a>

                </div>
            </div>
            <div class="ibox-content">
                <h2>

                </h2>
                <p>

                </p>

                <form id="form" method="post" action="${pageContext.request.contextPath}/insertData" enctype="multipart/form-data" class="wizard-big">
                    <h1>Information</h1>
                    <fieldset>
                        <div class="text-center" style="margin-top: 120px">
                            <h2>Choose excel file version 97-2007, system will improve this function with latest version excel in the future, import data can lose few time, please waiting...  Thanks you!</h2>
                        </div>
                    </fieldset>
                    <h1>Choose file</h1>
                    <fieldset>
                        <h2></h2>
                        <div class="row">
                            <div class="col-lg-8">
                                <div class="form-group">
                                    <label>Import data* </label>
                                    <input id="pathFile"  type="text" class="form-control required" readonly="readonly"><br><p id="warnPath" style="color:red;font-size: large"></p>
                                    <label style="font-size: 20px" title="Upload image file" for="fileExcel" class="btn btn-primary col-mb-3">
                                        <input type="file" name="fileExcel" id="fileExcel" class="hide " >
                                        <i class="fa fa-folder-open col-mb-3"> File data</i>
                                    </label>
                                </div>

                            </div>
                            <div class="col-lg-4">
                                <div class="text-center">
                                    <div style="margin-top: 20px">
                                        <i class="fa fa-sign-in" style="font-size: 180px;color: #e5e5e5 "></i>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </div>
        </div>
    </div>
</div>


<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">

        <div class="col-lg-15">
            <div class="ibox float-e-margins">
                <div class="ibox-title">

                    <div class="col-md-12 text-right">

                    </div>
                    <h5>Manage product</h5>

                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="table-responsive">
                        <table id="basictable"
                               class="table table-striped table-bordered table-hover dataTables-example">
                            <thead>
                            <tr style="text-align: center">
                                <th class="text-center" style="background-color:#B0BEC5;">Picture</th>
                                <th style="background-color:#B0BEC5">Code</th>
                                <th style="background-color:#B0BEC5;">Name</th>
                                <th style="background-color:#B0BEC5">Price</th>
                                <th id="headerDetail" class="text-center" style="background-color:#B0BEC5">Details</th>
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
                            <input id="sizelist" type="hidden" name="txtId" value="${listproduct.size()}"/>
                            <c:forEach var="list" items="${listproduct}">
                                <tr>
                                    <form action="${pageContext.request.contextPath}/viewUpdateProduct" method="GET" id="formUpdate"
                                          class="form-horizontal form-label-left">
                                        <td class="text-center"><img class="img-circle" alt="Cinque Terre"
                                                                     src="http://khh.travel/images/noImg.jpg" alt="Smiley face" height="42"
                                                                     width="60">
                                        </td>
                                        <td>${list.code}</td>
                                        <td>${list.name}</td>
                                        <td>${list.price}</td>
                                        <td class="text-center">
                                            <a href="${pageContext.request.contextPath}/viewDetailProduct?txtId=${list.id}"
                                               class="btn btn-sm btn-primary"> More info</a>
                                        </td>


                                        <input type="hidden" name="txtId" value="${list.id}"/>
                                        <input type="hidden" name="txtCode" value="${list.code}"/>
                                        <input type="hidden" name="txtName" value="${list.name}"/>
                                        <input type="hidden" name="txtPrice" value="${list.price}"/>
                                        <input type="hidden" name="txtDetails" value="${list.details}"/>
                                        <input type="hidden" name="txtAreaName" value="${list.areaName}"/>
                                        <input type="hidden" name="txtAreaId" value="${list.areaId}"/>
                                        <input type="hidden" name="txtImgUrl" value="${list.imgUrl}"/>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input id="listProductId" type="hidden" name="listProductId"/>
                                        <td class="text-center">
                                            <button type="submit" class="btn btn-success  dim"><i
                                                    class="fa fa-wrench "></i></button>
                                        </td>

                                    </form>
                                    <td class="text-center">
                                        <button type="button"
                                                class="btn btn-danger btn-circle btnDelete_${list.id} demo3"><i
                                                class="fa fa-times"></i></button>
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

<!-- Page-Level Scripts -->
<script>
    <c:forEach var="list" items="${listproduct}">
    $('.btnDelete_' + '${list.id}').click(function () {
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover this product!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        });
        swal("Deleted!", "Product has been deleted.", "success");
    });
    </c:forEach>
</script>

</body>
</html>
