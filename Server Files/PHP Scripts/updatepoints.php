<?php
require "connection.php";
$username = $_GET['username'];
$points = $_GET['points'];
	
$sql = "UPDATE user SET points = '$points' WHERE `user_name` = '$username' ";
    
mysqli_query($conn,$sql);
mysqli_close($conn);
?>