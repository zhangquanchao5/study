
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
                    alert("注册成功，请登录!");
                    if(responseJson.source=="1"){
                        if(responseJson.data==null||responseJson.data==""){
                            if(responseJson.msg!=null&&responseJson.msg!=""){
                                window.location.href = $contentPath+"/"+responseJson.msg+"/orgLogin";
                            }else{
                                window.location.href = $contentPath+"/orgLogin";
                            }
                        }else{
                            if(responseJson.msg!=null&&responseJson.msg!=""){
                                window.location.href = $contentPath+"/"+responseJson.msg+"/orgLogin?gotoURL="+responseJson.data;
                            }else{
                                window.location.href = $contentPath+"/orgLogin?gotoURL="+responseJson.data;
                            }
                        }
                    }else{
                        if(responseJson.data==null||responseJson.data==""){
                            if(responseJson.msg!=null&&responseJson.msg!=""){
                                window.location.href = $contentPath+"/"+responseJson.msg+"/login";
                            }else{
                                window.location.href = $contentPath+"/login";
                            }
                        }else{
                            if(responseJson.msg!=null&&responseJson.msg!=""){
                                window.location.href = $contentPath+"/"+responseJson.msg+"/login?gotoURL="+responseJson.data;
                            }else{
                                window.location.href = $contentPath+"/login?gotoURL="+responseJson.data;
                            }
                        }
                    }

                }else{
                    if(responseJson.code=="2003"){
                        alert("存在此账号!");
                    }else if(responseJson.code=="2005"){
                        alert("验证码错误!");
                    }else{
                        alert("系统出现未知错误!");
                        //bootbox.alert("系统出现未知错误!");
                    }
                    $("#registerUp").attr("disabled", false);
                }
            }
        };

        $('#registerForm').isValid(function(v){
            if(v){
                $("#registerUp").attr("disabled", true);
                $("#registerForm").ajaxSubmit(ajaxOptions);
            }
        });
    });

    $("#sendMS2").click(function (){
        alert("测试");
    });


});

var parent=null;
$("#registerForm").on("validation", function(e, current){
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
    }
})

var isSendMobile=true;
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
           // this.node.removeAttribute("disabled");
            this.node.innerHTML = "获取验证码60秒后没有收到短信可以重新获取";
            isSendMobile=true;
            this.count = 60;
        }
    },
    //初始化
    init:function(node){
        this.node = node;
        //this.node.setAttribute("disabled",true);
        this.start();
    }
};

function mobileAjax(){
    if($("#mobile").val()!=null&&$("#mobile").val()!=""){
        utils.ajaxRequest(
            {
                "url":$contentPath +"/user/registerValidate",
                "data":{"mobile":$("#mobile").val(),"domain":$("#domain").val()},
                "successCallBack":function(json){
                   // console.log("--------"+json)

                       // $("#sendMS").attr("disabled",false);
                    $('#sendMS').bind("click",sendCode);
                },
                "errorCallBack":function(json){
                     alert("帐号已存在!");
                //    bootbox.alert("手机号已存在!");
                    $('#sendMS').unbind("click",sendCode);
                }
            }
        );
    }
}

function sendCode(){
    if($("#mobile").val()==null||$("#mobile").val()==""){
        alert("手机号不能为空!");
        // bootbox.alert("手机号不能为空!");
        return;
    }
    if(!checkMobile($("#mobile").val())){
        alert("请检查输入的手机号!");
        // bootbox.alert("请检查输入的手机号!");
        return;
    }

    if(!isSendMobile){
        alert("短信发送中,等待....!");
        return;
    }

    isSendMobile=false;
    var timestmp=new Date().getTime();
    var obj={"userPhone":$("#mobile").val(),"domain":$("#domain").val(),"type":"1","timeStamp":timestmp,"signature":BASE64.encoder("unixue"+timestmp)+"|"+timestmp};
    send.init(document.getElementById("sendMSText"));
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
}