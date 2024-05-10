import * as styles from "@/containers/outline/styles/modals/outlineToolModal.css"
import closeIcon from '#/assets/icons/modals/closeIcon.svg'
import Image from 'next/image'
import { useState } from 'react'
import { LiveObject } from '@liveblocks/client'
import { ProjectTool } from '@/containers/outline/types/outlineStorage'
import { Toast } from '@/utils/toast'
import { useMutation } from '../../../../../../liveblocks.config'

function OutlineToolModal (props: { clickModal: () => void }) {
  const { clickModal } = props;
  const [toolName, setToolName] = useState("");
  const [toolURL, setToolURL] = useState("");
  const addTool = useMutation(({ storage }, tool) => {
    storage.get("outline")?.get("project_tools").push(new LiveObject<ProjectTool>(tool))
  }, []);


  const handleAddTool = () => {
    if (toolName) {
      addTool({
        id: crypto.randomUUID(),
        name: toolName,
        url: toolURL,
      });
      clickModal();
    } else {
      Toast.error("툴 이름은 필수 입력값입니다!");
    }
  };

  const handleKeyPress = (e:React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter') handleAddTool();
  };

  const handleOuterClick = (e: React.MouseEvent) => {
    if (e.target === e.currentTarget) clickModal();
  };

  return (
    <div className={styles.modalContainer} role="presentation" onClick={handleOuterClick}>
      <div className={styles.modalSubContainer}>
        <div className={styles.topDivision}>
          <p className={styles.topSubTitle}>협업 툴 등록</p>
          <div role="presentation" className={styles.topSubXDiv} onClick={() => clickModal()}>
            <Image src={closeIcon} width={33} height={33} alt="X" />
          </div>
        </div>
        <hr />

        <div className={styles.inputMainDiv}>
          <input placeholder="툴 이름을 입력해주세요." className={styles.toolInput} value={toolName}
                 onChange={(e) => setToolName(e.target.value)}/>
          <input placeholder="URL 주소를 입력해주세요." className={styles.urlInput}  value={toolURL}
                 onChange={(e) => setToolURL(e.target.value)}
                 onKeyDown={handleKeyPress}
          />
          <div className={styles.inputSubDiv}>
            <button type="button" className={styles.button} onClick={handleAddTool}>등록</button>
          </div>
        </div>

      </div>
    </div>
  );
}

export default OutlineToolModal;
