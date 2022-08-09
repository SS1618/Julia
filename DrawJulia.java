import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
public class DrawJulia extends JPanel implements AdjustmentListener, MouseListener{
	int imgWidth, imgHeight;
	double zoom;
	double A, B;
	JFrame frame;
	JScrollBar ABar, BBar, iterBar, eqBar, hueBar, zoomBar, satBar, brightBar;
	JPanel scrollPanel, labelPanel, bigPanel;
	JLabel ALabel, BLabel, iterLabel, eqLabel, hueLabel, zoomLabel, satLabel, brightLabel;
	int rez;
	float maxIter, hueValue, satValue, brightValue;
	int order;
	DrawJulia(){
		frame = new JFrame("Sets");
		imgWidth = 1000;
		imgHeight = 600;
		A = 0.0;
		B = 0.0;
		zoom = 1;
		rez = 1;
		frame.add(this);
		frame.setSize(imgWidth, imgHeight);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ABar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
		A = (double)ABar.getValue() / 2000.0;
		ABar.addAdjustmentListener(this);
		ABar.addMouseListener(this);

		BBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, -2000, 2000);
		B = (double)BBar.getValue() / 2000.0;
		BBar.addAdjustmentListener(this);
		BBar.addMouseListener(this);

		iterBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 1000);
		maxIter = (float)BBar.getValue();
		iterBar.addAdjustmentListener(this);
		iterBar.addMouseListener(this);

		eqBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 10);
		order = (int)eqBar.getValue();
		eqBar.addAdjustmentListener(this);
		eqBar.addMouseListener(this);

		hueBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 1000);
		hueValue = (int)hueBar.getValue();
		hueBar.addAdjustmentListener(this);
		hueBar.addMouseListener(this);

		satBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 1000);
		satValue = (int)satBar.getValue();
		satBar.addAdjustmentListener(this);
		satBar.addMouseListener(this);

		brightBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 1000);
		brightValue = (int)brightBar.getValue();
		brightBar.addAdjustmentListener(this);
		brightBar.addMouseListener(this);

		zoomBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 1000);
		zoom = (double)zoomBar.getValue() / 100.0;
		zoomBar.addAdjustmentListener(this);
		zoomBar.addMouseListener(this);

		GridLayout grid = new GridLayout(8, 1);
		ALabel = new JLabel("A");
		BLabel = new JLabel("B");
		iterLabel = new JLabel("Iterations");
		eqLabel = new JLabel("Order");
		hueLabel = new JLabel("Hue");
		zoomLabel = new JLabel("Zoom");
		satLabel = new JLabel("Saturation");
		brightLabel = new JLabel("Brightness");

		scrollPanel = new JPanel();
		scrollPanel.setLayout(grid);
		scrollPanel.add(ABar);
		scrollPanel.add(BBar);
		scrollPanel.add(iterBar);
		scrollPanel.add(eqBar);
		scrollPanel.add(hueBar);
		scrollPanel.add(zoomBar);
		scrollPanel.add(satBar);
		scrollPanel.add(brightBar);

		labelPanel = new JPanel();
		labelPanel.setLayout(grid);
		labelPanel.add(ALabel);
		labelPanel.add(BLabel);
		labelPanel.add(iterLabel);
		labelPanel.add(eqLabel);
		labelPanel.add(hueLabel);
		labelPanel.add(zoomLabel);
		labelPanel.add(satLabel);
		labelPanel.add(brightLabel);

		bigPanel = new JPanel();
		bigPanel.setLayout(new BorderLayout());
		bigPanel.add(labelPanel, BorderLayout.WEST);
		bigPanel.add(scrollPanel, BorderLayout.CENTER);

		frame.add(bigPanel, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		BufferedImage image = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
		for(int h = 0; h < imgHeight; h+= rez){
			for(int w = 0; w < imgWidth; w+= rez){
				double zy = ((h - (0.5 * imgHeight))/(0.5*zoom*imgHeight));
				double zx = 1.5 * ((w - (0.5 * imgWidth))/(0.5 * zoom * imgWidth));
				//System.out.println(h + " " + w);
				//System.out.println(zy + " " + zx);
				float iter = maxIter;
				while((zx * zx) + (zy * zy) < 6.0 && iter > 0){
					/*double a = (zx * zx) - (zy * zy) + A;
					zy = (2 * zx * zy) + B;
					zx = a;*/
					double[] comp = calcRec(zx, zy, order);
					zx = comp[0] + A;
					zy = comp[1] + B;
					iter -= 1;
				}
				int c;
				if(iter > 0)
					c = Color.HSBtoRGB(hueValue * (maxIter / iter) % 1, satValue, brightValue);
				else
					c = Color.HSBtoRGB(hueValue * maxIter / iter, satValue, 0);
				image.setRGB(w, h, c);
			}
		}
		g.drawImage(image, 0, 0, null);
	}
	public void adjustmentValueChanged(AdjustmentEvent e){
		if(e.getSource() == ABar){
			A = (double)ABar.getValue() / 2000.0;
		}
		else if(e.getSource() == BBar){
			B = (double)BBar.getValue() / 2000.0;
		}
		else if(e.getSource() == iterBar){
			maxIter = (float)iterBar.getValue();
		}
		else if(e.getSource() == eqBar){
			order = (int)eqBar.getValue();
		}
		else if(e.getSource() == hueBar){
			hueValue = (float)(hueBar.getValue() / 1000.0);
		}
		else if(e.getSource() == zoomBar){
			zoom = (double)zoomBar.getValue() / 100.0;
		}
		else if(e.getSource() == brightBar){
			brightValue = (float)brightBar.getValue() / 1000.0f;
		}
		else if(e.getSource() == satBar){
			satValue = (float)satBar.getValue() / 1000.0f;
		}
		repaint();
	}
	public void mousePressed(MouseEvent e) {
		rez = 3;
	}

	public void mouseReleased(MouseEvent e) {
		rez = 1;
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {

    }
    double[] calcRec(double a, double b, int n){
		double x_sum = 0;
		double y_sum = 0;
		for(int i = 0; i <= n; i++){
			if(i % 4 == 0){
				x_sum += (calcBinomial(n, i) * calcPow(a, n - i) * calcPow(b, i));
			}
			else if(i % 4 == 1){
				y_sum += (calcBinomial(n, i) * calcPow(a, n - i) * calcPow(b, i));
			}
			else if(i % 4 == 2){
				x_sum -= (calcBinomial(n, i) * calcPow(a, n - i) * calcPow(b, i));
			}
			else if(i % 4 == 3){
				y_sum -= (calcBinomial(n, i) * calcPow(a, n - i) * calcPow(b, i));
			}
		}
		double[] r = {x_sum, y_sum};
		return r;
	}
	double calcBinomial(int n, int k){
		double r = 1;
		for(int i = k + 1; i <= n; i++){
			r *= i;
		}
		double q = 1;
		for(int i = 2; i <= (n - k); i++){
			q *= i;
		}
		return r/q;
	}
	double calcPow(double a, int p){
		double r = 1;
		for(int i = 0; i < p; i++){
			r *= a;
		}
		return r;
	}
	public static void main(String[]args){
		DrawJulia dj = new DrawJulia();
	}

}