package be.mve.tsm.repository;

import be.mve.tsm.domain.TimesheetLabel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TimesheetLabel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimesheetLabelRepository extends JpaRepository<TimesheetLabel, Long> {

}
