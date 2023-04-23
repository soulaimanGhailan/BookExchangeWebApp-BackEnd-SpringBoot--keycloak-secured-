package soul.smi.pfe.bookexchangebackend.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import soul.smi.pfe.bookexchangebackend.dao.entities.AdminUser;
import soul.smi.pfe.bookexchangebackend.dao.entities.RegisteredUser;
import soul.smi.pfe.bookexchangebackend.dao.entities.RegularUser;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.RegisteredUserRepo;
import soul.smi.pfe.bookexchangebackend.dao.reposotories.UserAddressRepo;
import soul.smi.pfe.bookexchangebackend.dtos.AddressDTO;
import soul.smi.pfe.bookexchangebackend.dtos.AdminUserDTO;
import soul.smi.pfe.bookexchangebackend.dtos.RegisteredUserDTO;
import soul.smi.pfe.bookexchangebackend.dtos.RegularUserDTO;
import soul.smi.pfe.bookexchangebackend.exeptions.UserNotFoundExeption;
import soul.smi.pfe.bookexchangebackend.mappers.mapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Data
@AllArgsConstructor
public class UseresServiceImpl implements UseresService {
    private mapping mapper;
    private BookService bookService;
    private RegisteredUserRepo registeredUserRepo;
    private UserAddressRepo userAddressRepo;



    @Override
    public RegularUserDTO findUser(String userId) throws UserNotFoundExeption {
        RegisteredUser user = registeredUserRepo.findById(userId).orElseThrow(() -> new UserNotFoundExeption("user not found"));
        if(user instanceof AdminUser){
            return mapper.fromAdminUser((AdminUser) user);
        }else
            return mapper.fromRegularUser((RegularUser) user);
    }

    @Override
    public List<RegisteredUserDTO> getAllUsers() {
        return registeredUserRepo.findAll().stream()
                .map(registeredUser -> {
                    if(registeredUser instanceof AdminUser){
                        return mapper.fromAdminUser((AdminUser) registeredUser);
                    }else
                        return mapper.fromRegularUser((RegularUser) registeredUser);
                }).collect(Collectors.toList());
    }

    @Override
    public void updateUser(RegisteredUserDTO registeredUserDTO) {
        RegisteredUser registeredUser ;
        if(registeredUserDTO instanceof AdminUserDTO){
            registeredUser = mapper.fromAdminUserDTO((AdminUserDTO) registeredUserDTO);

        }else {
            registeredUser=mapper.fromRegularUserDTO((RegularUserDTO) registeredUserDTO);
        }
        registeredUserRepo.save(registeredUser);
    }

    @Override
    public void deleteUser(String userId) throws UserNotFoundExeption {
        bookService.deleteAllBooksOfUser(userId);
        // we may want to delete address of this user
        registeredUserRepo.deleteById(userId);
    }



    @Override
    public byte[] getUserProfilePic(String id) throws UserNotFoundExeption {
        RegisteredUser registeredUser = registeredUserRepo.findById(id).orElseThrow(() -> new UserNotFoundExeption("user not found"));
        return registeredUser.getProfilePic().getPictureContent();
    }



    @Override
    public AddressDTO getAddress(String id) throws UserNotFoundExeption {
        RegisteredUser registeredUser = registeredUserRepo.findById(id).orElseThrow(() ->new UserNotFoundExeption("use not found"));
        return mapper.fromAddress(registeredUser.getAddress());

    }
}
