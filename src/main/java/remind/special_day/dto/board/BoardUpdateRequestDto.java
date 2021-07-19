package remind.special_day.dto.board;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Getter
public class BoardUpdateRequestDto {

    private String content;
    private Set<String> tags;

    private List<MultipartFile> albums;
}
