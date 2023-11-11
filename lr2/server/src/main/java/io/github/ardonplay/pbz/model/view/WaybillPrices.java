package io.github.ardonplay.pbz.model.view;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.annotation.Immutable;

@Entity(name = "waybil_prices")
@Immutable
@Getter
public class WaybillPrices {

    @Id
    private int id;

    private String totalPrice;
}
