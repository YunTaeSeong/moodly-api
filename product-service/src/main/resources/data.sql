-- 카테고리 초기 데이터
INSERT IGNORE INTO categories (id, name, display_order, created_by, created_date) VALUES
(1, '의류', 1, 'system', NOW()),
(2, '가전용품', 2, 'system', NOW()),
(3, '푸드', 3, 'system', NOW()),
(4, '뷰티', 4, 'system', NOW()),
(5, '홈인테리어', 5, 'system', NOW());

-- 서브카테고리 초기 데이터
-- 의류 (category_id = 1)
INSERT IGNORE INTO sub_categories (id, category_id, name, display_order, created_by, created_date) VALUES
(1, 1, '남성의류', 1, 'system', NOW()),
(2, 1, '여성의류', 2, 'system', NOW()),
(3, 1, '아동의류', 3, 'system', NOW()),
(4, 1, '언더웨어', 4, 'system', NOW()),
(5, 1, '액세서리', 5, 'system', NOW()),
(6, 1, '신발', 6, 'system', NOW()),
(7, 1, '가방', 7, 'system', NOW()),
(8, 1, '시계', 8, 'system', NOW());

-- 가전용품 (category_id = 2)
INSERT IGNORE INTO sub_categories (id, category_id, name, display_order, created_by, created_date) VALUES
(9, 2, 'TV/영상가전', 1, 'system', NOW()),
(10, 2, '냉장고', 2, 'system', NOW()),
(11, 2, '세탁기/건조기', 3, 'system', NOW()),
(12, 2, '청소기', 4, 'system', NOW()),
(13, 2, '공기청정기', 5, 'system', NOW()),
(14, 2, '에어컨', 6, 'system', NOW()),
(15, 2, '주방가전', 7, 'system', NOW()),
(16, 2, '생활가전', 8, 'system', NOW());

-- 푸드 (category_id = 3)
INSERT IGNORE INTO sub_categories (id, category_id, name, display_order, created_by, created_date) VALUES
(17, 3, '신선식품', 1, 'system', NOW()),
(18, 3, '냉동식품', 2, 'system', NOW()),
(19, 3, '간편식', 3, 'system', NOW()),
(20, 3, '과자/스낵', 4, 'system', NOW()),
(21, 3, '음료', 5, 'system', NOW()),
(22, 3, '커피/차', 6, 'system', NOW()),
(23, 3, '건강식품', 7, 'system', NOW()),
(24, 3, '다이어트식품', 8, 'system', NOW());

-- 뷰티 (category_id = 4)
INSERT IGNORE INTO sub_categories (id, category_id, name, display_order, created_by, created_date) VALUES
(25, 4, '스킨케어', 1, 'system', NOW()),
(26, 4, '메이크업', 2, 'system', NOW()),
(27, 4, '향수', 3, 'system', NOW()),
(28, 4, '헤어케어', 4, 'system', NOW()),
(29, 4, '바디케어', 5, 'system', NOW()),
(30, 4, '남성화장품', 6, 'system', NOW()),
(31, 4, '네일', 7, 'system', NOW()),
(32, 4, '선케어', 8, 'system', NOW());

-- 홈인테리어 (category_id = 5)
INSERT IGNORE INTO sub_categories (id, category_id, name, display_order, created_by, created_date) VALUES
(33, 5, '가구', 1, 'system', NOW()),
(34, 5, '침구/커튼', 2, 'system', NOW()),
(35, 5, '조명', 3, 'system', NOW()),
(36, 5, '수납/정리', 4, 'system', NOW()),
(37, 5, '인테리어소품', 5, 'system', NOW()),
(38, 5, '주방용품', 6, 'system', NOW()),
(39, 5, '욕실용품', 7, 'system', NOW()),
(40, 5, '생활용품', 8, 'system', NOW());

-- 상품 초기 데이터
-- 인기 상품 (category_id NULL, sub_category_id NULL)
INSERT IGNORE INTO products (name, price, discount, image, description, details, category_id, sub_category_id, created_by, created_date) VALUES
('스마트폰 케이스', 15000.00, 25, 'https://images.unsplash.com/photo-1601972602237-8c79241e468b?w=500&h=500&fit=crop', 
'고급스러운 스마트폰 케이스로 기기를 완벽하게 보호하세요. 얇고 가벼우면서도 강력한 보호 기능을 제공합니다.', 
'• 방수 기능으로 일상 생활에서 안전하게 사용 가능\n• 충격 흡수 소재로 낙하 시 기기 보호\n• 다양한 색상 제공으로 개성 표현\n• 1년 품질 보증으로 안심하고 사용\n• 얇은 디자인으로 휴대성 우수', 
NULL, NULL, 'system', NOW()),

('무선 이어폰', 89000.00, 34, 'https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=500&h=500&fit=crop', 
'프리미엄 무선 이어폰으로 최고의 음질을 경험하세요. 노이즈 캔슬링 기능이 탑재되어 있어 어디서나 몰입감 있는 음악 감상을 즐길 수 있습니다.', 
'• 액티브 노이즈 캔슬링으로 주변 소음 차단\n• 30시간 연속 재생 가능한 긴 배터리 수명\n• IPX7 방수 등급으로 운동 시에도 안전\n• 터치 제어로 편리한 조작\n• 프리미엄 사운드 드라이버로 고음질 구현', 
NULL, NULL, 'system', NOW()),

('노트북 스탠드', 45000.00, 0, 'https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=500&h=500&fit=crop', 
'인체공학적 노트북 스탠드로 편안한 작업 환경을 만들어보세요. 높이와 각도를 자유롭게 조절할 수 있어 목과 어깨의 피로를 줄여줍니다.', 
'• 높이 조절 가능으로 사용자 맞춤형 설정\n• 알루미늄 소재로 가볍고 내구성 우수\n• 통풍 설계로 노트북 발열 방지\n• 휴대용으로 어디서나 사용 가능\n• 다양한 노트북 크기 지원 (13~17인치)', 
NULL, NULL, 'system', NOW()),

('블루투스 스피커', 120000.00, 28, 'https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=500&h=500&fit=crop', 
'고음질 블루투스 스피커로 어디서나 음악을 즐기세요. 강력한 베이스와 선명한 고음으로 콘서트장 같은 몰입감을 선사합니다.', 
'• 360도 사운드로 어디서나 균일한 음질\n• 20시간 연속 재생 가능한 배터리\n• IPX5 방수 기능으로 야외 사용 가능\n• 다중 연결 지원으로 여러 기기 연결\n• 프리미엄 드라이버로 깊이 있는 사운드', 
NULL, NULL, 'system', NOW()),

('스마트 워치', 250000.00, 29, 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500&h=500&fit=crop', 
'최신 스마트 워치로 건강과 일상을 관리하세요. 운동 추적부터 알림까지 모든 것을 한 손목에서 처리할 수 있습니다.', 
'• 심박수 측정으로 건강 모니터링\n• GPS 내장으로 운동 경로 추적\n• 7일 배터리 수명으로 장시간 사용\n• 스마트 알림으로 중요한 정보 확인\n• 수영 방수 기능으로 다양한 운동 지원', 
NULL, NULL, 'system', NOW()),

('무선 마우스', 35000.00, 0, 'https://images.unsplash.com/photo-1527814050087-3793815479db?w=500&h=500&fit=crop', 
'에르고노믹 무선 마우스로 장시간 사용해도 편안합니다. 손목의 피로를 줄이고 정밀한 작업을 도와줍니다.', 
'• 인체공학 디자인으로 손목 피로 감소\n• 6개월 배터리 수명으로 오래 사용\n• 고정밀 센서로 정확한 커서 제어\n• 조용한 클릭으로 조용한 환경에서 사용\n• 다양한 표면에서 작동하는 광학 센서', 
NULL, NULL, 'system', NOW()),

('USB-C 케이블', 12000.00, 0, 'https://images.unsplash.com/photo-1587825140708-dfaf72ae4b04?w=500&h=500&fit=crop', 
'고속 충전 USB-C 케이블로 빠르게 충전하세요. 데이터 전송과 충전을 동시에 지원하는 프리미엄 케이블입니다.', 
'• 고속 충전 지원으로 빠른 충전 시간\n• 내구성 강화된 나일론 브레이드 소재\n• 다양한 길이 옵션 제공 (1m, 2m)\n• 고속 데이터 전송 지원 (USB 3.0)\n• 다양한 기기 호환성', 
NULL, NULL, 'system', NOW()),

('태블릿 거치대', 28000.00, 0, 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=500&h=500&fit=crop', 
'조절 가능한 태블릿 거치대로 다양한 각도에서 사용하세요. 독서부터 영상 시청까지 최적의 각도로 설정할 수 있습니다.', 
'• 각도 조절 가능으로 사용자 맞춤형 설정\n• 안정적인 지지대로 흔들림 방지\n• 접이식 디자인으로 휴대성 우수\n• 범용 호환성으로 다양한 태블릿 지원\n• 알루미늄 소재로 가볍고 튼튼', 
NULL, NULL, 'system', NOW()),

('무선 키보드', 75000.00, 0, 'https://images.unsplash.com/photo-1679533662330-457ca8447e7d?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'프리미엄 무선 키보드로 편안한 타이핑을 경험하세요. 기계식 키 스위치로 만족스러운 타건감을 제공합니다.', 
'• 기계식 키로 만족스러운 타건감\n• 백라이트 지원으로 어두운 곳에서도 사용\n• 1년 배터리 수명으로 오래 사용\n• 멀티 디바이스 연결로 여러 기기 제어\n• 조용한 스위치로 조용한 환경에서 사용', 
NULL, NULL, 'system', NOW()),

('게이밍 마우스패드', 25000.00, 0, 'https://images.unsplash.com/photo-1612198188060-c7c2a3b66eae?w=500&h=500&fit=crop', 
'대형 게이밍 마우스패드로 정밀한 조작이 가능합니다. 부드러운 표면으로 마우스 움직임을 정확하게 추적합니다.', 
'• 대형 사이즈로 넓은 움직임 지원\n• 부드러운 표면으로 정밀한 조작\n• 미끄럼 방지 바닥으로 고정\n• 내구성 있는 소재로 오래 사용\n• 다양한 디자인으로 개성 표현', 
NULL, NULL, 'system', NOW()),

('웹캠 HD', 95000.00, 0, 'https://plus.unsplash.com/premium_photo-1685193950944-53efdddf79a9?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'고화질 웹캠으로 선명한 영상 통화를 즐기세요. 자동 조명 보정 기능으로 어두운 환경에서도 밝은 화면을 제공합니다.', 
'• Full HD 1080p 해상도로 선명한 화질\n• 자동 조명 보정으로 어두운 곳에서도 밝게\n• 내장 마이크로 깨끗한 음질\n• 프라이버시 셔터로 보안 강화\n• 다양한 플랫폼 지원 (Windows, Mac, Linux)', 
NULL, NULL, 'system', NOW()),

('스탠딩 데스크', 180000.00, 0, 'https://images.unsplash.com/photo-1524758631624-e2822e304c36?w=500&h=500&fit=crop', 
'전동 스탠딩 데스크로 건강한 업무 환경을 만들어보세요. 앉아서 일하는 시간을 줄이고 건강을 지킬 수 있습니다.', 
'• 전동 높이 조절로 편리한 사용\n• 넓은 작업 공간으로 다양한 용도 지원\n• 안정적인 구조로 흔들림 방지\n• 메모리 기능으로 자주 사용하는 높이 저장\n• 조용한 모터로 조용한 환경에서 사용', 
NULL, NULL, 'system', NOW()),

('USB 허브', 35000.00, 0, 'https://images.unsplash.com/photo-1625842268584-8f3296236761?w=500&h=500&fit=crop', 
'다중 포트 USB 허브로 여러 기기를 동시에 연결하세요. 고속 데이터 전송을 지원하는 프리미엄 허브입니다.', 
'• 7포트 USB 3.0 허브로 여러 기기 연결\n• 고속 데이터 전송 지원 (5Gbps)\n• 개별 전원 스위치로 선택적 사용\n• 컴팩트한 디자인으로 공간 절약\n• 다양한 기기 호환성', 
NULL, NULL, 'system', NOW()),

('모니터 스탠드', 55000.00, 0, 'https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=500&h=500&fit=crop', 
'조절 가능한 모니터 스탠드로 최적의 시야각을 설정하세요. 목과 어깨의 피로를 줄이고 작업 효율을 높여줍니다.', 
'• 높이 및 각도 조절로 맞춤형 설정\n• 넓은 지지대로 안정적인 고정\n• 케이블 관리 기능으로 깔끔한 정리\n• 다양한 모니터 크기 지원 (24~32인치)\n• 알루미늄 소재로 가볍고 튼튼', 
NULL, NULL, 'system', NOW()),

('블루투스 어댑터', 18000.00, 0, 'https://images.unsplash.com/photo-1587825140708-dfaf72ae4b04?w=500&h=500&fit=crop', 
'USB 블루투스 어댑터로 기존 PC에 무선 기능을 추가하세요. 간편한 설치로 즉시 사용할 수 있습니다.', 
'• USB 플러그 앤 플레이로 간편한 설치\n• 블루투스 5.0 지원으로 안정적인 연결\n• 다양한 기기와 호환 (이어폰, 스피커, 키보드 등)\n• 컴팩트한 디자인으로 공간 절약\n• 저전력 소비로 효율적인 사용', 
NULL, NULL, 'system', NOW()),

('노이즈 캔슬링 이어폰', 150000.00, 0, 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500&h=500&fit=crop', 
'프리미엄 노이즈 캔슬링 이어폰으로 완벽한 음악 감상을 즐기세요. 외부 소음을 차단하고 최고의 음질을 제공합니다.', 
'• 액티브 노이즈 캔슬링으로 주변 소음 완전 차단\n• 프리미엄 드라이버로 고음질 구현\n• 40시간 배터리 수명으로 장시간 사용\n• 터치 제어로 편리한 조작\n• 프리미엄 케이스 포함으로 안전한 보관', 
NULL, NULL, 'system', NOW());

-- 의류 (category_id = 1) — 남/녀/아동 이미지 URL은 서로 중복 없음. 아동은 키즈·유아 분위기 사진만 사용.
INSERT IGNORE INTO products (name, price, discount, image, description, details, category_id, sub_category_id, created_by, created_date) VALUES
('남성 캐주얼 데님 트러커 재킷', 89000.00, 0, 'https://images.unsplash.com/photo-1551028719-00167b16eac5?w=500&h=500&fit=crop', 
'남성용 데님 트러커 재킷으로 캐주얼 룩을 완성합니다.', 
'• 두께감 있는 데님\n• 가슴·사이드 포켓\n• S~XXL\n• 세탁 시 뒤집어 세탁 권장', 
1, 1, 'system', NOW()),

('남성 베이직 후드 스웨트셔츠', 45000.00, 0, 'https://images.unsplash.com/photo-1556821840-3a63f95609a7?w=500&h=500&fit=crop', 
'데일리로 입기 좋은 남성 후드 스웨트셔츠입니다.', 
'• 면 혼방 기모/논기모 선택\n• 이중 후드·시보리 마감\n• 오버핏·레귤러 핏', 
1, 1, 'system', NOW()),

('남성 테이퍼드 슬랙스', 65000.00, 0, 'https://images.unsplash.com/photo-1473966968600-fa801b869a1a?w=500&h=500&fit=crop', 
'발목이 좁아져 깔끔한 실루엣의 남성 테이퍼드 슬랙스입니다.', 
'• 주름 적은 원단\n• 비즈니스 캐주얼에 적합\n• 허리 밴딩 옵션', 
1, 1, 'system', NOW()),

('남성 케이블 라운드 니트', 75000.00, 0, 'https://images.unsplash.com/photo-1576566588028-4147f3842f27?w=500&h=500&fit=crop', 
'클래식 케이블 조직의 남성 라운드 니트입니다.', 
'• 부드러운 울·아크릴 블렌드\n• 목 늘어남 방지 네크\n• 레이어드용 기본 핏', 
1, 1, 'system', NOW()),

('남성 에센셜 트레이닝 세트', 120000.00, 0, 'https://images.unsplash.com/photo-1552902865-b72c031ac5ea?w=500&h=500&fit=crop', 
'운동·일상 겸용 남성 트레이닝 상·하의 세트입니다.', 
'• 흡습속건 원단\n• 지퍼 포켓\n• 세트 구매 시 할인', 
1, 1, 'system', NOW()),

('남성 스트레이트 핏 청바지', 89000.00, 0, 'https://images.unsplash.com/photo-1542272604-787c3835535d?w=500&h=500&fit=crop', 
'다리가 곧게 떨어지는 남성 스트레이트 핏 데님입니다.', 
'• 중간 워싱·진청·블랙\n• 신축 데님 옵션\n• 28~38인치', 
1, 1, 'system', NOW()),

('남성 오버핏 맨투맨', 52000.00, 0, 'https://images.unsplash.com/photo-1620799140408-edc6dcb6d633?w=500&h=500&fit=crop', 
'어깨가 넉넉한 남성 오버핏 맨투맨입니다.', 
'• 코튼 블렌드\n• 기모/싱글 선택\n• 프린트 무지 모델', 
1, 1, 'system', NOW()),

('여성 울 블렌드 핸드메이드 코트', 180000.00, 0, 'https://images.unsplash.com/photo-1539533018447-63fcce2678e3?w=500&h=500&fit=crop', 
'겨울 외출용 여성 롱 코트입니다.', 
'• 울 혼방\n• 더블·싱글 중 선택\n• 안감 퀼팅', 
1, 2, 'system', NOW()),

('여성 플레어 미디 원피스', 89000.00, 0, 'https://images.unsplash.com/photo-1595777457583-95e059d581b8?w=500&h=500&fit=crop', 
'밑단이 퍼지는 플레어 실루엣의 여성 미디 원피스입니다.', 
'• 허리 밴딩·끈 옵션\n• 안감 포함(일부 컬러)\n• 데일리·모임 겸용', 
1, 2, 'system', NOW()),

('여성 새틴 브이넥 블라우스', 72000.00, 0, 'https://images.unsplash.com/photo-1594633312681-425c7b97ccd1?w=500&h=500&fit=crop', 
'은은한 광택의 여성 브이넥 블라우스입니다.', 
'• 오피스·데이트 룩\n• 비침 완화 이너 추천\n• 드라이클리닝 권장', 
1, 2, 'system', NOW()),

('여성 울 혼방 H라인 미디 스커트', 68000.00, 0, 'https://images.unsplash.com/photo-1496747611176-843222e1e57c?w=500&h=500&fit=crop', 
'세로 라인이 깔끔한 여성 H라인 미디 스커트입니다.', 
'• 뒷트임·히든 밴딩\n• 사계절 두께 선택\n• 블라우스·니트와 매치', 
1, 2, 'system', NOW()),

('여성 오버사이즈 라운드 니트', 78000.00, 0, 'https://images.unsplash.com/photo-1529139574466-a303027c1d8b?w=500&h=500&fit=crop', 
'여유 있는 핏의 여성 오버사이즈 니트입니다.', 
'• 부드러운 편직\n• 팔꿈치 박음 강화\n• 레깅스·스키니진과 코디', 
1, 2, 'system', NOW()),

('여성 하이웨스트 스키니 데님', 84000.00, 0, 'https://images.unsplash.com/photo-1618354691373-d851c5c3a990?w=500&h=500&fit=crop', 
'허리를 안정적으로 잡아주는 여성 하이웨스트 스키니 청바지입니다.', 
'• 스트레치 데님\n• 9부·롱 기장\n• 워싱 다양', 
1, 2, 'system', NOW()),

('여성 크롭 박시 스웨트셔츠', 48000.00, 0, 'https://images.unsplash.com/photo-1515886657613-9f3515b0c78f?w=500&h=500&fit=crop', 
'짧은 기장의 여성 크롭 스웨트셔츠입니다.', 
'• 하이웨스트 하의와 매치 추천\n• 기모/싱글\n• 박시 어깨 라인', 
1, 2, 'system', NOW()),

('유아 순면 바디수트 3종 세트', 36000.00, 0, 'https://images.unsplash.com/photo-1503454537195-1dcabb73ffb9?w=500&h=500&fit=crop', 
'신생아·영아용 순면 바디수트 3종 세트입니다.', 
'• 스냅 단추로 기저귀 교환 편리\n• 무봉제 라벨\n• 60~90cm', 
1, 3, 'system', NOW()),

('주니어 체크 파자마 세트', 42000.00, 0, 'https://images.unsplash.com/photo-1602810318383-e386cc2a3ccf?w=500&h=500&fit=crop', 
'잠자리·홈웨어용 주니어 파자마 상·하 세트입니다.', 
'• 순면·모달 혼방\n• 상·하 별도 사이즈 가능\n• 세탁 후 보풀 적은 편직', 
1, 3, 'system', NOW()),

('키즈 스트라이프 티 & 멜빵 반바지 세트', 48000.00, 0, 'https://images.unsplash.com/photo-1511895426328-dc8714191300?w=500&h=500&fit=crop', 
'상큼한 스트라이프 티와 멜빵 반바지로 구성된 키즈 세트입니다.', 
'• 멜빵 길이 조절 가능\n• 활동적인 놀이에 적합\n• 100~140cm', 
1, 3, 'system', NOW()),

('여아용 프릴 카라 블라우스', 35000.00, 0, 'https://images.unsplash.com/photo-1615175254861-f9f581d95996?w=500&h=500&fit=crop', 
'프릴·레이스 디테일이 들어간 여아용 블라우스입니다. (썸네일은 유사 무드의 키즈 드레스 착용 예시입니다.)', 
'• 학원·외출복\n• 스커트·바지 모두 매치\n• 비침 적은 원단\n• 100~140cm', 
1, 3, 'system', NOW()),

('남아용 그래픽 반팔 티 2매 세트', 32000.00, 0, 'https://images.unsplash.com/photo-1563773617060-f29607ce70a1?w=500&h=500&fit=crop', 
'반팔 티셔츠를 입은 남아 착장 예시입니다. 캐릭터·레터링 그래픽 티 2매 세트로 구성됩니다.', 
'• 면 100% 기준\n• 목 늘어남 방지\n• 100~150cm', 
1, 3, 'system', NOW()),

('키즈 경량 후드 윈드브레이커', 59000.00, 0, 'https://images.unsplash.com/photo-1519689680058-324335c77eba?w=500&h=500&fit=crop', 
'가볍게 챙기기 좋은 키즈 후드 바람막이입니다.', 
'• 생활 방수\n• 접어 보관 가능\n• 야광·반사 띠',
1, 3, 'system', NOW()),

('키즈 판초형 레인코트', 45000.00, 0, 'https://images.unsplash.com/photo-1758535291260-d02ee9ae1ceb?w=500&h=500&fit=crop', 
'노란 우비를 입은 아동 컷으로, 비 오는 날 외출·등하원에 맞는 판초형 레인코트입니다.', 
'• 책가방 공간 확보 디자인\n• 투명 챙 옵션\n• 비닐·EVA 소재\n• 우산과 함께 쓰기 좋음', 
1, 3, 'system', NOW()),

('남성 순면 드로즈 3종 세트', 42000.00, 0, 'https://images.unsplash.com/photo-1608051000941-9250945deab9?w=500&h=500&fit=crop', 
'부드러운 순면 남성 드로즈 3종 세트입니다. (썸네일은 소재 질감·접지 컷 예시입니다.)', 
'• 통기성 좋은 코튼\n• 밴딩 늘어남 방지\n• M~XL\n• 세탁 시 뒤집어 세탁 권장', 
1, 4, 'system', NOW()),

