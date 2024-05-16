'use client'

import React, { useRef, useEffect } from 'react';
import { StreamManager } from 'openvidu-browser';

interface Props {
    streamManager: StreamManager;
}

function Audio({ streamManager }: Props) {
    const audioRef = useRef<HTMLVideoElement>(null);

    useEffect(() => {
        if (streamManager && audioRef.current) {
            streamManager.addVideoElement(audioRef.current);
        }
    }, [streamManager]);

    return (
        <audio autoPlay ref={audioRef}>
            <track kind="captions"/>
        </audio>
    );
}

export default Audio;