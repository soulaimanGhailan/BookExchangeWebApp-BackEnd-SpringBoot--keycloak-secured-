package soul.smi.pfe.bookexchangebackend.service;

import soul.smi.pfe.bookexchangebackend.dtos.AddressDTO;
import soul.smi.pfe.bookexchangebackend.dtos.UpdateFieldOfUser;
import soul.smi.pfe.bookexchangebackend.dtos.UserEntityDTO;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;

import java.util.List;

public interface UserService {
    UserEntityDTO findUser(String userId) throws UserNotFoundExeption;
    List<UserEntityDTO> getAllUsers();
    byte[] getUserProfilePic(String id) throws UserNotFoundExeption;
    AddressDTO getAddress(String id) throws UserNotFoundExeption;
    void syncUsers();
    UserEntityDTO updateUser(UpdateFieldOfUser fieldOfUser) throws UserNotFoundExeption;
}
