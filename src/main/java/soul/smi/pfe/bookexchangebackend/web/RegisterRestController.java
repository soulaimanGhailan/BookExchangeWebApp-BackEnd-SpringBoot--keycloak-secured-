package soul.smi.pfe.bookexchangebackend.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import soul.smi.pfe.bookexchangebackend.dtos.AddressDTO;
import soul.smi.pfe.bookexchangebackend.dtos.UpdateFieldOfUser;
import soul.smi.pfe.bookexchangebackend.dtos.UserEntityDTO;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
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
