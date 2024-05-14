import {create} from 'zustand';
import {MemberRes} from "@/containers/project/types/member";

type Store = {
    me: MemberRes | null;
    setMe: (me: MemberRes) => void;
    members: MemberRes[];
    setMembers: (Members: MemberRes[]) => void;
    filteredMembers: MemberRes[];
    setFilteredMembers: (members: MemberRes[]) => void;
    searchMember: (query: string) => void;
    isMemberModalVisible: string;
    setIsMemberModalVisible: (isVisible: string) => void;
};

const useMemberStore = create<Store>((set) => ({
    me: null,
    setMe: (me) => set({me} ),
    members: [],
    setMembers: (members) => set({ members }),
    filteredMembers: [],
    setFilteredMembers: (categories) => set({ filteredMembers: categories }),
    searchMember: (query) => {
        set((state) => {
            const searchedMembers = state.members.filter((member) =>
                member.nickname.toLowerCase().includes(query.toLowerCase())
            );
            return { filteredMembers: searchedMembers };
        });
    },
    isMemberModalVisible: 'none',
    setIsMemberModalVisible: (isVisible) => set({ isMemberModalVisible: isVisible }),

}));

export default useMemberStore;
