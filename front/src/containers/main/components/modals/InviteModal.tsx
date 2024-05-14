import * as styles from '@/containers/main/styles/inviteModal.css'
import Image from 'next/image'
import closeIcon from '#/assets/icons/modals/closeIcon.svg'
import { inviteProjectAPI } from '@/apis/project/projectAPI'
import { useState } from 'react'

function InviteModal (props: { clickModal: () => void }) {
  const { clickModal } = props;
  const [inviteCode, setInviteCode] = useState('')

  const handleOuterClick = (e: React.MouseEvent) => {
    if (e.target === e.currentTarget) {
      clickModal();
    }
  };

  const handleInvite = () => {
    const data = inviteProjectAPI(inviteCode)
    console.log(data)
  }

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInviteCode(e.target.value);
  };

  return (
    <div role="presentation" className={styles.modalContainer} onClick={handleOuterClick}>
      <div className={styles.modalSubContainer}>
        <div className={styles.topDivision}>
          <p className={styles.topSubTitle}>초대 코드 입력하기</p>
          <div role="presentation" className={styles.topSubXDiv} onClick={() => clickModal()}>
            <Image src={closeIcon} width={33} height={33} alt="X" />
          </div>
        </div>
        <hr />
        <div style={{display:'flex', flexDirection:'column', justifyContent:'center', height:'70%'}}>
          <div style={{display:'flex', justifyContent:'center'}}>
            <input placeholder="초대 코드를 입력해주세요."
                   className={styles.input}
                   onChange={handleInputChange}
                   value={inviteCode}
            />
          </div>
          <div style={{display:'flex', justifyContent:'flex-end'}}>
            <button type="button" className={styles.button} onClick={handleInvite}>확인</button>
          </div>
        </div>
      </div>
    </div>
  )
}

export default InviteModal