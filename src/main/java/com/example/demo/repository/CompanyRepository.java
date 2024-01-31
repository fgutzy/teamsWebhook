package com.example.demo.repository;

import com.example.demo.Entity.Company;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CompanyRepository extends CrudRepository<Company, Integer> {
    @Modifying
    @Transactional
    @Query("UPDATE CompanyPost c SET c.lastPrice = :newLastPrice WHERE c.name = :companyName")
    void updateLastPriceByName(String newLastPrice, String companyName);

    @Modifying
    @Transactional
    @Query("UPDATE CompanyPost c SET c.dateOfLastChange = :newDate WHERE c.name = :companyName")
    void updateDateByName(String newDate, String companyName);

    Company findCompanyByName(String companyName);
}
