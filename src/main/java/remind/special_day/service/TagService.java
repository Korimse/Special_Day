package remind.special_day.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import remind.special_day.domain.Board;
import remind.special_day.domain.BoardTag;
import remind.special_day.domain.Tag;
import remind.special_day.dto.board.BoardAddRequestDto;
import remind.special_day.repository.BoardTagRepository;
import remind.special_day.repository.TagRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;
    private final BoardTagRepository boardTagRepository;

    public List<Tag> findAllTag() {
        return tagRepository.findAll();
    }

    @Transactional
    public void addBoardTag(Board board, BoardAddRequestDto requestDto) {
        Set<String> tags = requestDto.getTags();

        tags.forEach(tag -> {
            BoardTag boardTag = new BoardTag();
            Tag savedTag = addTag(tag);
            boardTag.addBoard(board);
            boardTag.addTag(savedTag);
        });
    }

    public Tag addTag(String tag) {
        Tag savedTag = tagRepository.findByTag(tag).orElse(Tag.builder().tag(tag).build());
        Tag save = tagRepository.save(savedTag);
        return save;
    }

    public void deleteTag(Long tagId) {
        Tag tag = tagRepository.findById(tagId).get();
        boardTagRepository.deleteById(tagId);
        tagRepository.delete(tag);
    }

}
