'use client'

import {getToken, leaveSession} from "@/apis/openVidu/openViduAPI";
import React, {useCallback, useEffect, useState} from "react";

import {
    OpenVidu,
    Session as OVSession, StreamManager,
} from 'openvidu-browser';
import useAudioStore from "@/stores/useAudioStore";
import {OpenViduRes} from "@/containers/project/types/openVidu";
import Audio from "@/containers/project/Audio";

type Props = {
    projectId: string;
}

export default function SoundChat({projectId}: Props) {
    const {publisher, setPublisher} = useAudioStore();
    const [session, setSession] = useState<OVSession | ''>('');
    const [, setSessionId] = useState<string>('');
    const [subscribers, setSubscribers] = useState<StreamManager[]>([]);
    const [OV, setOV] = useState<OpenVidu | null>(null);
    const [tokenData, setTokenData] = useState<OpenViduRes>();

    const leave = useCallback(() => {
        if (session) session.disconnect();
        setOV(null);
        setSession('');
        setSessionId('');
        setSubscribers([]);
        if (tokenData) leaveSession(projectId, tokenData?.sessionId, tokenData?.connectionId);

    }, [session]);

    useEffect(() => {
        window.addEventListener('beforeunload', leave);

        return () => {
            window.removeEventListener('beforeunload', leave);
        };
    }, [leave]);


    const deleteSubscriber = useCallback((streamManager: StreamManager) => {
        setSubscribers((prevSubscribers) => {
            const index = prevSubscribers.indexOf(streamManager);
            if (index > -1) {
                const newSubscribers = [...prevSubscribers];
                newSubscribers.splice(index, 1);
                return newSubscribers;
            } 
                return prevSubscribers;
            
        });
    }, []);

    const joinSession = async () => {
        if (!tokenData) return;
        if (!session) return;
        if (!OV) return;
        session.on('streamCreated', (event) => {
            const subscriber = session.subscribe(event.stream, undefined);
            setSubscribers((temp) => [...temp, subscriber]);
        });
        session.on('streamDestroyed', (event) => {

            deleteSubscriber(event.stream.streamManager);
        });

        await session.connect(tokenData.token)
            .then(() => {
                if (OV) {
                    const publishers = OV.initPublisher(undefined, {
                        videoSource: false,
                        publishAudio: true,
                    });
                    setPublisher(publishers);
                    session
                        .publish(publishers)
                        .then(() => {
                        })
                        .catch(() => {
                        });
                }
            })

    };
    const createSession = async () => {
        const OVs = new OpenVidu();
        OVs.enableProdMode();
        setOV(OVs);
        setSession(OVs.initSession());
        await joinSession();
    };


    useEffect(() => {
        createSession();
        const fetchData = async () => {
            try {
                const sessionData = await getToken(projectId);
                if (sessionData) setTokenData(sessionData);
            } catch (error) {
                console.error('Error fetching session:', error);
            }
        };
        fetchData();
        return () => {
            leave();
        }
    }, [projectId]);


    useEffect(() => {
        createSession();
    }, [tokenData]);


    return (
        <div>
            {session && (
                <>
                    {publisher &&
                        <div>
                            <Audio streamManager={publisher}/>
                        </div>
                    }
                    {subscribers &&
                        subscribers.map((subscriberItem, index) => (
                            <div key={index}>
                                <Audio streamManager={subscriberItem}/>
                            </div>
                        ))}

                </>
            )}
        </div>
    );
}
