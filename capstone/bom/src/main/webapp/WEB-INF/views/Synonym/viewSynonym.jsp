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
<script src="${pageContext.request.contextPath}/resources/js/viewSynonym.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/dataTables/datatables.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/jeditable/jquery.jeditable.js"></script>

<html>
<head>
    <title>Synonym</title>
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
               href="${pageContext.request.contextPath}/viewAddSynonym?txtKeyOriginalId=${txtKeyOriginalId}&txtKeyOriginalName=${txtKeyOriginalName}"
            >
            </a>
        </div>
        <div class="col-md-12">
            <h2>Manage synonym</h2>
            </br>
            <h3>Original Word: ${txtKeyOriginalName}</h3>
        </div>
    </div>
    <div class="col-lg-8" style="margin-left: 15%">

        <div class="ibox float-e-margins">
            <div class="ibox-content">
                <div class="table-responsive">
                    <table id="basictable"
                           class="table table-striped table-bordered table-hover dataTables-example">
                        <thead>
                        <tr style="text-align: center">
                            <th width="10%" style="background-color:#B0BEC5;">No.</th>
                            <th style="background-color:#B0BEC5;">Synonym</th>
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
                        <input id="sizelist" type="hidden" name="txtId" value="${listSynonym.size()}" />
                        <c:forEach var="list" items="${listSynonym}" varStatus="counter">
                            <tr>
                                <td class="text-right" style="padding-right:35px;">${counter.count}</td>
                                <td>${list.name}</td>
                                <td class="text-center">
                                    <form action="${pageContext.request.contextPath}/viewUpdateSynonym" method="GET" id="formUpdate"
                                          class="form-horizontal form-label-left">
                                        <input type="hidden" name="txtId" value="${list.id}"/>
                                        <input type="hidden" name="txtName" value="${list.name}"/>
                                        <input type="hidden" name="txtKeyOriginalId" value="${txtKeyOriginalId}"/>
                                        <input type="hidden" name="txtKeyOriginalName" value="${txtKeyOriginalName}"/>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                        <input id="listProductId" type="hidden" name="listProductId"/>
                                        <button  type="submit" class="btn btn-success btn-circle btn-update"><i
                                                class="fa fa fa-edit"></i></button>
                                    </form>
                                </td>
                                <td class="text-center">
                                    <form action="${pageContext.request.contextPath}/deleteSynonym" method="POST"
                                          class="form-horizontal form-label-left form-delete-${list.id}">
                                        <button type="button"
                                                class="btn btn-danger btn-circle  btn-sm btn-delete"  value="${list.id}">
                                            <i
                                                    class="fa fa-times"></i></button>
                                        <input type="hidden" name="txtKeyOriginalId" value="${txtKeyOriginalId}"/>
                                        <input type="hidden" name="txtKeyOriginalName" value="${txtKeyOriginalName}"/>
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
    <div class="form-group">
        <div class="col-md-6 col-sm-6 col-xs-12">
            <a class="btn btn-primary waves-effect col-mb-3 fa fa-history" style="font-size:18px"
               href="${pageContext.request.contextPath}/viewKeyOriginal">
                Back
            </a>
        </div>
    </div>
</div>

<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header" >
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel" style="color: red">Warning</h4>
            </div>
            <div class="modal-body">
                <h2>Do you want to delete synonym?</h2>
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

