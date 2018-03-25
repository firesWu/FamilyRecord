/**
 * Created by yuan on 2018/3/25.
 */

var familyGroupCreateUrl = "/" + projectName + "/familyGroup/insert.do";

$(function(){
   verLogin();
});

var createFamilyGroup = function(formId){

    var params = formToJsonstr(formId);
    params.creator = userInfo.account;

    ajaxFunction(familyGroupCreateUrl,params,function(result){

        if(result.success){

        }

    });

};
