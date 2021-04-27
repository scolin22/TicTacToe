package org.it.me.colin.model;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GameResponse {
    @SerializedName(value = "game_id")
    String gameId;

    @SerializedName(value = "gameboard")
    List<GameTile> gameBoard;

    Status status;

    Winner winner;
}
