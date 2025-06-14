package com.yupi.yupicturebackend.utils;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
@Slf4j
public class OptimizedMainColorCalculator {

    /**
     * 计算图片的主色调
     *
     * @param imageFile 图片文件
     * @return 主色调的十六进制颜色值（如 #RRGGBB）
     * @throws Exception 如果图片无法处理
     */
    public static String calculateMainColor(File imageFile) throws Exception {
        log.info("读取的图片路径: {}", imageFile.getAbsolutePath());
        if (!imageFile.exists()) {
            log.error("图片文件不存在，路径: {}", imageFile.getAbsolutePath());
            throw new IllegalArgumentException("图片文件不存在");
        }

        // 1. 读取图片
        ImageIO.scanForPlugins(); // 加载更多格式支持
        BufferedImage image = ImageIO.read(imageFile);
        if (image == null) {
            log.error("无法读取图片，路径: {}", imageFile.getAbsolutePath());
            throw new IllegalArgumentException("无法读取图片，请检查文件格式！");
        }
        log.info("图片已成功读取，尺寸: {}x{}", image.getWidth(), image.getHeight());

        // 2. 缩放图片
        int width = Math.min(image.getWidth(), 100);
        int height = Math.min(image.getHeight(), 100);
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = scaledImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(image, 0, 0, width, height, null);
        g.dispose();

        // 3. 构建颜色直方图
        Map<Color, Double> colorHistogram = new HashMap<>();
        for (int x = 0; x < scaledImage.getWidth(); x++) {
            for (int y = 0; y < scaledImage.getHeight(); y++) {
                int pixel = scaledImage.getRGB(x, y);
                Color color = new Color(pixel, true);
                float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

                // 计算权重（饱和度 * 亮度 * 透明度）
                double weight = hsb[1] * hsb[2] * (color.getAlpha() / 255.0);
                colorHistogram.put(color, colorHistogram.getOrDefault(color, 0.0) + weight);
            }
        }

        // 4. 提取主色调（可扩展为 K-Means 聚类）
        Color mainColor = colorHistogram.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new IllegalStateException("无法计算主色调"));

        log.info("计算出的主色调为: #{}", String.format("%02X%02X%02X", mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue()));
        return String.format("#%02X%02X%02X", mainColor.getRed(), mainColor.getGreen(), mainColor.getBlue());
    }

    public static void main(String[] args) {
        try {
            // 测试图片路径
            File imageFile = new File("path/to/your/image.jpg");

            // 计算主色调
            String mainColor = calculateMainColor(imageFile);

            // 打印结果
            System.out.println("图片主色调为: " + mainColor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}