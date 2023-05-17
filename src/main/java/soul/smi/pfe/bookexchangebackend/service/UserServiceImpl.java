package soul.smi.pfe.bookexchangebackend.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soul.smi.pfe.bookexchangebackend.dao.entities.UserEntity;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.UserAddressRepo;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.UserEntityRepo;
import soul.smi.pfe.bookexchangebackend.dtos.*;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.mappers.Mapper;

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

    public UserServiceImpl(Mapper mapper, BookService bookService, UserAddressRepo userAddressRepo, UserEntityRepo userRepo) {
        this.mapper = mapper;
        this.bookService = bookService;
        this.userAddressRepo = userAddressRepo;
        this.userRepo = userRepo;
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
}
