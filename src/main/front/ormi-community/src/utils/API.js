import axios from 'axios';
import { GenerateLiElUUID } from './keygenerator';
const URL = "http://localhost:8080"


/**
 * 
 * @returns { {nickName: String, userId: String, commentDate: "YYYY-MM-DD", comment: String, likeCount: number, likeAble: boolean}[] } API returns
 */
export function fetchDocComments(){
    return [ {nickName: "thlee", userId: "130dfqi", commentDate: "2024-03-01", comment: "안녕하세요 저는 뚱이에요", likeCount: 3, likeAble: true},
        {nickName: "afl", userId: "1123123i", commentDate: "2024-03-01", comment: "안녕하세요 저는 뚱이에요2", likeCount: 2, likeAble: true},
    ];
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
   
    // return [
    //     {boardId: GenerateLiElUUID(), boardName: "이스트소프트1", career:"IT", comName: ""},
    //     {boardId: GenerateLiElUUID(), boardName: "이스트소프트2", career:"IT", comName: ""},
    //     {boardId: GenerateLiElUUID(), boardName: "이스트소프트3", career:"IT", comName: ""}
    //  ];
}

/**
 * @breif 업종 목록을 조회한다. 
 * @returns { { industryId: String, industryName: String, industryDescription: String }[] }
 */
export async function fetchIndustryList(){
    return axios.get(URL + "/industry")
    .then(async (response) => response.data);
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