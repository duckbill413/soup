'use client'

import { ProjectMemberImageUri, ProjectRes } from '@/types/project'
import * as styles from '@/containers/project.css'
import { useRouter } from 'next/navigation'
import Image from 'next/image'
import {faker} from "@faker-js/faker";

const PROJECT_IMAGE_WIDTH = 450
const PROJECT_IMAGE_HIGHT = 300
const MEMBER_IMAGE = 44

export default function Project({ id,name,imgUrl,projectMemberImageUrls }: ProjectRes) {
  const router = useRouter()
  return (
    <div className={styles.container}>
      <button onClick={() => router.push(`/project/${id}/outline`)} type="button">
        {imgUrl ?
          <Image unoptimized className={styles.projectImage} src={imgUrl} alt="프로젝트 이미지" width={PROJECT_IMAGE_WIDTH}
                 height={PROJECT_IMAGE_HIGHT} />
            :
            // Todo: Project 기본이미지 뭐 할 지?
            <Image unoptimized className={styles.projectImage} src={faker.image.urlPicsumPhotos()} alt="프로젝트 이미지" width={PROJECT_IMAGE_WIDTH}
                   height={PROJECT_IMAGE_HIGHT} />
          }
        <p className={styles.projectName}>{name}</p>
      </button>
      <div>
        {projectMemberImageUrls?.map((member: ProjectMemberImageUri) =>
          member.projectMemberImageUrl &&
          <Image unoptimized src={member.projectMemberImageUrl} alt="프로젝트 멤버 이미지" width={MEMBER_IMAGE} height={MEMBER_IMAGE} />)

        }
      </div>
    </div>
  )
}