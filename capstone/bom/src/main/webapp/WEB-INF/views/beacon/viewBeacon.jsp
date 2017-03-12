<%--
  Created by IntelliJ IDEA.
  User: manlm
  Date: 9/10/2016
  Time: 12:04 AM
  To change this template use File | Settings | File Templates.
--%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script>
    var deleteResult = '${deleteResult}';
</script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/viewBeacon.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/dataTables/datatables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/jeditable/jquery.jeditable.js"></script>

<html>
<head>
    <title><spring:message code="manage_beacon"/></title>
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/dataTables/datatables.min.css">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/CommonAlert.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/style.css" rel="stylesheet">
</head>
<body>
<div class="row wrapper  white-bg page-heading">
    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px;padding-top: 25px;">
        <%--<div class="col-md-12">--%>
        <%--<a class="btn btn-primary waves-effect col-mb-3 fa fa-plus" style="font-size: 20px"--%>
        <%--href="${pageContext.request.contextPath}/viewAddBeacon">--%>
        <%--</a>--%>
        <%--</div>--%>
        <div class="col-md-12">
            <h2><spring:message code="manage_beacon"/></h2>
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
                            <th style="background-color:#B0BEC5;"><spring:message code="id"/></th>
                            <th style="background-color:#B0BEC5;"><spring:message code="mac_address"/></th>
                            <th style="background-color:#B0BEC5;" class="text-right"><spring:message code="major"/></th>
                            <th style="background-color:#B0BEC5;" class="text-right"><spring:message code="minor"/></th>
                            <th style="background-color:#B0BEC5;" class="text-right"><spring:message code="x"/></th>
                            <th style="background-color:#B0BEC5;" class="text-right"><spring:message code="y"/></th>
                            <th style="background-color:#B0BEC5;" class="text-right"><spring:message code="z"/></th>
                            <%--<th style="background-color:#B0BEC5;"><spring:message code="update"/></th>--%>
                            <th style="background-color:#B0BEC5;" class="text-center"><spring:message
                                    code="delete"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="beacon" items="${beaconList}">
                            <tr>
                                <td>${beacon.id}</td>
                                <td>${beacon.uuid}</td>
                                <td class="text-right">${beacon.major}</td>
                                <td class="text-right">${beacon.minor}</td>
                                <td class="text-right">${beacon.x}</td>
                                <td class="text-right"> ${beacon.y}</td>
                                <td class="text-right">${beacon.z}</td>
                                    <%--<td>--%>
                                    <%--<form action="${pageContext.request.contextPath}/viewUpdateBeacon" method="GET">--%>
                                    <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                                    <%--<input type="hidden" name="macAddress" value="${beacon.uuid}"/>--%>
                                    <%--<input type="hidden" name="major" value="${beacon.major}"/>--%>
                                    <%--<input type="hidden" name="minor" value="${beacon.minor}"/>--%>
                                    <%--<input type="hidden" name="x" value="${beacon.x}"/>--%>
                                    <%--<input type="hidden" name="y" value="${beacon.y}"/>--%>
                                    <%--<input type="hidden" name="z" value="${beacon.z}"/>--%>
                                    <%--<button type="submit"--%>
                                    <%--class="btn btn-success btn-circle btn-update">--%>
                                    <%--<i class="fa fa-wrench"></i></button>--%>
                                    <%--</form>--%>
                                    <%--</td>--%>
                                <td class="text-center">
                                        <%--<a id='btnDelete' class='btn palette-Red bg waves-effect'><i class='zmdi zmdi-close'></i></a>--%>
                                        <%--<form action="${pageContext.request.contextPath}/deleteBeacon1" method="GET"--%>
                                        <%--onsubmit="return confirmDelete()">--%>
                                        <%--<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>--%>
                                        <%--<input type="hidden" name="minor" value="${beacon.minor}"/>--%>
                                        <%--<button type="submit"--%>
                                        <%--class="btn btn-danger btn-circle btn-sm btn-delete">--%>
                                        <%--<i class="fa fa-times"></i></button>--%>
                                        <%--</form>--%>
                                    <form action="${pageContext.request.contextPath}/deleteBeacon" method="POST"
                                          id="delete-beacon"
                                          class="form-horizontal form-label-left form-delete-${beacon.id}">
                                        <button type="button" value="${beacon.id}"
                                                class="btn btn-danger btn-circle btn-delete btn-sm demo4">
                                            <i
                                                    class="fa fa-times"></i></button>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input type="hidden" name="txtId" value="${beacon.id}"/>
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
                <h4 class="modal-title" id="myModalLabel" style="color: red">Warning</h4>
            </div>
            <div class="modal-body">
                <h2> Do you want to delete beacon?</h2>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button id="delete-submit" type="button" class="btn btn-danger">Delete</button>
            </div>
        </div>
    </div>
</div>
<input type="hidden" value="${errorMessage}" id="errorMessage">
<!-- Modal -->
<div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="errordialog" style="color: red">Warning</h4>
            </div>
            <div class="modal-body">
                <h2>Beacon still map with area!</h2>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-info" data-dismiss="modal">OK</button>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('.dataTables-example').DataTable();
    });
    function confirmDelete() {
        swal({
            title: "Are you sure?",
            text: "You will not be able to recover this imaginary file!",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "Yes, delete it!",
            closeOnConfirm: false
        }, function (isConfirm) {
            return isConfirm;
        });
    }
</script>
</body>
</html>