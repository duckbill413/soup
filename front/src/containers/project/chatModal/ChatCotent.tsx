import * as styles from '@/containers/project/chatModal/chatContent.css'
import Image from 'next/image'
import sendSvg from '#/assets/icons/chat/send.svg'
import { faker } from '@faker-js/faker'
import dayjs from 'dayjs'
import relativeTime from 'dayjs/plugin/relativeTime'
import 'dayjs/locale/ko'

dayjs.extend(relativeTime)
dayjs.locale('ko')

const chatList = [
  {
    memberNickname: '정승원',
    chatContent: '안녕하세요안녕하세요하세요안녕하세요안녕하세요하세요',
    memberProfileImage: faker.image.avatar(),
  },
  {
    memberNickname: '최지우',
    chatContent: 'ㅎㅇ',
    memberProfileImage: faker.image.avatar(),
    chatCreatedAt: new Date(),
  },
]

type Props = {
  isVisible: boolean | null
}
export default function ChatContent({ isVisible }: Props) {
  // Animation상태는 세 종류 1.첫 시작(first) 2. 열 때(after), 3. 닫을 때(before)
  const getModalStyle = () => {
    if (isVisible) {
      return styles.chatModalAnimation.after;
    }
    if (isVisible === null) {
      return styles.chatModalAnimation.first;
    }
    return styles.chatModalAnimation.before;
  };


  return (
    <div
      className={`${isVisible !== null && styles.chatModal} ${getModalStyle()}`}
    >
      {isVisible && (
        <>
          <div className={styles.chatModalContent.header}>채팅</div>
          <div className={styles.chatModalContent.background}>
            <div>
              {chatList.map((chat) => (
                <div
                  key={crypto.randomUUID()}
                  className={styles.chatModalContentList.layout}
                >
                  <div className={styles.chatModalContentList.profile}>
                    <Image unoptimized width={44} height={44} src={chat.memberProfileImage} alt="프로필" />
                  </div>
                  <div className={styles.chatModalContentList.userArea}>
                    <p className={styles.chatModalContentList.nickname}>
                      {chat.memberNickname}
                    </p>
                    <p className={styles.chatModalContentList.content}>
                      {chat.chatContent}
                    </p>
                  </div>
                  <p className={styles.chatModalContentList.time}>11:30</p>
                </div>
              ))}
            </div>
          </div>
          <div className={styles.chatModalContent.send}>
            <div>
              <input className={styles.input} placeholder="메시지 입력" />
              <Image
                style={{ cursor: 'pointer' }}
                src={sendSvg}
                alt="send-image"
                width={22}
                height={22}
              />
            </div>
          </div>
        </>
      )}
    </div>
  )
}
