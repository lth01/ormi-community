import LabelSection from "@/components/Layout/LabelSection";
import LogoHeader from "@/components/Layout/LogoHeader";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useState } from "react";


export default function EditPassword(){
    const [ddl_pwdhint_value, setPwdHintValue] = useState(null);

    return (<main className="flex flex-col bg-gray-100 min-h-screen items-center">
            <section className="w-800 p-2 mt-40">
                <LogoHeader>계정 비밀번호 재설정</LogoHeader>
                <LabelSection asChild isRequire label="새로운 비밀번호" className="mt-10">
                    <Input type="text" id="tBox_password" placeholder="새로운 비밀번호를 입력해주세요."></Input>
                </LabelSection>
                <LabelSection asChild isRequire label="새로운 비밀번호 확인">
                    <Input type="text" id="tBox_password_confirm" placeholder="새로운 비밀번호를 한번 더 입력해주세요."></Input>
                </LabelSection>

            
                {/* 버튼은 onClick 콜백 동작 불가 */}
                <Button asChild className="bg-violet-800 mt-10 w-full hover:bg-violet-600">
                    <div onClick={()=>{}}>비밀번호 변경</div>
                </Button>
            </section>
        </main>
    );
}