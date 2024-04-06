import { Card, CardContent, CardFooter, CardHeader } from "../ui/card";
import { Avatar, AvatarFallback } from "../ui/avatar";
import MoreVerticalIcon from "../Icon/MoreVerticalIcon";
import CommentCircleIcon from "../Icon/CommentCircleIcon";
import HeartIcon from "../Icon/HeartIcon";
import { Comment } from "../Comment/Comment";
import { useState, useEffect, useContext } from "react";
import { fetchDocComments, fetchOwnIp } from "@/utils/API";
import { Popover, PopoverContent, PopoverTrigger } from "../ui/popover";
import Menu from "../Menu/Menu";
import { AlertDialog, AlertDialogAction, AlertDialogCancel, AlertDialogContent, AlertDialogDescription, AlertDialogFooter, AlertDialogTitle, AlertDialogTrigger, AlertDialogHeader } from "../ui/alert-dialog";
import { getIcons } from "@/utils/getComponents";
import { isLoginUser } from "@/utils/API";
import { GenerateLiElUUID } from "@/utils/common";
import { GlobalContext } from "@/index";


const Document = (props) =>{
    const {boardName, docId, docTitle, docContent, boardId, nickname, email} = props;

    const [showComment, setCommentVisibility] = useState(false);
    const [commentInfoList, setCommentInfos] = useState([]);
    const [ownIP, setOwnIP] = useState("");
    //CommentList새로고침용
    const [reload, setReload] = useState(-1);

    useEffect(() =>{
        fetchOwnIp()
        .then(data => {
            setOwnIP(data.IPv4);
        });
    }, []);

    useEffect(() =>{
        fetchDocComments(docId)
        .then((data) => setCommentInfos(data));
    },[showComment, reload]);

    const toggleVisibility = () =>{
        setCommentVisibility(!showComment);
    }


    return (
        <Card key={GenerateLiElUUID()}>
            <DocumentHeader {...props}></DocumentHeader>
            <DocumentContent {...props}></DocumentContent>
            <DocumentFooter toggleFunc={toggleVisibility}></DocumentFooter>           
            {
                showComment ?
                <Comment commentInfoList={commentInfoList} ownIP={ownIP} docId={docId} reload={reload} setReload={setReload}></Comment> :
                <></>
            }
        </Card>
    );
}

const DocumentHeader = ({boardName, docTitle, nickname, email, docCreateDate}) =>{
    //docInfo는 인코딩 되어 저장하고있는다. 수정 버튼을 누르면 URL을 통해 데이터를 전달한다.
    return (
        <CardHeader className="p-4">
            <div className="flex flex-col">
                <div className="flex justify-between items-center">
                    <span className="text-lg font-bold text-gray-400">
                        {boardName}
                    </span>
                    {
                        // docInfo?.myDocumentation
                        true
                         ?
                        <Popover>
                            <PopoverTrigger asChild>
                                <div>
                                    <MoreVerticalIcon></MoreVerticalIcon>
                                </div>
                            </PopoverTrigger>
                            <PopoverContent className="w-80 grid gap-2">
                                <Menu href={`/docwrite#temp`} svg={getIcons("FileEdit")}>게시글 수정</Menu>
                                <AlertDialog>
                                    <AlertDialogTrigger asChild>
                                        {/* 메뉴 컴포넌트만 단독으로 쓰면, 클릭해도 다이얼로그 안뜸.. Menu 내부 li컴포넌트가 props를 안써서 그런가봄.. */}
                                        <div>
                                            <Menu svg={getIcons("Delete")}>게시글 삭제</Menu>
                                        </div>
                                    </AlertDialogTrigger>
                                    <AlertDialogContent>
                                        <AlertDialogHeader>
                                            <AlertDialogTitle>게시글을 삭제하시겠어요?</AlertDialogTitle>
                                            <AlertDialogDescription>게시글을 삭제하면 되돌릴 수 없어요.</AlertDialogDescription>
                                        </AlertDialogHeader>
                                        <AlertDialogFooter>
                                            <AlertDialogCancel>취소</AlertDialogCancel>
                                            <AlertDialogAction onClick={() =>{console.log("삭제 데모")}}>삭제</AlertDialogAction>
                                        </AlertDialogFooter>
                                    </AlertDialogContent>
                                </AlertDialog>
                            </PopoverContent>
                        </Popover>
                         :
                        <></>
                    }
                </div>
                <div className="mt-2 flex items-center justify-between">
                    <div className="flex gap-4">
                        <Avatar className="h-10 w-10">
                        <AvatarFallback>ni</AvatarFallback>
                        </Avatar>
                        <div className="grid gap-0.5">
                            {/* 게시글 제목 */}
                            <span className="font-bold">{docTitle}</span>
                            <span className="text-xs text-gray-500 dark:text-gray-400">
                                작성자: {nickname}({email}) 
                                {/* {`${userInfo.nickName}(${userInfo.userId})`} */}
                            </span>
                        </div>
                    </div>
                    <div className="text-xs text-gray-500 dark:text-gray-400">
                        {docCreateDate}
                    </div>
                </div>
            </div>
        </CardHeader>
    );
}

const DocumentContent = ({docContent}) =>{
    return (
        <CardContent>
            <p className="w-full h-300 break-normal">
                {docContent}
            </p>
        </CardContent>
    );
}

const DocumentFooter = ({docInfo, toggleFunc}) =>{
    const userInfo = {};
    return (
        <CardFooter className="border-t p-3 pl-4 flex gap-4 items-center">
            <div className="flex items-center mt-1 space-x-1 text-sm text-gray-500">
                <HeartIcon className={`h-6 w-6 ${userInfo?.likeAble ? "" : "text-red-500 cursor-pointer"}`} />
                <span>{userInfo?.likeCount}100</span>
            </div>
            <div className="flex items-center mt-1 space-x-1 text-sm text-gray-500 cursor-pointer">
                <CommentCircleIcon className={`h-6 w-6`} onClick={toggleFunc} />
            </div>
        </CardFooter>
    );
}

export {
    Document,
    DocumentHeader,
    DocumentContent
}