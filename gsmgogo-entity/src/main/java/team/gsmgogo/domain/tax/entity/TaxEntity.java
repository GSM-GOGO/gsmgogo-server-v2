package team.gsmgogo.domain.tax.entity;

import jakarta.persistence.*;
import lombok.*;
import team.gsmgogo.domain.user.entity.UserEntity;

import java.time.LocalDate;

@Entity
@Table(name = "tax")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@ToString
public class TaxEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tax_id")
    private Long taxId;

    @Column(name = "tax_point")
    private Integer taxPoint;

    @Column(name = "local_date")
    private LocalDate localDate = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
