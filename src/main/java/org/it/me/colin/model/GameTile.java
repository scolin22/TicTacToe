package org.it.me.colin.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameTile {
    Tile tile;
    int x;
    int y;
}
