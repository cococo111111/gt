<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 09/11/2016
  Time: 11:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/dataTables/datatables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/jeditable/jquery.jeditable.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/viewChatLog.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/iCheck/icheck.min.js"></script>

<html>
<head>
    <title>Title</title>
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/dataTables/datatables.min.css">

    <link href="${pageContext.request.contextPath}/resources/inspinia/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/CommonAlert.css" rel="stylesheet">
</head>
<body>
<div class="row wrapper  white-bg page-heading">
    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px;padding-top: 25px;">

        <div class="col-md-12">
            <h2>Manage Log</h2>
        </div>
    </div>
    <form action="/deleteChatLog" method="POST" id="delete-chatlog"
          class="form-horizontal form-label-left form-delete">
        <div class="col-lg-6" style="margin-left: 20%">
            <div class="ibox float-e-margins">
                <div class="ibox-content">
                    <div class="table-responsive">
                        <table id="basictable"
                               class="table table-striped table-bordered table-hover dataTables-example">
                            <thead>
                            <tr style="text-align: center">
                                <th style="background-color:#B0BEC5;" width="6%">No.</th>
                                <th style="background-color:#B0BEC5;">User say</th>
                                <th id="headerDelete" class="text-center" style="background-color:#B0BEC5" width="5%"
                                    data-sorter="false">
                                    Delete
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <input id="sizelist" type="hidden" name="txtId" value="${listChatLog.size()}"/>
                            <c:forEach var="list" items="${listChatLog}" varStatus="counter">
                                <tr>
                                    <td class="text-right" style="padding-right:35px;" >${counter.count}</td>
                                    <td>${list.userSay}</td>
                                    <td class="text-center">
                                        <div class="i-checks">
                                            <input type="checkbox" name="chatLogId"
                                                   value="${list.id}"/>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>


                    </div>
                    <c:if test="${not empty listChatLog}">
                        <div class="col-lg-6" style="margin-left: 80%">
                            <button id="btn-delete-chatlog" type="button" class="btn btn-danger fa fa-times" style="font-size:18px"
                            > Delete
                            </button>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </form>
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
                <h2>Do you want to delete log?</h2>
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


