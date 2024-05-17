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
        if (subscriber !== null) {
            setSubscribers(prevSubscribers => [...prevSubscribers, subscriber]);
        }
    }, [subscriber]);

    const renderSubscribers = () => (
        <div>
            {subscribers.map((subscriberItem, index) => (
                <div key={subscriberItem.stream.connection.connectionId || index}>
                    <Audio streamManager={subscriberItem} />
                </div>
            ))}
        </div>
    );

    return <>{renderSubscribers()}</>;
}

export default Session;