('남성 베이직 런닝 2매 세트', 28000.00, 0, 'https://images.unsplash.com/photo-1581655353564-df123a1eb820?w=500&h=500&fit=crop', 
'이너로 입기 좋은 남성 런닝(나시) 2매 세트입니다.', 
'• 면·모달 혼방 옵션\n• 어깨·암홀 여유 핏\n• 땀 흡수에 유리한 편직', 
1, 4, 'system', NOW()),

('여성 노와이어 소프트 브라', 36000.00, 0, 'https://images.unsplash.com/photo-1620799139507-2a76f79a2f4d?w=500&h=500&fit=crop', 
'와이어 없이 편안한 여성 데일리 브라입니다. (썸네일은 베이직 이너·티 플랫레이 예시입니다.)', 
'• 노와이어·부드러운 컵 라인\n• 후크·어깨끈 조절\n• S~L', 
1, 4, 'system', NOW()),

('여성 모달 데일리 팬티 3매 세트', 32000.00, 0, 'https://images.unsplash.com/photo-1618338054411-5341de249394?w=500&h=500&fit=crop', 
'착용감이 부드러운 모달 혼방 여성 팬티 3매 세트입니다.', 
'• 햄·빅라인 중 선택\n• 라벨 프린팅\n• 세탁 시 뒤집어 세탁', 
1, 4, 'system', NOW()),

('여성 스포츠 쿨링 브라탑', 39000.00, 0, 'https://images.unsplash.com/photo-1651761179569-4ba2aa054997?w=500&h=500&fit=crop', 
'가벼운 운동·홈트용 여성 브라탑입니다.', 
'• 흡습속건 원단\n• 패드 탈부착형\n• 요가·필라테스에 적합', 
1, 4, 'system', NOW()),

('남성 캐주얼 중목 양말 3켤레', 18000.00, 0, 'https://images.unsplash.com/photo-1640025867572-f6b3a8410c81?w=500&h=500&fit=crop', 
'데일리에 맞는 남성 중목 양말 3켤레 세트입니다.', 
'• 뒤꿈치·발바닥 보강 편직\n• 25~28cm\n• 세탁 시 망에 넣기 권장', 
1, 4, 'system', NOW()),

('여성 패턴 페이크삭스 4켤레', 16000.00, 0, 'https://images.unsplash.com/photo-1562015120-395a93b28310?w=500&h=500&fit=crop', 
'로퍼·스니커즈용 여성 페이크삭스(덧신) 4켤레 세트입니다.', 
'• 뒤꿈치 미끄럼 방지 실리콘(일부 컬러)\n• 얇은 두께로 발열 최소화\n• FREE 사이즈', 
1, 4, 'system', NOW()),

('골드 월계수·진주 포인트 3단 레이어드 네크리스', 52000.00, 0, 'https://images.unsplash.com/photo-1590548784585-643d2b9f2925?q=80&w=1064&auto=format&fit=crop',
'길이가 다른 세 줄의 골드 톤 체인으로 연출한 레이어드 네크리스입니다. 가운데 줄은 월계수 잎 모티프, 맨 아래는 작은 화이트 비즈·진주 포인트가 달려 데일리·보헤미안 무드에 잘 어울립니다.', 
'• 3줄 세트(한 번에 착용 연출)\n• 니켈 프리 도금\n• 길이 조절 익스텐더\n• 올리브·베이지 톤 상의와 매치 추천', 
1, 5, 'system', NOW()),

('데일리 골드 스택 링 10종 세트', 36000.00, 0, 'https://images.unsplash.com/photo-1731586249471-82bb9b2f769a?q=80&w=987&auto=format&fit=crop',
'얇은 밴드부터 포인트 스톤 링까지 골드 톤 링 여러 개를 한 세트로 구성했습니다. 여러 손가락에 나눠 끼거나 한 손가락에 스택해 트렌디하게 연출할 수 있습니다.', 
'• 약 10~12p 구성(디자인 혼합)\n• 오팔·펄 느낌 인레이 링 포함\n• US 5~8 / 국내 9~16호 범위 착용 가이드\n• 세탁·수영 시 착용 비권장', 
1, 5, 'system', NOW()),

('오션 블루 브릴리언트 컷 장식 스톤 4P 세트', 78000.00, 0, 'https://images.unsplash.com/photo-1613843351058-1dd06fda7c02?q=80&w=1674&auto=format&fit=crop',
'투명감 있는 스카이 블루 톤의 라운드 브릴리언트 컷 스톤 네 알 세트입니다. 반지·펜던트 리폼, 드레스 핀·헤어 액세서리 포인트 등 취미·촬영 소품으로 활용하기 좋습니다.', 
'• 4알 세트(사이즈·컷 통일감)\n• 합성 크리스탈·큐빅 계열(천연 보석 아님)\n• 접착·세팅은 별도 공구·재료 사용\n• 직사광선·충격에 주의', 
1, 5, 'system', NOW()),

('실버 미니 후프 이어링 & 골드 헤링본 체인 네크리스 세트', 58000.00, 0, 'https://images.unsplash.com/photo-1600721391776-b5cd0e0048f9?q=80&w=987&auto=format&fit=crop',
'은색 광택의 두께감 있는 미니 후프 이어링과, 목선에 밀착되는 골드 톤 헤링본(스네이크) 체인 네크리스를 한 세트로 맞춘 데일리 주얼리입니다. 화이트 셔츠·이너와 깔끔하게 매치됩니다.', 
'• 후프 1쌍 + 네크리스 1줄\n• 티타늄·스틸 포스트(이어링)\n• 네크리스 길이 40~45cm 내외\n• 세트 분리 착용 가능', 
1, 5, 'system', NOW()),

('마블 플랫레이 지오메트릭 레이어드 네크리스 & 링 세트', 49000.00, 0, 'https://images.unsplash.com/photo-1603298333647-ed142dbbc9d9?q=80&w=986&auto=format&fit=crop',
'화이트 마블 위에 올린 듯한 연출처럼, 삼중 레이어 네크리스(삼각 펜던트·텍스처 비즈 포인트)와 인터로킹 링·슬림 밴드 링이 함께 구성된 세트입니다.', 
'• 네크리스 1조 + 링 여러 개(세트 구성)\n• 골드 톤 도금\n• 마블 톤 배경과 어울리는 미니멀 무드\n• 습기·향수 직접 접촉 시 변색 주의', 
1, 5, 'system', NOW()),

('미드나잇 슬림 스마트폰 하드 케이스', 32000.00, 0, 'https://images.unsplash.com/photo-1423784346385-c1d4dac9893a?w=500&h=500&fit=crop', 
'어두운 톤의 손과 스마트폰 실루엣에 맞춘 슬림 하드 케이스입니다. 얇은 두께로 그립감을 살리고, 스크래치·미세 충격에 견디도록 설계했습니다.', 
'• 매트·소프트터치 옵션\n• 카메라·버튼 오픈 컷\n• 무선 충전 호환(기기별 상이)\n• 기종별 모델 선택 필수', 
1, 5, 'system', NOW()),

('미니멀 블랙 광폭 밴드 링 2종 세트', 44000.00, 0, 'https://images.unsplash.com/photo-1723576441002-ce9fe9c06343?q=80&w=2232&auto=format&fit=crop',
'건·블랙 세라믹 또는 터스텐 느낌의 고광택 밴드 링 두 개 세트입니다. 장식 없이 실루엣만으로 포인트를 주는 커플·데일리용 미니멀 링으로 추천합니다.', 
'• 2개 1세트(동일 디자인·사이즈 옵션)\n• 컴포트 핏 라운드 프로파일\n• 9~21호(반 사이즈 문의)\n• 금속 알레르기 시 소재 확인 후 구매', 
1, 5, 'system', NOW()),

('클래식 화이트 캔버스 스니커즈', 99000.00, 0, 'https://images.unsplash.com/photo-1588361861040-ac9b1018f6d5?q=80&w=1064&auto=format&fit=crop',
'데일리에 신기 좋은 화이트 캔버스 스니커즈입니다.', 
'• 쿠션 인솔\n• 230~280mm\n• 끈 탈착 세탁 가능', 
1, 6, 'system', NOW()),

('에어 메쉬 러닝화', 129000.00, 0, 'https://images.unsplash.com/photo-1603808033192-082d6919d3e1?q=80&w=1015&auto=format&fit=crop',
'가벼운 조깅·헬스에 맞는 메쉬 러닝화입니다.', 
'• 통기성 메쉬 갑피\n• 미끄럼 방지 아웃솔\n• 반 사이즈 업 권장', 
1, 6, 'system', NOW()),

('윈터 레더 첼시 부츠', 168000.00, 0, 'https://images.unsplash.com/photo-1562183241-b937e95585b6?q=80&w=1065&auto=format&fit=crop&',
'가죽 갑피의 첼시·로퍼 룩 부츠입니다.', 
'• 탄성 사이드 고무\n• 240~280mm\n• 방수 스프레이 권장', 
1, 6, 'system', NOW()),

('캠핑 슬라이드 샌들', 42000.00, 0, 'https://images.unsplash.com/photo-1556774687-0e2fdd0116c0?q=80&w=2232&auto=format&fit=crop',
'캠핑·피크닉 무드에 맞는 슬라이드형 샌들입니다. (썸네일은 아웃도어 무드 예시입니다.)', 
'• 경량 EVA·고무 혼용\n• 230~280mm\n• 물놀이 후 건조 권장', 
1, 6, 'system', NOW()),

('트레킹 워킹 슈즈', 118000.00, 0, 'https://images.unsplash.com/photo-1584609226397-de5612afdfea?q=80&w=1674&auto=format&fit=crop',
'숲길·가벼운 트레킹에 맞는 워킹 슈즈입니다. (썸네일은 산책로 무드 예시입니다.)', 
'• 접지력 아웃솔\n• 발볼 넓은 핏 옵션\n• 방수 라이닝(모델별)', 
1, 6, 'system', NOW()),

('스포츠 에어 슬립온', 105000.00, 0, 'https://images.unsplash.com/photo-1581803274518-8d42d0c961de?q=80&w=987&auto=format&fit=crop',
'숲길 산책에 어울리는 경량 슬립온입니다. (썸네일은 숲·산책 무드 예시입니다.)', 
'• 이지 온·오프\n• 메쉬 갑피 옵션\n• 230~275mm', 
1, 6, 'system', NOW()),

('스틸레토 미들힐 펌프스', 135000.00, 0, 'https://images.unsplash.com/photo-1560769629-975ec94e6a86?q=80&w=1064&auto=format&fit=crop',
'포멀 룩에 맞는 미들힐 펌프스입니다.', 
'• 6~7cm 힐\n• 225~250mm\n• 가죽·스웨이드 중 선택', 
1, 6, 'system', NOW()),

('소가죽 브라운 토트백', 158000.00, 0, 'https://images.unsplash.com/photo-1598532163257-ae3c6b2524b6?q=80&w=1063&auto=format&fit=crop',
'브라운 가죽 톤의 데일리 토트백입니다.', 
'• 탑 지퍼\n• 내부 오픈 포켓\n• 어깨 패드', 
1, 7, 'system', NOW()),

('미니 크로스 바디백', 92000.00, 0, 'https://images.unsplash.com/photo-1584917865442-de89df76afd3?q=80&w=1035&auto=format&fit=crop',
'핸드폰·지갑 위주로 메기 좋은 크로스 바디백입니다.', 
'• 조절 스트랩\n• 전면 플랩 포켓\n• 가벼운 착용감',
1, 7, 'system', NOW()),

('컬러 포인트 숄더백', 128000.00, 0, 'https://images.unsplash.com/photo-1590874103328-eac38a683ce7?q=80&w=1038&auto=format&fit=crop',
'레드 톤 포인트가 있는 숄더·토트 겸용 백입니다.', 
'• 탑 핸들·숄더 겸용\n• 지퍼 메인\n• 데이트·외출용', 
1, 7, 'system', NOW()),

('빈티지 레더 백팩', 185000.00, 0, 'https://images.unsplash.com/photo-1553062407-98eeb64c6a62?q=80&w=987&auto=format&fit=crop',
'가죽 질감이 살아 있는 빈티지 무드 백팩입니다.', 
'• 노트북 슬리브 13인치\n• 앞면 버클 포켓\n• 등판 패딩', 
1, 7, 'system', NOW()),

('컴팩트 데일리 백팩', 135000.00, 0, 'https://plus.unsplash.com/premium_photo-1680392544041-d89413b561ce?q=80&w=927&auto=format&fit=crop',
'출퇴근용 컴팩트 사이즈 백팩입니다.', 
'• 15L 내외\n• 측면 물병 포켓\n• USB 패스스루(모델별)', 
1, 7, 'system', NOW()),

('방수 슬링 힙색', 58000.00, 0, 'https://images.unsplash.com/photo-1605733513597-a8f8341084e6?q=80&w=2129&auto=format&fit=crop',
'폭포·계곡 여행 무드에 맞는 경량 슬링·힙색입니다. (썸네일은 아웃도어 풍경 예시입니다.)', 
'• 방수 원단\n• 메인 지퍼 2중\n• 허리·크로스 겸용', 
1, 7, 'system', NOW()),

('나이트 캠핑 에코 토트', 45000.00, 0, 'https://images.unsplash.com/photo-1544816155-12df9643f363?q=80&w=987&auto=format&fit=crop',
'밤하늘 무드 프린트 에코 토트입니다. (썸네일은 야경·캠핑 무드 예시입니다.)', 
'• 재생 폴리 혼방\n• 내부 소형 포켓\n• 접이 보관', 
1, 7, 'system', NOW()),

('썸머 쿼츠 필드 워치', 165000.00, 0, 'https://images.unsplash.com/photo-1563861826100-9cb868fdbe1c?q=80&w=2070&auto=format&fit=crop',
'바다·휴양지 무드에 맞는 썸머 필드 워치입니다. (썸네일은 해변·파도 무드 예시입니다.)', 
'• 40~42mm 케이스\n• 나토·러버 밴드 선택\n• 5ATM 생활방수', 
1, 8, 'system', NOW()),

('미니멀 실버 톤 시계', 142000.00, 0, 'https://images.unsplash.com/photo-1541480601022-2308c0f02487?q=80&w=2070&auto=format&fit=crop',
'안개·미니멀 톤 배경에 어울리는 심플 3핀 시계입니다. (썸네일은 무드 예시 컷입니다.)', 
'• 메탈 메시 밴드 옵션\n• 데이트 창\n• 배터리형 쿼츠', 
1, 8, 'system', NOW()),

('클래식 레더 스트랩 시계', 188000.00, 0, 'https://images.unsplash.com/photo-1509048191080-d2984bad6ae5?q=80&w=1064&auto=format&fit=crop',
'가을·겨울 코트와 매치하기 좋은 레더 스트랩 시계입니다. (썸네일은 자연광 무드 예시입니다.)', 
'• 가죽 밴드\n• 미네랄 글라스\n• 3ATM 생활방수', 
1, 8, 'system', NOW()),

('마운틴 스포츠 워치', 210000.00, 0, 'https://plus.unsplash.com/premium_photo-1681504446264-708b83f4ea12?q=80&w=987&auto=format&fit=crop',
'산악·트레킹 무드에 맞는 스포츠 워치입니다. (썸네일은 산 풍경 예시입니다.)', 
'• 나침반·고도(모델별)\n• 루미너스 핸즈\n• 실리콘 밴드', 
1, 8, 'system', NOW()),

('어스톤 그린 다이얼 시계', 175000.00, 0, 'https://images.unsplash.com/photo-1506193095-80bc749473f2?q=80&w=1035&auto=format&fit=crop',
'위에서 본 숲·녹지 무드에 맞는 그린 포인트 다이얼 시계입니다. (썸네일은 자연 조감 예시입니다.)', 
'• 38mm 케이스\n• 가죽·패브릭 밴드\n• 데일리 착용', 
1, 8, 'system', NOW()),

('어반 크로노그래프', 248000.00, 0, 'https://images.unsplash.com/photo-1495704907664-81f74a7efd9b?q=80&w=2070&auto=format&fit=crop',
'도심 룩에 맞는 크로노그래프 시계입니다. (썸네일은 시티뷰 무드 예시입니다.)', 
'• 스톱워치 서브다이얼\n• 스테인리스 케이스\n• 5ATM 방수', 
1, 8, 'system', NOW()),

('선셋 에디션 쿼츠 시계', 198000.00, 0, 'https://images.unsplash.com/photo-1517463700628-5103184eac47?q=80&w=987&auto=format&fit=crop',
'노을·골든아워 무드에 맞는 웜톤 다이얼 시계입니다. (썸네일은 일출·일몰 풍경 예시입니다.)', 
'• 선라이즈 그라데이션 다이얼\n• 메탈 밴드\n• 선물 케이스 포함', 
1, 8, 'system', NOW());

-- 가전용품 (category_id = 2)
INSERT IGNORE INTO products (name, price, discount, image, description, details, category_id, sub_category_id, created_by, created_date) VALUES
('벽걸이형 4K UHD 스마트 TV 55인치', 1180000.00, 24, 'https://images.unsplash.com/photo-1646861039459-fd9e3aabf3fb?q=80&w=1626&auto=format&fit=crop',
'슬림 베젤의 거실용 4K 스마트 TV입니다. OTT 앱과 음성 검색으로 콘텐츠를 빠르게 찾을 수 있습니다.', 
'• 4K UHD 패널\n• HDR10/HLG 지원(모델별)\n• HDMI 3~4포트\n• Wi-Fi 내장\n• 벽걸이 브라켓 별매', 
2, 9, 'system', NOW()),

('QLED 스마트 TV 65인치', 1450000.00, 24, 'https://images.unsplash.com/photo-1509281373149-e957c6296406?q=80&w=1028&auto=format&fit=crop',
'밝은 거실에서도 선명한 QLED 계열 대화면 TV입니다. 게임 모드·ALLM로 콘솔 연결에도 적합합니다.',
'• 65인치 클래스\n• 저반사·광시야각(모델별)\n• eARC 지원\n• 리모컨 음성 입력\n• 스탠드·벽걸이 겸용',
2, 9, 'system', NOW()),

('OLED 4K 스마트 TV 48인치', 1390000.00, 24, 'https://images.unsplash.com/photo-1567690187548-f07b1d7bf5a9?q=80&w=1036&auto=format&fit=crop',
'깊은 블랙과 높은 대비가 강점인 중소형 OLED 스마트 TV입니다. 영화·드라마 감상용으로 인기 있는 사이즈입니다.',
'• 4K OLED 패널\n• 무한대 명암비 체감\n• 필름메이커 모드(모델별)\n• 블루라이트 완화 설정\n• 48인치 클래스',
2, 9, 'system', NOW()),

('넷플릭스 버튼 리모컨 스마트 TV 50인치', 920000.00, 12, 'https://images.unsplash.com/photo-1574974409771-cebec54deb00?q=80&w=1673&auto=format&fit=crop',
'전용 OTT 단축키가 있는 보급형 스마트 TV입니다. 침실·원룸 보조 TV로 쓰기 좋습니다.',
'• FHD 또는 4K(모델별)\n• 넷플릭스·유튜브 단축키\n• USB 미디어 재생\n• 50인치 클래스\n• 에너지 소비효율 1~2등급(모델별)',
2, 9, 'system', NOW()),

('울트라 슬림 베젤 4K TV 58인치', 1080000.00, 20, 'https://plus.unsplash.com/premium_photo-1682125724182-1eadf9d1360d?q=80&w=1628&auto=format&fit=crop',
'거의 보이지 않는 슬림 베젤로 화면만 돋보이는 미니멀 디자인 TV입니다. 거실 인테리어와 잘 어울립니다.',
'• 초슬림 베젤\n• 4K 업스케일링(모델별)\n• 블루투스 오디오 아웃\n• 58인치 클래스\n• 3-sided 무테 룩',
2, 9, 'system', NOW()),

('게이밍 120Hz 스마트 TV 55인치', 1320000.00, 20, 'https://plus.unsplash.com/premium_photo-1683141392308-aaa39d916686?q=80&w=1480&auto=format&fit=crop',
'고주사율·저지연을 지향하는 게이밍에 맞춘 스마트 TV입니다. PC·콘솔 연결 시 반응 속도를 살려 줍니다.',
'• 120Hz 패널(모델별)\n• VRR/ALLM(모델별)\n• 게임 바 UI\n• 55인치 클래스\n• HDMI 2.1 포트(모델별)',
2, 9, 'system', NOW()),

('시네마 모드 4K HDR TV 75인치', 1890000.00, 20, 'https://plus.unsplash.com/premium_photo-1681044639826-5a00340735eb?q=80&w=2070&auto=format&fit=crop',
'대형 화면으로 영화관 같은 몰입감을 주는 75인치급 4K TV입니다. 거실 메인 TV로 적합합니다.',
'• 75인치 클래스\n• 멀티 HDR 포맷(모델별)\n• 시네마·스포츠 모드\n• 스피커 출력 20W급(모델별)\n• 대형 스탠드 포함(모델별)',
2, 9, 'system', NOW()),

('빌트인 양문형 냉장고 870L', 2890000.00, 24, 'https://images.unsplash.com/photo-1722603929403-de9e80c46a9a?q=80&w=987&auto=format&fit=crop',
'대용량 양문형 냉장고로 주간 장보기·가족 식재료를 한 번에 보관하세요. 냉장·냉동 구역이 넉넉합니다.',
'• 양문형 870L급(모델별)\n• 급속냉·급속냉동(모델별)\n• 항균 가스켓(모델별)\n• LED 내부등\n• 에너지 효율 1~2등급(모델별)',
2, 10, 'system', NOW()),

('메탈 실버 4도어 냉장고', 2450000.00, 22, 'https://images.unsplash.com/photo-1722859178634-ccc8ea5680d2?q=80&w=2070&auto=format&fit=crop',
'4도어 구성으로 냉장·냉동·야채칸을 분리해 보관하기 쉬운 프리미엄 냉장고입니다.',
'• 멀티 도어\n• 독립 냉각(모델별)\n• 물·얼음 디스펜서(모델별)\n• 금속 룩 패널\n• 스마트 진단(모델별)',
2, 10, 'system', NOW()),

('원룸용 미니 냉장고 130L', 289000.00, 0, 'https://images.unsplash.com/photo-1721563927724-74b1a0ddef33?q=80&w=1000&auto=format&fit=crop',
'좁은 주방·원룸에 맞는 컴팩트 냉장고입니다. 음료·간식·소량 식재료 보관에 적합합니다.',
'• 130L급(모델별)\n• 상칸 냉동실\n• 조용한 컴프레서(모델별)\n• 높이 조절 선반\n• 1인 가구 추천',
2, 10, 'system', NOW()),

('노크온 미러글라스 양문형 냉장고', 3150000.00, 20, 'https://images.unsplash.com/photo-1571175443880-49e1d25b2bc5?q=80&w=987&auto=format&fit=crop',
'문을 두드리면 내부가 비치는 미러 패널 양문형입니다. 주방 인테리어 포인트로 인기 있는 타입입니다.',
'• 미러·글라스 도어(모델별)\n• 노크온 뷰(모델별)\n• 대용량 선반\n• 탈취 필터(모델별)\n• 프리미엄 마감',
2, 10, 'system', NOW()),

('상냉장 하냉동 2도어 냉장고', 1120000.00, 24, 'https://images.unsplash.com/photo-1649518755041-651c29b56309?q=80&w=987&auto=format&fit=crop',
'전통적인 상냉장·하냉동 2도어 구성으로 사용이 직관적인 가정용 냉장고입니다.',
'• 2도어 구성\n• 야채함·계란칸\n• 급속냉(모델별)\n• 이지 클린 내부\n• 중형 가족 추천',
2, 10, 'system', NOW()),

