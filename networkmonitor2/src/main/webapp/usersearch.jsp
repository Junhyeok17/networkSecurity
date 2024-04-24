<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="monitordto.memberdto"%>
<!DOCTYPE html>
<html>
<%@ include file="header.jsp" %>
<body class="d-flex flex-column min-vh-100">
	<div class="container" style="margin-top:100px">
		<div class="row">
			<div class="col-1"></div>
			<div class="col-4" style="font-size:24px"><p><b>등록된 계정</b></p></div>
		</div>
		<div class="row">
			<div class="col-1"></div>
			<table class="table table-hover text-center" style="font-size: 18px; width: 100%; height: auto; margin-top:20px;">
				<thead>
					<tr>
						<th style="width:5%">번호</th>
						<th style="width:10%">선택</th>
						<th style="width:10%">아이디</th>
					   	<th style="width:10%">이름</th>
					    <th style="width:10%">메일주소</th>
					    <th style="width:10%">권한</th>
					    <th style="width:10%">로그인시도</th>
					    <th style="width:10%">계정활성화</th>
					    <th style="width:15%">계정생성일</th>
						<th style="width:15%">비밀번호 변경</th>
					</tr>
				</thead>
				<tbody>
					<%
					  String sessionid = (String)session.getAttribute("id");
					  String adminflag = (String)session.getAttribute("adminflag");
					  ArrayList<memberdto> list = (ArrayList<memberdto>)request.getAttribute("list");
					  if(list!=null){
						for(int i=0;i<list.size();i++){
					%>
					<tr>
						<th><%=i+1%></th>
						<th>
							<div class="form-check d-flex justify-content-center">
								<input class="form-check-input" type="checkbox" value="" id="flexCheckDefault">
						  	</div>
						</th>
						</div>
						<td><%=list.get(i).getId() %></td>
						<td><%=list.get(i).getName() %></td>
						<td><%=list.get(i).getEmail() %></td>
						<% if(list.get(i).getAdminflag().equals("Y")){ %>
						<td>관리자</td>
					    <% } else { %>
						<td>직원</td>
						<% } %>
						<td><%=list.get(i).getLoginattempt() %></td>
						<% if(list.get(i).getActivation().equals("1")){ %>
						<td style="color:green">활성화</td>
					    <% } else { %>
						<td style="color:red">비활성화</td>
						<% } %>
						<td><%=list.get(i).getRegdate() %></td>
						<% if(list.get(i).getId().equals(sessionid)){ %>
						<td>
							<button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#exampleModal" style="width:100px">변경하기</button>
						</td>
						<% } %>
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
		<% if(adminflag.equals("Y")){ %>
		<div class="row">
			<button id="activateUser" type="button" class="btn btn-success btn-sm" style="width:100px">계정 활성화</button>
			<button id="deleteUser" type="button" class="btn btn-danger btn-sm" style="width:100px; margin-left:30px;">삭제하기</button>
		</div>
		<% } %>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
	      		<div class="modal-header">
	        		<h1 class="modal-title fs-5" id="exampleModalLabel">비밀번호 변경</h1>
	        		<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      		</div>
	      		<form action="changepassword" method="post">
			    	<div class="modal-body">
			        	<div class="input-group mb-3 justify-center">
			        		<p style="width:150px;">현재 비밀번호</p>
							<input type="password" name="curpw" class="form-control" placeholder="현재 비밀번호 입력" aria-label="Recipient's username" aria-describedby="basic-addon2">
						</div>
			        	<div class="input-group mb-3">
			        		<p style="width:150px;">변경할 비밀번호</p>
							<input type="password" name="newpw" class="form-control" placeholder="변경할 비밀번호 입력" aria-label="Recipient's username" aria-describedby="basic-addon2">
						</div>
			    	</div>
		      		<div class="modal-footer">
		        		<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소하기</button>
		        		<button type="submit" class="btn btn-primary">변경하기</button>
		      		</div>
	      		</form>
	    	</div>
		</div>
	</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>	
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
<script>
	document.getElementById("activateUser").addEventListener("click", function(){
		sendData("/networkmonitor2/useractivate");
	});
	document.getElementById("deleteUser").addEventListener("click", function(){
		sendData("/networkmonitor2/userdelete");
	});
	function sendData(server){
		var checkboxes = document.querySelectorAll("input[type='checkbox']:checked");
		var selectedIds = [];
		
		checkboxes.forEach(function(checkbox){
			selectedIds.push(checkbox.closest("tr").querySelector("td:nth-child(3)").textContent);
		})

	    $.ajax({
	        url:server, // Specify the URL of your server endpoint
	        type: "POST", // Set the request type to POST
	        contentType: "text/plain", // Set the content type to plain text
	        data: selectedIds.join(','), // Set the data to be sent
	        success: function(response) {
	            // Request was successful, handle response if needed
	            console.log("Server response: " + response);
	            window.location.href="usersearch";
	        },
	        error: function(xhr, status, error) {
	            // Request failed, handle errors
	            console.error("Error: " + status + " - " + error);
	        }
	    });
	}
</script>
</body>
<%@ include file="footer.jsp" %>
</html>