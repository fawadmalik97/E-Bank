package myapp.ebank.repository;

import myapp.ebank.model.entity.Funds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundRepository extends JpaRepository<Funds,Long> {


}
