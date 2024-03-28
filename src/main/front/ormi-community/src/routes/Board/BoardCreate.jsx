import LabelSection from "@/components/Layout/LabelSection";
import LogoHeader from "@/components/Layout/LogoHeader";
import { Select, SelectTrigger, SelectContent, SelectLabel, SelectItem, SelectGroup, SelectValue } from "@/components/ui/select";
import { Button } from "@/components/ui/button";
import { fetchBoardList } from "@/utils/API";
import { useEffect, useState } from "react";
import { Input } from "@/components/ui/input";

export default function BoardCreate(){
    const [boardList, setBoardList] = useState([]);
    const [interestCareer, setInterestCareers] = useState([]);
    useEffect(() =>{
        const newBoardList = fetchBoardList();
        setBoardList([...newBoardList]);
    },[]);

    return (
        <main className="flex flex-col bg-gray-100 min-h-screen items-center justify-center">
            <section className="w-900 flex flex-col items-center bg-white p-20 rounded-lg shadow-2xl">
                <LogoHeader></LogoHeader>
                <LabelSection isRequire asChild label="게시판명" className="mt-4">
                    <Input type="text" id="tBox_doc_title" placeholder="게시글 제목을 입력해주세요."></Input>
                </LabelSection>
                <LabelSection isRequire asChild label="업종" className="mt-4">
                    <Select onValueChange={setInterestCareers}>
                        <SelectTrigger>
                            <SelectValue placeholder="업종"></SelectValue>
                        </SelectTrigger>
                        <SelectContent>
                            <SelectItem value="IT">IT</SelectItem>
                            <SelectItem value="조선">조선</SelectItem>
                            <SelectItem value="의료">의료</SelectItem>
                        </SelectContent>
                    </Select>
                </LabelSection>
                <div className="mt-4 w-full flex justify-end items-center gap-4">
                    <Button asChild className="cursor-pointer bg-white hover:bg-gray-400 hover:text-white hover:border-white border border-gray-400 text-black font-bold">
                        <div>취소</div>
                    </Button>
                    <Button asChild className="cursor-pointer bg-violet-800 hover:bg-violet-600 font-bold">
                        <div onClick={() =>{alert("awifm");}}>저장하기</div>
                    </Button>
                </div>
            </section>
        </main>
    );
}