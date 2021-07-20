package remind.special_day.service;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import remind.special_day.domain.Album;
import remind.special_day.domain.Board;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class AlbumServiceTest {

    @Autowired
    AlbumService albumService;

    @Test
    void albumupdatetest() throws IOException {
        MultipartFile photo1 = new MockMultipartFile("pic1.jpeg", "pic1", "jpeg", new FileInputStream(new File("/Users/isiheon/MyWork/special_day/src/test/resources/picture/pic1.jpeg")));
        MultipartFile photo2 = new MockMultipartFile("pic2.jpeg", "pic2", "jpeg", new FileInputStream(new File("/Users/isiheon/MyWork/special_day/src/test/resources/picture/pic2.jpeg")));
        MultipartFile photo3 = new MockMultipartFile("pic3.jpeg","pic3", "jpeg", new FileInputStream(new File("/Users/isiheon/MyWork/special_day/src/test/resources/picture/pic3.jpeg")));

        Board board = Board.builder()
                .area("incheon")
                .content("hi")
                .createDate(LocalDateTime.now())
                .build();

        List<MultipartFile> filelist = new ArrayList<>();
        filelist.add(photo1);
        filelist.add(photo3);

        for(MultipartFile file : filelist) {
            if(!board.getAlbums().contains(file.getOriginalFilename())) {
                Album album = Album.builder()
                        .url("")
                        .filename(file.getOriginalFilename())
                        .build();
                album.addBoard(board);
            }
        }
        filelist.remove(photo3);
        filelist.add(photo2);

        board.getAlbums().clear();

        for(MultipartFile file : filelist) {
            if(!board.getAlbums().contains(file.getOriginalFilename())) {
                Album album = Album.builder()
                        .url("")
                        .filename(file.getOriginalFilename())
                        .build();
                album.addBoard(board);
            }
        }
        System.out.println(board.getAlbums().stream().map(album -> album.getFilename()).collect(Collectors.toList()));

    }

}