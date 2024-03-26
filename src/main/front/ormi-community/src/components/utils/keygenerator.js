export function GenerateLiElUUID(){
    return crypto.randomUUID().replaceAll("-", "");
}

