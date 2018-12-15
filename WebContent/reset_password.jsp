<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>CTM mkii - Reset Password</title>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/main.css">
</head>
<body>

	<div class="fluid-container">
		<div class="bg-img">
			<header class="header" style="margin-bottom: 8px;">
				<h1>Reset Password</h1>
				<div class="row">

					<div class="col-9"></div>
					<div class="col-3"></div>

				</div>
			</header>
			<c:if test="${empty goodToken}">
				<form class="form-horizontal" method="post" style="width: 600px;">
					<c:if test="${! empty errorMessage}">
						<div class="row">
							<div class="col">
								<p class="alert alert-warning">${errorMessage}</p>
							</div>
						</div>
					</c:if>
					<c:if test="${!empty sendEmailSuccess}">
						<div class="row">
							<div class="col">
								<p class="alert alert-success">${sendEmailSuccess}</p>
							</div>
						</div>
					</c:if>

					<div class="form-group row"style="left-margin:10px;">
						<div class="pwresetemail">
							<label for="email" class="control-label col-4" >Email:</label> <input
								type="text" id="email" name="email" style="margin-bottom: 30px;"
								value="${email}">


							<div class="row">

								<div class="col">
									<button type="submit" name="doThings" value="sendEmail"
										class="btn btn-info">Submit</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</c:if>
			<c:if test="${!empty goodToken}">
				<div class="pwresetnewpass">
					<form class="form-horizontal" method="post" style="width: 600px;">

						<div class="form-group row">
							<label for="newPassword" class="control-label col-4">New
								Password:</label> <input type="password" id="newPassword"
								name="newPassword" value="${newPassword}">
						</div>
						<div class="form-group row">
							<label for="newPasswordConfirm" class="control-label col-4">Confirm
								Password:</label> <input type="password" id="newPasswordConfirm"
								name="newPasswordConfirm" value="${newPasswordConfirm}">
						</div>
						<div class="row">
							<div class="col">
								<button type="submit" name="doThings" value="changePassword"
									class="btn btn-info">Submit</button>
							</div>
						</div>

					</form>
				</div>
			</c:if>
			<strong class="note">Back to login? <a href="./login">Login</a></strong>
		</div>
	</div>
	



</body>
</html>