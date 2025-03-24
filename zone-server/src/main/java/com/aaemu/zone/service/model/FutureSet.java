package com.aaemu.zone.service.model;

import lombok.Data;

/**
 * @author Shannon
 */
@Data
public class FutureSet {
    private boolean siege;

    public String build() {
        if (siege) {
            return "1";
        }
        return "0";
    }
}
