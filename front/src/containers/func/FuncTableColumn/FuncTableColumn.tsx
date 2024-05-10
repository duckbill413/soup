import {FuncTableColumnProps} from "@/containers/func/types/functionDesc";
import Category from "@/containers/func/FuncTableColumn/Attribute/Category";
import Priority from "@/containers/func/FuncTableColumn/Attribute/Priority";
import Point from "@/containers/func/FuncTableColumn/Attribute/Point";
import Member from "@/containers/func/FuncTableColumn/Attribute/Member";


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
            <Member funcCurrData={funcCurrData} updateElement={updateElement}/>
        </tr>
            );
}
