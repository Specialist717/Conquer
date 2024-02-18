package model;

public class Card {
    private int value;
    private boolean is_enemy;;

    public Card(int value, boolean is_enemy) {
        if (value < 1 || value > 18) {
            throw new IllegalArgumentException("Number must be between 1 and 18");
        }
        this.value = value;
        this.is_enemy = is_enemy;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setIs_enemy(boolean is_enemy) {
        this.is_enemy = is_enemy;
    }

    public int getValue() {
        return value;
    }

    public boolean getIsEnemy() {
        return is_enemy;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}