package com.packtpub.java.coding.problems.java.io;

import lombok.SneakyThrows;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import static java.lang.System.out;

public class App {


    @Test
    public void relativePathToFileStoreRoot() {

        var pathOfFile = "~/Downloads/basic1.pdf";

        Path path = Paths.get(pathOfFile);
        out.println(path);

        Path path1 = Path.of(pathOfFile);
        out.println(path1);

        out.println(FileSystems.getDefault().getRootDirectories());
        out.println(FileSystems.getDefault().getSeparator());

    }

    @Test
    public void fileVisitor() throws IOException {

        class PathVisitor extends SimpleFileVisitor<Path> {

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {

                if (null != exc)
                    throw exc;

                out.println("Visited directory..:" + dir);

                return FileVisitResult.CONTINUE;
            }
        }

        var pathToVisit = "/Users/douglasdiasbatista/Downloads/";
        var pathVisitor = new PathVisitor();

        Files.walkFileTree(Path.of(pathToVisit), pathVisitor);

    }

    @Test
    public void walks() throws IOException {

        var dir = Paths.get("/Users/douglasdiasbatista/Downloads");
        // stream
        Files.walk(dir, FileVisitOption.FOLLOW_LINKS).forEach(out::println);
        out.println(Files.walk(dir, FileVisitOption.FOLLOW_LINKS).filter(f -> f.toFile().isFile())
                .mapToLong(f -> f.toFile().length())
                .sum());
    }

    @Test
    public void streamFileContent() {

        var path = "/Users/douglasdiasbatista/Downloads/file1.dat";

        try (var content = Files.lines(Paths.get(path), StandardCharsets.UTF_8)) {
            content.forEach(out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void efficientlyReadWriterJdk11() {
        // try set character charset
    }

    @Test
    public void writeTextFiles() {
        var path = Paths.get("file1.dat");
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            bw.write("Peace of fear...");
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Files.writeString()
    }


}
