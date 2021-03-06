<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>가지하기</title>

<!-- include libraries(jQuery, bootstrap) -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<!-- 파일 업로드 스크립트 -->
<script src="/javascript/fileUploadScript.js"></script>

<style>
.addContainer {
	width: 800px;
	height: 500px;
	margin: 0 auto; /*container 가운데 정렬*/
}
</style>
</head>
<body>
	<div class="addContainer">
		<form:form modelAttribute="item"
			action="${pageContext.request.contextPath}/item/edit/${itemId}"
			method="post" encType="multipart/form-data">
			<h1>판매글 수정</h1>
			<hr />
			<table class="table table-borderless">
				<tr>
					<td rowspan="4" width=300px height=300px>
						<div id='View_area'
							style='position: relative; width: 100%; height: 100%; color: black; border: 0px solid black; dispaly: inline;'>
							<img id="preview" src="${storedThumbnailPath}" width=300px
								height=300px>
						</div>
					</td>
					<th>카테고리</th>
					<td><form:select path="category">
							<form:option value="LIVING">생활용품</form:option>
							<form:option value="ELECTRO">가전</form:option>
							<form:option value="CLOTHES">의류</form:option>
							<form:option value="ETC">기타</form:option>
						</form:select> <form:errors path="category"></form:errors></td>
				</tr>
				<tr>
					<th>제품명</th>
					<td><form:input path="name" value="${item.name }" /> <form:errors
							path="name" /></td>
				</tr>
				<tr>
					<th>가격</th>
					<td><form:input path="price" value="${item.price }" /> <form:errors
							path="price" /></td>
				</tr>
				<tr>
					<th>대표 상품 이미지</th>
					<td><input type="file" name="uploadFile" id="uploadFile"
						onchange="previewImage(this,'View_area')"><br> <input
						type="button" id="remove" onclick="rmv()" value='기존 썸네일 삭제'>
					</td>
				</tr>
				<tr>
					<th colspan="3">상품 설명</th>
					<form:errors path="content" />
				</tr>
				<tr>
					<td colspan="3"><textarea class="summernote" name="content">${item.content }</textarea>
					</td>
				</tr>
			</table>
			<input type="submit" class="btn btn-primary btn-lg" value="판매글 수정">

			<!-- sellerId 조회를 위해 session에 저장된 에미엘 값 넣어주기 -->
			<input type="hidden" name="loginSession" value="${loginSession}">
		</form:form>
	</div>
	<script>
		$('.summernote').summernote({
			placeholder : 'Hello Bootstrap 4',
			tabsize : 2,
			height : 500,
			width : 800,
			lang : "ko-KR"
		});
	</script>

	<!-- 이미지 미리보기 삭제 -->
	<script>
		function rmv() {
			$('#preview').remove();
		}
	</script>
</body>
</html>