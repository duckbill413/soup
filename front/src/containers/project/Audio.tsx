'use client'

import React, { useRef, useEffect } from 'react';
import { StreamManager } from 'openvidu-browser';

interface Props {
    streamManager: StreamManager;
}

function Audio({ streamManager }: Props) {
    const audioRef = useRef<HTMLVideoElement>(null);

    useEffect(() => {
        // streamManager가 유효할 때만 작업 수행
        if (streamManager && audioRef.current) {
            streamManager.addVideoElement(audioRef.current);
        }
    }, [streamManager]); // streamManager가 변경될 때만 실행

    return (
        <audio autoPlay ref={audioRef}>
            <track kind="captions"/>
        </audio>
    );
}

export default Audio;