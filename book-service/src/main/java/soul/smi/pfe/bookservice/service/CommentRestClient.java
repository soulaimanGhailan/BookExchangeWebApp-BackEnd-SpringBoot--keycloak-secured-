package soul.smi.pfe.bookservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("ChatCom-service")
public interface CommentRestClient {
    @DeleteMapping("comments/All/{bookId}")
    void deleteAllCommentOfBook(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                                @PathVariable Long bookId);
}
