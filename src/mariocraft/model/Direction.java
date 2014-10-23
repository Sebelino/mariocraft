package mariocraft.model;

/**
 * Represents one of the eight ordinal directions.
 * 
 * @author Sebastian Olsson
 * @version 2011-04-27
 */
public enum Direction {
    LEFT{
        public Direction back() {
            return RIGHT;
        }
    },
    RIGHT{
        public Direction back() {
            return LEFT;
        }
    },
    UP{
        public Direction back() {
            return DOWN;
        }
    },
    DOWN{
        public Direction back() {
            return UP;
        }
    },
    DOWN_LEFT{
        public Direction back() {
            return UP_RIGHT;
        }
    },
    DOWN_RIGHT{
        public Direction back() {
            return UP_LEFT;
        }
    },
    UP_LEFT{
        public Direction back() {
            return DOWN_RIGHT;
        }
    },
    UP_RIGHT{
        public Direction back() {
            return DOWN_LEFT;
        }
    };
    
    /**
     * @return Opposite direction
     */
    public abstract Direction back();
}
