package soul.smi.pfe.bookexchangebackend.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import soul.smi.pfe.bookexchangebackend.dao.entities.Book;
import soul.smi.pfe.bookexchangebackend.dao.entities.Comment;
import soul.smi.pfe.bookexchangebackend.dao.entities.RegisteredUser;
import soul.smi.pfe.bookexchangebackend.dao.enums.CommentType;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.BookRepo;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.CommentRepo;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.RegisteredUserRepo;
import soul.smi.pfe.bookexchangebackend.dtos.CommentDTO;
import soul.smi.pfe.bookexchangebackend.dtos.PageInfo;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.bookNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.commentNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.mappers.mapping;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional @AllArgsConstructor
@Data
public class CommentServiceImpl implements CommentService {
    private BookRepo bookRepo;
    private RegisteredUserRepo registeredUserRepo;
    private mapping mapper;
    private CommentRepo commentRepo;

    @Override
    public List<CommentDTO> getCommentOfBook(Long bookId) {
        return commentRepo.findCommentsByCommentBookBookId(bookId).stream()
                .map(comment -> mapper.fromComment(comment)).collect(Collectors.toList());
    }
    @Override
    public List<CommentDTO> getCommentOfBook(Long bookId, int page, int size) {
        Page<Comment> commentPage = commentRepo.findCommentsByCommentBookBookId(bookId, PageRequest.of(page, size));
        return commentPage.getContent().stream().map(comment -> mapper.fromComment(comment)).collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> getRegularCommentOfBook(Long bookId, int page, int size) {
        Page<Comment> comments = commentRepo.findCommentsByCommentTypeAndCommentBookBookId(CommentType.REGULAR_COMMENT, bookId, PageRequest.of(page, size));
        return comments.getContent().stream().map(comment -> mapper.fromComment(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO findComment(Long commentId) throws commentNotFoundExeption {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new commentNotFoundExeption("comment not found"));
        return mapper.fromComment(comment);
    }

    @Override
    public List<CommentDTO> getCommentsOfUser(String userId) throws UserNotFoundExeption {
        return commentRepo.findCommentsByOwnerUserId(userId).stream()
                .map(comment -> getMapper().fromComment(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDTO comment(String userId, Long bookId, String commentContent) throws UserNotFoundExeption, bookNotFoundExeption {
        Comment comment = new Comment();
        RegisteredUser user = registeredUserRepo.findById(userId).orElseThrow(() ->new UserNotFoundExeption("userNotfound"));
        comment.setOwner( user);
        Book book = bookRepo.findById(bookId).orElseThrow(() -> new bookNotFoundExeption("book not found exeption"));
        comment.setCommentBook(book);
        comment.setCommentDate(new Date());
        comment.setOriginalComment(null);
        comment.setCommentType(CommentType.REGULAR_COMMENT);
        comment.setCommentContent(commentContent);
        Comment savedComment = commentRepo.save(comment);
        return mapper.fromComment(savedComment);
    }

    @Override
    public CommentDTO reply(String userId, Long commentId, String replyContent) throws UserNotFoundExeption {
        Comment comment = new Comment();
        RegisteredUser user = registeredUserRepo.findById(userId).orElseThrow(() ->new UserNotFoundExeption("userNotfound"));
        comment.setOwner( user);
        Comment commentOrigin = commentRepo.findCommentByCommentId(commentId);
        comment.setOriginalComment(commentOrigin);
        comment.setCommentBook(commentOrigin.getCommentBook());
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
        List<CommentDTO> comments = commentRepo.findCommentsByCommentBookBookId(bookId).stream()
                .filter(comment -> userId.equals(comment.getOwner()))
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
        Page<Comment> page = commentRepo.findCommentsByCommentTypeAndCommentBookBookId(CommentType.REGULAR_COMMENT , bookId , PageRequest.of(0 , size));
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


}
