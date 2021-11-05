package myapp.ebank.repository;



import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myapp.ebank.model.entity.InterestRates;

@Repository
public interface InterestRatesRepository extends JpaRepository<InterestRates, Long> {
	
 Optional<InterestRates> findByDateLike(String date);


}
