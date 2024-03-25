import { Link } from "react-router-dom";
import { AvatarImage, AvatarFallback, Avatar} from "./ui/avatar"
import { CardHeader, CardContent, CardFooter, Card } from "./ui/card"

function Header(){
    return <header className="flex items-center justify-between px-4 py-2 border-b dark:border-gray-800">
        <Link className="flex items-center gap-2 text-lg font-semibold" href="#">
          <GlobeIcon className="h-6 w-6" />
          <span>TopicShare</span>
        </Link>
        <div className="flex items-center gap-4">
          <Input className="w-64" id="search" placeholder="Search topics or communities" type="search" />
          <Button size="icon" variant="outline">
            <BellIcon className="h-4 w-4" />
            <span className="sr-only">Notifications</span>
          </Button>
          <Avatar className="h-8 w-8">
            <AvatarImage alt="@shadcn" src="/placeholder-avatar.jpg" />
            <AvatarFallback>JP</AvatarFallback>
          </Avatar>
        </div>
      </header>
}

export default Header;