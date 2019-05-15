package org.tokamak.xview;

import com.google.common.io.LittleEndianDataInputStream;
import org.tokamak.xview.exception.DataFormException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DataFileReader {

    public DataFileReader() {
    }

    public DataFile load(File datafile) throws  IOException, DataFormException {

        DataFile data = new DataFile();
        LittleEndianDataInputStream in = new LittleEndianDataInputStream(new FileInputStream(datafile));

        //read head
        byte[] sign = new byte[20];
        in.read(sign);
        data.setSign(new String(sign));
        data.setHeadLength(in.readInt());
        data.setDataLength(in.readInt());
        data.setDataForm(in.readByte());
        data.setDataType(in.readByte());
        data.setShotNumber(in.readInt());
        data.setShotDate(in.readInt());
        data.setShotTime(in.readInt());
        byte[] addon = new byte[in.readShort()];
        in.read(addon);
        data.setAddon(new String(addon));
        byte[] comment = new byte[in.readShort()];
        in.read(comment);
        data.setComment(new String(comment));

        switch (data.getDataForm()) {
            case 0: {
                readDataFormZero(in, data);
                break;
            }
            default: {
                throw new DataFormException();
            }
        }

        return data;

    }

    private void readDataFormZero(LittleEndianDataInputStream in, DataFile data) throws IOException {
        data.setColumnNum(in.readInt());
        data.setTimeNum(in.readShort());

        //read time



    }

}
