package team.gsmgogo.domain.buttongame.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import team.gsmgogo.domain.buttongame.enums.ButtonType;
import team.gsmgogo.domain.buttongameparticipate.entity.ButtonGameParticipate;

import java.time.LocalDateTime;
import java.util.List;

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

    @Column(name = "create_date")
    @CreatedDate
    private LocalDateTime createDate;

    @OneToMany(mappedBy = "buttonType")
    private List<ButtonGameParticipate> participates;

    public void setWinType(ButtonType winType) {
        this.winType = winType;
    }

    public void endGame() {
        isActive = false;
    }
}
