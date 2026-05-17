package com.moodly.product.config;

import com.moodly.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * products INSERT는 id 없이 들어가 INSERT IGNORE로 중복 방지가 되지 않는다.
 * DB에 상품이 없을 때만 classpath:data.sql 을 한 번 실행한다.
 */
@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class ProductDataSeedRunner implements ApplicationRunner {

    private final ProductRepository productRepository;
    private final DataSource dataSource;

    @Override
    public void run(ApplicationArguments args) {
        if (productRepository.count() > 0) {
            log.debug("Product seed skipped: {} product(s) already exist", productRepository.count());
            return;
        }

        log.info("Product seed: loading classpath:data.sql (empty products table)");
        var populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("data.sql"));
        populator.setContinueOnError(false);
        populator.execute(dataSource);
        log.info("Product seed completed: {} product(s)", productRepository.count());
    }
}
