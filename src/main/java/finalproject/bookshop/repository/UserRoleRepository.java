package finalproject.bookshop.repository;

import finalproject.bookshop.model.entity.UserRoleEntity;
import finalproject.bookshop.model.entity.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    UserRoleEntity findByRole(UserRoleEnum role);

}
