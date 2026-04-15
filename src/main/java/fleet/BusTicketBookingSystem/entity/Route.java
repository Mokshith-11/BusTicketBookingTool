package fleet.BusTicketBookingSystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "routes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "route_id")
    private Integer routeId;

    @NotBlank(message = "From city is required")
    @Column(name = "from_city", nullable = false)
    private String fromCity;

    @NotBlank(message = "To city is required")
    @Column(name = "to_city", nullable = false)
    private String toCity;

    @Column(name = "break_points")
    private Integer breakPoints;

    @Column(name = "duration")
    private Integer duration;
}