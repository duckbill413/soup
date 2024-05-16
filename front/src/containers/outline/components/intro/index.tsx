'use client'

import * as styles from "@/containers/outline/styles/intro/outlineIntro.css"
import basic from '#/assets/icons/outline/basicPhoto.svg'
import { useRef } from 'react'
import { changePhotoAPI } from '@/apis/outline/outlineAPI'
import { TextField } from '@mui/material'
import { useMutation, useStorage } from '../../../../../liveblocks.config'

function OutlineIntro () {
  const initialProject =useStorage((root)=>root.outline)
  const basicSrc = basic.src

  const updateProject = useMutation(({ storage }, key, newValue) => {
    const outline = storage.get('outline')
    outline?.set(key, newValue)
  }, [])

  const imgRef = useRef<HTMLInputElement>(null);
  const handlePhoto  = () => {
    imgRef.current?.click();
  }
  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const selectedFile = e.target.files?.[0];
    if (!selectedFile) return;
    if (selectedFile.size > 100 * 1024 * 1024) { // 100MB 제한 체크
      alert("파일 크기가 너무 큽니다. 100MB 이하의 파일만 업로드 가능합니다.");
      return;
    }

    const formData = new FormData();
    formData.append('files', selectedFile);
    changePhotoAPI(formData)
      .then(data =>{
        if (data.result && data.result[0]) {
          updateProject('project_photo', data.result[0]);
        }
      }).catch(error => alert(`업로드 실패: ${ error.message}`));
  }

  return (
    <div className={styles.container}>
      <div className={styles.photoDivision}>
        <p>사진</p>
        <button type="button" onClick={handlePhoto}>
          <img src={initialProject?.project_photo || basicSrc} alt="Project" width={355} height={258} className={styles.img} />
        </button>
        <input
          ref={imgRef}
          type="file"
          onChange={handleFileChange}
          style={{ display: 'none' }}
          accept="image/png, image/jpeg, image/webp"
        />
      </div>
      <div className={styles.introDivision}>
        <p>프로젝트 이름</p>
        <TextField
          label="프로젝트 이름"
          value={initialProject?.project_name}
          onChange={(e) => updateProject('project_name', e.target.value)}
        />
        <p>프로젝트 설명</p>
        <TextField
          multiline
          rows={4}
          label="프로젝트 설명"
          value={initialProject?.project_description}
          onChange={(e) => updateProject('project_description', e.target.value)}
        />
      </div>
    </div>
  )
}

export default OutlineIntro
