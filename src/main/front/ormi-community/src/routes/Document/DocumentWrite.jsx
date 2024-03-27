import CompleteIcon from "@/components/Icon/CompleteIcon";
import LabelSection from "@/components/Layout/LabelSection";
import LogoHeader from "@/components/Layout/LogoHeader";
import { Select, SelectTrigger, SelectContent, SelectLabel, SelectItem, SelectGroup, SelectValue } from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import { fetchBoardList } from "@/utils/API";
import { useEffect, useState } from "react";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { GenerateLiElUUID } from "@/utils/keygenerator";

export default function DocumentWrite(){
    const [boardList, setBoardList] = useState([]);
    useEffect(() =>{
        const newBoardList = fetchBoardList();
        setBoardList([...newBoardList]);
    },[]);
    return (
        <main className="flex flex-col bg-gray-100 min-h-screen items-center justify-center">
                <section className="w-900 flex flex-col items-center bg-white p-20 rounded-lg shadow-2xl">
                   <LogoHeader></LogoHeader>
                    <LabelSection isRequire asChild label="게시판 선택" className="mt-4">
                        <Select>
                            <SelectTrigger>
                                <SelectValue placeholder="선택"></SelectValue>
                            </SelectTrigger>
                            <SelectContent>
                                {
                                    boardList.map(board =>{
                                        return <SelectItem key={GenerateLiElUUID()} value={board.value}>{board.title}</SelectItem>;
                                    })
                                }
                            </SelectContent>
                        </Select>
                    </LabelSection>
                    <LabelSection isRequire asChild label="제목" className="mt-4">
                        <Input type="text" id="tBox_doc_title" placeholder="게시글 제목을 입력해주세요."></Input>
                    </LabelSection>
                    <LabelSection isRequire asChild label="게시글 내용" className="mt-4">
                        <Textarea placeholder="내용을 입력해주세요." className="resize-none w-full h-300"></Textarea>
                    </LabelSection>
                    <div className="mt-4 w-full flex justify-end items-center gap-4">
                        <Button asChild className="cursor-pointer bg-white hover:bg-gray-400 hover:text-white hover:border-white border border-gray-400 text-black font-bold">
                            <div>취소</div>
                        </Button>
                        <Button asChild className="cursor-pointer bg-violet-800 hover:bg-violet-600 font-bold">
                            <div>저장하기</div>
                        </Button>
                    </div>
                </section>
            </main>
    );
}