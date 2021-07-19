package remind.special_day.dto.album;

import lombok.Builder;
import lombok.Getter;
import remind.special_day.domain.Album;

@Getter
@Builder
public class AlbumResponseDto {

    private Long boardId;
    private String url;
    private String filename;

    public static AlbumResponseDto dto(Album album) {
        return AlbumResponseDto.builder()
                .boardId(album.getBoard().getId())
                .url(album.getUrl())
                .filename(album.getFilename())
                .build();
    }
}
