package io.github.ardonplay.pbz.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class WaybillDTO {
    private int id;
    private Date date;
    private UUID customerID;
}
