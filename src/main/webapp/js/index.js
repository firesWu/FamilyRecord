/**
 * Created by yuan on 2018/3/24.
 */

$(function(){
    verLogin();
    judgeFamilyGroup();
    openIndexPage();
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

    $('#reply_article0')
        .focus(function () {
            $(this).css('background', '#fff');
        })
        .blur(function () {
            $(this).css('background', '#ccc');
        });
});

/********************************************  homePage begin ********************************************/
var getHomePageInfoUrl = "/" + projectName + "/homePage/getHomePageInfo.do";

var openIndexPage = function(){

    getHomePageInfo();
    openForm("index_page","model1");

};

var getHomePageInfo = function () {

    var params = {topNum:10, groupId:userInfo.groupId};

    ajaxFunction(getHomePageInfoUrl,params,function(result){
        if(result.success){

            var articleList = result.data.articleList;
            var photoList = result.data.photoList;
            var videoList = result.data.videoList;
            var birthdayRemindingList = result.data.birthdayRemindingList;

            //photoList
            var photoListStr = "";
            for(var i in photoList){
                photoListStr += "<div><img src='" + "/" + projectName + "/" + photoList[i].filePath + " ' alt='' onclick='photoPreview(\""+ photoList[i].filePath+"\")' /></div>"
            }
            $("#index_page_photo_list").html(photoListStr);
            //videoList
            var videoListStr = "";
            for(var i in videoList){
                videoListStr += "<div><video class='img-thumbnail' src='" + "/" + projectName + "/" + videoList[i].filePath + " ' alt='' onclick='videoPreview(\""+ videoList[i].filePath+"\")' /></div>"
            }
            $("#index_page_video_list").html(videoListStr);

            //articleList
            var articleListStr = "";
            for(var i in articleList){
                articleListStr += "<li>" + articleList[i].title + "</li>"
            }
            $("#index_page_article_list").html(articleListStr);

            //birthdayRemindingList
            var birthdayRemindingListStr = "";
            if(birthdayRemindingList.length != 0){
                birthdayRemindingListStr += "<div class='name'>哟哟哟！<span style='font-size: 20px; color: red;'>" + birthdayRemindingList[0].nickName + "</span><br>将于 <span style='color: blue'>" + birthdayRemindingList[0].birthday + "</span> 生日</div>";
            }else{
                birthdayRemindingListStr += "<div class='name'>哟哟哟！最近没人生日</div>";

            }

            $("#birthday_reminding_list").append(birthdayRemindingListStr);

        }
    })

};

/********************************************  主题帖 begin ********************************************/

var createArticleUrl = "/" + projectName + "/article/createArticle.do";
var selectArticleUrl = "/" + projectName + "/article/selectArticle.do";
var replyArticleUrl = "/" + projectName + "/article/replyArticle.do";
var selectCommentUrl = "/" + projectName + "/article/selectComments.do";
var deleteArticleUrl = "/" + projectName + "/article/deleteArticle.do";
var articleInfo;

var openArticlePart = function(){

    selectArticle(1);
    openForm("article_manage","model1");

};

var editArticle = function(){

    $("#createForm_id").html(articleInfo["id"]);
    $("#createForm_title").val(articleInfo["title"]);
    $("#article_editor").html(articleInfo["content"]);

    openForm('create_article_form','model_article');
};

var openCreateForm = function(){
    $('#article_editor').html('');
    $("#createForm_id").html('');
    $("#createForm_title").val('');
    openForm('create_article_form','model_article')
};

var createArticle = function(formId,articleId){

    var params = formToJsonstr(formId);
    params.rId = userInfo.groupId;
    params.creator = userInfo.account;
    params.content = $("#"+articleId).html();

    ajaxFunction(createArticleUrl,params,function(result){

        if(result.success){
            selectArticle(1);
            openForm('article_list_show','model_article');
            //console.log(result.msg);
            $('.post-title').val('');
            $('#article_editor').html('');
            $("#createForm_id").html('');
            $("#createForm_title").val('');

        }else{
            layerMsg(result.msg);
        }

    });

};

