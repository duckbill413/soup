'use client'

import {CategoryModalProps} from "@/containers/func/types/functionDesc";
import {useEffect, useRef} from "react";
import useFuncDescStore from "@/stores/useFuncDescStore";
import * as styles from "./categoryModal.css";

export default function CategoryModal({setSelected,updateElement,selected,handleKey,funcCurrData}: CategoryModalProps){

    const { searchCategory,uniqueCategories,setFilteredCategories,filteredCategories,
        isCategoryModalVisible,setIsCategoryModalVisible} = useFuncDescStore();
    const inputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        setFilteredCategories(uniqueCategories);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [funcCurrData,setFilteredCategories]);


    useEffect(() => {
        if (filteredCategories && filteredCategories.length > 0 ) {
            setSelected(filteredCategories[0].functionId);
        }
        if (!inputRef.current || funcCurrData.category!=="")  return;

        inputRef.current.focus();
    }, [filteredCategories,isCategoryModalVisible,setSelected,funcCurrData.category]);


    return(<>
        {isCategoryModalVisible !== 'none' && (
            <div onClick={()=>setIsCategoryModalVisible('none')} className={styles.clickBackground} aria-hidden="true" role="presentation"/>
        )}
        {isCategoryModalVisible === funcCurrData.functionId &&

            <div className={styles.btnGroupContainer}>

                <div className={styles.btnGroup}>
                    <div className={`${styles.elementGroup}`}>
                        {funcCurrData.category.length > 0 ?
                            <div className={styles.currCategory}
                                 style={{
                                     backgroundColor: funcCurrData.color,
                                     width: `${funcCurrData.category.length * 20}px`
                                 }}>{`${funcCurrData.category}`}
                                <p onClick={() => updateElement(funcCurrData.functionId, 'none','category')} aria-hidden="true" role="presentation">×</p>
                            </div>
                            : <input className={styles.option} placeholder="옵션 검색"
                                     ref={inputRef}
                                     onChange={(e) => searchCategory(e.target.value)}
                                     onKeyDown={(event) => {
                                         handleKey(event);
                                     }}/>
                        }

                    </div>
                    <div>
                        <p>옵션 선택 또는 생성</p>
                        {filteredCategories.filter(category => category.category.trim() !== '').map(d =>
                            <div key={crypto.randomUUID()}
                                 className={`${styles.select} ${selected === d.functionId ? styles.whitesmoke : ''}`}
                                 onClick={() => updateElement(funcCurrData.functionId, d.functionId,'category')}
                                 aria-hidden="true"
                                 role="presentation"
                            >
                                {d.functionId !== 'temp' ? (
                                    <button
                                        className={styles.button}
                                        style={{backgroundColor: d.color}}
                                        key={crypto.randomUUID()} type='button'
                                        aria-hidden="true">{d.category}</button>
                                ) : (
                                    <>
                                        <span>생성</span>
                                        <button
                                            type='button'
                                            className={styles.button}
                                            style={{backgroundColor: d.color}}>
                                            {d.category}</button>
                                    </>
                                )}
                            </div>
                        )}
                    </div>
                </div>
            </div>
        }
    </>);
}
