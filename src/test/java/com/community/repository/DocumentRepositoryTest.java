package com.community.repository;

import com.community.domain.entity.*;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class DocumentRepositoryTest {

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private IndustryRepository industryRepository;

	@Autowired
	private CompaniesRepository companiesRepository;

	@Autowired
	private LikeItRepository likeItRepository;

	@Autowired
	private ViewershipRepository viewershipRepository;
	private MemberInterests interests;
	private Industry industry;
	private Member member;
	private Companies companies;
	private PasswordQuestion question;
	private MemberRole memberRole;
	private Board board;
	private Viewership viewership;
	private LikeIt likeIt;
	private String uuid;
	private Document document;

	@BeforeEach
	public void setUp() {
		industry = new Industry(UUID.randomUUID().toString(),
				"영림업",
				"임목을 생산하기 위하여 산림에서 나무를 심고, 가꾸고, 보호하는 산업활동을 말한다.");
		companies = new Companies(UUID.randomUUID().toString(), "1101111129497", "카카오");
		//권한 주입
		memberRole = new MemberRole(UUID.randomUUID().toString(), "USER");
		//질문 주입
		question = new PasswordQuestion(UUID.randomUUID().toString(), "좋아하는 음식은?");
		//회원 주입
		member = Member.builder()
				.memberId("4eed02a1-a986-4e6b-a48c-c67238797fab")
				.name("김요한")
				.nickname("요들레히")
				.email("test@com.com")
				.password("1234")
				.gender("M")
				.phone("010-1234-5678")
				.findPasswordAnswer("도도새")
				.passwordQuestion(question)
				.memberRole(memberRole)
				.build();
		uuid = UUID.randomUUID().toString();
		//viewership
		viewership = Viewership.builder().viewId(uuid).viewCount(0L).build();
		//liketItCount
		likeIt = LikeIt.builder().likeId(uuid).likeCount(0L).build();

		board = Board.builder()
				.boardId(UUID.randomUUID().toString())
				.boardName("자유")
				.industry(industry)
				.companies(companies)
				.memberAdmin(member)
				.memberUser(member)
				.approve(true)
				.build();

		document = Document.builder()
				.docId(uuid)
				.docTitle("제목")
				.docContent("내용")
				.docCreator(member)
				.board(board)
				.industry(industry)
				.build();
	}

	@AfterEach
	public void clear() {
		viewershipRepository.delete(viewership);
		likeItRepository.delete(likeIt);
		documentRepository.delete(document);
	}

	@Test
	public void save() {
        viewershipRepository.save(viewership);
        likeItRepository.save(likeIt);
		documentRepository.save(document);

		Assertions.assertEquals(document.getDocId(), documentRepository.findById(document.getDocId()).get().getDocId());
	}
}