package io.github.ardonplay.pbz.model.dto;

import io.github.ardonplay.pbz.model.enums.PersonType;
import lombok.*;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    @NonNull
    private UUID id;
    @NonNull
    private String name;
    @NonNull
    private PersonType type;

    private String address;

    private String phoneNumber;


    private BankDetailsDTO bankDetails;
}
