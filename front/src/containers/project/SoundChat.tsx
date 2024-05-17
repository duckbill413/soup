'use client'

import {getToken} from "@/apis/openVidu/openViduAPI";
import {useCallback, useEffect,  useState} from "react";

import {
    OpenVidu,
    Session as OVSession,
    Subscriber,
} from 'openvidu-browser';
import useAudioStore from "@/stores/useAudioStore";
import {OpenViduRes} from "@/containers/project/types/openVidu";
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
    const [tokenData, setTokenData] = useState<OpenViduRes>();

    const leave = useCallback(() => {
        if (session) session.disconnect();
        setOV(null);
        setSession('');
        setSessionId('');
        setSubscriber(null);
        setLocalStreamManager(null);
        setToggleAudio(false);
        // if(tokenData) leaveSession(projectId,tokenData?.sessionId,tokenData?.connectionId);

    }, [session]);

    const joinSession = async () => {
        if (!tokenData) return;
        if (!session) return;
        if (!OV) return;
        console.log(tokenData);
        session.on('streamCreated', event => {
            const subscribers = session.subscribe(event.stream, '');
            setSubscriber(subscribers);
        });
        session.on('streamDestroyed', event => {
            // Logic when a participant leaves
            // Check if the remote connection exists
            const connection = session.remoteConnections.get(event.stream.connection.connectionId);
            if (!connection) {
                console.error(`Remote connection ${event.stream.connection.connectionId} unknown when 'onParticipantLeft'.`);
                 // If the connection does not exist, stop processing here
            }
            // Execute other logic...
        });


        await session.connect(tokenData.token)
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
            {session && subscriber && (
                <Session
                    subscriber={subscriber as Subscriber}
                />
            )}
        </div>
    );
}
