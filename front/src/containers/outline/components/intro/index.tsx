"use client"

import * as styles from "@/containers/outline/styles/intro/outlineIntro.css"
import sample from '#/assets/icons/mainpage/sample1.jpg'
import { useEffect } from 'react'
import getOutlineInfoAPI from '@/apis/outline/outlineAPI'
import { useMutation, useStorage } from '../../../../../liveblocks.config'

function OutlineIntro () {
  const initialProject =useStorage((root)=>root.outline)
  const sampleSrc = sample.src

  const updateProjectName = useMutation(({ storage }, newName: string) => {
    // eslint-disable-next-line @typescript-eslint/no-shadow
    const newProName = storage.get("outline");
    newProName.set("name", newName);
  }, []);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getOutlineInfoAPI('663345425249cc4b837d65ad');
        console.log(data);
      } catch (error) {
        console.error('Error fetching data', error);
      }
    };
    fetchData();
  }, [])
  return (
    <div className={styles.container}>
      <div className={styles.photoDivision}>
        <p>사진</p>
        <img src={sampleSrc} alt="Project" width={355} height={258} className={styles.img} />
      </div>
      <div className={styles.introDivision}>
        <p>프로젝트 이름</p>
        <input placeholder="프로젝트 이름"
               value={initialProject.name}
               className={styles.input}
               onChange={(e) => updateProjectName(e.target.value)}
        />
        <p>프로젝트 설명</p>
        <textarea placeholder="프로젝트 설명"
                  className={styles.textarea}
        />
      </div>
    </div>
  )
}

export default OutlineIntro