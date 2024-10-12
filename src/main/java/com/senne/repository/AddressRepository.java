package com.senne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.senne.modal.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
    
}
