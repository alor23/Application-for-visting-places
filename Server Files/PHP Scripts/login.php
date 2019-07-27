<?php 
require "connection.php";
if($_SERVER['REQUEST_METHOD']=='POST'){
	$username = $_POST['user'];
		
	$password = md5($_POST['password']);
	$sql = "select * from user where user_name like '$username' and password like '$password';";
	
	$result = mysqli_query($conn,$sql);
	$check = mysqli_fetch_array($result);
	if(isset($check))
	{
	echo "success";
	}
	else{
	echo "failure";
	}
		mysqli_close($conn);
	}
?>