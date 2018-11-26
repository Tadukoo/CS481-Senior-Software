<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>CTM mkii - Reset Password</title>
	<link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
<h1>Reset Password</h1>
<div class="fluid-container">
	<c:if test ="${empty goodToken}">
		<form  class="form-horizontal" method="post">
			<c:if test="${! empty errorMessage}">
				<div class="row">
					<div class="col">
						<p class="alert alert-warning">${errorMessage}</p>
					</div>
				</div>
			</c:if>
			<c:if test = "${!empty sendEmailSuccess}">
				<div class="row">
					<div class="col">
						<p class="alert alert-success">${sendEmailSuccess}</p>
					</div>
				</div>
			</c:if>
			<div class="form-group row">
						<label for="email" class="control-label col-2">Email:</label>
						<input type="text" class="form-control col-2" id="email" name="email" value="${email}">
			</div>
			<div class="row">
				<div class="col">
					<button type="submit" name="doThings" value="sendEmail" class="btn btn-info">Submit</button>
				</div>
			</div>
		</form>
	</c:if>
	<c:if test ="${!empty goodToken}">
		<form  class="form-horizontal" method="post">
			<div class="form-group row">
						<label for="newPassword" class="control-label col-2">New Password:</label>
						<input type="password" class="form-control col-10" id="newPassword" name="newPassword" value="${newPassword}">
			</div>
			<div class="form-group row">
						<label for="newPasswordConfirm" class="control-label col-2">Confirm Password:</label>
						<input type="password" class="form-control col-10" id="newPasswordConfirm" name="newPasswordConfirm" value="${newPasswordConfirm}">
			</div>
			<div class="row">
				<div class="col">
					<button type="submit" name="doThings" value="changePassword" class="btn btn-info">Submit</button>
				</div>
			</div>
		</form>
	</c:if>
</div>
</body></html>