/*
 * This file is part of Bisq.
 *
 * Bisq is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * Bisq is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with Bisq. If not, see <http://www.gnu.org/licenses/>.
 */

package bisq.desktop.components.controls;

import bisq.desktop.common.utils.ImageUtil;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class BisqMenuItem extends Button {
    public static final String ICON_CSS_STYLE = "menu-item-icon";
    private boolean mouseIsInGraphic = false;
    private ImageView defaultIcon, activeIcon, buttonIcon;

    public BisqMenuItem(String defaultIconId, String activeIconId, String text) {
        if (text != null && !text.isEmpty()) {
            setText(text);
        }

        if (defaultIconId != null && activeIconId != null) {
            defaultIcon = ImageUtil.getImageViewById(defaultIconId);
            activeIcon = ImageUtil.getImageViewById(activeIconId);
            defaultIcon.getStyleClass().add(ICON_CSS_STYLE);
            activeIcon.getStyleClass().add(ICON_CSS_STYLE);
            buttonIcon = defaultIcon;
            setGraphic(buttonIcon);
            attachListeners();
        }

        setGraphicTextGap(10);
        setAlignment(Pos.CENTER_LEFT);
        getStyleClass().add("bisq-menu-item");
    }

    public BisqMenuItem(String text) {
        this(null, null, text);
    }

    public BisqMenuItem(String defaultIconId, String activeIconId) {
        this(defaultIconId, activeIconId, null);
    }

    public void useIconOnly() {
        useIconOnly(29);
    }

    public void useIconOnly(double size) {
        setMaxSize(size, size);
        setMinSize(size, size);
        setPrefSize(size, size);
        setAlignment(Pos.CENTER);
        getStyleClass().add("icon-only");
    }

    public void setTooltip(String tooltip) {
        if (tooltip != null) {
            Tooltip.install(this, new BisqTooltip(tooltip));
        }
    }

    public void createClickTooltip(String clickMessage) {
        setOnMouseClicked(e -> {
            updateIcon(defaultIcon);
            BisqTooltip tooltip = new BisqTooltip(clickMessage);
            double mouseX = e.getScreenX();
            double mouseY = e.getScreenY();
            tooltip.show(this, mouseX + 5, mouseY + 5);
            PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
            delay.setOnFinished(d -> {
                if (mouseIsInGraphic) {
                    updateIcon(activeIcon);
                }
                tooltip.hide();
            });
            delay.play();
        });
    }

    public void applyIconColorAdjustment(ColorAdjust colorAdjust) {
        if (colorAdjust != null) {
            buttonIcon.setEffect(colorAdjust);
        }
    }

    private void attachListeners() {
        setOnMouseMoved(e -> {
            ImageView iconView = (ImageView) this.getGraphic();
            double graphicX = iconView.getLayoutX();
            double graphicY = iconView.getLayoutY();
            double graphicWidth = iconView.getBoundsInParent().getWidth();
            double graphicHeight = iconView.getBoundsInParent().getHeight();
            double mouseX = e.getX();
            double mouseY = e.getY();
            // Check if the mouse is inside the bounds of the graphic (image)
            boolean isMouseOverGraphic = mouseX >= graphicX && mouseX <= graphicX + graphicWidth &&
                    mouseY >= graphicY && mouseY <= graphicY + graphicHeight;

            // If the mouse is over the graphic area, show the tooltip
            if (isMouseOverGraphic) {
                updateIcon(activeIcon);
                mouseIsInGraphic = true;
            }
        });
        setOnMouseExited(e -> {
            mouseIsInGraphic = false;
            updateIcon(defaultIcon);
        });
        createClickTooltip("Copied");
    }

    private void updateIcon(ImageView newIcon) {
        if (buttonIcon != newIcon) {
            buttonIcon = newIcon;
            setGraphic(buttonIcon);
        }
    }
}
