'use client'

import React, {useRef, useEffect} from 'react';
import dynamic from 'next/dynamic';
import mermaid from 'mermaid';
import {getReadMeTemplate} from "@/apis/readme/readmeAPI";
import {useMutation, useStorage} from "../../../liveblocks.config";

mermaid.initialize({
    startOnLoad: true,
    theme: 'default',
    securityLevel: 'loose',
});
// 서버 사이드 렌더링을 방지하기 위해 dynamic import 사용
const MDEditor = dynamic(() => import('@uiw/react-md-editor'), {ssr: false});

interface CodeProps {
    children: React.ReactNode;
    className?: string;
}


const getCode = (arr: React.ReactNode = []): string =>
    React.Children.toArray(arr)
        .map((dt) => {
            if (typeof dt === 'string') {
                return dt;
            }
            if (React.isValidElement(dt) && dt.props && dt.props.children) {
                return getCode(dt.props.children);
            }
            return '';
        })
        .filter(Boolean)
        .join('');


function Code({children = [], className}: CodeProps) {
    const demoid = useRef(`dome${parseInt(String(Math.random() * 1e15), 10).toString(36)}`);
    const code = getCode(children);
    const demo = useRef<HTMLDivElement>(null);

    useEffect(() => {
        if (demo.current) {
            try {
                const str = mermaid.render(demoid.current, code, () => null, demo.current);
                demo.current.innerHTML = str;
            } catch (error) {
                demo.current.innerHTML = error as string;
            }
        }
    }, [code]);


    if (
        typeof code === 'string' &&
        typeof className === 'string' &&
        /^language-mermaid/.test(className.toLocaleLowerCase())
    ) {
        return (
            <div ref={demo}>
                <div id={demoid.current} style={{display: 'none'}}/>
            </div>
        );
    }
    return <code className={String(className)}>{children}</code>;
}

type Props={
    projectId:string;
}
export default function Mermaid({projectId}:Props) {
    const init = useStorage((root) => root.readme)
    const updateElement = useMutation(({storage}, readme: string) => {
        const currData = storage.get('readme');
        currData?.set('json', readme);
    }, []);
    useEffect(() => {
        const fetchReadMeTemplate = async () => {
            if (!init?.json) {
                const readMeTemplate = await getReadMeTemplate(projectId);
                updateElement(readMeTemplate);
            }
        };
        fetchReadMeTemplate();
    }, []);

    return (
        <MDEditor
            onChange={(newValue = '') => updateElement(newValue || '')}
            textareaProps={{
                placeholder: 'Markdown 텍스트를 입력해주세요'
            }}
            height={700}
            value={init?.json}
            previewOptions={{
                components: {
                    code: Code
                }
            }}
        />
    );
};