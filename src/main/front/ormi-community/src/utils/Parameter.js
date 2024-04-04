/**
 * 
 * @param { String } name 
 * @param { String } nickname 
 * @param { String } email 
 * @param { String } password 
 * @param { String } gender 
 * @param { String } phone 
 * @param { String } passwordQuestionId 
 * @param { String } findPasswordAnswer 
 * @param { String[]  } industries 
 */
export function signupReqParam(name, nickname, email, password, gender, phone, passwordQuestionId, findPasswordAnswer, industries){
   const reqParam = {};
   reqParam.name = name || "";
   reqParam.nickname = nickname || "";
   reqParam.email = email || "";
   reqParam.password = password || "";
   reqParam.gender = gender || "";
   reqParam.phone = phone || "";
   reqParam.passwordQuestionId = passwordQuestionId || "";
   reqParam.findPasswordAnswer = findPasswordAnswer || "";
   reqParam.industries = industries || [];
   
   return reqParam;
}

export function loginReqParam(email, password){
   const reqParam = {};

   reqParam.email = email || "";
   reqParam.password = password || "";

   return reqParam
}

export function documentWriteReqParam(boardId, docTitle, docContent){
   const reqParam = {};

   reqParam.boardId = boardId || "";
   reqParam.docTitle = docTitle || "";
   reqParam.docContent = docContent || "";

   return reqParam;
}

export function commentWriteReqParam(commentPassword, commentCreatorIp, commentContent){
   const reqParam = {};

   reqParam.commentPassword = commentPassword;
   reqParam.commentCreatorIp = commentCreatorIp;
   reqParam.commentContent = commentContent;

   return reqParam;
}