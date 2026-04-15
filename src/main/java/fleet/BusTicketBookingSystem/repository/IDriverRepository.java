package fleet.BusTicketBookingSystem.repository;

import fleet.BusTicketBookingSystem.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDriverRepositorty extends JpaRepository<Driver,Integer> {
}
