package team.gsmgogo.domain.buttongame.entity;

import jakarta.persistence.*;
import lombok.*;
import team.gsmgogo.domain.buttongame.enums.ButtonType;

@Entity
@Table(name = "button_game")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class ButtonGameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "win_type")
    @Enumerated(EnumType.STRING)
    private ButtonType winType;
}
