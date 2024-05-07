'use client'

import { CategoryModalProps } from "@/types/functionDesc";
import { useEffect, useRef } from "react";
import useFuncDescCategoryStore from "@/stores/useFuncDescCategoryStore";
import * as styles from "./categoryModal.css";

export default function CategoryModal({ setSelected, updateElement, selected, handleKey, funcCurrData }: CategoryModalProps) {
    const { searchCategory, uniqueCategories, setFilteredCategories, filteredCategories, isCategoryModalVisible, setIsCategoryModalVisible } = useFuncDescCategoryStore();
    const inputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        setFilteredCategories(uniqueCategories);
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [funcCurrData,]);

    useEffect(() => {
        if (filteredCategories && filteredCategories.length > 0) {
            setSelected(filteredCategories[0].functionId);
        }
        if (!inputRef.current || funcCurrData.category !== "") return;

        inputRef.current.focus();

        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [filteredCategories, funcCurrData.category, isCategoryModalVisible, setSelected]);

    return (
        <>
            {isCategoryModalVisible !== 'none' && (
                <div onClick={() => setIsCategoryModalVisible('none')} className={styles.clickBackground} role="presentation" aria-hidden="true"/>
            )}
            {isCategoryModalVisible === funcCurrData.functionId &&
                <div className={styles.btnGroupContainer}>
                    <div className={styles.btnGroup}>
                        <div className={`${styles.elementGroup}`}>
                            {funcCurrData.category.length > 0 ?
                                <div className={styles.currCategory} style={{ backgroundColor: funcCurrData.color, width: `${funcCurrData.category.length * 20}px` }}>
                                    {`${funcCurrData.category}`}
                                    <div onClick={() => updateElement(funcCurrData.functionId, 'none', 'category')} role="presentation" aria-hidden="true" >×</div>
                                </div>
                                : <input className={styles.option} placeholder="옵션 검색" ref={inputRef} onChange={(e) => searchCategory(e.target.value)} onKeyDown={(event) => { handleKey(event); }} />
                            }
                        </div>
                        <div>
                            <p>옵션 선택 또는 생성</p>
                            {filteredCategories.filter(category => category.category.trim() !== '').map(d =>
                                <div
                                    key={d.functionId}
                                    className={`${styles.select} ${
                                        selected === d.functionId ? styles.whitesmoke : ""
                                    }`}
                                    onClick={() => updateElement(funcCurrData.functionId, d.functionId, "category")}
                                    onKeyDown={(event) => {
                                        if (event.key === "Enter") {
                                            updateElement(funcCurrData.functionId, d.functionId, "category");
                                        }
                                    }}
                                    role="button"
                                    tabIndex={0}
                                >
                                    {d.functionId !== "temp" ? (
                                        <button
                                            className={styles.button}
                                            style={{backgroundColor: d.color}}
                                            type="button"
                                        >
                                            {d.category}
                                        </button>
                                    ) : (
                                        <>
                                            <span>생성</span>
                                            <button
                                                className={styles.button}
                                                style={{backgroundColor: d.color}}
                                                type="button"
                                            >
                                                {d.category}
                                            </button>
                                        </>
                                    )}
                                </div>
                            )}
                        </div>
                    </div>
                </div>
            }
        </>
    );
}
