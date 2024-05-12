'use client'

import {getToken} from "@/apis/openVidu/openViduAPI";
import {useEffect, useState} from "react";
import {OpenViduRes} from "@/containers/project/types/openVidu";
import {
    OpenVidu,
    Publisher,
    Session,
    StreamManager,
} from "openvidu-browser";

type Props = {
    projectId: string;
}

export default function SoundChat({projectId}: Props) {
    const [OV, setOV] = useState<OpenVidu | null>(null);
    const [session, setSession] = useState<Session | null>(null);
    const [localStreamManager, setLocalStreamManager] = useState<StreamManager | null>(null);
    // const remoteStreamRef = useRef<HTMLVideoElement>(null); // 참여자 비디오 스트림을 나타내기 위한 ref

    const joinSession = async (sessionData: OpenViduRes) => {
        if (!sessionData) return;
        setOV(new OpenVidu());
        if (!OV) return;
        setSession(OV.initSession());
        if (!session) return;
        // session.on('streamCreated', (event: any) => {
        //     const subscriber = session.subscribe(event.stream, undefined);
        //
        // });


        // 퍼블리셔 초기화 (오디오 및 비디오 활성화)
        const publisher = OV.initPublisher(undefined, {
            audioSource: true,
            videoSource: false
        });

        // 세션에 연결
        await session.connect(sessionData.token, {clientData: Math.random()});
        session.publish(publisher);
        setLocalStreamManager(publisher);


        // setupMicrophone();
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const sessionData = await getToken(projectId);
                joinSession(sessionData);
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


    // 마이크 볼륨 모니터링 함수
    // const setupMicrophone = () => {
    //     navigator.mediaDevices.getUserMedia({audio: true})
    //         .then(stream => {
    //             const audioContext = new AudioContext();
    //             const microphone = audioContext.createMediaStreamSource(stream);
    //             const analyser = audioContext.createAnalyser();
    //
    //             analyser.fftSize = 256;
    //             const bufferLength = analyser.frequencyBinCount;
    //             const dataArray = new Uint8Array(bufferLength);
    //
    //             microphone.connect(analyser);
    //
    //             const updateVolume = () => {
    //                 analyser.getByteFrequencyData(dataArray);
    //                 const average = dataArray.reduce((sum, value) => sum + value, 0) / bufferLength;
    //                 console.log('>>>>>>볼륨: ' + average);
    //             };
    //
    //             setInterval(updateVolume, 500);
    //         })
    //         .catch(error => {
    //             console.error('마이크 접근 중 오류 발생:', error);
    //         });
    // };

    // 오디오 토글 함수
    const toggleAudio = () => {
        if (localStreamManager && localStreamManager instanceof Publisher) { // Publisher 인스턴스인지 확인
            const isAudioEnabled = localStreamManager.stream.audioActive;
            localStreamManager.publishAudio(!isAudioEnabled); // 오디오 상태 토글

        }
    };

    return (
        <div>

                <button type='button'
                    onClick={toggleAudio}>{localStreamManager && localStreamManager.stream.audioActive ? 'Mute' : 'Unmute'}</button>
                {/* <br/> */}
                {/* <br/> */}
                {/* <br/> */}
            </div>
    );
}
