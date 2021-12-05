package finalproject.bookshop.repository;


import finalproject.bookshop.model.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity,Long>{

    Optional<ImageEntity> findTopByUrl(String url);
    //select *from getLastRecord ORDER BY id DESC LIMIT 1
 //@Query("SELECT s FROM Students s ORDER BY s.id DESC LIMIT 1", nativeQuery=true)
    // Students getLastStudentDetails();



}
