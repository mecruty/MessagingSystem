package persistence;

import static org.junit.Assert.fail;

import model.Account;
import model.Conversation;
import model.Message;
import model.PostOffice;

public class JsonTest {

    protected PostOffice buildPostOffice() {
        PostOffice po = new PostOffice();
        Account alice = new Account("alice", "1234");
        Account bob = new Account("bob", "2345");
        po.addAccount("alice", alice);
        po.addAccount("bob", bob);
        alice.beginConversation(bob);
        alice.sendMessage(bob, new Message(alice, "hi"));
        bob.sendMessage(alice, new Message(bob, "hello"));
        bob.sendMessage(alice, new Message(bob, "How are you?"));
        alice.sendMessage(bob, new Message(alice, "good"));
        
        return po;
    }

    protected void testPostOffice(PostOffice po) {
        checkTrue(po.getAccounts().size() == 2);
        checkTrue(po.getAccounts().containsKey("alice"));
        checkTrue(po.getAccounts().containsKey("bob"));
        Account alice = po.getAccounts().get("alice");
        Account bob = po.getAccounts().get("bob");
        checkTrue(alice.getName().equals("alice"));
        checkTrue(alice.getPassword().equals("1234"));
        checkTrue(bob.getName().equals("bob"));
        checkTrue(bob.getPassword().equals("2345"));

        checkTrue(alice.getConversations().size() == 1);
        checkTrue(bob.getConversations().size() == 1);
        checkTrue(alice.getConversations().containsKey(bob));
        checkTrue(bob.getConversations().containsKey(alice));
        Conversation convoAlice = alice.getConversations().get(bob);
        Conversation convoBob = alice.getConversations().get(bob);
        checkTrue(convoAlice == convoBob);

        checkTrue(convoAlice.getMessages().size() == 4);
        Message m1 = convoAlice.getMessages().get(0);
        Message m2 = convoAlice.getMessages().get(1);
        Message m3 = convoAlice.getMessages().get(2);
        Message m4 = convoAlice.getMessages().get(3);
        checkTrue(m1.getSender().equals(alice));
        checkTrue(m1.getValue().equals("hi"));
        checkTrue(m2.getSender().equals(bob));
        checkTrue(m2.getValue().equals("hello"));
        checkTrue(m3.getSender().equals(bob));
        checkTrue(m3.getValue().equals("How are you?"));
        checkTrue(m4.getSender().equals(alice));
        checkTrue(m4.getValue().equals("good"));
    }

    private void checkTrue(boolean statement) {
        if (!statement) {
            fail("Post Office not equal to example Post Office");
        }
    }
}
