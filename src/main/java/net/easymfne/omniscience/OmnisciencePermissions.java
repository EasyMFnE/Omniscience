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

import com.mumfrey.liteloader.Permissible;
import com.mumfrey.liteloader.permissions.PermissionsManagerClient;

/**
 * Permissions helper class for use with systems such as ClientPermissions.
 * 
 * @since 1.0.1
 */
public class OmnisciencePermissions {

  /** Permission for full access to the modification. */
  public static final String STAR = "*";
  /** Permission for full access to all entity-spying functionality. */
  public static final String ENTITY_STAR = "entity.*";
  /** Permission for viewing invisible entities as partially transparent. */
  public static final String ENTITY_TRANSLUCENT = "entity.translucent";
  /** Permission for full access to all player-spying functionality. */
  public static final String PLAYER_STAR = "player.*";
  /** Permission for viewing invisible players as partially transparent. */
  public static final String PLAYER_TRANSLUCENT = "player.translucent";
  /** Permission for viewing entity-outline-highlighting in any gamemode. */
  public static final String RENDER_HIGHLIGHT = "render.highlight";

  private static PermissionsManagerClient permissionsManager;
  private static Permissible mod;
  private static boolean initDone;

  /** Permission-value cache booleans. */
  private static boolean canRenderHighlight = true;
  private static boolean canSpyEntity = true;
  private static boolean canSpyPlayer = true;

  /**
   * @return Whether the user can access the entity-highlight mode.
   */
  public static boolean canRenderHighlight() {
    return canRenderHighlight;
  }

  /**
   * @return Whether the user can spy invisible entities.
   */
  public static boolean canSpyEntity() {
    return canSpyEntity;
  }

  /**
   * @return Whether the user can spy invisible players.
   */
  public static boolean canSpyPlayer() {
    return canSpyPlayer;
  }

  /**
   * Clear permissions, re-granting full modification access.
   */
  public static void clear() {
    canRenderHighlight = true;
    canSpyEntity = true;
    canSpyPlayer = true;
  }

  /**
   * Check with the permission manager to see if the user has the given permission.
   * 
   * @param permission The permission
   * @return Whether the user has the permission
   */
  private static boolean hasPermission(String permission) {
    permissionsManager.tamperCheck();
    return permissionsManager.getModPermission(mod, permission, false);
  }

  /**
   * Initialize the permissions for the mod, given a specific permissions manager.
   * 
   * @param mod Reference to the mod instance
   * @param permissionsManager Reference to the manager instance
   */
  public static void init(LiteModOmniscience mod, PermissionsManagerClient permissionsManager) {
    if ((!initDone) || (OmnisciencePermissions.permissionsManager != permissionsManager)) {
      initDone = true;
      OmnisciencePermissions.mod = mod;
      OmnisciencePermissions.permissionsManager = permissionsManager;
      initPermissions();
    }
  }

  /**
   * Redister the modification's permissions and query the manager.
   */
  private static void initPermissions() {
    registerPermission(STAR);
    registerPermission(ENTITY_STAR);
    registerPermission(ENTITY_TRANSLUCENT);
    registerPermission(PLAYER_STAR);
    registerPermission(PLAYER_TRANSLUCENT);
    registerPermission(RENDER_HIGHLIGHT);
    queryPermissions();
  }

  /**
   * Check for tampering with the permissions manager and query it for the modification's
   * permissions.
   */
  public static void queryPermissions() {
    try {
      permissionsManager.tamperCheck();
      permissionsManager.sendPermissionQuery(mod);
    } catch (IllegalArgumentException ex) {
    }
  }

  /**
   * Refresh the cached permission values.
   */
  public static void refresh() {
    canRenderHighlight = hasPermission(STAR) || hasPermission(RENDER_HIGHLIGHT);
    canSpyEntity =
        hasPermission(STAR) || hasPermission(ENTITY_STAR) || hasPermission(ENTITY_TRANSLUCENT);
    canSpyPlayer =
        hasPermission(STAR) || hasPermission(PLAYER_STAR) || hasPermission(PLAYER_TRANSLUCENT);
  }

  /**
   * Register a permission for the modification after checking the permission manager for tampering.
   * 
   * @param permission Permission to register
   */
  private static void registerPermission(String permission) {
    permissionsManager.tamperCheck();
    permissionsManager.registerModPermission(mod, permission);
  }

}
