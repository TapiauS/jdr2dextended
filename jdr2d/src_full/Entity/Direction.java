package Entity;

public enum Direction {
    NORD,
    SUD,
    EST,
    OUEST;

    public Direction opposite(){
        switch (this){
            case NORD : return Direction.SUD;
            case SUD  : return Direction.NORD;
            case EST  : return Direction.OUEST;
            case OUEST: return Direction.EST;
        }
        return null;
    }
}
