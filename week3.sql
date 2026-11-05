-- 1. 팀 테이블을 't'로 생성
CREATE TABLE t (
    team_id SERIAL PRIMARY KEY, --serial은 postgreSQL에서 새 데이터를 추가 할 때 자동으로 1씩 증가(특수 데이터 타입)
    team_name VARCHAR(10) NOT NULL
);
select * from t;


-- 2. 회원 테이블을 'm'으로 생성
CREATE TABLE m (
    member_id SERIAL PRIMARY KEY, --serial은 postgreSQL에서 새 데이터를 추가 할 때 자동으로 1씩 증가(특수 데이터 타입)
    member_name VARCHAR(50) NOT NULL
);
select * from m;


-- 3. 팀원 연결 테이블을 'tm'으로 생성 (외래키 사용)
CREATE TABLE tm (
    team_member_id SERIAL PRIMARY KEY,
    team_id INT REFERENCES t(team_id),
    member_id INT REFERENCES m(member_id),
    position VARCHAR(50) NOT NULL
);
select * from tm;


-- 4. 팀 데이터 넣기 (1~6번 조)
INSERT INTO t (team_name) VALUES 
('1'), ('2'), ('3'), ('4'), ('5'), ('6');

-- 5. 멤버 데이터 넣기
INSERT INTO m (member_name) VALUES 
('김동건'), ('윤장현'), ('김효민'), ('장호진'), ('이종민'), ('양원야'), --(1~6)
('이승희'), ('구담욱'), ('박지성'), --(7~9)
('김민석'), ('타미르'), ('최익훈'); --(10~11)

-- 6. 팀과 멤버 연결하기
INSERT INTO tm (team_id, member_id, position) VALUES 
-- 3조 멤버
(3, 1, '조장'), --3조, 1번(김동건)
(3, 2, '조원'), --3조, 2번(윤장현)
(3, 3, '조원'), --3조, 3번(김효민)
(3, 4, '조원'), --3조, 4번(장호진)
(3, 5, '조원'), --3조, 5번(이종민)
(3, 6, '조원'), --3조, 6번(양원야)

-- 1조 멤버
(1, 7, '조장'), --1조, 7번(이승희)
(1, 8, '조원'), --1조, 8번(구담욱)
(1, 9, '조원'), --1조, 9번(박지성)

-- 2조 멤버
(2, 10, '조장'), --2조, 10번(김민석)
(2, 11, '조원'), --2조, 11번(타미르)
(2, 12, '조원'); --2조, 12번(최익훈)

-- 7. INNER JOIN으로 전체 결과 조회하기
SELECT 
    t.team_name AS "조 이름",
    m.member_name AS "팀원 이름",
    tm.position AS "역할"
FROM tm
INNER JOIN t ON tm.team_id = t.team_id
INNER JOIN m ON tm.member_id = m.member_id
WHERE t.team_name = '3'; -- 번호 1~6까지 바꾸면 조회 가능함

DROP TABLE IF EXISTS tm CASCADE;
DROP TABLE IF EXISTS m CASCADE;
DROP TABLE IF EXISTS t CASCADE;