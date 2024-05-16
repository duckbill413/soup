'use client'

import {getToken} from "@/apis/openVidu/openViduAPI";
import {useCallback, useEffect,  useState} from "react";

import {
    OpenVidu,
    Session as OVSession,
    Subscriber,
} from 'openvidu-browser';
import useAudioStore from "@/stores/useAudioStore";
import Session from './Session';

type Props = {
    projectId: string;
}

export default function SoundChat({projectId}: Props) {
    const {setLocalStreamManager,setToggleAudio} = useAudioStore();
    const [session, setSession] = useState<OVSession | ''>('');
    const [, setSessionId] = useState<string>('');
    const [subscriber, setSubscriber] = useState<Subscriber | null>(null);
    const [OV, setOV] = useState<OpenVidu | null>(null);
    const [token, setToken] = useState<string>('');

    const leaveSession = useCallback(() => {
        if (session) session.disconnect();
        setOV(null);
        setSession('');
        setSessionId('');
        setSubscriber(null);
        setLocalStreamManager(null);
        setToggleAudio(false);
    }, [session]);

    const joinSession = async () => {
        if (!token) return;
        if (!session) return;
        if (!OV) return;

        session.on('streamCreated', event => {
            const subscribers = session.subscribe(event.stream, '');
            setSubscriber(subscribers);
        });

        await session.connect(token)
            .then(() => {
                if (OV) {
                    const publishers = OV.initPublisher(undefined, {
                        videoSource: false,
                        publishAudio: false,
                    });
                    setLocalStreamManager(publishers);
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
                if (sessionData) setToken(sessionData.token);
            } catch (error) {
                console.error('Error fetching session:', error);
            }
        };
        fetchData();
        return () => {
            leaveSession();
        }
    }, [projectId]);


    useEffect(() => {
        createSession();
    }, [token]);


    return (
        <div>
            {session && (
                <Session
                    subscriber={subscriber as Subscriber}
                />
            )}
        </div>
    );
}
