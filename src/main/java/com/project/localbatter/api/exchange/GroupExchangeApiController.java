package com.project.localbatter.api.exchange;

import com.project.localbatter.dto.Group.GroupBoardDTO;
import com.project.localbatter.dto.TransactionDTO;
import com.project.localbatter.dto.exchangeDTO.ClientExchangeDTO;
import com.project.localbatter.dto.exchangeDTO.LocalBatterServiceDTO;
import com.project.localbatter.dto.exchangeDTO.ReviewDTO;
import com.project.localbatter.entity.Exchange.ClientExchangeEntity;
import com.project.localbatter.entity.Exchange.LocalBatterServiceEntity;
import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import com.project.localbatter.entity.Exchange.WriterExchangeEntity;
import com.project.localbatter.entity.Exchange.WriterExchangeEntity.ExchageOnOff;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupBoardFileEntity;
import com.project.localbatter.services.Exchange.ExchangeService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchange")
public class GroupExchangeApiController {

    private final ExchangeService groupExchangeService;

    @PostMapping("/write/review")
    public ReviewDTO writeReview(ReviewDTO reviewDTO){
        return groupExchangeService.writeReview(reviewDTO);
    }

    @PostMapping("/confirm")
    public void confirmExchange(@RequestParam("userId") Long userId, @RequestParam("exchangeId") Long exchangeId){
        groupExchangeService.confirmExchange(userId, exchangeId);
    }

    @GetMapping("/get_service_list")
    public List<LocalBatterServiceEntity> getServiceList(){
        return groupExchangeService.getServiceList();
    }

    @PostMapping("/delete/service")
    public ResponseEntity<String> deleteLocalBatterService(LocalBatterServiceDTO localBatterServiceDTO){
        return groupExchangeService.deleteLocalBatterService(localBatterServiceDTO);
    }

    @PostMapping("/save/service")
    public LocalBatterServiceDTO saveLocalBatterService(LocalBatterServiceDTO localBatterServiceDTO){
        return groupExchangeService.saveLocalBatterService(localBatterServiceDTO);
    }

    @GetMapping("/view/client/exchange")
    public ResponseClientDTO getClientRequestExchangeInfo(Long exchangeId){
        return groupExchangeService.getClientRequestExchangeInfo(exchangeId);
    }

    @GetMapping("/view/client_writer_exchange")
    public ResponseClientAndWriterBoard getClientWriterExchange(TransactionDTO transactionDTO){
        return groupExchangeService.getClientAndWriterBoard(transactionDTO);
    }

    @GetMapping("/view/board/client_reqeust_list")
    public Page<ResponseClientRequestDTO> getBoardClientReqeust(ClientExchangeDTO clientExchangeDTO){
        PageRequest page = PageRequest.of(clientExchangeDTO.getPage(), clientExchangeDTO.getDisplay());
        return groupExchangeService.getBoardClientRequestList(clientExchangeDTO, page);
    }

    @GetMapping("/my/request_list")
    public Page<ResponseRequestListDTO> getRequestList(TransactionDTO transactionDTO){
        Pageable page = PageRequest.of(transactionDTO.getPage(), transactionDTO.getDisplay());
        return groupExchangeService.getRequestList(transactionDTO, page);
    }

    @PostMapping("/cancel/request")
    public void cancelRequestEntity(ClientExchangeDTO clientExchangeDTO){
        groupExchangeService.cancelRequest(clientExchangeDTO);
    }

    @PostMapping("/select/request")
    public ResponseRequestExchangeDTO selectRequestEntity(TransactionDTO transactionDTO){
        return groupExchangeService.accpetClientsRequest(transactionDTO);
    }

    @GetMapping("/my/get_write_list")
    public Page<ResponseWrtierExchangeDTO> getWriterBoards(TransactionDTO transactionDTO) {
        Pageable page = PageRequest.of(transactionDTO.getPage(), transactionDTO.getDisplay());
        return groupExchangeService.getWriterBoards(transactionDTO, page);
    }

