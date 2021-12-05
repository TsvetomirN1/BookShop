package finalproject.bookshop.service.impl;

import finalproject.bookshop.model.entity.UserEntity;
import finalproject.bookshop.model.entity.UserRoleEntity;
import finalproject.bookshop.model.entity.enums.UserRoleEnum;
import finalproject.bookshop.model.service.UserRegisterServiceModel;
import finalproject.bookshop.model.service.UserUpdateServiceModel;
import finalproject.bookshop.repository.UserRepository;
import finalproject.bookshop.repository.UserRoleRepository;
import finalproject.bookshop.service.UserService;
import finalproject.bookshop.web.exceptions.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void initializeUsersAndRoles() {
        initializeRoles();
        initializeUsers();

    }

    private void initializeUsers() {
        if (userRepository.count() == 0) {

            UserRoleEntity adminRole = userRoleRepository.findByRole(UserRoleEnum.ADMIN);
            UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER);

            UserEntity admin = new UserEntity();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("test"));
            admin.setFirstName("Admin");
            admin.setLastName("Adminov");
            admin.setEmail("aaaa@abg.bg");
            admin.setRoles(Set.of(adminRole, userRole));
            userRepository.save(admin);

            UserEntity tosho = new UserEntity();
            tosho.setUsername("tosho");
            tosho.setPassword(passwordEncoder.encode("test"));
            tosho.setFirstName("Tosho");
            tosho.setLastName("Toshev");
            tosho.setEmail("toshko@bgz.bg");
            tosho.setRoles(Set.of(userRole));
            userRepository.save(tosho);
        }
    }

    private void initializeRoles() {

        if (userRoleRepository.count() == 0) {
            UserRoleEntity adminRole = new UserRoleEntity();
            adminRole.setRole(UserRoleEnum.ADMIN);

            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setRole(UserRoleEnum.USER);

            userRoleRepository.saveAll(List.of(adminRole, userRole));
        }
    }


    @Override
    public void registerAndLoginUser(UserRegisterServiceModel userRegisterServiceModel) {


        UserEntity newUser = modelMapper.map(userRegisterServiceModel, UserEntity.class);

        newUser.setPassword(passwordEncoder.encode(userRegisterServiceModel.getPassword()));

        UserRoleEntity userRole = userRoleRepository.findByRole(UserRoleEnum.USER);
        newUser.setRoles(Set.of(userRole));

        newUser = userRepository.save(newUser);

        UserDetails principal = userDetailsService.loadUserByUsername(newUser.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                newUser.getPassword(),
                principal.getAuthorities()
        );

        SecurityContextHolder.
                getContext().
                setAuthentication(authentication);
    }

    @Override
    public boolean isEmailFree(String email) {

        return userRepository.findByEmail(email).isEmpty();
    }

    @Override
    public UserUpdateServiceModel updateUser(UserUpdateServiceModel userUpdateServiceModel, Long id) {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id " + id + " not exists."));

        userEntity.setUsername(userUpdateServiceModel.getUsername());
        userEntity.setFirstName(userUpdateServiceModel.getFirstName());
        userEntity.setLastName(userUpdateServiceModel.getLastName());
        userEntity.setEmail(userUpdateServiceModel.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userUpdateServiceModel.getPassword()));

        UserEntity savedUserEntity = userRepository.save(userEntity);

        return modelMapper.map(savedUserEntity, UserUpdateServiceModel.class);

    }


    @Override
    public boolean isAuthorize(String name, Long id) {

        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(id));

        return user.getUsername().isEmpty();
    }

    @Override
    public UserUpdateServiceModel findLoggedUserByName(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + " not exists."));
        return modelMapper.map(userEntity, UserUpdateServiceModel.class);
    }

    @Override
    public UserUpdateServiceModel findById(Long id) {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with id " + id + " not exists."));
        return modelMapper.map(userEntity, UserUpdateServiceModel.class);
    }


    @Override
    public boolean isUserNameFree(String username) {
        return userRepository.findByUsername(username).isEmpty();
    }
}

