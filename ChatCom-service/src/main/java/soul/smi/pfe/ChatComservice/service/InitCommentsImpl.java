package soul.smi.pfe.ChatComservice.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soul.smi.pfe.ChatComservice.dao.entities.Comment;
import soul.smi.pfe.ChatComservice.dao.enums.CommentType;
import soul.smi.pfe.ChatComservice.dao.reposotories.CommentRepo;
import soul.smi.pfe.ChatComservice.dtos.CommentDTO;
import soul.smi.pfe.ChatComservice.exeptions.BookNotFoundExeption;
import soul.smi.pfe.ChatComservice.exeptions.UserNotFoundExeption;
import soul.smi.pfe.ChatComservice.mappers.Mapper;
import soul.smi.pfe.ChatComservice.model.Book;
import soul.smi.pfe.ChatComservice.model.UserEntity;


import java.util.Date;
import java.util.List;

@Service
@Transactional
public class InitCommentsImpl implements InitComments {
    private CommentService commentService;
    private CommentRepo commentRepo;
    private Mapper mapper;
    private List<String> defaultUsersIds = List.of("c92fb040-0719-4c15-a364-57e8e514d2c2" , "87d7a13c-d609-4557-8e95-df2a80870479" , "f6b6d8b9-b735-4eb3-b90b-6a8a76918823");
    private   List<String> comments = List.of("this is good", "i am intrested", "i want to exchange with you body",
            "i've read this one", "this book looks sridered");

    public InitCommentsImpl(CommentService commentService, CommentRepo commentRepo) {
        this.commentService = commentService;
        this.commentRepo = commentRepo;
    }

    @Override
    public void initComments() {
        defaultUsersIds.forEach(user ->{
            for (int n = 0; n < 8 * 3; n++) {
                for (int i = 0; i < 3; i++) {
                    try{
                        Comment comment = comment(user, Integer.toUnsignedLong(n+1), generateComment());
                        reply(user , comment.getCommentId() , generateComment() );
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    private String generateComment() {
        return (comments.get((int)(Math.random()*comments.size())));
    }

    private Comment comment(String userId, Long bookId, String commentContent) throws UserNotFoundExeption, BookNotFoundExeption {
        Comment comment = new Comment();
        comment.setOwnerId(userId);
        comment.setBookId(bookId);
        comment.setCommentDate(new Date());
        comment.setOriginalComment(null);
        comment.setCommentType(CommentType.REGULAR_COMMENT);
        comment.setCommentContent(commentContent);
        return commentRepo.save(comment);
    }


    private Comment reply(String userId, Long commentId, String replyContent) throws UserNotFoundExeption {
        Comment comment = new Comment();
        comment.setOwnerId(userId);
        Comment commentOrigin = commentRepo.findCommentByCommentId(commentId);
        comment.setOriginalComment(commentOrigin);
        comment.setBookId(commentOrigin.getBookId());
        comment.setCommentDate(new Date());
        comment.setCommentType(CommentType.REPLY_COMMENT);
        comment.setCommentContent(replyContent);
        return commentRepo.save(comment);
    }
}
