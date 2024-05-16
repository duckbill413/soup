'use client'

import React, { useState, useEffect } from 'react';

import { Subscriber } from 'openvidu-browser';
import Audio from './Audio';

interface SessionProps {
    subscriber: Subscriber;
}

function Session({ subscriber }: SessionProps) {
    const [subscribers, setSubscribers] = useState<Subscriber[]>([]);

    useEffect(() => {
        if (subscriber) {
            setSubscribers(prevSubscribers => [...prevSubscribers, subscriber]);
        }
    }, [subscriber]);

    const renderSubscribers = () => (
            <div>
                {subscribers.map(subscriberItem => (
                    <div key={crypto.randomUUID()}>
                        <Audio streamManager={subscriberItem} />
                    </div>
                ))}
            </div>
        );

    return <>{renderSubscribers()}</>;
}

export default Session;