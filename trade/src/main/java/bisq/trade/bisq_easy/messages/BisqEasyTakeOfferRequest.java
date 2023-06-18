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

package bisq.trade.bisq_easy.messages;

import bisq.contract.ContractSignatureData;
import bisq.contract.bisq_easy.BisqEasyContract;
import bisq.network.NetworkId;
import bisq.network.p2p.services.data.storage.MetaData;
import bisq.trade.TradeMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
@ToString(callSuper = true)
@Getter
@EqualsAndHashCode(callSuper = true)
public class BisqEasyTakeOfferRequest extends TradeMessage {
    public final static long TTL = TimeUnit.DAYS.toMillis(10);

    private final BisqEasyContract bisqEasyContract;
    private final ContractSignatureData contractSignatureData;

    public BisqEasyTakeOfferRequest(NetworkId sender, BisqEasyContract bisqEasyContract, ContractSignatureData contractSignatureData) {
        this(sender,
                bisqEasyContract,
                contractSignatureData,
                new MetaData(TTL, 100000, BisqEasyTakeOfferRequest.class.getSimpleName()));
    }

    private BisqEasyTakeOfferRequest(NetworkId sender, BisqEasyContract bisqEasyContract, ContractSignatureData contractSignatureData, MetaData metaData) {
        super(sender, metaData);

        this.bisqEasyContract = bisqEasyContract;
        this.contractSignatureData = contractSignatureData;
    }

    @Override
    protected bisq.trade.protobuf.TradeMessage toTradeMessageProto() {
        return getTradeMessageBuilder().setBisqEasyTakeOfferRequest(
                        bisq.trade.protobuf.BisqEasyTakeOfferRequest.newBuilder()
                                .setBisqEasyContract(bisqEasyContract.toProto())
                                .setContractSignatureData(contractSignatureData.toProto()))
                .build();
    }

    public static BisqEasyTakeOfferRequest fromProto(bisq.trade.protobuf.TradeMessage proto) {
        bisq.trade.protobuf.BisqEasyTakeOfferRequest bisqEasyTakeOfferRequest = proto.getBisqEasyTakeOfferRequest();
        return new BisqEasyTakeOfferRequest(
                NetworkId.fromProto(proto.getSender()),
                BisqEasyContract.fromProto(bisqEasyTakeOfferRequest.getBisqEasyContract()),
                ContractSignatureData.fromProto(bisqEasyTakeOfferRequest.getContractSignatureData()),
                MetaData.fromProto(proto.getMetaData()));
    }
}