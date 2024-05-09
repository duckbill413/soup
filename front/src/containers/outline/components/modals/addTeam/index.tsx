import Image from "next/image"
import closeIcon from "#/assets/icons/modals/closeIcon.svg"
import * as styles from "@/containers/outline/styles/modals/outlineModal.css"
import sample from "#/assets/icons/mainpage/sample1.jpg"
import { ChangeEvent, KeyboardEvent, useState } from 'react'
import { inviteMemberAPI } from '@/apis/outline/outlineAPI'
// import { useMutation } from '../../../../../../liveblocks.config'

function OutlineTeamModal (props: { clickModal: () => void }) {
  const { clickModal } = props;
  const [auth, setAuth] =useState<string>('DEVELOPER')
  const [nameInput, setNameInput] = useState<string>('');
  const [roleInput, setRoleInput] = useState<string>('');
  const [emailInput, setEmailInput] = useState<string>('')
  const [tags, setTags] = useState<Array<{ id: string, role_name: string }>>([]);

  // const updateTeam = useMutation(({ storage }) => {
  //   storage.get("outline")?.get("project_team")
  // }, []);

  const handleOuterClick = (e: React.MouseEvent) => {
    if (e.target === e.currentTarget) {
      clickModal();
    }
  };

  const addHashtag = (e: KeyboardEvent<HTMLInputElement>) => {
    if (e.key === 'Enter' && roleInput.trim() !== '') {
      const newTag = { id: crypto.randomUUID(), role_name: roleInput };
      setTags(prevTags => [...prevTags, newTag]);
      setRoleInput('');
      e.preventDefault();
      console.log(tags)
    }
  }
  const removeTag = (tagId: string) => {
    setTags(prevTags => prevTags.filter(tag => tag.id !== tagId));
  }

  const changeName = (e: ChangeEvent<HTMLInputElement>) => {setNameInput(e.target.value)}
  const changeRole = (e: ChangeEvent<HTMLInputElement>) => {setRoleInput(e.target.value)}
  const changeEmail = (e: ChangeEvent<HTMLInputElement>) => { setEmailInput(e.target.value) };
  const changeAuth = (e: ChangeEvent<HTMLSelectElement>) => {
    const selectedAuth = e.target.value === 'ADMIN' ? 'ADMIN' : 'DEVELOPER';
    setAuth(selectedAuth);
  };

  const invite = () => {
    inviteMemberAPI('663b23d4fd804b719071533e',{email:emailInput,roles:[`${auth}`]})
      .then(response=> {console.log(response)})
      .catch(error=>alert(`초대하기 실패: , ${error.message}`))

  }

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
          <div style={{ display: 'flex', flexDirection: 'column' }}>
            <div>JIRA 권한</div>
            <select style={{ borderRadius: '8px' }} onChange={changeAuth}>
              <option value="DEVELOPER">No</option>
              <option value="ADMIN">Yes</option>
            </select>
          </div>
          <input className={styles.roleInput} placeholder="엔터로 역할 추가"
                 value={roleInput}
                 onChange={changeRole}
                 onKeyDown={addHashtag}
          />
          <input className={styles.nameInput} placeholder="이름"
                 value={nameInput}
                 onChange={changeName}
          />
          <input className={styles.emailInput} placeholder="e-mail 주소"
                 value={emailInput}
                 onChange={changeEmail}
          />
          <button type="button" className={styles.invite} onClick={invite}>초대하기</button>
        </div>

        <div style={{ display: 'flex', flexWrap: 'wrap' }}>
          {tags.map(tag => (
            <div key={tag.id} className={styles.role}>
            {tag.role_name}
            <button type="button" style={{ color: '#515455' }} onClick={() => removeTag(tag.id)}>✖</button>
            </div>
          ))}
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