    @GetMapping("/my/get_complete_list")
    public Page<ResponseWrtierExchangeDTO> getCompleteBoards(TransactionDTO transactionDTO) {
        Pageable page = PageRequest.of(transactionDTO.getPage(), transactionDTO.getDisplay());
        return groupExchangeService.getCompleteBoards(transactionDTO, page);
    }

    @PostMapping("/client/post")
    public ResponseClientDTO clientPost(ClientExchangeDTO clientExchangeDTO){
        ClientExchangeEntity clientExchangeEntity = groupExchangeService.clientPost(clientExchangeDTO);
        return new ResponseClientDTO(clientExchangeEntity);
    }

    @GetMapping("/view/board")
    public ResponseExchagneInfo getExchangeInfo(GroupBoardDTO groupBoardDTO){
        return new ResponseExchagneInfo(groupExchangeService.getExchangeInfo(groupBoardDTO));
    }

    @Setter @Getter
    public static class ResponseClientAndWriterBoard{

        private Long writerId;
        private Long clientid; // client user id
        private Long boardId; // writer's board id
        private Long clientExchangeId; // client exchange board id

        private String title; // board variable
        private String content;
        private String longtitude;
        private String latitude;
        private String price;
        private String preferTime;

        private String writerUsername;
        private String writerProfile;
        private String clientUsername;
        private String clientProfile;

        private WriterExchangeEntity writerExchangeEntity;
        private ClientExchangeEntity clientExchangeEntity;

        private List<String> writerImages;
        private List<String> clientImages;

        public ResponseClientAndWriterBoard(Long writerId, Long clientid, GroupBoardEntity board, Long clientExchangeId, String writerUsername, String writerProfile, String clientUsername, String clientProfile, WriterExchangeEntity writerExchangeEntity, ClientExchangeEntity clientExchangeEntity) {
            this.writerId = writerId;
            this.clientid = clientid;
            this.clientExchangeId = clientExchangeId;
            this.writerUsername = writerUsername;
            this.writerProfile = writerProfile;
            this.clientUsername = clientUsername;
            this.clientProfile = clientProfile;
            this.writerExchangeEntity = writerExchangeEntity;
            this.clientExchangeEntity = clientExchangeEntity;
            this.title = board.getTitle();
            this.content = board.getContent();
            this.longtitude = writerExchangeEntity.getLongitude();
            this.latitude = writerExchangeEntity.getLatitude();
            this.price = writerExchangeEntity.getPrice();
            this.preferTime = writerExchangeEntity.getPreferTime();
            this.writerImages = board.getFiles().stream().map(item -> item.getName()).collect(Collectors.toList());
            this.clientImages = clientExchangeEntity.getFiles().stream().map(item -> item.getName()).collect(Collectors.toList());
        }
    }

    @Setter @Getter
    public static class ResponseClientRequestDTO{

        private Long userId;
        private String username;
        private String userProfile;
        private String filename;
        private Long exchangeId;
        private ClientExchangeEntity clientExchange;

        public ResponseClientRequestDTO(Long clientId, String username, String userProfile, String filename, Long exchangeId, ClientExchangeEntity clientExchange) {
            this.userId = clientId;
            this.username = username;
            this.userProfile = userProfile;
            this.filename = filename;
            this.exchangeId = exchangeId;
            this.clientExchange = clientExchange;
        }
    }

    @Setter @Getter
    public static class ResponseWrtierExchangeDTO{
        private Long writerId; // writer user id
        private Long clientId; // client user id
        private Long writerExchangeId; // writerExchange Entity id
        private Long boardId;
        private int requestCount;
        private String title;
        private String content;
        private LocalDateTime regTime;
        private String thumbnail;
        private WriterExchangeEntity.exchangeStatus status;
        private Long writerClientJoinId;

        private Long isReviewWrite;
        private String reviewWriterUsername;
        private String reviewWriterProfile;
        private String reviewReceiveUsername;
        private String reviewReceiveProfile;
    }

    @Setter @Getter
    public static class ResponseRequestListDTO{
        private Long clientId; // user Id
        private Long clientExchangeId; // client's request board id
        private String clientUsername;
        private String clientProfile;
        private Long writerId;
        private Long boardId;
        private String title;
        private String content;
        private WriterClientJoinEntity.status status;
        private LocalDateTime regTime;
        private String thumbnail;

