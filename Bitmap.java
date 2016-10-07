import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Bitmap {
	private final int m_width;
	private final int m_height;
	private final byte m_components[];
	
	public Bitmap(int width,int height)
	{
		m_width = width;
		m_height = height;
		m_components = new byte[width * height * 4];
	}
	
	public Bitmap(String fileName) throws IOException
	{
		int width = 0;
		int height = 0;
		byte[] components = null;

		BufferedImage image = ImageIO.read(new File(fileName));

		width = image.getWidth();
		height = image.getHeight();

		int imgPixels[] = new int[width * height];
		image.getRGB(0, 0, width, height, imgPixels, 0, width);
		components = new byte[width * height * 4];

		for(int i = 0; i < width * height; i++)
		{
			int pixel = imgPixels[i];

			components[i * 4]     = (byte)((pixel >> 24) ); // A
			components[i * 4 + 1] = (byte)((pixel      ) ); // B
			components[i * 4 + 2] = (byte)((pixel >> 8 ) ); // G
			components[i * 4 + 3] = (byte)((pixel >> 16) ); // R
		}

		m_width = width;
		m_height = height;
		m_components = components;
	}

	
	public void Clear(byte clearValue)
	{
		Arrays.fill(m_components, clearValue);
	}
	
	public void DrawPixel(int x,int y,byte a,byte r,byte g,byte b)
	{
		int index = (x + y * m_width) * 4;
		m_components[index    ] = a;
		m_components[index + 1] = r;
		m_components[index + 2] = g;
		m_components[index + 3] = b;
	}
	
	public void CopyPixel(int x,int y,int srcX,int srcY,Bitmap texture,float lightAmt)
	{
		int index = (x + y * m_width) * 4;
		int srcIndex = (srcX + srcY * texture.m_width) * 4;
		//有符号转无符号
		m_components[index    ] = (byte)((texture.GetComponent(srcIndex    )&0xFF) * lightAmt);
		m_components[index + 1] = (byte)((texture.GetComponent(srcIndex + 1)&0xFF) * lightAmt);
		m_components[index + 2] = (byte)((texture.GetComponent(srcIndex + 2)&0xFF) * lightAmt);
		m_components[index + 3] = (byte)((texture.GetComponent(srcIndex + 3)&0xFF) * lightAmt);
	}
	public void CopyToByteArray(byte[] dest)
	{
		int count = m_width * m_height;
		for(int i=0;i<count;i++)
		{
			dest[3 * i    ] = m_components[4 * i + 1];
			dest[3 * i + 1] = m_components[4 * i + 2];
			dest[3 * i + 2] = m_components[4 * i + 3];
		}
	}
	
	public int GetComponentLength()
	{
		return m_components.length;
	}
	public byte GetComponent(int index)
	{
		return m_components[index];
	}
	public int GetWidth()
	{
		return m_width;
	}
	public int GetHeight()
	{
		return m_height;
	}
}
