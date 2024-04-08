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

export function commentWriteReqParam(commentPassword, commentCreatorIp, commentContent, anonyNickname){
   const reqParam = {};

   reqParam.commentPassword = commentPassword;
   reqParam.commentCreatorIp = commentCreatorIp;
   reqParam.commentContent = commentContent;
   reqParam.anonyNickname = anonyNickname || null;

   return reqParam;
}

export function editUserInfoReqParam(nickname, passwordQuestionId, findPasswordAnswer, industriesId){
   const reqParam = {};

   reqParam.nickname = nickname;
   reqParam.passwordQuestionId = passwordQuestionId; reqParam.findPasswordAnswer = findPasswordAnswer;
   reqParam.industriesId = industriesId;

   return reqParam;
}

export function appendBoardReqParam(boardName, industryId, comId, requesterEmail){
   const reqParam = {};

   reqParam.boardName = boardName;
   reqParam.industryId = industryId;
   reqParam.comId = comId;
   reqParam.requesterEmail = requesterEmail;
   
   return reqParam;
}