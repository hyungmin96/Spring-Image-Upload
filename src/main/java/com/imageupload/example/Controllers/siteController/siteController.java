package com.imageupload.example.Controllers.siteController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import com.imageupload.example.Services.boardService;
import com.imageupload.example.Vo.boardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class siteController {
    
    @Autowired
    private boardService BoardService;
    
    @GetMapping("/")
    public String home(Model model){
        List<boardVo> boards = BoardService.getBoardList();

        model.addAttribute("board", boards);
        return "/board/articleList";
    }

    @GetMapping("/board/article/write")
    public String articleWrite(){
        return "/board/articleWrite";
    }

    @GetMapping("/board/article/{id}")
    public String viewBoard(Model model, @PathVariable int id) throws IOException{

        boardVo board = BoardService.findBoard(id);

        model.addAttribute("board", board);

        model.addAttribute("img", board.getFiles());

        return "/board/article";
    }

}