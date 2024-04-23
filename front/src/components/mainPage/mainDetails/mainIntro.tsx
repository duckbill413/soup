import vars from '@/styles/variables.css'
import DownButton from '#/assets/icons/mainpage/downbutton'
import { FirstPagepic1, FirstPagepic2, FirstPagepic3 } from '#/assets/icons/mainpage/intro/firstillust'
import { bounceAnimation } from '@/styles/main/mainPage.css'


function MainIntro({ onButtonClick }: {onButtonClick?: () => void}) {
    return (
        <div style={{height:"90vh", width:'100%', display: 'flex', flexDirection: 'column', alignItems: 'center', backgroundColor: '#b6d8f2'}}>
          <div style={{
            height:'100%',
            display: 'flex',
            flexDirection: 'column',
            justifyContent: 'center',
            alignItems: 'center'
          }}>
            <p style={{ margin: vars.space.tiny, fontSize: '3.5rem', fontStyle:'italic'}}>Start Organization, Upgrade Project.</p>
            <p style={{ margin: vars.space.tiny, fontSize: '1.3rem' }}>Spring 프레임워크 기반 프로젝트의 기획, 설계가</p>
            <p style={{ margin: vars.space.tiny, fontSize: '1.3rem' }}>간결해지도록 도와드립니다.</p>

            <div style={{ display: 'flex', justifyContent:'space-between'}}>
              <FirstPagepic1/>
              <FirstPagepic2/>
              <FirstPagepic3/>
            </div>

            <button
              type="button"
              style={{ margin: vars.space.tiny, padding: 0, background: 'none', border: 'none', position:'absolute',bottom:'0' }}
              className={bounceAnimation}
              onClick={onButtonClick}
              aria-label="내려가기"
            >
              <DownButton />
            </button>

          </div>
        </div>
    )
}

export default MainIntro