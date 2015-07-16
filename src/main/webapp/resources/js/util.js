/**
 * Created by Administrator on 2015/2/4.
 */
var utils = {};
utils.getDeviceType = function () {
    var deviceType = "pc";
    var sUserAgent = navigator.userAgent.toLowerCase();   //浏览器的用户代理设置为小写，再进行匹配
    var isIpad = sUserAgent.match(/ipad/i) == "ipad";   //或者利用indexOf方法来匹配
    var isIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";
    var isMidp = sUserAgent.match(/midp/i) == "midp";  //移动信息设备描述MIDP是一套Java应用编程接口，多适用于塞班系统
    var isUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";  //CVS标签
    var isUc = sUserAgent.match(/ucweb/i) == "ucweb";
    var isAndroid = sUserAgent.match(/android/i) == "android";
    var isCe = sUserAgent.match(/windows ce/i) == "windows ce";
    var isWM = sUserAgent.match(/windows mobil/i) == "windows mobil";
    if (isIpad || isIphoneOs || isMidp || isUc7 || isUc || isAndroid || isCe || isWM) {
        if(isIpad){
            deviceType = "pad";
        }else{
            deviceType = "phone";
        }
        console.log('该设备为移动设备');
    } else {
        console.log('该设备为PC设备');
    }
    return deviceType;
};
utils.ajaxRequest = function (obj) {
    $.ajax({
        url: obj.url,
        type: "POST",
        cache: false,
        data: obj.data,
        timeout: 10000,
        dataType: "json",
        success: function (json) {
            if (json.success) {
                obj.successCallBack(json);
            } else {
                obj.errorCallBack(json);
            }
        }
    });
};
utils.get = function (obj) {
    $.ajax({
        url: obj.url,
        type: "GET",
        cache: false,
        timeout: 10000,
        dataType: "json",
        success: function (json) {
            if (json.success) {
                obj.successCallBack(json);
            } else {
                obj.errorCallBack(json);
            }
        }
    });
};
utils.ajaxSubmit = function (target, obj) {

    var ajaxOptions = {
        url: obj.url,
        type: 'post',
        dataType: 'json',
        timeout: 10000,
        cache: false,
        success: function (json) {
            if (json.success) {
                obj.successCallBack(json)
            } else {
                obj.errorCallBack(json);
            }
        }
    };
    $("#" + target).ajaxSubmit(ajaxOptions);
};

utils.validateForm = function (formId, success) {
    $('#' + formId).isValid(function (v) {
        if (v) {
            success()
        }
    });
};

function checkMobile(str) {
    var re = /^1\d{10}$/
    if (re.test(str)) {
       return true;
    } else {
        return false;
    }
}