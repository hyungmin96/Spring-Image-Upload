package com.imageupload.example.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.imageupload.example.entity.BoardEntity;
import com.imageupload.example.entity.FileEntity;
import org.springframework.web.multipart.MultipartFile;

public class GenerateFile {

    private MultipartFile[] files;
    private BoardEntity vo;

    private FileOutputStream fos;
    private final String root = "D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\downloads\\";
    private String tempName = "";
    private String extention;

    public GenerateFile(BoardEntity vo, MultipartFile[] files) {
        this.vo = vo;
        this.files = files;
    }

    public List<FileEntity> generateFileVoList() {

        List<FileEntity> fileInfos = new ArrayList<>();

        for (MultipartFile file : files) {

            try {

                extention = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));

                tempName = UUID.randomUUID().toString();

                byte[] bytes = file.getBytes();

                fos = new FileOutputStream(new File(root + tempName + extention));

                fos.write(bytes);

                fos.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            }

            FileEntity filevo = FileEntity.builder()
                            .tempName(tempName + extention)
                            .filePath(root + tempName + extention)
                            .originName(file.getOriginalFilename())
                            .fileSize(file.getSize()).board(vo).build();

            fileInfos.add(filevo);
        }

        return fileInfos;

    }

}
