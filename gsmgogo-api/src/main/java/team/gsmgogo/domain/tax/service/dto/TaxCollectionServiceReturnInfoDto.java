package team.gsmgogo.domain.tax.service.dto;

import lombok.Builder;
import team.gsmgogo.domain.user.entity.UserEntity;

import java.util.List;

@Builder
public record TaxCollectionServiceReturnInfoDto(
        List<UserEntity> users,
        Integer allPoints,
        Integer allTaxes
) {
}