('김치냉장고 겸용 냉장고', 1980000.00, 20, 'https://images.unsplash.com/photo-1662647343598-951788d86310?q=80&w=987&auto=format&fit=crop',
'김치·장류 보관에 맞춘 저온·항온 모드가 있는 냉장고입니다. 한식 냉장 수납에 유리합니다.',
'• 김치·야채 전용칸(모델별)\n• 항온 유지(모델별)\n• 탈취(모델별)\n• 대용량 서랍\n• 부모님 선물용 인기',
2, 10, 'system', NOW()),

('화이트 베이직 냉장고 350L', 890000.00, 12, 'https://images.unsplash.com/photo-1662647343528-f7a5ed62c2dd?q=80&w=1049&auto=format&fit=crop',
'깔끔한 화이트 톤의 중형 냉장고입니다. 신혼·소형 가족 거실·주방에 무난하게 어울립니다.',
'• 350L급(모델별)\n• 글라스 선반\n• 냉동실 분리\n• A급 소비전력(모델별)\n• 교체형 필터(모델별)',
2, 10, 'system', NOW()),

('드럼 세탁기 12kg', 980000.00, 12, 'https://plus.unsplash.com/premium_photo-1675937428916-535f85f41c61?q=80&w=987&auto=format&fit=crop',
'대용량 드럼 세탁기로 이불·아우터까지 한 번에 세탁하세요. 저소음·진동 완화 설계(모델별)입니다.',
'• 12kg급(모델별)\n• 버블/스팀(모델별)\n• 급속 코스\n• 자동 세제 투입(모델별)\n• 인버터 모터(모델별)',
2, 11, 'system', NOW()),

('건조기 연동 세탁기 세트', 1890000.00, 20, 'https://images.unsplash.com/photo-1626806787461-102c1bfaaea1?q=80&w=2071&auto=format&fit=crop',
'세탁 후 바로 건조기로 이어 쓰기 좋은 세트 구성(모델별)입니다. 장마철·겨울 빨래 건조에 유리합니다.',
'• 세탁+건조 연동 UI(모델별)\n• 저온 건조(모델별)\n• 섬유 유연제 자동(모델별)\n• 대용량 드럼\n• 스택킷 별매(모델별)',
2, 11, 'system', NOW()),

('미니 드럼 세탁기 5kg', 520000.00, 14, 'https://images.unsplash.com/photo-1622473590925-e3616c0a41bf?q=80&w=992&auto=format&fit=crop',
'속옷·운동복·아기 옷을 따로 돌리기 좋은 소형 드럼 세탁기입니다.',
'• 5kg급(모델별)\n• 15분 급속\n• 살균/스팀(모델별)\n• 베란다·원룸 설치\n• 저소음(모델별)',
2, 11, 'system', NOW()),

('트윈워시 세탁기', 2150000.00, 22, 'https://images.unsplash.com/photo-1626806819282-2c1dc01a5e0c?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'상단 미니통+하단 대용량으로 색깔별·용도별 동시 세탁이 가능한 트윈 타입입니다.',
'• 트윈 드럼(모델별)\n• 동시 세탁\n• 스마트 진단(모델별)\n• 인버터\n• 대가족 추천',
2, 11, 'system', NOW()),

('인버터 드럼 세탁기 10kg', 820000.00, 14, 'https://plus.unsplash.com/premium_photo-1676320514014-f76904adeace?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'전기 요금을 아끼는 인버터 모터 드럼 세탁기입니다. 표준 가정에 무난한 10kg급입니다.',
'• 10kg급(모델별)\n• 인버터 DD모터(모델별)\n• 아기옷/울 코스\n• 자동 잔량 세제(모델별)\n• 도어 잠금',
2, 11, 'system', NOW()),

('히트펌프 의류건조기 9kg', 1120000.00, 24, 'https://images.unsplash.com/photo-1655041448985-f6666cba2d6c?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'히트펌프 방식으로 옷감 손상을 줄이며 건조하는 대용량 건조기입니다. 세탁기 위 스택 설치 가능(모델별).',
'• 9kg 건조(모델별)\n• 저온 건조\n• 섬유 케어 코스\n• 필터 청소 알림(모델별)\n• 인버터(모델별)',
2, 11, 'system', NOW()),

('통돌이 세탁기 18kg', 640000.00, 14, 'https://images.unsplash.com/photo-1626806787461-102c1bfaaea1?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'대용량 통돌이로 이불·러그 세탁에 강한 세탁기입니다. 가성비 중시 가정에 적합합니다.',
'• 18kg급(모델별)\n• 3D 워터플로우(모델별)\n• 세탁통 살균(모델별)\n• 상·하 분리 세탁(모델별)\n• 짧은 설치 공간',
2, 11, 'system', NOW()),

('스틱형 무선 청소기 HEPA', 358000.00, 0, 'https://plus.unsplash.com/premium_photo-1676810460039-661fc847e395?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'손목 부담을 줄인 경량 스틱형 무선 청소기입니다. 틈새·침구용 브러시로 구석까지 청소할 수 있습니다.',
'• 다단 흡입력(모델별)\n• HEPA/전처리 필터(모델별)\n• 40~60분 사용(모델별)\n• 거치대 충전(모델별)\n• 원터치 먼지 비움(모델별)',
2, 12, 'system', NOW()),

('라이다 매핑 로봇청소기', 468000.00, 27, 'https://plus.unsplash.com/premium_photo-1676810460522-bc963e5554d8?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'정밀 지도를 그리며 구역 청소·금지구역 설정이 가능한 프리미엄 로봇청소기입니다. 앱으로 예약 청소를 걸 수 있습니다.',
'• SLAM/라이다(모델별)\n• 자동 충전 도크\n• 카펫 부스트(모델별)\n• 물걸레 모듈(모델별)\n• 음성 비서 연동(모델별)',
2, 12, 'system', NOW()),

('슬림 로봇청소기 (저소음)', 329000.00, 27, 'https://plus.unsplash.com/premium_photo-1661679071407-9eb342c3a32c?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'소파 아래·가구 틈에 들어가기 쉬운 슬림 바디 로봇청소기입니다. 야간 청소에도 부담이 적은 저소음 모드(모델별)를 지원합니다.',
'• 초슬림 높이(모델별)\n• 장애물 회피 센서\n• 자동 복귀 충전\n• 앱 스케줄\n• 헤어 엉킴 방지 브러시(모델별)',
2, 12, 'system', NOW()),

('흡입+물걸레 일체형 로봇', 512000.00, 12, 'https://images.unsplash.com/photo-1722710070534-e31f0290d8de?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'먼지 흡입 후 패드로 바닥을 닦아 주는 2in1 로봇청소기입니다. 아이·반려동물 있는 가정에 인기 있는 타입입니다.',
'• 회전 패드/진동 물걸레(모델별)\n• 물탱크 분리 세척\n• 매트 인식(모델별)\n• 자동 건조 도크(모델별)\n• 고출력 흡입(모델별)',
2, 12, 'system', NOW()),

('원룸용 미니 로봇청소기', 249000.00, 27, 'https://images.unsplash.com/photo-1590164409291-450e859ccb87?q=80&w=1674&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'작은 평수에 맞춘 컴팩트 로봇청소기입니다. 책상 아래·협탁 주변을 돌며 기본 먼지 청소를 대신해 줍니다.',
'• 소형 직경(모델별)\n• 간단 앱 연결\n• 충돌 완충 범퍼\n• 예약 청소\n• 가성비 라인',
2, 12, 'system', NOW()),

('대용량 먼지통 로봇청소기', 445000.00, 27, 'https://images.unsplash.com/photo-1558317374-067fb5f30001?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'도크에 먼지를 자동으로 비워 주는 대용량 먼지통이 포함된 로봇청소기입니다. 자주 비우지 않아도 됩니다.',
'• 자동 먼지 비움 도크(모델별)\n• 대용량 먼지팩\n• 강력 흡입 모드\n• 구역 청소\n• 필터 교체 알림(모델별)',
2, 12, 'system', NOW()),

('코너 클린 로봇청소기', 399000.00, 27, 'https://plus.unsplash.com/premium_photo-1729006559482-d289e4385b1e?q=80&w=2012&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'사각 코너까지 닿는 사이드 브러시 설계(모델별)로 벽면 청소에 강한 로봇청소기입니다.',
'• 사이드 브러시 2개(모델별)\n• 벽 따라가기 센서\n• 야간 무소음(모델별)\n• 가상벽 스티커(모델별)\n• 자동 충전',
2, 12, 'system', NOW()),

('거실용 대형 공기청정기 H13', 429000.00, 0, 'https://images.unsplash.com/photo-1652352529254-5106f4c8e03c?q=80&w=1064&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'넓은 거실·오픈형 주방에 맞는 대용량 공기청정기입니다. 초미세먼지·냄새 센서로 자동 풍량을 조절합니다.',
'• H13급 HEPA(모델별)\n• PM2.5/가스 센서(모델별)\n• 취침 모드 저소음\n• 필터 교체 알림\n• 20~30평 권장(모델별)',
2, 13, 'system', NOW()),

('침실용 미니 공기청정기', 189000.00, 0, 'https://images.unsplash.com/photo-1632928274371-878938e4d825?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'협탁 위에 두기 좋은 소형 공기청정기입니다. 수면 중에도 방해 없는 야간 모드(모델별)를 지원합니다.',
'• 컴팩트 사이즈\n• 3단 풍량\n• LED 끄기(모델별)\n• 교체형 프리필터\n• 원룸·아이방 추천',
2, 13, 'system', NOW()),

('UV 살균 공기청정기', 359000.00, 0, 'https://plus.unsplash.com/premium_photo-1750393713352-21224f938edd?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'필터+UV(모델별)로 공기 속 유해 요소를 줄이려는 가정용 공기청정기입니다. 알레르기·비염 관리에 관심 있는 분께 적합합니다.',
'• 복합 필터(모델별)\n• UV-C 모듈(모델별)\n• 자동 모드\n• 탈취 코팅(모델별)\n• 스마트폰 연동(모델별)',
2, 13, 'system', NOW()),

('타워형 공기청정기', 298000.00, 0, 'https://images.unsplash.com/photo-1768471569643-717e823b5f9a?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'세로형 타워 디자인으로 공간을 덜 차지하는 공기청정기입니다. 360° 흡입 구조(모델별)로 방 중앙 배치에 유리합니다.',
'• 슬림 타워\n• 360° 흡입(모델별)\n• 공기질 표시등\n• 타이머\n• 이동 손잡이(모델별)',
2, 13, 'system', NOW()),

('반려동물용 탈취 공기청정기', 349000.00, 0, 'https://images.unsplash.com/photo-1672925216623-f32a54d732e0?q=80&w=2048&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'털·비린내 케어에 초점을 둔 탈취 필터 구성(모델별)의 공기청정기입니다. 반려견·반려묘 가정에 인기 있습니다.',
'• 애완동물 모드(모델별)\n• 탈취 필터(모델별)\n• 프리필터 세척형(모델별)\n• 강풍 터보\n• 필터 세트 할인(모델별)',
2, 13, 'system', NOW()),

('스마트 IoT 공기청정기', 389000.00, 0, 'https://images.unsplash.com/photo-1768471569643-717e823b5f9a?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'외출 중에도 앱으로 켜고 끄고 공기질 그래프를 볼 수 있는 IoT 공기청정기입니다. 동일 썸네일 라인의 프리미엄 스펙 모델입니다.',
'• Wi-Fi 연동(모델별)\n• 음성 비서(모델별)\n• 주간 리포트\n• 예약 가동\n• HEPA H13(모델별)',
2, 13, 'system', NOW()),

('필터세트 증정 공기청정기', 269000.00, 0, 'https://images.unsplash.com/photo-1572274679992-2ac9e0a86853?q=80&w=1015&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'초기 1년 필터를 함께 드리는 패키지형 공기청정기입니다. 유지비 부담을 줄이고 싶은 분께 추천합니다.',
'• 복합필터 2+1(모델별)\n• 에코 모드\n• 10~15평 권장\n• 간편 교체\n• A급 소비전력(모델별)',
2, 13, 'system', NOW()),

('벽걸이 인버터 에어컨 18평', 1120000.00, 24, 'https://plus.unsplash.com/premium_photo-1679943423706-570c6462f9a4?q=80&w=1005&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'거실 겸용 18평형 벽걸이 에어컨입니다. 인버터로 설정 온도 도달 후에도 전기 요금 부담을 줄입니다.',
'• 냉방 18평급(모델별)\n• 인버터 컴프레서\n• 자동 세척(모델별)\n• 스마트 진단(모델별)\n• 설치 별도',
2, 14, 'system', NOW()),

('시스템 에어컨 1way 실내기', 890000.00, 12, 'https://images.unsplash.com/photo-1436473849883-bb3464c23e93?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'천장 매립형 시스템 에어컨 실내기(1way)입니다. 인테리어 일체형 룩을 원하는 신축·리모델링에 적합합니다.',
'• 1way 패널(모델별)\n• 풍향 제어(모델별)\n• 외기와 세트 구매\n• 전문 시공\n• 리모컨 포함(모델별)',
2, 14, 'system', NOW()),

('창문형 에어컨 (설치 간편)', 498000.00, 0, 'https://images.unsplash.com/photo-1726614846573-c1ac2e6161d1?q=80&w=927&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'베란다·창틀에 거는 타입의 창문형 에어컨입니다. 원룸·고시원처럼 시공이 어려운 공간에 선택됩니다.',
'• 셀프 설치 키트(모델별)\n• 6~8평(모델별)\n• 배수 호스 포함\n• 리모컨\n• 겨울 보관 커버(모델별)',
2, 14, 'system', NOW()),

('스탠드 에어컨 2in1 냉난방', 1350000.00, 20, 'https://images.unsplash.com/photo-1681042803902-f79c240d8f03?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'이동이 가능한 스탠드형 냉난방 겸용 에어컨입니다. 별도 실외기 없이 플러그만 꽂아 쓰는 타입(모델별)입니다.',
'• 이동 바퀴\n• 냉방·난방(모델별)\n• 제습 모드(모델별)\n• 배기 덕트(모델별)\n• 10~12평(모델별)',
2, 14, 'system', NOW()),

('프리미엄 무풍 벽걸이 에어컨 24평', 1580000.00, 22, 'https://images.unsplash.com/photo-1759772238012-9d5ad59ae637?q=80&w=2023&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'바람이 직접 닿지 않는 무풍(모델별) 콘셉트의 대형 벽걸이 에어컨입니다. 영유아·어르신 거실에 많이 찾습니다.',
'• 무풍/미세풍(모델별)\n• 24평급(모델별)\n• AI 절전(모델별)\n• 셀프 클린\n• 금속 마감 패널(모델별)',
2, 14, 'system', NOW()),

('업소용 천장형 에어컨', 2100000.00, 20, 'https://images.unsplash.com/photo-1762341123870-d706f257a12e?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'카페·소형 매장용 카세트/천장형 에어컨입니다. 넓은 공간을 고르게 냉방합니다.',
'• 4way 풍향(모델별)\n• 상업용 컴프레서\n• 전문 시공 필수\n• 유지보수 패키지(모델별)\n• 냉방량 kW 표기(모델별)',
2, 14, 'system', NOW()),

('침실용 저소음 에어컨 13평', 920000.00, 12, 'https://images.unsplash.com/photo-1761330440311-16e160cad236?q=80&w=1020&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'수면 중 소음을 줄인 침실 전용 소형 벽걸이 에어컨입니다. 야간에는 바람 세기를 자동으로 낮춥니다.',
'• 13평급(모델별)\n• 취침 모드\n• 24시간 타이머\n• 곰팡이 방지 건조(모델별)\n• 에너지 1등급(모델별)',
2, 14, 'system', NOW()),

('대용량 오븐형 에어프라이어 7L', 182000.00, 28, 'https://images.unsplash.com/photo-1674504866626-fe4f19f68564?q=80&w=927&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'통닭·피자까지 넣을 수 있는 오븐형 에어프라이어입니다. 기름을 줄이면서도 바삭한 식감을 살려 줍니다.',
'• 7L급 바스켓(모델별)\n• 예열·보온\n• 8~12 요리 프리셋(모델별)\n• 논스틱 코팅 탈착\n• 안전 잠금(모델별)',
2, 15, 'system', NOW()),

('인버터 전자레인지 23L', 158000.00, 0, 'https://images.unsplash.com/photo-1574269909862-7e1d70bb8078?w=500&h=500&fit=crop',
'균일한 출력으로 음식이 덜 익는 구석을 줄인 인버터 전자레인지입니다. 해동·덴탈 모드로 활용도가 높습니다.',
'• 인버터 출력(모델별)\n• 23L 내부\n• 센서 해동(모델별)\n• 이지 클린 코팅\n• 타이머·잠금',
2, 15, 'system', NOW()),

('스팀 오븐 전자레인지', 289000.00, 0, 'https://plus.unsplash.com/premium_photo-1718043036199-d98bef36af46?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'전자레인지+스팀(모델별)으로 빵·밥을 촉촉하게 데울 수 있는 복합 오븐형입니다. 베이킹 입문자에게도 무난합니다.',
'• 스팀 급수 탱크(모델별)\n• 복합 자동요리\n• 오븐 상·하관(모델별)\n• 자동 세척(모델별)\n• 25~30L(모델별)',
2, 15, 'system', NOW()),

('글라스 도어 디자인 전자레인지', 175000.00, 0, 'https://images.unsplash.com/photo-1672925216623-f32a54d732e0?q=80&w=2048&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'주방 가구와 잘 어울리는 글라스 프런트 전자레인지입니다. 기본 조리·해동에 충실한 실속 모델입니다.',
'• 글라스 패널\n• 20L급(모델별)\n• 회전판\n• 어린이 잠금(모델별)\n• 에코 대기전력(모델별)',
2, 15, 'system', NOW()),

('전기밥솥 6인용 IH', 219000.00, 0, 'https://images.unsplash.com/photo-1578643463396-0997cb5328c1?q=80&w=1036&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'IH로 고르게 가열해 밥맛을 살려 주는 6인용 전기밥솥입니다. 잡곡·쾌속 모드로 바쁜 아침에도 편합니다.',
'• IH 가열(모델별)\n• 6인용 내솥\n• 분리형 커버(모델별)\n• 예약 타이머\n• 밥물 눈금 내솥',
2, 15, 'system', NOW()),

('템퍼 조절 전기포트 1.7L', 89000.00, 0, 'https://images.unsplash.com/photo-1452415005154-c06158558480?q=80&w=986&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'커피·차 종류별로 물 온도를 맞출 수 있는 온도 조절 전기포트입니다. 이중 단열로 외피 온도를 낮췄습니다.',
'• 5~7단 온도(모델별)\n• 보온 1~2시간(모델별)\n• 스테인리스 내통\n• 자동 전원 차단\n• 360° 받침대',
2, 15, 'system', NOW()),

('반죽기 겸용 스탠드 믹서', 329000.00, 0, 'https://plus.unsplash.com/premium_photo-1664391802903-aa09789f9a3e?q=80&w=1077&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'빵 반죽·크림·계란 흰자까지 한 기기로 처리하는 주방 스탠드 믹서입니다. 홈베이킹 동호회에서 자주 쓰는 타입입니다.',
'• 다단 속도\n• 반죽 훅·거품기·비터(모델별)\n• 틸트 헤드(모델별)\n• 4.5~5L 볼(모델별)\n• 과부하 방지(모델별)',
2, 15, 'system', NOW()),

('레트로 필름 카메라 (수동 포커스)', 42000.00, 0, 'https://plus.unsplash.com/premium_photo-1663134149019-284682ece04c?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'블랙·실버 바디의 빈티지 감성 필름 카메라입니다. 여행·스냅 촬영에 어울리는 클래식 실루엣을 살렸습니다.',
'• 35mm 필름 사용(모델별)\n• 수동 초점·조리개(모델별)\n• 내장 플래시(모델별)\n• 가죽 스트랩 별매(모델별)\n• 필름 별매',
2, 16, 'system', NOW()),

('스마트폰 디스플레이 (앱 아이콘 UI)', 79000.00, 0, 'https://images.unsplash.com/photo-1512941937669-90a1b58e7e9c?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'선명한 OLED급 패널에 메시지·지도·SNS 등 앱 아이콘이 배치된 스마트폰 화면입니다. 일상·업무용으로 무난한 미드레인지 라인입니다.',
'• 고해상도 디스플레이(모델별)\n• 터치 반응속도 개선(모델별)\n• 얼굴·지문 인식(모델별)\n• 5G 지원(모델별)\n• 용량·색상 옵션(모델별)',
2, 16, 'system', NOW()),

('USB-C to USB-C 고속 충전 케이블 1.8m', 149000.00, 0, 'https://plus.unsplash.com/premium_photo-1669261149433-febd56c05327?q=80&w=1015&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'노트북·태블릿·스마트폰 PD 충전에 쓰는 C to C 케이블입니다. 내구성 있는 커넥터와 굵은 선재로 발열을 줄였습니다.',
'• USB PD 100W급(모델별·어댑터 병행)\n• 데이터 전송 USB 3.2(모델별)\n• 1.8m 길이(모델별)\n• 휘어짐 내성(모델별)\n• E-Marker 칩(모델별)',
2, 16, 'system', NOW()),

('GaN 67W 멀티 포트 벽면 충전기 세트', 118000.00, 0, 'https://images.unsplash.com/photo-1731616103600-3fe7ccdc5a59?q=80&w=1674&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'노란 배경 위에 놓인 67W급 초소형 어댑터와 매칭 케이블 세트입니다. 노트북과 휴대폰을 한 콘센트에서 동시에 충전하기 좋습니다.',
'• 최대 67W 출력(모델별)\n• GaN 소자(모델별)\n• USB-C 포트 2개 이상(모델별)\n• 케이블 동봉(모델별)\n• 과전류·과열 보호',
2, 16, 'system', NOW()),

('욕실 아로마 디퓨저 & 우드 인테리어 세트', 189000.00, 0, 'https://plus.unsplash.com/premium_photo-1679520112650-9c2141d81a2d?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'리드 스틱 디퓨저와 우드 소품이 어우러진 욕실·화장대 무드 세트입니다. 은은한 향으로 공간 분위기를 바꿔 줍니다.',
'• 천연 에센셜 베이스(모델별)\n• 리필액 별매(모델별)\n• 우드 트레이/스틱 포함(모델별)\n• 향 종류 3종 중 택1(모델별)\n• 직사광선·고온 피하기',
2, 16, 'system', NOW()),

('글로브 펜던트 인테리어 조명 (전구 세트)', 99000.00, 0, 'https://images.unsplash.com/photo-1515948725-edac7b5bb0fc?q=80&w=988&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'천장에 매달린 둥근 글로브 전구가 따뜻한 빛을 퍼뜨리는 펜던트 조명입니다. 카페·거실·침실 포인트 조명으로 인기 있는 타입입니다.',
'• E26/E27 소켓(모델별)\n• LED 필라멘트 전구 동봉(모델별)\n• 코드 길이 조절(모델별)\n• 딤머 호환(모델별)\n• 전기 안전 인증(모델별)',
2, 16, 'system', NOW()),

('노이즈 캔슬링 블루투스 헤드폰 (오버이어)', 135000.00, 0, 'https://plus.unsplash.com/premium_photo-1677838847721-2bf14b48c256?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'그레이 마감의 오버이어형 무선 헤드폰입니다. 출퇴근·재택 회의·영화 감상 시 몰입감 있는 사운드를 제공합니다.',
'• ANC 노이즈 캔슬링(모델별)\n• 멀티포인트(모델별)\n• 통화 빔포밍 마이크(모델별)\n• 30시간 재생(모델별)\n• 접이식·파우치(모델별)',
2, 16, 'system', NOW());

