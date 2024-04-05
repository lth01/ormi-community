import LabelSection from "@/components/Layout/LabelSection";
import LogoHeader from "@/components/Layout/LogoHeader";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { Input } from "@/components/ui/input";
import { PhoneInput } from "@/components/ui/phone-input";
import { Select, SelectTrigger, SelectContent, SelectLabel, SelectItem, SelectGroup, SelectValue } from "@/components/ui/select";
import { GlobalContext } from "@/index";
import { fetchGender, fetchIndustryList, fetchPasswordQuestion, fetchEditableUserInfo, logout, isLoginUser } from "@/utils/API";
import { goToback } from "@/utils/RouteHelper";
import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"


export default function EditInformation(){
    //변수 선언 지역
    const navigate = useNavigate();
    //전역변수 - 로그인 여부 확인
    const {isLogin} = useContext(GlobalContext);
    //업종 목록
    const [industries, setIndustries] = useState([]);
    //비밀번호 찾기 질문
    const [passwordQuestions, setPasswordQuestions] = useState([]);

    useEffect(() =>{
        console.log(isLogin, isLoginUser());
        if(!isLogin){
            alert("로그인이 만료되었습니다.");
            logout();
            navigate("/");
        }else{
            Promise.all([fetchIndustryList(), fetchPasswordQuestion()])
            .then((result) =>{
                const industries = result[0];
                const passwordQuestions = result[1];
                const genders = result[2];

                setIndustries(industries);
                setPasswordQuestions(passwordQuestions);
            });
        }
    },[]);

    const [tBox_nickname_val, setNickNameVal] = useState("");
    const [tBox_phonenumber_val, setPhoneNumberVal] = useState("");
    const [interestIndustry1, setInterestIndustry1] = useState("");
    const [interestIndustry2, setInterestIndustry2] = useState("");
    const [interestIndustry3, setInterestIndustry3] = useState("");
    // 패스워드 힌트 바꾸기 처리
    const [ddl_phq_val, setPHQVal] = useState("");
    // 패스워드 힌트 응답 바꾸기 처리
    const [tBox_pha_val, setPHAVal] = useState("");

    const InitState = () =>{
        const {nickName, phoneNumber, passwordHintQuestion, passwordHintAnswer, career1, career2, career3} = fetchEditableUserInfo();
        setNickNameVal(nickName);
        setPhoneNumberVal(phoneNumber);
        setInterestIndustry1(career1);
        setInterestIndustry2(career2);
        setInterestIndustry3(career3);
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
                    <Select onValueChange={setInterestIndustry1}>
                        <SelectTrigger>
                            <SelectValue placeholder="업종"></SelectValue>
                        </SelectTrigger>
                        <SelectContent>
                           {
                                industries.map((industry) => <SelectItem key={industry.industryId} value={industry.industryId}>{industry.industryName}</SelectItem>)
                           } 
                        </SelectContent>
                    </Select>
                </LabelSection>
                <LabelSection asChild label="관심업종2" className="mt-2">
                    <Select  onValueChange={setInterestIndustry2}>
                        <SelectTrigger>
                            <SelectValue placeholder="업종"></SelectValue>
                        </SelectTrigger>
                        <SelectContent>
                            {
                                industries.map((industry) => <SelectItem key={industry.industryId} value={industry.industryId}>{industry.industryName}</SelectItem>)
                            } 
                        </SelectContent>
                    </Select>
                </LabelSection>
                <LabelSection asChild label="관심업종3" className="mt-2">
                    <Select onValueChange={setInterestIndustry3}>
                        <SelectTrigger>
                            <SelectValue placeholder="업종"></SelectValue>
                        </SelectTrigger>
                        <SelectContent>
                            {
                                industries.map((industry) => <SelectItem key={industry.industryId} value={industry.industryId}>{industry.industryName}</SelectItem>)
                            } 
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
                                {
                                    passwordQuestions.map(passwordQuestion => <SelectItem key={passwordQuestion.passwordQuestionId} value={passwordQuestion.passwordQuestionId}>{passwordQuestion.passwordQuestion}</SelectItem>)
                                }
                            </SelectGroup>
                        </SelectContent>
                    </Select>
                </LabelSection>
                <LabelSection asChild isRequire label="비밀번호 찾기 응답" className="mt-2">
                   <Input id="tBox_password_hint" placeholder="비밀번호 찾기 응답을 입력해주세요." value={tBox_pha_val} onChange={setPHAVal}></Input> 
                </LabelSection>
                <div className="flex justify-center gap-2 items-center mt-4">
                    <Button asChild className="mt-4">
                        <div onClick={()=>{navigate(-1)}}>취소</div>
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