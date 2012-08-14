package tutorial;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Iterator;
import java.util.StringTokenizer;

public class WordCountTest {


    private MiniDFSCluster dfsCluster = null;
    private MiniMRCluster mrCluster = null;

    private final Path input = new Path("input");
    private final Path output = new Path("output");

    @Before
    public void setUp() throws Exception {

        // make sure the log folder exists,
        // otherwise the test fill fail
        new File("test-logs").mkdirs();
        //
        System.setProperty("hadoop.log.dir", "test-logs");
        System.setProperty("javax.xml.parsers.SAXParserFactory",
                "com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");
        System.setProperty("dfs.permissions.supergroup", "su");
        //

        Configuration conf = new Configuration();
        dfsCluster = new MiniDFSCluster(conf, 1, true, null);
        dfsCluster.getFileSystem().makeQualified(input);
        dfsCluster.getFileSystem().makeQualified(output);
        //
        mrCluster = new MiniMRCluster(1, getFileSystem().getUri().toString(), 1);
    }

    protected FileSystem getFileSystem() throws IOException {
        return dfsCluster.getFileSystem();
    }

    private void createTextInputFile() throws IOException {
        OutputStream os = getFileSystem().create(new Path(input, "wordcount"));
        Writer wr = new OutputStreamWriter(os);
        wr.write("b a a\n");
        wr.close();
    }

    private JobConf createJobConf() {
        JobConf conf = mrCluster.createJobConf();
        conf.setJobName("wordcount test");

        conf.setMapperClass(Map.class);
        conf.setReducerClass(Reduce.class);

        conf.setInputFormat(TextInputFormat.class);
        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(IntWritable.class);
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(IntWritable.class);
        conf.setNumMapTasks(1);
        conf.setNumReduceTasks(1);
        FileInputFormat.setInputPaths(conf, input);
        FileOutputFormat.setOutputPath(conf, output);
        return conf;
    }

    @Test
    public void testCount() throws Exception {

        // prepare for test
        createTextInputFile();

        // run job
        JobClient.runJob(createJobConf());

        // check the output
        Path[] outputFiles = FileUtil.stat2Paths(getFileSystem().listStatus(
                output, new Utils.OutputFileUtils.OutputLogFilter()));
        Assert.assertEquals(1, outputFiles.length);
        InputStream is = getFileSystem().open(outputFiles[0]);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Assert.assertEquals("a\t2", reader.readLine());
        Assert.assertEquals("b\t1", reader.readLine());
        Assert.assertNull(reader.readLine());
        reader.close();
    }

    @After
    public void tearDown() throws Exception {
        if (dfsCluster != null) {
            dfsCluster.shutdown();
            dfsCluster = null;
        }
        if (mrCluster != null) {
            mrCluster.shutdown();
            mrCluster = null;
        }
    }

    public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
        @Override
        public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            int sum = 0;
            while (values.hasNext()) {
                sum += values.next().get();
            }
            output.collect(key, new IntWritable(sum));
        }
    }

    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {
        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();

        @Override
        public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
            String line = value.toString();
            StringTokenizer tokenizer = new StringTokenizer(line);
            while (tokenizer.hasMoreTokens()) {
                word.set(tokenizer.nextToken());
                output.collect(word, one);
            }
        }
    }

}
