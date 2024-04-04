export function GenerateLiElUUID(){
    return crypto.randomUUID().replaceAll("-", "");
}

export function maskingIpAddress(ipAddress){
    if(!ipAddress || typeof ipAddress  != "string") return ;

    return ipAddress.substring(0,7) + ".xxx.xxx";
}