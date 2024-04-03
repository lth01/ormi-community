import axios from 'axios';
import { GenerateLiElUUID } from './keygenerator';
const URL = "localhost:8080"

/**
 * @brief 게시판 목록을 조회하는 API입니다.
 * @returns 게시판 목록을 json 배열로 반환
 */
export function fetchBoardList(){
    return [
        {value: '1', title:'너의 이름은?'},
        {value: '2', title:'너의 직업은?'},
        {value: '3', title:'행복한가요?'},
    ];
}

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
 * @brief 아직 게시판 생성 허가가 되지 않은 게시판 목록을 불러온다.
 * @returns 
 */
export function fetchNotApproveBoardList(){
    return [
        {boardId: GenerateLiElUUID(), boardName: "이스트소프트1", career:"IT", comName: ""},
        {boardId: GenerateLiElUUID(), boardName: "이스트소프트2", career:"IT", comName: ""},
        {boardId: GenerateLiElUUID(), boardName: "이스트소프트3", career:"IT", comName: ""}
     ];
}

export function fetchIndustryList(){
    return [
        {industryId: GenerateLiElUUID(), industryName: 'IT', industyComment: ""},
        {industryId: GenerateLiElUUID(), industryName: '조선', industyComment: ""},
        {industryId: GenerateLiElUUID(), industryName: '철강', industyComment: ""},
    ];
}

/**
 * @brief 비밀번호 찾기 질문을 서버로부터 받아온다.
 * @returns 
 */
export function fetchPasswordQuestion(){
    return [
        {passwordQuestionId: GenerateLiElUUID(), passwordQuestion: '1'},
        {passwordQuestionId: GenerateLiElUUID(), passwordQuestion: '2'},
        {passwordQuestionId: GenerateLiElUUID(), passwordQuestion: '3'},
    ];
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