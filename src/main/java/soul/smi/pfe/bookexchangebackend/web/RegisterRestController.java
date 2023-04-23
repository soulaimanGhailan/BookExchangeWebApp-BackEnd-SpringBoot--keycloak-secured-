package soul.smi.pfe.bookexchangebackend.web;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import soul.smi.pfe.bookexchangebackend.dtos.AddressDTO;
import soul.smi.pfe.bookexchangebackend.dtos.RegisteredUserDTO;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.service.UseresService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class RegisterRestController {
    private UseresService useresService;
    @GetMapping("{id}")
    public RegisteredUserDTO getUser(@PathVariable String id){
        try {
            return useresService.findUser(id);
        } catch (UserNotFoundExeption e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("{id}/address")
    public AddressDTO getUserAddress(@PathVariable String id){
        try {
            return useresService.getAddress(id);
        } catch (UserNotFoundExeption e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping(path = "{id}/image" ,produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getUserImage(@PathVariable String id){
        try {
            return useresService.getUserProfilePic(id);
        } catch (UserNotFoundExeption e) {
            throw new RuntimeException(e);
        }
    }
}
