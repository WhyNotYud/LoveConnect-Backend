package com.website.loveconnect.repository.custom;

import jakarta.persistence.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserRepositoryCustom {
    Tuple getUserById(Integer idUser);
    Tuple getUserForUpdateById(Integer idUser);
    Page<Tuple> getAllUserByFilters(String status, String gender, String sort, String keyword, Pageable pageable);
}
