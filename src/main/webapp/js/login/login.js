/**
 * Created by yuan on 2018/3/24.
 */

var loginUrl = "/" + projectName + "/login/loginin.do";
var registerUrl= "/" + projectName + "/login/register.do";


var loginUser = function(formId){

    var params = formToJsonstr(formId);

    ajaxFunction(loginUrl,params,function(result){

        if(result.success){
            window.location = "/" + newProject + "/index.html";
        }else{
            alert(result.msg);
        }

    });
};


var registerUser = function(formId){

    var params = formToJsonstr(formId);

    if(!isNotNull(params.account) || params.account.length <6){
        return ;
    }

    ajaxFunction(registerUrl,params,function(result){

        if(result.success){
            window.location = "/" + newProject + "/index.html";
        }else{
            alert(result.msg);
        }

    });

};

var changeForm = function(form1,form2){
    $("#"+form1).hide();
    $("#"+form2).show();
};