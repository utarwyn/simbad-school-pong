package pongpong.hud;

import pongpong.PongPong;
import simbad.gui.Simbad;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HUD {

	private Simbad simbad;

	private BufferedImage header;

	private Font font;

	private int score1, score2;

	public HUD() {
		this.load();
	}

	public void setSimbad(Simbad simbad) {
		this.simbad = simbad;
	}

	public void updateScores() {
		PongPong instance = PongPong.getInstance();

		this.score1 = instance.getEntityBySide(-1).getScore();
		this.score2 = instance.getEntityBySide( 1).getScore();
	}

	public void draw(Graphics2D g) {
		if (this.simbad == null) return;

		if (this.header != null) {
			int origX = this.simbad.getWidth() / 2 - this.header.getWidth() / 2;
			int off1 = 90, off2 = 390;

			if (String.valueOf(this.score1).length() > 1)
				off1 -= 20;
			if (String.valueOf(this.score2).length() > 1)
				off2 -= 20;

			g.drawImage(this.header, origX, 0, null);

			g.setColor(Color.WHITE);
			g.setFont(this.font.deriveFont(Font.PLAIN, 28));
			g.drawString(String.valueOf(this.score1), origX + off1, 42);
			g.drawString(String.valueOf(this.score2), origX + off2, 42);

			g.setFont(this.font.deriveFont(Font.PLAIN, 26));
			g.drawString(PongPong.getInstance().getTimer(), origX + 187, 42);
		}
	}

	private void load() {
		try {
			this.header = ImageIO.read(this.getClass().getResourceAsStream("/img/scores.png"));

			this.font = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/font/arcade.ttf"));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
	}

}
