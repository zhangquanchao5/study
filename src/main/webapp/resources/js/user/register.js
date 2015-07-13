
$(document).ready(function() {

    $("#registerUp").click(function(){

        var ajaxOptions = {
            url: $contentPath+"/user/registerUp",
            type:'post',
            dataType:'json',
            timeout:30000,
            cache:false,
            success : function(responseJson) {
                if(responseJson.success){
                    bootbox.alert("注册成功，请登录!",function(){
                        window.location.href = $contentPath+"/login";
                    });
                }else{
                    if(responseJson.code=="2003"){
                        bootbox.alert("存在此账号!");
                    }else{
                        bootbox.alert("系统出现未知错误!");
                    }

                }
            }
        };

        $('#registerForm').isValid(function(v){
            if(v){
                $("#registerForm").ajaxSubmit(ajaxOptions);
            }
        });
    });

});