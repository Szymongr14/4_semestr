package org.example;

import org.apache.commons.lang3.tuple.Pair;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        String sourceFolder = args[0];
        String destinationFolder = args[1];

        List<Path> files;
        Path source = Path.of(sourceFolder);

        try {
            Stream<Path> stream = Files.list(source);
            //create list of paths to images from source dir
            files = stream.toList();
            //creating stream of Pairs (fileName, image)
            for (int i=1;i<=1;i++) {
                long start = System.currentTimeMillis();
                ForkJoinPool customThreadPool = new ForkJoinPool();
                customThreadPool.submit(() -> files.parallelStream()
                        .map(file -> {
                            try {
                                BufferedImage image = ImageIO.read(file.toRealPath().toFile());
                                return Pair.of(file.getFileName(), image);
                            } catch (IOException e) {
                                System.out.println("Failed to read image from file: " + file.getFileName());
                                throw new RuntimeException(e);
                            }

                        }).map(pair -> {
                            BufferedImage newImage = changeColor(pair.getRight());
                            String newFileName = "modified_" + pair.getLeft();
                            return Pair.of(newFileName, newImage);
                        }).forEach(pair -> {
                            File outputImage = new File(destinationFolder + "\\" + pair.getLeft());
                            try {
                                ImageIO.write(pair.getRight(), "jpg", outputImage);
                            } catch (IOException e) {
                                System.out.println("Failed to write image to file: " + pair.getLeft());
                                throw new RuntimeException(e);
                            }
                        })).get();
                customThreadPool.shutdownNow();
                long end = System.currentTimeMillis();
                System.out.println("Execution time: " + (end - start) / 1000 + "s for" + i + " threads");
            }
        }catch (IOException e){
            e.printStackTrace();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static BufferedImage changeColor(BufferedImage original){
        BufferedImage newImage = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());
        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {
                int rgb = original.getRGB(i, j);
                Color color = new Color(rgb);
                int red = color.getRed();
                int blue = color.getBlue();
                int green = color.getGreen();
                Color outColor = new Color(red, blue, green);
                int outRgb = outColor.getRGB();
                newImage.setRGB(i, j, outRgb);
            }
        }
        return newImage;
    }
}