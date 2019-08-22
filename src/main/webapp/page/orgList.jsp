<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>OA公司列表</title>
        <link href="${pageContext.request.contextPath}/res/js/bootstrap-3.0.3-dist/css/bootstrap.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/res/js/bootstrap-3.0.3-dist/css/bootstrap-theme.min.css"
              rel="stylesheet">
        <link href="${pageContext.request.contextPath}/res/js/self/theme.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/res/js/self/grid.css" rel="stylesheet">
    </head>
<body>
<div class="container">
    <h2>OA公司列表</h2>

    <table class="table table-bordered">

        <thead>
        <tr>
            <th>code</th>
            <th>canceled</th>
            <th>fullname</th>
            <th>shortname</th>
            <th>showorder</th>
            <th>subcompanyid</th>
            <th>supsubcompanyid</th>
            <th>action</th>
            <th>lastChangdate</th>
        </tr>
        </thead>
        <tbody>
<%--        //            private String canceled;--%>
<%--        //            private String code;--%>
<%--        //            private String fullname;--%>
<%--        //            private String shortname;--%>
<%--        //            private String showorder;--%>
<%--        //            private String subcompanyid;--%>
<%--        //            private String supsubcompanyid;--%>
<%--        //            private String website;--%>
<%--        //            private String action;--%>
<%--        //            private String lastChangdate;--%>
        <c:forEach items="${orgList}" var="org">
            <tr>
                <td>${org.code}</td>
                <td>${org.canceled}</td>
                <td>${org.fullname}</td>
                <td>${org.shortname}</td>
                <td>${org.showorder}</td>
                <td>${org.subcompanyid}</td>
                <td>${org.supsubcompanyid}</td>
                <td>${org.action}</td>
                <td>${org.lastChangdate}</td>
            </tr>
        </c:forEach>

        </tbody>
    </table>
</div>
<script src="${pageContext.request.contextPath}/res/js/jquery1.10.2/jquery-1.10.2.min.js"
        type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/res/js/bootstrap-3.0.3-dist/js/bootstrap.min.js"
        type="text/javascript"></script>
</body>
</html>
