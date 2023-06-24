package soul.smi.pfe.userservice.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soul.smi.pfe.userservice.dtos.AddressDTO;
import soul.smi.pfe.userservice.dtos.UpdateFieldOfUser;
import soul.smi.pfe.userservice.dtos.UserEntityDTO;
import soul.smi.pfe.userservice.exeptions.UserNotFoundExeption;
import soul.smi.pfe.userservice.service.PictureRestClient;
import soul.smi.pfe.userservice.service.UserService;

import javax.annotation.security.PermitAll;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class RegisterRestController {
    private UserService userService;
    private PictureRestClient pictureRestClient;
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
    @GetMapping("totalNumber")
    public Long getNumberOfUsers(){
        return userService.getNumberOfUsers();
    }

    @GetMapping("top/{id}")
    public UserEntityDTO getTopUser(@PathVariable String id){
        try {
            return userService.findUserAllowed(id);
        } catch (UserNotFoundExeption e) {
            throw new RuntimeException(e);
        }
    }
    @PutMapping("updateUserField")
    public ResponseEntity<UserEntityDTO> updateUserField(@RequestBody UpdateFieldOfUser fieldOfUser){
        try {
            UserEntityDTO userEntityDTO = userService.updateUser(fieldOfUser);
            return new ResponseEntity<>(userEntityDTO , HttpStatus.OK);
        } catch (UserNotFoundExeption e) {
            e.printStackTrace();
            return new ResponseEntity<>(null , HttpStatus.NOT_FOUND);
        }
    }


}
