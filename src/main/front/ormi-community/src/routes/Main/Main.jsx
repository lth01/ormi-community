import Header from "@/components/Layout/Header";
import Menus from "@/components/Menu/Menus";
import mainCharacter from "../../assets/image/mainCharacter.png";
import { Document } from "@/components/Document/Document";

export default function Main(){
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

            <Document></Document>
            <Document></Document>
          </div>
        </section>
      </main>
    </div>
  );
}