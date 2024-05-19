import * as styles from '@/containers/project/page.css'
import Header from '@/components/Header/Header'
import Navigation from '@/components/Navigation/Navigation'
import Chat from '@/containers/project/Chat'
import {ReactNode} from "react";
import SoundChat from "@/containers/project/SoundChat";

type Props = {
    children: ReactNode,
    params: { projectId: string },
}
export default function ProjectDetailLayout({children, params}: Props) {
    const {projectId} = params;

    return (
        <div className={styles.container}>
            <Navigation/>
            <Header theme='black' useVoice/>
            <div className={styles.content}>{children}</div>
            <Chat projectId={projectId}/>
            <SoundChat projectId={projectId}/>
        </div>
    )
}
