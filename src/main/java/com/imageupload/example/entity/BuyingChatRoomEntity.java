package com.imageupload.example.entity;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuyingChatRoomEntity {
    
    @Id @GeneratedValue
    @Column(name = "roomId")
    private Long id;

    private String title;
    private String description;
    private String roomTitle;
    private String roomDate;
    private int currentUsers;
    private int limitUsers;
    private int price;
    private String owner;

    @CreationTimestamp
    private Timestamp regTime;

    @OneToMany(mappedBy = "buyingChatRoomEntity")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<BuyingFileEntity> files;

    @OneToMany(mappedBy = "roomId")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<BuyingChatEntity> chats;

    @OneToMany(mappedBy = "buyingChatRoomEntity")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private List<BuyingUsersEntity> users;
}
