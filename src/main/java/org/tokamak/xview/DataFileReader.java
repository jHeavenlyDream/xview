package org.tokamak.xview;

import com.google.common.io.LittleEndianDataInputStream;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
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
        data.setTimeBegin(in.readInt());
        data.setTimeEnd(in.readInt());
        data.setTimeDelta(in.readInt());

        long rows = 0;
        double exp = 0;
        switch (data.getVersionFile()){
            case "01.040":{
                rows += data.getTimeEnd() / data.getTimeDelta() -
                        data.getTimeBegin() / data.getTimeDelta();
                exp = Double.valueOf("1E-7").longValue();
                break;
            }
            case "01.042":{
                rows += data.getTimeEnd() * 100 / data.getTimeDelta() -
                        data.getTimeBegin() *100 / data.getTimeDelta();
                exp = Double.valueOf("1E-9").longValue();
                break;
            }
            case "01.043":{
                rows += data.getTimeEnd() * 1000/ data.getTimeDelta() -
                        data.getTimeBegin() *1000 / data.getTimeDelta();
                exp = Double.valueOf("1E-10").longValue();
                break;
            }
        }

        //read data
        System.out.println("Row:" + rows);
        XYSeriesCollection seriesCollection =  new XYSeriesCollection();
        if(data.getDataType() == 6){
            for (int i = 0; i < data.getColumnNum(); i++) {
                XYSeries series = new XYSeries(data.listChannels()[i]);
                for (int j = 0; j < rows; j++) {
                    series.add(j, in.readFloat() + exp);
                }
                seriesCollection.addSeries(series);

            }
            data.setXyDataset(seriesCollection);
        }

        System.out.println("END READ");
    }

}
