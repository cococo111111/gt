<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 19/10/2016
  Time: 8:56 AM
  To change this template use File | Settings | File Templates.
--%>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/jquery.validate.min.js"></script>
<script src="http://cdn.jsdelivr.net/jquery.validation/1.15.0/additional-methods.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/inspinia/js/plugins/chosen/chosen.jquery.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/insertProduct.js"></script>
<script>
    var txtCode = '${txtCode}';
    var txtName = '${txtName}';
    var txtPrice = '${txtPrice}';
    var txtImgUrl = '${txtImgUrl}';
    var txtDetails = '${txtDetails}';
    var txtAreaId = '${txtAreaId}';
    var path = '${pageContext.request.contextPath}';
</script>
<html>
<head>
    <title>Insert Product</title>

    <link href="${pageContext.request.contextPath}/resources/inspinia/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resources/css/updateProduct.css" rel="stylesheet">
</head>
<body>

<form action="${pageContext.request.contextPath}/addProduct" method="post" id="formProduct"
      enctype="multipart/form-data"
      class="form-horizontal form-label-left">
    <div class="row">
        <div class="col-lg-12">
            <div class="ibox product-detail">
                <div class="ibox-content">
                    <div class="row">
                        <div class="col-md-5">
                            <div class="product-images">
                                <input type="hidden" name="txtImgUrl" value="${txtImgUrl}">
                                <div class="ratio-wrapper ratio-wrapper-4-3">
                                    <div class="ratio-item">
                                        <img id="imgUrl-product" class="img-responsive" style="margin: auto"
                                             src="http://khh.travel/images/noImg.jpg"/>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12 col-sm-12 col-xs-12">
                                    <span class="col-md-12 col-xs-12 text-center"
                                          style="color:red;font-style:italic;font-size: 17px"
                                          id="showImageExist"></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-0 col-sm-3 col-xs-12"><span
                                        class="required"></span>
                                </label>
                                <div class="col-md-8 col-sm-8 col-xs-8">
                                    <label title="Upload image file" for="fileImage"
                                           class="btn btn-primary col-md-8 col-sm-8">
                                        <input type="file" name="fileImage" id="fileImage" class="hide">
                                        Upload image
                                    </label>
                                </div>

                            </div>
                        </div>
                        <div class="col-md-7" style="font-size: 17px">
                            <h4>Code</h4>
                            <input type="text" name="txtCode" class="form-control font-bold "></br></br>
                            <h4>Name</h4>
                            <input type="text" id="name-product" name="txtName" required="required"
                                   class="form-control" value="${txtName}" maxlength="80">
                            <div class="product-main-price m-t-15">
                                <h4>Price</h4>
                                <div class="col-md-4 col-sm-4 col-xs-4">
                                    <input id="price-product2" class="form-control"
                                           type="number" name="txtPrice"
                                           required="required" min="0"
                                           value="${txtPrice}"
                                    >
                                </div>
                                <div class="col-md-8 col-sm-8 col-xs-8">
                                    <p>VNĐ</p>
                                </div>
                            </div>
                            <h4>Product description</h4>
                            <div class=" text-muted">
                                <div class="form-group">
                                    <div class="col-md-12 col-sm-4 col-xs-8">
                <textarea cols="100" rows="3" id="details-product2" class="form-control col-md-7 col-xs-12" type="text"
                          name="txtDetails"
                          required="required" maxlength="255">${txtDetails}</textarea>
                                    </div>
                                </div>
                            </div>
                            <h4>Area</h4>
                            <div class="form-group">
                                <label for="areaId-product" class="control-label col-md-0 col-sm-3 col-xs-12"><span
                                        class="required"></span>
                                </label>
                                <div class="col-md-12 col-sm-0 col-xs-8">
                                    <select id="areaId-product" name="txtAreaId" class="selectpicker"
                                            data-live-search="true" data-size="5">
                                        <c:forEach var="item" items="${listArea}">
                                            <option value="${item.id}"
                                                    <c:if test="${item.id==txtAreaId}">selected</c:if>>${item.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <h4>Category</h4>
                            <div class="form-group">
                                <label for="areaId-product" class="control-label col-md-0 col-sm-3 col-xs-12"><span
                                        class="required"></span>
                                </label>
                                <div class="col-md-12 col-sm-0 col-xs-8">
                                    <select id="select_item" name="listCategory" data-placeholder="Choose a category..."
                                            class="chosen-select text-center" multiple
                                            style="width:90%;"
                                            tabindex="4">
                                        <c:forEach var="item" items="${listCategory}">
                                            <option value="${item.id}">${item.name}</option>
                                        </c:forEach>
                                    </select>

                                </div>
                            </div>
                            <div>
                                <div class="btn-group">
                                    <a class="btn btn-primary waves-effect col-mb-3 fa fa-history"
                                       style="font-size:18px" href="${pageContext.request.contextPath}/viewProduct">
                                        Back
                                    </a>
                                </div>
                                <button type="reset" class="btn btn-primary fa fa-remove" style="font-size:18px"
                                        id="cancel"> Cancel
                                </button>
                                <button type="submit" class="btn btn-success fa fa-plus" style="font-size:18px"
                                        id="submit_add"> Add
                                </button>

                            </div>

                        </div>
                    </div>

                </div>

            </div>
        </div>
    </div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>

</body>
</html>
