interface Sender{
    memberId:string;
    nickname:string;
    profileImageUrl:string;
}
export type ChatReq={
    sender: Sender;
    message: string;
    mentionedMemberIds: string[];
}

export type ChatRes={
    chatMessageId: string;
    mentionedMemberIds: string[];
    message: string;
    sender: Sender;
    sentAt: string;

}

export type ChatContentProps={
    chatMessageId: string;
    header:string;
    me:boolean
    mentionedMemberIds: string[];
    message: string;
    nickname: string;
    profileImageUrl:string | StaticImageDat;
    sentAt: string;
}

export type MentionModalProps ={
    selected: string;
    setSelected: (id:string)=>void;
    selectMember: (message:string)=>void;
    handleKey: (event: React.KeyboardEvent<HTMLInputElement>) => void;
}