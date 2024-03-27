import { Link } from "react-router-dom";
import { AvatarImage, AvatarFallback, Avatar} from "../ui/avatar"
import { Input } from "../ui/input";
import DonkeyLogo from "../Icon/DonkeyLogo";
import { Popover, PopoverContent, PopoverTrigger } from "../ui/popover";
import Menu from "../Menu/Menu";
import { getIcons } from "../../utils/getComponents";
import { NavigationMenuLink, NavigationMenuList, NavigationMenu, NavigationMenuItem } from "../ui/navigation-menu";

function Header(){
    //테스트용
    const isLogin = false;

    return <header className="flex items-center justify-between px-4 py-2 border-b dark:border-gray-800">
        <Link className="flex items-center gap-2 text-lg font-semibold" to="/">
          <DonkeyLogo className="w-48 h-16"/>
        </Link>
        <div className="flex items-center gap-4">
          <Input className="w-64" id="search" placeholder="Search topics or communities" type="search" />
          {
            isLogin ?
            <Popover>
              <PopoverTrigger asChild>
                <Avatar className="h-8 w-8">
                  <AvatarImage alt="@shadcn" src="/placeholder-avatar.jpg" />
                  <AvatarFallback>JP</AvatarFallback>
                </Avatar>
              </PopoverTrigger>
              <PopoverContent className="w-80 grid gap-2">
                <Menu href={"/"} svg={getIcons("User")}>정보수정</Menu>               
                <Menu href={"/"} svg={getIcons("Logout")}>로그아웃</Menu>               
              </PopoverContent>
            </Popover> :
            <NavigationMenu>
              <NavigationMenuList className="flex gap-2">
                <NavigationMenuItem>
                  <Link to="/login">
                    <NavigationMenuLink className="cursor-pointer">로그인</NavigationMenuLink>
                  </Link>
                </NavigationMenuItem>
                <NavigationMenuItem>
                  <Link to="/signup">
                    <NavigationMenuLink className="cursor-pointer">회원가입</NavigationMenuLink>
                  </Link>
                </NavigationMenuItem>
              </NavigationMenuList> 
            </NavigationMenu>
          }
        </div>
      </header>
}

export default Header;