/**
 * This file is part of Omniscience by Eric Hildebrand.
 * 
 * Omniscience is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * Omniscience is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Omniscience. If not,
 * see <http://www.gnu.org/licenses/>.
 */
package net.easymfne.omniscience;

import net.minecraft.entity.player.EntityPlayer;

import com.mumfrey.liteloader.transformers.event.Event;
import com.mumfrey.liteloader.transformers.event.EventInjectionTransformer;
import com.mumfrey.liteloader.transformers.event.MethodInfo;
import com.mumfrey.liteloader.transformers.event.inject.MethodHead;
import com.mumfrey.liteloader.util.log.LiteLoaderLogger;

/**
 * Event injection to allow modification of the return value of the isInvisibleToPlayer method in
 * Entity and EntityPlayer classes.
 */
public class OmniscienceEventTransformer extends EventInjectionTransformer {

  /** Add Event for modifying entity visibility. */
  private void addEntityVisibilityCheckEvent() {
    addEvent(
        Event.getOrCreate("Omniscience_Entity_isInvisibleToPlayer", true, 9),
        new MethodInfo(OmniscienceObf.ENTITY, OmniscienceObf.IS_INVISIBLE_TO_PLAYER, Boolean.TYPE,
            OmniscienceObf.ENTITY_PLAYER), new MethodHead()).addListener(
        new MethodInfo("net.easymfne.omniscience.LiteModOmniscience", "adjustEntityVisibility"));
  }

  @Override
  protected void addEvents() {
    addEntityVisibilityCheckEvent();
    addPlayerVisibilityCheckEvent();
  }

  /** Add Event for modifying player visibility. */
  private void addPlayerVisibilityCheckEvent() {
    addEvent(
        Event.getOrCreate("Omniscience_EntityPlayer_isInvisibleToPlayer", true, 9),
        new MethodInfo(OmniscienceObf.ENTITY_PLAYER, OmniscienceObf.IS_INVISIBLE_TO_PLAYER,
            Boolean.TYPE, OmniscienceObf.ENTITY_PLAYER), new MethodHead()).addListener(
        new MethodInfo("net.easymfne.omniscience.LiteModOmniscience", "adjustPlayerVisibility"));
  }

}
