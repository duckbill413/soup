'use client';

import {useEffect} from 'react';
import {FuncDescResWithColor} from '@/containers/func/types/functionDesc';
import useFuncDescStore from "@/stores/useFuncDescStore";
import FuncTableColumn from '@/containers/func/FuncTableColumn/FuncTableColumn';
import {LiveObject} from "@liveblocks/client";
import useMemberStore from "@/stores/useMemberStore";
import * as styles from './funcTable.css';
import {useMutation, useStorage} from "../../../liveblocks.config";


const createNewFuncDescResWithColor = () => new LiveObject<FuncDescResWithColor>({
        functionId: crypto.randomUUID(),
        category: "",
        description: "",
        point: 0,
        color: "",
        priority: "Medium",
        functionName: "",
        reporter: { id: "", nickname: "", profileImageUrl: "" },
    });

export default function FuncTable() {

    const init:FuncDescResWithColor[] =useStorage((root)=>root.func) as FuncDescResWithColor[];
    const {filteredCategories,setIsPriorityModalVisible,setIsCategoryModalVisible,setFuncDescData} = useFuncDescStore();
    const {members,setMembers,setIsMemberModalVisible} = useMemberStore();
    useEffect(() => {
        setFuncDescData(init);
    }, [init, setFuncDescData,setMembers]);

    const updateCategory = (currData: LiveObject<FuncDescResWithColor> | undefined, changeId: string) => {
        if (!currData) return;
        if (changeId === 'none') {
            currData.set('category', '');
            currData.set('color', '');
        } else {
            const selectedCategory = filteredCategories.find(data => data.functionId === changeId);
            if (selectedCategory) {
                setIsCategoryModalVisible('none');
                currData.set('category', selectedCategory.category);
                currData.set('color', selectedCategory.color);
            }
        }
    };

    const updateReporter = (currData: LiveObject<FuncDescResWithColor> | undefined, changeId: string) => {
        if (!currData) return;
        if (changeId === 'none') {
            currData.set('reporter', { id: "", nickname: "", profileImageUrl: "", });
        } else {
            setIsMemberModalVisible('none');
            const selectedMember = members.find(data => data.id === changeId);
            if (selectedMember) {
                currData.set('reporter', { id: selectedMember.id, nickname: selectedMember.nickname, profileImageUrl: selectedMember.profileImageUrl });
            }
        }
    };
    const updateElement = useMutation(({ storage }, currId: string, changeId: string, attribute: string) => {
        const currData = storage.get("func")?.find(data => data.get("functionId") === currId);
        switch (attribute) {
            case 'add':
                storage.get('func')?.push(createNewFuncDescResWithColor());
                break;
            case 'functionName':
                currData?.set('functionName', changeId);
                break;
            case 'description':
                currData?.set('description', changeId);
                break;
            case 'point':
                currData?.set('point', Number(changeId));
                break;
            case 'priority':
                setIsPriorityModalVisible('none');
                currData?.set('priority', changeId);
                break;
            case 'category':
                updateCategory(currData, changeId);
                break;
            case 'reporter':
                updateReporter(currData, changeId);
                break;
            default:
                break;
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
                {init.map((item, index) => (
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

