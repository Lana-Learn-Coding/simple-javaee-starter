<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layouts" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:base>
    <jsp:attribute name="title">Hello Program</jsp:attribute>
    <jsp:attribute name="body">
        <a href="/customers">Customer App</a>
        <a href="/bus">Bus Ride App</a>
    </jsp:attribute>
</layout:base>

