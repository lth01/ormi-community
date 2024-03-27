-- 회원 테이블
CREATE TABLE IF NOT EXISTS member (
    member_id	            VARCHAR(36)	PRIMARY KEY,
    authority_id	        VARCHAR(36),
    name	                VARCHAR(20) NOT NULL,
    nickname	            VARCHAR(20) NOT NULL,
    email	                VARCHAR(100) NOT NULL UNIQUE,
    password	            VARCHAR(255) NOT NULL,
    gender	                VARCHAR(1),
    phone	                VARCHAR(20) NOT NULL,
    password_question_id	VARCHAR(36) NOT NULL,
    find_password_answer	VARCHAR(100) NOT NULL, -- 수정
    withdrawal              BOOLEAN NOT NULL DEFAULT FALSE,
    create_date             TIMESTAMP DEFAULT NOW(),
    mod_date                TIMESTAMP DEFAULT NOW()
);

-- 권한 테이블
CREATE TABLE IF NOT EXISTS authorities (
    authority_id	        VARCHAR(36)	PRIMARY KEY,
    authority_name	        VARCHAR(100) NOT NULL
);

-- 패스워드 질문 테이블
CREATE TABLE IF NOT EXISTS password_question (
    password_question_id	VARCHAR(36)	PRIMARY KEY,
    question	            VARCHAR(200) NOT NULL
);


-- 회원 관심 업종 테이블
CREATE TABLE IF NOT EXISTS member_interests (
    interests_id	        VARCHAR(36)	PRIMARY KEY,
    industry_id	            VARCHAR(36) NOT NULL,
    member_id	            VARCHAR(36) NOT NULL
);

-- 회원 인증 기업 테이블 (보류)
-- CREATE TABLE certification (
--     com_certification_id	VARCHAR(36)	PRIMARY KEY,
--     member_id	VARCHAR(36) ,
--     com_id	VARCHAR(36) ,
--     com_certification	boolean	,
--     authority_id	VARCHAR(36)
-- );

-- 기업 테이블
CREATE TABLE IF NOT EXISTS companies (
    com_id	                VARCHAR(36)	PRIMARY KEY,
    reg_num	                INT UNIQUE NOT NULL,
    com_name	            VARCHAR(20) NOT NULL
);

-- 업종 테이블(산업 테이블)
CREATE TABLE IF NOT EXISTS industry (
    industry_id	            VARCHAR(36)	PRIMARY KEY,
    industry_name	        VARCHAR(100) NOT NULL,
    industry_description	VARCHAR(255)
);

-- 리뷰 테이블
CREATE TABLE IF NOT EXISTS reviews (
    review_id	            VARCHAR(36)	PRIMARY KEY,
    com_id	                VARCHAR(36) NOT NULL,
    member_id	            VARCHAR(36) NOT NULL,
    review_title	        VARCHAR(100) NOT NULL,
    review_content	        VARCHAR(200) NOT NULL,
    create_date	            TIMESTAMP   DEFAULT NOW(),
    mod_date	            TIMESTAMP   DEFAULT NOW(),
    mod_check	            boolean NOT NULL DEFAULT FALSE, -- NOT NULL 추가 해야 됨
    authority_id	        VARCHAR(36) NOT NULL
);

-- 리뷰 항목별 점수 테이블
CREATE TABLE IF NOT EXISTS review_score (
    score_id	            VARCHAR(36)	PRIMARY KEY,
    review_id	            VARCHAR(36) NOT NULL,
    review_Categories_id	VARCHAR(36) NOT NULL,
    authority_id	        VARCHAR(36) NOT NULL,
    score	                INT NOT NULL CHECK (score > 0 AND score <= 5)
);

-- 리뷰 항목 테이블
CREATE TABLE IF NOT EXISTS Review_Categories (
    review_Categories_id	VARCHAR(36)	PRIMARY KEY,
    category_name	        VARCHAR(100) NOT NULL
    );

-- 게시판 테이블
CREATE TABLE IF NOT EXISTS board (
    commu_board_id	        VARCHAR(36)	PRIMARY KEY,
    industry_id	            VARCHAR(36) NOT NULL,
    com_id	                VARCHAR(36) NOT NULL,
    commu_board_create_date	TIMESTAMP DEFAULT NOW(),
    commu_board_creator	    VARCHAR(36) NOT NULL, -- 운영진
    member_id	            VARCHAR(36) NOT NULL -- 생성요청자 회원ID
);

-- 게시글 테이블
CREATE TABLE IF NOT EXISTS Document (
    doc_id	                VARCHAR(36)	PRIMARY KEY,
    commu_board_id	        VARCHAR(36) NOT NULL,
    industry_id	            VARCHAR(36) NOT NULL,
    com_id	                VARCHAR(36) NOT NULL,
    doc_name	            VARCHAR(100) NOT NULL,
    doc_content	            TEXT NOT NULL,
    doc_writer	            VARCHAR(20)	NOT NULL,
    doc_like	            VARCHAR(36) ,
    doc_view_count	        VARCHAR(36)	,
    doc_create_date	        TIMESTAMP	DEFAULT NOW(),
    doc_creator	            VARCHAR(36)	NOT NULL, --작성자
    doc_mod_date	        TIMESTAMP	DEFAULT NOW(),
    doc_modifier	        VARCHAR(36)	,
    doc_visible	            BOOLEAN	NOT NULL DEFAULT FALSE, -- NOT NULL 추가 해야 됨
    report_key	            VARCHAR(36)
);

-- 댓글 테이블
CREATE TABLE IF NOT EXISTS comment (
    comment_id	            VARCHAR(36)	PRIMARY KEY,
    doc_id	                VARCHAR(36) NOT NULL,
    comment_password	    VARCHAR(30)	,
    like_id         	    VARCHAR(36)	,
    comment_creator_ip	    CIDR	NOT NULL,
    comment_content	        VARCHAR(200) NOT NULL,
    comment_create_date	    TIMESTAMP	DEFAULT NOW(),
    comment_creator	        VARCHAR(36)	NULL,
    comment_mod_date	    TIMESTAMP	DEFAULT NOW(),
    comment_modifier	    VARCHAR(36)	NULL,
    comment_visible	        BOOLEAN	NOT NULL DEFAULT FALSE, -- NOT NULL 추가 해야 됨
    report_id	            VARCHAR(36) ,
    industry_id	            VARCHAR(36) NOT NULL,
    com_id	                VARCHAR(36) NOT NULL
);

-- 좋아요 테이블
CREATE TABLE IF NOT EXISTS like_it (
    like_id	                VARCHAR(36)	PRIMARY KEY,
    like_count	            BIGINT  DEFAULT 0 -- NOT NULL 추가 해야 됨
);

-- 조회수 테이블
CREATE TABLE IF NOT EXISTS viewership (
    view_id	                VARCHAR(36)	PRIMARY KEY,
    view_count	            BIGINT DEFAULT 0 -- NOT NULL 추가 해야 됨
);

-- 신고 테이블
CREATE TABLE IF NOT EXISTS report (
    report_id	            VARCHAR(36)	PRIMARY KEY,
    member_id	            VARCHAR(36)	NOT NULL,
    reporter_ip	            CIDR NOT NULL,
    report_date	            TIMESTAMP DEFAULT NOW(),
    report_judge	        BOOLEAN	NOT NULL DEFAULT FALSE, -- NOT NULL 추가 해야 됨
    report_visible	        BOOLEAN	NOT NULL DEFAULT FALSE, -- NOT NULL 추가 해야 됨
    report_content	        VARCHAR(200) NOT NULL
);