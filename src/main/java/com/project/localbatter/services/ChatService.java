package com.project.localbatter.services;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import com.project.localbatter.dto.MessageDTO;
import com.project.localbatter.dto.NotificationDTO;
import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.entity.ChatEntity;
import com.project.localbatter.entity.RoomEntity;
import com.project.localbatter.entity.UserJoinRoomEntity;
import com.project.localbatter.repositories.ChatRepository;
import com.project.localbatter.repositories.ChatRoomRepository;
import com.project.localbatter.repositories.NotificationRepository;
import com.project.localbatter.repositories.RoomRepository;
import com.project.localbatter.repositories.UserJoinRommEnumType;
import com.project.localbatter.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class ChatService {
    
    private final SimpMessagingTemplate simpMessageTemplate;
    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    public void clearNotification(HttpSession session, String Type){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        Long id = userEntity.getNotification().getId();
        
        switch(Type){
            case "chat":
                notificationRepository.clearChat(id);
                break;
            case "notification":
                notificationRepository.clearNotification(id);
                break;
            case "transaction":
                notificationRepository.clearTransaction(id);
                break;
        }

    }

    public void deleteChatRoom(String roomId, String userId, String targetUsername){

        UserJoinRoomEntity userJoinRoomEntity = chatRoomRepository.findById(Long.parseLong(roomId)).get();
        if(targetUsername.equals(userJoinRoomEntity.getUserVo().getUsername()))
            userJoinRoomEntity.setUserConnectionType(UserJoinRommEnumType.disconnected);
        else
            userJoinRoomEntity.setTargetConnectionType(UserJoinRommEnumType.disconnected);

        if(userJoinRoomEntity.getTargetConnectionType() == UserJoinRommEnumType.disconnected && userJoinRoomEntity.getUserConnectionType() == UserJoinRommEnumType.disconnected)
            chatRoomRepository.delete(userJoinRoomEntity);            
        else
            chatRoomRepository.save(userJoinRoomEntity);

    }

    public void sendNotification(HttpSession session, NotificationDTO message){

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");
        UserEntity targetEntity = userRepository.findByUsername(message.getTarget()).get();

        Long id = targetEntity.getNotification().getId();

        switch (message.getNotificationType()){
            case chat:
                notificationRepository.updateChat(id);
                break;
            case transaction:
                notificationRepository.updateTransaction(id, userEntity.getNotification().getId());
                simpMessageTemplate.convertAndSend("/notification/" + message.getSender(), message);
                break;
            case notification:
                notificationRepository.updateNotification(id);
                break;
        }
            
        simpMessageTemplate.convertAndSend("/notification/" + message.getTarget(), message);
    }

    public List<UserJoinRoomEntity> getChatRoomList(Principal userVo){

        UserEntity user = userRepository.findByUsername(userVo.getName()).get();

        List<UserJoinRoomEntity> roomList = chatRoomRepository.findAllByuserVoOrTarget(user, user);

        roomList.forEach(action -> {
            if(action.getTarget().getUsername().equals(userVo.getName())){
                action.setTarget(action.getUserVo());
            }
        });

        roomList.sort((o1, o2) -> {
            int chatSizeofO1 = o1.getRoomEntity().getChats().size() -1;
            int chatSizeofO2 = o2.getRoomEntity().getChats().size() -1;
            if(chatSizeofO1 > -1 && chatSizeofO2 > -1){
                ChatEntity chatEntityofO1 = o1.getRoomEntity().getChats().get(chatSizeofO1);
                ChatEntity chatEntityofO2 = o2.getRoomEntity().getChats().get(chatSizeofO2);

                return chatEntityofO2.getCreateTime().compareTo(chatEntityofO1.getCreateTime());

            }else return 0;
        });

        return roomList;
            
    }

    // 프로필 전송
    public void sendProfile(Principal user, MessageDTO message){

        UserEntity userEntity = userRepository.findByUsername(user.getName()).get();

        message.setMessage("거래 선호시간 : " + userEntity.getProfile().getPreferTime() +
                        "<br> 핸드폰 번호 : " + userEntity.getProfile().getAccountNumber() +
                        "<br> 거래 선호지역 : " + userEntity.getProfile().getLocation());

        simpMessageTemplate.convertAndSend("/chat/" + message.getRoomId(), message);
    }

    // 계좌번호 전송
    public void sendNumber(Principal user, MessageDTO message){
        UserEntity userEntity = userRepository.findByUsername(user.getName()).get();
        message.setMessage("국민은행 : " + userEntity.getProfile().getAccountNumber());
        simpMessageTemplate.convertAndSend("/chat/" + message.getRoomId(), message);
    }

    // 메세지 전송
    public void sendMessage(Principal principal, MessageDTO message){

        Optional<UserEntity> user = userRepository.findByUsername(principal.getName());
        ChatEntity chatEntity = ChatEntity.builder()
        .message(message.getMessage())
        .userVo(user.get())
        .roomEntity(roomRepository.findById(message.getRoomId()).get())
        .build();
        chatRepository.save(chatEntity);
        simpMessageTemplate.convertAndSend("/chat/" + message.getRoomId(), message);

    }

    public Page<ChatEntity> getChatList(long roomId, int display, int page){
                                            
        Optional<RoomEntity> roomEntity = roomRepository.findById(roomId);
        RoomEntity room = roomEntity.get();

        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "id");
        Page<ChatEntity> chatList = chatRepository.findAllByroomEntity(room, request);

        return chatList;
    }

}