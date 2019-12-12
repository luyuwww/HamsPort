<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>消息</title>
    <link href="${pageContext.request.contextPath}/res/js/bootstrap-3.0.3-dist/css/bootstrap.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/js/bootstrap-3.0.3-dist/css/bootstrap-theme.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/res/js/self/theme.css" rel="stylesheet">
</head>
<body>

<div class="well">${message}</div>
<br/>
<a class="btn btn-lg btn-info"
   href="${pageContext.request.contextPath}/">返回</a>
</body>
</html>