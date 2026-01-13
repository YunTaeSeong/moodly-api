-- 상품 초기 데이터
-- 인기 상품 (category_id NULL)
INSERT INTO products (name, price, discount, image, description, details, category_id, sub_category_id, rating, review_count, created_by) VALUES
('스마트폰 케이스', 15000.00, 0, 'https://images.unsplash.com/photo-1601972602237-8c79241e468b?w=300&h=300&fit=crop', '고급스러운 스마트폰 케이스', NULL, NULL, NULL, 0.00, 0, 'system'),
('무선 이어폰', 89000.00, 0, 'https://images.unsplash.com/photo-1590658268037-6bf12165a8df?w=300&h=300&fit=crop', '프리미엄 무선 이어폰', NULL, NULL, NULL, 0.00, 0, 'system'),
('노트북 스탠드', 45000.00, 0, 'https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=300&h=300&fit=crop', '인체공학적 노트북 스탠드', NULL, NULL, NULL, 0.00, 0, 'system'),
('블루투스 스피커', 120000.00, 0, 'https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=300&h=300&fit=crop', '고음질 블루투스 스피커', NULL, NULL, NULL, 0.00, 0, 'system'),
('스마트 워치', 250000.00, 0, 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=300&h=300&fit=crop', '최신 스마트 워치', NULL, NULL, NULL, 0.00, 0, 'system'),
('무선 마우스', 35000.00, 0, 'https://images.unsplash.com/photo-1527814050087-3793815479db?w=300&h=300&fit=crop', '에르고노믹 무선 마우스', NULL, NULL, NULL, 0.00, 0, 'system'),
('USB-C 케이블', 12000.00, 0, 'https://images.unsplash.com/photo-1587825140708-dfaf72ae4b04?w=300&h=300&fit=crop', '고속 충전 USB-C 케이블', NULL, NULL, NULL, 0.00, 0, 'system'),
('태블릿 거치대', 28000.00, 0, 'https://images.unsplash.com/photo-1544244015-0df4b3ffc6b0?w=300&h=300&fit=crop', '조절 가능한 태블릿 거치대', NULL, NULL, NULL, 0.00, 0, 'system'),
('무선 키보드', 75000.00, 0, 'https://images.unsplash.com/photo-1587829741301-dc918b0c716f?w=300&h=300&fit=crop', '프리미엄 무선 키보드', NULL, NULL, NULL, 0.00, 0, 'system'),
('게이밍 마우스패드', 25000.00, 0, 'https://images.unsplash.com/photo-1612198188060-c7c2a3b66eae?w=300&h=300&fit=crop', '대형 게이밍 마우스패드', NULL, NULL, NULL, 0.00, 0, 'system'),
('웹캠 HD', 95000.00, 0, 'https://images.unsplash.com/photo-1587825143138-044a3fa50491?w=300&h=300&fit=crop', '고화질 웹캠', NULL, NULL, NULL, 0.00, 0, 'system'),
('스탠딩 데스크', 180000.00, 0, 'https://images.unsplash.com/photo-1524758631624-e2822e304c36?w=300&h=300&fit=crop', '전동 스탠딩 데스크', NULL, NULL, NULL, 0.00, 0, 'system'),
('USB 허브', 35000.00, 0, 'https://images.unsplash.com/photo-1625842268584-8f3296236761?w=300&h=300&fit=crop', '다중 포트 USB 허브', NULL, NULL, NULL, 0.00, 0, 'system'),
('모니터 스탠드', 55000.00, 0, 'https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=300&h=300&fit=crop', '조절 가능한 모니터 스탠드', NULL, NULL, NULL, 0.00, 0, 'system'),
('블루투스 어댑터', 18000.00, 0, 'https://images.unsplash.com/photo-1587825140708-dfaf72ae4b04?w=300&h=300&fit=crop', 'USB 블루투스 어댑터', NULL, NULL, NULL, 0.00, 0, 'system'),
('노이즈 캔슬링 이어폰', 150000.00, 0, 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=300&h=300&fit=crop', '프리미엄 노이즈 캔슬링 이어폰', NULL, NULL, NULL, 0.00, 0, 'system');

-- 의류 (category_id = 1)
INSERT INTO products (name, price, discount, image, description, details, category_id, sub_category_id, rating, review_count, created_by) VALUES
('데님 재킷', 89000.00, 0, 'https://images.unsplash.com/photo-1551028719-00167b16eac5?w=300&h=300&fit=crop', '클래식한 데님 재킷', NULL, 1, NULL, 0.00, 0, 'system'),
('후드티', 45000.00, 0, 'https://images.unsplash.com/photo-1556821840-3a63f95609a7?w=300&h=300&fit=crop', '편안한 후드티', NULL, 1, NULL, 0.00, 0, 'system'),
('슬랙스', 65000.00, 0, 'https://images.unsplash.com/photo-1473966968600-fa801b869a1a?w=300&h=300&fit=crop', '정장용 슬랙스', NULL, 1, NULL, 0.00, 0, 'system'),
('니트 스웨터', 75000.00, 0, 'https://images.unsplash.com/photo-1576566588028-4147f3842f27?w=300&h=300&fit=crop', '따뜻한 니트 스웨터', NULL, 1, NULL, 0.00, 0, 'system'),
('트레이닝복 세트', 120000.00, 0, 'https://images.unsplash.com/photo-1552902865-b72c031ac5ea?w=300&h=300&fit=crop', '편안한 트레이닝복', NULL, 1, NULL, 0.00, 0, 'system'),
('코트', 180000.00, 0, 'https://images.unsplash.com/photo-1539533018447-63fcce2678e3?w=300&h=300&fit=crop', '우아한 롱 코트', NULL, 1, NULL, 0.00, 0, 'system');

-- 가전용품 (category_id = 2)
INSERT INTO products (name, price, discount, image, description, details, category_id, sub_category_id, rating, review_count, created_by) VALUES
('스마트 TV', 1200000.00, 0, 'https://images.unsplash.com/photo-1593359677879-a4bb92f829d1?w=300&h=300&fit=crop', '4K UHD 스마트 TV', NULL, 2, NULL, 0.00, 0, 'system'),
('무선 청소기', 350000.00, 0, 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=300&h=300&fit=crop', '강력한 무선 청소기', NULL, 2, NULL, 0.00, 0, 'system'),
('에어프라이어', 180000.00, 0, 'https://images.unsplash.com/photo-1556910096-6f5e5ad8bcf4?w=300&h=300&fit=crop', '대용량 에어프라이어', NULL, 2, NULL, 0.00, 0, 'system'),
('로봇 청소기', 450000.00, 0, 'https://images.unsplash.com/photo-1558618047-3c8c76ca7d13?w=300&h=300&fit=crop', '스마트 로봇 청소기', NULL, 2, NULL, 0.00, 0, 'system'),
('공기청정기', 320000.00, 0, 'https://images.unsplash.com/photo-1585771724684-38269d6639fd?w=300&h=300&fit=crop', 'HEPA 필터 공기청정기', NULL, 2, NULL, 0.00, 0, 'system'),
('전자레인지', 150000.00, 0, 'https://images.unsplash.com/photo-1574269909862-7e1d70bb8078?w=300&h=300&fit=crop', '인버터 전자레인지', NULL, 2, NULL, 0.00, 0, 'system');

-- 푸드 (category_id = 3)
INSERT INTO products (name, price, discount, image, description, details, category_id, sub_category_id, rating, review_count, created_by) VALUES
('유기농 채소 세트', 25000.00, 0, 'https://images.unsplash.com/photo-1542838132-92c53300491e?w=300&h=300&fit=crop', '신선한 유기농 채소', NULL, 3, NULL, 0.00, 0, 'system'),
('프리미엄 한우', 85000.00, 0, 'https://images.unsplash.com/photo-1603048297172-c92544798d5a?w=300&h=300&fit=crop', '1등급 한우 세트', NULL, 3, NULL, 0.00, 0, 'system'),
('수입 치즈 세트', 45000.00, 0, 'https://images.unsplash.com/photo-1618164436249-4473940d1f5c?w=300&h=300&fit=crop', '프리미엄 수입 치즈', NULL, 3, NULL, 0.00, 0, 'system'),
('신선 과일 박스', 35000.00, 0, 'https://images.unsplash.com/photo-1619566636858-adf3ef46400b?w=300&h=300&fit=crop', '계절 과일 세트', NULL, 3, NULL, 0.00, 0, 'system'),
('건강 간식 세트', 28000.00, 0, 'https://images.unsplash.com/photo-1551024506-0bccd828d307?w=300&h=300&fit=crop', '다양한 건강 간식', NULL, 3, NULL, 0.00, 0, 'system'),
('유기농 꿀', 32000.00, 0, 'https://images.unsplash.com/photo-1587049352846-4a222e784d38?w=300&h=300&fit=crop', '천연 유기농 꿀', NULL, 3, NULL, 0.00, 0, 'system');

-- 뷰티 (category_id = 4)
INSERT INTO products (name, price, discount, image, description, details, category_id, sub_category_id, rating, review_count, created_by) VALUES
('스킨케어 세트', 120000.00, 0, 'https://images.unsplash.com/photo-1556228578-0d85b1a4d571?w=300&h=300&fit=crop', '프리미엄 스킨케어', NULL, 4, NULL, 0.00, 0, 'system'),
('립스틱 세트', 65000.00, 0, 'https://images.unsplash.com/photo-1583241805004-e54e0752c8e5?w=300&h=300&fit=crop', '다양한 컬러 립스틱', NULL, 4, NULL, 0.00, 0, 'system'),
('향수', 150000.00, 0, 'https://images.unsplash.com/photo-1541643600914-78b084683601?w=300&h=300&fit=crop', '프리미엄 향수', NULL, 4, NULL, 0.00, 0, 'system'),
('마스크팩 세트', 35000.00, 0, 'https://images.unsplash.com/photo-1556229010-6c3f2c9ca5f8?w=300&h=300&fit=crop', '수분 마스크팩 10매', NULL, 4, NULL, 0.00, 0, 'system'),
('선크림', 28000.00, 0, 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?w=300&h=300&fit=crop', '자외선 차단 선크림', NULL, 4, NULL, 0.00, 0, 'system'),
('에센스', 95000.00, 0, 'https://images.unsplash.com/photo-1616394584738-fc6e612e71b9?w=300&h=300&fit=crop', '안티에이징 에센스', NULL, 4, NULL, 0.00, 0, 'system');

-- 홈인테리어 (category_id = 5)
INSERT INTO products (name, price, discount, image, description, details, category_id, sub_category_id, rating, review_count, created_by) VALUES
('디퓨저 세트', 45000.00, 0, 'https://images.unsplash.com/photo-1606800054160-8e3c14e1a0b0?w=300&h=300&fit=crop', '아로마 디퓨저', NULL, 5, NULL, 0.00, 0, 'system'),
('쿠션 세트', 65000.00, 0, 'https://images.unsplash.com/photo-1584100936595-c0655cf3c8f0?w=300&h=300&fit=crop', '편안한 소파 쿠션', NULL, 5, NULL, 0.00, 0, 'system'),
('조명 램프', 85000.00, 0, 'https://images.unsplash.com/photo-1507473885765-e6ed057f782c?w=300&h=300&fit=crop', '인테리어 조명', NULL, 5, NULL, 0.00, 0, 'system'),
('식물 화분 세트', 55000.00, 0, 'https://images.unsplash.com/photo-1485955900006-10f4d324d411?w=300&h=300&fit=crop', '공기정화 식물', NULL, 5, NULL, 0.00, 0, 'system'),
('커튼', 120000.00, 0, 'https://images.unsplash.com/photo-1586023492125-27b2c045efd7?w=300&h=300&fit=crop', '블랙아웃 커튼', NULL, 5, NULL, 0.00, 0, 'system'),
('러그', 180000.00, 0, 'https://images.unsplash.com/photo-1556911220-bff31c812dba?w=300&h=300&fit=crop', '프리미엄 러그', NULL, 5, NULL, 0.00, 0, 'system');

