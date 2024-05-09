'use client'

import Header from '@/components/Header/Header'
import Project from '@/containers/Project'
import * as styles from '@/containers/page.css'
import { ProjectRes } from '@/types/project'
import getProjectList from "@/apis/project/projectAPI";
import {useEffect, useState} from "react";


export default function AfterAuth() {
  const [projects, setProjects] = useState<ProjectRes[]>([]);

  useEffect(() => {
    async function fetchData() {
      try {
        const data = await getProjectList();
        setProjects(data);
      } catch (error) {
        console.error('Error fetching project list:', error);
      }
    }
    fetchData();
  }, []);

  return (
    <div className={styles.container}>
      <Header theme="black" useVoice={false} />
      <div className={styles.content}>
        <p>내 프로젝트</p>
        <div className={styles.projects}>
          {projects.map(project => (
              <Project key={project.id} {...project} />
          ))}
        </div>
      </div>
    </div>
  )
}
