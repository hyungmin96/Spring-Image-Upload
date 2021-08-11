package com.project.localbatter.entity.Exchange;

import com.project.localbatter.entity.BaseTimeEntity;
import com.project.localbatter.entity.GroupBoardEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_group_exchange")
public class WriterExchangeEntity extends BaseTimeEntity {

    @Column(name = "writer_id")
    @Id @GeneratedValue
    private Long id;

    @Column(name = "writer_location")
    private String location;

    @Column(name = "writer_locationDetail")
    private String locationDetail;

    @Column(name = "writer_longitude") // 경도
    private String longitude;

    @Column(name = "writer_latitude") // 위도
    private String latitude;

    @Column(name = "writer_prefer_time")
    private String exchangeTime;

    @Column(name = "writer_residence")
    private String residence;

    @Column(name = "writer_detailAddr")
    private String detailAddr;

    @Column(name = "writer_buildingcode")
    private String buildingcode;

    @OneToOne(mappedBy = "writerExchangeEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private GroupBoardEntity boardId;

    @OneToMany(mappedBy = "writerExchangeEntity", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<WriterClientJoinEntity> writerClientJoinEntity;

}