        public ResponseRequestListDTO(Long clientId, Long clientExchangeId, String clientUsername, String clientProfile, Long writerId, Long boardId, String title, String content, WriterClientJoinEntity.status status, LocalDateTime regTime, String thumbnail) {
            this.clientId = clientId;
            this.clientExchangeId = clientExchangeId;
            this.clientUsername = clientUsername;
            this.clientProfile = clientProfile;
            this.writerId = writerId;
            this.boardId = boardId;
            this.title = title;
            this.content = content;
            this.status = status;
            this.regTime = regTime;
            this.thumbnail = thumbnail;
        }
    }

    @Setter @Getter
    public static class ResponseExchagneInfo{
        private Long id;
        private String username;
        private String profilePath;
        private LocalDateTime regTime;
        private Long boardId;
        private String title;
        private String content;
        private List<String> boardFiles;

        // 게시글 거래위치 DTO
        private String price;
        private ExchageOnOff exchangeOn;
        private String location;
        private String locationDetail;
        private String longitude;
        private String latitude;
        private String preferTime;

        public ResponseExchagneInfo(GroupBoardEntity entity) {
            this.id = entity.getGroupUserJoinEntity().getUser().getId();
            this.username = entity.getGroupUserJoinEntity().getUser().getUsername();
            this.profilePath = entity.getGroupUserJoinEntity().getUser().getProfilePath();
            this.regTime = entity.getRegTime();
            this.boardId = entity.getBoardId();
            this.title = entity.getTitle();
            this.content = entity.getContent();
            this.boardFiles = entity.getFiles().stream().map(GroupBoardFileEntity::getName).collect(Collectors.toList());
            if (entity.getWriterExchangeEntity() != null) {
                this.price = entity.getWriterExchangeEntity().getPrice();
                this.exchangeOn = entity.getWriterExchangeEntity().getExchangeOn();
                this.location = entity.getWriterExchangeEntity().getLocation();
                this.locationDetail = entity.getWriterExchangeEntity().getLocationDetail();
                this.longitude = entity.getWriterExchangeEntity().getLongitude();
                this.latitude = entity.getWriterExchangeEntity().getLatitude();
                this.preferTime = entity.getWriterExchangeEntity().getPreferTime();
            }
        }
    }

    @Setter @Getter
    public static class ResponseRequestExchangeDTO{

        private Long userId; // writer id
        private String username;
        private String userProfile;
        private Long clientUserId;
        private String clilentUsername;
        private String clientProfile;
        private Long boardId;
        private Long clientId;

        public ResponseRequestExchangeDTO(WriterClientJoinEntity entity){
            this.userId = entity.getWriterId();
            this.clientUserId = entity.getClientId();
            this.boardId = entity.getWriterExchangeEntity().getId();
            this.clientId = entity.getClientExchangeEntity().getId();
        }
    }

    @Setter @Getter
    @NoArgsConstructor
    public static class ResponseClientDTO{
        private Long clientId;
        private Long userId;
        private Long writerId;      // writer user id
        private String username;    // writer user name
        private String profilePath; // wrtier profile image path
        private String title;
        private String content;     // client's product description
        private String price;       // add price with product or none product
        private String request;     // client's request content
        private String location;     // client's request location
        private WriterClientJoinEntity.status status; // To check exchange was progressing
        private List<String> file;

        public ResponseClientDTO(ClientExchangeEntity entity) {
            this.clientId = entity.getId();
            this.userId = entity.getUserId();
            this.content = entity.getContent();
            this.price = entity.getPrice();
            this.request = entity.getRequest();
        }

        public ResponseClientDTO(ResponseClientDTO dto, List<String> file){
            this.userId = dto.clientId;
            this.writerId = dto.writerId;
            this.username = dto.username;
            this.clientId = dto.clientId;
            this.profilePath = dto.profilePath;
            this.title = dto.title;
            this.content = dto.content;
            this.price = dto.price;
            this.request = dto.request;
            this.location = dto.location;
            this.file = file;
        }

    }
}
