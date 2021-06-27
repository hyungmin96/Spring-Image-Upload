package com.imageupload.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEntity {
    
    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId")
    private UserEntity userId;

    @Column(columnDefinition = "integer default 0")
    private int notification;

    @Column(columnDefinition = "integer default 0")
    private int transaction;

    @Column(columnDefinition = "integer default 0")
    private int chat;

}