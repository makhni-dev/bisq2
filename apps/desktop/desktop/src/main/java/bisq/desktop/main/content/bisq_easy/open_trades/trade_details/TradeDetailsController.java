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

import java.util.Optional;

import bisq.bisq_easy.NavigationTarget;
import bisq.chat.bisqeasy.open_trades.BisqEasyOpenTradeChannel;
import bisq.common.monetary.Coin;
import bisq.common.monetary.Fiat;
import bisq.common.monetary.Monetary;
import bisq.contract.bisq_easy.BisqEasyContract;
import bisq.desktop.ServiceProvider;
import bisq.desktop.common.view.Controller;
import bisq.desktop.common.view.InitWithDataController;
import bisq.desktop.common.view.NavigationController;
import bisq.desktop.overlay.OverlayController;
import bisq.presentation.formatters.AmountFormatter;
import bisq.presentation.formatters.DateFormatter;
import bisq.presentation.formatters.PriceFormatter;
import bisq.trade.bisq_easy.BisqEasyTrade;
import bisq.trade.bisq_easy.BisqEasyTradeUtils;
import bisq.user.identity.UserIdentityService;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TradeDetailsController extends NavigationController
        implements InitWithDataController<TradeDetailsController.InitData> {
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

    protected final UserIdentityService userIdentityService;

    public TradeDetailsController(ServiceProvider serviceProvider) {
        super(NavigationTarget.BISQ_EASY_TRADE_DETAILS);

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
        model.setBitcoinPaymentAddress(trade.getBitcoinPaymentData().get());
        model.setMediator(channel.getMediator());

        BisqEasyContract contract = trade.getContract();
        long date = contract.getTakeOfferDate();
        model.setOfferTakenDateTime(DateFormatter.formatDateTime(date));

        model.setMarketPrice(PriceFormatter.formatWithCode(BisqEasyTradeUtils.getPriceQuote(trade), false));

        long quoteSideAmount = contract.getQuoteSideAmount();
        Monetary quoteAmount = Fiat.from(quoteSideAmount, trade.getOffer().getMarket().getQuoteCurrencyCode());
        String amountInFiat = AmountFormatter.formatAmount(quoteAmount);
        String currencyAbbreviation = quoteAmount.getCode();
        model.setAmountInFiat(amountInFiat + " " + currencyAbbreviation);

        long baseSideAmount = contract.getBaseSideAmount();
        Coin amountInBTC = Coin.asBtcFromValue(baseSideAmount);
        String baseAmountString = AmountFormatter.formatAmount(amountInBTC, false);
        model.setAmountInBTC(baseAmountString);

        String btcPaymentMethod = contract.getBaseSidePaymentMethodSpec().getDisplayString();
        model.setBitcoinPaymentMethod(btcPaymentMethod);
        String fiatPaymentMethod = contract.getQuoteSidePaymentMethodSpec().getDisplayString();
        model.setFiatPaymentMethod(fiatPaymentMethod);

        String paymentAccountData = trade.getPaymentAccountData().get();
        model.setPaymentAccountData(paymentAccountData);
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