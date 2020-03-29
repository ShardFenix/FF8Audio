package ff8tools;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * I got sick of having to handle 4 different sound files against each other, so I made this one
 * This contains FF8SNDHEADER info as well as wav file data
 * It can convert from anything to anything.
 * @author Travis
 */
public class UnifiedSoundFile
{
//wave data
byte[] chunkID={0x52,0x49,0x46,0x46};
int chunkSize;
byte[] format={0x57,0x41,0x56,0x45};
byte[] subchunk1ID={0x66,0x6d,0x74,0x20};
int subchunk1Size;
short audioFormat;
short numChannels;
int sampleRate;
int byteRate;
short blockAlign;
short bitsPerSample;
byte[] subchunk2ID={0x64,0x61,0x74,0x61};
int length;
byte[] samples;

//ff8sndheader data
int offset;
byte[] zz1=new byte[12];
short wSamplesPerBlock;
short wNumCoef;
ADPCMCOEFSET[] aCoef=new ADPCMCOEFSET[8];	//32 bytes
WaveFormatEx wfex; //18 bytes

boolean nullHeader=false;
int fileSize=0;

public UnifiedSoundFile(FF8SNDHEADER header)
    {
    if (header==null)
        {
        nullHeader=true;
        return;
        }
    offset=header.offset;
    for (int i=0;i<12;i++)
        {
        zz1[i]=header.zz1[i];
        }
    wSamplesPerBlock=header.wSamplesPerBlock;
    wNumCoef=header.wNumCoef;
    aCoef=header.aCoef;
    //waveformatex
    wfex=header.wfex;
    audioFormat=wfex.wFormatTag;
    numChannels=wfex.nChannels;
    sampleRate=wfex.nSamplesPerSec;
    byteRate=wfex.nAvgBytesPerSec;
    blockAlign=wfex.nBlockAlign;
    bitsPerSample=wfex.wBitsPerSample;
    length=header.length;
    }

/*
 * Create a UnifiedSoundFile from a raw wav file (including header
 * this will only load a microsoft 4 bit PCM (the same format FF8 uses
 */
public UnifiedSoundFile(ByteBuffer buf)
    {
    //"RIFF"
    buf.get();buf.get();buf.get();buf.get();
    chunkSize=buf.getInt();
    //"WAVE"
    buf.get();buf.get();buf.get();buf.get();
    //"fmt "
    buf.get();buf.get();buf.get();buf.get();
    subchunk1Size=buf.getInt();
    audioFormat=buf.getShort();
    numChannels=buf.getShort();
    sampleRate=buf.getInt();
    byteRate=buf.getInt();
    blockAlign=buf.getShort();
    bitsPerSample=buf.getShort();
    //size of extra data
    short cbsize=buf.getShort();
    wSamplesPerBlock=buf.getShort();
    wNumCoef=buf.getShort();
    for (int i=0;i<wNumCoef;i++)
        {
        aCoef[i]=new ADPCMCOEFSET();
        aCoef[i].a=buf.getShort();
        aCoef[i].b=buf.getShort();
        }
    for (int i=0;i<8;i++)
        {
        if (aCoef[i]==null)
            {
            aCoef[i]=new ADPCMCOEFSET();
            aCoef[i].a=0;
            aCoef[i].b=0;
            }
        }
    //check fact chunk and remove it
    if (buf.get(70)=='f' && buf.get(71)=='a' && buf.get(72)=='c' && buf.get(73)=='t')
        {
        buf.get();buf.get();buf.get();buf.get();
        buf.get();buf.get();buf.get();buf.get();
        buf.get();buf.get();buf.get();buf.get();
        }
    //data
    buf.get();buf.get();buf.get();buf.get();
    length=buf.getInt();
    samples=new byte[length];
    buf.get(samples);
    
    //construct the wfex
    wfex=new WaveFormatEx();
    wfex.wFormatTag=audioFormat;
    wfex.nChannels=numChannels;
    wfex.nSamplesPerSec=sampleRate;
    wfex.nAvgBytesPerSec=byteRate;
    wfex.nBlockAlign=blockAlign;
    wfex.wBitsPerSample=bitsPerSample;
    wfex.cbSize=cbsize;//(short)(wNumCoef*4+4);
    }

public FF8SNDHEADER toHeader()
    {
    if (nullHeader)return null;
    FF8SNDHEADER header=new FF8SNDHEADER();
    header.aCoef=aCoef;
    header.length=length;
    header.wNumCoef=wNumCoef;
    header.wSamplesPerBlock=wSamplesPerBlock;
    header.wfex=wfex;
    header.zz1=zz1;
    
    return header;
    }

public int getHeaderSize()
    {
    if (nullHeader) return 38; //was 42
    return 42+4*wNumCoef;
    }

public byte[] toWaveFileByteArray()
    {
    int bufSize=50+wNumCoef*4+samples.length;
    ByteBuffer buf=ByteBuffer.wrap(new byte[bufSize]);
    buf.order(ByteOrder.LITTLE_ENDIAN);
    //"RIFF"
    buf.put(chunkID[0]);buf.put(chunkID[1]);buf.put(chunkID[2]);buf.put(chunkID[3]);
    buf.putInt(chunkSize);
    //"WAVE"
    buf.put(format[0]);buf.put(format[1]);buf.put(format[2]);buf.put(format[3]);
    //"fmt "
    buf.put(subchunk1ID[0]);buf.put(subchunk1ID[1]);buf.put(subchunk1ID[2]);buf.put(subchunk1ID[3]);
    
    buf.putInt(subchunk1Size);
    buf.putShort(audioFormat);
    buf.putShort(numChannels);
    buf.putInt(sampleRate);
    buf.putInt(byteRate);
    buf.putShort(blockAlign);
    buf.putShort(bitsPerSample);
    buf.putShort((short)(4+wNumCoef*4));
    buf.putShort(wSamplesPerBlock);
    buf.putShort(wNumCoef);
    for (int i=0;i<wNumCoef;i++)
        {
        buf.putShort(aCoef[i].a);
        buf.putShort(aCoef[i].b);
        }
    //"data"
    buf.put(subchunk2ID[0]);buf.put(subchunk2ID[1]);buf.put(subchunk2ID[2]);buf.put(subchunk2ID[3]);
    buf.putInt(length);
    buf.put(samples);
    return buf.array();
    }

public byte[] toJavaPlayableWaveFileByteArray()
    {
    int blockSize=blockAlign;
    int blocks=samples.length/blockSize;
    int newlength=0;
    int newSampleArraySize=((samples.length)-(7*blocks))*4;
    int bufSize=44+(newSampleArraySize);
    ByteBuffer buf=ByteBuffer.wrap(new byte[bufSize]);
    buf.order(ByteOrder.LITTLE_ENDIAN);
    
    //"RIFF"
    buf.put(chunkID[0]);buf.put(chunkID[1]);buf.put(chunkID[2]);buf.put(chunkID[3]);
    buf.putInt(bufSize+8);
    //"WAVE"
    buf.put(format[0]);buf.put(format[1]);buf.put(format[2]);buf.put(format[3]);
    //"fmt "
    buf.put(subchunk1ID[0]);buf.put(subchunk1ID[1]);buf.put(subchunk1ID[2]);buf.put(subchunk1ID[3]);
    
    buf.putInt(16);//16 for pcm, was subchunk1Size);
    //buf.putShort(audioFormat);
    buf.putShort((short)1);
    buf.putShort(numChannels);
    buf.putInt(sampleRate);
    buf.putInt(sampleRate*numChannels*(4*bitsPerSample)/8);
    buf.putShort((short)(numChannels*(4*bitsPerSample)/8));
    buf.putShort((short)(4*bitsPerSample));
    //"data"
    buf.put(subchunk2ID[0]);buf.put(subchunk2ID[1]);buf.put(subchunk2ID[2]);buf.put(subchunk2ID[3]);
    
    //convert ADPMC samples to waveform
    //this part doesnt quite work yet
    ByteBuffer newdata=ByteBuffer.wrap(new byte[newSampleArraySize]).order(ByteOrder.LITTLE_ENDIAN);
    for (int i=0;i<blocks;i++)
        {
        byte[] block=Arrays.copyOfRange(samples, i*blockSize, (i+1)*blockSize);
        byte[] convert=ADPCMConverter.decodeBlock(block, 0);
        newdata.put(convert);
        newlength+=convert.length;
        }
    buf.putInt(newlength);
    byte[] newsamples=Arrays.copyOfRange(newdata.array(),0,newlength);
    buf.put(newdata.array());
    return buf.array();
    }
}
