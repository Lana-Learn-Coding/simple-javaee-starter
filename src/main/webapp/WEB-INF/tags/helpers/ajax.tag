<%@ tag body-content="empty" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
    function loadTable($table, options, actionRender) {
        const $spinner = tableSpinner($table);
        const $body = $table.find('tbody');
        $body.empty();

        const keys = [];
        $table.find('th[data-key]').each(function () {
            const key = $(this).attr('data-key');
            if (key) keys.push(key);
        });
        $.ajax({
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                for (let i = 0; i < data.length; i++) {
                    const $tr = $(document.createElement('tr'));
                    keys.forEach(key => {
                        if (key === '#') return $tr.append(`<td scope="row">\${i + 1}</td>`);
                        const val = data[i][key] ?? '';
                        $tr.append(`<td>\${val}</td>`)
                    })

                    if (actionRender) $tr.append(`<td>\${actionRender(data[i])}</td>`);
                    $body.append($tr);
                }
            },
            error: function (err) {
                alert(`Cannot load data for table (\${err.status})`)
            },
            complete: function () {
                $spinner.remove();
            },
            ...options,
        })
    }

    function tableSpinner($table) {
        const $spinner = $(`
            <div class="d-flex justify-content-center table-loading">
                <div class="spinner-border" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </div>
        `);
        $spinner.insertAfter($table);
        return $spinner;
    }


    async function sendForm($form, options = {}) {
        const json = formToJson($form);
        const $spinner = formSpinner($form);

        return new Promise(((resolve, reject) => {
            $.ajax({
                type: "POST",
                data: JSON.stringify(json),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (data) {
                    resolve(data);
                },
                error: function (err) {
                    // auto render error if it is validation error
                    if (err.status === 400) formFromError($form, err.responseJSON);
                    reject(err);
                },
                complete: function () {
                    $spinner.remove();
                },
                ...options,
            })
        }))
    }

    function formFromError($form, errors) {
        if (!errors || !Object.keys(errors).length) return;
        $form.find('div.invalid-feedback.auto-feedback').remove();
        $form.find('input,select').removeClass('is-invalid');

        Object.keys(errors).forEach(key => {
            const $input = $form.find(`input[name=\${key}],select[name=\${key}]`);
            if (!$input.length) return;
            $input.addClass('is-invalid');
            $input.parent('.form-group').append(`<div class="invalid-feedback auto-feedback">\${errors[key]}</div>`)
        });
    }

    function formSpinner($form) {
        const $spinner = $(`
            <div class="d-flex justify-content-center mt-3 form-loading">
                <div class="spinner-grow" role="status">
                    <span class="visually-hidden">Loading...</span>
                </div>
            </div>
        `);
        $form.append($spinner);
        return $spinner;
    }

    function formToQuery($form) {
        const json = formToJson($form);
        let param = '?';
        Object.keys(json).forEach((key) => {
            if (param === '?') param += `\${key}=`;
            else param += `&\${key}=`
            param += urlEncode(json[key]);
        })

        return param === '?' ? '' : param;
    }

    function formToJson($form) {
        const $inputs = $form.find('input,select');
        const json = {};
        $inputs.each(function () {
            const $input = $(this);
            const val = $input.val();
            const name = $input.attr('name');
            const type = $input.attr('type');

            if (type === 'number') {
                json[name] = val.trim() === '' ? null : Number(val);
                return;
            }

            if (type === 'checkbox' && $input.is(":checked")) {
                json[name] = val === 'true' ? true : val;
                return;
            }

            if (type === 'date' || type === 'datetime') {
                json[name] = val.trim() === '' ? null : val;
                return;
            }

            json[name] = val;
        });
        return json;
    }

    function formFromJson($form, json) {
        Object.keys(json).forEach(key => {
            const $input = $form.find(`input[name=\${key}],select[name=\${key}]`);
            if (!$input.length) return;
            const val = json[key];

            if ($input.attr('type') === 'checkbox') {
                if (val === true && $input.val() === 'true') {
                    $input.prop('checked', true);
                    return;
                }
                if (val === $input.val()) {
                    $input.prop('checked', true);
                    return;
                }

                $input.prop('checked', false);
                return;
            }

            $input.val(val).change();
        });
    }

    function urlEncode(val) {
        return encodeURIComponent(val).replace(/[!'()]/g, escape).replace(/\*/g, "%2A");
    }

    function bsModal(selector) {
        return bootstrap.Modal.getOrCreateInstance(document.querySelector(selector));
    }
</script>
