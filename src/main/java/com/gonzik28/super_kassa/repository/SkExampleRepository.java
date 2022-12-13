package com.gonzik28.super_kassa.repository;

import com.gonzik28.super_kassa.entity.SkExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface SkExampleRepository extends JpaRepository<SkExampleEntity, Integer> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<SkExampleEntity> findWithLockingById(Integer id);
}
