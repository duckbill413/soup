import { ReactNode } from 'react'
import Draggable from 'react-draggable';

interface GuideProps {
  children: ReactNode;
  onClose: () => void;
}

function GuideModal ({ children, onClose }:GuideProps) {

  return (
    <Draggable>
      <div style={{ position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
        <div style={{ position: 'absolute', padding: 20, background: 'white', boxShadow: '0 4px 8px rgba(0,0,0,0.1)' }}>

          <button type="button" onClick={onClose}>X</button>
          {children}
        </div>
      </div>
    </Draggable>
  );
}

export default GuideModal;
