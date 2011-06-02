package pgoos.sniper;


import junit.framework.TestCase
import pgoos.sniper.events.Bid

public class MessageParserTest extends TestCase {
    void test_parses_msg_id() {
        assertEquals "someid", new MessageParser().parseId("B1:1.1:someid:Welcome")
    }

    void test_fetching_values_from_columns_in_message() {
        AuctionMessage m = new MessageParser().parse("B1:1.1:someItem:Close:123: someClient")
        assertEquals "123", m.column(4)
        assertEquals "someClient", m.column(5)
        assertEquals null, m.column(6)

    }

    void test_parsing_Bid() {
        Bid b = Bid.create(AuctionMessage.parseFrom("B1:1.1:someitem:Bid:10:someclient"))
        assertEquals "someitem", b.auctionId
        assertEquals "10", b.price
        assertEquals "someclient", b.client
    }
}