var selectArticle = function(pageNum){

    var params = {};
    params.pageNum = pageNum || 1;
    params.pageSize = 100;
    params.rId = userInfo.groupId;
    params.title = $("#title_search").val();

    ajaxFunction(selectArticleUrl,params,function(result){
        if(result.success){
            var data = result.data.list;
            var str = "";
            for(var i = 0; i < data.length;i++){
                str += "<li class='list-group-item' onclick=\'openArticle(" + JSON.stringify(data[i]) + ")\'><span class='title'>" + data[i].title + "</span><span class='time'>" + data[i].createTime.slice(0, 10) + "</span></li>";
            }
            $("#article_list").html(str);
        }
    });

};

var deleteArticle = function(id){

    var params = {id:id};

    ajaxFunction(deleteArticleUrl,params,function(result){

        if(result.success){
            openForm('article_list_show','model_article');
            selectArticle(1);
            layerMsg("删除成功");
        }else{

        }

    });

};

var deleteArticleConfirm = function(){
    layer.confirm('是否删除？', {
        btn: ['删除','取消'] //按钮
    }, function(){
        deleteArticle(articleInfo.id);
    });
    event.stopPropagation();
};


var openArticle = function(result){
    articleInfo = result;
    for(var key in result){
        $("#article_"+key).html(result[key]);
    }

    $("#article_createTime").html(result["createTime"].slice(0, 19));

    selectComments(1);
    openForm('article_show','model_article');
};


var articleCommentParentId;

var replyReady = function(result,showReplyFormId){
    articleCommentParentId = result.replyId;

    $("div[name = replyFormDiv]").hide();
    $("#"+showReplyFormId).show();
    $('.secondary-reply')
        .focus()
        .blur(function () {
            if ($(this).val().length === 0) {
                $('.reply-input').hide();
            }
    });
};

var replyOtherUser = function(floor,replyContentId){
    replyArticle(floor,articleCommentParentId, replyContentId);
};

var replyArticle = function(floor,parentId,replyContentId){
    var params = {};
    params.comment = $("#"+replyContentId).val();

    if(!isNotNull(params.comment)){
        layerMsg("评论不允许为空");
        return ;
    }

    params.parentId = parentId || articleInfo.creator;
    params.floor = floor;
    params.replyId = userInfo.account;
    params.articleId = articleInfo.id;


    ajaxFunction(replyArticleUrl,params,function(result){
        if(result.success){
            selectComments(1);
        }
    });

};

var selectComments = function(pageNum){

    var params = {};
    params.articleId = articleInfo.id;
    params.pageNum = pageNum;
    params.pageSize = 100;
    ajaxFunction(selectCommentUrl,params,function(result){

        if(result.success){
            var data = result.data.list;
            var str = "";
            for(var i = 0;i<data.length;i++){
                var floor = data[i].floor;
                str += "<p class='first-comment'><button class='pull-right' onclick='replyReady("+ JSON.stringify(data[i]) +",\"articleReplyForm"+ (data[i].floor) +"\")'>回复</button><span  class='user-name'>" + data[i].replyNickName +"</span>：" + data[i].comment + "</p> <ul class='center-block'>";
                while(1){
                    if(i+1>= data.length || data[i+1].floor != floor) break;
                    i++;
                    str += "<li class='second-comment'><span class='user-name'>"+ data[i].replyNickName + "</span> 回复 <span class='user-name'>" + data[i].parentNickName + "</span>：" + data[i].comment +" <a href='##' onclick='replyReady("+ JSON.stringify(data[i]) +",\"articleReplyForm"+ (data[i].floor) +"\")'>回复</a> </li>";
                }
                str +="<div class='reply-input' name='replyFormDiv'style='display:none;' id='articleReplyForm"+ (data[i].floor) +"'><input class='secondary-reply' type='text' id='reply_article"+ data[i].floor +"' placeholder='回复 "+ data[i].replyNickName +": ' /><button class='btn-second-comment' onclick='replyOtherUser(\" "+ data[i].floor +" \",\"reply_article"+ data[i].floor +"\")'>回复</button></div></td></tr>";
            }

            $("#comment_list").html(str);
            $('#reply_article0').val('');
        }

    })

};

/********************************************  主题帖 end ********************************************/

/********************************************  相册模块 begin ********************************************/

