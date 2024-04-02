import { useEffect, useState } from "react";
import BoardAccept from "@/components/Board/BoardAccept";
import { fetchNotApproveBoardList } from "@/utils/API";

// 게시판 관리 화면
// api 호출하여 승인 여부를 리스트로 받아오고, 이를 화면에 출력한다.
export default function AdminBoard(){
    const [boardList, setBoardList] = useState([])
    useEffect(() =>{
        setBoardList(fetchNotApproveBoardList());
    },[]);

    return (
        <ul className="grid gap-4 p-4">
            {
                boardList.map((board) => <BoardAccept key={board.boardId} {...board}/>)
            }
        </ul>
    );
}