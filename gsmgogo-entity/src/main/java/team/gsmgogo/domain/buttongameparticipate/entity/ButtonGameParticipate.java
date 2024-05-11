package team.gsmgogo.domain.buttongameparticipate.entity;

import jakarta.persistence.*;
import lombok.*;
import team.gsmgogo.domain.buttongame.entity.ButtonGameEntity;
import team.gsmgogo.domain.buttongame.enums.ButtonType;
import team.gsmgogo.domain.user.entity.UserEntity;

@Entity
@Table(name = "button_game_participate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class ButtonGameParticipate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @Column(name = "button_game_id")
    private ButtonGameEntity buttonGame;

    @ManyToOne
    @Column(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "button_type")
    private ButtonType buttonType;
}
