<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>CTM mkii - Create Account</title>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/main.css">
</head>
<body>


	<form class="form-horizontal" method="post">
		<div class="bg-img">
			<header class="header" style="margin-bottom: 8px;">
				<h1>Create Account</h1>
				<div class="row">
					<c:if test="${managerCreate}">
						<div class="col-9">
							<a type="button" class="btn" href="./user_home"
								style="margin: 10px;">Want to return to the Homepage?</a>
						</div>
						<div class="col-3">

							<div class="loggedInAs">

								<div id="#loggedinlabel">Currently logged in as:</div>
								<div id="loggedinEmail">${userEmail}</div>
							</div>

							<button type="submit" name="action" class="btn btn-danger"
								value="logout" id="logoutbtn">Logout</button>
					</div>
					</c:if>
				
		</div>
		</header>
		<div class="createAccount">

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
			<div class="row" style="width: 500px">
				<div style="width: 500px">
					<div class="form-group row" style="width: 500px">
						<label for="firstName" class="control-label col-5">First
							Name:</label> <input type="text" id="firstName" name="firstName"
							value="${firstName}">
					</div>
				</div>
				<div style="width: 500px">
					<div class="form-group row" style="width: 500px">
						<label for="lastName" class="control-label col-5">Last
							Name:</label> <input type="text" id="lastName" name="lastName"
							value="${lastName}">
					</div>
				</div>
			</div>
			<div class="row">
				<div>
					<c:if test="${! empty emailError}">
						<p class="alert alert-warning">${emailError}</p>
					</c:if>
				</div>
				<div>
					<c:if test="${! empty emailConfirmError}">
						<p class="alert alert-warning">${emailConfirmError}</p>
					</c:if>
				</div>
			</div>
			<div class="row" style="width: 500px">
				<div style="width: 500px">
					<div class="form-group row" style="width: 500px">
						<label for="email" class="form-label col-5">Email:</label> <input
							type="email" id="email" name="email" value="${email}">
					</div>
				</div>
				<div style="width: 500px">
					<div class="form-group row" style="width: 500px">
						<label for="emailConfirm" class="form-label col-5">Confirm
							Email:</label> <input type="email" id="emailConfirm" name="emailConfirm"
							value="${emailConfirm}">
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
			<div class="row" style="width: 500px">
				<div style="width: 500px">
					<div class="form-group row" style="width: 500px">
						<label for="password" class="form-label col-5">Password:</label> <input
							type="password" id="password" name="password" value="${password}">
					</div>
				</div>
				<div style="width: 500px">
					<div class="form-group row" style="width: 500px">
						<label for="passwordConfirm" class="form-label col-5">Confirm
							Password:</label> <input type="password" id="passwordConfirm"
							name="passwordConfirm" value="${passwordConfirm}">
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
		</div>
		<c:if test="${empty managerCreate}">
			<b class="note"> Already have an account? <a href="./login">Login</a>
			</b>
		</c:if>
		
		</div>
	</form>


</body>
</html>