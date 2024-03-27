
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