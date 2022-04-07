<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>메인!</title>
    <jsp:include page="common/header.jsp"/>
	<link rel="stylesheet" href="/css/main.css">

</head>
<body>
	<div class="container">

	<jsp:include page="common/tab.jsp"/>
	<c:if test="${!empty items }">
	<form action="${pageContext.request.contextPath}/item/search/condition" method="post">
	<label for="select">조건으로 검색하기</label>
	<select id="select" name="condition">
		<option value="recent">최신 순(기본)</option>
		<option value="highHit">조회수 높은 순</option>
		<option value="highRate">판매자 평점 높은 순</option>
		<option value="lowPrice">낮은 가격 순</option>
	</select>
	<input type="submit" value="검색">
	</form>
	<br><br>
	<h3>이 상품은 어떠세요?</h3>
	<div class="container">
    <div class="row">
    <c:forEach var="item" items="${items }">
        <div class="col-md-3 col-sm-6">
            <div class="product-grid4">
                <div class="product-image4">
                    <a href="${pageContext.request.contextPath}/item/detail/${item.id}">
                        <img class="pic-1" width="300" height="400" src="${item.filePath }">
                          </a>
                    <span class="product-new-label">New</span>
                </div>
                <div class="product-content">
                    <h3 class="title">${item.name }</h3>
                    <div class="price">${item.price }
                    </div>
                </div>
            </div>
        </div>
        </c:forEach>
    </div>
</div>	
</c:if>
</div>
</body>
</html>