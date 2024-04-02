import { acceptBoardPublicing } from "@/utils/API";
import { Button } from "../ui/button";

export default function BoardAccept({boardName, career, comName, boardId}){
    return (
        <div className="p-4 flex justify-between items-end border rounded-sm">
            <div className="grid gap-2">
                <span>게시판명: {boardName}</span>
                <span>업종: {career}</span>
                <span>회사명: {comName}</span>
            </div>
            <Button className="bg-violet-800 mt-4 hover:bg-violet-600">
                <div onClick={() =>{acceptBoardPublicing(boardId)}}>
                    승인
                </div>
            </Button>
        </div>
    );
}