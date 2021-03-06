<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>CTM mkii - Edit Position</title>
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
			<h1>Edit Position</h1>
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
					<form class="form-horizontal" method="post">
					<button type="submit" name="action" class="btn btn-danger"
						value="logout" id="logoutbtn">Logout</button>
					</form>
				</div>


			</div>
		</header>
		<div class=editPos>
		<div class="row">
		<div class="col-6"></div>
		<div class="col-6 text-right">
			<form class="form-horizontal" method="post">
				<input type="hidden" name="posID" value="${posID}">
				<button type="submit" name="doStuff" value="deletePosition"
					class="btn btn-danger">Delete Position</button>
			</form>
		</div>
	</div>
		<div class="row">
			<div class="col-3">ID: ${posID}</div>
			<div class="col-3">Title: ${title}</div>
			<div class="col-3">Priority: ${priority}</div>
		</div>
		<div class="row" style="margin-top:20px;">
			<div class="col-2">Description:</div>
			<div class="col-8">${description}</div>
		</div>
		<div class="row" style="margin-top:50px;">
			<div class="col text-center">Requirements TBA</div>
		</div>
		<h2>Change Basic Details</h2>
		<form class="form-horizontal" method="post">
			<input type="hidden" name="posID" value="${posID}">
			<div class="row">
				<div class="col-1"></div>
				<div class="col-5">
					<c:if test="${! empty titleError}">
						<p class="alert alert-warning">${titleError}</p>
					</c:if>
				</div>
				<div class="col-1"></div>
				<div class="col-5">
					<c:if test="${!empty titleConfirmError}">
						<p class="alert alert-warning">${titleConfirmError}</p>
					</c:if>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<div class="form-group row">
						<label for="newTitle" class="control-label col-2">New
							Title:</label> <input type="text" class="form-control col-10"
							id="newTitle" name="newTitle" value="${newTitle}">
					</div>
				</div>
				<div class="col">
					<div class="form-group row">
						<label for="newTitleConfirm" class="control-label col-2">Confirm
							Title:</label> <input type="text" class="form-control col-10"
							id="newTitleConfirm" name="newTitleConfirm"
							value="${newTitleConfirm}">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<button type="submit" name="doStuff" value="changeTitle"
						class="btn btn-info">Change Title</button>
				</div>
			</div>
		</form>
		<form class="form-horizontal" method="post">
			<input type="hidden" name="posID" value="${posID}">
			<div class="row">
				<div class="col-1"></div>
				<div class="col-5">
					<c:if test="${!empty priorityError}">
						<p class="alert alert-warning">${priorityError}</p>
					</c:if>
				</div>
				<div class="col-1"></div>
				<div class="col-5">
					<c:if test="${!empty priorityConfirmError}">
						<p class="alert alert-warning">${priorityConfirmError}</p>
					</c:if>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<div class="form-group row">
						<label for="newPriority" class="control-label col-4">New
							Priority:</label> <input type="number" class="form-control col-10"
							id="newPriority" name="newPriority" value="${newPriority}">
					</div>
				</div>
				<div class="col">
					<div class="form-group row">
						<label for="newTitleConfirm" class="control-label col-4">Confirm
							Priority :</label> <input type="number" class="form-control col-10"
							id="newPriorityConfirm" name="newPriorityConfirm"
							value="${newPriorityConfirm}">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<button type="submit" name="doStuff" value="changePriority"
						class="btn btn-info">Change Priority</button>
				</div>
			</div>
		</form>
		<form class="form-horizontal" method="post">
			<input type="hidden" name="posID" value="${posID}">
			<div class="row">
				<div class="col">
					<h2>Change Description</h2>
				</div>
			</div>
			<div class="row">
				<div class="col-2"></div>
				<div class="col-10">
					<c:if test="${!empty descriptionError}">
						<p class="alert alert-warning">${descriptionError}</p>
					</c:if>
				</div>
			</div>
			<div class="form-group row">
				<label for="newDescription" class="control-label col-2">New
					Description: </label>
				<textarea rows="4" cols="50" name="newDescription"></textarea>
			</div>
			<div class="row">
				<div class="col">
					<button type="submit" name="doStuff" value="changeDescription"
						class="btn btn-info">Change Description</button>
				</div>
			</div>
		</form>
		<h2>View and Change Requirements</h2>
		<form class="form-horizontal" method="post">
			<h3>View Them</h3>
			<div class="row">
				<div class="col">
					<a href="search_sops?posID=${posID}" class="btn btn-info btn-block"
						role="button">View Them</a>
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