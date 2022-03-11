package com.cardozo.multidb.repositories.maindatabase;

import com.cardozo.multidb.models.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
}
