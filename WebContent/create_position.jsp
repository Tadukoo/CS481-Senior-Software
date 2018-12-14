<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<title>CTM mkii - Create Position</title>
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/main.css">
</head>
<body>


	<form class="form-horizontal" method="post">
	<div class="bg-img">
		<header class="header">
				<h1>Create Position</h1>
				<div class="row">

					<div class="col-9">
						<a type="button" class="btn" href="./user_home"
							style="margin: 10px;">Want to return to the Homepage?</a>
					</div>
					<div class="col-3" style="padding-bottom: 4px; padding-left: 15px;">
						<div class="loggedInAs">
						<div id="#loggedinlabel">Currently logged in as:</div>
						<div id="loggedinEmail">${currentemail}</div>
						</div>
						<button type="submit" name="action" class="btn btn-danger"
							value="logout" id="logoutbtn">Logout</button>
					</div>


				</div>
			</header>
			<div class="createPosition">
		<div class="row">
			<div class="col">
				<c:if test="${! empty titleError}">
					<p class="alert alert-warning">${titleError}</p>
				</c:if>
			</div>
			<div class="col">
				<c:if test="${! empty descError}">
					<p class="alert alert-warning">${descError}</p>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<div class="form-group row">
					<label for="title" class="control-label col-4">Title:</label>
					<input type="text" class="form-control col-10" id="title" name="title" value="${title}">
				</div>
			</div>
			<div class="col">
				<br>
				<div class="form-group row">
					<label for="description" class="control-label col-2">Description:</label>
					<textarea rows="4" cols="50" name="description"><c:out value="${description}" /></textarea>
				<br>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<c:if test="${! empty priorityError}">
					<p class="alert alert-warning">${priorityError}</p>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<div class="form-group row">
					<label for="priority" class="form-label col-2">Priority (1-10):</label>
					<input type="number" class="form-control col-10" id="priority" name="priority" value="${priority}">
				</div>
			</div>
		</div>
		
		<div class="row">
			<div class="col">
				<button type="submit" class="btn btn-info">Submit</button>
			</div>
		</div>
		
	</form>
	


</body></html>	
