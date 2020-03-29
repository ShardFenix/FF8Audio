package ff8tools;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ADPCMConverter
{

static int[] AdaptationTable = { 
  230, 230, 230, 230, 307, 409, 512, 614, 
  768, 614, 512, 409, 307, 230, 230, 230 } ;

static int[] AdaptCoeff1 = { 256, 512, 0, 192, 240, 460, 392 } ;
static int[] AdaptCoeff2 = { 0, -256, 0, 64, 0, -208, -232 } ;

//Number of samples in a single block.
public static int BLOCKSAMPLES = 1017;

//44100

/*
 * works like the other algorithm (same output). Reasonable to assume that
 * the sample data is being written incorrectly elsewhere
 */ 
public static byte[] decodeBlock(byte[] adpcm, int offset)
    {
    ByteBuffer header=ByteBuffer.wrap(adpcm,0,7).order(ByteOrder.LITTLE_ENDIAN);
    byte blockPredictor=header.get();
    short delta=header.getShort();
    short sample1=header.getShort();
    short sample2=header.getShort();
    
    int coeff1=AdaptCoeff1[blockPredictor];
    int coeff2=AdaptCoeff2[blockPredictor];
    
    ByteBuffer result=ByteBuffer.allocate(BLOCKSAMPLES*4).order(ByteOrder.LITTLE_ENDIAN);
    result.putShort(sample2);
    result.putShort(sample1);
    
    boolean highNibble=true;
    int predictor;
    int nibble;
    for (int i=7;i<BLOCKSAMPLES;)
        {
        byte cur=adpcm[i];
        if (highNibble)
            {
            nibble=(cur >> 4) & 0x0f;
            highNibble=false;
            }
        else
            {
            nibble=(cur & 0x0f);
            highNibble=true;
            i++;
            }
        
        Nibble nib=new Nibble(nibble);
        int signedNib=nib.getSignedValue();
        int unsignedNib=nib.getUnsignedValue();
        
        predictor = ((sample1 * coeff1) + (sample2 * coeff2)) / 256;
        predictor+=delta*(nib.getSignedValue());
        if (predictor<-32768)predictor=-32768;
        if (predictor>32767)predictor=32767;
        result.putShort((short)predictor);
        if (i==9 && !highNibble)
            {
            System.out.println();
            }
        sample2=sample1;
        sample1=(short)predictor;
        delta = (short) ((AdaptationTable[nibble] * delta) / 256);
        if (delta<16)delta=16;
        }
    return result.array();
    }

}
