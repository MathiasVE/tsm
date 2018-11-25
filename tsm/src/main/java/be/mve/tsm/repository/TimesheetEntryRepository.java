package be.mve.tsm.repository;

import be.mve.tsm.domain.TimesheetEntry;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TimesheetEntry entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimesheetEntryRepository extends JpaRepository<TimesheetEntry, Long> {

}
