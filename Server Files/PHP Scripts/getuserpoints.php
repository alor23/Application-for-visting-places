<?php
require "connection.php";
if($_SERVER['REQUEST_METHOD']=='POST'){
    $username = $_POST['user'];
        
    $sql = "select points from user where user_name like '$username';";
    $result = mysqli_query($conn,$sql);
    $row = mysqli_fetch_array($result);
    if(isset($result))
    {
        echo $row['points'];
        mysqli_close($conn);
    }
}
?>