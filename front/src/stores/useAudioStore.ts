import {create} from 'zustand';
import { StreamManager, Publisher } from "openvidu-browser";

type Store = {
    localStreamManager: StreamManager | null;
    setLocalStreamManager: (streamManager: StreamManager | null) => void;
    toggleAudio: () => void;
};

const useStore = create<Store>((set) => ({
    localStreamManager: null,
    setLocalStreamManager: (streamManager) => set({ localStreamManager: streamManager }),
    toggleAudio: () => {
        set((state) => {
            if (state.localStreamManager && state.localStreamManager instanceof Publisher) {
                const isAudioEnabled = state.localStreamManager.stream.audioActive;
                state.localStreamManager.publishAudio(!isAudioEnabled);
                return { ...state };
            }
            return state;
        });
    },
}));

export default useStore;
