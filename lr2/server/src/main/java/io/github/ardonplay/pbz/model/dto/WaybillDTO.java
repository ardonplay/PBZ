package io.github.ardonplay.pbz.model.dto;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class WaybillDTO {
    @NonNull
    private Integer id;
    @NonNull
    private Date date;
    @NonNull
    private UUID customerID;
    @NonNull
    private DestinationDTO destination;

    private List<WaybillProductDTO> waybillProducts;
}
