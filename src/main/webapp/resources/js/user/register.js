
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
                    }else if(responseJson.code=="2005"){
                        bootbox.alert("验证码错误!");
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

    $("#sendMS").click(function (){
        if($("#mobile").val()==null||$("#mobile").val()==""){
            bootbox.alert("手机号不能为空!");
            return;
        }
        if(!checkMobile($("#mobile").val())){
            bootbox.alert("请检查输入的手机号!");
            return;
        }

        var obj={"userPhone":$("#mobile").val(),"type":"1"};
        send.init(document.getElementById("sendMS"));
        $.ajax({
            url:  $contentPath + "/pub/getCode",
            type: "POST",
            data: JSON.stringify(obj),
            timeout: 10000,
            contentType:"application/json",
            dataType: "json",
            success: function (json) {
                if (json.code=="001") {
                    //document.getElementById("sendMS").innerHTML = "发送成功";
                   //alert("发送成功!");
                } else {
                    //document.getElementById("sendMS").innerHTML = "失败,重新发送";
                }
            }
        });
    });

});



var send = {
    node:null,
    count:60,
    start:function(){
        //console.log(this.count);
        if(this.count > 0){
            this.node.innerHTML ="重新发送("+ this.count--+")";
            var _this = this;
            setTimeout(function(){
                _this.start();
            },1000);
        }else{
            this.node.removeAttribute("disabled");
            this.node.innerHTML = "获取验证码";
            this.count = 60;
        }
    },
    //初始化
    init:function(node){
        this.node = node;
        this.node.setAttribute("disabled",true);
        this.start();
    }
};