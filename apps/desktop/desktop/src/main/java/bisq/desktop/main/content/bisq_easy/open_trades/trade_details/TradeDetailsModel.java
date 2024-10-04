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

import bisq.bisq_easy.NavigationTarget;
import bisq.desktop.common.view.TabModel;
import bisq.i18n.Res;
import javafx.beans.property.SimpleStringProperty;
import lombok.extern.slf4j.Slf4j;
import lombok.Getter;
import lombok.Setter;

@Slf4j
@Getter
public class TradeDetailsModel extends TabModel {
    @Setter
    private SimpleStringProperty myUsername, peerUsername, tradeId, amountInFiat;
    @Setter
    private SimpleStringProperty bitcoinPaymentAddress, amountInBTC, marketPrice, myRole;
    @Setter
    private SimpleStringProperty offerTakenDateTime, fiatPaymentMethod, bitcoinPaymentMethod, paymentAccountData;
    @Setter
    private SimpleStringProperty peerNetworkAddress, myNetworkAddress;

    @Getter
    private boolean paymentAccountDataProvided, bitcoinPaymentAddressProvided;

    @Override
    public NavigationTarget getDefaultNavigationTarget() {
        return NavigationTarget.BISQ_EASY_TRADE_DETAILS;
    }

    public SimpleStringProperty getBitcoinPaymentAddress() {
        if (bitcoinPaymentAddress.get() == null) {
            bitcoinPaymentAddressProvided = false;
            return new SimpleStringProperty(Res.get("bisqEasy.openTrades.tradeDetails.dataNotYetProvided"));
        }
        bitcoinPaymentAddressProvided = true;
        return bitcoinPaymentAddress;
    }

    public SimpleStringProperty getPaymentAccountData() {
        if (paymentAccountData.get() == null) {
            paymentAccountDataProvided = false;
            return new SimpleStringProperty(Res.get("bisqEasy.openTrades.tradeDetails.dataNotYetProvided"));
        }
        paymentAccountDataProvided = true;
        return paymentAccountData;
    }

}
