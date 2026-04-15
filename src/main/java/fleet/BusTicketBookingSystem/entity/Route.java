package fleet.BusTicketBookingSystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="route")
@Data
public class Route {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer routeId;

    private String routeName;

    private float distance;
}