-- 푸드 (category_id = 3)
INSERT IGNORE INTO products (name, price, discount, image, description, details, category_id, sub_category_id, created_by, created_date) VALUES
('유기농 농장 박스 (당근·비트·케일·계란 등)', 25000.00, 0, 'https://plus.unsplash.com/premium_photo-1675798983878-604c09f6d154?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'잎 당근·애호박·레드비트·곱슬 케일·완숙 토마토·청사과·통마늘·농장 계란이 한 번에 담긴 유기농 구성입니다. 한 끼 채소·과일을 넉넉히 채우기 좋습니다.',
'• 유기농·무농약 재배(모델별)\n• 잎채소·뿌리채소·과일·난류 혼합 구성\n• 냉장 배송\n• 수확 직후 포장(모델별)\n• 레시피 카드 동봉(모델별)',
3, 17, 'system', NOW()),

('산지직송 햇채소 믹스 (당근·파프리카·감자 등)', 32900.00, 18, 'https://images.unsplash.com/photo-1579113800032-c38bd7635818?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'당근·노랑·빨강 파프리카·햇감자·완숙 토마토·쥬키니·적환무(래디시)·사과 등이 한 화면 가득 담긴 신선 채소·과일 혼합 상자입니다.',
'• 산지 직송으로 싱싱한 상태 배송\n• 비타민 가득 컬러 채소 구성\n• 조림·샐러드·스튜 재료로 활용\n• 냉장 보관 후 빠른 섭취 권장\n• 구성 품목은 시즌별 상이(모델별)',
3, 17, 'system', NOW()),

('프리미엄 세척 적사과 1kg (3~4과)', 8900.00, 0, 'https://images.unsplash.com/photo-1630563451961-ac2ff27616ab?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'진한 붉은빛 껍질에 물방울이 맺힌 신선한 적사과입니다. 썸네일은 대표 과 1알 클로즈업이며, 실제 배송은 1kg 내외(3~4과) 팩입니다.',
'• 당도·아삭함 선별(모델별)\n• 세척 후 섭취 가능(모델별)\n• 비타민 C·식이섬유\n• 냉장 보관 권장\n• 간식·샐러드·디저트용',
3, 17, 'system', NOW()),

('고당도 통 파인애플 1통', 35000.00, 0, 'https://images.unsplash.com/photo-1589820296156-2454bb8a6ad1?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'노란 배경 위로 돋보이는 통 파인애플입니다. 껍질 눈이 선명하고 윗잎이 푸르러 숙성도가 좋은 편입니다.',
'• 과즙·당도 높은 품종(모델별)\n• 비타민 C·브로멜라인\n• 껍질 제거 후 냉장(모델별)\n• 주스·볶음·디저트 활용\n• 원산지 필리핀/코스타리카 등(모델별)',
3, 17, 'system', NOW()),

('산지직송 국산 딸기 (한가득)', 8900.00, 0, 'https://images.unsplash.com/photo-1587393855524-087f83d95bc9?q=80&w=1060&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'알이 굵고 붉게 익은 딸기가 가득 담긴 구성입니다. 꼭지가 초록으로 살아 있어 갓 수확한 듯한 신선함을 느낄 수 있습니다.',
'• 중대과 혼합(모델별)\n• 새콤달콤한 밸런스\n• 냉장 보관·세척 후 바로 섭취\n• 요거트·케이크 토핑\n• 산지별 상이(모델별)',
3, 17, 'system', NOW()),

('탱글 생 블루베리 (볼·주변 산)', 12900.00, 0, 'https://images.unsplash.com/photo-1498557850523-fd3d118b962e?q=80&w=2069&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'흰 그릇에 담고 주변에도 흩어 놓은 듯한 생 블루베리입니다. 과피에 블룸(백분)이 남아 있어 신선도가 좋아 보입니다.',
'• 125~200g 팩(모델별)\n• 안토시아닌·항산화 성분\n• 세척 후 요거트·시리얼과\n• 냉장 필수\n• 국내·수입 혼재(모델별)',
3, 17, 'system', NOW()),

('제철 고당도 감귤 (껍질 얇은 노지·하우스)', 7500.00, 0, 'https://images.unsplash.com/photo-1605723937099-84ce35162741?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'노란 배경 위에 온전한 알갱이와 까서 드러난 과육이 함께 담긴 감귤입니다. 껍질이 얇고 과즙이 풍부한 제철 귤맛을 기대할 수 있습니다.',
'• 제주·노지 등 원산지 표기(모델별)\n• 비타민 C 풍부\n• 실온·냉장 모두 가능(모델별)\n• 간식·주스·청\n• 500g~1kg 박스(모델별)',
3, 17, 'system', NOW()),

('IQF 냉동 통딸기 (얼음 결)', 8900.00, 0, 'https://plus.unsplash.com/premium_photo-1675855790814-61c0c0cecf65?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'수확 직후 급속 냉동한 통딸기입니다. 과육에 하얀 서리·얼음 결정이 맺힌 IQF 컷으로, 스무디·에이드·디저트 토핑에 그대로 넣기 좋습니다.',
'• 딸기 100%(모델별)\n• IQF 개별 급냉(모델별)\n• -18℃ 이하 보관\n• 해동 후 즉시 조리 권장\n• 당도·산미 밸런스(모델별)',
3, 18, 'system', NOW()),

('냉동 레드커런트 500g', 19900.00, 0, 'https://images.unsplash.com/photo-1533760077673-895abb64471e?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'작고 동글동글한 붉은 열매에 서리가 낀 냉동 레드커런트입니다. 새콤달콤한 베이킹 장식·잼·요거트 토핑에 어울립니다.',
'• 수입산(모델별)\n• 급속 냉동\n• 비타민 C·안토시아닌\n• -18℃ 보관\n• 해동 후 타르트·소스 활용',
3, 18, 'system', NOW()),

('수제 얇은피 고기만두 (냉동)', 18900.00, 0, 'https://images.unsplash.com/photo-1590385014317-6a78bc23b090?q=80&w=2232&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'밀가루를 뿌린 판 위에 올려 둔 반달형 손만두입니다. 얇고 반투명한 피 속 진한 소가 비치는 전통 스타일입니다. 찜·군만두·떡국에 넣어 드세요.',
'• 12~16알(모델별)\n• 급랭 냉동\n• 돼지고기·야채 소(모델별)\n• 전자레인지·에어프라이어 가능(모델별)\n• -18℃',
3, 18, 'system', NOW()),

('급랭 원물 고등어 2미 (얼음 진열)', 16900.00, 0, 'https://plus.unsplash.com/premium_photo-1674498270829-eb9d5fec7a5e?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'은빛 줄무늬가 선명한 통고등어 두 마리가 부순 얼음 위에 올려진 구성입니다. 눈이 맑고 껍질이 반짝이는 신선 원물을 급랭해 배송합니다.',
'• 2미(모델별)\n• 1미당 300~400g급(모델별)\n• 소금구이·조림·구이에 적합\n• 해동 후 당일 조리 권장\n• 원산지 국산/수입 표기(모델별)',
3, 18, 'system', NOW()),

('손질 냉동 칵테일 새우 (자숙·껍질제거)', 21900.00, 0, 'https://plus.unsplash.com/premium_photo-1707927340705-ad673cf26177?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'껍질을 벗기고 등쪽을 손질한 새우가 얼음 더미 위에 올려진 냉동 제품입니다. 연분홍빛 살코기가 돋보이며 샐러드·파스타·튀김에 바로 쓰기 좋습니다.',
'• 400~500g(모델별)\n• 자숙 또는 생살(모델별)\n• 급속 냉동\n• -18℃ 보관\n• 해동 후 1회 조리 권장',
3, 18, 'system', NOW()),

('프리미엄 냉동 티본 스테이크 (얼음 위 생고기)', 42900.00, 0, 'https://images.unsplash.com/photo-1551028150-64b9f398f678?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'T자 뼈가 보이는 두툼한 티본이 부순 얼음 위에 올려져 있고, 후추·고추·허브와 셰프 나이프가 함께 연출된 프리미엄 냉동 스테이크 컷입니다. 해동 후 팬 시어링을 권장합니다.',
'• 약 450~550g(모델별)\n• 안심+채끝 한 판\n• 숙성육(모델별)\n• -18℃ 보관\n• 해동 후 실온 30분·소금·후추만으로도 충분',
3, 18, 'system', NOW()),

('IQF 냉동 브로콜리 플로렛 (서리 입힌)', 6900.00, 0, 'https://images.unsplash.com/photo-1622484964723-d1456419fcb0?q=80&w=2073&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'송이 부분에 하얀 얼음 결정이 붙어 있는 냉동 브로콜리 클로즈업입니다. 급속 냉동으로 색과 영양을 잡았으며, 살짝 데치거나 에어프라이어에 구워 드시면 됩니다.',
'• 500g~1kg(모델별)\n• IQF(모델별)\n• 블랜칭 후 급냉(모델별)\n• -18℃ 보관\n• 스프·볶음·샐러드',
3, 18, 'system', NOW()),

('매콤 로제 소시지 면 도시락 (전자레인지용)', 14900.00, 0, 'https://images.unsplash.com/photo-1767469576701-b05165afd418?q=80&w=2075&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'검은 일회용 트레이에 담긴 면 요리입니다. 주황빛 크림·로제 소스에 비엔나 소시지 슬라이스·청피망이 들어가 있고 위에 고운 붉은 가루가 뿌려져 있습니다.',
'• 1인분(모델별)\n• 전자레인지 3~4분(모델별)\n• 냉장 또는 냉동(모델별)\n• 매운맛 단계 표기(모델별)\n• 포크 동봉(모델별)',
3, 19, 'system', NOW()),

('클래식 비프 치즈버거 (즉석 조리)', 9900.00, 0, 'https://plus.unsplash.com/premium_photo-1723618906158-d6682e969a90?q=80&w=2066&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'참깨 번 사이에 두툼한 그릴드 패티와 녹은 체다 치즈가 올라간 버거를 한 입 베어 문 컷입니다. 패티육 100% 표기(모델별) 간편식 라인입니다.',
'• 냉동 패티+번 분리 포장(모델별)\n• 에어프라이어·팬 조리(모델별)\n• 양상추·토마토·소스 동봉(모델별)\n• 1개입(모델별)\n• -18℃ 또는 냉장(모델별)',
3, 19, 'system', NOW()),

('프리미엄 멀티그레인 델리 샌드위치', 7800.00, 0, 'https://images.unsplash.com/photo-1553909489-cd47e0907980?q=80&w=1625&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'씨앗이 박힌 통곡물 식빵으로 포장한 높은 샌드위치입니다. 로스트비프(또는 파스트라미)·슬라이스 햄·체다·토마토·양상추가 층층이 쌓여 있습니다.',
'• 1개(모델별)\n• 냉장 당일·익일 섭취 권장(모델별)\n• 픽클·머스타드 소스(모델별)\n• 카페·편의점 동일 컨셉\n• 칼로리·알레르기 표기',
3, 19, 'system', NOW()),

('옥수수 치즈 그릴 샌드위치 (소스 3종)', 7900.00, 0, 'https://images.unsplash.com/photo-1528735602780-2552fd46c7af?q=80&w=2073&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'대각선 그릴 마크가 선명한 삼각 컷 샌드입니다. 옥수수 알과 녹은 치즈(또는 크림소스) 필링이 보이며, 케첩·마요·허브류 딥 소스가 곁들여져 있습니다.',
'• 2조각(1세트)(모델별)\n• 전자레인지·토스터(모델별)\n• 냉장 보관\n• 매운맛 옵션(모델별)\n• 접시·포장 이미지는 연출(모델별)',
3, 19, 'system', NOW()),

('프리미엄 데일리 견과 혼합 세트', 12900.00, 0, 'https://images.unsplash.com/photo-1616252576862-bd9abd7467f9?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'우드 보드 위에 아몬드·호두·브라질너트·헤이즐넛·피칸·피스타치오·잣·호박씨 등이 나뉘어 담긴 견과 플래터입니다. 작은 글라스볼에도 견과가 담겨 있습니다.',
'• 무염·저염 로스팅(모델별)\n• 200~300g(모델별)\n• 지퍼백·캔(모델별)\n• 하루 한 줌 간식용\n• 알레르기: 견과류 함유',
3, 19, 'system', NOW()),

('우유 식빵 쇼쿠판 (두툼 슬라이스)', 6900.00, 0, 'https://images.unsplash.com/photo-1598373182133-52452f7691ef?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'어두운 우드 보드 위에 두껍게 썬 하얀 속심의 식빵 한 덩이입니다. 겉은 연한 갈색, 속은 촉촉한 밀크 브레드 스타일로 토스트·샌드 베이스로 쓰기 좋습니다.',
'• 400g 전후(모델별)\n• 6~8매 슬라이스(모델별)\n• 실온 2~3일(모델별)\n• 냉동 보관 가능(모델별)\n• 우유·밀 함유',
3, 19, 'system', NOW()),

('그릴드 소시지 가든 샐러드 (드레싱)', 8900.00, 0, 'https://images.unsplash.com/photo-1607532941433-304659e8198a?q=80&w=1678&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'흰 볼에 버터헤드류 상추·방울토마토·큼직하게 썬 그릴드 소시지·견과·허브가 올라가고 크리미 드레싱이 뿌려진 샐러드입니다.',
'• 250~350g(모델별)\n• 드레싱 분리(모델별)\n• 냉장 유통\n• 포크·뚜껑 일체형(모델별)\n• 한 끼 대용 포만감(모델별)',
3, 19, 'system', NOW()),

('마카롱 & 파운드 디저트 트레이', 9900.00, 0, 'https://images.unsplash.com/photo-1541985446-a345f1b9d7de?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'분홍 테이블 위 흰 트레이에 담긴 컬러 마카롱과 골든 파운드케이크 슬라이스입니다. 달콤한 디저트 플래터로 보입니다.',
'• 트레이 3구성(모델별)\n• 마카롱 다채로운 맛(모델별)\n• 파운드 슬라이스 동봉(모델별)\n• 냉장 당일·익일 섭취 권장(모델별)\n• 우유·계란·견과 알레르기 표기(모델별)',
3, 20, 'system', NOW()),

('수입 초콜릿·웨이퍼 종합 박스', 21900.00, 0, 'https://images.unsplash.com/photo-1621939514649-280e2ee25f60?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'골판지 상자에 캐러멜·누가·웨이퍼 타입 등 해외 인기 초콜릿·시리얼 바가 개별 포장으로 빼곡히 담긴 대용량 구성입니다.',
'• 대용량 어쏘티드(모델별)\n• 브랜드·종 구성 변동(모델별)\n• 개별 포장\n• 실온 보관\n• 선물·파티용(모델별)',
3, 20, 'system', NOW()),

('치즈 시즈닝 스낵 믹스', 6800.00, 0, 'https://images.unsplash.com/photo-1699666397768-0126340e880a?q=80&w=988&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'토르티야 칩·치즈볼·콘 스낵·감자칩 등 오렌지빛 치즈 가루가 묻은 바삭한 믹스가 한 화면을 가득 채운 샷입니다.',
'• 200~350g(모델별)\n• 파티 믹스 타입(모델별)\n• 짭짤·치즈 풍미(모델별)\n• 밀봉 지퍼(모델별)\n• 실온 보관',
3, 20, 'system', NOW()),

('솔티드 오리지널 감자칩', 7200.00, 0, 'https://images.unsplash.com/photo-1599490659213-e2b9527bd087?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'노란 배경 위에 얇고 곡선진 감자칩이 흩어져 있고, 겉면에 구운 자국·소금 간이 보이는 미니멀한 제품 컷입니다.',
'• 80~120g(모델별)\n• 얇은 슬라이스(모델별)\n• 소금 또는 라이트 시즈닝(모델별)\n• 밀봉 파우치(모델별)\n• 실온 보관',
3, 20, 'system', NOW()),

('초코칩 쿠키 크림 샌드', 14800.00, 0, 'https://images.unsplash.com/photo-1559622214-f8a9850965bb?q=80&w=1065&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'초코칩이 박힌 도톰한 쿠키 두 장 사이에 하얀 크림이 두껍게 끼워진 샌드가 쌓여 있고, 옆에는 꿀디퍼가 비치는 디저트 사진입니다.',
'• 2~4개 샌드(모델별)\n• 바닐라·버터크림 필(모델별)\n• 냉장 또는 냉동 보관(모델별)\n• 밀·계란·우유 함유\n• 카페·디저트 라인(모델별)',
3, 20, 'system', NOW()),

('솔트 화이트 팝콘', 6200.00, 0, 'https://images.unsplash.com/photo-1579642761360-eabd1cca1e81?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'핫핑크 배경에 흰 그릇에서 흘러나온 솔트 팝콘이 흩어져 있고, 알갱이가 푹신하게 튀겨진 모습이 강조된 스낵 컷입니다.',
'• 100~200g(모델별)\n• 가벼운 소금 간(모델별)\n• 에어팝 또는 저유(모델별)\n• 영화·홈파티용(모델별)\n• 실온 밀봉(모델별)',
3, 20, 'system', NOW()),

('에너지바 & 견과 스낵 세트', 12900.00, 0, 'https://plus.unsplash.com/premium_photo-1726676075271-d08aef815d79?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'베이지 배경에 오렌지·머스타드색 포장 에너지바와 초콜릿 코팅 바 조각, 캐슈·땅콩 등 견과가 함께 놓인 플랫레이입니다.',
'• 바 4~6개+견과(모델별)\n• 개별 포장 바(모델별)\n• 단백·견과 함량 라인(모델별)\n• 실온 보관\n• 운동·출근 간식(모델별)',
3, 20, 'system', NOW()),

('어반 컬러 쿠페 에이드 듀오', 28900.00, 0, 'https://plus.unsplash.com/premium_photo-1673108852149-85f46a4dee4b?q=80&w=1064&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'도심 빌딩 배경 앞에서 두 손이 라벤더빛과 오렌지빛 음료가 담긴 쿠페 글라스를 맞대는 장면입니다. 거품 층과 슈가 림이 보입니다.',
'• 2잔 세트(모델별)\n• 논알콜 에이드·모히또 스타일(모델별)\n• 매장·배달 음료 컨셉(모델별)\n• 냉장 즉시 음용(모델별)\n• 알코올 유무는 옵션(모델별)',
3, 21, 'system', NOW()),

('앰버 허브·라임 발효 음료 플라이트', 19800.00, 0, 'https://images.unsplash.com/photo-1609951651467-713256d1a3be?q=80&w=3164&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'밝은 석재 위에 와인글·쿠페·텀블러 등 네 잔에 꿀빛 앰버 액체가 담겨 있고, 라임·로즈마리·건과일이 곁들여진 프리미엄 음료 사진입니다.',
'• 콤부차·에이드·하드 시더 느낌(모델별)\n• 허브·시트러스 가니쉬(모델별)\n• 카페·브런치 메뉴 컷(모델별)\n• 저온 냉장 권장(모델별)\n• 4잔 분량 기준(모델별)',
3, 21, 'system', NOW()),

('초코 샌드 쿠키 프라페', 8900.00, 0, 'https://images.unsplash.com/photo-1572490122747-3968b75cc699?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'플라스틱 컵에 연한 브라운 블렌드 음료, 컵 안쪽 초코 시럽 마블링, 풍성한 초코 휘핑, 옆에 초코 샌드위치 쿠키가 올라간 카페 프라페입니다.',
'• 테이크아웃 컵(모델별)\n• 우유·초콜릿 함유(모델별)\n• 당 함량 표기(모델별)\n• 매장 제조·즉시 섭취 권장(모델별)\n• 얼음 옵션(모델별)',
3, 21, 'system', NOW()),

('프레시 오렌지 착즙 주스', 6500.00, 0, 'https://images.unsplash.com/photo-1600271886742-f049cd451bba?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'높은 하이볼 글라스에 진한 오렌지색 과즙이 담기고 림에는 오렌지 휠, 안에는 흰 빨대가 꽂인 바·카페 스타일 착즙 컷입니다.',
'• 300~450ml(모델별)\n• 펄프감(모델별)\n• 냉장 제조(모델별)\n• 비타민 C\n• 당일·익일 음용 권장(모델별)',
3, 21, 'system', NOW()),

('루비 시트러스 민트 아이스티', 15900.00, 0, 'https://images.unsplash.com/photo-1563227812-0ea4c22e6cc8?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'흰 테이블 위 롱글라스 두 잔에 짙은 루비색 얼음 음료, 민트·라임 가니쉬, 자몽·레몬·라임 슬라이스가 흩어진 여름용 아이스티 장면입니다.',
'• 히비스커스·베리 베이스(모델별)\n• 대용량 얼음(모델별)\n• 무알콜(모델별)\n• 2인 시음 세트 이미지(모델별)\n• 냉장 유통(모델별)',
3, 21, 'system', NOW()),

('햇살 아이스 미네랄 워터', 12800.00, 0, 'https://images.unsplash.com/photo-1612984420824-146f46c75b86?q=80&w=3087&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'원목 테이블에 투명한 실린더 글라스에 물과 큼직한 얼음이 담겨 있고, 창가 햇살 줄무늬 그림자가 드리운 미니멀 생수·정수 컨셉 사진입니다.',
'• 500ml×12(모델별)\n• 미네랄·암반수 라인(모델별)\n• 무탄산\n• 실온·냉장 모두 가능(모델별)\n• 이미지는 글라스 서빙 예시(모델별)',
3, 21, 'system', NOW()),

('스파이시 파인 트로피컬 에이드', 9900.00, 0, 'https://images.unsplash.com/photo-1468465236047-6aac20937e92?q=80&w=983&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'이슬 맺힌 글라스에 핑크빛 차가운 음료와 얼음, 림에는 파인애플 웨지, 주변에 고추·허브가 놓인 달콤·은은한 매운맛 트로피컬 에이드 컷입니다.',
'• 350~500ml(모델별)\n• 파인·베리·히비스커스 느낌(모델별)\n• 매운맛 온도 조절(모델별)\n• 무알콜(모델별)\n• 냉장 필수(모델별)',
3, 21, 'system', NOW()),

('크레마 에스프레소 블렌드 원두 500g', 22000.00, 0, 'https://plus.unsplash.com/premium_photo-1675435644687-562e8042b9db?q=80&w=1049&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'볶은 원두를 바닥에 깔고 그 위에 놓인 화이트 디미타스에 진한 에스프레소와 황금빛 크레마가 담긴 탑뷰 컷입니다.',
'• 500g 홀빈(모델별)\n• 미디엄다크 로스트(모델별)\n• 에스프레소·모카포트 추천(모델별)\n• 볶은 날짜 표기(모델별)\n• 밀폐 지퍼백',
3, 22, 'system', NOW()),

('라떼 아트 홈카페 블렌드 원두', 14900.00, 0, 'https://images.unsplash.com/photo-1509042239860-f550ce710b93?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'원목 테이블에 차콜 컬러 컵 세 잔이 사선으로 놓여 있고 하트·로제타 라떼 아트가 선명하며 화분이 곁들여진 홈카페 분위기 사진입니다.',
'• 200~250g(모델별)\n• 에스프레소·라떼용 블렌드(모델별)\n• 우유와 밸런스 맞춤 로스팅(모델별)\n• 핸드밀·머신 겸용(모델별)\n• 밀폐 보관',
3, 22, 'system', NOW()),

