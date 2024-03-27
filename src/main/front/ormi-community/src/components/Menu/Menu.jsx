import HeartIcon from "../Icon/HeartIcon";
import { useEffect, useState } from "react";
import { GenerateLiElUUID } from "../../utils/keygenerator"

// 게시판 목록 메뉴를 제외한 일반적인 메뉴를 표현
function Menu({href, svg, children, beforeClicks, size}){
    const defaultIcon = HeartIcon(getSizeToSvgClassName(size));
    const [onclicks, setOnClick] = useState([]);
    const [svgIconEl, setSvgIcon] = useState(svg ? svg(getSizeToSvgClassName(size)) : defaultIcon);
    const moveToHref = () =>{
        location.href = href;
    };

    const changeSvgIcon = (newSvg) =>{
        setSvgIcon(newSvg ? newSvg : defaultIcon);
    }

    useEffect(() => {
        setOnClick(beforeClicks.length > 0 ? [...beforeClicks, moveToHref ] : [moveToHref]);
    },[]);
   
    return (
        <li key={GenerateLiElUUID()}
            className="list-none flex items-center gap-2 text-sm font-medium truncate"
            onClick={() => {
                onclicks.forEach(onclick => onclick.call());
        }}>
                {svgIconEl}
                <span className="trunacte">
                {children}
            </span>
        </li>
    );
}

function getSizeToSvgClassName(size){
    return size ?
    { className: `h-${size} w-${size} shrink-0` } :
    { className: `h-4 w-4 shrink-0`};
}


export default Menu;