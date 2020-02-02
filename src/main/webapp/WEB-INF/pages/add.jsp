<html>
<head>
<meta charset="UTF-8">
<title>Add user</title>
</head>
<body>
<h3>New user</h3>
<form method="post">
<label>Name</label><br>
<input name="username" placeholder="Alex" pattern="[a-zA-Z]+" minlength="1" maxlength="40"/><br><br>
<label>Password(at least 4 digits)</label><br>
<input name="password" placeholder="12345" pattern="[0-9]+" minlength="4" maxlength="40"/><br><br>
<label>Age</label><br>
<input name="age" placeholder="33" /><br><br>
<label>Active</label><br>
<input name="is_active" pattern="(true|false)" placeholder="true or false"/><br><br>
<label>Telephone</label><br>
<input name="telephone" placeholder="80297559536 or +375297559536" pattern="(\+375|80)(29|25|44|33)(\d{3})(\d{2})(\d{2})" minlength="11" maxlength="13"/><br><br>
<label>Address</label><br>
<input name="address" placeholder="city Minsk street Alleynaya" pattern="(city)\s(\D{1,33})\s(street)\s(\D{1,44})" minlength="11" maxlength="100"  /><br><br>
<input type="submit" value="Save"/>
</form>
</body>
</html>