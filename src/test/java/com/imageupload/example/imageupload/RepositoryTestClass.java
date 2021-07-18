package com.imageupload.example.imageupload;

import com.imageupload.example.repositories.*;
import com.imageupload.example.vo.Role;
import java.util.Optional;

import com.imageupload.example.entity.CommentEntity;
import com.imageupload.example.entity.NotificationEntity;
import com.imageupload.example.entity.ProfileEntity;
import com.imageupload.example.entity.TransactionEntity;
import com.imageupload.example.enumtype.TransactionEnumType;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.entity.BoardEntity;
import com.imageupload.example.entity.ChatEntity;
import com.imageupload.example.entity.RoomEntity;
import com.imageupload.example.entity.UserJoinRoomEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application.yml")
public class RepositoryTestClass{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private GroupUsersRepository GroupUsersRepository;

    @Test
    void 알림_테이블_생성(){
        for(int i = 1; i < 61; i ++){
            NotificationEntity notificationEntity = NotificationEntity.builder().chat(0).notification(0).transaction(0).build();
            notificationRepository.save(notificationEntity);
        }
    }

    @Test
    void 채팅알림_카운트_증가_테스트(){
        UserEntity userEntity = userRepository.findById(4L).get();
        notificationRepository.clearChat(userEntity.getNotification().getId());
    }

    @Test
    void 알림_저장_테스트(){

        UserEntity userEntity = userRepository.findById(123L).get();

        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setUserId(userEntity);
        notificationEntity.setChat(0);

        notificationRepository.save(notificationEntity);
        // notificationRepository.findBychat();

    }
    
    @Test
    void 매너점수_구하기_테스트(){

        String username = "2";

        float test = commentRepository.findAllBymannerScore(username);

        System.out.println("");

    }

    @Test
    void 거래리뷰_저장_테스트(){

        BoardEntity boardEntity = boardRepository.findById(604L).get();

        for(int i = 60; i < 100; i++){

            CommentEntity commentEntity = CommentEntity.builder()
            .writer(boardEntity.getWriter())
            .commentValue("commentValue")
            .target("1")
            .build();
            
            commentRepository.save(commentEntity);

        }
            
    }

    @Test
    void 거래리스트_저장_테스트(){

        UserEntity sellerEntity = userRepository.findById(122L).get();
        UserEntity buyerEntity = userRepository.findById(123L).get();
        
        for(int i = 1; i < 104; i ++){
            BoardEntity board = boardRepository.findById(Long.parseLong(String.valueOf(i))).get();;
            
            TransactionEntity transactionEntity = new TransactionEntity();
            transactionEntity.setBoardId(board);
            transactionEntity.setType(TransactionEnumType.complete);
            transactionEntity.setSeller(sellerEntity);
            transactionEntity.setBuyer(buyerEntity);
            transactionRepository.save(transactionEntity);

        }

    }

    @Test
    public void 채팅방_개설_테스트(){

        for(int i = 21; i < 59; i ++){

            Optional<UserEntity> user = userRepository.findById(21L);
            Optional<UserEntity> target = userRepository.findById(Long.parseLong(String.valueOf(i)) + 1);

            if(target != null){

                RoomEntity room = new RoomEntity();
                roomRepository.save(room);

                UserJoinRoomEntity userJoinRoomEntity = new UserJoinRoomEntity();
                userJoinRoomEntity.setTarget(user.get());
                userJoinRoomEntity.setUserVo(target.get());
                userJoinRoomEntity.setRoomEntity(room);

                chatRoomRepository.save(userJoinRoomEntity);
            }
        }
    }

    @Test
    public void 채팅내용_저장_테스트(){

        for(int i = 23; i < 60; i ++){
        Optional<UserEntity> user = userRepository.findById(Long.parseLong(String.valueOf(i)));

        Optional<RoomEntity> room = roomRepository.findById(Long.parseLong(String.valueOf(i)) + 1);

        ChatEntity chat = new ChatEntity();
        chat.setMessage("message");
        chat.setUserVo(user.get());
        chat.setRoomEntity(room.get());

            chatRepository.save(chat);
        }
    }

    @Test
    public void 회원가입_테스트(){

        for(int i = 1; i < 61; i ++){

            NotificationEntity notificationEntity = NotificationEntity.builder()
            .chat(0)
            .notification(0)
            .transaction(0)
            .build();

            ProfileEntity profile = ProfileEntity.builder()
            .location("location")
            .introduce("introduce")
            .mannerScore(0)
            .mileage(0)
            .phoneNum("phoneNum")
            .nickname("nickname")
            .preferTime("preferTime")
            .profilePath("default_profile_img.png")
            .build();

            UserEntity user = UserEntity.builder()
            .username(i + "")
            .password(new BCryptPasswordEncoder().encode(String.valueOf(i)))
            .profile(profile)
            .notification(notificationEntity)
            .Role(Role.ROLE_USER)
            .build();
            
            userRepository.save(user);

        }

    }
    
}
