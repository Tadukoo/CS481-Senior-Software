<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>CTM mkii - Create Account</title>
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/main.css">
</head>
<body>


	<form class="form-horizontal" method="post">
	<header class="header" style="margin-bottom: 8px;">
				<h1>Create Account</h1>
				<div class="row">

					<div class="col-9"></div>
					<div class="col-3">
					<c:if test="${managerCreate}">
						<div class="loggedInAs">
						
							<div id="#loggedinlabel">Currently logged in as:</div>
							<div id="loggedinEmail">${userEmail}</div>
						</div>
						
						<button type="submit" name="action" class="btn btn-danger"
							value="logout" id="logoutbtn">Logout</button>
							</c:if>
					</div>

				</div>
			</header>
		<div class="row">
			<div class="col">
				<c:if test="${! empty firstNameError}">
					<p class="alert alert-warning">${firstNameError}</p>
				</c:if>
			</div>
			<div class="col">
				<c:if test="${! empty lastNameError}">
					<p class="alert alert-warning">${lastNameError}</p>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<div class="form-group row">
					<label for="firstName" class="control-label col-2">First Name:</label>
					<input type="text" class="form-control col-10" id="firstName" name="firstName" value="${firstName}">
				</div>
			</div>
			<div class="col">
				<div class="form-group row">
					<label for="lastName" class="control-label col-2">Last Name:</label>
					<input type="text" class="form-control col-10" id="lastName" name="lastName" value="${lastName}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<c:if test="${! empty emailError}">
					<p class="alert alert-warning">${emailError}</p>
				</c:if>
			</div>
			<div class="col">
				<c:if test="${! empty emailConfirmError}">
					<p class="alert alert-warning">${emailConfirmError}</p>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<div class="form-group row">
					<label for="email" class="form-label col-2" >Email:</label>
					<input type="email" class="form-control col-10" id="email" name="email" value="${email}">
				</div>
			</div>
			<div class="col">
				<div class="form-group row">
					<label for="emailConfirm" class="form-label col-2" >Confirm Email:</label>
					<input type="email" class="form-control col-10" id="emailConfirm" name="emailConfirm" value="${emailConfirm}">
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<c:if test="${! empty passwordError}">
					<p class="alert alert-warning">${passwordError}</p>
				</c:if>
			</div>
			<div class="col">
				<c:if test="${! empty passwordConfirmError}">
					<p class="alert alert-warning">${passwordConfirmError}</p>
				</c:if>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<div class="form-group row">
					<label for="password" class="form-label col-2">Password:</label>
					<input type="password" class="form-control col-10" id="password" name="password" value="${password}">
				</div>
			</div>
			<div class="col">
				<div class="form-group row">
					<label for="passwordConfirm" class="form-label col-2">Confirm Password:</label>
					<input type="password" class="form-control col-10" id="passwordConfirm" name="passwordConfirm" 
					value="${passwordConfirm}">
				</div>
			</div>
		</div>
		<!-- This input line passes managerCreate value from doGet to ensure proper redirect -->
		<input type="hidden" name="managerCreate" value="${managerCreate}">
		<div class="row">
			<div class="col">
				<button type="submit" class="btn btn-info">Submit</button>
			</div>
		</div>
	</form>

<c:if test="${empty managerCreate}">
<p class="note">Already have an account? <a href="./login">Login</a></p>
</c:if>
<c:if test="${managerCreate}">
<p class="note">Want to return to the Homepage? <a href="./user_home">Home</a></p>
</c:if>
</body></html>