('모닝 스팀 블랙 원두 & 드립 세트', 19900.00, 0, 'https://images.unsplash.com/photo-1518057111178-44a106bad636?q=80&w=988&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'야외 발코니에서 흰 머그를 든 손과 짙은 커피(또는 진한 차) 위로 피어오르는 하얀 김이 강조된 아침 음료 컷입니다.',
'• 싱글오리진 또는 블렌드(모델별)\n• 드립백·원두 구성(모델별)\n• 다크 로스트 톤(모델별)\n• 핫 추출 권장(모델별)\n• 카페인 함유',
3, 22, 'system', NOW()),

('북앤블랭킷 골든 티타임 세트', 16800.00, 0, 'https://images.unsplash.com/photo-1621850204256-a84f9acbc5e9?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'담요를 덮고 책을 읽는 사람이 화이트 머그에 담긴 옅은 앰버색 차(홍차·허브티 느낌)를 들고 있는 포근한 실내 티타임 장면입니다.',
'• 티백 24~40개(모델별)\n• 캐모마일·페퍼민트·얼그레이 등 믹스(모델별)\n• 무카페인 옵션 포함(모델별)\n• 실온 보관\n• 선물 포장(모델별)',
3, 22, 'system', NOW()),

('듀얼 글래스 어쏘티드 티백 세트', 15900.00, 0, 'https://images.unsplash.com/photo-1461595520627-42e3c83019bc?q=80&w=2232&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'두 사람이 손잡이 달린 투명 글래스 머그로 홍차빛 진한 차와 연한 황록색 차를 마시며 티백 끈이 보이는 2인 티타임 사진입니다.',
'• 홍차·녹차·허브 어쏘티드(모델별)\n• 티백 20~30개(모델별)\n• 내열 글라스 머그 별매 가능(모델별)\n• 실온 유통\n• 카페인 표기(모델별)',
3, 22, 'system', NOW()),

('큐브 아이스 카페라떼 (2잔)', 9900.00, 0, 'https://plus.unsplash.com/premium_photo-1663933533712-eef7095f782b?q=80&w=1065&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'흰 테이블 위 실린더 글라스에 큼직한 얼음과 밀크티·아이스 라떼 톤의 크리미한 음료가 담기고 옆잔이 비치는 미니멀 아이스 음료 컷입니다.',
'• 매장 음료 또는 RTD(모델별)\n• 우유·커피 함유(모델별)\n• 얼음 많이(모델별)\n• 냉장 즉시 음용(모델별)\n• 당도 옵션(모델별)',
3, 22, 'system', NOW()),

('마블 아이스 라떼 & 스콘 세트', 18900.00, 0, 'https://plus.unsplash.com/premium_photo-1677607237294-b041e4b57391?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'테라코타 배경 앞 톨 글라스에 우유와 에스프레소가 마블링되고, 앞에 원두가 흩어지며 작은 우유잔·돔 아래 스콘(페이스트리)이 놓인 세트 구도입니다.',
'• 아이스 라떼 1잔+스콘(모델별)\n• 추가 우유잔(모델별)\n• 매장 픽업·배달 메뉴 컨셉(모델별)\n• 카페인 함유\n• 당일 섭취 권장(모델별)',
3, 22, 'system', NOW()),

('레인보우 슈퍼푸드 브런치 보울 키트', 18900.00, 0, 'https://images.unsplash.com/photo-1490645935967-10de6ba17061?q=80&w=2053&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'파란 그릇에 반숙란·아보카도·케일·방울토마토·수박무·오이면 등이 담기고, 원목 테이블에 벚꽃 가지·허브티·소금 그릇이 어우러진 영양 브런치 플레이팅입니다.',
'• 1인분(모델별)\n• 단백질·불포화지방·식이섬유 밸런스(모델별)\n• 냉장 당일(모델별)\n• 견과·치즈 크럼블(모델별)\n• 계란·견과 알레르기 표기(모델별)',
3, 23, 'system', NOW()),

('프레쉬 과일·그린 글래스 밀프렙 3팩', 26900.00, 0, 'https://plus.unsplash.com/premium_photo-1726217054433-6810c54fbe3d?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'대리석 위 직사각 유리 용기 세 개에 사과·복숭아·바나나, 시금치, 양상추, 그래놀라·씨앗이 나눠 담긴 냉장 밀프렙 샐러드·과일팩 사진입니다.',
'• 3컨테이너(모델별)\n• 냉장 2~3일(모델별)\n• 그래놀라·건과 옵션(모델별)\n• 저칼로리 간식·아침 대용(모델별)\n• 용기 내열 글라스(모델별)',
3, 23, 'system', NOW()),

('볶음 껍질 땅콩', 9900.00, 0, 'https://plus.unsplash.com/premium_photo-1726072356924-e29e8999df09?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'껍질째 견과내부가 보이는 연한 황갈색 땅콩이 화면을 가득 채운 클로즈업으로, 겉껍질의 그물 무늬 질감이 강조된 원물 스낵 이미지입니다.',
'• 400~600g(모델별)\n• 볶음 또는 생(모델별)\n• 식물성 단백·불포화지방(모델별)\n• 밀봉 지퍼(모델별)\n• 땅콩 알레르기 표기',
3, 23, 'system', NOW()),

('프리미엄 로스티드 홀 아몬드', 14900.00, 0, 'https://images.unsplash.com/photo-1615485737457-f07082c77813?q=80&w=1674&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'흰 배경 위에 껍질 벗긴 통아몬드가 한 덩이로 모이고 몇 알이 옆으로 흩어진 미니멀 제품 컷입니다. 겉피 결이 살아 있는 볶은 아몬드 톤입니다.',
'• 250~350g(모델별)\n• 비타민 E·식이섬유(모델별)\n• 무염·저염 옵션(모델별)\n• 실온 밀봉(모델별)\n• 견과 알레르기 표기',
3, 23, 'system', NOW()),

('건조 적강낭콩 (레드 키드니빈)', 12900.00, 0, 'https://plus.unsplash.com/premium_photo-1668446314000-a8bb3e4c874c?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'흰 도자기 볼 가득 짙은 적자색 강낭콩이 담긴 탑뷰로, 팥·콩 요리용 건조 콩류를 강조한 깔끔한 푸드 스타일링입니다.',
'• 500g~1kg(모델별)\n• 단백질·식이섬유·철분(모델별)\n• 쌀뜨물·압력솥 삶기 안내(모델별)\n• 실온 보관\n• 유기농 옵션(모델별)',
3, 23, 'system', NOW()),

('혼합 콩·옥수수 곡물 믹스', 18900.00, 0, 'https://images.unsplash.com/photo-1765144815957-6bc44c13fc2c?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'흰콩·적콩·얼룩콩·검은콩·녹두·마른 옥수수 알갱이 등이 색별로 모자이크처럼 펼쳐진 혼합 레귬·곡물 클로즈업입니다.',
'• 500g~800g(모델별)\n• 콩국·영양밥·수프용(모델별)\n• 세척·불리기 가이드(모델별)\n• 실온 밀봉(모델별)\n• 콩 알레르기 주의(모델별)',
3, 23, 'system', NOW()),

('데일리 스펙클 멀티비타민 타블렛', 28900.00, 0, 'https://images.unsplash.com/photo-1544829894-eb023ba95a38?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'보라색 플립캡이 열린 흰색 플라스틱 병이 기울어져 베이지빛에 갈색 점이 박힌 타원형 정제가 흘러나온 영양제 제품 사진입니다.',
'• 60~90정(모델별)\n• 1일 1~2정(모델별)\n• 종합 비타민·미네랄 복합(모델별)\n• 식후 물과 함께(모델별)\n• 건강기능식품 해당 시 인증 문구(모델별)',
3, 23, 'system', NOW()),

('저탄수 바질·리코타 주키니 누들 보울', 8900.00, 0, 'https://plus.unsplash.com/premium_photo-1663843332690-6220174770d7?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'스파이럴 주키니면에 리코타·바질·올리브오일·레몬이 어우러진 플레이팅이고, 손으로 허브를 뿌리는 지중해풍 저칼로리 한 끼 컷입니다.',
'• 1인분(모델별)\n• 글루텐프리·저탄수(모델별)\n• 냉장 당일(모델별)\n• 올리브오일·페퍼론치노(모델별)\n• 유제품 알레르기 표기(모델별)',
3, 24, 'system', NOW()),

('지중해식 병아리콩 페타 샐러드', 11200.00, 0, 'https://images.unsplash.com/photo-1679744034792-705da160c109?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'하늘색 배경 앞 두 손이 든 흰 볼에 병아리콩·오이·페타·칼라마타 올리브·허브가 담긴 포만감 있는 샐러드가 담긴 사진입니다.',
'• 1인분(모델별)\n• 식물성 단백·식이섬유(모델별)\n• 냉장(모델별)\n• 드레싱 분리(모델별)\n• 유제품·견과 알레르기(모델별)',
3, 24, 'system', NOW()),

('블랙볼 가든 믹스 샐러드', 7900.00, 0, 'https://images.unsplash.com/photo-1673006768205-b9a7bc6842a5?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'짙은 블랙 볼에 양상추·오이·방울토마토·브로콜리·버섯·당근·옥수수·올리브가 가득한 컬러풀 저칼로리 채소 샐러드입니다.',
'• 1인 대용량(모델별)\n• 드레싱 옵션(모델별)\n• 냉장 당일·익일(모델별)\n• 비건 가능(모델별)\n• 나트륨 저감(모델별)',
3, 24, 'system', NOW()),

('주키니·당근 스파이럴 채소면 키트', 9900.00, 0, 'https://images.unsplash.com/photo-1524240293321-31b1b9a207af?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'흰 접시에 오렌지빛 당근 스파이럴과 연녹색 주키니 스파이럴이 나란히 담긴 생채소면(죠들) 제품 이미지입니다.',
'• 300~400g(모델별)\n• 밀가루 0g(모델별)\n• 토마토·페스토 소스 별매(모델별)\n• 냉장 2~3일(모델별)\n• 키토·저탄수 식단용(모델별)',
3, 24, 'system', NOW()),

('시즌 신선 채소·과일 다이어트 박스', 19800.00, 0, 'https://plus.unsplash.com/premium_photo-1664302148512-ddea30cd2a92?q=80&w=2069&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'원목 테이블에 당근·콜리플라워·아티초크·방울토마토·허브, 사과·아보카도·블루베리·스타프루트·감귤 등이 한꺼번에 펼쳐진 클린식 재료 박스 컷입니다.',
'• 5~8종 이상(모델별)\n• 샐러드·스무디·밀프렙용(모델별)\n• 냉장 배송(모델별)\n• 계절 구성 변동(모델별)\n• 세척 권장(모델별)',
3, 24, 'system', NOW()),

('5종 견과·건과일 포션 세트', 12900.00, 0, 'https://images.unsplash.com/photo-1769255484888-e2c82fc1238c?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'회색 바탕에 흰 그릇 다섯 개에 캐슈·대추야자·아몬드·호두·껍질 피스타치오가 나뉘 담긴 한줌 간식·고단백 다이어트 스낵 구도입니다.',
'• 볼당 30~40g 기준(모델별)\n• 무염·무첨가당 옵션(모델별)\n• 실온 보관\n• 견과 알레르기 표기\n• 운동 전후 간식(모델별)',
3, 24, 'system', NOW()),

('밸런스 밀프렙 저칼로리 도시락 5팩', 14900.00, 0, 'https://plus.unsplash.com/premium_photo-1701213306571-e8097610bb10?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'투명 사각 용기에 감자·콩·토마토·그릴 단백질, 통밀 파스타 볼, 레인보우 채소·퀴노아 베이스 샐러드 등이 담긴 냉장 밀프렙 세트 사진입니다.',
'• 5팩(모델별)\n• 팩당 350~450kcal대(모델별)\n• 냉장 3~4일(모델별)\n• 전자레인지 가능(모델별)\n• 단백질·탄수·채소 밸런스(모델별)',
3, 24, 'system', NOW());

-- 뷰티 (category_id = 4)
INSERT IGNORE INTO products (name, price, discount, image, description, details, category_id, sub_category_id, created_by, created_date) VALUES
('골든 너리싱 페이셜 오일 드롭', 89000.00, 34, 'https://plus.unsplash.com/premium_photo-1679046948909-ab47e96082e7?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 와플 가운 차림의 손에 검정 캡 유리 드로퍼로 황금빛 오일이 한 방울 떨어지는 클린 뷰티 히어로 컷입니다.',
'• 세안 후 2~3방울(모델별)\n• 페이셜 오일·농축 세럼 제형(모델별)\n• 가벼운 흡수감(모델별)\n• 30~50ml(모델별)\n• 광채·보습 레이어링(모델별)',
4, 25, 'system', NOW()),

('웜톤 광채 세럼 드롭퍼', 72000.00, 34, 'https://images.unsplash.com/photo-1573461160327-b450ce3d8e7f?q=80&w=1065&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'따뜻한 자연광 아래 화이트 캡 드로퍼에서 앰버빛 액이 손등 위로 떨어지는 질감 강조 세럼·오일 연출 사진입니다.',
'• 앰플·세럼·페이셜 오일 라인(모델별)\n• 20~40ml(모델별)\n• 데일리·야간 겸용(모델별)\n• 끈적임 저감 포뮬러(모델별)\n• 민감 피부 패치 테스트 권장(모델별)',
4, 25, 'system', NOW()),

('미니멀 클렌저 & 펌프 모이스처 듀오', 52000.00, 34, 'https://images.unsplash.com/photo-1556228578-0d85b1a4d571?w=500&h=500&fit=crop',
'민트 타일 위 물방울이 맺힌 네이비 튜브형 클렌저와 펌프형 모이스처라이저가 나란히 선 바스룸 2스텝 루틴 컷입니다.',
'• 클렌저 80ml 전후(모델별)\n• 모이스처 50ml 전후(모델별)\n• 순한 세정·수분 밸런스(모델별)\n• 데일리 베이스 루틴(모델별)\n• 성분표 확인 권장(모델별)',
4, 25, 'system', NOW()),

('앰버 보태니컬 드롭 세럼', 95000.00, 34, 'https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'조각 받침대 위 앰버글라스 드롭병과 베이지 벽면에 드리운 열대 잎 그림자가 어우러진 보태니컬 무드 세럼 패키지 사진입니다.',
'• 광민감 성분 보호용 앰버병(모델별)\n• 30ml 전후(모델별)\n• 오일·세럼 겸용 느낌(모델별)\n• 비건·크루얼티프리 옵션(모델별)\n• 직사광선 피해 보관(모델별)',
4, 25, 'system', NOW()),

('로즈쿼츠 롤러·괄사 & 앰버 세럼 키트', 78000.00, 34, 'https://images.unsplash.com/photo-1600428853876-fb5a850b444f?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 배경에 듀얼 롤 로즈쿼츠 페이셜 롤러, 하트형 괄사, 라벨 있는 앰버 드롭병, 원석 로즈쿼츠 청크가 함께 놓인 홈케어 마사지 세트입니다.',
'• 롤러+괄사+세럼(모델별)\n• 순환·림프 마사지 가이드 동봉(모델별)\n• 세럼 15~30ml(모델별)\n• 사용 전 세정 권장(모델별)\n• 선물 포장(모델별)',
4, 25, 'system', NOW()),

('앰버·누드 톤 풀 루틴 세트', 128000.00, 34, 'https://images.unsplash.com/photo-1631730486572-226d1f595b68?q=80&w=1075&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'핑크 바탕에 앰버 유리병, 누드 톤 펌프·로션병, 화이트 튜브, 소형 자·드로퍼가 사선으로 펼쳐진 미니멀 프리미엄 라인 구성 컷입니다.',
'• 토너·세럼·에멀전·클렌저·크림 등 복수 구성(모델별)\n• 로즈골드·구리 캡 무드(모델별)\n• 데일리 풀 루틴(모델별)\n• 개봉 후 사용기한 준수(모델별)\n• 박스 세트(모델별)',
4, 25, 'system', NOW()),

('테라조 트레이 괄사 & 글로우 세럼 듀오', 76000.00, 34, 'https://plus.unsplash.com/premium_photo-1678377959909-3542d8096fa5?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'스펙클 테라조 오벌 트레이 위에 연한 로즈쿼츠 괄사와 황금빛 액이 든 클리어 드로퍼가 놓이고 유칼립투스 잎이 비치는 스파 무드 플랫레이입니다.',
'• 괄사 1+세럼 드로퍼(모델별)\n• 세럼 20~30ml(모델별)\n• 마사지 후 세럼 흡수(모델별)\n• 유리·천연석 취급 주의(모델별)\n• 셀프케어 선물용(모델별)',
4, 25, 'system', NOW()),

('더 플럼 풀 페이스 메이크업 키트', 78000.00, 0, 'https://plus.unsplash.com/premium_photo-1677526496597-aa0f49053ce2?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'짙은 플럼 배경 위 6색·2색 팔레트, 베이지 컴팩트 파우더, 리퀴드 파운데이션 튜브 2종, 레드 립스틱, 퍼플 네일 폴리시가 함께 연출된 풀 페이스 구성 3D 렌더 연출입니다.',
'• 아이·페이스·립·네일 포함(모델별)\n• 리퀴드 베이스 2쉐이드(모델별)\n• 세팅 파우더 컴팩트(모델별)\n• 선물 박스 구성(모델별)\n• 브랜드 무표기 컨셉 이미지(모델별)',
4, 26, 'system', NOW()),

('로즈골드 브러시 & 데일리 메이크업 세트', 62000.00, 0, 'https://images.unsplash.com/photo-1596462502278-27bfdc403348?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'베이지 바탕에 핑크 핸들 파우더·아이 브러시, 레드 립스틱, 로즈골드 마스카라, 오픈 브론저 컴팩트, 피치 크림 팟, 번개 이어링, 벨벳 스크런치가 흩어진 플랫레이입니다.',
'• 페이스+아이 브러시(모델별)\n• 립·아이·페이스 제품 믹스(모델별)\n• 액세서리 연출 포함(모델별)\n• 데일리 룩 연출용(모델별)\n• 구성 품목은 세트별 상이(모델별)',
4, 26, 'system', NOW()),

('듀얼 포색 아이 & 치크 팔레트', 58000.00, 0, 'https://images.unsplash.com/photo-1625093525885-282384697917?q=80&w=1701&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'흰 배경에 블랙 케이스 4구 팔레트 두 개가 대각선으로 놓였고, 한쪽은 누드·핑크 데일리 톤, 다른 쪽은 버건디·오렌지·플럼·마젠타 등 포인트 컬러 구성입니다.',
'• 4색×2팔레트(모델별)\n• 투명 리드(모델별)\n• 매트·새틴 믹스(모델별)\n• 아이섀도·블러셔 겸용 톤(모델별)\n• 미니멀 패키지',
4, 26, 'system', NOW()),

('트리 쉐이드 리퀴드 립 스와치 세트', 48000.00, 0, 'https://images.unsplash.com/photo-1631214524049-0ebbbe6d81aa?q=80&w=1674&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'핑크 배경에 레드·핑크·누드 MLBB 세 가지 리퀴드 립 튜브가 삼각 배치되고, 중앙 핑크 타일에 동일 색 스미어 스와치가 올라간 립 제품 컷입니다.',
'• 3색 세트(모델별)\n• 크리미 발색(모델별)\n• 블랙 캡 클리어 바디(모델별)\n• 틴트·글로스 제형 혼용 가능(모델별)\n• 박스 동봉(모델별)',
4, 26, 'system', NOW()),

('트리 톤 글래스 파운데이션 라인', 92000.00, 0, 'https://plus.unsplash.com/premium_photo-1673628167571-532a6c5f5d16?q=80&w=927&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'그레이 마블 위 직사각 이중유리 파운데이션 병 세 개가 라이트·미디엄·딥 쉐이드로 놓이고, 한 병은 펌프 오픈, 벽면에는 야자 잎 그림자가 드리운 미니멀 뷰티 사진입니다.',
'• 30ml 전후×3(모델별)\n• 펌프 타입(모델별)\n• 쉐이드 확장 라인(모델별)\n• 세미 매트·내추럴 스킨(모델별)\n• 박스 세트(모델별)',
4, 26, 'system', NOW()),

('우드 트레이 루스 파우더 트리오', 55000.00, 0, 'https://plus.unsplash.com/premium_photo-1678377960024-de52c1a872bf?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'밝은 우드 트레이 위 큰 시프터 루스 파우더, 테라코타 톤 블러셔·브론저 느낌 파우더, 화이트 자의 페일 하이라이터·세팅 파우더가 꽃·유칼립투스와 함께 놓인 자연 무드 컷입니다.',
'• 루스 3종(모델별)\n• 세팅·치크·하이라이트 용도(모델별)\n• 미네랄 느낌 포뮬러(모델별)\n• 비건 옵션(모델별)\n• 개봉 후 사용기한(모델별)',
4, 26, 'system', NOW()),

('스퀘어 케이스 코랄 누드 립스틱', 45000.00, 0, 'https://images.unsplash.com/photo-1625093742435-6fa192b6fb10?q=80&w=2089&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 배경에 초콜릿 브라운 직사각 케이스와 은색 메탈링, 피치·코랄 누드 컬러 촉이 올라온 싱글 립스틱 오픈 컷입니다.',
'• 단품 3~4g(모델별)\n• 새틴·크림 매트(모델별)\n• MLBB·코랄 시즌(모델별)\n• 케이스 각인 디자인(모델별)\n• 백화점 라인 느낌(모델별)',
4, 26, 'system', NOW()),

('로즈 글라스 리본 오 드 퍼퓸', 165000.00, 31, 'https://images.unsplash.com/photo-1458538977777-0549b2370168?q=80&w=2074&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'베이지 톤 배경 위 스퀘어 글라스에 연한 로즈·피치빛 액체, 은빛 넥 리본과 큐브 캡, 하단 텍스처 글라스가 보이는 플로럴 무드 오 드 퍼퓸 병 컷입니다.',
'• 50~100ml(모델별)\n• 플로럴·머스키 베이스(모델별)\n• EDP(모델별)\n• 직사광선 피해 보관\n• 선물용 박스(모델별)',
4, 27, 'system', NOW()),

('코지 윈터 앰버 오 드 뚜왈렛', 118000.00, 31, 'https://plus.unsplash.com/premium_photo-1679106770086-f4355693be1b?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'케이블 니트 스웨터 차림의 손이 맑은 유리병 앰버빛 퍼퓸을 손목에 뿌리는 라이프스타일 샷으로, 흰 커튼·은색 주얼리가 함께 비칩니다.',
'• 30~50ml(모델별)\n• EDT(모델별)\n• 우디·시트러스 톤(모델별)\n• 데일리·오피스(모델별)\n• 지속력은 피부 타입별 상이(모델별)',
4, 27, 'system', NOW()),

('골든 앰버 퍼퓸 & 프로스트 자 트리오', 138000.00, 31, 'https://images.unsplash.com/photo-1622618991746-fe6004db3a47?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'머스타드 옐로우 바탕에 직사각 앰버 퍼퓸 병과 은캡 프로스트 글라스 크림 자 두 개가 놓이고 강한 광선과 카우스틱 그림자가 드리운 럭셔리 뷰티 스틸입니다.',
'• 퍼퓸+페이스 밤·크림(모델별)\n• 세트 구성(모델별)\n• 50ml+15ml×2 등(모델별)\n• 선물 포장(모델별)\n• 향·제형은 라인별 상이(모델별)',
4, 27, 'system', NOW()),

