package swp490.spa.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import swp490.spa.entities.Category;
import swp490.spa.repositories.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> findAllByStatus(String status, Pageable pageable){
        return this.categoryRepository.findByStatus(status, pageable);
    }

//    public List<Category> findAllByStatus(String status){
//        return this.categoryRepository.findByStatus(status);
//    }
}
