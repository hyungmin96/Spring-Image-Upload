package com.project.localbatter.dto;

import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupBoardFileEntity;
import com.project.localbatter.entity.GroupUserJoinEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class
GroupBoardDTO implements Serializable {

    private int page;
    private int display;

    private Long groupId;
    private Long boardId;
    private String type = "general";
    private Long userId;
    private String username;
    private String content;
    private String result;
    private int boardLike = 0;
    private MultipartFile[] board_img;
    private List<GroupBoardFileEntity> files = new ArrayList<>();

    public GroupBoardEntity.BoardType getType(){
        if(this.type.equals("notice"))
            return GroupBoardEntity.BoardType.notice;
        else if(this.type.equals("fixed"))
            return GroupBoardEntity.BoardType.fix;
        else
            return GroupBoardEntity.BoardType.general;
    }

    public void addFile(GroupBoardFileEntity file){
        files.add(file);
    }

    public GroupBoardEntity toEntity(GroupUserJoinEntity groupUserJoinEntity){
        return GroupBoardEntity.builder()
                .groupId(groupId)
                .content(content)
                .groupUserJoinEntity(groupUserJoinEntity)
                .boardLike(0)
                .type(getType())
                .files(files)
                .build();
    }
}