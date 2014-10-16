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

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.ExposableOptions;
import com.mumfrey.liteloader.transformers.event.ReturnEventInfo;
import com.mumfrey.liteloader.util.log.LiteLoaderLogger;

@ExposableOptions(strategy = ConfigStrategy.Unversioned, filename = "omniscience.config.json")
public class LiteModOmniscience implements LiteMod, Configurable {

  /** Handle entity visibility checks, modifying return values if necessary. */
  public static void adjustEntityVisibility(ReturnEventInfo<Entity, Boolean> event,
      EntityPlayer player) {
    if (instance.spyEntities) {
      event.setReturnValue(Boolean.FALSE);
    }
  }

  /** Handle player visibility checks, modifying return values if necessary. */
  public static void adjustPlayerVisibility(ReturnEventInfo<Entity, Boolean> event,
      EntityPlayer player) {
    if (instance.spyPlayers) {
      event.setReturnValue(Boolean.FALSE);
    }
  }

  /** Modification instance. */
  public static LiteModOmniscience instance;

  /** Name/Version information. */
  public static final String MOD_NAME = "Omniscience";
  public static final String MOD_VERSION = "1.0.0";

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

  /** Get the human-readable modification version. */
  @Override
  public String getVersion() {
    return MOD_VERSION;
  }

  /** On initialization, nothing needs to be done. */
  @Override
  public void init(File configPath) {}

  /** On upgrading from a previous version, nothing needs to be done. */
  @Override
  public void upgradeSettings(String version, File configPath, File oldConfigPath) {}

}
