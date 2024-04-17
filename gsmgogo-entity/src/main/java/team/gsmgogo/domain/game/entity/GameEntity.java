package team.gsmgogo.domain.game.entity;

import jakarta.persistence.*;
import lombok.*;
import team.gsmgogo.domain.user.entity.UserEntity;

@Entity
@Table(name = "game")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_Id")
    private Long gameId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_b")
    private UserEntity user;

    @Column(name = "coin_toss")
    private Integer coinToss;

    @Column(name = "daily_roulette")
    private Boolean dailyRoulette;

    public void rollDailyRoulette() {
        this.dailyRoulette = true;
    }

    public void addCoinTossCount(){ this.coinToss++; }
}
