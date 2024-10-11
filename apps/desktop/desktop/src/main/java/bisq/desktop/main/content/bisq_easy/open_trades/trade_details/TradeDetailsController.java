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

import lombok.extern.slf4j.Slf4j;
import bisq.desktop.common.view.Controller;
import bisq.desktop.common.view.TabController;
import bisq.bisq_easy.NavigationTarget;
import bisq.desktop.ServiceProvider;
import bisq.desktop.overlay.OverlayController;
import bisq.user.identity.UserIdentityService;
import javafx.beans.property.SimpleStringProperty;
import bisq.chat.bisqeasy.open_trades.BisqEasyOpenTradeChannel;
import bisq.chat.bisqeasy.open_trades.BisqEasyOpenTradeChannelService;
import bisq.trade.bisq_easy.BisqEasyTradeService;
import bisq.desktop.common.view.NavigationController;
import bisq.desktop.common.view.InitWithDataController;
import bisq.trade.bisq_easy.BisqEasyTrade;
import bisq.presentation.formatters.PriceFormatter;
import bisq.desktop.main.content.bisq_easy.BisqEasyServiceUtil;
import bisq.trade.bisq_easy.BisqEasyTradeUtils;

import java.util.Optional;

import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Slf4j
public class TradeDetailsController extends NavigationController implements InitWithDataController<TradeDetailsController.InitData> {
    @Getter
    @EqualsAndHashCode
    @ToString
    public static class InitData {
        private final BisqEasyTrade bisqEasyTrade;
        private final BisqEasyOpenTradeChannel channel;

        public InitData(BisqEasyTrade BisqEasyTrade, BisqEasyOpenTradeChannel channel) {
            this.bisqEasyTrade = BisqEasyTrade;
            this.channel = channel;
        }
    }

    @Getter
    private final TradeDetailsView view;

    @Getter
    private final TradeDetailsModel model;

    private final ServiceProvider serviceProvider;
    protected final UserIdentityService userIdentityService;

    public TradeDetailsController(ServiceProvider serviceProvider) {
        super(NavigationTarget.BISQ_EASY_TRADE_DETAILS);

        this.serviceProvider = serviceProvider;
        userIdentityService = serviceProvider.getUserService().getUserIdentityService();
        model = new TradeDetailsModel();
        view = new TradeDetailsView(model, this);
    }

    @Override
    public void initWithData(InitData initData) {
        BisqEasyTrade trade = initData.getBisqEasyTrade();
        BisqEasyOpenTradeChannel channel = initData.channel;
        model.setTradeId(trade.getId());
        model.setMyUsername(channel.getMyUserIdentity().getUserName());
        model.setPeerUsername(channel.getPeer().getUserName());
        model.setBitcoinPaymentData(trade.getBitcoinPaymentData().get());
        model.setMediator(channel.getMediator());

        // System.out.println(PriceFormatter.format(trade.getContract().getAgreedPriceSpec(), true));
        // trade.getOffer().get

        model.setMarketPrice(PriceFormatter.format(BisqEasyTradeUtils.getPriceQuote(trade)));
        // String marketPrice = BisqEasyServiceUtil.getFormattedPriceSpec(trade.getContract().getAgreedPriceSpec());
        // model.setMarketPrice(new SimpleStringProperty(marketPrice));

        model.setMyTag(trade.getMyIdentity().getTag());
        model.setTradeAmount(trade.getOffer().getAmountSpec().toString());
    }

    @Override
    public void onActivate() {
    }

    @Override
    public void onDeactivate() {
    }

    @Override
    protected Optional<? extends Controller> createController(NavigationTarget navigationTarget) {
        return Optional.empty();
    }

    void onClose() {
        OverlayController.hide();
    }
}