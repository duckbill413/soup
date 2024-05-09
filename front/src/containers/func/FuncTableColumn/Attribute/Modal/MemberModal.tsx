'use client'

import {MemberModalProps} from "@/types/functionDesc";
import {useEffect, useRef} from "react";
import useMemberStore from "@/stores/useMemberStore";
import Image from "next/image";
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
            setSelected(filteredMembers[0].memberId);
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
                        {funcCurrData.reporter?.memberId!=="" ?
                            <div className={styles.currCategory}
                                 >
                                <div>
                                    <Image unoptimized src={funcCurrData.reporter?.memberProfileUri} alt="프로필 이미지" width={30} height={30}/>
                                    {`${funcCurrData.reporter?.memberNickname}`}
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
                        {filteredMembers.filter(member => member.memberNickname.trim() !== '').map(d =>
                            <div key={crypto.randomUUID()}
                                 className={`${styles.select} ${selected === d.memberId ? styles.whitesmoke : ''}`}
                                 onClick={() => updateElement(funcCurrData.functionId, d.memberId,'reporter')}
                                 aria-hidden="true"
                                 role="presentation"
                            >
                                <button
                                        className={styles.button}
                                        key={crypto.randomUUID()} type='button'
                                        aria-hidden="true">
                                        <Image unoptimized src={d.memberProfileUri} alt="프로필 이미지" width={30} height={30}/>
                                        {d.memberNickname}</button>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        }
    </>);
}
