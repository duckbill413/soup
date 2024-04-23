import AI from "#/assets/icons/mainpage/plan/illustAI.svg"
import Image from 'next/image'

function MainPlan() {
  return (
    <div style={{height:"90vh", width:'100%', display: 'flex', flexDirection: 'column', alignItems: 'center', backgroundColor: '#f7f6cf'}}>
      <div>
        <p>SOUP AI로</p>
        <p>기획서를 간단하게 작성해보세.</p>
        <p>기획 배경, 서비스 타겟, 기대 효과 등</p>
        <p>간단하게 작성하면 기획서가 완성됩니다.</p>
      </div>
      <div>
        <Image src={AI} alt="Illust" width={364} height={364}/>
      </div>
    </div>
  )
}

export default MainPlan