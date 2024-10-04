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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import lombok.extern.slf4j.Slf4j;
import javafx.scene.control.ScrollPane;

@Slf4j
public class TradeDetailsView extends NavigationView<AnchorPane, TradeDetailsModel, TradeDetailsController> {

    private final Button closeButton;
    private final ScrollPane scrollPane;

    private final VBox allInfoContainer, informationContainer, detailsContainer;

    private final DetailsTextRow myUsername, peerUsername, tradeId, myRole;
    private final DetailsTextRow tradeAmountFiat, fiatPaymentMethod;
    private final DetailsTextRow bitcoinPaymentMethod, marketPrice, offerTakenTime;
    private final DetailsTextRow myNetworkAddress, peerNetworkAddress;
    private final DetailsRow tradeAmountBTC, paymentAccountData, bitcoinPaymentAddress;

    private static final int ROW_HEIGHT = 40;

    public TradeDetailsView(TradeDetailsModel model, TradeDetailsController controller) {
        super(new AnchorPane(), model, controller);

        root.setPrefWidth(OverlayModel.WIDTH);
        root.setPrefHeight(OverlayModel.HEIGHT + 200);
        int rowSpacing = 2;

        allInfoContainer = new VBox(25);
        scrollPane = new ScrollPane(allInfoContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(15, 15, 15, 15));
        allInfoContainer.setPadding(new Insets(30, 30, 30, 30));
        allInfoContainer.prefHeightProperty().bind(root.heightProperty());
        allInfoContainer.prefWidthProperty().bind(root.widthProperty());
        root.getChildren().add(scrollPane);

        informationContainer = new VBox(rowSpacing);
        Label headline = new Label("Trade Information");
        headline.getStyleClass().add("bisq-text-headline-2");
        informationContainer.getChildren().add(headline);
        Region separator = Layout.hLine();
        separator.setStyle("-fx-background-color: green;");
        separator.getStyleClass().add("green-h-line");
        informationContainer.getChildren().add(separator);

        marketPrice = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.marketPrice"));
        tradeAmountFiat = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.amountFiat"));
        fiatPaymentMethod = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.fiatPaymentMethod"));
        bitcoinPaymentMethod = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.btcPaymentMethod"));
        tradeAmountBTC = new DetailsRow(Res.get("bisqEasy.openTrades.table.baseAmount"));
        bitcoinPaymentAddress = new DetailsRow(Res.get("bisqEasy.openTrades.tradeDetails.btcPaymentAddress"));
        paymentAccountData = new DetailsRow(Res.get("bisqEasy.openTrades.tradeDetails.paymentAccountData"));
        myRole = new DetailsTextRow(Res.get("bisqEasy.openTrades.table.makerTakerRole"));
        informationContainer.getChildren().addAll(myRole, marketPrice, fiatPaymentMethod,
                bitcoinPaymentMethod, tradeAmountFiat, tradeAmountBTC, bitcoinPaymentAddress, paymentAccountData);

        detailsContainer = new VBox(rowSpacing);
        Label detailsHeadline = new Label("Details");
        detailsHeadline.getStyleClass().add("bisq-text-headline-2");
        Region detailsSeparator = Layout.hLine();
        detailsSeparator.getStyleClass().add("green-h-line");
        detailsContainer.getChildren().addAll(detailsHeadline, detailsSeparator);

        tradeId = new DetailsTextRow(Res.get("bisqEasy.openTrades.table.tradeId"));
        myUsername = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.myUsername"));
        peerUsername = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.peerUsername"));
        offerTakenTime = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.offerTakenTimeStamp"));
        myNetworkAddress = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.myNetworkAddress"));
        peerNetworkAddress = new DetailsTextRow(Res.get("bisqEasy.openTrades.tradeDetails.peerNetworkAddress"));
        detailsContainer.getChildren().addAll(tradeId, myUsername, peerUsername, offerTakenTime, myNetworkAddress,
                peerNetworkAddress);

        allInfoContainer.getChildren().addAll(informationContainer, detailsContainer);

        closeButton = BisqIconButton.createIconButton("close");
        Layout.pinToAnchorPane(closeButton, 16, 20, null, null);
        root.getChildren().add(closeButton);
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

    @Override
    protected void onViewAttached() {
        myUsername.textField.textProperty().bind(model.getMyUsername());
        peerUsername.textField.textProperty().bind(model.getPeerUsername());
        tradeId.textField.textProperty().bind(model.getTradeId());
        tradeAmountFiat.textField.textProperty().bind(model.getAmountInFiat());
        bitcoinPaymentAddress.textField.textProperty().bind(model.getBitcoinPaymentAddress());

        marketPrice.textField.textProperty().bind(model.getMarketPrice());
        offerTakenTime.textField.textProperty().bind(model.getOfferTakenDateTime());
        tradeAmountBTC.textField.textProperty().bind(model.getAmountInBTC());
        fiatPaymentMethod.textField.textProperty().bind(model.getFiatPaymentMethod());
        bitcoinPaymentMethod.textField.textProperty().bind(model.getBitcoinPaymentMethod());

        paymentAccountData.textField.textProperty().bind(model.getPaymentAccountData());
        myRole.textField.textProperty().bind(model.getMyRole());
        myNetworkAddress.textField.textProperty().bind(model.getMyNetworkAddress());
        peerNetworkAddress.textField.textProperty().bind(model.getPeerNetworkAddress());

        setupColorChangeEffects();

        closeButton.setOnAction(e -> controller.onClose());
    }

    @Override
    protected void onViewDetached() {
        closeButton.setOnAction(null);
    }

    private void setupColorChangeEffects() {
        bitcoinPaymentAddress.textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                changeColorOfTextField(bitcoinPaymentAddress.textField, !model.isBitcoinPaymentAddressProvided());
            }
        });
        changeColorOfTextField(bitcoinPaymentAddress.textField, !model.isBitcoinPaymentAddressProvided());

        paymentAccountData.textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                changeColorOfTextField(paymentAccountData.textField, !model.isPaymentAccountDataProvided());
            }
        });
        changeColorOfTextField(paymentAccountData.textField, !model.isPaymentAccountDataProvided());
    }

    private void changeColorOfTextField(MaterialTextField textField, boolean shouldHaveLowFocus) {
        if (shouldHaveLowFocus) {
            // Make the text less visible for the sake of clarity
            textField.getTextInputControl().getStyleClass().remove("material-text-field-read-only");
            textField.getTextInputControl().getStyleClass()
                    .add("material-text-field-description-deselected");
        } else {
            textField.getTextInputControl().getStyleClass()
                    .remove("material-text-field-description-deselected");
            textField.getTextInputControl().getStyleClass().add("material-text-field-read-only");
        }
    }
}