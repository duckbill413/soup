'use client'

import {PriorityIcons,  PriorityModalProps} from "@/containers/func/types/functionDesc";
import useFuncDescStore from "@/stores/useFuncDescStore";
import Image from "next/image";
import Lowest from "#/assets/icons/func/doubleDown.svg";
import Low from "#/assets/icons/func/down.svg";
import Medium from "#/assets/icons/func/medium.svg";
import High from "#/assets/icons/func/up.svg";
import Highest from "#/assets/icons/func/doubleUp.svg";
import * as styles from "./priorityModal.css";


const PRIORITY_TYPE = ['Highest', 'High', 'Medium', 'Low', 'Lowest'];

export const priorityIcons: PriorityIcons = {
    Lowest,
    Low,
    Medium,
    High,
    Highest,
};

export default function PriorityModal({updateElement,funcCurrData}: PriorityModalProps){

    const {isPriorityModalVisible,setIsPriorityModalVisible} = useFuncDescStore();

    return(<>
        {isPriorityModalVisible !== 'none' && (
            <div onClick={()=>setIsPriorityModalVisible('none')} className={styles.clickBackground} aria-hidden="true" role="presentation"/>
        )}
        {isPriorityModalVisible === funcCurrData.functionId &&

            <div className={styles.btnGroupContainer}>
                <div className={styles.btnGroup}>
                    <div>
                        {PRIORITY_TYPE.map(d =>
                            <div key={d}
                                 className={`${styles.select} ${funcCurrData.priority === d ? styles.whitesmoke : ''}`}
                                 onClick={() => updateElement(funcCurrData.functionId, d, 'priority')}
                                 aria-hidden="true"
                                 role="presentation"
                            >
                                {priorityIcons[d] && (
                                    <Image src={priorityIcons[d]} alt={d} width={20} height={20} />
                                )}
                                <button
                                    className={styles.button}
                                    key={d}
                                    type='button'
                                    aria-hidden="true">{d}</button>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        }
    </>);
}
