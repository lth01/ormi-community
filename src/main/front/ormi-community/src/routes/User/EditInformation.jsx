import LabelSection from "@/components/Layout/LabelSection";
import LogoHeader from "@/components/Layout/LogoHeader";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";
import { PhoneInput } from "@/components/ui/phone-input";
import { Select, SelectTrigger, SelectContent, SelectLabel, SelectItem, SelectGroup, SelectValue } from "@/components/ui/select";
import { fetchEditableUserInfo } from "@/utils/API";
import { goToback } from "@/utils/RouteHelper";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"


export default function EditInformation(){
    //변수 선언 지역
    const navigate = useNavigate();
    const [tBox_nickname_val, setNickNameVal] = useState("");
    const [tBox_phonenumber_val, setPhoneNumberVal] = useState("");
    const [ddl_career1_val, setCaeer1Val] = useState("");
    const [ddl_career2_val, setCaeer2Val] = useState("");
    const [ddl_career3_val, setCaeer3Val] = useState("");
    // 패스워드 힌트 바꾸기 처리
    const [ddl_phq_val, setPHQVal] = useState("");
    // 패스워드 힌트 응답 바꾸기 처리
    const [tBox_pha_val, setPHAVal] = useState("");

    const InitState = () =>{
        const {nickName, phoneNumber, passwordHintQuestion, passwordHintAnswer, career1, career2, career3} = fetchEditableUserInfo();
        setNickNameVal(nickName);
        setPhoneNumberVal(phoneNumber);
        setCaeer1Val(career1);
        setCaeer2Val(career2);
        setCaeer3Val(career3);
        setPHQVal(passwordHintQuestion);
        setPHAVal(passwordHintAnswer);
    }

    useEffect(()=>{
        InitState();
    },[]);

    return (
        <main className="flex flex-col bg-gray-100 min-h-screen items-center overflow-y-auto pb-20">
            <section className="w-800 p-2 mt-10">
                <LogoHeader>정보수정</LogoHeader>
                <LabelSection asChild isRequire label="닉네임" className="mt-2">
                    <Input type="text" id="tBox_nickname" value={tBox_nickname_val} onChange={setNickNameVal} placeholder="닉네임을 입력해주세요."></Input>
                </LabelSection>
                <LabelSection asChild isRequire label="휴대폰번호" className="mt-2">
                    {/* value에 initial 값 삽입! */}
                    <PhoneInput value={tBox_phonenumber_val} onChange={setPhoneNumberVal} id="tBox_phonenumber" className="w-full"></PhoneInput>
                </LabelSection>
                <LabelSection asChild label="관심업종1" className="mt-2">
                    <Select value={ddl_career1_val} onValueChange={setCaeer1Val}>
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
                <LabelSection asChild label="관심업종2" className="mt-2">
                    <Select value={ddl_career2_val} onValueChange={setCaeer2Val}>
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
                <LabelSection asChild label="관심업종3" className="mt-2">
                    <Select value={ddl_career3_val} onValueChange={setCaeer3Val}>
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
                <LabelSection asChild isRequire label="비밀번호 찾기 질문" className="mt-2">
                    <Select value={ddl_phq_val} onValueChange={setPHQVal}>
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
                   <Input id="tBox_password_hint" placeholder="비밀번호 찾기 응답을 입력해주세요." value={tBox_pha_val} onChange={setPHAVal}></Input> 
                </LabelSection>
                <div className="flex justify-center gap-2 items-center mt-4">
                    <Button asChild className="mt-4">
                        <div onClick={()=>{goToback(navigate)}}>취소</div>
                    </Button>
                    <Button asChild className="bg-violet-800 mt-4 hover:bg-violet-600">
                        <div onClick={()=>{console.log("정보수정")}}>정보수정</div>
                    </Button>
                </div>
                {/* 버튼은 onClick 콜백 동작 불가 */}
                
                
            </section>
        </main>
    );
}