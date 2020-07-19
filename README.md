# README.md

이 앱은 폐쇄형 음악 공유앱입니다. 

### 작동순서
LoginActivitiy -> MainActivity -> MusicPlayingActivity
<br>               -> SignUpActivity                      -> AddMuicActivity

### 각 컴포넌트별 설명
1. LoginActivity
<br>로그인 하는 액티비티입니다. 아이디가 없을 경우에는 가입하셔야 합니다.
2. MainActivity
<br>음악재생 액티비티로 이동하거나 로그아웃 할 수 있습니다.
3. MusicPlayingActivity
<br>음악재생하는 액티비티 입니다. 유튜브 API를 사용하여 YoutubePlayer를 이용해 음악을 재생합니다. 재생을 위해서는 해당 유튜브 영상의 ID가 필요합니다. 
4. ServerCommunicator
<br>서버와 통신하기 위한 모든 함수가 들어있습니다. REST api를 이용하여 통신합니다.

### 주의사항
최소 SDK 버젼은 15부터 가능합니다.
유튜브 API를 사용하고 있습니다. API 키도 들어가 있으니까 너무 많이 사용하면 저한테 과금됩니다;;
