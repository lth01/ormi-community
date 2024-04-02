import LabelSection from "@/components/Layout/LabelSection";
import { Input } from "@/components/ui/input";
import { appendIndustry, fetchIndustryList } from "@/utils/API";
import { useState, useEffect } from "react";
import IndustryArticle from "@/components/Industry/IndustryArticle";
import { Button } from "@/components/ui/button";

export default function AdminIndustry(){
    // 업종을 불러오는 code
    const [industryList, setIndustries] = useState([]);
    const [industryName, setIndustryName] = useState("");
    const [industryComment, setIndustryComment] = useState("");
    useEffect(() =>{
        setIndustries(fetchIndustryList());
    },[]);


    return (
        <ul className="grid gap-2">
            {/* 화면 상단에는 업종 추가 section */}
            <article className="p-4 flex justify-between items-end border rounded-md gap-2">
                <div className="grid gap-2 w-full">
                    <LabelSection asChild label="업종명" className="font-bold">
                        <Input type="text" value={industryName} onChange={(val) => setIndustryName(val)} className="w-full"></Input>
                    </LabelSection>
                    <LabelSection asChild label="업종설명" className="font-bold">
                        <Input type="text" value={industryComment} onChange={(val) => setIndustryComment(val)} className="w-full"></Input>
                    </LabelSection>
                </div>
                <Button className="bg-violet-800 mt-4 hover:bg-violet-600">
                    <div onClick={()=>{appendIndustry(industryName, industryComment)}}>업종 추가</div>
                </Button>
            </article>

            {/* 화면 하단에는 현재 업종 리스트 */}
            {
                industryList.map((industry) => <IndustryArticle key={industry.industryId} {...industry}></IndustryArticle>)
            }
        </ul>
    );
}