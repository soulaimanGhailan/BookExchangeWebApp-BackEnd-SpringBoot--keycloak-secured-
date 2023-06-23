package soul.smi.pfe.ChatComservice.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soul.smi.pfe.ChatComservice.dao.entities.Comment;
import soul.smi.pfe.ChatComservice.dao.entities.Notification;
import soul.smi.pfe.ChatComservice.dao.enums.CommentType;
import soul.smi.pfe.ChatComservice.dao.enums.NotificationType;
import soul.smi.pfe.ChatComservice.dao.reposotories.CommentRepo;
import soul.smi.pfe.ChatComservice.dao.reposotories.NotificationRepo;
import soul.smi.pfe.ChatComservice.dtos.CommentDTO;
import soul.smi.pfe.ChatComservice.dtos.PageInfo;
import soul.smi.pfe.ChatComservice.exeptions.BookNotFoundExeption;
import soul.smi.pfe.ChatComservice.exeptions.UserNotFoundExeption;
import soul.smi.pfe.ChatComservice.exeptions.commentNotFoundExeption;
import soul.smi.pfe.ChatComservice.mappers.Mapper;
import soul.smi.pfe.ChatComservice.model.Book;
import soul.smi.pfe.ChatComservice.model.UserEntity;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static soul.smi.pfe.ChatComservice.dao.enums.ReceivingStatus.UNSEEN;
import static soul.smi.pfe.ChatComservice.dao.enums.NotificationType.POST_COMMENT_NOTIFICATION;

@Service
@Transactional
@AllArgsConstructor
@Data
public class CommentServiceImpl implements CommentService {
    private Mapper mapper;
    private CommentRepo commentRepo;
    private NotificationRepo notificationRepo;
    private BookRestClient bookRestClient ;
    private UsersRestClient usersRestClient;

