package soul.smi.pfe.ChatComservice.dao.reposotories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import soul.smi.pfe.ChatComservice.dao.entities.Comment;
import soul.smi.pfe.ChatComservice.dao.enums.CommentType;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByBookId(Long bookId);
    Page<Comment> findCommentsByBookId(Long bookId , Pageable pageable);
    Page<Comment> findCommentsByCommentTypeAndBookId(CommentType commentType, Long bookId , Pageable pageable);
    Page<Comment> findCommentsByOriginalCommentCommentId(Long commentId  , Pageable pageable);
    List<Comment> findCommentsByOwnerId(String userId);
    Comment findCommentByCommentId(Long commentId);


}
