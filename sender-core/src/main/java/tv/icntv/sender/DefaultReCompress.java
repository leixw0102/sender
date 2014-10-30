package tv.icntv.sender;/*
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


import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.icntv.sender.compress.Compress;
import tv.icntv.sender.compress.DefaultCompress;
import tv.icntv.sender.conf.Configuration;
import tv.icntv.sender.decompress.DeCompress;
import tv.icntv.sender.decompress.DefaultDeCompress;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2014/04/14
 * Time: 14:01
 */
public  class DefaultReCompress implements ReCompress {
    private int BUFFER = 128 * 1024* 1024;
    private String sourceFile;
    private String targetFile;
    private String file;



    @Inject
    private DeCompress unCompress;
    @Inject
    private Compress compress;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static String COMPRESS_NAME="sender.compress";
    private static String UNCOMPRESS_NAME="sender.decompress";
    @Inject
    private Configuration configuration;

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getTargetFile() {
        return targetFile;
    }

    public void setTargetFile(String targetFile) {
        this.targetFile = targetFile;
    }

    public DefaultReCompress() {
    }

    public DefaultReCompress(@Named("source")String sourceFile, @Named("reCompressSource")String targetFile) {
        this.sourceFile = sourceFile;
        this.targetFile = targetFile;
        this.setFile(sourceFile);
    }


    @Override
    public boolean reCompress() throws IOException {

        InputStream inputStream=unCompress.getInputStream(getSourceFile());
        OutputStream outputStream = compress.getOutputStream(getTargetFile());
        try {
            int count;
            byte data[] = new byte[BUFFER];
            while ((count = inputStream.read(data, 0, BUFFER)) != -1) {
                outputStream.write(data, 0, count);
            }
        } catch (Exception e) {
            logger.error("compress error !",e);
            return false;
        } finally {
            unCompress.close(inputStream);
            compress.close(outputStream);
        }
        this.setFile(this.getTargetFile());
        return true;

    }

    @Override
    public boolean isReCompress() {
        return !(compress instanceof DefaultCompress) && !(unCompress instanceof DefaultDeCompress);
    }

    @Override
    public String getSendFile() {
        return getFile();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
