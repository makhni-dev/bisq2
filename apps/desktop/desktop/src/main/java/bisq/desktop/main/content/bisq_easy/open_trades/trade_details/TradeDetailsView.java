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
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import lombok.extern.slf4j.Slf4j;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import bisq.desktop.components.table.BisqTableView;
import bisq.desktop.components.table.DateTableItem;

@Slf4j
public class TradeDetailsView extends NavigationView<AnchorPane, TradeDetailsModel, TradeDetailsController> {

    private final Button closeButton;

    // box containing all details
    private final VBox detailsContainer;
    private final MaterialTextField myNickname;
    private final MaterialTextField peerNickname;
    private final MaterialTextField tradeId;
    private final MaterialTextField myTag;
    private final MaterialTextField tradeAmount;
    private final MaterialTextField bitcoinPaymentAddress;
    // private final BisqTableView<ListItem> tableView;

    public TradeDetailsView(TradeDetailsModel model, TradeDetailsController controller) {
        super(new AnchorPane(), model, controller);

        root.setPrefWidth(OverlayModel.WIDTH);
        root.setPrefHeight(OverlayModel.HEIGHT);

        // tableView = new BisqTableView<>(tableView.getItems());
        // tableView.getStyleClass().addAll("bisq-easy-open-trades");
        detailsContainer = new VBox(5);
        // detailsContainer.getStyleClass().add("bisq-easy-container-headline");
        detailsContainer.setPadding(new Insets(30, 30, 30, 30));
        Label headline = new Label("Trade details");
        detailsContainer.getChildren().add(headline);
        detailsContainer.prefHeightProperty().bind(root.heightProperty());
        detailsContainer.prefWidthProperty().bind(root.widthProperty());

        headline.getStyleClass().add("chat-guide-headline");
        root.getChildren().add(detailsContainer);

        myNickname = createMaterialTextField("My nickname", "a tooltip");
        peerNickname = createMaterialTextField("Peer nickname", "a tooltip");
        tradeId = createMaterialTextField("Trade ID","to be done");
        myTag = createMaterialTextField("my tag", "a tooltop");
        tradeAmount = createMaterialTextField("trade amount", "a tooltop");
        bitcoinPaymentAddress = createMaterialTextField("Bitcoin payment address", "a tooltop");


        closeButton = BisqIconButton.createIconButton("close");
        Layout.pinToAnchorPane(closeButton, 16, 20, null, null);
        root.getChildren().add(closeButton);
    }

    private MaterialTextField createMaterialTextField(String description, String tooltip){
        MaterialTextField field = new MaterialTextField(description, null);
        field.setEditable(false);
        field.showCopyIcon();
        
        detailsContainer.getChildren().add(field);
        // field.setIconTooltip(tooltip);
        return field;
    }

    @Override
    protected void onViewAttached() {
        myNickname.textProperty().bind(model.getMyNickname());
        peerNickname.textProperty().bind(model.getPeerNickname());
        tradeId.textProperty().bind(model.getTradeId());
        myTag.textProperty().bind(model.getMyTag());
        bitcoinPaymentAddress.textProperty().bind(model.getBitcoinPaymentAddress());
        tradeAmount.textProperty().bind(model.getTradeAmount());
        closeButton.setOnAction(e -> controller.onClose());
    }

    @Override
    protected void onViewDetached() {
        closeButton.setOnAction(null);
    }
}