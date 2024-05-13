import * as styles from "@/containers/outline/styles/table/outlineTable.css"
import { useEffect, useState } from 'react'
import { getProjectMembers } from '@/apis/member/memberAPI'
import { useParams } from 'next/navigation'
import { TeamMember } from '@/containers/outline/types/outlineAPI'
import Image from 'next/image'
import { useStorage } from '../../../../../liveblocks.config'

interface Props {
  showModal:boolean;
}

function TeamTable ({showModal}: Props) {
  const {projectId} = useParams()
  const [members, setMembers] = useState<TeamMember[]>([]);
  const teamStorage = useStorage((root)=>root.outline)

  useEffect (()=> {
    getProjectMembers(`${projectId}`).then(data => {setMembers(data)})
  },[projectId, showModal])

  const getRolesByEmail = (email: string) => {
    const teamMember = teamStorage?.project_team.find((member) => member.email === email);
    return teamMember ? teamMember.roles.map((role, index) => (
      <span key={`${role.role_id}-${index}`} className={styles.tdDetail}>{role.role_name}</span>
    )) : <span>역할 없음</span>;
  };

  return (
    <table>
      <thead>
      <tr>
        <th className={styles.tableToolTitle}>이름</th>
        <th className={styles.tableURLTitle}>역할</th>
        <th className={styles.tableURLTitle}>e-mail</th>
      </tr>
      </thead>
      <tbody>
      {members.map((row) => (
        <tr key={row.id}>
          <td className={styles.tdMemberName}>
            <Image unoptimized src={row.profileImageUrl} alt="profile" width={35} height={35} className={styles.tdMemberImage}/>
            <div>{row.nickname}</div>
          </td>
          <td>{getRolesByEmail(row.email)}</td>
          <td><a href={`mailto:${row.email}`}>{row.email}</a></td>
        </tr>
      ))}
      </tbody>
    </table>
  )
}

export default TeamTable