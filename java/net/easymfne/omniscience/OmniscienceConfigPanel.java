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

import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;

/**
 * In-game configuration panel with buttons for independently enabling and disabling functionality
 * and changing settings.
 */
public class OmniscienceConfigPanel extends Gui implements ConfigPanel {

  /** Line spacing, in points. */
  private final static int SPACING = 16;

  private GuiCheckbox spyEntitiesBox;
  private GuiCheckbox spyPlayersBox;
  private GuiButton activeButton;

  /** Draw the configuration panel's elements every refresh. */
  @Override
  public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks) {
    spyEntitiesBox.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
    spyPlayersBox.drawButton(Minecraft.getMinecraft(), mouseX, mouseY);
  }

  /** Get the height of the panel in points. */
  @Override
  public int getContentHeight() {
    return SPACING * 3;
  }

  /** Get the title to display for the panel. */
  @Override
  public String getPanelTitle() {
    return I18n.format("config.panel.title", new Object[] {LiteModOmniscience.MOD_NAME});
  }

  /** On key-presses, nothing needs to be done. */
  @Override
  public void keyPressed(ConfigPanelHost host, char keyChar, int keyCode) {}

  /** On mouse movement, nothing needs to be done. */
  @Override
  public void mouseMoved(ConfigPanelHost host, int mouseX, int mouseY) {}

  /** On click, activate button under cursor if one exists. */
  @Override
  public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton) {
    if (spyEntitiesBox.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY)) {
      activeButton = spyEntitiesBox;
      LiteModOmniscience.instance.spyEntities = !LiteModOmniscience.instance.spyEntities;
      spyEntitiesBox.checked = LiteModOmniscience.instance.spyEntities;
    } else if (spyPlayersBox.mousePressed(Minecraft.getMinecraft(), mouseX, mouseY)) {
      activeButton = spyPlayersBox;
      LiteModOmniscience.instance.spyPlayers = !LiteModOmniscience.instance.spyPlayers;
      spyPlayersBox.checked = LiteModOmniscience.instance.spyPlayers;
    }
  }

  /** On release of click, deactivate the selected button (if any). */
  @Override
  public void mouseReleased(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton) {
    if (activeButton != null) {
      activeButton.func_146111_b(mouseX, mouseY);
      activeButton = null;
    }
  }

  /** On closing of panel, nothing needs to be done. */
  @Override
  public void onPanelHidden() {}

  /** On resizing of panel, nothing needs to be done. */
  @Override
  public void onPanelResize(ConfigPanelHost host) {}

  /** On opening of panel, instantiate the user interface components. */
  @Override
  public void onPanelShown(ConfigPanelHost host) {
    int id = 0;
    int line = 0;
    spyEntitiesBox =
        new GuiCheckbox(id++, 10, SPACING * line++, I18n.format("config.spy.entity.text",
            new Object[0]));
    spyEntitiesBox.checked = LiteModOmniscience.instance.spyEntities;
    spyPlayersBox =
        new GuiCheckbox(id++, 10, SPACING * line++, I18n.format("config.spy.player.text",
            new Object[0]));
    spyPlayersBox.checked = LiteModOmniscience.instance.spyPlayers;
  }

  /** On each tick, nothing needs to be done. */
  @Override
  public void onTick(ConfigPanelHost host) {}

}
