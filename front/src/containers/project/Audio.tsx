import React, { useRef, useEffect } from 'react';
import { StreamManager } from 'openvidu-browser';

interface Props {
    streamManager: StreamManager;
}

function Audio({ streamManager }: Props) {
    const videoRef = useRef<HTMLVideoElement>(null);
    const autoplay = true;

    useEffect(() => {
        if (streamManager && videoRef.current) {
            streamManager.addVideoElement(videoRef.current);
        }
    }, [streamManager]);

    return (
        <video autoPlay={autoplay} ref={videoRef} style={{ width: '50%' }}>
            <track kind="captions" />
        </video>
    );
}

export default Audio;