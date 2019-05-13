package org.tokamak.xview;

import com.google.common.io.LittleEndianDataInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataFileReader {

    public DataFileReader() {
    }

    public void load(File dataFile){
        try {
            LittleEndianDataInputStream in = new LittleEndianDataInputStream(new FileInputStream(dataFile));

            //read head
            byte[] sign = new byte[20];
            in.read(sign);
            String version = new String(sign).substring(10, 16);
            int headLen = in.readInt();
            int dataLen = in.readInt();
            byte dataForm = in.readByte();
            byte dataType = in.readByte();
            int shotNumber = in.readInt();
            int shotDate = in.readInt();
            int shotTime = in.readInt();
            short addonLen = in.readShort();
            byte[] bAddon = new byte[addonLen];
            in.read(bAddon);
            String addon = new String(bAddon);
            short commentLen = in.readShort();
            byte[] comment = new byte[commentLen];
            in.read(comment);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
