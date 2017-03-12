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

<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/viewAccount.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/dataTables/datatables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/jeditable/jquery.jeditable.js"></script>

<html>
<head>
    <title><spring:message code="manage_account"/></title>
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
        <div class="col-md-12">
            <a class="btn btn-primary waves-effect col-mb-3 fa fa-plus" style="font-size: 20px"
               href="${pageContext.request.contextPath}/viewAddAccount">
            </a>
        </div>
        <div class="col-md-12">
            <h2><spring:message code="manage_account"/></h2>
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
                            <th style="background-color:#B0BEC5;"><spring:message code="username"/></th>
                            <th style="background-color:#B0BEC5;"><spring:message code="email"/></th>
                            <th style="background-color:#B0BEC5;" class="text-right"><spring:message code="phone"/></th>
                            <th style="background-color:#B0BEC5;" class="text-center"><spring:message
                                    code="action"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="account" items="${adminAccountList}">
                            <tr>
                                <td>${account.id}</td>
                                <td>${account.username}</td>
                                <td>${account.email}</td>
                                <td class="text-right">${account.phone}</td>
                                <td class="text-center">
                                    <c:if test="${account.status=='1'}">
                                        <%--<a href='./changeStatus?username=${account.username}'--%>
                                        <%--class='btn palette-Red bg waves-effect command-deactivate'><i--%>
                                        <%--class='zmdi zmdi-close p-r-5'></i>Deactivate</a>--%>
                                        <form action="${pageContext.request.contextPath}/changeStatus" method="POST"
                                              id="deactivate-account"
                                              class="form-horizontal form-label-left form-deactivate-${account.username}">
                                            <button type="button" value="${account.username}"
                                                    class="btn palette-Red bg waves-effect btn-deactivate">
                                                <i
                                                        class="zmdi zmdi-close p-r-5"></i>Deactivate
                                            </button>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <input type="hidden" name="username" value="${account.username}"/>
                                        </form>
                                    </c:if>
                                    <c:if test="${account.status!='1'}">
                                        <%--<a href='./changeStatus?username=${account.username}'--%>
                                        <%--class='btn btn-info btn-icon-text waves-effect'><i--%>
                                        <%--class='zmdi zmdi-check p-r-5'></i>Activate</a>--%>
                                        <form action="${pageContext.request.contextPath}/changeStatus" method="POST"
                                              id="activate-account"
                                              class="form-horizontal form-label-left form-activate-${account.username}">
                                            <button type="button" value="${account.username}"
                                                    class="btn btn-info btn-icon-text waves-effect btn-activate">
                                                <i
                                                        class="zmdi zmdi-check p-r-5"></i>Activate
                                            </button>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <input type="hidden" name="username" value="${account.username}"/>
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
                <h2>Do you want to deactivate account?</h2>
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
                <h2>Do you want to activate account?</h2>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                <button id="activate-submit" type="button" class="btn btn-info">Activate</button>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('.dataTables-example').DataTable();
    });
</script>
</body>
</html>