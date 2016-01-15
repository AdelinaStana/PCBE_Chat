Messaging Server

A server that can be used by applications to communicate
to each other through messages. Three
activities can be performed using the server:
- Messaging
- Reception of messages
- Administration

Messages are sent and received using two types of
resources offered by server:
- Message queues
- Topics

Message queues can hold a maximum number of messages at a time,
and work as FIFO structure. Each message specifies
a recipient in the message header. Programs that are not marked as recipient 
for a message will not receive that mesage. Consuming a message involves
removing it from the queue.

Topic resources allow publication of messages that can be read
by an unlimited number of clients. There are no explicit recipients, but 
each mesage has a certain type, stored in a field in its header. 
Clients specify the type of the messages they want to rea. 
Messages are not removed when read by clients, but
they will be automatically deleted after a timeout specified in the 
message header. The server may be configured with a maximum time 
for storing messages (expiration time), that supercedes the timeout in the 
header. Expired messaged will be automatically deleted.
