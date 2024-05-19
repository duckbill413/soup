import React, { useState, useEffect } from 'react';

import { Publisher, Subscriber } from 'openvidu-browser';
import Audio from './Audio';

interface SessionProps {
    subscriber: Subscriber;
    publisher: Publisher;
}

function Session({ subscriber, publisher }: SessionProps) {
    const [subscribers, setSubscribers] = useState<Subscriber[]>([]);
    useEffect(() => {
        if (subscriber) {
            setSubscribers(prevSubscribers => [...prevSubscribers, subscriber]);
        }
    }, [subscriber]);

    const renderSubscribers = () => (
            <div >
                <div>
                    <Audio streamManager={publisher} />
                </div>
                {subscribers.map((subscriberItem,index) => (
                    <div key={index}>
                        <Audio streamManager={subscriberItem} />
                    </div>
                ))}
            </div>
        );

    return <>{renderSubscribers()}</>;
}

export default Session;