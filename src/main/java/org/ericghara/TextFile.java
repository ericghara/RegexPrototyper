package org.ericghara;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TextFile implements Iterable<String> {

    private final String path;
    private final List<String> lines;

    public TextFile(String filePath) {
        Objects.requireNonNull("Received a null file path");
        this.path = filePath;
        this.lines = readIn(filePath);
    }

    public String getPath() {
        return path;
    }

    public List<String> getLines() {
        return lines;
    }

    public int getNumLines() {
        return lines.size();
    }

    static List<String> readIn(String filePath) {
        File file = new File(filePath);
        List<String> lines = new ArrayList<>();
        try (var scanner = new Scanner(file) ) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while reading in the file");
        }
        return lines;
    }

    public Stream<String> stream() {
        return lines.stream();
    }

    @Override
    public Iterator<String> iterator() {
        return lines.iterator();
    }

    @Override
    public void forEach(Consumer<? super String> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<String> spliterator() {
        return Iterable.super.spliterator();
    }
}
