package com.imageupload.example.imageupload;

import com.imageupload.example.JpaRepositories.BoardRepository;
import com.imageupload.example.Vo.boardVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.yml")
public class BoardInjectionTest{
    
    @Autowired
    private BoardRepository boardRepository;

    @Test
    void 게시글_작성_테스트(){

        for(int i = 0; i < 30; i ++){
        final boardVo vo = boardVo.builder()
                            .title("Test" + 1)
                            .category("일반")
                            .price("18000")
                            .descryption("설명" + 2)
                            .writer("123")
                            .location("location")
                            .build();

        boardRepository.save(vo);
        }
    }
}
