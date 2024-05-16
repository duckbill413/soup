'use client'

import { getToken } from "@/apis/openVidu/openViduAPI";
import { useEffect, useRef } from "react";
import { OpenViduRes } from "@/containers/project/types/openVidu";
import {
    OpenVidu,
} from "openvidu-browser";
import useAudioStore from "@/stores/useAudioStore";
import useMemberStore from "@/stores/useMemberStore";

type Props = {
    projectId: string;
}

export default function SoundChat({ projectId }: Props) {
    const {setLocalStreamManager} = useAudioStore();
    const remoteStreamRef = useRef<HTMLVideoElement>(null); // 참여자 비디오 스트림을 나타내기 위한 ref
    const {me} = useMemberStore();
    const joinSession = async (sessionData: OpenViduRes) => {
        if (!sessionData) return;

        const OV = new OpenVidu();
        const session = OV.initSession();
        session.on('streamCreated', (event: any) => {
            const subscriber = session.subscribe(event.stream, undefined);
            if (remoteStreamRef.current) {
                subscriber.addVideoElement(remoteStreamRef.current);
            }
        });

        try {
            const publisher = OV.initPublisher(undefined, {
                audioSource: true,
                videoSource: false
            });

            await session.connect(sessionData.token, { clientData: me?.id });
            session.publish(publisher);
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
                console.error('Error fetching session:', error);
            }
        };
        fetchData();

    }, [projectId]);





    return (
        <>
        </>
    );
}
