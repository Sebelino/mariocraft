package mariocraft.model.attributes;

import mariocraft.model.Entity;

/**
 * Objects that are corporal cannot move through each other.
 * 
 * @author Sebastian Olsson
 * @version 2011-04-14
 */
public interface Corporal {
    
    /**
     * @param The entity which may be intersecting this entity
     * @return True iff the corporal object intersects the entity
     */
    boolean intersects(Entity entity);
}
