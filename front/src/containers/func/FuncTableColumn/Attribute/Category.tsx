'use client'

import useFuncDescStore from "@/stores/useFuncDescStore";
import useHandleKeys from "@/hooks/useHandleKeys";
import {FuncTableColumnProps} from "@/containers/func/types/functionDesc";
import CategoryModal from "@/containers/func/FuncTableColumn/Attribute/Modal/CategoryModal";
import {useEffect} from "react";
import * as styles from "./category.css";

export default function Category({funcCurrData,updateElement}: FuncTableColumnProps){
    const { filteredCategories,setIsCategoryModalVisible} = useFuncDescStore();
    const { selected,setSelected, handleKey,action,setAction } = useHandleKeys(filteredCategories,
        filteredCategories.length > 0 ? filteredCategories[0].functionId : "");

    useEffect(() => {
        if(action==='esc') {
            setIsCategoryModalVisible('none');
            setAction("");
            return;
        }
        if(action==='enter'){
            updateElement(funcCurrData.functionId,selected,'category');
            setAction("");
            
        }
    }, [action, setIsCategoryModalVisible, updateElement, funcCurrData, selected, setAction]);


    return (
        <td>
            <input
                className={styles.category}
                onClick={() => setIsCategoryModalVisible(funcCurrData.functionId)}
                value={funcCurrData.category}
                style={{backgroundColor: funcCurrData.color, width: `${funcCurrData.category.length * 12.5}px`}}
                onKeyDown={(event) => {
                    handleKey(event);
                }}
                readOnly
            />
            <CategoryModal funcCurrData={funcCurrData} updateElement={updateElement} selected={selected} setSelected={setSelected} handleKey={handleKey}/>
        </td>
    );
}