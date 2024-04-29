import CursorSVG from "#/assets/icons/cursor/CursorSVG";
import * as styles from "@/components/cursor/cursor.css"

type Props = {
  color: string;
  x: number;
  y: number;
  message?: string;
};

function Cursor({ color, x, y, message }: Props) {
  return <div
    className={styles.container}
    style={{ transform: `translateX(${x}px) translateY(${y}px)` }}
  >
    <CursorSVG color={color} />

    {message && (
      <div
        className={styles.division}
        style={{ backgroundColor: color, borderRadius: 20 }}
      >
        <p className={styles.pTag}>
          {message}
        </p>
      </div>
    )}
  </div>
}

export default Cursor;
