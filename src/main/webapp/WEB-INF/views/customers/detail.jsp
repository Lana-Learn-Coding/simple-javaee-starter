<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layouts" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="it" scope="request" type="io.lana.ejb.customer.Customer"/>

<layout:base>
    <jsp:attribute name="title">Create Customer</jsp:attribute>
    <jsp:attribute name="body">
        <h4>Create Customer</h4>
        <div>
            <div class="form-group mb-2">
                <label for="code">ID</label>
                <input type="text" class="form-control" value="${it.id}" id="code" readonly>
            </div>
            <div class="form-group mb-2">
                <label for="name">Full Name</label>
                <input type="text" class="form-control" id="name" name="name"
                       value="${it.name}" readonly>
            </div>
            <div class="form-group mb-2">
                <label for="gender">Gender</label>
                <input type="text" class="form-control" id="gender" name="gender"
                       value="${it.gender ? 'male' : 'female'}" readonly>
            </div>
            <div class="form-group mb-2">
                <label for="birth_date">Birth Date</label>
                <input class="form-control" id="birth_date" name="birth_date" type="date"
                       value="${it.birthDate}" readonly>
            </div>
            <div class="form-group mb-2">
                <label for="email">Email</label>
                <input type="email" class="form-control" id="email" name="email"
                       value="${it.email}" readonly>
            </div>
            <div class="form-group mb-2">
                <label for="phone">Phone Number</label>
                <input type="text" class="form-control" id="phone" name="phone"
                       value="${it.phone}" readonly>
            </div>
            <a href="/customers" class="btn btn-primary btn-sm">Back</a>
        </div>
    </jsp:attribute>
</layout:base>
