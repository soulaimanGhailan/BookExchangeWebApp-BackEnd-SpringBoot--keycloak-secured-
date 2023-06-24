package soul.smi.pfe.userservice.mappers;


import soul.smi.pfe.userservice.dao.entities.UserAddress;
import soul.smi.pfe.userservice.dao.entities.UserEntity;
import soul.smi.pfe.userservice.dtos.AddressDTO;
import soul.smi.pfe.userservice.dtos.UserEntityDTO;
import soul.smi.pfe.userservice.model.Picture;

public interface Mapper {
    UserAddress fromAddressDTO(AddressDTO addressDTO);
    AddressDTO fromAddress(UserAddress address);
    UserEntityDTO fromUserEntityWithRestImage(UserEntity user);
    UserEntityDTO fromUserEntityWithRestImageAllowed(UserEntity user);
    UserEntityDTO fromUserEntityWithLocalImage(UserEntity user , Picture picture);
    UserEntity fromUserEntityDTO(UserEntityDTO user);
}
