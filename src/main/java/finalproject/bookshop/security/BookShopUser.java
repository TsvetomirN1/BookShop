package finalproject.bookshop.security;

import finalproject.bookshop.model.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


public class BookShopUser extends User {

    public String firstname;
    public String lastName;
    private UserEntity userEntity;




    public BookShopUser(String username, String password,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);



    }

    public BookShopUser(String username, String password,  boolean enabled, boolean accountNonExpired,
                        boolean credentialsNonExpired, boolean accountNonLocked,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                authorities);

    }


    public String getUserIdentifier() {
        return getUsername();
    }

}
