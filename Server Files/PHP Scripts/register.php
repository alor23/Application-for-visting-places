<?php
require "connection.php";
$username = $_GET['username'];
	
$password = md5($_GET['password']);
    
if($username == '' || $password == '')
{
	echo 'Please fill fields';
}
else
{
	$sql = "select * from user where user_name like '$username' and password like '$password';";
    $check = mysqli_fetch_array(mysqli_query($conn,$sql));
if(isset($check))
{
	echo 'User in db';
}
else
{
    $sql = "INSERT INTO user (user_name,password) VALUES('$username','$password')";
    
if(mysqli_query($conn,$sql))
{
	echo 'Register success';
}
else
{
	echo 'Register failure';
}
}
	mysqli_close($conn);
}	
?>