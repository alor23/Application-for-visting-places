<?php 
require "connection.php";

	$sql = "SELECT FIND_IN_SET( points, ( SELECT GROUP_CONCAT( points ORDER BY points DESC ) FROM user ) ) AS place, user_name, points FROM user ORDER by points DESC";
	$result = mysqli_query($conn,$sql);
	$json = array();


	if(mysqli_num_rows($result)){
		while($row=mysqli_fetch_assoc($result))
		{
			$json['users'][]=$row;
		}
	}
	echo json_encode($json);

	mysqli_close($conn);
?>