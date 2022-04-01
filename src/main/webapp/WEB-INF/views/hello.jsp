<!DOCTYPE html>
<html>
    <head th:replace="../fragments/template :: head">
	</head>
	<body>
		<h1>Hello [[${name}]]</h1>
		<p>This is an example page.</p>
		<div th:replace="../fragments/template :: footer"></div>
		<div id="principalInfoDiv">
			<c:if test="${pageContext.request.userPrincipal.name != null}">
				<h2>Welcome : ${pageContext.request.userPrincipal.name} 
					| <a href="<c:url value="/j_spring_security_logout" />" > Logout</a></h2>  
			 </c:if>
		</div>
	</body>
</html>