('팜 섀도우 뉴트럴 퍼퓸 & 비누 세트', 92000.00, 31, 'https://plus.unsplash.com/premium_photo-1679064286615-e5e4d4940dfc?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'베이지 벽과 바닥에 야자·몬스테라 잎 그림자가 드리운 가운데, 플랫 클리어 퍼퓸 병과 올리브 그린 아티산 비누·메쉬 파우치가 놓인 스파 무드 세트입니다.',
'• 퍼퓸 30~50ml(모델별)\n• 천연 비누 동봉(모델별)\n• 클린·그린 노트(모델별)\n• 여행·욕실 인테리어(모델별)\n• 박스 세트(모델별)',
4, 27, 'system', NOW()),

('미니멀 클리어 글래스 퍼퓸 쿼드', 88000.00, 31, 'https://plus.unsplash.com/premium_photo-1739831741647-ae1bff25492d?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'밝은 그레이 톤 평면에 투명 유리 퍼퓸 병 네 개가 사선으로 늘어서고, 실버 펌프와 긴 그림자·유리 굴절 하이라이트가 강조된 모노크롬 제품 사진입니다.',
'• 4병 세트 또는 디스플레이용(모델별)\n• 각 10~15ml 미니(모델별)\n• 무향·베이스만 옵션(모델별)\n• 리필 라인(모델별)\n• 직사광선 피하기',
4, 27, 'system', NOW()),

('블랙 캡 골드 악센트 앰버 퍼퓸', 178000.00, 31, 'https://images.unsplash.com/photo-1723391962154-8a2b6299bc09?q=80&w=1064&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'블랙 배경에 직사각 글라스 속 허니골드 액체, 금속 골드 장식 넥과 비대칭 블랙 캡이 돋보이는 하이콘트라스트 럭셔리 퍼퓸 히어로 이미지입니다.',
'• 50~90ml(모델별)\n• 오리엔탈·앰버 우디(모델별)\n• EDP(모델별)\n• 이브닝·시즈널(모델별)\n• 박스·리본 포장(모델별)',
4, 27, 'system', NOW()),

('플로럴 리본 리 드 퍼퓸', 132000.00, 31, 'https://images.unsplash.com/photo-1615108395437-df128ad79e80?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 배경에 세로 리드가 있는 둥근 투명 병, 은색 캡, 민트 리본, 피치·크림 톤 난초 류 꽃이 병을 감싸 프레이밍한 로맨틱 플로럴 퍼퓸 컷입니다.',
'• 30~75ml(모델별)\n• 화이트 플로럴·머스크(모델별)\n• EDT·EDP 라인(모델별)\n• 웨딩·선물 추천(모델별)\n• 알코올 함유 표기(모델별)',
4, 27, 'system', NOW()),

('프로스티 펌프 헤어 에센스 오일', 32000.00, 0, 'https://plus.unsplash.com/premium_photo-1706800175276-047b33155dc9?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'연베이지 배경 위 손바닥에 올려 든 서리 유리감 실린더 병, 블랙 펌프, 연노랑 헤어 오일·에센스 액이 담긴 미니멀 제품 컷입니다.',
'• 80~150ml(모델별)\n• 젖은 모발·건조 모발 겸용(모델별)\n• 실리콘·오일 블렌드(모델별)\n• 끝 갈라짐 케어(모델별)\n• 1~2펌프(모델별)',
4, 28, 'system', NOW()),

('허니 글로스 샴푸 트리트먼트 컷', 26000.00, 0, 'https://plus.unsplash.com/premium_photo-1728693697249-1d56feca531a?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'짙은 앰버 병에서 꿀처럼 진한 액이 거품 낀 젖은 갈색 머리카락 위로 붓는 클렌징·트리트먼트 연출 사진입니다.',
'• 샴푸 또는 인샤워 트리트먼트(모델별)\n• 300~500ml(모델별)\n• 보습·광택(모델별)\n• 색조모 옵션(모델별)\n• 두피 자극 시 중단(모델별)',
4, 28, 'system', NOW()),

('바이올렛 헤어 세럼 & 팜 브러시 세트', 38000.00, 0, 'https://plus.unsplash.com/premium_photo-1677849533990-ad83e1d7024e?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'와플 질감 화이트 타월 위 반투명 퍼플 펌프 병과 파스텔 핑크·라벤더 디탱글링 팜 브러시가 놓인 데일리 헤어 케어 정적 컷입니다.',
'• 세럼·오일 1+브러시 1(모델별)\n• 엉킴 방지(모델별)\n• 젖은 머리 빗기 권장(모델별)\n• 여행용 파우치(모델별)\n• 정기 세척(모델별)',
4, 28, 'system', NOW()),

('내추럴 우드 와이드투스 빗 8P', 22000.00, 0, 'https://plus.unsplash.com/premium_photo-1664544673201-9f1809938ddd?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'피치 톤 평면에 연한 우드 빗 여러 자루가 사선 그리드로 반복 배치되고 부드러운 그림자가 드리운 에코 헤어 툴 패턴 이미지입니다.',
'• 8~10자루 세트(모델별)\n• 대나무·원목(모델별)\n• 두피 마사지 겸용(모델별)\n• 선물·살롱 디스플레이(모델별)\n• 물에 장시간 담그지 않기(모델별)',
4, 28, 'system', NOW()),

('프로 살롱 헤어 커팅 시저스', 48000.00, 0, 'https://images.unsplash.com/photo-1711274091943-5aae912e6985?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'어두운 배경 앞 손에 잡힌 스테인리스 전문 가위가 벌어진 채 블레이드 하이라이트가 살아 있는 살롱급 커팅 시저스 클로즈업입니다.',
'• 스테인리스 스틸(모델별)\n• 5.5~6인치(모델별)\n• 오일링·보관 케이스(모델별)\n• 가정용·디자이너 겸용(모델별)\n• 날쏠 주의(모델별)',
4, 28, 'system', NOW()),

('실리콘 스칼프 샴푸 브러시', 18000.00, 0, 'https://plus.unsplash.com/premium_photo-1706800175562-eaaa425204db?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'라이트 베이지 배경에서 손에 든 블랙 실리콘 원형 스칼프 브러시에 샴푸 거품이 묻어 있는 두피 클렌징 툴 사진입니다.',
'• 샴푸 시 원형 마사지(모델별)\n• 실리콘 돌기(모델별)\n• 사용 후 헹굼·건조(모델별)\n• 두피 각질·순환(모델별)\n• 손톱 긁힘 완화(모델별)',
4, 28, 'system', NOW()),

('벨벳 헤어 스크런치 7종 세트', 24000.00, 0, 'https://images.unsplash.com/photo-1672699323645-75ace776093e?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'오프화이트 린넨 위 블랙·로얄블루·슬레이트블루·올리브·더스티핑크·코랄·머스타드 순으로 쌓인 벨벳 질감 스크런치 스택 이미지입니다.',
'• 7컬러(모델별)\n• 벨벳·새틴 혼용 옵션(모델별)\n• 잠금 자국 적은 스타일(모델별)\n• 세탁 시 찬물(모델별)\n• 데일리·운동 헤어(모델별)',
4, 28, 'system', NOW()),

('세라믹 볼 네이처 배스 스파 세트', 36000.00, 0, 'https://plus.unsplash.com/premium_photo-1679064286466-6e1ee9d3a44d?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'플루티드 오프화이트 도자기 볼 안에 연녹색 서리글라스 드로퍼 병, 천연 바다 스펀지, 크림·허니톤 비누 두 장, 녹색 스톤 마사지 툴이 담긴 마블 위 스파 정물입니다.',
'• 바디 오일·세럼 드로퍼(모델별)\n• 핸드메이드 솝(모델별)\n• 천연 스펀지(모델별)\n• 괄사·스톤 툴(모델별)\n• 선물 바구니 구성(모델별)',
4, 29, 'system', NOW()),

('마블 트레이 화이트 바디 로션 세트', 34000.00, 0, 'https://plus.unsplash.com/premium_photo-1679064286611-ad551a46b126?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 마블 위 도자기 트레이에 무지 화이트 펌프 병, 탄·크림 비누 스택, 내추럴 스펀지, 소형 글라스 접시와 골드 네크리스가 어우러진 미니멀 바디 케어 컷입니다.',
'• 바디 로션·워시 겸용 펌프(모델별)\n• 아티산 솝(모델별)\n• 천연 스펀지(모델별)\n• 300~500ml(모델별)\n• 무향·저자극 옵션(모델별)',
4, 29, 'system', NOW()),

('스캘럽 볼 솝 & 스펀지 컬렉션', 32000.00, 0, 'https://plus.unsplash.com/premium_photo-1679064286475-f15eca3e313d?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'스캘럽 림 도자기 볼에 바다 스펀지 더미, 원형 퍽 솝 3단 스택, 직사각 글리세린·크림 비누가 함께 담긴 제로웨이스트 배스 무드 사진입니다.',
'• 솝 4~6종(모델별)\n• 천연 스펀지(모델별)\n• 각질·세정(모델별)\n• 실온 보관·건조망 권장(모델별)\n• 비건 옵션(모델별)',
4, 29, 'system', NOW()),

('옐로 드레이프 우드볼 스파 키트', 39000.00, 0, 'https://plus.unsplash.com/premium_photo-1720861081152-a58ff7b9342f?q=80&w=1674&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'밝은 옐로우 드레이프 천 앞 나무 그릇에 블랙 펌프 병·화이트 라벨, 연두 비누, 우드핸들 바디 브러시, 내추럴 스펀지, 롤드 화이트 타월이 담긴 스파 키트 컷입니다.',
'• 바디 워시·로션 펌프(모델별)\n• 드라이 브러싱 브러시(모델별)\n• 아티산 솝(모델별)\n• 타월 2롤(모델별)\n• 선물 세트(모델별)',
4, 29, 'system', NOW()),

('팜 섀도우 코스트 배스 트리오', 35000.00, 0, 'https://plus.unsplash.com/premium_photo-1679064287823-fbd549bf47dd?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'베이지 입자감 표면에 초승달 도자기 접시의 천연 스펀지, 크로셰 파우치 위 올리브 그린 비누, 은캡 사각 글래스의 밀키 화이트 액, 야자 잎 그림자가 겹친 탑뷰입니다.',
'• 밀크 바디 로션·오일(모델별)\n• 천연 솝·비누망(모델별)\n• 스펀지+디쉬(모델별)\n• 200~400ml(모델별)\n• 열대 무드 인테리어(모델별)',
4, 29, 'system', NOW()),

('파스텔 펌프 & 드라이 브러시 리추얼 세트', 42000.00, 0, 'https://images.unsplash.com/photo-1658915379545-2c0edd7feb2a?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'트라버틴 느낌 베이지 스톤 위 파스텔 핑크 매트 펌프 병, 우드 핸들 바디 브러시, 연녹 광택 빗, 크리스탈 펜던트 네크리스, 촛불이 켜진 다크 그린 캔들홀더가 함께한 웰니스 정물입니다.',
'• 펌프 클렌저·로션 350ml급(모델별)\n• 드라이 브러시(모델별)\n• 스톤·캔들 연출(모델별)\n• 샤워 후 바디 케어(모델별)\n• 성분표 확인(모델별)',
4, 29, 'system', NOW()),

('와이어 바스켓 바디 케어 오거나이저 세트', 31000.00, 0, 'https://plus.unsplash.com/premium_photo-1679064286464-4aa8ff30b03a?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'블랙 와이어 바스켓 안에 화이트 펌프 병, 크림·탄 비누 스택, 옐로우 스펀지, 세이지 그린 프린지 타월이 정리되어 화이트 마블 선반 위에 놓인 욕실 스토리지 컷입니다.',
'• 바스켓+펌프+솝+타월(모델별)\n• 욕실 수납(모델별)\n• 게스트 타월 겸용(모델별)\n• 무지 리필 병(모델별)\n• 선물 세트(모델별)',
4, 29, 'system', NOW()),

('리브드 월 앰버 퍼밍 오일 드로퍼', 42000.00, 0, 'https://plus.unsplash.com/premium_photo-1669735916600-17335a9e0db8?q=80&w=1015&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'베이지 톤 바닥과 세로 리브 벽면에 앰버글라스 드롭퍼 병이 기대어 있고, 화이트 무지 라벨·골드 넥·블랙 러버 벌브가 보이는 럭셔리 미니멀 스튜디오 컷입니다.',
'• 페이셜·비어드 오일(모델별)\n• 30~50ml(모델별)\n• 야간 보습·광택(모델별)\n• 유니섹스 패키지(모델별)\n• 직사광선 피해 보관(모델별)',
4, 30, 'system', NOW()),

('테라코타 프로스티 세럼 드로퍼', 36000.00, 0, 'https://plus.unsplash.com/premium_photo-1674911578945-aa498430b670?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'테라코타 단색 배경 앞 손가락으로 집은 서리글라스 소형 드롭퍼 병, 골드 캡링, 블랙 벌브가 강조된 세럼·앰플 히어로 이미지입니다.',
'• 에센스·세럼(모델별)\n• 15~30ml(모델별)\n• 산뜻 제형(모델별)\n• 아침·저녁 겸용(모델별)\n• 민감 피부 패치(모델별)',
4, 30, 'system', NOW()),

('앰버 골드 그루밍 6종 디스플레이 세트', 48000.00, 0, 'https://images.unsplash.com/photo-1636740599648-ae84f705fc2e?q=80&w=2069&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'연한 우드 상판에 클리어·프로스트 앰버 병, 골드 뚜껑 자, 골드 펌프·미스트 소형병이 층위 있게 배치된 무표지 그루밍 라인 정물입니다.',
'• 크림·토너·미스트 등 복합(모델별)\n• 세트 또는 단품 라인(모델별)\n• 남성·유니섹스 톤(모델별)\n• 욕실 디스플레이 컨셉(모델별)\n• 개봉 후 기한(모델별)',
4, 30, 'system', NOW()),

('누드 스킨 컨트롤 파우더 & 카부키 키트', 32000.00, 0, 'https://images.unsplash.com/photo-1701271482230-5ecaec3cd3e1?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'오프화이트 배경에 프로스트 자에 베이지 루스 파우더, 초승달로 흘린 파우더, 로즈골드 페룰 미니 카부키, 핑크베이지 크림 자가 나란히 놓인 누드 톤 그루밍 컷입니다.',
'• 유분·광 조절 파우더(모델별)\n• 컨실링 밤·BB 겸용(모델별)\n• 휴대용 브러시(모델별)\n• 자연 스킨 룩(모델별)\n• 세안으로 제거(모델별)',
4, 30, 'system', NOW()),

('매트 블랙 페이셜 미스트', 28000.00, 0, 'https://images.unsplash.com/photo-1697309006580-cda397ee09c2?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'뮤트 베이지 배경 하단에 매트 블랙 미니 스프레이 병, 연보라 코스모스 한 송이, 찢어진 가장자리 수제지와 질감 카드가 어우러진 미니멀 뷰티 스틸입니다.',
'• 토너·픽싱·수분 미스트(모델별)\n• 50~100ml(모델별)\n• 무광 케이스(모델별)\n• 외출·사무실(모델별)\n• 눈 입고 시 즉시 씻기(모델별)',
4, 30, 'system', NOW()),

('팩티드 캡 글래스 오 드 퍼퓸', 45000.00, 0, 'https://images.unsplash.com/photo-1707539159801-87009aded4cb?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'큰 다이아컷 투명 캡이 올라간 스퀘어 글래스 향수병이 라넌큘러스·수국·오렌지 릴리 등 파스텔·비비드 플로럴 앞에 놓인 로맨틱 향수 광고 컷입니다.',
'• 50~100ml(모델별)\n• 플로럴·시트러스 향조(모델별)\n• 유니섹스 착향(모델별)\n• EDP·EDT(모델별)\n• 선물용 박스(모델별)',
4, 30, 'system', NOW()),

('글로우 크림 & 펌프 & 괄사 미니 세트', 34000.00, 0, 'https://plus.unsplash.com/premium_photo-1670584248601-187ba4c8b301?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'파스텔 핑크 배경과 반사 바닥 위 클리어 항아리 화이트 크림, 뚜껑 분리, 슬림 화이트 펌프 병, 연마블 느낌 괄사가 한데 놓인 미러 스튜디오 구도입니다.',
'• 데일리 크림+로션 펌프(모델별)\n• 페이셜 괄사(모델별)\n• 유니섹스 미니멀 패키지(모델별)\n• 아침 붓기 케어(모델별)\n• 세라마이드 등 성분(모델별)',
4, 30, 'system', NOW()),

('프로 누드 핑크 매니큐어 시술', 38000.00, 0, 'https://images.unsplash.com/photo-1659391542239-9648f307c0b1?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'터키시 블루 네일의 매니큐어사가 골드 캡 브러시로 고객 손에 연핑크 폴리시를 바르는 살롱 클로즈업입니다.',
'• 베이스·컬러·탑 코트(모델별)\n• 원컬러 또는 그라데이션(모델별)\n• 예약제(모델별)\n• 건조 시간 안내(모델별)\n• 젤·일반 폴리시 옵션(모델별)',
4, 31, 'system', NOW()),

('스퀘어 캡 네일 컬러 컬렉션 12색', 32000.00, 0, 'https://images.unsplash.com/photo-1667242197482-ffe672de74da?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 바탕에 직사각 글래스 병이 무리 지어 놓였고 블랙·화이트 무광 스퀘어 캡, 그레이·화이트·차트루즈·핑크·레드·오렌지·틸 등 다채로운 액이 보입니다.',
'• 10~12색 세트(모델별)\n• 무표지 미니멀 패키지(모델별)\n• 매트·글로시 혼합(모델별)\n• 실온 직사광선 피하기\n• 아세톤 리무버',
4, 31, 'system', NOW()),

('마블 프롭 파스텔 블루 네일 폴리시', 18000.00, 0, 'https://plus.unsplash.com/premium_photo-1679064286417-538b9230b5ce?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'집 모양으로 깎인 화이트 마블 받침에 기댄 스퀘어 글래스, 코니플라워 블루 불투명 액, 블랙 실린더 캡이 비치는 연마블 테이블 반사 컷입니다.',
'• 10~15ml(모델별)\n• 크림 타입(모델별)\n• 셀프 네일·풋(모델별)\n• 2코트 권장(모델별)\n• 개봉 후 24개월 내(모델별)',
4, 31, 'system', NOW()),

('프로 레드 젤 폴리시 & 샵 툴', 26000.00, 0, 'https://plus.unsplash.com/premium_photo-1664375245804-6c5714615396?q=80&w=1036&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'핑크 니트릴 장갑 손이 블랙 매트 병에서 붉은 오렌지 폴리시가 방울져 떨어지는 브러시를 들고, 흰 테이블에 파일·스와치·트레이가 흐릿하게 보이는 샵 장면입니다.',
'• 젤 또는 고발색 일반(모델별)\n• UV·LED 램프 별매(젤 시)(모델별)\n• 8~12ml(모델별)\n• 위생적 사용(모델별)\n• 전문가용 연출 이미지(모델별)',
4, 31, 'system', NOW()),

('선셋 코랄 네일 폴리시 4종', 24000.00, 0, 'https://images.unsplash.com/photo-1667242196587-33f541537cc6?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'연회색 바탕에 스퀘어 병 네 개가 한 줄로 놓였고 코랄핑크·오렌지·버밀리온·딥레드 액과 화이트·블랙 캡이 교차하는 강한 햇살 그림자 컷입니다.',
'• 4색 세트(모델별)\n• 썸머·가을 시즌 톤(모델별)\n• 글로시(모델별)\n• 실온 보관\n• 셀프 네일 아트 베이스(모델별)',
4, 31, 'system', NOW()),

('주얼톤 펄 네일 폴리시 6종', 29000.00, 0, 'https://images.unsplash.com/photo-1636019411401-82485711b6ba?q=80&w=1689&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 배경에 블랙 스퀘어 캡 병 여섯 개가 부채꼴로 펼쳐지고 라임·올리브·로얄블루·페리윙클·마젠타·퍼플 등 메탈릭 펄 컬러가 반사광을 띱니다.',
'• 6색 세트(모델별)\n• 펄·쉬머(모델별)\n• 파티·포인트 네일(모델별)\n• 베이스·탑 권장(모델별)\n• 흔들어 혼합 후 사용(모델별)',
4, 31, 'system', NOW()),

('스필 아트 네일 팔레트 6색', 36000.00, 0, 'https://plus.unsplash.com/premium_photo-1723651384223-0a6c63108671?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'탑뷰로 열린 스퀘어 병 여섯 개가 흰 바닥에 놓이고 시안·로열 블루·라벤더·코랄·피치·누드 핑크 액이 중앙으로 흘러 겹치는 아트 디렉션 네일 컷입니다.',
'• 컬러 스토리 세트(모델별)\n• 글로시 텍스처(모델별)\n• 네일 아트·스와치용(모델별)\n• 환기가 잘 되는 곳에서 사용(모델별)\n• 피부 접촉 후 즉시 씻기(모델별)',
4, 31, 'system', NOW()),

('미니멀 비치 라운지 선크림 튜브 3종', 26800.00, 0, 'https://plus.unsplash.com/premium_photo-1681701831486-428bc0700cdb?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'피치톤 모래와 연한 풀 물결 위에 핑크 데크체어·줄무늬 비치 타월, 기하 패턴 파라솔이 어우러진 3D 장면에 화이트 스퀴즈 튜브 세 개가 놓인 썸머 선케어 비주얼입니다.',
'• 무표지 미니멀 튜브(모델별)\n• 야외·휴양 컨셉 스틸(모델별)\n• SPF·PA 표기는 실제 제품 확인(모델별)\n• 직사광선·고온 피해 보관(모델별)\n• 눈 입고 시 즉시 씻기(모델별)',
4, 32, 'system', NOW()),

('풀사이드 파라솔 선크림 튜브 3종', 27200.00, 0, 'https://plus.unsplash.com/premium_photo-1681701831504-7d48582d800d?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'모래와 곡선 풀 물, 핑크·브라운 줄무늬 파라솔 아래 데크체어·타월에 골드캡 화이트 튜브 세 개가 배치된 밝은 햇살 그림자의 비치 3D 컷입니다.',
'• 스퀴즈 튜브 타입(모델별)\n• 바캉스·물놀이 무드(모델별)\n• 끈적임 적은 제형(모델별)\n• 사용 전 흔들어 혼합(모델별)\n• 유통기한·개봉 후 사용기한 준수(모델별)',
4, 32, 'system', NOW()),

('오렌지 워터프루프 선 스프레이', 23800.00, 0, 'https://plus.unsplash.com/premium_photo-1682535210542-21dceae4530c?q=80&w=988&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 타월과 투명한 풀 물가에 블랙 선글라스와 오렌지 플라스틱 스프레이 병이 놓여 강한 햇빛 그림자가 드리운 탑뷰 풀사이드 선케어 사진입니다.',
'• 분사형 선케어(모델별)\n• 물놀이·야외 재도포에 유리(모델별)\n• 얼굴 사용 시 눈·입 피하기(모델별)\n• 환기가 잘 되는 곳에서 분사(모델별)\n• 플라멍·고온 근처 보관 금지(모델별)',
4, 32, 'system', NOW()),

('샌드 플랫레이 선 로션 & 비치 키트', 22400.00, 0, 'https://plus.unsplash.com/premium_photo-1663133679087-bc5deb50ab00?q=80&w=1002&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'고운 모래 바닥에 화이트 프레임 선글라스, 오렌지 캡 화이트 그립 병, 노란 쪼리가 나란히 놓인 미니멀 해변 에센셜 플랫레이입니다.',
'• 손으로 짜는 로션·젤 타입(모델별)\n• 휴양 액세서리와 함께 연출된 컷(모델별)\n• 전신·얼굴 겸용 여부는 제품별 확인(모델별)\n• 모래·이물 묻으면 닦은 뒤 사용(모델별)\n• 어린이 손이 닿지 않는 곳에 보관(모델별)',
4, 32, 'system', NOW()),

