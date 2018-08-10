package au.com.normist.capital.repository.cap.catalog;

import au.com.normist.capital.domain.cap.catalog.Stock;
import au.com.normist.capital.repository.cap.AdsConnDriver;
import au.com.normist.capital.repository.cap.AdsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

@Repository
@DependsOn("AdsConnDriver")
public class StockAdsRepository extends AdsRepository<Stock> implements IStockRepository {
    private static final Logger log = LoggerFactory.getLogger(StockAdsRepository.class);

    public StockAdsRepository(AdsConnDriver adsConnDriver) {
        super(adsConnDriver, Stock.class);

        try {
            Stock stk = super.getById("0000130758");
            log.info("got stock, location: " + stk.getLocation());
        } catch (Exception e) {
            log.error("unable to get stock: ", e);
        }
    }

}

