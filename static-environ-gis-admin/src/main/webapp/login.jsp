<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>Static Environment GIS :: Administration :: Login</title>
	</head>
	<body>
		<div>
			<form method="post" action="j_security_check">
				<div>
					<label for="j_username">Username:</label>
					<input type="text" name="j_username" />
				</div>
				<div>
					<label for="j_password">Password:</label>
					<input type="password" name="j_password" />
				</div>
				<input type="submit" name="Login" value="Login" />
			</form>
		</div>
	</body>
</html>