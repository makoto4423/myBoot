package well.prometheus;

import io.prometheus.client.Collector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MyCollector extends Collector {
    @Override
    public List<MetricFamilySamples> collect() {
        List<MetricFamilySamples> mfs = new ArrayList<>();
        String metricName = "my_";

        MetricFamilySamples.Sample sample = new MetricFamilySamples.Sample(metricName, Collections.singletonList("l1"), Collections.singletonList("v1"),4);
        MetricFamilySamples samples = new MetricFamilySamples(metricName,Type.GAUGE,"help", Collections.singletonList(sample));
        mfs.add(samples);
        return mfs;
    }
}
