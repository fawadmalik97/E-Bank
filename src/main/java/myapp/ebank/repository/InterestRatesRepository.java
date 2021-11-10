package myapp.ebank.repository;


import java.sql.Date;
import java.util.List;
import java.util.Optional;

import myapp.ebank.model.entity.ForeignExchangeRates;
import myapp.ebank.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import myapp.ebank.model.entity.InterestRates;

@Repository
public interface InterestRatesRepository extends JpaRepository<InterestRates, Long> {

    @Query(value = "SELECT * FROM interest_rates where date like CONCAT(:date,'%')", nativeQuery = true)
    Optional<InterestRates> findByDateLike(Date date);

    @Query(value = "SELECT * from interest_rates where date >= :startDate order by date asc", nativeQuery = true)
    Optional<InterestRates> findByStartDate(java.util.Date startDate);

    List<InterestRates> findByCreatedDateBetweenOrderByCreatedDateDesc(java.util.Date startDate, java.util.Date endDate);

    List<InterestRates> findAllByActiveOrderByCreatedDateDesc(boolean status);

}
