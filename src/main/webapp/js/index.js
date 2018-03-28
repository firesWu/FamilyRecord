/**
 * Created by yuan on 2018/3/24.
 */

$(function(){
    //verLogin();
    //judgeFamilyGroup();
    $('#article_editor').wysiwyg();

    $('#avatarInput').on('change', function(e) {
        var filemaxsize = 1024 * 5;//5M
        var target = $(e.target);
        var Size = target[0].files[0].size / 1024;
        if(Size > filemaxsize) {
            alert('图片过大，请重新选择!');
            $(".avatar-wrapper").childre().remove;
            return false;
        }
        if(!this.files[0].type.match(/image.*/)) {
            alert('请选择正确的图片!')
        } else {
            var filename = document.querySelector("#avatar-name");
            var texts = document.querySelector("#avatarInput").value;
            var teststr = texts; //你这里的路径写错了
            testend = teststr.match(/[^\\]+\.[^\(]+/i); //直接完整文件名的
            filename.innerHTML = testend;
        }

    });

    $(".avatar-save").on("click", function() {
        var img_lg = document.getElementById('imageHead');
        // 截图小的显示框内的内容
        html2canvas(img_lg, {
            allowTaint: true,
            taintTest: false,
            onrendered: function(canvas) {
                canvas.id = "mycanvas";
                //生成base64图片数据
                var dataUrl = canvas.toDataURL("image/jpeg");
                var newImg = document.createElement("img");
                newImg.src = dataUrl;
                $("#newImage").attr('src',dataUrl);
                imagesAjax(dataUrl)
            }
        });
    });

    $('#user_birthday').datetimepicker({
        viewMode : 'months',
        format : ' YYYY-MM-DD',
        sideBySide : true
    });

});

/********************************************  相册模块 begin ********************************************/

var insertAlbumUrl = "/" + projectName + "/album/insert.do";
var selectAlbumInfoUrl = "/" + projectName + "/album/selectAlbumInfo.do";
var selectPhotoOrVideoUrl = "/" + projectName + "/commonFile/selectPhotoOrVideo.do";
var uploadPhotoOrVideoUrl = "/" + projectName + "/fileUpload/upload.do";
var albumInfo = {id:1};


//视频模块
var selectVideoList = function(){

    var params = {};
    params.rId = userInfo.groupId;
    params.type=2;

    ajaxFunction(selectPhotoOrVideoUrl,params,function(result){

        if(result.success){
            var data = result.data;
            var str = "";

            for(var i = 0 ; i < data.length ;i++){
                str += "<div class='pull-left album' ><video  onclick='videoPreview(\""+ data[i].filePath+"\")' src='"+"/"+projectName+"/" + (data[i].filePath) +"' controls='controls' class='family_record_photo img-thumbnail' /></div>";
            }

            $("#video_list").html(str);
            initFileInput('upload_video_file',"#",['avi','wmv','mpeg','mp4','mov','mkv','flv','f4v','m4v','rmvb','rm','3gp','dat','ts','mts','vob']);
        }else{
            console.log(result.msg);
        }

    });

};

var videoPreview = function(path){
    $("#video_show").modal("show");
    $("#video_preview").attr("src","/"+projectName + "/" + path);
    $("#video_show").on("hide.bs.modal",function(){
        $("#video_preview").attr("src","");
    });
};

var uploadVideo = function(formId,showFormId){

    var params = {};
    params.type = 2;
    params.creator = userInfo.account;
    params.rId = userInfo.groupId;

    fileUpload($("#"+formId),"uploadVideoCallback",params,uploadPhotoOrVideoUrl);

    $("#"+showFormId).modal("hide");
};

var uploadVideoCallback = function(result){

    if(result.success){
        selectVideoList();
        console.log(result.msg);
    }else{
        console.log(result.msg);
    }

};

//相册模块
var createAlbum = function(formId){

    var params = formToJsonstr(formId);

    if(!isNotNull(params.albumName)){
        return ;
    }

    params.groupId= userInfo.groupId;
    params.type = 1;
    params.creator = userInfo.account;

    ajaxFunction(insertAlbumUrl,params,function(result){

        if(result.success){
            console.log(result.msg);
            selectAlbumInfo();
            $("#create_album").modal("hide");
        }else{
            console.log(result.msg);
        }

    });

};

var selectAlbumInfo = function(divId){

    var params = {};
    params.groupId = userInfo.groupId;

    ajaxFunction(selectAlbumInfoUrl,params,function(result){

        if(result.success){
            console.log(result.data);
            var data = result.data;
            var str = "";

            for(var i = 0 ; i < data.length ;i++){
                str += "<div class='pull-left album'> <a href='#' onclick='openAlbum(" + JSON.stringify(data[i]) +")'> <img src='" + (data[i].filePath == undefined?default_album_image:"/"+projectName+"/"+data[i].filePath) +"' class='family_record_photo img-thumbnail' /> <span>" + data[i].albumName + "</span> </a> </div>";
            }

            $("#"+ divId).html(str);

        }else{
            console.log(result.msg);
        }

    });

};

var openAlbum = function(album){

    var params = {};
    albumInfo = album;
    params.rId = albumInfo.id;
    params.type = 1;

    ajaxFunction(selectPhotoOrVideoUrl,params,function(result){

        if(result.success){
            var data = result.data;
            var str = "";

            for(var i = 0 ; i < data.length ;i++){
                str += "<div class='pull-left album' ><img  onclick='photoPreview(\""+ data[i].filePath+"\")' src='"+"/"+projectName+"/" + (data[i].filePath) +" ' class='family_record_photo img-thumbnail' /></div>";
            }

            $("#photo_list").html(str);
            changeForm("album_page","photo_page");
            initFileInput('upload_photo_file',"#",['jpg','png']);
        }else{
            console.log(result.msg);
        }

    });

};

var photoPreview = function(path){
    $("#photo_show").modal("show");
    $("#photo_preview").attr("src","/"+projectName + "/" + path);
};

var uploadPhoto = function(formId,showFormId){

    var params = {};
    params.type = 1;
    params.creator = userInfo.account;
    params.rId = albumInfo.id;

    fileUpload($("#"+formId),"uploadPhotoCallback",params,uploadPhotoOrVideoUrl);

    $("#"+showFormId).modal("hide");
};

var uploadPhotoCallback = function(result){

    if(result.success){
        openAlbum(albumInfo);
        console.log(result.msg);
    }else{
        console.log(result.msg);
    }

};

/********************************************  相册模块 end ********************************************/


/********************************************  用户管理模块 begin ********************************************/

var showUserMangerForm = function(formId){

    $("#person_setting").hide();
    $("#member_manager").hide();
    $("#message_form").hide();

    $("#" + formId).show();

}

/********************************************  个人设置模块  ********************************************/

var updateUserUrl = "/" + projectName + "/user/update.do";

function updateUser(formId){
    var params = formToJsonstr(formId);

    params.account = userInfo.account;;

    if(!(isNotNull(params.nickName)||isNotNull(params.birthday)||isNotNull(params.password)||isNotNull(params.repassword))){
        return ;
    }

    if(isNotNull(params.password)||isNotNull(params.repassword)){
        if(params.password != params.repassword){
            alert("两次密码不一致");
            return;
        }
    }


    ajaxFunction(updateUserUrl,params,function(result){

        if(result.success){
            console.log("修改成功")
        }else{
            alert(result.msg);
        }

    });
}

function imagesAjax(src) {
    var data = {};
    data.img = src;
    data.account = userInfo.account;
    $.ajax({
        url: "/"+projectName+"/fileUpload/uploadHeadImage.do",
        data: data,
        type: "POST",
        dataType: 'json',
        success: function(re) {
            if(re.status == '1') {
                $('.user_pic img').attr('src',src );
            }
        }
    });
}


/********************************************  成员管理模块  ********************************************/

var insertFamilyUserUrl = "/" + projectName + "/familyGroup/insertFamilyUser.do";
var selectFamilyUserUrl = "/" + projectName + "/familyGroup/selectFamilyUserByGroupId.do";
var deleteFamilyUserUrl = "/" + projectName + "/familyGroup/deleteFamilyUserUrl.do";


var addMember = function(formId){

    var params = formToJsonstr(formId);

    if(!isNotNull(params.userId)){
        return ;
    }

    params.familyId = userInfo.groupId;

    ajaxFunction(insertFamilyUserUrl,params,function(result){

        if(result.success){
            console.log(result.msg);
        }else{
            alert(result.msg);
        }

    });

};

var getMemeberList = function(){

    var params = {familyId:userInfo.groupId};

    ajaxFunction(selectFamilyUserUrl,params,function(result){

        if(result.success){
            console.log(result.data);
            var data = result.data;
            var str = "";
            for(var i = 0 ; i < data.length;i++){
                str += "<tr><td>"+data[i].userId+"</td><td>" + data[i].nickName + "</td><td>"+ ((data[i].userId==userInfo.account)?"":"<a href='#' onclick='deleteFamilyUser("+JSON.stringify(data[i])+")' >删除</a>") +"</td></tr>";
            }
            $("#member_list tbody").html(str);

        }else{
            alert(result.msg);
        }

    },false);

};

var deleteFamilyUser = function(params){

    ajaxFunction(deleteFamilyUserUrl,params,function(result){

        if(result.success){
            console.log("删除成功");
        }else{
            console.log("删除失败");
        }

    });

};

/********************************************  用户管理模块 end  ********************************************/