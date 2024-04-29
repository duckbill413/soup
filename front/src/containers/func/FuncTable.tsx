'use client'

import { useState } from 'react';
import { faker } from '@faker-js/faker';
import Image from 'next/image'
import * as styles from './funcTable.css';

interface FuncData {
  category: string;
  functionName: string;
  description: string;
  point: number;
  priority: string;
  manager: {
    memberId: number;
    memberProfileUri: string;
    memberNickname: string;
  };
  id: string;
}

const Member = [
  {
    memberId: 1,
    memberProfileUri: faker.image.avatarGitHub(),
    memberNickname: "최지우",
  },
  {
    memberId: 2,
    memberProfileUri: faker.image.avatarGitHub(),
    memberNickname: "정승원",
  }
];

const DummyData: FuncData[] = [
  {
    category: '회원관리',
    functionName: '소셜 로그인',
    description: '구글 소셜로그인을 적용합니다.',
    point: 3,
    priority: '넘높음',
    manager: Member[0],
    id: 'func1'
  },
  {
    category: '게시물 등록',
    functionName: '카테고리 검색',
    description: '사용자가 입력한 제목 기반',
    point: 3,
    priority: '넘높음',
    manager: Member[1],
    id: 'func2'
  }
];

export default function FuncTable() {
  const [data, setData] = useState(DummyData);

  const handleChange = (id: string, field: string, value: string) => {
    const newData = data.map(item => {
      if (item.id === id) {
        return { ...item, [field]: value };
      }
      return item;
    });
    setData(newData);
  };

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
        {data.map(dummy =>
          <tr key={dummy.id}>
            <td>{dummy.category}</td>
            <td>
              <input aria-label="input" type="text" value={dummy.functionName} onChange={(e) => handleChange(dummy.id, 'functionName', e.target.value)} />
            </td>
            <td>
              <input aria-label="input" type="text" value={dummy.description} onChange={(e) => handleChange(dummy.id, 'description', e.target.value)} />
            </td>
            <td>
              <input aria-label="input" type="text" value={dummy.point} onChange={(e) => handleChange(dummy.id, 'point', e.target.value)} />
            </td>
            <td>
              <input aria-label="input" type="text" value={dummy.priority} onChange={(e) => handleChange(dummy.id, 'priority', e.target.value)} />
            </td>
            <td>
              <div className={styles.manager}>
                <p>{dummy.manager.memberNickname}</p>
                <Image unoptimized src={dummy.manager.memberProfileUri} alt="프로필 이미지" width={30} height={30} />
              </div>
            </td>
          </tr>
        )}

        <tr className={styles.createNew}>
          <td colSpan={6}>+ 새로 만들기</td>
        </tr>
        </tbody>
      </table>
    </div>
  );
}
