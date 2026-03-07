<?php

$server1 = '192.168.0.34';
$username1 = 'james';
$password1 = '1';
$database1 = 'sik';

$connection1 = mysqli_connect($server1,$username1,$password1,$database1);

if(!$connection1)
{
	echo "Koneksi gagal!" . mysqli_connect_error();
	die();
} 
?>
