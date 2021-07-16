package remind.special_day.dto.board;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import remind.special_day.domain.Album;
import remind.special_day.domain.Board;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class BoardAddRequestDto {

    @NotEmpty(message = "지역이름 태그 필수입니다.")
    private String area;

    @NotEmpty(message = "내용은 필수입니다.")
    private String content;

    @NotEmpty(message = "테그는 필수입니다.")
    private Set<String> tags;

    private List<MultipartFile> albums;

    public Board to(BoardAddRequestDto requestDto) {
        return Board.builder()
                .area(requestDto.getArea())
                .content(requestDto.getContent())
                .createDate(LocalDateTime.now())
                .build();
    }
}
