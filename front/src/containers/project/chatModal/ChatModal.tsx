'use client'

import * as styles from '@/containers/project/chatModal/chatModal.css'
import Image from 'next/image'
import sendSvg from '#/assets/icons/chat/send.svg'
import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'
import 'dayjs/locale/ko'
import {useMessageSocketStore} from "@/stores/useMessageSocketStore";
import {useEffect, useState} from "react";
import useMemberStore from "@/stores/useMemberStore";
import defaultImage from "#/assets/images/defaultProfile.png"
import ChatContent from "@/containers/project/chatModal/chatContent/ChatContent";
import useMentionStore from "@/stores/useMentionStore";
import useHandleKeys from "@/hooks/useHandleKeys";
import MentionModal from "@/containers/project/chatModal/mentionModal/MentionModal";
import getMentionedMember from "@/utils/getMentionedMember";


dayjs.extend(relativeTime)
dayjs.locale('ko')


type Props = {
    isVisible: boolean | null,
    projectId: string
}

export default function ChatModal({isVisible, projectId}: Props) {
    const {send, chatList} = useMessageSocketStore();
    const {me, members} = useMemberStore();
    const [message, setMessage] = useState<string>("");
    const { isSenderModalVisible,setIsSenderModalVisible,filteredSenders,searchSender} = useMentionStore();
    const { selected,setSelected, handleKey } = useHandleKeys(filteredSenders,
        filteredSenders.length > 0 ? filteredSenders[0].memberId : "");

    const [cursorIndex, setCursorIndex] = useState<number>(0);

    // Animation상태는 세 종류 1.첫 시작(first) 2. 열 때(after), 3. 닫을 때(before)
    const getModalStyle = () => {
        if (isVisible) {
            return styles.chatModalAnimation.after;
        }
        if (isVisible === null) {
            return styles.chatModalAnimation.first;
        }
        return styles.chatModalAnimation.before;
    };

    useEffect(() => {

        if (message[cursorIndex] === '@') {
            setIsSenderModalVisible(true);
            // 검색 함수 호출
            searchSender(message.slice(cursorIndex + 1, message.length));
        } else {
            setIsSenderModalVisible(false);
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [message, cursorIndex]);

    useEffect(() => {
        if (filteredSenders.length <= 0) {
            setIsSenderModalVisible(false);
        }

    }, [filteredSenders,setIsSenderModalVisible]);
    const sendMessage = () => {
        if (!me || !message) return;
        send(projectId, {
            message, mentionedMemberIds: getMentionedMember(message,members), sender: {
                memberId: me.id,
                nickname: me.nickname,
                profileImageUrl: me.profileImageUrl
            }
        });
        setMessage("");
    }

    const handleMessage = (query:string) =>{
        setMessage(query);
    }
    const selectMember = (nickname:string) =>{
        let count = message.length-cursorIndex;
        let addSize = 0;
        while(count){
            count+=-1
            if(message[cursorIndex] ===" "){
                break;
            }
            addSize+=1;
        }
        setMessage(`${message.slice(0, cursorIndex+1) + nickname + message.slice(cursorIndex+1+addSize)} `);
    }

    const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
        if(e.key === 'Escape') {
            setIsSenderModalVisible(false);
        }
        if (e.key === 'Enter') {
            if(isSenderModalVisible){
                const searchMember = members.find(data=>data.id===selected)
                if(searchMember) {
                    selectMember(searchMember?.nickname);
                }
            }
            if(!isSenderModalVisible){
                sendMessage();
            }
        }
        if (e.key === '@') {
            setCursorIndex(e.currentTarget.selectionStart || 0);
        }
    };

    const updatedChatList = [];
    let prevMember = null;
    let prevYearMonth = null;
    for (let i = 0; i < chatList.length; i+=1) {
        const chat = chatList[i];
        const member = members.find((data) => data.id === chat.sender.memberId);
        const profileImage = member ? member.profileImageUrl : defaultImage;
        updatedChatList.push({
            ...chat,
            memberId: prevMember !== chat.sender.memberId ? chat.sender.memberId : "",
            profileImageUrl: prevMember !== chat.sender.memberId ? profileImage : "",
            nickname: prevMember !== chat.sender.memberId ? chat.sender.nickname : "",
            me: me?.id === chat.sender.memberId,
            header: (!prevYearMonth || prevYearMonth !== chat.sentAt.slice(0, 10)) ? chat.sentAt.slice(0, 10) : ""
        });
        prevMember = chat.sender.memberId;
        prevYearMonth = chat.sentAt.slice(0, 10);
    }
    return (
        <div className={`${isVisible !== null && styles.chatModal} ${getModalStyle()}`}>
            {isVisible && (
                <>
                    <div className={styles.chatModalContent.header}>채팅</div>
                    <div className={styles.chatModalContent.background}>
                        <div>
                            {updatedChatList.map((chat) => (
                                <ChatContent {...chat} key={chat.chatMessageId} memberNicknames={members.map(data=>data.nickname)} myNickname={me?.nickname} nickname={chat.nickname} header={chat.header} profileImageUrl={chat.profileImageUrl}/>
                            ))}
                        </div>
                    </div>
                    <div className={styles.chatModalContent.send}>
                        <div>
                            <MentionModal  selected={selected} setSelected={setSelected} handleKey={handleKey} selectMember={selectMember}/>
                            <input

                                onChange={(e) => handleMessage(e.target.value)}
                                onKeyDown={(event) => {
                                    handleKey(event);
                                    handleKeyDown(event);
                                }}
                                value={message}
                                className={styles.input}
                                placeholder="메시지 입력"
                            />
                            <Image
                                onClick={sendMessage}
                                style={{cursor: 'pointer'}}
                                src={sendSvg}
                                alt="send-image"
                                width={22}
                                height={22}
                            />
                        </div>
                    </div>
                </>
            )}
        </div>
    )
}
