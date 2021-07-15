package remind.special_day.dto.board;

import lombok.Builder;
import lombok.Getter;
import remind.special_day.domain.Board;
import remind.special_day.domain.BoardTag;
import remind.special_day.domain.Member;
import remind.special_day.dto.tag.TagResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class BoardListResponseDto {

    private Long id;
    private String area;
    private String content;
    private LocalDateTime createDate;
    private Member member;
    private int commentCount;
    private int albumCount;
    private List<TagResponseDto> boardTagList;

    public static BoardListResponseDto dto(Board board) {
        return BoardListResponseDto.builder()
                .id(board.getId())
                .area(board.getArea())
                .content(board.getContent())
                .createDate(board.getCreateDate())
                .member(board.getMember())
                .commentCount(board.getComments().size())
                .albumCount(board.getAlbums().size())
                .boardTagList(board.getBoardTags()
                    .stream()
                    .map(tag -> new TagResponseDto(tag.getTag()))
                    .collect(Collectors.toList()))
                .build();
    }



}
