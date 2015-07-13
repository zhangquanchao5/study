
$(document).ready(function() {

    $("#loginUp").click(function(){

        var ajaxOptions = {
            url: $contentPath+"/loginUp",
            type:'post',
            dataType:'json',
            timeout:30000,
            cache:false,
            success : function(responseJson) {
                if(responseJson.success){
                        window.location.href =$contentPath+"/index";
                }else{
                    if(responseJson.code=="2002"){
                        bootbox.alert("用户名不存在!");
                    }else if(responseJson.code=="2001"){
                        bootbox.alert("密码不正确!");
                    }else{
                        bootbox.alert("系统出现未知错误!");
                    }

                }
            }
        };

        $('#loginForm').isValid(function(v){
            if(v){
                $("#loginForm").ajaxSubmit(ajaxOptions);
            }
        });
    });

});