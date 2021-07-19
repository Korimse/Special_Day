package remind.special_day.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import remind.special_day.domain.Album;
import remind.special_day.domain.Board;
import remind.special_day.dto.album.AlbumResponseDto;
import remind.special_day.dto.board.BoardAddRequestDto;
import remind.special_day.dto.board.BoardUpdateRequestDto;
import remind.special_day.repository.AlbumRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;

    @Transactional
    public Board addBoardAlbums(BoardAddRequestDto requestDto) {
        Board board = requestDto.to(requestDto);
//        for(MultipartFile file : requestDto.getAlbums()){
//            Album album = Album.builder()
//                    .url("")
//                    .filename(file.getOriginalFilename())
//                    .build();
//            album.addBoard(board);
//        }
        for(int i = 0;i<3;i++){
            Album album = Album.builder()
                    .url("")
                    .filename("i"+i)
                    .build();
            album.addBoard(board);
        }
        return board;
    }

    /**
     * Update Album
     */
    public Board updateBoardAlbums(Board board, BoardUpdateRequestDto requestDto) {
        Set<Album> albums = board.getAlbums();
        albums.clear();

        for(MultipartFile file : requestDto.getAlbums()) {
            if(!albums.contains(file.getOriginalFilename())) {
                Album album = Album.builder()
                        .url("")
                        .filename(file.getOriginalFilename())
                        .build();
                album.addBoard(board);
            }
        }

        return board;
    }

}
