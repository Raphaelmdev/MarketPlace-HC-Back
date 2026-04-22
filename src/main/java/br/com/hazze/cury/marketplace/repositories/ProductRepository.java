package br.com.hazze.cury.marketplace.repositories;

import br.com.hazze.cury.marketplace.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByActiveTrue();

    boolean existsByName(String name);
}