('파스텔 옐로 비치 웨이브 선크림', 29500.00, 0, 'https://plus.unsplash.com/premium_photo-1750182350810-79fced10d5a4?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'골든 모래 위에 파스텔 옐로 무광 튜브가 놓이고 모서리로 하얀 파도 거품이 스치는 워터 액티비티 무드의 비치 선크림 컷입니다.',
'• 워터프루프 제형 옵션(모델별)\n• 무표지 미니멀 패키지(모델별)\n• 물에 들어가기 15~20분 전 도포 권장(모델별)\n• 타월로 닦은 뒤 재도포(모델별)\n• 개봉 후 직사광선 피해 보관(모델별)',
4, 32, 'system', NOW()),

('타월 플랫레이 선 스틱 & 스프레이 세트', 35800.00, 0, 'https://plus.unsplash.com/premium_photo-1750232555525-252db4b866b0?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'베이지 타월 위에 버건디 오벌 선글라스, 화이트 스틱을 잡은 손, 매트 화이트 튜브, 갈색 투명 스프레이 병이 함께 놓인 데일리·휴양 겸용 선케어 플랫레이입니다.',
'• 스틱·튜브·스프레이 조합 연출(모델별)\n• 부분 덧바르기·전신 분사 등 용도 구분(모델별)\n• 투명 스틱·톤업 스틱 등 제형별 선택(모델별)\n• 세트 구성은 판매 페이지 기준(모델별)\n• 알레르기 반응 시 사용 중단(모델별)',
4, 32, 'system', NOW()),

('SPF50 옐로 풀사이드 선크림 튜브', 26200.00, 0, 'https://plus.unsplash.com/premium_photo-1770480286062-bb3f35cbed8d?q=80&w=2019&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'머스타드 줄무늬 타월과 라운드 선글라스, 오렌지 주스가 보이는 풀데크 위에 옐로 튜브에 SUNSCREEN·SPF50 인쇄가 보이는 하이앵글 풀사이드 컷입니다.',
'• 튜브 표기 SPF는 실제 제품과 동일(모델별)\n• 광범위 자외선 차단 컨셉(모델별)\n• 물놀이 전 충분량 도포(모델별)\n• 땀·물에 지워지면 재도포(모델별)\n• 세안제로 깨끗이 세정(모델별)',
4, 32, 'system', NOW());

-- 홈인테리어 (category_id = 5)
INSERT IGNORE INTO products (name, price, discount, image, description, details, category_id, sub_category_id, created_by, created_date) VALUES
('원목 조각형 페데스탈 오벌 테이블', 985000.00, 14, 'https://plus.unsplash.com/premium_photo-1682582241642-d16c69cc087c?q=80&w=1691&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'밝은 실내에 세워진 따뜻한 톤 원목 오벌 상판과 원기둥·반구가 겹쳐 보이는 조각적 페데스탈 베이스가 특징인 미니멀 테이블입니다. 상판 오른쪽에는 화이트 도자 화병에 마른 가지가 꽂여 있습니다.',
'• 오벌 상판·기하 베이스(모델별)\n• 원목 그레인 노출 마감(모델별)\n• 식탁·소파 테이블 겸용 사이즈(모델별)\n• 조립·배송 조건은 상품 안내 확인(모델별)\n• 코스터·패드 사용 권장(모델별)',
5, 33, 'system', NOW()),

('크림 부클레 오가닉 1인 라운지 체어', 628000.00, 14, 'https://images.unsplash.com/photo-1684165610413-2401399e0e59?q=80&w=1065&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'크림·아이보리 톤의 입체적인 부클레 패브릭으로 몸을 감싸는 오가닉 실루엣과 낮은 시트, 통합형 팔걸이가 이어지는 1인용 라운지 체어입니다. 하부는 다크 메탈의 낮은 원형 베이스로 마감되어 있습니다.',
'• 부클레·벨벳 등 커버 소재(모델별)\n• 스위블 여부는 모델별 상이(모델별)\n• 좌판 높이·깊이 스펙 확인(모델별)\n• 직사광선 장시간 피하기(모델별)\n• 오염 시 전문 클리닝 권장(모델별)',
5, 33, 'system', NOW()),

('머스타드 미드센추리 액센트 암체어', 385000.00, 0, 'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?q=80&w=1558&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'머스타드 옐로 패브릭 시트와 높은 등받이, 블랙 테이퍼드 레그가 돋보이는 액센트 체어가 거실 한켠에 놓인 스테이징입니다. 골드 플로어 램프, 화이트 마블 사이드 테이블, 화이트 미디어 콘솔이 같은 컷에 보입니다.',
'• 액센트 체어 단품 중심 연출(모델별)\n• 주변 가구·조명은 별매(모델별)\n• 원목 바닥·러그와 톤 매칭 용이(모델별)\n• 패브릭 이염·마찰 주의(모델별)\n• 하중·좌폭 스펙은 상세페이지 확인(모델별)',
5, 33, 'system', NOW()),

('대형 원목 식탁 & 앵글 체어 식사 세트', 1680000.00, 20, 'https://images.unsplash.com/photo-1604578762246-41134e37f9cc?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'광택 바닥과 통유리 문 너머 정원이 보이는 다이닝룸에 장방형 솔리드 우드 테이블과 같은 목재 톤의 앵글 프레임 체어·라이트 쿠션 시트가 둘러진 고급 식사 공간 컷입니다. 상단에는 화이트·코퍼 톤 3단 펜던트가 걸려 있습니다.',
'• 테이블 길이·인원은 모델별(모델별)\n• 체어 쿠션 탈착·세탁 여부 확인(모델별)\n• 펜던트·테이블웨어는 연출용(모델별)\n• 배송·설치 옵션 별도(모델별)\n• 실내 습도 관리로 목재 변형 방지(모델별)',
5, 33, 'system', NOW()),

('올리브 벨벳 채널 터프팅 3인 소파', 1420000.00, 24, 'https://images.unsplash.com/photo-1606744888344-493238951221?q=80&w=1674&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'올리브 그린 벨벳에 세로 채널 스티치가 들어간 유선형 3인 소파와 골드 슬림 레그, 앞쪽 원형 우드톤 커피테이블·흑백 기하 러그가 어우러진 클래식 몰딩 벽면의 거실 장면입니다. 쿠션은 벨벳·하운즈투스 등 믹스 매치입니다.',
'• 3인 기준 폭·깊이(모델별)\n• 벨벳·채널 터프팅 디테일(모델별)\n• 쿠션 구성은 세트별 상이(모델별)\n• 커피테이블·러그는 별매(모델별)\n• 정기적으로 방향 돌려 착좌(모델별)',
5, 33, 'system', NOW()),

('라이트 오크 곡선백 다이닝 체어', 245000.00, 0, 'https://plus.unsplash.com/premium_photo-1682582245151-aa44d698771f?q=80&w=1078&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'그레이 톤 벽 앞에 연한 오크 프레임의 곡선 등받이와 테이퍼드 라운드 레그, 크림 시트 패드가 조합된 미드센추리 스타일 식탁용 의자가 단독 배치된 스튜디오 컷입니다. 좌측에는 다이아몬드 텍스처 화이트 도자 화병이 받침대 위에 놓여 있습니다.',
'• 원목 프레임·패브릭·가죽 시트(모델별)\n• 식탁·데스크 체어 겸용(모델별)\n• 최대 하중·좌높이 스펙 확인(모델별)\n• 습기·직사광선 피해 보관(모델별)\n• 바닥 패드·의자 캡 상태 점검(모델별)',
5, 33, 'system', NOW()),

('오크 트레슬 데스크 & 체어 세트', 582000.00, 10, 'https://images.unsplash.com/photo-1611269154421-4e27233ac5c7?q=80&w=1065&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'밝은 원목 바닥과 흰 벽, 블라인드 그림자 아래 연한 오크 트레슬 다리와 라운드 코너 상판, 측면 가죽 포켓이 달린 데스크와 블랙 메탈 프레임·우드 암레스트 체어가 짝을 이룬 홈오피스 구성입니다. 상면에는 화이트 데스크 램프와 슬림 화병이 놓여 있습니다.',
'• 데스크 폭·깊이·높이(모델별)\n• 체어·포켓 포함 세트 또는 단품(모델별)\n• 케이블 홀·서랍 유무 확인(모델별)\n• 조립 도구·설명서 동봉(모델별)\n• 무거운 물건은 상판 중앙 배치 권장(모델별)',
5, 33, 'system', NOW()),

('원목 플랫폼베드 & 뉴트럴 침구 스테이징', 428000.00, 0, 'https://images.unsplash.com/photo-1617325247661-675ab4b64ae2?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'거친 스터코 톤 벽과 스트라이프 러그 위에 두꺼운 라이트 우드 플랫폼 프레임이 놓이고, 화이트·베이지·그레이 톤 베개와 이불, 바닥에는 벨벳 느낌 베이지 대형 쿠션과 그레이 직사각 쿠션이 기대어진 미니멀 침실 컷입니다.',
'• 침대 프레임·침구·쿠션 구성은 모델별(모델별)\n• 린넨·워시코튼 느낌 화이트 이불(모델별)\n• 패브릭 커버 세탁 방법 확인(모델별)\n• 매트리스 호환 사이즈 안내(모델별)\n• 배송·조립 옵션 별도(모델별)',
5, 34, 'system', NOW()),

('크림 이불 & 스트라이프 담요 & 화이트 롤 블라인드', 268000.00, 0, 'https://plus.unsplash.com/premium_photo-1684445035187-c4bc7c96bc5d?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'우드 스핀들 헤드보드와 크림 톤 이불, 중앙 원형 플리츠 쿠션, 발치 그레이·화이트 줄무늬 담요와 접어 올린 오프화이트 타월이 어우러지고, 양쪽 창에는 말려 올린 화이트 롤 블라인드가 보이는 밝은 북유럽풍 침실입니다.',
'• 이불커버·베개·쿠션·담요 세트(모델별)\n• 롤 블라인드 채광·암막 등급 확인(모델별)\n• 원목 침대·협탁은 연출 포함 여부 확인(모델별)\n• 세탁 라벨에 따른 관리(모델별)\n• 블라인드 설치 폭·높이 주문 제작(모델별)',
5, 34, 'system', NOW()),

('블라인드 빛무늬 화이트 호텔 침구 세트', 142000.00, 0, 'https://images.unsplash.com/photo-1601276174812-63280a55656e?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'연한 베이지 헤드 쪽으로 블라인드 줄 간격의 햇빛이 비스듬히 드리워지고, 도톰한 화이트 이불과 베개 커버의 자연스러운 주름이 강조된 호텔식 순백 침구 클로즈업입니다.',
'• 이불커버·베개커버 구성(모델별)\n• 순면·모달 등 소재(모델별)\n• 사계절용 두께 옵션(모델별)\n• 지퍼·단추 여부 확인(모델별)\n• 건조기 사용 가능 여부 라벨 확인(모델별)',
5, 34, 'system', NOW()),

('오션뷰 플리츠 화이트 쉬어 커튼', 78000.00, 0, 'https://plus.unsplash.com/premium_photo-1668073437337-5734dc7ef812?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'짙은 목재 창틀 사이로 하늘과 바다가 비치는 창에 세로 주름이 잡힌 얇은 화이트 쉬어가 걸려, 햇살과 풍경이 은은하게 스며드는 해안가 감성 인테리어 컷입니다.',
'• 시폰·보일 등 쉬어 소재(모델별)\n• 암막 아님·은은한 사생활 보호(모델별)\n• 나비주름·핀주름 등 주름 방식(모델별)\n• 가로·세로 사이즈 맞춤(모델별)\n• 세탁 시 섬유유연제 과다 사용 자제(모델별)',
5, 34, 'system', NOW()),

('바닥길이 화이트 시폰 롱 커튼', 92000.00, 0, 'https://images.unsplash.com/photo-1528822855841-e8bf3134cdc9?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 벽과 밝은 원목 바닥이 이어지는 거실형 대창에 바닥까지 늘어진 화이트 시폰이 걸려 있고, 햇빛이 천을 통과하며 긴 그림자 줄무늬를 만드는 미니멀 실내 컷입니다.',
'• 바닥까지 낙하하는 롱 길이(모델별)\n• 대형 창·발코니문 연출에 적합(모델별)\n• 속커튼·거실 겸용(모델별)\n• 커튼봉·레일 별매(모델별)\n• 다림·스팀 시 온도 주의(모델별)',
5, 34, 'system', NOW()),

('크림 린넨 느낌 차광 커튼', 118000.00, 0, 'https://images.unsplash.com/photo-1473252812967-d565c3607e28?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 프레임 창 양옆에 크림·오프화이트 톤의 자연 직조감 커튼이 걸려 부드러운 확산광을 만들고, 창턱 테라코타 화분의 그린 플랜트가 포인트로 보이는 도심 뷰 미니멀 창가입니다.',
'• 린넨·코튼 블렌드 등(모델별)\n• 완전 암막은 아닌 은은한 차광(모델별)\n• 솔리드 무지 컬러(모델별)\n• 주름 수·폭에 따른 주문(모델별)\n• 이염 방지 단독 세탁 권장(모델별)',
5, 34, 'system', NOW()),

('햇살 가득 화이트 쉬어 커튼', 68000.00, 0, 'https://images.unsplash.com/photo-1573507811472-909cd17e834d?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 쉬어 원단 가까이에서 찍힌 컷으로, 햇빛이 천을 비추며 나뭇가지 실루엣이 비치는 따뜻한 톤의 감성 채광 연출이 돋보입니다.',
'• 얇은 보일·시폰 타입(모델별)\n• 침실·거실 소형 창에도 활용(모델별)\n• 자연광 확산·은은한 시야 차단(모델별)\n• 세탁망 사용·약한 물세탁(모델별)\n• 실측 후 폭·길이 선택(모델별)',
5, 34, 'system', NOW()),

('라탄 왕골 야외 펜던트 스트링 조명', 138000.00, 0, 'https://plus.unsplash.com/premium_photo-1675027775616-e61937ca8201?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'해질녘 보케 배경 앞 나무 기둥과 굵은 로프에 원추형 라탄·왕골 짜임 갓이 연속으로 매달려 안쪽 전구가 은은한 주황빛을 비추는 테라스·야외 다이닝 무드 조명 컷입니다.',
'• 내외장 겸용 여부는 모델별(모델별)\n• 방수·전원 코드 규격 확인(모델별)\n• 갓 직경·줄 간격(모델별)\n• LED·백열 전구 소켓(모델별)\n• 설치 높이·고정 구조 전문 시공 권장(모델별)',
5, 35, 'system', NOW()),

('브러시드 코퍼 벨 펜던트 5구 세트', 248000.00, 0, 'https://images.unsplash.com/photo-1540932239986-30128078f3c5?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'연회색 배경 앞 검은 코드로 서로 다른 높이에 매달린 로즈골드·구리 톤 메탈 벨(물방울형) 갓 다섯 개가 켜져 하단에서 따뜻한 빛이 퍼지는 미니멀 주방·식탁용 펜던트 연출입니다.',
'• 5구 일괄 구성 또는 단품(모델별)\n• 코드 길이·높이 조절(모델별)\n• E26·E27 등 소켓(모델별)\n• 식탁·아일랜드 상부 설치(모델별)\n• 천장 고정 앙카 별도(모델별)',
5, 35, 'system', NOW()),

('헥사곤 터널 라인 LED 아키텍처 조명', 198000.00, 0, 'https://plus.unsplash.com/premium_photo-1664297899314-c9752762d449?q=80&w=1674&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'흰색 기하 프레임이 반복되는 긴 통로에 천장·벽 매립 라인 LED가 차가운 화이트 광을 내고, 광택 바닥에 빛 띠가 반사되어 미래지향 실내를 만드는 상업·전시 공간 컨셉 조명입니다.',
'• 매립형 라인 LED 시스템(모델별)\n• 길이·각도 커스텀 시공(모델별)\n• 드라이버·디밍 연동(모델별)\n• 상업·복도·갤러리 용도(모델별)\n• 전기 공사 법규 준수(모델별)',
5, 35, 'system', NOW()),

('브라스 돔 펜던트 & 할로 벽등 연출', 186000.00, 0, 'https://images.unsplash.com/photo-1568302621993-8700d490546a?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'콘크리트 질감 벽과 창밖 녹음이 보이는 카페형 공간에 브라스·골드 톤 와이드 돔 펜던트가 한 줄로 걸리고, 양측 벽에는 원형 할로 벽등이 보조 광을 내는 인더스트리얼 다이닝 조명 장면입니다.',
'• 펜던트 단품·벽등 별매(모델별)\n• 돔 직경·코드 색(모델별)\n• 밝은 전구색 권장(모델별)\n• 다이닝·카페 인테리어(모델별)\n• 벽면 배선 위치 사전 협의(모델별)',
5, 35, 'system', NOW()),

('딥 레드 복도 매립 라인 LED 그리드', 224000.00, 0, 'https://images.unsplash.com/photo-1586753513812-462ed2a82584?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'선명한 레드 벽·천장에 수직·수평 매립 LED 라인이 격자를 이루고 끝쪽 문을 향해 원근이 강조된 호텔 복도형 고대비 조명 연출입니다. 바닥은 짙은 카펫입니다.',
'• 색온도·밝기 패키지(모델별)\n• 벽체 홈파기·프로파일 규격(모델별)\n• 호텔·오피스 복도 응용(모델별)\n• 연속 길이 단위 주문(모델별)\n• 시공 시 화재·절연 규정 준수(모델별)',
5, 35, 'system', NOW()),

('큐브 타공 우드베이스 무드 탁상등', 92000.00, 0, 'https://images.unsplash.com/photo-1764430209144-c3368dcf9456?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 매트 타원형 갓에 입체 큐브 패턴이 뚫려 안쪽 전구색 광이 비치며 벽과 바닥에 기하 그림자를 드리우고, 하부는 작은 원형 원목 베이스로 받친 인테리어 무드 램프입니다.',
'• USB·콘센트 타입(모델별)\n• 밝기 단계·터치(모델별)\n• 침실·거실 코너(모델별)\n• LED 모듈 교체 가능 여부(모델별)\n• 장시간 사용 시 통풍 유지(모델별)',
5, 35, 'system', NOW()),

('LED 헤일로 원형 욕실 거울', 318000.00, 0, 'https://images.unsplash.com/photo-1572962186593-acc363cc17ac?q=80&w=1674&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'페니 타일 벽면에 직경이 같은 원형 거울 네 개가 나란히 박혀 가장자리 링에서 따뜻한 백색 LED가 비치고, 화이트 상판·크롬 수전과 어우러진 모던 욕실 화장대 연출입니다.',
'• 거울 직경·두께(모델별)\n• 방습 전원·IP 등급(모델별)\n• 디밍·색온도 옵션(모델별)\n• 단면 설치 기준(모델별)\n• 전기 연결 전문 시공(모델별)',
5, 35, 'system', NOW()),

('LED 매립 그레이 모듈 드레스룸 시스템', 1380000.00, 20, 'https://plus.unsplash.com/premium_photo-1674815329029-6473c5a1b70f?q=80&w=992&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'매트 그레이 마감의 모듈형 붙박이장에 이단 행거·상단 LED 바·모서리 오픈 큐브·무손잡이 서랍이 배치되고 화이트 셔츠·네이비 재킷이 걸려 있는 프리미엄 드레스룸 수납 컷입니다.',
'• 모듈 폭·높이 주문 제작(모델별)\n• LED 색온도·센서(모델별)\n• 행거·선반·서랍 비율 조합(모델별)\n• 의류는 연출용(모델별)\n• 시공·전기 배선 별도(모델별)',
5, 36, 'system', NOW()),

('내추럴 우드 벽걸이 욕실 수납장', 156000.00, 0, 'https://images.unsplash.com/photo-1595428774223-ef52624120d2?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'피치 톤 벽면에 연한 우드 그레인 도어가 양쪽에 달리고 가운데는 좁은 칸막이 오픈 선반 열 칸이 세로로 이어져 말아 올린 수건·병류가 정돈된 벽부착형 슬림 수납 유닛입니다.',
'• 벽면 앙카 고정(모델별)\n• 도어 내부 선반 깊이(모델별)\n• 욕실·드레스룸 겸용(모델별)\n• 방습 코팅 여부(모델별)\n• 실측 폭에 맞춘 주문(모델별)',
5, 36, 'system', NOW()),

('화이트 키친 오픈 수납 선반 유닛', 88500.00, 0, 'https://images.unsplash.com/photo-1653087881002-071d4c840f9e?q=80&w=1115&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'매트 화이트 오픈 선반 세 단에 코르크 마개 유리병, 스택 접시·볼, 골드림 글라스가 올라가고 상판에는 작은 화분이 늘어선 주방·팬트리 정리 연출입니다. 좌측에는 동일 시리즈 도어가 보입니다.',
'• 선반 단수·간격(모델별)\n• 양념병·식기는 연출(모델별)\n• 모듈장과 조합 가능(모델별)\n• 하중별 적재 주의(모델별)\n• 물기 많은 곳은 환기(모델별)',
5, 36, 'system', NOW()),

('미러형 핑크 2단 서랍 화장품 정리함', 42000.00, 0, 'https://images.unsplash.com/photo-1766242281507-dca096a98464?q=80&w=1065&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 본체에 핑크 도장 서랍 두 칸, 상단 립·네일 수납 칸, 중앙 소형 원형 거울이 달린 데스크탑 화장품 오거나이저가 퍼 스타일 매트 위에 놓이고 배경 니트와 스트링 라이트가 비치는 감성 연출입니다.',
'• 칸막이·서랍 구성(모델별)\n• 립스틱·브러시 등 수납(모델별)\n• 화장품은 미포함(모델별)\n• 물티슈로 가볍게 닦기(모델별)\n• 직사광선 장시간 피하기(모델별)',
5, 36, 'system', NOW()),

('투명 아크릴 데스크 정리함 & 행잉 파일 박스', 52000.00, 0, 'https://images.unsplash.com/photo-1768875813090-11426e70fc04?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'전경에는 투명 아크릴 행잉 폴더 박스에 청록·초록 폴더가 꽂혀 있고, 손에 들린 4칸 아크릴 데스크 오거나이저에 색별 펜·가위·스티키가 정리된 홈오피스·학습 책상 스타일링입니다.',
'• 4칸 펜홀더·파일 박스 세트 또는 단품(모델별)\n• 아크릴 두께·모서리 마감(모델별)\n• 문구류는 연출(모델별)\n• 긁힘 방지 부드러운 천 닦기(모델별)\n• 직사광선에 장시간 두지 않기(모델별)',
5, 36, 'system', NOW()),

('다크 월넛 브라스핸들 수납 캐비닛', 742000.00, 14, 'https://images.unsplash.com/photo-1600422086908-72be2c8f5f3f?q=80&w=986&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'짙은 브라운 목재 그레인 패널과 화이트 백패널 오픈 선반, 브라스 톤 매립 손잡이의 넓은 서랍·도어 면이 보이는 미니멀 거실·침실용 하이브리드 수납 가구 클로즈업입니다.',
'• 서랍·도어 비율(모델별)\n• 오픈 선반 단수(모델별)\n• 책·그릇 등 소품은 연출(모델별)\n• 바닥 수평 조절발(모델별)\n• 배송 조립 옵션(모델별)',
5, 36, 'system', NOW()),

