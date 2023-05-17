package soul.smi.pfe.bookexchangebackend.web;

import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soul.smi.pfe.bookexchangebackend.dtos.AddressDTO;
import soul.smi.pfe.bookexchangebackend.dtos.UserEntityDTO;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.bookNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class RegisterRestController {
    private UserService userService;
    @GetMapping("{id}")
    public UserEntityDTO getUser(@PathVariable String id){
        try {
            return userService.findUser(id);
        } catch (UserNotFoundExeption e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("{id}/address")
    public AddressDTO getUserAddress(@PathVariable String id){
        try {
            return userService.getAddress(id);
        } catch (UserNotFoundExeption e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping(path = "{id}/image")
    public ResponseEntity<String> getUserImage(@PathVariable String id){
        try {
            // Retrieve the image data based on the bookId
            byte[] imageData = getImage(id);

            // Encode the image data to base64
            String base64Image = Base64.encodeBase64String(imageData);

            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(base64Image);
        } catch (UserNotFoundExeption e) {
            throw new RuntimeException(e);
        }
    }
    private byte[] getImage(String userId) throws UserNotFoundExeption {
        return userService.getUserProfilePic(userId);
    }
}
