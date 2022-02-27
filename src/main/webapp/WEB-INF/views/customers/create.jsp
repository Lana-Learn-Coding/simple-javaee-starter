<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layouts" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="it" scope="request" type="io.lana.ejb.customer.Customer"/>
<jsp:useBean id="errors" scope="request" type="java.util.Map"/>

<layout:base>
    <jsp:attribute name="title">Create Customer</jsp:attribute>
    <jsp:attribute name="body">
        <h4>Create Customer</h4>
        <form method="post">
            <div class="form-group mb-2">
                <label for="name">Full Name</label>
                <input type="text" class="form-control <c:if test="${not empty errors['name']}">is-invalid</c:if>"
                       id="name" name="name" value="${it.name}">
                <div class="invalid-feedback">${errors['name']}</div>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="gender" id="gender_male"
                       <c:if test="${it.gender}">checked</c:if> value="true">
                <label class="form-check-label" for="gender_male">
                    Male
                </label>
            </div>
            <div class="form-check form-check-inline">
                <input class="form-check-input" type="radio" name="gender" id="gender_female"
                       <c:if test="${!it.gender}">checked</c:if> value="false">
                <label class="form-check-label" for="gender_female">
                    Female
                </label>
            </div>
            <div class="form-group mb-2">
                <label for="birth_date">Birth Date</label>
                <input class="form-control <c:if test="${not empty errors['birthDate']}">is-invalid</c:if>"
                       id="birth_date" name="birthDate" type="date"
                       value="${it.birthDate}">
                <div class="invalid-feedback">${errors['birthDate']}</div>
            </div>
            <div class="form-group mb-2">
                <label for="email">Email</label>
                <input type="email" class="form-control <c:if test="${not empty errors['email']}">is-invalid</c:if>"
                       id="email" name="email" value="${it.email}">
                <div class="invalid-feedback">${errors['email']}</div>
            </div>
            <div class="form-group mb-2">
                <label for="phone">Phone Number</label>
                <input type="text" class="form-control <c:if test="${not empty errors['phone']}">is-invalid</c:if>"
                       id="phone" name="phone" value="${it.phone}">
                <div class="invalid-feedback">${errors['phone']}</div>
            </div>
            <button type="submit" class="btn btn-primary btn-sm">Submit</button>
            <a href="/customers" class="btn btn-primary btn-sm">Back</a>
        </form>
    </jsp:attribute>
</layout:base>
