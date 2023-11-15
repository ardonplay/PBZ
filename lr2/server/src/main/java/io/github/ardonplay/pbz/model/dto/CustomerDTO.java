package io.github.ardonplay.pbz.model.dto;

import io.github.ardonplay.pbz.model.enums.PersonType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CustomerDTO {
    private UUID id;
    private String name;
    private PersonType type;
}
