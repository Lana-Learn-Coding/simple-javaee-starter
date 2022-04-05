<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="helper" tagdir="/WEB-INF/tags/helpers" %>

<%@ attribute name="url" %>

<helper:ajax/>
<script>
    $(function () {
        loadData();
    });

    function doCreate() {
        const $form = $('#modal-create form');
        const modal = bsModal('#modal-create');
        sendForm($form, { url: "${url}" }).then(() => {
            modal.hide();
            $form[0].reset();
            loadData();
        })
    }

    function loadData() {
        const url = '${url}' + formToQuery($('#data-table-query'));
        loadTable($('table'), { url: url }, (row) => {
            return `
                <button onclick="doShow(\${row.id}, '#modal-detail')" class="btn btn-primary btn-sm me-1">View</button>
                <button onclick="doShow(\${row.id}, '#modal-edit')" class="btn btn-primary btn-sm me-1">Edit</button>
                <button onclick="doDelete(\${row.id})" class="btn btn-danger btn-sm me-1" type="submit">Del</button>
            `
        });
    }

    function doDelete(id) {
        if (!confirm('Are you sure?')) return;
        $.ajax({
            method: 'DELETE',
            url: `${url}/\${id}`,
            complete: function () {
                loadData();
            }
        });
    }

    function doShow(id, modalId) {
        const $form = $(`\${modalId} form`);
        $form[0].reset();
        const modal = bsModal(modalId);
        modal.show();
        const $spinner = formSpinner($form);

        $.ajax({
            method: 'GET',
            url: `${url}/\${id}`,
            success: function (data) {
                formFromJson($form, data);
            },
            error: function (err) {
                modal.hide();
                alert(`Cannot view data \${err.status}}`);
            },
            complete: function () {
                $spinner.remove();
            }
        });
    }

    function doUpdate() {
        const $form = $('#modal-edit form');
        const modal = bsModal('#modal-edit');
        const id = $form.find('input[name=id]').val();
        sendForm($form, { url: `${url}/\${id}`, type: 'PUT' }).then(() => {
            modal.hide();
            $form[0].reset();
            loadData();
        })
    }
</script>
