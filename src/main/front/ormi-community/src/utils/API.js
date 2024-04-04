import axios from 'axios';
const URL = "http://localhost:8080"


/**
 * 
 * @returns { {commentId: String, nickname: String, commentCreatorIp: String || null , email: String, commentDate: "YYYY-MM-DD HH:MM:SS.XXX", commentContent: String, likeCount: number}[] } API returns
 */
export async function fetchDocComments(docId){
    return axios.get(URL + `/comment/list/${docId}`)
    .then((response) => response.data);
}

/**
 * @breif 게시판id와 pageNumber로 게시글을 불러온다.
 * @param {String} boardId 
 * @param {number} docPageNumber 
 * @returns { {docId: String, docTitle: String, docContent : String, docCreateDate: YYYY-MM-DD HH:MM:SS.XXX, nickname: String || null, email: String, memberRoleName: USER, boardId: String, boardName: String, likeCount: number, viewCount: number} }
 */
export async function fetchDocumentList(boardId, docPageNumber){
    axios.get(URL + `/document/list/${boardId}?page=${docPageNumber || 0}`)
    .then((response) => response.data);
}

/**
 * @brief 현재 로그인한 유저의 수정 가능 정보를 불러옵니다.
 * @returns { {nickName: String, phoneNumber: String passwordHintQuestion: String, passwordHintAnswer: String, career1: String, career2: String, career3: String} }
 */
export function fetchEditableUserInfo(){
    return {
        nickName : 'th',
        phoneNumber: '+821037740456',
        passwordHintQuestion: '1',
        passwordHintAnswer: "answer",
        career1: 'IT',
        career2: '조선',
        career3: '의료'
    };
}

/**
 * @brief 게시판 생성 허가가 완료된 목록을 조회하는 API입니다.
 * @returns  { {boardId: UUID, boardName: String, industyrName: String, comName: String}[] }
 */
export async function fetchBoardList(){
    return axios.get(URL + "/board/true")
    .then(response => response.data);
}

/**
 * @brief 아직 게시판 생성 허가가 되지 않은 게시판 목록을 불러온다.
 * @returns  { {boardId: UUID, boardName: String, industyrName: String, comName: String}[] }
 */
export async function fetchNotApproveBoardList(){
    return axios.get(URL + "/board/false")
    .then((response) => response.data);
}

/**
 * @breif 업종 목록을 조회한다. 
 * @returns { { industryId: String, industryName: String, industryDescription: String }[] }
 */
export async function fetchIndustryList(){
    return axios.get(URL + "/industry")
    .then((response) => response.data);
}

/**
 * @brief 비밀번호 찾기 질문을 서버로부터 받아온다.
 * @returns 
 */
export async function fetchPasswordQuestion(){
    return axios.get(URL + "/passwordquestion")
    .then(async (response) => response.data);
}

export function acceptBoardPublicing(boardId, approve = true){
    //Accept API 호출
    console.log('API 호출완료!');
}

export function appendIndustry(industryName, industryComment){
    // 업종 추가 API
    console.log("API 호출 완료");
}

export function appendPasswordQuestion(passwordQuestion){
    // 비밀번호 질문 추가 API
    console.log("비밀번호 질문 추가 API 호출 완료");
}