package be.mve.tsm.repository;

import be.mve.tsm.domain.Timesheet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Timesheet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {

}
