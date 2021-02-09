package Startlearn;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

public class Basic {
	static String name;
	static WebDriver driver;
	static int w1;
	static int w2;
	static int h1;
	static int h2;
	static BufferedImage img1;
	static BufferedImage img2;
	
	
	@BeforeTest
	public void Launchwebsite() throws InterruptedException{
		
		System.setProperty("webdriver.chrome.driver", "/home/abirami/Videos/chromedriver");
		 driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://freechurchwebsites.com/");
		Thread.sleep(2000);
	}

	@Test
	public void Screenshotverification() throws InterruptedException, IOException {
		for (int i = 1; i < 3; i++) {
			driver.findElement(By.cssSelector(".txt")).click();
			Thread.sleep(3000);
            Screenshot(driver,"Homepage"+i);
			driver.findElement(By.xpath("(//*[@class='txt'])[2]")).click();
            Thread.sleep(3000);
			Screenshot(driver,"About"+i);
			driver.findElement(By.linkText("Website Features")).click();
			Thread.sleep(2000);
			Screenshot(driver,"Website_features"+i);
			driver.findElement(By.xpath("//*[text()='Blog']")).click();
			Thread.sleep(1000);
			Screenshot(driver,"Blog"+i);
			

			if (i == 2) {
				for (int z = 0; z < 4; z++) {
					if (z == 0) {
						img1 = ImageIO.read(new File("/home/abirami/Pictures/Homepage1.png"));
						img2 = ImageIO.read(new File("/home/abirami/Pictures/Homepage2.png"));
						name = "Homepage";

					}
					if (z == 1) {
						img1 = ImageIO.read(new File("/home/abirami/Pictures/About1.png"));
						img2 = ImageIO.read(new File("/home/abirami/Pictures/About2.png"));
						name = "About";
					}
					if (z == 2) {
						img1 = ImageIO.read(new File("/home/abirami/Pictures/Website_features1.png"));
						img2 = ImageIO.read(new File("/home/abirami/Pictures/Website_features2.png"));
						name = "Website_features";

					}
					if (z == 3) {

						img1 = ImageIO.read(new File("/home/abirami/Pictures/Blog1.png"));
						img2 = ImageIO.read(new File("/home/abirami/Pictures/Blog2.png"));
						name = "Blog";
					}

					w1 = img1.getWidth();
					w2 = img2.getWidth();
					h1 = img1.getHeight();
					h2 = img2.getHeight();

					if ((w1 != w2) || (h1 != h2)) {
						System.out.println("Both images should have same dimensions");
					} else {
						long diff = 0;
						for (int j = 0; j < h1; j++) {
							for (int k = 0; k < w1; k++) {
								// Getting the RGB values of a pixel
								int pixel1 = img1.getRGB(k, j);
								Color color1 = new Color(pixel1, true);
								int r1 = color1.getRed();
								int g1 = color1.getGreen();
								int b1 = color1.getBlue();
								int pixel2 = img2.getRGB(k, j);
								Color color2 = new Color(pixel2, true);
								int r2 = color2.getRed();
								int g2 = color2.getGreen();
								int b2 = color2.getBlue();
								// sum of differences of RGB values of the two images
								long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
								diff = diff + data;
							}
						}
						double avg = diff / (w1 * h1 * 3);
						double percentage = (avg / 255) * 100;
						System.out.println("Difference: " + percentage);

						if (percentage == 0) {
							System.out.println("Image comparison is perfect for " + name);

						} else {
							 //throw new IOException("Image comparison is not perfect for" +name);
						}

					}
				}

				break;
			}
		}
			
		}


private void Screenshot(WebDriver webdriver,String filepath) throws IOException {
	Screenshot s = new AShot().takeScreenshot(driver);

	ImageIO.write(s.getImage(), "PNG", new File("/home/abirami/Pictures/" + filepath + ".png"));

	
		
	}
	@AfterTest
	public void Closewebsite() {
		
		driver.close();
	}

	}


