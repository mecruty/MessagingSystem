package persistence;

import model.Account;
import model.Message;
import model.PostOffice;

public class JsonTest {
    protected PostOffice buildPostOffice() {
        PostOffice po = new PostOffice();
        Account alice = new Account("alice", "1234");
        Account bob = new Account("bob", "2345");
        po.addAccount("alice", alice);
        po.addAccount("bob", bob);;
        alice.beginConversation(bob);
        alice.sendMessage(bob, new Message(alice, "hi"));
        bob.sendMessage(alice, new Message(bob, "hello"));
        bob.sendMessage(alice, new Message(bob, "How are you?"));
        alice.sendMessage(bob, new Message(alice, "good"));

        return po;
    }
}
