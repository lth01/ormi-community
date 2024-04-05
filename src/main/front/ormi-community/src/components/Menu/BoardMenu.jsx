import { GenerateLiElUUID } from "@/utils/common";
import { forwardRef } from "react";

const BoardMenu = forwardRef((props, ref) =>{
    const {boardId, boardName, clickCallbackFn , className}  = props;
    
    return (
        <li
            ref={ref}
            key={GenerateLiElUUID()}
            className={`list-none flex items-center gap-2 text-sm font-medium truncate cursor-pointer ${className ? className : ""}`}
            onClick={clickCallbackFn}
        >
        <span className="truncate">
            {boardName}
        </span>
        </li>
    );

},[]);

export default BoardMenu;