    @Override
    public List<CommentDTO> getCommentOfBook(Long bookId) {
        return commentRepo.findCommentsByBookId(bookId).stream()
                .map(comment -> mapper.fromComment(comment)).collect(Collectors.toList());
    }
    @Override
    public List<CommentDTO> getCommentOfBook(Long bookId, int page, int size) {
        Page<Comment> commentPage = commentRepo.findCommentsByBookId(bookId, PageRequest.of(page, size));
        return commentPage.getContent().stream().map(comment -> mapper.fromComment(comment)).collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getRegularCommentOfBook(Long bookId, int page, int size) {
        Page<Comment> comments = commentRepo.findCommentsByCommentTypeAndBookId(CommentType.REGULAR_COMMENT, bookId, PageRequest.of(page, size));
        return comments.getContent().stream().map(comment -> mapper.fromComment(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO findComment(Long commentId) throws commentNotFoundExeption {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new commentNotFoundExeption("comment not found"));
        return mapper.fromComment(comment);
    }

    @Override
    public List<CommentDTO> getCommentsOfUser(String userId) throws UserNotFoundExeption {
        UserEntity user = usersRestClient.getUser(getToken(), userId);
        if(user == null) throw  new UserNotFoundExeption("owner of comment not found");
        return commentRepo.findCommentsByOwnerId(userId).stream()
                .map(comment -> getMapper().fromComment(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO comment(String userId, Long bookId, String commentContent) throws UserNotFoundExeption, BookNotFoundExeption {
        Comment comment = new Comment();
        UserEntity user = usersRestClient.getUser(getToken() , userId);
        if(user == null) throw new UserNotFoundExeption("owner of a comment not found");
        comment.setOwnerId(user.getUserId());
        Book book = bookRestClient.getBook(getToken(),  bookId);
        if(book == null ) throw new BookNotFoundExeption("book not found exception");
        comment.setBookId(bookId);
        comment.setCommentDate(new Date());
        comment.setOriginalComment(null);
        comment.setCommentType(CommentType.REGULAR_COMMENT);
        comment.setCommentContent(commentContent);
        // create a notification comment
        createNotification(book.getBookId() , book.getOwner().getUserId() , POST_COMMENT_NOTIFICATION);

        Comment savedComment = commentRepo.save(comment);
        return mapper.fromComment(savedComment);
    }

    @Override
    public CommentDTO reply(String userId, Long commentId, String replyContent) throws UserNotFoundExeption {
        Comment comment = new Comment();
        UserEntity user = usersRestClient.getUser(getToken() , userId);
        if(user == null) throw new UserNotFoundExeption("owner of a comment not found");
        comment.setOwnerId( user.getUserId());
        Comment commentOrigin = commentRepo.findCommentByCommentId(commentId);
        comment.setOriginalComment(commentOrigin);
        comment.setBookId(commentOrigin.getBookId());
        comment.setCommentDate(new Date());
        comment.setCommentType(CommentType.REPLY_COMMENT);
        comment.setCommentContent(replyContent);
        Comment savedComment = commentRepo.save(comment);
        return mapper.fromComment(savedComment);
    }

    @Override
    public List<CommentDTO> getRepliesOfComment(Long commentId, int page, int size) {
        List<Comment> content = commentRepo.findCommentsByOriginalCommentCommentId(commentId, PageRequest.of(page, size)).getContent();
        return content.stream().map(comment -> mapper.fromComment(comment)).collect(Collectors.toList());
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepo.deleteById(commentId);
    }

    @Override
    public List<CommentDTO> getCommentsOfUserInBook(String userId, Long bookId) {
        List<CommentDTO> comments = commentRepo.findCommentsByBookId(bookId).stream()
                .filter(comment -> userId.equals(comment.getOwnerId()))
                .map(comment -> mapper.fromComment(comment))
                .collect(Collectors.toList());
        return comments;
    }

    @Override
    public void deleteCommentOfUserInBook(String userId , Long bookId) {
        getCommentsOfUserInBook(userId,bookId).stream()
                .forEach(commentDTO -> deleteComment(commentDTO.getCommentId()));
    }

    @Override
    public void deleteCommentsOfUser(String userId) throws UserNotFoundExeption {
       getCommentsOfUser(userId).stream()
               .forEach(commentDTO -> deleteComment(commentDTO.getCommentId()));
    }

    @Override
    public void deleteAllCommentOfBook(Long bookId) {
        getCommentOfBook(bookId).stream()
                .map(commentDTO -> mapper.fromCommentDTO(commentDTO))
                .forEach(comment -> commentRepo.deleteById(comment.getCommentId()));
    }

    @Override
    public PageInfo getPageInfo(Long bookId, int size) {
        Page<Comment> page = commentRepo.findCommentsByCommentTypeAndBookId(CommentType.REGULAR_COMMENT , bookId , PageRequest.of(0 , size));
        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotalPages(page.getTotalPages());
        pageInfo.setTotalElements(page.getTotalElements());
        return pageInfo;
    }

    @Override
    public PageInfo getPageInfoOfReplies(Long commentId, int size) {
        PageInfo pageInfo = new PageInfo();
        Page<Comment> page = commentRepo.findCommentsByOriginalCommentCommentId(commentId, PageRequest.of(0, size));
        pageInfo.setTotalElements(page.getTotalElements());
        pageInfo.setTotalPages(page.getTotalPages());
        return pageInfo;
    }

    @Override
    public Boolean hasReplies(Long commentId) {
        return getRepliesOfComment(commentId , 0 , 1).size() != 0;
    }

    @Override
    public Long GetNumberOfComments() {
        return commentRepo.count();
    }

    private String getToken(){
        KeycloakSecurityContext context = (KeycloakSecurityContext) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String token ="bearer "+ context.getTokenString();
        return token;
    }
    private Notification createNotification(Long bookId , String receiverId , NotificationType type ){
        Notification notification = Notification.builder()
                .BookId(bookId)
                .type(type)
                .receiverId(receiverId)
                .status(UNSEEN).build();
        return notificationRepo.save(notification);
    }
}
