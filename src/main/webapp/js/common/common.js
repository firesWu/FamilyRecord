/**
 * Created by yuan on 2018/3/24.
 */

var projectName = "FamilyRecord";
var default_album_image = "/" + projectName + "/images/project/default_album_image.png";

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


var userInfo = {account:123456,groupId:1,headImageUrl:"/images/headImage.jpg"};
var verLoginUrl = "/" + projectName + "/login/verLogin.do";
//判断是否登录状态
var verLogin = function(){

    ajaxFunction(verLoginUrl,{}, function (result) {
        if(result.success){
            userInfo = result.data[0];

            if(isNotNull(userInfo.headImageUrl)) $("#head_image_show").attr("src","/" + projectName + userInfo.headImageUrl);

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

var openForm = function(formId,name){
    $("div[name="+name+"]").hide();
    $("#"+formId).show();
}


//初始化fileIput组件
var initFileInput = function(id, uploadUrl,fileType){
    console.log($("#"+id));
    $("#" + id).fileinput({
        language: 'zh',  //设置中文
        uploadUrl: uploadUrl,   //上传地址
        allowedFileExtensions : fileType,
        overwriteInitial: false,
        browseClass : "btn btn-primary",
        previewFileIcon : "<i class=''></i>",
        uploadAsync : false,
        maxFileCount : 2,
        maxFileSize : 512000,
        theme: 'fa',
        autoReplace : false,
        uploadExtraData : function(previewId , index){
            var params = {};
            return params;
        }
    });

};

/**
 支持多文件上传 兼容低版本浏览器
 $form  上传的表单表单里  要有file
 callback 回调方法
 **/
function commonWinFileUploadCallback(data){


}

function fileUpload($form,callback,params,action) {
    var myform = $form;
    var deferred = new $.Deferred //create a custom deferred object, because there's no ajax here to create it for us
    var temporary_iframe_id = 'temporary_iframe_' + (new Date()).getTime()
        + '-' + (parseInt(Math.random() * 1000));
    var temp_iframe = $('<iframe id="'+temporary_iframe_id+'" name="'+temporary_iframe_id+'"  frameborder="0" width="0" height="0" src="about:blank"\ style="position:absolute; z-index:-1; visibility: hidden;" />')
        .insertAfter(myform);
    params.temporary_iframe_id = temporary_iframe_id;

    for(var i in params){
        myform.append("<input type='hidden' name='"+ i +"' value=' "+ params[i] +" '/>");
    }
    myform.append('<input type="hidden" name="callback" value="parent.'+callback+'" />');

    temp_iframe.data('deferrer', deferred);

    myform.attr({
        'method' : 'POST',
        'enctype' : 'multipart/form-data',
        'target' : temporary_iframe_id,
        'action' :action
    });

    myform.get(0).submit();

    return 1;
}