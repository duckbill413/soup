'use client'

import { StepTitleWithGuide } from '@/components/StepTitle/StepTitle'
import Image from 'next/image'
import jiraSVG from '#/assets/icons/func/jira.svg'
import * as styles from '@/containers/func/page.css'
import FuncTable from '@/containers/func/FuncTable'
import IconButton from '@/components/IconButton'
import { FilterIcon } from '#/assets/icons'
import Room from '@/app/(after-auth)/project/[projectId]/func/Room'
import Live from '@/components/cursor/Live'
import useMemberStore from "@/stores/useMemberStore";
import {useEffect, useState} from "react";
import {synchronizationJira} from "@/apis/jira/jiraAPI";

type Props = {
  params: { projectId: string },
}
export default function Func({params}:Props) {
  const [isAdmin, setIsAdmin] = useState<boolean>(false);
  const {me,chatMembers} = useMemberStore();
  const {projectId} = params;

  useEffect(() => {
    chatMembers.some(member => {
      if (me && member.id === me.id) {
        setIsAdmin(member.roles?.some(data => data === 'ADMIN'));
        return true;
      }
      return false;
    });
  }, [me,chatMembers]);
  const handleSynchronization = () =>{
    synchronizationJira(projectId).then(data=>{
        console.log(data);
    })
  }
  return (
    <Room>
      <Live>
        <StepTitleWithGuide
          stepNum={3}
          title="기능 명세서"
          desc="필요한 기능을 정리하고 JIRA와 연동해보세요"
          guideTitle="기능 명세서 작성 가이드"
        />
        <div className={styles.tableHeader}>
          <IconButton name="정렬 기준" eventHandler="/sort">
            <FilterIcon color="currentColor" />
          </IconButton>
          <div role="presentation" onClick={handleSynchronization}>

            {isAdmin && <><Image src={jiraSVG} alt="지라 아이콘" /><p>JIRA 동기화</p></>}
          </div>
        </div>
        <FuncTable />
      </Live>
    </Room>
  )
}
