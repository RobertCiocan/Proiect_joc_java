package core;

import io.Persistable;

public class Size implements Persistable {

    private int width;
    private int height;

    public Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public static Size copyOf(Size size) {
        return new Size(size.getWidth(), size.getHeight());
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String serialize() {
        return String.format("%d|%d", width, height);
    }

    @Override
    public void applySerializedData(String serializedData) {
        String[] tokens = serializedData.split("\\|");
        width = Integer.parseInt(tokens[0]);
        height = Integer.parseInt(tokens[1]);
    }
}
