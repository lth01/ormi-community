package com.community.repository;

import com.community.domain.entity.*;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Assertions;
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

	@Test
	public void save() {
        String uuid = UUID.randomUUID().toString();
		Member member = memberRepository.findAll().get(0);
		Board board = boardRepository.findAll().get(0);
		Industry industry = industryRepository.findAll().get(0);
		Companies companies = companiesRepository.findAll().get(0);

        //viewership
        Viewership viewership = Viewership.builder().viewId(uuid).viewCount(0L).build();
        //liketItCount
        LikeIt likeIt = LikeIt.builder().likeId(uuid).likeCount(0L).build();


		Document document = Document.builder()
			.docId(uuid)
			.docTitle("제목")
			.docContent("내용")
			.docCreator(member)
			.board(board)
			.industry(industry)
			.build();

        viewershipRepository.save(viewership);
        likeItRepository.save(likeIt);
		documentRepository.save(document);

		Assertions.assertEquals(document.getDocId(), documentRepository.findAll().get(0).getDocId());
	}
}