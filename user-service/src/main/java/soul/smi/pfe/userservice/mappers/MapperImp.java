package soul.smi.pfe.userservice.mappers;

import lombok.AllArgsConstructor;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import soul.smi.pfe.userservice.dao.entities.UserAddress;
import soul.smi.pfe.userservice.dao.entities.UserEntity;
import soul.smi.pfe.userservice.dtos.AddressDTO;
import soul.smi.pfe.userservice.dtos.UserEntityDTO;
import soul.smi.pfe.userservice.model.Picture;
import soul.smi.pfe.userservice.service.PictureRestClient;


@Service
@AllArgsConstructor
public class MapperImp implements Mapper {
    private PictureRestClient pictureRestClient;
    @Override
    public UserEntityDTO fromUserEntityWithRestImage(UserEntity user) {
        UserEntityDTO userDto=new UserEntityDTO();
        String imageData = pictureRestClient.getImageOfUser(getToken() , user.getPictureId()).getPictureContent();
        userDto.setImageContentBase64(imageData);
        BeanUtils.copyProperties(user , userDto);
        return userDto;
    }
    @Override
    public UserEntityDTO fromUserEntityWithRestImageAllowed(UserEntity user) {
        UserEntityDTO userDto=new UserEntityDTO();
        String imageData = pictureRestClient.getImageOfUserAllowed(user.getPictureId()).getPictureContent();
        userDto.setImageContentBase64(imageData);
        BeanUtils.copyProperties(user , userDto);
        return userDto;
    }

    @Override
    public UserEntityDTO fromUserEntityWithLocalImage(UserEntity user, Picture picture) {
        UserEntityDTO userDto=new UserEntityDTO();
        userDto.setImageContentBase64(picture.getPictureContent());
        BeanUtils.copyProperties(user , userDto);
        return userDto;
    }

    @Override
    public UserEntity fromUserEntityDTO(UserEntityDTO userDto) {
        UserEntity user=new UserEntity();
        BeanUtils.copyProperties(userDto , user);
        return user;
    }


    @Override
    public UserAddress fromAddressDTO(AddressDTO addressDTO) {
        UserAddress address=new UserAddress();
        BeanUtils.copyProperties(addressDTO,address);
        return address;
    }

    @Override
    public AddressDTO fromAddress(UserAddress address) {
        AddressDTO addressDTO=new AddressDTO();
        BeanUtils.copyProperties(address,addressDTO);
        return addressDTO;
    }
    private String getToken(){
        KeycloakSecurityContext context = (KeycloakSecurityContext) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String token ="bearer "+ context.getTokenString();
        return token;
    }
}
