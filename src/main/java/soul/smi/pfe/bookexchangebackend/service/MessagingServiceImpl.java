package soul.smi.pfe.bookexchangebackend.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import soul.smi.pfe.bookexchangebackend.dtos.MessageDTO;
import soul.smi.pfe.bookexchangebackend.exeptions.MessageEmtyExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.MessageNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;

import java.util.List;
@Service @Transactional
public class MessagingServiceImpl implements MessagingService {
    @Override
    public MessageDTO findMessage(Long messageId) throws MessageNotFoundExeption {
        return null;
    }

    @Override
    public List<MessageDTO> getMessagesSendByUser(String userId) {
        return null;
    }

    @Override
    public void sendMessage(String senderId, String reciverId, MessageDTO messageDTO) throws UserNotFoundExeption, MessageEmtyExeption {

    }

    @Override
    public void recieveMessage(String senderId, String reciverId, MessageDTO messageDTO) throws UserNotFoundExeption {

    }
}
