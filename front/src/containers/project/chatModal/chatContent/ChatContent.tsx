import {ChatContentProps} from "@/containers/project/types/chat";
import dayjs from "dayjs";
import Image from "next/image";
import defaultImage from "#/assets/images/defaultProfile.png";
import * as styles from "./chatContent.css";

export default function ChatContent({chatMessageId,header, me, message, nickname, profileImageUrl, sentAt,myNickname,memberNicknames}:ChatContentProps){

    const highlightMention = (text:string) => {
        const regex = /(@\S+)/g;

        return text.split(regex).map((part, index) => {
            if (part.match(regex) && memberNicknames.find(data=>data===part.slice(1))) {
                if(myNickname===part.slice(1)){
                    return <span key={index} className={styles.myMention}>{part}</span>;
                }
                return <span key={index} className={styles.mentioned}>{part}</span>;
            } 
                return part;
            
        });
    };
    return(<div key={chatMessageId}>
            <div className={styles.chatHeader}>
                {header === new Date().toISOString().slice(0, 10) &&
                    <span className={styles.hrSect}>오늘</span>}
                {(header !== new Date().toISOString().slice(0, 10) && header) &&
                    <span className={styles.hrSect}>{dayjs(header).fromNow(true)}</span>}
            </div>
            {!me ?
                <div
                    key={chatMessageId}
                    className={styles.chatModalContentList.layout}
                >

                    <div className={styles.chatModalContentList.profile}>
                        {profileImageUrl &&
                            <Image
                                unoptimized
                                width={40}
                                height={40}
                                src={profileImageUrl || defaultImage}
                                alt="프로필"
                            />
                        }
                    </div>

                    <div className={styles.chatModalContentList.userArea}>
                        <p className={styles.chatModalContentList.nickname}>
                            {nickname}
                        </p>
                        <p className={styles.chatModalContentList.content}>
                            {message}
                        </p>
                    </div>

                    {sentAt &&

                        <p className={styles.chatModalContentList.time}>
                            {dayjs(sentAt.slice(0, 10)).isSame(dayjs(), 'day')
                                ? dayjs(sentAt).format('HH:mm')
                                : dayjs(sentAt).format('MM-DD')}
                        </p>
                    }
                </div>
                : <div
                    key={chatMessageId}
                    className={styles.chatModalContentList.layoutMe}
                >
                    <div className={styles.chatModalContentList.profile} />
                    {sentAt &&
                        <p className={styles.chatModalContentList.time}>

                            {dayjs(sentAt.slice(0, 10)).isSame(dayjs(), 'day')
                                ? dayjs(sentAt).format('HH:mm')
                                : dayjs(sentAt).format('MM-DD')}
                        </p>
                    }
                    <div className={styles.chatModalContentList.userArea}>
                        <p className={styles.chatModalContentList.content}>
                            {highlightMention(message)}
                        </p>
                    </div>
                </div>}
        </div>);
}