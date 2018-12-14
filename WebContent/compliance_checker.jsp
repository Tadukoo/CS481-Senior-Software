<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
<title>CTM mkii - Compliance Checker</title>
<link rel="stylesheet" href="css/bootstrap.css">
</head>
<body>
	<h1>Compliance Checkers</h1>
	<p class="note">
		Want to return to the Homepage? <a href="./user_home">Home</a>
	</p>
	<div class="fluid-container">
		<form class="form-horizontal" method="post">
			<div class="form-group row">
				<div class="col-4"></div>

				<div class="fieldset col-2 text-center">
					<input type="number" class="form-control" id="priority"
						name="priority" value="${priority}" placeholder="Priority">
				</div>
				<div class="fieldset col-1 text-center">
					<button type="submit" class="btn btn-info">Search</button>
				</div>
			</div>
			<div class="row">
				<div class="col-1"></div>
				<div class="col">Showing results ${(page*displaySize) + 1} -
					${fn:length(issues) lt ((page+1)*displaySize)?fn:length(issues):((page+1)*displaySize)}
					of ${fn:length(issues)}</div>
			</div>
			<div class="row">
				<div class="col-1"></div>
				<div class="col-2 text-center">
					<p>
						<b>SOP Title</b>
					</p>
				</div>
				<div class="col-1 text-center">
					<p>
						<b>SOP Priority</b>
					</p>
				</div>
				<div class="col-2 text-center">
					<p>
						<b>User Email</b>
					</p>
				</div>
			</div>
			<c:forEach begin="${page*displaySize}" end="${((page+1)*displaySize) - 1}" items="${issues}" var="current">
				<div class="row">
					<div class="col-1 text-center">
						<p>
							<input type="checkbox"></input>
						</p>
					</div>
					<div class="col-2 text-center">
						<p>${current.title}</p>
					</div>
					<div class="col-1 text-center">
						<p>${current.priority}</p>
					</div>
					<div class="col-2 text-center">
						<p>${current.email}</p>
					</div>

				</div>
			</c:forEach>
			<input type="hidden" name="page" value="${page}"> <input type="hidden" name="displaySize" value="${displaySize}">
			<div class="row">
				<div class="col-2 text-center">
					<c:if test="${page gt 0}">
						<button type="submit" name="changePage" value="prev"
							class="btn btn-info">Prev Page</button>
					</c:if>
				</div>
				<div class="col-2 text-right">Results Per Page:</div>
				<c:if test="${displaySize != 5}">
					<div class="col-1">
						<button type="submit" name="changeDisplaySize" value="5"
							class="btn btn-info">5</button>
					</div>
				</c:if>
				<c:if test="${displaySize != 10}">
					<div class="col-1">
						<button type="submit" name="changeDisplaySize" value="10"
							class="btn btn-info">10</button>
					</div>
				</c:if>
				<c:if test="${displaySize != 25}">
					<div class="col-1">
						<button type="submit" name="changeDisplaySize" value="25"
							class="btn btn-info">25</button>
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
					<c:if test="${fn:length(issues) gt (displaySize*(page + 1))}">
						<button type="submit" name="changePage" value="next"
							class="btn btn-info">Next Page</button>
					</c:if>
				</div>
			</div>
		</form>
	</div>
</body>
</html>