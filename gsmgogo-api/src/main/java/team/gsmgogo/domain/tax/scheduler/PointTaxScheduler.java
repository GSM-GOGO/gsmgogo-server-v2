package team.gsmgogo.domain.tax.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import team.gsmgogo.domain.tax.service.ShareTaxPointService;
import team.gsmgogo.domain.tax.service.TaxCollectionService;
import team.gsmgogo.domain.tax.service.dto.TaxCollectionServiceReturnInfoDto;

@Component
@RequiredArgsConstructor
public class PointTaxScheduler {

    private final TaxCollectionService taxCollectionService;
    private final ShareTaxPointService shareTaxPointService;

    @Scheduled(cron = "0 0 4 * * *")
    public void shareTaxPointSchedulerMethod(){
        TaxCollectionServiceReturnInfoDto dto = taxCollectionService.taxCollection();
        shareTaxPointService.shareTaxPoint(dto);
    }
}
