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

import lombok.extern.slf4j.Slf4j;
import bisq.desktop.common.view.Controller;
import bisq.desktop.common.view.TabController;
import bisq.bisq_easy.NavigationTarget;
import bisq.desktop.ServiceProvider;

import java.util.Optional;

import lombok.Getter;

@Slf4j
public class TradeDetailsController extends TabController<TradeDetailsModel> {
    @Getter
    private final TradeDetailsView view;
    private final ServiceProvider serviceProvider;

    public TradeDetailsController(ServiceProvider serviceProvider) {
        super(new TradeDetailsModel(), NavigationTarget.TRADE_DETAILS);

        this.serviceProvider = serviceProvider;
        view = new TradeDetailsView(model, this);
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

// is this needed?
    public void onQuit() {
        serviceProvider.getShutDownHandler().shutdown();
    }
}