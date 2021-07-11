package com.imageupload.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BuyingChatEntity {

    @Id @GeneratedValue
    private Long id;
    private String message;
    private String sender;
    private String type;
    private String profilePath;
    private String localDate;

    @ManyToOne
    @JoinColumn(name = "chatRoomId")
    @JsonIgnore
    private BuyingChatRoomEntity buyingChatRoomEntity;
}
