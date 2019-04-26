<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>hello</title>
</head>
<body>
	<h2>当前时间:${date}</h2>
	<h2>前置机信息：</h2>
	<h3>media_id : ${media_id}</h3>
    <h3>fec_id : ${fec_id}</h3>
    <h3>fec_url : ${fec_url}</h3>
    <h3>userdesk_url : ${service_url}</h3>
    <h3>fec_file_path : ${fec_file_path}</h3>
    <h3>fec_ver : ${fec_ver}</h3>
    <h3>fec_status : ${fec_status}</h3>
    <h3>log_switch : ${log_switch}</h3>
    
    <h3>camp_ok : ${camp_ok}</h3>
    <h3>camp_bad : ${camp_bad}</h3>
    <h3>camp_invalid : ${camp_invalid}</h3>
    <h3>au_ok : ${au_ok}</h3>
    <h3>au_bad : ${au_bad}</h3>
    <h3>camp_match : ${camp_match}</h3>
    <h3>log_index : ${log_index}</h3>
    
    <h3>app_path : ${app_path}</h3>
    <h3>heartbeat : ${heartbeat}</h3>
</body>
</html>
