'use client'

import useFuncDescStore from "@/stores/useFuncDescStore";
import {FuncTableColumnProps, PriorityIcons} from "@/containers/func/types/functionDesc";
import PriorityModal from "@/containers/func/FuncTableColumn/Attribute/Modal/PriorityModal";
import Image from "next/image";
import Lowest from "#/assets/icons/func/doubleDown.svg";
import Low from "#/assets/icons/func/down.svg";
import Medium from "#/assets/icons/func/medium.svg";
import High from "#/assets/icons/func/up.svg";
import Highest from "#/assets/icons/func/doubleUp.svg";

const priorityIcons: PriorityIcons = {Lowest, Low, Medium, High, Highest};

export default function Priority({ funcCurrData, updateElement }: FuncTableColumnProps) {
    const { setIsPriorityModalVisible } = useFuncDescStore();
    const priorityIcon = priorityIcons[funcCurrData.priority];

    return (
        <td>
            <div
                onClick={() => setIsPriorityModalVisible(funcCurrData.functionId)} role="presentation" aria-hidden="true">
            {priorityIcon && (
                <Image
                    src={priorityIcon}
                    alt={funcCurrData.priority}
                    width={20}
                    height={20}
                />
            )}
                ㅤ</div>
            <PriorityModal funcCurrData={funcCurrData} updateElement={updateElement} />
        </td>
    );
}