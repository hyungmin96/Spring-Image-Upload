package com.imageupload.example.repositories;

import java.util.List;

import com.imageupload.example.entity.UserJoinRoomEntity;
import com.imageupload.example.models.UserVo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<UserJoinRoomEntity, Long>{
    public List<UserJoinRoomEntity> findAllByuserVoOrTarget(UserVo userVo, UserVo target);
}
