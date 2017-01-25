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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;

/**
 * In-game configuration panel with buttons for independently enabling and disabling functionality
 * and changing settings.
 */
public class OmniscienceConfigPanel extends Gui implements ConfigPanel {

  /** Line spacing, in points. */
  private final static int SPACING = 16;

  /** Cursor offset amount for permission warning. */
  private final static int CURSOR_OFFSET_X = 6;
  private final static int CURSOR_OFFSET_Y = 10;

  /** Instance references. */
  private Minecraft minecraft;
  private GuiCheckbox spyEntitiesBox;
  private GuiCheckbox spyPlayersBox;
  private GuiButton activeButton;

  /** Draw the configuration panel's elements every refresh. */
  @Override
  public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks) {
    spyEntitiesBox.drawButton(minecraft, mouseX, mouseY);
    spyPlayersBox.drawButton(minecraft, mouseX, mouseY);
    if (spyEntitiesBox.isMouseOver() && !spyEntitiesBox.enabled || spyPlayersBox.isMouseOver()
        && !spyPlayersBox.enabled) {
      minecraft.fontRenderer.drawStringWithShadow(
          I18n.format("omniscience.configpanel.nopermission.text"), mouseX + CURSOR_OFFSET_X,
          mouseY + CURSOR_OFFSET_Y, 0xff0000);
    }
  }

  /** Get the height of the panel in points. */
  @Override
  public int getContentHeight() {
    return SPACING * 3;
  }

  /** Get the title to display for the panel. */
  @Override
  public String getPanelTitle() {
    return I18n.format("omniscience.configpanel.title", new Object[] {LiteModOmniscience.MOD_NAME});
  }

  /** On key-presses, nothing needs to be done. */
  @Override
  public void keyPressed(ConfigPanelHost host, char keyChar, int keyCode) {
    if (keyCode == Keyboard.KEY_ESCAPE || keyCode == Keyboard.KEY_RETURN) {
      host.close();
    }
  }

  /** On mouse movement, nothing needs to be done. */
  @Override
  public void mouseMoved(ConfigPanelHost host, int mouseX, int mouseY) {}

  /** On click, activate button under cursor if one exists. */
  @Override
  public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton) {
    if (spyEntitiesBox.mousePressed(minecraft, mouseX, mouseY)) {
      activeButton = spyEntitiesBox;
      LiteModOmniscience.instance.spyEntities = !LiteModOmniscience.instance.spyEntities;
      spyEntitiesBox.checked = LiteModOmniscience.instance.spyEntities;
      spyEntitiesBox.playPressSound(minecraft.getSoundHandler());
    } else if (spyPlayersBox.mousePressed(minecraft, mouseX, mouseY)) {
      activeButton = spyPlayersBox;
      LiteModOmniscience.instance.spyPlayers = !LiteModOmniscience.instance.spyPlayers;
      spyPlayersBox.checked = LiteModOmniscience.instance.spyPlayers;
      spyPlayersBox.playPressSound(minecraft.getSoundHandler());
    }
  }

  /** On release of click, deactivate the selected button (if any). */
  @Override
  public void mouseReleased(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton) {
    if (activeButton != null) {
      activeButton.mouseReleased(mouseX, mouseY);
      activeButton = null;
    }
  }

  /** On closing of panel, save the configuration to disk. */
  @Override
  public void onPanelHidden() {
    LiteLoader.getInstance().writeConfig(LiteModOmniscience.instance);
  }

  /** On resizing of panel, nothing needs to be done. */
  @Override
  public void onPanelResize(ConfigPanelHost host) {}

  /** On opening of panel, instantiate the user interface components. */
  @Override
  public void onPanelShown(ConfigPanelHost host) {
    minecraft = Minecraft.getMinecraft();
    int id = 0;
    int line = 0;
    spyEntitiesBox =
        new GuiCheckbox(id++, 10, SPACING * line++, I18n.format(
            "omniscience.configpanel.spyentity.text", new Object[0]));
    spyEntitiesBox.checked = LiteModOmniscience.instance.spyEntities;
    spyPlayersBox =
        new GuiCheckbox(id++, 10, SPACING * line++, I18n.format(
            "omniscience.configpanel.spyplayer.text", new Object[0]));
    spyPlayersBox.checked = LiteModOmniscience.instance.spyPlayers;
    updateForPermissions();
  }

  /** On each tick, nothing needs to be done. */
  @Override
  public void onTick(ConfigPanelHost host) {}

  /** Enable/disable the configuration checkboxes based on user permissions. */
  private void updateForPermissions() {
    if (spyEntitiesBox != null) {
      spyEntitiesBox.enabled = OmnisciencePermissions.canSpyEntity();
      spyEntitiesBox.checked &= spyEntitiesBox.enabled;
    }
    if (spyPlayersBox != null) {
      spyPlayersBox.enabled = OmnisciencePermissions.canSpyPlayer();
      spyPlayersBox.checked &= spyPlayersBox.enabled;
    }
  }

}
