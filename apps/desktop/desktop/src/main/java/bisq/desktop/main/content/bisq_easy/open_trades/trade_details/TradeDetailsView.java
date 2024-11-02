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
import bisq.desktop.common.utils.GridPaneUtil;
import bisq.desktop.common.view.NavigationView;
import bisq.desktop.components.containers.Spacer;
import bisq.desktop.components.controls.BisqIconButton;
import bisq.desktop.components.controls.MaterialTextField;
import bisq.desktop.overlay.OverlayModel;
import bisq.i18n.Res;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeDetailsView extends NavigationView<VBox, TradeDetailsModel, TradeDetailsController> {

    private static final int ROW_HEIGHT = 40;
    private static final int ROW_SPACING = 10;
    private final Label headline, processHeadline, processDescription, myRoleHeadline, amountHeadline, paymentMethodHeadline;
    private final Label tradePriceLabel, tradeIdLabel;
    private final Text makerTakerRole, buySellRole, tradeAmountBtc, btcPaymentMethod, tradeAmountFiat, currency;
    private final Text fiatPaymentMethod, btcLabel, tradePriceAmount, tradeId;
    private final Button closeButton;
    //    private final DetailsTextRow peerUsername, tradeId, myRole;
//    private final DetailsTextRow fiatPaymentMethod, peerNetworkAddress;
//    private final DetailsTextRow bitcoinPaymentMethod, tradePrice, offerTakenDateTime;
//    private final DetailsTextRow mediator;
//    private final DetailsRow tradeAmountFiat, tradeAmountBTC, paymentAccountData, bitcoinPaymentAddress;
    MaterialTextField btcPaymentAddress, paymentAccountData;
    VBox allInfoContainer;

    public TradeDetailsView(TradeDetailsModel model, TradeDetailsController controller) {
        super(new VBox(), model, controller);

        root.setPrefWidth(OverlayModel.WIDTH);
        root.setPrefHeight(OverlayModel.HEIGHT);
        closeButton = BisqIconButton.createIconButton("close");
        HBox closeButtonRow = new HBox(Spacer.fillHBox(), closeButton);
        closeButtonRow.setPadding(new Insets(0, 10, 0, 10));
        closeButtonRow.getStyleClass().add("trade-overview-top-row");
        closeButtonRow.setAlignment(Pos.CENTER_RIGHT);
        root.getChildren().add(closeButtonRow);

        Region emptySpace = new Region();
        emptySpace.setPrefHeight(30);
        headline = createLabel("trade-overview-headline");
        VBox headlineBox = new VBox(5, emptySpace, headline);
        headlineBox.setAlignment(Pos.CENTER);
        Region line1 = getLine();
        headlineBox.getChildren().add(line1);
        root.getChildren().add(headlineBox);

        // overview
        int numColumns = 3;
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setMouseTransparent(true);
        GridPaneUtil.setGridPaneMultiColumnsConstraints(gridPane, numColumns);

        int rowIndex = 0;
        myRoleHeadline = createLabel("trade-overview-text");
        gridPane.add(myRoleHeadline, 0, rowIndex);
        amountHeadline = createLabel("trade-overview-text");
        gridPane.add(amountHeadline, 1, rowIndex);
        paymentMethodHeadline = createLabel("trade-overview-text");
        gridPane.add(paymentMethodHeadline, 2, rowIndex);

        rowIndex++;
        makerTakerRole = createText("trade-overview-value");
        gridPane.add(makerTakerRole, 0, rowIndex);
        tradeAmountFiat = createText("trade-overview-value");
        HBox.setMargin(tradeAmountFiat, new Insets(0.5, 0, 0, 0));
        currency = createText("trade-overview-currency");
        HBox fiatText = new HBox(5, tradeAmountFiat, currency);
        fiatText.setAlignment(Pos.BASELINE_LEFT);
        gridPane.add(fiatText, 1, rowIndex);
        fiatPaymentMethod = createText("trade-overview-value");
        gridPane.add(fiatPaymentMethod, 2, rowIndex);

        rowIndex++;
        buySellRole = createText("trade-overview-value");
        gridPane.add(buySellRole, 0, rowIndex);
        tradeAmountBtc = createText("trade-overview-value");
        btcLabel = createText("trade-overview-currency");
        HBox btcText = new HBox(5, tradeAmountBtc, btcLabel);
        btcText.setAlignment(Pos.BASELINE_LEFT);
        gridPane.add(btcText, 1, rowIndex);
        btcPaymentMethod = createText("trade-overview-value");
        gridPane.add(btcPaymentMethod, 2, rowIndex);

        rowIndex++;
        Region emptyRow1 = new Region();
        GridPane.setColumnSpan(emptyRow1, numColumns);
        emptyRow1.setPrefHeight(40);
        gridPane.add(emptyRow1, 0, rowIndex);
        rowIndex++;
        Region line3 = getLine();
        GridPane.setColumnSpan(line3, numColumns);
        gridPane.add(line3, 0, rowIndex);

        rowIndex++;
        tradePriceLabel = createLabel("trade-overview-text");
//        gridPane.add(tradePriceLabel, 0, rowIndex);
        // change style
        tradePriceAmount = createText("trade-overview-section");
//        gridPane.add(tradePriceAmount, 1, rowIndex, 2, 1);
        rowIndex++;
        tradeIdLabel = createLabel("trade-overview-text");
//        gridPane.add(tradeIdLabel, 0, rowIndex);
        // change style
        tradeId = createText("trade-overview-section");
//        gridPane.add(tradeId, 1, rowIndex, 2, 1);


        // process
        rowIndex++;
        Region emptyRow2 = new Region();
        GridPane.setColumnSpan(emptyRow2, numColumns);
        emptyRow2.setPrefHeight(40);
        gridPane.add(emptyRow2, 0, rowIndex);
        rowIndex++;
        processHeadline = createLabel("trade-overview-section");
        GridPane.setColumnSpan(processHeadline, numColumns);
        gridPane.add(processHeadline, 0, rowIndex);
        rowIndex++;
        processDescription = createLabel("trade-overview-text");
        processDescription.setPadding(new Insets(-10, 0, 0, 0));
        GridPane.setColumnSpan(processDescription, numColumns);
        gridPane.add(processDescription, 0, rowIndex);
        rowIndex++;
        Region line2 = getLine();
        GridPane.setColumnSpan(line2, numColumns);
        gridPane.add(line2, 0, rowIndex);
        rowIndex++;
        btcPaymentAddress = new MaterialTextField(Res.get("bisqEasy.openTrades.tradeOverview.btcPaymentAddress"));
        btcPaymentAddress.setEditable(false);
        btcPaymentAddress.showCopyIcon();
        GridPane.setColumnSpan(btcPaymentAddress, numColumns);
        gridPane.add(btcPaymentAddress, 0, rowIndex);
        rowIndex++;
        paymentAccountData = new MaterialTextField(Res.get("bisqEasy.openTrades.tradeOverview.paymentAccountData"));
        paymentAccountData.setEditable(false);
        paymentAccountData.showCopyIcon();
        GridPane.setColumnSpan(paymentAccountData, numColumns);
        gridPane.add(paymentAccountData, 0, rowIndex);

//        ColumnConstraints column1 = new ColumnConstraints();
//        column1.setPercentWidth(25);
//        ColumnConstraints column2 = new ColumnConstraints();
//        column2.setPercentWidth(30);
//        ColumnConstraints column3 = new ColumnConstraints();
//        column2.setPercentWidth(45);
//        gridPane.getColumnConstraints().addAll(column1, column2, column3);
        gridPane.setPadding(new Insets(10, 40, 10, 20));
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.setFitToWidth(true);
        VBox container = new VBox(scrollPane);
        container.setPadding(new Insets(10, 22, 10, 20));
        root.getChildren().addAll(container);
    }

    @Override
    protected void onViewAttached() {
        headline.setText(Res.get("bisqEasy.openTrades.tradeOverview.headline"));
        processHeadline.setText(Res.get("bisqEasy.openTrades.tradeOverview.processSection"));
        myRoleHeadline.setText(Res.get("bisqEasy.openTrades.tradeOverview.myRole"));
        amountHeadline.setText(Res.get("bisqEasy.openTrades.tradeOverview.amount"));
        paymentMethodHeadline.setText(Res.get("bisqEasy.openTrades.tradeOverview.paymentMethod"));
        processDescription.setText(Res.get("bisqEasy.openTrades.tradeOverview.processDescription"));
        btcLabel.setText("BTC");
        tradePriceLabel.setText(Res.get("bisqEasy.openTrades.tradeOverview.tradePrice"));
        tradeIdLabel.setText(Res.get("bisqEasy.openTrades.table.tradeId"));

        buySellRole.textProperty().bind(model.getMySellBuyRole());
        makerTakerRole.textProperty().bind(model.getMyMakerTakerRole());
        tradeAmountFiat.textProperty().bind(model.getAmountInFiat());
        currency.textProperty().bind(model.getCurrency());
        tradeAmountBtc.textProperty().bind(model.getAmountInBTC());
        btcPaymentMethod.textProperty().bind(model.getBitcoinPaymentMethod());
        fiatPaymentMethod.textProperty().bind(model.getFiatPaymentMethod());
        btcPaymentAddress.textProperty().bind(model.getBitcoinPaymentAddress());
        paymentAccountData.textProperty().bind(model.getPaymentAccountData());
        tradePriceAmount.textProperty().bind(model.getTradePrice());
        tradeId.textProperty().bind(model.getTradeId());
        //   allInfoContainer.prefHeightProperty().bind(root.heightProperty());
        //   allInfoContainer.prefWidthProperty().bind(root.widthProperty());
//        peerUsername.textField.textProperty().bind(model.getPeerUsername());
//        tradeId.textField.textProperty().bind(model.getTradeId());
//        tradeAmountFiat.description.textProperty().bind(model.getCurrencyDescription());
//        tradeAmountFiat.textField.textProperty().bind(model.getAmountInFiat());
//        bitcoinPaymentAddress.textField.textProperty().bind(model.getBitcoinPaymentAddress());
//
//        tradePrice.textField.textProperty().bind(model.getTradePrice());
//        offerTakenDateTime.textField.textProperty().bind(model.getOfferTakenDateTime());
//        tradeAmountBTC.textField.textProperty().bind(model.getAmountInBTC());
//        fiatPaymentMethod.textField.textProperty().bind(model.getFiatPaymentMethod());
//        bitcoinPaymentMethod.textField.textProperty().bind(model.getBitcoinPaymentMethod());
//
//        paymentAccountData.textField.textProperty().bind(model.getPaymentAccountData());
//        myRole.textField.textProperty().bind(model.getMyRole());
//        peerNetworkAddress.textField.textProperty().bind(model.getPeerNetworkAddress());
//        mediator.textField.textProperty().bind(model.getMediator());
//
//        applyDarkTextColorIfNotProvided(bitcoinPaymentAddress.textField.getTextInputControl(), !model.isBitcoinPaymentAddressProvided());
//        applyDarkTextColorIfNotProvided(paymentAccountData.textField.getTextInputControl(), !model.isPaymentAccountDataProvided());
//        applyDarkTextColorIfNotProvided(mediator.textField, !model.isMediatorProvided());

        closeButton.setOnAction(e -> controller.onClose());
    }

    @Override
    protected void onViewDetached() {
        buySellRole.textProperty().unbind();
        makerTakerRole.textProperty().unbind();
        tradeAmountFiat.textProperty().unbind();
        currency.textProperty().unbind();
        tradeAmountBtc.textProperty().unbind();
        btcPaymentMethod.textProperty().unbind();
        fiatPaymentMethod.textProperty().unbind();
        btcPaymentAddress.textProperty().unbind();
        paymentAccountData.textProperty().unbind();
        tradePriceAmount.textProperty().unbind();
        tradeId.textProperty().unbind();
        //   allInfoContainer.prefHeightProperty().unbind();
        //   allInfoContainer.prefWidthProperty().unbind();

        //     peerUsername.textField.textProperty().unbind();
        //     tradeId.textField.textProperty().unbind();
        //     tradeAmountFiat.description.textProperty().unbind();
        //     tradeAmountFiat.textField.textProperty().unbind();
        //     bitcoinPaymentAddress.textField.textProperty().unbind();

        //     tradePrice.textField.textProperty().unbind();
        //     offerTakenDateTime.textField.textProperty().unbind();
        //     tradeAmountBTC.textField.textProperty().unbind();
        //     fiatPaymentMethod.textField.textProperty().unbind();
        //     bitcoinPaymentMethod.textField.textProperty().unbind();

        //     paymentAccountData.textField.textProperty().unbind();
        //     myRole.textField.textProperty().unbind();
        //     peerNetworkAddress.textField.textProperty().unbind();
        //     mediator.textField.textProperty().unbind();

        closeButton.setOnAction(null);
    }

    private Label createLabel(String style) {
        Label label = new Label();
        label.getStyleClass().add(style);
        return label;
    }

    private Text createText(String style) {
        Text text = new Text();
        text.getStyleClass().add(style);
        return text;
    }

    // copied from TradeWizardReviewView
    private Region getLine() {
        Region line = new Region();
        line.setMinHeight(1);
        line.setMaxHeight(1);
        line.setStyle("-fx-background-color: -bisq-border-color-grey");
        line.setPadding(new Insets(9, 0, 8, 0));
        return line;
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