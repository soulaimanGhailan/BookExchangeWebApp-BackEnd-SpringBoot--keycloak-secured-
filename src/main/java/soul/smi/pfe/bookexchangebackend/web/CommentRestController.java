package soul.smi.pfe.bookexchangebackend.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soul.smi.pfe.bookexchangebackend.dtos.CommentDTO;
import soul.smi.pfe.bookexchangebackend.dtos.PageInfo;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.BookNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.service.CommentService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("comment")
@AllArgsConstructor
@CrossOrigin("http://localhost:4200")
public class CommentRestController {
    private CommentService commentService;
//    @GetMapping("/pageInfo/{size}")
//    public PageInfo getPageInfoOfComments(@PathVariable int size){
//        return commentService.
//    }
    @GetMapping("{bookId}")
    public ResponseEntity<Collection<CommentDTO>> getComments(@PathVariable Long bookId,
                                                              @RequestParam(name = "page" , defaultValue = "0") int page,
                                                              @RequestParam(name = "size" , defaultValue = "5")int size
    ){
        List<CommentDTO> commentOfBook = commentService.getCommentOfBook(bookId , page ,size);
        return new ResponseEntity<>(commentOfBook , HttpStatus.OK);
    }
    @GetMapping("{bookId}/regularComments")
    public ResponseEntity<Collection<CommentDTO>> getRegularComments(@PathVariable Long bookId,
                                                              @RequestParam(name = "page" , defaultValue = "0") int page,
                                                              @RequestParam(name = "size" , defaultValue = "5")int size
    ){
        List<CommentDTO> commentOfBook = commentService.getRegularCommentOfBook(bookId , page ,size);
        commentOfBook.forEach(c -> c.setRepliesPageInfo(commentService.getPageInfoOfReplies(c.getCommentId() , 3)));
        return new ResponseEntity<>(commentOfBook , HttpStatus.OK);
    }
    @GetMapping("{commentId}/replies")
    public ResponseEntity<Collection<CommentDTO>> getRepliesOfComment(@PathVariable Long commentId,
                                                              @RequestParam(name = "page" , defaultValue = "0") int page,
                                                              @RequestParam(name = "size" , defaultValue = "5")int size
    ){
        List<CommentDTO> commentOfBook = commentService.getRepliesOfComment(commentId , page , size);
        return new ResponseEntity<>(commentOfBook , HttpStatus.OK);
    }

    @GetMapping("{bookId}/pageInfo")
    public PageInfo getPageInfoOfComment(@PathVariable Long bookId ,
                                         @RequestParam(name = "size" , defaultValue = "5") int size){
        return commentService.getPageInfo(bookId ,size);
    }

    @GetMapping("{commentId}/replies/pageInfo")
    public PageInfo getPageInfoOfReplies(@PathVariable Long commentId ,
                                         @RequestParam(name = "size" , defaultValue = "5") int size){
        return commentService.getPageInfoOfReplies(commentId ,size);
    }
    @GetMapping("{commentId}/hasReplies")
    public Boolean hasReplies(@PathVariable Long commentId ){
        return commentService.hasReplies(commentId);
    }

    @PostMapping("{userId}/{bookId}")
    public ResponseEntity<CommentDTO> comment(@PathVariable String userId ,
                                  @PathVariable Long bookId ,
                                  @RequestBody CommentDTO commentDTO){
        try {
            CommentDTO comment = commentService.comment(userId, bookId, commentDTO.getCommentContent());
            return new  ResponseEntity(comment , HttpStatus.OK);
        } catch (UserNotFoundExeption | BookNotFoundExeption e) {
            return new ResponseEntity<>(null , HttpStatus.valueOf("exception : user not found or BookNotFound"));
        }
    }
    @PostMapping("{userId}/{commentId}/reply")
    public ResponseEntity<CommentDTO> replyTsComment(@PathVariable String userId ,
                                              @PathVariable Long commentId ,
                                              @RequestBody CommentDTO commentDTO){
        try {
            CommentDTO comment = commentService.reply(userId , commentId , commentDTO.getCommentContent());
            return new  ResponseEntity(comment , HttpStatus.OK);
        } catch (UserNotFoundExeption e) {
            return new ResponseEntity<>(null , HttpStatus.valueOf("exception : user not found or BookNotFound"));
        }
    }
    @DeleteMapping("{commentId}")
    public void delete(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
    }
    @DeleteMapping("/user/{userid}/{bookId}")
    public void deleteCommentOfUser(@PathVariable Long bookId,
                                    @PathVariable String userid){
        commentService.deleteCommentOfUserInBook(userid,bookId);
    }
    @DeleteMapping("{bookId}")
    public void deleteCommentOfUser( @PathVariable Long bookId){
        commentService.deleteAllCommentOfBook(bookId);
    }


}
