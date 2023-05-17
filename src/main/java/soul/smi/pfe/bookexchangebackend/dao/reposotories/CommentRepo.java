package soul.smi.pfe.bookexchangebackend.dao.reposotories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import soul.smi.pfe.bookexchangebackend.dao.entities.Comment;
import soul.smi.pfe.bookexchangebackend.dao.enums.CommentType;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByCommentBookBookId(Long bookId);
    Page<Comment> findCommentsByCommentBookBookId(Long bookId , Pageable pageable);
    Page<Comment> findCommentsByCommentTypeAndCommentBookBookId(CommentType commentType, Long bookId ,  Pageable pageable);
    Page<Comment> findCommentsByOriginalCommentCommentId(Long commentId  , Pageable pageable);
    List<Comment> findCommentsByOwnerUserId(String userId);
    Comment findCommentByCommentId(Long commentId);


}
