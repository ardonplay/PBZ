package io.github.ardonplay.pbz.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Builder
public class DestinationDTO {
    private Integer id;

    @NonNull
    private String name;

    private String region;
    private String country;
}
