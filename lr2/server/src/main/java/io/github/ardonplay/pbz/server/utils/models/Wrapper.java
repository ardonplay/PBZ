package io.github.ardonplay.pbz.server.utils.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Wrapper {
    private List<Object> items;
    private Long totalCount;
}
