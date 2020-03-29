package ff8tools;

//18 bytes
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class WaveFormatEx
{
short wFormatTag;
short nChannels;
int nSamplesPerSec;
int nAvgBytesPerSec;
short nBlockAlign;
short wBitsPerSample;
short cbSize;

public WaveFormatEx(){}

public WaveFormatEx(ByteBuffer buf)
    {
    wFormatTag=buf.getShort();
    nChannels=buf.getShort();
    nSamplesPerSec=buf.getInt();
    nAvgBytesPerSec=buf.getInt();
    nBlockAlign=buf.getShort();
    wBitsPerSample=buf.getShort();
    cbSize=buf.getShort();
    }

public byte[] toByteArray()
    {
    ByteBuffer buf=ByteBuffer.wrap(new byte[18]).order(ByteOrder.LITTLE_ENDIAN);
    buf.putShort(wFormatTag);
    buf.putShort(nChannels);
    buf.putInt(nSamplesPerSec);
    buf.putInt(nAvgBytesPerSec);
    buf.putShort(nBlockAlign);
    buf.putShort(wBitsPerSample);
    buf.putShort(cbSize);
    return buf.array();
    }
}
