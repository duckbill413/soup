import {create} from 'zustand';
import * as Stomp from "@stomp/stompjs";
import {ChatReq, ChatRes} from "@/containers/project/types/chat";


type Store = {
    client: Stomp.Client | null;
    connect: (projectId: string) => void;
    chatList: ChatRes[];
    setChatList: (chatList: ChatRes[]) => void;
    send: (projectId:string,{sender,message,mentionedMemberIds}:ChatReq) => void;
    disconnect: (client:Stomp.Client)=>void;
    isVisible: boolean;
    setIsVisible: (isVisible:boolean)=>void;
    tempChatMessageId: string;
    setTempChatMessageId: (chatMessageId:string)=>void;
    moveChatMessageId: (chatMessageId:string)=>void;
};

export const useMessageSocketStore = create<Store>((set) => ({
    client: null,
    chatList: [],
    setChatList: (chatList) => set({ chatList }),
    disconnect: (client) => {
        if (client) {
            client.deactivate();
            set((state) => ({ ...state, client: null }));
        }
    },
    connect: (projectId: string) => {
        const clientdata = new Stomp.Client({
            brokerURL: process.env.NEXT_PUBLIC_SERVER_SOCKET,
            connectHeaders: {},
            // debug: function (str) {
            //     console.log(str);
            // },
            reconnectDelay: 5000,
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
        });

        clientdata.onConnect =  () => {
            set((state) => ({ ...state, initiating: true }));
            clientdata.subscribe(`/sub/chatrooms/${projectId}`, (message: any) => {
                const chatRes: ChatRes = JSON.parse(message.body);
                set((state) => ({ ...state, chatList: [...state.chatList, chatRes] }));
            });

        };

        clientdata.activate();
        set((state) => ({ ...state, client: clientdata }));
    },

    send: (projectId, {message, mentionedMemberIds, sender}) => {
        set((state) => {
            if (state.client) {
                state.client.publish({
                    destination: `/pub/chatrooms/${projectId}`,
                    body: JSON.stringify({
                        sender,
                        message,
                        mentionedMemberIds,
                    }),
                });
            }
            return state;
        });
    },
    isVisible: false,
    setIsVisible: (isVisible) => set({ isVisible }),
    tempChatMessageId:"",
    setTempChatMessageId:(chatMessageId)=>set({tempChatMessageId:chatMessageId}),
    moveChatMessageId: (chatMessageId) => {
        const chatMessage = document.querySelector(`.a${chatMessageId}`);
        if (chatMessage) {
            chatMessage.scrollIntoView({ behavior: 'smooth' });
        }

        set(() => ({ tempChatMessageId: '' }));
    }
}));