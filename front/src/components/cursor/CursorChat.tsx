import { useEffect, useRef } from "react";
import { CursorChatProps, CursorMode } from "@/types/cursor";
import CursorSVG from "#/assets/icons/cursor/CursorSVG";
import * as styles from "@/components/cursor/cursor.css"

function CursorChat({ cursor, cursorState, setCursorState, updateMyPresence }: CursorChatProps) {
  const inputRef = useRef<HTMLInputElement | null>(null);
  useEffect(() => {
    if (cursorState.mode === CursorMode.Chat && inputRef.current) {
      inputRef.current.focus();
    }
  }, [cursorState.mode]);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    updateMyPresence({ message: e.target.value });
    setCursorState({
      mode: CursorMode.Chat,
      previousMessage: null,
      message: e.target.value,
    });
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (e.key === "Enter") {
      setCursorState({
        mode: CursorMode.Chat,
        // @ts-ignore
        previousMessage: cursorState.message,
        message: "",
      });
    } else if (e.key === "Escape") {
      setCursorState({
        mode: CursorMode.Hidden,
      });
    }
  };

  return (
    <div
      className={styles.cursorChatContainer}
      style={{
        transform: `translateX(${cursor.x}px) translateY(${cursor.y}px)`,
      }}
    >
      {cursorState.mode === CursorMode.Chat && (
        <>
          <CursorSVG color="#000" />

          <div role="presentation"
            className={styles.cursorChatDiv}
            onKeyUp={(e) => e.stopPropagation()}
          >
            {cursorState.previousMessage && <div>{cursorState.previousMessage}</div>}
            <input
              className={styles.cursorChatInput}
              onChange={handleChange}
              onKeyDown={handleKeyDown}
              placeholder={cursorState.previousMessage ? "" : "채팅을 해보세요.."}
              value={cursorState.message}
              maxLength={50}
              ref={inputRef}
            />
          </div>
        </>
      )}
    </div>
  );
}

export default CursorChat;
