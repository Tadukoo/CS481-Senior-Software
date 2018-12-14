<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>CTM mkii - Account Settings</title>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/main.css">
</head>
<body>

	<div class="bg-img">

		

			<header class="header" style="margin-bottom: 8px;">
				<h1>Account Settings</h1>
				<div class="row">

					<div class="col-9"></div>
					<div class="col-3">
						
							<div class="loggedInAs">

								<div id="#loggedinlabel">Currently logged in as:</div>
								<div id="loggedinEmail">${userEmail}</div>
							</div>

							<button type="submit" name="action" class="btn btn-danger"
								value="logout" id="logoutbtn">Logout</button>
					
					</div>

				</div>
			</header>
			<div class="createAccount">
			<form class="form-horizontal" method="post">
				<div class="row">
					<div class="col">
						<h3>Change Email</h3>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<div class="row">
							<div class="col">Current Email:</div>
							<div class="col">${email}</div>
						</div>
					</div>
					<div class="col">
						<c:choose>
							<c:when test="${! empty changeEmailError}">
								<p class="alert alert-warning">${changeEmailError}</p>
							</c:when>
							<c:when test="${! empty changeEmailSuccess}">
								<p class="alert alert-success">${changeEmailSuccess}</p>
							</c:when>
						</c:choose>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<div class="form-group row">
							<label for="newEmail" class="control-label col-4">New
								Email:</label> <input type="email" class="form-control col-10"
								id="newEmail" name="newEmail" value="${newEmail}">
						</div>
					</div>
					<div class="col">
						<div class="form-group row">
							<label for="newEmailConfirm" class="control-label col-4">Confirm
								Email:</label> <input type="email" class="form-control col-10"
								id="newEmailConfirm" name="newEmailConfirm"
								value="${newEmailConfirm}">
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col"></div>
					<div class="col-6">
						<div class="form-group row">
							<label for="emailPass" class="control-label col-4">Password:</label>
							<input type="password" class="form-control col-10" id="emailPass"
								name="emailPass" value="${emailPass}">
						</div>
					</div>
					<div class="col"></div>
				</div>
				<div class="row">
					<div class="col">
						<button type="submit" name="doStuff" value="changeEmail"
							class="btn btn-info">Change Email</button>
					</div>
				</div>
		</form>
	</div>
	
	<div class="createAccount">
		<form class="form-horizontal" method="post">
			<div class="row">
				<div class="col">
					<h3>Change Password</h3>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<c:choose>
						<c:when test="${! empty changePasswordError}">
							<p class="alert alert-warning">${changePasswordError}</p>
						</c:when>
						<c:when test="${! empty changePasswordSuccess}">
							<p class="alert alert-success">${changePasswordSuccess}</p>
						</c:when>
					</c:choose>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<div class="form-group row">
						<label for="newPassword" class="control-label col-4">New
							Password:</label> <input type="password" class="form-control col-10"
							id="newPassword" name="newPassword" value="${newPassword}">
					</div>
				</div>
				<div class="col">
					<div class="form-group row">
						<label for="newPasswordConfirm" class="control-label col-4">Confirm
							Password:</label> <input type="password" class="form-control col-10"
							id="newPasswordConfirm" name="newPasswordConfirm"
							value="${newPasswordConfirm}">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col"></div>
				<div class="col-6">
					<div class="form-group row">
						<label for="passPass" class="control-label col-4">Password:</label>
						<input type="password" class="form-control col-10" id="passPass"
							name="passPass" value="${passPass}">
					</div>
				</div>
				<div class="col"></div>
			</div>
			<div class="row">
				<div class="col">
					<button type="submit" name="doStuff" value="changePassword"
						class="btn btn-info">Change Password</button>
				</div>
			</div>

		</form>
	</div>
	</div>
</body>
</html>