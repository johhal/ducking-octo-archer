package network;

public class Job<E> {

    private E message;
    private Session session;

    public Job(Session session, E message) {
        this.message = message;
        this.session = session;
    }


    public Session getSession() {
        return session;
    }


    public E getMessage() {
        return message;
    }
}
