import {Member} from "@/containers/project/types/member";


export type FuncDescResWithColor = {
    functionId: string;
    category: string;
    functionName: string;
    description: string;
    point: number;
    priority: string;
    reporter: Member;
    color: string
}

// export type FuncDescResWithColor extends FuncDescRes = {
//     color: string
// }

export interface Category {
    functionId: string;
    category: string;
    color: string;
}

export type FuncTableColumnProps = {
    funcCurrData: FuncDescResWithColor;
    updateElement: (currId,changeId,attribute)=>void;
}

export type CategoryModalProps ={
    funcCurrData: FuncDescResWithColor;
    selected: string;
    setSelected: (id:string)=>void;
    handleKey: (event: React.KeyboardEvent<HTMLInputElement>) => void;
    updateElement: (currId,changeId,attribute)=>void;
}

export type PriorityModalProps ={
    funcCurrData: FuncDescResWithColor;
    updateElement: (currId,changeId,attribute)=>void;
}

export type MemberModalProps ={
    funcCurrData: FuncDescResWithColor;
    selected: string;
    setSelected: (id:string)=>void;
    handleKey: (event: React.KeyboardEvent<HTMLInputElement>) => void;
    updateElement: (currId,changeId,attribute)=>void;
}
export interface PriorityIcons {
    [key: string]: string;
}
