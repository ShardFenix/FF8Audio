package ff8tools;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FF8SND extends javax.swing.JFrame
{
char[] hdrmem;
int[] hdroffset;
byte[] fmtHeader=new byte[40];
ArrayList<UnifiedSoundFile> soundFiles=new ArrayList<UnifiedSoundFile>();
String ff8dir="";


public FF8SND()
    {
    initComponents();
    ff8dir="C:/Games/FF8/Data/Sound/";
    File dir=new File(ff8dir+"export/");
        try
            {
            Files.createDirectory(dir.toPath());
            }
        catch (IOException e){}   
    }

/*
 * Reloads all the information for the table.
 */
public void reloadTableData()
    {
    tSounds.setModel(new javax.swing.table.DefaultTableModel(
            
            new Object [soundFiles.size()+5][5],
            new String [] {
                "FF8SND ID", "Field ID","Size (bytes)", "Bitrate","Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {false, false, false,false,true};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
    tSounds.getColumnModel().getColumn(0).setPreferredWidth(80);
    tSounds.getColumnModel().getColumn(0).setMaxWidth(80);
    tSounds.getColumnModel().getColumn(1).setPreferredWidth(60);
    tSounds.getColumnModel().getColumn(1).setMaxWidth(60);
    tSounds.getColumnModel().getColumn(2).setPreferredWidth(80);
    tSounds.getColumnModel().getColumn(2).setMaxWidth(80);
    tSounds.getColumnModel().getColumn(3).setPreferredWidth(60);
    tSounds.getColumnModel().getColumn(3).setMaxWidth(60);
    
    int index2=0;
    for (int i=0;i<soundFiles.size();i++)
        {
        UnifiedSoundFile sound=soundFiles.get(i);
        if (sound.nullHeader)
            {
            tSounds.setValueAt("-", i, 0);
            tSounds.setValueAt(7851+i, i, 1);
            tSounds.setValueAt("0", i, 2);
            tSounds.setValueAt("0", i, 3);
            continue;
            }
        else
            {
            tSounds.setValueAt(index2, i, 0);
            tSounds.setValueAt(7851+i, i, 1);
            tSounds.setValueAt(sound.toHeader().length, i, 2);
            tSounds.setValueAt(sound.wfex.nSamplesPerSec, i, 3);
            index2++;
            }
        }
    //descriptions
    try{
    File descript=new File(ff8dir+"Descriptions.txt");
    Scanner scan=new Scanner(descript);
    while (scan.hasNextLine())
        {
        try{
        String sLine=scan.nextLine();
        if (sLine.isEmpty())continue;
        sLine=sLine.replaceAll("//.*", ""); //erase comments
        sLine=sLine.replaceFirst("\\s+", "=");//convert to readable line
        int index=sLine.indexOf('=');
        if (index==-1)continue;
        int rowNumber=Integer.parseInt(sLine.substring(0,index))-7851;
        String value=sLine.substring(index+1);
        tSounds.setValueAt(value, rowNumber, 4);
        }catch (NumberFormatException e){;}
        }
    }catch (Exception e){;}
    }

@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        loadFMT = new javax.swing.JButton();
        saveFMT = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tSounds = new javax.swing.JTable();
        bAddSound = new javax.swing.JButton();
        bReplaceSound = new javax.swing.JButton();
        bPlaySound = new javax.swing.JButton();
        bDebug = new javax.swing.JButton();
        bExtract = new javax.swing.JButton();
        bExtractAll = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FF8Audio        © 2014 Travis Schnell");

        loadFMT.setText("Load FMT");
        loadFMT.setToolTipText("Loads audio.fmt");
        loadFMT.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                loadFMTActionPerformed(evt);
            }
        });

        saveFMT.setText("Save FMT");
        saveFMT.setToolTipText("Saves audio.fmt and audio.dat");
        saveFMT.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                saveFMTActionPerformed(evt);
            }
        });

        tSounds.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][]
            {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String []
            {
                "FF8SND ID", "Field ID", "Size", "Bitrate", "Description"
            }
        )
        {
            boolean[] canEdit = new boolean []
            {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tSounds);
        tSounds.getColumnModel().getColumn(0).setPreferredWidth(70);
        tSounds.getColumnModel().getColumn(0).setMaxWidth(70);
        tSounds.getColumnModel().getColumn(1).setPreferredWidth(50);
        tSounds.getColumnModel().getColumn(1).setMaxWidth(50);
        tSounds.getColumnModel().getColumn(2).setPreferredWidth(70);
        tSounds.getColumnModel().getColumn(2).setMaxWidth(70);
        tSounds.getColumnModel().getColumn(3).setPreferredWidth(60);
        tSounds.getColumnModel().getColumn(3).setMaxWidth(60);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        bAddSound.setText("Add Sound");
        bAddSound.setToolTipText("Add a new sound to the list");
        bAddSound.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bAddSoundActionPerformed(evt);
            }
        });

        bReplaceSound.setText("Replace Sound");
        bReplaceSound.setToolTipText("Replace the currently selected sound with a new one");
        bReplaceSound.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bReplaceSoundActionPerformed(evt);
            }
        });

        bPlaySound.setText("Play Sound");
        bPlaySound.setToolTipText("This doesn't work properly on some sounds");
        bPlaySound.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bPlaySoundActionPerformed(evt);
            }
        });

        bDebug.setText("Mass Import");
        bDebug.setToolTipText("All files in the /import folder will be added by Field ID based on their file name.");
        bDebug.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bDebugActionPerformed(evt);
            }
        });

        bExtract.setText("Extract Sound");
        bExtract.setToolTipText("Saves the selected sound to the /export folder");
        bExtract.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bExtractActionPerformed(evt);
            }
        });

        bExtractAll.setText("Extract All");
        bExtractAll.setToolTipText("Saves all sounds to the /export folder");
        bExtractAll.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                bExtractAllActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loadFMT)
                    .addComponent(saveFMT)
                    .addComponent(bAddSound)
                    .addComponent(bReplaceSound)
                    .addComponent(bPlaySound)
                    .addComponent(bDebug)
                    .addComponent(bExtract)
                    .addComponent(bExtractAll))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(loadFMT)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveFMT)
                        .addGap(70, 70, 70)
                        .addComponent(bPlaySound)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bExtract)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bExtractAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addComponent(bDebug)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bReplaceSound)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bAddSound)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadFMTActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_loadFMTActionPerformed
    {//GEN-HEADEREND:event_loadFMTActionPerformed
    try{
    File audioDat=new File(ff8dir+"audio.dat");
    File fmtFile=new File(ff8dir+"audio.fmt");
    
    ByteBuffer datData=ByteBuffer.wrap(Files.readAllBytes(audioDat.toPath())).order(ByteOrder.LITTLE_ENDIAN);
    ByteBuffer fileHDR=ByteBuffer.wrap(Files.readAllBytes(fmtFile.toPath())).order(ByteOrder.LITTLE_ENDIAN);
    for (int i=0;i<40;i++)
        {
        fmtHeader[i]=fileHDR.get();
        }
    
    fileHDR.rewind();
    int len=fileHDR.limit()-0x28;
    int nHeaders = 5+fileHDR.getInt();
    fileHDR.position(0x28);
    
    hdrmem=new char[len];
    hdroffset=new int[len];
    fileHDR.asCharBuffer().get(hdrmem, fileHDR.position(), len/2);
    FF8SNDHEADER hdr=null;
    
    soundFiles.clear();
    
    tSounds.setModel(new javax.swing.table.DefaultTableModel(
            
            new Object [nHeaders][5],
            new String [] {
                "FF8SND ID", "Field ID","Size (bytes)", "Bitrate","Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {false, false, false,false,true};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
    tSounds.getColumnModel().getColumn(0).setPreferredWidth(80);
    tSounds.getColumnModel().getColumn(0).setMaxWidth(80);
    tSounds.getColumnModel().getColumn(1).setPreferredWidth(60);
    tSounds.getColumnModel().getColumn(1).setMaxWidth(60);
    tSounds.getColumnModel().getColumn(2).setPreferredWidth(80);
    tSounds.getColumnModel().getColumn(2).setMaxWidth(80);
    tSounds.getColumnModel().getColumn(3).setPreferredWidth(60);
    tSounds.getColumnModel().getColumn(3).setMaxWidth(60);
    
    
    
    int n=40, i=0;
    int totalTries=-1;
    fileHDR.rewind();
    byte[] hdrBuffer=fileHDR.array();
    
    fileHDR.position(0x28);
    while (n<len)
        {
        totalTries++;
        
        byte[] temp=new byte[0];
        try{
        //find size of this header line (42 + 4*numCoefs)
        //numCoefs is a short at offset 0x42
        short numCoefs=fileHDR.getShort(n+40);
        temp=new byte[42+4*numCoefs];
        fileHDR.get(temp, 0, 42+4*numCoefs);
        }catch (Exception e2){e2.printStackTrace();}
        try {
            hdr=new FF8SNDHEADER(ByteBuffer.wrap(temp).order(ByteOrder.LITTLE_ENDIAN));
            }catch (Exception e)
                {
                //I don't think this block ever gets run
                n+=38;
                e.printStackTrace();
                FF8SNDHEADER nullHeader=null;
                soundFiles.add(new UnifiedSoundFile(nullHeader));
                fileHDR.position(n);
                tSounds.setValueAt("-", totalTries, 0);
                tSounds.setValueAt(7851+totalTries, totalTries, 1);
                tSounds.setValueAt("0", totalTries, 2);
                tSounds.setValueAt("0", totalTries, 3);
                continue;
                }
        if (hdr.length==0)
            {
            n+=38;
            fileHDR.position(n);
            FF8SNDHEADER nullHeader=null;
            soundFiles.add(new UnifiedSoundFile(nullHeader));
            tSounds.setValueAt("-", totalTries, 0);
            tSounds.setValueAt(7851+totalTries, totalTries, 1);
            tSounds.setValueAt("0", totalTries, 2);
            tSounds.setValueAt("0", totalTries, 3);
            continue;
            }
        tSounds.setValueAt(i, totalTries, 0);
        tSounds.setValueAt((7851+totalTries),totalTries,1);
        tSounds.setValueAt(hdr.length, totalTries, 2);
        tSounds.setValueAt(hdr.wfex.nSamplesPerSec, totalTries, 3);
        n+=42+hdr.wNumCoef*4;
        hdroffset[i]=n;
        
        UnifiedSoundFile sound=new UnifiedSoundFile(hdr);
        if (sound.zz1[0]==1)
            {
            //System.out.println("Sound at index "+(7851+totalTries)+" ("+i+") has zz1s: "+Arrays.toString(sound.zz1));
            }
        int chunkSize = hdr.length + 36 + (hdr.wfex.cbSize == 0 ? 0 : (4+hdr.wNumCoef * 4));
        sound.chunkSize=chunkSize;
        //8 after chunk size
        int subchunksize=18 + (hdr.wfex.cbSize == 0 ? 0 : (4+hdr.wNumCoef*4));
        sound.subchunk1Size=subchunksize;
        //write the sound data
        byte[] cbuf=Arrays.copyOfRange(datData.array(), hdr.offset, hdr.offset+hdr.length);
        sound.samples=cbuf;
        soundFiles.add(sound);
        i++;
        }
    //update descriptions from Descriptions.txt
    File descript=new File(ff8dir+"Descriptions.txt");
    Scanner scan=new Scanner(descript);
    while (scan.hasNextLine())
        {
        try{
        String sLine=scan.nextLine();
        if (sLine.isEmpty())continue;
        sLine=sLine.replaceAll("//.*", ""); //erase comments
        sLine=sLine.replaceFirst("\\s+", "=");//convert to readable line
        int index=sLine.indexOf('=');
        if (index==-1)continue;
        int rowNumber=Integer.parseInt(sLine.substring(0,index))-7851;
        String value=sLine.substring(index+1);
        tSounds.setValueAt(value, rowNumber, 4);
        }catch (NumberFormatException e){;}
        }
    }catch (Exception e){e.printStackTrace();}
    }//GEN-LAST:event_loadFMTActionPerformed

    private void saveFMTActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_saveFMTActionPerformed
    {//GEN-HEADEREND:event_saveFMTActionPerformed
    try{
    int totalSizeFMT=40;
    for (UnifiedSoundFile fsh : soundFiles)
        {totalSizeFMT+=fsh.getHeaderSize();}
    File datFile=new File(ff8dir+"audio.dat");
    FileOutputStream fos=new FileOutputStream(datFile);
    
    ByteBuffer headerBuf=ByteBuffer.allocate(totalSizeFMT).order(ByteOrder.LITTLE_ENDIAN);
    int offset=0; //where each sound is in the dat
    headerBuf.putInt(soundFiles.size());
    for (int i=4;i<40;i++){headerBuf.put(fmtHeader[i]);}//fmt file header
    
    int count=0;
    //remove all blank entries from the end of the list
    for (int i=soundFiles.size()-1;i>=0;i--)
        {
        if (soundFiles.get(i).nullHeader)
            {
            soundFiles.remove(i);
            count++;
            continue;
            }
        else
            i=-1;
        }
    System.out.println("Removed last "+count+" empty sound files.");
    System.out.println("Last index is now "+(7851+soundFiles.size()));
    
    int count2=0;
    for (UnifiedSoundFile sound : soundFiles)
        {
        count2++;
        FF8SNDHEADER header=sound.toHeader();
        if (header==null)
            {
            for (int i=0;i<38;i++)//38 zeroes in blank header line
                headerBuf.put((byte)0);
            continue;
            }
        fos.write(sound.samples);
        header.offset=offset;
        headerBuf.put(header.toByteArray());
        offset+=sound.samples.length;
        }
    //the FMT file might have a footer, this is it
    byte[] fmtFooter={(byte)0xbf,(byte)0x32,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0xd0,(byte)0x4c,(byte)0x06,
                      (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                      (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x00,(byte)0x01,(byte)0x00,
                      (byte)0x11,(byte)0x2b,(byte)0x00,(byte)0x00,(byte)0x01,(byte)0x00,(byte)0x08,(byte)0x00,
                      (byte)0x00,(byte)0x00};
    //headerBuf.put(fmtFooter);
    fos.close();
    Files.write(new File(ff8dir+"audio.fmt").toPath(), headerBuf.array());

    //write back Descriptions.txt file
    File fDescript=new File(ff8dir+"Descriptions.txt");
    if (!fDescript.exists())
        fDescript.createNewFile();
    PrintWriter pw=new PrintWriter(new BufferedWriter(new FileWriter(fDescript)));
    for (int i=0;i<tSounds.getRowCount();i++)
        {
        try{
        String descript=(String)tSounds.getValueAt(i, 4);
        if (descript.isEmpty())continue;
        pw.println((i+7851)+"\t\t"+descript);
        }catch (Exception e){}
        }
    pw.close();
    }catch (Exception e){e.printStackTrace();}

    }//GEN-LAST:event_saveFMTActionPerformed

    private void bPlaySoundActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bPlaySoundActionPerformed
    {//GEN-HEADEREND:event_bPlaySoundActionPerformed
    //if (1==1)return;
    try{
    int row=tSounds.getSelectedRow(); 
    UnifiedSoundFile sound=soundFiles.get(row);
    if (sound.nullHeader)
        {
        System.out.println("Sound file at line "+row+" is null.");
        return;
        }
    File file=new File(ff8dir+"export/PlayPreview.wav");
    FileOutputStream fos=new FileOutputStream(file);
    fos.write(sound.toJavaPlayableWaveFileByteArray());
    fos.close();
    
    URL url = file.toURI().toURL();
    AudioInputStream ais = AudioSystem.getAudioInputStream(url); 
    AudioFormat littleEndianFormat = ais.getFormat();
    AudioInputStream converted = AudioSystem.getAudioInputStream(littleEndianFormat, ais);
    
    Clip clip=AudioSystem.getClip();
    clip.open(converted);
    clip.start();
    }catch (Exception e){e.printStackTrace();}
    }//GEN-LAST:event_bPlaySoundActionPerformed

    private void bReplaceSoundActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bReplaceSoundActionPerformed
    {//GEN-HEADEREND:event_bReplaceSoundActionPerformed
    int row=tSounds.getSelectedRow();
    if (row>soundFiles.size())return;
    try{
        JFileChooser fc=new JFileChooser();
        FileNameExtensionFilter filter=new FileNameExtensionFilter("Microsoft 4 bit ADPCM","wav");
        fc.setFileFilter(filter);
        fc.setCurrentDirectory(new File(ff8dir));
        int returnval=fc.showOpenDialog(jPanel1);
        File file = fc.getSelectedFile();
        ByteBuffer wavdata=ByteBuffer.wrap(Files.readAllBytes(file.toPath())).order(ByteOrder.LITTLE_ENDIAN);
        UnifiedSoundFile sound=new UnifiedSoundFile(wavdata);
        soundFiles.set(row, sound);
        reloadTableData();
        }catch (Exception e){e.printStackTrace();}
    }//GEN-LAST:event_bReplaceSoundActionPerformed

    private void bDebugActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bDebugActionPerformed
    {//GEN-HEADEREND:event_bDebugActionPerformed
    File file=new File(ff8dir+"import/");
    if (!file.exists())return;
    if (!file.isDirectory())return;
    File[] fileList=file.listFiles();
    for (File f : fileList)
        {
        int index=0;
        if (!f.getName().endsWith(".wav"))continue;
        try{
            index=Integer.parseInt(f.getName().substring(0, f.getName().indexOf('.')));
            }catch (NumberFormatException e){e.printStackTrace();continue;}
        index-=7851;
        try{
        ByteBuffer wavdata=ByteBuffer.wrap(Files.readAllBytes(f.toPath())).order(ByteOrder.LITTLE_ENDIAN);
        UnifiedSoundFile sound=new UnifiedSoundFile(wavdata);
        while (soundFiles.size()<=index)
            {
            FF8SNDHEADER nullHeader=null;
            soundFiles.add(new UnifiedSoundFile(nullHeader));
            }
        soundFiles.set(index, sound);
        }catch (Exception e){e.printStackTrace();continue;}
        }
    reloadTableData();
    }//GEN-LAST:event_bDebugActionPerformed

    private void bExtractActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bExtractActionPerformed
    {//GEN-HEADEREND:event_bExtractActionPerformed
    //if (1==1)return;
    try{
    int row=tSounds.getSelectedRow(); 
    UnifiedSoundFile sound=soundFiles.get(row);
    if (sound.nullHeader)
        {
        System.out.println("Sound file at line "+row+" is null.");
        return;
        }
    File file=new File(ff8dir+"export/"+(7851+row)+".wav");
    FileOutputStream fos=new FileOutputStream(file);
    fos.write(sound.toWaveFileByteArray());
    fos.close();
    }catch (Exception e){e.printStackTrace();}
    }//GEN-LAST:event_bExtractActionPerformed

    private void bExtractAllActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bExtractAllActionPerformed
    {//GEN-HEADEREND:event_bExtractAllActionPerformed
    try{
    for (int i=0;i<soundFiles.size();i++)
        {
        UnifiedSoundFile sound=soundFiles.get(i);
        if (sound.nullHeader)
            {
            continue;
            }
        File file=new File(ff8dir+"export/"+(i+7851)+".wav");
        FileOutputStream fos=new FileOutputStream(file);
        fos.write(sound.toWaveFileByteArray());
        fos.close();
        }
    }catch (Exception e){e.printStackTrace();}
    }//GEN-LAST:event_bExtractAllActionPerformed

    private void bAddSoundActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_bAddSoundActionPerformed
    {//GEN-HEADEREND:event_bAddSoundActionPerformed
    try{
        JFileChooser fc=new JFileChooser();
        FileNameExtensionFilter filter=new FileNameExtensionFilter("Microsoft 4 bit ADPCM","wav");
        fc.setFileFilter(filter);
        fc.setCurrentDirectory(new File(ff8dir));
        int returnval=fc.showOpenDialog(jPanel1);
        File file = fc.getSelectedFile();
        ByteBuffer wavdata=ByteBuffer.wrap(Files.readAllBytes(file.toPath())).order(ByteOrder.LITTLE_ENDIAN);
        
        UnifiedSoundFile sound=new UnifiedSoundFile(wavdata);
        soundFiles.add(sound);
        reloadTableData();
        }catch (Exception e){e.printStackTrace();}
    }//GEN-LAST:event_bAddSoundActionPerformed

/**
 * @param args the command line arguments
 */
public static void main(String args[])
    {
    /* Set the Nimbus look and feel */
    //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
     * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
     */
    try
        {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
            if ("Nimbus".equals(info.getName()))
                {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
                }
            }
        }
    catch (ClassNotFoundException ex)
        {
        java.util.logging.Logger.getLogger(FF8SND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    catch (InstantiationException ex)
        {
        java.util.logging.Logger.getLogger(FF8SND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    catch (IllegalAccessException ex)
        {
        java.util.logging.Logger.getLogger(FF8SND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
        java.util.logging.Logger.getLogger(FF8SND.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    //</editor-fold>

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable()
    {
    public void run()
        {
        new FF8SND().setVisible(true);
        }
    });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAddSound;
    private javax.swing.JButton bDebug;
    private javax.swing.JButton bExtract;
    private javax.swing.JButton bExtractAll;
    private javax.swing.JButton bPlaySound;
    private javax.swing.JButton bReplaceSound;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton loadFMT;
    private javax.swing.JButton saveFMT;
    private javax.swing.JTable tSounds;
    // End of variables declaration//GEN-END:variables
}
