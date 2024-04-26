import * as styles from "@/containers/outline/styles/modals/outlineModal.css"

function OutlineModal (props: { clickModal: () => void }) {
  const { clickModal } = props;
  const handleOuterClick = (e: React.MouseEvent) => {
    if (e.target === e.currentTarget) {
      clickModal();
    }
  };

  const clickConfirm = () => {
    clickModal();
  }

  return (
    <div className={styles.modalContainer} role="presentation" onClick={handleOuterClick}>
      <div className="SearchModalContent">
        <div>팀 추가 모달</div> {/* 모달 제목이나 헤더 */}
        <input type="text" placeholder="팀 이름" /> {/* 팀 이름을 위한 입력 필드 */}
        <div>
          <button type="button" onClick={clickConfirm}>확인</button> {/* 확인 버튼 */}
          <button type="button" onClick={clickModal}>취소</button> {/* 취소 버튼 */}
        </div>
      </div>
    </div>
  );
}

export default OutlineModal;
