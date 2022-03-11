package com.cardozo.multidb;

import com.cardozo.multidb.services.MainService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

@Service
public class Runner implements ApplicationRunner {
    private final MainService mainService;

    public Runner(MainService mainService) {
        this.mainService = mainService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        mainService.run();
    }
}
