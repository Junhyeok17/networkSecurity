<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp" %>
<body class="d-flex flex-column min-vh-100">
	<div class="container" style="margin-top:70px; width:800px;">
		<form action="userregister" method="post">
			<div class="text-center" style="font-size:24px;">
				<p><b><h3>사용자 등록</h3></b></p>
			</div>
			<div class="input-group mb-5 mt-5">
				<input type="text" class="form-control" name="userid" placeholder="아이디 입력" aria-label="id" aria-describedby="basic-addon1">
			</div>
			<div class="input-group mb-5">
				<input type="text" class="form-control" name="nickname" placeholder="닉네임 입력" aria-label="password" aria-describedby="basic-addon1">
			</div>
			<div class="input-group mb-5">
				<input type="text" class="form-control" name="usermail" placeholder="메일 주소 입력">
			</div>
			<div class="input-group mb-5">
				<input type="password" class="form-control" name="userpw" placeholder="비밀번호 입력" aria-label="id" aria-describedby="basic-addon1">
			</div>
			<div class="input-group mb-5">
				<select name="adminflag" class="form-select form-control-sm"  style="height: 33px; vertical-align: low;" aria-label="Default select example">
		      		<option value='Y'>관리자</option>
		      		<option value='N' selected>사용자</option>
				</select>
			</div>
			<button type="submit" class="form-control btn btn-warning" aria-describedby="basic-addon1">등록하기</button>
		</form>
	</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</body>
<%@ include file="footer.jsp" %>
</html>