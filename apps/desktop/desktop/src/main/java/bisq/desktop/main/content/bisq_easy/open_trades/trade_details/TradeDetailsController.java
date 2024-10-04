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
import bisq.common.network.AddressByTransportTypeMap;
import bisq.common.util.StringUtils;
import bisq.contract.bisq_easy.BisqEasyContract;
import bisq.desktop.common.view.Controller;
import bisq.desktop.common.view.InitWithDataController;
import bisq.desktop.common.view.NavigationController;
import bisq.desktop.overlay.OverlayController;
import bisq.desktop.ServiceProvider;
import bisq.network.identity.NetworkId;
import bisq.presentation.formatters.AmountFormatter;
import bisq.presentation.formatters.DateFormatter;
import bisq.presentation.formatters.PriceFormatter;
import bisq.trade.bisq_easy.BisqEasyTrade;
import bisq.trade.bisq_easy.BisqEasyTradeFormatter;
import bisq.trade.bisq_easy.BisqEasyTradeUtils;
import bisq.user.identity.UserIdentityService;
import com.google.common.base.Joiner;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.ToString;

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
        model.setTradeId(new SimpleStringProperty(trade.getId()));
        model.setMyUsername(new SimpleStringProperty(channel.getMyUserIdentity().getUserName()));
        model.setPeerUsername(new SimpleStringProperty(channel.getPeer().getUserName()));
        model.setBitcoinPaymentAddress(new SimpleStringProperty(trade.getBitcoinPaymentData().get()));

        BisqEasyContract contract = trade.getContract();
        long date = contract.getTakeOfferDate();
        model.setOfferTakenDateTime(new SimpleStringProperty(DateFormatter.formatDateTime(date)));

        model.setMarketPrice(new SimpleStringProperty(
                PriceFormatter.formatWithCode(BisqEasyTradeUtils.getPriceQuote(trade), false)));
        long quoteSideAmount = contract.getQuoteSideAmount();
        Monetary quoteAmount = Fiat.from(quoteSideAmount, trade.getOffer().getMarket().getQuoteCurrencyCode());

        NetworkId peerNetworkId = trade.getPeer().getNetworkId();
        String peerAddress = formatNetworkAddresses(peerNetworkId.getAddressByTransportTypeMap());
        model.setPeerNetworkAddress(new SimpleStringProperty(peerAddress));
        NetworkId myNetworkId = trade.getMyself().getNetworkId();
        String myAddress = formatNetworkAddresses(myNetworkId.getAddressByTransportTypeMap());
        model.setMyNetworkAddress(new SimpleStringProperty(myAddress));
        
        String amountInFiat = AmountFormatter.formatAmount(quoteAmount);
        String currencyAbbreviation = quoteAmount.getCode();
        model.setAmountInFiat(new SimpleStringProperty(amountInFiat + " " + currencyAbbreviation));

        long baseSideAmount = contract.getBaseSideAmount();
        Coin amountInBTC = Coin.asBtcFromValue(baseSideAmount);
        String baseAmountString = AmountFormatter.formatAmount(amountInBTC, false);
        model.setAmountInBTC(new SimpleStringProperty(baseAmountString));

        String btcPaymentMethod = contract.getBaseSidePaymentMethodSpec().getDisplayString();
        model.setBitcoinPaymentMethod(new SimpleStringProperty(btcPaymentMethod));
        String fiatPaymentMethod = contract.getQuoteSidePaymentMethodSpec().getDisplayString();
        model.setFiatPaymentMethod(new SimpleStringProperty(fiatPaymentMethod));

        String paymentAccountData = trade.getPaymentAccountData().get();
        model.setPaymentAccountData(new SimpleStringProperty(paymentAccountData));

        String myRole = BisqEasyTradeFormatter.getMakerTakerRole(trade);
        model.setMyRole(new SimpleStringProperty(myRole));
    }

    public String formatNetworkAddresses(AddressByTransportTypeMap addressMap) {
        return Joiner.on("; ").join(addressMap.entrySet().stream()
                .map(e -> StringUtils.truncate(e.getValue().getFullAddress(), Integer.MAX_VALUE))
                .collect(Collectors.toList()));
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