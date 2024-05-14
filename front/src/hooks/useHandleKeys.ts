import { useState } from 'react';

interface Option {
    id: string;
}

const getNextOption = (options: Option[], selected: string): string => {
    const currentIndex = options.findIndex(opt => opt.id === selected);
    return options[(currentIndex + 1) % options.length].id;
};

const getPreviousOption = (options: Option[], selected: string): string => {
    const currentIndex = options.findIndex(opt => opt.id === selected);
    return options[(currentIndex - 1 + options.length) % options.length].id;
};

const itemsToOptions = <T extends { id?: string; functionId?: string; memberId?:string }>(items: T[]): Option[] => items.map(item => {
        const itemId = item.functionId || item.id ||  item.memberId || '';
        return {

            id: itemId,
        };
    });

// 기본 내보내기로 변경
export default function useHandleKeys<T extends { [key: string]: any }>(
    items: T[],
    initialSelected: string,
) {
    const options = itemsToOptions(items);
    const [selected, setSelected] = useState<string>(initialSelected);
    const [action, setAction] = useState<string>('');

    const handleKey = (event: React.KeyboardEvent<HTMLInputElement>) => {
        if(!options || options.length<=0) return;
        switch (event.key) {
            case 'ArrowDown':
                setSelected(getNextOption(options, selected));
                setAction('down');
                break;
            case 'ArrowUp':
                setSelected(getPreviousOption(options, selected));
                setAction('up');
                break;

            case 'Enter':
                setAction('enter');
                break;
            case 'Escape':
                setAction('esc');
                break;
            default:
                break;
        }
    };

    return { selected, setSelected, action, setAction, handleKey };
}
