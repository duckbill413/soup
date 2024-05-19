import COLORS from '@/constants'
import { LiveCursorProps } from '@/types/cursor'
import Cursor from './Cursor'

function LiveCursors({ others }: LiveCursorProps) {
  return others.map(({ connectionId, presence, info }) => {
    if (presence == null || !presence?.cursor) {
      return null
    }

    return (
      <Cursor
        key={connectionId}
        color={COLORS[Number(connectionId) % COLORS.length]}
        x={presence.cursor.x}
        y={presence.cursor.y}
        name={info?.name ?? '팀원'}
        message={presence.message}
      />
    )
  })
}

export default LiveCursors
