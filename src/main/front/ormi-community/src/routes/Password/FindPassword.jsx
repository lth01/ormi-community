import LogoHeader from "@/components/Layout/LogoHeader";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Link } from "react-router-dom";
import { useState } from "react";
import { Select, SelectTrigger, SelectContent, SelectLabel, SelectItem, SelectGroup, SelectValue } from "@/components/ui/select";
import LabelSection from "@/components/Layout/LabelSection";

export default function FindPassword(){
    const [ddl_pwdhint_value, setPwdHintValue] = useState(null);

    return (<main className="flex flex-col bg-gray-100 min-h-screen items-center">
            <section className="w-800 p-2 mt-40">
                <LogoHeader>계정 비밀번호 찾기</LogoHeader>
                <LabelSection asChild isRequire label="이메일" className="mt-2">
                    <Input type="email" id="tBox_email" placeholder="이메일을 입력해주세요."></Input>
                </LabelSection>
                <LabelSection asChild isRequire label="비밀번호 찾기 질문" className="mt-2">
                        <Select onValueChange={setPwdHintValue}>
                            <SelectTrigger>
                                <SelectValue placeholder="선택"></SelectValue>
                            </SelectTrigger>
                            <SelectContent>
                                <SelectGroup>
                                    {/* value는 백엔드와 합의 필요 */}
                                    <SelectItem value="1">질문1</SelectItem>
                                    <SelectItem value="2">질문2</SelectItem>
                                </SelectGroup>
                            </SelectContent>
                        </Select>
                </LabelSection>
                <LabelSection asChild isRequire label="비밀번호 찾기 응답" className="mt-2">
                    <Input id="tBox_password_hint" placeholder="비밀번호 찾기 응답을 입력해주세요."></Input> 
                </LabelSection>

            <div className="flex justify-between items-center mt-4 text-violet-800">
                    <div></div>
                    <Link to="signup">회원가입</Link>
                </div>
                {/* 버튼은 onClick 콜백 동작 불가 */}
                <Button asChild className="bg-violet-800 mt-4 w-full hover:bg-violet-600">
                    <div onClick={()=>{}}>확인하기</div>
                </Button>
            </section>
        </main>
    );
}