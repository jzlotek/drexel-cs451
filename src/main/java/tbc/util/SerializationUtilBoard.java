package tbc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import tbc.client.components.Board;

import java.io.IOException;

public class SerializationUtilBoard {

    private SerializationUtilJSON serializer;

    public SerializationUtilBoard() {
        this.serializer = new SerializationUtilJSON();
    }

    public Board deserialize(String board) throws IOException {
        return (Board)this.serializer.deserialize(board, Board.class);
    }

    public String serialize(Board board) throws JsonProcessingException {
        return this.serializer.serialize(board);
    }
}
