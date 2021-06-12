package com.imageupload.example.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinRoomEntity{
    
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "targetId")
    private UserEntity target;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity userVo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "createRoomId")
    private RoomEntity roomEntity;


    public String toString(){
        return this.id + " | " + 
                this.roomEntity.getId() + " | " + 
                this.target.getId() + " | " + 
                this.userVo.getId() + " | ";
    }

}