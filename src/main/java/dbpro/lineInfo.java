package dbpro;

public class lineInfo {

    public int line;
    public int track;
    public int order;

    public lineInfo(int line, int track, int order) {

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

    public int getTrack() {
        return this.track;
    }

    public int getOrder() {
        return this.order;
    }

}
