import { getMenuComponents } from "../../utils/getComponents";
import BoardMenus from "./BoardMenus";

export default function Menus(){
    //fetch MenuList by Server
    //가데이터
    const fetchMenus = [
        {href: "/doc", svg: "Heart", title: "삼성전자"},
        {href: "/doc", svg: "Share", title: "공유상회"},
    ];

    const MenuList = getMenuComponents(fetchMenus);

    return (
        <nav>
            <ul className="grid gap-2 p-4">
                <BoardMenus></BoardMenus>
                {...MenuList}
            </ul>
        </nav>
    );
}