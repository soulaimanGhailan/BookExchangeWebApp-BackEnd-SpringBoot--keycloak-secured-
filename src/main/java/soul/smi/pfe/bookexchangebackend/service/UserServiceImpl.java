package soul.smi.pfe.bookexchangebackend.service;

import lombok.Data;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soul.smi.pfe.bookexchangebackend.dao.entities.Picture;
import soul.smi.pfe.bookexchangebackend.dao.entities.UserAddress;
import soul.smi.pfe.bookexchangebackend.dao.entities.UserEntity;
import soul.smi.pfe.bookexchangebackend.dao.enums.UpdateUserFieldType;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.PictureRepo;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.UserAddressRepo;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.UserEntityRepo;
import soul.smi.pfe.bookexchangebackend.dtos.*;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.mappers.Mapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Data
public class UserServiceImpl implements UserService {
    private Mapper mapper;
    private BookService bookService;
    private UserAddressRepo userAddressRepo;
    private UserEntityRepo userRepo ;
    private PictureRepo pictureRepo;
    private PicturesService picturesService;

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

    public UserServiceImpl(Mapper mapper, BookService bookService, UserAddressRepo userAddressRepo,
                           UserEntityRepo userRepo ,  PictureRepo pictureRepo , PicturesService picturesService) {
        this.mapper = mapper;
        this.bookService = bookService;
        this.userAddressRepo = userAddressRepo;
        this.userRepo = userRepo;
        this.pictureRepo = pictureRepo;
        this.picturesService = picturesService;
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
                try {
                    user.setProfilePic(createImage("initImages/book/profile/defaultProfile.jpg" , "defaultProfile"  ));
                } catch (IOException e) {
                    throw new RuntimeException("init default exception");
                }
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
                return mapper.fromUserEntity(userRepo.save(user));
            case UpdateCOUNTRY: user.getAddress().setCountry(fieldOfUser.getData());
                return mapper.fromUserEntity(userRepo.save(user));
            case UpdateHOMEADDRESS: user.getAddress().setHomeAddress(fieldOfUser.getData());
                return mapper.fromUserEntity(userRepo.save(user));
            case UpdatePhoneNum: user.setPhoneNumber(fieldOfUser.getData());
                return mapper.fromUserEntity(userRepo.save(user));
            case UpdatePICTURE: user.setProfilePic(picturesService.upDatePicture(user.getProfilePic().getId()  ,user.getUsername() , fieldOfUser.getData()));
                return mapper.fromUserEntity(userRepo.save(user));
            default : return mapper.fromUserEntity(user);
        }
    }

    @Override
    public UserEntityDTO findUser(String userId) throws UserNotFoundExeption {
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundExeption("user not found"));
        return mapper.fromUserEntity(user);
    }

    @Override
    public List<UserEntityDTO> getAllUsers() {
        return userRepo.findAll().stream()
                .map(user -> mapper.fromUserEntity(user))
                .collect(Collectors.toList());
    }

    @Override
    public byte[] getUserProfilePic(String id) throws UserNotFoundExeption {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundExeption("user not found"));
        return user.getProfilePic().getPictureContent();
    }



    @Override
    public AddressDTO getAddress(String id) throws UserNotFoundExeption {
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundExeption("user not found"));
        return mapper.fromAddress(user.getAddress());

    }
    private Picture createImage(String pathName , String name)throws IOException {
        File file=new File(pathName);
        Path path= Paths.get(file.toURI());
        byte[] picContent = Files.readAllBytes(path);
        Picture picture1 = new Picture();
        picture1.setPictureContent(picContent);
        picture1.setAddingDate(new Date());
        picture1.setPictureName(name);
        return pictureRepo.save(picture1);
    }
}
