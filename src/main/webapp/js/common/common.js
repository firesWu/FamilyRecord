/**
 * Created by yuan on 2018/3/24.
 */

var projectName = "FamilyRecord";

function ajaxFunction(url, params, fn, sync) {
    // $.blockUI(maskContent);

    if (sync == undefined || sync == null) {
        sync = true;

    }
    $.ajax({
        async : sync,
        type : "post",
        url : url,
        dataType : "json",
        data : params,
        success : function(data) {
            if ($.isFunction(fn)) {
                try {
                    fn(data);
                } catch (e) {
                    alert(e.stack);
                }
            }
            // 关闭遮罩
            // $.unblockUI();
        },
        error : function(r) {
            alert("error");
            // $.unblockUI();
        }
    });

}

/*******************************************************************************
 * 表单序列化
 *
 * @param id
 *            dom id
 * @return object
 */
function formToJsonstr(fromId) {
    var params = {};

    $('#' + fromId + ' :input').each(
        function(index, obj) {
            var type = obj.type;
            var tag = obj.tagName.toLowerCase();
            var $obj = $(obj);
            var value = $obj.val();
            var key = $obj.attr('name');

            if ((type == 'text' || type == 'password' || type == 'hidden'
                || tag == 'textarea' || tag == 'select')
                && key != undefined && key != '') {

                var sType = $obj.attr('data-sType');
                if (sType == "date") {
                    if (value != "" && value != "0001-01-01 00:00:00")
                        params[key] = $obj.val();
                } else {
                    params[key] = $obj.val();
                }

            }
            if (type == 'radio') {
                if ($obj.is(':checked') == true) {
                    params[key] = $obj.val();

                }
            }
            if (type == 'checkbox') {
                if ($obj.is(':checked') == true) {
                    //改动
                    if (key != null && key != 'undefined') {
                        params[key] = '1';
                    }
                }else{
                    if (key != null && key != 'undefined') {
                        params[key] = '0';
                    }
                }

            }

        });
    return params;
}


var userInfo;
var verLoginUrl = "/" + projectName + "/login/verLogin.do";
//判断是否登录状态
var verLogin = function(){

    ajaxFunction(verLoginUrl,{}, function (result) {
        if(result.success){
            userInfo = result.data[0];
            console.log(userInfo);
        }else{
            window.location = "/" + projectName + "/login.html";
        }
    },false);

};

//判断是否有家庭组
var judgeFamilyGroup = function(){
  if(userInfo.groupId == undefined || userInfo.groupId == ''){
      window.location = "/" + projectName + "/familyManager/familyGroupCreate.html";
  }
};

//切换两个表单的显示
var changeForm = function(form1,form2){
    $("#"+form1).hide();
    $("#"+form2).show();
};