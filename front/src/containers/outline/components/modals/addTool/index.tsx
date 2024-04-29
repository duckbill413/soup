import * as styles from "@/containers/outline/styles/modals/outlineToolModal.css"
import closeIcon from '#/assets/icons/modals/closeIcon.svg'
import Image from 'next/image'

function OutlineToolModal (props: { clickModal: () => void }) {
  const { clickModal } = props;
  const handleOuterClick = (e: React.MouseEvent) => {
    if (e.target === e.currentTarget) {
      clickModal();
    }
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

        <div style={{display:'flex', flexDirection:'column',marginLeft:'5%', width:'95%', height:'30%'}}>
          <input placeholder="툴 이름을 입력해주세요." className={styles.toolInput}/>
          <input placeholder="URL 주소를 입력해주세요." className={styles.urlInput}/>
        </div>

        <div style={{display:'flex', height:'8%', marginTop:'20%', justifyContent:'center'}}>
          <button type="button" className={styles.button}>등록</button>
        </div>
      </div>
    </div>
  );
}

export default OutlineToolModal;
