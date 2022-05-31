package com.example.demo;

import java.sql.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = false)

public interface BdataRespository extends JpaRepository<bdata, Integer> {
	

    @Modifying
    @Query("delete from bdata where end_date < ?1")
    void deleteByedate(Date edate);
}
