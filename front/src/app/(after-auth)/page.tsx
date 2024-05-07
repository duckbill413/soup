import Header from '@/components/Header/Header'
import Project from '@/containers/Project'
import * as styles from '@/containers/page.css'
import { ProjectRes } from '@/types/project'
import { faker } from '@faker-js/faker'

// TODO 지우기
const DummyData: ProjectRes[] = [
  {
    projectId: 1,
    projectName: '스타트 프로젝트, 스프',
    projectImageUri: faker.image.urlPicsumPhotos(),
    projectMemberImageUris: Array.from({ length: 3 }, () => ({
      projectMemberImageUri: faker.image.avatarGitHub(),
    })),
  },
  {
    projectId: 2,
    projectName: '멋진승원',
    projectImageUri: faker.image.urlPicsumPhotos(),
    projectMemberImageUris: Array.from({ length: 3 }, () => ({
      projectMemberImageUri: faker.image.avatarGitHub(),
    })),
  },
  {
    projectId: 3,
    projectName: '멋진승원',
    projectImageUri: faker.image.urlPicsumPhotos(),
    projectMemberImageUris: Array.from({ length: 3 }, () => ({
      projectMemberImageUri: faker.image.avatarGitHub(),
    })),
  },
  {
    projectId: 4,
    projectName: '멋진승원',
    projectImageUri: faker.image.urlPicsumPhotos(),
    projectMemberImageUris: Array.from({ length: 3 }, () => ({
      projectMemberImageUri: faker.image.avatarGitHub(),
    })),
  },
]

export default function AfterAuth() {
  return (
    <div className={styles.container}>
      <Header theme="black" useVoice={false} />
      <div className={styles.content}>
        <p>내 프로젝트</p>
        <div className={styles.projects}>
          {DummyData.map((data) => (
            <Project {...data} />
          ))}
        </div>
      </div>
    </div>
  )
}
