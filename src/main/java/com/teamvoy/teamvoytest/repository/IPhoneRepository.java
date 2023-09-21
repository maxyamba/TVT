package com.teamvoy.teamvoytest.repository;

import com.teamvoy.teamvoytest.model.IPhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPhoneRepository extends JpaRepository<IPhone, Integer> {
}
