package soul.smi.pfe.bookservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import soul.smi.pfe.bookservice.model.Picture;

@FeignClient("images-service")
public interface PictureRestClient {
    @GetMapping("/pictures/{id}")
    Picture getImage(@RequestHeader(value = "Authorization", required = true) String authorizationHeader , @PathVariable Long id);
    @PutMapping("/pictures/updatePicture/{picId}/{picName}")
    Picture updatePicture(@RequestHeader(value = "Authorization", required = true) String authorizationHeader ,
                          @RequestBody String imageBase64, @PathVariable Long picId , @PathVariable String picName);
    @PostMapping("/pictures/createImage/{picName}")
    Picture createPic(@RequestHeader(value = "Authorization", required = true) String authorizationHeader ,@RequestBody String imageBase64 , @PathVariable String picName);
    @PutMapping("/pictures/updateBookPicture/{picId}")
    Picture updateBookPic(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                          @RequestBody String imageContentBase64,@PathVariable Long picId);
    @DeleteMapping("/pictures/{id}")
    Picture deletePictureOfBook(@RequestHeader(value = "Authorization", required = true) String authorizationHeader ,
                                @PathVariable Long id);
}
