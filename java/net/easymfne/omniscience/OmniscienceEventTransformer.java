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

import com.mumfrey.liteloader.transformers.event.Event;
import com.mumfrey.liteloader.transformers.event.EventInjectionTransformer;
import com.mumfrey.liteloader.transformers.event.MethodInfo;
import com.mumfrey.liteloader.transformers.event.inject.MethodHead;

/**
 * Event injection to allow modification of the return value of the isInvisibleToPlayer method in
 * Entity and EntityPlayer classes.
 */
public class OmniscienceEventTransformer extends EventInjectionTransformer {

  /** Add Event for modifying entity visibility. */
  private void addEntityVisibilityCheckEvent() {
    addEvent(
        Event.getOrCreate("Omniscience_Entity_isInvisibleToPlayer", true),
        new MethodInfo(OmniscienceObf.ENTITY, OmniscienceObf.IS_INVISIBLE_TO_PLAYER, Boolean.TYPE,
            OmniscienceObf.ENTITY_PLAYER), new MethodHead()).addListener(
        new MethodInfo("net.easymfne.omniscience.LiteModOmniscience", "adjustVisibility"));
  }

  @Override
  protected void addEvents() {
    addEntityVisibilityCheckEvent();
    addPlayerVisibilityCheckEvent();
    addHighlightHotkeyTweakEvent();
  }
  
  /** Add Event for allowing usage of entity-highlight keybind in any gamemode. */
  private void addHighlightHotkeyTweakEvent() {
    addEvent(
        Event.getOrCreate("Omniscience_RenderGlobal_isRenderEntityOutlines", true),
        new MethodInfo(OmniscienceObf.RENDER_GLOBAL, OmniscienceObf.IS_RENDER_ENTITY_OUTLINES, Boolean.TYPE),
        new MethodHead()).addListener(
            new MethodInfo("net.easymfne.omniscience.LiteModOmniscience", "adjustHighlight"));
  }

  /** Add Event for modifying player visibility. */
  private void addPlayerVisibilityCheckEvent() {
    addEvent(
        Event.getOrCreate("Omniscience_EntityPlayer_isInvisibleToPlayer", true),
        new MethodInfo(OmniscienceObf.ENTITY_PLAYER, OmniscienceObf.IS_INVISIBLE_TO_PLAYER,
            Boolean.TYPE, OmniscienceObf.ENTITY_PLAYER), new MethodHead()).addListener(
        new MethodInfo("net.easymfne.omniscience.LiteModOmniscience", "adjustVisibility"));
  }

}
