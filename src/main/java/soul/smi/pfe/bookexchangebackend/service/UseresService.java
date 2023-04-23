package soul.smi.pfe.bookexchangebackend.service;

import soul.smi.pfe.bookexchangebackend.dtos.AddressDTO;
import soul.smi.pfe.bookexchangebackend.dtos.RegisteredUserDTO;
import soul.smi.pfe.bookexchangebackend.dtos.RegularUserDTO;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.exeptions.bookNotFoundExeption;

import java.util.List;

public interface UseresService {
    RegisteredUserDTO findUser(String userId) throws UserNotFoundExeption;
    List<RegisteredUserDTO> getAllUsers();
    void updateUser(RegisteredUserDTO regularUserDTO);
    void deleteUser(String userId) throws UserNotFoundExeption;
    byte[] getUserProfilePic(String id) throws UserNotFoundExeption;
    AddressDTO getAddress(String id) throws UserNotFoundExeption;
}
