package io.github.ardonplay.pbz;

import io.github.ardonplay.pbz.model.table.Waybill;
import io.github.ardonplay.pbz.repository.table.WaybillRepository;
import io.github.ardonplay.pbz.services.impl.WaybillController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class PbzApplication {

    public static void main(String[] args) {

        SpringApplication.run(PbzApplication.class, args);

    }
}
