import {
    NavigationMenu,
    NavigationMenuContent,
    NavigationMenuItem,
    NavigationMenuList,
    NavigationMenuTrigger,
  } from "@/components/ui/navigation-menu"
import { getMenuComponents } from "../../utils/getComponents";

export default function BoardMenus(){
    const fetchBoards = [
        {href: "", svg: "Share", title: "삼성전자"},
        {href: "", svg: "Share", title: "SK하이닉스"},
        {href: "", svg: "Share", title: "웅진코놀ㅈㅁㄹㅁㅈ로맺돼awefaweofiajwefojo"},
    ];

    const MenuList = getMenuComponents(fetchBoards);

    return (
        <NavigationMenu>
            <NavigationMenuList>
                <NavigationMenuItem>
                    <NavigationMenuTrigger className="p-0">게시판목록</NavigationMenuTrigger>
                    <NavigationMenuContent className="overflow-y-auto">
                        <ul className="flex flex-col w-[150px] gap-4 p-4 md:w-[200px] lg:w-[200px] max-h-[500px] overflow-y-auto">
                            {...MenuList}
                        </ul>
                    </NavigationMenuContent>
                </NavigationMenuItem>
            </NavigationMenuList>
        </NavigationMenu>
        );
}