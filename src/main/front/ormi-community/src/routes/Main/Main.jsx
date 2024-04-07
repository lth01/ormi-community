import Header from "@/components/Layout/Header";
import Menus from "@/components/Menu/Menus";
import mainCharacter from "../../assets/image/mainCharacter.png";
import { useContext, useEffect, useState } from "react";
import { GlobalContext } from "@/index";
import { fetchDocumentList, fetchUserInfo, isLoginUser } from "@/utils/API";
import { getDocumentComponents } from "@/utils/getComponents";

export default function Main(){
   const [documentList, setDocumentList] = useState([]);
   //전역 변수 - 게시판 ID
   const {selectBoardID} = useContext(GlobalContext);

    useEffect(()=>{
        if(!selectBoardID) return ;
        fetchDocumentList(selectBoardID)
        .then(data => {
          setDocumentList(getDocumentComponents(data));
        });
      },[selectBoardID]);

    return (
    <div className="flex flex-col h-screen">
        <Header></Header>
      <main className="flex flex-1 overflow-hidden">
        <aside className="w-64 border-r dark:border-gray-800 overflow-auto">
            <Menus></Menus>
        </aside>
        <section className="flex-1 overflow-auto">
          <div className="grid gap-4 p-4">
            <div className="flex justify-center bg-[#7a8cd1] rounded-lg">
                <img src={mainCharacter} className="h-[380px]"></img>
            </div>
            <div className="flex flex-col gap-4">
              {...documentList}
            </div>
          </div>
        </section>
      </main>
    </div>
  );
}