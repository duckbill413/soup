'use client'

import {useEffect} from "react";
import Image from "next/image";
import defaultProfile from "#/assets/images/defaultProfile.png"
import useMentionStore from "@/stores/useMentionStore";
import {MentionModalProps} from "@/containers/project/types/chat";
import * as styles from "./mentionModal.css";


export default function MentionModal({setSelected,selected,selectMember}: MentionModalProps){

    const { senders,isSenderModalVisible,setIsSenderModalVisible,setFilteredSenders,filteredSenders} = useMentionStore();
    useEffect(() => {
        setFilteredSenders(senders);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [setFilteredSenders]);

    useEffect(() => {
        if (filteredSenders && filteredSenders.length > 0 ) {
            setSelected(filteredSenders[0].memberId);
        }
    }, [filteredSenders,isSenderModalVisible,setSelected,]);


    return(<>
        {isSenderModalVisible  && (
            <div onClick={()=>setIsSenderModalVisible(false)} className={styles.clickBackground} aria-hidden="true" role="presentation"/>
        )}
        {isSenderModalVisible &&

            <div className={styles.btnGroupContainer}>

                <div className={styles.btnGroup}>
                    <div className={`${styles.elementGroup}`}>
                         <div className={styles.option} >채널 사용자</div>
                    </div>
                    <div>
                        {filteredSenders.filter(member => member.nickname.trim() !== '').map(d =>
                            <div key={crypto.randomUUID()}
                                 className={`${styles.select} ${selected === d.memberId ? styles.whitesmoke : ''}`}
                                 onClick={() => selectMember(d.nickname)}
                                 aria-hidden="true"
                                 role="presentation"
                            >
                                <button
                                        className={styles.button}
                                        key={crypto.randomUUID()} type='button'
                                        aria-hidden="true">
                                    {d.profileImageUrl ?
                                        <Image unoptimized src={d.profileImageUrl} alt="프로필 이미지" width={30} height={30}/>
                                        :<Image unoptimized src={defaultProfile} alt="프로필 이미지" width={30} height={30}/>
                                    }
                                        {d.nickname}</button>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        }
    </>);
}
