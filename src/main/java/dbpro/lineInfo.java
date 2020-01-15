package dbpro;

public class lineInfo {

    private int line;
    private long track;
    private int order;

    public lineInfo(int line, long track, int order) {

        this.line = line;
        this.track = track;
        this.order = order;

    }

    public String toString() {
        return "line: " + line + " track: " + track + " order:" + order;
    }

    public int getLine() {
        return this.line;
    }

    public long getTrack() {
        return this.track;
    }

    public int getOrder() {
        return this.order;
    }

}
