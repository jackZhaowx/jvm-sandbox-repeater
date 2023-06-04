var canReplay = false;
jQuery(function ($) {
    $(".btn-change-ingore-keys").on('click', function () {
        var id = $(this).attr('data-id');
        $('#module-id').val(id);
        var ingoreKeys = $(this).attr('data-ingore-keys');
        $('#module-ingore-kyes').val(ingoreKeys);
        var modal = $('#change-ingore-keys-model');
        modal.on('shown.bs.modal', function () {
            $(this).css('display', 'block');
            var modalHeight = $(window).height() / 2 - $('#change-ingore-keys-model .modal-dialog').height() / 2;
            $(this).find('.modal-dialog').css({
                'margin-top': modalHeight
            });
        });
        modal.modal('show')
    });

    $("#change-ingore-keys").on('click', function () {
        var id=$('#module-id').val();
        showLoading(10);
        $("#startReplayForm").ajaxSubmit({
            type: "post",
            url: "//" + host + "/module/updateIngoreKeys.json",
            success: function (data) {
                $("#change-ingore-keys-model").modal('hide')
                hideLoading(10)
                if (data.success) {
                    openNewWindow(protocol + "//" + host + "/module/detail.htm?id=" + id,
                        "执行发起成功，您的浏览器阻止了结果页面自动打开，请先允许或点击前往 >> ")
                } else {
                    notice(data.message, data.success)
                }
            },
            error: function (XMLHttpRequest) {
                hideLoading(10);
                notice("服务抽风了，网络异常 " + XMLHttpRequest.responseText, false);
            }
        })
    });
});
