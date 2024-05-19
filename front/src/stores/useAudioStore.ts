import {create} from 'zustand';
import { Publisher } from "openvidu-browser";

type Store = {
    publisher: Publisher | null;
    setPublisher: (publisher: Publisher | null) => void;
    toggleAudio: () => void;
    setToggleAudio: (value:boolean) => void;
};

const useStore = create<Store>((set) => ({
    publisher: null,
    setPublisher: (publisher) => set({ publisher }),
    toggleAudio: () => {
        set((state) => {
            if (state.publisher) {
                const isAudioEnabled = state.publisher.stream.audioActive;
                state.publisher.publishAudio(!isAudioEnabled);
                return { ...state };
            }
            return state;
        });
    },
    setToggleAudio: (value) => {
        set((state) => {
            if (state.publisher) {
                state.publisher.publishAudio(value);
                return { ...state };
            }
            return state;
        });
    },
}));

export default useStore;
