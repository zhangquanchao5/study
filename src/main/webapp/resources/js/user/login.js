
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
                        if(responseJson.code!=null&&responseJson.code!=""&&responseJson.code==200){
                            window.location.href =responseJson.data;
                        }else if(responseJson.code!=null&&responseJson.code!=""){
                            window.location.href =responseJson.code;
                        }else{
                            if(responseJson.msg!=null&&responseJson.msg!=""){
                               // console.log("domain:"+responseJson.msg);
                                window.location.href ="http://"+responseJson.msg;
                            }else{
                                window.location.href =baseUrl;
                            }
                        }
                }else{
                    if(responseJson.code=="2002"){
                        alert("用户名不存在!");
                    }else if(responseJson.code=="2001"){
                        alert("密码不正确!");
                    }else{
                        alert("系统出现未知错误!");
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