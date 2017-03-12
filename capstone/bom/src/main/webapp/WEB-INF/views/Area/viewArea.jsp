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
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/viewArea.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/dataTables/datatables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/jeditable/jquery.jeditable.js"></script>

<html>
<head>
    <title>Area</title>
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/dataTables/datatables.min.css">

    <link href="${pageContext.request.contextPath}/resources/inspinia/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/viewArea.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/CommonAlert.css" rel="stylesheet">
</head>
<body>
<div class="row wrapper  white-bg page-heading">
    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px;padding-top: 25px;">
        <div class="col-md-12">
        </div>
        <div class="col-md-12">
            <h2>Manage Area</h2>
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
                            <th style="background-color:#B0BEC5;">Name</th>
                            <th class="text-right"style="background-color:#B0BEC5;">Weight</th>
                            <th class="text-right" style="background-color:#B0BEC5;">Beacon ID 1</th>
                            <th class="text-right" style="background-color:#B0BEC5;">Beacon ID 2</th>
                            <th class="text-right" style="background-color:#B0BEC5;">Beacon ID 3</th>
                            <th class="text-right" style="background-color:#B0BEC5;">Beacon ID 4</th>
                            </th>
                            <th id="headerDelete" class="text-center" style="background-color:#B0BEC5"
                                data-sorter="false">
                                Delete
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        <input id="sizelist" type="hidden" name="txtId" value="${listArea.size()}"/>
                        <c:forEach var="list" items="${listArea}">
                            <tr>
                                <td>${list.name}</td>
                                <td class="text-right" style="padding-right: 30px">${list.weight}</td>
                                <td class="text-right" style="padding-right: 40px">${list.beaconId1}</td>
                                <td class="text-right" style="padding-right: 40px">${list.beaconId2}</td>
                                <td class="text-right" style="padding-right: 40px">${list.beaconId3}</td>
                                <td class="text-right" style="padding-right: 40px">${list.beaconId4}</td>
                                <td class="text-center">
                                    <form action="${pageContext.request.contextPath}/deleteArea" method="POST"
                                          class="form-horizontal form-label-left form-delete-${list.id}">
                                        <button type="button"  value="${list.id}"
                                                class="btn btn-danger btn-circle btnDelete_${list.id} btn-sm btn-delete">
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

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="color: red;text-align: center">Warning</h4>
            </div>
            <div class="modal-body">
                <h2>Do you want to delete area?</h2>
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

