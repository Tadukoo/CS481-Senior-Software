<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>CTM mkii - Login</title>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/main.css">
</head>
<body>

	<div class="fluid-container">
		<div class="bg-img" >
			<form class="form-horizontal" method="post">

				<c:if test="${! empty errorMessage}">
					<div class="row">
						<div class="col">
							<p class="alert alert-warning">${errorMessage}</p>
						</div>
					</div>
				</c:if>
				<c:if test="${! empty successMessage}">
					<div class="row">
						<div class="col">
							<p class="alert alert-success">${successMessage}</p>
						</div>
					</div>
				</c:if>
				<header class="header">
					<h1>Login</h1>
					<div class="row">

						<div class="col-9"></div>
						<div class="col-3"></div>

					</div>
				</header>
				<div class="logincontainer">
					<div class="form-group row">
						<label for="email" class="control-label col-4">Email:</label> <input
							type="text" id="email" name="email"
							value="${email}">
					</div>
					<div class="row">
						<label for="password" class="control-label col-4">Password:</label>
						<input type="password" id="password"
							name="password" value="${password}" style="margin-bottom: 30px;">
					</div>
					
					<div class="row" >
						<div class="col">
							<button type="submit" class="btn btn-info" >Submit</button>
						</div>
					</div>
				</div>
			</form>

			<strong class="note">
				<p>Don't have an account? <a href="./create_account">Create an
					Account</a></p>
			</strong>
			<b class="note">
				Forgot your password? <a href="./reset_password">Reset password</a>
			</b>
		</div>
	</div>
</body>
</html>

