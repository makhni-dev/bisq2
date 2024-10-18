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
import bisq.desktop.overlay.OverlayModel;
import bisq.desktop.common.view.NavigationView;
import bisq.desktop.components.controls.BisqIconButton;
import bisq.desktop.components.controls.MaterialTextField;
import bisq.desktop.common.utils.ImageUtil;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Material;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.TextField;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.image.ImageView;



import lombok.extern.slf4j.Slf4j;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import bisq.desktop.components.table.BisqTableView;
import javafx.scene.control.ScrollPane;
import bisq.desktop.components.table.DateTableItem;

@Slf4j
public class TradeDetailsView extends NavigationView<AnchorPane, TradeDetailsModel, TradeDetailsController> {

    private final Button closeButton;
     private final ScrollPane scrollPane;

    // box containing all details
    private final VBox allInfoContainer, informationContainer, detailsContainer;

    private final DetailsTextRow myUsername, peerUsername,tradeId;
    private final DetailsTextRow tradeAmountFiat, fiatPaymentMethod;
    private final DetailsTextRow  bitcoinPaymentMethod, mediator, marketPrice, offerTakenTime;
    private final DetailsRow tradeAmountBTC,paymentAccountData, bitcoinPaymentAddress;
    // private final BisqTableView<ListItem> tableView;

    public TradeDetailsView(TradeDetailsModel model, TradeDetailsController controller) {
        super(new AnchorPane(), model, controller);

        root.setPrefWidth(OverlayModel.WIDTH);
        root.setPrefHeight(OverlayModel.HEIGHT + 200);

        allInfoContainer = new VBox(40);
        scrollPane = new ScrollPane(allInfoContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(15, 15, 15, 15));
        allInfoContainer.setPadding(new Insets(30, 30, 30, 30));
        allInfoContainer.prefHeightProperty().bind(root.heightProperty());
        allInfoContainer.prefWidthProperty().bind(root.widthProperty());
        root.getChildren().add(scrollPane);
        
        informationContainer = new VBox(5);
        Label headline = new Label("Trade Information");
        headline.getStyleClass().add("bisq-text-headline-2");
        informationContainer.getChildren().add(headline);
        Region separator = Layout.hLine();
        separator.setStyle("-fx-background-color: green;");
        separator.getStyleClass().add("green-h-line");
        informationContainer.getChildren().add(separator);

        // add role here
        marketPrice = new DetailsTextRow("Market price");
        tradeAmountFiat = new DetailsTextRow("Trade amount in fiat");
        fiatPaymentMethod = new DetailsTextRow("Fiat payment method");
        bitcoinPaymentMethod = new DetailsTextRow("BTC payment method");
        tradeAmountBTC = new DetailsRow("Trade amount in BTC");
        bitcoinPaymentAddress = new DetailsRow("Bitcoin payment address");
        paymentAccountData = new DetailsRow("Payment account data");
        informationContainer.getChildren().addAll(marketPrice, fiatPaymentMethod, 
            bitcoinPaymentMethod, tradeAmountFiat, tradeAmountBTC, bitcoinPaymentAddress, paymentAccountData);

        detailsContainer = new VBox(5);
        Label detailsHeadline = new Label("Details");
        detailsHeadline.getStyleClass().add("bisq-text-headline-2");
        Region detailsSeparator = Layout.hLine();
        detailsSeparator.getStyleClass().add("green-h-line");
        detailsContainer.getChildren().addAll(detailsHeadline, detailsSeparator);

        tradeId = new DetailsTextRow("Trade ID");
        myUsername = new DetailsTextRow("My username");
        peerUsername = new DetailsTextRow("Peer username");
        offerTakenTime = new DetailsTextRow("Offer taken date");
        mediator = new DetailsTextRow("Mediator");
        detailsContainer.getChildren().addAll(tradeId, myUsername, peerUsername, offerTakenTime, mediator);

        allInfoContainer.getChildren().addAll(informationContainer, detailsContainer);

        // detailsContainer.getChildren().addAll(tradeId, myUsername, peerUsername, bitcoinPaymentAddress, marketPrice,
        // offerTakenTime, tradeAmountFiat, tradeAmountBTC, fiat);
        // paymentAccountData.getTextInputControl().setVisible(false);
        // paymentAccountData.setHelpText("Not yet provided");


        // helpLabel.setManaged(helpLabel.isVisible());
        // paymentAccountData.getHelpText()(true);
        // paymentAccountData.getTextInputControl().getStyleClass().add("material-text-field-low-focus");
        // paymentAccountData.getTextInputControl().getStyleClass().add("material-text-field-read-only");

        closeButton = BisqIconButton.createIconButton("close");
        Layout.pinToAnchorPane(closeButton, 16, 20, null, null);
        root.getChildren().add(closeButton);
    }

