<?php
require "connection.php";
$username = $_GET['username'];
$place_name = $_GET['place_name'];
$points = $_GET['points'];
	
	$sql = "select * from visited where user_name = '$username' and title_name = '$place_name';";
    $check = mysqli_fetch_array(mysqli_query($conn,$sql));
if(isset($check))
{
	echo 'Already visited';
}
else
{
	$sql = "INSERT INTO visited (user_name,title_name) VALUES('$username','$place_name')";
	$sql2 = "UPDATE user SET points = '$points' WHERE `user_name` = '$username' ";
    
if(mysqli_query($conn,$sql) && mysqli_query($conn,$sql2))
{
	echo 'Visited';
}
else
{
	echo 'Visited failure';
}
}
	mysqli_close($conn);	
?>