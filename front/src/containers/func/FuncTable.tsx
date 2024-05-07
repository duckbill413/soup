'use client';

import {faker} from "@faker-js/faker";

import {useEffect} from 'react';
import {FuncDescResWithColor} from '@/types/functionDesc';
import useFuncDescCategoryStore from "@/stores/useFuncDescCategoryStore";
import FuncTableColumn from '@/containers/func/FuncTableColumn/FuncTableColumn';
import * as styles from './funcTable.css';
import {useMutation, useStorage} from "../../../liveblocks.config";

const Member = [
    {
        memberId: crypto.randomUUID(),
        memberProfileUri: faker.image.avatarGitHub(),
        memberNickname: '최지우',
    },
    {
        memberId: crypto.randomUUID(),
        memberProfileUri: faker.image.avatarGitHub(),
        memberNickname: '정승원',
    },
    {
        memberId: crypto.randomUUID(),
        memberProfileUri: faker.image.avatarGitHub(),
        memberNickname: '정승원',
    },
];
// 가짜 기능 데이터
const DummyData: FuncDescResWithColor[] = [
    {
        functionId: crypto.randomUUID(),
        category: '회원관리',
        functionName: '소셜 로그인',
        description: '구글 소셜로그인을 적용합니다.',
        point: 3,
        priority: '넘높음',
        reporter: Member[0],
        color:'#F6CECE',
    },
    {
        functionId: crypto.randomUUID(),
        category: '게시물 등록',
        functionName: '카테고리 검색',
        description: '사용자가 입력한 제목 기반',
        point: 3,
        priority: '넘높음',
        reporter: Member[1],
        color:'#F8ECE0'
    },
    {
        functionId: crypto.randomUUID(),
        category: '게시물 ',
        functionName: '카테고리 검색',
        description: '사용자가 입력한 제목 기반',
        point: 3,
        priority: '넘높음',
        reporter: Member[2],
        color: '#F5F6CE'
    },

];

export default function FuncTable() {
    const init:FuncDescResWithColor[] =useStorage((root)=>root.func) as FuncDescResWithColor[];
    const {funcDescData,filteredCategories,setFuncDescData} = useFuncDescCategoryStore();


    useEffect(() => {
        // 초기 데이터 세팅
        setFuncDescData(DummyData);
    }, [setFuncDescData]);

    useEffect(() => {
        setFuncDescData(init);
    }, [init, setFuncDescData]);

    const update = useMutation(({ storage }, newFuncData: FuncDescResWithColor[]) => {
        const funcList = storage.get("func");
        if (funcList) {
            funcList.clear();
            newFuncData.forEach(data => {
                funcList.push(data);
            });
        }
    }, []);
    const updateElement = (currId: string, changeId: string,attribute: string) => {
        let updatedData = funcDescData;
        if (attribute === 'category') {
            if (changeId === 'none') {
                updatedData = funcDescData.map(item => ({
                    ...item,
                    category: item.functionId === currId ? '' : item.category,
                    color: item.functionId === currId ? '' : item.color,
                }));
            } else {
                const changeData = filteredCategories.find(item => item.functionId === changeId);
                if (changeData) {
                    updatedData = funcDescData.map(item => {
                        if (changeId !== 'temp' && item.functionId === currId) {
                            return {
                                ...item,
                                category: changeData.category,
                                color: changeData.color,
                            };
                        } if (item.category === '' || item.category === changeData.category) {
                            return {
                                ...item,
                                category: changeData.category,
                                color: changeData.color,
                            };
                        } 
                            return item;
                        
                    });
                }
            }
        }

        if(attribute==="functionName"){
            updatedData = funcDescData.map(item=>({
                ...item,
                functionName: (currId===item.functionId) ? changeId : item.functionName
            }))
        }
        update(updatedData);
    }



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
                <tr className={styles.createNew}>
                    <td colSpan={6}>
                        <button type="button">+ 새로 만들기</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    );

}
