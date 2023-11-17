package io.github.ardonplay.pbz.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class DestinationDTO {
    @NonNull
    private String name;

    private String region;
    private String country;
}
