<?php
//******************************************************************************************************************
function query($koneksi,$sql){
	return mysqli_query($koneksi,$sql);
	mysqli_close($koneksi);
}
//******************************************************************************************************************
function escape($koneksi,$isidata){
	$hasil=mysqli_real_escape_string($koneksi,$isidata);
	return $hasil;
}
//******************************************************************************************************************
function num_rows($koneksi,$sql){
	return mysqli_num_rows(mysqli_query($koneksi,$sql));
	mysqli_close($koneksi);
}
//******************************************************************************************************************
function fetch_array($koneksi,$sql){
	$cek=num_rows($koneksi,$sql);
	if($cek>0){
		$hasil=mysqli_fetch_array(mysqli_query($koneksi,$sql));
		return $hasil;
	}
	else{
		$hasil=false;
		return $hasil;
	}
	mysqli_close($koneksi);
}
//******************************************************************************************************************
function affected_rows($koneksi,$sql){
	$query=mysqli_query($koneksi,$sql);
	if(mysqli_affected_rows($koneksi)>0){
		return true;
	}
	else{
		return mysqli_error($koneksi);
	}
	mysqli_close($koneksi);
}
//******************************************************************************************************************
?>