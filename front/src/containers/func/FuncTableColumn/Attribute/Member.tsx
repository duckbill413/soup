'use client'

import useHandleKeys from "@/hooks/useHandleKeys";
import {FuncTableColumnProps} from "@/containers/func/types/functionDesc";
import {useEffect} from "react";
import Image from "next/image";
import useMemberStore from "@/stores/useMemberStore";
import MemberModal from "@/containers/func/FuncTableColumn/Attribute/Modal/MemberModal";
import defaultProfile from "#/assets/images/defaultProfile.png"
import * as styles from "./member.css";

export default function Category({funcCurrData,updateElement}: FuncTableColumnProps){
    const { filteredMembers,setIsMemberModalVisible} = useMemberStore();
    const { selected,setSelected, handleKey,action,setAction } = useHandleKeys(filteredMembers,
        filteredMembers.length > 0 ? filteredMembers[0].id : "");

    useEffect(() => {
        if(action==='esc') {
            setIsMemberModalVisible('none');
            setAction("");
            return;
        }
        if(action==='enter'){
            updateElement(funcCurrData.reporter.id,selected,'reporter');
            setAction("");

        }
    }, [action, setIsMemberModalVisible, updateElement, funcCurrData, selected, setAction]);


    return (
        <td>
            <div className={styles.manager} onClick={()=>setIsMemberModalVisible(funcCurrData.functionId)} role="presentation" aria-hidden="true" >
                {funcCurrData.reporter?.id!=="" && funcCurrData.reporter ?
                    <>
                    {funcCurrData.reporter?.profileImageUrl ?
                        <Image unoptimized src={funcCurrData.reporter?.profileImageUrl} alt="프로필 이미지" width={30} height={30}/>
                        :
                        <Image unoptimized src={defaultProfile} alt="프로필 이미지" width={30} height={30}/>
                    }
                        <p>{funcCurrData.reporter?.nickname}</p>
                    </> : <div/>
            }
                ㅤ
            </div>
            <MemberModal funcCurrData={funcCurrData} updateElement={updateElement} selected={selected}
                           setSelected={setSelected} handleKey={handleKey}/>

        </td>
    );
}