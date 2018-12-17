package cn.evake.auth.util.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import cn.evake.auth.util.DateUtil;

/**
 *  图片工具类
 *  图片水印
 *  文字水印
 *  缩放 
 * @author wang yi
 * @desc 
 * @version $Id: ImageUtils.java, v 0.1 2018年8月5日 下午1:36:56 wang yi Exp $
 */
public final class ImageUtils {
    /** 图片格式：PNG */
    private static final String PICTRUE_FORMATE_PNG = "png";

    /** 
     * 添加图片水印 
     *  
     * @param targetImg 
     *            目标图片 
     * @param waterImg 
     *            水印图片 
     * @param x 
     *            水印图片距离目标图片左侧的偏移量  负数则居中
     * @param y 
     *            水印图片距离目标图片上侧的偏移量  负数则居中
     * @param alpha 
     *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明) 
     * @return 
     */
    public final static byte[] addImageWeatermark(byte[] originImg, byte[] waterImg, int x, int y,
                                                  float alpha) {
        try {
            // 获取底图
            BufferedImage buffImg = readImg(originImg);
            //获取底图宽高
            int bkImgWidth = buffImg.getWidth();// 获取层图的宽度
            int bkImgHeight = buffImg.getHeight();// 获取层图的高度
            // 获取层图
            BufferedImage waterIm = readImg(waterImg);
            // 创建Graphics2D对象，用在底图对象上绘图
            Graphics2D g2d = buffImg.createGraphics();
            int waterImgWidth = waterIm.getWidth();// 获取层图的宽度
            int waterImgHeight = waterIm.getHeight();// 获取层图的高度
            // 在图形和图像中实现混合和透明效果
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            //计算居中位置
            if (x < 0) {
                x = (bkImgWidth / 2 - waterImgWidth / 2);
            }
            if (y < 0) {
                y = bkImgHeight / 2 - waterImgHeight / 2;
            }
            // 绘制
            g2d.drawImage(waterIm, x, y, waterImgWidth, waterImgHeight, null);
            // 抗锯齿
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.dispose();// 释放图形上下文使用的系统资源

            //输出字节流
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(buffImg, PICTRUE_FORMATE_PNG, output);
            return output.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 
     * 添加文字水印 
     *  
     * @param targetImg 
     *            目标图片 
     * @param pressText 
     *            水印文字， ex:王小二
     * @param fontName 
     *            字体名称， ex:宋体 
     * @param fontStyle 
     *            字体样式，ex:粗体和斜体(Font.BOLD|Font.ITALIC) 
     * @param fontSize 
     *            字体大小，单位为像素 
     * @param color 
     *            字体颜色  ex:Color.black
     * @param x 
     *            水印文字距离目标图片左侧的偏移量
     * @param y 
     *            水印文字距离目标图片上侧的偏移量 
     * @param alpha 
     *            透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明) 
     * @return 
     */
    public static byte[] addTextWeatermark(byte[] file, String pressText, Font font, Color color,
                                           int x, int y, float alpha) {
        try {
            //原图
            BufferedImage bufferedImage = readImg(file);
            //宽高
            int width = bufferedImage.getWidth();// 获取层图的宽度
            int height = bufferedImage.getHeight();// 获取层图的高度
            //创建画板
            Graphics2D g = bufferedImage.createGraphics();
            // 抗锯齿
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setFont(font);
            g.setColor(color);
            //计算居中位置
            // 计算文字长度，计算居中的x点坐标
            FontMetrics fm = g.getFontMetrics(font);
            int textWidth = fm.stringWidth(pressText);
            if (x < 0) {
                x = (width - textWidth) / 2;
            }
            if (y < 0) {
                y = (height / 2 - pressText.length() / 2);
            }
            g.drawString(pressText, x, y);
            g.dispose();

            //写文件
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, PICTRUE_FORMATE_PNG, output);
            return output.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static BufferedImage readImg(byte[] file) throws IOException {
        ByteArrayInputStream fileInputStream = new ByteArrayInputStream(file);
        BufferedImage bufferedImage = ImageIO.read(fileInputStream);
        return bufferedImage;
    }

    /** 
     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符 
     *  
     * @param text 
     * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4. 
     */
    public static int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }

    /** 
     * 图片缩放 
     *  
     * @param filePath 
     *            图片 
     * @param height 
     *            高度 
     * @param width 
     *            宽度 
     * @param bb 
     *            比例不对时是否需要补白 
     * @return 
     */
    public static byte[] resize(byte[] file, int height, int width, boolean bb) {
        try {
            double ratio = 0; // 缩放比例  
            BufferedImage bi = readImg(file);
            Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
            // 计算比例  
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(
                    AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {
                //重新构造图片
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null)) {
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                        itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
                } else {
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null),
                        itemp.getHeight(null), Color.white, null);
                }
                // 抗锯齿
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
                g.dispose();
                itemp = image;
            }
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write((BufferedImage) itemp, PICTRUE_FORMATE_PNG, output);
            return output.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 
     * 图片合并(将两张图片合并为一张图片) 
     * @param fileType 图片类型  
     * @param oneImg  源文件
     * @param twoImg  需要合并文件
     *  合并后的图片 
     * @return 
     * @throws Exception 
     */
    public static byte[] mergeImage(String fileType, byte[] oneImg,
                                    byte[] twoImg) throws Exception {
        if (StringUtils.isBlank(fileType)) {
            throw new Exception("文件名不能为空");
        }
        if (!fileType.equalsIgnoreCase("png") && fileType.equalsIgnoreCase("jpg")) {
            throw new Exception("暂不支持png与jpg以外的图片类型");
        }
        try {
            BufferedImage src = readImg(oneImg);// 读取第一张图片  
            int width = src.getWidth(null);
            int height = src.getHeight(null);
            if (width > 900 || height > 900) {
                int num = (int) Math.ceil((double) width / 900);
                int num2 = (int) Math.ceil((double) height / 900);
                num = num > num2 ? num : num2;
                width = width / num;
                height = height / num;
            }
            BufferedImage bufferedImageOne = null;
            if ("png".equalsIgnoreCase(fileType.toLowerCase())) {
                bufferedImageOne = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            } else {
                bufferedImageOne = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            }
            bufferedImageOne.getGraphics()
                .drawImage(src.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            int[] imageArrayOne = new int[width * height];// 从图片中读取RGB  
            imageArrayOne = bufferedImageOne.getRGB(0, 0, width, height, imageArrayOne, 0, width);

            ByteArrayInputStream filetwo = new ByteArrayInputStream(twoImg);// 读取第二张图片  
            src = ImageIO.read(filetwo);
            BufferedImage bufferedImageTwo = null;
            if ("png".equalsIgnoreCase(fileType.toLowerCase())) {
                bufferedImageTwo = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
            } else {
                bufferedImageTwo = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            }
            bufferedImageTwo.getGraphics()
                .drawImage(src.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            int[] imageArrayTwo = new int[width * height];// 从图片中读取RGB  
            imageArrayTwo = bufferedImageTwo.getRGB(0, 0, width, height, imageArrayTwo, 0, width);

            // 生成新图片  
            BufferedImage imageNew = new BufferedImage(width * 2, height,
                BufferedImage.TYPE_INT_RGB);
            imageNew.setRGB(0, 0, width, height, imageArrayOne, 0, width); // 设置左半部分的RGB  
            imageNew.setRGB(width, 0, width, height, imageArrayTwo, 0, width); // 设置右半部分的RGB  

            // 写图片  
            ByteArrayOutputStream outFile = new ByteArrayOutputStream();
            ImageIO.write(imageNew, fileType, outFile);
            return outFile.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        byte[] file = IOUtils.toByteArray(FileUtils
            .openInputStream(new File("C:\\Users\\ANDY\\Desktop\\后台生成-证书\\img_zhengshu.png")));
        byte[] waterImg = IOUtils.toByteArray(FileUtils
            .openInputStream(new File("C:\\Users\\ANDY\\Desktop\\后台生成-证书\\img_zhang.png")));
        //字体名称 思源黑
        String fontNameRegular = "Source Han Sans SC Regular";
        String fontNameMedium = "Source Han Sans SC Medium";
        String fontNameBold = "Source Han Sans SC Bold";
        //最终文件
        byte[] newImg = null;
        //合并文字 设置日期
        newImg = ImageUtils.addTextWeatermark(file, DateUtil.formateChinese(new Date()),
            new Font(fontNameBold, Font.BOLD, 14), Color.black, -1, 822, 0);
        //合并文字 设置名称
        newImg = ImageUtils.addTextWeatermark(newImg, "杨智能",
            new Font(fontNameMedium, Font.BOLD, 22), Color.black, -1, 465, 0);
        //合并文字 设置比赛名称
        newImg = ImageUtils.addTextWeatermark(newImg, "智能跳绳挑战赛",
            new Font(fontNameRegular, Font.PLAIN, 20), Color.black, -1, 555, 0);
        //合并文字 设置比赛项
        newImg = ImageUtils.addTextWeatermark(newImg, "男子组-8分钟跳绳",
            new Font(fontNameRegular, Font.PLAIN, 18), Color.black, -1, 600, 0);
        //合并文字 设置证书编号
        newImg = ImageUtils.addTextWeatermark(newImg, "证书编号: 201808051602",
            new Font(fontNameRegular, Font.PLAIN, 18), new Color(214, 161, 41), 150, 700, 0);
        //合并图片 奖章
        newImg = ImageUtils.addImageWeatermark(newImg, waterImg, -1, 720, 1f);
        //写入磁盘
        IOUtils.write(newImg, new FileOutputStream(
            new File("C:\\Users\\ANDY\\Desktop\\后台生成-证书\\newFile\\img_zhengshu_ck.png")));
        //生成缩略图650*922
        newImg = ImageUtils.resize(newImg, 650, 922, false);
        IOUtils.write(newImg, new FileOutputStream(
            new File("C:\\Users\\ANDY\\Desktop\\后台生成-证书\\newFile\\img_zhengshu_ck_650_922.png")));
        //生成缩略图300*426
        newImg = ImageUtils.resize(newImg, 300, 426, false);
        IOUtils.write(newImg, new FileOutputStream(
            new File("C:\\Users\\ANDY\\Desktop\\后台生成-证书\\newFile\\img_zhengshu_ck_300_426.png")));

        //获取系统中可用的字体的名字  
        GraphicsEnvironment e = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fn = e.getAvailableFontFamilyNames();
        for (int i = 0; i < fn.length; i++) {
            System.out.println(fn[i]);
            if (fn[i].equals("Source Han Sans SC Bold")) {
                System.err.println("思源黑体");
            }
        }

    }
}