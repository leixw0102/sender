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


import com.google.common.io.ByteSource;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.icntv.sender.compress.Compress;
import tv.icntv.sender.compress.DefaultCompress;
import tv.icntv.sender.conf.Configuration;
import tv.icntv.sender.decompress.DeCompress;
import tv.icntv.sender.decompress.DefaultDeCompress;

import java.io.*;
import java.nio.CharBuffer;

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
    private CharBuffer charBuffer = CharBuffer.allocate(BUFFER);


    @Inject
    private DeCompress unCompress;
    @Inject
    private Compress compress;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
    private String encode;
    public DefaultReCompress(@Named("source")String sourceFile, @Named("reCompressSource")String targetFile,@Named("encoding") String encode) {
        this.sourceFile = sourceFile;
        this.targetFile = targetFile;
        this.encode = encode;
        this.setFile(sourceFile);
    }


    @Override
    public boolean reCompress() throws IOException {

        BufferedReader reader=unCompress.getBufferedReader(getSourceFile(),this.encode);
        BufferedWriter writer = compress.getWriter(getTargetFile(),"utf-8");
        try {
            while(reader.read(charBuffer) !=-1){
                charBuffer.flip();
                writer.write(charBuffer.toString());
                charBuffer.clear();
            }
            writer.flush();

        } catch (Exception e) {
            logger.error("compress error !",e);
//            this.setFile(this.getTargetFile());
            return false;
        } finally {
            unCompress.close(reader);
            compress.close(writer);
        }
        this.setFile(this.getTargetFile());
        return true;

    }

    @Override
    public boolean isReCompress() {

        return !(compress instanceof DefaultCompress) ;
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
