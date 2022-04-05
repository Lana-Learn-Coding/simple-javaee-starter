<%@taglib prefix="layout" tagdir="/WEB-INF/tags/layouts" %>
<%@taglib prefix="component" tagdir="/WEB-INF/tags/components" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="helper" tagdir="/WEB-INF/tags/helpers" %>

<layout:base>
    <jsp:attribute name="title">List Customers</jsp:attribute>
    <jsp:attribute name="body">
        <div class="container">
            <div class="d-flex mb-3 align-items-center">
                <div class="h4 mb-0">Bus Ride App</div>
                <div class="flex-grow-1"></div>
                <button class="btn btn-primary btn-sm" data-bs-toggle="modal" data-bs-target="#modal-create">
                    New Bus Ride
                </button>
            </div>

            <div class="d-flex" id="data-table-query">
                <input type="text"
                       style="max-width: 300px"
                       class="form-control form-control-sm"
                       name="search"
                       placeholder="Search item"
                       aria-label="search"
                       value="${param.search}"
                       onchange="loadData()">
                <button class="btn btn-sm btn-primary ms-2" onclick="loadData()">Search</button>
            </div>

            <table class="table" id="data-table">
                <thead>
                <tr>
                    <th scope="col" data-key="id">Id</th>
                    <th scope="col" data-key="name">Name</th>
                    <th scope="col" data-key="driver">Birth Date</th>
                    <th scope="col" data-key="numberOfPassenger">Passengers</th>
                    <th scope="col" data-key="price">Price</th>
                    <th scope="col">Action</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>

        <div class="modal fade" id="modal-edit" data-bs-backdrop="static" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Edit</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <jsp:include page="edit.jsp"/>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" onclick="doUpdate()">Update</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modal-detail" data-bs-backdrop="static" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">Detail</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <jsp:include page="detail.jsp"/>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modal-create" data-bs-backdrop="static" tabindex="-1" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title">Create New</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <jsp:include page="create.jsp"/>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary btn-submit" onclick="doCreate()">
                            Create
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>

    <jsp:attribute name="scripts">
        <helper:ajax-crud url="bus"/>
    </jsp:attribute>
</layout:base>
