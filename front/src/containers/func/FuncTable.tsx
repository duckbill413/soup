'use client';

import {useEffect} from 'react';
import {FuncDescResWithColor} from '@/types/functionDesc';
import useFuncDescStore from "@/stores/useFuncDescStore";
import FuncTableColumn from '@/containers/func/FuncTableColumn/FuncTableColumn';
import {LiveObject} from "@liveblocks/client";
import {faker} from "@faker-js/faker";
import useMemberStore from "@/stores/useMemberStore";
import * as styles from './funcTable.css';
import {useMutation, useStorage} from "../../../liveblocks.config";

const MEMBER_INIT=[{
    memberId: crypto.randomUUID(),
    memberProfileUri: faker.image.avatarGitHub(),
    memberNickname: '최지우',

},{
    memberId: crypto.randomUUID(),
    memberProfileUri: faker.image.avatarGitHub(),
    memberNickname: '정승원'}]

const createNewFuncDescResWithColor = () => new LiveObject<FuncDescResWithColor>({
        functionId: crypto.randomUUID(),
        category: "",
        description: "",
        point: 0,
        color: "",
        priority: "Medium",
        functionName: "",
        reporter: { memberId: "", memberNickname: "", memberProfileUri: "" },
    });

export default function FuncTable() {

    const init:FuncDescResWithColor[] =useStorage((root)=>root.func) as FuncDescResWithColor[];
    const {funcDescData,filteredCategories,setIsPriorityModalVisible,setIsCategoryModalVisible,setFuncDescData} = useFuncDescStore();
    const {members,setMembers,setIsMemberModalVisible} = useMemberStore();
    useEffect(() => {
        setFuncDescData(init);
        setMembers(MEMBER_INIT);
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
            currData.set('reporter', { memberId: "", memberNickname: "", memberProfileUri: "" });
        } else {
            setIsMemberModalVisible('none');
            const selectedMember = members.find(data => data.memberId === changeId);
            if (selectedMember) {
                currData.set('reporter', { memberId: selectedMember.memberId, memberNickname: selectedMember.memberNickname, memberProfileUri: selectedMember.memberProfileUri });
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

