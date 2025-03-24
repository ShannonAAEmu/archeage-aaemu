package com.aaemu.game.repository.dto.item;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Shannon
 */
@Data
public class SpawnDoodadItem {
    private int doodadId;
    private List<Integer> itemIds;

    public SpawnDoodadItem() {
        this.itemIds = new ArrayList<>();
    }
}
