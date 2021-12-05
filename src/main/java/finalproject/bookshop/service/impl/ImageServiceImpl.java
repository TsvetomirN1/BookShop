package finalproject.bookshop.service.impl;

import finalproject.bookshop.model.entity.ImageEntity;
import finalproject.bookshop.model.service.BookServiceModel;
import finalproject.bookshop.repository.ImageRepository;
import finalproject.bookshop.service.ImageService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;


    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;

    }

    @Override
    public ImageEntity findByUrl(String url) {
        return imageRepository.findTopByUrl(url)
                .orElse(new ImageEntity()) ;

    }

    @Override
    public ImageEntity findByID(Long id) {
        return imageRepository.findById(id)
                .orElse(new ImageEntity());
    }

    @Override
    public void addImage(BookServiceModel bookAddServiceModel) {

        ImageEntity image = new ImageEntity();
        image.setId(bookAddServiceModel.getId());
        image.setUrl(bookAddServiceModel.getImage());
        imageRepository.save(image);

    }


}

