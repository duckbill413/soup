'use client'

import chatSvg from '#/assets/icons/chat/chat.svg'
import chatXSvg from '#/assets/icons/chat/chatX.svg'
import Image from 'next/image'
import styles from '@/containers/project/chat.css'
import {useEffect, useState} from 'react'
import ChatModal from '@/containers/project/chatModal/ChatModal'
import {getProjectMembers,getMemberInfo} from "@/apis/member/memberAPI";
import useMemberStore from "@/stores/useMemberStore";
import {useMessageSocketStore} from "@/stores/useMessageSocketStore";
import {MemberRes} from "@/containers/project/types/member";
import {getChatting} from "@/apis/chat/chatAPI";
import useMentionStore from "@/stores/useMentionStore";

type Props = {
    projectId: string,
}

const SVG_SIZE = 90

export default function Chat({projectId}: Props) {
    // Modal을 보여주는 상태관리 null타입은 첫 상태이고 이 후 true false가 된다.
    const [isVisible, setIsVisible] = useState<boolean | null>(null)
    const {connect, disconnect, client,setChatList} = useMessageSocketStore();
    const {setMembers,setMe} = useMemberStore();
    const {setSenders} = useMentionStore();
    const handleVisible = () => {
        if (isVisible === null || isVisible === false) setIsVisible(true)
        else if (isVisible === true) setIsVisible(false)
    }

    useEffect(() => {
        getProjectMembers(projectId).then(data => {
            setMembers(data);
            const modifiedData = data.map(d => {
                const { id, ...rest } = d;
                return {
                    memberId: id,
                    ...rest
                };
            });
            setSenders(modifiedData);
        })
        getMemberInfo().then(data=>{
            const { memberId, ...tempWithoutMemberId } = data.result;
            const temp: MemberRes = {
                ...tempWithoutMemberId,
                id: memberId
            };
            setMe(temp);
        })
        getChatting(projectId).then(data=>{
            setChatList(data);
        });
        connect(projectId);
        return (() => {
            if (client) {
                disconnect(client);
            }
        });

    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [projectId,connect]);
    return (
        <>
            <ChatModal isVisible={isVisible} projectId={projectId}/>
            <button type='button' onClick={handleVisible}>
                {!isVisible ? (
                    <Image
                        className={styles}
                        src={chatSvg}
                        alt="Chat"
                        width={SVG_SIZE}
                        height={SVG_SIZE}

                    />
                ) : (
                    <Image
                        className={styles}
                        src={chatXSvg}
                        alt="Chat"
                        width={SVG_SIZE}
                        height={SVG_SIZE}
                    />
                )}
            </button>
        </>
    )
}
