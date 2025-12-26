package com.anthony.blacksmithOnlineStore.repository;

import com.anthony.blacksmithOnlineStore.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

  Page<Item> findByCraftedById(Long blacksmithId, Pageable pageable);

  @Modifying
  @Query("""
        UPDATE Item i
        SET i.stock = i.stock - :qty
        WHERE i.id = :itemId AND i.stock >= :qty
    """)
  int decrementStock(Long productId, int qty);

  @Modifying
  @Query("""
        UPDATE Item i
        SET i.stock = i.stock + :qty
        WHERE i.id = :itemId
    """)
  int incrementStock(Long productId, int qty);
}
