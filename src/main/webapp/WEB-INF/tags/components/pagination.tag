<%@ tag body-content="empty" %>
<%@ attribute name="pageMeta" required="true" type="io.lana.ejb.lib.pageable.PageMeta" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="url" value="">
    <c:forEach items="${param}" var="entry">
        <c:if test="${entry.key != 'page'}">
            <c:param name="${entry.key}" value="${entry.value}"/>
        </c:if>
    </c:forEach>
</c:url>

<ul class="pagination pagination-sm">
    <li class="page-item <c:if test="${pageMeta.currentPage == 1}">disabled</c:if>">
        <a class="page-link" href="${(empty url ? "?page=" : url.concat("&page=")).concat(pageMeta.currentPage - 1)}"
           aria-label="Previous">&laquo;</a>
    </li>
    <c:forEach var="i" begin="1" end="${pageMeta.totalPages}">
        <li class="page-item <c:if test="${pageMeta.currentPage == i}">active</c:if>">
            <a class="page-link" href="${(empty url ? "?page=" : url.concat("&page=")).concat(i)}">${i}</a>
        </li>
    </c:forEach>
    <li class="page-item <c:if test="${pageMeta.currentPage == pageMeta.totalPages}">disabled</c:if>">
        <a class="page-link" href="${(empty url ? "?page=" : url.concat("&page=")).concat(pageMeta.currentPage + 1)}"
           aria-label="Next">
            &raquo;
        </a>
    </li>
</ul>
