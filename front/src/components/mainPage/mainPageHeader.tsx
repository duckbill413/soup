import Blacklogo from '#/assets/icons/mainpage/blacklogo'

function MainPageHeader () {
  return (
    <div style={{ display: 'flex', justifyContent: 'space-between', height:'10vh',width:'95%', marginLeft:'2%' }}>
      <Blacklogo />
      <button type="button" style={{
        padding: 0,
        background: 'none',
        border: 'none',
      }}>
        <img
          src="//k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
          width="222"
          alt="카카오 로그인 버튼"
        />
      </button>
    </div>
  )
}

export default MainPageHeader