'use client'

import {MemberModalProps} from "@/containers/func/types/functionDesc";
import {useEffect, useRef} from "react";
import useMemberStore from "@/stores/useMemberStore";
import Image from "next/image";
import defaultProfile from "#/assets/images/defaultProfile.png"
import * as styles from "./memberModal.css";


export default function MemberModal({setSelected,updateElement,selected,handleKey,funcCurrData}: MemberModalProps){

    const { setIsMemberModalVisible,filteredMembers,isMemberModalVisible,members,setFilteredMembers,searchMember} = useMemberStore();
    const inputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        setFilteredMembers(members);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [funcCurrData,setFilteredMembers]);

    useEffect(() => {
        if (filteredMembers && filteredMembers.length > 0 ) {
            setSelected(filteredMembers[0].id);
        }
        if (!inputRef.current || funcCurrData.category!=="")  return;

        inputRef.current.focus();
    }, [filteredMembers,isMemberModalVisible,setSelected,funcCurrData.category]);


    return(<>
        {isMemberModalVisible !== 'none' && (
            <div onClick={()=>setIsMemberModalVisible('none')} className={styles.clickBackground} aria-hidden="true" role="presentation"/>
        )}
        {isMemberModalVisible === funcCurrData.functionId &&

            <div className={styles.btnGroupContainer}>

                <div className={styles.btnGroup}>
                    <div className={`${styles.elementGroup}`}>
                        {funcCurrData.reporter?.id!=="" ?
                            <div className={styles.currCategory}
                                 >
                                <div>
                                    {funcCurrData.reporter?.profileImageUrl ?
                                        <Image unoptimized src={funcCurrData.reporter?.profileImageUrl} alt="프로필 이미지" width={30} height={30}/>
                                        :<Image unoptimized src={defaultProfile} alt="프로필 이미지" width={30} height={30}/>
                                    }

                                    {`${funcCurrData.reporter?.nickname}`}
                                </div>
                                <p onClick={() => updateElement(funcCurrData.functionId, 'none','reporter')} aria-hidden="true" role="presentation">×</p>
                            </div>
                            : <input className={styles.option} placeholder="사용자 검색"
                                     ref={inputRef}
                                     onChange={(e) => searchMember(e.target.value)}
                                     onKeyDown={(event) => {
                                         handleKey(event);
                                     }}/>
                        }

                    </div>
                    <div>
                        {filteredMembers.filter(member => member.nickname.trim() !== '').map(d =>
                            <div key={crypto.randomUUID()}
                                 className={`${styles.select} ${selected === d.id ? styles.whitesmoke : ''}`}
                                 onClick={() => updateElement(funcCurrData.functionId, d.id,'reporter')}
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
