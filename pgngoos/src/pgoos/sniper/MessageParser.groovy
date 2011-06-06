package pgoos.sniper

/**
 * Copy right of Prasanth Nath.
 * Date: 5/26/11, Time: 7:51 PM
 * Do not use without permission.
 */
class MessageParser {

  AuctionMessage parse(String message) {
    def tokens = tokens(message)
    def serverid = serverid(tokens)
    def type = type(tokens)
    def properties = message - prefix() - ":" - serverid - ":" - type
    AuctionMessage.createFrom(serverid, type, properties)

  }

  String prefix() {
    "B1:1.1"
  }

  private String[] tokens(String message) {
    return message.split(":")
  }

  def type(String[] tokens) {
    tokens[3]
  }

  def serverid(String[] tokens) {
    tokens[2]
  }

  String parseId(String message) {
    serverid(tokens(message))
  }
}
