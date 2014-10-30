package tv.icntv.sender.push;/*
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

import com.google.inject.name.Named;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tv.icntv.sender.AbstractSender;

import java.io.File;
import java.io.IOException;

/**
 * Created by leixw
 * <p/>
 * Author: leixw
 * Date: 2014/04/04
 * Time: 13:51
 */
public class HdfsDispatcher extends AbstractSender {
    Configuration configuration = new Configuration();
    DistributedFileSystem fileSystem = null;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final String WRITING = ".writing", WRITED = ".writed";


//    private String source;
    private String url;



    public HdfsDispatcher( @Named("hdfsUrl")String url) {

	    this.url = url;
//
//        logger.info("source {} \r\n url {}" , source,getUrl());
    }

//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void sendMsg(String file) throws Exception {
        long start = System.nanoTime();
        try {
            String hdfsUrl=getUrl()+ File.separator + new File(file).getName();
            Path source = new Path(file);
            Path target = new Path(hdfsUrl);
            logger.info("from ={} to ={}",file,hdfsUrl);
            fileSystem = (DistributedFileSystem) FileSystem.get(configuration);
            Path writedPath = new Path(hdfsUrl + WRITED);
            if (fileSystem.exists(writedPath) && fileSystem.exists(target)) {
                if (new File(file).length() == fileSystem.getFileStatus(target).getLen()) {
                    logger.info("source file " + file + " exist hdfs path=" + hdfsUrl);
                    return ;
                } else {
                    logger.info("delete file because file size no consistency");
                    fileSystem.delete(target, true);
                    fileSystem.delete(writedPath, true);
                }
            }
            Path writingPath = new Path(hdfsUrl + WRITING);
            //create...
            if (!fileSystem.exists(writingPath)) {
                logger.info("create file .writing");
                FSDataOutputStream out = fileSystem.create(writingPath);
                out.flush();
                out.close();
            }
            logger.info("start to hdfs...");
            fileSystem.copyFromLocalFile(false, true, source, target);
            //rename..
            logger.info("file to hdfs over..rename status from .writing to .writed ");
            fileSystem.rename(writingPath, writedPath);
            logger.info("complete file " + source + " to hdfs " + hdfsUrl + " . use time :" + (System.nanoTime() - start) / Math.pow(10, 9));
        } catch (Exception e) {

            throw e ;
        } finally {
            if (fileSystem != null) {
                try {
                    fileSystem.close();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }

}
