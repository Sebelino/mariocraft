package mariocraft.model;

import java.util.HashSet;

/**
 * Contains information about a level.
 * 
 * @author Sebastian Olsson
 * @version 2011-04-22
 */
public class LevelDesigner {
    Protagonist protagonist;
    BlockManager blocks;
    HashSet<Ball> balls;
    HashSet<Spikes> spikes;
    HashSet<MovingPlatform> movingPlatforms;
    Goal goal;
}
