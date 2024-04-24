<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="monitordto.accesslogdto"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp" %>
<body class="d-flex flex-column min-vh-100">
	<div class="container" style="margin-top:80px">
		<div class="row">
			<div class="col-2"></div>
			<div class="col-4" style="font-size:24px"><p><b>접속 기록</b></p></div>
		</div>
		<div class="row">
			<div class="col-1"></div>
			<table class="table table-hover text-center" style="font-size: 18px; width: 90%; height: auto; margin-top:20px;">
				<thead>
					<tr>
						<th style="width:8%">아이디</th>
						<th style="width:8%">이름</th>
						<th style="width:10%">IP주소</th>
					   	<th style="width:15%">장치명</th>
					    <th style="width:15%">접속시간</th>
					    <th style="width:10%">활동로그</th>
					</tr>
				</thead>
				<tbody>
					<%
					  ArrayList<accesslogdto> list = (ArrayList<accesslogdto>)request.getAttribute("list");
					  if(list!=null){
						for(int i=0;i<list.size();i++){
					%>
					<tr>
						<td><%=list.get(i).getId() %></td>
						<td><%=list.get(i).getName() %></td>
						<td><%=list.get(i).getIp() %></td>
						<td><%=list.get(i).getMachine() %></td>
						<td><%=list.get(i).getAccesstime() %></td>
						<td><%=list.get(i).getActivity() %></td>
					</tr>
					<%  
						}
					}
					%>
				</tbody>
			</table>
			<nav class="navbar navbar-expand-sm justify-content-end" aria-label="Page navigation example">
				<ul class="pagination" style="font-size : 10px">
					${page}
				</ul>
			</nav>
		</div>
	</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
</body>
<%@ include file="footer.jsp" %>
</html>