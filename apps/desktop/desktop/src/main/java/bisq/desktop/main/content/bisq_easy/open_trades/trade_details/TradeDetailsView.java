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

package bisq.desktop.main.content.bisq_easy.open_trades.trade_details;

import bisq.desktop.common.Layout;
import bisq.desktop.common.view.NavigationView;
import bisq.desktop.components.controls.BisqIconButton;
import bisq.desktop.components.controls.MaterialTextField;
import bisq.desktop.overlay.OverlayModel;
import bisq.i18n.Res;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeDetailsView extends NavigationView<AnchorPane, TradeDetailsModel, TradeDetailsController> {

    private static final int ROW_HEIGHT = 40;
    private static final int ROW_SPACING = 10;
    private final Button closeButton;
    private final DetailsTextRow peerUsername, tradeId, myRole;
    private final DetailsTextRow fiatPaymentMethod, peerNetworkAddress;
    private final DetailsTextRow bitcoinPaymentMethod, tradePrice, offerTakenDateTime;
    private final DetailsTextRow mediator;
    private final DetailsRow tradeAmountFiat, tradeAmountBTC, paymentAccountData, bitcoinPaymentAddress;
    VBox allInfoContainer;

    public TradeDetailsView(TradeDetailsModel model, TradeDetailsController controller) {
        super(new AnchorPane(), model, controller);

        root.setPrefWidth(OverlayModel.WIDTH + 60);
        root.setPrefHeight(OverlayModel.HEIGHT + 140);

        allInfoContainer = new VBox(40);
        ScrollPane scrollPane = new ScrollPane(allInfoContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(15, 15, 15, 15));
        allInfoContainer.setPadding(new Insets(30, 30, 30, 30));
        root.getChildren().add(scrollPane);

        VBox informationContainer = createSectionHeader(Res.get("bisqEasy.openTrades.tradeDetails.informationSection"));

        tradePrice = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.tradePrice"));
        fiatPaymentMethod = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.fiatPaymentMethod"));
        bitcoinPaymentMethod = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.btcPaymentMethod"));
        tradeAmountFiat = new DetailsRow("");
        tradeAmountBTC = new DetailsRow(Res.get("bisqEasy.openTrades.table.baseAmount"));
        myRole = new DetailsTextRow(Res.get("bisqEasy.openTrades.table.makerTakerRole"));
        informationContainer.getChildren().addAll(myRole, tradePrice, fiatPaymentMethod, bitcoinPaymentMethod, tradeAmountFiat, tradeAmountBTC);

        VBox processInformation = createSectionHeader(Res.get("bisqEasy.openTrades.tradeDetails.processSection"));
        bitcoinPaymentAddress = new DetailsRow(Res.get("bisqEasy.openTrades.tradeDetails.btcPaymentAddress"));
        paymentAccountData = new DetailsRow(Res.get("bisqEasy.openTrades.tradeDetails.paymentAccountData"));
        processInformation.getChildren().addAll(bitcoinPaymentAddress, paymentAccountData);

        VBox detailsContainer = createSectionHeader(Res.get("bisqEasy.openTrades.tradeDetails.detailsSection"));
        tradeId = new DetailsTextRow(Res.get("bisqEasy.openTrades.table.tradeId"));
        peerUsername = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.peerUsername"));
        offerTakenDateTime = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.offerTakenDate"));
        peerNetworkAddress = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.peerNetworkAddress"));
        mediator = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.selectedMediator"));
        detailsContainer.getChildren().addAll(tradeId, offerTakenDateTime, peerUsername, peerNetworkAddress, mediator);

        allInfoContainer.getChildren().addAll(informationContainer, processInformation, detailsContainer);

        closeButton = BisqIconButton.createIconButton("close");
        Layout.pinToAnchorPane(closeButton, 16, 18, null, null);
        root.getChildren().add(closeButton);
    }

    @Override
    protected void onViewAttached() {
        allInfoContainer.prefHeightProperty().bind(root.heightProperty());
        allInfoContainer.prefWidthProperty().bind(root.widthProperty());

        peerUsername.textField.textProperty().bind(model.getPeerUsername());
        tradeId.textField.textProperty().bind(model.getTradeId());
        tradeAmountFiat.description.textProperty().bind(model.getCurrencyDescription());
        tradeAmountFiat.textField.textProperty().bind(model.getAmountInFiat());
        bitcoinPaymentAddress.textField.textProperty().bind(model.getBitcoinPaymentAddress());

        tradePrice.textField.textProperty().bind(model.getTradePrice());
        offerTakenDateTime.textField.textProperty().bind(model.getOfferTakenDateTime());
        tradeAmountBTC.textField.textProperty().bind(model.getAmountInBTC());
        fiatPaymentMethod.textField.textProperty().bind(model.getFiatPaymentMethod());
        bitcoinPaymentMethod.textField.textProperty().bind(model.getBitcoinPaymentMethod());

        paymentAccountData.textField.textProperty().bind(model.getPaymentAccountData());
        myRole.textField.textProperty().bind(model.getMyRole());
        peerNetworkAddress.textField.textProperty().bind(model.getPeerNetworkAddress());
        mediator.textField.textProperty().bind(model.getMediator());

        applyDarkTextColorIfNotProvided(bitcoinPaymentAddress.textField.getTextInputControl(), !model.isBitcoinPaymentAddressProvided());
        applyDarkTextColorIfNotProvided(paymentAccountData.textField.getTextInputControl(), !model.isPaymentAccountDataProvided());
        applyDarkTextColorIfNotProvided(mediator.textField, !model.isMediatorProvided());

        closeButton.setOnAction(e -> controller.onClose());
    }

    @Override
    protected void onViewDetached() {
        allInfoContainer.prefHeightProperty().unbind();
        allInfoContainer.prefWidthProperty().unbind();

        peerUsername.textField.textProperty().unbind();
        tradeId.textField.textProperty().unbind();
        tradeAmountFiat.description.textProperty().unbind();
        tradeAmountFiat.textField.textProperty().unbind();
        bitcoinPaymentAddress.textField.textProperty().unbind();

        tradePrice.textField.textProperty().unbind();
        offerTakenDateTime.textField.textProperty().unbind();
        tradeAmountBTC.textField.textProperty().unbind();
        fiatPaymentMethod.textField.textProperty().unbind();
        bitcoinPaymentMethod.textField.textProperty().unbind();

        paymentAccountData.textField.textProperty().unbind();
        myRole.textField.textProperty().unbind();
        peerNetworkAddress.textField.textProperty().unbind();
        mediator.textField.textProperty().unbind();

        closeButton.setOnAction(null);
    }

    private VBox createSectionHeader(String headlineText) {
        VBox section = new VBox(ROW_SPACING);
        Label headline = new Label(headlineText);
        headline.getStyleClass().add("bisq-text-headline-2");
        Region separator = Layout.hLine();
        separator.setStyle("-fx-background-color: green;");
        separator.getStyleClass().add("green-h-line");
        section.getChildren().addAll(headline, separator);
        return section;
    }

    private TextArea createDescription(String text) {
        int LEFT_COLUMN_WIDTH = 250;
        TextArea area = new TextArea(text);
        area.setEditable(false);
        area.setMinWidth(LEFT_COLUMN_WIDTH);
        area.setMaxWidth(LEFT_COLUMN_WIDTH);
        area.setPrefHeight(ROW_HEIGHT);
        area.setMinHeight(ROW_HEIGHT);
        return area;
    }

    private void applyDarkTextColorIfNotProvided(TextInputControl textField, boolean dataNotProvided) {
        String brightTextColor = "material-text-field-read-only";
        String darkTextColor = "material-text-field-description-deselected";
        if (dataNotProvided) {
            textField.getStyleClass().remove(brightTextColor);
            if (!textField.getStyleClass().contains(darkTextColor)) {
                // Make the text less visible for the sake of clarity
                textField.getStyleClass().add(darkTextColor);
            }
        } else {
            textField.getStyleClass().remove(darkTextColor);
            if (!textField.getStyleClass().contains(brightTextColor)) {
                textField.getStyleClass().add(brightTextColor);
            }
        }
    }

    private class DetailsRow extends HBox {
        TextArea description;
        MaterialTextField textField;

        public DetailsRow(String description) {
            super();
            this.setSpacing(20);
            this.description = createDescription(description);

            textField = new MaterialTextField();
            textField.setEditable(false);
            textField.showCopyIcon();
            HBox.setHgrow(textField, Priority.ALWAYS);
            this.getChildren().addAll(this.description, textField);
        }
    }

    private class DetailsTextRow extends HBox {
        TextArea description;
        TextArea textField;

        public DetailsTextRow(String description) {
            super();
            this.setSpacing(20);
            this.description = createDescription(description);

            textField = new TextArea();
            textField.setPrefHeight(ROW_HEIGHT);
            textField.setMinHeight(ROW_HEIGHT);
            textField.setEditable(false);
            // ensure usage of the same font size as material text field to align text
            textField.getStyleClass().add("material-text-field-description-big");
            HBox.setHgrow(textField, Priority.ALWAYS);
            this.getChildren().addAll(this.description, textField);
        }
    }
}