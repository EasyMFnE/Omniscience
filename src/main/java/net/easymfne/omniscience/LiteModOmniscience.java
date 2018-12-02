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

import java.io.File;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.Permissible;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.ExposableOptions;
import com.mumfrey.liteloader.permissions.PermissionsManager;
import com.mumfrey.liteloader.permissions.PermissionsManagerClient;
import com.mumfrey.liteloader.transformers.event.ReturnEventInfo;
import com.mumfrey.liteloader.util.log.LiteLoaderLogger;

@ExposableOptions(strategy = ConfigStrategy.Unversioned, filename = "omniscience.config.json")
public class LiteModOmniscience implements LiteMod, Configurable, Permissible {

  /** Handle enabled-check for entity highlighting key-bind. */
  public static void adjustHighlight(ReturnEventInfo<RenderGlobal, Boolean> event) {
    if (OmnisciencePermissions.canRenderHighlight()) {
      event.setReturnValue(Minecraft.getMinecraft().gameSettings.keyBindSpectatorOutlines.isKeyDown());
    }
  }
  
  /** Handle entity and player visibility checks, modifying return values if necessary. */
  public static void adjustVisibility(ReturnEventInfo<Entity, Boolean> event, EntityPlayer player) {
    if (!(event.getSource() instanceof EntityPlayer)) {
      if (instance.spyEntities && OmnisciencePermissions.canSpyEntity()) {
        event.setReturnValue(Boolean.FALSE);
      }
    } else {
      if (instance.spyPlayers && OmnisciencePermissions.canSpyPlayer()) {
        event.setReturnValue(Boolean.FALSE);
      }
    }
  }

  /** Modification instance. */
  public static LiteModOmniscience instance;

  /** Name/Version information. */
  public static final String MOD_NAME = "Omniscience";
  public static final String MOD_VERSION = "1.1.0";
  public static final float MOD_VERSION_NUMBER = 01.0100f;

  /** Spy invisible Entities. */
  @Expose
  @SerializedName("spy_entities")
  protected boolean spyEntities = false;

  /** Show invisible EntityPlayers. */
  @Expose
  @SerializedName("spy_players")
  protected boolean spyPlayers = true;

  /** Construct new instance of the mod and update static reference to it. */
  public LiteModOmniscience() {
    if (instance != null) {
      LiteLoaderLogger.severe("Attempted to instantiate " + MOD_NAME + " twice.");
      throw new RuntimeException("Double instantiation of " + MOD_NAME);
    } else {
      instance = this;
    }
  }

  /** Get class responsible for the configuration panel. */
  @Override
  public Class<? extends ConfigPanel> getConfigPanelClass() {
    return OmniscienceConfigPanel.class;
  }

  /** Get the human-readable modification name. */
  @Override
  public String getName() {
    return MOD_NAME;
  }

  /** Get the lowercase name of the mod for permission usage. */
  @Override
  public String getPermissibleModName() {
    return MOD_NAME.toLowerCase();
  }

  /** Get the numeric version of the mod for permission usage. */
  @Override
  public float getPermissibleModVersion() {
    return MOD_VERSION_NUMBER;
  }

  /** Get the human-readable modification version. */
  @Override
  public String getVersion() {
    return MOD_VERSION;
  }

  /** On initialization, nothing needs to be done. */
  @Override
  public void init(File configPath) {}

  /** On permissions changing, refresh their cached values. */
  @Override
  public void onPermissionsChanged(PermissionsManager manager) {
    OmnisciencePermissions.refresh();
  }

  /** On clearing of permissions, clear their cached values. */
  @Override
  public void onPermissionsCleared(PermissionsManager manager) {
    OmnisciencePermissions.clear();
  }

  /** Register the modification's permission nodes. */
  @Override
  public void registerPermissions(PermissionsManagerClient permissionsManager) {
    OmnisciencePermissions.init(this, permissionsManager);
  }

  /** On upgrading from a previous version, nothing needs to be done. */
  @Override
  public void upgradeSettings(String version, File configPath, File oldConfigPath) {}

}
