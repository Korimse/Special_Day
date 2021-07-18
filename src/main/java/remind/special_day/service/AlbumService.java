package remind.special_day.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import remind.special_day.domain.Album;
import remind.special_day.domain.Board;
import remind.special_day.dto.board.BoardAddRequestDto;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlbumService {

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


    /**
     * Delete Album
     */


    /**
     * Album 조회 by board_id
     */


    /**
     * Album 조회 by member_id
     */
}
