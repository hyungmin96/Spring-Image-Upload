package com.imageupload.example.Services;


import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import com.imageupload.example.Components.boardServiceMethod.createTime;
import com.imageupload.example.Components.boardServiceMethod.generateFile;
import com.imageupload.example.JpaRepositories.boardRepository;
import com.imageupload.example.JpaRepositories.fileRepository;
import com.imageupload.example.Vo.boardVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class boardService {

    @Autowired
    private boardRepository boardRep;

    @Autowired
    private fileRepository fileRep;

    public void boardWrite(boardVo vo, MultipartFile[] uploadFiles) {
        boardRep.save(vo);
        if (uploadFiles != null && uploadFiles.length > 0) {
            generateFile gen = new generateFile(vo, uploadFiles);
            fileRep.saveAll(gen.generateFileVoList());
        }
    }

    @Transactional
    public void boardUpdate(boardVo vo, MultipartFile[] uploadFiles, Integer[] deleteArr) {
        
        boardVo inputVo = boardRep.findById(vo.getId()).orElse(null);

        if (deleteArr != null) {
            for (Integer index : deleteArr) {
                if (index >= 0) {
                    Integer item = inputVo.getFiles().get(index).getFileid();
                    new File(inputVo.getFiles().get(index).getFilePath()).delete();
                    fileRep.deleteById(item);
                }
            }
        }

        if(inputVo != null){
            if (uploadFiles != null && uploadFiles.length > 0) {
                generateFile gen = new generateFile(vo, uploadFiles);
                fileRep.saveAll(gen.generateFileVoList());
            }

            vo.setCreateTime(inputVo.getCreateTime());
            boardRep.save(vo);
        }
    }

    public void boardDelete(int id) {
        boardVo vo = boardRep.findById(id).orElse(null);
        if (vo != null)
            vo.getFiles().forEach(element -> new File(element.getFilePath()).delete());

        boardRep.deleteById(id);
    }

    public List<boardVo> searchBoards(String search){
        List<boardVo> items = boardRep.findByTitleContainingOrderByIdDesc(search);
        return items;
    }

    public LinkedHashMap<String, List<boardVo>> getBoardList(){

        List<boardVo> boards = boardRep.findAllByOrderByIdDesc();

        List<boardVo> generalBoards = new ArrayList<>();
        List<boardVo> fastBoards = new ArrayList<>();

        LinkedHashMap<String, List<boardVo>> map = new LinkedHashMap<>();

        boards.forEach(action -> {
            try {
                action.setDisplayDate(new createTime(action.getCreateTime()).getTimeDiff());
                if(action.getCategory().equals("일반"))
                    generalBoards.add(action);
                else
                    fastBoards.add(action);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        map.put("general", generalBoards);
        map.put("fast", fastBoards);
        return map;

    }
    
    public boardVo findBoard(int id){
        return boardRep.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("정보없음");
        });
    }

}
