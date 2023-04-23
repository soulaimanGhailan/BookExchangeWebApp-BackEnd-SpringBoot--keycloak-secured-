package soul.smi.pfe.bookexchangebackend.service;

import soul.smi.pfe.bookexchangebackend.dtos.CommentDTO;
import soul.smi.pfe.bookexchangebackend.dtos.PageInfo;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.bookNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.commentNotFoundExeption;

import java.util.List;

public interface CommentService {
    List<CommentDTO> getCommentOfBook(Long bookId, int page, int size);
    List<CommentDTO> getRegularCommentOfBook(Long bookId, int page, int size);
    List<CommentDTO> getCommentOfBook(Long bookId);
    CommentDTO findComment(Long commentId) throws commentNotFoundExeption;
    List<CommentDTO> getCommentsOfUser(String userId) throws UserNotFoundExeption ;
    CommentDTO comment(String userId, Long bookId, String commentContent) throws UserNotFoundExeption, bookNotFoundExeption;
    CommentDTO reply(String userId , Long commentId , String replyContent) throws UserNotFoundExeption;
    List<CommentDTO> getRepliesOfComment(Long commentId , int page , int size);
    void deleteComment(Long commentId);
    List<CommentDTO> getCommentsOfUserInBook(String userId , Long bookId);
    void deleteCommentOfUserInBook(String userId , Long bookId);
    void deleteCommentsOfUser(String userId) throws UserNotFoundExeption;
    void deleteAllCommentOfBook(Long bookId);
    PageInfo getPageInfo(Long bookId , int size);
    PageInfo getPageInfoOfReplies(Long commentId, int size);
    Boolean hasReplies(Long commentId);
}
