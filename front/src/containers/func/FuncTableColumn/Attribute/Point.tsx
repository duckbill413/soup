import {FuncTableColumnProps} from "@/containers/func/types/functionDesc";
import * as styles from "./point.css";

export default function Point({funcCurrData,updateElement}: FuncTableColumnProps){
    const handleInputChange = (pointValue: string) => {
        if (pointValue.toString().length === 1) {
            updateElement(funcCurrData.functionId, Number(pointValue), "point");
        } else if (pointValue.toString().length > 1) {
            const lastDigit = pointValue.toString().charAt(pointValue.toString().length - 1);
            updateElement(funcCurrData.functionId, Number(lastDigit), "point");
        }
    };
    return(<td>
            <input aria-label="input" style={{width: '20px'}} value={funcCurrData.point}
                   className={styles.point}
                   type='number'
                   onChange={(e)=>handleInputChange(e.target.value)}/>
        </td>);
}