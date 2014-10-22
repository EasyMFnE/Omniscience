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

import com.mumfrey.liteloader.core.runtime.Obf;

/** Obfuscation reference helper. */
public class OmniscienceObf extends Obf {

  /** Obfuscation reference for Entity class. */
  public static OmniscienceObf ENTITY = new OmniscienceObf("net.minecraft.entity.Entity", "sa");

  /** Obfuscation reference for EntityPlayer class. */
  public static OmniscienceObf ENTITY_PLAYER = new OmniscienceObf(
      "net.minecraft.entity.player.EntityPlayer", "yz");

  /** Obfuscation reference for isInvisibleToPlayer(EntityPlayer) method. */
  public static OmniscienceObf IS_INVISIBLE_TO_PLAYER = new OmniscienceObf("func_98034_c", "d",
      "isInvisibleToPlayer");

  /**
   * @param seargeName Searge's name for it
   * @param obfName Obfuscated name for it
   */
  protected OmniscienceObf(String seargeName, String obfName) {
    super(seargeName, obfName);
  }

  /**
   * @param seargeName Searge's name for it
   * @param obfName Obfuscated name for it
   * @param mcpName MCP name for it
   */
  protected OmniscienceObf(String seargeName, String obfName, String mcpName) {
    super(seargeName, obfName, mcpName);
  }

}