var insertAlbumUrl = "/" + projectName + "/album/insert.do";
var selectAlbumInfoUrl = "/" + projectName + "/album/selectAlbumInfo.do";
var selectPhotoOrVideoUrl = "/" + projectName + "/commonFile/selectPhotoOrVideo.do";
var uploadPhotoOrVideoUrl = "/" + projectName + "/fileUpload/upload.do";
var deleteAlbumUrl = "/" + projectName + "/album/deleteAlbum.do";
var deletePhotoOrVideoUrl = "/" + projectName + "/commonFile/deleteCommonFile.do";
var albumInfo = {id:1};

//相册模块
var openPhotoPart = function(){

    openForm("album_manager","model1");
    selectAlbumInfo('album_list')

};

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
            layerMsg(result.msg);
            selectAlbumInfo('album_list');
            $("#create_album").modal("hide");
        }else{
            layerMsg(result.msg);
        }

    });

};

var selectAlbumInfo = function(divId){

    var params = {};
    params.groupId = userInfo.groupId;

    ajaxFunction(selectAlbumInfoUrl,params,function(result){

        if(result.success){
            //console.log(result.data);
            var data = result.data;
            var str = "";

            for(var i = 0 ; i < data.length ;i++){
                str += "<div class='pull-left album del'> <a href='#' onclick='openAlbum(" + JSON.stringify(data[i]) +")'> <img src='" + (data[i].filePath == undefined?default_album_image:"/"+projectName+"/"+data[i].filePath) +"' class='family_record_photo img-thumbnail' /> <i class='del-icon hidden' onclick='layerConfirm(event,deleteAlbum," + data[i].id + ")'></i><span>" + data[i].albumName + "</span> </a> </div>";
            }

            $("#"+ divId).html(str);

            $('.album').hover(function() {
                $(this).find('.del-icon').removeClass('hidden');
            }, function() {
                $(this).find('.del-icon').addClass('hidden');
            })

        }else{
            layerMsg(result.msg);
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
                str += "<div class='pull-left album' ><img  onclick='photoPreview(\""+ data[i].filePath+"\")' src='"+"/"+projectName+"/" + (data[i].filePath) +" ' class='family_record_photo img-thumbnail' /><i class='del-icon hidden' onclick='layerConfirmCommonFile(event,deletePhotoOrVideo,"+ data[i].id +",1)'></i></div>";
            }

            $("#photo_list").html(str);
            $('.album').hover(function() {
                $(this).find('.del-icon').removeClass('hidden');
            }, function() {
                $(this).find('.del-icon').addClass('hidden');
            })

            openForm("photo_page","model_album");
            initFileInput('upload_photo_file',"#",['jpg','png']);
        }else{
            layerMsg(result.msg);
        }

    });

};

var deleteAlbum = function(id){

    var params = {id:id};

    ajaxFunction(deleteAlbumUrl,params,function(result){
        if(result.success){
            layerMsg("删除成功");
            selectAlbumInfo('album_list');
        }else{

        }
    });
    return false;
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
        layerMsg(result.msg);
        $("#upload_photo_form").html('<input id="upload_photo_file" name="photo" multiple type="file" class="file-loading" >');
    }else{
        layerMsg(result.msg);
    }

};

//视频模块
var openVideoPart = function(){

    openForm("video_manager","model1");
    selectVideoList('video_list');

};

