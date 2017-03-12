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
<script src="${pageContext.request.contextPath}/resources/js/viewKeyOriginal.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/dataTables/datatables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/jeditable/jquery.jeditable.js"></script>

<html>
<head>
    <title>Original Word</title>
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/sweetalert/sweetalert.css"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/dataTables/datatables.min.css">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/inspinia/css/style.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/CommonAlert.css" rel="stylesheet">
</head>
<body>
<div class="row wrapper  white-bg page-heading">
    <div class="col-md-12 col-sm-12 col-xs-12" style="padding-bottom: 15px;padding-top: 25px;">
        <div class="col-md-12">
            <a class="btn btn-primary waves-effect col-mb-3 fa fa-plus" style="font-size: 20px"
               href="${pageContext.request.contextPath}/viewAddKeyOriginal">
            </a>
        </div>
        <div class="col-md-12">
            <h2>Manage Original Word</h2>
        </div>
    </div>
    <div class="col-lg-10" style="margin-left: 10%">

        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <div class="table-responsive">
                    <table id="basictable"
                           class="table table-striped table-bordered table-hover dataTables-example">
                        <thead>
                        <tr style="text-align: center">
                            <th width="8%" class="text-center" style="background-color:#B0BEC5;">No.</th>
                            <th class="text-center" style="background-color:#B0BEC5;">Key original</th>
                            <th class="text-center" style="background-color:#B0BEC5;">Synonym</th>
                            <th class="text-center" style="background-color:#B0BEC5;">Manage synonym</th>
                            <th class="text-center" style="background-color:#B0BEC5;">Add synonym</th>
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
                        <input id="sizelist" type="hidden" name="txtId" value="${listSynonym.size()}"/>
                        <c:forEach var="list" items="${listKeyOriginal}" varStatus="counter">
                            <tr>
                                <td class="text-right" style="padding-right:35px;">${counter.count}</td>
                                <td class="text-center">${list.name}</td>
                                <td>
                                    <c:forEach var="listSyn" items="${listSynonym}">
                                        <c:if test="${list.id==listSyn.synonymId}">
                                            - ${listSyn.name}<br>
                                        </c:if>
                                    </c:forEach>

                                </td>
                                <td class="text-center">
                                    <form action="${pageContext.request.contextPath}/viewSynonym" method="GET"
                                          class="form-horizontal form-label-left">
                                        <input type="hidden" name="txtKeyOriginalId" value="${list.id}"/>
                                        <input type="hidden" name="txtKeyOriginalName" value="${list.name}"/>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <button type="submit" class="btn btn-primary btn-circle btn-update"><i
                                                class="fa fa-exchange"></i></button>
                                    </form>
                                </td>
                                <td class="text-center">
                                    <form action="${pageContext.request.contextPath}/viewAddSynonym" method="GET"
                                          class="form-horizontal form-label-left">
                                        <input type="hidden" name="txtKeyOriginalId" value="${list.id}"/>
                                        <input type="hidden" name="txtKeyOriginalName" value="${list.name}"/>
                                        <button type="submit" class="btn btn-primary btn-circle btn-update"><i
                                                class="fa fa-plus"></i></button>
                                    </form>
                                </td>
                                <td class="text-center">
                                    <form action="${pageContext.request.contextPath}/viewUpdateKeyOriginal" method="GET"
                                          id="formUpdate"
                                          class="form-horizontal form-label-left">
                                        <input type="hidden" name="txtId" value="${list.id}"/>
                                        <input type="hidden" name="txtName" value="${list.name}"/>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <button type="submit" class="btn btn-success btn-circle btn-update"><i
                                                class="fa fa-edit "></i></button>
                                    </form>
                                </td>
                                <td class="text-center">
                                    <form action="${pageContext.request.contextPath}/deleteOriginalWord" method="POST"
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

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="color: red">Warning</h4>
            </div>
            <div class="modal-body">
                <h2>Do you want to delete original word?</h2>
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

