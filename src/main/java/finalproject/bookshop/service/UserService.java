package finalproject.bookshop.service;


import finalproject.bookshop.model.binding.UserUpdateBindingModel;
import finalproject.bookshop.model.entity.UserEntity;
import finalproject.bookshop.model.service.UserRegisterServiceModel;
import finalproject.bookshop.model.service.UserUpdateServiceModel;
import finalproject.bookshop.model.view.ProfileUpdateView;


public interface UserService {

    void initializeUsersAndRoles();

    boolean isUserNameFree(String username);

    void registerAndLoginUser(UserRegisterServiceModel serviceModel);

    boolean isEmailFree(String email);

    UserUpdateServiceModel updateUser(UserUpdateServiceModel userUpdateServiceModel, Long id);

    boolean isAuthorize(String name, Long id);

    UserUpdateServiceModel findLoggedUserByName(String username);

    UserUpdateServiceModel findById(Long id);


}
