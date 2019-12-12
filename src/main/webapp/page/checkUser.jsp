<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>删除异构系统指定人员待办流程</title>
        <link href="${pageContext.request.contextPath}/res/js/bootstrap-3.0.3-dist/css/bootstrap.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/res/js/bootstrap-3.0.3-dist/css/bootstrap-theme.min.css"
              rel="stylesheet">
        <link href="${pageContext.request.contextPath}/res/js/self/theme.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/res/js/self/grid.css" rel="stylesheet">
    </head>
<body>
<div class="container">
    <h2>检测用户是否存在</h2>

    <form action="${pageContext.request.contextPath}/checkUser" method="post">
        <label for="username">用户code: </label>
        <input type="text" name="usercode" id="usercode" class="form-control-static" />
        <br/>
        <label for="username">用户密码:</label>
        <input type="text" name="password" id="password" class="form-control-static" />
        <br/>
        <button type="submit" class="btn btn-lg btn-danger">验证</button>
    </form>
</div>
<script src="${pageContext.request.contextPath}/res/js/jquery1.10.2/jquery-1.10.2.min.js"
        type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/res/js/bootstrap-3.0.3-dist/js/bootstrap.min.js"
        type="text/javascript"></script>
</body>
</html>
