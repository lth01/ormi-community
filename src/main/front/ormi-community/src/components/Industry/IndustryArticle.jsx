export default function IndustryArticle({industryId ,industryName, industryComment}){
    return (
        <li className="p-4 flex flex-col border rounded-sm">
                <span>업종: {industryName}</span>
                <span>설명: {industryComment}</span>
        </li>
    );
}