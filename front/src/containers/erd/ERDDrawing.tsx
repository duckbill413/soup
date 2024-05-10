'use client'

import { useEffect, useState } from 'react';
import { ErdEditorElement } from '@dineug/erd-editor';
import * as styles from '@/containers/erd/erd.css';
import {updateERD} from "@/apis/erd/erdAPI";
import {useMutation, useStorage} from "../../../liveblocks.config";

type Props={
  projectId:string;
}
export default function ERDDrawing({projectId}:Props) {
  const init = useStorage((root)=>root.erd)
  const [editor, setEditor] = useState<ErdEditorElement | null>(null);
  const [initStart, setInitStart] = useState<boolean>(false);
  const updateElement = useMutation(({ storage },erd:string ) => {
    const currData = storage.get('erd');
    currData?.set('json',erd);
  }, []);
  const update = ()=>{
    if(init?.json && init?.json!==''){
      editor?.setInitialValue(init?.json);
    }
  }
  useEffect(() => {
      update();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [init,initStart]);


  useEffect(() => {
    if (editor) {
      const handleChange = () => {
        updateElement(editor.value);
      };
      editor.addEventListener('change', handleChange);
      return () => {
        editor.removeEventListener('change', handleChange);
      };
    }
    return () => {};
  }, [editor,updateElement]);

  useEffect(() => {
    const generateVuerd = async () => {
      const container: any = document.querySelector('#app-erd');
      if (!container) return;

      if (container.children.item(0)) {
        container.removeChild(container.children.item(0));
      }

      const newEditor = document.createElement('erd-editor');
      container.appendChild(newEditor);
      newEditor.systemDarkMode = false;
      newEditor.setInitialValue('');
      setEditor(newEditor);
      setInitStart(true);
    };

    const loadErdEditor = async () => {
      await import('@dineug/erd-editor');

      generateVuerd();
    };

    loadErdEditor();

    return () => {
      if(init){
        const initString = typeof init === 'string' ? init : init.json;
        updateERD(projectId, initString);
      }
      editor?.destroy();
    };

    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return <div className={styles.container} id="app-erd" />;
}
