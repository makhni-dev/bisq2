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
import javafx.beans.property.SimpleStringProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.Setter;

@Slf4j
@Getter
public class TradeDetailsModel extends TabModel {
    @Setter
    private String myNickname; 
    @Setter
    private String peerNickname; 
    @Setter
    private String tradeId; 
    @Setter
    private String myTag; 
    @Setter
    private String bitcoinPaymentData; 
    @Setter
    private String tradeAmount; 

    @Override
    public NavigationTarget getDefaultNavigationTarget() {
        return NavigationTarget.BISQ_EASY_TRADE_DETAILS;
    }
    public SimpleStringProperty getMyNickname() {
        return new SimpleStringProperty(myNickname);
    }
    public SimpleStringProperty getPeerNickname() {
        return new SimpleStringProperty(peerNickname);
    }
    public SimpleStringProperty getTradeId() {
        return new SimpleStringProperty(tradeId);
    }
    // create decorator for this
    public SimpleStringProperty getMyTag() {
        return new SimpleStringProperty(myTag);
    }
    public SimpleStringProperty getTradeAmount() {
        return new SimpleStringProperty(tradeAmount);
    }

    public SimpleStringProperty getBitcoinPaymentAddress() {
        if (bitcoinPaymentData == null){
            return new SimpleStringProperty("Not yet provided");
        }
        return new SimpleStringProperty(bitcoinPaymentData);
    }

}
