'use client'

import chatSvg from '#/assets/icons/chat/chat.svg'
import chatXSvg from '#/assets/icons/chat/chatX.svg'
import Image from 'next/image'
import styles from '@/containers/project/chat.css'
import {useEffect} from 'react'
import ChatModal from '@/containers/project/chatModal/ChatModal'
import {getProjectMembers,getMemberInfo} from "@/apis/member/memberAPI";
import useMemberStore from "@/stores/useMemberStore";
import {useMessageSocketStore} from "@/stores/useMessageSocketStore";
import {MemberRes} from "@/containers/project/types/member";
import {getChatting} from "@/apis/chat/chatAPI";
import useMentionStore from "@/stores/useMentionStore";
import {getJiraInfo, getJiraMembers} from "@/apis/jira/jiraAPI";
import useJiraStore, {convertJiraMembers} from "@/stores/useJiraStore";

type Props = {
    projectId: string,
}

const SVG_SIZE = 90

export default function Chat({projectId}: Props) {

    const {connect, disconnect, client,setChatList,isVisible,setIsVisible} = useMessageSocketStore();
    const {setMembers,setMe,setChatMembers,} = useMemberStore();
    const {setSenders} = useMentionStore();
    const {setIsConnected,setJiraMembers,isConnected,jiraMembers,setJiraInfo} = useJiraStore();

    const handleVisible = () => {
        if (isVisible === null || isVisible === false) setIsVisible(true)
        else if (isVisible === true) setIsVisible(false)
    }
    useEffect (() => {
        getProjectMembers(projectId).then(data => {
            setMembers(data);
            setChatMembers(data);
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
        getJiraInfo(projectId).then(data=>{
            setJiraInfo(data);
            if(data.jiraHost) {
                getJiraMembers(projectId).then(jiraData => {
                    setJiraMembers(convertJiraMembers(jiraData));
                    setIsConnected(true);
                })
            }

        })
        if(!client)
        connect(projectId);
        return (() => {
            setIsVisible(false);
            if (client) {
                disconnect(client);
            }
        });

    // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [projectId,connect]);

    useEffect(() => {
        setMembers(jiraMembers);
    }, [isConnected]);
    return (
        <>
            <ChatModal projectId={projectId}/>
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
