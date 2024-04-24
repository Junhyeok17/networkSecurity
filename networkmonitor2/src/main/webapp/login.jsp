<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<style>
* { padding: 0; margin: 0; }

html, body {
  height: 100%;
  background: #ffffff;
}

#container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  height: 100%;
}

#loginBox {
  width: 300px;
  text-align: center;
  background-color: #ffffff;
}
.input-form-box {
  border: 0px solid #ff0000;
  display: flex;
  margin-bottom: 5px;
}
.input-form-box > span {
  display: block;
  text-align: left;
  padding-top: 5px;
  min-width: 65px;
}
.button-login-box {
  margin: 10px 0;
}
#loginBoxTitle {
  color:#000000;
  font-weight: bold;
  font-size: 32px;
  text-transform: uppercase;
  padding: 5px;
  margin-bottom: 20px;
  background: linear-gradient(to right, #270a09, #8ca6ce);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
#inputBox {
  margin: 10px;
}

#inputBox button {
  padding: 3px 5px;
}
</style>
<html>
<head>
<meta charset="UTF-8">
<title>네트워크 모니터링</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
</head>
<body>
	   <div id="container">
	      <!--  login 폼 영역을 : loginBox -->
	      <div id="loginBox">
	        <form id = "loginfrom" method="post" action="login" autocomplete="off">
	        <!-- 로그인 페이지 타이틀 -->
	        <div id="loginBoxTitle">네트워크 모니터링 Login</div>
	        <!-- 아이디, 비번, 버튼 박스 -->
	        <div id="inputBox">
	          <div class="input-form-box"><span><small>아이디</small> </span><input type="text" id="id" name="id" class="form-control"></div>
	          <div class="input-form-box"><span><small>비밀번호</small> </span><input type="password" id="password" name="password" class="form-control"></div>
	          <div class="button-login-box" >
	            <button type="submit" class="btn btn-primary btn-xs" style="width:100%">로그인</button>
	          </div>
	        </div>
	        </form>
	      </div>
	    </div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</body>
</html>