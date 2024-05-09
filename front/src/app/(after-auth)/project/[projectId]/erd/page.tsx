import {StepTitle} from '@/components/StepTitle/StepTitle'
import ERDDrawing from '@/containers/erd/ERDDrawing'
import * as styles from '@/containers/erd/erd.css'
import Room from "@/app/(after-auth)/project/[projectId]/erd/Room";
import Live from "@/components/cursor/Live";

export default function ERD() {
    return (
        <Room>
            <Live>
                <StepTitle
                    stepNum={5}
                    title="ERD"
                    desc="데이터베이스를 설계하세요"
                />
                <div className={styles.container}>
                    <ERDDrawing/>
                </div>
            </Live>
        </Room>
    )
}
