export type Member = {
    id: string;
    nickname: string;
    profileImageUrl: string;
}
export type MemberRes = {
    id: string;
    email: string;
    nickname: string;
    displayName: string;
    phone: string;
    roles: string[];
    profileImageUrl: string;
}