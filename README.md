1.
AWS 서비스(IoT, DynamoDB, Api Gateway등)와  IoT 디바이스를 연동하여 IoT 디바이스에서 수집된 로그 정보를 조회하는 기능 구현.
AWS 클라우드 플랫폼에 저장된 로그 정보를 바탕으로 안드로이드 앱 및 웹 앱에서 대시보드 구현
안드로이드 앱 및 웹 앱에서 IoT 디바이스 제어 및 관리

2.
다음과 같이 동작하는 IoT 서비스를 AWS 서비스를 이용하여 구축해 본다.
Co2센서와 온습도 센서(DHT-11)를 장착한 아두이노 보드(MKR WiFi1010)를 AWS IoT와 연결
아두이노 보드에서는 AWS IoT와 인증과정을 거친후, 온습도 센서에서 측정된 온습도 값을 매 5초마다 AWS IoT로 전송

2.1 
다음 5가지 라이브러리를 검색하여 설치
WiFiNINA (or WiFi101 for the MKR1000), ArduinoBearSSL, ArduinoECCX08, ArduinoMqttClient
Arduino Cloud Provider Examples
Arduino IDE의 파일-예제-ArduinoECCX08-Tools-ECCX08CSR 메뉴 선텍 -CSR 생성
2.2
1. AWS IoT 콘솔을 엽니다.
2. 왼쪽 탐색 창에서 관리를 선택합니다.
3. 아직 사물이 없습니다 페이지에서 사물 등록을 선택합니다.
4. Creating AWS IoT things(AWS IoT 사물 생성) 페이지에서 Create a single thing(단일 사물 생성)
5. csr 등록하여 연동



3. AWS IoT로 온도 값 전송 및 팬 제어
1)AWS_IoT_DHT11을 다운로드하여 Arduino IDE에서 실행한다.
2)arduino_secrets.h에서 다음 항목을 사용 환경에 맞도록 수정후, 빌드/업로드 한다.
SECRET_SSID: 무선랜 아이디
SECRET_PASS: 무선랜 패스워드
SECRET_BROKER: AWS IoT broker 엔드포인트
SECRET_CERTIFICATE: 인증서 
4. DynamoDB 테이블
다음 단계에 따라 Logging 테이블을 생성합니다.
콘솔 왼쪽의 탐색 창에서 [대시보드]를 선택합니다.
콘솔의 오른쪽에서 [테이블 만들기]을 선택합니다.
다음과 같이 테이블 세부 정보를 입력합니다.
테이블 이름에 Logging을 입력합니다.
파티션 키에 deviceId를 입력합니다. 데이터유형은 문자열을 선택합니다.
정렬 키 추가 체크박스를 선택합니다.
정렬 키에 time을 입력합니다. 데이터유형은 번호를 선택합니다.
[생성]을 선택하여 테이블을 생성합니다.
4.2 로그조회 Lambda 함수 정의
AWS Lambda 프로젝트를 Eclipse용 AWS Toolkit을 이용하여 생성한다.
Project name: RecordingDeviceDataJavaProject2
Group ID: com.example.lambda
Artifact ID: recording
Class Name: RecordingDeviceInfoHandler2
Input Type에서 Custom을 선택합니다.
Finish 버튼을 클릭합니다.
코드는 첨부파일 참조 할 것
웹에서 로그 데이터 뽑아서 대시보드구현
