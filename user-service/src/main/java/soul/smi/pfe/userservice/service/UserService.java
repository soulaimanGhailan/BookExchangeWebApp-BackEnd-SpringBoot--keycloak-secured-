package soul.smi.pfe.userservice.service;


import soul.smi.pfe.userservice.dtos.AddressDTO;
import soul.smi.pfe.userservice.dtos.UpdateFieldOfUser;
import soul.smi.pfe.userservice.dtos.UserEntityDTO;
import soul.smi.pfe.userservice.exeptions.UserNotFoundExeption;

public interface UserService {
    UserEntityDTO findUser(String userId) throws UserNotFoundExeption;
    AddressDTO getAddress(String id) throws UserNotFoundExeption;
    Long getNumberOfUsers();
    void syncUsers();
    UserEntityDTO updateUser(UpdateFieldOfUser fieldOfUser) throws UserNotFoundExeption;
    UserEntityDTO findUserAllowed(String id) throws UserNotFoundExeption;
}
