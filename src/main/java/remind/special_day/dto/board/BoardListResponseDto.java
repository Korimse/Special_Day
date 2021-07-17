package remind.special_day.dto.board;

import lombok.Builder;
import lombok.Getter;
import remind.special_day.domain.Board;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class BoardListResponseDto {

    private Long id;
    private String area;
    private LocalDateTime createDate;
    private String email;
    private int commentCount;
    private int albumCount;
    private List<String> boardTagList;

    public static BoardListResponseDto dto(Board board) {
        return BoardListResponseDto.builder()
                .id(board.getId())
                .area(board.getArea())
                .createDate(board.getCreateDate())
                .email(board.getMember().getEmail())
                .commentCount(board.getComments().size())
                .albumCount(board.getAlbums().size())
                .boardTagList(board.getBoardTags()
                        .stream()
                        .map(boardTag -> boardTag.getTag().getTag())
                        .collect(Collectors.toList()))
                .build();
    }



}
