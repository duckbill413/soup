import Image from "next/image"
import closeIcon from "#/assets/icons/modals/closeIcon.svg"
import * as styles from "@/containers/outline/styles/modals/outlineModal.css"
import sample from "#/assets/icons/mainpage/sample1.jpg"

function OutlineTeamModal (props: { clickModal: () => void }) {
  const { clickModal } = props;
  const handleOuterClick = (e: React.MouseEvent) => {
    if (e.target === e.currentTarget) {
      clickModal();
    }
  };

  return (
    <div role="presentation" className={styles.modalContainer} onClick={handleOuterClick}>
      <div className={styles.modalSubContainer}>
        {/* 팀원 초대하기 / X버튼 */}
        <div className={styles.topDivision}>
          <p className={styles.topSubTitle}>팀원 초대하기</p>
          <div role="presentation" className={styles.topSubXDiv} onClick={()=>clickModal()} >
            <Image src={closeIcon} width={33} height={33} alt="X"/>
          </div>
        </div>
        <hr/>
        {/* 역할, 이메일, 초대하기 */}
        <div className={styles.middleDivision}>
          <input className={styles.roleInput} placeholder="역할"/>
          <input className={styles.emailInput} placeholder="e-mail 주소"/>
          <button type="button" className={styles.invite}>초대하기</button>
        </div>

        <p className={styles.nowTeamTitle}>현재 프로젝트 팀원</p>
        {/* 현재 팀원 */}
        <div className={styles.bottomDivision}>
          <div className={styles.bottomProfile}>
            <Image className={styles.profileImg} src={sample} width={60} height={60} alt="profile"/>
            <div className={styles.profileDetail}>
              <p>석주짜응</p>
              <p>seokjugo@gmail.com</p>
            </div>
          </div>
          <div className={styles.roleSubDiv}>
            <div className={styles.role}>프론트엔드</div>
            <div className={styles.role}>곰</div>
          </div>
        </div>

      </div>
    </div>
  );
}

export default OutlineTeamModal;
