<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
	<title>CTM mkii - Search Positions</title>
	<link rel="stylesheet" href="css/bootstrap.css">
	<link rel="stylesheet" href="css/main.css">
</head>
<body>


<div class="fluid-container">
	<form class="form-horizontal" method="post">
	<header class="header">
				<h1>Search Positions</h1>
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
			<div style="margin-top: 10px;margin-left: 10%">
		<div class="form-group row">
			<div class="col-1">
			
			</div>
			<div class="fieldset col-1 text-center">
				<input type="number" class="form-control" id="positionID" name="positionID" value="${positionID}" placeholder="ID">
			</div>
			<div class="fieldset col-3 text-center">
				<input type="text" class="form-control" id="title" name="title" value="${title}" placeholder="Title">
			</div>
			<div class="fieldset col-3 text-center">
				<input type="text" class="form-control" id="description" name="description" value="${description}" placeholder="Description">
			</div>
			<div class="fieldset col-2 text-center">
				<input type="number" class="form-control" id="priority" name="priority" value="${priority}" placeholder="Priority">
			</div>
			<div class="fieldset col-1 text-center">
				<button type="submit" class="btn btn-info">Search</button>
			</div>
			</div>
		</div>
		<div class="SUtabletop">
		<div class="row">
			<div class="col-1">
			
			</div>
			<div class="col">
				Showing results ${(page*displaySize) + 1} - ${fn:length(positions) lt ((page+1)*displaySize)?fn:length(positions):((page+1)*displaySize)} of ${fn:length(positions)}
			</div>
		</div>
		<div class="row">
			<div class="col-1">
				
			</div>
			<div class="col-1 text-center">
				<p><b>ID</b></p>
			</div>
			<div class="col-3 text-center">
				<p><b>Title</b></p>
			</div>
			<div class="col-3 text-center">
				<p><b>Description</b></p>
			</div>
			<div class="col-2 text-center">
				<p><b>Priority</b></p>
			</div>
			</div>
		</div>
		<div class="SUtablecontents">
		<c:forEach begin="${page*displaySize}" end="${((page+1)*displaySize) - 1}" items="${positions}" var="current">
				<div class="row">
					<div class="col-1 text-center">
						<p><a href="edit_position?posID=${current.ID}">Edit</a></p>
					</div>
					<div class="col-1 text-center">
						<p>${current.ID}</p>
					</div>
					<div class="col-3 text-center">
						<p>${current.title}</p>
					</div>
					<div class="col-3 text-center">
						<p>${current.description}</p>
					</div>
					<div class="col-2 text-center">
						<p>${current.priority}</p>
					</div>
					<div class="col-1 text-center">
						<p><a href="search_sops?posID=${current.ID}">View Requirements</a></p>
					</div>
				</div>
		</c:forEach>
		</div>
		<div style="margin-top: 5px">
		<input type="hidden" name="page" value="${page}">
		<input type="hidden" name="displaySize" value="${displaySize}">
		<div class="row">
			<div class="col-2 text-center">
				<c:if test="${page gt 0}">
					<button type="submit" name="changePage" value="prev" class="btn btn-info">Prev Page</button>
				</c:if>
			</div>
			<div class="col-2 text-right">
				Results Per Page:
			</div>
			<c:if test="${displaySize != 5}">
				<div class="col-1">
						<button type="submit" name="changeDisplaySize" value="5" class="btn btn-info">5</button>
				</div>
			</c:if>
			<c:if test="${displaySize != 10}">
				<div class="col-1">
						<button type="submit" name="changeDisplaySize" value="10" class="btn btn-info">10</button>
				</div>
			</c:if>
			<c:if test="${displaySize != 25}">
				<div class="col-1">
						<button type="submit" name="changeDisplaySize" value="25" class="btn btn-info">25</button>
				</div>
			</c:if>
			<c:if test="${displaySize != 50}">
				<div class="col-1">
						<button type="submit" name="changeDisplaySize" value="50" class="btn btn-info">50</button>
				</div>
			</c:if>
			<c:if test="${displaySize != 100}">
				<div class="col-1">
						<button type="submit" name="changeDisplaySize" value="100" class="btn btn-info">100</button>
				</div>
			</c:if>
			<div class="col-2 text-center">
				<c:if test="${fn:length(positions) gt (displaySize*(page + 1))}">
					<button type="submit" name="changePage" value="next" class="btn btn-info">Next Page</button>
				</c:if>
			</div>
			</div>
		</div>
	</form>
</div>
</body>
</html>