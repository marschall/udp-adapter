package com.github.marschall.udpadapter;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.resource.spi.UnavailableException;
import javax.resource.spi.endpoint.MessageEndpointFactory;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

class SampleWorkerThread {

  // Reader Thread(s)
  // 1. Strip off msg header and parse message description
  // 2. Choose a set of endpoints which match message description // 3. Place message in appropriate buffer / queue
  // 4. Notify worker threads

  // Worker Thread(s)

  void run() throws UnavailableException {

    // 1. Wake up on notification (message arrival)
    // 2. Pick up the message and locate the MessageEndpointFactory // associated with the subscription
    Message msg = null;
    MessageEndpointFactory endpointFactory = null;
    MyXAResource xaResource = null; // for transacted delivery
    // 4. Obtain a message endpoint and narrow it to the
    // correct type.
    // ActivationSpec has endpoint message listener type
    // information.
    Object obj = endpointFactory.createEndpoint(xaResource);
    MessageListener endpoint = (MessageListener) obj;
    // 5. Link the XAResource with the endpoint. This allows the
    // XAResource object to know which endpoint/message delivery
    // is transacted when it receives transaction notifications.
    // This may be unnecessary depending on the implementation.
    //		xaResource.setEndpoint(endpoint);

    // Note: It may be possible to make the XAResource object
    // thread-safe/reentrant and reuse the same object for receiving // transaction notifications for different endpoints.
    // The XAResource object may use thread-local storage to
    // remember state, and thus avoid creating multiple
    // XAResource objects.
    // 6. Deliver the message.
    endpoint.onMessage(msg);
    // 7. Wait for notification of incoming messages
    // and repeat the above steps on message arrival.
  }

  class MyXAResource implements XAResource {
    public void start(Xid xid) throws XAException {
      // Associate the message delivery with the transaction id.
      // That is, create the tuple (msg id, transaction id) in
      // memory.
    }
    public int prepare(Xid xid) throws XAException {
      // Forward the tuple (message id, transaction id) to the
      // message provider. The provider must persist this
      // information, which is used during crash recovery by the
      // application server. During crash recovery,
      // the application calls the message provider, via the
      // recover method on an XAResource object, queries for
      // a list of in-doubt transactions and completes them.
      // Upon successful completion, return "ready_to_commit"
      // vote, else return "rollback_only" vote.
      return 0;
    }
    public void commit(Xid xid, boolean onePhase) throws XAException {
      // forward the transaction ID to the message provider. This
      // serves as the acknowledgement that a message was
      // delivered.
    }
    public void rollback(Xid xid) throws XAException {
      // forward the transaction ID to the message provider. This
      // indicates to the provider that the message was not
      // delivered.
    }

    @Override
    public Xid[] recover(int arg0) throws XAException {
      // TODO Auto-generated method stub
      return null;
    }
    @Override
    public void end(Xid xid, int flags) throws XAException {
      // TODO Auto-generated method stub

    }
    @Override
    public void forget(Xid xid) throws XAException {
      // TODO Auto-generated method stub

    }
    @Override
    public int getTransactionTimeout() throws XAException {
      // TODO Auto-generated method stub
      return 0;
    }
    @Override
    public boolean isSameRM(XAResource xares) throws XAException {
      // TODO Auto-generated method stub
      return false;
    }
    @Override
    public boolean setTransactionTimeout(int seconds) throws XAException {
      // TODO Auto-generated method stub
      return false;
    }
    @Override
    public void start(Xid xid, int flags) throws XAException {
      // TODO Auto-generated method stub

    }

    // other methods
  }

}
