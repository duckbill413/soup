import {create} from 'zustand';
import { Category, FuncDescResWithColor } from '@/containers/func/types/functionDesc';
import { getLowestIndexMissingColor } from "@/utils/getLightColorByIndex";

type Store = {
    funcDescData: FuncDescResWithColor[];
    setFuncDescData: (funcDescData: FuncDescResWithColor[]) => void;
    uniqueCategories: Category[];
    filteredCategories: Category[];
    setFilteredCategories: (categories: Category[]) => void;
    searchCategory: (query: string) => void;
    isCategoryModalVisible: string;
    setIsCategoryModalVisible: (isVisible: string) => void;
    priorities: 'Low' | 'Medium' | 'High' | 'Highest'
    isPriorityModalVisible: string;
    setIsPriorityModalVisible: (isVisible: string) => void;
};

const useFuncDescStore = create<Store>((set) => ({
    funcDescData: [],
    setFuncDescData: (funcDescData) => set({ funcDescData }),
    uniqueCategories: [],
    filteredCategories: [],
    setFilteredCategories: (categories) => set({ filteredCategories: categories }),
    searchCategory: (query) => {
        set((state) => {
            let currInput = state.filteredCategories.find((category) => category.functionId === 'temp');
            let searchedCategories = state.uniqueCategories.filter((category) =>
                category.category.toLowerCase().includes(query.toLowerCase())
            );
            if (!currInput) {
                const newColor = getLowestIndexMissingColor(state.filteredCategories.map(category => category.color));
                const newCategory = {
                    functionId: 'temp',
                    category: query,
                    color: newColor,
                };
                searchedCategories = [...searchedCategories, newCategory];
            }
            else if (currInput) {
                currInput = { ...currInput, category: query };
                searchedCategories = [...searchedCategories, currInput];
            }
            return { filteredCategories: searchedCategories };
        });
    },
    isCategoryModalVisible: 'none',
    priorities: 'Medium',
    setIsCategoryModalVisible: (isVisible) => set({ isCategoryModalVisible: isVisible }),
    isPriorityModalVisible: 'none',
    setIsPriorityModalVisible: (isVisible) => set({ isPriorityModalVisible: isVisible }),

}));

// eslint-disable-next-line no-param-reassign
useFuncDescStore.subscribe(
    (state) => {
        state.uniqueCategories = Array.from(
            new Set(
                state.funcDescData
                    .filter((data) => data.category !== '')
                    .map((data) => data.category)
            )
        ).map((category) => ({
            functionId: state.funcDescData.find((item) => item.category === category)?.functionId || '',
            category,
            color: state.funcDescData.find((item) => item.category === category)?.color || '',
        }));
    }
);
export default useFuncDescStore;
