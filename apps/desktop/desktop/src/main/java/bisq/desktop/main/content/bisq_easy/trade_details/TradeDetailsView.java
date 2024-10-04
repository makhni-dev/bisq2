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

package bisq.desktop.main.content.bisq_easy.trade_details;

import bisq.bisq_easy.NavigationTarget;
import bisq.desktop.common.view.TabView;
import bisq.desktop.common.Styles;
import bisq.i18n.Res;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeDetailsView extends TabView<TradeDetailsModel, TradeDetailsController> {
    public TradeDetailsView(TradeDetailsModel model, TradeDetailsController controller) {
        super(model, controller);
        root.setPadding(new Insets(15, 30, 10, 30));
        VBox.setMargin(scrollPane, new Insets(20, 0, 0, 0));

        Styles styles = new Styles("bisq-text-grey-9", "bisq-text-white", "bisq-text-green", "bisq-text-grey-9");
        addTab(Res.get("bisqEasy.walletGuide.intro"),
                NavigationTarget.WALLET_GUIDE_INTRO,
                styles);
        addTab(Res.get("bisqEasy.walletGuide.download"),
                NavigationTarget.WALLET_GUIDE_DOWNLOAD,
                styles);
    }

    @Override
    protected void onViewAttached() {
        line.prefWidthProperty().unbind();
        double paddings = root.getPadding().getLeft() + root.getPadding().getRight();
        line.prefWidthProperty().bind(root.widthProperty().subtract(paddings));
    }

    @Override
    protected void onViewDetached() {
        line.prefWidthProperty().unbind();
    }
}