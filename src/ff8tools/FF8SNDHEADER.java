package ff8tools;

//70 bytes

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class FF8SNDHEADER
{
int length;
int offset;
byte[] zz1=new byte[12];
WaveFormatEx wfex; //18 bytes
short wSamplesPerBlock;
short wNumCoef;
ADPCMCOEFSET[] aCoef=new ADPCMCOEFSET[8];	//32 bytes

public FF8SNDHEADER(){}

public FF8SNDHEADER(ByteBuffer buf)
    {
    length=buf.getInt();
    offset=buf.getInt();
    for (int i=0;i<12;i++)
        zz1[i]=buf.get();
    byte[] waveformatex=new byte[18];
    buf.get(waveformatex, 0, 18);
    wfex=new WaveFormatEx(ByteBuffer.wrap(waveformatex).order(ByteOrder.LITTLE_ENDIAN));
    wSamplesPerBlock=buf.getShort();
    wNumCoef=buf.getShort();
    for (int i=0;i<8;i++)
        {
        try{
        aCoef[i]=new ADPCMCOEFSET();
        aCoef[i].a=buf.getShort();
        aCoef[i].b=buf.getShort();
        }catch (java.nio.BufferUnderflowException e){}
        }
    }

//Returns the object's size in bytes
public int getSize()
    {
    return 42+4*wNumCoef;
    }

public byte[] toByteArray()
    {
    ByteBuffer buf=ByteBuffer.allocate(getSize()).order(ByteOrder.LITTLE_ENDIAN);
    buf.putInt(length);
    buf.putInt(offset);
    for (int i=0;i<12;i++)
        buf.put(zz1[i]);
    buf.put(wfex.toByteArray());
    buf.putShort(wSamplesPerBlock);
    buf.putShort(wNumCoef);
    for (int i=0;i<wNumCoef;i++)
        {
        buf.putShort(aCoef[i].a);
        buf.putShort(aCoef[i].b);
        }
    return buf.array();
    }
}
