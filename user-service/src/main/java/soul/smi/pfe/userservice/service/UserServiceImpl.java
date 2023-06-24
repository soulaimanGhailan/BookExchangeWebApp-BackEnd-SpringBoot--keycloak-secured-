package soul.smi.pfe.userservice.service;

import lombok.Data;

import org.keycloak.KeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soul.smi.pfe.userservice.dao.entities.UserAddress;
import soul.smi.pfe.userservice.dao.entities.UserEntity;
import soul.smi.pfe.userservice.dao.enums.UpdateUserFieldType;
import soul.smi.pfe.userservice.dao.reposotories.UserAddressRepo;
import soul.smi.pfe.userservice.dao.reposotories.UserEntityRepo;
import soul.smi.pfe.userservice.dtos.AddressDTO;
import soul.smi.pfe.userservice.dtos.UpdateFieldOfUser;
import soul.smi.pfe.userservice.dtos.UserEntityDTO;
import soul.smi.pfe.userservice.exeptions.UserNotFoundExeption;
import soul.smi.pfe.userservice.mappers.Mapper;
import soul.smi.pfe.userservice.model.Picture;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Data
public class UserServiceImpl implements UserService {
    private Mapper mapper;
    private UserAddressRepo userAddressRepo;
    private UserEntityRepo userRepo ;
    private PictureRestClient pictureRestClient ;
    private final String ADMIN_PASSWORD = "admin";
    private final String ADMIN_USERNAME = "admin";
    private final String KEYCLOAK_URL = "http://localhost:8080/";
    Keycloak keycloak = KeycloakBuilder.builder()
            .serverUrl(KEYCLOAK_URL)
            .realm("master")
            .clientId("admin-cli")
            .grantType("password")
            .username(ADMIN_USERNAME)
            .password(ADMIN_PASSWORD)
            .build();

    public UserServiceImpl(Mapper mapper, UserAddressRepo userAddressRepo,
                           UserEntityRepo userRepo , PictureRestClient pictureRestClient) {
        this.mapper = mapper;
        this.userAddressRepo = userAddressRepo;
        this.userRepo = userRepo;
        this.pictureRestClient= pictureRestClient;
    }

    public void syncUsers() {
        List<UserRepresentation> keycloakUsers = keycloak.realm("bookRealm").users().list();
        for (UserRepresentation keycloakUser : keycloakUsers) {
            Optional<UserEntity> optionalUser = userRepo.findById(keycloakUser.getId());
            UserEntity user ;
            if(optionalUser.isPresent()){
                // updating the user
                user = optionalUser.get();
            }else {
                // initializing user info
                user = new UserEntity();
                user.setPictureId(1L);
                user.setAddress(userAddressRepo.save(new UserAddress()));
            }
            user.setUsername(keycloakUser.getUsername());
            user.setEmail(keycloakUser.getEmail());
            user.setUserId(keycloakUser.getId());
            user.setFirstname(keycloakUser.getFirstName());
            user.setLastname(keycloakUser.getLastName());
            userRepo.save(user);

        }
    }

    @Override
    public UserEntityDTO updateUser(UpdateFieldOfUser fieldOfUser) throws UserNotFoundExeption {
        UserEntity user = userRepo.findById(fieldOfUser.getUserId()).orElseThrow(() -> new UserNotFoundExeption("user Not Found"));
        UpdateUserFieldType fieldType = UpdateUserFieldType.valueOf(fieldOfUser.getUpdateUserFieldType());
        switch (fieldType){
            case UpdateCITY: user.getAddress().setCity(fieldOfUser.getData());
                return mapper.fromUserEntityWithRestImage(userRepo.save(user));
            case UpdateCOUNTRY: user.getAddress().setCountry(fieldOfUser.getData());
                return mapper.fromUserEntityWithRestImage(userRepo.save(user));
            case UpdateHOMEADDRESS: user.getAddress().setHomeAddress(fieldOfUser.getData());
                return mapper.fromUserEntityWithRestImage(userRepo.save(user));
            case UpdatePhoneNum: user.setPhoneNumber(fieldOfUser.getData());
                return mapper.fromUserEntityWithRestImage(userRepo.save(user));
            case UpdatePICTURE:
                Picture picture = pictureRestClient.updatePicture(getToken(), fieldOfUser.getData(), user.getPictureId(), user.getUsername());
                System.out.println(picture.getId());
                user.setPictureId(picture.getId());
                return mapper.fromUserEntityWithLocalImage(userRepo.save(user) , picture);
            default : return mapper.fromUserEntityWithRestImage(user);
        }
    }

    @Override
    public UserEntityDTO findUserAllowed(String id) throws UserNotFoundExeption {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundExeption("user not found"));
        return mapper.fromUserEntityWithRestImageAllowed(user);
    }

    @Override
    public UserEntityDTO findUser(String userId) throws UserNotFoundExeption {
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundExeption("user not found"));
        return mapper.fromUserEntityWithRestImage(user);
    }


    @Override
    public AddressDTO getAddress(String id) throws UserNotFoundExeption {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundExeption("user not found"));
        return mapper.fromAddress(user.getAddress());

    }

    @Override
    public Long getNumberOfUsers() {
        return userRepo.count() ;
    }

    private String getToken(){
        KeycloakSecurityContext context = (KeycloakSecurityContext) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String token ="bearer "+ context.getTokenString();
        return token;
    }

}
