<?php 
require "connection.php";

	$sql = "SELECT * from places";
	$result = mysqli_query($conn,$sql);
	$json = array();


	if(mysqli_num_rows($result)){
		while($row=mysqli_fetch_assoc($result))
		{
			$json['places'][]=$row;
		}
	}
	echo json_encode($json);

	mysqli_close($conn);
?>