('아치형 원목 오픈선반 & 크림 하부장', 1680000.00, 20, 'https://images.unsplash.com/photo-1647481045013-8efc5594369d?q=80&w=1028&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'벽면에 매립된 큰 반원 아치 안을 연한 오크 톤 선반과 중앙 칸막이가 가르고, 하부는 크림색 도어 네 짝에 소형 골드 노브가 달린 빌트인 디스플레이·수납장입니다. 도자기·액자·도서 등이 큐레이션되어 있습니다.',
'• 현장 시공·모듈 단품(모델별)\n• 아치 반경·선반 깊이(모델별)\n• 장식품은 미포함(모델별)\n• 하부 수납 용도(모델별)\n• 전문 시공 업체 권장(모델별)',
5, 36, 'system', NOW()),

('앰버 돔 펜던트 & 왕골 플랜터 홈 스테이징', 205000.00, 0, 'https://images.unsplash.com/photo-1633110187937-6e3b2f36dfca?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'밝은 오픈플랜 거실·주방에 원형 화이트 식탁, 미드센추리 스타일 셸 체어, 연한 우드 상부장과 세이지 하부장이 보이고 식탁 위 앰버·오렌지 톤 글라스 돔 펜던트와 전경 왕골·해초 짜임 대형 플랜터가 포인트로 잡힌 북유럽풍 인테리어 컷입니다.',
'• 펜던트·플랜터 단품 또는 세트(모델별)\n• 식탁·가전·식물은 연출(모델별)\n• 전구 소켓·코드 길이(모델별)\n• 플랜터 내경·이물 방수(모델별)\n• 천장 고정 부속 별매(모델별)',
5, 37, 'system', NOW()),

('골드 프레임 추상 액자 & 2단 바 카트 세트', 278000.00, 0, 'https://images.unsplash.com/photo-1618219944342-824e40a13285?q=80&w=927&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'세이지 그린 벽에 골드 프레임 추상 포스터가 걸리고 해머드 질감 골드 아워글라스 사이드 스툴, 이단 골드 메탈 바 카트에 보틀·쉐이커가 올라가며 몬스테라·골드 화분, 리프 모티프 장스탠드가 어우러진 럭셔리 코너 스테이징입니다.',
'• 액자·바카트·사이드 스툴 구성(모델별)\n• 액자 사이즈·유리(모델별)\n• 카트 캐스터·선반 재질(모델별)\n• 주류·식물은 연출(모델별)\n• 장스탠드는 별도 상품(모델별)',
5, 37, 'system', NOW()),

('원형 블랙 프레임 거울 & 라탄 펜던트 리빙', 168000.00, 0, 'https://images.unsplash.com/photo-1618219908412-a29a1bb7b86e?q=80&w=927&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'차콜 벽면 중앙에 슬림 블랙 링의 대형 원형 거울이 걸리고 라탄 종형 펜던트, 블랙 프레임 라탄 도어 사이드보드, 블랙 원추 베이스 우드 톱 사이드 테이블, 황마·주트 러그가 깔린 모던 내추럴 거실 연출입니다.',
'• 거울 직경·펜던트 세트(모델별)\n• 사이드보드·러그는 연출(모델별)\n• 거울 벽면 앙카(모델별)\n• 펜던트 전구별 색감(모델별)\n• 화분·화병은 별매(모델별)',
5, 37, 'system', NOW()),

('세이지 오벌 벽등 & 보헤미안 기하 러그 코너', 148000.00, 0, 'https://images.unsplash.com/photo-1616046229478-9901c5536a45?q=80&w=1480&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'세이지 그린 벽에 알약형 매트 피니시 벽등 한 쌍이 따뜻한 다운라이트를 내리고, 대형 원형 거울·크림 톤 기하·에스닉 패턴 러그, 줄무늬 왕골 바스켓, 크림 태슬 쿠션이 보헤미안 무드를 만드는 거실 코너입니다.',
'• 벽등 2구·러그 단품 조합(모델별)\n• 러그 사이즈·프린지(모델별)\n• 벽등 전기 시공(모델별)\n• 거울·콘솔은 연출(모델별)\n• LED·할로겐 소스(모델별)',
5, 37, 'system', NOW()),

('스모크 글라스 마른가지 디스플레이 화병', 38000.00, 0, 'https://images.unsplash.com/photo-1567016376408-0226e4d0c1ea?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'아치형 문틀 너머로 핑크 베이지 톤 거실이 보이고 전경에는 스모크 그레이 투명 글라스 병에 가느다란 마른 가지·갈대가 꽂혀 미니멀 감성을 주는 테이블·선반용 디스플레이 화병입니다.',
'• 화병 높이·입구 직경(모델별)\n• 가지는 세트 또는 화병 단품(모델별)\n• 유리 파손 주의(모델별)\n• 소파·테이블은 연출(모델별)\n• 물·이물 사용 시 건조 후 보관(모델별)',
5, 37, 'system', NOW()),

('빈티지 페인팅 우드 덕 인테리어 오브제', 36000.00, 0, 'https://images.unsplash.com/photo-1662842610230-1e08912e48ce?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'오프화이트 벽 앞에 방사형 짜임 라탄 트레이가 기대어 있고 중앙에는 연녹색 베이스에 풍화된 듯한 스플래터가 더해진 원목 조각 오리가 고개를 돌린 포즈로 놓이며 오른쪽에는 왕골 바스켓의 고사리가 보입니다.',
'• 원목·수공 느낌 마감(모델별)\n• 바구니·식물은 연출(모델별)\n• 실내 장식용(모델별)\n• 직사광선·습기 피해 보관(모델별)\n• 크기·무게(모델별)',
5, 37, 'system', NOW()),

('행잉 글라스 테라리움 & 로즈골드 에디슨 스탠드', 62000.00, 0, 'https://images.unsplash.com/photo-1674564074890-9a5367ee5747?q=80&w=960&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 코너에 와이어로 매단 물방울형 글라스 테라리움, 반고흐 「사이프러스가 있는 길」 프린트, 노출 전구의 로즈골드 곡선 탁상 스탠드, 질감 코랄 톤 쿠션이 함께 놓인 감성 데스크·협탁 코너 연출입니다.',
'• 테라리움·램프·프린트 구성(모델별)\n• 식물·모래는 연출 또는 포함(모델별)\n• 전구 E26·E14 등(모델별)\n• 저작권 프린트 정품 여부(모델별)\n• 쿠션 커버 별매(모델별)',
5, 37, 'system', NOW()),

('모노톤 기하 패턴 도자기 접시·수저 세트', 52000.00, 0, 'https://images.unsplash.com/photo-1738484708927-c1f45df0b56e?q=80&w=2067&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 마블 상판 위에 림 전체가 블랙 다이아몬드 패턴인 대형 원접시가 놓이고 그 위로 도트·다이아·지그재그 무늬가 다른 화이트 젓가락 세 쌍이 얹혀 있으며 좌측은 도트·다이아 손잡이 도자기 스푼, 우측은 체커·스트라이프·지그재그 볼 패턴의 미니 레들이 배치된 그래픽 식기 컷입니다.',
'• 접시·젓가락·스푼·레들 구성(모델별)\n• 도자기·포슬린(모델별)\n• 식기세척기 사용 가능 여부(모델별)\n• 전자레인지 금지 품목 확인(모델별)\n• 날카로운 모서리 파손 주의(모델별)',
5, 38, 'system', NOW()),

('내추럴 대나무 주방 조리 도구 세트', 28000.00, 0, 'https://plus.unsplash.com/premium_photo-1664007654191-75992ed6627b?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'오프화이트 리넨 느낌 천 위에 슬롯 뒤집개, 둥근 스푼, 중앙 홀 스푼, 와이드 틴 서빙 포크 등 연한 대나무·원목 톤 조리 도구가 겹쳐 놓인 탑뷰 에코 키친 스타일링입니다.',
'• 4~6종 세트(모델별)\n• 대나무·비치우드 등(모델별)\n• 식기세척기 상단랙·건조 권장(모델별)\n• 기름칠 유지 시 수명 연장(모델별)\n• 고온 프라이팬 장시간 방치 금지(모델별)',
5, 38, 'system', NOW()),

('논스틱 프라이팬 & 딥 소스팟 세트', 142000.00, 0, 'https://images.unsplash.com/photo-1518291344630-4857135fb581?q=80&w=2069&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'시안 단색 배경에 블랙 핸들 논스틱 프라이팬, 차콜 딥 냄비, 스파게티·토마토·바질·마늘·소금·후추가 함께 놓인 요리 준비 플랫레이로 주방 조리 도구를 강조한 컷입니다.',
'• 프라이팬 직경·냄비 용량(모델별)\n• 인덕션 호환(모델별)\n• 코팅 금속 도구 사용 금지(모델별)\n• 식재료는 연출(모델별)\n• 세척 후 완전 건조 보관(모델별)',
5, 38, 'system', NOW()),

('S훅 스테인리스 조리 도구 걸이 세트', 118000.00, 0, 'https://images.unsplash.com/photo-1586969593928-1c87c1f9c2ef?q=80&w=2073&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'브러시드 메탈 벽면 앞 가로 봉에 S자 후크로 박스 강판, 풍선 거품기, 슬롯 스키머, 국자, 슬롯 스푼, 소형 팬, 핸드 강판, 콘형 체, 집게 등이 걸린 상업용 키친 무드의 스테인리스 조리기구 연출입니다.',
'• 봉·후크·도구 구성(모델별)\n• 스테인리스 304 등(모델별)\n• 벽면 고정 앙카(모델별)\n• 식세기 가능 여부 손잡이별 상이(모델별)\n• 습기 후 건조로 수증 방지(모델별)',
5, 38, 'system', NOW()),

('글로시 화이트 세라믹 조리도구 홀더 세트', 36000.00, 0, 'https://plus.unsplash.com/premium_photo-1664007654112-a6a19547ae7b?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'밝은 그레이 벽과 화이트 린넨 위에 귀 손잡이가 달린 광택 화이트 실린더 항아리에 슬롯 포크, 홀 스푼, 플랫 뒤집개 등 연한 우드 조리 도구가 꽂혀 있는 미니멀 주방 카운터 연출입니다.',
'• 항아리·도구 세트 또는 단품(모델별)\n• 세라믹·도자(모델별)\n• 내열 온도(모델별)\n• 조리도구 종류·개수(모델별)\n• 충격 시 도자 파손 주의(모델별)',
5, 38, 'system', NOW()),

('올스틸 브러시드 나이프 3종 세트', 98000.00, 0, 'https://images.unsplash.com/photo-1690368675879-73bf1fb404d0?q=80&w=927&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'크림 톤 배경에 블레이드와 손잡이가 일체형인 브러시드 스테인리스 셰프나이프·슬라이서·톱니 에지 소형 나이프가 나란히 선 미니멀 프로 키친 나이프 구성 컷입니다.',
'• 3종 세트(모델별)\n• 스테인리스 강도·쉐프날 각도(모델별)\n• 세척 후 즉시 건조(모델별)\n• 도마 위 사용·보관 시 칼날 커버(모델별)\n• 어린이 손 닿지 않게 보관(모델별)',
5, 38, 'system', NOW()),

('매트 테라코타 식기 & 앰버 글라스 세트', 78000.00, 0, 'https://plus.unsplash.com/premium_photo-1714702845024-8afbbca28196?q=80&w=2069&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'붉은 기가 도는 질감 바탕 위에 매트 테라코타 톤 볼·플레이트 스택, 앰버 브라운 실린더 글라스, 짙은 우드 톤 오벌 스푼 세 개가 어우러진 오가닉 모던 다이닝 식기 스타일링입니다.',
'• 볼·접시·글라스·스푼 구성(모델별)\n• 세라믹·소다유리(모델별)\n• 전자레인지·오븐 사용 범위(모델별)\n• 스택 높이·용량(모델별)\n• 글라스 급온·급냉 주의(모델별)',
5, 38, 'system', NOW()),

('모노크롬 블랙 트레이 욕실 디스펜서 세트', 68000.00, 0, 'https://plus.unsplash.com/premium_photo-1678217547366-3156d075c867?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'화이트 마블 상판 위 짙은 그레이 벽을 배경으로 블랙 우드그레인 트레이에 플리츠 화이트 세라믹 솔루션 디스펜서, 릿지 블랙 양치컵과 대나무 칫솔, 면봉 유리병·크림 소형 단지·면도 패드·은색 틴이 올려진 미니멀 욕실 데스크 세팅입니다. 전경에는 유칼립투스 잎이 보입니다.',
'• 디스펜서·컵·트레이 구성(모델별)\n• 세라믹·유리·금속(모델별)\n• 리필용기 호환(모델별)\n• 면봉·크림 단지 내용물 별매(모델별)\n• 물기 마른 뒤 보관(모델별)',
5, 39, 'system', NOW()),

('골드캡 호텔형 미니 어메니티 보틀 4종', 24000.00, 0, 'https://plus.unsplash.com/premium_photo-1746731478711-c9d1583a9b5f?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'베이지 레지 위에 화이트 욕조 곡선이 보이는 가운데 골드 나사캡 미니 플라스틱 보틀 네 개가 늘어서 있고, 화이트 로션·베이지·앰버 투명 액체·전면 병의 흰 입욕 소금이 담긴 구성의 호텔·게스트 배스 어메니티 컷입니다.',
'• 30~50ml급 트래블 사이즈(모델별)\n• 샴푸·바디·컨디셔·바스솔트 등(모델별)\n• 내용물 성분은 라벨 확인(모델별)\n• 1회용·리필 정책(모델별)\n• 직사광선·고온 보관 피하기(모델별)',
5, 39, 'system', NOW()),

('포레스트 그린 글라스 & 로즈골드 탁상 거울 세트', 185000.00, 0, 'https://images.unsplash.com/photo-1731336478619-aaeb3ce74f25?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'베이지 마블 상·배면에 포레스트 그린 리브드 글라스 두 개가 거꾸로 놓이고, 동일 마블 비누접시·화이트 비누, 블랙 가죽 느낌 티슈 박스, 접은 화이트 핸드타월, 화이트 드로퍼 병 두 개, 링 라이트형 로즈골드 탁상 거울, 남색 패키지 프리미엄 치약 2입이 나란히 놓인 럭셔리 바스룸 카운터 연출입니다.',
'• 글라스·거울·티슈커버 구성(모델별)\n• 거울 LED 전원·밝기(모델별)\n• 화장품·치약 브랜드는 모델별 상이(모델별)\n• 마블 자연 패턴 차이(모델별)\n• 전기 콘센트 근처 설치(모델별)',
5, 39, 'system', NOW()),

('마블 패턴 디스펜서 & 골드림 미러 트레이 세트', 128000.00, 0, 'https://images.unsplash.com/photo-1625940119840-585d3495dc94?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'베이지 브라운 마블 타일과 화이트 세면대 위에 마블 패턴 사각 디스펜서, 골드 내부 화이트 세라믹 칫솔통, 골드 프레임 미러 바닥 트레이에 자수 포인트 화이트 롤 타월 세 개, 마블 패턴 뚜껑 수납함이 놓이고 상부에는 세로 리브 유리문 욕실장이 보입니다.',
'• 디스펜서·칫솔통·트레이·수납함(모델별)\n• 솔루션 리필 구멍(모델별)\n• 타월 자수 디자인(모델별)\n• 금속 부식 방지 건조(모델별)\n• 거울 트레이 긁힘 주의(모델별)',
5, 39, 'system', NOW()),

('블랙 와이어 바스켓 스파 배스 키트', 42000.00, 0, 'https://plus.unsplash.com/premium_photo-1679064286464-4aa8ff30b03a?q=80&w=987&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'오프화이트 배경에 블랙 메탈 와이어 바스켓 안에 화이트 매트 펌프 병, 천연 베이지 바다 스펀지, 크림 바 비누, 세이지 그린 프린지 워시클로스가 담긴 미니멀 스파 바스켓 구성 컷입니다.',
'• 바스켓·펌프병·스펀지·비누·타월 세트(모델별)\n• 천연 스펀지 개체 차이(모델별)\n• 액체 내용물 별매 가능(모델별)\n• 금속 부식 방지(모델별)\n• 세탁 라벨에 따른 타월 관리(모델별)',
5, 39, 'system', NOW()),

('리브 화이트 디스펜서 & 컷글라스 욕실 선반 세트', 95000.00, 0, 'https://images.unsplash.com/photo-1672370688943-08269b014bcb?q=80&w=1035&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'차콜 스페클 돌 상판과 앵글 볼 싱크, 라이트 우드 선반 위에 가로 리브 화이트 디스펜서, 컷 패턴 클리어 글라스 두 개, 실버캡 유리 보틀, 말린 화이트 핸드타월, 블랙 프레임 소형 확대 거울이 놓이고 배경 거울에는 벽돌과 글로브 2등 브라스 벽등이 비칩니다.',
'• 디스펜서·글라스·거울 중심 구성(모델별)\n• 우드 선반·돌 상판은 연출(모델별)\n• 유리·도자 파손 주의(모델별)\n• 펌프 부품 분리 세척(모델별)\n• 습한 환경 환기(모델별)',
5, 39, 'system', NOW()),

('앰버 글라스 보틀 & 그레이 칫솔통 세트', 36000.00, 0, 'https://plus.unsplash.com/premium_photo-1661421890783-3c5699340709?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'그레이 스톤 질감 타일 벽 앞 화이트 상판에 앰버 쇼트 글라스 보틀, 슬림 차콜 실린더 보틀, 모서리 둥근 매트 그레이 칫솔통과 대나무 핸들 블랙 브리슬 칫솔, 좌하단 화이트 튜브 치약 일부가 보이는 미니멀 워시스테이션 컷입니다.',
'• 보틀 2종·칫솔통·칫솔(모델별)\n• 앰버·다크 글라스 두께(모델별)\n• 튜브·액체 내용물 별매(모델별)\n• 금속 캡 산화 시 닦기(모델별)\n• 칫솔 건조 후 보관(모델별)',
5, 39, 'system', NOW()),

('스팽클 이중손잡이 볼 & 허브 스토리지 플랫레이', 62000.00, 0, 'https://plus.unsplash.com/premium_photo-1716476978325-585094057a4f?q=80&w=2069&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'연한 옐로 질감 바탕에 스팽클 도자 볼 세 개, 올리브오일 작은 볼, 건조 허브 유리병, 화이트 후추 클립병, 골드 와이어 바구니의 양파, 우드 뒤집개, 줄무늬 키친클로스가 어우러진 키친·팬트리 생활 소품 플랫레이입니다.',
'• 볼·저장병·바구니 구성(모델별)\n• 향신료·채소는 연출(모델별)\n• 오븐·전자레인지 사용 범위(모델별)\n• 유리·도자 파손 주의(모델별)\n• 밀폐 뚜껑 호환(모델별)',
5, 40, 'system', NOW()),

('빈티지 메탈·우드 핸들 조리 도구 세트', 88000.00, 0, 'https://plus.unsplash.com/premium_photo-1726743707863-e6a862465664?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'짙은 슬레이트 질감 상판 위에 셰프나이프, 주철 느낌 국자, 화이트 핸들 거품기, 미세망 체, 핸드 강판, 작은 칼 끝이 가로로 늘어선 빈티지 무드 금속 조리 도구 연출입니다.',
'• 5~6종 세트(모델별)\n• 카본·스테인리스 등(모델별)\n• 칼날 보호 커버 별매(모델별)\n• 세척 후 건조·기름칠(주철)(모델별)\n• 식세기 가능 여부 도구별 상이(모델별)',
5, 40, 'system', NOW()),

('파스텔 세라믹 계량컵 7종 세트', 45000.00, 0, 'https://plus.unsplash.com/premium_photo-1716196101576-db778a2e7e5f?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'크림 톤 테라조 상판 위에 핑크·민트·올리브·마젠타·틸·피치·번트오렌지 등 광택 세라믹 계량컵 일곱 개가 흩어져 있고 내부 림에 1 Cup/250ml 등 용량 인쇄가 보이는 컬러 베이킹·조리 소품 컷입니다.',
'• ml·cup 표기(모델별)\n• 세트 입수·색 구성(모델별)\n• 오븐 사용 온도(모델별)\n• 전자레인지 가능 여부(모델별)\n• 쌓기 시 긁힘 주의(모델별)',
5, 40, 'system', NOW()),

('파스텔 실리콘 키친 툴 5종', 38000.00, 0, 'https://plus.unsplash.com/premium_photo-1716051170366-31998e5b4d54?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'크래프트 톤 질감 배경에 더스티 블루 실리콘 뒤집개, 스카이블루 거품기, 민트 헤드·그레이 핸들 이중 뒤집개, 세이지 그린 거품기, 연그린 실리콘 바스팅 브러시가 나란히 놓이고 좌상단 광으로 그림자가 긴 미니멀 키친 툴 플랫레이입니다.',
'• 내열 실리콘·플라스틱 핸들(모델별)\n• 5종 세트(모델별)\n• 행잉 홀(모델별)\n• 식세기 상단랙 권장(모델별)\n• 직화·화구 직접 접촉 주의(모델별)',
5, 40, 'system', NOW()),

('리브 화이트 세라믹 전기포트 & 티타임 세트', 198000.00, 0, 'https://plus.unsplash.com/premium_photo-1715774735265-92a0305302ea?q=80&w=2069&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'골드 라인 마블 보드 위 가로 리브 무광·광택 화이트 세라믹 전기 주전자가 중심이고 우유저그, 딤플 볼에 갈색 설탕 큐브, 컵·소서와 우드 슈가 스틱, 배경 화이트 머그가 보이는 북유럽풍 티·커피 코너입니다.',
'• 용량·소비전력(모델별)\n• 자동 전원·보온(모델별)\n• 설탕·머그는 연출(모델별)\n• 무선 분리 베이스(모델별)\n• 단자 방수·건조(모델별)',
5, 40, 'system', NOW()),

('번개 탄화 무늬 원목 도마 & 스택 식기 세트', 72000.00, 0, 'https://images.unsplash.com/photo-1610701596295-4dc5d6289214?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'밝은 우드 테이블에 벽에 기댄 번개 탄화 무늬 직사각 도마와 링 핸들, 밀 이삭 액자, 스팽클 화이트 볼, 줄무늬 린넨이 담긴 다크 우드 트레이, 투톤 머그·소서, 얕은 우드 볼, 화이트 접시 스택과 페일 옐로 볼 스택이 놓인 내추럴 키친 스테이징입니다.',
'• 도마·식기·트레이 중심(모델별)\n• 밀 액자·천은 연출(모델별)\n• 도마 오일·왁스 관리(모델별)\n• 도자기 식세기 가능 여부(모델별)\n• 도마 생선·고기 후 소독(모델별)',
5, 40, 'system', NOW()),

('핸드메이드 원목 체 & 미니 나무통 디스플레이', 55000.00, 0, 'https://images.unsplash.com/photo-1731601815550-095f5679206b?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D',
'야외 마켓 느낌의 두꺼운 우드 선반에 금속 링 미니 나무통·디스펜서, 전통 인물 조각, 장식 마차, 걸이형 우드 머그, 큰 원형 나무 체가 쌓여 있고 일각에 화이트 펌프병이 비치는 러스틱 우드 생활 소품 진열 컷입니다.',
'• 체·미니통·머그 단품 또는 세트(모델별)\n• 펌프병은 연출(모델별)\n• 목재 건조·곰팡이 방지(모델별)\n• 체 망 청소 후 완전 건조(모델별)\n• 장식 오브제 포함 여부(모델별)',
5, 40, 'system', NOW());