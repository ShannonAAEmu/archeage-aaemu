package com.aaemu.stream.service.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StreamCharacterDto {

    @JsonProperty("account_id")
    private long accountId;
}
