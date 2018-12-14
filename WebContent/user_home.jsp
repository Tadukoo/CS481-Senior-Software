<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>CTM mkii - User Home</title>
<link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>


	<div class="fluid-container">
		<form method="post">
			<header style = "background-color: #667fcc; padding-bottom: 5px;">
				<h1>Home</h1>
				<div class="row">
					<div class="col-10"></div>
					<div class="col-2">
					<p>Currently logged in as: ${user}</p>
						<button type="submit" name="action" class="btn btn-danger"
							value="logout">Logout</button>
					</div>

				</div>
			</header>
			<c:if test="${!empty error}">
				<div class="row">
					<div class="col text-center">
						<p class="alert alert-danger">${error}</p>
					</div>
				</div>
			</c:if>
			<c:if test="${!empty success}">
				<div class="row">
					<div class="col text-center">
						<p class="alert alert-success">${success}</p>
					</div>
				</div>
			</c:if>

			<div class="row">
				<div class="col text-center">
					<h2>My Account</h2>
				</div>
			</div>
			<div class="row">
				<div class="col text-center"></div>
				<div class="col-3 text-center">
					<a href="account_settings" class="btn btn-info btn-block"
						role="button">Account Settings</a>
				</div>
				<div class="col"></div>
			</div>
			<br>
			<div class="row">
				<div class="col text-center">
					<h2>Users</h2>
				</div>
			</div>

			<div class="row">
				<div class="col"></div>
				<div class="col text-center">
					<a href="create_account" class="btn btn-info btn-block"
						role="button">Create Account</a>
				</div>
				<div class="col text-center">
					<a href="search_users" class="btn btn-info btn-block" role="button">Search
						Users</a>
				</div>
				<div class="col"></div>
			</div>
			<br>
			<div class="row">
				<div class="col text-center">
					<h2>Positions</h2>
				</div>
			</div>

			<div class="row">
				<div class="col"></div>
				<div class="col text-center">
					<a href="create_position" class="btn btn-info btn-block"
						role="button">Create Position</a>
				</div>
				<div class="col text-center">
					<a href="search_positions" class="btn btn-info btn-block"
						role="button">Search Positions</a>
				</div>
				<div class="col"></div>
			</div>
			<br>
			<div class="row">
				<div class="col text-center">
					<h2>SOPs</h2>
				</div>
			</div>
			<div class="row">
				<div class="col"></div>
				<div class="col text-center">
					<a href="create_sop" class="btn btn-info btn-block" role="button">Create
						SOP</a>
				</div>
				<div class="col text-center">
					<a href="search_sops" class="btn btn-info btn-block" role="button">Search
						SOPs</a>
				</div>
				<div class="col"></div>
			</div>

			<br>
			<div class="row">
				<div class="col text-center">
					<h2>Compliance Tools</h2>
				</div>
			</div>
			<div class="row">
				<div class="col"></div>
				<div class="col text-center">
					<a href="compliance_checker" class="btn btn-info btn-block"
						role="button">Compliance Monitor</a>
				</div>
				<div class="col"></div>

			</div>
			<br>
			<div class="row">
				<div class="col text-center">
					<h2>Time Clock</h2>
				</div>
			</div>
			<div class="row">
				<div class="col"></div>
				<div class="col text-center">
					<c:if test="${clockedIn == 'false'}">
						<button type="submit" name="action"
							class="btn btn-success btn-block" value="clockIn">Clock
							in</button>
					</c:if>
					<c:if test="${clockedIn == 'true'}">
						<button type="submit" name="action"
							class="btn btn-danger btn-block" value="clockOut">Clock
							out</button>
					</c:if>
				</div>
				<div class="col"></div>

			</div>
		</form>
	</div>
</body>
</html>