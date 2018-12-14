<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>CTM mkii - Edit User</title>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/main.css">
</head>
<body>


	<div class="row">
		<div class="col">
			<c:if test="${! empty successMessage}">
				<p class="alert alert-success">${successMessage}</p>
			</c:if>
		</div>
	</div>


	<div class="bg-img">
		<header class="header">
			<h1>Current User Details</h1>
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
		<div class=editUser>
			<div class="row">
				<div class="col-6"></div>
				<div class="col-6 text-right">
					<form class="form-horizontal" method="post">
						<input type="hidden" name="userID" value="${userID}">
						<c:if test="${archived == false}">
							<button type="submit" name="doStuff" value="archiveUser"
								class="btn btn-danger">Archive User</button>
						</c:if>
						<c:if test="${archived == true}">
							<button type="submit" name="doStuff" value="unarchiveUser"
								class="btn btn-danger">Unarchive User</button>
						</c:if>
					</form>
				</div>
			</div>
			<div class="row" style="margin-bottom: 10px">
				<div class="col-3 text-center">ID: ${userID} EID:
					${employeeID}</div>
				<div class="col-3 text-center">Email: ${email}</div>
				<div class="col-3 text-center">Name: ${firstName} ${lastName}</div>
			</div>
			<div class="row">
				<div class="col text-center"></div>
				<div class="col text-center">Locked Out: ${lockedOut}</div>
				<div class="col text-center">
					<form class="form-horizontal" method="post">
						<input type="hidden" name="userID" value="${userID}">
						<c:if test="${lockedOut == false}">
							<button type="submit" name="doStuff" value="lockoutUser"
								class="btn btn-danger">Lockout User</button>
						</c:if>
						<c:if test="${lockedOut == true}">
							<button type="submit" name="doStuff" value="overrideLockout"
								class="btn btn-danger">Override Lockout</button>
						</c:if>
					</form>
				</div>
				<div class="col"></div>
			</div>
			<h2>Change Basic Details</h2>
			<form class="form-horizontal" method="post">
				<input type="hidden" name="userID" value="${userID}">
				<div class="row">
					<div class="col-1"></div>
					<div class="col-5">
						<c:if test="${! empty firstNameError}">
							<p class="alert alert-warning">${firstNameError}</p>
						</c:if>
					</div>
					<div class="col-1"></div>
					<div class="col-5">
						<c:if test="${!empty firstNameConfirmError}">
							<p class="alert alert-warning">${firstNameConfirmError}</p>
						</c:if>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<div class="form-group row">
							<label for="newFirstName" class="control-label col-4">New
								First Name:</label> <input type="text" class="form-control col-10"
								id="newFirstName" name="newFirstName" value="${newFirstName}">
						</div>
					</div>
					<div class="col">
						<div class="form-group row">
							<label for="newFirstNameConfirm" class="control-label col-4">Confirm
								First Name:</label> <input type="text" class="form-control col-10"
								id="newFirstNameConfirm" name="newFirstNameConfirm"
								value="${newFirstNameConfirm}">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<button type="submit" name="doStuff" value="changeFirstName"
							class="btn btn-info">Change First Name</button>
					</div>
				</div>
			</form>
			<form class="form-horizontal" method="post">
				<input type="hidden" name="userID" value="${userID}">
				<div class="row">
					<div class="col-1"></div>
					<div class="col-5">
						<c:if test="${!empty lastNameError}">
							<p class="alert alert-warning">${lastNameError}</p>
						</c:if>
					</div>
					<div class="col-1"></div>
					<div class="col-5">
						<c:if test="${!empty lastNameConfirmError}">
							<p class="alert alert-warning">${lastNameConfirmError}</p>
						</c:if>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<div class="form-group row">
							<label for="newLastName" class="control-label col-4">New
								Last Name:</label> <input type="text" class="form-control col-10"
								id="newLastName" name="newLastName" value="${newLastName}">
						</div>
					</div>
					<div class="col">
						<div class="form-group row">
							<label for="newTitleConfirm" class="control-label col-4">Confirm
								Last Name :</label> <input type="text" class="form-control col-10"
								id="newLastNameConfirm" name="newLastNameConfirm"
								value="${newLastNameConfirm}">
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col">
						<button type="submit" name="doStuff" value="changeLastName"
							class="btn btn-info">Change Last Name</button>
					</div>
				</div>
			</form>
			<h2>View and Change Their SOPs</h2>
			<form class="form-horizontal" method="post">
				<h3>View Them</h3>
				<div class="row">
					<div class="col">
						<a href="search_sops?userID=${userID}"
							class="btn btn-info btn-block" role="button">View Them</a>
					</div>
					<div class="col"></div>
					<div class="col"></div>
				</div>
				<h3>Add SOP</h3>
				<div class="row">
					<div class="col">
						<c:if test="${!empty sopError}">
							<p class="alert alert-warning">${sopError}</p>
						</c:if>
					</div>
					<div class="col"></div>
				</div>
				<div class="row">
					<div class="col">
						<input type="number" class="form-control" id="sopID" name="sopID"
							value="${sopID}">
					</div>
					<div class="col">
						<button type="submit" name="doStuff" value="addSOP"
							class="btn btn-info">Add SOP</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>