package com.imageupload.example.components.boardServiceMethod;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.imageupload.example.entity.boardEntity;
import com.imageupload.example.entity.fileEntity;

import org.springframework.web.multipart.MultipartFile;

public class generateFile {

    private MultipartFile[] files;
    private boardEntity vo;

    private FileOutputStream fos;
    private final String root = "D:\\ImageUpload example\\src\\main\\downloads\\";
    private String tempName = "";
    private String extention;

    public generateFile(boardEntity vo, MultipartFile[] files) {
        this.vo = vo;
        this.files = files;
    }

    public List<fileEntity> generateFileVoList() {

        List<fileEntity> fileInfos = new ArrayList<>();

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

            fileEntity filevo = fileEntity.builder()
                            .tempName(tempName + extention)
                            .filePath(root + tempName + extention)
                            .originName(file.getOriginalFilename())
                            .fileSize(file.getSize()).board(vo).build();

            fileInfos.add(filevo);
        }

        return fileInfos;

    }

}
