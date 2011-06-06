package pgoos.sniper

import pgoos.sniper.events.*

/**
 * Copy right of Prasanth Nath.
 * Date: 5/27/11, Time: 9:18 PM
 * Do not use without permission.
 *
 * Stateful representation of an Auction of some item.
 *
 */
class Auction {


    private interface State {
        void handle(AuctionEvent msg)

        boolean isLoosing(Bid bid)
    }

    class InitialState implements State {
        @Override
        void handle(AuctionEvent msg) {
            msg.handle(listener, Auction.this)
            switchTo(HANDLE_BID)
        }

        @Override
        boolean isLoosing(Bid bid) {
            return false
        }

    }
    State INITIAL = new InitialState()

    class BidState implements State {
        @Override
        void handle(AuctionEvent msg) {
            msg.handle(listener, Auction.this)
            postHandle(msg)
        }

        private def postHandle(AuctionEvent msg) {
            if (isResponseUpdateForOurOwnBid(msg)) {
                ourLastBidPrice = msg.price
            }
        }

        boolean isResponseUpdateForOurOwnBid(AuctionEvent event) {
            event instanceof Bid && event.client == clientId
        }

        boolean isLoosing(Bid bid) {
            isNotOurBid(bid) && !firstBidUpdate() && bid.isMoreThan(ourLastBidPrice as int)
        }

        private boolean isNotOurBid(Bid bid) {
            return !bid.belongsToClient(auctionId)
        }

    }

    boolean firstBidUpdate() {
        ourLastBidPrice == null
    }

    String ourLastBidPrice

    AutoBid autobid

    State HANDLE_BID = new BidState()

    class Closed implements State {
        @Override
        void handle(AuctionEvent msg) { }

        @Override
        boolean isLoosing(Bid bid) {return false }
    }

    State CLOSED = new Closed()

    class AutoBidState implements State {
        Sniper sniper


        AutoBidState(Sniper sniper) {
            this.sniper = sniper
        }

        @Override
        void handle(AuctionEvent msg) {
            HANDLE_BID.handle msg
            if (msg instanceof Bid) {
                Bid bid = msg
                autobid.bidHigher bid.price as int, sniper
            }
        }

        @Override
        boolean isLoosing(Bid bid) {
            HANDLE_BID.isLoosing bid
        }

    }

    String auctionId
    String clientId
    AuctionStateListener listener = AuctionStateListener.NONE
    State currentState = INITIAL


    public String toString() {
        auctionId
    }

    boolean isLoosing(Bid bid) {
        currentState.isLoosing bid
    }

    private def switchTo(State state) {
        currentState = state
    }

    def autoBid(AutoBid autoBid, Sniper sniper) {
        this.autobid = autoBid
        switchTo new AutoBidState(sniper)
    }

    def update(AuctionMessage message) {
        currentState.handle(EventFactory.createFrom(message))
    }

    void close() {
        switchTo CLOSED
    }

    boolean exceededStopPrice(Bid bid) {
        autobid?.exceededStopPrice(bid)
    }

}
