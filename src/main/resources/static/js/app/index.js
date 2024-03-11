const main ={
    init : function (){
        const _this = this;

        $('#btn-join').on('click', function (){
            _this.join();
        });
    },
    join : function (){
        const data = {
            email: $('#email').val(),
            password: $('#password').val(),
            nickName: $('#nickName').val()
        };

        $.ajax({
            type: 'POST',
            url: '/member/save',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('회원가입 되었습니다.');
            this.doubleSubmitFlag = true;
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();