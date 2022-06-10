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

package bisq.desktop.primary.overlay.onboarding.offer.complete;

import bisq.desktop.common.view.View;
import bisq.desktop.components.containers.Spacer;
import bisq.i18n.Res;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OfferCompletedView extends View<StackPane, OfferCompletedModel, OfferCompletedController> {
    private final Button backButton;
    private final ImageView imageView;

    public OfferCompletedView(OfferCompletedModel model, OfferCompletedController controller) {
        super(new StackPane(), model, controller);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.getStyleClass().add("bisq-content-bg");

        Label headLineLabel = new Label(Res.get("onboarding.completed.headline"));
        headLineLabel.getStyleClass().add("bisq-text-headline-2");

        Label subtitleLabel = new Label(Res.get("onboarding.completed.subTitle"));
        subtitleLabel.setTextAlignment(TextAlignment.CENTER);
        subtitleLabel.setAlignment(Pos.CENTER);
        subtitleLabel.getStyleClass().addAll("bisq-text-10", "wrap-text");

        backButton = new Button(Res.get("back"));

        VBox.setMargin(headLineLabel, new Insets(38, 0, 4, 0));
        VBox.setMargin(subtitleLabel, new Insets(0, 0, 60, 0));
        VBox.setMargin(backButton, new Insets(0, 0, 90, 0));
        vBox.getChildren().addAll(headLineLabel, subtitleLabel, Spacer.fillVBox(), backButton);

        // WIP
        // vBox.setStyle("-fx-background-color: transparant");
        // topPaneBox.setStyle("-fx-background-color: transparant");

        double width = 920;
        double height = 550;
        Canvas canvas = new Canvas();
        canvas.setWidth(width);
        canvas.setHeight(height);
        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
        graphicsContext2D.setImageSmoothing(true);

        graphicsContext2D.beginPath();
        graphicsContext2D.moveTo(80, 100);
        graphicsContext2D.lineTo(840, 100);
        graphicsContext2D.lineTo(840, 400);
        graphicsContext2D.lineTo(80, 400);
        graphicsContext2D.closePath();
        graphicsContext2D.clip();

        Image image = new Image("images/onboarding/template/onboarding-template_0006_complete.png");
        graphicsContext2D.drawImage(image, 0, 0, width, height);
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setFill(Color.TRANSPARENT);
        WritableImage clipped = canvas.snapshot(snapshotParameters, null);
        imageView = new ImageView(clipped);
        root.getChildren().addAll(vBox, imageView);
    }

    @Override
    protected void onViewAttached() {
        imageView.setOnMouseReleased(e -> {
            e.consume();
            if (e.getY() < 280) {
                controller.onPublishOffer();
            } else {
                controller.onTakeOffer();
            }
        });
        backButton.setOnAction(evt -> controller.onBack());
    }

    @Override
    protected void onViewDetached() {
        imageView.setOnMouseReleased(null);
        backButton.setOnAction(null);
    }
}