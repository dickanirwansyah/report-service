package id.corp.app.reportservice.repository;

import id.corp.app.reportservice.entity.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsRepository extends JpaRepository<Accounts, Long>,
        JpaSpecificationExecutor<Accounts> {
}
