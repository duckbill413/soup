'use client';

import { getToken } from '@/apis/openVidu/openViduAPI';
import { useEffect, useState } from 'react';
import { OpenViduRes } from '@/containers/project/types/openVidu';
import { OpenVidu, Publisher, Session,
    StreamManager, } from 'openvidu-browser';

type Props = {
    projectId: string;
};

export default function SoundChat({ projectId }: Props) {
    const [OV, setOV] = useState<OpenVidu | null>(null);
    const [session, setSession] = useState<Session | null>(null);
    const [localStreamManager, setLocalStreamManager] = useState<StreamManager | null>(null);

    const joinSession = async (sessionData: OpenViduRes) => {
        if (!sessionData) return;
        setOV(new OpenVidu());
        if(!OV) return;
        const newSession = OV.initSession();
        setSession(newSession);
        try {
            const publisher = OV.initPublisher(undefined, {
                audioSource: true,
                videoSource: false
            });
            await newSession.connect(sessionData.token, { clientData: 'Your client name' });
            await newSession.publish(publisher);
            setLocalStreamManager(publisher);
        } catch (error) {
            console.error('Error connecting to session:', error);
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const sessionData = await getToken(projectId);
                await joinSession(sessionData);
            } catch (error) {
                console.log(error);
            }
        };
        fetchData();
        return () => {
            if (session) {
                session.disconnect();
            }
        };
    }, [projectId]);

    // 오디오 토글 함수
    const toggleAudio = () => {
        if (localStreamManager && localStreamManager instanceof Publisher) { // Publisher 인스턴스인지 확인
            const isAudioEnabled = localStreamManager.stream.audioActive;
            localStreamManager.publishAudio(!isAudioEnabled); // 오디오 상태 토글
            console.log(`Audio is now ${isAudioEnabled ? 'disabled' : 'enabled'}.`);
        }
    };


    return (
        <div>
            <button type="button" onClick={toggleAudio}>
                {localStreamManager && localStreamManager.stream.audioActive ? 'Mute' : 'Unmute'}
            </button>
        </div>
    );
}
