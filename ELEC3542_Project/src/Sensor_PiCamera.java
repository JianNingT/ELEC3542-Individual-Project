
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class Sensor_PiCamera{
	
	private String						prevCommand;
	private String						saveDir;
	private HashMap<String, String[]>	options	= new HashMap<>();
	private ProcessBuilder				pb;
	private Process						p;
	private static final int DEFAULT_WIDTH  = 500;
	private static final int DEFAULT_HEIGHT = 500;
								
	private enum AWB {
		OFF,AUTO,SUN,CLOUD,SHADE,TUNGSTEN,FLUORESCENT,INCANDESCENT,FLASH,HORIZON;
		public String toString() {
			String id = name();
			return id.toLowerCase();
		}
	}
	private enum DRC {
		OFF,LOW,MEDIUM,HIGH;
		public String toString() {
			String id = name();
			return id.toLowerCase();
		}
	}
	private enum Encoding {
		JPG,BMP,GIF,PNG;
		public String toString() {
			String id = name();
			return id.toLowerCase();
		}
	}
	private enum Exposure {
		AUTO,NIGHT,NIGHTPREVIEW,BACKLIGHT,SPOTLIGHT,SPORTS,SNOW,BEACH,VERYLONG,FIXEDFPS,ANTISHAKE,FIREWORKS;
		public String toString() {
			String id = name();
			return id.toLowerCase();
		}
	}
	private enum ImageEffect {
		NONE,NEGATIVE,SOLARISE,POSTERISE,WHITEBOARD,BLACKBOARD,SKETCH,DENOISE,EMBOSS,OILPAINT,HATCH,GPEN,
		PASTEL,WATERCOLOUR,FILM,BLUR,SATURATION,COLOURSWAP,WASHEDOUT,COLOURPOINT,COLOURBALANCE,CARTOON;
		public String toString() {
			String id = name();
			return id.toLowerCase();
		}
	}
	private enum MeteringMode {
		AVERAGE,SPOT,BACKLIT,MATRIX;
		public String toString() {
			String id = name();
			return id.toLowerCase();
		}
	}

	
	public Sensor_PiCamera() throws Exception {
		this("/home/pi/Pictures");
	}
	
	public Sensor_PiCamera(String saveDir) throws Exception {
		
		this.setAWB(AWB.AUTO); 	    // Change Automatic White Balance setting to automatic
		this.setDRC(DRC.OFF); 			// Turn off Dynamic Range Compression
		this.setContrast(100); 			// Set maximum contrast = 100
		this.setSharpness(50);		    // Set maximum sharpness = 100
		this.setQuality(50); 		    // Set maximum quality = 100 
		this.setTimeout(1000);		    // Wait 1 second to take the image ( 1000 = 1 second )
		this.turnOnPreview();            // Turn on image preview
		this.setEncoding(Encoding.PNG); // Change encoding of images to PNG
		
		this.saveDir = saveDir;
		try {
			pb = new ProcessBuilder("raspistill");
			pb.start();
		} catch (IOException e) {
			throw new Exception(
					"RPiCamera failed to run raspistill. The JRPiCam library relies on"
							+ "raspistill to function. Please ensure it is installed and configured"
							+ "on your system.");
		}
        this.setWidth(DEFAULT_WIDTH);
		this.setHeight(DEFAULT_HEIGHT);
		
	}
	
	public void shootStill() {
		this.shootStill("Untitled.jpg");
	}
	
	public void shootStill(int waitTime) {
		this.setTimeout(waitTime);
		this.shootStill("Untitled.jpg");
	}
	
	public void shootStill(String pictureName, int waitTime) {
		this.setTimeout(waitTime);
		this.shootStill(pictureName);
	}
	
	public void shootStill(String pictureName) {
		try {
			this.takeStill(pictureName, 200, 200);
		} catch (Exception e) {
			System.out.println("PiCamera Problem");
		}
	}
	
	public File takeStill(String pictureName, int width, int height) throws IOException, InterruptedException {
		List<String> command = new ArrayList<>();
		command.add("raspistill");
		command.add("-o");
		command.add(saveDir + File.separator + pictureName);
		command.add("-w");
		command.add("" + width);
		command.add("-h");
		command.add("" + height);
		for (Map.Entry<String, String[]> entry : options.entrySet()) {
			if (entry.getValue() != null        &&
                !"width".equals(entry.getKey()) &&
                !"height".equals(entry.getKey())) {
                command.addAll(Arrays.asList(entry.getValue()));
			}
		}
		prevCommand = command.toString();
		pb = new ProcessBuilder(command);
		
		p = pb.start();
		p.waitFor();
		return new File(saveDir + File.separator + pictureName);
	}
	
	public File takeStill(String pictureName) throws IOException, InterruptedException {
		return takeStill(pictureName,
				Integer.parseInt(options.get("width")[1]),
				Integer.parseInt(options.get("height")[1]));
	}
	
	public BufferedImage takeBufferedStill(int width, int height) throws IOException, InterruptedException {
		List<String> command = new ArrayList<>();
		command.add("raspistill");
		command.add("-o");
		command.add("-v");
		command.add("-w");
		command.add("" + width);
		command.add("-h");
		command.add("" + height);
		for (Map.Entry<String, String[]> entry : options.entrySet()) {
			if (entry.getValue() != null        &&
                !entry.getKey().equals("width") &&
                !entry.getKey().equals("height")) {
                Collections.addAll(command, entry.getValue());
			}
		}
		prevCommand = command.toString();
		pb = new ProcessBuilder(command);
		
		p = pb.start();
		BufferedImage bi = ImageIO.read(p.getInputStream());

		p.getInputStream().close();
		return bi;
	}
	
	public BufferedImage takeBufferedStill() throws IOException, InterruptedException {
		return takeBufferedStill(
				Integer.parseInt(options.get("width")[1]),
				Integer.parseInt(options.get("height")[1]));
	}
	
	public int[] takeStillAsRGB(int width, int height, boolean keepPadding) throws IOException {
		List<String> command = new ArrayList<>();
		command.add("raspiyuv");
		command.add("-rgb");
		command.add("-o");
		command.add("-v");
		command.add("-w");
		command.add("" + width);
		command.add("-h");
		command.add("" + height);
		for (Map.Entry<String, String[]> entry : options.entrySet()) {
			if (entry.getValue() != null        &&
                !entry.getKey().equals("width") &&
                !entry.getKey().equals("height")) {
                Collections.addAll(command, entry.getValue());
			}
		}
		prevCommand = command.toString();
		pb = new ProcessBuilder(command);

		p = pb.start();
		BufferedInputStream inputStream = new BufferedInputStream(p.getInputStream());

		int paddedWidth = width;
		int paddedHeight = height;
		
		int widthRemainder = width % 16;
		if (widthRemainder != 0)
			paddedWidth = width + 16 - widthRemainder;
			
		int heightRemainder = height % 16;
		if (heightRemainder != 0)
			paddedHeight = height + 16 - heightRemainder;

		int[] rgbVals;
		if (!keepPadding)
			rgbVals = new int[width * height * 3];
		else
			rgbVals = new int[paddedWidth * paddedHeight * 3];
			
		int rgbData;
		int pos = 0;
		
		if (!keepPadding) {
			int storedBytes = 0;
			int columnPos = 1;
			int areaWithoutPadding = width * height;
			while ((rgbData = inputStream.read()) != -1) {
				if ((columnPos / 3d) <= width) {
					rgbVals[storedBytes] = rgbData;
					storedBytes++;
				}
				columnPos++;
				if (columnPos == (paddedWidth * 3) + 1)
					columnPos = 1;
				if ((storedBytes / 3d) == areaWithoutPadding)
					break;
			}
		}
		
		else {
			while ((rgbData = inputStream.read()) != -1) {
				rgbVals[pos] = rgbData;
				pos++;
			}
		}
		inputStream.close();
		return rgbVals;
	}

	public int[] takeStillAsRGB(boolean keepPadding) throws IOException {
		return takeStillAsRGB(
				Integer.parseInt(options.get("width")[1]),
				Integer.parseInt(options.get("height")[1]),
				keepPadding);
	}

	public File timelapse(boolean wait, String pictureName, int time) throws IOException, InterruptedException {
		if (!pictureName.contains("%04d"))
			pictureName = "%04d" + pictureName;
			
		List<String> command = new ArrayList<>();
		command.add("raspistill");
		command.add("-tl");
		command.add("" + time);
		command.add("-o");
		command.add(saveDir + File.separator + pictureName);
		for (Map.Entry<String, String[]> entry : options.entrySet()) {
			if (entry.getValue() != null)
                Collections.addAll(command, entry.getValue());
		}
		prevCommand = command.toString();
		pb = new ProcessBuilder(command);

		p = pb.start();
		if (wait)
			p.waitFor();
			
		try {
			return new File(options.get("latest")[1]);
		} catch (NullPointerException e) {
			return null;
		}

	}

//	public void stop() {
//		if (p != null)
//			p.destroy();
//	}

	public String getPrevCommand() {
		return prevCommand.substring(1, prevCommand.lastIndexOf("]")).replaceAll(",", "");
	}

	public Sensor_PiCamera setSaveDir(String saveDir) {
		this.saveDir = saveDir;
		return this;
	}

	public Sensor_PiCamera setToDefaults() {
		saveDir = "/home/pi/Pictures";
		for (Map.Entry<String, String[]> entry : options.entrySet()) {
			entry.setValue(null);
		}
		return this;
	}

	public Sensor_PiCamera turnOffPreview() {
		options.put("preview", new String[] { "-n" });
		return this;
	}

	public Sensor_PiCamera turnOnPreview() {
		options.put("preview", null);
		return this;
	}

	public Sensor_PiCamera setFullPreviewOn() {
		options.put("fullpreview", new String[] { "-fp" });
		return this;
	}

	public Sensor_PiCamera setFullPreviewOff() {
		options.put("fullpreview", null);
		return this;
	}

	public Sensor_PiCamera turnOnPreview(int x, int y, int w, int h) {
		options.put("preview", new String[] { "-p", "" + x + "," + y + "," + w + "," + h });
		return this;
	}

	public Sensor_PiCamera setPreviewFullscreen(boolean fullscreen) {
		if (fullscreen)
			options.put("fullscreen", new String[] { "-f" });
		else
			options.put("fullscreen", null);
		return this;
	}

	public Sensor_PiCamera setPreviewOpacity(int opacity) {
		if (opacity > 255)
			opacity = 255;
		else if (opacity < 0)
			opacity = 0;
		options.put("opacity", new String[] { "-op", "" + opacity });
		return this;
	}

	public Sensor_PiCamera setSharpness(int sharpness) {
		if (sharpness > 100)
			sharpness = 100;
		else if (sharpness < -100)
			sharpness = -100;
		options.put("sharpness", new String[] { "-sh", "" + sharpness });
		return this;
	}

	public Sensor_PiCamera setContrast(int contrast) {
		if (contrast > 100)
			contrast = 100;
		else if (contrast < -100)
			contrast = -100;
		options.put("constast", new String[] { "-co", "" + contrast });
		return this;
	}

	public Sensor_PiCamera setBrightness(int brightness) {
		if (brightness > 100)
			brightness = 100;
		else if (brightness < 0)
			brightness = 0;
		options.put("brightness", new String[] { "-br", "" + brightness });
		return this;
	}

	public Sensor_PiCamera setSaturation(int saturation) {
		if (saturation > 100)
			saturation = 100;
		else if (saturation < -100)
			saturation = -100;
		options.put("saturation", new String[] { "-sa", "" + saturation });
		return this;
	}

	public Sensor_PiCamera setISO(int iso) {
		options.put("ISO", new String[] { "-ISO", "" + iso });
		return this;
	}

	public Sensor_PiCamera setExposure(Exposure exposure) {
		options.put("exposure", new String[] { "-ex", exposure.toString() });
		return this;
	}

	public Sensor_PiCamera setAWB(AWB awb) {
		options.put("awb", new String[] { "-awb", awb.toString() });
		return this;
	}

	public Sensor_PiCamera setImageEffect(ImageEffect imageEffect) {
		options.put("imxfx", new String[] { "-ifx", imageEffect.toString() });
		return this;
	}

	public Sensor_PiCamera setColourEffect(int U, int V) {
		if (U > 255)
			U = 255;
		else if (U < 0)
			U = 0;
		if (V > 255)
			V = 255;
		else if (V < 0)
			V = 0;
		options.put("colfx", new String[] { "-cfx", "" + U, ":", "" + V });
		return this;
	}
	

	public Sensor_PiCamera setMeteringMode(MeteringMode meteringMode) {
		options.put("metering", new String[] { "-mm", meteringMode.toString() });
		return this;
	}
	

	public Sensor_PiCamera setRotation(int rotation) {
		if (rotation > 359)
			while (rotation > 359)
				rotation = rotation - 360;
		else if (rotation < 0)
			while (rotation < 0)
				rotation = rotation + 360;
		options.put("rotation", new String[] { "-rot", "" + rotation });
		return this;
	}
	
	public Sensor_PiCamera setHorizontalFlipOn() {
		options.put("hflip", new String[] { "-hf" });
		return this;
	}
	
	public Sensor_PiCamera setHorizontalFlipOff() {
		options.put("hflip", null);
		return this;
	}
	

	public Sensor_PiCamera setVerticalFlipOn() {
		options.put("vflip", new String[] { "-vf" });
		return this;
	}
	

	public Sensor_PiCamera setVerticalFlipOff() {
		options.put("vflip", null);
		return this;
	}
	

	public Sensor_PiCamera setRegionOfInterest(double x, double y, double w, double d) {
		if (x > 1.0)
			x = 1.0;
		else if (x < 0.0)
			x = 0.0;
		if (y > 1.0)
			y = 1.0;
		else if (y < 0.0)
			y = 0.0;
		if (w > 1.0)
			w = 1.0;
		else if (w < 0.0)
			w = 0.0;
		if (d > 1.0)
			d = 1.0;
		else if (d < 0.0)
			d = 0.0;
		options.put("roi", new String[] { "-roi", "" + x, ",", "" + y, ",", "" + w, ",", "" + d });
		return this;
	}
	

	public Sensor_PiCamera setShutter(int speed) {
		if (speed > 6000000)
			speed = 6000000;
		if (speed < 0)
			speed = 0;
		options.put("shutter", new String[] { "-ss", "" + speed });
		return this;
	}
	

	public Sensor_PiCamera setDRC(DRC drc) {
		options.put("drc", new String[] { "-drc", drc.toString() });
		return this;
	}

	public Sensor_PiCamera setWidth(int width) {
		options.put("width", new String[] { "-w", "" + width });
		return this;
	}

	public Sensor_PiCamera setHeight(int height) {
		options.put("height", new String[] { "-h", "" + height });
		return this;
	}

	public Sensor_PiCamera setQuality(int quality) {
		if (quality > 100)
			quality = 100;
		else if (quality < 0)
			quality = 0;
		options.put("quality", new String[] { "-q", "" + quality });
		return this;
	}
	
	public Sensor_PiCamera setAddRawBayer(boolean add) {
		if (add)
			options.put("raw", new String[] { "-r" });
		else
            options.put("raw", null);
		return this;
	}
	
	public Sensor_PiCamera setLinkLatestImage(boolean link, String fileName) {
		if (link)
			options.put("latest", new String[] { "-l", "" + fileName });
		else
			options.put("latest", null);
		return this;
	}

	public Sensor_PiCamera setTimeout(int time) {
		options.put("timeout", new String[] { "-t", "" + time });
		return this;
	}

	public Sensor_PiCamera setThumbnailParams(int x, int y, int quality) {
		options.put("thumb", new String[] { "-th", "" + x, ":", "" + y, ":", "" + quality });
		return this;
	}
	
	public Sensor_PiCamera turnOffThumbnail() {
		options.put("thumb", new String[] { "-th", "none" });
		return this;
	}

	public Sensor_PiCamera setEncoding(Encoding encoding) {
		options.put("encoding", new String[] { "-e", encoding.toString() });
		return this;
	}

	public Sensor_PiCamera selectCamera(int camNumber) {
		if (camNumber < 0)
			camNumber = 0;
		else if (camNumber > 1)
			camNumber = 1;
		options.put("camselect", new String[] { "-cs", "" + camNumber });
		return this;
	}

	public Sensor_PiCamera enableBurst() {
		options.put("burst", new String[] { "-bm" });
		return this;
	}

	public Sensor_PiCamera disableBurst() {
		options.put("burst", null);
		return this;
	}

	public Sensor_PiCamera setDateTimeOn() {
		options.put("datetime", new String[] { "-dt" });
		return this;
	}

	public Sensor_PiCamera setDateTimeOff() {
		options.put("datetime", null);
		return this;
	}

	public Sensor_PiCamera setTimestampOn() {
		options.put("timestamp", new String[] { "-ts" });
		return this;
	}

	public Sensor_PiCamera setTimestampOff() {
		options.put("timestamp", null);
		return this;
	}
	
}