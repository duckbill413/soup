import {FuncTableColumnProps} from "@/types/functionDesc";
import Category from "@/containers/func/FuncTableColumn/Attribute/Category";
import * as styles from "./funcTableColumn.css";

export default function FuncTableColumn({funcCurrData,updateElement}: FuncTableColumnProps) {

    return (

        <tr>
            <Category updateElement={updateElement} funcCurrData={funcCurrData}/>
            <td><input aria-label="input" type="text" value={funcCurrData.functionName}
                       onChange={event => updateElement(funcCurrData.functionId,event.target.value,"functionName")}/></td>
            <td>
                <input aria-label="input" type="text" value={funcCurrData.description}
                       onChange={event => event.target.value}/>
            </td>
            <td>
                <input aria-label="input" type="text" value={funcCurrData.point}
                       onChange={event => event.target.value}/>
            </td>
            <td>
                <input aria-label="input" type="text" value={funcCurrData.priority}
                       onChange={event => event.target.value}/>
            </td>
            <td>
                <div className={styles.manager}>
                    {/* <p>{Props.funcCurrData.manager.memberNickname}</p> */}
                    {/* <Image unoptimized src={Props.funcCurrData.manager.memberProfileUri} alt="프로필 이미지" width={30} */}
                    {/*       height={30}/> */}
                </div>
            </td>
        </tr>
            );
}
