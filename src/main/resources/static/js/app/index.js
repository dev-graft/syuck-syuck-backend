var index = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () { _this.save(); });
    },
    save : function () {
        var data = {
            nickName: $('#nickName').val(),
            email: $('#email').val(),
            stateMessage: $('#stateMessage').val()
        };

        $.ajax({
            type: 'POST',
            url: '/members',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('회원가입이 성공했습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
};

index.init();