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
import bisq.user.profile.UserProfile;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Optional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import lombok.Setter;

@Slf4j
@Getter
public class TradeDetailsModel extends TabModel {
    @Setter
    private String myUsername; 
    @Setter
    private String peerUsername; 
    @Setter
    private String tradeId; 
    @Setter
    private String amountInFiat; 

    @Setter
    private String bitcoinPaymentData; 
    @Setter
    private String amountInBTC; 
    @Setter
    private String marketPrice; 
    @Setter
    private String offerTakenDateTime; 
    private String mediator; 

    @Override
    public NavigationTarget getDefaultNavigationTarget() {
        return NavigationTarget.BISQ_EASY_TRADE_DETAILS;
    }
    public SimpleStringProperty getMyUsername() {
        return new SimpleStringProperty(myUsername);
    }
    public SimpleStringProperty getPeerUsername() {
        return new SimpleStringProperty(peerUsername);
    }
    public SimpleStringProperty getTradeId() {
        return new SimpleStringProperty(tradeId);
    }
    public void setMediator(Optional<UserProfile> mediator){
        if (mediator.isEmpty()) {
            this.mediator = null;
        } 
        else{
            this.mediator = mediator.get().getUserName();
        }
    }
    public SimpleStringProperty getMediator() {
        if (mediator == null){
            return new SimpleStringProperty("No mediator provided");
        }
        return new SimpleStringProperty(mediator);
    }
    public SimpleStringProperty getAmountInFiat() {
        return new SimpleStringProperty(amountInFiat);
    }
    public SimpleStringProperty getAmountInBTC() {
        return new SimpleStringProperty(amountInBTC);
    }
    public SimpleStringProperty getMarketPrice() {
        return new SimpleStringProperty(marketPrice);
    }
    public SimpleStringProperty getOfferTakenDateTime() {
        return new SimpleStringProperty(offerTakenDateTime);
    }

    public SimpleStringProperty getBitcoinPaymentAddress() {
        if (bitcoinPaymentData == null){
            return new SimpleStringProperty("Not yet provided");
        }
        return new SimpleStringProperty(bitcoinPaymentData);
    }

}
