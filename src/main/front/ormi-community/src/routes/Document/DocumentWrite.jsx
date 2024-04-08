import LabelSection from "@/components/Layout/LabelSection";
import LogoHeader from "@/components/Layout/LogoHeader";
import { Select, SelectTrigger, SelectContent, SelectLabel, SelectItem, SelectGroup, SelectValue } from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import { fetchBoardList, writeDocument } from "@/utils/API";
import { useEffect, useState } from "react";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { documentWriteReqParam } from "@/utils/Parameter";
import { useNavigate } from "react-router-dom";

/**
 * @brief 게시글 작성, 수정이 일어나는 컴포넌트. 수정일 경우, fetch하여 데이터를 가지고 온 다음 여기에 붙여넣을임
 * @returns 
 */
export default function DocumentWrite(){
    const nevigate = useNavigate();
    const [boardList, setBoardList] = useState([]);
    const [boardId, setBoardId] = useState("");
    const [tBox_title, setTitleVal] = useState("");
    const [tBox_content, setContentVal] = useState("");

    useEffect(() =>{
        fetchBoardList()
        .then(data => {setBoardList(data);});
    },[]);

    const doWrite = () =>{
        const reqParam = documentWriteReqParam(boardId, tBox_title, tBox_content);

        writeDocument(reqParam)
        .then(data => {
            alert("정상적으로 작성되었습니다!");
            nevigate("/");
        });
    };
    return (
        <main className="flex flex-col bg-gray-100 min-h-screen items-center justify-center">
                <section className="w-900 flex flex-col items-center bg-white p-20 rounded-lg shadow-2xl">
                   <LogoHeader></LogoHeader>
                    <LabelSection isRequire asChild label="게시판 선택" className="mt-4">
                        <Select onValueChange={setBoardId}>
                            <SelectTrigger>
                                <SelectValue placeholder="선택"></SelectValue>
                            </SelectTrigger>
                            <SelectContent>
                                {
                                    boardList.map(board =>{
                                        return <SelectItem key={board.boardId} value={board.boardId}>{board.boardName}</SelectItem>;
                                    })
                                }
                            </SelectContent>
                        </Select>
                    </LabelSection>
                    <LabelSection isRequire asChild label="제목" className="mt-4">
                        <Input type="text" onChange={(ev) =>{setTitleVal(ev.target.value)}} id="tBox_doc_title" placeholder="게시글 제목을 입력해주세요."></Input>
                    </LabelSection>
                    <LabelSection isRequire asChild label="게시글 내용" className="mt-4">
                        <Textarea onChange={(ev) => {setContentVal(ev.target.value)}} placeholder="내용을 입력해주세요." className="resize-none w-full h-300"></Textarea>
                    </LabelSection>
                    <div className="mt-4 w-full flex justify-end items-center gap-4">
                        <Button asChild className="cursor-pointer bg-white hover:bg-gray-400 hover:text-white hover:border-white border border-gray-400 text-black font-bold">
                            <div onClick={() =>{nevigate(-1)}}>취소</div>
                        </Button>
                        <Button asChild className="cursor-pointer bg-violet-800 hover:bg-violet-600 font-bold">
                            <div onClick={doWrite}>저장하기</div>
                        </Button>
                    </div>
                </section>
            </main>
    );
}