<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layouts" %>
<%@taglib prefix="component" tagdir="/WEB-INF/tags/components" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="helper" tagdir="/WEB-INF/tags/helpers" %>

<jsp:useBean id="it" scope="request" type="io.lana.ejb.lib.pageable.Page<io.lana.ejb.customer.Customer>"/>
<layout:base>
    <jsp:attribute name="title">List Customers</jsp:attribute>
    <jsp:attribute name="body">
        <div class="container">
            <div class="d-flex mb-3 align-items-center">
                <div class="h4 mb-0">Customers App</div>
                <div class="flex-grow-1"></div>
                <a class="btn btn-primary btn-sm" href="/customers/create">New Customers</a>
            </div>
            <form method="get" class="d-flex">
                <helper:inherit-param excludes="search"/>
                <input type="text"
                       style="max-width: 300px"
                       class="form-control form-control-sm"
                       name="search"
                       placeholder="Search item"
                       aria-label="search"
                       value="${param.search}">
                <button class="btn btn-sm btn-primary ms-2" type="submit">Search</button>
            </form>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Id</th>
                    <th scope="col">Full Name</th>
                    <th scope="col">Birth Date</th>
                    <th scope="col">Gender</th>
                    <th scope="col">Phone</th>
                    <th scope="col">Email</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${it.data}" varStatus="loop">
                    <tr>
                        <th scope="row">${loop.index + 1}</th>
                        <td class="truncate">${ item.id }</td>
                        <td>${ item.name }</td>
                        <td>${ item.birthDate }</td>
                        <td>${ item.gender ? 'male' : 'female' }</td>
                        <td>${ item.phone }</td>
                        <td>${ item.email }</td>
                        <td>
                            <a href="/customers/detail?id=${item.id}" class="btn btn-primary btn-sm me-1"
                               type="submit">View</a>
                            <a href="/customers/update?id=${item.id}" class="btn btn-primary btn-sm me-1"
                               type="submit">Edit</a>
                            <a onclick="return confirm('Are you sure delete this customer: ${item.name}')"
                               href="/customers/delete?id=${item.id}" class="btn btn-danger btn-sm me-1"
                               type="submit">Del</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <nav class="d-flex justify-content-center">
                <component:pagination pageMeta="${it.meta}"/>
                <div class="flex-grow-1"></div>
                <div>
                    <component:sorting labels="Phone,Name" values="phone desc,name asc"/>
                </div>
            </nav>
        </div>
    </jsp:attribute>
</layout:base>
