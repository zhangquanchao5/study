$(document).ready(function() {

    $("#step2").css('display','none');
    $("#step3").css('display','none');
    $("#step4").css('display','none');

    $("#stepUp1").click(function(){
        $.ajax({
            url:  $contentPath + "/user/forgetValidate",
            type: "POST",
            data: {"userName":$("#userName").val()},
            timeout: 10000,
            dataType: "json",
            success: function (json) {
                if (json.success) {
                    if(json.mobile==null||json.mobile==""){
                        bootbox.alert("帐户名注册的用户没有填写手机号，请重新注册!!");
                    }else{
                        $("#step1").css('display','none');
                        $("#step2").css('display','block');
                        $("#step3").css('display','none');
                        $("#step4").css('display','none');
                        $("#mobile").val(json.mobile);
                    }

                } else {
                    bootbox.alert("帐户名不存在!");
                }
            }
        });
    });

    $("#sendMS").click(function (){
        var timestmp=new Date().getTime();
        var obj={"userPhone":$("#mobile").val(),"type":"5","timeStamp":timestmp,"signature":BASE64.encoder("unixue"+timestmp)+"|"+timestmp};
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

    $("#stepUp2").click(function(){
        $.ajax({
            url:  $contentPath + "/user/code",
            type: "POST",
            data: {"mobile":$("#mobile").val(),"valCode":$("#valCode").val()},
            timeout: 10000,
            dataType: "json",
            success: function (json) {
                if (json.success) {
                        $("#step1").css('display','none');
                        $("#step2").css('display','none');
                        $("#step3").css('display','block');
                        $("#step4").css('display','none');
                } else {
                    bootbox.alert("验证码失效或者输入错误!");
                }
            }
        });
    });


    $("#stepUp3").click(function(){
        $.ajax({
            url:  $contentPath + "/user/updatepwd",
            type: "POST",
            data: {"mobile":$("#mobile").val(),"password":$("#password").val()},
            timeout: 10000,
            dataType: "json",
            success: function (json) {
                if (json.success) {
                    $("#step1").css('display','none');
                    $("#step2").css('display','none');
                    $("#step3").css('display','none');
                    $("#step4").css('display','block');
                } else {
                    bootbox.alert("系统异常错误!");
                }
            }
        });
    });


});

var parent=null;
$("#forgetForm").on("validation", function(e, current){
    // 表单全部字段验证通过则返回 true
    // 只要有一个字段验证不通过就返回 false
    // 还没验证完，即验证结果未知的情况下返回 undefined
    // 当前正在验证的字段是否通过
    //if(parent!=null){
    //    if(parent.key!=current.key){
    //       $("#pre"+current.key).remove();
    //        console.log(parent.key);
    //    }
    //}
    //parent=current;
    console.log(current.key);
    if(!current.isValid){
        setTimeout(function(){
            $("#pre"+current.key).remove();
        },500);

        //  console.log(  $("#pre"+current.key).html());

    }
})


var send = {
    node:null,
    count:60,
    start:function(){
        if(this.count > 0){
            this.node.value ="重新发送("+ this.count--+")";
            var _this = this;
            setTimeout(function(){
                _this.start();
            },1000);
        }else{
            this.node.removeAttribute("disabled");
            this.node.value = "重新获取验证码";
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