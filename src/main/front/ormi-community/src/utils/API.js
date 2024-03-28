
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
    return [
        {nickName: "thlee", userId: "130dfqi", commentDate: "2024-03-01", comment: "안녕하세요 저는 뚱이에요", likeCount: 3, likeAble: true},
        {nickName: "afl", userId: "1123123i", commentDate: "2024-03-01", comment: "안녕하세요 저는 뚱이에요2", likeCount: 2, likeAble: true},
    ];
}