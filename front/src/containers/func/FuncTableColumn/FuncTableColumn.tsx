import {FuncTableColumnProps} from "@/types/functionDesc";
import Category from "@/containers/func/FuncTableColumn/Attribute/Category";
import Image from "next/image";
import Priority from "@/containers/func/FuncTableColumn/Attribute/Priority";
import Point from "@/containers/func/FuncTableColumn/Attribute/Point";
import * as styles from "./funcTableColumn.css";


export default function FuncTableColumn({funcCurrData,updateElement}: FuncTableColumnProps) {

    return (

        <tr>
            <Category updateElement={updateElement} funcCurrData={funcCurrData}/>
            <td><input aria-label="input" type="text" value={funcCurrData.functionName}
                       onChange={event => updateElement(funcCurrData.functionId,event.target.value,"functionName")}/></td>
            <td>
                <input aria-label="input" type="text" value={funcCurrData.description}
                       onChange={event => updateElement(funcCurrData.functionId,event.target.value,"description")}/>
            </td>
            <Point funcCurrData={funcCurrData} updateElement={updateElement}/>
            <Priority funcCurrData={funcCurrData} updateElement={updateElement}/>
            <td>
                {funcCurrData.reporter.memberNickname && funcCurrData.reporter.memberNickname.length>0 &&
                <div className={styles.manager}>
                     <p>{funcCurrData.reporter.memberNickname}</p>
                     <Image unoptimized src={funcCurrData.reporter.memberNickname} alt="프로필 이미지" width={30} height={30}/>
                </div>
                }
            </td>
        </tr>
            );
}
