package soul.smi.pfe.bookexchangebackend.service;

import soul.smi.pfe.bookexchangebackend.dtos.MessageDTO;
import soul.smi.pfe.bookexchangebackend.exeptions.MessageEmtyExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.MessageNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;

import java.util.List;

public interface MessagingService {
    MessageDTO findMessage(Long messageId) throws MessageNotFoundExeption;
    List<MessageDTO> getMessagesSendByUser(String userId);
    void sendMessage(String senderId , String reciverId , MessageDTO messageDTO) throws UserNotFoundExeption , MessageEmtyExeption;
    void recieveMessage(String senderId , String reciverId , MessageDTO messageDTO)throws UserNotFoundExeption;
}