var selectVideoList = function(){

    var params = {};
    params.rId = userInfo.groupId;
    params.type=2;

    ajaxFunction(selectPhotoOrVideoUrl,params,function(result){

        if(result.success){
            var data = result.data;
            var str = "";

            for(var i = 0 ; i < data.length ;i++){
                str += "<div class='pull-left album del' ><video  onclick='videoPreview(\""+ data[i].filePath+"\")' src='"+"/"+projectName+"/" + (data[i].filePath) +"' controls='controls' class='family_record_photo img-thumbnail' /><i class='del-icon hidden' onclick='layerConfirmCommonFile(event,deletePhotoOrVideo,"+ data[i].id +",2)'></i></div>";
            }

            $("#video_list").html(str);
            initFileInput('upload_video_file',"#",['avi','wmv','mpeg','mp4','mov','mkv','flv','f4v','m4v','rmvb','rm','3gp','dat','ts','mts','vob']);

            $('.album').hover(function() {
                $(this).find('.del-icon').removeClass('hidden');
            }, function() {
                $(this).find('.del-icon').addClass('hidden');
            })

        }else{
            layerMsg(result.msg);
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
        layerMsg(result.msg);
        $("#upload_video_form").html('<input id="upload_video_file" name="photo" multiple type="file" class="file-loading" >');
    }else{
        layerMsg(result.msg);
    }

};

var deletePhotoOrVideo = function(id,type){

    var params = {id:id};

    ajaxFunction(deletePhotoOrVideoUrl,params,function(result){
        if(result.success){
            layerMsg("删除成功");

            if(type == 1){
                openAlbum(albumInfo);
            }else if(type == 2){
                selectVideoList();
            }

        }else{

        }
    });

};

var layerConfirmCommonFile = function(event,method,id,type){
    layer.confirm('是否删除？', {
        btn: ['删除','取消'] //按钮
    }, function(){
        if(typeof method == "function" ){
            method(id,type);
        }
    });
    event.stopPropagation();
};

/********************************************  相册模块 end ********************************************/


/********************************************  用户管理模块 begin ********************************************/

var openSettingPart = function(){

    openForm("user_setting","model1");

};

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
            layerMsg("两次密码不一致");
            return;
        }
    }


    ajaxFunction(updateUserUrl,params,function(result){

        if(result.success){
            layerMsg("修改成功")
        }else{
            layerMsg(result.msg);
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
            if(re.success) {
                //$('.user_pic img').attr('src',src );
                $('#head_image_show').attr('src',src );
            }
        }
    });
}


/********************************************  成员管理模块  ********************************************/

var insertFamilyUserUrl = "/" + projectName + "/familyGroup/insertFamilyUser.do";
var selectFamilyUserUrl = "/" + projectName + "/familyGroup/selectFamilyUserByGroupId.do";
var deleteFamilyUserUrl = "/" + projectName + "/familyGroup/deleteFamilyUserUrl.do";

var showMemberPage = function(){
    getMemeberList();
    openForm('member_manager','model_user_setting')
};

var addMember = function(formId){

    var params = formToJsonstr(formId);

    if(!isNotNull(params.userId)){
        return ;
    }

    params.familyId = userInfo.groupId;

    ajaxFunction(insertFamilyUserUrl,params,function(result){

        if(result.success){
            layerMsg(result.msg);
        }else{
            layerMsg(result.msg);
        }

    });

};

var getMemeberList = function(){

    var params = {familyId:userInfo.groupId};

    ajaxFunction(selectFamilyUserUrl,params,function(result){

        if(result.success){
            //console.log(result.data);
            var data = result.data;
            var str = "";
            for(var i = 0 ; i < data.length;i++){
                str += "<tr><td>"+data[i].userId+"</td><td>" + data[i].nickName + "</td><td>"+ ((data[i].userId==userInfo.account)?"":"<a href='#' onclick='deleteFamilyUser("+JSON.stringify(data[i])+")' >删除</a>") +"</td></tr>";
            }
            $("#member_list tbody").html(str);

        }else{
            layerMsg(result.msg);
        }

    },false);

};

var deleteFamilyUser = function(params){

    ajaxFunction(deleteFamilyUserUrl,params,function(result){

        if(result.success){
            layerMsg("删除成功");
        }else{
            layerMsg("删除失败");
        }

    });

};

/********************************************  消息管理  ********************************************/

var getMessageListUrl = "/" + projectName + "/article/getUnreadMessage.do";

var openMessageList = function(){
    getMessageList();
    openForm('message_form','model_user_setting')
};

var getMessageList = function(){

     var params = {account:userInfo.account};

    ajaxFunction(getMessageListUrl,params,function(result){
        if(result.success){

            var data = result.data;
            var str = "";
            for(var i = 0;i<data.length;i++){
                str += "<li style='color:#948585'>" + data[i].replyNickName +"在《" + data[i].articleTitle + "》中回复您：" + data[i].comment +"</li>";
            }

            $("#message_list").html(str);
        }
    });

};


/********************************************  用户管理模块 end  ********************************************/