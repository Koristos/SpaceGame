package ru.fefelov.movement;

import com.badlogic.gdx.math.Vector2;

public class MovementHelper {
    static Vector2 move = new Vector2();
    public static void calculateMovement (Vector2 position, Vector2 destination){
        move=destination.cpy().sub(position);
        if (move.len()>1){
            move.nor();
        }
        position.add(move);
    }
}
