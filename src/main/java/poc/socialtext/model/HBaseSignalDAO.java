package poc.socialtext.model;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class HBaseSignalDAO {

    private HTable table;

    public HBaseSignalDAO() {
        try {
            table = new HTable(HBaseConfiguration.create(), "signals_raw");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void putRawSignals(Signal[] rawSignals) {
        List<Put> puts = new LinkedList<Put>();
        long id = 1;
        for (Signal rawSignal : rawSignals) {
            Put put = new Put((Bytes.toBytes(id++)));
            put.add(Bytes.toBytes("data"), Bytes.toBytes("raw"), Bytes.toBytes(rawSignal.toJson()));
        }
        try {
            table.put(puts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