    private class DetailsRow extends HBox{
        TextArea description;
        MaterialTextField textField;
        public DetailsRow(String description){
            super();
            int LEFT_COLUMN_WIDTH = 250;
            this.setSpacing(20); 

            this.description = new TextArea(description);
            // this.description.getStyleClass().add("text-color-green");
            this.description.setMinWidth(LEFT_COLUMN_WIDTH);
            this.description.setMaxWidth(LEFT_COLUMN_WIDTH);
            this.description.setPrefHeight(40);
            this.description.setMinHeight(40);
            // this.description.getStyleClass().add("material-text-field");
            // this.description.getStyleClass().add("material-text-field-description-small");

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
        public DetailsTextRow(String description){
            super();
            int LEFT_COLUMN_WIDTH = 250;
            this.setSpacing(20); 
            ImageView greenDot = ImageUtil.getImageViewById("green-dot");

            this.description = new TextArea(description);
            // this.description.getStyleClass().add("text-color-green");
            this.description.setMinWidth(LEFT_COLUMN_WIDTH);
            this.description.setMaxWidth(LEFT_COLUMN_WIDTH);
            this.description.setPrefHeight(40);
            this.description.setMinHeight(40);
            // this.description.getStyleClass().add("material-text-field-description-small");

            textField = new TextArea();
            textField.setLayoutX(6.5); 
            // textField.setLayoutY(3);
            textField.setPrefHeight(40);
            textField.setMinHeight(40);
            textField.getStyleClass().add("material-text-field-description-big");
            HBox.setHgrow(textField, Priority.ALWAYS);
            this.getChildren().addAll(this.description, textField);
        }


    }

    private MaterialTextField createMaterialTextField(String description){
        MaterialTextField field = new MaterialTextField(description, null);
        field.setEditable(false);
        field.showCopyIcon();
        field.showIcon();
        
        allInfoContainer.getChildren().add(field);
        return field;
    }

    private HBox createTextBoxLine(String leftText, String rightText){
        int LEFT_COLUMN_WIDTH = 100;
        HBox hbox = new HBox();
        hbox.setSpacing(10);  // Space between columns
        hbox.setPadding(new Insets(5));  // Padding within each row

        // Left column (fixed width)
        Label leftLabel = new Label(leftText);
        leftLabel.setMinWidth(LEFT_COLUMN_WIDTH);
        leftLabel.setMaxWidth(LEFT_COLUMN_WIDTH);
        leftLabel.setPrefHeight(40);
        // hbox.setStyle("-fx-background-color: green;");

        MaterialTextField field = new MaterialTextField(null, null, null, rightText);
        field.setEditable(false);
        field.showCopyIcon();
        HBox.setHgrow(field, Priority.ALWAYS);
        hbox.getChildren().addAll(leftLabel, field);
        // Right column (flexible width)
        return hbox;
    }

    @Override
    protected void onViewAttached() {
        myUsername.textField.textProperty().bind(model.getMyUsername());
        peerUsername.textField.textProperty().bind(model.getPeerUsername());
        tradeId.textField.textProperty().bind(model.getTradeId());
        tradeAmountFiat.textField.textProperty().bind(model.getAmountInFiat());
        bitcoinPaymentAddress.textField.textProperty().bind(model.getBitcoinPaymentAddress());
        if (!model.isBitcoinPaymentAddressProvided()){
            // Make the text less visible for the sake of clarity
            bitcoinPaymentAddress.textField.getTextInputControl().getStyleClass().add("material-text-field-low-focus");
        } else {
            bitcoinPaymentAddress.textField.getTextInputControl().getStyleClass().add("material-text-field");
        }         
        mediator.textField.textProperty().bind(model.getMediator());
        // if (!model.isMediatorProvided()){
            // Make the text less visible for the sake of clarity
            // mediator.textField.getTextInputControl().getStyleClass().add("material-text-field-low-focus");
        // } else {
            // mediator.textField.getTextInputControl().getStyleClass().add("material-text-field");
        // }         
        marketPrice.textField.textProperty().bind(model.getMarketPrice());
        offerTakenTime.textField.textProperty().bind(model.getOfferTakenDateTime());
        tradeAmountBTC.textField.textProperty().bind(model.getAmountInBTC());
        fiatPaymentMethod.textField.textProperty().bind(model.getFiatPaymentMethod());
        bitcoinPaymentMethod.textField.textProperty().bind(model.getBitcoinPaymentMethod());

        paymentAccountData.textField.textProperty().bind(model.getPaymentAccountData());
        if (!model.isPaymentAccountDataProvided()){
            // Make the text less visible for the sake of clarity
            paymentAccountData.textField.getTextInputControl().getStyleClass().add("material-text-field-low-focus");
        } else {
            paymentAccountData.textField.getTextInputControl().getStyleClass().add("material-text-field");
        }         
        closeButton.setOnAction(e -> controller.onClose());
    }

    @Override
    protected void onViewDetached() {
        closeButton.setOnAction(null);
    }
}