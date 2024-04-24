<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<head>
	<meta charset="UTF-8">
	<title>네트워크 모니터링</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
	<nav class="navbar navbar-expand-lg navbar-light bg-info">
		<div class="container-fluid">
	  		<div class="collapse navbar-collapse" id="navbarSupportedContent">
	    		<ul class="navbar-nav ms-auto mb-2 mb-lg-0">
	      			<li class="nav-item">
	          			<a class="nav-link active" aria-current="page" href="index">Home</a>
	        		</li>
				   	<li class="nav-item">
				        <a class="nav-link" href="monitoring">네트워크 모니터링</a>
				   	</li>
			        <li class="nav-item dropdown">
			        	<a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
			            사용자관리
			        	</a>
			        	<ul class="dropdown-menu" aria-labelledby="navbarDropdown">
				            <li><a class="dropdown-item" href="userregister">계정등록</a></li>
				            <li><a class="dropdown-item" href="usersearch">계정조회</a></li>
				            <li><a class="dropdown-item" href="logout">로그아웃</a></li>
				        </ul>
			        </li>
				   	<li class="nav-item">
				        <a class="nav-link" href="accesslog">활동로그</a>
				   	</li>
		    	</ul>
			</div>
		</div>
	</nav>
</head>