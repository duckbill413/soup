'use client'

import { ProjectMemberImageUri, ProjectRes } from '@/types/project'
import * as styles from '@/containers/project.css'
import { useRouter } from 'next/navigation'
import Image from 'next/image'

const PROJECT_IMAGE_WIDTH = 450
const PROJECT_IMAGE_HIGHT = 300
const MEMBER_IMAGE = 44

export default function Project({ projectId, projectImageUri, projectName, projectMemberImageUris }: ProjectRes) {
  const router = useRouter()
  return (
    <div className={styles.container}>
      <button onClick={() => router.push(`/project/${projectId}/outline`)} type="button">
        {projectImageUri &&
          <Image unoptimized className={styles.projectImage} src={projectImageUri} alt="프로젝트 이미지" width={PROJECT_IMAGE_WIDTH}
                 height={PROJECT_IMAGE_HIGHT} />}
        <p className={styles.projectName}>{projectName}</p>
      </button>
      <div>
        {projectMemberImageUris?.map((member: ProjectMemberImageUri) =>
          member.projectMemberImageUri &&
          <Image unoptimized src={member.projectMemberImageUri} alt="프로젝트 멤버 이미지" width={MEMBER_IMAGE} height={MEMBER_IMAGE} />)

        }
      </div>
    </div>
  )
}