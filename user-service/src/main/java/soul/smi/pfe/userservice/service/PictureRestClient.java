package soul.smi.pfe.userservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import soul.smi.pfe.userservice.model.Picture;

@FeignClient(name = "images-service")
public interface PictureRestClient {
    @GetMapping("/pictures/{id}")
    Picture getImageOfUser(@RequestHeader(value = "Authorization", required = true) String authorizationHeader , @PathVariable Long id);
    @PutMapping("/pictures/updatePicture/{picId}/{picName}")
    Picture updatePicture(@RequestHeader(value = "Authorization", required = true) String authorizationHeader ,
                          @RequestBody String imageBase64, @PathVariable Long picId , @PathVariable String picName);
//    @PostMapping("/createImage/{picName}")
//     Picture createPic(@RequestBody String imageBase64 , @PathVariable String picName);
    @GetMapping("/pictures/allowed/{id}")
    Picture getImageOfUserAllowed(@PathVariable Long id);
}
