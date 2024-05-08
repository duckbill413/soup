'use client';

import {useEffect} from 'react';
import {FuncDescResWithColor} from '@/types/functionDesc';
import useFuncDescStore from "@/stores/useFuncDescStore";
import FuncTableColumn from '@/containers/func/FuncTableColumn/FuncTableColumn';
import {LiveObject} from "@liveblocks/client";
import * as styles from './funcTable.css';
import {useMutation, useStorage} from "../../../liveblocks.config";


export default function FuncTable() {

    const init:FuncDescResWithColor[] =useStorage((root)=>root.func) as FuncDescResWithColor[];
    const {funcDescData,filteredCategories,setIsPriorityModalVisible,setIsCategoryModalVisible,setFuncDescData} = useFuncDescStore();

    useEffect(() => {
        setFuncDescData(init);

    }, [init, setFuncDescData]);

    const updateElement = useMutation(({ storage },currId: string, changeId: string,attribute: string) => {
        const currData = storage.get("func")?.find(data=>data.get("functionId")===currId);
        const changeData = filteredCategories.find(data=>data.functionId===changeId);

        if(attribute==='add'){
            storage.get('func')?.push(new LiveObject<FuncDescResWithColor>({
                functionId: crypto.randomUUID(),
                category: "",
                description: "",
                point: 0,
                color: "",
                priority: "Medium",
                functionName: "",
                reporter: {    memberId: "",
                    memberNickname: "",
                    memberProfileUri: ""},}));
            return;
        }
        if(attribute==='functionName'){
            currData?.set('functionName',changeId);
            return;
        }
        if(attribute==='description'){
            currData?.set('description',changeId);
            return;
        }
        if(attribute==='point'){
            currData?.set('point',Number(changeId));
            return;
        }
        if(attribute==='priority'){
            setIsPriorityModalVisible('none');
            currData?.set('priority',changeId);
            return;
        }
        if(attribute==='category') {
            if(changeId==='none') {
                currData?.set('category','');
                currData?.set('color','');
            }
            else if(changeId==='temp'){
                if(!changeData) return;
                currData?.set('category',changeData.category);
                currData?.set('color',changeData.color);
                setIsCategoryModalVisible('none');
            }
            else{
                if(!changeData) return;
                currData?.set('category',changeData.category);
                currData?.set('color',changeData.color);
                setIsCategoryModalVisible('none');
            }
        }

    }, [filteredCategories]);


    return (
        <div className={styles.container}>

            <table className={styles.table}>
                <thead>
                <tr>
                    <th>카테고리</th>
                    <th>기능명</th>
                    <th>설명</th>
                    <th>포인트</th>
                    <th>우선순위</th>
                    <th>담당자</th>
                </tr>
                </thead>
                <tbody>
                {funcDescData.map((item, index) => (
                    <FuncTableColumn
                        key={index}
                        updateElement={updateElement}
                        funcCurrData={item}
                    />
                ))}
                <tr className={styles.createNew} onClick={()=>updateElement("","","add")}>
                    <td colSpan={6}>
                        <button type="button" >+ 새로 만들기</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    );

}
