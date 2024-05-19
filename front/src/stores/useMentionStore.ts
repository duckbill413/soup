import {create} from 'zustand';
import {Sender} from "@/containers/project/types/chat";


type Store = {
    senders: Sender[];
    setSenders: (Senders: Sender[]) => void;
    filteredSenders: Sender[];
    setFilteredSenders: (senders: Sender[]) => void;
    searchSender: (query: string) => void;
    isSenderModalVisible: boolean;
    setIsSenderModalVisible: (isVisible: boolean) => void;
};

const useMentionStore = create<Store>((set) => ({
    senders: [],
    setSenders: (senders) => set({ senders }),
    filteredSenders: [],
    setFilteredSenders: (categories) => set({ filteredSenders: categories }),
    searchSender: (query) => {
        set((state) => {
            const searchedSenders = state.senders.filter((member) =>
                member.nickname.toLowerCase().includes(query.toLowerCase())
            );
            return { filteredSenders: searchedSenders };
        });
    },
    isSenderModalVisible: false,
    setIsSenderModalVisible: (isVisible) => set({ isSenderModalVisible: isVisible }),

}));

export default useMentionStore;
