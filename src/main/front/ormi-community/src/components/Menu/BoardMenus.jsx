import {
    NavigationMenu,
    NavigationMenuContent,
    NavigationMenuItem,
    NavigationMenuList,
    NavigationMenuTrigger,
  } from "@/components/ui/navigation-menu"
import { getBoardMenuComponents} from "../../utils/getComponents";
import { useContext, useEffect, useState } from "react";
import { fetchBoardList } from "@/utils/API";
import { GlobalContext } from "@/index";

export default function BoardMenus(){
    const [boardList, setBoardList] = useState([]);
    const [menuList, setMenuList] = useState([]);
    
    // global state hook
    const {selectDocID, setSelectDocID} = useContext(GlobalContext);


    useEffect(() =>{
        fetchBoardList()
        .then(data =>{
            setBoardList(data);
            setMenuList(getBoardMenuComponents(data));
            setSelectDocID(data.length > 0 ? data[0].boardId : "");
        });
    }, []);
    
    return (
        <NavigationMenu>
            <NavigationMenuList>
                <NavigationMenuItem>
                    <NavigationMenuTrigger className="p-0">게시판목록</NavigationMenuTrigger>
                    <NavigationMenuContent className="overflow-y-auto">
                        <ul className="flex flex-col w-[150px] gap-4 p-4 md:w-[200px] lg:w-[200px] max-h-[500px] overflow-y-auto">
                            {...menuList}
                        </ul>
                    </NavigationMenuContent>
                </NavigationMenuItem>
            </NavigationMenuList>
        </NavigationMenu>
        );
}