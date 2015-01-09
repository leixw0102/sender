package tv.icntv.sender.compress;/*
 * Copyright 2014 Future TV, Inc.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */

import com.google.common.io.Closeables;
import com.google.inject.Inject;
import com.hadoop.compression.lzo.LzopCodec;


import tv.icntv.sender.conf.Configuration;

import java.io.*;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2014/04/04
 * Time: 11:48
 */
public class LzoCompressImpl extends AbstractCompress {


    private LzopCodec lzoCodec;

    public LzoCompressImpl() {
       super();
        lzoCodec = new LzopCodec();
        lzoCodec.setConf(getHdfsConfiguration());
    }



//    @Override
//    public void init() {
//        conf.setAlias("LZO",this.getClass());
//        lzoCodec = new LzopCodec();
//        lzoCodec.setConf(getHdfsConfiguration());
//    }

    protected  org.apache.hadoop.conf.Configuration getHdfsConfiguration() {
        org.apache.hadoop.conf.Configuration configuration = new org.apache.hadoop.conf.Configuration();
        configuration.set("mapred.job.tracker", "local");
        configuration.set("io.compression.codecs", "com.hadoop.compression.lzo.LzoCodec");
        return configuration;
    }

    @Override
    public OutputStream getOutputStream(String target) throws IOException {
        File file = new File(target);
        if (!file.exists()) {
            file.createNewFile();
        }
        return lzoCodec.createOutputStream(new FileOutputStream(file));
    }



}
