import { Avatar, AvatarFallback } from "../ui/avatar";
import HeartIcon from "../Icon/HeartIcon"
import { GenerateLiElUUID } from "@/utils/keygenerator";
import { fetchDocComments } from "@/utils/API";
import { useState, useEffect } from "react";
import { Textarea } from "../ui/textarea";
import { Button } from "../ui/button";

const Comment = (props) => {
    const [usrInfoList, setUsrInfos] = useState([]);
    //현재는 Comment를 생성할 때 호출하도록 되어있음. 함수 호출 시점은 바뀔 수 있음.
    useEffect(() =>{
        const newUsers = fetchDocComments();
        setUsrInfos([...newUsers]);
    },[]);

    return (
        <div className="p-4 border-l border-b border-r border-t-0 border-gray-300">
            <ul className="flex flex-col gap-4">
                {
                    usrInfoList.map(userInfo =>{
                        return (<CommentItem key={GenerateLiElUUID()} userInfo={userInfo}></CommentItem>);
                    })
                }
            </ul>
            <Textarea className="mt-4 resize-none" placeholder="댓글을 입력해주세요"></Textarea>
            <Button ahChild className="mt-2 bg-black text-white w-full">
                <div onClick={() =>{}}>댓글 등록</div>
            </Button>
        </div>
    );
};

/**
 * 
 * @param { {nickName: String, userId: String, commentDate: Date, comment: String, likeCount: number, likeAble: boolean} } userInfo 
 * @returns 
 */
const CommentItem = ({userInfo}) =>{
    const shortNickName = getShortNickName(userInfo.nickName);

    return (
        <li className="space-y-4">
            <div className="flex items-start space-x-3">
                <Avatar>
                    <AvatarFallback className="font-bold">{shortNickName}</AvatarFallback>
                </Avatar>
                <div>
                    <p className="text-sm font-semibold">{`${userInfo.nickName}(${userInfo.userId})`}</p>
                    <p className="text-xs text-gray-500">{userInfo.commentDate}</p>
                    <p className="mt-1 text-sm">{userInfo.comment}</p>
                    <div className="flex items-center mt-1 space-x-1 text-sm text-gray-500">
                        <HeartIcon className={`h-4 w-4 ${userInfo.likeAble ? "" : "text-red-500"}`} />
                        <span>{userInfo.likeCount}</span>
                    </div>
                </div>
            </div>
        </li>
      );
};


const getShortNickName = (nickName) =>{
    if(!nickName || typeof nickName !== "string") return "";
    return nickName.substring(0,nickName.length > 2 ? 2 : nickName.length);
}

export{
    Comment,
    CommentItem
}